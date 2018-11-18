package com.xly.spring.data.jpa.user.service;

import com.xly.spring.data.jpa.entity.AccountInfo;

import java.util.List;

/**
 * @auther xuxq
 * @date 2018/11/17 18:03
 */
public interface AccountInfoService {

    List<AccountInfo> findAll();

}
