package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    private final Cache cache = new Cache();

    @Test
    public void whenAddRight() {
        Base userFirst = new Base(1, 0);
        assertThat(cache.add(userFirst)).isTrue();
        assertThat(cache.getById(1).get()).isEqualTo(userFirst);
    }

    @Test
    public void whenAddDouble() {
        Base userFirst = new Base(1, 0);
        Base userSecond = new Base(1, 0);
        cache.add(userFirst);
        assertThat(cache.add(userSecond)).isFalse();
    }

    @Test
    public void whenDelete() {
        Base user = new Base(1, 0);
        cache.add(user);
        cache.delete(user);
        assertThat(cache.getById(1)).isEmpty();
    }

    @Test
    public void whenUpdateSuccess() {
        Base userFirst = new Base(1, 0);
        userFirst.setName("UserFirst");
        cache.add(userFirst);
        Base userSecond = new Base(1, 0);
        userSecond.setName("UserSecond");
        assertThat(cache.update(userSecond)).isTrue();
        assertThat(cache.getById(1).get().getVersion()).isEqualTo(1);
        assertThat(cache.getById(1).get().getName()).isEqualTo("UserFirst");
    }

    @Test
    public void whenUpdateGenThrow() {
        Base userFirst = new Base(1, 0);
        cache.add(userFirst);
        Base userSecond = new Base(1, 1);
        assertThatThrownBy(() -> cache.update(userSecond))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
    }
}