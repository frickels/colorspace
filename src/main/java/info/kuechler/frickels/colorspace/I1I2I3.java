package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

// https://de.wikipedia.org/wiki/I1I2I3-Farbraum
public class I1I2I3 implements Color {
    private static final long serialVersionUID = -1798753724712729381L;

    private final double[] fdata;
    private final RGBColorSpace colorSpace;

    private static final double[][] MATRIX = new double[][] { //
            { 1. / 3., 1. / 3., 1. / 3. }, //
            { 1. / 2., 0., -1. / 2. }, //
            { -1. / 4., 1. / 2., -1. / 4. } //
    };

    private static final double[][] MATRIX_REVERSE = MatrixUtil.invertMatrix33(MATRIX);

    public I1I2I3(final RGBColorSpace colorSpace, final double I1, final double I2, final double I3) {
        fdata = new double[] { I1, I2, I3 };
        this.colorSpace = colorSpace;
    }

    protected I1I2I3(final RGBColorSpace colorSpace, final double[] I) {
        fdata = I;
        this.colorSpace = colorSpace;
    }

    public static I1I2I3 fromRGB(final RGB rgb) {
        final double[] d = MatrixUtil.multMatrix33Vec3(MATRIX, rgb.toDouble());
        return new I1I2I3(rgb.getColorSpace(), d);
    }

    public RGB toRGB() {
        final double[] d = MatrixUtil.multMatrix33Vec3(MATRIX_REVERSE, toDoubleInternal());
        return new RGB(colorSpace, d[0], d[1], d[2]);
    }

    public double getI1() {
        return toDoubleInternal()[0];
    }

    public double getI2() {
        return toDoubleInternal()[1];
    }

    public double getI3() {
        return toDoubleInternal()[2];
    }

    public RGBColorSpace getColorSpace() {
        return colorSpace;
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    public Illuminant getIlluminant() {
        return colorSpace.getIlluminant();
    }

    @Override
    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    @Override
    public String toString() {
        return String.format("I1I2I3 [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hash(colorSpace);
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
        final I1I2I3 other = (I1I2I3) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(colorSpace, other.colorSpace);
    }

    @Override
    public I1I2I3 clone() {
        return new I1I2I3(colorSpace, getI1(), getI2(), getI3());
    }
}
