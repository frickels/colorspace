package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XYYTest {
    private static final Logger LOG = LoggerFactory.getLogger(XYYTest.class);
    
    @Test
    public final void test() {
        final RGB rgb = new RGB(sRGB, 0, 0.5, 1.);
        LOG.info("XYYTest " + rgb);
        final XYZ xyz = rgb.toXYZ();
        LOG.info("XYYTest " + xyz);
        final XYY xyy = XYY.fromXYZ(xyz);
        LOG.info("XYYTest " + xyy);
        final XYZ xyz2 = xyy.toXYZ();
        LOG.info("XYYTest " + xyz2);
        final RGB rgb2 = RGB.fromXYZ(sRGB, xyz2);
        LOG.info("XYYTest " + rgb2);

        assertDoubleDiff(0.001, 0., rgb2.getR());
        assertDoubleDiff(0.001, 0.5, rgb2.getG());
        assertDoubleDiff(0.001, 1., rgb2.getB());
    }

}
