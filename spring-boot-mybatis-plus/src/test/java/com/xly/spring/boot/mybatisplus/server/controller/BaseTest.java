package com.xly.spring.boot.mybatisplus.server.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther xuxq
 * @date 2018/11/23 16:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BaseTest {

    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MESSAGE = "success";
    public static final String URL = "http://localhost";

    @Test
    public void init() throws Exception {

    }

}
