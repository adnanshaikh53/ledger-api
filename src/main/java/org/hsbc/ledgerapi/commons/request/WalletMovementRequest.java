package org.hsbc.ledgerapi.commons.request;

import lombok.Builder;
import lombok.Data;
import org.hsbc.ledgerapi.commons.enums.AssetType;

@Data
@Builder
public class WalletMovementRequest {
    private Long sourceWalletId;
    private Long destinationWalletId;
    private AssetType assetType;
    private String assetIdentifier;
    private Double amount;
}
