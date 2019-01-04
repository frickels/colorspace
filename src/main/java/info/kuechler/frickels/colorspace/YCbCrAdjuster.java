package info.kuechler.frickels.colorspace;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class YCbCrAdjuster implements Serializable, Cloneable {

    /**
     * 255 steps with 0 to 15 footroom and from 236 to 255 headroom.
     */
    public static final YCbCrAdjuster ITU_R = new YCbCrAdjuster("ITU_R", d -> new double[] { //
            219. * d[0] + 16., //
            224. * d[1] + 128., //
            224. * d[2] + 128. //
    }, d -> new double[] { //
            (d[0] - 16.) / 219., //
            (d[1] - 128.) / 224., //
            (d[2] - 128.) / 224. //
    });

    /**
     * 255 steps, full usage
     */
    public static final YCbCrAdjuster NONE = new YCbCrAdjuster("NONE", d -> new double[] { //
            255. * d[0], //
            255. * d[1] + 128., //
            255. * d[2] + 128. //
    }, d -> new double[] { //
            d[0] / 255., //
            (d[1] - 128.) / 255., //
            (d[2] - 128.) / 255. //
    });

    private static final long serialVersionUID = -9184127472581659998L;

    private static final ConcurrentMap<String, YCbCrAdjusterOperator> OPERATORS = new ConcurrentHashMap<>();

    @FunctionalInterface
    public interface YCbCrAdjusterOperator {
        double[] adjust(final double[] doubles);
    }

    private final String id;
    private transient final YCbCrAdjusterOperator operator;
    private transient final YCbCrAdjusterOperator reverseOperator;

    public YCbCrAdjuster(final String id, final YCbCrAdjusterOperator operator,
            final YCbCrAdjusterOperator reverseOperator) {
        OPERATORS.put(id, operator);
        OPERATORS.put(createReverseId(id), reverseOperator);
        this.operator = operator;
        this.reverseOperator = reverseOperator;
        this.id = id;
    }

    private String createReverseId(final String id) {
        return id + "_reverse";
    }

    public double[] adjust(final double[] doubles) {
        return operator.adjust(doubles);
    }

    public double[] reverseAdjust(final double[] doubles) {
        return reverseAdjust(doubles);
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
        final YCbCrAdjuster other = (YCbCrAdjuster) obj;
        return Objects.equals(id, other.id);
    }

    public Object readResolve() {
        if (!OPERATORS.containsKey(id)) {
            throw new RuntimeException("Cannot read, operator is not registered: " + id);
        }
        if (!OPERATORS.containsKey(createReverseId(id))) {
            throw new RuntimeException("Cannot read, reverse operator is not registered: " + id);
        }
        return new YCbCrAdjuster(id, OPERATORS.get(id), OPERATORS.get(createReverseId(id)));
    }

    @Override
    public YCbCrAdjuster clone() {
        return new YCbCrAdjuster(id, operator, reverseOperator);
    }
}
