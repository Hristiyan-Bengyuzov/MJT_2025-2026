package bg.sofia.uni.fmi.mjt.imagekit;

public class Color {
    private static final int MIN_PIXEL_VALUE = 0;
    private static final int MAX_PIXEL_VALUE = 255;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;
    private static final int MASK = 0xFF;

    private final int rgb;

    public Color(int rgb) {
        this.rgb = rgb;
    }

    public Color(int red, int green, int blue) {
        red = getPixelValue(red);
        green = getPixelValue(green);
        blue = getPixelValue(blue);

        this.rgb = (red << RED_SHIFT) | (green << GREEN_SHIFT) | blue;
    }

    public int getRed() {
        return (rgb >> RED_SHIFT) & MASK;
    }

    public int getGreen() {
        return (rgb >> GREEN_SHIFT) & MASK;
    }

    public int getBlue() {
        return rgb & MASK;
    }

    public int getRGB() {
        return rgb;
    }

    public static int fromGrayscale(int grayscaleValue) {
        return (grayscaleValue << RED_SHIFT) | (grayscaleValue << GREEN_SHIFT) | grayscaleValue;
    }

    public static int getPixelValue(double value) {
        return Math.clamp((int) Math.round(value), MIN_PIXEL_VALUE, MAX_PIXEL_VALUE);
    }
}
