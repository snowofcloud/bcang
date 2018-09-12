/**
 * 文件名：SrcGoodsInfo
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.formbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SrcGoodsForm implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 6927375328211887542L;
    
    /** true:保存并发布、false:保存 */
    private Boolean publishOrNot;
    
    /** 货单号 */
    private String waybilllNo;
    
    /** 截止日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;
    
    /** 发货地址 */
    private String senderAddr;
    
    /** 发货单位 */
    private String senderCompany;
    
    /** 发货单位联系电话 */
    private String senderTel;
    
    /** 收货地址 */
    private String receiverAddr;
    
    /** 收货单位 */
    private String receiverCompany;
    
    /** 收货单位联系电话 */
    private String receiverTel;
    
    /** 交易状态 */
    private String tradeStatus;
    
    /** 化工企业法人代码 */
    private String hgCorporateNo;
    
    /** 物流企业法人代码 */
    private String wlCorporateNo;
    
    /** 发布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDate;
    
    /** 货源具体信息（列表） */
    private List<SrcGoodsInfoForm> goodsInfos;
    
    /** 订单号 */
    private String orderNo;
    
    /** 订单生成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;
    
    /** 起点 */
    private String startPoint;
    
    /** 终点 */
    private String endPoint;
    
    /** 发送单位联系人 */
    private String senderLinker;
    
    /** 接收单位联系人 */
    private String receiverLinker;
    
    /** 所需运输车辆类型 */
    private String carType;
    
    /** 所需车长 */
    private BigDecimal carLength;
    
    
    /** 起点经度 */
    private String startLng;
    
    /** 起点纬度 */
    private String startLat;
    
    /** 到点经度 */
    private String endLng;
    
    /** 到点纬度 */
    private String endLat;
    /**
     * 〈一句话功能简述〉startLng
     * 〈功能详细描述〉
     * 
     * @return startLng
     * @see [类、类#方法、类#成员]
     */
    public String getStartLng() {
        return startLng;
    }
    /**
     * 〈一句话功能简述〉startLng
     * 〈功能详细描述〉
     * 
     * @param startLng startLng
     * @see [类、类#方法、类#成员]
     */
    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }
    
    /**
     * 〈一句话功能简述〉startLat
     * 〈功能详细描述〉
     * 
     * @return startLat
     * @see [类、类#方法、类#成员]
     */
    public String getStartLat() {
        return startLat;
    }
    /**
     * 〈一句话功能简述〉startLat
     * 〈功能详细描述〉
     * 
     * @param startLat startLat
     * @see [类、类#方法、类#成员]
     */
    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }
    /**
     * 〈一句话功能简述〉endLng
     * 〈功能详细描述〉
     * 
     * @return endLng
     * @see [类、类#方法、类#成员]
     */
    public String getEndLng() {
        return endLng;
    }
    /**
     * 〈一句话功能简述〉endLng
     * 〈功能详细描述〉
     * 
     * @param endLng endLng
     * @see [类、类#方法、类#成员]
     */
    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }
    /**
     * 〈一句话功能简述〉endLat
     * 〈功能详细描述〉
     * 
     * @return endLat
     * @see [类、类#方法、类#成员]
     */
    public String getEndLat() {
        return endLat;
    }
    /**
     * 〈一句话功能简述〉endLat
     * 〈功能详细描述〉
     * 
     * @param endLat endLat
     * @see [类、类#方法、类#成员]
     */
    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }
    /**
     * 〈一句话功能简述〉true:保存并发布、false:保存
     * 〈功能详细描述〉
     * 
     * @return true:保存并发布、false:保存
     * @see [类、类#方法、类#成员]
     */
    public Boolean getPublishOrNot() {
        return publishOrNot;
    }
    
    /**
     * 〈一句话功能简述〉true:保存并发布、false:保存
     * 〈功能详细描述〉
     * 
     * @param publishOrNot true:保存并发布、false:保存
     * @see [类、类#方法、类#成员]
     */
    public void setPublishOrNot(Boolean publishOrNot) {
        this.publishOrNot = publishOrNot;
    }
    
    /**
     * 〈一句话功能简述〉货单号
     * 〈功能详细描述〉
     * 
     * @return 货单号
     * @see [类、类#方法、类#成员]
     */
    public String getWaybilllNo() {
        return waybilllNo;
    }
    
    /**
     * 〈一句话功能简述〉货单号
     * 〈功能详细描述〉
     * 
     * @param waybilllNo 货单号
     * @see [类、类#方法、类#成员]
     */
    public void setWaybilllNo(String waybilllNo) {
        this.waybilllNo = waybilllNo == null ? null : waybilllNo.trim();
    }
    
    /**
     * 〈一句话功能简述〉截止日期
     * 〈功能详细描述〉
     * 
     * @return 截止日期
     * @see [类、类#方法、类#成员]
     */
    public Date getEndDate() {
        return endDate;
    }
    
    /**
     * 〈一句话功能简述〉截止日期
     * 〈功能详细描述〉
     * 
     * @param endDate 截止日期
     * @see [类、类#方法、类#成员]
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 〈一句话功能简述〉发货地址
     * 〈功能详细描述〉
     * 
     * @return 发货地址
     * @see [类、类#方法、类#成员]
     */
    public String getSenderAddr() {
        return senderAddr;
    }
    
    /**
     * 〈一句话功能简述〉发货地址
     * 〈功能详细描述〉
     * 
     * @param senderAddr 发货地址
     * @see [类、类#方法、类#成员]
     */
    public void setSenderAddr(String senderAddr) {
        this.senderAddr = senderAddr == null ? null : senderAddr.trim();
    }
    
    /**
     * 〈一句话功能简述〉发货单位
     * 〈功能详细描述〉
     * 
     * @return 发货单位
     * @see [类、类#方法、类#成员]
     */
    public String getSenderCompany() {
        return senderCompany;
    }
    
    /**
     * 〈一句话功能简述〉发货单位
     * 〈功能详细描述〉
     * 
     * @param senderCompany 发货单位
     * @see [类、类#方法、类#成员]
     */
    public void setSenderCompany(String senderCompany) {
        this.senderCompany =
            senderCompany == null ? null : senderCompany.trim();
    }
    
    /**
     * 〈一句话功能简述〉发货单位联系电话
     * 〈功能详细描述〉
     * 
     * @return 发货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public String getSenderTel() {
        return senderTel;
    }
    
    /**
     * 〈一句话功能简述〉发货单位联系电话
     * 〈功能详细描述〉
     * 
     * @param senderTel 发货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel == null ? null : senderTel.trim();
    }
    
    /**
     * 〈一句话功能简述〉收货地址
     * 〈功能详细描述〉
     * 
     * @return 收货地址
     * @see [类、类#方法、类#成员]
     */
    public String getReceiverAddr() {
        return receiverAddr;
    }
    
    /**
     * 〈一句话功能简述〉收货地址
     * 〈功能详细描述〉
     * 
     * @param receiverAddr 收货地址
     * @see [类、类#方法、类#成员]
     */
    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr == null ? null : receiverAddr.trim();
    }
    
    /**
     * 〈一句话功能简述〉收货单位
     * 〈功能详细描述〉
     * 
     * @return 收货单位
     * @see [类、类#方法、类#成员]
     */
    public String getReceiverCompany() {
        return receiverCompany;
    }
    
    /**
     * 〈一句话功能简述〉收货单位
     * 〈功能详细描述〉
     * 
     * @param receiverCompany 收货单位
     * @see [类、类#方法、类#成员]
     */
    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany =
            receiverCompany == null ? null : receiverCompany.trim();
    }
    
    /**
     * 〈一句话功能简述〉收货单位联系电话
     * 〈功能详细描述〉
     * 
     * @return 收货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public String getReceiverTel() {
        return receiverTel;
    }
    
    /**
     * 〈一句话功能简述〉收货单位联系电话
     * 〈功能详细描述〉
     * 
     * @param receiverTel 收货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel == null ? null : receiverTel.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取交易状态
     * 〈功能详细描述〉
     * 
     * @return tradeStatus
     * @see [类、类#方法、类#成员]
     */
    public String getTradeStatus() {
        return tradeStatus;
    }
    
    /**
     * 〈一句话功能简述〉设置交易状态
     * 〈功能详细描述〉
     * 
     * @param tradeStatus tradeStatus
     * @see [类、类#方法、类#成员]
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取化工企业法人代码
     * 〈功能详细描述〉
     * 
     * @return hgCorporateNo
     * @see [类、类#方法、类#成员]
     */
    public String getHgCorporateNo() {
        return hgCorporateNo;
    }
    
    /**
     * 〈一句话功能简述〉设置化工企业法人代码
     * 〈功能详细描述〉
     * 
     * @param hgCorporateNo hgCorporateNo
     * @see [类、类#方法、类#成员]
     */
    public void setHgCorporateNo(String hgCorporateNo) {
        this.hgCorporateNo =
            hgCorporateNo == null ? null : hgCorporateNo.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取物流企业法人代码
     * 〈功能详细描述〉
     * 
     * @return wlCorporateNo
     * @see [类、类#方法、类#成员]
     */
    public String getWlCorporateNo() {
        return wlCorporateNo;
    }
    
    /**
     * 〈一句话功能简述〉设置物流企业法人代码
     * 〈功能详细描述〉
     * 
     * @param wlCorporateNo wlCorporateNo
     * @see [类、类#方法、类#成员]
     */
    public void setWlCorporateNo(String wlCorporateNo) {
        this.wlCorporateNo =
            wlCorporateNo == null ? null : wlCorporateNo.trim();
    }
    
    /**
     * 〈一句话功能简述〉发布日期
     * 〈功能详细描述〉
     * 
     * @return 发布日期
     * @see [类、类#方法、类#成员]
     */
    public Date getPublishDate() {
        return publishDate;
    }
    
    /**
     * 〈一句话功能简述〉发布日期
     * 〈功能详细描述〉
     * 
     * @param publishDate 发布日期
     * @see [类、类#方法、类#成员]
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    
    /**
     * 〈一句话功能简述〉货源具体信息（列表）
     * 〈功能详细描述〉
     * 
     * @return 货源具体信息（列表）
     * @see [类、类#方法、类#成员]
     */
    public List<SrcGoodsInfoForm> getGoodsInfos() {
        return goodsInfos;
    }
    
    /**
     * 〈一句话功能简述〉货源具体信息（列表）
     * 〈功能详细描述〉
     * 
     * @param goodsInfos List<SrcGoodsInfoForm>
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsInfos(List<SrcGoodsInfoForm> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }
    
    /**
     * 〈一句话功能简述〉获取订单号
     * 〈功能详细描述〉
     * 
     * @return orderNo
     * @see [类、类#方法、类#成员]
     */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 〈一句话功能简述〉设置订单号
     * 〈功能详细描述〉
     * 
     * @param orderNo orderNo
     * @see [类、类#方法、类#成员]
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    /**
     * 〈一句话功能简述〉订单生成日期
     * 〈功能详细描述〉
     * 
     * @return 订单生成日期
     * @see [类、类#方法、类#成员]
     */
    public Date getOrderDate() {
        return orderDate;
    }
    
    /**
     * 〈一句话功能简述〉订单生成日期
     * 〈功能详细描述〉
     * 
     * @param orderDate 订单生成日期
     * @see [类、类#方法、类#成员]
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    /**
     * 〈一句话功能简述〉起点
     * 〈功能详细描述〉
     * 
     * @return 起点
     * @see [类、类#方法、类#成员]
     */
    public String getStartPoint() {
        return startPoint;
    }
    
    /**
     * 〈一句话功能简述〉起点
     * 〈功能详细描述〉
     * 
     * @param startPoint 起点
     * @see [类、类#方法、类#成员]
     */
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }
    
    /**
     * 〈一句话功能简述〉终点
     * 〈功能详细描述〉
     * 
     * @return 终点
     * @see [类、类#方法、类#成员]
     */
    public String getEndPoint() {
        return endPoint;
    }
    
    /**
     * 〈一句话功能简述〉终点
     * 〈功能详细描述〉
     * 
     * @param endPoint 终点
     * @see [类、类#方法、 类#成员]
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    
    /**
     * 〈一句话功能简述〉发送单位联系人
     * 〈功能详细描述〉
     * 
     * @return 发送单位联系人
     * @see [类、类#方法、类#成员]
     */
    public String getSenderLinker() {
        return senderLinker;
    }
    
    /**
     * 〈一句话功能简述〉发送单位联系人
     * 〈功能详细描述〉
     * 
     * @param senderLinker 发送单位联系人
     * @see [类、类#方法、类#成员]
     */
    public void setSenderLinker(String senderLinker) {
        this.senderLinker = senderLinker;
    }
    
    /**
     * 〈一句话功能简述〉接收单位联系人
     * 〈功能详细描述〉
     * 
     * @return 接收单位联系人
     * @see [类、类#方法、类#成员]
     */
    public String getReceiverLinker() {
        return receiverLinker;
    }
    
    /**
     * 〈一句话功能简述〉接收单位联系人
     * 〈功能详细描述〉
     * 
     * @param receiverLinker 接收单位联系人
     * @see [类、类#方法、类#成员]
     */
    public void setReceiverLinker(String receiverLinker) {
        this.receiverLinker = receiverLinker;
    }
    
    /**
     * 〈一句话功能简述〉所需运输车辆类型
     * 〈功能详细描述〉
     * 
     * @return 所需运输车辆类型
     * @see [类、类#方法、类#成员]
     */
    public String getCarType() {
        return carType;
    }
    
    /**
     * 〈一句话功能简述〉所需运输车辆类型
     * 〈功能详细描述〉
     * 
     * @param carType 所需运输车辆类型
     * @see [类、类#方法、类#成员]
     */
    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }
    
    /**
     * 〈一句话功能简述〉所需车长
     * 〈功能详细描述〉
     * 
     * @return 所需车长
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCarLength() {
        return carLength;
    }
    
    /**
     * 〈一句话功能简述〉所需车长
     * 〈功能详细描述〉
     * 
     * @param carLength 所需车长
     * @see [类、类#方法、类#成员]
     */
    public void setCarLength(BigDecimal carLength) {
        this.carLength = carLength;
    }
}
