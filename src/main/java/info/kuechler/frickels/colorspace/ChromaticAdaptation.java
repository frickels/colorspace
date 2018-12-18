package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.matrix33mult31;

public class ChromaticAdaptation {

    public static final ChromaticAdaptation XYZ_SCALING = new ChromaticAdaptation(new double[][] { //
            { 1., 0., 0. }, //
            { 0., 1., 0. }, //
            { 0., 0., 1. } //
    });

    public static final ChromaticAdaptation BRADFORD = new ChromaticAdaptation(new double[][] { //
            { 0.8951, 0.2664, -0.1614 }, //
            { -0.7502, 1.7135, 0.0367 }, //
            { 0.0389, -0.0685, 1.0296 } //
    });

    public static final ChromaticAdaptation VON_KRIES = new ChromaticAdaptation(new double[][] { //
            { 0.40024, 0.7076, -0.08081 }, //
            { -0.2263, 1.16532, 0.0457 }, //
            { 0., 0., 0.9182200 } //
    });

    private final double[][] fdata;

    private final double[][] fdataReverse;

    public ChromaticAdaptation(final double[][] data) {
        this.fdata = data;
        this.fdataReverse = MatrixUtil.invert33(data);
    }

    public double[][] getData() {
        return fdata.clone();
    }

    public double[][] getDataReverse() {
        return fdataReverse.clone();
    }

    public static XYZ adapt(final XYZ xyz, final ChromaticAdaptation method, final Illuminant fromIlluminant,
            final Illuminant toIlluminant) {

        if (fromIlluminant == toIlluminant) { // same instance
            return xyz;
        }

        final double[] ργβSource = matrix33mult31(method.getData(), fromIlluminant.getXyy().toXYZ().toDouble());
        final double[] ργβDestination = matrix33mult31(method.getData(), toIlluminant.getXyy().toXYZ().toDouble());

        final double[] data2 = matrix33mult31(method.getData(), xyz.toDouble());
        final double[] data3 = new double[] { //
                data2[0] = data2[0] * (ργβDestination[0] / ργβSource[0]), //
                data2[1] = data2[1] * (ργβDestination[1] / ργβSource[1]), //
                data2[2] = data2[2] * (ργβDestination[2] / ργβSource[2]) //
        };
        final double[] data4 = matrix33mult31(method.getDataReverse(), data3);

        return new XYZ(data4[0], data4[1], data4[2]);
    }
}
