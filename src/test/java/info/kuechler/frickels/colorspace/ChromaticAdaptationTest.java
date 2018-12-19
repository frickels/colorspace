package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.ChromaticAdaptation.BRADFORD;
import static info.kuechler.frickels.colorspace.ChromaticAdaptation.VON_KRIES;
import static info.kuechler.frickels.colorspace.ChromaticAdaptation.XYZ_SCALING;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleArrayEquals;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;

public class ChromaticAdaptationTest {

    private static final double[][] BRADFORD_REVERSE = new double[][] { //
            { 0.9869929, -0.1470543, 0.1599627 }, //
            { 0.4323053, 0.5183603, 0.0492912 }, //
            { -0.0085287, 0.0400428, 0.9684867 } //
    };

    private static final double[][] VON_KRIES_REVERSE = new double[][] { //
            { 1.8599364, -1.1293816, 0.2198974 }, //
            { 0.3611914, 0.6388125, -0.0000064 }, //
            { 0., 0., 1.0890636 } //
    };

    private static final double[][] XYZ_SCALING_REVERSE = new double[][] { //
            { 1., 0., 0. }, //
            { 0., 1., 0. }, //
            { 0., 0., 1. } //
    };

    @Test
    public final void testBradford() {
        assertDoubleArrayEquals(0.0000001, BRADFORD_REVERSE, BRADFORD.getDataReverse());
    }

    @Test
    public final void testVonKries() {
        assertDoubleArrayEquals(0.0000001, VON_KRIES_REVERSE, VON_KRIES.getDataReverse());
    }

    @Test
    public final void testXyzScaling() {
        assertDoubleArrayEquals(0.0000001, XYZ_SCALING_REVERSE, XYZ_SCALING.getDataReverse());
    }

    @Test
    public final void testSRgb2Xyz() {
        final XYZ source = new RGB(RGBColorSpace.sRGB, 0.5, 0.5, 0.5).toXYZ();
        System.out.println("ChromaticAdaptationTest source " + source);
        assertDoubleDiff(0.00001, 0.2034400, source.getX());
        assertDoubleDiff(0.00001, 0.2034400, source.getX());
        assertDoubleDiff(0.00001, 0.2034400, source.getX());

        final XYZ target = BRADFORD.adapt(source, Illuminant.D50_2);
        System.out.println("ChromaticAdaptationTest target " + target);
        assertDoubleDiff(0.00001, 0.206385, target.getX());
        assertDoubleDiff(0.00001, 0.214040, target.getY());
        assertDoubleDiff(0.00001, 0.176624, target.getZ());
    }
}
