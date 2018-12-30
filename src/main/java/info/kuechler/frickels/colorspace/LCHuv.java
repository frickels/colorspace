package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class LCHuv implements CIEColor {
    private static final long serialVersionUID = 6190528715730574774L;

    private final double[] fdata;
    private final Illuminant illuminant;

    public static LCHuv fromLUV(final LUV luv) {
        final double L = luv.getL();
        final double u = luv.getU();
        final double v = luv.getV();

        final double C = Math.sqrt(u * u + v * v);
        final double H = Math.toDegrees(Math.atan2(v, u));
        return new LCHuv(luv.getIlluminant(), L, C, (H < 0.) ? H + 360. : H);
    }

    public LUV toLUV() {
        final double C = getC();
        final double Hrad = Math.toRadians(getH());

        return new LUV(illuminant, getL(), C * Math.cos(Hrad), C * Math.sin(Hrad));
    }

    public LCHuv(final Illuminant illuminant, final double L, final double C, final double H) {
        this.fdata = new double[] { L, C, H };
        this.illuminant = illuminant;
    }

    public double getL() {
        return fdata[0];
    }

    public double getC() {
        return fdata[1];
    }

    public double getH() {
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
        return String.format("LCHuv [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        final LCHuv other = (LCHuv) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }

    @Override
    public LCHuv clone() {
        return new LCHuv(illuminant, getL(), getC(), getH());
    }
}
