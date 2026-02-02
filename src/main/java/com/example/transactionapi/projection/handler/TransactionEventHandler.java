package com.example.transactionapi.projection.handler;

import com.example.transactionapi.projection.model.*;
import com.example.transactionapi.projection.repository.*;
import com.example.transactionapi.shared.event.TransactionImportedEvent;
import com.example.transactionapi.shared.event.TransactionRejectedEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionEventHandler {

    private final StatisticsByCategoryRepository categoryRepository;
    private final StatisticsByIBANRepository ibanRepository;
    private final StatisticsByMonthRepository monthRepository;

    public TransactionEventHandler(
        StatisticsByCategoryRepository categoryRepository,
        StatisticsByIBANRepository ibanRepository,
        StatisticsByMonthRepository monthRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.ibanRepository = ibanRepository;
        this.monthRepository = monthRepository;
    }

    public void handle(final TransactionImportedEvent event) {
        String month = event.transactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String catId = event.category() + "_" + month;
        StatisticsByCategoryDocument catDoc = categoryRepository.findById(catId)
            .orElseGet(() -> new StatisticsByCategoryDocument(
                catId,
                event.category(),
                month,
                BigDecimal.ZERO
            ));
        catDoc.setTotalAmount(catDoc.getTotalAmount().add(event.amount()));
        categoryRepository.save(catDoc);

        String ibanId = event.iban() + "_" + month;
        StatisticsByIBANDocument ibanDoc = ibanRepository.findById(ibanId)
            .orElseGet(() -> new StatisticsByIBANDocument(
                ibanId,
                event.iban(),
                month,
                BigDecimal.ZERO
            ));
        ibanDoc.setTotalAmount(ibanDoc.getTotalAmount().add(event.amount()));
        ibanRepository.save(ibanDoc);

        String monthId = month;
        StatisticsByMonthDocument monthDoc = monthRepository.findById(monthId)
            .orElseGet(() -> new StatisticsByMonthDocument(
                monthId,
                month,
                BigDecimal.ZERO
            ));
        monthDoc.setTotalAmount(monthDoc.getTotalAmount().add(event.amount()));
        monthRepository.save(monthDoc);
    }

    public void handle(TransactionRejectedEvent event) {
        System.out.println("Rejected row: " + event.rowData() + " Reason: " + event.reason());
    }
}
