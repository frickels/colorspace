package info.kuechler.frickels.colorspace;

public class MatrixUtil {

    public static double[] matrix33mult31(double[][] m, double[] d) {
        return new double[] { //
                m[0][0] * d[0] + m[0][1] * d[1] + m[0][2] * d[2], //
                m[1][0] * d[0] + m[1][1] * d[1] + m[1][2] * d[2], //
                m[2][0] * d[0] + m[2][1] * d[1] + m[2][2] * d[2] //
        };
    }

    private MatrixUtil() {
        // nothing
    }
}
