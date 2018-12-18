package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleArrayEquals;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class RGBColorSpaceTest {

    private final static double[][] SRGB_MATRIX_TO = { //
            { 0.4124, 0.3576, 0.1805 }, //
            { 0.2126, 0.7152, 0.0722 }, //
            { 0.0193, 0.1192, 0.9505 } //
    };

    private final static double[][] SRGB_MATRIX_FROM = { //
            { 3.2406, -1.5372, -0.4986 }, //
            { -0.9689, 1.8758, 0.0415 }, //
            { 0.0557, -0.2040, 1.0570 } //
    };

    private final static double[][] ADOBE_RGB_FROM = { //
            { 2.04159, -0.56501, -0.34473 }, //
            { -0.96924, 1.87597, 0.04156 }, //
            { 0.01344, -0.11836, 1.01517 }//
    };

    private final static double[][] ADOBE_RGB_TO = { //
            { 0.57667, 0.18556, 0.18823 }, //
            { 0.29734, 0.62736, 0.07529 }, //
            { 0.02703, 0.07069, 0.99134 } //
    };

    @Test
    public final void testSRGB() {
        final RGBColorSpace cs = RGBColorSpace.sRGB;
        assertDoubleArrayEquals(0.001, SRGB_MATRIX_TO, cs.getTransformationMatrix());
        assertDoubleArrayEquals(0.001, SRGB_MATRIX_FROM, cs.getReverseTransformationMatrix());
    }

    @Test
    public final void testAdobeRGB() {
        final RGBColorSpace cs = RGBColorSpace.AdobeRGB_98;
        assertDoubleArrayEquals(0.001, ADOBE_RGB_TO, cs.getTransformationMatrix());
        assertDoubleArrayEquals(0.001, ADOBE_RGB_FROM, cs.getReverseTransformationMatrix());
    }

    @Test
    public final void testsRGB2AdobeRGB() {
        final XYZ xyz = new RGB(.5, .5, .5).toXYZ(RGBColorSpace.sRGB);
        System.out.println("RGB2AdobeRGB " + xyz);
        assertDoubleDiff(0.00001, 0.203440, xyz.getX());
        assertDoubleDiff(0.00001, 0.214041, xyz.getY());
        assertDoubleDiff(0.00001, 0.233054, xyz.getZ());
        final RGB rgb = RGB.fromXYZ(RGBColorSpace.AdobeRGB_98, xyz);
        System.out.println("RGB2AdobeRGB " + rgb);
        assertDoubleDiff(0.0005, 0.496228, rgb.getR());
        assertDoubleDiff(0.0005, 0.496227, rgb.getG());
        assertDoubleDiff(0.0005, 0.496227, rgb.getB());
    }
}
