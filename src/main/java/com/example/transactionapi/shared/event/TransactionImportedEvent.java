package com.example.transactionapi.shared.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record TransactionImportedEvent(
    String aggregateId,
    String iban,
    LocalDate transactionDate,
    String currency,
    String category,
    BigDecimal amount,
    Instant occurredAt
) implements DomainEvent {

    @Override
    public String type() {
        return "TransactionImported";
    }
}

