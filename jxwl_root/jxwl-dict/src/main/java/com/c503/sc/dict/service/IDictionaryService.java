/*
 * 文件名：IDictionaryService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年7月22日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.service;

/**
 * 〈一句话功能简述〉数据字典业务接口 〈功能详细描述〉主要用于数据字典的增删改查操作以及缓存处理
 * 
 * @author duanhy
 * @version [版本号, 2015年8月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IDictionaryService {
    
    /**
     * 〈一句话功能简述〉初始化数据字典至缓存 〈功能详细描述〉
     * 
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    // void initDictCache()
    // throws Exception;
    
    /**
     * 〈一句话功能简述〉根据编码从缓存中获得字典对象 〈功能详细描述〉
     * 
     * @param code 编码
     * @return List<DictDataVo>
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Object findDictFromCache(String code)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉从数据库根据编码获得对应字典数据
     * 〈功能详细描述〉
     * 
     * @param code 编码
     * @return 某个模块下的分类以及明细或者某个分类下的明细
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Object findDictFromDB(String code)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获得数据字典数据根据编码
     * 〈功能详细描述〉
     * 
     * @param code 编码
     * @return 某个模块下的分类以及明细或者某个分类下的明细 List<Map<String, Object>>
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Object findDictByCode(String code)
        throws Exception;
}
