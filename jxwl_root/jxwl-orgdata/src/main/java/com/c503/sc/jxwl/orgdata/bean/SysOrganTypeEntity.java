/**
 * 文件名：SysOrganType.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-9
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉通过业务系统更新业务机构信息
 * 〈功能详细描述〉
 * 
 * @author yangjh
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysOrganTypeEntity extends BaseEntity {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -4030840946085246442L;
    
    /**
     * 所属系统的id(物流默认全国，化工默认港区)
     */
    private String pid;
    
    /**
     * 所属系统id（默认6嘉兴物流系统）
     */
    private String sysId;
    
    /**
     * 法人代码，机构编码（同步）
     */
    private String code;
    
    /**
     * 机构给名称(企业名称)
     */
    private String name;
    
    /**
     * 企业简称ALIAS
     */
    private String alias;
    
    /**
     * 排序
     */
    private String orders;
    
    /**
     * STATUS(状态(0 启用，1停用))
     */
    private String status;
    
    /**
     * 区域id
     */
    private String raeaId;
    
    /**
     * 机构类型(政府，化工企业和物流企业的类型)对应enterpriseType
     */
    private String organTypeId;
    
    /**
     * 
     * 〈一句话功能简述〉企业简称
     * 〈功能详细描述〉
     * 
     * @return alias 企业简称
     * @see [类、类#方法、类#成员]
     */
    public String getAlias() {
        return alias;
    }
    
    /**
     * 
     * 〈一句话功能简述〉A设置企业简称
     * 〈功能详细描述〉
     * 
     * @param alias 企业简称
     * @see [类、类#方法、类#成员]
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 排序
     * 〈功能详细描述〉
     * 
     * @return 获取排序
     * @see [类、类#方法、类#成员]
     */
    public String getOrders() {
        return orders;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置排序
     * 〈功能详细描述〉
     * 
     * @param orders 排序
     * @see [类、类#方法、类#成员]
     */
    public void setOrders(String orders) {
        this.orders = orders;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取启用状态
     * 〈功能详细描述〉
     * 
     * @return status 启用状态
     * @see [类、类#方法、类#成员]
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置启用状态
     * 〈功能详细描述〉
     * 
     * @param status 启用状态
     * @see [类、类#方法、类#成员]
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取区域id
     * 〈功能详细描述〉
     * 
     * @return 区域id
     * @see [类、类#方法、类#成员]
     */
    public String getRaeaId() {
        return raeaId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置区域id
     * 〈功能详细描述〉
     * 
     * @param raeaId raeaId
     * @see [类、类#方法、类#成员]
     */
    public void setRaeaId(String raeaId) {
        this.raeaId = raeaId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取所属系统id
     * 〈功能详细描述〉
     * 
     * @return pid 所属系统id
     * @see [类、类#方法、类#成员]
     */
    public String getPid() {
        return pid;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置所属系统id
     * 〈功能详细描述〉
     * 
     * @param pid 系统id
     * @see [类、类#方法、类#成员]
     */
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取系统id
     * 〈功能详细描述〉
     * 
     * @return sysId 子系统id
     * @see [类、类#方法、类#成员]
     */
    public String getSysId() {
        return sysId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置子系统id
     * 〈功能详细描述〉
     * 
     * @param sysId 子系统id
     * @see [类、类#方法、类#成员]
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉后去机构代码
     * 〈功能详细描述〉
     * 
     * @return 机构代码
     * @see [类、类#方法、类#成员]
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置机构代码
     * 〈功能详细描述〉
     * 
     * @param code 机构代码
     * @see [类、类#方法、类#成员]
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业名称
     * 〈功能详细描述〉
     * 
     * @return name 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名称
     * 〈功能详细描述〉
     * 
     * @param name 企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取机构类型
     * 〈功能详细描述〉
     * 
     * @return 机构类型
     * @see [类、类#方法、类#成员]
     */
    public String getOrganTypeId() {
        return organTypeId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置机构类型
     * 〈功能详细描述〉
     * 
     * @param organTypeId 机构类型
     * @see [类、类#方法、类#成员]
     */
    public void setOrganTypeId(String organTypeId) {
        this.organTypeId = organTypeId;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
