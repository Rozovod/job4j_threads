package ru.job4j.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        model = memory.computeIfPresent(model.getId(), (id, oldModel) ->
                new Base(oldModel.getId(), oldModel.getVersion() + 1));
        return memory.replace(model.getId(), memory.get(model.getId()), model);
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Optional<Base> getById(int id) {
        return Optional.ofNullable(memory.get(id));
    }
}
