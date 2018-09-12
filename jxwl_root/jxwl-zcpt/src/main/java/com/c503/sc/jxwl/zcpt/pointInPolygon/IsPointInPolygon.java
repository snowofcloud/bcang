/**
 * 文件名：IsPointInPolygon.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016年8月15日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.pointInPolygon;

import java.util.List;

import com.c503.sc.jxwl.zcpt.constant.Constants;

/**
 * 〈一句话功能简述〉是否在指定区域范围内
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IsPointInPolygon {
    /**
     * 〈一句话功能简述〉isInPolygon
     * 〈功能详细描述〉
     * 
     * @param point Point
     * @param pts List<Point>
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isInPolygon(Point point, List<Point> pts) {
        int len = pts.size();
        // 点在多边形定点或边上
        boolean boundOrVertex = true;
        // 横跨点个数
        int intersectCount = 0;
        // 浮点类型计算时候与0比较时候的容差
        double precision = Constants.PRECISION;
        // neighbour bound vertices
        Point p1, p2;
        // 当前点
        Point p = point;
        
        // 左顶点(left vertex)
        p1 = pts.get(0);
        
        for (int i = 1; i < len; ++i) { // 检测所有射线(ray)
            // 测试点是否是左顶点
            if (p.getLng() == p1.getLng() && p.getLat() == p1.getLat()) {
                return boundOrVertex;
            }
            
            // 右顶点
            p2 = pts.get(i % len);
            // ray is outside of our interests
            if (p.getLat() < Math.min(p1.getLat(), p2.getLat())
                || p.getLat() > Math.max(p1.getLat(), p2.getLat())) {
                p1 = p2;
                continue;
            }
            
            if (p.getLat() > Math.min(p1.getLat(), p2.getLat())
                && p.getLat() < Math.max(p1.getLat(), p2.getLat())) {
                if (p.getLng() <= Math.max(p1.getLng(), p2.getLng())) {
                    if (p1.getLat() == p2.getLat()
                        && p.getLng() >= Math.min(p1.getLng(), p2.getLng())) {
                        return boundOrVertex;
                    }
                    
                    if (p1.getLng() == p2.getLng()) {
                        if (p1.getLng() == p.getLng()) {
                            return boundOrVertex;
                        }
                        else {
                            ++intersectCount;
                        }
                    }
                    else {
                        double xinters =
                            (p.getLat() - p1.getLat())
                                * (p2.getLng() - p1.getLng())
                                / (p2.getLat() - p1.getLat()) + p1.getLng();
                        if (Math.abs(p.getLng() - xinters) < precision) {
                            return boundOrVertex;
                        }
                        
                        if (p.getLng() < xinters) {
                            ++intersectCount;
                        }
                    }
                }
            }
            else {
                if (p.getLat() == p2.getLat() && p.getLng() <= p2.getLng()) {
                    Point p3 = pts.get((i + 1) % len);
                    if (p.getLat() >= Math.min(p1.getLat(), p3.getLat())
                        && p.getLat() <= Math.max(p1.getLat(), p3.getLat())) {
                        ++intersectCount;
                    }
                    else {
                        intersectCount += 2;
                    }
                }
            }
            
            p1 = p2;
        }
        return !(0 == intersectCount % 2);
    }
    
}
