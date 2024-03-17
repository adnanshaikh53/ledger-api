package org.hsbc.ledgerapi.kafka.broadcast.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaWalletMovementListener {
    @KafkaListener(topics = "wallet-movement-topic", groupId = "ledger-client-group")
    public void listenWalletMovementChange(String walletMovement) {
        log.info("wallet received for Wallet movement change , sending it to notification service {}",walletMovement);
    }
}
