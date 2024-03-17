package org.hsbc.ledgerapi.kafka.broadcast.producer;

import org.hsbc.ledgerapi.commons.response.WalletMovementResponse;
import org.hsbc.ledgerapi.kafka.constants.BalanceChangeEvent;
import org.hsbc.ledgerapi.kafka.constants.WalletMovementChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventChangeBroadcaster {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaEventChangeBroadcaster(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishBalanceChangeEvent(BalanceChangeEvent event) {
        kafkaTemplate.send("balance-change-topic", event.toString());
    }

    public void publishMovementEvent(WalletMovementChangeEvent event) {
        kafkaTemplate.send("wallet-movement-topic", event.toString());
    }

    public void publishPaymentCompletionAsyncEvent(WalletMovementResponse event) {
        kafkaTemplate.send("payment-created-topic", event.toString());
    }
}
