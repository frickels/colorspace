package info.kuechler.frickels.colorspace;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVTestUtil {
    public static final CSVFormat FORMAT = CSVFormat.DEFAULT.withDelimiter(';');

    public static void loadRGBFile(final BiConsumer<LAB, RGB> consumer) throws IOException {
        try (final InputStreamReader in = new InputStreamReader(
                CSVTestUtil.class.getResourceAsStream("/files/hlc-lab-rgb.csv"), StandardCharsets.UTF_8);
                final CSVParser parser = new CSVParser(in, FORMAT);) {
            for (final CSVRecord record : parser) {
                if (parser.getCurrentLineNumber() != 1L) {
                    final LAB lab = new LAB(Illuminant.D65_2, toDouble(record.get(3)), toDouble(record.get(4)),
                            toDouble(record.get(5)));
                    final RGB rgb = RGB.from0TO255(RGBColorSpace.sRGB, toInt(record.get(6)), toInt(record.get(7)),
                            toInt(record.get(8)));
                    consumer.accept(lab, rgb);
                }
            }
        }
    }

    private static int toInt(String string) {
        return (int) toDouble(string);
    }

    private static double toDouble(String string) {
        return Double.valueOf(string.replace(',', '.'));
    }
}
