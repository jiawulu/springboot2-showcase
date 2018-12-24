package com.wuzhong.reactivestream;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

public class MyConsumber implements Flow.Subscriber<String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("onSubscribe");
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        System.out.println("consumer onNext " + item);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * TODO 请求下一个数据， 根据消费能力请求下一个数据
         */
        this.subscription.request(1);
        System.out.println("consumer after request another one!");
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        //TODO 可以取消发布
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
