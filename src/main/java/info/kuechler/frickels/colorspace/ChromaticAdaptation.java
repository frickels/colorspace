package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.determinantMatrix33;
import static info.kuechler.frickels.colorspace.MatrixUtil.multMatrix33Vec3;

import java.util.Objects;

public class ChromaticAdaptation implements Cloneable {

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
    private final double determinatMatrix;

    public ChromaticAdaptation(final double[][] data) {
        this.fdata = data;
        this.fdataReverse = MatrixUtil.invertMatrix33(data);
        this.determinatMatrix = determinantMatrix33(this.fdata);
    }

    public XYZ adapt(final XYZ xyz, final Illuminant toIlluminant) {
        if (xyz.getIlluminant().equals(toIlluminant)) {
            return xyz;
        }

        final double[] ργβSource = multMatrix33Vec3(getData(), xyz.getIlluminant().getXyy().toXYZ().toDouble());
        final double[] ργβDestination = multMatrix33Vec3(getData(), toIlluminant.getXyy().toXYZ().toDouble());

        final double[] data2 = multMatrix33Vec3(getData(), xyz.toDouble());
        final double[] data3 = new double[] { //
                data2[0] *= (ργβDestination[0] / ργβSource[0]), //
                data2[1] *= (ργβDestination[1] / ργβSource[1]), //
                data2[2] *= (ργβDestination[2] / ργβSource[2]) //
        };
        final double[] data4 = multMatrix33Vec3(getDataReverse(), data3);

        return new XYZ(toIlluminant, data4[0], data4[1], data4[2]);
    }

    public double[][] getData() {
        return fdata.clone();
    }

    public double[][] getDataReverse() {
        return fdataReverse.clone();
    }

    @Override
    public int hashCode() {
        return Objects.hash(determinatMatrix);
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
        final ChromaticAdaptation other = (ChromaticAdaptation) obj;
        return Double.doubleToLongBits(determinatMatrix) == Double.doubleToLongBits(other.determinatMatrix);
    }

    @Override
    public ChromaticAdaptation clone() {
        return new ChromaticAdaptation(fdata.clone());
    }
}
