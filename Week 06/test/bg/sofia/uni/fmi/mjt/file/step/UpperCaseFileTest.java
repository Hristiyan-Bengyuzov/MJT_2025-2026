package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpperCaseFileTest {
    UpperCaseFile upperCaseFile = new UpperCaseFile();

    @Test
    void testProcessShouldConvertContentToUpperCase() {
        File file = new File("alabala");
        File res = upperCaseFile.process(file);

        assertEquals("ALABALA", res.getContent(), "alabala should become ALABALA");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileIsNull() {
        assertThrows(IllegalArgumentException.class, () -> upperCaseFile.process(null),
                "IllegalArgumentException should be thrown when file is null");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileContentIsNull() {
        File file = new File("alabala");
        file.setContent(null);

        assertThrows(IllegalArgumentException.class, () -> upperCaseFile.process(file),
                "IllegalArgumentException should be thrown when file content is null");
    }
}