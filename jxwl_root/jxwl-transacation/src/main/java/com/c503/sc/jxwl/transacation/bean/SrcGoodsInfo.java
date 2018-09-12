/**
 * 文件名：SrcGoodsInfo
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.math.BigDecimal;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉货源基本信息
 * 〈功能详细描述〉T_SOURCE_GOODS_INFO
 * 
 * @author zz
 * @version [版本号, 2016-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SrcGoodsInfo extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = 6927359108211887542L;
    
    /** 货源发送ID */
    private String srcGoodsId;
    
    /** 货物序号 */
    private String goodsSerialNo;
    
    /** 货物名称 */
    private String goodsName;
    
    /** 包装 */
    private String pack;
    
    /** 数量 */
    private BigDecimal amount;
    
    /** 重量 */
    private BigDecimal weight;
    
    /** 单价 */
    private BigDecimal unitPrice;
    
    /** 货物价值 */
    private BigDecimal goodsWorth;
    
    /** 体积 */
    private BigDecimal volume;
    
    /** 交易状态 */
    private String tradeStatus;
    
    /** 逻辑删除 */
    private String remove;
    
    /**
     * 〈一句话功能简述〉交易状态
     * 〈功能详细描述〉
     * 
     * @return 交易状态
     * @see [类、类#方法、类#成员]
     */
    public String getTradeStatus() {
        return tradeStatus;
    }
    
    /**
     * 〈一句话功能简述〉交易状态
     * 〈功能详细描述〉
     * 
     * @param tradeStatus 交易状态
     * @see [类、类#方法、类#成员]
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    
    /**
     * 〈一句话功能简述〉货源发送ID
     * 〈功能详细描述〉
     * 
     * @return 货源发送ID
     * @see [类、类#方法、类#成员]
     */
    public String getSrcGoodsId() {
        return srcGoodsId;
    }
    
    /**
     * 〈一句话功能简述〉货源发送ID
     * 〈功能详细描述〉
     * 
     * @param srcGoodsId 货源发送ID
     * @see [类、类#方法、类#成员]
     */
    public void setSrcGoodsId(String srcGoodsId) {
        this.srcGoodsId = srcGoodsId == null ? null : srcGoodsId.trim();
    }
    
    /**
     * 〈一句话功能简述〉货物序号
     * 〈功能详细描述〉
     * 
     * @return 货物序号
     * @see [类、类#方法、类#成员]
     */
    public String getGoodsSerialNo() {
        return goodsSerialNo;
    }
    
    /**
     * 〈一句话功能简述〉货物序号
     * 〈功能详细描述〉
     * 
     * @param goodsSerialNo 货物序号
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsSerialNo(String goodsSerialNo) {
        this.goodsSerialNo =
            goodsSerialNo == null ? null : goodsSerialNo.trim();
    }
    
    /**
     * 〈一句话功能简述〉货物名称
     * 〈功能详细描述〉
     * 
     * @return 货物名称
     * @see [类、类#方法、类#成员]
     */
    public String getGoodsName() {
        return goodsName;
    }
    
    /**
     * 〈一句话功能简述〉货物名称
     * 〈功能详细描述〉
     * 
     * @param goodsName 货物名称
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }
    
    /**
     * 〈一句话功能简述〉包装
     * 〈功能详细描述〉
     * 
     * @return 包装
     * @see [类、类#方法、类#成员]
     */
    public String getPack() {
        return pack;
    }
    
    /**
     * 〈一句话功能简述〉包装
     * 〈功能详细描述〉
     * 
     * @param pack 包装
     * @see [类、类#方法、类#成员]
     */
    public void setPack(String pack) {
        this.pack = pack == null ? null : pack.trim();
    }
    
    /**
     * 〈一句话功能简述〉数量
     * 〈功能详细描述〉
     * 
     * @return 数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getAmount() {
        return amount;
    }
    
    /**
     * 〈一句话功能简述〉数量
     * 〈功能详细描述〉
     * 
     * @param amount 数量
     * @see [类、类#方法、类#成员]
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    /**
     * 〈一句话功能简述〉重量
     * 〈功能详细描述〉
     * 
     * @return 重量
     * @see [类、类#方 法、类#成员]
     */
    public BigDecimal getWeight() {
        return weight;
    }
    
    /**
     * 〈一句话功能简述〉重量
     * 〈功能详细描述〉
     * 
     * @param weight 重量
     * @see [类、类#方法、类#成员]
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    /**
     * 〈一句话功能简述〉单价
     * 〈功能详细描述〉
     * 
     * @return 单价
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    /**
     * 〈一句话功能简述〉单价
     * 〈功能详细描述〉
     * 
     * @param unitPrice 单价
     * @see [类、类#方法、类#成员]
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    /**
     * 〈一句话功能简述〉货物价值
     * 〈功能详细描述〉
     * 
     * @return 货物价值
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getGoodsWorth() {
        return goodsWorth;
    }
    
    /**
     * 〈一句话功能简述〉货物价值
     * 〈功能详细描述〉
     * 
     * @param goodsWorth 货物价值
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsWorth(BigDecimal goodsWorth) {
        this.goodsWorth = goodsWorth;
    }
    
    /**
     * 〈一句话功能简述〉体积
     * 〈功能详细描述〉
     * 
     * @return 体积
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getVolume() {
        return volume;
    }
    
    /**
     * 〈一句话功能简述〉体积
     * 〈功能详细描述〉
     * 
     * @param volume 体积
     * @see [类、类#方法、类#成员]
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    
    /**
     * 〈一句话功能简述〉逻辑删除
     * 〈功能详细描述〉
     * 
     * @param volume 体积
     * @see [类、类#方法、类#成员]
     */
    public String getRemove() {
		return remove;
	}
    
    /**
     * 〈一句话功能简述〉逻辑删除
     * 〈功能详细描述〉
     * 
     * @param volume 体积
     * @see [类、类#方法、类#成员]
     */
	public void setRemove(String remove) {
		this.remove = remove;
	}

	@Override
    protected Object getBaseEntity() {
        return this;
    }
}
