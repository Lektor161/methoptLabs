package linear;

import java.util.stream.IntStream;

public class MatrixE extends AbstractMatrix {
    private final Matrix matrix;
    private final Matrix E;

    private final int rows;
    private final int columns;

    public MatrixE(Matrix matrix) {
        this.matrix = matrix;
        rows = matrix.getN();
        columns = matrix.getN() * 2;
        final double[][] data = new double[rows][rows];
        IntStream.range(0, rows).forEach(i -> data[i][i] = 1d);
        E = new RegularMatrix(data);
    }

    @Override
    public double get(int row, int col) {
        if (col < rows) {
            return matrix.get(row, col);
        } else {
            return E.get(row, col - rows);
        }
    }

    @Override
    public int getM() {
        return columns;
    }

    @Override
    public int getN() {
        return rows;
    }

    @Override
    public void set(int row, int col, double value) {
        if (col < rows) {
            matrix.set(row, col, value);
        } else {
            E.set(row, col - rows, value);
        }
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    public Matrix getReversed() {
        return E;
    }
}
