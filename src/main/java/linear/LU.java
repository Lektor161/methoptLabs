package linear;

public class LU {
    Matrix l, u;

    public LU(Matrix l, Matrix u) {
        this.l = l;
        this.u = u;
    }

    public Matrix getL() {
        return l;
    }

    public Matrix getU() {
        return u;
    }
}
