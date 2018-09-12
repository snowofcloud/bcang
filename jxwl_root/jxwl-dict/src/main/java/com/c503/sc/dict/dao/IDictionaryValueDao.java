/*
 * 文件名：IDictionaryValueDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：
 * 修改时间：2015年7月22日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.dict.bean.DictionaryValueEntity;

/**
 * 〈一句话功能简述〉数据字典值对象数据接口 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年7月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "dictionaryValueDao")
public interface IDictionaryValueDao {
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param arg0
     * @return List<DictionaryValueEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<DictionaryValueEntity> findByParams(Map<String, Object> arg0)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据条件获得数据字典对象 〈功能详细描述〉主要用于缓存处理
     * 
     * @param params 条件
     * @return List<DictDataVo>
     * @see [类、类#方法、类#成员]
     */
    List<?> findByCond(Map<String, Object> params);
    
    /**
     * 
     * 〈一句话功能简述〉根据数据字典的值获取数据字典详细信息
     * 〈功能详细描述〉
     * 
     * @param value 数据字典的值
     * @return 数据字典详细信息
     * @throws Exception 数据库异常
     * @see [类、类#方法、类#成员]
     */
    DictionaryValueEntity findDicByValue(String value)
        throws Exception;
}
