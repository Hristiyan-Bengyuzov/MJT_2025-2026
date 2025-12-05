package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.Color;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SobelEdgeDetectionTest {

    private final SobelEdgeDetection sobel = new SobelEdgeDetection(new LuminosityGrayscale());

    @Test
    void testProcessShouldThrowForNullImage() {
        assertThrows(IllegalArgumentException.class, () -> sobel.process(null),
                "Processing null image should throw IllegalArgumentException.");
    }

    @Test
    void testProcessShouldReturnImageOfSameDimensions() {
        BufferedImage input = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        BufferedImage output = sobel.process(input);

        assertEquals(input.getWidth(), output.getWidth(), "Output width should match input width.");
        assertEquals(input.getHeight(), output.getHeight(), "Output height should match input height.");
    }

    @Test
    void testProcessSimpleEdge() {
        BufferedImage input = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (x < 2) {
                    input.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    input.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }
            }
        }

        BufferedImage output = sobel.process(input);

        int edgeGray = new Color(output.getRGB(2, 2)).getRed();
        assertTrue(edgeGray > 0, "Edge pixel should have a non-zero value after edge detection.");

        int cornerGray = new Color(output.getRGB(0, 0)).getRed();
        assertEquals(0, cornerGray, "Corner pixel should remain black.");
    }

    @Test
    void testProcessAllBlackImage() {
        BufferedImage input = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        BufferedImage output = sobel.process(input);

        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                assertEquals(0, new Color(output.getRGB(x, y)).getRed(),
                        "All pixels in output should be black for an all-black input.");
            }
        }
    }
}
