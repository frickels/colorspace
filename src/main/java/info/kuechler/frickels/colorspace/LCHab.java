package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

// https://web.archive.org/web/20080311083637/http://www.farbmetrik-gall.de:80/cielab/index.html
// http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
public class LCHab implements CIEColor {
    private final double[] fdata;
    private final Illuminant illuminant;

    public static LCHab fromLAB(final LAB lab) {
        final double L = lab.getL();
        final double a = lab.getA();
        final double b = lab.getB();
        
        final double C = Math.sqrt(a * a + b * b);
        final double H = Math.toDegrees(Math.atan2(b, a));
        return new LCHab(lab.getIlluminant(), L, C, (H < 0.) ? H + 360. : H);
    }

    public LAB toLAB() {
        final double C = getC();
        final double Hrad = Math.toRadians(getH());
        
        return new LAB(illuminant, getL(), C * Math.cos(Hrad), C * Math.sin(Hrad));
    }

    public LCHab(final Illuminant illuminant, final double L, final double C, final double H) {
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
        return String.format("LCHab [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        final LCHab other = (LCHab) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(illuminant, other.illuminant);
    }
}
