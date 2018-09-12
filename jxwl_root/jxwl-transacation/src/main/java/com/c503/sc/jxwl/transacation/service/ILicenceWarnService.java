/**
 * 文件名：IEnterpriseLicenceService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-12-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.LicenceEntity;

/**
 * 〈一句话功能简述〉形象展示接口 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public interface ILicenceWarnService {
    /**
     * 
     * 〈一句话功能简述〉分页查询企业资质信息 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LicenceEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>保存资质信息 〈功能详细描述〉
     * 
     * @param entity
     *            entity
     * @return 返回成功 影响的行数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int update(LicenceEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>计算过期证件个数
     * 
     * @param map
     *            map
     * @return 返回成功 int
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int amount(Map<String, Object> map)
        throws Exception;
    
}
