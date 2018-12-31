package info.kuechler.frickels.colorspace;

import java.util.function.ToDoubleBiFunction;

public class DeltaE<T> {

    public static final DeltaE<LAB> CIE1976Δ = new DeltaE<>((lab1, lab2) -> {
        return MatrixUtil.euclideanDistance3(lab1.toDouble(), lab2.toDouble());
    });

    public static final DeltaE<DIN99> DIN99Δ = new DeltaE<>((din991, din992) -> {
        return MatrixUtil.euclideanDistance3(din991.toDouble(), din992.toDouble());
    });

    public static final DeltaE<DIN99O> DIN99OΔ = new DeltaE<>((din99o1, din99o2) -> {
        return MatrixUtil.euclideanDistance3(din99o1.toDouble(), din99o2.toDouble());
    });

    public static final DeltaE<LUV> LUVΔ = new DeltaE<>((luv1, luv2) -> {
        return MatrixUtil.euclideanDistance3(luv1.toDouble(), luv2.toDouble());
    });

    public static final DeltaE<LAB> CIE1994Δ_TEXTILES = new DeltaE<>(DeltaE1994::calculateTextiles);

    public static final DeltaE<LAB> CIE1994Δ_GRAPHIC_ART = new DeltaE<>(DeltaE1994::calculateGraphicArts);

    public static final DeltaE<LAB> CIE2000Δ = new DeltaE<>(DeltaE2000::calculate);

    public static final DeltaE<LAB> CMC11Δ = new DeltaE<>((lab1, lab2) -> {
        return DeltaECMC.calculate(lab1, lab2, 1., 1.);
    });

    public static final DeltaE<LAB> CMC21Δ = new DeltaE<>((lab1, lab2) -> {
        return DeltaECMC.calculate(lab1, lab2, 2., 1.);
    });

    private ToDoubleBiFunction<T, T> calculator;

    public DeltaE(final ToDoubleBiFunction<T, T> calculator) {
        this.calculator = calculator;
    }

    public double calculate(final T color1, final T color2) {
        return calculator.applyAsDouble(color1, color2);
    }
}
