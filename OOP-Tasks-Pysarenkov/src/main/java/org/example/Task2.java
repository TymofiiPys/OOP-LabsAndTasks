package org.example;


import java.util.concurrent.RecursiveTask;

public class Task2 {
    public final double[][] A;
    public final double[] a;
    public final double[] b;
    public final double[] c;
    public final double[] F;

    public int N;

    public Task2(final double[][] A, final double[] F) {
        this.A = A;
        this.a = getDiagonal(A, -1);
        this.b = getDiagonal(A, 1);
        this.c = getDiagonal(A, 0);
        this.F = F;
        this.N = F.length;
    }

    /**
     * Method for getting main/upper/lower diagonal of a 2D array
     *
     * @param input  - 2D array
     * @param offset - 0 for main diagonal, 1 for upper diagonal, -1 for lower diagonal
     * @return diagonal of input 2D array
     */
    private double[] getDiagonal(final double[][] input, int offset) {
        double[] diag = new double[input.length];
        int start = (offset == -1) ? 1 : 0;
        int end = (offset == 1) ? input.length - 1 : input.length;
        for (int i = start; i < end; i++) {
            diag[i] = input[i][i + offset];
        }
        return diag;
    }

    public double[] seqThomas() {
        double[] alpha = new double[N];
        double[] beta = new double[N];

        alpha[1] = -b[0] / c[0];
        beta[1] = F[0] / c[0];
        for (int i = 1; i < N - 1; i++) {
            alpha[i + 1] = -b[i] / (a[i] * alpha[i] + c[i]);
            beta[i + 1] = (F[i] - a[i] * beta[i]) / (a[i] * alpha[i] + c[i]);
        }

        double[] x = new double[N];
        x[N - 1] = (F[N - 1] - a[N - 1] * beta[N - 1]) / (a[N - 1] * alpha[N - 1] + c[N - 1]);
        for (int i = N - 2; i >= 0; i--) {
            x[i] = alpha[i + 1] * x[i + 1] + beta[i + 1];
        }
        return x;
    }

    /**
     * Computes the size of each chunk, given the number of chunks to create
     * across a given number of elements.
     *
     * @param nChunks   The number of chunks to create
     * @param nElements The number of elements to chunk across
     * @return The default chunk size
     */
    private static int getChunkSize(final int nChunks, final int nElements) {
        // Integer ceil
        return (nElements + nChunks - 1) / nChunks;
    }

    private static int getChunkStartInclusive(final int chunk,
                                              final int nChunks, final int nElements) {
        final int chunkSize = getChunkSize(nChunks, nElements);
        return chunk * chunkSize;
    }

    /**
     * Computes the exclusive element index that the provided chunk ends at,
     * given there are a certain number of chunks.
     *
     * @param chunk     The chunk to compute the end of
     * @param nChunks   The number of chunks created
     * @param nElements The number of elements to chunk across
     * @return The exclusive end index for this chunk
     */
    private static int getChunkEndExclusive(final int chunk, final int nChunks,
                                            final int nElements) {
        final int chunkSize = getChunkSize(nChunks, nElements);
        final int end = (chunk + 1) * chunkSize;
        if (end > nElements) {
            return nElements;
        } else {
            return end;
        }
    }

    /**
     * Class used for forward and backward communication steps of the algorithm
     */
    private class ForwardBackwardCom extends RecursiveTask<double[][]> {
        private final int left;
        private final int right;
        private final double[][] A;

        private final double[] F;

        public ForwardBackwardCom(final double[][] A, final double[] F, int left, final int right) {
            this.left = left;
            this.right = right;
            this.A = A;
            this.F = F;
        }

