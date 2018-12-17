package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class XYYTest {
    @Test
    public final void test() {
        final RGB rgb = new RGB(0, 0.5, 1.);
        System.out.println("XYYTest " + rgb);
        final XYZ xyz = rgb.toXYZ();
        System.out.println("XYYTest " + xyz);
        final XYY xyy = XYY.fromXYZ(xyz);
        System.out.println("XYYTest " + xyy);
        final XYZ xyz2 = xyy.toXYZ();
        System.out.println("XYYTest " + xyz2);
        final RGB rgb2 = RGB.fromXYZ(xyz2);
        System.out.println("XYYTest " + rgb2);

        assertDoubleDiff(rgb2.getR(), 0);
        assertDoubleDiff(rgb2.getG(), 0.5);
        assertDoubleDiff(rgb2.getB(), 1.);
    }

    private static void assertDoubleDiff(final double expected, final double actual) {
        final double diff = Math.abs(actual - expected);
        Assertions.assertTrue(diff < 0.001, Double.toString(diff));
    }
}
