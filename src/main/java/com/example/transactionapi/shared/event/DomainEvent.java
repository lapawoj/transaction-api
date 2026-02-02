package com.example.transactionapi.shared.event;

import java.time.Instant;

public interface DomainEvent {

    String aggregateId();

    Instant occurredAt();

    String type();
}
