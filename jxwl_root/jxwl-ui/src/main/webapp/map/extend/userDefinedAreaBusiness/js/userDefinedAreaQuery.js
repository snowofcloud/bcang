/**
 * <p>Description: 自定义区域--提交数据</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-08-01 </p>
 * @author: wl
 * @extends：
 * @param:
 */

/*保存画图中限制区域坐标点集*/
var pointSet = null;
var pointList = null;
var elecRailflagCircleOrNot = false; /*true:为圆，false: 非圆*/
var preDrawFeature;
$(function() {
//	$("#save-enterpriseRight-form").html5Validate(function (e) {
//		submitElecRailToDB();
//        return false;
//	});
});

//限制区域区域入口
function queryUserDefined(){
	//只是用多边形区域时
	var id = arguments[0].data.id;
	//var id = $(this).attr("id");
	var start = id.indexOf("b");
	var str = id.slice(0, start);
	var str1 = str.slice(0,1).toUpperCase();
	var drawType = str1 + str.slice(1);
	beginDrawDefined(drawType);
};
//限制区域画区域
function beginDrawDefined(drawType){
	removeMapEvents();
	var id = drawType.slice(0,1).toLowerCase() + drawType.slice(1);
	var typeId = id  + "ElectRail";
	
	//先清除当前激活控件样式，再添加即将要激活的控件样式
	if(HthxMap.curControl.target){
		$("#" + HthxMap.curControl.target).removeClass(HthxMap.curControl.displayClass + 'Activate');
		$("#" + HthxMap.curControl.target).addClass(HthxMap.curControl.displayClass + 'Inactivate');
	}	
	$("#" + typeId).removeClass(typeId + "Inactivate");
	$("#" + typeId).addClass(typeId + "Activate");
	//记录地图当前激活的控件
	HthxMap.curControl.target = typeId;
	HthxMap.curControl.displayClass = typeId;
	
	addInteractionsDefined(drawType, typeId);
};

function addInteractionsDefined(drawType, typeId){	
	if(!HthxMap.curFeatureInteraction){
		HthxMap.curFeatureInteraction = "active";
	}
	var layers = map.getLayers();
	var featureLayer =  HthxMap.getLayerById("featureLayer");
    
    var geometryFunction, maxPoints;
	var boxType = '';
	var searchRadius;
    if('Box' === drawType){
		drawType = 'LineString';
		boxType = "Polygon";
		maxPoints = 2;
	    geometryFunction = function(coordinates, geometry) {
	        if (!geometry) {
	            geometry = new ol.geom.Polygon(null);
	        }
	        var start = coordinates[0];
	        var end = coordinates[1];
	        geometry.setCoordinates([
	            [start, [start[0], end[1]], end, [end[0], start[1]], start]
	        ]);
	        return geometry;
	    };
	}
    var lineType = "";
    if("Line" == drawType){
    	drawType = 'LineString';
    	lineType = 'LineString';
    }
    var drawElectRailArea = new ol.interaction.Draw({
		source:featureLayer.getSource(),
		type:drawType,
	    geometryFunction: geometryFunction,
	    maxPoints: maxPoints,
		style:new ol.style.Style({
			fill:new ol.style.Fill({
				color:'rgba(255,255,255,0.2)'
			}),
			stroke:new ol.style.Stroke({
				color: 'rgba(0, 0, 0, 0.5)',
		        lineDash: [10, 10],
		        width: 2
			})
		})
	});
    
    HthxMap.curMapInteraction = drawElectRailArea;
	map.addInteraction(drawElectRailArea);
	/*设置鼠标图标样式*/
	HthxMap.mouseUnbindEvent(map, "crosshair"); 
	drawElectRailArea.on('drawstart', function(e){});
	drawElectRailArea.on('drawend', function(e){
		HthxMap.mouseBindEvent(map);
		sketch = e.feature;
		preDrawFeature = sketch;
		var geomSketch = sketch.getGeometry();		
		var bufferUtil = new HthxMap.BufferUtil();
		var data = [];
		var dataSet = [];
		if(drawType == "Circle"){
			elecRailflagCircleOrNot = true;
			var center = geomSketch.getCenter();
			var radius = geomSketch.getRadius();
			for(var i = 0; i < 2;) {
				var x = center[0] + radius * Math.cos(Math.PI * i);
				var y = center[1] + radius * Math.sin(Math.PI * i);
				var obj = {
				 		'longitude': JSON.parse(x),
				 		'latitude': JSON.parse(y)
				 		};
				data.push(obj);
				i += 1/32;
			}
			data.push(data[0]);
			/*将数据序列化*/
	    	$.each(data, function(index, set) {
				var obj = [parseFloat(set.longitude),parseFloat(set.latitude)];
				dataSet.push(obj);
			});
			pointSet = JSON.stringify(data); /*将经纬度转换为JSON格式--poin-set*/
			pointList = JSON.stringify(dataSet); /*将经纬度转换为JSON格式--poin-list*/
		}else{
			elecRailflagCircleOrNot = false;
			var electRailCoordinate;/*捕获图形的经纬度*/
			if(lineType == "LineString"){
				electRailCoordinate = geomSketch.getCoordinates();
			}else{
				electRailCoordinate = geomSketch.getCoordinates()[0];
			}
	        /*将捕获到的经纬度显示在页面表格中*/
	        /*首先将数据转换为json*/
			$.each(electRailCoordinate, function(index, coordinates) {
				var obj = {
			 		'longitude': JSON.parse(coordinates[0]),
			 		'latitude': JSON.parse(coordinates[1])
			 		};
				data.push(obj);
			});
			pointList = JSON.stringify(electRailCoordinate); /*将经纬度转换为JSON格式--poin-list*/
			pointSet = JSON.stringify(data); /*将经纬度转换为JSON格式--poin-set*/
		}
		/*页面中显示经纬度坐标列表*/
		userDefinedAreaFind.definedAreaShowPanel("#addUserDefinedArea-panel","新增自定义区域");
		//userDefinedAreaFind.saveFlag=true;
		
		map.once('pointermove',doubleClick);
	    map.removeInteraction(HthxMap.curMapInteraction);
	    setTimeout(function(){
			HthxMap.curFeatureInteraction = null;
		},300);
	    
	    //恢复控件未被激活的状态
		$("#" + typeId).removeClass(typeId + "Activate");
		$("#" + typeId).addClass(typeId + "Inactivate");
		HthxMap.curControl.target = "";
		HthxMap.curControl.displayClass = "";	    
	},this);
};

