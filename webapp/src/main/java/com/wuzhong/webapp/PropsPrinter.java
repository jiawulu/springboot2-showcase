package com.wuzhong.webapp;

import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PropsPrinter implements InitializingBean {

    @Autowired
    private Environment environment;

    @Value("${wz.name:jj}")
    private String name;

    @Autowired
    private WZ wz;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("afterPropertiesSet >>>> ");
        log.warn("@Value(\"${wz.name:jj}\")");
        log.warn(name);

        log.warn("environment.getProperty(\"wz.name\")");
        log.warn(environment.getProperty("wz.name"));

        log.warn("@ConfigurationProperties(\"wz\")");
        log.warn(wz.getFav());

    }

    @ConfigurationProperties("wz")
    @Data
    @Configuration
    public static class WZ {
        private String name;
        private String fav;
        private boolean enabled;
    }

}
