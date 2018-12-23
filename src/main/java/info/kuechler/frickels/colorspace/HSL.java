package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class HSL {
    private final double[] fdata;
    private final RGBColorSpace colorSpace;

    public static HSL fromRGB(final RGB rgb) {
        final double R = rgb.getR();
        final double G = rgb.getG();
        final double B = rgb.getB();

        final double min = Math.min(R, Math.min(G, B));
        final double max = Math.max(R, Math.max(G, B));
        final double _ΔMax = max - min;

        final double L = (max + min) / 2.;

        if (_ΔMax == 0.) {
            return new HSL(rgb.getColorSpace(), 0., 0., L);
        }

        final double S;
        if (max == 0. || min == 1.) {
            S = 0.;
        } else {
            S = _ΔMax / (1. - Math.abs(max + min - 1.));
        }

        final double _ΔColor;
        if (min == max) {
            _ΔColor = 0.;
        } else if (R == max) {
            _ΔColor = (G - B) / _ΔMax;
        } else if (G == max) {
            _ΔColor = (B - R) / _ΔMax + 2.;
        } else { // if (B == max)
            _ΔColor = (R - G) / _ΔMax + 4.;
        }
        final double H = (_ΔColor < 0.) ? 360. + 60. * _ΔColor : 60. * _ΔColor;

        return new HSL(rgb.getColorSpace(), H, S, L);
    }

    public RGB toRGB() {
        final double H = getH();
        final double S = getS();
        final double L = getL();
        if (S == 0.) {
            return new RGB(colorSpace, L, L, L);
        }
        final double h_60 = H / 60.;
        final int hi = (int) Math.floor(h_60);
        final double C = (1. - Math.abs(2. * L - 1.)) * S;
        final double X = C * (1. - Math.abs(h_60 % 2 - 1.));
        final double m = L - C / 2.;

        switch (hi) {
        case 0:
        default:
            return new RGB(colorSpace, C + m, X + m, m);
        case 1:
            return new RGB(colorSpace, X + m, C + m, m);
        case 2:
            return new RGB(colorSpace, m, C + m, X + m);
        case 3:
            return new RGB(colorSpace, m, X + m, C + m);
        case 4:
            return new RGB(colorSpace, X + m, m, C + m);
        case 5:
            return new RGB(colorSpace, C + m, m, X + m);
        }
    }

    public static HSL fromGradAnd0To100(final RGBColorSpace colorSpace, final double h, final double s,
            final double l) {
        return new HSL(colorSpace, h, s / 100., l / 100.);
    }

    public HSL(final RGBColorSpace colorSpace, final double H, final double S, final double L) {
        this.fdata = new double[] { H, S, L };
        this.colorSpace = colorSpace;
    }

    public double getH() {
        return fdata[0];
    }

    public double getS() {
        return fdata[1];
    }

    public double getL() {
        return fdata[2];
    }

    public double[] toDouble() {
        return toDoubleInternal().clone();
    }

    private double[] toDoubleInternal() {
        return fdata;
    }

    @Override
    public String toString() {
        return String.format("HSL [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        final HSL other = (HSL) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(colorSpace, other.colorSpace);
    }
}
