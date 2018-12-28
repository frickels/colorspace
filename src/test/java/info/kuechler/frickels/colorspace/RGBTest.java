package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RGBTest {
    private static final Logger LOG = LoggerFactory.getLogger(RGBTest.class);

    // http://www.brucelindbloom.com/index.html?ColorCalculator.html
    // http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html

    @Test
    public final void test() {
        final RGB c = new RGB(sRGB, 0.5, 0.5, 0.5);
        LOG.info("RGBTest " + c);
        final XYZ xyz = c.toXYZ();
        LOG.info("RGBTest " + xyz);
        TestUtil.assertDoubleArrayEquals(0.000001, new double[] { 0.203440, 0.214041, 0.233054 }, xyz.toDouble());
        final RGB d = RGB.fromXYZ(sRGB, xyz);
        LOG.info("RGBTest " + d);
        TestUtil.assertDoubleArrayEquals(0.0001, new double[] { 0.5, 0.5, 0.5 }, d.toDouble());
    }
}
