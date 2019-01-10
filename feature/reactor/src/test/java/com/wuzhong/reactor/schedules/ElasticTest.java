package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static com.wuzhong.reactor.schedules.ScheduleHelper.createFlux;
import static com.wuzhong.reactor.schedules.ScheduleHelper.print;

public class ElasticTest {

    @Test
    public void testReactorThread() throws Exception {

        System.setProperty("reactor.schedulers.defaultPoolSize","100");

        Flux<Long> fibonacciGenerator = createFlux();

        fibonacciGenerator
                .filter(x -> {
                    print("2. Executing Filter");
                    return true;
                })
                .delayElements(Duration.ofNanos(10), Schedulers.elastic())
                .window(2)
//                .publishOn(Schedulers.parallel())
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribe(x -> print("4. Sub received : " + x.blockLast()));

        ScheduleHelper.sleep(600);
    }

}
