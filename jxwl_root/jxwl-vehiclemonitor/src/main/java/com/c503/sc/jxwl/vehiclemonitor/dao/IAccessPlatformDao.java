/**
 * 文件名：IAccessPlatformDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.AccessPlatformEntity;

/**
 * 
 * 〈一句话功能简述〉获取接入平台数据
 * 〈功能详细描述〉
 * @author    yuanyl
 * @version   [版本号, 2016-7-26]
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
@Repository(value = "accessPlatformDao")
public interface IAccessPlatformDao {
    
    /**
     * 
     *〈一句话功能简述〉获取接入平台数据
     * 〈功能详细描述〉
     * @param map 查询条件
     * @return 返回接入平台数据
     * @throws Exception 系统异常
     * @see  [类、类#方法、类#成员]
     */
    List<AccessPlatformEntity>findAccessPlatformByParams(Map<String, Object> map) throws Exception;
}
