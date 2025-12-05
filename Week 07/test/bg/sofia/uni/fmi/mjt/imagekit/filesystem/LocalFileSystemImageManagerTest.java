package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class LocalFileSystemImageManagerTest {

    private final LocalFileSystemImageManager manager = new LocalFileSystemImageManager();

    @TempDir
    Path tempDir;

    @Test
    void testLoadImageShouldThrowForNullFile() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImage(null),
                "Loading null file should throw IllegalArgumentException.");
    }

    @Test
    void testLoadImageShouldThrowForNonExistingFile() {
        File nonExistent = tempDir.resolve("nonexistent.png").toFile();
        assertThrows(IOException.class, () -> manager.loadImage(nonExistent));
    }

    @Test
    void testLoadImageShouldThrowForUnsupportedFormat() throws IOException {
        File file = tempDir.resolve("file.txt").toFile();
        assertTrue(file.createNewFile());
        assertThrows(IOException.class, () -> manager.loadImage(file));
    }

    @Test
    void testSaveImageShouldThrowForNullImage() {
        File file = tempDir.resolve("image.png").toFile();
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(null, file));
    }

    @Test
    void testSaveImageShouldThrowForNullFile() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(image, null));
    }

    @Test
    void testSaveImageShouldThrowIfFileAlreadyExists() throws IOException {
        File file = tempDir.resolve("image.png").toFile();
        assertTrue(file.createNewFile());

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        assertThrows(IOException.class, () -> manager.saveImage(image, file));
    }

    @Test
    void testSaveAndLoadImageSuccessfully() throws IOException {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        File file = tempDir.resolve("test.png").toFile();

        manager.saveImage(image, file);

        BufferedImage loaded = manager.loadImage(file);

        assertEquals(image.getWidth(), loaded.getWidth(), "Width should match after saving and loading.");
        assertEquals(image.getHeight(), loaded.getHeight(), "Height should match after saving and loading.");
    }

    @Test
    void testLoadImagesFromDirectory() throws IOException {
        BufferedImage image1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        BufferedImage image2 = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

        File file1 = tempDir.resolve("img1.png").toFile();
        File file2 = tempDir.resolve("img2.jpg").toFile();

        manager.saveImage(image1, file1);
        manager.saveImage(image2, file2);

        List<BufferedImage> images = manager.loadImagesFromDirectory(tempDir.toFile());
        assertEquals(2, images.size(), "Should load 2 images from directory.");
    }

    @Test
    void testLoadImagesFromDirectoryShouldThrowForNullDirectory() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImagesFromDirectory(null));
    }

    @Test
    void testLoadImagesFromDirectoryShouldThrowForNonDirectory() throws IOException {
        File file = tempDir.resolve("file.png").toFile();
        assertTrue(file.createNewFile());
        assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(file));
    }
}
