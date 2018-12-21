package info.kuechler.frickels.colorspace;

import java.util.Arrays;

public class CMY {
    private final double[] fdata;

    public static CMY fromRGB(final RGB rgb) {
        final double R = rgb.getR();
        final double G = rgb.getG();
        final double B = rgb.getB();

        return new CMY(1. - R, 1. - G, 1. - B);
    }

    public RGB toRGB(final RGBColorSpace colorSpace) {
        return new RGB(colorSpace, 1. - getC(), 1. - getM(), 1. - getY());
    }

    public CMYK toCMYK() {
        final double C = getC();
        final double M = getM();
        final double Y = getY();

        final double K = Math.min(C, Math.min(M, Y));
        if (K == 1.) {
            return new CMYK(0., 0., 0., 1.);
        }
        final double Ck = (C - K) / (1. - K);
        final double Mk = (M - K) / (1. - K);
        final double Yk = (Y - K) / (1. - K);
        return new CMYK(Ck, Mk, Yk, K);
    }

    public CMY(final double C, final double M, final double Y) {
        this.fdata = new double[] { C, M, Y };
    }

    public double getC() {
        return fdata[0];
    }

    public double getM() {
        return fdata[1];
    }

    public double getY() {
        return fdata[2];
    }

    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public String toString() {
        return String.format("CMY [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        final CMY other = (CMY) obj;
        return Arrays.equals(fdata, other.fdata);
    }
}
