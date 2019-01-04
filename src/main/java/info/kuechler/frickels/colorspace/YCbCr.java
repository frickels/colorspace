package info.kuechler.frickels.colorspace;

import static java.lang.Math.round;

import java.util.Arrays;
import java.util.Objects;

public class YCbCr implements Color {
    private static final long serialVersionUID = 771181363666261273L;

    private final double[] fdata;
    private final YPbPrStandard standard;
    private final YCbCrAdjuster adjuster;

    public YCbCr(final YPbPrStandard standard, final YCbCrAdjuster adjuster, final double Y, final double Cb,
            final double Cr) {
        this.fdata = new double[] { Y, Cb, Cr };
        this.standard = standard;
        this.adjuster = adjuster;
    }

    public static YCbCr fromYPbPr(final YCbCrAdjuster adjuster, final YPbPr ypbpr) {
        final double[] d = adjuster.adjust(ypbpr.toDouble());
        return new YCbCr(ypbpr.getStandard(), adjuster, d[0], d[1], d[2]);
    }

    /**
     * Shortcut, same as <code>fromYPbPr(adjuster, YPbPr.fromRGB(standard, rgb))</code>.
     * 
     * @param standard
     * @param rgb
     * @return
     */
    public static YCbCr fromRGB(final YPbPrStandard standard, final YCbCrAdjuster adjuster, final RGB rgb) {
        return fromYPbPr(adjuster, YPbPr.fromRGB(standard, rgb));
    }

    public YPbPr toYPbPr() {
        final double[] d = getAdjuster().reverseAdjust(toDoubleInternal());
        return new YPbPr(getStandard(), d[0], d[1], d[2]);
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

    public int getDigialY() {
        return (int) round(getY());
    }

    public int getDigialCb() {
        return (int) round(getCb());
    }

    public int getDigialCr() {
        return (int) round(getCr());
    }

    public YPbPrStandard getStandard() {
        return standard;
    }

    public YCbCrAdjuster getAdjuster() {
        return adjuster;
    }

    private double[] toDoubleInternal() {
        return fdata;
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
        result = prime * result + Objects.hash(adjuster);
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
        return Arrays.equals(fdata, other.fdata) && Objects.equals(standard, other.standard)
                && Objects.equals(adjuster, other.adjuster);
    }

    @Override
    public YCbCr clone() {
        return new YCbCr(getStandard(), getAdjuster(), getY(), getCb(), getCr());
    }
}
