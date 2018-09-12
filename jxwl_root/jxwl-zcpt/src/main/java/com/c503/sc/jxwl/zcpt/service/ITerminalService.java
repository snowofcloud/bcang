/**
 * 文件名：IGetInfoService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-1
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalParamEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalUpgrade;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;

/**
 * <strong>位置服务平台</strong>终端相关接口
 * 参考文档《嘉兴项目位置平台API接口文档合成版0927（公开）》
 * 
 * @author qianxq
 * @version [版本号, 2016-8-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ITerminalService {
    
    /**
     * 终端开户
     * 参考：章节2.1 终端基础数据录入
     * 
     * @param terminal terminal
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalEntity save(TerminalEntity terminal)
        throws Exception;
    
    /**
     * 车牌号是否存在
     * 
     * @param carrierName carrierName
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int carrierNameIsExist(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 修改终端 参考：章节2.2 终端基础数据修改
     * 〈功能详细描述〉
     * 
     * @param terminal TerminalEntity
     * @return TerminalEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalEntity update(TerminalEntity terminal)
        throws Exception;
    
    /**
     * 分页查询危险品车辆信息
     * 参考：章节2.3 终端基础数据查询
     * 
     * @param map 查询条件
     * @return PageEntity<TerminalEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    PageEntity<TerminalEntity> findByParams(Map<String, String> map)
        throws Exception;
    
    /**
     * 删除终端
     * 参考：章节2.4 终端基础数据删除
     * 
     * @param terminalSerialID 终端ID
     * @throws Exception Exception
     */
    void delete(String terminalSerialID)
        throws Exception;
    
    /**
     * 注销终端、终端与载体解绑
     * 参考：章节2.5 终端与载体解绑
     * 
     * @param id 终端ID
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void unbind(String id)
        throws Exception;
    
    /**
     * 注册终端、终端与载体绑定
     * 参考：章节2.6 终端与载体绑定
     * 
     * @param terminal TerminalEntity
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalEntity bind(TerminalEntity terminal)
        throws Exception;
    
    /**
     * 获取车辆的历史位置信息
     * 参考：章节2.7 终端历史位置
     * 
     * @param carrierName 车牌号
     * @param startTime 轨迹查询起始时间
     * @param endTime 轨迹查询终止时间
     * @return List<LocationEntity>
     * @throws Exception Exception
     */
    List<LocationEntity> getCarHistoryLocation(String carrierName,
        String startTime, String endTime)
        throws Exception;
    
    /**
     * 设置终端参数(设置报警信息)
     * 参考：章节2.8 设置终端参数[为所有终端设置终端参数]
     * 
     * @param map 终端参数
     * @throws Exception Exception
     */
    void setTerminalParam(Map<String, String> map)
        throws Exception;
    
    /**
     * 设置终端参数(设置报警信息)
     * 参考：章节2.8 设置终端参数[为一个终端设置终端参数]
     * 
     * @param map 终端参数
     * @throws Exception Exception
     */
    void setTerminalParam(TerminalParamEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 查询（一个终端）的终端参数
     * 
     * @param map
     *            map
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalParamEntity findTerminalParamById(String id)
        throws Exception;
    
    /**
     * 终端固件升级
     * 参考：章节2.11 固件升级（指定固件升级）
     * 
     * @param terminal TerminalUpgrade
     * @throws Exception Exception
     */
    void upgrade(TerminalUpgrade terminal)
        throws Exception;
    
    /**
     * 升级所有终端的固件
     * 参考：章节2.12 固件升级（一键升级所有固件）
     * 
     * @param terminal 升级参数
     * @throws Exception Exception
     */
    void upgradeAll(TerminalUpgrade terminal)
        throws Exception;
    
    /**
     * 设置限制区域
     * 参考：章节2.13 设置限制区域
     * 已过时，限制区域业务在业务系统处理
     * 
     * @param tel 终端手机号
     * @param limitArea 限制区域
     * @throws Exception
     */
    // @Deprecated
    // void setLimitArea(String tel, LimitArea limitArea)
    // throws Exception;
    
    /**
     * 删除限制区域,
     * 以分号分隔的要删除的区域ID,只有一个区域ID也要加分号；
     * 当删除所有区域时只传递0，不加分号
     * 参考：章节2.14 删除限制区域
     * 已过时，限制区域业务在业务系统处理
     * 
     * @param ids 要删除的限制区域，
     * @throws Exception
     */
    // @Deprecated
    // void delLimitArea(String ids)
    // throws Exception;
    
    /**
     * 发送录音指令
     * 参考：章节2.15 下达音频录制命令
     * 
     * @param map 发送录音的参数
     * @throws Exception
     */
    // void sendAudioCmd(Map<String, String> map)
    // throws Exception;
    
    /**
     * 下发拍照命令
     * 参考：章节2.16 下达拍照命令
     * 
     * @param map 发送拍照命令的参数
     * @throws Exception
     */
    // void sendPhotoCmd(Map<String, String> map)
    // throws Exception;
    
    /**
     * 
     * 通过"厂商终端ID"获取终端
     * 
     * @param id 厂商终端ID
     * @return TerminalEntity 终端对象
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalEntity findById(String id)
        throws Exception;
    
    /**
     * 
     * 通过"车牌号"获取终端
     * 
     * @param carrierName 车牌号
     * @return TerminalEntity 终端对象
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalEntity findByCarrierName(String carrierName)
        throws Exception;
    
    /**
     * 
     * 通过"手机号"获取终端
     * 
     * @param simNum 手机号
     * @return TerminalEntity 终端对象
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    TerminalEntity findBySimNum(String simNum)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过
     * 〈功能详细描述〉
     * 
     * @param carrierName
     * @param cardNum
     * @return
     * @throws Exception
     */
    public TerminalEntity findByCarrierNameAndcardNum(String carrierName,
        String cardNum)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过账号查询车牌号
     * 〈功能详细描述〉
     * 
     * @param String account
     * @return List<String> carrierNames
     * @throws Exception
     */
    WayBillEntity findCarrierNameByAccount(String account)
			throws Exception;
    
    /**
     * 〈一句话功能简述〉发送808协议命令给终端
     * 〈功能详细描述〉
     * 
     * @param String account
     * @return List<String> carrierNames
     * @throws Exception
     */
    public void sendCommand808(Map<String, String> map, String action, String actionFlag)
        throws Exception;
    
    public void refreshTerminalBaseInfo()
        throws Exception;
    

    public void setTerminalBaseInfoList(List<TerminalEntity> terminalBaseInfoList)
        throws Exception;

    public Map<String, Object> getCarNo2TerminalSourceMap()
        throws Exception;

    
    
}
