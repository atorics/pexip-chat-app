package com.pexip.chatapp;


import com.pexip.chatapp.domain.Channel;
import com.pexip.chatapp.domain.Message;
import com.pexip.chatapp.domain.Profile;
import com.pexip.chatapp.repository.ChannelRepository;
import com.pexip.chatapp.repository.MessagesRepository;
import com.pexip.chatapp.repository.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Log4j2
@Component
//@Profile("demo")
class SampleDataInitializer
        implements ApplicationListener<ApplicationReadyEvent> {

    private final ProfileRepository repository;
    private final ChannelRepository channelRepository;
    private final MessagesRepository messagesRepository;

    public SampleDataInitializer(ProfileRepository repository, ChannelRepository channelRepository, MessagesRepository messagesRepository) {
        this.repository = repository;
        this.channelRepository = channelRepository;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent  event) {
        String andrewId = UUID.randomUUID().toString();

        repository
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Adam Jhones", "B", "C", "D","Anderw Smith")
                                .map(name -> {
                                    if (name.equals("Anderw Smith")) {
                                        return new Profile(andrewId, name + "@email.com");
                                    } else {
                                        return new Profile(UUID.randomUUID().toString(), name + "@email.com") ;
                                    }
                                })
                                .flatMap(repository::save)
                )
                .thenMany(repository.findAll())
                .subscribe(profile -> log.info("saving " + profile.toString()));

        channelRepository.deleteAll();
        Channel channel = new Channel(UUID.randomUUID().toString(), "conversation");
        channelRepository.save(channel );

        messagesRepository.deleteAll().subscribe();

        Mono<Profile> profileMono = repository.findById(andrewId);
        profileMono.subscribe(profile -> {
            Message message = new Message(UUID.randomUUID().toString(),profile, channel,"Hello new" + Instant.now() , true, Instant.now());
            messagesRepository.deleteAll().then(
                    Mono.just(message)
                            .flatMap(messagesRepository::save)
            ).thenMany(messagesRepository.findAll())
                    .subscribe(message1 ->  log.info("saving message " + message1.toString() ));

        });




    }
}
