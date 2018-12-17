package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Test;

public class RGBTest {

    // http://www.brucelindbloom.com/index.html?ColorCalculator.html
    // http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
    
    @Test
    public final void test() {
        final RGB c = new RGB(0.5, 0.5, 0.5);
        System.err.println(c);
        final XYZ xyz = c.toXYZ();
        System.err.println(xyz);
        final RGB d = RGB.fromXYZ(xyz);
        System.err.println(d);
    }
}
