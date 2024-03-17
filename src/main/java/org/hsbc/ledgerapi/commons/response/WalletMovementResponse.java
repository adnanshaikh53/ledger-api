package org.hsbc.ledgerapi.commons.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletMovementResponse {
    private String requestId;
    private Long sourceWalletId;
    private Long destinationWalletId;
    private String status;
    private String description;
    private LocalDateTime createdAt;
}
