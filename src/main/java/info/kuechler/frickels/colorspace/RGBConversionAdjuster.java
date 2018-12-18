package info.kuechler.frickels.colorspace;

public class RGBConversionAdjuster {

    @FunctionalInterface
    public interface RGBValueAdjusterOperator {
        double adjust(RGBColorSpace colorSpace, double operand);
    }

    private final static double CONVERT_SRGB_A = 0.055;

    private final static double CONVERT_SRGB_PHI = 12.92321;

    public static final RGBConversionAdjuster XYZ_SRGB = new RGBConversionAdjuster((cs, f) -> {
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.0031308) {
            return correct * (f * CONVERT_SRGB_PHI);
        }
        return correct * ((1. + CONVERT_SRGB_A) * Math.pow(f, 1. / cs.getGamma()) - CONVERT_SRGB_A);
    });

    public static final RGBConversionAdjuster SRGB_XYZ = new RGBConversionAdjuster((cs, f) -> {
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.04045) {
            return correct * (f / CONVERT_SRGB_PHI);
        }
        return correct * (Math.pow((f + CONVERT_SRGB_A) / (1. + CONVERT_SRGB_A), cs.getGamma()));
    });

    public static final RGBConversionAdjuster XYZ_RGB_LINEAR = new RGBConversionAdjuster((cs, f) -> {
        final double gammaFac = 1. / cs.getGamma();
        return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
    });

    public static final RGBConversionAdjuster RGB_XYZ_LINEAR = new RGBConversionAdjuster((cs, f) -> {
        final double gammaFac = cs.getGamma();
        return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
    });

    private final RGBValueAdjusterOperator operator;

    public RGBConversionAdjuster(final RGBValueAdjusterOperator operator) {
        this.operator = operator;
    }

    public double adjust(final RGBColorSpace colorSpace, final double d) {
        return operator.adjust(colorSpace, d);
    }
}
