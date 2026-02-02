package com.example.transactionapi.importservice.handler;

import com.example.transactionapi.eventstore.service.EventStore;
import com.example.transactionapi.importservice.command.ImportFileCommand;
import com.example.transactionapi.projection.handler.TransactionImportedEventHandler;
import com.example.transactionapi.shared.event.*;
import com.example.transactionapi.projection.handler.ImportCompletedEventHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportFileCommandHandler {

    private final EventStore eventStore;
    private final ImportCompletedEventHandler importCompletedEventHandler;
    private final TransactionImportedEventHandler transactionImportedEventHandler;


    public ImportFileCommandHandler(EventStore eventStore,
        ImportCompletedEventHandler importCompletedEventHandler,
        TransactionImportedEventHandler transactionImportedEventHandler) {
        this.eventStore = eventStore;
        this.importCompletedEventHandler = importCompletedEventHandler;
        this.transactionImportedEventHandler = transactionImportedEventHandler;
    }

    public void handle(ImportFileCommand command) {
        String importId = command.importId();
        MultipartFile file = command.file();

        List<DomainEvent> newEvents = new ArrayList<>();
        int totalImported = 0;
        int totalRejected = 0;

        try (CSVParser parser = CSVParser.parse(
            new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8),
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim())) {

            for (CSVRecord record : parser) {
                try {
                    String iban = record.get("IBAN");
                    String dateStr = record.get("transactionDate");
                    String currency = record.get("currency");
                    String category = record.get("category");
                    String amountStr = record.get("amount");

                    LocalDate date = LocalDate.parse(dateStr);
                    BigDecimal amount = new BigDecimal(amountStr);

                    // prosty check IBAN
                    if (!iban.startsWith("DE") || iban.length() != 22) {
                        throw new IllegalArgumentException("Invalid IBAN");
                    }

                                        // dodajemy TransactionImportedEvent
                    TransactionImportedEvent event= new TransactionImportedEvent(
                        importId,
                        iban,
                        date,
                        currency,
                        category,
                        amount,
                        Instant.now()
                    );
                    newEvents.add(event);

                    transactionImportedEventHandler.handle(event);
                    totalImported++;

                } catch (Exception e) {
                    newEvents.add(new TransactionRejectedEvent(
                        importId,
                        record.toString(),
                        e.getMessage(),
                        Instant.now()
                    ));
                    totalRejected++;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }

        eventStore.append(newEvents);

        ImportCompletedEvent completedEvent = new ImportCompletedEvent(
            importId,
            totalImported,
            totalRejected,
            Instant.now()
        );
        eventStore.append(List.of(completedEvent));

        importCompletedEventHandler.handle(completedEvent);
    }
}
