package com.c503.hthj.boot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangr on 2018/7/3.
 */
@RestController
@RequestMapping("api/demo")
public class DemoController {

    @GetMapping("ok")
    public String ok(){
        return "ok";
    }
}
