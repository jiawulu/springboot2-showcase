package com.wuzhong.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarterConfig.class)
public class StarterAuoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(StarterAuoConfiguration.class);

    @Autowired
    private StarterConfig starterConfig;

    @Bean(name = "firstStarterService")
    @ConditionalOnProperty(name = "starter.enabled", havingValue = "true", matchIfMissing = false)
    public StarterService createEnabledStarterService() {
        return new StarterService() {
            @Override
            public void start() {
                logger.warn("on first start service ", starterConfig.isEnabled());
            }
        };
    }


    @Bean
    @ConditionalOnMissingBean(StarterService.class)
    public StarterService createDisabledStarterService() {
        return new StarterService() {
            @Override
            public void start() {
                logger.error("start service disabled");
            }
        };
    }


}
