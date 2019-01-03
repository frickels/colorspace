package info.kuechler.frickels.colorspace;

import static java.lang.Math.round;

import java.util.Arrays;
import java.util.Objects;

public class YCbCr implements Color {
    private static final long serialVersionUID = 771181363666261273L;

    private final double[] fdata;
    private final YPbPrStandard standard;

    public YCbCr(final YPbPrStandard standard, final double Y, final double Cb, final double Cr) {
        fdata = new double[] { Y, Cb, Cr };
        this.standard = standard;
    }

    public static YCbCr fromYPbPr(final YPbPr ypbpr) {
        final double Y = 219. * ypbpr.getY() + 16.;
        final double Cb = 224. * ypbpr.getPb() + 128.;
        final double Cr = 224. * ypbpr.getPr() + 128.;
        return new YCbCr(ypbpr.getStandard(), Y, Cb, Cr);
    }

    /**
     * Shortcut, same as <code>YCbCr.fromYPbPr(YPbPr.fromRGB(standard, rgb))</code>.
     * 
     * @param standard
     * @param rgb
     * @return
     */
    public static YCbCr fromRGB(final YPbPrStandard standard, final RGB rgb) {
        return YCbCr.fromYPbPr(YPbPr.fromRGB(standard, rgb));
    }

    public YPbPr toYPbPr() {
        final double Y = (getY() - 16.) / 219.;
        final double Pb = (getCb() - 128.) / 224.;
        final double Pr = (getCr() - 128.) / 224.;
        return new YPbPr(getStandard(), Y, Pb, Pr);
    }

    /**
     * Shortcut, same as <code>toYPbPr().toRGB(colorSpace)</code>
     * 
     * @param colorSpace
     *            the RGB color space
     * @return {@link RGB}
     */
    public RGB toRGB(final RGBColorSpace colorSpace) {
        return toYPbPr().toRGB(colorSpace);
    }

    public double getY() {
        return toDoubleInternal()[0];
    }

    public double getCb() {
        return toDoubleInternal()[1];
    }

    public double getCr() {
        return toDoubleInternal()[2];
    }

    public YPbPrStandard getStandard() {
        return standard;
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    public double[] toDoubleRounded() {
        final double[] d = toDoubleInternal();
        return new double[] { round(d[0]), round(d[1]), round(d[2]) };
    }

    @Override
    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    @Override
    public String toString() {
        return String.format("YCbCr [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hash(standard);
        result = prime * result + Arrays.hashCode(fdata);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final YCbCr other = (YCbCr) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(standard, other.standard);
    }

    @Override
    public YCbCr clone() {
        return new YCbCr(getStandard(), getY(), getCb(), getCr());
    }
}
