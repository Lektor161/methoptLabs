package linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DoubleMatrix extends AbstractMatrix {
    protected final int n, m;
    protected final List<DoubleVector> values;

    public DoubleMatrix(DoubleVector... rows) {
        Vectors.requireEqualSizes(rows);

        n = rows.length;
        if (Arrays.stream(rows).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Rows have null vectors.");
        }
        final int maxSize = Arrays.stream(rows).map(DoubleVector::size).max(Comparator.naturalOrder()).orElse(0);
        m = maxSize;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(maxSize)));
        IntStream.range(0, n).forEach(
                i -> values.set(i, new DoubleVector(rows[i], maxSize))
        );
    }

    public DoubleMatrix(final double[][] data) {
        n = data.length;
        m = data[0].length;
        if (Arrays.stream(data).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Rows have null vectors.");
        }
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(data[0].length)));
        IntStream.range(0, n).forEach(
                i -> values.set(i, new DoubleVector(data[i]))
        );
    }

    public DoubleMatrix(int n, int m) {
        this.n = n;
        this.m = m;
        values = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            values.add(new DoubleVector(m));
        }
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public DoubleVector get(int i) {
        return values.get(i);
    }

    public void set(int i, int j, double val) {
        values.get(i).set(j, val);
    }

    public double get(int i, int j) {
        return values.get(i).get(j);
    }

    public void set(int i, DoubleVector val) {
        values.set(i, val);
    }

    public boolean isDiagonal() {
        return false;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values.toArray()).replace("],", String.format("],%n"));
    }

    public DoubleVector multiply(final DoubleVector vector) {
        if (m != vector.size()) {
            throw new IllegalArgumentException("Wide and height should be same.");
        }
        return new DoubleVector(values.stream().mapToDouble(v -> v.scalar(vector)).toArray());
    }

    public DoubleMatrix multilpy(final double k) {
        final double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = k * get(i, j);
            }
        }
        return new DoubleMatrix(data);
    }

    public Stream<DoubleVector> stream() {
        return values.stream();
    }
}
