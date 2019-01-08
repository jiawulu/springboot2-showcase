package com.wuzhong.reactor;

import reactor.core.publisher.Mono;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Mono1 {

    public static void main(String[] args) {
        just();

        from();
    }

    static void just(){
        Mono.justOrEmpty(Optional.of(123)).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.empty()).subscribe( s -> {
            System.out.println(">>>>");
            System.out.println(s);
        });
    }

    static void from(){

        Mono.fromCallable(() -> {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("produce date");
            return new Date().toString();
        }).subscribe(s -> {
            System.out.print(">>>> date : ");
            System.out.println(s);
        });

        Mono.fromSupplier(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("produce date2");
            return new Date().toString();
        }).subscribe(s -> {
            System.out.print(">>>> date2 : ");
            System.out.println(s);
        });

        CompletableFuture<Object> objectCompletableFuture = new CompletableFuture<>();
        Mono.fromFuture(objectCompletableFuture).subscribe(s -> {
            System.out.print(">>>> date3 : ");
            System.out.println(s);
        });
        objectCompletableFuture.complete("123");

        Mono.fromRunnable( () -> {
            System.out.println("from runnable");
        }).subscribe( s -> {
            System.out.println("consume runnalbe " + s);
        });

        Mono.from(Mono.justOrEmpty("123")).subscribe(s -> System.out.println(s));

        Mono.defer(() -> {
            return Mono.just("abc");
        }).subscribe(System.out::println);

        Mono.create(monoSink -> {
            monoSink.success("hello");
        }).subscribe(System.out::println);

    }

}
