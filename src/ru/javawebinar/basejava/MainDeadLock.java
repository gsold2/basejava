package ru.javawebinar.basejava;

public class MainDeadLock {

    public final static Object one = new Object(), two = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (one) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (two) {
                    System.out.println("Завершение работы t1");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (two) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (one) {
                    System.out.println("Завершение работы t2");
                }
            }
        });

        t1.start();
        t2.start();
    }
}

