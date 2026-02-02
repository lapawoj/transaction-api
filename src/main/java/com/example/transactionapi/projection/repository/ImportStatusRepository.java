package com.example.transactionapi.projection.repository;

import com.example.transactionapi.projection.model.ImportStatusDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportStatusRepository extends MongoRepository<ImportStatusDocument, String> {
}

