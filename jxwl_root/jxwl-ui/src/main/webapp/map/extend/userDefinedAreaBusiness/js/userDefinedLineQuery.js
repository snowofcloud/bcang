/**
 * <p>Description: 自定义线条--提交数据</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-08-01 </p>
 * @author: wl
 * @extends：
 * @param:
 */

/*保存画图中限制线条坐标点集*/
var pointSet = null;
var pointList = null;
var startLat = null;
var startLng = null;
var endLat = null;
var endLng = null;
var elecRailflagCircleOrNot = false; /*true:为圆，false: 非圆*/
var preDrawFeature;
$(function() {
//	$("#save-enterpriseRight-form").html5Validate(function (e) {
//		submitElecRailToDB();
//        return false;
//	});
});

//限制线条线条入口
function queryUserDefinedLine(){
	//只是用多边形线条时
	var id = arguments[0].data.id;
	//var id = $(this).attr("id");
	var start = id.indexOf("b");
	var str = id.slice(0, start);
	var str1 = str.slice(0,1).toUpperCase();
	var drawType = str1 + str.slice(1);
	beginDrawDefinedLine(drawType);
};
//限制线条画线条
function beginDrawDefinedLine(drawType){
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
	
	addInteractionsDefinedLine(drawType, typeId);
};

function addInteractionsDefinedLine(drawType, typeId){	
	if(!HthxMap.curFeatureInteraction){
		HthxMap.curFeatureInteraction = "active";
	}
	var layers = map.getLayers();
	var featureLayer =  HthxMap.getLayerById("featureLayer");
	var MonitorLayer =  HthxMap.getLayerById("monitorLayer");
	//画自定义线条时清除所有车辆图标
	MonitorLayer.getSource().clear();
	isUserDefinedLineDrawing = true;
    var geometryFunction, maxPoints;
	var boxType = '';
	var searchRadius;
    if('Box' === drawType){
		drawType = 'LineString';
		boxType = "Line";
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
    var drawElectRailLine = new ol.interaction.Draw({
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
    
    HthxMap.curMapInteraction = drawElectRailLine;
	map.addInteraction(drawElectRailLine);
	/*设置鼠标图标样式*/
	HthxMap.mouseUnbindEvent(map, "crosshair"); 
	drawElectRailLine.on('drawstart', function(e){});
	drawElectRailLine.on('drawend', function(e){
		HthxMap.mouseBindEvent(map);
		sketch = e.feature;
		preDrawFeature = sketch;
		var geomSketch = sketch.getGeometry();		
		var bufferUtil = new HthxMap.BufferUtil();
		var data = [];
		var dataSet = [];
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
		/*起始点和终点经纬度*/
		var length = electRailCoordinate.length - 1;
		startLng = electRailCoordinate[0][0];
		startLat = electRailCoordinate[0][1];
		endLng = electRailCoordinate[length][0];
		endLat = electRailCoordinate[length][1];
		
		/*页面中显示经纬度坐标列表*/
		userDefinedLineFind.definedLineShowPanel("#addUserDefinedLine-panel","新增自定义线条");
		//userDefinedLineFind.saveFlag=true;
		
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

