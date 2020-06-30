package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("./");
        printDirectoriesDeeply(dir, 0);
    }

    public static void printDirectoriesDeeply(File directory, int i) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (!file.isDirectory()) {
                printFile(file, i);
            }
        }
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                printFile(file, i);
                printDirectoriesDeeply(file, i + file.getName().length());
            }
        }
    }

    public static void printFile(File file, int i) {
        for (int count = 0; count <= i; count++) {
            System.out.print(" ");
        }
        System.out.println(file.getName());
    }
}
