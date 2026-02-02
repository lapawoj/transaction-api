package com.example.transactionapi.eventstore.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "events")
public class EventDocument {

    @Id
    private String id;
    private String aggregateId;
    @Getter
    private String type;
    @Getter
    private String payload;
    private Instant occurredAt;

    protected EventDocument() {
    }

    public EventDocument(String aggregateId, String type, String payload, Instant occurredAt) {
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
        this.occurredAt = occurredAt;
    }

}