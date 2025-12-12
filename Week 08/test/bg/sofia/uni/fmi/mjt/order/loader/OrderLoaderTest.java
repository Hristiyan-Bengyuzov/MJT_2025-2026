package bg.sofia.uni.fmi.mjt.order.loader;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderLoaderTest {
    @Test
    void testLoadShouldThrowWhenReaderIsNull() {
        assertThrows(IllegalArgumentException.class, () -> OrderLoader.load(null),
                "IllegalArgumentException should be thrown when reader is null."
        );
    }

    @Test
    void testLoadShouldReturnOrdersSkippingHeader() {
        String csvData = """
                id,date,product,category,price,quantity,totalSales,customerName,customerLocation,paymentMethod,status
                ORD001,25-12-25,PC,Electronics,2999.99,1,2999.99,Haralampy Slavkov,Sofia,Credit card,completed
                ORD002,25-12-25,Controller,Electronics,139.99,2,279.98,Hristiyan Bengyuzov,Sofia,Debit card,completed
                """;

        Reader reader = new StringReader(csvData);

        List<Order> orders = OrderLoader.load(reader);

        assertEquals(2, orders.size(), "Should load 2 orders (header skipped).");

        Order first = orders.getFirst();
        assertEquals("ORD001", first.id(), "ID should be parsed correctly.");
        assertEquals(LocalDate.of(2025, 12, 25), first.date(), "Date should be parsed using dd-MM-yy format.");
        assertEquals("PC", first.product(), "Product should be parsed correctly.");
        assertEquals(Category.ELECTRONICS, first.category(), "Category should be parsed correctly.");
        assertEquals(2999.99, first.price(), "Price should be parsed correctly.");
        assertEquals(1, first.quantity(), "Quantity should be parsed correctly.");
        assertEquals(2999.99, first.totalSales(), "Total sales should be parsed correctly.");
        assertEquals("Haralampy Slavkov", first.customerName(), "Customer name should be parsed correctly.");
        assertEquals("Sofia", first.customerLocation(), "Customer location should be parsed correctly.");
        assertEquals(PaymentMethod.CREDIT_CARD, first.paymentMethod(), "Payment method should be parsed correctly.");
        assertEquals(Status.COMPLETED, first.status(), "Status should be parsed correctly.");

        Order second = orders.get(1);
        assertEquals("ORD002", second.id(), "ID should be parsed correctly.");
        assertEquals(LocalDate.of(2025, 12, 25), second.date(), "Date should be parsed using dd-MM-yy format.");
        assertEquals("Controller", second.product(), "Product should be parsed correctly.");
        assertEquals(Category.ELECTRONICS, second.category(), "Category should be parsed correctly.");
        assertEquals(139.99, second.price(), "Price should be parsed correctly.");
        assertEquals(2, second.quantity(), "Quantity should be parsed correctly.");
        assertEquals(279.98, second.totalSales(), "Total sales should be parsed correctly.");
        assertEquals("Hristiyan Bengyuzov", second.customerName(), "Customer name should be parsed correctly.");
        assertEquals("Sofia", second.customerLocation(), "Customer location should be parsed correctly.");
        assertEquals(PaymentMethod.DEBIT_CARD, second.paymentMethod(), "Payment method should be parsed correctly.");
        assertEquals(Status.COMPLETED, second.status(), "Status should be parsed correctly.");
    }
}