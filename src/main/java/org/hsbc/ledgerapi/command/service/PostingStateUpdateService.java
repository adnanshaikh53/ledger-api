package org.hsbc.ledgerapi.command.service;

import org.hsbc.ledgerapi.commons.enums.PostingState;
import org.hsbc.ledgerapi.commons.model.LedgerTransaction;
import org.hsbc.ledgerapi.commons.response.TransactionPostingResponse;
import org.hsbc.ledgerapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostingStateUpdateService {

    private final TransactionRepository transactionRepository;

    public PostingStateUpdateService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionPostingResponse updatePostingState(Long transactionid, PostingState postingState) {
        Optional<LedgerTransaction> transaction = transactionRepository.findById(transactionid);
         if(transaction.isPresent()){
             transaction.get().setState(postingState);
             transactionRepository.save(transaction.get());
             return TransactionPostingResponse.builder()
                     .status("Updated")
                     .description("Posting updated to "+postingState.name()+" for transaction Id "+transactionid)
                     .createdAt(LocalDateTime.now())
                     .build();
         }
         return TransactionPostingResponse.builder()
                     .status("Failed")
                     .description("No transaction found for Transaction Id "+transactionid)
                     .createdAt(LocalDateTime.now())
                     .build();
    }
}
