package com.pexip.chatapp.web.rest;

import com.pexip.chatapp.domain.Channel;
import com.pexip.chatapp.domain.Message;
import com.pexip.chatapp.service.ChannelService;
import com.pexip.chatapp.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
@RequestMapping("/channels/{name}")
public class ChannelController {

    private ChannelService channelService;
    private MessageService messageService;

    ChannelController(ChannelService channelService, MessageService messageService) {
        this.channelService = channelService;
        this.messageService = messageService;
    }


    @PostMapping("")
    public ResponseEntity<Mono<Message>> createMessage(@PathVariable(name = "name") String channelName, @RequestBody Message message){

        Channel channel = this.channelService.findByName(channelName);

        if (channel == null ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        message.setChannel(channel);
        message.setCreatedOn(Instant.now());
        message.setStatus(true);

        Mono<Message> messageMono = messageService.save(message);

        return new ResponseEntity<>(messageMono, HttpStatus.CREATED);

    }

    @GetMapping("/messages")
    public ResponseEntity<Flux<Message>> getMessages(@PathVariable(name = "name") String channelName) {
        Channel channel = this.channelService.findByName(channelName);

        if (channel == null ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Flux<Message> messageFlux = this.messageService.getMessagesByChannel(channel, true);

        return new ResponseEntity<>(messageFlux, HttpStatus.OK);

    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity deleteMessage(@PathVariable(name = "name") String channelName, @PathVariable(name = "id") String messageId){
        this.messageService.delete(messageId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/messages")
    public ResponseEntity deleteMessages(@PathVariable(name = "name") String channelName){
        this.messageService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Channel> channelResponseEntity(@PathVariable(name = "name") String channelName) {
        return new ResponseEntity<>(this.channelService.findByName(channelName), HttpStatus.OK);
    }


}
