package info.kuechler.frickels.colorspace;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RGBConversionAdjuster implements Serializable, Cloneable {

    private static final long serialVersionUID = -7628671151496518075L;

    private static final ConcurrentMap<String, RGBValueAdjusterOperator> OPERATORS = new ConcurrentHashMap<>();

    @FunctionalInterface
    public interface RGBValueAdjusterOperator {
        double adjust(RGBColorSpace colorSpace, double operand);
    }

    private static final double CONVERT_SRGB_GAMMA = 2.4;

    private static final double CONVERT_SRGB_OFFSET = 0.055;

    private static final double CONVERT_SRGB_SLOPE = 12.92321;

    public static final RGBConversionAdjuster XYZ_SRGB = new RGBConversionAdjuster("XYZ_SRGB", (cs, f) -> {
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.0031308) {
            return correct * (f * CONVERT_SRGB_SLOPE);
        }
        return correct * ((1. + CONVERT_SRGB_OFFSET) * Math.pow(f, 1. / CONVERT_SRGB_GAMMA) - CONVERT_SRGB_OFFSET);
    });

    public static final RGBConversionAdjuster SRGB_XYZ = new RGBConversionAdjuster("SRGB_XYZ", (cs, f) -> {
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.04045) {
            return correct * (f / CONVERT_SRGB_SLOPE);
        }
        final double a = (f + CONVERT_SRGB_OFFSET) / (1. + CONVERT_SRGB_OFFSET);
        return correct * Math.pow(a, CONVERT_SRGB_GAMMA);
    });

    public static final RGBConversionAdjuster XYZ_RGB_STD = new RGBConversionAdjuster("XYZ_RGB_STD", (cs, f) -> {
        final double gammaFac = 1. / cs.getGamma();
        return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
    });

    public static final RGBConversionAdjuster RGB_XYZ_STD = new RGBConversionAdjuster("RGB_XYZ_STD", (cs, f) -> {
        final double gammaFac = cs.getGamma();
        return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
    });

    public static final RGBConversionAdjuster XYZ_RGB_L = new RGBConversionAdjuster("XYZ_RGB_L", (cs, f) -> {
        // http://www.brucelindbloom.com/index.html?Eqn_XYZ_to_RGB.html
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= CIEColor.ϵ) {
            return correct * f * CIEColor.κ / 100.;
        }
        return correct * 1.16 * Math.cbrt(f) - 0.16;
    });

    public static final RGBConversionAdjuster RGB_XYZ_L = new RGBConversionAdjuster("RGB_XYZ_L", (cs, f) -> {
        // http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
        double correct = 1.;
        if (f < 0.) {
            f = -f;
            correct = -1.;
        }
        if (f <= 0.08) {
            return correct * f * 100. * CIEColor.κ;
        }
        return correct * Math.pow(((f + 0.16) / 1.16), 3);
    });

    private final String id;
    private transient final RGBValueAdjusterOperator operator;

    public RGBConversionAdjuster(final String id, final RGBValueAdjusterOperator operator) {
        OPERATORS.put(id, operator);
        this.operator = operator;
        this.id = id;
    }

    public double adjust(final RGBColorSpace colorSpace, final double d) {
        return operator.adjust(colorSpace, d);
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        final RGBConversionAdjuster other = (RGBConversionAdjuster) obj;
        return Objects.equals(id, other.id);
    }

    public Object readResolve() {
        if (!OPERATORS.containsKey(id)) {
            throw new RuntimeException("Cannot read, operator is not registered: " + id);
        }
        return new RGBConversionAdjuster(id, OPERATORS.get(id));
    }

    @Override
    public RGBConversionAdjuster clone() {
        return new RGBConversionAdjuster(id, operator);
    }
}
