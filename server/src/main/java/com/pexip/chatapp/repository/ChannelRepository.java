package com.pexip.chatapp.repository;

import com.pexip.chatapp.domain.Channel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelRepository extends MongoRepository<Channel, String> {


    Channel findByName(String name);
}
