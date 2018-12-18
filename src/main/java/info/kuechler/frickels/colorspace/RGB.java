package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.matrix33mult31;

public class RGB {
    private double[] fdata = null;
    private int[] idata = null;

    public RGB(final double r, final double g, final double b) {
        fdata = new double[] { r, g, b };
    }

    public RGB(final int r, final int g, final int b) {
        idata = new int[] { r, g, b };
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

    public static RGB fromXYZ(final RGBColorSpace colorSpace, final XYZ xyz) {
        final double[] data = xyz.toDouble();
        final double[] convert = matrix33mult31(colorSpace.getReverseTransformationMatrix(), data);
        final RGBConversionAdjuster adjuster = colorSpace.getValueAdjuster();
        return new RGB(adjuster.adjust(colorSpace, convert[0]), adjuster.adjust(colorSpace, convert[1]),
                adjuster.adjust(colorSpace, convert[2]));
    }

    public XYZ toXYZ(final RGBColorSpace colorSpace) {
        final double[] data = toDoubleInternal();
        final RGBConversionAdjuster adjuster = colorSpace.getValueReverseAdjuster();
        final double[] dataAdj = new double[] { adjuster.adjust(colorSpace, data[0]),
                adjuster.adjust(colorSpace, data[1]), adjuster.adjust(colorSpace, data[2]) };
        final double[] convert = matrix33mult31(colorSpace.getTransformationMatrix(), dataAdj);
        return new XYZ(convert[0], convert[1], convert[2]);
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
