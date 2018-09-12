package com.c503.sc.jxwl.orgdata.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.orgdata.bean.BizUserEntity;

/**
 * 〈一句话功能简述〉SysUserDao
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "sysUserDao")
public interface SysUserDao {
    /**
     * 
     * 〈一句话功能简述〉查询用户信息〈功能详细描述〉
     * 
     * @param account account
     * @param sysId sysId
     * @return BizUserEntity BizUserEntity
     * @see [类、类#方法、类#成员]
     */
    @ResultMap("BaseResultMap")
    @Select("select * from T_SYS_USER where account=#{account} and status='0' and remove='0' and sys_id=#{sysId}")
    BizUserEntity getUserByAccount(@Param("account") String account,
        @Param("sysId") String sysId);
    
    /**
     * 
     * 〈一句话功能简述〉保存用户信息〈功能详细描述〉
     * 
     * @param user
     *            user
     * @see [类、类#方法、类#成员]
     */
    @Insert("insert into T_SYS_USER (id, sys_id, account, name,type,status, password, salt, organ_id, remove) "
        + " values (#{id}, #{sysId}, #{account}, #{name},#{type},'0', #{password}, #{salt}, #{orgId}, '0')")
    void save(BizUserEntity user);
    
    /**
     * 
     * 〈一句话功能简述〉更新用户信息〈功能详细描述〉
     * 
     * @param user
     *            user
     * @see [类、类#方法、类#成员]
     */
    @Update("update T_SYS_USER set account = #{account}, name = #{name} "
        + "where sys_id = #{sysId} and account = #{account} ")
    void update(BizUserEntity user);
    
    /**
     * 
     * 〈一句话功能简述〉更新用户附属信息〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param userId
     *            userId
     * @param roleId
     *            roleId
     * @see [类、类#方法、类#成员]
     */
    @Insert("insert into T_SYS_USER_REL_ROLE (id, user_id, role_id) values (#{id}, #{userId}, #{roleId})")
    void saveUserRole(@Param("id") String id, @Param("userId") String userId,
        @Param("roleId") String roleId);
    
    /**
     * 
     * 〈一句话功能简述〉保存新增的用户信息以及附属信息〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return 返回成功 影响的行数
     * @see [类、类#方法、类#成员]
     */
    int saveSysUser(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉更新的用户信息〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return 返回成功 影响的行数
     * @see [类、类#方法、类#成员]
     */
    int updateInfo(Map<String, Object> map);
    
}
