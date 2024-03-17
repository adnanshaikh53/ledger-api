package org.hsbc.ledgerapi.query.service;

import org.hsbc.ledgerapi.repository.WalletRepository;
import org.hsbc.ledgerapi.commons.model.LedgerTransaction;
import org.hsbc.ledgerapi.commons.model.Wallet;
import org.hsbc.ledgerapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LedgerBalanceService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public LedgerBalanceService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public BigDecimal getLatestBalance(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        return wallet.getBalance();
    }

    @Transactional(readOnly = true)
    public BigDecimal getHistoricalBalance(Long walletId, Date timestamp) {
        List<LedgerTransaction> transactions = transactionRepository.findAllBySourceWalletIdOrDestinationWalletIdAndTimestampBefore(walletId, walletId, timestamp);

        BigDecimal balance = BigDecimal.ZERO;
        for (LedgerTransaction ledgerTransaction : transactions) {
            if (ledgerTransaction.getSourceWallet().getId().equals(walletId)) {
                balance = balance.subtract(ledgerTransaction.getAmount());
            } else {
                balance = balance.add(ledgerTransaction.getAmount());
            }
        }

        return balance;
    }
}
