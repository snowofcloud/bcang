package com.c503.sc.jxwl.vehiclemonitor.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉接入平台展示列表实体类
 * 〈功能详细描述〉
 * @author    yuanyl
 * @version   [版本号, 2016-7-26]
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
public class AccessPlatformEntity extends BaseEntity{

    /** 序列号 */
	private static final long serialVersionUID = 2244787938127436665L;
	
	/** 平台名称 */
	private String platformName;
	
	/** 平台数据 */
	private String platformData;

    /**
	 * 
	 *〈一句话功能简述〉获得平台名称
	 * 〈功能详细描述〉
	 * @return platformName
	 * @see  [类、类#方法、类#成员]
	 */
	public String getPlatformName() {
        return platformName;
    }


	/**
	 * 
	 *〈一句话功能简述〉设置平台名称
	 * 〈功能详细描述〉
	 * @param platformName 平台名称
	 * @see  [类、类#方法、类#成员]
	 */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * 
     *〈一句话功能简述〉获取平台数据
     * 〈功能详细描述〉
     * @return platformData
     * @see  [类、类#方法、类#成员]
     */
    public String getPlatformData() {
        return platformData;
    }


    /**
     * 
     *〈一句话功能简述〉设置平台数据
     * 〈功能详细描述〉
     * @param platformData 平台数据
     * @see  [类、类#方法、类#成员]
     */
    public void setPlatformData(String platformData) {
        this.platformData = platformData;
    }


    @Override
	protected Object getBaseEntity() {
		return this;
	}

}
