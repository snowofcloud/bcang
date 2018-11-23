package com.xly.spring.boot.mybatisplus.server.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xly.spring.boot.mybatisplus.server.entity.Enterprise;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @auther xuxq
 * @date 2018/11/23 14:52
 */
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    @Select("select * from enterprise order by created_time desc")
    List<Enterprise> queryAll(Pagination page);


}
