/*
 * 文件名：DictDataVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉数据字典类
 * 修改时间：2015年8月1日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
  
/**
 * 〈一句话功能简述〉数据字典类 〈功能详细描述〉用于缓存存放的值对象
 * 
 * @author duanhy
 * @version [版本号, 2015年8月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DictDataVo implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = -3392779800000528965L;
    
    /** 父code */
    private String pcode;
    
    /** 代码 */
    private String code;
    
    /** 名称 */
    private String name;
    
    /** 英文名称 */
    private String enName;
    
    /** 描述 */
    private String remark;
    
    /** 排序号 */
    private Integer orders;
    
    /** 子对象集合 */
    private List<DictDataVo> dictData = new ArrayList<DictDataVo>();
    
    /**
     * 〈一句话功能简述〉 获取子对象集合〈功能详细描述〉
     * 
     * @return 子对象集合
     * @see [类、类#方法、类#成员]
     */
    public List<DictDataVo> getDictData() {
        return dictData;
    }
    
    /**
     * 〈一句话功能简述〉设置子对象集合 〈功能详细描述〉
     * 
     * @param dictData 子对象集合
     * @see [类、类#方法、类#成员]
     */
    public void setDictData(List<DictDataVo> dictData) {
        this.dictData = dictData;
    }
    
    /**
     * 〈一句话功能简述〉 获得父code〈功能详细描述〉
     * 
     * @return 父code
     * @see [类、类#方法、类#成员]
     */
    public String getPcode() {
        return pcode;
    }
    
    /**
     * 〈一句话功能简述〉设置父code 〈功能详细描述〉
     * 
     * @param pcode 父code
     * @see [类、类#方法、类#成员]
     */
    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
    
    /**
     * 〈一句话功能简述〉获得编码 〈功能详细描述〉
     * 
     * @return 编码
     * @see [类、类#方法、类#成员]
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 〈一句话功能简述〉 设置编码〈功能详细描述〉
     * 
     * @param code 编码
     * @see [类、类#方法、类#成员]
     */
    public void setCode(String code) {
        this.code = code;
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
        this.name = name;
    }
    
    /**
     * 〈一句话功能简述〉获得英文名称 〈功能详细描述〉
     * 
     * @return 英文名称
     * @see [类、类#方法、类#成员]
     */
    public String getEnName() {
        return enName;
    }
    
    /**
     * 〈一句话功能简述〉设置英文名称 〈功能详细描述〉
     * 
     * @param enName 英文名称
     * @see [类、类#方法、类#成员]
     */
    public void setEnName(String enName) {
        this.enName = enName;
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
        this.remark = remark;
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
    
}
