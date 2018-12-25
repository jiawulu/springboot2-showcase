package com.wuzhong.webapp.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "api/v1/flux" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class FluxController {

    @GetMapping("/sync")
    public String sync() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String str = gen();
        stopWatch.stop();
        log.info("sync >>> " + stopWatch.toString());
        return str;
    }

    @GetMapping("/mono")
    public Mono<String> mono() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Mono<String> stringMono = Mono.fromSupplier(() -> gen());
        stopWatch.stop();
        log.info("async >>> " + stopWatch.toString());
        return stringMono;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Flux<String> stringFlux = Flux.fromStream(IntStream.rangeClosed(1, 3).boxed()).map(integer -> gen());
        stopWatch.stop();
        log.info("async flux >>> " + stopWatch.toString());
        return stringFlux;
    }

    @GetMapping(value = "/flux2")
    public Flux<String> flux2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Flux<String> stringFlux = Flux.fromStream(IntStream.rangeClosed(2, 4).boxed()).map(integer -> gen());
        stopWatch.stop();
        log.info("async flux >>> " + stopWatch.toString());
        return stringFlux;
    }

    private String gen() {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }

}
