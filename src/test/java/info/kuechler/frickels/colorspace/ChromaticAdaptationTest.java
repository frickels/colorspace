package info.kuechler.frickels.colorspace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChromaticAdaptationTest {
    @Test
    public final void test() {
        final XYZ source = new RGB(0.5, 0.5, 0.5).toXYZ();
        System.out.println("ChromaticAdaptationTest source " + source);
        Assertions.assertEquals(0.20344610647786848, source.getX());
        Assertions.assertEquals(0.20344610647786848, source.getX());
        Assertions.assertEquals(0.20344610647786848, source.getX());
        final XYZ target = ChromaticAdaptation.adapt(source, ChromaticAdaptationMethod.BRADFORD, Illuminant.D65_2,
                Illuminant.D50_2);
        System.out.println("ChromaticAdaptationTest target " + target);
        Assertions.assertEquals(0.20638558857044492, target.getX());
        Assertions.assertEquals(0.21404070648939433, target.getY());
        Assertions.assertEquals(0.17665159786194004, target.getZ());
    }
}
