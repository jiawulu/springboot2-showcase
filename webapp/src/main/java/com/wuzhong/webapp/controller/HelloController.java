package com.wuzhong.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/hellos")
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String hello(){
        return "自律给我自由";
    }

    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }

    @GetMapping("/proxy")
    public String proxy(){
        return restTemplate.getForEntity("https://www.baidu.com/", String.class, new HashMap<>()).getBody();
    }

    @GetMapping("/exception")
    public String exception(){
        throw new RuntimeException("exception test");
    }

}
