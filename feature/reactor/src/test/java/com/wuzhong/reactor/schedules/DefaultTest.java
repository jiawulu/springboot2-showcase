package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import static com.wuzhong.reactor.schedules.ScheduleHelper.createFlux;
import static com.wuzhong.reactor.schedules.ScheduleHelper.print;

public class DefaultTest {

    @Test
    public void testReactorThread() throws Exception {

        Flux<Long> fibonacciGenerator =
                createFlux();

        fibonacciGenerator
                .filter(x -> {
                    print("2. Executing Filter");
                    return x < 100;
                })
                .doOnNext(x -> print("3. Next value is  " + x))
                .doFinally(x -> print("5. Closing "))
                .subscribe(x -> print("4. Sub received : " + x));

    }



}
