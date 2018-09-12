package com.c503.sc.dict.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.dict.vo.DictDataVo;

/**
 * 〈一句话功能简述〉数据字典常量业务关联数据接口
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年10月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "dictBizComRelaDao")
public interface IDictBizComRelaDao {
    
    /**
     * 〈一句话功能简述〉通过业务编码查询业务模块下的常量分类集合
     * 〈功能详细描述〉
     * 
     * @param params 查询条件，其中（code:编码，remove：逻辑删除标识）
     * @return 常量分类集合
     * @see [类、类#方法、类#成员]
     */
    List<DictDataVo> findByCond(Map<String, Object> params);
}
