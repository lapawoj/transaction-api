package com.example.transactionapi.projection.handler;

import com.example.transactionapi.projection.model.*;
import com.example.transactionapi.projection.repository.*;
import com.example.transactionapi.shared.event.TransactionImportedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TransactionImportedEventHandlerTest {

    private StatisticsByCategoryRepository categoryRepo;
    private StatisticsByIBANRepository ibanRepo;
    private StatisticsByMonthRepository monthRepo;
    private TransactionImportedEventHandler handler;

    @BeforeEach
    void setup() {
        categoryRepo = mock(StatisticsByCategoryRepository.class);
        ibanRepo = mock(StatisticsByIBANRepository.class);
        monthRepo = mock(StatisticsByMonthRepository.class);
        handler = new TransactionImportedEventHandler(categoryRepo, ibanRepo, monthRepo);
    }

    @Test
    void handleCreatesOrUpdatesProjections() {
        TransactionImportedEvent event = new TransactionImportedEvent(
            "import-1",
            "DE12345678901234567890",
            LocalDate.of(2026,1,30),
            "EUR",
            "GROCERIES",
            BigDecimal.valueOf(100),
            Instant.now()
        );

        when(categoryRepo.findById(any())).thenReturn(Optional.empty());
        when(ibanRepo.findById(any())).thenReturn(Optional.empty());
        when(monthRepo.findById(any())).thenReturn(Optional.empty());

        handler.handle(event);

        verify(categoryRepo).save(any(StatisticsByCategoryDocument.class));
        verify(ibanRepo).save(any(StatisticsByIBANDocument.class));
        verify(monthRepo).save(any(StatisticsByMonthDocument.class));
    }
}
