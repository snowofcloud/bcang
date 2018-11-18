package com.xly.spring.data.jpa.controller;

import com.xly.spring.data.jpa.entity.AccountInfo;
import com.xly.spring.data.jpa.user.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther xuxq
 * @date 2018/11/18 10:25
 */
@RestController
@EnableAutoConfiguration
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService;

    @RequestMapping(value = "/hi")
    public String hi(){
        return "welcome to back!";
    }

    @GetMapping(value = "/list")
    public List<AccountInfo> list(){
        return accountInfoService.findAll();
    }

}
