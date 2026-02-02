package com.example.transactionapi.eventstore.service;

import com.example.transactionapi.eventstore.model.EventDocument;
import com.example.transactionapi.eventstore.repository.EventStoreRepository;
import com.example.transactionapi.shared.event.DomainEvent;
import com.example.transactionapi.shared.event.EventFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import tools.jackson.databind.ObjectMapper;

@Service
public class MongoEventStore implements EventStore {

    private final EventStoreRepository repository;
    private final ObjectMapper objectMapper;

    public MongoEventStore(EventStoreRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void append(List<DomainEvent> events) {
        events.forEach(event -> {
            try {
                String payload = objectMapper.writeValueAsString(event);

                repository.save(new EventDocument(
                    event.aggregateId(),
                    event.type(),
                    payload,
                    event.occurredAt()
                ));
            } catch (Exception e) {
                throw new RuntimeException("Failed to persist event", e);
            }
        });
    }

    @Override
    public List<DomainEvent> load(String aggregateId) {
        return repository.findByAggregateIdOrderByOccurredAtAsc(aggregateId)
            .stream()
            .map(doc -> {
                try {
                    Class<? extends DomainEvent> clazz = EventFactory.getEventClass(doc.getType());
                    return objectMapper.readValue(doc.getPayload(), clazz);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to deserialize event", e);
                }
            })
            .collect(Collectors.toList());
    }


}