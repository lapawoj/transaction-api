package com.example.transactionapi.importservice.handler;

import com.example.transactionapi.eventstore.service.EventStore;
import com.example.transactionapi.importservice.aggregate.ImportAggregate;
import com.example.transactionapi.importservice.command.StartImportCommand;
import com.example.transactionapi.shared.event.DomainEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportCommandHandler {

    private final EventStore eventStore;

    public ImportCommandHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void handle(StartImportCommand command) {
        List<DomainEvent> history = eventStore.load(command.importId());

        ImportAggregate aggregate = ImportAggregate.rehydrate(history);

        List<DomainEvent> newEvents = aggregate.startImport(command.importId());

        eventStore.append(newEvents);
    }
}

