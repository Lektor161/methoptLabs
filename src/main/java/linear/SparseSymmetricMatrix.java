package linear;

import java.util.stream.IntStream;

public class SparseSymmetricMatrix extends AbstractMatrix {
    private final int n;
    private final int[] columns;
    private final int[] beginIndex;
    private final double[] diagonal;
    private final double[] rowElements;

    public SparseSymmetricMatrix(double[][] data) {
        Matrices.desiredSquare(data);

        n = data.length;
        diagonal = new double[n];
        IntStream.range(0, n).forEach(i -> diagonal[i] = data[i][i]);

        final int size = IntStream.range(0, n).map(i ->
                IntStream.range(0, i).map(j -> Matrices.epsEquals(data[i][j], 0d) ? 0 : 1).sum()
        ).sum();
        rowElements = new double[size];
        columns = new int[size];
        beginIndex = new int[n + 1];

        int index = 0;
        for (int i = 0; i < n; ++i) {
            beginIndex[i] = index;
            for (int j = 0; j < i; ++j) {
                final double element = data[i][j];
                if (!Matrices.epsEquals(element, 0d)) {
                    rowElements[index] = element;
                    columns[index++] = j;
                }
            }
        }
        beginIndex[n] = size;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return n;
    }

    private int countRow(int row) {
        return beginIndex[row + 1] - beginIndex[row];
    }

    @Override
    public double get(int row, int col) {
        if (row == col) {
            return diagonal[row];
        }
        if (row < col) {
            row += col;
            col = row - col;
            row -= col;
        }
        if (countRow(row) == 0) {
            return 0d;
        }
        // binary search
        int left = beginIndex[row] - 1;
        int right = beginIndex[row + 1];
        while (left + 1 < right) {
            final int mid = (left + right) / 2;
            if (columns[mid] <= col) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (left == beginIndex[row] - 1 || columns[left] != col) {
            return 0d;
        }
        return rowElements[left];
    }

    @Override
    public void set(int row, int col, double value) {
        int index;
        for (index = beginIndex[row]; index < beginIndex[row + 1] && columns[index] <= col; ++index) {
            if (index == columns[index]) {
                rowElements[index] = value;
            }
        }
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public DoubleVector mulByVec(DoubleVector vector) {
        final DoubleVector result = new DoubleVector(n);
        for (int row = 0; row < n; row++) {
            for (int index = beginIndex[row]; index < beginIndex[row + 1]; index++) {
                final int col = columns[index];
                var el = rowElements[index];
                result.set(row, result.get(row) + rowElements[index] * vector.get(col));
                result.set(col, result.get(col) + rowElements[index] * vector.get(row));
            }
        }
        for (int i = 0; i < n; i++) {
            result.set(i, result.get(i) + diagonal[i] * vector.get(i));
        }
        return result;
    }
}
