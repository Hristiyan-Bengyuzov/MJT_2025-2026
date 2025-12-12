package bg.sofia.uni.fmi.mjt.order.analyzer;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderAnalyzerImplTest {

    List<Order> orders = List.of(
            new Order("ORD001", LocalDate.of(2025, 12, 25), "PC", Category.ELECTRONICS, 2999.99, 1, 2999.99,
                    "Haralampy Slavkov", "Sofia", PaymentMethod.CREDIT_CARD, Status.COMPLETED),
            new Order("ORD002", LocalDate.of(2025, 12, 25), "Shoes", Category.FOOTWEAR, 120.0, 2, 240.0,
                    "Hristiyan Bengyuzov", "Sofia", PaymentMethod.DEBIT_CARD, Status.COMPLETED),
            new Order("ORD003", LocalDate.of(2025, 12, 26), "Laptop", Category.ELECTRONICS, 1500.0, 1, 1500.0,
                    "Haralampy Slavkov", "Plovdiv", PaymentMethod.CREDIT_CARD, Status.CANCELLED),
            new Order("ORD004", LocalDate.of(2025, 12, 27), "Book A", Category.BOOKS, 30.0, 2, 60.0,
                    "Ivan Ivanov", "Sofia", PaymentMethod.PAYPAL, Status.CANCELLED),
            new Order("ORD005", LocalDate.of(2025, 12, 27), "Book B", Category.BOOKS, 25.0, 2, 50.0,
                    "Ivan Ivanov", "Sofia", PaymentMethod.PAYPAL, Status.CANCELLED),
            new Order("ORD006", LocalDate.of(2025, 12, 27), "Book C", Category.BOOKS, 40.0, 1, 40.0,
                    "Ivan Ivanov", "Sofia", PaymentMethod.PAYPAL, Status.CANCELLED),
            new Order("ORD007", LocalDate.of(2025, 12, 27), "Book D", Category.BOOKS, 20.0, 3, 60.0,
                    "Ivan Ivanov", "Sofia", PaymentMethod.PAYPAL, Status.CANCELLED)
    );
    OrderAnalyzer analyzer = new OrderAnalyzerImpl(orders);

    OrderAnalyzer emptyAnalyzer = new OrderAnalyzerImpl(List.of());

    @Test
    void testAllOrdersShouldReturnAllOrders() {
        List<Order> allOrders = analyzer.allOrders();
        assertEquals(orders.size(), allOrders.size(), "Should return all orders.");
        assertTrue(allOrders.containsAll(orders), "Returned list should contain all orders.");
    }

    @Test
    void testOrdersByCustomerShouldReturnOnlyCustomerOrders() {
        List<Order> customerOrders = analyzer.ordersByCustomer("Haralampy Slavkov");
        assertEquals(2, customerOrders.size(), "Should return 2 orders for the customer.");
        assertTrue(customerOrders.stream().allMatch(o -> o.customerName().equals("Haralampy Slavkov")));
    }

    @Test
    void testOrdersByCustomerShouldThrowWhenCustomerIsNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.ordersByCustomer(null));
        assertThrows(IllegalArgumentException.class, () -> analyzer.ordersByCustomer(""));
        assertThrows(IllegalArgumentException.class, () -> analyzer.ordersByCustomer("   "));
    }

    @Test
    void testDateWithMostOrdersShouldReturnCorrectDate() {
        Map.Entry<LocalDate, Long> result = analyzer.dateWithMostOrders();
        assertNotNull(result, "Result should not be null.");
        assertEquals(LocalDate.of(2025, 12, 27), result.getKey(), "Date with most orders should be 27-12-2025.");
        assertEquals(4L, result.getValue(), "There should be 4 orders on that date.");
    }

    @Test
    void testTopNMostOrderedProductsShouldReturnCorrectProducts() {
        List<String> topProducts = analyzer.topNMostOrderedProducts(2);
        assertEquals(2, topProducts.size(), "Top products size should match the limit.");
    }

    @Test
    void testTopNMostOrderedProductsShouldThrowWhenNegativeN() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.topNMostOrderedProducts(-1));
    }

    @Test
    void testRevenueByCategoryShouldReturnCorrectRevenue() {
        Map<Category, Double> revenue = analyzer.revenueByCategory();
        assertEquals(3, revenue.size(), "There should be 3 categories in the data.");
        assertEquals(2999.99 + 1500.0, revenue.get(Category.ELECTRONICS), 0.01);
        assertEquals(240.0, revenue.get(Category.FOOTWEAR), 0.01);
        assertEquals(60.0 + 50.0 + 40.0 + 60.0, revenue.get(Category.BOOKS), 0.01);
    }

    @Test
    void testSuspiciousCustomersShouldReturnCorrectCustomers() {
        Set<String> suspicious = analyzer.suspiciousCustomers();
        assertEquals(Set.of("Ivan Ivanov"), suspicious, "Ivan Ivanov should be suspicious.");
    }

    @Test
    void testMostUsedPaymentMethodForCategoryShouldReturnCorrectMapping() {
        Map<Category, PaymentMethod> result = analyzer.mostUsedPaymentMethodForCategory();
        assertEquals(PaymentMethod.CREDIT_CARD, result.get(Category.ELECTRONICS));
        assertEquals(PaymentMethod.DEBIT_CARD, result.get(Category.FOOTWEAR));
        assertEquals(PaymentMethod.PAYPAL, result.get(Category.BOOKS));
    }

    @Test
    void testLocationWithMostOrdersShouldReturnCorrectLocation() {
        String location = analyzer.locationWithMostOrders();
        assertEquals("Sofia", location, "Sofia should have the most orders.");
    }

    @Test
    void testGroupByCategoryAndStatusShouldReturnCorrectGrouping() {
        Map<Category, Map<Status, Long>> grouped = analyzer.groupByCategoryAndStatus();

        assertEquals(3, grouped.size(), "There should be 3 categories.");

        assertEquals(1, grouped.get(Category.ELECTRONICS).get(Status.COMPLETED));
        assertEquals(1, grouped.get(Category.ELECTRONICS).get(Status.CANCELLED));

        assertEquals(1, grouped.get(Category.FOOTWEAR).get(Status.COMPLETED));

        assertEquals(4, grouped.get(Category.BOOKS).get(Status.CANCELLED));
    }

    @Test
    void testDateWithMostOrdersShouldReturnNullWhenOrdersAreEmpty() {
        assertNull(emptyAnalyzer.dateWithMostOrders(), "Date with most orders should be null when orders are empty");
    }

    @Test
    void testTopNMostOrderedProductsShouldThrowWhenLimitIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.topNMostOrderedProducts(-1),
                "IllegalArgumentException should be thrown when limit is negative");
    }

    @Test
    void testGroupByCategoryAndStatusShouldReturnEmptyMapWhenOrdersAreEmpty() {
        assertEquals(Map.of(), emptyAnalyzer.groupByCategoryAndStatus(), "Empty map should be returned when orders are empty");
    }

    @Test
    void testLocationWithMostOrdersShouldReturnNullWhenOrdersAreEmpty() {
        assertNull(emptyAnalyzer.locationWithMostOrders(), "Location should be null when orders are empty");
    }
}
