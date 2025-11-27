package bg.sofia.uni.fmi.mjt.eventbus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

public class EventBusImpl implements EventBus {
    private final Map<Class<? extends Event<?>>, List<Subscriber<?>>> subscribers;
    private final Map<Class<? extends Event<?>>, List<Event<?>>> eventLogs;

    public EventBusImpl() {
        this.subscribers = new HashMap<>();
        this.eventLogs = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }

        List<Subscriber<?>> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers == null) {
            eventSubscribers = new ArrayList<>();
            subscribers.put(eventType, eventSubscribers);
        }

        if (!eventSubscribers.contains(subscriber)) {
            eventSubscribers.add(subscriber);
        }
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
            throws MissingSubscriptionException {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }

        List<Subscriber<?>> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers == null || !eventSubscribers.remove(subscriber)) {
            throw new MissingSubscriptionException("Subscriber is not subscribed to the event type: "
                + eventType.getName());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        Class<? extends Event<?>> eventType = (Class<? extends Event<?>>) event.getClass();
        List<Event<?>> eventsForType = eventLogs.get(eventType);
        if (eventsForType == null) {
            eventsForType = new ArrayList<>();
            eventLogs.put(eventType, eventsForType);
        }
        eventsForType.add(event);

        List<Subscriber<?>> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers != null) {
            for (Subscriber<?> subscriber : eventSubscribers) {
                @SuppressWarnings("unchecked")
                Subscriber<? super T> typedSubscriber = (Subscriber<? super T>) subscriber;
                typedSubscriber.onEvent(event);
            }
        }
    }

    @Override
    public void clear() {
        subscribers.clear();
        eventLogs.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (from == null) {
            throw new IllegalArgumentException("Start timestamp cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("End timestamp cannot be null");
        }

        List<Event<?>> eventsInRange = new ArrayList<>();
        List<Event<?>> events = eventLogs.get(eventType);

        if (events != null) {
            for (Event<?> event : events) {
                Instant timestamp = event.getTimestamp();
                if (!timestamp.isBefore(from) && timestamp.isBefore(to)) {
                    eventsInRange.add(event);
                }
            }
        }

        return Collections.unmodifiableCollection(eventsInRange);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }

        List<Subscriber<?>> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers == null) {
            return Collections.unmodifiableCollection(new ArrayList<>());
        }

        return Collections.unmodifiableCollection(new ArrayList<>(eventSubscribers));
    }
}