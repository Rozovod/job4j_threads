package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenIncrement20ThenGet20() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        casCount.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get()).isEqualTo(20);
    }

}