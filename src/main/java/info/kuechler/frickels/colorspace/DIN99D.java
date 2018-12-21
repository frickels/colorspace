package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

// https://web.archive.org/web/20080221072513/http://www.farbmetrik-gall.de/cielab/korrcielab/din99.html
// https://de.wikipedia.org/wiki/DIN99-Farbraum
public class DIN99D implements CIEColor {
    private static final double SIN_16DEG = Math.sin(Math.toRadians(16.));
    private static final double COS_16DEG = Math.cos(Math.toRadians(16.));
    private static final double kCH = 1.;
    private static final double kE = 1.;

    private final double[] fdata;
    private final Illuminant illuminant;

    public static DIN99D fromLAB(final LAB lab) {
        final double L = lab.getL();
        final double a = lab.getA();
        final double b = lab.getB();

        final double L99 = 105.51 * Math.log(1. + 0.0158 * L);
        double a99 = 0.;
        double b99 = 0.;
        if (a != 0. || b != 0.) {
            final double e = a * COS_16DEG + b * SIN_16DEG;
            final double f = 0.7 * (-a * SIN_16DEG + b * COS_16DEG);
            final double G = Math.sqrt(e * e + f * f);
            final double k = Math.log(1. + 0.045 * G) / (0.045 * kCH * kE);
            if (G != 0.) {
                a99 = k * (e / G);
                b99 = k * (f / G);
            }
        }
        return new DIN99D(lab.getIlluminant(), L99, a99, b99);
    }

    public LAB toLAB() {
        final double L = getL();
        final double a = getA();
        final double b = getB();

        final double hef = Math.atan2(b, a);
        final double C = Math.sqrt(a * a + b * b);
        final double G = (Math.exp(0.045 * C * kCH * kE) - 1.) / 0.045;
        final double e = G * Math.cos(hef);
        final double f = G * Math.sin(hef);

        final double Ln = (Math.exp((L * kE) / 105.51) - 1.) / 0.0158;
        final double an = e * COS_16DEG - (f / 0.7) * SIN_16DEG;
        final double bn = e * SIN_16DEG + (f / 0.7) * COS_16DEG;
        return new LAB(illuminant, Ln, an, bn);
    }

    public DIN99D(final Illuminant illuminant, final double L, final double A, final double B) {
        this.fdata = new double[] { L, A, B };
        this.illuminant = illuminant;
    }

    public double getL() {
        return fdata[0];
    }

    public double getA() {
        return fdata[1];
    }

    public double getB() {
        return fdata[2];
    }

    @Override
    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public Illuminant getIlluminant() {
        return illuminant;
    }

    @Override
    public String toString() {
        return String.format("DIN99 [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hash(illuminant);
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
        final DIN99D other = (DIN99D) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }
}
