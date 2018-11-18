package com.xly.spring.data.jpa.user.service.impl;

import com.xly.spring.data.jpa.entity.AccountInfo;
import com.xly.spring.data.jpa.repository.AccountInfoRepository;
import com.xly.spring.data.jpa.user.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther xuxq
 * @date 2018/11/17 18:04
 */
@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Override
    public List<AccountInfo> findAll() {
        return accountInfoRepository.findAll();
    }
}
