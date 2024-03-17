package org.hsbc.ledgerapi.commons.dto;

import lombok.Data;
import org.hsbc.ledgerapi.commons.enums.AccountState;
import org.hsbc.ledgerapi.commons.model.LedgerEntity;

import java.util.List;

@Data
public class AccountDto {
    private String name;
    private AccountState status;
    private List<WalletDto> wallets;
}