package com.c503.sc.jxwl.transacation.bean;

import java.util.Date;

/**
 * 〈一句话功能简述〉园区区域
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-11-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ParkArea {
    /** id */
    private String id;
    
    /** 区域点 */
    private String points;
    
    /** 创建时间 */
    private Date createTime;
    
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
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public ParkArea setId(String id) {
        this.id = id;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉园区点
     * 〈功能详细描述〉
     * 
     * @return 路径点
     * @see [类、类#方法、类#成员]
     */
    public String getPoints() {
        return points;
    }
    
    /**
     * 〈一句话功能简述〉园区点
     * 〈功能详细描述〉
     * 
     * @param points 路径点
     * @return 路径点
     * @see [类、类#方法、类#成员]
     */
    public ParkArea setPoints(String points) {
        this.points = points;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉创建时间
     * 〈功能详细描述〉
     * 
     * @return 创建时间
     * @see [类、类#方法、类#成员]
     */
    public Date getCreateTime() {
        return createTime;
    }
    
    /**
     * 〈一句话功能简述〉创建时间
     * 〈功能详细描述〉
     * 
     * @param createTime 创建时间
     * @return 创建时间
     * @see [类、类#方法、类#成员]
     */
    public ParkArea setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
}
