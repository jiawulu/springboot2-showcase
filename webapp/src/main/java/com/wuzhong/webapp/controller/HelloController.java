package com.wuzhong.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hellos")
public class HelloController {

    @GetMapping
    public String hello(){
        return "自律给我自由";
    }

    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }

}
