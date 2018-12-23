package info.kuechler.frickels.colorspace;

import java.util.function.ToDoubleBiFunction;

public class DeltaE<T> {
    
    // TODO: CIE94 und CIEDE2000 und CMC(l:c)

    public static final DeltaE<LAB> CIE1976Δ = new DeltaE<>((lab1, lab2) -> {
        final double dL = lab1.getL() - lab2.getL();
        final double dA = lab1.getA() - lab2.getA();
        final double dB = lab1.getB() - lab2.getB();
        return Math.sqrt((dL * dL) + (dA * dA) + (dB * dB));
    });

    public static final DeltaE<DIN99> DIN99Δ = new DeltaE<>((din991, din992) -> {
        final double dL = din991.getL() - din992.getL();
        final double dA = din991.getA() - din992.getA();
        final double dB = din991.getB() - din992.getB();
        return Math.sqrt((dL * dL) + (dA * dA) + (dB * dB));
    });
    
    public static final DeltaE<DIN99O> DIN99OΔ = new DeltaE<>((din99o1, din99o2) -> {
        final double dL = din99o1.getL() - din99o2.getL();
        final double dA = din99o1.getA() - din99o2.getA();
        final double dB = din99o1.getB() - din99o2.getB();
        return Math.sqrt((dL * dL) + (dA * dA) + (dB * dB));
    });

    private ToDoubleBiFunction<T, T> calculator;

    public DeltaE(final ToDoubleBiFunction<T, T> calculator) {
        this.calculator = calculator;
    }

    public double calculate(final T lab1, final T lab2) {
        return calculator.applyAsDouble(lab1, lab2);
    }
}
