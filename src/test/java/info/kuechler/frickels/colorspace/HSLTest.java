package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HSLTest {
    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(sRGB, 0., 0., 0.);

        System.out.println("HSLTest " + rgb);
        final HSL hsl = HSL.fromRGB(rgb);
        System.out.println("HSLTest " + hsl);
        assertDoubleDiff(0.00001, 0., hsl.getH());
        assertDoubleDiff(0.00001, 0., hsl.getS());
        assertDoubleDiff(0.00001, 0., hsl.getL());
    }

    @Test
    public final void testWhite() {
        final RGB rgb = new RGB(sRGB, 1., 1., 1.);

        System.out.println("HSLTest " + rgb);
        final HSL hsl = HSL.fromRGB(rgb);
        System.out.println("HSLTest " + hsl);
        assertDoubleDiff(0.00001, 0., hsl.getH());
        assertDoubleDiff(0.00001, 0., hsl.getS());
        assertDoubleDiff(0.00001, 1., hsl.getL());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(sRGB, 1., 0., 0.);

        System.out.println("HSLTest " + rgb);
        final HSL hsl = HSL.fromRGB(rgb);
        System.out.println("HSLTest " + hsl);
        assertDoubleDiff(0.00001, 0., hsl.getH());
        assertDoubleDiff(0.00001, 1., hsl.getS());
        assertDoubleDiff(0.00001, .5, hsl.getL());
    }

    @Test
    public final void testIndigo() {
        final RGB rgb = new RGB(sRGB, .25, 0., 1.);

        System.out.println("HSLTest " + rgb);
        final HSL hsl = HSL.fromRGB(rgb);
        System.out.println("HSLTest " + hsl);
        assertDoubleDiff(0.00001, 255., hsl.getH());
        assertDoubleDiff(0.00001, 1., hsl.getS());
        assertDoubleDiff(0.00001, .5, hsl.getL());
    }

    @ParameterizedTest
    @MethodSource("info.kuechler.frickels.colorspace.CSVTestUtil#loadRGBHSVHSLFile")
    public final void testFile(final RGB rgb, final HSV hsv, final HSL hsl) throws IOException {
        final HSL calc = HSL.fromRGB(rgb);
        assertDoubleDiff(0.00001, hsl.getH(), calc.getH());
        assertDoubleDiff(0.00001, hsl.getS(), calc.getS());
        assertDoubleDiff(0.00001, hsl.getL(), calc.getL());

        final RGB calc2 = hsl.toRGB();
        assertDoubleDiff(0.00001, rgb.getR(), calc2.getR());
        assertDoubleDiff(0.00001, rgb.getG(), calc2.getG());
        assertDoubleDiff(0.00001, rgb.getB(), calc2.getB());
    }
}
