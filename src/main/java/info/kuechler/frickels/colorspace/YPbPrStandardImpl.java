package info.kuechler.frickels.colorspace;

import java.io.IOException;
import java.io.ObjectInputStream;

public class YPbPrStandardImpl implements YPbPrStandard, Cloneable {

    private static final long serialVersionUID = 6095211812516664940L;

    private final double Kb;
    private final double Kr;
    private transient double[][] transformationMatrix;
    private transient double[][] reverseTransformationMatrix;

    public YPbPrStandardImpl(final double Kb, final double Kr) {
        this.Kb = Kb;
        this.Kr = Kr;
        init();
    }

    private void init() {
        this.transformationMatrix = preCalculateMatrix(this.Kb, this.Kr);
        this.reverseTransformationMatrix = MatrixUtil.invertMatrix33(this.transformationMatrix);
    }

    private static double[][] preCalculateMatrix(final double kb, final double kr) {
        final double kg = (1. - kr - kb);

        final double divPb = (2. - kb - kb);
        final double divPr = (2. - kr - kr);
        return new double[][] { //
                { kr, kg, kb }, //
                { -kr / divPb, -kg / divPb, (1. - kb) / divPb }, //
                { (1. - kr) / divPr, -kg / divPr, -kb / divPr } //
        };
    }

    public double[][] getTransformationMatrix() {
        return transformationMatrix;
    }

    public double[][] getReverseTransformationMatrix() {
        return reverseTransformationMatrix;
    }

    @Override
    public YPbPr fromRGB(final RGB rgb) {
        final double[] d = MatrixUtil.multMatrix33Vec3(getTransformationMatrix(), rgb.toDouble());
        return new YPbPr(this, d[0], d[1], d[2]);
    }

    @Override
    public RGB toRGB(final RGBColorSpace colorSpace, final YPbPr yPbPr) {
        final double d[] = MatrixUtil.multMatrix33Vec3(getReverseTransformationMatrix(), yPbPr.toDouble());
        return new RGB(colorSpace, d[0], d[1], d[2]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(Kb);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Kr);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        YPbPrStandardImpl other = (YPbPrStandardImpl) obj;
        if (Double.doubleToLongBits(Kb) != Double.doubleToLongBits(other.Kb))
            return false;
        if (Double.doubleToLongBits(Kr) != Double.doubleToLongBits(other.Kr))
            return false;
        return true;
    }

    private void readObject(final ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        init();
    }

    @Override
    public YPbPrStandardImpl clone() {
        return new YPbPrStandardImpl(Kb, Kr);
    }
}
