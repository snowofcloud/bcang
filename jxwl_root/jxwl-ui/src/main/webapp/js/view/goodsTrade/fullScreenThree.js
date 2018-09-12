$(function(){
	var _init = function(){
		if(location.pathname == '/JXWL/view/goodsTrade/fullScreenThree.html'){
			$(".button_top > a:nth(2)").addClass("buttonActive");
		}
	};
	var timerFull = window.setTimeout(function(){
		location.pathname = '/JXWL/view/goodsTrade/fullScreenFour.html';
	},90000);
	_init();
});
//海图范围
var extentTemp; 
$(document).ready(function() {
	//初始化加载固定地图宽和高，不让它受流式布局影响
	var width  = $('.container-fluid').width()+15;
	var header = $(".header").height();
	var nav    = $("nav").height();
	var height = $(window).height()-header-35;
	$("#map").css({"width":width+"px","height":height+"px"});
	$(window).resize(function() {
		var width  = $('.container-fluid').width();
		var header = $(".header").height();
		var nav    = $("nav").height();
		var height = $(window).height();
		$("#map").css({"width":width+"px","height":height+"px"});
	});
	//取消右键默认弹出框
	$(window).bind('contextmenu', function(){
	});
	//定义2439坐标系
    var str ="+title=Beijing1954"; 
            str +=" +proj=tmerc";//投影类型 
            str +=" +a=6356863.018773047";//椭球长半轴 
            str +=" +rf=298.3";//扁率 
            str +=" +lat_0=43.948000";//投影原点纬线 
            str +=" +lon_0=129.265346";//投影中央经线 
            //str +=" +lat_1=24";//第一标准纬线 
            //str +=" +lat_2=40";//第二标准纬线 
            str +=" +x_0=500000.000";//水平偏移量 
            str +=" +y_0=0.000";//垂直偏移量 
            str +=" +units=m";//投影坐标系单位 

	
	proj4.defs("EPSG:2439", str);
	var initialView = new ol.View(HthxMap.Settings.initialView);

    map = new HthxMap.Map({
        target: 'map',
        view: initialView
    });
    var fullscreen = new ol.control.FullScreen();
    map.addControl(fullscreen);
    //创建一个矢量图层，用于绘制要素
    var stroke = new ol.style.Stroke({color: '#0061D9', width:1.1});
    var fill = new ol.style.Fill({color: 'rgba(0,97,217,0.2)'});
	var source = new ol.source.Vector({wrapX: false});
	var featureLayer = new ol.layer.Vector({
		source:source,
		style:new ol.style.Style({
			fill:fill,
			stroke:stroke
		})
	});
	featureLayer.setProperties({id: "featureLayer"});
    map.addLayer(featureLayer);
	//创建矢量图层，用于显示车辆信息
	monitLayer = new ol.layer.Vector({
		source: new ol.source.Vector(),
		style:new ol.style.Style({
			fill:fill,
			stroke:stroke
		})
	});
	monitLayer.setProperties({id:"monitorLayer"});
	map.addLayer(monitLayer);
	//鼠标小手样式
	HthxMap.mouseBindEvent(map);	
	
	/**	地图权限配置
	 * 		 ---配置方法：
	 * 				在门户系统中：1、功能管理：添加想要的模块权限；（记录功能标示符：例如 aaa）
	 *							2、模块管理：在车辆监控中加入添加好的功能模块；
	 *							3、权限管理：对应需求选择名称后，将车辆监控中的模块勾选上；
	 *				前端业务代码中：调用$.mapInitPrivg方法将功能标识符添加即可； $.mapInitPrivg("getRtLocation","getRealValue","aaa");
	 * */
	var getMapPrivg = $.mapInitPrivg("getRtLocation","getRealValue");
	if (getMapPrivg.getRtLocation) {
		//车辆显示
		initCarInfor();
	}
	/*******************临时代码************************/
	
	//TODO 左侧导航栏下车辆数据
	initLeftCar();	
});
function projTransform(pointsArray) {
	var points = [];
	pointsArray.map(function (point) {
		points.push(ol.proj.transform(point, "EPSG:4326", HthxMap.Settings.projection));
	});
	return points;
}

function initCarInfor() {
	//初始化绑定事件
	HthxMap.mousePointer(map, true);
	HthxMap.mouseEvent(map, "singleclick", "monitorLayer", boatDiv);
	//webSocket请求实时位置
	if (window.WebSocket) {
		//初始化查询所有车辆
		initAllCarInfor();
		webscoketCarInfor();
	} else {
		//首次加载时请求
		initAllCarInfor();
		//定时请求车辆上下线情况、车辆报警情况
		setInterval( function() {
			carOnlinStatus.getInfor();	
		}, 5000);
		//定时请求车辆信息
		timeoutUpdate = setInterval(function() {
			initAllCarInfor();
		}, 15000);
	}
}

