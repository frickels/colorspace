package info.kuechler.frickels.colorspace;

import static info.kuechler.frickels.colorspace.Illuminant.Type.CIE_1931_2;
import static info.kuechler.frickels.colorspace.Illuminant.Type.CIE_1964_10;

import java.util.Objects;

public class Illuminant {

    public enum Type {
        CIE_1931_2, CIE_1964_10
    }

    // https://en.wikipedia.org/wiki/Standard_illuminant

    public static final Illuminant A_2 = fromXYY(CIE_1931_2, 0.44757, 0.40745, 1., 2856);
    public static final Illuminant A_10 = fromXYY(Type.CIE_1964_10, 0.45117, 0.40594, 1., 2856);

    public static final Illuminant B_2 = fromXYY(CIE_1931_2, 0.34842, 0.35161, 1., 4874);
    public static final Illuminant B_10 = fromXYY(Type.CIE_1964_10, 0.34980, 0.35270, 1., 4874);

    public static final Illuminant C_2 = fromXYY(CIE_1931_2, 0.31006, 0.31616, 1., 6774);
    public static final Illuminant C_10 = fromXYY(Type.CIE_1964_10, 0.31039, 0.31905, 1., 6774);

    public static final Illuminant D50_2 = fromXYY(CIE_1931_2, 0.34567, 0.35850, 1., 5003);
    public static final Illuminant D50_10 = fromXYY(CIE_1964_10, 0.34773, 0.35952, 1., 5003);

    public static final Illuminant D55_2 = fromXYY(CIE_1931_2, 0.33242, 0.34743, 1., 5503);
    public static final Illuminant D55_10 = fromXYY(Type.CIE_1964_10, 0.33411, 0.34877, 1., 5503);

    // 0.312727, 0.329023
    // see https://en.wikipedia.org/wiki/Talk:Illuminant_D65
    // based of https://www.rit.edu/cos/colorscience/rc_useful_data.php
    //
    // https://de.wikipedia.org/wiki/CIE-Normvalenzsystem
    // 0.312713 0.329016
    public static final Illuminant D65_2 = fromXYY(CIE_1931_2, 0.312727, 0.329023, 1., 6504);
    public static final Illuminant D65_10 = fromXYY(CIE_1964_10, 0.31382, 0.33100, 1., 6504);

    public static final Illuminant D75_2 = fromXYY(CIE_1931_2, 0.29902, 0.31485, 1., 7504);
    public static final Illuminant D75_10 = fromXYY(Type.CIE_1964_10, 0.29968, 0.31740, 1., 7504);

    public static final Illuminant E_2 = fromXYY(CIE_1931_2, 1. / 3., 1. / 3., 1., 5454);
    public static final Illuminant E_10 = fromXYY(Type.CIE_1964_10, 1. / 3., 1. / 3., 1., 5454);

    public static final Illuminant F1_2 = fromXYY(CIE_1931_2, 0.31310, 0.33727, 1., 6430);
    public static final Illuminant F1_10 = fromXYY(Type.CIE_1964_10, 0.31811, 0.33559, 1., 6430);

    public static final Illuminant F2_2 = fromXYY(CIE_1931_2, 0.37208, 0.37529, 1., 4230);
    public static final Illuminant F2_10 = fromXYY(Type.CIE_1964_10, 0.37925, 0.36733, 1., 4230);

    public static final Illuminant F3_2 = fromXYY(CIE_1931_2, 0.40910, 0.39430, 1., 3450);
    public static final Illuminant F3_10 = fromXYY(Type.CIE_1964_10, 0.41761, 0.38324, 1., 3450);

    public static final Illuminant F4_2 = fromXYY(CIE_1931_2, 0.44018, 0.40329, 1., 2940);
    public static final Illuminant F4_10 = fromXYY(Type.CIE_1964_10, 0.44920, 0.39074, 1., 2940);

    public static final Illuminant F5_2 = fromXYY(CIE_1931_2, 0.31379, 0.34531, 1., 6350);
    public static final Illuminant F5_10 = fromXYY(Type.CIE_1964_10, 0.31975, 0.34246, 1., 6350);

    public static final Illuminant F6_2 = fromXYY(CIE_1931_2, 0.37790, 0.38835, 1., 4150);
    public static final Illuminant F6_10 = fromXYY(Type.CIE_1964_10, 0.38660, 0.37847, 1., 4150);

    public static final Illuminant F7_2 = fromXYY(CIE_1931_2, 0.31292, 0.32933, 1., 6500);
    public static final Illuminant F7_10 = fromXYY(Type.CIE_1964_10, 0.31569, 0.32960, 1., 6500);

    public static final Illuminant F8_2 = fromXYY(CIE_1931_2, 0.34588, 0.35875, 1., 5000);
    public static final Illuminant F8_10 = fromXYY(Type.CIE_1964_10, 0.34902, 0.35939, 1., 5000);

    public static final Illuminant F9_2 = fromXYY(CIE_1931_2, 0.37417, 0.37281, 1., 4150);
    public static final Illuminant F9_10 = fromXYY(Type.CIE_1964_10, 0.37829, 0.37045, 1., 4150);

    public static final Illuminant F10_2 = fromXYY(CIE_1931_2, 0.34609, 0.35986, 1., 5000);
    public static final Illuminant F10_10 = fromXYY(Type.CIE_1964_10, 0.35090, 0.35444, 1., 5000);

    public static final Illuminant F11_2 = fromXYY(CIE_1931_2, 0.38052, 0.37713, 1., 4000);
    public static final Illuminant F11_10 = fromXYY(Type.CIE_1964_10, 0.38541, 0.37123, 1., 4000);

    public static final Illuminant F12_2 = fromXYY(CIE_1931_2, 0.43695, 0.40441, 1., 3000);
    public static final Illuminant F12_10 = fromXYY(Type.CIE_1964_10, 0.44256, 0.39717, 1., 3000);

    private final XYY xyy;

    private final Type type;

    private final int cct;

    public static Illuminant fromXYY(final Type type, final double x, final double y, final double y2, final int cct) {
        return new Illuminant(type, x, y, y2, cct);
    }

    public Illuminant(final Type type, final double x, final double y, final double y2, final int cct) {
        this.type = type;
        this.xyy = new XYY(this, x, y, y2);
        this.cct = cct;
    }

    public XYY getXyy() {
        return xyy;
    }

    /**
     * Correlated color temperature in Kelvin
     */
    public int getCct() {
        return cct;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Illuminant [%s, xyz=%s, cct=%s]", type, xyy, cct);
    }

    public static double adjustPlankBlackbody(final double f) {
        // https://en.wikipedia.org/wiki/Illuminant_D65
        return f * (1.4388 / 1.438);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cct, type, xyy);
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
        final Illuminant other = (Illuminant) obj;
        return cct == other.cct && type == other.type && Objects.equals(xyy, other.xyy);
    }
}
