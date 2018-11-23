package com.example.demo.one.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.one.base.model.PageInfo;
import com.example.demo.one.entity.Enterprise;

/**
 * @auther xuxq
 * @date 2018/11/23 14:32
 */
public interface EnterpriseService extends IService<Enterprise> {

    PageInfo<Enterprise> queryAll(int pageIndex, int pageSize);

}
