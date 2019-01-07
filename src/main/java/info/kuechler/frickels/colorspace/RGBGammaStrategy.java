package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.CIEColor.κ;
import static info.kuechler.frickels.colorspace.CIEColor.ϵ;
import static info.kuechler.frickels.colorspace.RGBGammaStrategy.RGBValueAdjusterOperator.splitToSingle;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RGBGammaStrategy implements Serializable, Cloneable {

    private static final long serialVersionUID = -7628671151496518075L;

    private static final ConcurrentMap<String, RGBValueAdjusterOperator> OPERATORS = new ConcurrentHashMap<>();

    @FunctionalInterface
    public interface RGBValueSingleAdjusterOperator {
        double adjust(RGBColorSpace colorSpaces, double operand);
    }

    @FunctionalInterface
    public interface RGBValueAdjusterOperator {
        double[] adjust(RGBColorSpace colorSpaces, double[] operand);

        static RGBValueAdjusterOperator splitToSingle(final RGBValueSingleAdjusterOperator op) {
            return (cs, fa) -> {
                return new double[] { //
                        op.adjust(cs, fa[0]), //
                        op.adjust(cs, fa[1]), //
                        op.adjust(cs, fa[2])//
                };
            };
        }
    }

    private static final double CONVERT_SRGB_GAMMA = 2.4;

    private static final double CONVERT_SRGB_OFFSET = 0.055;

    private static final double CONVERT_SRGB_SLOPE = 12.92321;

    public static final RGBGammaStrategy XYZ_SRGB = new RGBGammaStrategy("XYZ_SRGB", //
            splitToSingle((cs, f) -> {
                double correct = 1.;
                if (f < 0.) {
                    f = -f;
                    correct = -1.;
                }
                // https://de.wikipedia.org/wiki/RGB-Farbraum
                if (f <= 0.0031306684425) {
                    return correct * (f * CONVERT_SRGB_SLOPE);
                }
                return correct
                        * ((1. + CONVERT_SRGB_OFFSET) * Math.pow(f, 1. / CONVERT_SRGB_GAMMA) - CONVERT_SRGB_OFFSET);
            }), splitToSingle((cs, f) -> {
                double correct = 1.;
                if (f < 0.) {
                    f = -f;
                    correct = -1.;
                }
                if (f <= 0.040448236277) {
                    return correct * (f / CONVERT_SRGB_SLOPE);
                }
                final double a = (f + CONVERT_SRGB_OFFSET) / (1. + CONVERT_SRGB_OFFSET);
                return correct * Math.pow(a, CONVERT_SRGB_GAMMA);
            })//
    );

    public static final RGBGammaStrategy XYZ_RGB_STD = new RGBGammaStrategy("XYZ_RGB_STD", //
            splitToSingle((cs, f) -> {
                final double gammaFac = 1. / cs.getGamma();
                return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
            }), splitToSingle((cs, f) -> {
                final double gammaFac = cs.getGamma();
                return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
            })//
    );

    public static final RGBGammaStrategy NONE = new RGBGammaStrategy("NONE", //
            (cs, fa) -> fa, //
            (cs, fa) -> fa//
    );

    public static final RGBGammaStrategy PHOTO_PRO = new RGBGammaStrategy("NONE", //
            splitToSingle((cs, f) -> {
                if (f <= 1. / 512.) {
                    return 16. * f;
                }
                final double gammaFac = 1. / cs.getGamma();
                return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
            }), splitToSingle((cs, f) -> {
                if (f <= 1. / 32.) {
                    return f / 16.;
                }
                final double gammaFac = cs.getGamma();
                return (f >= 0.) ? Math.pow(f, gammaFac) : -Math.pow(-f, gammaFac);
            })//
    );

    public static final RGBGammaStrategy XYZ_RGB_L = new RGBGammaStrategy("XYZ_RGB_L", //
            splitToSingle((cs, f) -> {
                // http://www.brucelindbloom.com/
                double correct = 1.;
                if (f < 0.) {
                    f = -f;
                    correct = -1.;
                }
                if (f <= ϵ) {
                    return correct * f * κ / 100.;
                }
                return correct * 1.16 * Math.cbrt(f) - 0.16;
            }), splitToSingle((cs, f) -> {
                // http://www.brucelindbloom.com/
                double correct = 1.;
                if (f < 0.) {
                    f = -f;
                    correct = -1.;
                }
                if (f <= 0.08) {
                    return correct * f * 100. * κ;
                }
                return correct * Math.pow(((f + 0.16) / 1.16), 3);
            })//
    );

    private final String id;
    private transient final RGBValueAdjusterOperator operator;
    private transient final RGBValueAdjusterOperator reverseOperator;

    public RGBGammaStrategy(final String id, final RGBValueAdjusterOperator operator,
            final RGBValueAdjusterOperator reverseOperator) {
        OPERATORS.put(id, operator);
        OPERATORS.put(createReverseId(id), reverseOperator);
        this.operator = operator;
        this.reverseOperator = reverseOperator;
        this.id = id;
    }

    private String createReverseId(final String id) {
        return id + "_reverse";
    }

    public double[] adjust(final RGBColorSpace colorSpace, final double[] d) {
        return operator.adjust(colorSpace, d);
    }

    public double[] reverseAdjust(final RGBColorSpace colorSpace, final double[] doubles) {
        return reverseOperator.adjust(colorSpace, doubles);
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
        final RGBGammaStrategy other = (RGBGammaStrategy) obj;
        return Objects.equals(id, other.id);
    }

    public Object readResolve() {
        if (!OPERATORS.containsKey(id)) {
            throw new RuntimeException("Cannot read, operator is not registered: " + id);
        }
        if (!OPERATORS.containsKey(createReverseId(id))) {
            throw new RuntimeException("Cannot read, reverse operator is not registered: " + id);
        }
        return new RGBGammaStrategy(id, OPERATORS.get(id), OPERATORS.get(createReverseId(id)));
    }

    @Override
    public RGBGammaStrategy clone() {
        return new RGBGammaStrategy(id, operator, reverseOperator);
    }
}
