package com.example.transactionapi.importservice.aggregate;

import com.example.transactionapi.shared.event.DomainEvent;

import com.example.transactionapi.shared.event.ImportStartedEvent;
import java.time.Instant;
import java.util.List;

public class ImportAggregate {

    private String importId;

    private ImportAggregate() {
    }

    public static ImportAggregate rehydrate(List<DomainEvent> events) {
        ImportAggregate aggregate = new ImportAggregate();
        events.forEach(aggregate::apply);
        return aggregate;
    }

    public List<DomainEvent> startImport(String importId) {
        this.importId = importId;

        return List.of(
            new ImportStartedEvent(
                importId,
                Instant.now()
            )
        );
    }

    private void apply(DomainEvent event) {
        if (event instanceof ImportStartedEvent e) {
            this.importId = e.aggregateId();
        }
    }
}
