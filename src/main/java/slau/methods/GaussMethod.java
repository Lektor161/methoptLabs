package slau.methods;

import linear.Matrix;

public class GaussMethod implements Method {

    @Override
    public double[] solve(Matrix matrix, double[] numbers) {
        final int len = matrix.getN();
        final int columns = matrix.getM();
        if (len != numbers.length) {
            throw new IllegalArgumentException("vector of numbers must have same size with matrix");
        }
        for (int i = 1; i < len; i++) {
            final double aii = matrix.get(i - 1, i - 1);

            for (int ri = i; ri < len; ri++) {
                final double aji = matrix.get(ri, i - 1);
                for (int j = i - 1; j < columns; j++) {
                    matrix.set(ri, j,
                            matrix.get(ri, j)
                                    - aji / aii * matrix.get(i - 1, j));
                }
                numbers[ri] -= aji / aii * numbers[i - 1];
            }
        }
        for (int curRow = len - 1; curRow >= 0; curRow--) {
            final double aii = matrix.get(curRow, curRow);
            for (int nextRow = curRow - 1; nextRow >= 0; nextRow--) {
                final double coefficient = matrix.get(nextRow, curRow) / aii;
                for (int ind = curRow; ind < columns; ind++) {
                    final double element = matrix.get(nextRow, ind);
                    matrix.set(nextRow, ind,
                            element - matrix.get(curRow, ind) * coefficient
                    );
                }
                numbers[nextRow] -= numbers[curRow] * coefficient;
            }
            for (int ind = curRow; ind < columns; ind++) {
                matrix.set(curRow, ind, matrix.get(curRow, ind) / aii);
            }
            numbers[curRow] /= aii;
        }
        return numbers;
    }
}
