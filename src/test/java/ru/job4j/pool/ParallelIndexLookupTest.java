package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelIndexLookupTest {

    @Test
    void whenLineSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThat(ParallelIndexLookup.findIndex(array, 8)).isEqualTo(7);
    }

    @Test
    void whenRecursiveSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(ParallelIndexLookup.findIndex(array, 12)).isEqualTo(11);
    }

    @Test
    void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(ParallelIndexLookup.findIndex(array, 20)).isEqualTo(-1);
    }

    @Test
    void whenSearchString() {
        String[] array = {"Ivan", "Stepan", "John", "Tom", "Anna", "Valentina"};
        assertThat(ParallelIndexLookup.findIndex(array, "Anna")).isEqualTo(4);
    }

    @Test
    void whenSearchUser() {
        User[] array = new User[6];
        User userIvan = new User("Ivan", "ivan@mail.ru");
        array[0] = userIvan;
        User userStepan = new User("Stepan", "stepan@mail.ru");
        array[1] = userStepan;
        User userJohn = new User("John", "john@gmail.com");
        array[2] = userJohn;
        User userTom = new User("Tom", "tom@gmail.com");
        array[3] = userTom;
        User userAnna = new User("Anna", "anna@gmail.com");
        array[4] = userAnna;
        User userValentina = new User("Valentina", "valentina@mail.ru");
        array[5] = userValentina;
        assertThat(ParallelIndexLookup.findIndex(array, userTom)).isEqualTo(3);
    }
}