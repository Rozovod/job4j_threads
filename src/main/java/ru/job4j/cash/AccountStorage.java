package ru.job4j.cash;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            boolean rsl = false;
            if (!accounts.containsValue(account)) {
                accounts.put(account.id(), account);
                rsl = true;
            }
            return rsl;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            boolean rsl = false;
            if (accounts.containsKey(account.id())) {
                accounts.replace(account.id(), account);
                rsl = true;
            }
            return rsl;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.keySet().removeIf(key -> key == id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            Optional<Account> rsl = Optional.empty();
            for (Integer accId : accounts.keySet()) {
                if (Objects.equals(accId, id)) {
                    rsl = Optional.of(accounts.get(accId));
                    break;
                }
            }
            return rsl;
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
