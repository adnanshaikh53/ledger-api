package org.hsbc.ledgerapi.command.service;

import org.hsbc.ledgerapi.commons.AssetExchangeConstants;
import org.hsbc.ledgerapi.commons.enums.AccountState;
import org.hsbc.ledgerapi.commons.enums.AssetType;
import org.hsbc.ledgerapi.commons.enums.PostingState;
import org.hsbc.ledgerapi.commons.model.LedgerTransaction;
import org.hsbc.ledgerapi.commons.model.Wallet;
import org.hsbc.ledgerapi.commons.request.WalletMovementRequest;
import org.hsbc.ledgerapi.commons.response.WalletMovementResponse;
import org.hsbc.ledgerapi.kafka.broadcast.producer.KafkaEventChangeBroadcaster;
import org.hsbc.ledgerapi.kafka.constants.BalanceChangeEvent;
import org.hsbc.ledgerapi.kafka.constants.WalletMovementChangeEvent;
import org.hsbc.ledgerapi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletMovementService {

    private final WalletRepository walletRepository;


    private final KafkaEventChangeBroadcaster broadcaster;

    @Autowired
    public WalletMovementService(WalletRepository repository, KafkaEventChangeBroadcaster broadcaster) {
        this.walletRepository = repository;
        this.broadcaster = broadcaster;
    }
    @Transactional
    public List<WalletMovementResponse> createPayment(List<WalletMovementRequest> walletMovementRequests) {
        List<WalletMovementResponse> walletMovementResponses = new ArrayList<>();
        for(WalletMovementRequest walletMovementRequest: walletMovementRequests){
            Optional<Wallet> sourceWalletOptional = walletRepository.findById(walletMovementRequest.getSourceWalletId());
            Optional<Wallet> destinationWalletOptional = walletRepository.findById(walletMovementRequest.getDestinationWalletId());

            if (sourceWalletOptional.isPresent() && destinationWalletOptional.isPresent()) {
                Wallet sourceWallet = sourceWalletOptional.get();
                Wallet destinationWallet = destinationWalletOptional.get();

                if (sourceWallet.getAccount().getStatus().name().equals(AccountState.CLOSED.name())) {
                    walletMovementResponses.add(handleClosedAccountScenario());
                }

                if (sourceWallet.getId().equals(destinationWallet.getId())) {
                    walletMovementResponses.add(handleSameWalletScenario());
                }

                if (!sourceWallet.getAssetType().equals(destinationWallet.getAssetType())) {
                    double exchangeRate = getExchangeRate(sourceWallet.getAssetType(), destinationWallet.getAssetType());
                    if (exchangeRate <= 0) {
                        walletMovementResponses.add(handleNoExchangeRateAvailable());
                    }
                    double convertedAmount = walletMovementRequest.getAmount() * exchangeRate;
                    if (sourceWallet.getBalance().compareTo(BigDecimal.valueOf(walletMovementRequest.getAmount())) < 0) {
                        walletMovementResponses.add(WalletMovementResponse.builder().build());
                    }
                    sourceWallet.setBalance(sourceWallet.getBalance().subtract(BigDecimal.valueOf(walletMovementRequest.getAmount())));
                    destinationWallet.setBalance(destinationWallet.getBalance().add(BigDecimal.valueOf(convertedAmount)));
                } else {
                    walletMovementResponses.add(moveAssets(sourceWallet, destinationWallet, walletMovementRequest.getAmount()));
                }
                walletRepository.save(sourceWallet);
                walletRepository.save(destinationWallet);
                sendWalletMovementBroadcast(sourceWallet.getId(),destinationWallet.getId(),walletMovementRequest.getAmount());
                sendWalletBalanceUpdate(sourceWallet.getId(),sourceWallet.getBalance().doubleValue());
                sendWalletBalanceUpdate(destinationWallet.getId(),destinationWallet.getBalance().doubleValue());
                walletMovementResponses.add(handleSuccessScenario());
            }
            walletMovementResponses.add(WalletMovementResponse.builder()
                    .status("Failure")
                    .description("Source or destination wallet not found")
                    .build());
        }
        return walletMovementResponses;
    }

    public void sendWalletMovementBroadcast(Long sourceWalletId, Long destinationWalletId, double amount) {
        broadcaster.publishMovementEvent(WalletMovementChangeEvent.builder()
                        .sourceWalletId(sourceWalletId)
                        .destinationWalletId(destinationWalletId)
                        .amount(amount)
                .build());
    }

    public void sendWalletBalanceUpdate(Long walletId, double balance) {
        broadcaster.publishBalanceChangeEvent(BalanceChangeEvent.builder().balance(balance).walletId(walletId).build());
    }

    private double getExchangeRate(AssetType sourceAssetType, AssetType destinationAssetType) {
        String assetPair = sourceAssetType.toString() + "^" + destinationAssetType.toString();
        if (AssetExchangeConstants.assetExchangePair.containsKey(assetPair)) {
            return AssetExchangeConstants.assetExchangePair.get(assetPair);
        }
        return -1;
    }

    private WalletMovementResponse moveAssets(Wallet sourceWallet, Wallet destinationWallet, double amount) {
        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
        BigDecimal sourceBalance = sourceWallet.getBalance();
        BigDecimal destinationBalance = destinationWallet.getBalance();
        if (sourceBalance.compareTo(amountBigDecimal) >= 0) {
            setTransactionDetails(sourceWallet, destinationWallet, amount);
            sourceWallet.setBalance(sourceBalance.subtract(amountBigDecimal));
            destinationWallet.setBalance(destinationBalance.add(amountBigDecimal));
            walletRepository.save(sourceWallet);
            walletRepository.save(destinationWallet);
            sendWalletMovementBroadcast(sourceWallet.getId(),destinationWallet.getId(),amount);
            sendWalletBalanceUpdate(sourceWallet.getId(),sourceWallet.getBalance().doubleValue());
            sendWalletBalanceUpdate(destinationWallet.getId(),destinationWallet.getBalance().doubleValue());
            return handleSuccessScenario();
        }
        return WalletMovementResponse.builder()
                .status("Failed")
                .description("Insufficient Funds")
                .createdAt(LocalDateTime.now())
                .build();

    }

    private void setTransactionDetails(Wallet sourceWallet, Wallet destinationWallet, double amount) {
        LedgerTransaction transaction = LedgerTransaction.builder()
                .state(PostingState.CLEARED)
                .timestamp(new Date())
                .amount(new BigDecimal(amount))
                .sourceWallet(sourceWallet)
                .destinationWallet(destinationWallet)
                .build();
        List<LedgerTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        sourceWallet.setOutgoingLedgerTransactions(transactions);
        destinationWallet.setIncomingLedgerTransactions(transactions);
    }


    private WalletMovementResponse handleClosedAccountScenario() {
        return WalletMovementResponse.builder()
                .status("Failed")
                .description("Account is currently closed for the wallet , kindly re-open the account again to use wallet")
                .createdAt(LocalDateTime.now())
                .build();
    }


    private WalletMovementResponse handleSameWalletScenario() {
        return WalletMovementResponse.builder()
                .status("Failed")
                .description("Same wallets provided for Source and Destination")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private WalletMovementResponse handleNoExchangeRateAvailable() {
        return WalletMovementResponse.builder()
                .status("Failed")
                .description("No exchange rate available for the asset pair")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private WalletMovementResponse handleSuccessScenario() {
        return WalletMovementResponse.builder()
                .status("Success")
                .description("Moved Assets Successfully")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
