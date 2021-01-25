package com.pexip.chatapp.event;

import com.pexip.chatapp.domain.Profile;
import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(Profile source) {
        super(source);
    }
}