/*
 * 文件名：ManagerEntity
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉用户实体模型
 * 修改时间：2015年6月5日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.bean;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉ManagerEntity
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ManagerEntity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 2819272905297847668L;
    
    /** id */
    private String id;
    
    /** 法人代码 */
    private String corporateCode;
    
    /**
     * 〈一句话功能简述〉id
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉id
     * 〈功能详细描述〉
     * 
     * @param id id
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @return 法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateCode() {
        return corporateCode;
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateCode 法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateCode(String corporateCode) {
        this.corporateCode =
            corporateCode == null ? null : corporateCode.trim();
    }
}
