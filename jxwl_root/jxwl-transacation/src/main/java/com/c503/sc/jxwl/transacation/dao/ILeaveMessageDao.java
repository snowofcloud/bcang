/**
 * 文件名：ILeaveMessageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;

/**
 * 
 * 〈一句话功能简述〉留言信息Dao 〈功能详细描述〉
 * 
 * @author xinzhiwei
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "leaveMessageDao")
public interface ILeaveMessageDao {
	/**
	 * 
	 * 〈一句话功能简述〉分页查询留言信息 〈功能详细描述〉
	 * 
	 * @param map
	 *            查询参数
	 * @return list 结果集合
	 * @throws Exception
	 *             异常
	 * @see [类、类#方法、类#成员]
	 */
	List<LeaveMessageEntity> findByParams(Map<String, Object> map)
			throws Exception;
	
	/**
	 * 〈一句话功能简述〉查询留言remove字段值 〈功能详细描述〉
	 * 
	 * @param id id
	 * @return remove字段值
	 * @throws Exception Exception
	 * @see [类、类#方法、类#成员]
	 */
	String findLeaveMessageRemove(@Param("id")String id) throws Exception;
	
	/**
	 * 〈一句话功能简述〉删除留言 〈功能详细描述〉
	 * 
	 * @param map
	 *            id、remove='1'、updateBy、updateTime
	 * @return 影响条数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int delete(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉保存留言 〈功能详细描述〉
	 * 
	 * @param entity
	 *            实体类
	 * @return 保存条数
	 * @throws Exception
	 *             异常
	 * @see [类、类#方法、类#成员]
	 */
	int save(LeaveMessageEntity entity) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉编辑留言 〈功能详细描述〉
	 * 
	 * @param entity 实体类
	 * @return 保存条数
	 * @throws Exception 异常
	 * @see [类、类#方法、类#成员]
	 */
	int update(LeaveMessageEntity entity) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉通过id查询留言 〈功能详细描述〉
	 * 
	 * @param map map
	 * @return LeaveMessageEntity
	 * @throws Exception 异常
	 * @see [类、类#方法、类#成员]
	 */
	LeaveMessageEntity findById(Map<String, Object> map)
			throws Exception;

	/**
	 * 
	 *〈一句话功能简述〉通过货单号查询货单所属的化工企业
	 * 〈功能详细描述〉
	 * @param goodsNo 货单号
	 * @return 化工企业名
	 * @throws Exception 异常
	 * @see  [类、类#方法、类#成员]
	 */
	String findChemicalEnterpriseByGoodNo(@Param("goodsNo")String goodsNo) throws Exception;

	/**
     * 
     *〈一句话功能简述〉校验货源的状态是否为‘已发布’且‘未签订订单 ’
     * 〈功能详细描述〉
     * @param goodsNo 货源号
     * @return true|false
     * @throws Exception 异常
     * @see  [类、类#方法、类#成员]
     */
    String checkGoodsStatus(@Param("goodsNo")String goodsNo) throws Exception;

    /**
     * 
     *〈一句话功能简述〉根据物流企业ID和货源ID查看是否已经存在留言
     * 〈功能详细描述〉
     * @param logisticsEnterprise 物流企业ID
     * @param goodsNo 货源ID
     * @return int
     * @throws Exception 异常
     * @see  [类、类#方法、类#成员]
     */
    int findLeaveMessageExist(
        @Param("logisticsEnterprise") String logisticsEnterprise, 
        @Param("goodsNo") String goodsNo) 
        throws Exception;
	

}
