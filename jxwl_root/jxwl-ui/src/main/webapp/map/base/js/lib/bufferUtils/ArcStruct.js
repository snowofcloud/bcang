/**
 * <p>Description: 弧线基本操作构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-25 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */

HthxMap.ArcStruct = function(center, radius, startAngle, endAngle){
	this.midAngles = [];
	this.midPts = [];
	
	this.center = center;
	this.radius = radius;
	this.startAngle = startAngle;
	this.endAngle = endAngle;
	
	/**生成符合规则的中间点的角度*/		
	this.makeMidAngle = function(angle){
		var mid = (angle+ Math.PI*2)%(Math.PI*2);
		if(mid > this.startAngle && mid < this.endAngle){
			return mid;
		}
		if(this.endAngle > Math.PI*2 && mid < (this.endAngle - Math.PI*2)){  //方向不对
			return mid + Math.PI*2;
		}
		return NaN;  //null
	}
	
	/**获取下一个单元*/
	this.getNextShape = function(uPt){
		if(!uPt)		{
			if(this.midPts.length == 0){
				return this;
			}
			else{
				return new HthxMap.ArcStruct(this.center, this.radius, this.startAngle, this.midAngles[0]);
			}
		}			
		
		var index = ArrayUtil.getIndexInArray(uPt, this.midPts);
		if(index == this.midPts.length-1){
			return new HthxMap.ArcStruct(this.center, this.radius, this.midAngles[index], this.endAngle);
		}
		else{
			return new HthxMap.ArcStruct(this.center, this.radius, this.midAngles[index], this.midAngles[index+1]);
		}
	}		
}

HthxMap.ArcStruct.prototype = {
	/**起点*/
	getStart: function(){
		return GeomUtil.getPointByAngle(this.center, this.radius, this.startAngle);
	},

	/**终点*/	
	getEnd: function(){			
		return GeomUtil.getPointByAngle(this.center, this.radius, this.endAngle);
	},

	/**添加交点*/
	addMidPoint: function(uPt){
		this.midPts.push(uPt);
		this.midAngles.push(this.makeMidAngle(GeomUtil.getRotation(uPt.point, this.center)));    //根据当前交点获取正确的角度
	},	

	/**按距离起点的距离排序*/
	sort: function(){
		var temp;    //UnionPoint
		var temp2;    //Number
		for(var i = 0; i < this.midAngles.length; i++){
			for(var j = 0; j < this.midAngles.length - i - 1; j++){
				if(this.midAngles[j] > this.midAngles[j+1]){
					temp = this.midPts[j];
					this.midPts[j] = this.midPts[j+1];
					this.midPts[j+1] = temp;
					
					temp2 = this.midAngles[j];
					this.midAngles[j] = this.midAngles[j+1];
					this.midAngles[j+1] = temp2;						
				}
			}
		}
	},	

	/**获取下一个单元的中点*/
	getNextMidPoint: function(uPt){
		var arc = this.getNextShape(uPt);
		var pt = GeomUtil.getPointByAngle(this.center, this.radius, (arc.startAngle+arc.endAngle)/2);
		return pt;
	},

	/**获取下一个交点*/
	getNextUnionPoint: function(uPt){
		if(!uPt){
			return this.midPts[0];    //如果midPts为空，将返回null	
		}
		var index = ArrayUtil.getIndexInArray(uPt, this.midPts);
		return this.midPts[index+1];
	}
}
