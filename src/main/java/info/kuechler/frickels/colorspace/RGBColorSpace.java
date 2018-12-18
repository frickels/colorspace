package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.invert33;
import static info.kuechler.frickels.colorspace.MatrixUtil.matrix33mult31;
import static info.kuechler.frickels.colorspace.MatrixUtil.transpose33;

public class RGBColorSpace {

    public static final RGBColorSpace sRGB = new RGBColorSpace(//
            // https://en.wikipedia.org/wiki/SRGB
            new XYY(0.6400, 0.3300, 1./* 0.2126 */), //
            new XYY(0.3000, 0.6000, 1./* 0.7152 */), //
            new XYY(0.1500, 0.0600, 1./* 0.0722 */), //
            Illuminant.D65_2, //
            2.4, // TODO 2.4 for adjust, 2.2 ...
            RGBConversionAdjuster.XYZ_SRGB, //
            RGBConversionAdjuster.SRGB_XYZ);

    public static final RGBColorSpace AdobeRGB_98 = new RGBColorSpace(//
            // https://en.wikipedia.org/wiki/Adobe_RGB_color_space
            new XYY(0.6400, 0.3300, 1.), //
            new XYY(0.2100, 0.7100, 1.), //
            new XYY(0.1500, 0.0600, 1.), //
            Illuminant.D65_2, //
            563. / 256., //
            RGBConversionAdjuster.XYZ_RGB_LINEAR, //
            RGBConversionAdjuster.RGB_XYZ_LINEAR);

    private final XYY xr;
    private final XYY xg;
    private final XYY xb;
    private final Illuminant refWhite;
    private final double gamma;
    private final double[][] transformationMatrix;
    private final double[][] reverseTransformationMatrix;
    private final RGBConversionAdjuster valueAdjuster;
    private final RGBConversionAdjuster valueReverseAdjuster;

    public RGBColorSpace(final XYY xr, final XYY xg, final XYY xb, final Illuminant refWhite, final double gamma,
            RGBConversionAdjuster valueAdjuster, RGBConversionAdjuster valueReverseAdjuster) {
        this.xr = xr;
        this.xg = xg;
        this.xb = xb;
        this.refWhite = refWhite;
        this.gamma = gamma;
        this.valueAdjuster = valueAdjuster;
        this.valueReverseAdjuster = valueReverseAdjuster;
        this.transformationMatrix = calculateTransformationMatrix(xr, xg, xb, refWhite);
        this.reverseTransformationMatrix = invert33(this.transformationMatrix);
    }

    public static double[][] calculateTransformationMatrix(final XYY xr, final XYY xg, final XYY xb,
            final Illuminant refWhite) {
        final double[][] m = transpose33(new double[][] { //
                xr.toXYZ().toDouble(), //
                xg.toXYZ().toDouble(), //
                xb.toXYZ().toDouble() //
        });
        final double[] s = matrix33mult31(invert33(m), refWhite.getXyy().toXYZ().toDouble());

        return new double[][] { //
                { s[0] * m[0][0], s[1] * m[0][1], s[2] * m[0][2] }, //
                { s[0] * m[1][0], s[1] * m[1][1], s[2] * m[1][2] }, //
                { s[0] * m[2][0], s[1] * m[2][1], s[2] * m[2][2] } //
        };
    }

    public XYY getXr() {
        return xr;
    }

    public XYY getXg() {
        return xg;
    }

    public XYY getXb() {
        return xb;
    }

    public Illuminant getRefWhite() {
        return refWhite;
    }

    public double getGamma() {
        return gamma;
    }

    public RGBConversionAdjuster getValueAdjuster() {
        return valueAdjuster;
    }

    public RGBConversionAdjuster getValueReverseAdjuster() {
        return valueReverseAdjuster;
    }

    public double[][] getTransformationMatrix() {
        return transformationMatrix;
    }

    public double[][] getReverseTransformationMatrix() {
        return reverseTransformationMatrix;
    }
}
