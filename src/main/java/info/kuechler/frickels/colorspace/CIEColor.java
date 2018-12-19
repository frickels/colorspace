package info.kuechler.frickels.colorspace;

public interface CIEColor {
    double[] toDouble();

    Illuminant getIlluminant();
}
