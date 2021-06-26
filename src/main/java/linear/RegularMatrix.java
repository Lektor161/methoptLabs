package linear;

import slau.utils.PresentationUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RegularMatrix extends AbstractMatrix implements SaveMatrix {
    protected double[][] data;
    protected final int n, m;

    public RegularMatrix(double[][] data) {
        Matrices.desiredShape(data);

        this.data = data;
        n = data.length;
        m = data[0].length;
        if (!IntStream.range(1, n).allMatch(i -> data[i].length == m)) {
            throw new IllegalArgumentException("all rows should have same size");
        }
    }

    public RegularMatrix(int n, int m) {
        this(new double[n][m]);
    }

    public double[][] getData() {
        return data;
    }

    public RegularMatrix(Path pathFile) throws UncheckedIOException {
        try (final BufferedReader reader = Files.newBufferedReader(pathFile)) {
            final Scanner scanner = new Scanner(reader);
            n = scanner.nextInt();
            m = scanner.nextInt();
            data = new double[n][m];
            IntStream.range(0, n).forEach(row -> {
                IntStream.range(0, m).forEach(col -> {
                    data[row][col] = scanner.nextDouble();
                });
            });
        } catch (final IOException e) {
            System.err.println("Exception while reading: " + e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public double get(int row, int col) {
        if (invalid(row, col)) {
            throw new IllegalArgumentException(
                    "illegal row or column:[" + row + ", " + col + "]");
        }
        return data[row][col];
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public void set(int row, int col, double value) {
        if (invalid(row, col)) {
            throw new IllegalArgumentException(
                    "illegal row or column:[" + row + ", " + col + "]");
        }
        data[row][col] = value;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public void saveToFile(Path file) {
        PresentationUtils.createDir(file);
        try (final BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write(Integer.toString(n));
            writer.write(System.lineSeparator());
            writer.write(Integer.toString(m));
            writer.write(System.lineSeparator());
            IntStream.range(0, n).forEach(row -> {
                IntStream.range(0, m).forEach(col -> {
                    try {
                        writer.write(PresentationUtils.getFormattedDouble(data[row][col]));
                        writer.write(" ");
                    } catch (final IOException e) {
                        System.err.println("Exception while writing: " + e.getMessage());
                    }
                });
                try {
                    writer.write(System.lineSeparator());
                } catch (final IOException e) {
                    System.err.println("Exception while writing: " + e.getMessage());
                }
            });
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data)
                .replace("],", "],\n");
    }
}
