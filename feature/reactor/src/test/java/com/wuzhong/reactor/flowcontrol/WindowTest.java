package com.wuzhong.reactor.flowcontrol;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;

/**
 * The groupBy(), buffer(), and window() operators aggregate inputs and consolidate them into chunks, based on their size, time period, or condition.
 */
public class WindowTest {

    @Test
    public  void testWindow(){
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long,
                Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0)
                sink.complete();
            else
                sink.next(state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
        Flux<Long> take = fibonacciGenerator.take(20);

        Flux<Flux<Long>> window = take.window(6);

//        Flux<Flux<Long>> window = take.windowWhile(x -> x < 20);

//        TODO until not work
//        Flux<Flux<Long>> window = take.windowUntil(x -> x > -1);

        //concatMap 和 flatMap 是对立的
        Flux<Long> longFlux = window.concatMap(f -> f);

        longFlux.subscribe(System.out::println);

    }

}
