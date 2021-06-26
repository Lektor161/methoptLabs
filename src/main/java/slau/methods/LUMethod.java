package slau.methods;

import linear.LU;
import linear.LUDecomp;
import linear.Matrix;

public class LUMethod implements Method{
    @Override
    public double[] solve(Matrix matrix, double[] numbers) {
        if (!(matrix instanceof LUDecomp)) {
            throw new IllegalArgumentException("Matrix must have LU decomposition.");
        }
        final LU lu = ((LUDecomp) matrix).getLU();
        final Method gauss = new GaussMethod();
        return gauss.solve(lu.getU(),
                gauss.solve(lu.getL(), numbers));
    }
}
