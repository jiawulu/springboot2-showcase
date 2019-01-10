package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import static com.wuzhong.reactor.schedules.ScheduleHelper.createFlux;
import static com.wuzhong.reactor.schedules.ScheduleHelper.print;
import static com.wuzhong.reactor.schedules.ScheduleHelper.sleep;

public class BroadcastTest {

    @Test
    public void test() {
        Flux<Long> fibonacciGenerator = createFlux().take(5);

        fibonacciGenerator.subscribe(i -> {
            print("1 > " + i);
        });

        //执行完再接着执行下一个
        System.out.println(">>>>>");
        fibonacciGenerator.subscribe(i -> {
            print("2 > " + i);
        });

        sleep(500);
    }

    @Test
    public void test2() {
        Flux<Long> fibonacciGenerator = createFlux().take(10).replay(3).refCount(2);

        fibonacciGenerator.subscribe(i -> {
            print("1 > " + i);
        });
        //执行完再接着执行下一个
        System.out.println(">>>>>");
        fibonacciGenerator.subscribe(i -> {
            print("2 > " + i);
        });

        fibonacciGenerator.subscribe(i -> {
            print("3 > " + i);
        });

        sleep(5000);
    }


}
