/*
 * 文件名：IControllerTemplate.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年6月12日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.controller;

import org.springframework.validation.BindingResult;

/**
 * 〈一句话功能简述〉 控制层模版 〈功能详细描述〉 规范增删改查的方法书写以及传入参数
 *
 * @author chenl
 * @version [版本号, 2015年6月10日]
 * @param <T>
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IControllerTemplate<T> {
    /**
     * 〈一句话功能简述〉保存实体对象 〈功能详细描述〉
     * 
     * @param entity 需要保存的formbean
     * @param bindingResult BindingResult中校验信息
     * @return Object 保存成功的实体对象
     * @throws Exception Java运行时异常
     * @see [类、类#方法、类#成员]
     */
    Object save(T entity, BindingResult bindingResult)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改实体对象 〈功能详细描述〉
     * 
     * @param entity 需要修改的formbean
     * @param bindingResult BindingResult中校验信息
     * @return 响应消息
     * @throws Exception Java运行时异常
     * @see [类、类#方法、类#成员]
     */
    Object update(T entity, BindingResult bindingResult)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉按照ID批量或单个删除实体对象 〈功能详细描述〉
     * 
     * @param ids 可变参数的id
     * @return 删除成功的标识
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    Object deletes(String... ids)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉按照ID查询实体对象 〈功能详细描述〉
     * 
     * @param id ID
     * @return 返回查找的记录
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    Object findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过(条件查询)所有数据 〈功能详细描述〉
     * 
     * @param entity 查询条件对象
     * @return 返回查找的所有记录
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    Object findAll(T entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 分页查询 〈功能详细描述〉
     *
     * @param entity 查询条件对象
     * @return 返回页数
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    Object findByPage(T entity)
        throws Exception;
    
}
