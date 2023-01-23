package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.pools.RolColSum.*;

class RolColSumTest {
    private final int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

    @Test
    void whenSum() {
        Sums[] sums = sum(matrix);
        Sums[] expected = {
                new Sums(10, 28),
                new Sums(26, 32),
                new Sums(42, 36),
                new Sums(58, 40)
        };
        assertThat(sums).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() {
        Sums[] sums = asyncSum(matrix);
        Sums[] expected = {
                new Sums(10, 28),
                new Sums(26, 32),
                new Sums(42, 36),
                new Sums(58, 40)
        };
        assertThat(sums).isEqualTo(expected);
    }
}