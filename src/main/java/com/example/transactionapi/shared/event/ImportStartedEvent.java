package com.example.transactionapi.shared.event;

import java.time.Instant;

public record ImportStartedEvent(
    String aggregateId,
    Instant occurredAt
) implements DomainEvent {

    @Override
    public String type() {
        return "ImportStarted";
    }
}