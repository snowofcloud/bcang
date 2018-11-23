package com.example.demo.one.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.demo.one.entity.Enterprise;
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
