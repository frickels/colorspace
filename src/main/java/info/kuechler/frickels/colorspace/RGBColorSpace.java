package info.kuechler.frickels.colorspace;

public interface RGBColorSpace {
    RGB fromXYZ(final XYZ xyz);

    XYZ toXYZ(final RGB rgb);

    Illuminant getIlluminant();
    
    double getGamma();
}
