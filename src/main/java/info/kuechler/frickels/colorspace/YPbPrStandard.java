package info.kuechler.frickels.colorspace;

import java.io.Serializable;

public interface YPbPrStandard extends Serializable {

    YPbPrStandard ITU_R_BT_601 = new YPbPrStandardImpl(0.114, 0.299);

    YPbPrStandard ITU_R_BT_709 = new YPbPrStandardImpl(0.0722, 0.2126);

    YPbPrStandard ITU_R_BT_2020 = new YPbPrStandardImpl(0.0593, 0.2627);

    YPbPr fromRGB(final RGB rgb);

    RGB toRGB(final RGBColorSpace colorSpace, final YPbPr yPbPr);
}
