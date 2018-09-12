/**
 * <p>Description: 缓冲区边界生成构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-06 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */

HthxMap.BufferUtil = function(){
//	document.write("<script src='../map/js/lib/bufferUtils/ArcStruct.js'></script>");
	//记录多边形内部的边数
	var inLine = 0;   
	//第一部分，获取缓冲区初始边界：由线段和弧段两种类型构成
	this.getInitBufferArray = function(pts, radius){
		var structs = [];		
		
		//保证折线至少有两个点
		var len = pts.length; 
		if(len < 2){
			return [];	
		}
		if(len == 2){
			structs.push(getArcStructByTwoPts(pts[1], pts[0], radius));
			structs.push(getLineStruct(pts[0], pts[1],radius));
			structs.push(getArcStructByTwoPts(pts[0], pts[1], radius));
			structs.push(getLineStruct(pts[1],pts[0],radius));
			
			return structs;
		}
		var i;			
		var struct;//弧段
		var line1, line2;//线段
		var isConvex;//记录是否是凸角
		var preConvex = true;
		
		//按照逆时针顺寻组织右半部分
		for(i = 0; i < len; i++){	
			if(i == 0){    //起点,加入弧段
				structs.push(getArcStructByTwoPts(pts[1], pts[0], radius));
				preConvex = true;
			}
			else if(i == len - 1){    //终点，加入弧段
				if(preConvex){
					structs.push(getLineStruct(pts[i-1], pts[i],radius));//加入线段
					inLine++;
				}
				structs.push(getArcStructByTwoPts(pts[len-2], pts[len-1], radius));
				preConvex = true
			}
			else{    //中间点
				isConvex = Boolean(GeomUtil.isLeft(pts[i],pts[i-1],pts[i+1]) < 0);    										
				if(isConvex){    //凸角
					if(preConvex){
						structs.push(getLineStruct(pts[i-1],pts[i],radius));//加入线段
						inLine++;
					}
					structs.push(getArcStructByThreePts(pts[i-1],pts[i],pts[i+1],radius));//加入弧段			
					inLine++;
				}
				else{    //凹角						
					if(preConvex){
						structs.push(getLineStruct(pts[i-1],pts[i],radius));
						inLine++;
					}
					structs.push(getLineStruct(pts[i],pts[i+1],radius));
					inLine++;
				}
				preConvex = isConvex;
			}
		}	
		
		//按照逆时针组织左半部分
		for(i = len-2; i >= 1; i--){//中间点
			isConvex = Boolean(GeomUtil.isLeft(pts[i],pts[i+1],pts[i-1]) < 0);
			
			if(isConvex){//凸角
				if(preConvex){
					structs.push(getLineStruct(pts[i+1],pts[i],radius));//加入线段
				}
				structs.push(getArcStructByThreePts(pts[i+1],pts[i],pts[i-1],radius));//加入弧段	
				if(i == 1){//处理和起点半圆弧之间的关系
					structs.push(getLineStruct(pts[1],pts[0],radius));	
				}
			}
			else{//凹角处
				if(preConvex){
					structs.push(getLineStruct(pts[i+1],pts[i],radius));
				}
				structs.push(getLineStruct(pts[i],pts[i-1],radius));					
			}
			preConvex = isConvex;		
		}
		return structs;
	}	
	/**获取两点的平行线段*/
	function getLineStruct(ptStart, ptEnd, radius){
		var angle = GeomUtil.getRotation(ptEnd, ptStart) - Math.PI/2;
		var start = GeomUtil.getPointByAngle(ptStart, radius, angle);
		var end  = GeomUtil.getPointByAngle(ptEnd, radius, angle);
		return new HthxMap.LineStruct(start, end);
	}
	
	/**根据两点获取半圆弧*/
	function getArcStructByTwoPts(ptStart, ptEnd, radius){
		var angle = GeomUtil.getRotation(ptEnd, ptStart);
		var startAngle = (angle - Math.PI/2+Math.PI*2)%(Math.PI*2);
		var endAngle = (angle + Math.PI/2 + Math.PI*2)%(Math.PI*2);
		if(endAngle < startAngle){
			endAngle += Math.PI*2;
		}
		return new HthxMap.ArcStruct(ptEnd, radius, startAngle, endAngle);
	}
	
	/**根据三点获取圆弧*/
	function getArcStructByThreePts(ptPre, ptCur, ptNext, radius){
		var startAngle = (GeomUtil.getRotation(ptCur, ptPre) - Math.PI/2 + Math.PI*2)%(Math.PI*2);
		var endAngle = (GeomUtil.getRotation(ptNext, ptCur) - Math.PI/2 + Math.PI*2)%(Math.PI*2);
		if(endAngle < startAngle){
			endAngle += Math.PI*2;	
		}
		return new HthxMap.ArcStruct(ptCur,radius,startAngle,endAngle);
	}
	
	/**两线段/弧段之间求交**/
	this.GetIntersectArray = function(structs){
		var unionPts = [];
		for(var i = 0; i < structs.length-1; i++){
			for(var j = i+1; j < structs.length; j++){
				//如果首尾结点相等，两者处于连接关系时
				if((structs[i] instanceof HthxMap.ArcStruct) && (structs[j] instanceof HthxMap.LineStruct)){
					if(isPtEqual(structs[i].getEnd(), structs[j].start)){
						continue;
					}
				}
				if((structs[i] instanceof HthxMap.LineStruct) && (structs[j] instanceof HthxMap.ArcStruct)){
					if(isPtEqual(structs[i].end, structs[j].getStart())){
						continue;
					}
				}
					
				var insecs;
				
				//处理线段弧段的四种情况
				if(structs[i] instanceof HthxMap.LineStruct && structs[j] instanceof HthxMap.LineStruct){//两个都是线段
					insecs = getInsecsOfTwoLines(structs[i], structs[j]);
				}
				else if(structs[i] instanceof HthxMap.ArcStruct && structs[j] instanceof HthxMap.ArcStruct){//两个都是弧段
					insecs = getInsecsOfTwoArcs(structs[i], structs[j]);
				}
				else if(structs[i] instanceof HthxMap.LineStruct && structs[j] instanceof HthxMap.ArcStruct ){//一个是线段，一个是弧段
					insecs = getInsecsOfLineAndArc(structs[i], structs[j]);
				}
				else{//一个弧段，一个线段
					insecs = getInsecsOfLineAndArc(structs[j], structs[i]);
				}
				for(var k = 0; k < insecs.length;k++){
					unionPts.push(new HthxMap.UnionPoint(insecs[k],i,j));    //表示每两条线段/弧线之间交点的个数
				}
			}
		}
		return unionPts;			
	}		
	
	/**求线段和线段之间的交点*/
	function getInsecsOfTwoLines(line1, line2){
		var insecs = [];
		if(GeomUtil.isLineSegmentsIntersect(line1.start,line1.end,line2.start,line2.end)){
			insecs.push(GeomUtil.getLineIntersection(line1.start,line1.end,line2.start,line2.end));	
		}
		return insecs;			
	}
	
	/**求弧段和弧段之间的交点*/
	function getInsecsOfTwoArcs(arc1, arc2){
		var insecs = [];
		
		var dis = Point.distance(arc1.center, arc2.center);
		if(dis > 2*arc1.radius){//相离
			return insecs;
		}
		
		//求出夹角
		var rotation = GeomUtil.getRotation(arc2.center, arc1.center);    //同一点，值为0
		var angle = Math.acos(dis/2/arc1.radius);			
		
		var mid1 = arc1.makeMidAngle(rotation - angle);
		var mid2 = arc2.makeMidAngle(rotation + Math.PI + angle);
		
		if(!isNaN(mid1) && !isNaN(mid2)){                                      
			insecs.push(GeomUtil.getPointByAngle(arc1.center, arc1.radius, mid1));
		}
		
		mid1 = arc1.makeMidAngle(rotation + angle);
		mid2 = arc2.makeMidAngle(rotation + Math.PI - angle);
		if(!isNaN(mid1) && !isNaN(mid2)){
			insecs.push(GeomUtil.getPointByAngle(arc1.center, arc1.radius, mid1));
		}
		return insecs;    //交点可能有多个
	}
	
	/**求线段和弧段之间的交点*/
	function getInsecsOfLineAndArc(line, arc){
		var insecs = [];
		
		//这里需要垂直距离
		var dis = GeomUtil.getVerticalDistanceOfPointAndLine(arc.center, line.start, line.end);
		if(dis > arc.radius){
			return insecs;
		}
		
		var k = (line.end.y - line.start.y)/(line.end.x - line.start.x);
		var b = line.end.y - k*line.end.x;
		var A = 1 + k*k;
		var B =-2*arc.center.x + 2*k*b-2*k*arc.center.y;
		var C = arc.center.x*arc.center.x+(b-arc.center.y)*(b-arc.center.y)-arc.radius*arc.radius;
		var DELTA =B*B-4*A*C;
		var x1 =(-B+Math.sqrt(DELTA))/(2*A);
		var x2 =(-B-Math.sqrt(DELTA))/(2*A);
		var y1 =k*x1+b;
		var y2 =k*x2+b;
		
		var insec1 = new HthxMap.Point(x1, y1);
		var insec2 = new HthxMap.Point(x2, y2);
		var lineDis = Point.distance(line.start, line.end);
		
		var rotation = GeomUtil.getRotation(insec1, arc.center);		
		var midAngle = arc.makeMidAngle(rotation);
		if(!isNaN(midAngle) && Point.distance(insec1, line.start) < lineDis && Point.distance(insec1, line.end) < lineDis){
			insecs.push(insec1);
		}
		
		rotation = GeomUtil.getRotation(insec2, arc.center);		
		midAngle = arc.makeMidAngle(rotation);
		if(!isNaN(midAngle) && Point.distance(insec2, line.start) < lineDis && Point.distance(insec2, line.end) < lineDis){
			insecs.push(insec2);
		}
		return insecs;
	}
	
	/**求交时改变两个弧段*/
	function changeTwoArcsByInsec(arc1,arc2){			
		var dis = Point.distance(arc1.center,arc2.center);
		//求出夹角
		var rotation = GeomUtil.getRotation(arc2.center,arc1.center);
		var angle = Math.acos(dis/2/arc1.radius);			
		
		var mid1 = arc1.makeMidAngle(rotation - angle);
		var mid2 = arc2.makeMidAngle(rotation + Math.PI + angle);
		var pt;
		if(!isNaN(mid1) && !isNaN(mid2)){                                             
			arc1.endAngle = mid1;
			arc2.startAngle = mid2;
			return;
		}
		
		//在下部分逆时针时
		mid1 = arc1.makeMidAngle(rotation + angle);
		mid2 = arc2.makeMidAngle(rotation + Math.PI - angle);
		if(!isNaN(mid1) && !isNaN(mid2)){                                   
			arc1.endAngle = mid1;
			arc2.startAngle = mid2;
		}
	}		
	
	/***求交时候改变线段和弧段*/
	function changeLineAndArcByInsec(line,arc,isEnd){
		var k = (line.end.y - line.start.y)/(line.end.x - line.start.x);
		var b = line.end.y - k*line.end.x;
		var A = 1 + k*k;
		var B =-2*arc.center.x + 2*k*b-2*k*arc.center.y;
		var C = arc.center.x*arc.center.x+(b-arc.center.y)*(b-arc.center.y)-arc.radius*arc.radius;
		var DELTA =B*B-4*A*C;
		var x1 =(-B+Math.sqrt(DELTA))/(2*A);
		var x2 =(-B-Math.sqrt(DELTA))/(2*A);
		var y1 =k*x1+b;
		var y2 =k*x2+b;			
		
		var insec1 = new HthxMap.Point(x1,y1);			
		var lineDis = Point.distance(line.start,line.end);
		
		var rotation = GeomUtil.getRotation(insec1,arc.center);		
		var midAngle = arc.makeMidAngle(rotation);
		if(!isNaN(midAngle) && Point.distance(insec1,line.start) < lineDis && Point.distance(insec1,line.end) < lineDis){ 
			if(isEnd){
				arc.startAngle = midAngle;
				line.end = insec1;
			}				
			else{
				arc.endAngle = midAngle;
				line.start = insec1;
			}				
			return;
		}
		
		var insec2 = new HthxMap.Point(x2,y2);
		rotation = GeomUtil.getRotation(insec2,arc.center);		
		midAngle = arc.makeMidAngle(rotation);
		if(!isNaN(midAngle) && Point.distance(insec2,line.start) < lineDis && Point.distance(insec2,line.end) < lineDis){ 
			if(isEnd){
				arc.startAngle = midAngle;
				line.end = insec2;
			}					
			else{
				arc.endAngle = midAngle;
				line.start = insec2;
			}
		}			
	}			
	
	
	/**两点是否相等*/
	function isPtEqual(pt1, pt2){
		var threshold = 0.0001;
		if(Math.abs(pt1.x - pt2.x) < threshold && Math.abs(pt1.y - pt2.y) < threshold){
			return true;
		}
		else{
			return false;
		}
	}
	
	//第三部分，删除缓冲区内部的点，返回所有外部的点
	this.selectInsecsOnBufferEdge = function(insecs, pts, radius){
		var eInsecs = [];    
		for(var i = 0; i < insecs.length; i++){	
			var flag = true;
			for(var j = 0; j < pts.length - 1; j++){
				var dis = GeomUtil.getDistanceOfPointAndLine(insecs[i].point, pts[j], pts[j+1]);
				if(radius - dis > 0.001){    // 为内部点
					flag = false;
					break;
				}
			}
			if(flag){    //不为内部的点
				eInsecs.push(insecs[i]);
			}
		}			
		return eInsecs;    
	}
	
	/**判断点是否在缓冲区内部*/
	function isPtInBuffer(pt, pts, radius){
		var threshold = 0.0001;
		var len = pts.length;
		var dis;
		for(var i = 0; i < len - 1;i++)	{
			dis = GeomUtil.getDistanceOfPointAndLine(pt, pts[i], pts[i+1]);
			if(radius - dis > threshold){
				return true;
			}
		}			
		return false;
	}
	//第四部分，将交点加入边界中
	this.addInsecsToStructs = function(eInsecs, structs){
		var len = eInsecs.length;
		var i;			
		for(i = 0; i < len;i++){
			structs[eInsecs[i].i].addMidPoint(eInsecs[i]);
			structs[eInsecs[i].j].addMidPoint(eInsecs[i]);
		}
		var sLen = structs.length;
		for(i = 0; i < sLen;i++){
			structs[i].sort();
		}
	}
	
	//第五部分，跟踪生成缓冲区
	this.trackBufferStructs = function(unionPts, structs, pts, radius){
		//如果没有交点，则直接返回原来的structs
		if(unionPts.length == 0){
			return structs;	
		}
		var bf = [];
		
		var beginUpt = unionPts[0];
		var unionPt;
		var no;
		var cnt = 0;//已经参与构造边界的交点个数			
		var index = 0;//在unionPts中没有参与构造边界的最小索引
		var len = unionPts.length;
		while(cnt < len){
			//判断第一个点的出入性		
			unionPt = beginUpt;	
			var pt1 = (structs[unionPt.i]).getNextMidPoint(unionPt);
			var pt2 = (structs[unionPt.j]).getNextMidPoint(unionPt);
			if(GeomUtil.multiply(pt1, pt2, unionPt.point) < 0){//利用叉积判断出入性
				no = unionPt.i;
			}
			else{
				no = unionPt.j;
			}
			
			do{
				if(unionPt){//如果是交点，则跳转到下一条线段/弧段上
					unionPt.hasUsed = true;
					cnt++;
					if(no == unionPt.i){
						no = unionPt.j;
					}
					else{
						no = unionPt.i;
					}

					bf.push((structs[no]).getNextShape(unionPt));//添加线段/弧段,根据交点，一段一段地添加
					unionPt = (structs[no]).getNextUnionPoint(unionPt);//记录下一个交点
				}
				else{//否则，继续前行
					no = (no+1)%structs.length;							
					bf.push(structs[no].getNextShape(null));	
					unionPt = (structs[no]).getNextUnionPoint(null);//记录下一个交点
				}		
				
			}while(beginUpt != unionPt);
				
			//找到下一个没有参与构造缓冲区边界的交点
			for(var i = index; i < len;i++)	{
				if(!unionPts[i].hasUsed){
					beginUpt = unionPts[i];
					index = i+1;
					break;
				}
			}
			
			/*if(pts[0].y < pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])<0){  //GeomUtil.isLeft(pts[0], pts[1], pts[pts.length-2])
				for(var i=structs.length-1; i>structs.length-pts.length*2+1; i--){
					bf.push(structs[i]);	
				}
			}else if(pts[0].y > pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])>0){
				for(var i=structs.length-1; i>structs.length-pts.length*3+2; i--){
					bf.push(structs[i]);	
				}
			}else if(pts[0].y < pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])>0){
				for(var i=structs.length-1; i>structs.length-pts.length*3+2; i--){
					bf.push(structs[i]);	
				}
			}else if(pts[0].y > pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])<0){
				for(var i=structs.length-1; i>structs.length-pts.length*2+1; i--){
					bf.push(structs[i]);	
				}
			}*/
		}			
		return bf;
	}	
	
	//第六部分，直线，根据输入点，生成缓冲区对象集合
	this.lineBufferStructs = function(pts, radius){
		//数据预处理,删除相邻的相同点
		var bfPts = GeomUtil.RemoveNearEqualPointFromPts(pts);
		//第一步 获取缓冲区初始边界：由线段和弧段两种类型构成
		var structs = this.getInitBufferArray(bfPts, radius);
		//第二步 进行求交运算
		var insecs = this.GetIntersectArray(structs);    //UnionPoint
		
		//第三步 剔除在缓冲区内部的交点 ,返回除内部交点外的所有交点	
		var eInsecs = this.selectInsecsOnBufferEdge(insecs, bfPts, radius);

		//第四步 将交点加到线段/弧段上，并按距离起点的距离排序
		this.addInsecsToStructs(eInsecs, structs);
		
		//第五步 跟踪，生成缓冲区 
		var bBuffer = this.trackBufferStructs(eInsecs, structs, bfPts, radius);
		
		return bBuffer;
	}
	
	//第七部分，多边形，根据输入点，生成缓冲区对象集合
	this.polygonBufferStructs = function(pts, radius){
		//数据预处理,删除相邻的相同点
		var bfPts = GeomUtil.RemoveNearEqualPointFromPts(pts);
		//第一步 获取缓冲区初始边界：由线段和弧段两种类型构成
		var structs = this.getInitBufferArray(bfPts, radius);
		//存放新的线段和弧线
		var tempStructs = [];
		if(pts[0].y < pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])<0){  //GeomUtil.isLeft(pts[0], pts[1], pts[pts.length-2])
			for(var i=inLine+1; i<structs.length; i++){
				if(i == inLine+1){
					tempStructs.push(getArcStructByThreePts(pts[1], pts[0], pts[pts.length-2], radius));//加入弧段
					continue;
				}
				tempStructs.push(structs[i]);
			}
		}else if(pts[0].y > pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])>0){
			for(var i=1; i<=inLine+1; i++){
				if(i == inLine+1){
					tempStructs.push(getArcStructByThreePts(pts[pts.length-2], pts[0], pts[1], radius));//加入弧段
					break;
				}
				tempStructs.push(structs[i]);
			}
		}else if(pts[0].y < pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])>0){
			for(var i=1; i<=inLine+1; i++){
				if(i == inLine+1){
					tempStructs.push(getArcStructByThreePts(pts[pts.length-2], pts[0], pts[1], radius));//加入弧段
					break;
				}
				tempStructs.push(structs[i]);
			}
		}else if(pts[0].y > pts[1].y && GeomUtil.isLeft(pts[pts.length-2], pts[0], pts[1])<0){
			for(var i=inLine+1; i<structs.length; i++){
				if(i == inLine+1){
					tempStructs.push(getArcStructByThreePts(pts[1], pts[0], pts[pts.length-2], radius));//加入弧段
					continue;
				}
				tempStructs.push(structs[i]);
			}
		}
		
		//第二步 进行求交运算
		var insecs = this.GetIntersectArray(tempStructs);    //UnionPoint
		//第三步 剔除在缓冲区内部的交点 ,返回除内部交点外的所有交点	
		var eInsecs = this.selectInsecsOnBufferEdge(insecs, bfPts, radius);
		//第四步 将交点加到线段/弧段上，并按距离起点的距离排序
		this.addInsecsToStructs(eInsecs, tempStructs);
		
		//第五步 跟踪，生成缓冲区 
		var bBuffer = this.trackBufferStructs(eInsecs, tempStructs, bfPts, radius);
		
		return bBuffer;
	}
} 

