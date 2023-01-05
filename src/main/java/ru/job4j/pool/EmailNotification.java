package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        pool.submit(() -> {
                    String subject = new StringBuilder()
                            .append("Notification ")
                            .append(user.username())
                            .append(" to email ")
                            .append(user.email())
                            .toString();
                    String body = new StringBuilder()
                            .append("Add a new event to ")
                            .append(user.username())
                            .toString();
                    send(subject, body, user.email());
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
