package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexLookup<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T objSearch;

    public ParallelIndexLookup(T[] array, int from, int to, T objSearch) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.objSearch = objSearch;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return lineSearch();
        }
        int mid = (to - from) / 2;
        ParallelIndexLookup<T> leftLookup = new ParallelIndexLookup<>(array, from, mid, objSearch);
        ParallelIndexLookup<T> rightLookup = new ParallelIndexLookup<>(array, mid + 1, to, objSearch);
        leftLookup.fork();
        rightLookup.fork();
        int leftLookupRsl = leftLookup.join();
        int rightLookupRsl = rightLookup.join();
        return Math.max(leftLookupRsl, rightLookupRsl);
    }

    private int lineSearch() {
        for (int i = from; i <= to; i++) {
            if (Objects.equals(array[i], objSearch)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int findIndex(T[] array, T objSearch) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(
                new ParallelIndexLookup<>(array, 0, array.length - 1, objSearch));
    }
}