//初始化获取业务数据库中所有的车辆
var initAllCarInfor = function(){
	if(this instanceof initAllCarInfor){
		// 获取当前区域坐标
		var mapView = map.getView().calculateExtent(map.getSize());
		var minPoint = ol.proj.transform([mapView[0],mapView[1]],  HthxMap.Settings.projection,"EPSG:4326").join(":");
		var maxPoint = ol.proj.transform([mapView[2],mapView[3]], HthxMap.Settings.projection, "EPSG:4326").join(":");
		var inputData = minPoint+","+maxPoint;
		mapUrl = HthxMap.Settings.rest + "/dangerVehicle/findRealLocationAll";
		$.ajax({
		   type: "POST",
		   url: mapUrl,
		   //data: {"points": inputData},
		   success: function(objList){
			  if(objList['code'] === 1){
				  initMonitLayer(map, monitLayer, objList.data);
			  }
		   }
	    }); 
	}else{
		return new initAllCarInfor();
	}
};


/**
 * webSocket接受服务器端推车辆送消息
 */
var webscoketCarInfor = function() {
	if(this instanceof webscoketCarInfor){
		var ws = HthxMap.Settings.webSocketURL+'/JXWL/monitor.ws';
		var webscoket = new WebSocket(ws);
		webscoket.binaryType = "arraybuffer";
		webscoket.onopen  = function(evt){};
		webscoket.onclose = function(evt){};
		webscoket.onerror = function(evt){};
		//客户端在收到消息时便会触发onmessage方法
		webscoket.onmessage = function(evt) {
			var json = JSON.parse(evt.data);
			var data = json.data;
			if (json.type == "location") {
				saveMapCarInfor(map, data, monitLayer);
			} else if ( json.type == "alarm") {
				
			} else if ( json.type =="on"){
				var data = json.data;
				initMonitLayer(map,monitLayer,data);
		         //调用topMenu.js的车辆数据计算函数
		         topMenuMag.carAmountFunc(0,data.length,-data.length);
			}else if(json.type == "off") {
				 //删除下线车辆图标
	            removeMonitLayer(map,monitLayer,json.data);
	            //调用topMenu.js的车辆数据计算函数
	            topMenuMag.carAmountFunc(0,-json.data.length,json.data.length);
			}
		};
	} else {
		return new webscoketCarInfor();
	}
};

var removeMonitLayer = function(map,layer,datas){
	var source = layer.getSource();
	if( datas instanceof Array){//如果传入的是数组
		for(var i=0;i<datas.length;i++){
			var data = datas[i];
			var feature = source.getFeatureById(data.carrierName);
			source.removeFeature(feature);
		}
	}else{//一个对象
		var feature = source.getFeatureById(datas.carrierName);
		source.removeFeature(feature);
	}
};

//移除绘制要素,关闭搜索标签时调用
function remVectorDrawedFeature(){
	var featureLayer = HthxMap.getLayerById("featureLayer");
	var src = featureLayer.getSource();
	if(featureLayer){		
		for(var i = 0; i < HthxMap.shipBufferQueryFeature.length; i++){
			var index = HthxMap.shipBufferQueryFeature[i].getId();
	    	HthxMap.clearMeasure(index);
		}
	}
	
	//删除渔船周围搜索要素
	var aroundSrchf = src.getFeatureById("shipAroundSearchF");
	if(aroundSrchf){
		src.removeFeature(aroundSrchf);   
	}else{
		HthxMap.removeShipStyle();
		if($("#popUp")){     //删除弹出框
	    }
	}
	if(document.getElementsByClassName("shipAroundSearch") || document.getElementById("popUp")){
		HthxMap.removeOverlay(map);
		$(".shipAroundSearch").remove();    //删除中心点标记	  
	}
}

/************************************初始化显示区域限制在地图中******************************************************/
function initRestrictOpen(){
	var restrictMsg = null;
	$.ajax({
	        url:  $.backPath  + "/limitArea/findLimitArea",
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        success: function (json) {
	     	   if (json["code"] === 1) {
	     		  restrictMsg = json.data;
	     		/* 一条一条填充电子围栏*/
	     	    	$.each(restrictMsg, function(index, electSet) {
	     	    			if(electSet.pointList != "[]"){
	     	    				/*围栏为打开状态--填充围栏到地图中*/
	     	    				var tmpPoint = JSON.parse(electSet.points);
	     	           		    var len = tmpPoint.length;
	     	           		    var coor;
	     	           		    if((electSet.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
	     	           		        coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
	     	           		    }else{
	     	           		        coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle()); //多边形
	     	           		    }
				     			createElecNameElement(electSet.limitName, coor, HthxMap.measureIndex, electSet.id);
				     			HthxMap.areaRestrictIndex[electSet.id] = HthxMap.measureIndex;
				     			HthxMap.areaRestrictRail[HthxMap.measureIndex] = electSet.id;    //用于区分标绘和测量
				     			HthxMap.measureIndex++;
	     	    			}
	     			});
	            } else {
	            	Message.show({Msg: "读取限制区域信息出错", isModal: false});
    	    		return false;
	            }
	        }
	});
};

