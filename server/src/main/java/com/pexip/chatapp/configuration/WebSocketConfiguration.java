package com.pexip.chatapp.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pexip.chatapp.event.MessageCreatedEvent;
import com.pexip.chatapp.event.MessageCreatedEventPublisher;
import com.pexip.chatapp.event.ProfileCreatedEvent;
import com.pexip.chatapp.event.ProfileCreatedEventPublisher;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.util.Collections;

@Log4j2
@Configuration
class WebSocketConfiguration {

    @Bean
    HandlerMapping profileHandlerMapping(@Qualifier("profileWebSocketHandler") WebSocketHandler wsh) {
        return new SimpleUrlHandlerMapping() {
            {
                setUrlMap(Collections.singletonMap("/ws/profiles", wsh));
                setOrder(10);

            }
        };
    }

    @Bean
    HandlerMapping messageHandlerMapping(@Qualifier("messageWebSocketHandler") WebSocketHandler wsh) {
        return new SimpleUrlHandlerMapping() {
            {
                setUrlMap(Collections.singletonMap("/ws/conversations", wsh));
                setOrder(10);
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean(name = "profileWebSocketHandler")
    WebSocketHandler profileWebSocketHandler(
            ObjectMapper objectMapper,
            ProfileCreatedEventPublisher eventPublisher
    ) {

        Flux<ProfileCreatedEvent> publish = Flux
                .create(eventPublisher)
                .share();

        return session -> {

            Flux<WebSocketMessage> messageFlux = publish
                    .map(evt -> {
                        try {
                            return objectMapper.writeValueAsString(evt.getSource());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(str -> {
                        log.info("sending " + str);
                        return session.textMessage(str);
                    });

            return session.send(messageFlux);
        };
    }

    @Bean(name = "messageWebSocketHandler")
    WebSocketHandler messageWebSocketHandler(
            ObjectMapper objectMapper,
            MessageCreatedEventPublisher messagePublisher
    ) {

        Flux<MessageCreatedEvent> publish = Flux
                .create(messagePublisher)
                .share();

        return session -> {

            Flux<WebSocketMessage> messageFlux = publish
                    .map(evt -> {
                        try {
                            return objectMapper.writeValueAsString(evt.getSource());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(str -> {
                        log.info("sending " + str);
                        return session.textMessage(str);
                    });

            return session.send(messageFlux);
        };
    }
}