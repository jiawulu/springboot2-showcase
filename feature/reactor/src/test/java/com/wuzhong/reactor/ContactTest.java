package com.wuzhong.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class ContactTest {

    @Test
    public void test() {

        Flux.just("1", "3")
                .concatWith(Flux.just("4"))
                .subscribe(System.out::println);


    }

    @Test
    public void test2() {

        Flux.create(emiter -> {
            try {
                System.out.println(1);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            emiter.next("123");
            try {
                System.out.println(2);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            emiter.complete();

            //FIXME conactWith 一定是等前面的流完成了再继续往下走的
        }).concatWith(Flux.create(emiter -> {
            try {
                System.out.println(3);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            emiter.next("456");
            try {
                System.out.println(4);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            emiter.complete();
        }))
                .subscribe(System.out::println);


    }


}