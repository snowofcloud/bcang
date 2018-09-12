/**
 * <p>Description: 地图业务红常用的基础芳芳</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-8-15</p>
 * @author:  
 * @extends：
 * @param:
 */
  
var mapBaseMethod ={
	 /*浮点数转换为度分秒*/
	degreesToStringHDMS: function(degrees) {
	   	 var normalizedDegrees = goog.math.modulo(degrees + 180, 360) - 180;
	   	 var x = Math.abs(Math.round(3600 * normalizedDegrees));
	   	 if (degrees < 0){
	   		 return '-' + Math.floor(x / 3600) + '\u00b0 ' +
	 				Math.floor((x / 60) % 60) + '\u2032 ' +
	 				Math.floor(x % 60) + '\u2033 '; 
	 }else{
		 return Math.floor(x / 3600) + '\u00b0 ' +
	      			Math.floor((x / 60) % 60) + '\u2032 ' +
	      			Math.floor(x % 60) + '\u2033 '; 
	 }
	},
	
	//判断返回坐标长度
	coordinateLength:function(){
		return arguments[0].toString().split(".")[0].length; 
	},
	
	/**
	 * 转换坐标函数
	 * @param lon  经度
	 * @param lat  纬度
	 * @param flag "EPSG:4326", "EPSG:3857"转换标志
	 */
	 initProjTransform:function(lon,lat,flag){
		var points = [];
		if(flag){
			points.push(ol.proj.transform([parseFloat(lon),parseFloat(lat)], "EPSG:4326", HthxMap.Settings.projection));
		}else{
			points.push(ol.proj.transform([parseFloat(lon),parseFloat(lat)],  HthxMap.Settings.projection,"EPSG:4326"));
		}
		return points;
	}
}