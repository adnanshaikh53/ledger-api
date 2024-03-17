package org.hsbc.ledgerapi.commons.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletMovementValidationResponse {
    Boolean isValidRequest;
    String reason;
}
