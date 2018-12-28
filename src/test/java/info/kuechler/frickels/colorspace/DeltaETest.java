package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeltaETest {
    private static final Logger LOG = LoggerFactory.getLogger(DeltaETest.class);

    @Test
    public final void testCIE1976() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CIE1976Δ.calculate(lab1, lab2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 11.180340, v1);
    }

    @Test
    public final void testCIE1994Textiles() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CIE1994Δ_TEXTILES.calculate(lab1, lab2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 6.278501, v1);
    }

    @Test
    public final void testCIE1994Graphic() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CIE1994Δ_GRAPHIC_ART.calculate(lab1, lab2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 10.6920880, v1);
    }

    @Test
    public final void testCIE2000Graphic() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CIE2000Δ.calculate(lab1, lab2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 7.142951, v1);
    }

    @Test
    public final void testCIE2000GraphicReverse() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CIE2000Δ.calculate(lab2, lab1);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 7.142951, v1);
    }

    @Test
    public final void testCIE2000GraphicZero() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final double v1 = DeltaE.CIE2000Δ.calculate(lab1, lab1);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 0., v1);
    }

    @Test
    public final void testCMC11() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CMC11Δ.calculate(lab1, lab2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 9.609243, v1);
    }

    @Test
    public final void testCMC12() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CMC21Δ.calculate(lab1, lab2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 7.627959, v1);
    }

    @Test
    public final void testLuv() {
        final LUV luv1 = LUV.fromXYZ(new LAB(Illuminant.D65_2, 100., 10., 10.).toXYZ());
        final LUV luv2 = LUV.fromXYZ(new LAB(Illuminant.D65_2, 90., 10., 5.).toXYZ());
        final double v1 = DeltaE.LUVΔ.calculate(luv1, luv2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 13.010304, v1);
    }

    @Test
    public final void testLuv2() {
        final LUV luv1 = LUV.fromXYZ(new LAB(Illuminant.D65_2, 100., 10., 10.).toXYZ());
        final LUV luv2 = LUV.fromXYZ(new LAB(Illuminant.D65_2, 100., 10., 5.).toXYZ());
        final double v1 = DeltaE.LUVΔ.calculate(luv1, luv2);
        LOG.info("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 8.127678, v1);
    }
}
