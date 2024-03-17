package org.hsbc.ledgerapi.kafka.broadcast.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaBalanceChangeListener {

    @KafkaListener(topics = "balance-change-topic", groupId = "ledger-client-group")
    public void listenBalanceChange(String event) {
        log.info("event received for Balance change , sending it to notification service {}",event);
    }
}
