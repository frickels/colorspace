package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HSVTest {
    private static final Logger LOG = LoggerFactory.getLogger(HSVTest.class);
    
    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(sRGB, 0., 0., 0.);

        LOG.info("HSVTest " + rgb);
        final HSV hsv = HSV.fromRGB(rgb);
        LOG.info("HSVTest " + hsv);
        assertDoubleDiff(0.00001, 0., hsv.getH());
        assertDoubleDiff(0.00001, 0., hsv.getS());
        assertDoubleDiff(0.00001, 0., hsv.getV());
    }

    @Test
    public final void testWhite() {
        final RGB rgb = new RGB(sRGB, 1., 1., 1.);

        LOG.info("HSVTest " + rgb);
        final HSV hsv = HSV.fromRGB(rgb);
        LOG.info("HSVTest " + hsv);
        assertDoubleDiff(0.00001, 0., hsv.getH());
        assertDoubleDiff(0.00001, 0., hsv.getS());
        assertDoubleDiff(0.00001, 1., hsv.getV());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(sRGB, 1., 0., 0.);

        LOG.info("HSVTest " + rgb);
        final HSV hsv = HSV.fromRGB(rgb);
        LOG.info("HSVTest " + hsv);
        assertDoubleDiff(0.00001, 0., hsv.getH());
        assertDoubleDiff(0.00001, 1., hsv.getS());
        assertDoubleDiff(0.00001, 1., hsv.getV());
    }

    @Test
    public final void testIndigo() {
        final RGB rgb = new RGB(sRGB, .25, 0., 1.);

        LOG.info("HSVTest " + rgb);
        final HSV hsv = HSV.fromRGB(rgb);
        LOG.info("HSVTest " + hsv);
        assertDoubleDiff(0.00001, 255., hsv.getH());
        assertDoubleDiff(0.00001, 1., hsv.getS());
        assertDoubleDiff(0.00001, 1., hsv.getV());
    }

    @ParameterizedTest
    @MethodSource("info.kuechler.frickels.colorspace.CSVTestUtil#loadRGBHSVHSLFile")
    public final void testFile(final RGB rgb, final HSV hsv, final HSL hsl) throws IOException {
        final HSV calc = HSV.fromRGB(rgb);
        assertDoubleDiff(0.00001, hsv.getH(), calc.getH());
        assertDoubleDiff(0.00001, hsv.getS(), calc.getS());
        assertDoubleDiff(0.00001, hsv.getV(), calc.getV());

        final RGB calc2 = hsv.toRGB();
        assertDoubleDiff(0.00001, rgb.getR(), calc2.getR());
        assertDoubleDiff(0.00001, rgb.getG(), calc2.getG());
        assertDoubleDiff(0.00001, rgb.getB(), calc2.getB());
    }
}
