/**
 * 文件名：ISrcGoodsDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsForFull;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsInfo;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface ISrcGoodsDao extends IBaseDao<SrcGoods> {
    
    /**
     * 〈一句话功能简述〉保存货物信息
     * 〈功能详细描述〉
     * 
     * @param list List<SrcGoodsInfo>
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int saveGoods(List<SrcGoodsInfo> list)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询货单号是否存在
     * 〈功能详细描述〉
     * 
     * @param waybilllNo 货单号
     * @param id id
     * @return return waybilllNo
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findWaybilllNoHasExist(@Param(value = "waybilllNo")
    String waybilllNo, @Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param updateBy updateBy
     * @param updateTime updateTime
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(@Param("id")
    String id, @Param("updateBy")
    String updateBy, @Param("updateTime")
    Date updateTime)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新时候删除该货源下的所有货物
     * 〈功能详细描述〉
     * 
     * @param srcGoodsId 货源id
     * @return 删除条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Delete("delete from t_source_goods_info where src_goods_id = #{srcGoodsId}")
    int delGoodsBySrcGoodsId(@Param("srcGoodsId")
    String srcGoodsId)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询交易状态
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return tradeStatus
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT TRADE_STATUS FROM T_SOURCE_GOODS WHERE ID = #{id, jdbcType=VARCHAR}")
    String findTradeStatus(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉要发布的信息是否已经删除
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return tradeStatus
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT REMOVE FROM T_SOURCE_GOODS WHERE ID = #{id, jdbcType=VARCHAR}")
    String findExsitById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新交易状态
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param tradeStatus tradeStatus
     * @param updateBy updateBy
     * @param publishDate publishDate
     * @throws Exception Exception
     * @return 影响行数
     * @see [类、类#方法、类#成员]
     */
    @Update(value = "UPDATE T_SOURCE_GOODS SET "
        + "TRADE_STATUS = #{tradeStatus,jdbcType=VARCHAR}, "
        + "UPDATE_BY    = #{updateBy,jdbcType=VARCHAR}, "
        + "UPDATE_TIME  = #{publishDate,jdbcType=TIMESTAMP}, "
        + "PUBLISH_DATE = #{publishDate,jdbcType=TIMESTAMP} "
        + "WHERE ID = #{id, jdbcType=VARCHAR} ")
    int updateByPublish(@Param("id")
    String id, @Param("tradeStatus")
    String tradeStatus, @Param("updateBy")
    String updateBy, @Param("publishDate")
    Date publishDate)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新交易状态通过签订
     * 〈功能详细描述〉
     * 
     * @param orderNo orderNo
     * @param tradeStatus tradeStatus
     * @param wlCorporateNo wlCorporateNo
     * @param updateBy updateBy
     * @param id id
     * @param signDate signDate
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update(value = "UPDATE T_SOURCE_GOODS SET "
        + "ORDER_NO        = #{orderNo       ,jdbcType=VARCHAR}, "
        + "TRADE_STATUS    = #{tradeStatus   ,jdbcType=VARCHAR}, "
        + "WL_CORPORATE_NO = #{wlCorporateNo ,jdbcType=VARCHAR}, "
        + "UPDATE_BY       = #{updateBy      ,jdbcType=VARCHAR}, "
        + "UPDATE_TIME     = #{signDate,jdbcType=TIMESTAMP}, "
        + "ORDER_DATE      = #{signDate,jdbcType=TIMESTAMP}  "
        + "WHERE ID = #{id ,jdbcType=VARCHAR}")
    int updateBySign(@Param("orderNo")
    String orderNo, @Param("tradeStatus")
    String tradeStatus, @Param("wlCorporateNo")
    String wlCorporateNo, @Param("updateBy")
    String updateBy, @Param("id")
    String id, @Param("signDate")
    Date signDate)
        throws Exception;
    
    /****************************************** 企业信息相关 FIXME **************************************/
    /**
     * 〈一句话功能简述〉根据车辆数查询企业信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findEnpsByCarNumParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据交易数查询企业信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findEnpsByTradeNumParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据评分查询企业信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findEnpsByCommentScoreParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询货物信息for大屏展示
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return List<SrcGoodsForFull>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoodsForFull> findAll(String corporateNo)
        throws Exception;
    
   

}
