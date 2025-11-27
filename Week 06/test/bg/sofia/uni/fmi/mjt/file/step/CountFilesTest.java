package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountFilesTest {
    CountFiles countFiles = new CountFiles();

    @Test
    void testProcessShouldReturnCorrectNumberOfFilesWhenMultipleFiles() {
        Collection<File> files = List.of(new File("ala"), new File("bala"));

        assertEquals(2, countFiles.process(files), "Process should return correct number of files.");
    }

    @Test
    void testProcessShouldReturnCorrectNumberOfFilesWhenEmptyFiles() {
        Collection<File> files = List.of();

        assertEquals(0, countFiles.process(files), "Process should return correct number of files.");
    }

    @Test
    void testProcessShouldThrowExceptionWhenInputCollectionIsNull() {
        assertThrows(IllegalArgumentException.class, () -> countFiles.process(null),
                "IllegalArgumentException should be thrown when input collection is null.");
    }
}