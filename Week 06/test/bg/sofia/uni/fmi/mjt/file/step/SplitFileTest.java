package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SplitFileTest {
    SplitFile splitFile = new SplitFile();

    @Test
    void testProcessShouldReturnSplitFileWhenMultipleSpaces() {
        File file = new File("ala    bala");
        Set<File> actual = splitFile.process(file);
        Set<File> expected = Set.of(new File("ala"), new File("bala"));

        assertEquals(expected, actual, "Process should split correctly with multiple spaces");
    }

    @Test
    void testProcessShouldIgnoreDuplicates() {
        File file = new File("ala ala");
        Set<File> actual = splitFile.process(file);
        Set<File> expected = Set.of(new File("ala"));

        assertEquals(actual, expected, "Process should ignore duplicates");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileIsNull() {
        assertThrows(IllegalArgumentException.class, () -> splitFile.process(null),
                "IllegalArgumentException should be thrown when file is null");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileContentIsNull() {
        File file = new File("alabala");
        file.setContent(null);

        assertThrows(IllegalArgumentException.class, () -> splitFile.process(file),
                "IllegalArgumentException should be thrown when file content is null");
    }
}