package com.wuzhong.stream;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Case1 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};

        System.out.println("Arrays.stream");
        Arrays.stream(nums).forEach(System.out::println);

        System.out.println("Arrays.stream");
        System.out.println(IntStream.of(nums).sum());

        System.out.println("rangeClosed");
        IntStream.rangeClosed(1, 5).forEach(System.out::println);

        System.out.println("new Random().ints().limit(100).forEach(System.out::println);");
        new Random().ints().limit(5).forEach(System.out::println);

        System.out.println("Stream.generate");
        Stream.generate(() -> new Random().nextInt()).limit(2).forEach(System.out::println);

        System.out.println("Stream.iterable");
        Stream.iterate(1, s -> 2*s).limit(5).forEach(System.out::println);
//
//        Optional<int[]> first = Stream.of(nums).findFirst();
//        Optional<Integer> first2 = Stream.of(1,2,3,4).findFirst();
//
//        Stream.of(1,2,3,4);
//        System.out.println(first.get());
//        System.out.println(first2.get());
//
//        new Random().ints().limit(100).forEach(System.out::println);
//
//        IntStream.rangeClosed(1, 100).forEach(System.out::println);



    }

}
