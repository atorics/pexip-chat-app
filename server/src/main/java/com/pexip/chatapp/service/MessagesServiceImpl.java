package com.pexip.chatapp.service;

import com.pexip.chatapp.domain.Channel;
import com.pexip.chatapp.domain.Message;
import com.pexip.chatapp.event.MessageCreatedEvent;
import com.pexip.chatapp.repository.MessagesRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessagesServiceImpl implements MessageService {
    private final ApplicationEventPublisher publisher;
    private MessagesRepository messagesRepository;

    MessagesServiceImpl(ApplicationEventPublisher publisher, MessagesRepository messagesRepository){
        this.publisher = publisher;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public Flux<Message> getMessagesByChannel(Channel channel, Boolean isActive) {
        return this.messagesRepository.findAll( );
    }

    @Override
    public Mono<Message> save(Message message) {

        return this.messagesRepository.save(message)
                .doOnSuccess(message1 -> this.publisher.publishEvent(new MessageCreatedEvent(message1)));
    }

    @Override
    public void delete(String id) {
        this.messagesRepository.deleteById(id).subscribe();
    }

    @Override
    public void deleteAll() {
        this.messagesRepository.deleteAll().subscribe();
    }

}
