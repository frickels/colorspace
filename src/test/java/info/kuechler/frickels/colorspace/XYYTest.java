package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class XYYTest {
    @Test
    public final void test() {
        final RGB rgb = new RGB(sRGB, 0, 0.5, 1.);
        System.out.println("XYYTest " + rgb);
        final XYZ xyz = rgb.toXYZ();
        System.out.println("XYYTest " + xyz);
        final XYY xyy = XYY.fromXYZ(xyz);
        System.out.println("XYYTest " + xyy);
        final XYZ xyz2 = xyy.toXYZ();
        System.out.println("XYYTest " + xyz2);
        final RGB rgb2 = RGB.fromXYZ(sRGB, xyz2);
        System.out.println("XYYTest " + rgb2);

        assertDoubleDiff(0.001, 0., rgb2.getR());
        assertDoubleDiff(0.001, 0.5, rgb2.getG());
        assertDoubleDiff(0.001, 1., rgb2.getB());
    }

}
