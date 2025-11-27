package bg.sofia.uni.fmi.mjt.file;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileTest {
    @Test
    void testConstructorThrowsExceptionWhenContentIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new File(null),
                "IllegalArgumentException should be thrown when file content is null.");
    }
}