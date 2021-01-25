package com.pexip.chatapp.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

@Component
public class ProfileCreatedEventPublisher implements
        ApplicationListener<ProfileCreatedEvent>,
        Consumer<FluxSink<ProfileCreatedEvent>> {

    private FluxSink<ProfileCreatedEvent> sink;

    @Override
    public void onApplicationEvent(ProfileCreatedEvent event) {
        FluxSink<ProfileCreatedEvent> sink = this.sink;
        if (sink != null) {
            sink.next(event);
        }
    }

    @Override
    public void accept(FluxSink<ProfileCreatedEvent> sink) {
        this.sink = sink;
        sink.onDispose(() -> this.sink = null);
    }
}