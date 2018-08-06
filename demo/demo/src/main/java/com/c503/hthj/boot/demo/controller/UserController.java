package com.c503.hthj.boot.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.c503.hthj.boot.demo.uac.AppToken;
import com.c503.hthj.boot.demo.uac.TokenKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *     //该类需要实现获取用户信息、获取权限信息
 * Created by huangr on 2018/7/4.
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private TokenKit tokenKit;
    @GetMapping("info")
    public JSONObject getPersonalInfo(){
        JSONObject obj =tokenKit.getUserInfo();
        //接口统一格式，需要再封装一下（code\message等）,以下未封装
        return obj;
    }
}
