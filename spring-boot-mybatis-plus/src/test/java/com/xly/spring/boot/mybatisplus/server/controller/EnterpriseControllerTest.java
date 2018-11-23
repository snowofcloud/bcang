package com.xly.spring.boot.mybatisplus.server.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @auther xuxq
 * @date 2018/11/23 16:33
 */
public class EnterpriseControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 初始化MockMvc，对每个方法进行执行
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

    }

    /**
     * 释放资源
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void queryAllEnterprise() throws Exception {
        this.mockMvc.perform(get(URL + "/api/enterprise/queryAllEnterprise?pageIndex=1&pageSize=10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.code",is(SUCCESS_CODE)))
                .andExpect(jsonPath("$.message",is(SUCCESS_MESSAGE)));
    }



}
