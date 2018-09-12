/**
 * 文件名：LeaveMessageEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息实体类
 * 〈功能详细描述〉
 * 
 * @author xinzhiwei
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]++++++
 */
public class LeaveMessageEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 8468325966364289479L;
    
    /** 留言ID */
    private String id;
    
    /** 留言的物流企业 */
    private String logisticsEnterprise;
    
    /** 货单号(用于从后台加载信息到前台展示，如查看详情) */
    private String waybilllNo;
    
    /** 货源号(用于从前台提交表单保存到后台，如save,update) */
    private String goodsNo;
    
    /** 货源主键id */
    private String goodsSourceId;
    
    /** 留言内容 */
    private String messageContent;
    
    /** 货源所属化工企业 */
    private String chemicalEnterprise;
    
    /** 留言时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
    
    /** 交易对象 */
    private String tradeObject;
    
    /** 发货单位 */
    private String senderCompany;
    
    /** 收货单位 */
    private String receiverCompany;
    
    /** remove */
    private String remove;
    
    /** 货源remove */
    private String srcGoodsRemove;
    
    /** 交易状态 */
    private String tradeStatus;
    
    /**
     * 〈一句话功能简述〉获得留言ID
     * 〈功能详细描述〉
     * 
     * @return id 主键
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉设置留言ID
     * 〈功能详细描述〉
     * 
     * @param id 留言ID
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 〈一句话功能简述〉获得物流企业
     * 〈功能详细描述〉
     * 
     * @return logisticsEnterprise logisticsEnterprise
     * @see [类、类#方法、类#成员]
     */
    public String getLogisticsEnterprise() {
        return logisticsEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉设置物流企业
     * 〈功能详细描述〉
     * 
     * @param logisticsEnterprise 物流企业
     * @see [类、类#方法、类#成员]
     */
    public void setLogisticsEnterprise(String logisticsEnterprise) {
        this.logisticsEnterprise = logisticsEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉获得货源号
     * 〈功能详细描述〉
     * 
     * @return waybilllNo waybilllNo
     * @see [类、类#方法、类#成员]
     */
    public String getWaybilllNo() {
        return waybilllNo;
    }
    
    /**
     * 〈一句话功能简述〉设置货源号
     * 〈功能详细描述〉
     * 
     * @param waybilllNo 货源号
     * @see [类、类#方法、类#成员]
     */
    public void setWaybilllNo(String waybilllNo) {
        this.waybilllNo = waybilllNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得货源号
     * 〈功能详细描述〉
     * 
     * @return goodsNo goodsNo
     * @see [类、类#方法、类#成员]
     */
    public String getGoodsNo() {
        return goodsNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置货源号
     * 〈功能详细描述〉
     * 
     * @param goodsNo goodsNo
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得货源主键id
     * 〈功能详细描述〉
     * 
     * @return goodsSourceId goodsSourceId
     * @see [类、类#方法、类#成员]
     */
    public String getGoodsSourceId() {
        return goodsSourceId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置货源主键id
     * 〈功能详细描述〉
     * 
     * @param goodsSourceId goodsSourceId
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsSourceId(String goodsSourceId) {
        this.goodsSourceId = goodsSourceId;
    }
    
    /**
     * 〈一句话功能简述〉获得留言内容
     * 〈功能详细描述〉
     * 
     * @return messageContent messageContent
     * @see [类、类#方法、类#成员]
     */
    public String getMessageContent() {
        return messageContent;
    }
    
    /**
     * 〈一句话功能简述〉设置留言内容
     * 〈功能详细描述〉
     * 
     * @param messageContent 留言内容
     * @see [类、类#方法、类#成员]
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    /**
     * 〈一句话功能简述〉获得化工企业
     * 〈功能详细描述〉
     * 
     * @return chemicalEnterprise chemicalEnterprise
     * @see [类、类#方法、类#成员]
     */
    public String getChemicalEnterprise() {
        return chemicalEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉设置化工企业
     * 〈功能详细描述〉
     * 
     * @param chemicalEnterprise 化工企业
     * @see [类、类#方法、类#成员]
     */
    public void setChemicalEnterprise(String chemicalEnterprise) {
        this.chemicalEnterprise = chemicalEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉获得时间
     * 〈功能详细描述〉
     * 
     * @return createdTime createdTime
     * @see [类、类#方法、类#成员]
     */
    public Date getCreatedTime() {
        return createdTime;
    }
    
    /**
     * 〈一句话功能简述〉设置时间
     * 〈功能详细描述〉
     * 
     * @param createdTime 留言时间
     * @see [类、类#方法、类#成员]
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得交易对象
     * 〈功能详细描述〉
     * 
     * @return tradeObject tradeObject
     * @see [类、类#方法、类#成员]
     */
    public String getTradeObject() {
        return tradeObject;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置交易对象
     * 〈功能详细描述〉
     * 
     * @param tradeObject tradeObject
     * @see [类、类#方法、类#成员]
     */
    public void setTradeObject(String tradeObject) {
        this.tradeObject = tradeObject;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得发货单位
     * 〈功能详细描述〉
     * 
     * @return senderCompany senderCompany
     * @see [类、类#方法、类#成员]
     */
    public String getSenderCompany() {
        return senderCompany;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置发货单位
     * 〈功能详细描述〉
     * 
     * @param senderCompany senderCompany
     * @see [类、类#方法、类#成员]
     */
    public void setSenderCompany(String senderCompany) {
        this.senderCompany = senderCompany;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得收货单位
     * 〈功能详细描述〉
     * 
     * @return receiverCompany receiverCompany
     * @see [类、类#方法、类#成员]
     */
    public String getReceiverCompany() {
        return receiverCompany;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置收货单位
     * 〈功能详细描述〉
     * 
     * @param receiverCompany receiverCompany
     * @see [类、类#方法、类#成员]
     */
    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得remove
     * 〈功能详细描述〉
     * 
     * @return remove remove
     * @see [类、类#方法、类#成员]
     */
    public String getRemove() {
        return remove;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置remove
     * 〈功能详细描述〉
     * 
     * @param remove remove
     * @see [类、类#方法、类#成员]
     */
    public void setRemove(String remove) {
        this.remove = remove;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得货源remove
     * 〈功能详细描述〉
     * 
     * @return srcGoodsRemove srcGoodsRemove
     * @see [类、类#方法、类#成员]
     */
    public String getSrcGoodsRemove() {
        return srcGoodsRemove;
    }
    
    /**
     * 〈一句话功能简述〉srcGoodsRemove
     * 〈功能详细描述〉
     * 
     * @param srcGoodsRemove srcGoodsRemove
     * @see [类、类#方法、类#成员]
     */
    public void setSrcGoodsRemove(String srcGoodsRemove) {
        this.srcGoodsRemove = srcGoodsRemove;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得交易状态
     * 〈功能详细描述〉
     * 
     * @return tradeStatus tradeStatus
     * @see [类、类#方法、类#成员]
     */
    public String getTradeStatus() {
        return tradeStatus;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置交易状态
     * 〈功能详细描述〉
     * 
     * @param tradeStatus tradeStatus
     * @see [类、类#方法、类#成员]
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
