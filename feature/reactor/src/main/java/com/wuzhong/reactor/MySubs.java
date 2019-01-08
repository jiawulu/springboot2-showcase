package com.wuzhong.reactor;

import reactor.core.publisher.BaseSubscriber;

public class MySubs extends BaseSubscriber<String> {

    @Override
    protected void hookOnNext(String value) {
        super.hookOnNext(value);
    }
}
