package info.kuechler.frickels.colorspace;

import java.util.function.ToDoubleBiFunction;

public class DeltaE {

    public static final DeltaE CIE1976 = new DeltaE((lab1, lab2) -> {
        final double dL = lab1.getL() - lab2.getL();
        final double dA = lab1.getA() - lab2.getA();
        final double dB = lab1.getB() - lab2.getB();
        return Math.sqrt((dL * dL) + (dA * dA) + (dB * dB));
    });
    
    public static final DeltaE DIN99D_ = new DeltaE((lab1, lab2) -> {
        final DIN99D din991 = DIN99D.fromLAB(lab1);
        final DIN99D din992 = DIN99D.fromLAB(lab2);
        final double dL = din991.getL() - din992.getL();
        final double dA = din991.getA() - din992.getA();
        final double dB = din991.getB() - din992.getB();
        return Math.sqrt((dL * dL) + (dA * dA) + (dB * dB));
    });

    private ToDoubleBiFunction<LAB, LAB> calculator;

    public DeltaE(final ToDoubleBiFunction<LAB, LAB> calculator) {
        this.calculator = calculator;
    }

    public double calculate(final LAB lab1, final LAB lab2) {
        return calculator.applyAsDouble(lab1, lab2);
    }
}
