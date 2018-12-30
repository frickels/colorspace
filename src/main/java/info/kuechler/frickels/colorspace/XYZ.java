package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class XYZ implements CIEColor {
    private static final long serialVersionUID = 2007100774667081289L;

    private final double[] fdata;
    private final Illuminant illuminant;

    /**
     * 
     * @param illuminant
     *            the white point
     * @param x
     *            x, [0..1]
     * @param y
     *            y, [0..1]
     * @param z,
     *            [0..1]
     */
    public XYZ(final Illuminant illuminant, final double x, final double y, final double z) {
        this.illuminant = illuminant;
        this.fdata = new double[] { x, y, z };
    }

    public XYZ changeIlluminant(final Illuminant targetIlluminant, final ChromaticAdaptation chromaticAdaptation) {
        return chromaticAdaptation.adapt(this, targetIlluminant);
    }

    public XYZ changeIlluminant(final Illuminant targetIlluminant) {
        return changeIlluminant(targetIlluminant, ChromaticAdaptation.BRADFORD);
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
        final double[] data = toDoubleInternal();
        return String.format("XYZ [%.10f, %.10f, %.10f]", data[0], data[1], data[2]);
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
        final XYZ other = (XYZ) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }

    @Override
    public XYZ clone() {
        return new XYZ(illuminant, getX(), getY(), getZ());
    }
}
