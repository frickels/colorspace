package info.kuechler.frickels.colorspace;

// http://www.brucelindbloom.com/Eqn_DeltaE_CIE2000.html
// http://colormine.org/delta-e-calculator/Cie2000
// Line numbers based on www.brucelindbloom.com
public class DeltaE2000 {
    private static final double pow25_7 = Math.pow(25., 7.);
    public static final double _κL = 1.;
    public static final double _κC = 1.;
    public static final double _κH = 1.;

    public static double calculate(final LAB lab1, final LAB lab2) {
        final double L1 = lab1.getL();
        final double a1 = lab1.getA();
        final double b1 = lab1.getB();
        final double L2 = lab2.getL();
        final double a2 = lab2.getA();
        final double b2 = lab2.getB();

        // (2)
        final double Lxs = (L1 + L2) / 2.;

        // (3)
        final double C1 = Math.sqrt(a1 * a1 + b1 * b1);

        // (4)
        final double C2 = Math.sqrt(a2 * a2 + b2 * b2);

        // (5)
        final double Cx = (C1 + C2) / 2.;

        // (6)
        final double GPre = Math.pow(Cx, 7.);
        final double G = 0.5 * (1. - Math.sqrt(GPre / (GPre + pow25_7)));

        // (7)
        final double as1 = a1 * (1. + G);

        // (8)
        final double as2 = a2 * (1. + G);

        // (9)
        final double Cs1 = Math.sqrt(as1 * as1 + b1 * b1);

        // (10)
        final double Cs2 = Math.sqrt(as2 * as2 + b2 * b2);

        // (11)
        final double Cxs = (Cs1 + Cs2) / 2.;

        // (12)
        final double hs1Pre = Math.toDegrees(Math.atan2(b1, as1));
        final double hs1 = hs1Pre < 0. ? hs1Pre + 360. : hs1Pre;

        // (13)
        // from radian to degree
        final double hs2Pre = Math.toDegrees(Math.atan2(b2, as2));
        final double hs2 = hs2Pre < 0. ? hs2Pre + 360. : hs2Pre;

        // (14)
        final double HxsPre = Math.abs(hs1 - hs2) > 180. ? 360. : 0.;
        final double Hxs = (hs1 + hs2 + HxsPre) / 2.;

        // (15)
        // degree to radian
        final double T = 1. - 0.17 * Math.cos(Math.toRadians(Hxs - 30.)) + 0.24 * Math.cos(Math.toRadians(2. * Hxs))
                + 0.32 * Math.cos(Math.toRadians(3. * Hxs + 6.)) - 0.20 * Math.cos(Math.toRadians(4. * Hxs - 63.));

        // (16)
        final double _Δhs1Hs2 = hs2 - hs1;
        final double _Δhs1Hs2Abs = Math.abs(_Δhs1Hs2);
        final double _Δhs;
        if (_Δhs1Hs2Abs <= 180.) {
            _Δhs = _Δhs1Hs2;
        } else if (hs2 <= hs1) {
            _Δhs = _Δhs1Hs2 + 360.;
        } else {
            _Δhs = _Δhs1Hs2 - 360.;
        }

        // (17)
        final double _ΔLs = L2 - L1;

        // (18)
        final double _ΔCs = Cs2 - Cs1;

        // (19)
        final double _ΔHs = 2. * Math.sqrt(Cs1 * Cs2) * Math.sin(Math.toRadians(_Δhs / 2.));

        // (20)
        final double SLPre = Lxs - 50.;
        final double SLPre2 = SLPre * SLPre;
        final double SL = 1. + ((0.015 * SLPre2) / Math.sqrt(20. + SLPre2));

        // (21)
        final double SC = 1. + 0.045 * Cxs;

        // (22)
        final double SH = 1. + 0.015 * Cxs * T;

        // (23)
        final double _ΔΘPre = (Hxs - 275.) / 25.;
        final double _ΔΘ = 30. * Math.exp(-_ΔΘPre * _ΔΘPre);

        // (24)
        final double RCPre = Math.pow(Cxs, 7.);
        final double RC = 2. * Math.sqrt(RCPre / (RCPre + pow25_7));

        // (25)
        // degree to radian
        final double RT = -RC * Math.sin(Math.toRadians(2. * _ΔΘ));

        // (26) (27) (28)
        // see constants

        // (1)
        final double f1 = _ΔLs / (_κL * SL);
        final double f2 = _ΔCs / (_κC * SC);
        final double f3 = _ΔHs / (_κH * SH);
        final double f4 = RT * f2 * f3;
        return Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3 + f4);
    }

    private DeltaE2000() {
        // nothing
    }
}
