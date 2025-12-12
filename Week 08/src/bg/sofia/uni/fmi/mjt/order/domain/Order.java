package bg.sofia.uni.fmi.mjt.order.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record Order(
        String id,
        LocalDate date,
        String product,
        Category category,
        double price,
        int quantity,
        double totalSales,
        String customerName,
        String customerLocation,
        PaymentMethod paymentMethod,
        Status status
) {
    private static final String ORDER_ATTRIBUTE_DELIMITER = ",";
    private static final String DATE_FORMAT = "dd-MM-yy";
    private static final int ID_TOKEN_INDEX = 0;
    private static final int DATE_TOKEN_INDEX = 1;
    private static final int PRODUCT_TOKEN_INDEX = 2;
    private static final int CATEGORY_TOKEN_INDEX = 3;
    private static final int PRICE_TOKEN_INDEX = 4;
    private static final int QUANTITY_TOKEN_INDEX = 5;
    private static final int TOTAL_SALES_TOKEN_INDEX = 6;
    private static final int CUSTOMER_NAME_TOKEN_INDEX = 7;
    private static final int CUSTOMER_LOCATION_TOKEN_INDEX = 8;
    private static final int PAYMENT_METHOD_TOKEN_INDEX = 9;
    private static final int STATUS_TOKEN_INDEX = 10;

    public static Order of(String line) {
        final String[] tokens = line.split(ORDER_ATTRIBUTE_DELIMITER);
        return new Order(
                tokens[ID_TOKEN_INDEX],
                LocalDate.parse(tokens[DATE_TOKEN_INDEX], DateTimeFormatter.ofPattern(DATE_FORMAT)),
                tokens[PRODUCT_TOKEN_INDEX],
                Category.of(tokens[CATEGORY_TOKEN_INDEX]),
                Double.parseDouble(tokens[PRICE_TOKEN_INDEX]),
                Integer.parseInt(tokens[QUANTITY_TOKEN_INDEX]),
                Double.parseDouble(tokens[TOTAL_SALES_TOKEN_INDEX]),
                tokens[CUSTOMER_NAME_TOKEN_INDEX],
                tokens[CUSTOMER_LOCATION_TOKEN_INDEX],
                PaymentMethod.of(tokens[PAYMENT_METHOD_TOKEN_INDEX]),
                Status.of(tokens[STATUS_TOKEN_INDEX])
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
