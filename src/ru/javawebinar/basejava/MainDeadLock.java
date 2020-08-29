package ru.javawebinar.basejava;

public class MainDeadLock {
    public final static Object one = new Object();
    public final static Object two = new Object();

    public static void main(String[] args) {
        threadWithParametrs(one, two);
        threadWithParametrs(two, one);
    }

    public static void threadWithParametrs(Object one, Object two) {
        new Thread(() ->
        {
            System.out.println("Начало работы нити" + " -> " + Thread.currentThread().getName());
            synchronized (one) {
                System.out.println(Thread.currentThread().getName() + " захватила " + one);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (two) {
                    System.out.println(Thread.currentThread().getName() + " захватила " + two);
                }
            }
        }).start();
    }
}

