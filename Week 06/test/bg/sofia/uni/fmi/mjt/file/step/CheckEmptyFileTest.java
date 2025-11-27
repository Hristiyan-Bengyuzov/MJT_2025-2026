package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckEmptyFileTest {
    CheckEmptyFile checkEmptyFile = new CheckEmptyFile();

    @Test
    void testProcessShouldReturnSameFileWhenNotEmpty() {
        File file = new File("alabala");

        assertSame(file, checkEmptyFile.process(file), "Process should return same file");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileIsNull() {
        assertThrows(EmptyFileException.class, () -> checkEmptyFile.process(null),
                "EmptyFileException should be thrown when file is null");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileContentIsEmpty() {
        assertThrows(EmptyFileException.class, () -> checkEmptyFile.process(new File("")),
                "EmptyFileException should be thrown when file content is empty");
    }

    @Test
    void testProcessShouldThrowExceptionWhenFileContentIsNull() {
        File file = new File("alabala");
        file.setContent(null);

        assertThrows(EmptyFileException.class, () -> checkEmptyFile.process(file),
                "EmptyFileException should be thrown when file content is null");
    }
}