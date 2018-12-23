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
    public final void testDiff() {
        // https://jolars.github.io/qualpalr/articles/introduction.html#examples
        final LAB lab1 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0x5C70C8).toXYZ());
        final LAB lab2 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0xDCC670).toXYZ());
        System.out.println("DIN99DTest " + RGB.fromRGBNumber(sRGB, 0x5C70C8));
        System.out.println("DIN99DTest " + RGB.fromRGBNumber(sRGB, 0xDCC670));
        System.out.println("DIN99DTest " + lab1);
        System.out.println("DIN99DTest " + lab2);
        final double deltaELab = lab1.getDiff(DeltaE.CIE1976Δ, lab2);
        System.out.println("DIN99DTest " + deltaELab);
        final DIN99 din99d1 = DIN99.fromLAB(lab1);
        final DIN99 din99d2 = DIN99.fromLAB(lab2);
        System.out.println("DIN99DTest " + din99d1);
        System.out.println("DIN99DTest " + din99d2);
        final double deltaE = din99d1.getDiff(DeltaE.DIN99Δ, din99d2);
        System.out.println("DIN99DTest " + deltaE);
        // TODO What is right???
        assertDoubleDiff(0.0001, 37.65622994848604, deltaE);
    }
}
