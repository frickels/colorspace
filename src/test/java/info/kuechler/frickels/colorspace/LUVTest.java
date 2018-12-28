package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class LUVTest {
    @Test
    public final void test() {
        final XYZ xyz = new RGB(sRGB, 0.5, 0.5, 0.5).toXYZ();
        assertDoubleDiff(0.0001, 0.203440, xyz.getX());
        assertDoubleDiff(0.0001, 0.214041, xyz.getY());
        assertDoubleDiff(0.0001, 0.233054, xyz.getZ());

        final LUV luv = LUV.fromXYZ(xyz);
        assertDoubleDiff(0.0001, 53.3890, luv.getL());
        assertDoubleDiff(0.00001, 0., luv.getU());
        assertDoubleDiff(0.00001, 0., luv.getV());

        final XYZ xyz2 = luv.toXYZ();
        assertDoubleDiff(0.00001, xyz.getX(), xyz2.getX());
        assertDoubleDiff(0.00001, xyz.getY(), xyz2.getY());
        assertDoubleDiff(0.00001, xyz.getZ(), xyz2.getZ());
    }

    @Test
    public final void testBlack() {
        final XYZ xyz = new RGB(sRGB, 0., 0., 0.).toXYZ();
        assertDoubleDiff(0.0001, 0., xyz.getX());
        assertDoubleDiff(0.0001, 0., xyz.getY());
        assertDoubleDiff(0.0001, 0., xyz.getZ());

        final LUV luv = LUV.fromXYZ(xyz);
        assertDoubleDiff(0.0001, 0., luv.getL());
        assertDoubleDiff(0.00001, 0., luv.getU());
        assertDoubleDiff(0.00001, 0., luv.getV());

        final XYZ xyz2 = luv.toXYZ();
        assertDoubleDiff(0.00001, xyz.getX(), xyz2.getX());
        assertDoubleDiff(0.00001, xyz.getY(), xyz2.getY());
        assertDoubleDiff(0.00001, xyz.getZ(), xyz2.getZ());
    }
}
