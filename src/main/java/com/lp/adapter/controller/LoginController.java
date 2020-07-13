package com.lp.adapter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adapter")
public class LoginController {

    @RequestMapping("/index")
    public String index() {
        return "config";
    }
}
