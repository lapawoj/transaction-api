package com.example.transactionapi.projection.repository;

import com.example.transactionapi.projection.model.StatisticsByMonthDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsByMonthRepository extends MongoRepository<StatisticsByMonthDocument, String> {}
