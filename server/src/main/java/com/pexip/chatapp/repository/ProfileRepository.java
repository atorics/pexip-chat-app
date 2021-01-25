package com.pexip.chatapp.repository;

import com.pexip.chatapp.domain.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProfileRepository extends ReactiveMongoRepository<Profile , String> {
}
