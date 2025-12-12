package bg.sofia.uni.fmi.mjt.order.analyzer;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderAnalyzerImpl implements OrderAnalyzer {
    private static final int TOTAL_SALES_BOUND = 100;
    private static final int ORDERS_COUNT_BOUND = 3;

    private final List<Order> orders;

    public OrderAnalyzerImpl(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public List<Order> allOrders() {
        return List.copyOf(orders);
    }

    @Override
    public List<Order> ordersByCustomer(String customer) {
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("Customer cannot be null or blank.");
        }

        return orders.stream()
                .filter(order -> order.customerName().equals(customer))
                .toList();
    }

    @Override
    public Map.Entry<LocalDate, Long> dateWithMostOrders() {
        if (orders == null || orders.isEmpty()) {
            return null;
        }

        Map<LocalDate, Long> result =
                orders.stream()
                        .collect(Collectors.groupingBy(Order::date, Collectors.counting()));

        return result.entrySet().stream()
                .max(
                        Map.Entry.<LocalDate, Long>comparingByValue()
                                .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                )
                .orElse(null);
    }

    @Override
    public List<String> topNMostOrderedProducts(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Limit cannot be less than 0.");
        }

        Map<String, Long> result =
                orders.stream()
                        .collect(Collectors.groupingBy(Order::product, Collectors.counting()));

        return result.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue().reversed()
                                .thenComparing(Map.Entry.comparingByKey()))
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Map<Category, Double> revenueByCategory() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::category, Collectors.summingDouble(Order::totalSales)));
    }

    @Override
    public Set<String> suspiciousCustomers() {
        Map<String, Long> result =
                orders.stream()
                        .filter(order -> order.status() == Status.CANCELLED && order.totalSales() < TOTAL_SALES_BOUND)
                        .collect(Collectors.groupingBy(Order::customerName, Collectors.counting()));

        return result.entrySet().stream()
                .filter(entry -> entry.getValue() > ORDERS_COUNT_BOUND)
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Map<Category, PaymentMethod> mostUsedPaymentMethodForCategory() {
        if (orders == null || orders.isEmpty()) {
            return Map.of();
        }

        Map<Category, Map<PaymentMethod, Long>> result =
                orders.stream()
                        .collect(Collectors.groupingBy(
                                Order::category,
                                Collectors.groupingBy(
                                        Order::paymentMethod,
                                        Collectors.counting()
                                )
                        ));

        return result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().entrySet().stream()
                                .max(
                                        Map.Entry.<PaymentMethod, Long>comparingByValue()
                                                .thenComparing(e -> e.getKey().name(), Comparator.reverseOrder()))
                                .map(Map.Entry::getKey)
                                .orElseThrow() // it will never get here
                ));
    }

    @Override
    public String locationWithMostOrders() {
        if (orders == null || orders.isEmpty()) {
            return null;
        }

        Map<String, Long> result =
                orders.stream()
                        .collect(Collectors.groupingBy(Order::customerLocation, Collectors.counting()));

        return result.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue().reversed()
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map<Category, Map<Status, Long>> groupByCategoryAndStatus() {
        if (orders == null || orders.isEmpty()) {
            return Map.of();
        }

        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.groupingBy(
                                Order::status,
                                Collectors.counting()
                        )
                ));
    }
}
