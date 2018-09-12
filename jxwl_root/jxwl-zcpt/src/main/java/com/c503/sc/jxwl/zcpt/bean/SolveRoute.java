package com.c503.sc.jxwl.zcpt.bean;

import java.util.Date;

/**
 * 〈一句话功能简述〉路径规划
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-11-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SolveRoute {
    /** id */
    private String id;
    
    /** 路径点 */
    private String points;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 车牌号 */
    private String carrierName;
    
    /** 运单号 */
    private String waybillId;
    
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
    public SolveRoute setId(String id) {
        this.id = id;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉路径点
     * 〈功能详细描述〉
     * 
     * @return 路径点
     * @see [类、类#方法、类#成员]
     */
    public String getPoints() {
        return points;
    }
    
    /**
     * 〈一句话功能简述〉路径点
     * 〈功能详细描述〉
     * 
     * @param points 路径点
     * @return 路径点
     * @see [类、类#方法、类#成员]
     */
    public SolveRoute setPoints(String points) {
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
    public SolveRoute setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @return 车牌号
     * @see [类、类#方法、类#成员]
     */
    public String getCarrierName() {
        return carrierName;
    }
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @return 车牌号
     * @see [类、类#方法、类#成员]
     */
    public SolveRoute setCarrierName(String carrierName) {
        this.carrierName = carrierName;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉运单号
     * 〈功能详细描述〉
     * 
     * @return 运单号
     * @see [类、类#方法、类#成员]
     */
    public String getWaybillId() {
        return waybillId;
    }
    
    /**
     * 〈一句话功能简述〉运单号
     * 〈功能详细描述〉
     * 
     * @param waybillId 运单号
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public SolveRoute setWaybillId(String waybillId) {
        this.waybillId = waybillId;
        return this;
    }
    
}
