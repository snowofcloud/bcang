/**
 * 文件名：SrcGoodsInfo
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OrderForFull {
	/** 货物名称 */
	private String hgEnterpriseName;

	/** 起点 */
	private String wlEnterpriseName;

	/** 数量 */
	private BigDecimal amount;

	/** 联系方式 */
	private String goodsName;

	/** 交易状态 */
	private String tradeStatus;

	/**
	 * @return hgEnterpriseName
	 */
	public String getHgEnterpriseName() {
		return hgEnterpriseName;
	}

	/**
	 * @param hgEnterpriseName
	 *            hgEnterpriseName
	 */
	public void setHgEnterpriseName(String hgEnterpriseName) {
		this.hgEnterpriseName = hgEnterpriseName;
	}

	/**
	 * @return wlEnterpriseName
	 */
	public String getWlEnterpriseName() {
		return wlEnterpriseName;
	}

	/**
	 * @param wlEnterpriseName
	 *            wlEnterpriseName
	 */
	public void setWlEnterpriseName(String wlEnterpriseName) {
		this.wlEnterpriseName = wlEnterpriseName;
	}

	/**
	 * 〈一句话功能简述〉货物名称 〈功能详细描述〉
	 * 
	 * @return 货物名称
	 * @see [类、类#方法、类#成员]
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 〈一句话功能简述〉货物名称 〈功能详细描述〉
	 * 
	 * @param goodsName
	 *            货物名称
	 * @see [类、类#方法、类#成员]
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	/**
	 * 〈一句话功能简述〉数量 〈功能详细描述〉
	 * 
	 * @return 数量
	 * @see [类、类#方法、类#成员]
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 〈一句话功能简述〉数量 〈功能详细描述〉
	 * 
	 * @param amount
	 *            数量
	 * @see [类、类#方法、类#成员]
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return 交易状态
	 */
	public String getTradeStatus() {
		return tradeStatus;
	}

	/**
	 * @param tradeStatus
	 *            交易状态
	 */
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

}
