package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.matrix33mult31;

public class ChromaticAdaptation {
    public static XYZ adapt(final XYZ xyz, final ChromaticAdaptationMethod method, final Illuminant fromIlluminant,
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

    private ChromaticAdaptation() {
        // nothing
    }
}
