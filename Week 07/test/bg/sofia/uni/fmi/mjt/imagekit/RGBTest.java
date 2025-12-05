package bg.sofia.uni.fmi.mjt.imagekit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RGBTest {
    // 0x33FF99 is 51 red, 255 green, 153 blue
    RGB rgb = new RGB(0x33FF99);

    @Test
    void testColourGettersShouldReturnCorrectValue() {
        assertEquals(51, rgb.getRed(), "Red value of 0x33FF99 should be 51.");
        assertEquals(255, rgb.getGreen(), "Green value of 0x33FF99 should be 255.");
        assertEquals(153, rgb.getBlue(), "Blue value of 0x33FF99 should be 153.");
    }

    @Test
    void testGetPixelValueShouldClampBelowBound() {
        assertEquals(0, RGB.getPixelValue(-50), "GetPixelValue should clamp -50 to 0.");
    }

    @Test
    void testGetPixelValueShouldClampAboveBound() {
        assertEquals(255, RGB.getPixelValue(300), "GetPixelValue should clamp 300 to 255.");
    }

    @Test
    void testGetPixelValueShouldRoundCorrectly() {
        assertEquals(124, RGB.getPixelValue(123.6), "GetPixelValue should round 123.6 to 124.");
    }

    @Test
    void testFromGrayscaleShouldReturnSameValueForAllChannels() {
        int grayscale = 128;
        RGB rgb = new RGB(RGB.fromGrayscale(grayscale));

        assertEquals(grayscale, rgb.getRed(), "Red should be same as grayscale.");
        assertEquals(grayscale, rgb.getGreen(), "Green should be same as grayscale.");
        assertEquals(grayscale, rgb.getBlue(), "Blue should be same as grayscale.");
    }

}