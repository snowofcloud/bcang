package com.c503.sc.jxwl.transacation.bean;

import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-11-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Feature {
    /**
     * 几何区域
     * geometry : {
     * rings:几何区域
     * spatialReference:{"wkid": 4326}
     * }
     */
    private Map<String, Object> geometry;
    
    /** 属性 */
    private Map<String, Object> attributes;
    
    /**
     * 〈一句话功能简述〉几何区域
     * 〈功能详细描述〉
     * 
     * @return 几何区域
     * @see [类、类#方 法、类#成员]
     */
    public Map<String, Object> getGeometry() {
        return geometry;
    }
    
    /**
     * 〈一句话功能简述〉几何区域
     * 〈功能详细描述〉
     * 
     * @param geometry 几何区域
     * @return 几何区域
     * @see [类、类#方法、类#成员]
     */
    public Feature setGeometry(Map<String, Object> geometry) {
        this.geometry = geometry;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉属性
     * 〈功能详细描述〉
     * 
     * @return 属性
     * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    /**
     * 〈一句话功能简述〉属性
     * 〈功能详细描述〉
     * 
     * @param attributes 属性
     * @return 属性
     * @see [类、类#方法、类#成员]
     */
    public Feature setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }
}
