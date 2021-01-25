package com.pexip.chatapp.service;

import com.pexip.chatapp.domain.Channel;
import com.pexip.chatapp.repository.ChannelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChannelServiceImpl implements  ChannelService {

    private ChannelRepository channelRepository;


    @Override
    public Channel findByName(String name) {
        return  channelRepository.findByName(name);

    }
}
