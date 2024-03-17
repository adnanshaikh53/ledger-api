package org.hsbc.ledgerapi.command.service;

import org.hsbc.ledgerapi.commons.request.WalletMovementRequest;
import org.hsbc.ledgerapi.commons.response.WalletMovementValidationResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class ValidatePaymentRequest implements Function<WalletMovementRequest, WalletMovementValidationResponse> {
    @Override
    public WalletMovementValidationResponse apply(WalletMovementRequest walletMovementRequest) {
        return WalletMovementValidationResponse.builder().isValidRequest(true).reason("Valid Payment").build();
    }
}
