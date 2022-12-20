package ru.job4j.cash;

public record Account(int id, int amount) {
    public Account {
        if (amount < 0) {
            throw new IllegalArgumentException("Invoice amount cannot be negative.");
        }
    }
}
