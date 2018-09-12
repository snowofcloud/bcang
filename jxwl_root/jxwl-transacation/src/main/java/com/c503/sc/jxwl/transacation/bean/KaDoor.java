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
public class KaDoor {
    /** id */
    private String id;
    
    /** 卡口名称 */
    private String name;
    
    /** 卡口经度 */
    private String lng;
    
    /** 卡口纬度 */
    private String lat;
    
    /** 创建卡口时间 */
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
    public KaDoor setId(String id) {
        this.id = id;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉卡口名称
     * 〈功能详细描述〉
     * 
     * @return 卡口名称
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 〈一句话功能简述〉卡口名称
     * 〈功能详细描述〉
     * 
     * @param name 卡口名称
     * @return 卡口名称
     * @see [类、类#方法、类#成员]
     */
    public KaDoor setName(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉卡口经度
     * 〈功能详细描述〉
     * 
     * @return 卡口经度
     * @see [类、类#方法、类#成员]
     */
    public String getLng() {
        return lng;
    }
    
    /**
     * 〈一句话功能简述〉卡口经度
     * 〈功能详细描述〉
     * 
     * @param lng 卡口经度
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public KaDoor setLng(String lng) {
        this.lng = lng;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉卡口纬度
     * 〈功能详细描述〉
     * 
     * @return 卡口纬度
     * @see [类、类#方法、类#成员]
     */
    public String getLat() {
        return lat;
    }
    
    /**
     * 〈一句话功能简述〉卡口纬度
     * 〈功能详细描述〉
     * 
     * @param lat 卡口纬度
     * @return 卡口纬度
     * @see [类、类#方法、类#成员]
     */
    public KaDoor setLat(String lat) {
        this.lat = lat;
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
    public KaDoor setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
}
