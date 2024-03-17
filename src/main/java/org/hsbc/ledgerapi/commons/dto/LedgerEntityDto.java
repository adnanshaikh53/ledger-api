package org.hsbc.ledgerapi.commons.dto;

import lombok.Data;
import org.hsbc.ledgerapi.commons.enums.EntityType;

import java.util.List;

@Data
public class LedgerEntityDto {
    private String name;
    private EntityType type;
    private List<AccountDto> accounts;

}
