package com.example.transactionapi.projection.repository;

import com.example.transactionapi.projection.model.StatisticsByCategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsByCategoryRepository extends MongoRepository<StatisticsByCategoryDocument, String> {}


