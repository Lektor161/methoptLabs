package methods.dimensional.poly;

import linear.DoubleVector;
import linear.Matrices;
import linear.Matrix;

import java.util.Objects;
import java.util.stream.IntStream;

public class QuadraticForm {
    private static final double EPS = 1e-6d;

    private final int n;
    private final Matrix a;
    private final DoubleVector b;
    private final double c;
    private final DoubleVector values;
    private final double minValue, maxValue;


    public QuadraticForm(Matrix a, DoubleVector b, double c, DoubleVector values) {
        if (a.stream().anyMatch(Objects::isNull) || !(Matrices.checkSymmetric(a))) {
            throw new IllegalArgumentException("Illegal matrix input.");
        }
        this.n = a.getN();
        if (b.size() != n) {
            throw new IllegalArgumentException("Vector and Matrix should have same dimension.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.values = values;
        minValue = values.stream().min().orElse(1d);
        maxValue = values.stream().max().orElse(1d);
    }

    public QuadraticForm(Matrix a, DoubleVector b, Double c) {
        this(a, b, c, new DoubleVector(IntStream.range(0, a.getN()).mapToDouble(i -> a.get(i, i)).toArray()));
    }

    public int getN() {
        return n;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    private IntStream range() {
        return IntStream.range(0, n);
    }

    private double scalarProduct(DoubleVector x, DoubleVector y) {
        return range().mapToDouble(i -> x.get(i) * y.get(i)).sum();
    }

    public double apply(DoubleVector x) {
        if (a.isDiagonal()) {
            return scalarProduct(a.mulByVec(x), x) / 2d + scalarProduct(x, b) + c;
        }
        return scalarProduct(new DoubleVector(range().mapToDouble(i -> scalarProduct(x, a.get(i))).toArray()), x) / 2d
                + scalarProduct(x, b) + c;
    }

    public DoubleVector gradient(DoubleVector x) {
        if (a.isDiagonal()) {
            return a.mulByVec(x).add(b);
        }
        return new DoubleVector(range().mapToDouble(i -> (scalarProduct(x, a.get(i)) + b.get(i))).toArray());
    }

    public Matrix getA() {
        return a;
    }

    public DoubleVector getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    @Override
    public String toString() {
        return a.toString();
    }

    private static boolean compare(double a, double b) {
        return Math.abs(a - b) <= EPS;
    }
}
