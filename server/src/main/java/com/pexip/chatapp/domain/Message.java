package com.pexip.chatapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    private String id;

    @DBRef()
    private Profile profile;

    @DBRef()
    private Channel channel;

    private String message;

    private Boolean status;

    private Instant createdOn;

}
