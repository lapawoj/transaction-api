package com.example.transactionapi.shared.event;

import java.util.HashMap;
import java.util.Map;

public class EventFactory {

    private static final Map<String, Class<? extends DomainEvent>> EVENT_TYPES = new HashMap<>();

    static {
        EVENT_TYPES.put("TransactionImported", TransactionImportedEvent.class);
        EVENT_TYPES.put("TransactionRejected", TransactionRejectedEvent.class);
        EVENT_TYPES.put("ImportStarted", ImportStartedEvent.class);
        EVENT_TYPES.put("ImportCompleted", ImportCompletedEvent.class);

    }

    public static Class<? extends DomainEvent> getEventClass(String type) {
        Class<? extends DomainEvent> clazz = EVENT_TYPES.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown event type: " + type);
        }
        return clazz;
    }
}
