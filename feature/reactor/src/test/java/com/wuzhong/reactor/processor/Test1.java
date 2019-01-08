package com.wuzhong.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.*;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1. What are the limitations of DirectProcessor?
 *      run current thread, no backpressure
 * 2. What are the limitations of UnicastProcessor?
 *      only one subscriper
 * 3. What are the capabilities of EmitterProcessor?
 *      sync ， backpressure ， multiscrib
 * 4. What are the capabilities of ReplayProcessor?
 *      replay， multiscrib， backpressure， sync
 * 5. What are the capabilities of TopicProcessor?
 *      async ， backpressure ， multiscrib
 * 6. What are the capabilities of WorkQueueProcessor?
 *      async ， backpressure ， multiscrib， round-robin manner
 * 7. What is the difference between a hot publisher and a cold publisher?
 */
public class Test1 {

    @Test
    public void test(){

        DirectProcessor<Long> directProcessor = DirectProcessor.create();

        directProcessor.subscribe(l -> {
            System.out.println(Thread.currentThread().getName() + ">>" + l);
        });

        System.out.println(Thread.currentThread().getName() + ">>" );

        directProcessor.onNext(1L);
        directProcessor.onNext(11L);
        directProcessor.onNext(12L);
        directProcessor.onNext(13L);

        directProcessor.onComplete();


    }

    @Test
    public void test2(){
        DirectProcessor<Long> data = DirectProcessor.create();
        data.subscribe(t -> System.out.println(Thread.currentThread().getName() + ">>" + t),
                e -> e.printStackTrace(),
                () -> System.out.println("Finished"),
                s -> s.request(1));
        data.onNext(10L);
        data.onNext(11L);
        data.onNext(12L);



    }

    @Test
    public void test3(){
        UnicastProcessor<Long> data = UnicastProcessor.create();
        data.subscribe(t -> {
            System.out.println(Thread.currentThread().getName() + ">>" + t);
        });
        //有背压，但是只能有一个订阅者
//        data.subscribe(t -> {
//            System.out.println(t);
//        });
        data.sink().next(10L);
    }

    @Test
    public void test4(){
        EmitterProcessor<Long> data = EmitterProcessor.create(2);
        data.subscribe(t -> System.out.println( Thread.currentThread().getName() + "sub1>>" + t));
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        data.subscribe(t -> System.out.println( Thread.currentThread().getName() +"sub2>>" +t));
        sink.next(13L);
        sink.next(14L);
        sink.next(15L);
    }

    @Test
    public void testEmitterProcessor(){
        //每次获取1
        EmitterProcessor<Long> data = EmitterProcessor.create(1);
        data.subscribe(t -> {
            sleep(500);
            System.out.println( Thread.currentThread().getName() + "sub1>>" + t);
        });
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        sink.next(21L);
        sink.next(121L);
        sink.next(122L);
        data.subscribe(t -> {
            sleep(100);
            System.out.println( Thread.currentThread().getName() +"sub2>>" +t);
        });
        sink.next(13L);
        sink.next(14L);
        sink.next(15L);
    }

    private void sleep(long ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5(){
        //make cache size 3 for replay
        ReplayProcessor<Long> data = ReplayProcessor.create(3);
        data.subscribe(t -> System.out.println(  Thread.currentThread().getName() + "1 >> " + t));
        FluxSink<Long> sink = data.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        sink.next(13L);
        sink.next(14L);
        //先replay完毕再执行下一个
        data.subscribe(t -> System.out.println(Thread.currentThread().getName() +"2 >> " +t));
        data.subscribe(t -> System.out.println(Thread.currentThread().getName() +"3 >> " +t));
    }

    @Test
    public void testTopic() throws InterruptedException {

        TopicProcessor<Integer> topicProcessor = TopicProcessor.create();

        topicProcessor.subscribe(integer -> {
            System.out.println(Thread.currentThread().getName() + ">>" + integer);
        });
        topicProcessor.subscribe(integer -> {
            System.out.println(Thread.currentThread().getName() + ">>" + integer);
        });

        FluxSink<Integer> sink = topicProcessor.sink();
        sink.next(1);
        sink.next(2);
        sink.next(3);
        sink.complete();

        Thread.sleep(1000);


    }

    /**
     * 并发异步的，非阻塞的，多订阅者
     * @throws InterruptedException
     */
    @Test
    public void testTopic2() throws InterruptedException {

        TopicProcessor<Integer> topicProcessor = TopicProcessor.<Integer>builder().executor(Executors.newFixedThreadPool(2)).build();

        topicProcessor.subscribe(integer -> {
            System.out.println(Thread.currentThread().getName() + ">>" + integer);
        });
        topicProcessor.subscribe(integer -> {
            System.out.println(Thread.currentThread().getName() + ">>" + integer);
        });

        FluxSink<Integer> sink = topicProcessor.sink();
        sink.next(1);
        Thread.sleep(100);
        sink.next(2);
        Thread.sleep(100);
        sink.next(3);
        sink.complete();

        Thread.sleep(1000);


    }

    /**
     * a round-robin manner
     * @throws InterruptedException
     */
    @Test
    public void testWorkQueue() throws InterruptedException {

        WorkQueueProcessor<Integer> topicProcessor = WorkQueueProcessor.<Integer>builder().executor(Executors.newFixedThreadPool(2)).build();

        topicProcessor.subscribe(integer -> {
            System.out.println(Thread.currentThread().getName() + ">>1 " + integer);
        });
        topicProcessor.subscribe(integer -> {
            System.out.println(Thread.currentThread().getName() + ">>2 " + integer);
        });

        FluxSink<Integer> sink = topicProcessor.sink();
        sink.next(1);
        Thread.sleep(100);
        sink.next(2);
        Thread.sleep(100);
        sink.next(3);
        sink.complete();

        Thread.sleep(1000);


    }


}
