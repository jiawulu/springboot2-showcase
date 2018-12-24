package com.wuzhong.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 中间操作
 * 有状态
 * 无状态
 *
 * 1.0-1.4 中的 java.lang.Thread
 * 5.0 中的 java.util.concurrent
 * 6.0 中的 Phasers 等
 * 7.0 中的 Fork/Join 框架
 * 8.0 中的 Lambda
 *
 * ForkJoinPool主要用来使用分治法(Divide-and-Conquer Algorithm)来解决问题
 * 那么使用ThreadPoolExecutor或者ForkJoinPool，会有什么性能的差异呢？
 * 首先，使用ForkJoinPool能够使用数量有限的线程来完成非常多的具有父子关系的任务，比如使用4个线程来完成超过200万个任务。但是，使用ThreadPoolExecutor时，是不可能完成的，因为ThreadPoolExecutor中的Thread无法选择优先执行子任务，需要完成200万个具有父子关系的任务时，也需要200万个线程，显然这是不可行的。
 *
 * 1. 当需要处理递归分治算法时，考虑使用ForkJoinPool。
 * 2. 仔细设置不再进行任务划分的阈值，这个阈值对性能有影响。
 * 3. Java 8中的一些特性会使用到ForkJoinPool中的通用线程池。在某些场合下，需要调整该线程池的默认的线程数量。
 */
public class Case5_forjoinpool {

    public static void main(String[] args) throws InterruptedException {


        System.out.println(">>>>>>>>>");
        ForkJoinPool forkJoinPool = new ForkJoinPool(1);
        ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
            IntStream.rangeClosed(1, 100)
                    .parallel()
                    .peek(i -> {
                        System.out.println("parallel > " + Thread.currentThread().getName() + ">>>" + i);
                    }).count();
        });
//        submit.complete(null);
        forkJoinPool.shutdown();

        try {
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        }


    }


}
