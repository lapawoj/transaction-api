package com.example.transactionapi.projection.repository;

import com.example.transactionapi.projection.model.StatisticsByIBANDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsByIBANRepository extends MongoRepository<StatisticsByIBANDocument, String> {}
