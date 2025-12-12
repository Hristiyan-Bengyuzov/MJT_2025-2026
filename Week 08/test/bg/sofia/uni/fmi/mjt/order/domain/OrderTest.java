package bg.sofia.uni.fmi.mjt.order.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OrderTest {
    @Test
    void testOfShouldParseOrderCorrectly() {
        String csvLine = "ORD001,25-12-25,PC,Electronics,2999.99,1,2999.99,Haralampy Slavkov,Sofia,Credit card,completed";

        Order order = Order.of(csvLine);

        assertEquals("ORD001", order.id(), "ID should be parsed correctly.");
        assertEquals(LocalDate.of(2025, 12, 25), order.date(), "Date should be parsed using dd-MM-yy format.");
        assertEquals("PC", order.product(), "Product should be parsed correctly.");
        assertEquals(Category.ELECTRONICS, order.category(), "Category should be parsed correctly.");
        assertEquals(2999.99, order.price(), "Price should be parsed correctly.");
        assertEquals(1, order.quantity(), "Quantity should be parsed correctly.");
        assertEquals(2999.99, order.totalSales(), "Total sales should be parsed correctly.");
        assertEquals("Haralampy Slavkov", order.customerName(), "Customer name should be parsed correctly.");
        assertEquals("Sofia", order.customerLocation(), "Customer location should be parsed correctly.");
        assertEquals(PaymentMethod.CREDIT_CARD, order.paymentMethod(), "Payment method should be parsed correctly.");
        assertEquals(Status.COMPLETED, order.status(), "Status should be parsed correctly.");
    }
}