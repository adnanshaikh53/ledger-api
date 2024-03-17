package org.hsbc.ledgerapi.command.service;

import org.hsbc.ledgerapi.repository.AccountRepository;
import org.hsbc.ledgerapi.commons.enums.AccountState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountStateUpdateService {

    private final AccountRepository repository;

    @Autowired
    public AccountStateUpdateService(AccountRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public void updateAccountState(Long accountId, AccountState state){
        repository.updateAccountState(state,accountId);
    }


}
