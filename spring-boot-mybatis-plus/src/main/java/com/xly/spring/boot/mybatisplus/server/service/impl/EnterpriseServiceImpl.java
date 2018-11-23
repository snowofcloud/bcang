package com.xly.spring.boot.mybatisplus.server.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xly.spring.boot.mybatisplus.server.base.model.PageInfo;
import com.xly.spring.boot.mybatisplus.server.entity.Enterprise;
import com.xly.spring.boot.mybatisplus.server.mapper.EnterpriseMapper;
import com.xly.spring.boot.mybatisplus.server.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther xuxq
 * @date 2018/11/23 14:47
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public PageInfo<Enterprise> queryAll(int pageIndex, int pageSize) {
        Page<Enterprise> page = new Page<>(pageIndex, pageSize);
        page.setRecords(enterpriseMapper.queryAll(page));
        return new PageInfo<>(page);
    }
}
