package slau.utils;

import linear.*;
import methods.Pair;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FormulaGenerator {

    public static Pair<Matrix, DoubleVector>
        generateFormula(
            final int n,
            final int k,
            final BiFunction<Integer, Integer, Matrix> generator) {
        final Matrix m = generator.apply(n, k);
        final DoubleVector x = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> x.set(i, i + 1d));
        return new Pair<>(m, m.mulByVec(x));
    }

    @Deprecated
    public static Matrix generateMatrix(final int n, final int k) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        double eps = 1;
        for (int i = 0; i < k; i++) {
            eps /= 10d;
        }
        final double[][] data = new double[n][n];
        final Random rnd = new Random();
        final double finalEps = eps;
        IntStream.range(0, n).forEach(
                row -> {
                    IntStream.range(0, n - 1).forEach(
                            col -> {
                                data[row][col] = -rnd.nextInt(5);
                            }
                    );
                    data[row][row] = 0;
                    double sum = Arrays.stream(data[row], 0, n - 1)
                            .sum();
                    final double last = -rnd.nextInt(5);
                    sum += last;
                    data[row][n - 1] = last;
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += finalEps;
                    }
                }
        );
        return new LUMatrix(data);
    }

    private static final Supplier<Integer> random5 = () -> {
        int[] ratios = new int[]{5, 2, 1, 1, 1};
        int len = Arrays.stream(ratios).sum();
        int[] rnd = new int[len];
        int cnt = 0;
        int point = 0;
        for (int i = 0; i < ratios.length; i++) {
            cnt += ratios[i];
            while (point < cnt) {
                rnd[point] = i;
                point++;
            }
        }
        return rnd[new Random().nextInt(rnd.length)];
    };

    public static Matrix generateMatrix2(final int n, final int k) {
        return genRandomMatrix(n, k, 0.5);
    }

    public static Matrix genRandomMatrix(final int n, final int k, final double zp) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        double eps = 1;
        for (int i = 0; i < k; i++) {
            eps /= 10d;
        }
        final double[][] data = new double[n][n];
        final Random rnd = new Random();
        final double finalEps = eps;
        IntStream.range(0, n).forEach(
                row -> {
                    IntStream.range(0, n - 1).forEach(
                            col -> {
                                data[row][col] = -RandomUtils.randomValue(zp);
                            }
                    );
                    data[row][row] = 0;
                    double sum = Arrays.stream(data[row], 0, n - 1)
                            .sum();
                    double last = -random5.get();
                    while (last + sum == 0) {
                        last = -rnd.nextInt(5);
                    }
                    sum += last;
                    data[row][n - 1] = last;
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += finalEps;
                    }
                }
        );
        return new LUMatrix(data);
    }

    public static Matrix generateHilbert(final int n) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        final double[][] data = new double[n][n];
        IntStream.range(0, n).forEach(
                i -> IntStream.range(0, n).forEach(
                        j -> data[i][j] = 1d/(i + j + 1)
                )
        );
        return new RegularMatrix(data);
    }

    public static Pair<Matrix, DoubleVector> generateHilbertFormula(final int n) {
        final Matrix m = generateHilbert(n);
        final DoubleVector x = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> x.set(i, i + 1d));
        return new Pair<>(m, m.mulByVec(x));
    }

    public static Matrix generateDigMatrix(int n, double zp) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        double eps = 1;
        final double[][] data = new double[n][n];
        final Random rnd = new Random();
        final double finalEps = eps;
        IntStream.range(0, n).forEach(
                row -> {
                    IntStream.range(0, row).forEach(
                            col -> {
                                data[row][col] = -RandomUtils.randomValue(zp);
                            }
                    );
                    IntStream.range(row + 1, n).forEach(
                            col -> {
                                data[row][col] = data[row][n - 1 - col];
                            }
                    );
                    data[row][row] = 0;
                    double sum = Arrays.stream(data[row], 0, n).sum();
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += finalEps;
                    }
                }
        );
        return new SparseSymmetricMatrix(data);
    }


    private static class RandomUtils {
        /**
         * Generate random value from [l, r) or 0 with {@code zeroPercent} chance
         *
         * @param l left bound > 0
         * @param r right bound, r > l
         * @return randomValue with {@code zeroPercent} percent of 0, otherwise from l to r
         * */
        static double randomValue(final int l, final int r, final double zeroPercent) {
            final Random rnd = new Random();
            if (rnd.nextDouble() <= zeroPercent) {
                return 0d;
            }
            return rnd.nextInt(r - l) + l;
        }

        static double randomValue(final double zeroPercent) {
            return randomValue(1, 5, zeroPercent);
        }
    }
}
