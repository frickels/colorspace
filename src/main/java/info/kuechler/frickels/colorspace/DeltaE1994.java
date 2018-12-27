package info.kuechler.frickels.colorspace;

public class DeltaE1994 {
    public static final double _κ1Graphic = 0.045;
    public static final double _κ1Texiles = 0.048;
    public static final double _κ2Graphic = 0.015;
    public static final double _κ2Textiles = 0.014;
    public static final double _κLDefault = 1.;
    public static final double _κLTextiles = 2.;

    public static final double _κC = 1.;
    public static final double _κH = 1.;

    public static double calculateTextiles(final LAB lab1, final LAB lab2) {
        return calculate(lab1, lab2, _κLTextiles, _κ1Texiles, _κ2Textiles);
    }

    public static double calculateGraphicArts(final LAB lab1, final LAB lab2) {
        return calculate(lab1, lab2, _κLDefault, _κ1Graphic, _κ2Graphic);
    }

    public static double calculate(final LAB lab1, final LAB lab2, final double _κL, final double _κ1,
            final double _κ2) {
        final double L1 = lab1.getL();
        final double a1 = lab1.getA();
        final double b1 = lab1.getB();
        final double L2 = lab2.getL();
        final double a2 = lab2.getA();
        final double b2 = lab2.getB();

        final double C1 = Math.sqrt(a1 * a1 + b1 * b1);
        final double C2 = Math.sqrt(a2 * a2 + b2 * b2);

        final double sL = 1.;
        final double sC = 1. + _κ1 * C1;
        final double sH = 1. + _κ2 * C1;

        final double _ΔL = L1 - L2;
        final double _Δa = a1 - a2;
        final double _Δb = b1 - b2;
        final double _ΔC = C1 - C2;

        final double _ΔH = _Δa * _Δa + _Δb * _Δb - _ΔC * _ΔC; // w/o Math.sqrt

        final double f1 = _ΔL / (_κL * sL);
        final double f2 = _ΔC / (_κC * sC);
        final double f3_special = _ΔH / (_κH * _κH * sH * sH);

        return Math.sqrt(f1 * f1 + f2 * f2 + f3_special);
    }

    private DeltaE1994() {
        // nothing
    }
}
