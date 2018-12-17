package info.kuechler.frickels.colorspace;

public class XYY {
    private double[] fdata = null;

    public XYY(final double x, final double y, final double y2) {
        fdata = new double[] { x, y, y2 };
    }

    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    public static XYY fromXYZ(final XYZ xyz) {
        final double[] data = xyz.toDouble();
        final double sum = data[0] + data[1] + data[2];
        if (sum == 0.) {
            // TODO alternative: set to white point
            return new XYY(0., 0., 0.);
        }
        return new XYY(data[0] / sum, data[1] / sum, data[1]);
    }

    public XYZ toXYZ() {
        final double[] data = toDoubleInternal();
        if (data[1] == 0) {
            return new XYZ(0., 0., 0.);
        }
        return new XYZ((data[0] * data[2]) / data[1], data[2], ((1. - data[0] - data[1]) * data[2]) / data[1]);
    }

    @Override
    public String toString() {
        return String.format("xyY [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
}
