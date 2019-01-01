package info.kuechler.frickels.colorspace;

public class MatrixUtil {

    public static double[] multMatrix33Vec3(double[][] m, double[] d) {
        return new double[] { //
                m[0][0] * d[0] + m[0][1] * d[1] + m[0][2] * d[2], //
                m[1][0] * d[0] + m[1][1] * d[1] + m[1][2] * d[2], //
                m[2][0] * d[0] + m[2][1] * d[1] + m[2][2] * d[2] //
        };
    }

    public static double determinantMatrix33(double[][] m) {
        // http://www.mathwords.com/d/determinant.htm
        // https://de.wikipedia.org/wiki/Determinante
        // + extract one to reduce multiplies
        return m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) //
                + m[0][1] * (m[1][2] * m[2][0] - m[1][0] * m[2][2]) //
                + m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]) //
        ;
    }

    public static double[][] invertMatrix33(final double[][] m) {
        // http://www.mathwords.com/i/inverse_of_a_matrix.htm
        // 3. Adjoint method
        // = 1/determinant(A) * transpose(cofactor(A))
        //
        // cofactor
        // http://www.mathwords.com/c/cofactor_matrix.htm
        //
        // https://de.wikipedia.org/wiki/Adjunkte

        final double _1detA = 1. / determinantMatrix33(m);
        return new double[][] { //
                { //
                        _1detA * (m[2][2] * m[1][1] - m[2][1] * m[1][2]), //
                        -_1detA * (m[2][2] * m[0][1] - m[2][1] * m[0][2]), //
                        _1detA * (m[1][2] * m[0][1] - m[1][1] * m[0][2]) //
                }, { //
                        -_1detA * (m[2][2] * m[1][0] - m[2][0] * m[1][2]), //
                        _1detA * (m[2][2] * m[0][0] - m[2][0] * m[0][2]), //
                        -_1detA * (m[1][2] * m[0][0] - m[1][0] * m[0][2]) //
                }, { //
                        _1detA * (m[2][1] * m[1][0] - m[2][0] * m[1][1]), //
                        -_1detA * (m[2][1] * m[0][0] - m[2][0] * m[0][1]), //
                        _1detA * (m[1][1] * m[0][0] - m[1][0] * m[0][1]) //
                }//
        };
    }

    public static double[][] transposeMatrix33(final double[][] m) {
        return new double[][] { //
                { m[0][0], m[1][0], m[2][0] }, //
                { m[0][1], m[1][1], m[2][1] }, //
                { m[0][2], m[1][2], m[2][2] } //
        };
    }

    public static double euclideanDistance3(final double[] p1, final double[] p2) {
        final double _Δ0 = p1[0] - p2[0];
        final double _Δ1 = p1[1] - p2[1];
        final double _Δ2 = p1[2] - p2[2];
        return Math.sqrt((_Δ0 * _Δ0) + (_Δ1 * _Δ1) + (_Δ2 * _Δ2));
    }

    private MatrixUtil() {
        // nothing
    }
}
