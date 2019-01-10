package com.wuzhong.reactor.schedules;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class ScheduleHelper {

    public static void print(String text) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + text);
    }

    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Flux<Long> createFlux() {
        return Flux.generate(() -> Tuples.<Long,
                Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0)
                sink.complete();
            else
                sink.next(state.getT1());
            print("1. Generating next of " + state.getT2());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
    }

}
