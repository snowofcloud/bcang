/**
 * 文件名：EnterpriseFileEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-2
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-8-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnterpriseFileEntity extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = 9169856404441698817L;
    
    /** 企业id */
    private String enterpriseId;
    
    /** 附件id */
    private String fileId;
    
    /** 附件名字 */
    private String fileName;
    
    /**
     * 〈一句话功能简述〉获得enterpriseId
     * 〈功能详细描述〉
     * 
     * @return enterpriseId
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }
    
    /**
     * 〈一句话功能简述〉设置enterpriseId
     * 〈功能详细描述〉
     * 
     * @param enterpriseId enterpriseId
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    
    /**
     * 〈一句话功能简述〉获得fileId
     * 〈功能详细描述〉
     * 
     * @return fileId
     * @see [类、类#方法、类#成员]
     */
    public String getFileId() {
        return fileId;
    }
    
    /**
     * 〈一句话功能简述〉设置fileId
     * 〈功能详细描述〉
     * 
     * @param fileId fileId
     * @see [类、类#方法、类#成员]
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
    /**
     * 〈一句话功能简述〉获得fileName
     * 〈功能详细描述〉
     * 
     * @return fileName
     * @see [类、类#方法、类#成员]
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * 〈一句话功能简述〉设置fileName
     * 〈功能详细描述〉
     * 
     * @param fileName fileName
     * @see [类、类#方法、类#成员]
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
