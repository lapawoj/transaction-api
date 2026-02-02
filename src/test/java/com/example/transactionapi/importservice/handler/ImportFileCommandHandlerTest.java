package com.example.transactionapi.importservice.handler;

import com.example.transactionapi.eventstore.service.EventStore;
import com.example.transactionapi.importservice.command.ImportFileCommand;
import com.example.transactionapi.projection.handler.ImportCompletedEventHandler;
import com.example.transactionapi.projection.handler.TransactionImportedEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ImportFileCommandHandlerTest {

    private EventStore eventStore;
    private ImportCompletedEventHandler importCompletedEventHandler;
    private ImportFileCommandHandler handler;
    private TransactionImportedEventHandler transactionImportedEventHandler;

    @BeforeEach
    void setup() {
        eventStore = mock(EventStore.class);
        importCompletedEventHandler = mock(ImportCompletedEventHandler.class);
        transactionImportedEventHandler = mock(TransactionImportedEventHandler.class);
        handler = new ImportFileCommandHandler(eventStore, importCompletedEventHandler, transactionImportedEventHandler);
    }

    @Test
    void handleCsvGeneratesEventsAndCallsCompletedHandler() throws Exception {
        String csv = "IBAN,transactionDate,currency,category,amount\n" +
            "DE12345678901234567890,2026-01-30,EUR,GROCERIES,100";

        MockMultipartFile file = new MockMultipartFile(
            "file",
            "transactions.csv",
            "text/csv",
            csv.getBytes(StandardCharsets.UTF_8)
        );

        handler.handle(new ImportFileCommand("import-1", file));

        verify(eventStore, times(2)).append(anyList());

        verify(importCompletedEventHandler, times(1)).handle(any());
    }
}

