package com.wuzhong.reactor.flowcontrol;

import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BackPressureTest {

    @Test
    public void testExc() throws InterruptedException {

        Flux<Object> flux = Flux.create(sink -> {

            int length = 100;
            for (int i = 0; i < length; i++) {
                sink.next(i);
            }

            sink.complete();

        });

        CountDownLatch countDownLatch = new CountDownLatch(1);
        flux.subscribe(new BaseSubscriber<Object>() {

            private Subscription subscription;

//            The subscriber requested a single event in the subscribe hook
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
//                super.hookOnSubscribe(subscription);
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            protected void hookOnNext(Object value) {
                super.hookOnNext(value);
                System.out.println(value);
//                invoke next element
//                subscription.request(1);
            }

            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1L, TimeUnit.SECONDS);
        Assert.assertTrue(await);


    }

}
