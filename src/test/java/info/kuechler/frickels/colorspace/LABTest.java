package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class LABTest {
    @Test
    public final void testGrey() {
        final LAB lab = LAB.fromXYZ(new RGB(sRGB, 0.5, 0.5, 0.5).toXYZ());
        System.out.println("LABTest " + lab);
        assertDoubleDiff(0.0001, 53.3890, lab.getL());
        assertDoubleDiff(0.00001, 0., lab.getA());
        assertDoubleDiff(0.00001, 0., lab.getB());
    }

    @Test
    public final void testOther() {
        final LAB lab = LAB.fromXYZ(new RGB(sRGB, 0.5, 1., 0.25).toXYZ());
        System.out.println("LABTest " + lab);
        assertDoubleDiff(0.0001, 90.0607, lab.getL());
        assertDoubleDiff(0.0001, -66.0112, lab.getA());
        assertDoubleDiff(0.0001, 74.8157, lab.getB());
    }

    @Test
    public final void testWhite() {
        final LAB lab = LAB.fromXYZ(D65_2.getXyy().toXYZ());
        System.out.println("LABTest " + lab);
        assertDoubleDiff(0.00001, 100., lab.getL());
        assertDoubleDiff(0.00001, 0., lab.getA());
        assertDoubleDiff(0.00001, 0., lab.getB());
    }

    @Test
    public final void testGreyReverse() {
        final XYZ source = new LAB(D65_2, 53.3890, 0., 0.).toXYZ();
        final XYZ xyz = new RGB(sRGB, 0.5, 0.5, 0.5).toXYZ();
        System.out.println("LABTest " + xyz);
        assertDoubleDiff(0.00001, source.getX(), xyz.getX());
        assertDoubleDiff(0.00001, source.getY(), xyz.getY());
        assertDoubleDiff(0.00001, source.getZ(), xyz.getZ());
    }

    @Test
    public final void testOtherReverse() {
        final XYZ source = new LAB(D65_2, 90.0607, -66.0112, 74.8157).toXYZ();
        final XYZ xyz = new RGB(sRGB, 0.5, 1., 0.25).toXYZ();
        System.out.println("LABTest " + xyz);
        assertDoubleDiff(0.00001, source.getX(), xyz.getX());
        assertDoubleDiff(0.00001, source.getY(), xyz.getY());
        assertDoubleDiff(0.00001, source.getZ(), xyz.getZ());
    }

    @Test
    public final void testWhiteReverse() {
        final LAB lab = new LAB(D65_2, 100., 0., 0.);
        final XYZ xyz = lab.toXYZ();
        final XYZ ill = D65_2.getXyy().toXYZ();
        System.out.println("LABTest " + xyz);
        assertDoubleDiff(0.00001, ill.getX(), xyz.getX());
        assertDoubleDiff(0.00001, ill.getY(), xyz.getY());
        assertDoubleDiff(0.00001, ill.getZ(), xyz.getZ());
    }

//    @ParameterizedTest
//    @MethodSource("info.kuechler.frickels.colorspace.CSVTestUtil#loadRGBLabFile")
    public final void testWithCSV(final RGB rgb, final LAB lab) throws IOException {
        final RGB r = RGB.fromXYZ(RGBColorSpace.sRGB, lab.toXYZ());
        final LAB l = LAB.fromXYZ(rgb.toXYZ());
        System.out.println(r + " " + rgb + " " + l + " " + lab);
    }
}
