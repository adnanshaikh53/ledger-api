package org.hsbc.ledgerapi.kafka.constants;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class BalanceChangeEvent implements Serializable {
    private Long walletId;
    private Double balance;
}
