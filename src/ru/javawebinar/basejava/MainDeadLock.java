package ru.javawebinar.basejava;

public class MainDeadLock {

    public final static Object one = new Object(), two = new Object();
    public static boolean flageOne = false, flageTwo = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (one) {
                flageOne = true;
                System.out.println("Поток t1 зашел в synchronized (one)");
                while (!flageTwo) {
                    System.out.println("Поток t1 ожидает пока t2 synchronized (two)");
                }
                System.out.println("Поток t1 пытается synchronized (two)");
                synchronized (two) {
                    System.out.println("Завершение работы t1");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (two) {
                flageTwo = true;
                System.out.println("Поток t2 зашел в synchronized (two)");
                while (!flageOne) {
                    System.out.println("Поток t2 ожидает пока t1 synchronized (one)");
                }
                System.out.println("Поток t2 пытается synchronized (one)");
                synchronized (one) {
                    System.out.println("Завершение работы t2");
                }
            }
        });

        t1.start();
        t2.start();
    }
}

