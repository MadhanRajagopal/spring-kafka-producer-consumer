package com.dev.config;

import com.dev.model.PaymentEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {


    public static final String PAYMENT_TOPIC = "payment-topic";
    public static final int NUM_PARTITIONS = 3;
    public static final int REPLICAS = 1;
    public static final String KAFKA_SERVERS = "localhost:9092";


    /**
     * Configuration related to DefaultKafkaProducerFactory for sending custom object messages
     *
     * @return DefaultKafkaProducerFactory
     */
    @Bean
    public ProducerFactory<String, PaymentEvent> producerFactory() {
        Map<String, Object> configMaps = new HashMap<>();
        configMaps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVERS);
        configMaps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMaps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configMaps);
    }




    /**
     * Creating Kafka template with kafka producer properties
     *
     * @return KafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, PaymentEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Creating a new topic as payment-topic with three partitions and one replication
     *
     * @return
     */
    @Bean
    public NewTopic paymentTopic() {
        return new NewTopic(PAYMENT_TOPIC, NUM_PARTITIONS, (short) REPLICAS);
    }


}
