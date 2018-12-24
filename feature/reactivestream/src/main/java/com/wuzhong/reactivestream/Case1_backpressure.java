package com.wuzhong.reactivestream;

import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * 背压
 */
public class Case1_backpressure {

    public static void main(String[] args) throws InterruptedException {

        //TODO 默认256个buffer，调整到10
        //定义一个发布者
        SubmissionPublisher publisher = new SubmissionPublisher(ForkJoinPool.commonPool(),64);

        //绑定订阅关系
        publisher.subscribe(new Flow.Subscriber() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("onSubscribe");
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Object item) {
                System.out.println("onNext");
                System.out.println(item);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * TODO 请求下一个数据， 根据消费能力请求下一个数据
                 */
                this.subscription.request(1);
                System.out.println("after request another one!");
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
        });

        //TODO submit 是阻塞的，默认256个缓冲空间
        for (int i = 0; i < 1000; i++) {
            System.out.println("publish " + i);
            publisher.submit("hello world " + i);
        }

        publisher.close();

        Thread.currentThread().join(3000);


    }

}
