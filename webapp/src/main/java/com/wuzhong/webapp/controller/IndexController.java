package com.wuzhong.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("web")
public class IndexController {

    @GetMapping("/index")
    public String hello(@RequestParam(name = "name", defaultValue = "world", required = false) String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("date", LocalDate.now().toString());
        return "index";
    }

}
