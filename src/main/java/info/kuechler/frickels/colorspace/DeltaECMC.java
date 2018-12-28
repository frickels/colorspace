package info.kuechler.frickels.colorspace;

// http://www.brucelindbloom.com/index.html?Eqn_DeltaE_CMC.html
// http://colormine.org/delta-e-calculator/Cmc
public class DeltaECMC {

    // Line numbers based on www.brucelindbloom.com
    public static double calculate(final LAB lab1, final LAB lab2, final double l, final double c) {
        final double L1 = lab1.getL();
        final double a1 = lab1.getA();
        final double b1 = lab1.getB();
        final double L2 = lab2.getL();
        final double a2 = lab2.getA();
        final double b2 = lab2.getB();

        // (3)
        final double C1 = Math.sqrt(a1 * a1 + b1 * b1);

        // (4)
        final double C2 = Math.sqrt(a2 * a2 + b2 * b2);

        // (2)
        final double _ΔC = C1 - C2;

        // (6)
        final double _ΔL = L1 - L2;

        // (7)
        final double _Δa = a1 - a2;

        // (8)
        final double _Δb = b1 - b2;

        // (9)
        final double SL;
        if (L1 < 16.) {
            SL = 0.511;
        } else {
            SL = (0.040975 * L1) / (1. + 0.01765 * L1);
        }

        // (10)
        final double SC = (0.0638 * C1) / (1. + 0.0131 * C1) + 0.638;

        // (14)
        // radian to degree
        final double H = Math.toDegrees(Math.atan2(b1, a1));

        // (15)
        final double H1;
        if (H >= 0.) {
            H1 = H;
        } else {
            H1 = H + 360.;
        }

        // (12)
        // degree to radian
        final double T;
        if (164. <= H && H1 <= 345.) {
            T = 0.56 + Math.abs(0.2 * Math.cos(Math.toRadians(H1 + 168.)));
        } else {
            T = 0.36 + Math.abs(0.4 * Math.cos(Math.toRadians(H1 + 35.)));
        }

        // (13)
        final double FPre = Math.pow(C1, 4.);
        final double F = Math.sqrt(FPre / (FPre + 1900.));

        // (11)
        final double SH = SC * (F * T + 1. - F);

        // (5)
        // no sqrt
        final double _ΔH = _Δa * _Δa + _Δb * _Δb - _ΔC * _ΔC;

        // (1)
        final double f1 = _ΔL / (l * SL);
        final double f2 = _ΔC / (c * SC);
        final double f3Special = _ΔH / (SH * SH); // resolve with sqrt
        return Math.sqrt(f1 * f1 + f2 * f2 + f3Special);
    }

    private DeltaECMC() {
        // nothing
    }
}
