/**
 * 文件名：IImageShowDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "imageShowDao")
public interface IImageShowDao
{
    /**
     * 
     * 〈一句话功能简述〉通过企业名字或法人代码查询企业信息 〈功能详细描述〉
     * 
     * @param queryStr 查询参数
     * @return 返回成功 的数据信息
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findByNameOrCode(String queryStr);
    
    /**
     * 
     * 〈一句话功能简述〉通过法人代码查询企业信息 〈功能详细描述〉
     * 
     * @param code 查询参数
     * @return 返回成功 的数据信息
     * @see [类、类#方法、类#成员]
     */
    EnterpriseEntity findByCode(String code);
    
    /**
     * 
     * 〈一句话功能简述〉政府查询企业信息 〈功能详细描述〉
     * 
     * 
     * @return 返回成功 的数据信息
     * @see [类、类#方法、类#成员]
     */
	String findByGOVER();

}
