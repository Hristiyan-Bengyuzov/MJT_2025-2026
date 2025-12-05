package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.Color;

import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {
    private static final double RED_COEFFICIENT = 0.21;
    private static final double GREEN_COEFFICIENT = 0.72;
    private static final double BLUE_COEFFICIENT = 0.07;

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));

                double grayscaleValue = RED_COEFFICIENT * color.getRed()
                        + GREEN_COEFFICIENT * color.getGreen()
                        + BLUE_COEFFICIENT * color.getBlue();

                int grayPixel = Color.getPixelValue(grayscaleValue);
                result.setRGB(x, y, Color.fromGrayscale(grayPixel));
            }
        }

        return result;
    }
}
