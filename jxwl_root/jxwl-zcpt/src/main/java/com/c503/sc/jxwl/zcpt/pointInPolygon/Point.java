/**
 * 文件名：Point.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016年8月15日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.pointInPolygon;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Point {
    /** 经度 */
    private double lng;
    
    /** 纬度 */
    private double lat;
    
    /**
     * 〈一句话功能简述〉无参构造
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public Point() {
        super();
    }
    
    /**
     * 〈一句话功能简述〉有参构造
     * 〈功能详细描述〉
     * 
     * @param lng 经度
     * @param lat 纬度
     * @see [类、类#方法、类#成员]
     */
    public Point(double lng, double lat) {
        super();
        this.lng = lng;
        this.lat = lat;
    }
    
    /**
     * 〈一句话功能简述〉经度
     * 〈功能详细描述〉
     * 
     * @return 经度
     * @see [类、类#方法、类#成员]
     */
    public double getLng() {
        return lng;
    }
    
    /**
     * 〈一句话功能简述〉经度
     * 〈功能详细描述〉
     * 
     * @param lng 经度
     * @see [类、类#方法、类#成员]
     */
    public void setLng(double lng) {
        this.lng = lng;
    }
    
    /**
     * 〈一句话功能简述〉纬度
     * 〈功能详细描述〉
     * 
     * @return 纬度
     * @see [类、类#方法、类#成员]
     */
    public double getLat() {
        return lat;
    }
    
    /**
     * 〈一句话功能简述〉纬度
     * 〈功能详细描述〉
     * 
     * @param lat 纬度
     * @see [类、类#方法、类#成员]
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
}
