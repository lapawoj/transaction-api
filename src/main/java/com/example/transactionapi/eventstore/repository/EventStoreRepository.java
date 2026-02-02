package com.example.transactionapi.eventstore.repository;

import com.example.transactionapi.eventstore.model.EventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventStoreRepository extends MongoRepository<EventDocument, String> {

    List<EventDocument> findByAggregateIdOrderByOccurredAtAsc(String aggregateId);
}
