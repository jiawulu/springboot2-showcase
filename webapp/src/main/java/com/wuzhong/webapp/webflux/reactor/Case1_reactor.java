package com.wuzhong.webapp.webflux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Case1_reactor {

    public static void main(String[] args) {

        String[] strings = {"a","b","c"};

        Flux.fromArray(strings).map(s -> {return "> " + s;}).subscribe( s -> {
            System.out.println(s);
        });

        Mono.just("123");


    }

}
