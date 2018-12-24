package com.wuzhong.stream.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * http://blog.dyngr.com/blog/2016/09/15/java-forkjoinpool-internals/
 */
public class ForkJoinCalculator implements Calculator {
    private ForkJoinPool pool;

    private static class SumTask extends RecursiveTask<Long> {
        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            // 当需要计算的数字小于6时，直接计算结果
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
                // 否则，把任务一分为二，递归计算
            } else {
                int middle = (from + to) / 2;
                SumTask taskLeft = new SumTask(numbers, from, middle);
                SumTask taskRight = new SumTask(numbers, middle + 1, to);
                /**
                 fork()：开启一个新线程（或是重用线程池内的空闲线程），将任务交给该线程处理。
                 join()：等待该任务的处理线程处理完毕，获得返回值。
                 以上模型似乎可以（？）解释 ForkJoinPool 能够多线程执行的事实，但有一个很明显的问题
                 当任务分解得越来越细时，所需要的线程数就会越来越多，而且大部分线程处于等待状态。
                 */
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }
        }
    }

    public ForkJoinCalculator() {
        // 也可以使用公用的 ForkJoinPool：
        // pool = ForkJoinPool.commonPool()
        pool = new ForkJoinPool();
    }

    @Override
    public long sumUp(long[] numbers) {
        return pool.invoke(new SumTask(numbers, 0, numbers.length - 1));
    }
}