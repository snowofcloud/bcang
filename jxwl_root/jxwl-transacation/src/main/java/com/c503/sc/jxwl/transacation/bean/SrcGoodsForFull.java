/**
 * 文件名：SrcGoodsInfo
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SrcGoodsForFull {
	/** 货物名称 */
	private String goodsName;

	/** 发布日期 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publishDate;

	/** 起点 */
	private String startPoint;

	/** 终点 */
	private String endPoint;
	
	/** 数量 */
	private BigDecimal amount;
	
	/** 联系方式 */
	private String tel;

	/**
	 * 〈一句话功能简述〉发布日期 〈功能详细描述〉
	 * 
	 * @return 发布日期
	 * @see [类、类#方法、类#成员]
	 */
	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * 〈一句话功能简述〉发布日期 〈功能详细描述〉
	 * 
	 * @param publishDate
	 *            发布日期
	 * @see [类、类#方法、类#成员]
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * 〈一句话功能简述〉起点 〈功能详细描述〉
	 * 
	 * @return 起点
	 * @see [类、类#方法、类#成员]
	 */
	public String getStartPoint() {
		return startPoint;
	}

	/**
	 * 〈一句话功能简述〉起点 〈功能详细描述〉
	 * 
	 * @param startPoint
	 *            起点
	 * @see [类、类#方法、类#成员]
	 */
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * 〈一句话功能简述〉终点 〈功能详细描述〉
	 * 
	 * @return 终点
	 * @see [类、类#方法、类#成员]
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * 〈一句话功能简述〉终点 〈功能详细描述〉
	 * 
	 * @param endPoint
	 *            终点
	 * @see [类、类#方法、 类#成员]
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
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
	 * @return 联系方式
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel 联系方式
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
}
