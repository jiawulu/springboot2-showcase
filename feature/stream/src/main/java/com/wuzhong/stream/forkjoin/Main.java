package com.wuzhong.stream.forkjoin;

import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {

        long[] numbers = LongStream.rangeClosed(1, 1000).toArray();
        Calculator calculator = new ForLoopCalculator();
        System.out.println(calculator.sumUp(numbers)); // 打印结果500500

        Calculator calculator2 = new ExecutorServiceCalculator();
        System.out.println(calculator2.sumUp(numbers)); // 打印结果500500

        Calculator calculator3 = new ForkJoinCalculator();
        System.out.println(calculator3.sumUp(numbers)); // 打印结果500500
    }
}