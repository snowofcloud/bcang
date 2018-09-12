/**
 * 文件名：IOccupationPersonDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

/**
 * 
 * 〈一句话功能简述〉从业人员信息Dao
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "occupationPersonDao")
public interface IOccupationPersonDao {
    /**
     * 
     * 〈一句话功能简述〉findByParams
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return List<OccupationPersonEntity>
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<OccupationPersonEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity OccupationPersonEntity
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int save(OccupationPersonEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业下面的驾驶员、押运员
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<OccupationPersonEntity>
     * @throws Exception Exception
     */
    List<OccupationPersonEntity> findPersonType(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity OccupationPersonEntity
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int update(OccupationPersonEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param cardNo cardNo
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int updateLicenceInfo(String cardNo)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT IDENTIFICATION_CARD_NO FROM T_OCCUPATION_PERSON "
        + "   WHERE ID = #{id, jdbcType=VARCHAR} AND REMOVE = '0' AND ROWNUM= 1 ")
    String findCardNo(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return OccupationPersonEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    OccupationPersonEntity findById(String id)
        throws Exception;
    
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
     * 〈一句话功能简述〉查询ID对应的驾驶员姓名
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return String 驾驶员姓名
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT PERSON_NAME,IDENTIFICATION_CARD_NO FROM T_OCCUPATION_PERSON "
        + "   WHERE ID = #{id, jdbcType=VARCHAR} AND REMOVE = '0' AND ROWNUM= 1 ")
    String findName(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询黑名单中驾驶员是否存在
     * 〈功能详细描述〉
     * 
     * @param name name
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT DRIVER FROM T_BLACKLIST_MANAGE "
        + "   WHERE DRIVER = #{name, jdbcType=VARCHAR} AND REMOVE = '0'")
    String findBlackListName(String name)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除黑名单中相应的驾驶员数据
     * 〈功能详细描述〉
     * 
     * @param name name
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("UPDATE T_BLACKLIST_MANAGE SET REMOVE='1' "
        + "   WHERE  DRIVER= #{name, jdbcType=VARCHAR} AND REMOVE = '0' AND ROWNUM= 1 ")
    String deleteBlackList(String name)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉判断身份证号码唯一
     * 〈功能详细描述〉
     * 
     * @param identificationCardNo identificationCardNo
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findIdCardNoExist(
        @Param("identificationCardNo") String identificationCardNo,
        @Param("id") String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉判断是否存在驾驶员
     * 〈功能详细描述〉
     * 
     * @param driver driver
     * @return List<OccupationPersonEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<OccupationPersonEntity> findDriverExist(String driver)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除app账号
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int deleteAccount(Map<String, Object> map)
        throws Exception;
}
