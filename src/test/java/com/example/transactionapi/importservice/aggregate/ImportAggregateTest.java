package com.example.transactionapi.importservice.aggregate;

import com.example.transactionapi.shared.event.ImportStartedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImportAggregateTest {

    @Test
    void startImportGeneratesImportStartedEvent() {
        ImportAggregate aggregate = ImportAggregate.rehydrate(List.of());

        List events = aggregate.startImport("import-123");

        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ImportStartedEvent);
        assertEquals("import-123", ((ImportStartedEvent) events.get(0)).aggregateId());
    }


    @Test
    void rehydrateRestoresState() {
        ImportStartedEvent event = new ImportStartedEvent("import-123", java.time.Instant.now());
        ImportAggregate aggregate = ImportAggregate.rehydrate(List.of(event));

        List newEvents = aggregate.startImport("import-123");
        assertEquals(1, newEvents.size());
    }
}
