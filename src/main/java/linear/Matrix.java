package linear;

import java.util.stream.Stream;

public interface Matrix {
    double DOUBLE_EQUALS_EPS = 0.0001;

    DoubleVector get(int row);

    void set(int row, int col, double value);

    double get(int row, int col);

    int getM();

    Matrix mulByMatrix(Matrix matrix);

    double[] mulByVec(double[] vector);


    Stream<DoubleVector> stream();

    boolean equals(Matrix matrix);

    double minor(int row, int col, int delta);

    double determinant();

    int getN();

    void swapRows(int row1, int row2);

    Matrix add(Matrix matrix);

    boolean isDiagonal();

    DoubleVector mulByVec(DoubleVector vector);
}
