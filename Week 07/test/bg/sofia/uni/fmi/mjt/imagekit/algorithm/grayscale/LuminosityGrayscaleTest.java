package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.Color;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LuminosityGrayscaleTest {
    LuminosityGrayscale grayscale = new LuminosityGrayscale();

    @Test
    void testProcessShouldThrowWhenImageIsNull() {
        assertThrows(IllegalArgumentException.class, () -> grayscale.process(null),
                "IllegalArgumentException should be thrown when image is null.");
    }

    @Test
    void testProcessShouldConvertSinglePixelCorrectly() {
        BufferedImage input = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        input.setRGB(0, 0, new Color(100, 150, 200).getRGB());

        BufferedImage output = grayscale.process(input);

        int resultPixel = output.getRGB(0, 0);
        Color result = new Color(resultPixel);

        int expectedGray = Color.getPixelValue(0.21 * 100 + 0.72 * 150 + 0.07 * 200);

        assertEquals(expectedGray, result.getRed());
        assertEquals(expectedGray, result.getGreen());
        assertEquals(expectedGray, result.getBlue());
    }

    @Test
    void testProcessShouldProduceGrayscaleImageForMultiplePixels() {
        BufferedImage input = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

        input.setRGB(0, 0, new Color(50, 100, 150).getRGB());
        input.setRGB(1, 0, new Color(10, 20, 30).getRGB());
        input.setRGB(0, 1, new Color(200, 100, 50).getRGB());
        input.setRGB(1, 1, new Color(255, 255, 255).getRGB());

        BufferedImage output = grayscale.process(input);

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                Color pixel = new Color(output.getRGB(x, y));
                assertEquals(pixel.getRed(), pixel.getGreen());
                assertEquals(pixel.getGreen(), pixel.getBlue());
            }
        }
    }

    @Test
    void testProcessShouldPreserveImageSize() {
        BufferedImage input = new BufferedImage(3, 4, BufferedImage.TYPE_INT_RGB);

        BufferedImage output = grayscale.process(input);

        assertEquals(3, output.getWidth());
        assertEquals(4, output.getHeight());
    }

    @Test
    void testProcessShouldProduceTypeIntRgbOutput() {
        BufferedImage input = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);

        BufferedImage output = grayscale.process(input);

        assertEquals(BufferedImage.TYPE_INT_RGB, output.getType());
    }
}