package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = indexSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < matrix.length; i++) {
                sums[i] = indexSums(matrix, i);
            }
            return sums;
        });
        return sums;
    }

    private static Sums indexSums(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[index][j];
            colSum += matrix[j][index];
        }
        return new Sums(rowSum, colSum);
    }
}
