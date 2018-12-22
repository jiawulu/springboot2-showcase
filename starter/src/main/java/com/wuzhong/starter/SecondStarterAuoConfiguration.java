package com.wuzhong.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondStarterAuoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(SecondStarterAuoConfiguration.class);

    @Bean(name = "secondStarterService")
    @ConditionalOnExpression("${starter.enabled:false}==true")
    public StarterService createSecondStarterService() {
        return new StarterService() {
            @Override
            public void start() {
                logger.warn("on second start service ");
            }
        };
    }


}
