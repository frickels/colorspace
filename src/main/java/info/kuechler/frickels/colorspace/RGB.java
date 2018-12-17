package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.matrix33mult31;

public class RGB {
    private double[] fdata = null;
    private int[] idata = null;

    private final static double[][] MATRIX_TO = { //
            { 0.4124f, 0.3576f, 0.1805f }, //
            { 0.2126f, 0.7152f, 0.0722f }, //
            { 0.0193f, 0.1192f, 0.9505f } //
    };

    private final static double[][] MATRIX_FROM = { //
            { 3.2406f, -1.5372f, -0.4986f }, //
            { -0.9689f, 1.8758f, 0.0415f }, //
            { 0.0557f, -0.2040f, 1.0570f } //
    };

    private final static double CONVERT_A = 0.055;

    private final static double CONVERT_GAMMA = 2.4;

    private final static double CONVERT_PHI = 12.92321;

    public RGB(final double x, final double y, final double z) {
        fdata = new double[] { x, y, z };
    }

    public RGB(final int x, final int y, final int z) {
        idata = new int[] { x, y, z };
    }

    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        if (fdata != null) {
            return fdata;
        }
        return new double[] { idata[0] / 255., idata[1] / 255., idata[2] / 255. };
    }

    public static RGB fromXYZ(final XYZ xyz) {
        final double[] data = xyz.toDouble();
        final double[] convert = matrix33mult31(MATRIX_FROM, data);
        return new RGB(adjustFromXYZ(convert[0]), adjustFromXYZ(convert[1]), adjustFromXYZ(convert[2]));
    }

    public XYZ toXYZ() {
        final double[] data = toDoubleInternal();
        final double[] dataAdj = new double[] { adjustToXYZ(data[0]), adjustToXYZ(data[1]), adjustToXYZ(data[2]) };
        final double[] convert = matrix33mult31(MATRIX_TO, dataAdj);
        return new XYZ(convert[0], convert[1], convert[2]);
    }

    private static double adjustFromXYZ(double f) {
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.0031308) {
            return correct * (f * CONVERT_PHI);
        }
        return correct * ((1. + CONVERT_A) * Math.pow(f, 1. / CONVERT_GAMMA) - CONVERT_A);
    }

    private double adjustToXYZ(double f) {
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.04045) {
            return correct * (f / CONVERT_PHI);
        }
        return correct * (Math.pow((f + CONVERT_A) / (1. + CONVERT_A), CONVERT_GAMMA));
    }

    @Override
    public String toString() {
        final double[] d = toDoubleInternal();
        return String.format("RGB [%f, %f, %f]", d[0], d[1], d[2]);
    }
    
    public double getR() {
        return toDoubleInternal()[0];
    }

    public double getG() {
        return toDoubleInternal()[1];
    }

    public double getB() {
        return toDoubleInternal()[2];
    }
}
