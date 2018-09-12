/*
 * 文件名：IDictionaryTypeDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：
 * 修改时间：2015年7月22日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.dict.vo.DictDataVo;

/**
 * 〈一句话功能简述〉数据字典类型数据接口 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年7月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "dictionaryTypeDao")
public interface IDictionaryTypeDao {
    
    /**
     * 〈一句话功能简述〉获取所有的数据字典对象 〈功能详细描述〉
     * 
     * @param params 条件，其中：remove：逻辑删除标识，pcode：父分类编码,code：分类编码
     * @return List<DictDataVo>或者null
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    List<DictDataVo> findAllDictData(Map<String, Object> params)
        throws Exception;
    
}
