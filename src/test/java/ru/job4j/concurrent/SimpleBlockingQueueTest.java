package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenOffer3ThenPoll3() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread producer is interrupted");
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread consumer is interrupted");
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        producer.join();
        assertThat(queue.poll()).isEqualTo(3);
    }

}