package com.c503.sc.jxwl.pipelinemangnt.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉PipeLineLocationEntity
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2017-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PipeLineLocationEntity extends BaseEntity {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    /** pipeNo */
    private String pipeNo;
    
    /** 经度 */
    private Float longitude;
    
    /** 纬度 */
    private Float latitude;
    
    /** 坐标点集合 */
    private String points;
    
    /** 类型 */
    private String pipeType;
    
    
    /**
     * 〈一句话功能简述〉pipeType
     * 〈功能详细描述〉
     * 
     * @return pipeType
     * @see [类、类#方法、类#成员]
     */
    public String getPipeType() {
		return pipeType;
	}
    /**
     * 〈一句话功能简述〉pipeType
     * 〈功能详细描述〉
     * 
     * @param pipeType pipeType
     * @see [类、类#方法、类#成员]
     */
	public void setPipeType(String pipeType) {
		this.pipeType = pipeType;
	}

	/**
     * 〈一句话功能简述〉pipeNo
     * 〈功能详细描述〉
     * 
     * @return pipeNo
     * @see [类、类#方法、类#成员]
     */
    public String getPipeNo() {
        return pipeNo;
    }
    
    /**
     * 〈一句话功能简述〉pipeNo
     * 〈功能详细描述〉
     * 
     * @param pipeNo pipeNo
     * @see [类、类#方法、类#成员]
     */
    public void setPipeNo(String pipeNo) {
        this.pipeNo = pipeNo;
    }
    
    /**
     * 〈一句话功能简述〉经度
     * 〈功能详细描述〉
     * 
     * @return 经度
     * @see [类、类#方法、类#成员]
     */
    public Float getLongitude() {
        return longitude;
    }
    
    /**
     * 〈一句话功能简述〉经度
     * 〈功能详细描述〉
     * 
     * @param longitude 经度
     * @see [类、类#方法、类#成员]
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 〈一句话功能简述〉纬度
     * 〈功能详细描述〉
     * 
     * @return 纬度
     * @see [类、类#方法、类#成员]
     */
    public Float getLatitude() {
        return latitude;
    }
    
    /**
     * 〈一句话功能简述〉纬度
     * 〈功能详细描述〉
     * 
     * @param latitude 纬度
     * @see [类、类#方法、类#成员]
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    /**
     * 〈一句话功能简述〉获取坐标点集合
     * 〈功能详细描述〉
     * 
     * @return 坐标点集合
     * @see [类、类#方法、类#成员]
     */
	public String getPoints() {
		return points;
	}
	
	/**
     * 〈一句话功能简述〉设置坐标点
     * 〈功能详细描述〉
     * 
     * @param points 坐标点集合
     * @see [类、类#方法、类#成员]
     */
	public void setPoints(String points) {
		this.points = points;
	}
    
}
