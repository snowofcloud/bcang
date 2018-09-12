/*
 * 文件名：GeometricAlgorithm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-8-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.utils;

import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;

/**
 * 〈一句话功能简述〉几何算法工具类
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-8-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GeometricAlgorithmUtils {
    
    /** pi的静态变量 */
    private static double pi = Math.PI;
    
    /** 地球的平均半径，单位：米 */
    private static double r = 6378137;
    
    /**
     * 〈一句话功能简述〉计算点p0与线段p1p2关系
     * 〈功能详细描述〉
     * 
     * @param p1 线段起点坐标
     * @param p2 线段终点坐标
     * @param p0 点
     * @return [投影比例,最短距离,最近交点x坐标,最近交点y坐标]
     * @see [类、类#方法、类#成员]
     */
    public static double[] vector(Point p0, Point[] linesegment) {
        Point p1 = linesegment[0];
        Point p2 = linesegment[1];
        
        Point p1p2 = vectorSub(p1, p2);
        Point p1p3 = vectorSub(p1, p0);
        // p1p2与p1p3的点积
        double dotMultiply =
            p1p2.getLat() * p1p3.getLat() + p1p2.getLng() * p1p3.getLng();
        // p1p3在p1p2的投影比上p1p2
        dotMultiply = dotMultiply / getDistance(p1, p2);
        double[] data = new double[4];
        data[0] = dotMultiply;
        if (dotMultiply == 0) {
            data[1] = 0;
            data[2] = p0.getLat();
            data[3] = p0.getLng();
        }
        else if (dotMultiply == 1) {
            data[1] = 0;
            data[2] = p2.getLat();
            data[3] = p2.getLng();
        }
        else if (dotMultiply < 0) {
            // 点p0在p1外
            data[1] = getDistance(p1, p0);
            data[2] = p0.getLat();
            data[3] = p0.getLng();
        }
        else if (dotMultiply > 1) {
            // 点p0在p2外
            data[1] = getDistance(p2, p0);
            data[2] = p2.getLat();
            data[3] = p2.getLng();
        }
        else {
            // 点p0的投影在p1p2之间
            Point d = p1p2;
            d.setLat(d.getLat() * dotMultiply);
            d.setLng(d.getLng() * dotMultiply);
            
            d.setLat(d.getLat() + p0.getLat());
            d.setLng(d.getLng() + p0.getLng());
            
            data[1] = getDistance(d, p0);
            data[2] = d.getLat();
            data[3] = d.getLng();
            
        }
        return data;
    }
    
    public static Point vectorSub(Point p1, Point p2) {
        Point p = new Point();
        p.setLat(p2.getLat() - p1.getLat());
        p.setLat(p2.getLng() - p1.getLng());
        return p;
    }
    
    /**
     * 〈一句话功能简述〉点与线段关系
     * 〈功能详细描述〉
     * 
     * @param point 点
     * @param linesegment 线段
     * @return 距离
     * @see [类、类#方法、类#成员]
     */
    public static double relation(Point point, Point[] linesegment) {
        Point[] tmpLinesegment = new Point[] {linesegment[0], point};
        return dotMultiply(tmpLinesegment[1], linesegment[1], linesegment[0])
            / (getDistance(linesegment[0], linesegment[1]) * getDistance(linesegment[0],
                linesegment[1]));
    }
    
    /**
     * 〈一句话功能简述〉得到矢量(p1-p0)和(p2-p0)的点积
     * 〈功能详细描述〉
     * 
     * @param p1 点
     * @param p2 点
     * @param p0 点
     * @return 得到矢量点积
     * @see [类、类#方法、类#成员]
     */
    public static double dotMultiply(Point p1, Point p2, Point p0) {
        return ((p1.getLat() - p0.getLat()) * (p2.getLat() - p0.getLat()) + (p1.getLng() - p0.getLng())
            * (p2.getLng() - p0.getLng()));
    }
    
    /**
     * 〈一句话功能简述〉求点C到线段AB所在直线的垂足P
     * 〈功能详细描述〉
     * 
     * @return 垂足P
     * @see [类、类#方法、类#成员]
     */
    public static Point perpendicular(Point point, Point[] linesegment) {
        double r = relation(point, linesegment);
        Point footPoint = new Point();
        double lat =
            linesegment[0].getLat() + r
                * (linesegment[1].getLat() - linesegment[0].getLat());
        footPoint.setLat(lat);
        double lng =
            linesegment[0].getLng() + r
                * (linesegment[1].getLng() - linesegment[0].getLng());
        footPoint.setLng(lng);
        return footPoint;
    }
    
    /**
     * 〈一句话功能简述〉点与线段最短距离
     * 〈功能详细描述〉注：不一定是垂足
     * 
     * @param point 点
     * @param linesegment 线段
     * @return 点与线段最短距离
     * @see [类、类#方法、类#成员]
     */
    public static double point2LinesegmentDistance(Point point,
        Point[] linesegment) {
        double r = relation(point, linesegment);
        Point np = null;
        if (r < 0) {
            np = linesegment[0];
        }
        else if (r > 1) {
            np = linesegment[1];
        }
        else {
            np = perpendicular(point, linesegment);
        }
        return getDistance(point, np);
    }
    
    public static double getDistance(Point p1, Point p2) {
        double startLat = p1.getLat();
        double startLng = p1.getLng();
        
        double endLat = p2.getLat();
        double endLng = p2.getLng();
        
        double pk = 180 / pi;
        double t1 =
            Math.cos(startLat / pk) * Math.cos(startLng / pk)
                * Math.cos(endLat / pk) * Math.cos(endLng / pk);
        double t2 =
            Math.cos(startLat / pk) * Math.sin(startLng / pk)
                * Math.cos(endLat / pk) * Math.sin(endLng / pk);
        double t3 = Math.sin(startLat / pk) * Math.sin(endLat / pk);
        double result = Math.acos((t1 + t2 + t3)) * r;
        return result;
    }
    
    public static double getDistance(double startLat, double startLng,
        double endLat, double endLng) {
        double pk = 180 / pi;
        double t1 =
            Math.cos(startLat / pk) * Math.cos(startLng / pk)
                * Math.cos(endLat / pk) * Math.cos(endLng / pk);
        double t2 =
            Math.cos(startLat / pk) * Math.sin(startLng / pk)
                * Math.cos(endLat / pk) * Math.sin(endLng / pk);
        double t3 = Math.sin(startLat / pk) * Math.sin(endLat / pk);
        double result = Math.acos((t1 + t2 + t3)) * r;
        return result;
    }
   
}
