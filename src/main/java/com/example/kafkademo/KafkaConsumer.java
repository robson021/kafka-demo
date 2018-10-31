package com.example.kafkademo;

import com.example.kafkademo.db.Message;
import com.example.kafkademo.db.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.kafkademo.ConfigConstants.DEFAULT_TOPIC;

@Component
@SuppressWarnings("all")
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private final MessageRepository repository;

    public KafkaConsumer(MessageRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = DEFAULT_TOPIC)
    public void listen(String message) {
        repository.save(new Message(message))
                .subscribe(msg -> log.info("Saved entity: {}", msg));
    }
}
