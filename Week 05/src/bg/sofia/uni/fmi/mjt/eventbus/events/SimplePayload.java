package bg.sofia.uni.fmi.mjt.eventbus.events;

public class SimplePayload<T> implements Payload<T> {
    private final T payload;

    public SimplePayload(T payload) {
        this.payload = payload;
    }

    @Override
    public int getSize() {
        if (payload instanceof String) {
            return ((String) payload).length();
        }
        return 1;
    }

    @Override
    public T getPayload() {
        return payload;
    }
}