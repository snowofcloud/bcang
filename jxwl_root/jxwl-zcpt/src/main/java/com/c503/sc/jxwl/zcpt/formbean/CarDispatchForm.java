/*
 * 文件名：CarDispatchForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.formbean;

import com.c503.sc.base.formbean.BaseForm;

public class CarDispatchForm extends BaseForm {
    
    /** 序列化版本号 */
    private static final long serialVersionUID = 4329686376408113515L;
    
    /** 调度信号（1） */
    private String sign;
    
    /** 车牌号 */
    private String carrierName;
    
    /** 用户名 */
    private String userName;
    
    /** 调度内容 */
    private String content;
    
    /***/
    private String mark;
    
    /***/
    private String account;
    
    /** 调度标识 */
    private String carDispatchFlag;
    
    /** 手机号 */
    private String telePhone;
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public String getCarrierName() {
        return carrierName;
    }
    
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getMark() {
        return mark;
    }
    
    public void setMark(String mark) {
        this.mark = mark;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getCarDispatchFlag() {
        return carDispatchFlag;
    }
    
    public void setCarDispatchFlag(String carDispatchFlag) {
        this.carDispatchFlag = carDispatchFlag;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }
    
}
