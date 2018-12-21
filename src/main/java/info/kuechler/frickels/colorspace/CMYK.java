package info.kuechler.frickels.colorspace;

import java.util.Arrays;

public class CMYK {
    private final double[] fdata;

    public static CMYK fromRGB(final RGB rgb) {
        final double R = rgb.getR();
        final double G = rgb.getG();
        final double B = rgb.getB();
        final double max = Math.max(R, Math.max(G, B));
        if (max == 0.) {
            return new CMYK(0., 0., 0., 1.);
        }
        final double K = 1. - max;
        final double C = (1. - R - K) / (1. - K);
        final double M = (1. - G - K) / (1. - K);
        final double Y = (1. - B - K) / (1. - K);
        return new CMYK(C, M, Y, K);
    }

    public RGB toRGB(final RGBColorSpace colorSpace) {
        final double K = getK();
        final double R = (1. - getC()) * (1. - K);
        final double G = (1. - getM()) * (1. - K);
        final double B = (1. - getY()) * (1. - K);
        return new RGB(colorSpace, R, G, B);
    }

    public CMY toCMY() {
        final double K = getK();
        final double C = (getC() * (1. - K) + K);
        final double M = (getM() * (1. - K) + K);
        final double Y = (getY() * (1. - K) + K);
        return new CMY(C, M, Y);
    }

    public CMYK(final double C, final double M, final double Y, final double K) {
        this.fdata = new double[] { C, M, Y, K };
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

    public double getK() {
        return fdata[3];
    }

    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public String toString() {
        return String.format("CMYK [%.10f, %.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2], fdata[3]);
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
        final CMYK other = (CMYK) obj;
        return Arrays.equals(fdata, other.fdata);
    }
}
