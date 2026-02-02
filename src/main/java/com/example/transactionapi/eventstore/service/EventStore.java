package com.example.transactionapi.eventstore.service;

import com.example.transactionapi.shared.event.DomainEvent;

import java.util.List;

public interface EventStore {

    void append(List<DomainEvent> events);

    List<DomainEvent> load(String aggregateId);
}