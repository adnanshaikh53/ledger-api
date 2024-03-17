package org.hsbc.ledgerapi.kafka.constants;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class WalletMovementChangeEvent implements Serializable {
    private Long sourceWalletId;
    private Long destinationWalletId;
    private Double amount;
}
