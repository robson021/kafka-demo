package com.example.kafkademo;

import com.example.kafkademo.db.Message;
import com.example.kafkademo.db.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
@SuppressWarnings("all")
public class RoutesConfig {

    private static final Mono<ServerResponse> MESSAGE_ADDED_RESPONSE = ServerResponse.ok().contentType(TEXT_PLAIN).syncBody("Message has been added.");

    private final KafkaTemplate template;

    private final MessageRepository repository;

    public RoutesConfig(KafkaTemplate template, MessageRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions //
                .route(POST("/message/{text}"), this::postNewMessage)
                .andRoute(GET("/message"), this::getRecentMessages);
    }

    private Mono<ServerResponse> postNewMessage(ServerRequest request) {
        String text = request.pathVariable("text");
        template.send(new GenericMessage<>(text));
        return MESSAGE_ADDED_RESPONSE;
    }

    private Mono<ServerResponse> getRecentMessages(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findAll(), Message.class);
    }

}
