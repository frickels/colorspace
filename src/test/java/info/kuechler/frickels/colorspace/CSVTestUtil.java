package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.RGBColorSpaceImpl.sRGB;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.params.provider.Arguments;

public class CSVTestUtil {

    public static final CSVFormat FORMAT = CSVFormat.DEFAULT.withDelimiter(';');

    public static Stream<Arguments> loadRGBHSVHSLFile() throws IOException {
        try (final InputStreamReader in = new InputStreamReader(
                CSVTestUtil.class.getResourceAsStream("/files/rgb-hsv-hsl.csv"), UTF_8);
                final CSVParser parser = new CSVParser(in, FORMAT);) {
            return parser.getRecords().stream().filter(rec -> rec.getRecordNumber() != 1L).map(record -> {
                final HSV hsv = HSV.fromGradAnd0To100(sRGB, toDouble(record.get(1)), toDouble(record.get(2)),
                        toDouble(record.get(3)));
                final HSL hsl = HSL.fromGradAnd0To100(sRGB, toDouble(record.get(4)), toDouble(record.get(5)),
                        toDouble(record.get(6)));
                final RGB rgb = RGB.from0To100(sRGB, toInt(record.get(7)), toInt(record.get(8)), toInt(record.get(9)));
                System.out.println("RGB-2-Hsv File " + rgb + " - " + hsv + " - " + hsl);
                return Arguments.arguments(rgb, hsv, hsl);
            });
        }
    }

    private static int toInt(String string) {
        return (int) toDouble(string);
    }

    private static double toDouble(String string) {
        // System.out.println(string);
        return Double.valueOf(string.replace(',', '.').replace('–', '0').replaceAll("[^\\d\\.]+", ""));
    }
}
