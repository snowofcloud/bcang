/**
 * 文件名：PtStatisticEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-7-29 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import com.c503.sc.base.entity.BaseEntity;


public class PtStatisticEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    
    /** 平台名称  */
    private String name;
    
    /** 是否在线  */
    private String isOnline;
    
    /** 平台入网车辆数  */
    private int joinCarNum;
    
    /** 平台在线车辆数  */
    private int onlineCarNum;
    
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIsOnline()
    {
        return isOnline;
    }

    public void setIsOnline(String isOnline)
    {
        this.isOnline = isOnline;
    }

    public int getJoinCarNum()
    {
        return joinCarNum;
    }

    public void setJoinCarNum(int joinCarNum)
    {
        this.joinCarNum = joinCarNum;
    }

    public int getOnlineCarNum()
    {
        return onlineCarNum;
    }

    public void setOnlineCarNum(int onlineCarNum)
    {
        this.onlineCarNum = onlineCarNum;
    }

    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
