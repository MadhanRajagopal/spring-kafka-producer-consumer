package com.dev.consumer;

import com.dev.model.PaymentEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @RetryableTopic(attempts = "3", backOff = @BackOff(delay = 2000, multiplier = 2))
    @KafkaListener(topics = "payment-topic", groupId = "group_id")
    public void consume(ConsumerRecord<String, PaymentEvent> message) {
        log.info("Key {} | value {}", message.key(), message.value());
        log.info("Partition {} | Offset {}", message.partition(), message.offset());
/*
        trigger event processing failure
        Integer.parseInt(message.value().paymentId());*/


    }

    @DltHandler
    public void processFailureEvent(ConsumerRecord<String, PaymentEvent> message) {
        log.info("Key {} | value {}", message.key(), message.value());
        // send email notification to team
    }
}
