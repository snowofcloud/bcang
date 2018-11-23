package com.xly.spring.boot.mybatisplus.server.service;

import com.baomidou.mybatisplus.service.IService;
import com.xly.spring.boot.mybatisplus.server.base.model.PageInfo;
import com.xly.spring.boot.mybatisplus.server.entity.Enterprise;

/**
 * @auther xuxq
 * @date 2018/11/23 14:32
 */
public interface EnterpriseService extends IService<Enterprise> {

    PageInfo<Enterprise> queryAll(int pageIndex, int pageSize);

}