        /**
         * @return Array with zeros instead of lower and upper diagonal and changed values in main one
         */
        protected double[][] compute() {
            double[][] ANew = new double[right - left][N+1];
            for (int i = left; i < right; i++) {
                for (int j = 0; j < N; j++) {
                    ANew[i - left][j] = A[i][j];
                }
                ANew[i - left][N] = F[i - left];
            }
            for (int i = 1; i < right - left; i++) {
                double factor = ANew[i][i + left - 1] / ANew[i - 1][i + left - 1];
                for (int j = 0; j <= N; j++) {
                    ANew[i][j] -= ANew[i - 1][j] * factor;
                }
            }
            for (int i = right - left - 2; i >= 0; i--) {
                double factor = ANew[i][i + left + 1] / ANew[i + 1][i + left + 1];
                for (int j = 0; j <= N; j++) {
                    ANew[i][j] -= ANew[i + 1][j] * factor;
                }
            }
            return ANew;
        }
    }

    private double[][] parseShortenedArray(final double[][] A, int numThreads){
        double[][] shortened = new double[2 * numThreads][2 * numThreads];
        int k = 0, l = 0;
        for (int i = 0; i < A.length; i++) {
            l = 0;
            if(i % (A.length / numThreads) != 0 && (i + 1) % (A.length / numThreads) != 0)
                continue;
            for (int j = 0; j < A.length; j++) {
                if(j % (A.length / numThreads) != 0 && (j + 1) % (A.length / numThreads) != 0)
                    continue;
                shortened[k][l] = A[i][j];
                l++;
            }
            k++;
        }
        return shortened;
    }

    private double[] getFullSol(double[] shortSol, final double[][] A, int numThreads){
        double[] sol = new double[A.length];
        int k = 0;
        for (int i = 0; i < A.length; i++) {
            if(i % (A.length / numThreads) != 0 && (i + 1) % (A.length / numThreads) != 0)
                continue;
            sol[i] = shortSol[k];
            k++;
        }
        k = 0;
        for (int i = 0; i < A.length; i++) {
            if(i % (A.length / numThreads) != 0 && (i + 1) % (A.length / numThreads) != 0) {
                double res = F[i];
                int l = 0;
                for (int j = 0; j < A.length; j++) {
                    if(j % (A.length / numThreads) != 0 && (j + 1) % (A.length / numThreads) != 0)
                        continue;
                    res -= sol[j] * A[i][j];
                    l++;
                }
                sol[i] = res / A[i][i];
            }
        }
        return sol;
    }

    public double[] parThomas(int numThreads) {
        //Запуск прямого та зворотного ходів алгоритму (с. 13-14) для numThreads потоків
        ForwardBackwardCom[] fbcTasks = new ForwardBackwardCom[numThreads];
        double[][][] piecesOfA = new double[numThreads][N / numThreads][N];
        for (int i = 0; i < numThreads; i++) {
            fbcTasks[i] = new ForwardBackwardCom(A, F, getChunkStartInclusive(i, numThreads, N), getChunkEndExclusive(i, numThreads, A.length));
            if (i != 0)
                fbcTasks[i].fork();
        }
        double[][] temp = fbcTasks[0].compute();
        piecesOfA[0] = temp;
        for (int i = 1; i < numThreads; i++) {
            temp = fbcTasks[i].join();
            piecesOfA[i] = temp;
        }
        //Об'єднання оброблених смуг в одну матрицю
        double[][] ANew = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ANew[i][j] = piecesOfA[i / numThreads][i % numThreads][j];
            }
        }
        double[] FNew = new double[N];
        for (int i = 0; i < N; i++) {
            FNew[i] = piecesOfA[i / numThreads][i % numThreads][N];
        }
        //Виключення внутрішніх рядків із кожної смуги
        double[][] ANewShort = parseShortenedArray(A, numThreads);
        Task2 shortTask = new Task2(ANewShort, F);
        double[] shortSol = shortTask.seqThomas();

        return getFullSol(shortSol, A, numThreads);
    }

    public double[][] testForwBackwCom(final int left, final int right) {
        ForwardBackwardCom testTask = new ForwardBackwardCom(A, F, left, right);
        return testTask.compute();
    }

    public static void main(String[] args) {

    }
}
