package com.wuzhong.webapp;

import com.wuzhong.starter.StarterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StarterPrinter implements InitializingBean {

    @Autowired
    private List<StarterService> starterServices;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("StarterPrinter afterPropertiesSet  >>>> ");

        for (StarterService starterService : starterServices) {
            starterService.start();
        }

//      hot deploy
//      add devtools dependency
//      edit ->  build -> recompile java
        System.out.println(">>>>");
    }

}
