package methods.dimensional.poly;

import linear.DoubleVector;
import methods.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGradientMethod implements GradientOptimizationMethod {
    protected final static double DEFAULT_EPS = 1e-5d;
    protected final double eps;
    protected final List<State> table;
    protected QuadraticForm form;
    protected int iterations = 0;
    protected boolean iterationsWithoutTable = false;

    @Override
    public int getIterations() {
        return this.iterations;
    }

    @Override
    public void setIterationsWithoutTable(boolean iterationsWithoutTable) {
        this.iterationsWithoutTable = iterationsWithoutTable;
    }

    protected AbstractGradientMethod(QuadraticForm form, double eps) {
        this.form = form;
        this.eps = eps;
        table = new ArrayList<>();
    }

    protected AbstractGradientMethod(QuadraticForm form) {
        this(form, DEFAULT_EPS);
    }

    public AbstractGradientMethod() {
        this(null);
    }

    public void setForm(QuadraticForm form) {
        this.form = form;
    }

    public List<State> getTable() {
        return table;
    }

    @Override
    public List<Pair<Integer, List<Pair<Integer, Integer>>>> valueAndDimToIterations() {
        final int[] values = new int[]{1, 2, 5, 10, 20, 30, 50, 100, 200, 500, 1000, 2000};
        final int[] dimensions = new int[]{2, 5, 10, 20, 30, 40, 50, 100, 200, 500, 1000, 2000, 5000, 10000};
        final List<Pair<Integer, List<Pair<Integer, Integer>>>> result = new ArrayList<>(values.length);
        System.out.printf("%-5s", "m\\n");
        for (final int dim : dimensions) {
            System.out.printf(" %-5s", dim);
        }
        System.out.println();
        for (final int value : values) {
            System.out.printf("%-5s", value);
            result.add(new Pair<>(value, new ArrayList<>(dimensions.length)));
            for (final int dim : dimensions) {
                try {
                    form = FormGenerator.generate(dim, value);
                    table.clear();
                    findMin();
                    result.get(result.size() - 1).second.add(new Pair<>(dim, table.size()));
                } catch (final Exception e) {
                    System.err.println("aaa");
                    return result;
                }
                System.out.printf(" %-5s", result.get(result.size() - 1).second.get(result.get(result.size() - 1).second.size() - 1).second);
            }
            System.out.println();
        }

        return result;
    }

    public static class State {
        private final DoubleVector point;
        private final double value;

        public State(DoubleVector point, double value) {
            this.point = point;
            this.value = value;
        }

        public DoubleVector getPoint() {
            return point;
        }

        public double getValue() {
            return value;
        }
    }
}
