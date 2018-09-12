/**
 * <p>Description: 线段或弧线间交点构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-25 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */

HthxMap.UnionPoint = function(point,i,j){
	//是否已经使用，用于跟踪生成缓冲区的边界
	this.hasUsed = false; 
	
	this.point = point;
	this.i = i;    
	this.j = j;
}
