package info.kuechler.frickels.colorspace;

public interface CIEColor {

    // 29^3 / 3^3
    double κ = 24389. / 27.;

    // 6^3 / 29^3
    double ϵ = 216. / 24389.;

    double[] toDouble();

    Illuminant getIlluminant();
}
