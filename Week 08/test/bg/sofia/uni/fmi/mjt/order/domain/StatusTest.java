package bg.sofia.uni.fmi.mjt.order.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StatusTest {
    @ParameterizedTest
    @CsvSource({
            "COMPLETED, COMPLETED",
            "PENDING, PENDING",
            "CANCELLED, CANCELLED",
    })
    void testOfShouldReturnCorrectStatusExact(String input, Status expected) {
        assertEquals(expected, Status.of(input), "Input should return the expected status.");
    }

    @ParameterizedTest
    @CsvSource({
            "completed, COMPLETED",
            "pending, PENDING",
            "cancelled, CANCELLED",
    })
    void testOfShouldReturnCorrectPaymentMethodCaseInsensitive(String input, Status expected) {
        assertEquals(expected, Status.of(input), "Input should be case insensitive.");
    }

    @Test
    void testOfShouldIgnoreLeadingAndTrailingWhitespaces() {
        assertEquals(Status.COMPLETED, Status.of("    completed   "), "Leading and trailing whitespaces should be ignored.");
    }
}