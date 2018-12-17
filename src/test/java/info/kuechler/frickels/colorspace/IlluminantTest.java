package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IlluminantTest {
    @Test
    public final void test() {
        final Illuminant d65 = Illuminant.D65_2;
        final XYZ xyz = d65.getXyy().toXYZ();
        System.out.println("IlluminantTest D65 2° " + xyz);

        Assertions.assertEquals(0.9504715475817799, xyz.getX());
        Assertions.assertEquals(1., xyz.getY());
        Assertions.assertEquals(1.088829656285427, xyz.getZ());
    }
}
