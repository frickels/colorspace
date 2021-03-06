package info.kuechler.frickels.colorspace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Assertions;

public class TestUtil {
    public static void assertDoubleArrayEquals(final double threshold, final double[][] expected,
            final double[][] actual) {
        Assertions.assertNotNull(expected, "expected not null");
        Assertions.assertNotNull(actual, "actual not null");
        Assertions.assertEquals(expected.length, actual.length, "Same length");
        for (int i = 0; i < expected.length; i++) {
            final double[] expectedSub = expected[i];
            final double[] actualSub = actual[i];
            assertDoubleArrayEquals(threshold, expectedSub, actualSub);
        }
    }

    public static void assertDoubleArrayEquals(final double threshold, final double[] expected, final double[] actual) {
        Assertions.assertNotNull(expected, "expected not null");
        Assertions.assertNotNull(actual, "actual not null");
        Assertions.assertEquals(expected.length, actual.length, "Same length");

        for (int i = 0; i < expected.length; i++) {
            final double expectedSub = expected[i];
            final double actualSub = actual[i];
            assertDoubleDiff(threshold, expectedSub, actualSub);
        }
    }

    public static void assertDoubleDiff(final double threshold, final double expected, final double actual) {
        final double diff = Math.abs(actual - expected);
        Assertions.assertTrue(diff < threshold, "Expected " + expected + " [" + Double.toString(threshold)
                + "] but was " + actual + " [" + Double.toString(diff) + "]");
    }

    public static String toString(double[][] m) {
        final StringBuilder bui = new StringBuilder();
        for (int i = 0; i < m.length; i++) {
            bui.append('[');
            for (int j = 0; j < m[i].length; j++) {
                bui.append(m[i][j]).append(", ");
            }
            bui.append("]\n");
        }
        return bui.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(final byte[] bytes) throws IOException, ClassNotFoundException {
        try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                final ObjectInputStream ois = new ObjectInputStream(bais);) {
            return (T) ois.readObject();
        }
    }

    public static byte[] serialize(final Object object) throws IOException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();
        }
    }

    private TestUtil() {
        // nothing
    }
}
