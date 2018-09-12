/*
 * 文件名：DictDataVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉数据字典类
 * 修改时间：2015年8月1日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.vo;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉数据字典类 〈功能详细描述〉用于缓存存放的值对象
 * 
 * @author duanhy
 * @version [版本号, 2015年8月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DictVo implements Serializable {
    
    /** 序列化版本号 */
    private static final long serialVersionUID = 306030781106360251L;
    
    /** 代码 */
    private String code;
    
    /** 名称 */
    private String name;
    
    /** 英文名称 */
    private String enName;
    
    /**
     * 〈一句话功能简述〉默认构造器
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public DictVo() {
    }
    
    /**
     * 〈一句话功能简述〉构造器
     * 〈功能详细描述〉
     * 
     * @param code 代码
     * @param name 名称
     * @param enName 英文名称
     * @see [类、类#方法、类#成员]
     */
    public DictVo(String code, String name, String enName) {
        this.code = code;
        this.name = name;
        this.enName = enName;
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
}
