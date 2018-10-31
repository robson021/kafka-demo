package com.example.kafkademo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
@SuppressWarnings("all")
public class RoutesConfig {

    private final KafkaTemplate template;

    public RoutesConfig(KafkaTemplate template) {
        this.template = template;
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(POST("/message"), this::postNewMessage)
                .andRoute(GET("/message"), this::getRecentMessage);
    }

    private Mono<ServerResponse> postNewMessage(ServerRequest request) {
        template.send(new GenericMessage<>("example msg " + LocalDateTime.now()));
        return ServerResponse.ok().contentType(TEXT_PLAIN).syncBody("Message has been added.");
    }

    private Mono<ServerResponse> getRecentMessage(ServerRequest request) {
        return ServerResponse.badRequest().syncBody("TODO");
    }

}
