package com.wuzhong.stream;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 中间操作
 * 有状态
 * 无状态
 */
public class Case4_parallel {

    public static void main(String[] args) {

        //多次调用串行和并行，以最后一次为准
        long count = IntStream.rangeClosed(1, 100)
                .parallel()
                .peek(i -> {
                    System.out.println("parallel > " + Thread.currentThread().getName() + ">>>" + i);
                })
                .sequential()
                .peek(i -> {
                    System.out.println("sequential > " + Thread.currentThread().getName() + ">>>" + i);
                })
                .count();


        //1. 设置并发的数量 https://www.jianshu.com/p/bd825cb89e00
        //2. 使用并行流使用的是一个相同的线程池
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","100");
        count = IntStream.rangeClosed(1, 2)
                .parallel()
                .peek(i -> {
                    System.out.println("parallel > " + Thread.currentThread().getName() + ">>>" + i);
                })
                .count();

        System.out.println(count);


    }


}
