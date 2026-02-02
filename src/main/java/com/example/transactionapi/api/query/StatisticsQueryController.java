package com.example.transactionapi.api.query;

import com.example.transactionapi.projection.model.*;
import com.example.transactionapi.projection.repository.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatisticsQueryController {

    private final StatisticsByCategoryRepository categoryRepository;
    private final StatisticsByIBANRepository ibanRepository;
    private final StatisticsByMonthRepository monthRepository;

    public StatisticsQueryController(
        StatisticsByCategoryRepository categoryRepository,
        StatisticsByIBANRepository ibanRepository,
        StatisticsByMonthRepository monthRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.ibanRepository = ibanRepository;
        this.monthRepository = monthRepository;
    }

    @GetMapping("/category")
    public List<StatisticsByCategoryDocument> byCategory() {
        return categoryRepository.findAll();
    }

    @GetMapping("/iban")
    public List<StatisticsByIBANDocument> byIBAN() {
        return ibanRepository.findAll();
    }

    @GetMapping("/month")
    public List<StatisticsByMonthDocument> byMonth() {
        return monthRepository.findAll();
    }
}

