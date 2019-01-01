package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixUtilTest {
    @Test
    public final void testTranspose33() {
        final double[][] in = new double[][] { //
                { 1., 2., 3. }, //
                { 4., 5., 6. }, //
                { 7., 8., 9. } //
        };
        final double[][] out = MatrixUtil.transposeMatrix33(in);
        Assertions.assertArrayEquals(new double[][] { //
                { 1., 4., 7. }, //
                { 2., 5., 8. }, //
                { 3., 6., 9. } //
        }, out);
    }

    @Test
    public final void testDeterminant33() {
        final double[][] in = new double[][] { //
                { 1., 2., 3. }, //
                { 0., 4., 5. }, //
                { 1., 0., 6. } //
        };
        final double out = MatrixUtil.determinantMatrix33(in);
        Assertions.assertEquals(22., out);
    }

    @Test
    public final void testInvert33() {
        final double[][] in = new double[][] { //
                { 1., 2., 3. }, //
                { 0., 4., 5. }, //
                { 1., 0., 6. } //
        };
        final double[][] out = MatrixUtil.invertMatrix33(in);
        TestUtil.assertDoubleArrayEquals(0.0000000000000001, new double[][] { //
                { 24. / 22., -12. / 22., -2. / 22. }, //
                { 5. / 22., 3. / 22., -5. / 22. }, //
                { -4. / 22., 2. / 22., 4. / 22. } //
        }, out);
    }
}
