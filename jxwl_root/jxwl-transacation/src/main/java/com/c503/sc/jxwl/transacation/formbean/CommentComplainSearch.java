/**
 * 文件名：CommentComplainSearch.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.formbean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.formbean.PageForm;
import com.c503.sc.utils.basetools.C503DateUtils;

/**
 * 
 * 〈一句话功能简述〉评价、投诉搜索对象
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommentComplainSearch extends PageForm {
    /** 序列号 */
    private static final long serialVersionUID = 2143457856339348085L;
    
    /** 0：订单评价；1：投诉 */
    private String moudle;
    
    /** 0：来自企业；1：给他人 */
    private String who;
    
    /** orderNo */
    private String orderNo;
    
    /** startTime */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date startTime;
    
    /** endTime */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date endTime;
    
    /** corporateNo（形象展示所用） */
    private String corporateNo;
    
    /**
     * 〈一句话功能简述〉0：订单评价；1：投诉
     * 〈功能详细描述〉
     * 
     * @return 0：订单评价；1：投诉
     * @see [类、类#方法、类#成员]
     */
    public String getMoudle() {
        return moudle;
    }
    
    /**
     * 〈一句话功能简述〉0：订单评价；1：投诉
     * 〈功能详细描述〉
     * 
     * @param moudle 0：订单评价；1：投诉
     * @see [类、类#方法、类#成员]
     */
    public void setMoudle(String moudle) {
        this.moudle = moudle;
    }
    
    /**
     * 〈一句话功能简述〉0：来自企业；1：给他人
     * 〈功能详细描述〉
     * 
     * @return 0：来自企业；1：给他人
     * @see [类、类#方法、类#成员]
     */
    public String getWho() {
        return who;
    }
    
    /**
     * 〈一句话功能简述〉0：来自企业；1：给他人
     * 〈功能详细描述〉
     * 
     * @param who 0：来自企业；1：给他人
     * @see [类、类#方法、类#成员]
     */
    public void setWho(String who) {
        this.who = who;
    }
    
    /**
     * 〈一句话功能简述〉orderNo
     * 〈功能详细描述〉
     * 
     * @return orderNo
     * @see [类、类#方法、类#成员]
     */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 〈一句话功能简述〉orderNo
     * 〈功能详细描述〉
     * 
     * @param orderNo orderNo
     * @see [类、类#方法、类#成员]
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    
    /**
     * 〈一句话功能简述〉startTime
     * 〈功能详细描述〉
     * 
     * @return startTime
     * @see [类、类#方法、类#成员]
     */
    public Date getStartTime() {
        return startTime;
    }
    
    /**
     * 〈一句话功能简述〉startTime
     * 〈功能详细描述〉
     * 
     * @param startTime startTime
     * @see [类、类#方法、类#成员]
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * 〈一句话功能简述〉endTime
     * 〈功能详细描述〉
     * 
     * @return endTime
     * @see [类、类#方法、类#成员]
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 〈一句话功能简述〉endTime
     * 〈功能详细描述〉
     * 
     * @param endTime endTime
     * @see [类、类#方法、类#成员]
     */
    public void setEndTime(Date endTime) {
        this.endTime =
            endTime == null ? null : C503DateUtils.getDay(1, endTime);
    }
    
    /**
     * 〈一句话功能简述〉corporateNo
     * 〈功能详细描述〉
     * 
     * @return corporateNo
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉corporateNo
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
}
