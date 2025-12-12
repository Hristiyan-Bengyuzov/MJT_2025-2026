package bg.sofia.uni.fmi.mjt.order.domain;

public enum Status {
    COMPLETED, PENDING, CANCELLED;

    public static Status of(String str) {
        return Status.valueOf(str.trim().toUpperCase());
    }
}