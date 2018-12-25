package com.wuzhong.webapp.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/flux")
@Slf4j
public class FluxController {

    @GetMapping("/sync")
    public String sync(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String str = gen();
        stopWatch.stop();
        log.info( "sync >>> " + stopWatch.toString());
        return str;
    }

    @GetMapping("/mono")
    public Mono<String> mono(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Mono<String> stringMono = Mono.fromSupplier(() -> gen());
        stopWatch.stop();
        log.info( "async >>> " + stopWatch.toString());
        return stringMono;
    }

    private String gen(){
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }

}
