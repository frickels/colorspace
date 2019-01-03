package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class YPbPr implements Color {
    private static final long serialVersionUID = -4670160322162439997L;

    private final double[] fdata;
    private final YPbPrStandard standard;

    public YPbPr(final YPbPrStandard standard, final double Y, final double Pb, final double Pr) {
        fdata = new double[] { Y, Pb, Pr };
        this.standard = standard;
    }

    public static YPbPr fromRGB(final YPbPrStandard standard, final RGB rgb) {
        return standard.fromRGB(rgb);
    }

    public RGB toRGB(final RGBColorSpace colorSpace) {
        return getStandard().toRGB(colorSpace, this);
    }

    public double getY() {
        return toDoubleInternal()[0];
    }

    public double getPb() {
        return toDoubleInternal()[1];
    }

    public double getPr() {
        return toDoubleInternal()[2];
    }

    public YPbPrStandard getStandard() {
        return standard;
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    @Override
    public String toString() {
        return String.format("YPbPr [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hash(standard);
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
        final YPbPr other = (YPbPr) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(standard, other.standard);
    }

    @Override
    public YPbPr clone() {
        return new YPbPr(getStandard(), getY(), getPb(), getPr());
    }
}
