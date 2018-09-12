package com.c503.sc.jxwl.orgdata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;

/**
 * 同步企业信息到auth系统中的 t_sys_organization
 * 
 * @author Administrator
 */
@Repository
public interface ISyncOrganDataDao {
    
    /**
     * 查询auth中t_sys_organization所有化工机构的id
     * 
     * @param enpType 企业类型
     * @param sysId 所属系统
     * @return List<String>
     * @throws Exception Exception
     */
    @Select("select id from t_sys_organization where organ_type_id = #{enpType} and sys_id = #{sysId} and remove = '0'")
    List<String> findAllOrgId(@Param("enpType") String enpType,
        @Param("sysId") String sysId)
        throws Exception;
    
    /**
     * 对auth中t_sys_organization数据库中不在在的企业信息进行save
     * 
     * @param organ SysOrganTypeEntity
     * @throws Exception Exception
     */
    void saveOrgInfo(SysOrganTypeEntity organ)
        throws Exception;
    
    /**
     * 对数据库中t_sys_organization已存在的机构进行update
     * 
     * @param organ SysOrganTypeEntity
     * @throws Exception Exception
     */
    void updateOrgInfo(SysOrganTypeEntity organ)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉企业中的化工企业在公共平台中不存在, 则删除业务系统中的企业
     * 〈功能详细描述〉
     * 
     * @param enpCode enpCode
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Delete("update t_sys_organization set remove='1' where id = #{enpCode}")
    void deleteOrgInfo(@Param("enpCode") String enpCode)
        throws Exception;
}
