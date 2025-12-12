package bg.sofia.uni.fmi.mjt.order.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {
    @ParameterizedTest
    @CsvSource({
            "ELECTRONICS, ELECTRONICS",
            "CLOTHING, CLOTHING",
            "FOOTWEAR, FOOTWEAR",
            "HOME_APPLIANCES, HOME_APPLIANCES",
            "BOOKS, BOOKS"
    })
    void testOfShouldReturnCorrectCategoryExact(String input, Category expected) {
        assertEquals(expected, Category.of(input), "Input should return the expected category.");
    }

    @ParameterizedTest
    @CsvSource({
            "electronics, ELECTRONICS",
            "clothing, CLOTHING",
            "footwear, FOOTWEAR",
            "home_appliances, HOME_APPLIANCES",
            "books, BOOKS"
    })
    void testOfShouldReturnCorrectCategoryCaseInsensitive(String input, Category expected) {
        assertEquals(expected, Category.of(input), "Input should be case insensitive.");
    }

    @Test
    void testOfShouldIgnoreLeadingAndTrailingWhitespaces() {
        assertEquals(Category.BOOKS, Category.of("    books   "), "Leading and trailing whitespaces should be ignored.");
    }

    @Test
    void testOfShouldReplaceWhitespacesWithUnderscore() {
        assertEquals(Category.HOME_APPLIANCES, Category.of("Home appliances"), "Whitespaces should be replaced with underscore.");
    }
}
