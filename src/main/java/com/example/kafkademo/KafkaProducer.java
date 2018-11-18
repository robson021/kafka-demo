package com.example.kafkademo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@SuppressWarnings("all")
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate template;

    public KafkaProducer(KafkaTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 3_500L)
    public void publishData() {
        String message = UUID.randomUUID().toString();
        log.info("Sending message to Kafka: {}", message);
        template.send(new GenericMessage<>(message));
    }
}
