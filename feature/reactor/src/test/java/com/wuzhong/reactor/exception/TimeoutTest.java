package com.wuzhong.reactor.exception;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class TimeoutTest {

    /**
     * The timeout fails when there is no response received in the specific time interval.
     * Once the timeout expires, it triggers the error callback configured for the subscriber.
     */
    @Test
    public void test(){

        Flux<Object> flux = createTimeoutFlux(1000);

        flux
                .timeout(Duration.ofMillis(500))
                .subscribe(System.out::println);

    }

    @Test
    public void test2(){

        Flux<Object> flux = createTimeoutFlux(1000);

        flux
                .timeout(Duration.ofMillis(500))
                .retry(3)
                .subscribe(System.out::println);

    }

    private Flux<Object> createTimeoutFlux(long ms) {
        return Flux.create(emitter -> {
            sleep(ms);
            emitter.next(1);
            sleep(ms);
            emitter.next(2);
            sleep(ms);
            emitter.complete();
        });
    }

    private void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
