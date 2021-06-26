package linear;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Matrices {
    public static final double EPS = 1e-3d;

    private Matrices() {
    }

    public static boolean epsEquals(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    public static void requireNonNullComponents(Matrix matrix) {
        if (matrix == null || IntStream.range(0, matrix.getN())
                .mapToObj(matrix::get).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("matrix and every matrix row must be non null");
        }
    }

    public static boolean checkShape(double[][] data) {
        requireNonNullComponents(data);

        return Arrays.stream(data).allMatch((row) -> row.length == data[0].length);
    }

    public static void requireNonNullComponents(double[][] data) {
        if (data == null || Arrays.stream(data).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("data and every data[i] must be non null");
        }
    }

    public static void desiredSymmetric(Matrix matrix) {
        if (!checkSymmetric(matrix)) {
            throw new IllegalArgumentException("matrix must be symmetric");
        }
    }

    public static void desiredShape(double[][] data) {
        if (!checkShape(data)) {
            throw new IllegalArgumentException("data rows must have same length");
        }
    }

    public static boolean checkSquare(double[][] data) {
        desiredShape(data);
        return Arrays.stream(data).allMatch((row) -> row.length == data.length);
    }

    public static void desiredSquare(double[][] data) {
        if (!checkSquare(data)) {
            throw new IllegalArgumentException("data must have same dimensions");
        }
    }

    public static boolean checkSymmetric(Matrix matrix) {
        requireNonNullComponents(matrix);

        return checkSquare(matrix) && IntStream.range(0, matrix.getN())
                .anyMatch(row -> IntStream.range(0, row).allMatch(col ->
                        matrix.get(row, col) == matrix.get(col, row)));
    }

    public static void desiredSquare(Matrix matrix) {
        if (!checkSquare(matrix)) {
            throw new IllegalArgumentException("matrix must have same dimensions");
        }
    }

    public static boolean checkSquare(Matrix matrix) {
        requireNonNullComponents(matrix);
        return matrix.getN() == matrix.getM();
    }

}
