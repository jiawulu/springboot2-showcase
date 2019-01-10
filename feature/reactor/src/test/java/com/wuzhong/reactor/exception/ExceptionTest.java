package com.wuzhong.reactor.exception;

import org.junit.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

public class ExceptionTest {

    @Test(expected = Exception.class)
    public void test() {

        Flux<Object> flux = createExceptionFlux();

        flux.subscribe(System.out::println);

    }

    @Test
    public void test2() {

        Flux<Object> flux = createExceptionFlux();

        flux.subscribe(obj -> {
            System.out.println(obj);
        }, e -> {
            e.printStackTrace();
        });
    }

    @Test
    public void test3() {

        Flux<Object> flux = createExceptionFlux();

        flux.subscribe(obj -> {
            throw new RuntimeException("on subscribe");
        }, e -> {
            e.printStackTrace();
        });

    }

    @Test
    public void test4() {

        Flux<Object> flux = createExceptionFlux();

        flux.subscribe(obj -> {
            throw new RuntimeException("on subscribe");
        }, e -> {
            Throwable unwrap = Exceptions.unwrap(e);
//            e.printStackTrace();
            System.out.println(">>>>>");
            unwrap.printStackTrace();
        });

    }

    @Test
    public void test5() {

        Flux<Object> flux = createExceptionFlux();

        flux
                .doFinally(t -> {
                    System.out.println("finally");
                })
                .doOnTerminate(() -> {
                    System.out.println("Terminate");
                })
                .doOnError(e -> {
                    System.out.println(Thread.currentThread().getName() + " doOnError");
                    e.printStackTrace();
                })
                .subscribe(obj -> {
                    System.out.println(obj);
                }, e -> {
                    System.out.println(Thread.currentThread().getName() + " subscribe");
                    e.printStackTrace();
                });

    }


    @Test
    public void test6() {

        Flux<Object> flux = createExceptionFlux();

        flux
                .onErrorReturn(-1)
                .subscribe(obj -> {
                    System.out.println(obj);
                }, e -> {
                    System.out.println(Thread.currentThread().getName() + " subscribe");
                    e.printStackTrace();
                });

    }

    @Test
    public void test7() {

        Flux<Object> flux = createExceptionFlux();

        flux
                .onErrorResume( e -> {
                    return Flux.just(7,8,9);
                })
                .subscribe(obj -> {
                    System.out.println(obj);
                }, e -> {
                    System.out.println(Thread.currentThread().getName() + " subscribe");
                    e.printStackTrace();
                });

    }

    @Test
    public void test8() {

        Flux<Object> flux = createExceptionFlux();

        //The onErrorMap operator is configured to throw IllegalStateException when any error is received by the subscriber.
        flux
                .onErrorMap( e -> {
                    return new RuntimeException("xxxxx",e);
                })
                .subscribe(obj -> {
                    System.out.println(obj);
                }, e -> {
                    System.out.println(Thread.currentThread().getName() + " subscribe");
                    e.printStackTrace();
                });

    }


    private Flux<Object> createExceptionFlux() {
        return Flux.create(emitter -> {
            emitter.next(1);
            emitter.next(2);
            throw new RuntimeException();
        });
    }


}
