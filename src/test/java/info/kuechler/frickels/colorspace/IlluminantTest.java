package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.TestUtil.assertDoubleDiff;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IlluminantTest {
    private static final Logger LOG = LoggerFactory.getLogger(IlluminantTest.class);
    
    @Test
    public final void test() {
        final Illuminant d65 = Illuminant.D65_2;
        final XYZ xyz = d65.getXyy().toXYZ();
        LOG.info("IlluminantTest D65 2° " + xyz);

        assertDoubleDiff(0.00001, 0.9504715475817799, xyz.getX());
        assertDoubleDiff(0.00001, 1., xyz.getY());
        assertDoubleDiff(0.00001, 1.088829656285427, xyz.getZ());
    }
}
