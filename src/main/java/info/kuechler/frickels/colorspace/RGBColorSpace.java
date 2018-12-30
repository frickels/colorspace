package info.kuechler.frickels.colorspace;

import java.io.Serializable;

public interface RGBColorSpace extends Serializable {
    RGB fromXYZ(final XYZ xyz);

    XYZ toXYZ(final RGB rgb);

    Illuminant getIlluminant();
    
    double getGamma();
}
