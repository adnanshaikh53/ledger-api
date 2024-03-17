package org.hsbc.ledgerapi.command.controller;

import org.hsbc.ledgerapi.command.service.*;
import org.hsbc.ledgerapi.commons.dto.LedgerEntityDto;
import org.hsbc.ledgerapi.commons.enums.AccountState;
import org.hsbc.ledgerapi.commons.enums.PostingState;
import org.hsbc.ledgerapi.commons.request.WalletMovementRequest;
import org.hsbc.ledgerapi.commons.response.EntityCreationResponse;
import org.hsbc.ledgerapi.commons.response.TransactionPostingResponse;
import org.hsbc.ledgerapi.commons.response.WalletMovementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController("ledger/api/v1")
public class CommandsController {

    private final WalletMovementService walletMovementService;
    private final AccountStateUpdateService accountStateUpdateService;
    private final EntityService entityService;
    private final PostingStateUpdateService postingStateUpdateService;
    private final AsyncWalletMovementService asyncWalletMovementService;

    @Autowired
    public CommandsController(WalletMovementService walletMovementService, AccountStateUpdateService accountStateUpdateService, EntityService entityService, PostingStateUpdateService postingStateUpdateService, AsyncWalletMovementService asyncWalletMovementService) {
        this.walletMovementService = walletMovementService;
        this.accountStateUpdateService = accountStateUpdateService;
        this.entityService = entityService;
        this.postingStateUpdateService = postingStateUpdateService;
        this.asyncWalletMovementService = asyncWalletMovementService;
    }
    @PostMapping("wallet/moveAsset")
    private ResponseEntity<List<WalletMovementResponse>> moveAssets(@RequestBody List<WalletMovementRequest> walletMovementRequests){
        if(!CollectionUtils.isEmpty(walletMovementRequests)){
            if(walletMovementRequests.size()>10){
                List<WalletMovementResponse> walletMovementResponses = new ArrayList<>();
                walletMovementResponses.add(WalletMovementResponse.builder()
                        .status("Not processed")
                        .description("Ledger system cannot handle more than 10 movements at a time, please use Asynchronous function to process more than 10 request")
                        .createdAt(LocalDateTime.now())
                        .build());
                return new ResponseEntity<>(walletMovementResponses,HttpStatus.OK);
            }
        }
        List<WalletMovementResponse> response =  walletMovementService.createPayment(walletMovementRequests);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("account/state/update")
    private ResponseEntity<String> changeAccountState(@RequestParam Long accountId,
                                                      @RequestParam AccountState accountState){
        accountStateUpdateService.updateAccountState(accountId,accountState);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("entity/create")
    private ResponseEntity<EntityCreationResponse> initialEntitySetup(@RequestBody LedgerEntityDto ledgerEntityDto){
        EntityCreationResponse response = entityService.saveEntity(ledgerEntityDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("posting/state/update")
    private ResponseEntity<TransactionPostingResponse> changePostingState(@RequestParam Long transactionid,
                                                                          @RequestParam PostingState postingState){
        TransactionPostingResponse response = postingStateUpdateService.updatePostingState(transactionid,postingState);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("wallet/moveAsset/async")
    private ResponseEntity<WalletMovementResponse> moveAssetsAsynchronously(@RequestBody List<WalletMovementRequest> walletMovementRequests){
        asyncWalletMovementService.processAsyncMovement(walletMovementRequests);
        WalletMovementResponse response = WalletMovementResponse.builder().status("Scheduled")
                .description("Your wallet movement request is scheduled, you will be notified once request is completed")
                .createdAt(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
