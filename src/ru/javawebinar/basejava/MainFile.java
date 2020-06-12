package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("..\\basejava");
        writetFiles(dir);
    }

    public static void writetFiles(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                writetFiles(file);
            } else {
                System.out.println(file);
            }
        }
    }
}
