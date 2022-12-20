package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ContentReader {
    private final File file;

    public ContentReader(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        StringBuilder out = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    out.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public synchronized String content() {
        return getContent(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(data -> data < 0x80);
    }
}
