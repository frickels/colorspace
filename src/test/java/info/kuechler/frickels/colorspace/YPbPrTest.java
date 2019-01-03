package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpace.sRGB;
import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;
import static info.kuechler.frickels.colorspace.YPbPrStandard.ITU_R_BT_601;

import org.junit.jupiter.api.Test;

public class YPbPrTest {
    @Test
    public final void testBlack() {
        final RGB rgb = new RGB(sRGB, 0., 0., 0.);
        final YPbPr yPbPr = YPbPr.fromRGB(ITU_R_BT_601, rgb);
        System.out.println("YPbPrTest " + yPbPr);

        assertDoubleDiff(0.00001, 0., yPbPr.getY());
        assertDoubleDiff(0.00001, 0., yPbPr.getPb());
        assertDoubleDiff(0.00001, 0., yPbPr.getPr());
    }

    @Test
    public final void testGrey() {
        final RGB rgb = new RGB(sRGB, 0.5, 0.5, 0.5);
        final YPbPr yPbPr = YPbPr.fromRGB(ITU_R_BT_601, rgb);
        System.out.println("YPbPrTest " + yPbPr);

        assertDoubleDiff(0.00001, 0.5, yPbPr.getY());
        assertDoubleDiff(0.00001, 0., yPbPr.getPb());
        assertDoubleDiff(0.00001, 0., yPbPr.getPr());
    }

    @Test
    public final void testWhite() {
        final RGB rgb = new RGB(sRGB, 1., 1., 1.);
        final YPbPr yPbPr = YPbPr.fromRGB(ITU_R_BT_601, rgb);
        System.out.println("YPbPrTest " + yPbPr);

        assertDoubleDiff(0.00001, 1., yPbPr.getY());
        assertDoubleDiff(0.00001, 0., yPbPr.getPb());
        assertDoubleDiff(0.00001, 0., yPbPr.getPr());
    }

    @Test
    public final void testRed() {
        final RGB rgb = new RGB(sRGB, 1., .0, .0);
        final YPbPr yPbPr = YPbPr.fromRGB(ITU_R_BT_601, rgb);
        System.out.println("YPbPrTest " + yPbPr);

        assertDoubleDiff(0.00001, 0.299, yPbPr.getY());
        assertDoubleDiff(0.00001, -0.168735, yPbPr.getPb());
        assertDoubleDiff(0.00001, 0.5, yPbPr.getPr());
    }
}
