package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static com.wuzhong.reactor.schedules.ScheduleHelper.createFlux;
import static com.wuzhong.reactor.schedules.ScheduleHelper.print;
import static com.wuzhong.reactor.schedules.ScheduleHelper.sleep;

/**
 * delayElement 之后的执行线程在 parallel
 */
public class ImmediateTest {

    @Test
    public void testReactorThread() throws Exception {

        Flux<Long> fibonacciGenerator = createFlux();

        fibonacciGenerator
                .subscribeOn(Schedulers.single())
                .filter(x -> {
                    print("2. Executing Filter");
                    return x < 100;
                })
                .subscribeOn(Schedulers.single())
                .delayElements(Duration.ofNanos(1), Schedulers.immediate())
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribe(x -> print("4. Sub received : " + x));

        sleep(500);

    }

    @Test
    public void testReactorThread2() throws Exception {

        Flux<Long> fibonacciGenerator = createFlux();

        fibonacciGenerator
                .delayElements(Duration.ofNanos(1))
                .filter(x -> {
                    print("2. Executing Filter");
                    return x < 100;
                })
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribe(x -> print("4. Sub received : " + x));

    }


}
