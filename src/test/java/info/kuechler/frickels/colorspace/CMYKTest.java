package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class CMYKTest {
    @Test
    public final void testGrey() {
        final RGB rgb = new RGB(RGBColorSpace.sRGB, 0.5, 0.5, 0.5);

        final CMYK cmyk = CMYK.fromRGB(rgb);
        System.out.println("CMYKTest " + cmyk);
        assertDoubleDiff(0.00001, 0., cmyk.getC());
        assertDoubleDiff(0.00001, 0., cmyk.getM());
        assertDoubleDiff(0.00001, 0., cmyk.getY());
        assertDoubleDiff(0.00001, 0.5, cmyk.getK());

        final RGB rgb2 = cmyk.toRGB(RGBColorSpace.sRGB);
        assertDoubleDiff(0.00001, 0.5, rgb2.getR());
        assertDoubleDiff(0.00001, 0.5, rgb2.getG());
        assertDoubleDiff(0.00001, 0.5, rgb2.getB());
    }

    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(RGBColorSpace.sRGB, 0., 0., 0.);

        final CMYK cmyk = CMYK.fromRGB(rgb);
        System.out.println("CMYKTest " + cmyk);
        assertDoubleDiff(0.00001, 0., cmyk.getC());
        assertDoubleDiff(0.00001, 0., cmyk.getM());
        assertDoubleDiff(0.00001, 0., cmyk.getY());
        assertDoubleDiff(0.00001, 1., cmyk.getK());

        final RGB rgb2 = cmyk.toRGB(RGBColorSpace.sRGB);
        assertDoubleDiff(0.00001, 0., rgb2.getR());
        assertDoubleDiff(0.00001, 0., rgb2.getG());
        assertDoubleDiff(0.00001, 0., rgb2.getB());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(RGBColorSpace.sRGB, 1., 0.5, 0.);

        final CMYK cmyk = CMYK.fromRGB(rgb);
        System.out.println("CMYKTest " + cmyk);
        assertDoubleDiff(0.00001, 0., cmyk.getC());
        assertDoubleDiff(0.00001, 0.5, cmyk.getM());
        assertDoubleDiff(0.00001, 1., cmyk.getY());
        assertDoubleDiff(0.00001, 0., cmyk.getK());

        final RGB rgb2 = cmyk.toRGB(RGBColorSpace.sRGB);
        assertDoubleDiff(0.00001, 1., rgb2.getR());
        assertDoubleDiff(0.00001, 0.5, rgb2.getG());
        assertDoubleDiff(0.00001, 0., rgb2.getB());
    }

    @Test
    public final void testRedToCMY() {
        final RGB rgb = new RGB(RGBColorSpace.sRGB, 1., 0.5, 0.);

        final CMY cmy = CMYK.fromRGB(rgb).toCMY();
        System.out.println("CMYTest " + cmy);
        assertDoubleDiff(0.00001, 0., cmy.getC());
        assertDoubleDiff(0.00001, 0.5, cmy.getM());
        assertDoubleDiff(0.00001, 1., cmy.getY());
    }
}
