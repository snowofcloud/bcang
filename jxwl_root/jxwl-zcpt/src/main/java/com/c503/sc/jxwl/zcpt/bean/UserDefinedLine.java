/**
 * 文件名：UserDefinedLine.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉用户自定义线条
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDefinedLine extends BaseEntity {
    /** 序列号号 */
    private static final long serialVersionUID = -3573005840703434582L;
    
    /** 线条限制点集合 */
    private String points;
    
    /** 线条名称 */
    private String lineName;
    
    /** 起点纬度 */
    private String startLat;
    
    /** 起点经度 */
    private String startLng;
    
    /** 终点纬度 */
    private String endLat;
    
    /** 起点经度 */
    private String endLng;
    
    /**
     * 〈一句话功能简述〉获得startLat
     * 〈功能详细描述〉
     * 
     * @return startLat
     * @see [类、类#方法、类#成员]
     */
    public String getStartLat() {
        return startLat;
    }
    
    /**
     * 〈一句话功能简述〉设置startLat
     * 〈功能详细描述〉
     * 
     * @param startLat startLat
     * @see [类、类#方法、类#成员]
     */
    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }
    
    /**
     * 〈一句话功能简述〉获得startLng
     * 〈功能详细描述〉
     * 
     * @return startLng
     * @see [类、类#方法、类#成员]
     */
    public String getStartLng() {
        return startLng;
    }
    
    /**
     * 〈一句话功能简述〉设置startLng
     * 〈功能详细描述〉
     * 
     * @param startLng startLng
     * @see [类、类#方法、类#成员]
     */
    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }
    
    /**
     * 〈一句话功能简述〉获得endLat
     * 〈功能详细描述〉
     * 
     * @return endLat
     * @see [类、类#方法、类#成员]
     */
    public String getEndLat() {
        return endLat;
    }
    
    /**
     * 〈一句话功能简述〉设置endLat
     * 〈功能详细描述〉
     * 
     * @param endLat endLat
     * @see [类、类#方法、类#成员]
     */
    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }
    
    /**
     * 〈一句话功能简述〉获得endLng
     * 〈功能详细描述〉
     * 
     * @return endLng
     * @see [类、类#方法、类#成员]
     */
    public String getEndLng() {
        return endLng;
    }
    
    /**
     * 〈一句话功能简述〉设置endLng
     * 〈功能详细描述〉
     * 
     * @param endLng endLng
     * @see [类、类#方法、类#成员]
     */
    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }
    
    /**
     * 〈一句话功能简述〉构造方法
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public UserDefinedLine() {
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
     * @param lineName
     * @see [类、类#方法、类#成员]
     */
    public UserDefinedLine(String id, String userId, String points,
        String lineName, String startLat, String startLng, String endLat,
        String endLng) {
        super.setId(id);
        super.setCreateBy(userId);
        super.setUpdateBy(userId);
        this.points = points;
        this.lineName = lineName;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
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
     * 〈一句话功能简述〉获得lineName
     * 〈功能详细描述〉
     * 
     * @return lineName
     * @see [类、类#方法、类#成员]
     */
    public String getLineName() {
        return lineName;
    }
    
    /**
     * 〈一句话功能简述〉设置lineName
     * 〈功能详细描述〉
     * 
     * @param lineName lineName
     * @see [类、类#方法、类#成员]
     */
    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
