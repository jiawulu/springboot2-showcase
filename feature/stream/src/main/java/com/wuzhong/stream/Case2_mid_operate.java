package com.wuzhong.stream;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 中间操作
 * 有状态
 * 无状态
 */
public class Case2_mid_operate {

    public static void main(String[] args) {

        System.out.println("无状态，返回一个新的stream");
        //1. map mapToXxx
        IntStream.of(1, 2).mapToLong(Long::valueOf).forEach(System.out::println);

        //2. FIXME flatMap , 一个到多个
        Stream.of("ab", "cde").flatMap(s -> s.chars().boxed()).
                forEach(action -> System.out.println(action + " >>> " + (char) action.intValue()));

        //3. filter
        Stream.of("ab", "cde").filter(s -> s.startsWith("a")).peek(System.out::println).forEach(System.out::println);

        //4. peek 和foreach比较像

        //5. unordered
        Stream.of("ab", "cde").unordered().forEach(System.out::println);
        System.out.println("有状态");

        //6. distinct
        Stream.of("ab", "cde").sorted().forEach(System.out::println);

        //7. sorted

        //8. limit
        new Random().ints().filter(i -> i > 0).limit(100).forEach(System.out::println);

        //9. skip

    }


}
