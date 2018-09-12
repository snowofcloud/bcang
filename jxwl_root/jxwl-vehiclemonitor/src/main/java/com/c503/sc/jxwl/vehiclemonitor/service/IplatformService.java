/**
 * 文件名：IplatformService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.PlatformEntity;


/**
 * 〈一句话功能简述〉平台信息接口
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-8]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public interface IplatformService {
    /**
     *〈一句话功能简述〉分页查询平台信息
     * 〈功能详细描述〉
     * @param map map
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    List<PlatformEntity> findByParams(Map<String, Object> map) throws Exception;
}
