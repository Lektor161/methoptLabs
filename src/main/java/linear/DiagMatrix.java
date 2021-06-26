package linear;

import java.util.stream.IntStream;

public class DiagMatrix extends AbstractMatrix {
    private final int n;
    private final DoubleVector diag;

    public DiagMatrix(final DoubleVector diag) {
        Vectors.requireNonNull(diag);
        n = diag.size();
        this.diag = diag;
    }

    @Override
    public double get(int i, int j) {
        return i == j ? diag.get(i) : 0d;
    }

    @Override
    public int getM() {
        return n;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public DoubleVector get(int i) {
        final DoubleVector row = new DoubleVector(n);
        row.set(i, get(i, i));
        return row;
    }

    @Override
    public void set(int i, int j, double val) {
        if (i == j) {
            diag.set(i, val);
        } else {
            throw new IllegalArgumentException("Can't set out of the main diagonal in DiagonalMatrix");
        }
    }

    @Override
    public DoubleVector mulByVec(DoubleVector vector) {
        if (n != vector.size()) {
            throw new IllegalArgumentException("Wide and height must be same.");
        }
        final DoubleVector v = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> v.set(i, diag.get(i) * vector.get(i)));
        return v;
    }

    public void set(final int i, final DoubleVector val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }

}