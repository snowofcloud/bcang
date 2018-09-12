/**
 * 文件名：DangerFileRelaEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-9 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import com.c503.sc.base.entity.BaseEntity;


/**
 * 〈一句话功能简述〉危险品车辆附件关系实体类
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-9]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public class DangerFileRelaEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 危险品车辆ID */
    private String dangerVehicleId;
    
    /** 附件ID*/
    private String fileId;
    
    /** 附件名字 */
    private String fileName;

    /**
     * 
     * 〈一句话功能简述〉 获取危险品车辆ID
     * 〈功能详细描述〉
     * 
     * @return 危险品车辆ID
     * @see [类、类#方法、类#成员]
     */
    public String getDangerVehicleId() {
        return dangerVehicleId;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置危险品车辆ID
     * 〈功能详细描述〉
     * 
     * @param dangerVehicleId 危险品车辆ID
     * @see [类、类#方法、类#成员]
     */
    public void setDangerVehicleId(String dangerVehicleId) {
        this.dangerVehicleId = dangerVehicleId == null ? null : dangerVehicleId.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取附件ID
     * 〈功能详细描述〉
     * 
     * @return 附件ID
     * @see [类、类#方法、类#成员]
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置附件ID
     * 〈功能详细描述〉
     * 
     * @param fileId 附件ID
     * @see [类、类#方法、类#成员]
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 〈一句话功能简述〉附件名字
     * 〈功能详细描述〉
     * 
     * @return fileName:附件名字
     * @see [类、类#方法、类#成员]
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * 〈一句话功能简述〉附件名字
     * 〈功能详细描述〉
     * 
     * @param fileName 附件名字
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
