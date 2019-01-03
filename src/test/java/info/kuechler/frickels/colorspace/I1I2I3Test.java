package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class I1I2I3Test {

    @Test
    public final void testGrey() {
        final RGB rgb = new RGB(sRGB, 0.5, 0.5, 0.5);
        System.out.println("I1I2I3Test " + rgb);

        final I1I2I3 i1i2i3 = I1I2I3.fromRGB(rgb);
        System.out.println("I1I2I3Test " + i1i2i3);
        assertDoubleDiff(0.00001, 0.5, i1i2i3.getI1());
        assertDoubleDiff(0.00001, 0., i1i2i3.getI2());
        assertDoubleDiff(0.00001, 0., i1i2i3.getI3());

        final RGB rgb2 = i1i2i3.toRGB();
        System.out.println("I1I2I3Test " + rgb2);

        assertDoubleDiff(0.00001, 0.5, rgb2.getR());
        assertDoubleDiff(0.00001, 0.5, rgb2.getG());
        assertDoubleDiff(0.00001, 0.5, rgb2.getB());
    }

    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(sRGB, 0., 0., 0.);
        System.out.println("I1I2I3Test " + rgb);

        final I1I2I3 i1i2i3 = I1I2I3.fromRGB(rgb);
        System.out.println("I1I2I3Test " + i1i2i3);
        assertDoubleDiff(0.00001, 0., i1i2i3.getI1());
        assertDoubleDiff(0.00001, 0., i1i2i3.getI2());
        assertDoubleDiff(0.00001, 0., i1i2i3.getI3());

        final RGB rgb2 = i1i2i3.toRGB();
        System.out.println("I1I2I3Test " + rgb2);

        assertDoubleDiff(0.00001, 0., rgb2.getR());
        assertDoubleDiff(0.00001, 0., rgb2.getG());
        assertDoubleDiff(0.00001, 0., rgb2.getB());
    }

    @Test
    public final void testWhite() {
        final RGB rgb = new RGB(sRGB, 1., 1., 1.);
        System.out.println("I1I2I3Test " + rgb);

        final I1I2I3 i1i2i3 = I1I2I3.fromRGB(rgb);
        System.out.println("I1I2I3Test " + i1i2i3);
        assertDoubleDiff(0.00001, 1., i1i2i3.getI1());
        assertDoubleDiff(0.00001, 0., i1i2i3.getI2());
        assertDoubleDiff(0.00001, 0., i1i2i3.getI3());

        final RGB rgb2 = i1i2i3.toRGB();
        System.out.println("I1I2I3Test " + rgb2);

        assertDoubleDiff(0.00001, 1., rgb2.getR());
        assertDoubleDiff(0.00001, 1., rgb2.getG());
        assertDoubleDiff(0.00001, 1., rgb2.getB());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(sRGB, 1., 0., 0.);
        System.out.println("I1I2I3Test " + rgb);

        final I1I2I3 i1i2i3 = I1I2I3.fromRGB(rgb);
        System.out.println("I1I2I3Test " + i1i2i3);
        assertDoubleDiff(0.00001, 1. / 3., i1i2i3.getI1());
        assertDoubleDiff(0.00001, 1. / 2., i1i2i3.getI2());
        assertDoubleDiff(0.00001, -1. / 4., i1i2i3.getI3());

        final RGB rgb2 = i1i2i3.toRGB();
        System.out.println("I1I2I3Test " + rgb2);

        assertDoubleDiff(0.00001, 1., rgb2.getR());
        assertDoubleDiff(0.00001, 0., rgb2.getG());
        assertDoubleDiff(0.00001, 0., rgb2.getB());
    }
}
