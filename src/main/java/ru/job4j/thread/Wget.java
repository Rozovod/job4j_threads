package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String outFile;

    public Wget(String url, int speed, String outFile) {
        this.url = url;
        this.speed = speed;
        this.outFile = outFile;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(outFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long timeBegin = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData == speed) {
                    long timeReal = System.currentTimeMillis() - timeBegin;
                    if (timeReal < 1000) {
                        Thread.sleep(1000 - timeReal);
                    }
                    downloadData = 0;
                    timeBegin = System.currentTimeMillis();
                }
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validationArgs(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Введены не все параметры.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validationArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String outFile = args[2];
        Thread wget = new Thread(new Wget(url, speed, outFile));
        wget.start();
        wget.join();
    }
}
