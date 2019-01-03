package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.YPbPrStandard.ITU_R_BT_601;

import org.junit.jupiter.api.Test;

public class YPbPrStandardImplTest {

    @Test
    public final void test() {
        TestUtil.assertDoubleArrayEquals(0.0001, new double[][] { //
                { 0.299, 0.587, 0.114 }, //
                { -0.168736, -0.331264, 0.5 }, //
                { 0.5, -0.418688, -0.081312 } //
        }, ((YPbPrStandardImpl) ITU_R_BT_601).getTransformationMatrix());
    }

    @Test
    public final void testeReverse() {
        TestUtil.assertDoubleArrayEquals(0.0001, new double[][] { //
                { 1., 0., 1.402 }, //
                { 1., -0.344136, -0.714136 }, //
                { 1., 1.772, 0. } //
        }, ((YPbPrStandardImpl) ITU_R_BT_601).getReverseTransformationMatrix());
    }
}
