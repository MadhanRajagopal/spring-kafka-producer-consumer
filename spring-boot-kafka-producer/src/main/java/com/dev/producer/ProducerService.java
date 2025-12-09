package com.dev.producer;

import com.dev.model.PaymentEvent;
import com.dev.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.dev.config.ProducerConfiguration.PAYMENT_TOPIC;

@Service
public class ProducerService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    /**
     * Generate a random transaction
     *
     * @return transaction result
     */
    public PaymentEvent getTransactionDetails() {
        String[] Payments = {"Visa", "Paypal", "UPI", "Amazon Pay"};
        String PaymentMode = Payments[ThreadLocalRandom.current().nextInt(Payments.length)];
        double amount = ThreadLocalRandom.current().nextDouble(0.10, 1000);
        PaymentEvent paymentEvent = new PaymentEvent(UUID.randomUUID().toString(), PaymentMode, amount, PaymentStatus.SUCCESS, Instant.now().toEpochMilli());
        log.info("event message :: {}", paymentEvent);
        return paymentEvent;
    }

    /**
     * Sending Payment transaction to a kafka topic via asynchronously
     */
    @Scheduled(fixedDelay = 10000)
    public void sendPaymentTransaction() {
        PaymentEvent payment = getTransactionDetails();
        kafkaTemplate.send(PAYMENT_TOPIC, UUID.randomUUID().toString(), payment)
                .whenComplete((sendResult, exception) -> {
                    if (exception != null) {
                        onFailure((Throwable) exception);
                    } else {
                        onSuccess((SendResult<String, PaymentEvent>) sendResult);
                    }
                });
    }


    /**
     * Capture kafka publish event details
     *
     * @param result
     */
    private void onSuccess(SendResult<String, PaymentEvent> result) {
        log.info("""
                        Message sent successfully
                        Topic : {}
                        Partition : {}
                        Offset : {}
                        Timestamp :{}
                        """, result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().timestamp());
    }

    /**
     * Capture exception during publish event
     *
     * @param throwable
     */
    private void onFailure(Throwable throwable) {
        log.error("Error occurred during payment transaction ::", throwable);
    }


}
