package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class RGB implements CIEColor {
    private final double[] fdata;
    private final RGBColorSpace colorSpace;

    public RGB(final RGBColorSpace colorSpace, final double r, final double g, final double b) {
        fdata = new double[] { r, g, b };
        this.colorSpace = colorSpace;
    }

    public static RGB fromRGBNumber(final RGBColorSpace colorSpace, final int rgb) {
        return from0TO255(colorSpace, rgb % 0xff, (rgb >> 8) % 0xff, (rgb >> 16) % 0xff);
    }

    public static RGB from0TO255(final RGBColorSpace colorSpace, final int r, final int g, final int b) {
        return new RGB(colorSpace, r / 255., g / 255., b / 255.);
    }
    
    public static RGB from0To100(final RGBColorSpace colorSpace, final int r, final int g, final int b) {
        return new RGB(colorSpace, r / 100., g / 100., b / 100.);
    }

    /**
     * Creates a {@link RGB} from a {@link XYZ}. Before transformation a chromatic adaption to the {@link RGBColorSpace}
     * {@link Illuminant} is called. The {@link ChromaticAdaptation#BRADFORD} method is used.
     * 
     * @param colorSpace
     *            the target {@link RGBColorSpace}
     * @param xyz
     *            the source
     * @return the {@link RGB}
     */
    public static RGB fromXYZ(final RGBColorSpace colorSpace, final XYZ xyz) {
        return colorSpace.fromXYZ(xyz);
    }

    /**
     * Creates a {@link XYZ} from the current {@link RGB} at the given {@link RGBColorSpace}. The {@link Illuminant} at
     * the {@link XYZ} is set from the {@link RGBColorSpace}.
     * 
     * @param colorSpace
     *            the source {@link RGBColorSpace}
     * @return the {@link XYZ}
     */
    public XYZ toXYZ() {
        return colorSpace.toXYZ(this);
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

    public RGBColorSpace getColorSpace() {
        return colorSpace;
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public Illuminant getIlluminant() {
        return colorSpace.getIlluminant();
    }

    @Override
    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    @Override
    public String toString() {
        return String.format("RGB [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        final RGB other = (RGB) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(colorSpace, other.colorSpace);
    }
}
