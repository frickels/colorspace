package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

// http://www.brucelindbloom.com/index.html?Eqn_XYZ_to_Luv.html
// https://de.wikipedia.org/wiki/CIELUV-Farbraumsystem
// https://en.wikipedia.org/wiki/CIELUV
public class LUV implements CIEColor {
    private static final long serialVersionUID = 4472194476809655875L;
    
    private final double[] fdata;
    private final Illuminant illuminant;

    public static LUV fromXYZ(final XYZ xyz) {
        // white point
        final XYZ ill = xyz.getIlluminant().getXyy().toXYZ();
        final double Xn = ill.getX();
        final double Yn = ill.getY();
        final double Zn = ill.getZ();
        //
        final double xsnPre = (Xn + 15. * Yn + 3. * Zn);
        final double usn = (4. * Xn) / xsnPre;
        final double vsn = (9. * Yn) / xsnPre;

        // point
        final double X = xyz.getX();
        final double Y = xyz.getY();
        final double Z = xyz.getZ();
        //
        final double us;
        final double vs;
        final double sPre = (X + 15. * Y + 3. * Z);
        if (sPre == 0.) {
            us = 0.;
            vs = 0.;
        } else {
            us = (4. * X) / sPre;
            vs = (9. * Y) / sPre;
        }

        final double yr = Y / Yn;
        final double Ll;
        if (yr > ϵ) {
            Ll = 116. * Math.cbrt(yr) - 16.;
        } else {
            Ll = κ * yr;
        }
        final double ul = 13. * Ll * (us - usn);
        final double vl = 13. * Ll * (vs - vsn);

        return new LUV(xyz.getIlluminant(), Ll, ul, vl);
    }

    public XYZ toXYZ() {
        // white point
        final XYZ ill = getIlluminant().getXyy().toXYZ();
        final double Xn = ill.getX();
        final double Yn = ill.getY();
        final double Zn = ill.getZ();
        //
        final double xsnPre = (Xn + 15. * Yn + 3. * Zn);
        final double usn = (4. * Xn) / xsnPre;
        final double vsn = (9. * Yn) / xsnPre;

        // point
        final double L = getL();
        final double U = getU();
        final double V = getV();

        // calc
        final double Y;
        if (L > κ * ϵ) { // κ*ϵ=8
            Y = Math.pow((L + 16.) / 116., 3.);
        } else {
            Y = L / κ;
        }

        final double a = ((52. * L) / (U + 13. * L * usn) - 1.) / 3.;
        final double b = -5. * Y;
        final double c = -1. / 3.;
        final double d = Y * ((39. * L) / (V + 13. * L * vsn) - 5.);

        final double X;
        if (Double.isNaN(a) || Double.isNaN(d)) {
            X = 0.;
        } else {
            X = (d - b) / (a - c);
        }
        final double Z;
        if (Double.isNaN(a)) {
            Z = 0.;
        } else {
            Z = X * a + b;
        }
        return new XYZ(illuminant, X, Y, Z);
    }

    public LUV(final Illuminant illuminant, final double L, final double u, final double v) {
        this.fdata = new double[] { L, u, v };
        this.illuminant = illuminant;
    }

    public double getL() {
        return fdata[0];
    }

    public double getU() {
        return fdata[1];
    }

    public double getV() {
        return fdata[2];
    }

    public double getDiff(final DeltaE<LUV> deltaE, final LUV luv2) {
        return deltaE.calculate(this, luv2);
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
        return String.format("LUV [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        LUV other = (LUV) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }
    
    @Override
    public LUV clone() {
        return new LUV(illuminant, getL(), getU(), getV());
    }
}
