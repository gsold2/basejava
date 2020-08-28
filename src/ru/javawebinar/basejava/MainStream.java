package ru.javawebinar.basejava;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] values = randomArrayWithInt(9, 4);
        System.out.println(Arrays.toString(values));

        Arrays.stream(values).distinct()
                .sorted()
                .forEach(System.out::print);
        System.out.print("\n");
        System.out.println(minValue(values));

        System.out.println(Arrays
                .stream(values)
                .reduce(0, Integer::sum));
        System.out.print(oddOrEven(Arrays
                .stream(values)
                .boxed()
                .collect(Collectors.toList())));
    }

    public static int randomInt(int max) {
        return (int) (Math.random() * max + 1);
    }

    public static int[] randomArrayWithInt(int max, int length) {
        int[] arrayInt = new int[length];
        for (int i = 0; i < length; i++) {
            arrayInt[i] = randomInt(max);
        }
        return arrayInt;
    }

    static int minValue(int[] values) {
        AtomicInteger count = new AtomicInteger();
        AtomicInteger minValue = new AtomicInteger();
        Arrays.stream(values).distinct()
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(i -> {
                    minValue.addAndGet(i * (int) Math.pow(10, count.get()));
                    count.incrementAndGet();
                });
        return minValue.get();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        AtomicInteger count = new AtomicInteger();
        List<Integer> evenNumbers = new ArrayList<>();
        List<Integer> notEvenNumbers = new ArrayList<>();
        integers.forEach(integer -> {
            if (integer % 2 == 0) {
                evenNumbers.add(integer);
            } else {
                notEvenNumbers.add(integer);
            }
            count.addAndGet(integer);
        });
        return (count.get() % 2 == 0) ? notEvenNumbers : evenNumbers;
    }
}
