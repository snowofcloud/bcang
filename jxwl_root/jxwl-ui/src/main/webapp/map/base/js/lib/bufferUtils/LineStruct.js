/**
 * <p>Description: 线段基本操作构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-25 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */

HthxMap.LineStruct = function(start, end){

	this.midPts = [];    //中间点数组		

	this.start = start;
	this.end = end;
	
	/**获取下一个单元*/
	this.getNextShape = function(uPt){
		var line = new HthxMap.LineStruct(this.start, this.end);
		if(!uPt){
			if(this.midPts.length == 0){
				return this;
			}
			else{
				return new HthxMap.LineStruct(line.start,this.midPts[0].point);
			}
		}
		var index = ArrayUtil.getIndexInArray(uPt,this.midPts);
		if(index == this.midPts.length-1){
			return new HthxMap.LineStruct(this.midPts[index].point, this.end);
		}
		else{
			return new HthxMap.LineStruct(this.midPts[index].point,this.midPts[index+1].point);
		}
	}
}

HthxMap.LineStruct.prototype = {
	/**添加交点*/
	addMidPoint: function(uPt){
		this.midPts.push(uPt);    //加入点
	},
			
	/**按照距离起点的远近排序*/
	sort: function(){
		var temp;    //UnionPoint
		for(var i=0; i<this.midPts.length;i++){
			for(var j=0; j<this.midPts.length - i - 1; j++){
				if(Point.distance(this.start,this.midPts[j].point) > Point.distance(this.start,this.midPts[j+1].point)){
					temp = this.midPts[j];
					this.midPts[j] = this.midPts[j+1];
					this.midPts[j+1] = temp;
				}
			}
		}
	},
			
	/**获取下一个单元的中点*/
	getNextMidPoint: function(uPt){
		var line = this.getNextShape(uPt);
		return Point.interpolate(line.start, line.end, 0.5);
	},

	/**获取下一个交点*/
	getNextUnionPoint: function(uPt){
		if(!uPt){
			return this.midPts[0];
		}
		//获取点的索引
		var index = ArrayUtil.getIndexInArray(uPt,this.midPts);
		return  this.midPts[index+1];
	}
}
