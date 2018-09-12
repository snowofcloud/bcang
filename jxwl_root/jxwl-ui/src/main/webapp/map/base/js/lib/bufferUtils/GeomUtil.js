/**
 * <p>Description: 常用几何计算构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-25 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */
	
HthxMap.GeomUtil = function(){}

HthxMap.GeomUtil.prototype = {
	/**点到线段的垂直距离*/
	getVerticalDistanceOfPointAndLine: function(pt,pt1,pt2){
		var dis;
		var vec1 = pt.subtract(pt1);
		var vec2 = pt.subtract(pt2);
		var area = Math.abs(vec1.x*vec2.y - vec1.y*vec2.x);    //根据叉积计算面积
		var disLine = Point.distance(pt1,pt2);
		if(disLine == 0){
			dis = Point.distance(pt,pt1);
		}
		else{
			dis = area/disLine;
		}
		return dis;
	},
		
	/**点到线段的最短距离*/
	getDistanceOfPointAndLine:	function(pt, pt1, pt2){
		var dis;
		//通过内积判断夹角为锐角或钝角
		if((pt.x - pt1.x)*(pt2.x - pt1.x) + (pt.y - pt1.y)*(pt2.y - pt1.y) < 0){
			dis = Point.distance(pt,pt1);
		}
		else if((pt.x - pt2.x)*(pt1.x - pt2.x) + (pt.y - pt2.y)*(pt1.y - pt2.y) < 0){
			dis = Point.distance(pt, pt2);
		}
		else{
			var vec1 = pt.subtract(pt1);
			var vec2 = pt.subtract(pt2);
			var area = Math.abs(vec1.x*vec2.y - vec1.y*vec2.x);    //根据叉积计算面积
			var disLine = Point.distance(pt1, pt2);
			if(disLine == 0){
				dis = Point.distance(pt, pt1);
			}
			else{
				dis = area/disLine;
			}
		}
		return dis;
	},
	
	/**根据圆心和角度获取一点*/
	getPointByAngle: function(ptCenter,radius,angle){
		var x = ptCenter.x + radius * Math.cos(angle);
		var y = ptCenter.y + radius * Math.sin(angle);
		
		return new HthxMap.Point(x,y);
	},		
	
	/** 利用叉积判断点在线段前进方向（pt1>>>pt2）的左侧或右侧；
	 * 	大于0表示在左侧，小于0表示在右侧；		
	 * pt0   : 进行判断的点
	 * pt1   : 线段的起点
	 * pt2   : 线段的终点
	 * 返回值  : (pt2 - pt0)和 (pt2 - pt0)的叉积
	 */
	isLeft: function(pt0,pt1,pt2){   
		return ((pt1.x - pt0.x) * (pt2.y - pt0.y) - (pt2.x - pt0.x) * (pt1.y - pt0.y));   
	},
	
	/**获取对角线上一点
	 * */
	/*GeomUtil.prototype.getDiagonalPoint(ptPre,ptCur,ptNext,len){
		//对角线方向上的矢量
		var vec1 = ptPre.subtract(ptCur);
		var vec2 = ptNext.subtract(ptCur);
		vec1.normalize(1);
		vec2.normalize(1);
		var ptDiag = vec1.add(vec2);
		ptDiag.normalize(1);		
	
		var cosQ = vec1.x*ptDiag.x + vec1.y*ptDiag.y;
		var lenDiag = len/Math.sqrt(1 - cosQ*cosQ); 
		ptDiag.normalize(lenDiag);
		
		var pt = ptCur.add(ptDiag);
		return pt;
	}*/
	
	/**获取弧段的折线点数组
	 * */
	getArcPtsByAngle: function(center, radius, bAngle, eAngle){
		var pts = [];
		for(var angle = bAngle; angle < eAngle; angle += 0.1){    //离散间隔取0.1弧度
			var x = center.x + radius * Math.cos(angle);
			var y = center.y + radius * Math.sin(angle);
			pts.push(new HthxMap.Point(x,y));
		}	
		//保证终止角度对应点加入圆弧坐标点
		x = center.x + radius * Math.cos(eAngle);
		y = center.y + radius * Math.sin(eAngle);
		pts.push(new HthxMap.Point(x,y));
		
		return pts;
	},
	
	/**剔除相同点
	 */
	RemoveNearEqualPointFromPts: function(pts){
		var myPts = [];
		var pt;
		if(pts.length > 0){
			pt = pts[0];
			myPts.push(pt);
		}
		/*for(var i=0; i < pts.length-1;i++){
			for(var j=i+1; j<pts.length; j++){
				if(!pts[i].equals(pts[j])){
					myPts.push(pts[i]);
//					pt = pts[i];
				}else{
					
				}
			}
		}*/
		for(var i = 1;i < pts.length;i++){
			if(!pts[i].equals(pt)){
				myPts.push(pts[i]);
				pt = pts[i];
			}
		}			
		return myPts;
	},
	
	/**获取两点之间的角度，单位为弧度
	 * */
	getRotation: function(pt, ptCenter){
		var dx = pt.x - ptCenter.x;
		var dy = pt.y - ptCenter.y;			
		var dis = Math.sqrt(dx*dx + dy*dy);	
//		if(dis == 0){    //为同一个 点
//			return 0;
//		}
		var q;
		if(dy > 0){    //ptCenter在下面
			q = Math.acos(dx/dis);
		}
		else{    //ptCenter在上面
			q = Math.PI*2 - Math.acos(dx/dis);
		} 
		return q;
	},				
	
	/**求两条直线的交点
	 */
	getLineIntersection: function(a1,a2,b1,b2){
		var k1 = (a2.y-a1.y) / (a2.x-a1.x);
		var k2 = (b2.y-b1.y) / (b2.x-b1.x);
		
		//平行
		if(k1 == k2){
			return null;
		} 
		
		var x,y;
		var m1,m2;
		
		if(!isFinite(k1)){
			x = a1.x;
			m2 = b1.y - k2 * b1.x;
			y = k2 * x + m2;
		}else if(!isFinite(k2)) {
			m1 = a1.y - k1 * a1.x;
			x = b1.x;
			y = k1 * x + m1;
		}else{
			m1 = a1.y - k1 * a1.x;
			m2 = b1.y - k2 * b1.x;				
			x = (m1-m2) / (k2-k1);
			y = k1 * x + m1;
		}
		return new HthxMap.Point(x,y);
	},
	
	/**判断两条线段是否相交  
	 */ 
	isLineSegmentsIntersect: function(p0,p1,p2,p3){   
		return((Math.max(p0.x,p1.x)>=Math.min(p2.x,p3.x))&&   
			(Math.max(p2.x,p3.x)>=Math.min(p0.x,p1.x))&&   
			(Math.max(p0.y,p1.y)>=Math.min(p2.y,p3.y))&&   
			(Math.max(p2.y,p3.y)>=Math.min(p0.y,p1.y))&&  
			(this.multiply(p2,p1,p0)*this.multiply(p1,p3,p0)>=0)&&   
			(this.multiply(p0,p3,p2)*this.multiply(p3,p1,p2)>=0));   
	}, 	
	
	/**叉积
	 */
	multiply: function(p1,p2,p0){   
		return((p1.x-p0.x)*(p2.y-p0.y)-(p2.x-p0.x)*(p1.y-p0.y)); 
	}		
}
