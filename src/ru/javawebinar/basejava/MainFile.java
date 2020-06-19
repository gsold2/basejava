package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("./");
        printDirectoriesDeeply(dir);
    }

    public static void printDirectoriesDeeply(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                printDirectoriesDeeply(file);
            } else {
                System.out.println(file);
            }
        }
    }
}
