package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LCHuvTest {
    private static final Logger LOG = LoggerFactory.getLogger(LCHuvTest.class);
    
    @Test
    public final void test() {
        final LUV luv = new LUV(D65_2, 90., -66., 75.);
        final LCHuv lch = LCHuv.fromLUV(luv);
        LOG.info("LCHuvTest " + lch);
        assertDoubleDiff(0.0001, 90., lch.getL());
        assertDoubleDiff(0.0001, 99.9050, lch.getC());
        assertDoubleDiff(0.0001, 131.3478, lch.getH());

        final LUV luv2 = lch.toLUV();
        LOG.info("LCHuvTest " + luv2);
        assertDoubleDiff(0.0001, 90., luv2.getL());
        assertDoubleDiff(0.0001, -66., luv2.getU());
        assertDoubleDiff(0.0001, 75., luv2.getV());
    }

    @Test
    public final void testBlack() {
        final LUV luv = new LUV(D65_2, 0., 0., 0.);
        final LCHuv lch = LCHuv.fromLUV(luv);
        LOG.info("LCHuvTest " + lch);
        assertDoubleDiff(0.0001, 0., lch.getL());
        assertDoubleDiff(0.0001, 0., lch.getC());
        assertDoubleDiff(0.0001, 0., lch.getH());

        final LUV luv2 = lch.toLUV();
        LOG.info("LCHuvTest " + luv2);
        assertDoubleDiff(0.0001, 0., luv2.getL());
        assertDoubleDiff(0.0001, 0., luv2.getU());
        assertDoubleDiff(0.0001, 0., luv2.getV());
    }

    @Test
    public final void testWhite() {
        final LUV luv = new LUV(D65_2, 100., 0., 0.);
        final LCHuv lch = LCHuv.fromLUV(luv);
        LOG.info("LCHuvTest " + lch);
        assertDoubleDiff(0.0001, 100., lch.getL());
        assertDoubleDiff(0.0001, 0., lch.getC());
        assertDoubleDiff(0.0001, 0., lch.getH());

        final LUV luv2 = lch.toLUV();
        LOG.info("LCHuvTest " + luv2);
        assertDoubleDiff(0.0001, 100., luv2.getL());
        assertDoubleDiff(0.0001, 0., luv2.getU());
        assertDoubleDiff(0.0001, 0., luv2.getV());
    }

}
