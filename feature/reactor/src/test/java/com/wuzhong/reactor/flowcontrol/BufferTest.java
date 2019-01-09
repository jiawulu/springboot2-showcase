package com.wuzhong.reactor.flowcontrol;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BufferTest {

    @Test
    public  void testBuffer1(){
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long,
                Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0)
                sink.complete();
            else
                sink.next(state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
        Flux<Long> take = fibonacciGenerator.take(20);

        Flux<List<Long>> buffer = take.buffer(6);

        buffer.subscribe(list -> {
            System.out.println(list);
        });
    }


    @Test
    public  void testBuffer2(){
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long,
                Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0)
                sink.complete();
            else
                sink.next(state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
        Flux<Long> take = fibonacciGenerator.take(20);

        /**
         * skip表示从第几位开始新的buffer，默认和buffersize一致
         */
        Flux<List<Long>> buffer = take.buffer(6,5);

        buffer.subscribe(list -> {
            System.out.println(list);
        });
    }

    @Test
    public  void testBuffer3(){
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long,
                Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0)
                sink.complete();
            else
                sink.next(state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
        Flux<Long> take = fibonacciGenerator.take(100);

        /**
         * skip表示从第几位开始新的buffer，默认和buffersize一致
         */
        Flux<List<Long>> buffer = take.buffer(Duration.ofNanos(10));

        buffer.subscribe(list -> {
            System.out.println(list);
        });
    }

}
