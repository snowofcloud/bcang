/**
 * <p>Description: 限制区域--提交数据</p>
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
function queryCar(){
	//只是用多边形区域时
	var id = arguments[0].data.id;
	//var id = $(this).attr("id");
	var start = id.indexOf("b");
	var str = id.slice(0, start);
	var str1 = str.slice(0,1).toUpperCase();
	var drawType = str1 + str.slice(1);
	beginDraw(drawType);
};
//限制区域画区域
function beginDraw(drawType){
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
	
	addInteractions(drawType, typeId);
};

function addInteractions(drawType, typeId){	
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
		areaRestrictFind.restrictShowPanel("#addAreaRestrict-panel","新增限制区域");
		//areaRestrictFind.saveFlag=true;
		$("#reaRestrict-speed-place").removeClass("hidden");
		
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

/*提交新增限制区域到数据库*/
//function submitElecRailToDB() {
//	var name = $("#reaRestrict-name").val();
//	var text = $("#electronRailText").text();
//	/*验证围栏名称是否为空*/
//	if ("" === name) {
//		name.val("");
//		name.testRemind("您尚未输入"+ text);
//		name.focus();
//		return;
//	}
//	var data = {
//			"name": name,
//			"pointSet": pointSet,
//			"pointList": pointList,
//			"limitType":$("#reaRestrict-type").val(),
//			"limitSpeed":$("#reaRestrict-speed").val()
//			};
//	/*将限制区域数据传输到后台*/
//	$.ajax({
//        url: $.backPath + '/limitArea/limit',
//        type: "POST",
//        dataType: 'json',
//        data: data,
//        async: false,
//        success: function (json) {
//            if (json["code"] === 1) {
//        		clearElectRail();
//        		/*围栏为打开状态--填充围栏到地图中*/
//        		var tmpPoint = JSON.parse(pointList);
//        		var len = tmpPoint.length;
//        		var coor;
//        		if((data.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
//        			coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线       			
//        		}else{
//        			coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle(),json.data); //多边形
//        		}
//    			/*在地图中显示围栏名称*/
//    			createElecNameElement(name, coor, HthxMap.measureIndex, json.data);
//    			HthxMap.areaRestrictIndex[json.data] = HthxMap.measureIndex;
//    			HthxMap.areaRestrictRail[HthxMap.measureIndex] = json.data;    //用于区分标绘和测量
//     			HthxMap.measureIndex++;
//     			map.getView().setCenter(coor);
//    			/*清空缓存的坐标点集*/
//    			pointSet = null;
//    			pointList = null;
//    			$("#addAreaRestrict-panel").closePanelModel();
//    			//在地图中填充点
////    			electFenceRegular.showPanelAddRegular(json.data, name);
////                //清空围栏名称文本内容
////        		$("#electronRailName").val("");
////        		toolMethod.closePanel("#electronRailAdd", true);
////        		Message.show({Msg: json["msg"], isModal: false});
//            	return false;
//            } else if (json["code"] === 3) {
//            	Message.alert({Msg: json["msg"], iconImg: "warning", isModal: false});
//                return false;
//            } else {
//            	 Message.alert({Msg: json["msg"], iconImg: "error", isModal: false});
//                 return false;      
//            }
//        }
//    });
//};


//function showPanel(data){
////显示弹框 新增限制区域 
//$("#addAreaRestrict-panel").showPanelModel("新增限制区域");
////$("#electronRailNameForm").addClass("hidden");   	/*输入围栏提交表单*/
////$("#electronRailDrawForm").removeClass("hidden");  /*手绘围栏提交表单*/
////$("#floatInput").addClass("drawHidden"); /*浮点数输入框操作*/
////$(".drawHidden").css("display","none"); /*隐藏经纬度输入框*/
/////*判断页面中无表格标签时，添加相应表格标签元素*/
////if ($("#outDrawElectronRail").length === 0) {
////	$("#electronRailDrawForm").prepend("<div id='outDrawElectronRail' class='panelOverFlow' style='width:95%; margin:5px auto;'>"+
////        "<table id='drawElectronRailTabel'></table>"+
////        "<!-- 限制区域-新增经纬度  表格 翻页 -->"+
////        "<div id='drawElectronRailPage'></div>"+
////        "</div>");
////}
////$("#drawElectronRailTabel").jqGrid({
////	data:data,
////    datatype: "local",
////    colNames: ["北纬", "东经"],
////	colModel: [
////        {name: "latitude", index: "1", align: "center", width:"20px", sortable:false,
////        	formatter: function (cellvalue, options, rowObject) {
////        		return toolMethod.changeToDMS(rowObject["latitude"]);
////            }
////        },
////	    {name: "longitude", index: "2", align: "center", width:"20px", sortable:false,
////	    	formatter: function (cellvalue, options, rowObject) {
////	    		return toolMethod.changeToDMS(rowObject["longitude"]);
////            }
////        }
////	    
////	],
////    loadonce: false,
////    viewrecords: true,
////    autowidth: true,
////    height: 300,
////    rownumbers: true,
////    multiselect: false,
////    multiboxonly: false,
////    rowNum: 10, // 每页显示记录数
////    rowList: [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
////    pager: "#drawElectronRailPage"
////}); 
//};
