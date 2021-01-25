package com.pexip.chatapp.repository;

import com.pexip.chatapp.domain.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MessagesRepository extends ReactiveMongoRepository<Message, String > {

}
