/**
 * <p>Description: 地图工具函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-06 </p>
 * @author: 张琴
 */
HthxMap.Utils={};


HthxMap.Utils.decToHex=function(str){
	var res=[];
	for(var i=0;i<str.length;i++){
		res[i]=('00'+str.charCodeAt(i).toString(16)).slice(-4);		
	}
	return '\\u'+res.join('\\u');
};
/**
 * 从距离（m）转换到经纬度
 * @param lat 中心点的纬度
 * @param distance 距离
 * @returns {Number} 度
 */
HthxMap.Utils.getJWDSize =  function(lat,distance){  
//	   var r1 = 6378137;
//	   var r = r1*Math.cos(lat);
//	   var lonDes = 180/(3.1415927*r);
	   
	   //取一度的距离
	   var lat1 = lat;
	   var lat2 = lat+1;	   
	   var lon1 = 100;
	   var lon2 = 100;
	   
	   var des = 1/HthxMap.Utils.getDistance(lat1,lon1,lat2,lon2);
	   
	    return distance*des ;  
};




/**
 * 计算两点之间的距离  xyanzhao
 * @param lat1  第一点纬度
 * @param lon1  第一点经度
 * @param lat2  第二点纬度
 * @param lon2  第二点经度
 * @returns {Number}
 */
HthxMap.Utils.getDistance = function getDistance(lat1,lon1,lat2,lon2){
	   
	  var  radLat1=rad(lat1);
	  var  radLat2=rad(lat2);
	  
	  var a=radLat1 - radLat2;
	  var b=rad(lon1)-rad(lon2);
	  
	 var  s=2*Math.asin(
			 Math.sqrt(
		    
					 Math.pow(Math.sin(a/2),2)+
					 Math.cos(radLat1)*
					 Math.cos(radLat2)*
					 Math.pow(Math.sin(b/2), 2)
			 
			 ));	  
	  s = s*6378.137;
	  s = Math.round(s*10000)/10;
	  return s;	  
};

function rad(d){
	   return d*Math.PI/180.0;
}

/**
 * km m 转化为海里
 * @param unit 单位 (km,m)
 * @param value值
 * @returns {Number}转换后的值，保留两位小数
 */
HthxMap.Utils.lengthUnitShift = function lengthUnitShift(unit,value){
	var num;
	if(unit=='km'){
		num = Math.round(value/1.852 * 100) / 100;
	}else if(unit=='m'){
		num = Math.round((value/1000)/1.852 * 100) / 100;
	}else if(unit=='hl'){
		//周边查询使用，单位为海里转化为M
		num = Math.round((value*1.852*1000) * 100) / 100;
	}
	return num;
};

/**
 * km² m² 转化为平方海里
 * @param unit 单位 (km,m)
 * @param value值
 * @returns {Number}
 */
HthxMap.Utils.polygonUnitShift = function polygonUnitShift(unit,value){
	if(unit=='km'){
		return value/(1.852*1.852);
	}else if(unit=='m'){
		return (value/(1000*1000))/(1.852*1.852);
	}
};

/**
 * 两个点算角度
 * @param onePoint 起点
 * @param twoPoint 终点
 * @returns {Number}
 */
HthxMap.Utils.countOrientation = function countOrientation(onePoint,twoPoint){
	var retGap=0;
	var gapX=twoPoint.x-onePoint.x;
	var gapY=twoPoint.y-onePoint.y;
	var piValue=Math.atan2(gapY,gapX);
	retGap=piValue*(180/Math.PI);
	if(piValue>0){
		return retGap.toFixed(0);
	}else{
		return (360+retGap).toFixed(0);
	}
	
};

/**
 * 把浮点数表示的经(纬)度转换为度分秒表示
 * @param origin 初始经(纬)度
 * @returns target 转换后的经(纬)度,以逗号(,)分隔的字符串
 */
HthxMap.Utils.floatToLonlat = function (origin){
    var target = "";
    var du = parseInt(origin);
    var floatFen = (origin - du) * 60;
    var fen = parseInt(floatFen);
    var floatMiao = (floatFen - fen) * 60;
    var miao = parseInt(floatMiao);
    var arr = [du, fen, miao];
    target = arr.join(",");
    return target;
    
};

/**
 * 把度分秒表示的经(纬)度转换为浮点数表示，保留6位小数
 * @param origin 初始经(纬)度,以逗号(,)分隔的字符串,如果分秒没有，则为0
 * @returns target 转换后的经(纬)度
 */
HthxMap.Utils.lonlatToFloat = function (origin){
    var arr = origin.split(",");
    var target = 0.0;
    if(arr[0].indexOf("-") > -1){
    	var a = -(parseFloat(arr[2])/(60*60)) - parseFloat(arr[1])/60 + parseFloat(arr[0]);
    	target = parseFloat(a.toFixed(6));
    }else{
    	var a = parseFloat(arr[2])/(60*60) + parseFloat(arr[1])/60 + parseFloat(arr[0]);
    	target = parseFloat(a.toFixed(6));
    }
    return target;
};