package org.hsbc.ledgerapi.kafka.broadcast.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncPaymentCreatedListener {
    @KafkaListener(topics = "payment-created-topic", groupId = "ledger-client-group")
    public void asyncPaymentCreated(String event) {
        log.info("event received for Async payment change, sending it to notification service {}",event);
    }
}
