package info.kuechler.frickels.colorspace;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RGBGammaStrategyTest {
    @Test
    public final void testSerialize() throws IOException, ClassNotFoundException {
        final RGBGammaStrategy strategy = new RGBGammaStrategy(RGBGammaStrategyTest.class.getName(), (cs, f) -> {
            return new double[] { f[0] * 1.11 };
        }, (cs, f) -> {
            return new double[] { f[0] / 1.11 };
        });
        Assertions.assertEquals(2 * 1.11, strategy.adjust(null, new double[] { 2. })[0]);
        byte[] bytes = TestUtil.serialize(strategy);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        final RGBGammaStrategy strategy2 = TestUtil.deserialize(bytes);
        Assertions.assertEquals(2 * 1.11, strategy2.adjust(null, new double[] { 2. })[0]);
    }

}
