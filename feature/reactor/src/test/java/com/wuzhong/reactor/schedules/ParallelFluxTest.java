package com.wuzhong.reactor.schedules;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import static com.wuzhong.reactor.schedules.ScheduleHelper.*;

public class ParallelFluxTest {

    @Test
    public void test() {
        Flux<Long> fibonacciGenerator = createFlux();

        ParallelFlux<Long> parallel = fibonacciGenerator
                .filter(x -> {
                    print("2. Executing Filter");
                    return true;
                }).parallel();

        ParallelFlux<Long> longParallelFlux = parallel.runOn(Schedulers.parallel())
                .doOnNext(x -> print("3. Next value is  " + x));

        longParallelFlux.runOn(Schedulers.single())
                .subscribe(x -> print("4. Sub received : " + x));

        sleep(500);
    }


    @Test
    public void test2() {
        Flux<Long> fibonacciGenerator = createFlux();

        ParallelFlux<Long> parallel = fibonacciGenerator
                .filter(x -> {
                    print("2. Executing Filter");
                    return true;
                }).parallel();
        ParallelFlux<Long> longParallelFlux = parallel.runOn(Schedulers.parallel())
                .doOnNext(x -> print("3. Next value is  " + x));

        longParallelFlux.sequential().publishOn(Schedulers.single())
                .subscribe(x -> print("4. Sub received : " + x));

        sleep(500);
    }

}
