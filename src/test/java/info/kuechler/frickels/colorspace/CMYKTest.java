package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMYKTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(CMYKTest.class);
    
    @Test
    public final void testGrey() {
        final RGB rgb = new RGB(sRGB, 0.5, 0.5, 0.5);

        final CMYK cmyk = CMYK.fromRGB(rgb);
        LOG.info("CMYKTest " + cmyk);
        assertDoubleDiff(0.00001, 0., cmyk.getC());
        assertDoubleDiff(0.00001, 0., cmyk.getM());
        assertDoubleDiff(0.00001, 0., cmyk.getY());
        assertDoubleDiff(0.00001, 0.5, cmyk.getK());

        final RGB rgb2 = cmyk.toRGB(sRGB);
        assertDoubleDiff(0.00001, 0.5, rgb2.getR());
        assertDoubleDiff(0.00001, 0.5, rgb2.getG());
        assertDoubleDiff(0.00001, 0.5, rgb2.getB());
    }

    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(sRGB, 0., 0., 0.);

        final CMYK cmyk = CMYK.fromRGB(rgb);
        LOG.info("CMYKTest " + cmyk);
        assertDoubleDiff(0.00001, 0., cmyk.getC());
        assertDoubleDiff(0.00001, 0., cmyk.getM());
        assertDoubleDiff(0.00001, 0., cmyk.getY());
        assertDoubleDiff(0.00001, 1., cmyk.getK());

        final RGB rgb2 = cmyk.toRGB(sRGB);
        assertDoubleDiff(0.00001, 0., rgb2.getR());
        assertDoubleDiff(0.00001, 0., rgb2.getG());
        assertDoubleDiff(0.00001, 0., rgb2.getB());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(sRGB, 1., 0.5, 0.);

        final CMYK cmyk = CMYK.fromRGB(rgb);
        LOG.info("CMYKTest " + cmyk);
        assertDoubleDiff(0.00001, 0., cmyk.getC());
        assertDoubleDiff(0.00001, 0.5, cmyk.getM());
        assertDoubleDiff(0.00001, 1., cmyk.getY());
        assertDoubleDiff(0.00001, 0., cmyk.getK());

        final RGB rgb2 = cmyk.toRGB(sRGB);
        assertDoubleDiff(0.00001, 1., rgb2.getR());
        assertDoubleDiff(0.00001, 0.5, rgb2.getG());
        assertDoubleDiff(0.00001, 0., rgb2.getB());
    }

    @Test
    public final void testRedToCMY() {
        final RGB rgb = new RGB(sRGB, 1., 0.5, 0.);

        final CMY cmy = CMYK.fromRGB(rgb).toCMY();
        LOG.info("CMYTest " + cmy);
        assertDoubleDiff(0.00001, 0., cmy.getC());
        assertDoubleDiff(0.00001, 0.5, cmy.getM());
        assertDoubleDiff(0.00001, 1., cmy.getY());
    }
}
