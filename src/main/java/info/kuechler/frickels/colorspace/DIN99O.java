package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

// Georg A. Klein; Industria Color Physics; 978-1-4419-1196-4
// Google Books https://books.google.de/books?id=WsKOAVCrLnwC&pg=PA148&lpg=PA148&dq=DIN99o&source=bl&ots=dFN7xrKesR&sig=6ctiZrEzsfXUMhh8KHzXCdkniJw&hl=de&ei=5sKlToDMFoLg4QSt3tj1BA&sa=X&oi=book_result&ct=result#v=onepage&q=DIN99o&f=true
//
// https://de.wikipedia.org/wiki/Diskussion:DIN99-Farbraum
public class DIN99O implements CIEColor {
    private static final double RAD_26DEG = Math.toRadians(26.);
    private static final double SIN_26DEG = Math.sin(RAD_26DEG);
    private static final double COS_26DEG = Math.cos(RAD_26DEG);
    private static final double FAC_1 = 100. / Math.log(139. / 100.); // = 303.67
    private static final double kCH = 1.;
    private static final double kE = 1.;

    private final double[] fdata;
    private final Illuminant illuminant;

    public static DIN99O fromLAB(final LAB lab) {
        final double L = lab.getL();
        final double a = lab.getA();
        final double b = lab.getB();

        final double eo = a * COS_26DEG + b * SIN_26DEG;
        final double fo = 0.83 * (b * COS_26DEG - a * SIN_26DEG);
        final double heofo = Math.atan2(fo, eo);
        final double Go = Math.sqrt(eo * eo + fo * fo);
        final double C99o = Math.log(1. + 0.075 * Go) / (0.0435 * kCH * kE);
        final double h99o = heofo + RAD_26DEG;

        final double L99 = FAC_1 / kE * Math.log(1. + 0.0039 * L);
        final double a99 = C99o * Math.cos(h99o);
        final double b99 = C99o * Math.sin(h99o);

        return new DIN99O(lab.getIlluminant(), L99, a99, b99);
    }

    public LAB toLAB() {
        final double L = getL();
        final double a = getA();
        final double b = getB();

        final double h99ef = Math.atan2(b, a);
        final double heofo = h99ef - RAD_26DEG;
        final double C99 = Math.sqrt(a * a + b * b);
        final double G = (Math.exp(0.0435 * kE * kCH * C99) - 1.) / 0.075;
        final double e = G * Math.cos(heofo);
        final double f = G * Math.sin(heofo);

        final double Ln = (Math.exp(L * kE / FAC_1) - 1.) / 0.0039;
        // rotation 26°
        final double an = (e * COS_26DEG - (f / 0.83) * SIN_26DEG);
        final double bn = (e * SIN_26DEG + (f / 0.83) * COS_26DEG);

        return new LAB(illuminant, Ln, an, bn);
    }

    public DIN99O(final Illuminant illuminant, final double L, final double A, final double B) {
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

    public double getDiff(final DeltaE<DIN99O> deltaE, final DIN99O din992) {
        return deltaE.calculate(this, din992);
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
        final DIN99O other = (DIN99O) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }
}
