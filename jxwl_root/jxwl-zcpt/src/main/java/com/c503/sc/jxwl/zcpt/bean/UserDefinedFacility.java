/**
 * 文件名：UserDefinedFacility.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉自定义设施
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDefinedFacility extends BaseEntity {
    
    /** 序列化号 */
    private static final long serialVersionUID = -2031994471371213538L;
    
    /** 设施名称 */
    private String facilityName;
    
    /** 图标 */
    private String iconId;
    
    /** 区域限制点集合 */
    private String points;
    
    /** 经度 */
    private String longitude;
    
    /** 纬度 */
    private String latitude;
    
    /**
     * 〈一句话功能简述〉获得longitude
     * 〈功能详细描述〉
     * 
     * @return longitude
     * @see [类、类#方法、类#成员]
     */
    public String getLongitude() {
        return longitude;
    }
    
    /**
     * 〈一句话功能简述〉设置longitude
     * 〈功能详细描述〉
     * 
     * @param longitude longitude
     * @see [类、类#方法、类#成员]
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 〈一句话功能简述〉获得latitude
     * 〈功能详细描述〉
     * 
     * @return latitude
     * @see [类、类#方法、类#成员]
     */
    public String getLatitude() {
        return latitude;
    }
    
    /**
     * 〈一句话功能简述〉设置latitude
     * 〈功能详细描述〉
     * 
     * @param latitude latitude
     * @see [类、类#方法、类#成员]
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    /**
     * 〈一句话功能简述〉获得points
     * 〈功能详细描述〉
     * 
     * @return points
     * @see [类、类#方法、类#成员]
     */
    public String getPoints() {
        return points;
    }
    
    /**
     * 〈一句话功能简述〉设置points
     * 〈功能详细描述〉
     * 
     * @param points points
     * @see [类、类#方法、类#成员]
     */
    public void setPoints(String points) {
        this.points = points == null ? null : points.trim();
    }
    
    /**
     * 〈一句话功能简述〉构造方法
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public UserDefinedFacility() {
        super();
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id
     * @param userId
     * @param iconId
     * @param facilityName
     * @see [类、类#方法、类#成员]
     */
    public UserDefinedFacility(String id, String userId, String iconId,
        String facilityName, String points, String longitude, String latitude) {
        super.setId(id);
        super.setCreateBy(userId);
        super.setUpdateBy(userId);
        this.iconId = iconId;
        this.facilityName = facilityName;
        this.points = points;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    /**
     * 〈一句话功能简述〉获得facilityName
     * 〈功能详细描述〉
     * 
     * @return facilityName
     * @see [类、类#方法、类#成员]
     */
    public String getFacilityName() {
        return facilityName;
    }
    
    /**
     * 〈一句话功能简述〉设置facilityName
     * 〈功能详细描述〉
     * 
     * @param facilityName facilityName
     * @see [类、类#方法、类#成员]
     */
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    
    /**
     * 〈一句话功能简述〉获得iconId
     * 〈功能详细描述〉
     * 
     * @return iconId
     * @see [类、类#方法、类#成员]
     */
    public String getIconId() {
        return iconId;
    }
    
    /**
     * 〈一句话功能简述〉设置iconId
     * 〈功能详细描述〉
     * 
     * @param iconId iconId
     * @see [类、类#方法、类#成员]
     */
    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
