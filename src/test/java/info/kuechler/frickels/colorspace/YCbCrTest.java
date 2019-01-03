package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;
import static info.kuechler.frickels.colorspace.YPbPrStandard.ITU_R_BT_601;

import org.junit.jupiter.api.Test;

public class YCbCrTest {

    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(sRGB, 0., 0., 0.);
        final YCbCr yCbCr = YCbCr.fromRGB(ITU_R_BT_601, rgb);

        System.out.println("YCbCrTest " + yCbCr);
        assertDoubleDiff(0.00001, 16., yCbCr.getY());
        assertDoubleDiff(0.00001, 128., yCbCr.getCb());
        assertDoubleDiff(0.00001, 128., yCbCr.getCr());
    }

    @Test
    public final void testGrey() {
        final RGB rgb = new RGB(sRGB, 0.5, 0.5, 0.5);
        final YCbCr yCbCr = YCbCr.fromRGB(ITU_R_BT_601, rgb);

        System.out.println("YCbCrTest " + yCbCr);
        assertDoubleDiff(0.00001, 125.5, yCbCr.getY());
        assertDoubleDiff(0.00001, 128., yCbCr.getCb());
        assertDoubleDiff(0.00001, 128., yCbCr.getCr());
    }

    @Test
    public final void testWhite() {
        final RGB rgb = new RGB(sRGB, 1., 1., 1.);
        final YCbCr yCbCr = YCbCr.fromRGB(ITU_R_BT_601, rgb);

        System.out.println("YCbCrTest " + yCbCr);
        assertDoubleDiff(0.00001, 235., yCbCr.getY());
        assertDoubleDiff(0.00001, 128., yCbCr.getCb());
        assertDoubleDiff(0.00001, 128., yCbCr.getCr());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(sRGB, 1., 0., 0.);
        final YCbCr yCbCr = YCbCr.fromRGB(ITU_R_BT_601, rgb);

        System.out.println("YCbCrTest " + yCbCr);
        assertDoubleDiff(0.00001, 81.481, yCbCr.getY());
        assertDoubleDiff(0.00001, 90.20316, yCbCr.getCb());
        assertDoubleDiff(0.00001, 240., yCbCr.getCr());
    }
}
