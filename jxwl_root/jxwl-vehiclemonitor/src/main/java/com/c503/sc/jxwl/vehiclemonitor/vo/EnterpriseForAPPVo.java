/**
 * 文件名：AlarmNumVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.vo;


/**
 * 
 * 〈一句话功能简述〉返回企业信息VO
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnterpriseForAPPVo {
	/** 法人代码 */
	private String corporateNo;

	/** 企业名称 */
	private String enterpriseName;
	/**
	 * 〈一句话功能简述〉获得法人代码 〈功能详细描述〉
	 * 
	 * @return corporateNo
	 * @see [类、类#方法、类#成员]
	 */
	public String getCorporateNo() {
		return corporateNo;
	}
	/**
	 * 〈一句话功能简述〉设置法人代码 〈功能详细描述〉
	 * 
	 * @param corporateNo
	 *            法人代码
	 * @see [类、类#方法、类#成员]
	 */
	public void setCorporateNo(String corporateNo) {
		this.corporateNo = corporateNo;
	}
	/**
	 * 〈一句话功能简述〉获得企业名称 〈功能详细描述〉
	 * 
	 * @return enterpriseName
	 * @see [类、类#方法、类#成员]
	 */
	public String getEnterpriseName() {
		return enterpriseName;
	}
	/**
	 * 〈一句话功能简述〉设置企业名称 〈功能详细描述〉
	 * 
	 * @param enterpriseName
	 *            企业名称
	 * @see [类、类#方法、类#成员]
	 */
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
    
    
    
}
