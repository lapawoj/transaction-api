package com.example.transactionapi.shared.event;

import java.time.Instant;

public record ImportCompletedEvent(
    String aggregateId,
    int totalImported,
    int totalRejected,
    Instant occurredAt
) implements DomainEvent {

    @Override
    public String type() {
        return "ImportCompleted";
    }
}
