package com.wuzhong.reactor;

import com.google.common.collect.Lists;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Flux1 {

    public static void main(String[] args) {

//        Flux.just("1");

//        consumer 是没有返回值的 InterfaceFunction
//        Flux.just(1, 2, 3).subscribe(System.out::println);

//        testFrom();
//
//        testDefer();
//
//        testGenerate();

//        testFibonacciFluxSink();

//        testMap();

//        reduce();

        concat();
    }


    public static void testFrom() {
        Flux.fromArray(new Integer[]{1, 1, 2, 3, 5, 8, 13});
        Flux.fromIterable(Arrays.asList("Red", "Blue", "Yellow", "Black"));
        //惰性
        Flux.fromStream(IntStream.range(1, 100).boxed());

        Flux.fromStream(Stream.generate(() -> {
            return "123";
        }).limit(12));
    }

    //Lazy
    public static void testDefer() {
        System.out.println("testDefer >>>>> ");
        Flux<String> defer = Flux.defer((() -> {
            System.out.println("flux defer >>> ");
            return Mono.just("123");
        }));
        defer.subscribe(System.out::println);
    }


    public static void testGenerate() {

        System.out.println("testGenerate >>>>> ");
        Flux<Object> generate = Flux.generate(() -> Tuples.of(0, 1),
                (state, sink) -> {
                    sink.next(state.getT1());
                    System.out.println("generate " + state.getT1());
                    return Tuples.of(state.getT2(), state.getT2() + state.getT1());
                });

        generate.take(10).subscribe(t -> {
            System.out.println("consume " + t);
        });

    }

    /**
     * This means that even if the subscriber has cancelled its subscription, the create API will continue to generate events.
     */
    public static void testFibonacciFluxSink() {
        System.out.println(">>>testFibonacciFluxSink");
        Flux<Long> fibonacciGenerator = Flux.create(e -> {
            long current = 1, prev = 0;
            AtomicBoolean stop = new AtomicBoolean(false);
            e.onCancel(() -> {
                stop.set(true);
                System.out.println("******* Stop Received ****** ");
            });
            while (current > 0) {
                e.next(current);
                System.out.println("generated " + current);
                long next = current + prev;
                prev = current;
                current = next;
            }
            e.complete();
        }, FluxSink.OverflowStrategy.IGNORE);
        List<Long> fibonacciSeries = new LinkedList<>();
        int size = 50;
        fibonacciGenerator.take(size).subscribe(t -> {
            System.out.println("consuming " + t);
            fibonacciSeries.add(t);
        });
        System.out.println(fibonacciSeries);
    }


    public static void testMap() {

        System.out.println("testMap>>>");

        Flux.just(1, 2, 3).map(integer -> {
            System.out.println("map for " + integer);
            return integer * integer;
        }).flatMap(integer -> {
            System.out.println("flatmap for " + integer);
            return Flux.just(integer, integer * 2, integer * 3);
        }).repeat(2).collectList().subscribe(integer -> {
            System.out.println(integer);
        });


    }

    static void collect() {
        //收集到容器里
        Flux.fromIterable(Lists.newArrayList(5, 4, 3, 9, 1))
                .collectSortedList((i1, i2) -> i1 - i2)
                .subscribe(System.out::println);

        Flux.fromIterable(Lists.newArrayList(5, 4, 3, 9, 1))
                .collectMap(i -> i % 2 == 0 ? "even" : "odd")
                .subscribe(System.out::println);

        Flux.fromIterable(Lists.newArrayList(5, 4, 3, 9, 1))
                .collectMultimap(i -> i % 2 == 0 ? "even" : "odd")
                .subscribe(System.out::println);
    }

    static void reduce() {

        Flux.fromIterable(Lists.newArrayList(5, 4, 3, 9, 1))
                .filter(i -> i > 2)
                .reduce((i1, i2) -> i1 * i2)
                .subscribe(System.out::println);

    }

    //连接
    static void concat() {


        Flux.fromIterable(Lists.newArrayList(5, 4, 3, 9, 1))
                .concatWith(Mono.just(100))
//                .filter(i -> i > 2)
                .skipWhile( i -> i>4)
//                .reduce((i1, i2) -> i1 * i2)
                .subscribe(System.out::println);

    }


}
