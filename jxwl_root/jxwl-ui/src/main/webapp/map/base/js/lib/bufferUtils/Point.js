/**
 * <p>Description: 点的构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-06 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */

HthxMap.Point = function(x, y){
	this.x = x;
	this.y = y;
}

HthxMap.Point.prototype = {
	//两点间的距离
	distance: function(pt1, pt2){
		var x = pt1.x - pt2.x;
		var y = pt1.y - pt2.y;
		return Math.sqrt(x*x + y*y);
	},

	//确定两个指定点之间的点
	interpolate: function(pt1,pt2,num){
		var point;
		if(pt1.x > pt2.x){
			point = pt1;
			pt1 = pt2;
			pt2 = point;
		}
		var x = (pt2.x - pt1.x) * num + pt1.x;
		var y = (pt2.y - pt1.y) * num + pt1.y;
		return new HthxMap.Point(x, y);
	},

	//从一点的坐标中减去另外一个点的坐标，以创建一个新点
	subtract: function(pt){
		var x = this.x - pt.x;
		var y = this.y - pt.y;
		return new HthxMap.Point(x, y);
	},
	
	equals: function(pt){
		if((this.x==pt.x) && (this.y==pt.y)){
			return true;
		}
		return false;
	}
}
