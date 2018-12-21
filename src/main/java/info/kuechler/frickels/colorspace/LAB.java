package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

// https://de.wikipedia.org/wiki/Lab-Farbraum
// http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
// [Wyszecki Stiles], P.166ff & P.829
public class LAB implements CIEColor {

    private static final double κ = 24389. / 27.;
    
    private static final double ϵ = 216. / 24389.;

    private static final double κ_16 = κ / 16.;
    
    private static final double _16_116 = 16. / 116.;


    private final double[] fdata;
    private final Illuminant illuminant;

    public static LAB fromXYZ(final XYZ xyz) {
        final XYZ ill = xyz.getIlluminant().getXyy().toXYZ();

        final double fx = xyz.getX() / ill.getX();
        final double fy = xyz.getY() / ill.getY();
        final double fz = xyz.getZ() / ill.getZ();

        final double x = (fx <= ϵ) ? (κ_16 * fx + _16_116) : Math.cbrt(fx);
        final double y = (fy <= ϵ) ? (κ_16 * fy + _16_116) : Math.cbrt(fy);
        final double z = (fz <= ϵ) ? (κ_16 * fz + _16_116) : Math.cbrt(fz);

        // ? [Wyszecki Stiles] P.167: (κ * fy) for (fy <= ϵ)
        final double L = 116. * y - 16.;
        final double a = 500. * (x - y);
        final double b = 200. * (y - z);

        return new LAB(xyz.getIlluminant(), L, a, b);
    }

    public XYZ toXYZ() {
        final double L = getL();
        final double a = getA();
        final double b = getB();

        final double fy = (L + 16.) / 116.;
        final double fx = a / 500. + fy;
        final double fz = fy - (b / 200.);

        final double fx_3 = Math.pow(fx, 3.);
        final double fz_3 = Math.pow(fz, 3.);

        final double xr = (fx_3 > ϵ) ? fx_3 : (116. * fx - 16.) / κ;
        final double yr = (L > κ * ϵ) ? Math.pow((L + 16.) / 116., 3) : L / κ;
        final double zr = (fz_3 > ϵ) ? fz_3 : (116. * fz - 16.) / κ;

        final XYZ illXYZ = illuminant.getXyy().toXYZ();
        return new XYZ(illuminant, xr * illXYZ.getX(), yr * illXYZ.getY(), zr * illXYZ.getZ());
    }

    public LAB(final Illuminant illuminant, final double L, final double a, final double b) {
        this.fdata = new double[] { L, a, b };
        this.illuminant = illuminant;
    }

    public double getDiff(final DeltaE deltaE, final LAB lab2) {
        return deltaE.calculate(this, lab2);
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
        return String.format("Lab [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        LAB other = (LAB) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }
}
