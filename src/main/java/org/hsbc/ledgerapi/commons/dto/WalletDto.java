package org.hsbc.ledgerapi.commons.dto;

import lombok.Builder;
import lombok.Data;
import org.hsbc.ledgerapi.commons.enums.AssetType;

import java.math.BigDecimal;

@Data
@Builder
public class WalletDto {
    private BigDecimal balance;
    private AssetType assetType;
    private String identifier;
}
