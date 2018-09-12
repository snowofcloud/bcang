/*
 * 文件名：LimitAreaForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.formbean;

import java.math.BigDecimal;

import com.c503.sc.base.formbean.BaseForm;

/**
 * 〈一句话功能简述〉限制区域表单
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-10-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LimitAreaForm extends BaseForm {
    
    /** 序列化号 */
    private static final long serialVersionUID = 4170831740023357445L;
    
    /** 限制区域名称 */
    private String limitName;
    
    /** 限制区域点集合字符串 */
    private String pointSet;
    
    /** 点 */
    private String pointList;
    
    /** 限制类型 */
    private String limitType;
    
    /** 限制速度 */
    private BigDecimal limitSpeed;

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public String getPointSet() {
        return pointSet;
    }

    public void setPointSet(String pointSet) {
        this.pointSet = pointSet;
    }

    public String getPointList() {
        return pointList;
    }

    public void setPointList(String pointList) {
        this.pointList = pointList;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public BigDecimal getLimitSpeed() {
        return limitSpeed;
    }

    public void setLimitSpeed(BigDecimal limitSpeed) {
        this.limitSpeed = limitSpeed;
    }
}
