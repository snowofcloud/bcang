/**
 * 文件名：SysOrganTypeDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-9
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;

/**
 * 
 * 〈一句话功能简述〉操作企业机构信息表
 * 〈功能详细描述〉
 * 
 * @author yangjh
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Repository(value = "sysOrganTypeDao")
public interface ISysOrganTypeDao {
    /**
     * 
     * 〈一句话功能简述〉保存机构信息
     * 〈功能详细描述〉
     * 
     * @param entity 保存对象
     * @return 保存数据的条数
     * @see [类、类#方法、类#成员]
     */
    int save(SysOrganTypeEntity entity);
    
    /**
     * 
     * 〈一句话功能简述〉通过法人代码(机构编码)来查看企业的机构信息
     * 〈功能详细描述〉
     * 
     * @param params 查找的条件
     * @return 实体对象
     * @see [类、类#方法、类#成员]
     */
    SysOrganTypeEntity findById(Map<String, Object> params);
    
    /**
     * 更新企业机构信息
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 更新对象
     * @return entity
     * @see [类、类#方法、类#成员]
     */
    int updateInfo(SysOrganTypeEntity entity);
    
    /**
     * 〈一句话功能简述〉删除
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查找用户的角色id
     * 〈功能详细描述〉
     * 
     * @param uerId 用户id
     * @return 用户的所有角色id
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    List<String> findUserRoleByUserId(String uerId)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查找用户的角色id
     * 〈功能详细描述〉
     * 
     * @param uerId 用户id
     * @return 用户的所有角色id
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    List<String> findUserRoleNameByUserId(String uerId)
        throws Exception;
    /**
     * 
     * 〈一句话功能简述〉通过机构id来获取企业的法人代码
     * 〈功能详细描述〉
     * 
     * @param params 机构id 和查询参数
     * @return 企业的法人代码
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    String findEnCodeByOrgId(Map<String, Object> params)
        throws Exception;

    /**
     * 
     * 〈一句话功能简述〉通过机构id来获取企业的法人代码
     * 〈功能详细描述〉
     * 
     * @param uerId uerId
     * @return  企业的法人代码
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT IDCARD FROM T_SYS_USER WHERE ID = #{uerId} AND REMOVE = '0' AND ROWNUM= 1 ")
    String findIdCard(@Param("uerId") String uerId)
        throws Exception;
}
