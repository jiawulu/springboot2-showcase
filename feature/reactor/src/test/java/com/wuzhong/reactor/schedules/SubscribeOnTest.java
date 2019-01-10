package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static com.wuzhong.reactor.schedules.ScheduleHelper.*;

/**
 * delayElement 之后的执行线程在 parallel
 */
public class SubscribeOnTest {

    @Test
    public void testReactorThread()  {

        Flux<Long> fibonacciGenerator = createFlux();

        fibonacciGenerator
                .subscribeOn(Schedulers.single())
                .filter(x -> {
                    print("2. Executing Filter");
                    return true;
                })
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribe(x -> print("4. Sub received : " + x));

        sleep(500);

    }

    @Test
    public void testReactorThread2() throws Exception {

        Flux<Long> fibonacciGenerator = createFlux();

        fibonacciGenerator
                .filter(x -> {
                    print("2. Executing Filter");
                    return true;
                })
                .publishOn(Schedulers.parallel())
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribeOn(Schedulers.single())
                .subscribe(x -> print("4. Sub received : " + x));

        sleep(500);

    }


}
