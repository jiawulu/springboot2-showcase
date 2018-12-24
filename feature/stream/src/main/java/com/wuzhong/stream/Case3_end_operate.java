package com.wuzhong.stream;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 中间操作
 * 有状态
 * 无状态
 */
public class Case3_end_operate {

    public static void main(String[] args) {

        System.out.println("reduce usage");
        /**
         * reduce as sum api
         */
        OptionalInt reduce = IntStream.of(1, 2, 3, 4).reduce((i1, i2) -> i1 + i2);
        System.out.println(reduce.getAsInt());
        System.out.println(reduce.orElse(100));
        Optional<String> reduce2 = Stream.of(1, 2, 3, 4).map(String::valueOf).reduce((i1, i2) -> {
            return String.valueOf(i1 + i2);
        });
        System.out.println(reduce2.get());

        int reduce1 = IntStream.of(1, 2, 3, 4).reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(reduce1);

        System.out.println("max/count usage");
        System.out.println(IntStream.of(1, 2, 3, 4).max().getAsInt());
        System.out.println(IntStream.of(1, 2, 3, 4).count());
    }


}
