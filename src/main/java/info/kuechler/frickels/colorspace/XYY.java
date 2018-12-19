package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class XYY implements CIEColor {
    private final double[] fdata;
    private final Illuminant illuminant;

    public XYY(final Illuminant illuminant, final double x, final double y, final double y2) {
        fdata = new double[] { x, y, y2 };
        this.illuminant = illuminant;
    }

    public static XYY fromXYZ(final XYZ xyz) {
        final double[] data = xyz.toDouble();
        final double sum = data[0] + data[1] + data[2];
        if (sum == 0.) {
            return xyz.getIlluminant().getXyy();
        }
        return new XYY(xyz.getIlluminant(), data[0] / sum, data[1] / sum, data[1]);
    }

    public XYZ toXYZ() {
        final double[] data = toDoubleInternal();
        if (data[1] == 0) {
            return new XYZ(illuminant, 0., 0., 0.);
        }
        return new XYZ(illuminant, (data[0] * data[2]) / data[1], data[2],
                ((1. - data[0] - data[1]) * data[2]) / data[1]);
    }

    public double getX() {
        return fdata[0];
    }

    public double getY() {
        return fdata[1];
    }

    public double getY2() {
        return fdata[2];
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    @Override
    public Illuminant getIlluminant() {
        return illuminant;
    }

    @Override
    public String toString() {
        return String.format("xyY [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        final XYY other = (XYY) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }
}