/**
 * 地图围栏中添加围栏名称
 */
function createElecNameElement(name,coor,index, id) {
	var deleteElement = document.createElement('div');
	var strs = '<div><label class="restrictDetail" onclick="areaRestrictFind.showDetail(\''+id+'\')" id="'+index+'">'+name+'</label>'+
				'<div class="restrictName openRestrictFlag" id="'+id+'" onclick="areaRestrictFind.restrictDelete('+ "'" + id +"'" + ')">x</div></div>';	
	deleteElement.innerHTML = strs;
    var deleteE = new ol.Overlay({
	    element: deleteElement,
	    offset: [0, 0],
	    position: coor,
	    positioning: 'center-center'
    });
    deleteE.setProperties({id: index});
    map.addOverlay(deleteE);
};


/************************************分页信息***************************************************/
/**
 * 下拉设置函数
 * @param addPageSelect 添加下拉设置每页显示几条
 */
function addPageSelect() {	
	var pagination = '<ul class="pagination" id="allShip_1Page">'
        + '</ul> '
        + '<input type="hidden" value="7" id="selectPage">';
    $("#fishQueryPage").html(pagination);
    
    var optionSV = $("#selectPage").val();
    return optionSV;
}

/**
 * 下拉设置函数
 * @param selectPageSizeChange 添加下拉菜单改变设置每页显示几条
 */
function selectPageSizeChange() {	
		pagesizeS = $("#selectPage option:selected").val();
		var name = $(".inquireItem-content").html();
		switch (name){
		    case HthxMap.commonConstant.offLine:
		    case HthxMap.commonConstant.onLine: queryShipByOnlineStatus(1, pagesizeS);
		        break;
		    case HthxMap.commonConstant.shipName:
		    case HthxMap.commonConstant.advanceSearch: queryShip(1,pagesizeS);
		        break;
		    case HthxMap.commonConstant.alarm: queryShipByAlarm(1,pagesizeS);
		        break;
		}    
}


/*********************************临时代码************************************************/

function initLeftCar(){
	//车辆数据显示字符串
	var carStr = "";
	//模拟数据
	var data = [{"businessName":"111","car":[{"name":"car1","online":"1"},{"name":"car2","online":"1"}]},
	            {"businessName":"112","car":[{"name":"car1","online":"1"},{"name":"car2","online":"1"}]},
	            {"businessName":"113","car":[{"name":"car1","online":"1"},{"name":"car2","online":"1"}]},
	            {"businessName":"114","car":[{"name":"car1","online":"1"},{"name":"car2","online":"1"}]},
	            {"businessName":"115","car":[{"name":"car1","online":"1"},{"name":"car2","online":"1"}]}];
	var dataLen = data.length;
	for(var i = 0; i<dataLen;i++){
		var dataNow = data[i].car;
		var carLen = dataNow.length;
		carStr += '<li id="carLi'+i+'">'+
        '<span class="mycursor" id="carBtn'+i+'" onclick="loadCar(this)">+</span><span id="businesssName'+i+'" onclick="loadCar(this)">'+data[i].businessName+'</span>';
		for(var j = 0; j < carLen; j++){
			carStr += '<div class="hidden" onclick="loadMapCar(this)">'+dataNow[j].name+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+dataNow[j].online+'</div>';
		}
		carStr += '</li>';
	}
	$(".mycursor").css("cursor","pointer");
    
    $($("a:contains('车辆监控')").parent().attr("data-target")).find("ul").append(carStr);
	
    $(".aaa").click(function(){
    	var featureid="黑A12345";
    	var e = event|| window.event;
		var feature = monitLayer.getSource().getFeatureById(featureid);
		if(feature){
			var style = feature.getStyle();
			if(style){
				feature.setStyle(featureStyles[featureid]);
				HthxMap.clickEvent(feature);
				HthxMap.popDiv.createClickPop(feature,boatDiv);
				HthxMap.preFeature = feature;
				var point = [feature.getProperties().longitude,feature.getProperties().latitude];
				map.getView().setCenter(point);
			}
		}
    });
}

function loadCar(car){
	var carNum = car.id.substring(6);
	var $carLi = $("#carLi"+carNum+"");
	var carDiv =  $carLi.find("div");
	if(carDiv.hasClass("hidden")){
		$carLi.find("div[class=hidden]").removeClass("hidden");
		$carLi.css("background-color","#0F4576");
		$("#carBtn"+carNum+"").text("-");
		$("#businesssName"+carNum+"").css("color","#fff");
		$("#carBtn"+carNum+"").css("padding-left","");
	}else{
		$("#carLi"+carNum+"").css("background-color","");
		carDiv.addClass("hidden");
		$("#carBtn"+carNum+"").text("+");
		$("#businesssName"+carNum+"").css("color","#92957F");
		$("#carBtn"+carNum+"").css("padding-left","4px");
	}
	
}

function loadMapCar(car){
	var plate = $car.text();
}