package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.MatrixUtil.invertMatrix33;
import static info.kuechler.frickels.colorspace.MatrixUtil.multMatrix33Vec3;
import static info.kuechler.frickels.colorspace.MatrixUtil.transposeMatrix33;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class RGBColorSpaceImpl implements RGBColorSpace, Cloneable {
    private static final long serialVersionUID = 4632087745617854955L;

    private final XYY xr;
    private final XYY xg;
    private final XYY xb;
    private final Illuminant illuminant;
    private final double gamma;
    private final RGBConversionAdjuster valueAdjuster;
    private final RGBConversionAdjuster valueReverseAdjuster;
    private transient double[][] transformationMatrix;
    private transient double[][] reverseTransformationMatrix;
    /** replaces (reverse) transformation matrix in {@link #equals(Object)} and {@link #hashCode()} method. */
    private transient double determinatTransformationMatrix;

    public RGBColorSpaceImpl(final XYY xr, final XYY xg, final XYY xb, final Illuminant illuminant, final double gamma,
            RGBConversionAdjuster valueAdjuster, RGBConversionAdjuster valueReverseAdjuster) {
        this.xr = xr;
        this.xg = xg;
        this.xb = xb;
        this.illuminant = illuminant;
        this.gamma = gamma;
        this.valueAdjuster = valueAdjuster;
        this.valueReverseAdjuster = valueReverseAdjuster;
        init();
    }

    private /* final */ void init() {
        this.transformationMatrix = calculateTransformationMatrix(xr, xg, xb, illuminant);
        this.reverseTransformationMatrix = invertMatrix33(this.transformationMatrix);
        this.determinatTransformationMatrix = MatrixUtil.determinantMatrix33(this.transformationMatrix);
    }

    @Override
    public RGB fromXYZ(final XYZ xyz) {
        final XYZ xyzIlluminated = xyz.changeIlluminant(getIlluminant());
        final double[] data = xyzIlluminated.toDouble();
        final double[] convert = multMatrix33Vec3(getReverseTransformationMatrix(), data);
        final RGBConversionAdjuster adjuster = getValueAdjuster();
        return new RGB(this, adjuster.adjust(this, convert[0]), adjuster.adjust(this, convert[1]),
                adjuster.adjust(this, convert[2]));
    }

    @Override
    public XYZ toXYZ(final RGB rgb) {
        final double[] data = rgb.toDouble();
        final RGBConversionAdjuster adjuster = getValueReverseAdjuster();
        final double[] dataAdj = new double[] { adjuster.adjust(this, data[0]), adjuster.adjust(this, data[1]),
                adjuster.adjust(this, data[2]) };
        final double[] convert = multMatrix33Vec3(getTransformationMatrix(), dataAdj);
        return new XYZ(getIlluminant(), convert[0], convert[1], convert[2]);
    }

    private double[][] calculateTransformationMatrix(final XYY xr, final XYY xg, final XYY xb,
            final Illuminant refWhite) {
        final double[][] m = transposeMatrix33(new double[][] { //
                Yto1(xr).toXYZ().toDouble(), //
                Yto1(xg).toXYZ().toDouble(), //
                Yto1(xb).toXYZ().toDouble()//
        });
        final double[] s = multMatrix33Vec3(invertMatrix33(m), refWhite.getXyy().toXYZ().toDouble());

        return new double[][] { //
                { s[0] * m[0][0], s[1] * m[0][1], s[2] * m[0][2] }, //
                { s[0] * m[1][0], s[1] * m[1][1], s[2] * m[1][2] }, //
                { s[0] * m[2][0], s[1] * m[2][1], s[2] * m[2][2] } //
        };
    }

    private XYY Yto1(XYY xyy) {
        final double[] m = xyy.toDouble();
        return new XYY(xyy.getIlluminant(), m[0], m[1], 1.);
    }

    public XYY getXr() {
        return xr;
    }

    public XYY getXg() {
        return xg;
    }

    public XYY getXb() {
        return xb;
    }

    @Override
    public Illuminant getIlluminant() {
        return illuminant;
    }

    @Override
    public double getGamma() {
        return gamma;
    }

    public RGBConversionAdjuster getValueAdjuster() {
        return valueAdjuster;
    }

    public RGBConversionAdjuster getValueReverseAdjuster() {
        return valueReverseAdjuster;
    }

    public double[][] getTransformationMatrix() {
        return transformationMatrix;
    }

    public double[][] getReverseTransformationMatrix() {
        return reverseTransformationMatrix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(determinatTransformationMatrix, gamma, valueAdjuster, valueReverseAdjuster);
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
        final RGBColorSpaceImpl other = (RGBColorSpaceImpl) obj;
        return Double.doubleToLongBits(determinatTransformationMatrix) == Double
                .doubleToLongBits(other.determinatTransformationMatrix)
                && Double.doubleToLongBits(gamma) == Double.doubleToLongBits(other.gamma)
                && Objects.equals(valueAdjuster, other.valueAdjuster)
                && Objects.equals(valueReverseAdjuster, other.valueReverseAdjuster);
    }

    private void readObject(final ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        init();
    }

    @Override
    public RGBColorSpaceImpl clone() {
        return new RGBColorSpaceImpl(xr, xg, xb, illuminant, gamma, valueAdjuster, valueReverseAdjuster);
    }
}
