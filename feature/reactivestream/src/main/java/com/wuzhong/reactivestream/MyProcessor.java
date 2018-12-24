package com.wuzhong.reactivestream;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {

    private Flow.Subscription subscription;


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("processor on next " + item);
        this.submit(" >>> " + String.valueOf(item));
        subscription.request(1);
        System.out.println("processor after request next");
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        System.out.println("on end");
    }
}
