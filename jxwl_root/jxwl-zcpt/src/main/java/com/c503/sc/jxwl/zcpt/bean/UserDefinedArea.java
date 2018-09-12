/**
 * 文件名：UserDefinedArea.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉用户自定义区域
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDefinedArea extends BaseEntity {
    /** 序列号号 */
    private static final long serialVersionUID = -3573005840703434582L;
    
    /** 区域限制点集合 */
    private String points;
    
    /** 区域名称 */
    private String areaName;
    
    /**
     * 〈一句话功能简述〉构造方法
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public UserDefinedArea() {
        super();
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id
     * @param userId
     * @param points
     * @param areaName
     * @see [类、类#方法、类#成员]
     */
    public UserDefinedArea(String id, String userId, String points,
        String areaName) {
        super.setId(id);
        super.setCreateBy(userId);
        super.setUpdateBy(userId);
        this.points = points;
        this.areaName = areaName;
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
     * 〈一句话功能简述〉获得areaName
     * 〈功能详细描述〉
     * 
     * @return areaName
     * @see [类、类#方法、类#成员]
     */
    public String getAreaName() {
        return areaName;
    }
    
    /**
     * 〈一句话功能简述〉设置areaName
     * 〈功能详细描述〉
     * 
     * @param areaName areaName
     * @see [类、类#方法、类#成员]
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
