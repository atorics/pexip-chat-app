package com.pexip.chatapp.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

@Component
public class MessageCreatedEventPublisher implements
        ApplicationListener<MessageCreatedEvent>,
        Consumer<FluxSink<MessageCreatedEvent>> {

    private FluxSink<MessageCreatedEvent> sink;

    @Override
    public void onApplicationEvent(MessageCreatedEvent event) {
        FluxSink<MessageCreatedEvent> sink = this.sink;
        if (sink != null) {
            sink.next(event);
        }
    }

    @Override
    public void accept(FluxSink<MessageCreatedEvent> sink) {
        this.sink = sink;
        sink.onDispose(() -> this.sink = null);
    }
}
