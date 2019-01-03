package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.C_2;
import static info.kuechler.frickels.colorspace.Illuminant.D50_2;
import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.Illuminant.E_2;
import static info.kuechler.frickels.colorspace.RGBConversionAdjuster.RGB_XYZ_L;
import static info.kuechler.frickels.colorspace.RGBConversionAdjuster.RGB_XYZ_STD;
import static info.kuechler.frickels.colorspace.RGBConversionAdjuster.SRGB_XYZ;
import static info.kuechler.frickels.colorspace.RGBConversionAdjuster.XYZ_RGB_L;
import static info.kuechler.frickels.colorspace.RGBConversionAdjuster.XYZ_RGB_STD;
import static info.kuechler.frickels.colorspace.RGBConversionAdjuster.XYZ_SRGB;

import java.io.Serializable;

public interface RGBColorSpace extends Serializable {

    public static final RGBColorSpace sRGB = new RGBColorSpaceImpl(//
            // https://en.wikipedia.org/wiki/SRGB
            new XYY(D65_2, 0.6400, 0.3300, 0.2126), //
            new XYY(D65_2, 0.3000, 0.6000, 0.7152), //
            new XYY(D65_2, 0.1500, 0.0600, 0.0722), //
            D65_2, 2.2, // about, transformation use 2.4 (fix)
            XYZ_SRGB, SRGB_XYZ//
    );

    public static final RGBColorSpace AdobeRGB1998 = new RGBColorSpaceImpl(//
            // https://en.wikipedia.org/wiki/Adobe_RGB_color_space
            new XYY(D65_2, 0.6400, 0.3300, 0.297361), //
            new XYY(D65_2, 0.2100, 0.7100, 0.627355), //
            new XYY(D65_2, 0.1500, 0.0600, 0.075285), //
            D65_2, 563. / 256., XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace AppleRGB = new RGBColorSpaceImpl(//
            new XYY(D65_2, 0.6250, 0.3400, 0.244634), //
            new XYY(D65_2, 0.2800, 0.5950, 0.672034), //
            new XYY(D65_2, 0.1550, 0.0700, 0.083332), //
            D65_2, 1.8, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace BestRGB = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.7347, 0.2653, 0.228457), //
            new XYY(D50_2, 0.2150, 0.7750, 0.737352), //
            new XYY(D50_2, 0.1300, 0.0350, 0.034191), //
            D50_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace BetaRGB = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.6888, 0.3112, 0.303273), //
            new XYY(D50_2, 0.1986, 0.7551, 0.663786), //
            new XYY(D50_2, 0.1265, 0.0352, 0.032941), //
            D50_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD //
    );

    public static final RGBColorSpace BruceRGB = new RGBColorSpaceImpl(//
            new XYY(D65_2, 0.6400, 0.3300, 0.240995), //
            new XYY(D65_2, 0.2800, 0.6500, 0.683554), //
            new XYY(D65_2, 0.1500, 0.0600, 0.075452), //
            D65_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace CIERGB = new RGBColorSpaceImpl(//
            new XYY(E_2, 0.7350, 0.2650, 0.176204), //
            new XYY(E_2, 0.2740, 0.7170, 0.812985), //
            new XYY(E_2, 0.1670, 0.0090, 0.010811), //
            E_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace ColorMatchRGB = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.6300, 0.3400, 0.274884), //
            new XYY(D50_2, 0.2950, 0.6050, 0.658132), //
            new XYY(D50_2, 0.1500, 0.0750, 0.066985), //
            D50_2, 1.8, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace DonRGB4 = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.6960, 0.3000, 0.278350), //
            new XYY(D50_2, 0.2150, 0.7650, 0.687970), //
            new XYY(D50_2, 0.1300, 0.0350, 0.033680), //
            D50_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace ECIRGBv2 = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.6700, 0.3300, 0.320250), //
            new XYY(D50_2, 0.2100, 0.7100, 0.602071), //
            new XYY(D50_2, 0.1400, 0.0800, 0.077679), //
            D50_2, 0., // L*, no gamma
            XYZ_RGB_L, RGB_XYZ_L//
    );

    public static final RGBColorSpace EktaSpacePS5 = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.6950, 0.3050, 0.260629), //
            new XYY(D50_2, 0.2600, 0.7000, 0.734946), //
            new XYY(D50_2, 0.1100, 0.0050, 0.004425), //
            D50_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD//
    );

    public static final RGBColorSpace NTSCRGB = new RGBColorSpaceImpl(//
            new XYY(C_2, 0.6700, 0.3300, 0.298839), //
            new XYY(C_2, 0.2100, 0.7100, 0.586811), //
            new XYY(C_2, 0.1400, 0.0800, 0.114350), //
            C_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD // Gamma between 2.2 and 2.5
    );

    public static final RGBColorSpace PAL_SECAMRGB = new RGBColorSpaceImpl(//
            new XYY(D65_2, 0.6400, 0.3300, 0.222021), //
            new XYY(D65_2, 0.2900, 0.6000, 0.706645), //
            new XYY(D65_2, 0.1500, 0.0600, 0.071334), //
            D65_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD// TODO wie sRGB?
                                                // https://de.wikipedia.org/wiki/RGB-Farbraum#Farbraum_von_PAL_und_SECAM_sowie_sp%C3%A4teres_NTSC_(EBU_3213,_ITU-R_BT.470-2,_SMPTE-C)
    );

    // https://en.wikipedia.org/wiki/ProPhoto_RGB_color_space
    public static final RGBColorSpace ProPhotoRGB = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.7347, 0.2653, 0.288040), //
            new XYY(D50_2, 0.1596, 0.8404, 0.711874), //
            new XYY(D50_2, 0.0366, 0.0001, 0.000086), //
            D50_2, 1.8, XYZ_RGB_STD, RGB_XYZ_STD// TODO andere Werte für kleine Y
                                                // https://de.wikipedia.org/wiki/RGB-Farbraum
    );

    public static final RGBColorSpace SMPTE_C_RGB = new RGBColorSpaceImpl(//
            new XYY(D65_2, 0.6300, 0.3400, 0.212395), //
            new XYY(D65_2, 0.3100, 0.5950, 0.701049), //
            new XYY(D65_2, 0.1550, 0.0700, 0.086556), //
            D65_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD// TODO https://de.wikipedia.org/wiki/RGB-Farbraum
    );

    public static final RGBColorSpace WideGamutRGB = new RGBColorSpaceImpl(//
            new XYY(D50_2, 0.7350, 0.2650, 0.258187), //
            new XYY(D50_2, 0.1150, 0.8260, 0.724938), //
            new XYY(D50_2, 0.1570, 0.0180, 0.016875), //
            D50_2, 2.2, XYZ_RGB_STD, RGB_XYZ_STD// TODO Gamma auch 563. / 256.???
    );

    RGB fromXYZ(final XYZ xyz);

    XYZ toXYZ(final RGB rgb);

    Illuminant getIlluminant();

    double getGamma();
}
