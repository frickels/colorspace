package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class LCHabTest {
    @Test
    public final void test() {
        final LAB lab = new LAB(D65_2, 90., -66., 75.);
        final LCHab lch = LCHab.fromLAB(lab);
        System.out.println("LCHabTest " + lch);
        assertDoubleDiff(0.0001, 90., lch.getL());
        assertDoubleDiff(0.0001, 99.9050, lch.getC());
        assertDoubleDiff(0.0001, 131.3478, lch.getH());
    }

    @Test
    public final void testReverse() {
        final LCHab lch = new LCHab(D65_2, 90., 99.9050, 131.3478);
        final LAB lab = lch.toLAB();
        System.out.println("LCHabTest " + lab);
        assertDoubleDiff(0.0001, 90., lab.getL());
        assertDoubleDiff(0.0001, -66., lab.getA());
        assertDoubleDiff(0.0001, 75., lab.getB());
    }
}
