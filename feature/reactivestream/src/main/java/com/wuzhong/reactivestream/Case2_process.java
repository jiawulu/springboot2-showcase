package com.wuzhong.reactivestream;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Case2_process {

    public static void main(String[] args) throws InterruptedException {

        SubmissionPublisher<Integer> submissionPublisher = new SubmissionPublisher<>();

        MyProcessor processor = new MyProcessor();

        Flow.Subscriber subscriber = new MyConsumber();

        submissionPublisher.subscribe(processor);

        processor.subscribe(subscriber);

        //submit 是阻塞方法
        submissionPublisher.submit(1);
        submissionPublisher.submit(2);
        submissionPublisher.submit(3);

        submissionPublisher.close();

        Thread.currentThread().join(4000);

    }

}
