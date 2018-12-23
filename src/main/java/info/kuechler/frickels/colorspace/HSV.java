package info.kuechler.frickels.colorspace;

import java.util.Arrays;
import java.util.Objects;

public class HSV {
    private final double[] fdata;
    private final RGBColorSpace colorSpace;

    public static HSV fromRGB(final RGB rgb) {
        final double R = rgb.getR();
        final double G = rgb.getG();
        final double B = rgb.getB();

        final double min = Math.min(R, Math.min(G, B));
        final double max = Math.max(R, Math.max(G, B));
        final double _ΔMax = max - min;

        final double V = max;

        if (_ΔMax == 0.) {
            return new HSV(rgb.getColorSpace(), 0., 0., V);
        }

        final double S = _ΔMax / max;

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

        return new HSV(rgb.getColorSpace(), H, S, V);
    }

    public RGB toRGB() {
        final double H = getH();
        final double S = getS();
        final double V = getV();
        if (S == 0.) {
            return new RGB(colorSpace, V, V, V);
        }
        final double h_60 = H / 60.;
        final int hi = (int) Math.floor(h_60);
        final double f = h_60 - hi;
        final double p = V * (1 - S);
        final double t_or_q_fac = ((hi % 2) == 0) ? 1 - f : f;
        final double t_or_q = V * (1 - S * t_or_q_fac);
        switch (hi) {
        case 1:
            return new RGB(colorSpace, t_or_q, V, p);
        case 2:
            return new RGB(colorSpace, p, V, t_or_q);
        case 3:
            return new RGB(colorSpace, p, t_or_q, V);
        case 4:
            return new RGB(colorSpace, t_or_q, p, V);
        case 5:
            return new RGB(colorSpace, V, p, t_or_q);
        default: // 0,6
            return new RGB(colorSpace, V, t_or_q, p);
        }
    }

    public static HSV fromGradAnd0To100(final RGBColorSpace colorSpace, final double h, final double s, final double v) {
        return new HSV(colorSpace, h, s / 100., v / 100.);
    }

    public HSV(final RGBColorSpace colorSpace, final double H, final double S, final double V) {
        this.fdata = new double[] { H, S, V };
        this.colorSpace = colorSpace;
    }

    public double getH() {
        return fdata[0];
    }

    public double getS() {
        return fdata[1];
    }

    public double getV() {
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
        return String.format("HSV [%.10f, %.10f, %.10f]", fdata[0], fdata[1], fdata[2]);
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
        final HSV other = (HSV) obj;
        return Arrays.equals(fdata, other.fdata) && Objects.equals(colorSpace, other.colorSpace);
    }
}
