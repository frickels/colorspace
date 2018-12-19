package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Test;

public class RGBTest {

    // http://www.brucelindbloom.com/index.html?ColorCalculator.html
    // http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html

    @Test
    public final void test() {
        final RGB c = new RGB(RGBColorSpace.sRGB, 0.5, 0.5, 0.5);
        System.out.println("RGBTest " + c);
        final XYZ xyz = c.toXYZ();
        System.out.println("RGBTest " + xyz);
        TestUtil.assertDoubleArrayEquals(0.000001, new double[] { 0.203440, 0.214041, 0.233054 }, xyz.toDouble());
        final RGB d = RGB.fromXYZ(RGBColorSpace.sRGB, xyz);
        System.out.println("RGBTest " + d);
        TestUtil.assertDoubleArrayEquals(0.0001, new double[] { 0.5, 0.5, 0.5 }, d.toDouble());
    }
}
