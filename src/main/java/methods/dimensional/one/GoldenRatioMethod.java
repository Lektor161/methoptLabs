package methods.dimensional.one;

import java.util.function.UnaryOperator;

public class GoldenRatioMethod extends AbstractOneDimensionalMethod {
    public final static double GOLD = ((Math.sqrt(5d) - 1d) / 2d);

    public GoldenRatioMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public GoldenRatioMethod() {
        super(null);
    }

    @Override
    public double findMin(double a, double b) {
        table.clear();
        calculations = 0;
        double epsN = (b - a) / 2d;

        // step 1
        double x1 = b - GOLD * (b - a);
        double x2 = a + GOLD * (b - a);
        double fx1 = function.apply(x1);
        double fx2 = function.apply(x2);
        calculations += 2;
        // step 2
        while (epsN > eps) {
            // step 3
            if (fx1 <= fx2) {
                b = x2;
                x2 = x1;
                fx2 = fx1;

                x1 = b - GOLD * (b - a);
                fx1 = function.apply(x1);
            } else {
                a = x1;
                x1 = x2;
                fx1 = fx2;

                x2 = a + GOLD * (b - a);
                fx2 = function.apply(x2);
            }
            calculations++;
            addInfo(a, b, (a + b) / 2d);
            epsN = (b - a) / 2d;
        }

        // step 4
        return (a + b) / 2d;
    }

    @Override
    public String getName() {
        return "Золотое сечение";
    }
}
