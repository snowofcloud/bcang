package com.c503.sc.jxwl.transacation.vo;

import java.util.Date;

import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BayOne extends WayBillEntity {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /** verifystatus */
    private String verifystatus;
    
    /** bayonetport */
    private String bayonetport;
    
    /** verifyperson */
    private String verifyperson;
    
    /** verifytime */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date verifytime;
    
    /**
     * 〈一句话功能简述〉verifystatus
     * 〈功能详细描述〉
     * 
     * @return verifystatus
     * @see [类、类#方法、类#成员]
     */
    public String getVerifystatus() {
        return verifystatus;
    }
    
    /**
     * 〈一句话功能简述〉verifystatus
     * 〈功能详细描述〉
     * 
     * @param verifystatus verifystatus
     * @see [类、类#方法、类#成员]
     */
    public void setVerifystatus(String verifystatus) {
        this.verifystatus = verifystatus;
    }
    
    /**
     * 〈一句话功能简述〉 verifyperson
     * 〈功能详细描述〉
     * 
     * @return verifyperson
     * @see [类、类#方法、类#成员]
     */
    public String getVerifyperson() {
        return verifyperson;
    }
    
    /**
     * 〈一句话功能简述〉verifyperson
     * 〈功能详细描述〉
     * 
     * @param verifyperson verifyperson
     * @see [类、类#方法、类#成员]
     */
    public void setVerifyperson(String verifyperson) {
        this.verifyperson = verifyperson;
    }
    
    /**
     * 〈一句话功能简述〉 verifytime
     * 〈功能详细描述〉
     * 
     * @return verifytime
     * @see [类、类#方法、类#成员]
     */
    public Date getVerifytime() {
        return verifytime;
    }
    
    /**
     * 〈一句话功能简述〉verifytime
     * 〈功能详细描述〉
     * 
     * @param verifytime verifytime
     * @see [类、类#方法、类#成员]
     */
    public void setVerifytime(Date verifytime) {
        this.verifytime = verifytime;
    }
    
    /**
     * 〈一句话功能简述〉 bayonetport
     * 〈功能详细描述〉
     * 
     * @return bayonetport
     * @see [类、类#方法、类#成员]
     */
    public String getBayonetport() {
        return bayonetport;
    }
    
    /**
     * 〈一句话功能简述〉bayonetport
     * 〈功能详细描述〉
     * 
     * @param bayonetport bayonetport
     * @see [类、类#方法、类#成员]
     */
    public void setBayonetport(String bayonetport) {
        this.bayonetport = bayonetport;
    }
    
}
