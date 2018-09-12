/**
 * 文件名：IDangerFileRelaDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-9
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.LicenceFileRelaEntity;

/**
 * 〈一句话功能简述〉危险品车辆附件关系Dao
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "licenceFileRelaDao")
public interface ILicenceFileRelaDao {
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param list List<DangerFileRelaEntity>
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int saves(List<LicenceFileRelaEntity> list)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param licenceId licenceId
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int deleteFileId(String licenceId)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param licenceId licenceId
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> findFileIdById(String licenceId)
        throws Exception;
    
    /**
     * 获取车辆图片
     * 
     * @param carrierName carrierName
     * @return 车辆图片
     * @throws Exception Exception
     */
    @Select("select tb.file_id from t_danger_vehicle ta join t_danger_file_rela tb on ta.id = tb.danger_vehicle_id"
        + " where ta.licence_plate_no = #{carrierName} and ta.remove = '0' and tb.remove = '0'")
    String findCarPicId(@Param("carrierName") String carrierName)
        throws Exception;
}
