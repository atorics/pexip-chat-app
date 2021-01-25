package com.pexip.chatapp.event;

import com.pexip.chatapp.domain.Message;
import org.springframework.context.ApplicationEvent;

public class MessageCreatedEvent extends ApplicationEvent {
    public MessageCreatedEvent(Message source) {
        super(source);
    }
}
