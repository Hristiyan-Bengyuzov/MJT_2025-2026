package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrintFilesTest {
    PrintFiles printFiles = new PrintFiles();

    @Test
    void testProcessShouldReturnTheSameCollectionWhenData() {
        Collection<File> files = List.of(new File("ala"), new File("bala"));
        Collection<File> result = printFiles.process(files);

        assertEquals(files, result, "Process should return the same input collection.");
    }

    @Test
    void testProcessShouldReturnTheSameCollectionWhenEmpty() {
        Collection<File> files = List.of();
        Collection<File> result = printFiles.process(files);

        assertEquals(files, result, "Process should return the same input collection.");
    }

    @Test
    void testProcessShouldThrowExceptionWhenInputCollectionIsNull() {
        assertThrows(IllegalArgumentException.class, () -> printFiles.process(null),
                "IllegalArgumentException should be thrown when input collection is null.");
    }
}