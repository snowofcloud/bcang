/**
 * 文件名：PlatformForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import com.c503.sc.base.formbean.PageForm;


/**
 * 〈一句话功能简述〉平台信息formBean
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-8]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public class PlatformForm extends PageForm{

    /** 序列号*/
    private static final long serialVersionUID = 1L;
    
    /** 平台名称*/
    private String platformName;

    /** 主要数据*/
    private String mainData;

    /**
     * 
     * 〈一句话功能简述〉 获取平台名称
     * 〈功能详细描述〉
     * 
     * @return 平台名称
     * @see [类、类#方法、类#成员]
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置平台名称
     * 〈功能详细描述〉
     * 
     * @param platformName 平台名称
     * @see [类、类#方法、类#成员]
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取主要数据
     * 〈功能详细描述〉
     * 
     * @return 主要数据
     * @see [类、类#方法、类#成员]
     */
    public String getMainData() {
        return mainData;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置主要数据
     * 〈功能详细描述〉
     * 
     * @param mainData 主要数据
     * @see [类、类#方法、类#成员]
     */
    public void setMainData(String mainData) {
        this.mainData = mainData == null ? null : mainData.trim();
    }
}
