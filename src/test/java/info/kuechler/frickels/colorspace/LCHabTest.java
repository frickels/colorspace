package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LCHabTest {
    private static final Logger LOG = LoggerFactory.getLogger(LCHabTest.class);
    
    @Test
    public final void test() {
        final LAB lab = new LAB(D65_2, 90., -66., 75.);
        final LCHab lch = LCHab.fromLAB(lab);
        LOG.info("LCHabTest " + lch);
        assertDoubleDiff(0.0001, 90., lch.getL());
        assertDoubleDiff(0.0001, 99.9050, lch.getC());
        assertDoubleDiff(0.0001, 131.3478, lch.getH());

        final LAB lab2 = lch.toLAB();
        LOG.info("LCHabTest " + lab);
        assertDoubleDiff(0.0001, 90., lab2.getL());
        assertDoubleDiff(0.0001, -66., lab2.getA());
        assertDoubleDiff(0.0001, 75., lab2.getB());
    }

    @Test
    public final void testBlack() {
        final LAB lab = new LAB(D65_2, 0., 0., 0.);
        final LCHab lch = LCHab.fromLAB(lab);
        LOG.info("LCHabTest " + lch);
        assertDoubleDiff(0.0001, 0., lch.getL());
        assertDoubleDiff(0.0001, 0., lch.getC());
        assertDoubleDiff(0.0001, 0., lch.getH());

        final LAB lab2 = lch.toLAB();
        LOG.info("LCHabTest " + lab);
        assertDoubleDiff(0.0001, 0., lab2.getL());
        assertDoubleDiff(0.0001, 0., lab2.getA());
        assertDoubleDiff(0.0001, 0., lab2.getB());
    }

    @Test
    public final void testWhite() {
        final LAB lab = new LAB(D65_2, 100., 0., 0.);
        final LCHab lch = LCHab.fromLAB(lab);
        LOG.info("LCHabTest " + lch);
        assertDoubleDiff(0.0001, 100., lch.getL());
        assertDoubleDiff(0.0001, 0., lch.getC());
        assertDoubleDiff(0.0001, 0., lch.getH());

        final LAB lab2 = lch.toLAB();
        LOG.info("LCHabTest " + lab);
        assertDoubleDiff(0.0001, 100., lab2.getL());
        assertDoubleDiff(0.0001, 0., lab2.getA());
        assertDoubleDiff(0.0001, 0., lab2.getB());
    }
}