HthxMap.BufferUtil.prototype = {
	/**
	 * 缓冲区生成算法
	 * */
	getBuffer: function(geometry, radius, type){
		
		if(type == "Point"){    //点
			var geo = [];
			var center = geometry.getCoordinates();
			geo.push(new ol.Feature(new ol.geom.Circle(center, radius)));
			
			return geo;
		}else if(type == "Circle"){    //圆
			var geo = [];
			var center = geometry.getCenter();
			var cRadius = geometry.getRadius();
			
			var feature1 = new ol.Feature(new ol.geom.Circle(center, cRadius+radius));
			geo.push(feature1);
//			if(cRadius > radius){
//				var feature2 = new ol.Feature(new ol.geom.Circle(center, cRadius-radius));
//				geo.push(feature2);
//			}
			
			return geo;
		}else if(type == "LineString"){    //线
			var pts = [];
			var geom = geometry.getCoordinates();
			for(var i=0; i<geom.length; i++){
				var point = new HthxMap.Point(geom[i][0], geom[i][1]);
				pts.push(point);
			}
			var bBuffer = this.lineBufferStructs(pts, radius);
			
			var geo = [];
			for(var i=0; i<bBuffer.length; i++){
				var b = [];
				var a = [];
				
				if(bBuffer[i] instanceof HthxMap.ArcStruct){
					var mPts = GeomUtil.getArcPtsByAngle(bBuffer[i].center, bBuffer[i].radius, bBuffer[i].startAngle, bBuffer[i].endAngle);
					for(var j=0; j<mPts.length-1; j++){
						a.push([mPts[j].x, mPts[j].y]);
						a.push([mPts[j+1].x, mPts[j+1].y]);
						b.push(a);
					}
					var ls = new ol.Feature(new ol.geom.MultiLineString(b));
					geo.push(ls);
				}else{
					a.push([bBuffer[i].start.x,bBuffer[i].start.y]);
					a.push([bBuffer[i].end.x,bBuffer[i].end.y]);
					var ls = new ol.Feature(new ol.geom.LineString(a));
					geo.push(ls);
				}
			}
			return geo;
		}else if(type == "Polygon"){    //面
			var pts = [];    //存放所有点构成的线
			var pts1 = [];    //存放最后两个点
			var ptss = [];
			var geom = geometry.getCoordinates()[0];
			
			for(var i=0; i<geom.length; i++){
				if(i == geom.length-1){
					var point = new HthxMap.Point(geom[i][0], geom[i][1]);
					pts.push(point);
				}else{
					var point = new HthxMap.Point(geom[i][0], geom[i][1]);
					pts.push(point);
				}
			}
			var bBuffer = this.polygonBufferStructs(pts, radius);
			
			var geo = [];
			for(var i=0; i<bBuffer.length; i++){
				var b = [];
				var a = [];
				
				if(bBuffer[i] instanceof HthxMap.ArcStruct){
					var mPts = GeomUtil.getArcPtsByAngle(bBuffer[i].center, bBuffer[i].radius, bBuffer[i].startAngle, bBuffer[i].endAngle);
					for(var j=0; j<mPts.length-1; j++){
						a.push([mPts[j].x, mPts[j].y]);
						a.push([mPts[j+1].x, mPts[j+1].y]);
						b.push(a);
					}
					var ls = new ol.Feature(new ol.geom.MultiLineString(b));
					geo.push(ls);
				}else{
					a.push([bBuffer[i].start.x, bBuffer[i].start.y]);
					a.push([bBuffer[i].end.x, bBuffer[i].end.y]);
					var ls = new ol.Feature(new ol.geom.LineString(a));
					geo.push(ls);
				}
			}
			return geo;
		}
	}
}
