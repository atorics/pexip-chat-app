package com.pexip.chatapp.service;

import com.pexip.chatapp.domain.Channel;
import com.pexip.chatapp.domain.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Flux<Message> getMessagesByChannel(Channel channel, Boolean isActive);

    Mono<Message> save(Message message);

    void delete(String id);
    void deleteAll();
}
