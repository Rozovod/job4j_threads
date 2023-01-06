package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelIndexLookupTest {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Test
    void whenLineSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThat(forkJoinPool.invoke(
                new ParallelIndexLookup<>(array, 0, array.length - 1, 8)
        )).isEqualTo(7);
    }

    @Test
    void whenRecursiveSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(forkJoinPool.invoke(
                new ParallelIndexLookup<>(array, 0, array.length - 1, 12)
        )).isEqualTo(11);
    }

    @Test
    void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(forkJoinPool.invoke(
                new ParallelIndexLookup<>(array, 0, array.length - 1, 20)
        )).isEqualTo(-1);
    }
}