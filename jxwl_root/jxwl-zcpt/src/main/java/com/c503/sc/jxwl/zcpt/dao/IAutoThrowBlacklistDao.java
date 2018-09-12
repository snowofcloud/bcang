/**
 * 文件名：IAlarmInfoDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.Blacklist;
import com.c503.sc.jxwl.zcpt.bean.BlacklistArg;

/**
 * 根据报警自动拉入黑名单
 * 
 * @author zz
 * @version [版本号, 2016-8-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "autoThrowBlacklistDao")
public interface IAutoThrowBlacklistDao {
    
    /**
     * 〈一句话功能简述〉根据车牌号查询报警次数
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return 报警次数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int findAlarmTimes4Vehicle(Map<String, String> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据车牌号查询报警次数
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return 报警次数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int findAlarmTimes4Driver(Map<String, String> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询报警参数设置（永远只有一条）
     * 〈功能详细描述〉
     * 
     * @return AlarmNumVo
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    BlacklistArg findAlarmOne()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉是否已被拉黑过
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findHasInBlacklist(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉是否已被拉黑过
     * 〈功能详细描述〉
     * 
     * @param driverId 身份证
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findHasInBlack4Driver(String driverId)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存自动拉黑黑名单
     * 〈功能详细描述〉
     * 
     * @param mod Blacklist
     * @return 保存条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(Blacklist mod)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询最新的更新时间
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return 保存条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Date findMaxDate4Vehicle(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询最新的更新时间
     * 〈功能详细描述〉
     * 
     * @param driver driver
     * @return 保存条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Date findMaxDate4Driver(String driver)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询是否已经存在次条自动拉黑信息
     * 〈功能详细描述〉
     * 
     * @param driver driver
     * @return 保存条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	Blacklist findExsist(@Param("carrierName")String carrierName) throws Exception;
}
