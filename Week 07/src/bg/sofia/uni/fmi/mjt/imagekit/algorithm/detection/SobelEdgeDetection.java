package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.Color;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    private static final int[][] HORIZONTAL_KERNEL = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private static final int[][] VERTICAL_KERNEL = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage grayImage = grayscaleAlgorithm.process(image);

        int width = grayImage.getWidth();
        int height = grayImage.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double gx = calculateGradient(grayImage, x, y, HORIZONTAL_KERNEL);
                double gy = calculateGradient(grayImage, x, y, VERTICAL_KERNEL);

                double magnitude = Math.sqrt(gx * gx + gy * gy);

                int pixelValue = Color.getPixelValue(magnitude);
                result.setRGB(x, y, Color.fromGrayscale(pixelValue));
            }
        }

        return result;
    }

    private double calculateGradient(BufferedImage image, int x, int y, int[][] kernel) {
        double sum = 0;

        for (int kx = -1; kx <= 1; kx++) {
            for (int ky = -1; ky <= 1; ky++) {
                int pixelX = x + kx;
                int pixelY = y + ky;

                if (pixelX < 0 || pixelX >= image.getWidth() || pixelY < 0 || pixelY >= image.getHeight()) {
                    continue;
                }

                Color color = new Color(image.getRGB(pixelX, pixelY));

                int pixelValue = color.getRed();
                int kernelValue = kernel[ky + 1][kx + 1];
                sum += pixelValue * kernelValue;
            }
        }

        return sum;
    }
}
