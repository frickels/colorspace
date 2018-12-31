package info.kuechler.frickels.colorspace;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RGBConversionAdjusterTest {
    @Test
    public final void testSerialize() throws IOException, ClassNotFoundException {
        final RGBConversionAdjuster adjuster = new RGBConversionAdjuster(RGBConversionAdjusterTest.class.getName(),
                (cs, f) -> {
                    return f * 1.11;
                });
        Assertions.assertEquals(2 * 1.11, adjuster.adjust(null, 2.));
        byte[] bytes = TestUtil.serialize(adjuster);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        final RGBConversionAdjuster adjuster2 = TestUtil.deserialize(bytes);
        Assertions.assertEquals(2 * 1.11, adjuster2.adjust(null, 2.));
    }

}
