package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {
    private final List<T> events;

    public DeferredEventSubscriber() {
        this.events = new ArrayList<>();
    }

    @Override
    public void onEvent(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        events.add(event);
    }

    @Override
    public Iterator<T> iterator() {
        List<T> sortedEvents = new ArrayList<>(events);

        sortedEvents.sort(new Comparator<>() {
            @Override
            public int compare(T e1, T e2) {
                int priorityCompare = Integer.compare(e1.getPriority(), e2.getPriority());
                if (priorityCompare != 0) {
                    return priorityCompare;
                }

                return e1.getTimestamp().compareTo(e2.getTimestamp());
            }
        });

        return sortedEvents.iterator();
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }
}