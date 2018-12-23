package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Test;

public class DeltaETest {

    @Test
    public final void testCIE1976() {
        final LAB lab1 = new LAB(Illuminant.D65_2, 100., 10., 10.);
        final LAB lab2 = new LAB(Illuminant.D65_2, 90., 10., 5.);
        final double v1 = DeltaE.CIE1976Δ.calculate(lab1, lab2);
        System.out.println("DeltaETest " + v1);
        TestUtil.assertDoubleDiff(0.000001, 11.180340, v1);
    }
}
