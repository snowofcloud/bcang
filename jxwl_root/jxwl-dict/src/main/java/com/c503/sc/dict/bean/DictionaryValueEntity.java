/*
 * 文件名：DictionaryValueEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年7月22日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉数据字典分类下的明细类 〈功能详细描述〉主要存储具体的分类，目前value代表分类的编码：暂且位数为9
 * 
 * @author duanhy
 * @version [版本号, 2015年7月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DictionaryValueEntity extends BaseEntity {
    /** 序列化版本号 */
    private static final long serialVersionUID = 2889495916226262822L;
    
    /** 类型code */
    private String typeCode;
    
    /** 名称 */
    private String name;
    
    /** 英文名称 */
    private String enName;
    
    /** 值 */
    private String value;
    
    /** 排序号，默认为-1 */
    private Integer orders = -1;
    
    /** 备注 */
    private String remark;
    
    /**
     * 〈一句话功能简述〉获得类型code 〈功能详细描述〉
     * 
     * @return 类型code
     * @see [类、类#方法、类#成员]
     */
    public String getTypeCode() {
        return typeCode;
    }
    
    /**
     * 〈一句话功能简述〉设置类型code 〈功能详细描述〉
     * 
     * @param typeCode 类型code
     * @see [类、类#方法、类#成员]
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }
    
    /**
     * 〈一句话功能简述〉获得名称 〈功能详细描述〉
     * 
     * @return 名称
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 〈一句话功能简述〉设置名称 〈功能详细描述〉
     * 
     * @param name 名称
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * 〈一句话功能简述〉获得英文名 〈功能详细描述〉
     * 
     * @return String 英文名
     * @see [类、类#方法、类#成员]
     */
    public String getEnName() {
        return enName;
    }
    
    /**
     * 〈一句话功能简述〉 设置英文名〈功能详细描述〉
     * 
     * @param enName 英文名
     * @see [类、类#方法、类#成员]
     */
    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }
    
    /**
     * 〈一句话功能简述〉获得值 〈功能详细描述〉
     * 
     * @return 值
     * @see [类、类#方法、类#成员]
     */
    public String getValue() {
        return value;
    }
    
    /**
     * 〈一句话功能简述〉设置值 〈功能详细描述〉
     * 
     * @param value 值
     * @see [类、类#方法、类#成员]
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
    
    /**
     * 〈一句话功能简述〉获得排序号 〈功能详细描述〉
     * 
     * @return 排序号
     * @see [类、类#方法、类#成员]
     */
    public Integer getOrders() {
        return orders;
    }
    
    /**
     * 〈一句话功能简述〉设置排序号 〈功能详细描述〉
     * 
     * @param orders 排序号
     * @see [类、类#方法、类#成员]
     */
    public void setOrders(Integer orders) {
        this.orders = orders;
    }
    
    /**
     * 〈一句话功能简述〉获得备注 〈功能详细描述〉
     * 
     * @return 备注
     * @see [类、类#方法、类#成员]
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * 〈一句话功能简述〉设置备注 〈功能详细描述〉
     * 
     * @param remark 备注
     * @see [类、类#方法、类#成员]
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
        addKVFieldStyles("typeCode", typeCode).addKVFieldStyles("name", name)
            .addKVFieldStyles("enName", enName)
            .addKVFieldStyles("value", value)
            .addKVFieldStyles("orders", orders)
            .addKVFieldStyles("remark", remark);
        
        return super.toString();
    }
}
