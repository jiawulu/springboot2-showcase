package com.wuzhong.reactor.flowcontrol;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Strategy
 * Description
 * IGNORE
 * This strategy ignores the limits of the subscriber for backpressure and keeps delivering the next event to the subscriber.
 * BUFFER
 * This strategy combines the undelivered events in a buffer. Events from the buffer are delivered when the subscriber requests the next events.
 * DROP
 * This strategy silently drops undelivered events that are produced. The subscriber will only get a newly produced event when the next request is raised.
 * LATEST
 * This strategy keeps the latest event raised in the buffer. The subscriber will only get the latest produced event when the next request is raised.
 * ERROR
 * This strategy raises an OverFlowException if the producer raises more than the events requested by the subscriber.
 */
public class BackPressureTest {

    @Test
    public void testBasic() throws InterruptedException {

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
        assertTrue(await);


    }

    @Test
    public void testOk() throws InterruptedException {

        Flux<Integer> flux = createFlux(FluxSink.OverflowStrategy.ERROR);
        consume(flux, true, 10);

    }

    @Test()
    public void testOverflow(){
        consume(createFlux(FluxSink.OverflowStrategy.ERROR), false, 10);
    }

//    http://zhangtielei.com/posts/blog-rxjava-backpressure.html 把最新的一条记录缓存下来，下次如果有令牌上来拿则返回
    @Test()
    public void testLATEST(){
        Flux<Integer> flux = createFlux(FluxSink.OverflowStrategy.LATEST);
        Flux<Integer> integerFlux = flux.onBackpressureLatest();
        consume(integerFlux, false, 1);
    }

    @Test()
    public void testDrop(){
        Flux<Integer> flux = createFlux(FluxSink.OverflowStrategy.DROP);
        Flux<Integer> integerFlux = flux.onBackpressureDrop(i -> {
            System.out.println("drop " + i);
        });
        consume(integerFlux, false, 10);
//        sleep(500);
    }


    @Test()
    public void testBuffer(){
        Flux<Integer> flux = createFlux(FluxSink.OverflowStrategy.BUFFER);
        Flux<Integer> integerFlux = flux.onBackpressureBuffer(2, i -> {
            System.out.println("> drop " + i);
        }, BufferOverflowStrategy.DROP_OLDEST);
//        Flux<Integer> integerFlux2 = integerFlux.onBackpressureDrop(i -> {
//            System.out.println("< drop " + i);
//        });
        consume(integerFlux, false, 1);
//        sleep(500);
    }

    /**
     * 忽律消费者是否能处理，全部投递过去
     */
    @Test()
    public void testIgnore(){
        consume(createFlux(FluxSink.OverflowStrategy.IGNORE), false, 10);
    }


    private Flux<Integer> createFlux(FluxSink.OverflowStrategy strategy) {
        return Flux.create(sink -> {
                int length = 100;
                for (int i = 0; i < length; i++) {
                    sink.next(i);
                }
                sink.complete();
            }, strategy);
    }

    private void consume(Flux<Integer> flux , boolean fetchNext , int fetchCount)  {
        CountDownLatch latch = new CountDownLatch(1);
        flux.subscribe(new BaseSubscriber<Integer>() {

            private Subscription subscription;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                this.subscription = subscription;
                request(fetchCount);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println(value);
                if (fetchNext){
                    this.subscription.request(fetchCount);
                }
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                throwable.printStackTrace();
                latch.countDown();
            }

            @Override
            protected void hookOnComplete() {
                latch.countDown();
            }
        });
        try {
            assertTrue(latch.await(1L, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
