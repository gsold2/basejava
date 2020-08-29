package ru.javawebinar.basejava;

import java.util.*;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] values = randomArrayWithInt(9, 5);
        System.out.println(Arrays.toString(values));

        Arrays.stream(values).distinct()
                .sorted()
                .forEach(System.out::print);
        System.out.print("\n");
        System.out.println(minValue(values));

        System.out.println(Arrays
                .stream(values)
                .sum());
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
        return Arrays.stream(values).distinct()
                .boxed()
                .sorted()
                .reduce((first, second) -> first * 10 + second)
                .get();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.partitioningBy(x -> x % 2 == 0),
                        (Map<Boolean, List<Integer>> result) ->
                                result.get(result.get(false).size() % 2 != 0)
                ));
    }
}
