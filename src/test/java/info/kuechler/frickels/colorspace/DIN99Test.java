package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class DIN99Test {
    @Test
    public final void testBlack() {
        final LAB lab = new LAB(D65_2, 0., 0., 0.);
        final DIN99 din99 = DIN99.fromLAB(lab);
        System.out.println("DIN99Test " + din99);
        final LAB lab2 = din99.toLAB();
        System.out.println("DIN99Test " + lab2);
        assertDoubleDiff(0.00001, 0., lab2.getL());
        assertDoubleDiff(0.00001, 0., lab2.getA());
        assertDoubleDiff(0.00001, 0., lab2.getB());
    }

    @Test
    public final void test() {
        final LAB lab = new LAB(D65_2, 90.0606492996, -66.0112378364, 74.8156751483);
        System.out.println("DIN99Test " + lab);
        final DIN99 din99 = DIN99.fromLAB(lab);
        System.out.println("DIN99Test " + din99);
        final LAB lab2 = din99.toLAB();
        System.out.println("DIN99Test " + lab2);
        assertDoubleDiff(0.0001, 90.0606492996, lab2.getL());
        assertDoubleDiff(0.0001, -66.0112378364, lab2.getA());
        assertDoubleDiff(0.0001, 74.8156751483, lab2.getB());
    }

    @Test
    public final void testDiff1() {
        final LAB lab1 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0x5C70C8).toXYZ());
        final LAB lab2 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0xDCC670).toXYZ());
        // TODO What is right???
        testDiffInternal(lab1, lab2, 37.9877);
    }

    @Test
    public final void testDiff2() {
        final LAB lab1 = new LAB(D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(D65_2, 90., 10., 5.);
        // TODO What is right???
        testDiffInternal(lab1, lab2, 7.19899);
    }

    @Test
    public final void testDiff3() {
        final LAB lab1 = new LAB(D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(D65_2, 100., 10., 5.);
        // TODO What is right???
        testDiffInternal(lab1, lab2, 2.714282);
    }

    @Test
    public final void testDiff4() {
        final LAB lab1 = new LAB(D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(D65_2, 100., 10., 10.);
        testDiffInternal(lab1, lab2, 0.);
    }

    public final void testDiffInternal(final LAB lab1, final LAB lab2, final double expected) {
        final double deltaELab = lab1.getDiff(DeltaE.CIE1976Δ, lab2);
        System.out.println("DIN99DTest " + deltaELab);
        final DIN99 DIN99d1 = DIN99.fromLAB(lab1);
        final DIN99 DIN99d2 = DIN99.fromLAB(lab2);
        System.out.println("DIN99DTest " + DIN99d1);
        System.out.println("DIN99DTest " + DIN99d2);
        final double deltaE = DIN99d1.getDiff(DeltaE.DIN99Δ, DIN99d2);
        System.out.println("DIN99DTest " + deltaE);
        assertDoubleDiff(0.0001, expected, deltaE);
    }
}
