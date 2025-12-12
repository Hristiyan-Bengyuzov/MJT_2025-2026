package bg.sofia.uni.fmi.mjt.order.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentMethodTest {
    @ParameterizedTest
    @CsvSource({
            "CREDIT_CARD, CREDIT_CARD",
            "DEBIT_CARD, DEBIT_CARD",
            "PAYPAL, PAYPAL",
            "AMAZON_PAY, AMAZON_PAY",
            "GIFT_CARD, GIFT_CARD"
    })
    void testOfShouldReturnCorrectPaymentMethodExact(String input, PaymentMethod expected) {
        assertEquals(expected, PaymentMethod.of(input), "Input should return the expected payment method.");
    }

    @ParameterizedTest
    @CsvSource({
            "credit_card, CREDIT_CARD",
            "debit_card, DEBIT_CARD",
            "paypal, PAYPAL",
            "amazon_pay, AMAZON_PAY",
            "gift_card, GIFT_CARD"
    })
    void testOfShouldReturnCorrectPaymentMethodCaseInsensitive(String input, PaymentMethod expected) {
        assertEquals(expected, PaymentMethod.of(input), "Input should be case insensitive.");
    }

    @Test
    void testOfShouldIgnoreLeadingAndTrailingWhitespaces() {
        assertEquals(PaymentMethod.PAYPAL, PaymentMethod.of("    paypal   "), "Leading and trailing whitespaces should be ignored.");
    }

    @ParameterizedTest
    @CsvSource({
            "Credit card, CREDIT_CARD",
            "Debit card, DEBIT_CARD",
            "Amazon pay, AMAZON_PAY",
            "Gift card, GIFT_CARD"
    })
    void testOfShouldReplaceWhitespacesWithUnderscore(String input, PaymentMethod expected) {
        assertEquals(expected, PaymentMethod.of(input), "Whitespaces should be replaced with underscore.");
    }
}