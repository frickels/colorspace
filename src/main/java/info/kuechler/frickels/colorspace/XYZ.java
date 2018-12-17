package info.kuechler.frickels.colorspace;

public class XYZ {
    private double[] fdata = null;

    public XYZ(final double x, final double y, final double z) {
        fdata = new double[] { x, y, z };
    }

    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public String toString() {
        final double[] data = toDoubleInternal();
        return String.format("XYZ [%.10f, %.10f, %.10f]", data[0], data[1], data[2]);
    }

    public double getX() {
        return fdata[0];
    }

    public double getY() {
        return fdata[1];
    }

    public double getZ() {
        return fdata[2];
    }
}
