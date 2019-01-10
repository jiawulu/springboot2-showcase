package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;

import static com.wuzhong.reactor.schedules.ScheduleHelper.createFlux;
import static com.wuzhong.reactor.schedules.ScheduleHelper.print;

/**
 * System.setProperty("reactor.schedulers.defaultPoolSize","100");
 */
public class CustomTest {

    @Test
    public void testReactorThread() throws Exception {

        System.setProperty("reactor.schedulers.defaultPoolSize","100");

        Scheduler scheduler = Schedulers.fromExecutor(Executors.newCachedThreadPool());

        Flux<Long> fibonacciGenerator = createFlux();

        fibonacciGenerator
                .filter(x -> {
                    print("2. Executing Filter");
                    return true;
                })
                .delayElements(Duration.ofNanos(10),scheduler)
//                .publishOn(Schedulers.parallel())
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribe(x -> print("4. Sub received : " + x));

        ScheduleHelper.sleep(600);
    }


}
