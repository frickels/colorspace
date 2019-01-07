package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.C_2;
import static info.kuechler.frickels.colorspace.Illuminant.D50_2;
import static info.kuechler.frickels.colorspace.Illuminant.D65_2;
import static info.kuechler.frickels.colorspace.Illuminant.E_2;
import static info.kuechler.frickels.colorspace.RGBGammaStrategy.NONE;
import static info.kuechler.frickels.colorspace.RGBGammaStrategy.PHOTO_PRO;
import static info.kuechler.frickels.colorspace.RGBGammaStrategy.XYZ_RGB_L;
import static info.kuechler.frickels.colorspace.RGBGammaStrategy.XYZ_RGB_STD;
import static info.kuechler.frickels.colorspace.RGBGammaStrategy.XYZ_SRGB;

import java.io.Serializable;

public interface RGBColorSpace extends Serializable {
    // https://de.wikipedia.org/wiki/RGB-Farbraum#Der_CIE-RGB-Farbraum
    public static final RGBColorSpace CIE_RGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(E_2, 0.734690, 0.265310, 0.000000), //
            new XYZ(E_2, 0.272958, 0.718062, 0.008980), //
            new XYZ(E_2, 0.166446, 0.008964, 0.824589), //
            E_2, 0, NONE // L=Y, Y=L
    );

    // https://de.wikipedia.org/wiki/RGB-Farbraum#Der_Farbraum_des_fr%C3%BChen_NTSC
    public static final RGBColorSpace NTSC_1953_RGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(C_2, 0.6700, 0.3300, 0.0), //
            new XYZ(C_2, 0.2100, 0.7100, 0.08), //
            new XYZ(C_2, 0.1400, 0.0800, 0.3737), //
            C_2, 2.2, XYZ_RGB_STD // Gamma between 2.2 and 2.5
    );

    // https://de.wikipedia.org/wiki/RGB-Farbraum#Farbraum_von_PAL_und_SECAM_sowie_sp%C3%A4teres_NTSC_(EBU_3213,_ITU-R_BT.470-2,_SMPTE-C)
    public static final RGBColorSpace PAL_SECAM_RGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D65_2, 0.6400, 0.3300, 0.030), //
            new XYZ(D65_2, 0.2900, 0.6000, 0.110), //
            new XYZ(D65_2, 0.1500, 0.0600, 0.790), //
            D65_2, 2.2, XYZ_SRGB);

    // https://de.wikipedia.org/wiki/RGB-Farbraum#Farbraum_von_PAL_und_SECAM_sowie_sp%C3%A4teres_NTSC_(EBU_3213,_ITU-R_BT.470-2,_SMPTE-C)
    // https://www5.in.tum.de/lehre/vorlesungen/graphik/info/csc/COL_30.htm
    // NTSC new
    public static final RGBColorSpace SMPTE_C_RGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D65_2, 0.630, 0.340, 0.030), //
            new XYZ(D65_2, 0.310, 0.595, 0.095), //
            new XYZ(D65_2, 0.1500, 0.070, 0.775), //
            D65_2, 2.2, XYZ_SRGB);

    // https://de.wikipedia.org/wiki/RGB-Farbraum#Der_sRGB-Farbraum
    // https://en.wikipedia.org/wiki/SRGB
    public static final RGBColorSpace sRGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D65_2, 0.6400, 0.3300, 0.030), //
            new XYZ(D65_2, 0.3000, 0.6000, 0.100), //
            new XYZ(D65_2, 0.1500, 0.0600, 0.790), //
            D65_2, 2.2, // about, transformation use 2.4 (fix)
            XYZ_SRGB//
    );

    // https://de.wikipedia.org/wiki/RGB-Farbraum#Adobe-RGB-(1998)-Farbraum
    public static final RGBColorSpace Adobe_RGB_1998 = RGBColorSpaceImpl.fromXYY(//
            // https://en.wikipedia.org/wiki/Adobe_RGB_color_space
            new XYY(D65_2, 0.6400, 0.3300, 0.030), //
            new XYY(D65_2, 0.2100, 0.7100, 0.080), //
            new XYY(D65_2, 0.1500, 0.0600, 0.790), //
            D65_2, 563. / 256., XYZ_RGB_STD//
    );

    // https://de.wikipedia.org/wiki/RGB-Farbraum#Der_Adobe-Wide-Gamut-RGB-Farbraum
    // http://www.babelcolor.com/index_htm_files/A%20review%20of%20RGB%20color%20spaces.pdf
    public static final RGBColorSpace WideGamutRGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D50_2, 0.734690, 0.265310, 0.000000), //
            new XYZ(D50_2, 0.114161, 0.826207, 0.059632), //
            new XYZ(D50_2, 0.156641, 0.017705, 0.825654), //
            D50_2, 563. / 256., XYZ_RGB_STD);

    // https://en.wikipedia.org/wiki/ProPhoto_RGB_color_space
    public static final RGBColorSpace ProPhotoRGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D50_2, 0.7347, 0.2653, 0.0000), //
            new XYZ(D50_2, 0.1596, 0.8404, 0.0000), //
            new XYZ(D50_2, 0.0366, 0.0001, 0.9633), //
            D50_2, 1.8, PHOTO_PRO);

    // http://www.babelcolor.com/index_htm_files/A%20review%20of%20RGB%20color%20spaces.pdf
    public static final RGBColorSpace AppleRGB = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D65_2, 0.6250, 0.3400, 0.0350), //
            new XYZ(D65_2, 0.2800, 0.5950, 0.1250), //
            new XYZ(D65_2, 0.1550, 0.0700, 0.7750), //
            D65_2, 1.8, XYZ_RGB_STD//
    );

    // https://de.wikipedia.org/wiki/RGB-Farbraum#European_Color_Initiative:_Der_eciRGB-Farbraum
    public static final RGBColorSpace ECI_RGBv2 = RGBColorSpaceImpl.fromXYZ(//
            new XYZ(D50_2, 0.6699997, 0.3300003, 0.0), //
            new XYZ(D50_2, 0.2099732, 0.7100362, 0.0799906), //
            new XYZ(D50_2, 0.1399915, 0.0800176, 0.7799909), //
            D50_2, 0., // L*, no/unknown gamma
            XYZ_RGB_L//
    );

    // TODO SMPTE ST2084:2014/CEA-861-3-Farbraum (Dolby HDR)
    // https://de.wikipedia.org/wiki/RGB-Farbraum#SMPTE_ST2084:2014/CEA-861-3-Farbraum_(Dolby_HDR)

    // TODO Hybrid Log Gamma-Farbraum
    // https://de.wikipedia.org/wiki/RGB-Farbraum#Hybrid_Log_Gamma-Farbraum

    // http://brucelindbloom.com/index.html?WorkingSpaceInfo.html
    public static final RGBColorSpace BestRGB = RGBColorSpaceImpl.fromXYY(//
            new XYY(D50_2, 0.7347, 0.2653, 0.228457), //
            new XYY(D50_2, 0.2150, 0.7750, 0.737352), //
            new XYY(D50_2, 0.1300, 0.0350, 0.034191), //
            D50_2, 2.2, XYZ_RGB_STD//
    );

    // http://brucelindbloom.com/index.html?WorkingSpaceInfo.html
    public static final RGBColorSpace BetaRGB = RGBColorSpaceImpl.fromXYY(//
            new XYY(D50_2, 0.6888, 0.3112, 0.303273), //
            new XYY(D50_2, 0.1986, 0.7551, 0.663786), //
            new XYY(D50_2, 0.1265, 0.0352, 0.032941), //
            D50_2, 2.2, XYZ_RGB_STD //
    );

    // http://brucelindbloom.com/index.html?WorkingSpaceInfo.html
    public static final RGBColorSpace BruceRGB = RGBColorSpaceImpl.fromXYY(//
            new XYY(D65_2, 0.6400, 0.3300, 0.240995), //
            new XYY(D65_2, 0.2800, 0.6500, 0.683554), //
            new XYY(D65_2, 0.1500, 0.0600, 0.075452), //
            D65_2, 2.2, XYZ_RGB_STD//
    );

    // http://brucelindbloom.com/index.html?WorkingSpaceInfo.html
    public static final RGBColorSpace ColorMatchRGB = RGBColorSpaceImpl.fromXYY(//
            new XYY(D50_2, 0.6300, 0.3400, 0.274884), //
            new XYY(D50_2, 0.2950, 0.6050, 0.658132), //
            new XYY(D50_2, 0.1500, 0.0750, 0.066985), //
            D50_2, 1.8, XYZ_RGB_STD//
    );

    // http://brucelindbloom.com/index.html?WorkingSpaceInfo.html
    public static final RGBColorSpace DonRGB4 = RGBColorSpaceImpl.fromXYY(//
            new XYY(D50_2, 0.6960, 0.3000, 0.278350), //
            new XYY(D50_2, 0.2150, 0.7650, 0.687970), //
            new XYY(D50_2, 0.1300, 0.0350, 0.033680), //
            D50_2, 2.2, XYZ_RGB_STD//
    );

    // http://brucelindbloom.com/index.html?WorkingSpaceInfo.html
    public static final RGBColorSpace EktaSpacePS5 = RGBColorSpaceImpl.fromXYY(//
            new XYY(D50_2, 0.6950, 0.3050, 0.260629), //
            new XYY(D50_2, 0.2600, 0.7000, 0.734946), //
            new XYY(D50_2, 0.1100, 0.0050, 0.004425), //
            D50_2, 2.2, XYZ_RGB_STD//
    );

    RGB fromXYZ(final XYZ xyz);

    XYZ toXYZ(final RGB rgb);

    Illuminant getIlluminant();

    double getGamma();
}
