/**
 * 文件名：LimitArea.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import java.math.BigDecimal;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉区域限制
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LimitArea extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = 94813564864425913L;
    
    /** 区域限制点集合 */
    private String points;
    
    /** 限制名称 */
    private String limitName;
    
    /** 限制类型 */
    private String limitType;
    
    /** 限制速度 */
    private BigDecimal limitSpeed;
    
    /**
     * 〈一句话功能简述〉构造方法
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public LimitArea() {
        super();
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param userId userId
     * @param points points
     * @param limitName limitName
     * @param limitType limitType
     * @param limitSpeed limitSpeed
     * @see [类、类#方法、类#成员]
     */
    public LimitArea(String id, String userId, String points, String limitName,
        String limitType, BigDecimal limitSpeed) {
        super.setId(id);
        super.setCreateBy(userId);
        super.setUpdateBy(userId);
        this.points = points;
        this.limitName = limitName;
        this.limitType = limitType;
        this.limitSpeed = limitSpeed;
    }
    
    /**
     * 〈一句话功能简述〉区域限制点集合
     * 〈功能详细描述〉
     * 
     * @return 区域限制点集合
     * @see [类、类#方法、类#成员]
     */
    public String getPoints() {
        return points;
    }
    
    /**
     * 〈一句话功能简述〉区域限制点集合
     * 〈功能详细描述〉
     * 
     * @param points 区域限制点集合
     * @see [类、类#方法、类#成员]
     */
    public void setPoints(String points) {
        this.points = points == null ? null : points.trim();
    }
    
    /**
     * 〈一句话功能简述〉限制名称
     * 〈功能详细描述〉
     * 
     * @return 限制名称
     * @see [类、类#方法、类#成员]
     */
    public String getLimitName() {
        return limitName;
    }
    
    /**
     * 〈一句话功能简述〉限制名称
     * 〈功能详细描述〉
     * 
     * @param limitName 限制名称
     * @see [类、类#方法、类#成员]
     */
    public void setLimitName(String limitName) {
        this.limitName = limitName == null ? null : limitName.trim();
    }
    
    /**
     * 〈一句话功能简述〉限制类型
     * 〈功能详细描述〉
     * 
     * @return 限制类型
     * @see [类、类#方法、类#成员]
     */
    public String getLimitType() {
        return limitType;
    }
    
    /**
     * 〈一句话功能简述〉限制类型
     * 〈功能详细描述〉
     * 
     * @param limitType 限制类型
     * @see [类、类#方法、类#成员]
     */
    public void setLimitType(String limitType) {
        this.limitType = limitType == null ? null : limitType.trim();
    }
    
    /**
     * 〈一句话功能简述〉限制速度
     * 〈功能详细描述〉
     * 
     * @return 限制速度
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getLimitSpeed() {
        return limitSpeed;
    }
    
    /**
     * 〈一句话功能简述〉限制速度
     * 〈功能详细描述〉
     * 
     * @param limitSpeed 限制速度
     * @see [类、类#方法、类#成员]
     */
    public void setLimitSpeed(BigDecimal limitSpeed) {
        this.limitSpeed = limitSpeed == null ? null : limitSpeed;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
