package org.npd.starspost.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
