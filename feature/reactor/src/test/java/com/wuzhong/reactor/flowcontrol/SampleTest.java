package com.wuzhong.reactor.flowcontrol;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * The groupBy(), buffer(), and window() operators aggregate inputs and consolidate them into chunks, based on their size, time period, or condition.
 */
public class SampleTest {

    @Test
    public  void testSample() throws InterruptedException {
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long,
                Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0)
                sink.complete();
            else
                sink.next(state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        Flux<Long> take = fibonacciGenerator.delayElements(Duration.ofMillis(100));

        Flux<Long> sample = take.sample(Duration.ofSeconds(1));

        CountDownLatch latch = new CountDownLatch(1);

        sample.subscribe(i-> {
            System.out.println(i);
        }, e -> {
            e.printStackTrace();
        }, () -> {
            latch.countDown();
        });

        latch.await();


    }

}
