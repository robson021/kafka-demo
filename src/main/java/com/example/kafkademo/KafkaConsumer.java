package com.example.kafkademo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.kafkademo.ConfigConstants.DEFAULT_TOPIC;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = DEFAULT_TOPIC)
    public void listen(String message) {
        log.info("Received message from Kafka: {}", message);
    }
}
