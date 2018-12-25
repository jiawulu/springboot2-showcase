package com.wuzhong.webflux.servlet;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(123);
        });

        future.get();

        System.out.println(">>> end ");

    }

}
