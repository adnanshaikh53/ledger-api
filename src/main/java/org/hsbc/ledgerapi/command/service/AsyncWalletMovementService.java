package org.hsbc.ledgerapi.command.service;

import org.hsbc.ledgerapi.commons.request.WalletMovementRequest;
import org.hsbc.ledgerapi.commons.response.WalletMovementResponse;
import org.hsbc.ledgerapi.kafka.broadcast.producer.KafkaEventChangeBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsyncWalletMovementService {

    private final WalletMovementService walletMovementService;
    private final KafkaEventChangeBroadcaster broadcaster;

    @Autowired
    public AsyncWalletMovementService(WalletMovementService walletMovementService, KafkaEventChangeBroadcaster broadcaster, KafkaEventChangeBroadcaster broadcaster1) {
        this.walletMovementService = walletMovementService;
        this.broadcaster = broadcaster1;
    }

    @Async
    @Transactional
    public void processAsyncMovement(List<WalletMovementRequest> request) {
        List<WalletMovementResponse> response = walletMovementService.createPayment(request);
        response.forEach(broadcaster::publishPaymentCompletionAsyncEvent);

    }
}
