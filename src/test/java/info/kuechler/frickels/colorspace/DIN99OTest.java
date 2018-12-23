package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class DIN99OTest {
    @Test
    public final void testBlack() {
        final LAB lab = new LAB(D65_2, 0., 0., 0.);
        final DIN99O din99o = DIN99O.fromLAB(lab);
        System.out.println("DIN99OOTest " + din99o);
        final LAB lab2 = din99o.toLAB();
        System.out.println("DIN99OOTest " + lab2);
        assertDoubleDiff(0.00001, 0., lab2.getL());
        assertDoubleDiff(0.00001, 0., lab2.getA());
        assertDoubleDiff(0.00001, 0., lab2.getB());
    }

    @Test
    public final void test() {
        final LAB lab = new LAB(D65_2, 90., -66., 74.);
        System.out.println("DIN99OOTest " + lab);
        final DIN99O din99o = DIN99O.fromLAB(lab);
        System.out.println("DIN99OOTest " + din99o);
        final LAB lab2 = din99o.toLAB();
        System.out.println("DIN99OOTest " + lab2);
        assertDoubleDiff(0.0001, 90., lab2.getL());
        assertDoubleDiff(0.0001, -66., lab2.getA());
        assertDoubleDiff(0.0001, 74., lab2.getB());
    }

    @Test
    public final void testDiff() {
        final LAB lab1 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0x5C70C8).toXYZ());
        final LAB lab2 = LAB.fromXYZ(RGB.fromRGBNumber(sRGB, 0xDCC670).toXYZ());
        System.out.println("DIN99ODTest " + RGB.fromRGBNumber(sRGB, 0x5C70C8));
        System.out.println("DIN99ODTest " + RGB.fromRGBNumber(sRGB, 0xDCC670));
        System.out.println("DIN99ODTest " + lab1);
        System.out.println("DIN99ODTest " + lab2);
        final double deltaELab = lab1.getDiff(DeltaE.CIE1976Δ, lab2);
        System.out.println("DIN99ODTest " + deltaELab);
        final DIN99O DIN99Od1 = DIN99O.fromLAB(lab1);
        final DIN99O DIN99Od2 = DIN99O.fromLAB(lab2);
        System.out.println("DIN99ODTest " + DIN99Od1);
        System.out.println("DIN99ODTest " + DIN99Od2);
        final double deltaE = DIN99Od1.getDiff(DeltaE.DIN99OΔ, DIN99Od2);
        System.out.println("DIN99ODTest " + deltaE);
        // TODO What is right???
        assertDoubleDiff(0.0001, 58.3878, deltaE);
    }
}
