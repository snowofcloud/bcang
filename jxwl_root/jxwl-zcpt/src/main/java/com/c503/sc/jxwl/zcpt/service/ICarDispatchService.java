/**
 * 文件名：ICarDispatchService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;
import java.util.Map;

public interface ICarDispatchService {
    
    /**
     * 〈一句话功能简述〉根据通过车牌号查询运单再通过运单id查询驾驶员 然后账号注册表查询regid
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @return 手机注册ID
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    String findRegidByCarrierName(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆调度信息
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @param userName 用户名
     * @return List<Map<String, Object>>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findCarDispVal(String carrierName, String userName)
        throws Exception;
    
    /**
     * 下发文本消息，车辆消息调度
     * 参考：章节2.10 文本信息下发
     * 
     * @param map 文本消息，
     * @throws Exception Exception
     */
    void sendTextMsg(Map<String, Object> map)
        throws Exception;
    
}
