package com.example.transactionapi.projection.handler;

import com.example.transactionapi.projection.model.ImportStatusDocument;
import com.example.transactionapi.projection.repository.ImportStatusRepository;
import com.example.transactionapi.shared.event.ImportCompletedEvent;
import org.springframework.stereotype.Service;

@Service
public class ImportCompletedEventHandler {

    private final ImportStatusRepository repository;

    public ImportCompletedEventHandler(ImportStatusRepository repository) {
        this.repository = repository;
    }

    public void handle(ImportCompletedEvent event) {
        ImportStatusDocument doc = repository.findById(event.aggregateId())
            .orElse(new ImportStatusDocument(event.aggregateId(), 0, 0, false));

        doc.setTotalImported(event.totalImported());
        doc.setTotalRejected(event.totalRejected());
        doc.setCompleted(true);

        repository.save(doc);
    }
}

