package com.example.transactionapi.shared.event;

import java.time.Instant;

public record TransactionRejectedEvent(
    String aggregateId,
    String reason,
    String rowData,
    Instant occurredAt
) implements DomainEvent {

    @Override
    public String type() {
        return "TransactionRejected";
    }
}
