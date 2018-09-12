/**
 * 文件名：IImageShowController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.List;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
/**
 * 〈一句话功能简述〉形象展示接口
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */


public interface IImageShowService
{
    /**
     * 
     * 〈一句话功能简述〉通过企业名字或法人代码查询企业信息 〈功能详细描述〉
     * 
     * @param queryStr 查询参数
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findByNameOrCode(String queryStr)throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过法人代码查询企业信息 〈功能详细描述〉
     * 
     * @param code 查询参数
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    EnterpriseEntity findByCode(String code)throws Exception;

    /**
     * 
     * 〈一句话功能简述〉政府用户查询企业信息 〈功能详细描述〉
     * 
     *
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	EnterpriseEntity findByGOVER()throws Exception;
    
}
