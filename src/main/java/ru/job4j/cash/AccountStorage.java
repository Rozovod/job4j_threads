package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), getById(account.id()).get(), account);
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.keySet().removeIf(key -> key == id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean rsl = false;
            Optional<Account> accFrom = getById(fromId);
            Optional<Account> accTo = getById(toId);
            if (accFrom.isPresent() && accTo.isPresent() && accFrom.get().amount() >= amount) {
                Account newAccFrom = new Account(fromId, accFrom.get().amount() - amount);
                update(newAccFrom);
                Account newAccTo = new Account(toId, accTo.get().amount() + amount);
                update(newAccTo);
                rsl = true;
            }
            return rsl;
        }
    }
}
