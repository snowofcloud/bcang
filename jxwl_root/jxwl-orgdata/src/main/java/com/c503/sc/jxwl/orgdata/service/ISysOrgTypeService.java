/**
 * 文件名：IOrganizationServie.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月21日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.service;

import java.util.List;

import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;

/**
 * 〈一句话功能简述〉组织机构业务接口
 * 〈功能详细描述〉
 * 
 * @author yjh
 * @version [版本号, 2016年08月09日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ISysOrgTypeService {
    /**
     * 〈一句话功能简述〉删除信息
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
  /*  int delete(Map<String, Object> map)
        throws Exception;
    */
    /**
     * 
     * 〈一句话功能简述〉修改或保存企业的信息
     * 〈功能详细描述〉
     * 
     * @param entity 保存对象
     * @return 返回值大于0说明更新成功
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int updateOrSave(SysOrganTypeEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉获取企业的法人代码
     * 〈功能详细描述〉
     * 
     * @param orgId 企业的机构id
     * @return 法人代码
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    String findEnCodeByOrgId(String orgId)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉获取用户的角色
     * 〈功能详细描述〉
     * 
     * @param uerId 用户id
     * @return list型的所有角色
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    List<String> findUserRoleByUserId(String uerId)
        throws Exception;
    
}
