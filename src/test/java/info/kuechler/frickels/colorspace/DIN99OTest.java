package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DIN99OTest {
    private static final Logger LOG = LoggerFactory.getLogger(DIN99OTest.class);
    
    @Test
    public final void testBlack() {
        final LAB lab = new LAB(D65_2, 0., 0., 0.);
        final DIN99O din99o = DIN99O.fromLAB(lab);
        LOG.info("DIN99OOTest " + din99o);
        final LAB lab2 = din99o.toLAB();
        LOG.info("DIN99OOTest " + lab2);
        assertDoubleDiff(0.00001, 0., lab2.getL());
        assertDoubleDiff(0.00001, 0., lab2.getA());
        assertDoubleDiff(0.00001, 0., lab2.getB());
    }

    @Test
    public final void test() {
        final LAB lab = new LAB(D65_2, 90., -66., 74.);
        LOG.info("DIN99OOTest " + lab);
        final DIN99O din99o = DIN99O.fromLAB(lab);
        LOG.info("DIN99OOTest " + din99o);
        final LAB lab2 = din99o.toLAB();
        LOG.info("DIN99OOTest " + lab2);
        assertDoubleDiff(0.0001, 90., lab2.getL());
        assertDoubleDiff(0.0001, -66., lab2.getA());
        assertDoubleDiff(0.0001, 74., lab2.getB());
    }

    @Test
    public final void testDiff1() {
        final LAB lab1 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0x5C70C8).toXYZ());
        final LAB lab2 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0xDCC670).toXYZ());
        // TODO What is right???
        testDiffInternal(lab1, lab2, 51.1104);
    }

    @Test
    public final void testDiff2() {
        final LAB lab1 = new LAB(D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(D65_2, 90., 10., 5.);
        // TODO What is right???
        testDiffInternal(lab1, lab2, 9.86138);
    }

    @Test
    public final void testDiff3() {
        final LAB lab1 = new LAB(D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(D65_2, 100., 10., 5.);
        // TODO What is right???
        testDiffInternal(lab1, lab2, 4.749877);
    }

    @Test
    public final void testDiff4() {
        final LAB lab1 = new LAB(D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(D65_2, 100., 10., 10.);
        testDiffInternal(lab1, lab2, 0.);
    }

    public final void testDiffInternal(final LAB lab1, final LAB lab2, final double expected) {
        final double deltaELab = lab1.getDiff(DeltaE.CIE1976Δ, lab2);
        LOG.info("DIN99ODTest " + deltaELab);
        final DIN99O DIN99Od1 = DIN99O.fromLAB(lab1);
        final DIN99O DIN99Od2 = DIN99O.fromLAB(lab2);
        LOG.info("DIN99ODTest " + DIN99Od1);
        LOG.info("DIN99ODTest " + DIN99Od2);
        final double deltaE = DIN99Od1.getDiff(DeltaE.DIN99OΔ, DIN99Od2);
        LOG.info("DIN99ODTest " + deltaE);
        assertDoubleDiff(0.0001, expected, deltaE);
    }
}
