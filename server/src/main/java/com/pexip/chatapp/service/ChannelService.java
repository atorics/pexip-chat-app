package com.pexip.chatapp.service;

import com.pexip.chatapp.domain.Channel;
import reactor.core.publisher.Mono;

/**
 * Channel Service functionality
 */
public interface ChannelService {

    Channel findByName(String name);
}
