package bg.sofia.uni.fmi.mjt.order.loader;

import bg.sofia.uni.fmi.mjt.order.domain.Order;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

public class OrderLoader {
    private static final int HEADER_TO_SKIP = 1;

    /**
     * Returns a list of orders read from the source Reader.
     *
     * @param reader the Reader with orders
     * @throws IllegalArgumentException if the reader is null
     */
    public static List<Order> load(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null.");
        }

        var bufferedReader = new BufferedReader(reader);
        return bufferedReader.lines().skip(HEADER_TO_SKIP).map(Order::of).toList();
    }
}