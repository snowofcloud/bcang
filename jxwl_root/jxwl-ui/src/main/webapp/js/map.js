/**
 * 初始化地图功能测试数据
 */
var timeoutUpdate;
var alarmOverlay = [];  // 报警层
var drawShip = [];    // 海图中需要渲染的车辆
var displayControlObjs = {}; // 显示控制对象
var mapLevelFlag = -1; // true代表大比例尺，false代表小比例尺
var mapLevelZoom = -1; // 前一个地图缩放级别
var trackRecords = [];  // 车辆跟踪记录
var featureStyles = [];  // 保存显示控制的车辆样式
var initUpdate = true;  // 绑定第一次加载执行
var extentTemp; // 海图范围
var areaCodeArr = [];   // 地区码和地区名称的hash数组
var isUserDefinedLineDrawing = false;

$(document).ready(function() {
	// 初始化加载固定地图宽和高，不让它受流式布局影响
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
	// 取消右键默认弹出框
	$(window).bind('contextmenu', function(){
	});
	
	var initialView = new ol.View(HthxMap.Settings.initialView);

    map = new HthxMap.Map({
        target: 'map',
        view: initialView
    });
    
    // 创建一个矢量图层，用于绘制要素
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
	// 创建矢量图层，用于显示车辆信息
	monitLayer = new ol.layer.Vector({
		source: new ol.source.Vector(),
		style:new ol.style.Style({
			fill:fill,
			stroke:stroke
		})
	});
	monitLayer.setProperties({id:"monitorLayer"});
	//创建一个矢量图层用于让选中车辆浮现在最顶层
	/*
	 * create by limingjun 
	 * create time 2017/7/10
	 */
	topMonitorLayer = new ol.layer.Vector({
		source: new ol.source.Vector(),
		style:new ol.style.Style({
			fill:fill,
			stroke:stroke
		})
	});
	topMonitorLayer.setProperties({id:"topMonitorLayer"});
	topMonitorLayer.setZIndex(90);
	map.addLayer(topMonitorLayer);
	map.addLayer(featureLayer);
	map.addLayer(monitLayer);
	// 鼠标小手样式
	HthxMap.mouseBindEvent(map);
	
	// 区域限制
    var areaRestrict = new HthxMap.AreaRestrict({
    	displayClass: 'areaRestrict',
    	target: 'areaRestrict',
    	map:map
    });
    
   // 自定义区域
    var userDefinedArea = new HthxMap.UserDefinedArea({
    	displayClass: 'userDefinedArea',
    	target: 'userDefinedArea',
    	map:map
    });
    
    // 自定义线条
    var userDefinedLine = new HthxMap.UserDefinedLine({
    	displayClass: 'userDefinedLine',
    	target: 'userDefinedLine',
    	map:map
    });
    
  // 自定义设施
    var userDefinedFacility = new HthxMap.UserDefinedFacility({
    	displayClass: 'userDefinedFacility',
    	target: 'userDefinedFacility',
    	map:map
    });
    
    var mapChange = new HthxMap.MapChange({
    	displayClass: 'mapChange',
    	target: 'mapChange',
    	map:map
    });
 // map.addControl(areaRestrict);
	
	// 区域查船
    var bufferSearch = new HthxMap.BufferSearch({
    	displayClass: 'bufferSearch',
    	target: 'bufferSearch',
    	map:map
    });
  // map.addControl(bufferSearch);
	// 平移
    var pan = new HthxMap.Pan({
    	displayClass:'pan',
    	target:'pan',
    	map:map
    });
 // map.addControl(pan);
    // 放大
    var zoomIn = new HthxMap.ZoomBox({
    	displayClass:'zoomIn',
    	target:'zoomIn',
    	map:map
    });
  // map.addControl(zoomIn);
    // 缩小
    var zoomOut = new HthxMap.ZoomBox({
    	displayClass:'zoomOut',
    	target:'zoomOut',
    	map:map,
    	out:true
    });
    // map.addControl(zoomOut);
    // 复位
    var mapReview = new HthxMap.MapReset({
    	displayClass:'mapReview',
    	target:'mapReview',
    	map:map,
    	// 这里不能直接传view作为参数，因为传进去的view和地图view指向的是同一个对象，当地图改变时view也会改变，获得的就不是初始时的view,
    	// 所以传入的是初始化的中心点和缩放级别
    	zoom: map.getView().getZoom(),
    	center: map.getView().getCenter()
    });
    map.addControl(mapReview);
    // 标绘
    var draw = new HthxMap.Draw({
    	displayClass:'draw',
    	target:'draw',
    	map:map
    });

    // 清除
	var clear = new HthxMap.ClearVector({
    	target:'clearVector',
    	displayClass:'clearVector',
    	map: map,
		layers:[featureLayer]                   // 要清除的图层
	});
// map.addControl(clear);
	/**
	 * 地图权限配置 ---配置方法： 在门户系统中：1、功能管理：添加想要的模块权限；（记录功能标示符：例如 aaa）
	 * 2、模块管理：在车辆监控中加入添加好的功能模块； 3、权限管理：对应需求选择名称后，将车辆监控中的模块勾选上；
	 * 前端业务代码中：调用$.mapInitPrivg方法将功能标识符添加即可；
	 * $.mapInitPrivg("getRtLocation","getRealValue","aaa");
	 */
	
	getMapPrivg = $.mapInitPrivg("getRtLocation","getRealValue","getCarAlarm","getCarOnoff","findPipeLocations");
	alarmPrivg = getMapPrivg.getCarAlarm;
	onoffPrivg = getMapPrivg.getCarOnoff;
	// 初始化管道、储罐信息
	if (getMapPrivg.getRtLocation) {
		initPipeAndTank();
		// 判别是否是管道或储罐监控跳转过来
		var len = location.href.split('?').length;
		if( len > 1){
			// 定位
			id = location.href.split('?')[1].split("=")[1];
			position(id);
		}
		
	}
	// 初始化车辆信息
	if (getMapPrivg.getRtLocation) {
		// 车辆显示
		initCarInfor();
	}
	
	// 初始化限制区域信息
	if (getMapPrivg.getRealValue) {
		// 显示或隐藏---添加限制区域按钮区域按钮
		map.addControl(areaRestrict);
		// 显示或隐藏---添加自定义区域按钮区域按钮
		map.addControl(userDefinedArea);
		// 显示或隐藏---添加自定义设施按钮区域按钮
		map.addControl(userDefinedFacility);
		// 显示或隐藏---添加自定义线条按钮区域按钮
		map.addControl(userDefinedLine);
		// 显示或隐藏---限制区域按钮区域按钮
		map.addControl(clear);
		// 初始化限制区域
		initRestrictOpen();
		// 初始化线条区域
		initDefinedLineOpen();
		// 初始化自定义区域
		initDefinedAreaOpen();
		// 初始化设施区域
		initDefinedFacilityOpen();
		// 封闭园区的范围
		initDefinedClosetype();
		// 封闭乍浦边界的范围
		initDefinedClosetype2();
	}
	// 添加收缩按钮
	
	// 左侧导航栏下车辆数据
	initLeftCar();
	
	//historyTrack.init("渝F00002");
	
});



function projTransform(pointsArray) {
	var points = [];
	pointsArray.map(function (point) {
		points.push(ol.proj.transform(point, "EPSG:4326", HthxMap.Settings.projection));
	});
	return points;
}

function initCarInfor() {
	// 初始化绑定事件
	HthxMap.mousePointer(map,true);
	HthxMap.mouseEvent(map,"singleclick", "monitorLayer", boatDiv);
	
	// webSocket请求实时位置
/*	if (window.WebSocket) {
		// 初始化查询所有车辆
		initAllCarInfor();
	    webscoketCarInfor();
		
	} else {*/
		// 首次加载时请求
		initAllCarInfor(map, monitLayer);
		// 定时请求车辆上下线情况、车辆报警情况
		//定时判断车辆离线
		setInterval( function() {
			updateCarOffIE9();
		}, 5000);

		//定时请求车辆上线
		/*setInterval( function() {
			$.ajax({
				   type: "POST",
				   url: HthxMap.Settings.rest + "/carOnline/getCarOnline4IE",
				   async:false,
				   data: {},
				   complete: function(json){
					  if(json['code'] === 1){
						  json['type'] =  'on';
						  carOnlinStatus.getInfor(json,onoffPrivg);
					  }
				   }
			    }); 
		}, 5000);
		//定时请求车辆下线
		setInterval( function() {
			$.ajax({
				   type: "POST",
				   url: HthxMap.Settings.rest + "/carOnline/getCarOffline4IE",
				   async:false,
				   data: {},
				   complete: function(json){
					  if(json['code'] === 1){
						  json['type'] =  'off';
						  carOnlinStatus.getInfor(json,onoffPrivg);
					  }
				   }
			    }); 
		}, 5000);*/
		
		// 定时请求车辆信息
		timeoutUpdate = setInterval(function() {
			updateAllCarInfor4IE9();
		}, 5000);
		
		setInterval( function() {
			//每隔五秒请求一次报警
			updateAlarmIE9();
		}, 5000);
		
/*	}*/
};

//下线判断
var updateCarOffIE9 = function() {
	var source = monitLayer.getSource();
	var features = source.getFeatures();
	var nowTime = new Date().getTime();
	var feature;
	var gpsTime;
	var carrierNameNow;
	var properties;
	var enterpriseName;
	var $offlineHtml = $("#offlineHtml");
	var carOffNum = 0;//车辆下线数量
	for(var i in features){
		feature = features[i];
		properties = feature.getProperties();
		gpsTime = properties.gpstime;
		carrierNameNow = properties.id;
		enterpriseName = properties.enterpriseName;
		//判断当前图层中每个车辆的gpsTime和当前时间的差是否大于30分，如果大于30分，则判断为下线
		if((nowTime - gpsTime)>1800000){
			//判断为车辆下线，然后给出弹框提醒
			carOffNum += 1;//车辆下线数量+1
			var temp = '';
			var offline = sessionStorage.offline;
			if(onoffPrivg){
				temp =	temp+'<div class="row">'+
				            '车牌号:'+
				            carrierNameNow+
			                '</div>'+
			                '<div class="row">'+
				            '所属物流企业:'+
				            enterpriseName+
			                '</div>';
				$offlineHtml.html("");
				$offlineHtml.append(temp);
				if(offline == "notRemind"){
					$("#offlineStatus").css("z-index", "3010").slideDown(2000).delay(5000).slideUp(2000);
				}
			}
			 //删除下线车辆图标
			source.removeFeature(feature);
            //调用topMenu.js的车辆数据计算函数
            topMenuMag.carAmountFunc(0,-1,1);
            //如果下线的车辆的 打开了资料卡
            var carrierName = $("#carInforDetail-Panel").attr("data-type");
            if(carrierName){
	            for(var i=0,len=datas.length;i<len;i++){
	            	if( carrierName === carrierNameNow){
	            		removeClickPop();
	            		break;
	            	}
	            }
            }
			
		}
	}
	if(carOffNum > 0){
		updateLeftMenu();
	}
};

//请求5秒以内的报警信息
var updateAlarmIE9 = function() {
	var $carAlarmStatusForm = $("#carAlarmStatus-form");
	$.ajax({
		url:HthxMap.Settings.rest + "/alarmManage/updateAlarmIE9",
		type:"GET",
		success:function(json){
			 if(json.code == 1){
				 var data = json.data;
				 for(var i in data){
					 if(alarmPrivg){
						$("#carAlarmMintor").attr("data", data[i].carrierName);
						$("#carAlarmStatus").css("z-index", "2990").slideDown(2000).delay(5000).slideUp(2000);
						$carAlarmStatusForm.setFormSingleObj(data[i]);
//						changeToAlarmImg(data[i].carrierName);
						//如果此时有6000条报警信息，则调用showBtn方法就会发出6000条
						//“dangerVehicle/findByCarrierName”请求到后台！导致页面直接卡死
						//showBtn(data[i].carrierName);
					 }
				 }
			 }
		}
	});
};

//此方法就是渲染右下角报警弹框里面是否显示‘监控’按钮，如果status为0，表示车辆在线，则显示监控按钮，
//status为1，表示车辆不在线，则不显示监控按钮
//var showBtn = function(carrierName){
//	var status;
//	$.ajax({
//	   type: "POST",
//	   url: HthxMap.Settings.rest+"/dangerVehicle/findByCarrierName",
//	   async:false,
//	   data: {"carrierName": carrierName},
//	   success: function(json){
//		  if(json['code'] === 1){
//			  status =  json.data;
//		  }
//	   }
//    }); 
//	var carAlarmMintor =$("#carAlarmMintor");
//	if( status === "0"){
//		carAlarmMintor.removeClass("hidden");
//	}else{
//		carAlarmMintor.addClass("hidden");
//	}
//};

var getCarOnlineStatus = function(){
	var carrierName = $("#carAlarmStatus-form span[name=carrierName]").text();
	$.ajax({
	   type: "POST",
	   url: HthxMap.Settings.rest+"/dangerVehicle/findByCarrierName",
	   async:false,
	   data: {"carrierName": carrierName},
	   success: function(json){
		  if(json['code'] === 1){
			  status =  json.data.status;
			  if(status != "0"){
//				  alert("当前车辆不在线！");
				  Message.alert({
						Msg : "当前车辆不在线！",
						isModal : false
					});
			  }
		  }
	   }
    });
};

// 初始化获取业务数据库中所有的车辆
var initAllCarInfor = function(){
	if(this instanceof initAllCarInfor){
		mapUrl = HthxMap.Settings.rest + "/dangerVehicle/findRealLocationAll";
		$.ajax({
		   type: "POST",
		   url: mapUrl,
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


//IE9下实时请求和更新车辆位置
var updateAllCarInfor4IE9 = function(){
	var feature;
	var features = [];
	var carrierName;
	var featuresLen;
	mapUrl = HthxMap.Settings.rest + "/dangerVehicle/findRealLocationAll";
	$.ajax({
	   type: "POST",
	   url: mapUrl,
	   success: function(objList){
		  if(objList['code'] === 1){
			  var data = objList.data;
			  var len = data.length;
			  for(var i = 0; i < len; i++){
				  carrierName = data[i].carrierName;
				  feature = monitLayer.getSource().getFeatureById(carrierName);
				  nowTime = new Date().getTime();
				  gpsTime = data[i].gpstime;
				  if( (nowTime - gpsTime) > 1800000){
					  //如果当前时间大于gpsTime且相差大于30分钟，则车辆下线，不显示在地图上
					  continue;
				  }
				  saveMapCarInfor(map, data[i], monitLayer);
				  //如果当前车辆图层找不到该车辆，则判断为上线，然后给出一个上线提醒
				  if(!feature && (nowTime - gpsTime) < 1800000){
					  features.push(data[i]);//features专门存放所有判断为上线的车辆
				  }
			  }
			  if(onoffPrivg){//有上下线弹框提示的权限
				  	 var onlineIndex = 0;
					 var timer = window.setInterval(function(){//开启定时器
						 //车辆上线弹框
						 onlineIndex = carOnlinStatus.onlineNotify(features, features.length, 
								 timer, onlineIndex);
					 },100);
			  }else{
					 initMonitLayer(map,monitLayer,features);
			         //调用topMenu.js的车辆数据计算函数
			         topMenuMag.carAmountFunc(0,features.length,-features.length);
			  }
			  featuresLen = features.length;//上线车辆数量
			  if(featuresLen>0){
				  updateLeftMenu();
			  }
		  }
	   }
    }); 
};

//更新 左侧菜单车辆列表的车辆状态  findIndex initLeftCar open在 map.js中
var updateLeftMenu = function(){
	var index = findIndex();
	initLeftCar();
	open(index);
};

/**
 * webSocket接受服务器端推车辆送消息
 * 
 * @param getMapPrivg
 *            权限
 */
var webscoketCarInfor = function(getMapPrivg) {
	if(this instanceof webscoketCarInfor){
		var ws = HthxMap.Settings.webSocketURL+'/JXWL/monitor.ws';
		var webscoket = new WebSocket(ws);
		webscoket.binaryType = "arraybuffer";
		webscoket.onopen  = function(evt){};
		webscoket.onclose = function(evt){};
		webscoket.onerror = function(evt){};
		// 客户端在收到消息时便会触发onmessage方法
		webscoket.onmessage = function(evt) {
			var json = JSON.parse(evt.data);
			var data = json.data;
			if (json.type == "location") {
				if(!isUserDefinedLineDrawing){
					saveMapCarInfor(map,data, monitLayer);
				}
				if(HthxMap.followingCar){
					var feature = monitLayer.getSource().getFeatureById(HthxMap.followingCar);
					map.getView().setCenter([feature.getProperties().longitude, feature.getProperties().latitude]);
					drawPoint(feature,monitLayer);
					//updatePopupPosition(HthxMap.followingCar,monitLayer);
				}
			} else if ( json.type == "alarm" && alarmPrivg) {
				carOnlinStatus.getInfor(json,alarmPrivg);
			} else if ( json.type =="on" || (json.type == "off")) {
				carOnlinStatus.getInfor(json,onoffPrivg);
			} else if ( json.type =="loginState"){
				carOnlinStatus.updateLoginState(json);
			} else if ( json.type =="alarmDealStatus"){
				alarmHandle(json);
			} else if ( json.type =="logRemind"){
				logRemind(json);
			} 
		};
	} else {
		return new webscoketCarInfor();
	}
};




// 移除绘制要素,关闭搜索标签时调用
function remVectorDrawedFeature(){
	var featureLayer = HthxMap.getLayerById("featureLayer");
	var src = featureLayer.getSource();
	if(featureLayer){		
		for(var i = 0; i < HthxMap.shipBufferQueryFeature.length; i++){
			var index = HthxMap.shipBufferQueryFeature[i].getId();
	    	HthxMap.clearMeasure(index);
		}
	}
	
	// 删除渔船周围搜索要素
	var aroundSrchf = src.getFeatureById("shipAroundSearchF");
	if(aroundSrchf){
		src.removeFeature(aroundSrchf);
	}else{
		HthxMap.removeShipStyle();
		if($("#popUp")){     // 删除弹出框
	    }
	}
	if(document.getElementsByClassName("shipAroundSearch") || document.getElementById("popUp")){
		HthxMap.removeOverlay(map);
		$(".shipAroundSearch").remove();    // 删除中心点标记
	}
}

/** **********************************初始化显示限制区域在地图中***************************************************** */
function initRestrictOpen(){
	var restrictMsg = null;
	$.ajax({
			//url添加时间和随机数后缀是为了解决ie9自动从缓存中读取数据导致刚刚添加的自定义区域无法显示在地图上，ie11和chrome不会从缓存中读取
	        url:  $.backPath  + "/limitArea/findLimitArea?r=" + new Date() + Math.random(1000),
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        success: function (json) {
	     	   if (json["code"] === 1) {
	     		  restrictMsg = json.data;
	     		 /* 一条一条填充电子围栏 */
	     	    	$.each(restrictMsg, function(index, electSet) {
	     	    			/* 围栏为打开状态--填充围栏到地图中 */
     	    				var tmpPoint = JSON.parse(electSet.points);
     	           		    var len = tmpPoint.length;
     	           		    var coor;
     	           		    if((electSet.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
     	           		        coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   // 线
     	           		    }else{
     	           		        coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle()); // 多边形
     	           		    }
			     			createElecNameElement(electSet.limitName, coor, HthxMap.measureIndex, electSet.id);
			     			HthxMap.areaRestrictIndex[electSet.id] = HthxMap.measureIndex;
			     			HthxMap.areaRestrictRail[HthxMap.measureIndex] = electSet.id;    // 用于区分标绘和测量
			     			HthxMap.measureIndex++;
	     			});
	            } else {
	            	Message.show({Msg: "读取限制区域信息出错", isModal: false});
    	    		return false;
	            }
	        }
	});
};

/** **********************************初始化显示标出封闭园区的范围在地图中***************************************************** */
function initDefinedClosetype(){
	var electArray = "[[121.0546213388443,30.62519431114197],[121.04601681232452,30.62117099761963],[121.03077918291092,30.61016857624054],[121.03774756193161,30.601446032524112],[121.04238241910936,30.603811740875244],[121.04627899825574,30.59346243739128],[121.05072945356369,30.593871474266052],[121.06165543198586,30.59740394353867],[121.0546213388443,30.62519431114197]]";
	var tmpPoint = JSON.parse(electArray);
    var len = tmpPoint.length;
    var coor;
    coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.userClosetypeStyle()); // 多边形
    createClosetypeDefined(null,coor,null,null);
};
/** **********************************初始化显示标出乍浦边界的范围在地图中***************************************************** */
function initDefinedClosetype2(){
	var electArray = "[[121.06687266379596,30.651108787860725],[121.0398168861866,30.64450747333467],[121.02661438286304,30.643125507049263],[121.0074689425528,30.624745198292658],[121.03775057941675,30.601443664636463],[121.05187106877567,30.574994401540607],[121.14868238568306,30.599017208442092],[121.15664787590505,30.633767279796302],[121.14858452696353,30.640080275188666],[121.13311920315027,30.642297731246803],[121.13317494280636,30.649251122376885],[121.1187918484211,30.652141040191054],[121.11742157489061,30.646036730613563],[121.09636321663857,30.639320081099868],[121.08511352911593,30.63823536853306],[121.07555078342558,30.634176515741274],[121.06998033821583,30.635974737815562],[121.06876362115146,30.646514163818214],[121.06687337625773,30.651109351019844]]";
	var tmpPoint = JSON.parse(electArray);
    var len = tmpPoint.length;
    var coor;
    coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.userClosetypeStyle2()); // 多边形
    createClosetypeDefined(null,coor,null,null);
};
/** **********************************初始化显示自定义区域在地图中***************************************************** */
function initDefinedAreaOpen(){
	var restrictMsg = null;
	$.ajax({
			//url添加时间和随机数后缀是为了解决ie9自动从缓存中读取数据导致刚刚添加的自定义区域无法显示在地图上，ie11和chrome不会从缓存中读取
	        url:  $.backPath  + "/userDefinedArea/findUserDefinedArea?r=" + new Date() + Math.random(1000),  
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        cache: false,
	        success: function (json) {
	     	   if (json["code"] === 1) {
	     		  restrictMsg = json.data;
	     		 /* 一条一条填充电子围栏 */
	     	    	$.each(restrictMsg, function(index, electSet) {
	     	    			/* 围栏为打开状态--填充围栏到地图中 */
     	    				var tmpPoint = JSON.parse(electSet.points);
     	           		    var len = tmpPoint.length;
     	           		    var coor;
     	           		    if((electSet.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
     	           		        coor = HthxMap.drawLineString([tmpPoint], creatStyle.userDefinedStyle());   // 线
     	           		    }else{
     	           		        coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.userDefinedStyle()); // 多边形
     	           		    }
     	           		    createElecNameUserDefined(electSet.areaName, coor, HthxMap.measureIndex, electSet.id);
			     			/*HthxMap.areaDefinedAreaIndex[electSet.id] = HthxMap.measureIndex;
			     			HthxMap.areaDefinedAreaRail[HthxMap.measureIndex] = electSet.id;    // 用于区分标绘和测量
*/				     			HthxMap.measureIndex++;
	     			});
	            } else {
	            	Message.show({Msg: "读取自定义区域信息出错", isModal: false});
    	    		return false;
	            }
	        }
	});
};

/** **********************************初始化显示自定义线条在地图中***************************************************** */
function initDefinedLineOpen(){
	var restrictMsg = null;
	$.ajax({
			//url添加时间和随机数后缀是为了解决ie9自动从缓存中读取数据导致刚刚添加的自定义区域无法显示在地图上，ie11和chrome不会从缓存中读取
	        url:  $.backPath  + "/userDefinedLine/findUserDefinedLine?r=" + new Date() + Math.random(1000),
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        success: function (json) {
	     	   if (json["code"] === 1) {
	     		  restrictMsg = json.data;
	     		 /* 一条一条填充电子围栏 */
	     	    	$.each(restrictMsg, function(index, electSet) {
	     	    			/* 围栏为打开状态--填充围栏到地图中 */
     	    				var tmpPoint = JSON.parse(electSet.points);
     	           		    var len = tmpPoint.length;
     	           		    var coor;
     	           		    coor = HthxMap.drawLineString([tmpPoint], creatStyle.userDefinedStyle());   // 线
     	           		    createElecNameUserLine(electSet.lineName, coor, HthxMap.measureIndex, electSet.id);
			     			/*HthxMap.areaDefinedLineIndex[electSet.id] = HthxMap.measureIndex;
			     			HthxMap.areaDefinedLineRail[HthxMap.measureIndex] = electSet.id;    // 用于区分标绘和测量
*/				     			HthxMap.measureIndex++;
	     			});
	            } else {
	            	Message.show({Msg: "读取自定义线条信息出错", isModal: false});
    	    		return false;
	            }
	        }
	});
};

/** **********************************初始化显示自定义设施在地图中***************************************************** */
function initDefinedFacilityOpen(){
	var restrictMsg = null;
	$.ajax({
			//url添加时间和随机数后缀是为了解决ie9自动从缓存中读取数据导致刚刚添加的自定义区域无法显示在地图上，ie11和chrome不会从缓存中读取
	        url:  $.backPath  + "/userDefinedFacility/findUserDefinedFacility?r=" + new Date() + Math.random(1000),
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        success: function (json) {
	     	   if (json["code"] === 1) {
	     		  restrictMsg = json.data;
	     		 /* 一条一条填充电子围栏 */
	     	    	$.each(restrictMsg,function(index,electSet) {
		     	    		/*标记图标*/
	 	    				var iconId = parseInt(electSet.iconId) + 1;
	 	    				/* 围栏为打开状态--填充围栏到地图中 */
	 	    				var coor = JSON.parse(electSet.points);
	 	           		    createElecNameUserFacility(electSet.facilityName,HthxMap.measureIndex, electSet.id,coor,iconId);
			     			//HthxMap.areaDefinedAreaIndex[electSet.id] = HthxMap.measureIndex;
			     			//HthxMap.areaDefinedAreaRail[HthxMap.measureIndex] = electSet.id;    // 用于区分标绘和测量
			     			HthxMap.measureIndex++;
	     			});
	            } else {
	            	Message.show({Msg: "读取自定义设施信息出错", isModal: false});
    	    		return false;
	            }
	        }
	});
};
/**
 * 地图围栏中添加围栏名称 自定义设施
 */
function createElecNameUserFacility(name,index,id,coor,iconId) {
	var deleteElement = document.createElement('div');
	/*var strs = '<div><label class="userDefinedFacility" style="color:red;" onclick="userDefinedFacilityFind.showDetail(\''+id+'\')" id="'+index+'"><img src="../map/base/img/faci_0'+iconId+'.png">'+name+'</label>'+
				'<div class="areaName openDefinedAreaFlag closered" style="color:red;position: absolute;top: -1px;right: -13px;" id="'+id+'" onclick="userDefinedFacilityFind.markerRemove('+ "'" + id +"'" + ')">x</div></div>';*/	
	var strs = '<div><label class="userDefinedFacility_'+id+'" style="color:red;" onclick="userDefinedFacilityFind.showDetail(\''+id+'\')" id="'+index+'"><img src="../map/base/img/faci_0'+iconId+'.png"><span>'+name+'</span></label></div>';
	deleteElement.innerHTML = strs;
	var len = name.length;
	var xStr = len - 2;
    var deleteE = new ol.Overlay({
	    element: deleteElement,
	    offset: [xStr, 19],
	    position:coor,
	    positioning: 'bottom-center'
    });
    deleteE.setProperties({id: id});
    map.addOverlay(deleteE);
};

/**
 * 地图封闭园区范围
 */
function createClosetypeDefined(name,coor,index, id) {
	var deleteElement = document.createElement('div');
	/*var strs = '<div><label class="definedAreaDetail" onclick="userDefinedAreaFind.showDetail(\''+id+'\')" id="'+index+'">'+name+'</label>'+
				'<div class="areaName openDefinedAreaFlag" id="'+id+'" onclick="userDefinedAreaFind.definedAreaDelete('+ "'" + id +"'" + ')">x</div></div>';	
	deleteElement.innerHTML = strs;*/
    var deleteE = new ol.Overlay({
	    element: deleteElement,
	    offset: [0, 0],
	    position: coor,
	    positioning: 'center-center'
    });
    deleteE.setProperties({id: index});
    map.addOverlay(deleteE);
};

/**
 * 地图围栏中添加围栏名称 自定义区域
 */
function createElecNameUserDefined(name,coor,index, id) {
	var deleteElement = document.createElement('div');
	var strs = '<div><label class="definedAreaDetail" onclick="userDefinedAreaFind.showDetail(\''+id+'\')" id="'+index+'">'+name+'</label>'+
				'<div class="areaName openDefinedAreaFlag" id="'+id+'" onclick="userDefinedAreaFind.definedAreaDelete('+ "'" + id +"'" + ')">x</div></div>';	
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

/**
 * 地图围栏中添加围栏名称 自定义线条
 */
function createElecNameUserLine(name,coor,index, id) {
	var deleteElement = document.createElement('div');
	var strs = '<div><label class="definedLineDetail" style="color:#0000ff" onclick="userDefinedLineFind.showDetail(\''+id+'\')" id="'+index+'">'+name+'</label>'+
				'<div class="lineName openDefinedLineFlag" style="color:#0000ff" id="'+id+'" onclick="userDefinedLineFind.definedLineDelete('+ "'" + id +"'" + ')">x</div></div>';	
	deleteElement.innerHTML = strs;
    var deleteE = new ol.Overlay({
	    element: deleteElement,
	    offset: [0, 0],
	    position: coor,
	    positioning: 'center-center'
    });
    deleteE.setProperties({id: index});
    //console.log(index);
    map.addOverlay(deleteE);
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


/** **********************************分页信息************************************************** */
/**
 * 下拉设置函数
 * 
 * @param addPageSelect
 *            添加下拉设置每页显示几条
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
 * 
 * @param selectPageSizeChange
 *            添加下拉菜单改变设置每页显示几条
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


/** *******************************临时代码*********************************************** */
/**
 * 初始化左侧栏车辆列表
 */
function initLeftCar() {
	var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
	var html = "";
	 /* 默认的参数信息 */
    var ops = {
    	listId: "", 
    	enterpriseName: '',
    	carNum: ""
    };
	// 车辆数据显示字符串
	var carStr = "";
	// 获取企业车辆数据
	var data = '';
	$.ajax({
		type : 'get',
		cache: false,  //解决ie9下自动从缓存中获取数据的问题
		url  :  $.backPath + '/dangerVehicle/findEnpCarForWl',
		data : {},
		async: false,
		success : function(json) {
			data = json.data;
		}
	});
	
	if (data) {
		var dataLen = data.length;
		// 由企业就去加载模板
		if(dataLen > 0) {
			$.ajax({
				url: '/JXWL/js/template/left-car.html',
				async: false,
				dataType: "html",
				cache: true,
				success: function (data) {
					html = data;
				}
			});
			$($("#list-group-LeftMenu").find("li")[2]).empty().append($('<ul class="list-groups-busness"></ul>'));
			// 循环生产企业和车
			for (var i = 0;i < dataLen;i++) {
				// 加载企业
				/* 传入的值，和默认值进行替换 */
				$.extend(ops, data[i]);
				ops.listId = "lists-cars-" + i;
				/* 替换模板dom结构里面的内容 */
				var replaceHtml = html.replace(reg, function (node, key) {
					return {
						listId: ops.listId,
						enterpriseName: ops.enterpriseName,
						carNum: ops.carNum
					}[key];
				});
				$(".list-groups-busness").append($(replaceHtml));
				// 加载车辆
				var cars = data[i].cars;
				if (cars && cars.length > 0) {
					var string = '';
					for(var j = 0;j < cars.length;j++){
						var trackHtml = '<span class="following" onClick="following(\''+cars[j].carrierName+'\',this)">跟踪</span>';
						var untrackHtml = '<span class="following" onClick="following(\''+cars[j].carrierName+'\',this)">取消</span>';
						if(cars[j].onOffLine === "on"){// 在线
							string += '<li class="list-car car-backgroud" >'+
								'<span class="carName">'+ cars[j].carrierName +'</span>'+
								'<span class="rollCall" onClick="rollCall(this)">点名</span>';
							if(cars[j].carrierName == HthxMap.followingCar){//车辆正在被跟踪
								string += untrackHtml;
							}else{
								string += trackHtml;
							}
							string += '<span class="status live" onClick="loadMapCar(this)">在线</span></li>';
							
						} else {// 离线
							string += '<li class="list-car car-backgroud">'+
							'<span class="carName">'+ cars[j].carrierName +'</span>'+
							'<span class="rollCall" onClick="rollCall(this)">点名</span>'+
							//离线车辆不展示跟踪按钮
							//'<span class="following" onClick="following(\''+cars[j].onOffLine+'\',\''+cars[j].carrierName+'\',this)">跟踪</span>'+
							'<span class="status">离线</span></li>';
						}
					}
					$("#lists-cars-"+ i +" .list-subgroup-cars").append($(string));
				}
			}
			// 注册展开事件
			$(".lists-busness").on("click", function(){
				var $this = $(this);
				if($this.attr("aria-expanded") === "true"){
					$this.find(".rows-handle>label").text("+");
					$this.addClass("collapsed").attr("aria-expanded", 'false');
					$this.next(".collapse").removeClass("in").attr("aria-expanded", 'false');
					//删除换行
					if(navigator.userAgent.indexOf("Chrome")>=0){
						$this.find(".busnessName").next().remove();
						$this.find(".countCar").next().remove();
					}
				} else {
					$this.find(".rows-handle>label").text("-");
					//增加换行
					if(navigator.userAgent.indexOf("Chrome")>=0){
						$this.find(".countCar").before("<br>");
						$this.find(".countCar").after("<br>");
					} 
					$this.removeClass("collapsed").attr("aria-expanded", 'true');
					$this.next(".collapse").addClass("in").attr("aria-expanded", 'true');
				}
				return false;
			});
		}
	}
}

/**
 * 打开左侧栏企业车辆信息栏
 * 
 * @param index
 * @returns {Boolean}
 */
function open(index){
	var leftli = $("#left-menu-1>ul>li");
	var $this = $(leftli[index]);
	$this.find(".rows-handle>label").text("-");
	$this.removeClass("collapsed").attr("aria-expanded", 'true');
	$this.next(".collapse").addClass("in").attr("aria-expanded", 'true');
	return false;
}

/**
 * 返回左侧栏已打开的企业车辆信息序列
 * 
 * @returns {Number}
 */
function findIndex(){
	var leftli = $("#left-menu-1>ul>li");
	var index = 0;
	for(len=leftli.size();index<len;index++){
		if( $(leftli[index]).attr("aria-expanded") ==="true" ){
			return index;
		}
	}
	return -1;
}
/**
 * 定位车辆 点击左侧栏车辆列表中在线车辆
 * 
 * @param car
 */
function loadMapCar(car) {
	var plateNo = $(car).parent("li").text();
	var onoff = plateNo.substring(plateNo.length - 2, plateNo.length);
	if (onoff === "在线") {
		var featureid =  plateNo.substring(0, plateNo.length - 6);// "黑A12345";
		var e = event || window.event;
		var feature = monitLayer.getSource().getFeatureById(featureid);
		if (feature) {
			var style = feature.getStyle();
			if (style) {
				feature.setStyle(featureStyles[featureid]);
				HthxMap.clickEvent(feature);
				HthxMap.popDiv.createClickPop(feature,boatDiv);
				HthxMap.preFeature = feature;
				var point = [feature.getProperties().longitude,feature.getProperties().latitude];
				map.getView().setCenter(point);
			}
		}
	}
}

/**
 *  点名
 * @param car
 */
function rollCall(car) {
	var plateNo = $(car).parent('li').text();
	var onoff = plateNo.substring(plateNo.length - 2, plateNo.length);
	if (onoff === "在线") {
		var featureid =  plateNo.substring(0, plateNo.length - 6);// "黑A12345";
		var e = event || window.event;
		//var feature = monitLayer.getSource().getFeatureById(featureid);
		topMonitorLayer.getSource().clear();
		topMonitorLayer.getSource().addFeature(monitLayer.getSource().getFeatureById(featureid));
		var feature = topMonitorLayer.getSource().getFeatureById(featureid);
		var terminalType = feature.get("terminalTypeImage");  //原始图片
		/*if(terminalType == "/JXWL/map/business/img/ship/normalGPS.png"){
			feature.set("orgin","第三方GPS运营平台");
		}else{
			feature.set("orgin","嘉兴位置服务平台");
		}*/
		var orgin = feature.get("orgin");//车辆来源
		if (feature) {
			var style = feature.getStyle();
			if (style) {
				feature.setStyle(featureStyles[featureid]);
				HthxMap.clickEvent(feature);
				HthxMap.preFeature = feature;
				var point = [feature.getProperties().longitude,feature.getProperties().latitude];
				map.getView().setCenter(point);
				var i= 0;
				var timeId = window.setInterval(function(){
					    i++;
						changeToRollCallImg(featureid,orgin);
						window.setTimeout(function(){
							var imgArr = [];
							imgArr.push(terminalType);
							feature.set("terminalTypeImage",terminalType);
							HthxMap.changeShipImg(feature, 0, imgArr);
						}, 500);
						if(i>2){
							window.clearInterval(timeId);
						};
					}, 1000);
			}
		}
		
	}
	else{
		Message.alert({Msg: "当前车辆不在线！", isModal: false});
	}
}

function findRealLocationByCarrierName(carrierName){
	var data;
	$.ajax({
		url : '/JXWL/rest/dangerVehicle/findRealLocationByCarrierName/'+ carrierName,
		type : 'GET',
		dataType : 'JSON',
//		data: data,
		success : function(result) {
			data = result;
		},
		error: function(e) {
			Message.alert({Msg:'获取车辆实时位置失败!',isModal: true}); 
		}
	});
	return data;
}
/**
 * 定位车辆 点击左侧栏车辆列表中跟踪按钮 
 * 
 * @param car
 */
function following(plateNo,car){
	var featureid =  plateNo;// "黑A12345";
	var e = event || window.event;
	var feature = monitLayer.getSource().getFeatureById(featureid);
		if (feature) {
			var coor = [feature.getProperties().longitude,feature.getProperties().latitude];
			if(!coor[0] || !coor[1] || coor[0]=="null" || coor[1]=="null"){
				Messager.alert({Msg:"该车辆没有上传位置信息,无法跟踪/取消跟踪", isModal: true});
				return;
			}
			coor = [JSON.parse(coor[0]), JSON.parse(coor[1])];
			//地图大小级别
			/*if(map.getView().getZoom() !== 19){
				map.getView().setZoom(19); 
			}*/
			map.getView().setCenter(coor);//将被跟踪车辆显示在地图中心
			shipTracking(featureid, $(car), monitLayer, map);
		}
}




/**
 * 订单日志记录提醒 接受后端websocket发送过来的消息处理
 * 
 * @param json
 */
function logRemind(json){
	$("#logRemindRightPanel").showPanelModel("订单日志提醒");
	var logData = json.data;
	var str = "";
	for(var i=0; i<logData.length; i++){
		var rowdata = "<tr>"
			+"<td>"+logData[i].corporateNo
			+"</td>"
			+"<td>"+logData[i].recordTime
			+"</td>"
			+"<td>"+logData[i].content
			+"</td>"
			+"</tr>";
		str += rowdata;
	}
	$("#logRemindRightTable").append(str);
	//将日志设置为已读，最后单独提出去
	$("#logRemindRightSubmitBtn").bind("click", _logRead);
}

//将日志设置为已读，最后单独提出去
var _logRead = function() {
	var formData         = $("#logRemindRightForm").packageFormData();
		$.ajax({
			url : $.backPath + '/orderManage/updateLogRemind'  /*订单日志已读设置*/,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$("#logRemindRightPanel").closePanelModel();
				$("#logRemindRightTable").jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
};

/**
 * 报警记录以及车辆图片处理 接受后端websocket发送过来的消息处理
 * 
 * @param json
 */
function alarmHandle(json){
	if(json.data.alarmDealStatus === "0"){
		// 没有报警记录 则改变车辆图标
		changeToNormalImg(json.data.carrierName);
	}
}


/**
 * 改变到正常状态图片
 * 
 * @param carrierName
 *            车牌号
 */
function changeToNormalImg(carrierName){
	if(!carrierName){
		return;
	}
	var source = monitLayer.getSource();
	var feature = source.getFeatureById(carrierName);
	var imgArr = [];
	var pngUrl;
	var properties = feature.getProperties();
	if("嘉兴位置服务平台" == properties.orgin){
		pngUrl = HthxMap.Settings.configData.targetImg.normal;
	}else{
		pngUrl = HthxMap.Settings.configData.targetImg.normalGPS;
	}
	imgArr.push(pngUrl);
	feature.set("terminalTypeImage",pngUrl);
	HthxMap.changeShipImg(feature, 0, imgArr);
}

/**
 * 改变到报警状态图片
 * 
 * @param carrierName
 *            车牌号
 */
var  changeToAlarmImg= function(carrierName){
	if(!carrierName){
		return;
	}
	var source = monitLayer.getSource();
	var feature = source.getFeatureById(carrierName);
	if( !feature){
		return;
	}
	var imgArr = [];
	var properties = feature.getProperties();
	var pngUrl = null;
	if("嘉兴位置服务平台" == properties.orgin){
		pngUrl = HthxMap.Settings.configData.targetImg.alarm;
	}else{
		pngUrl = HthxMap.Settings.configData.targetImg.alarmGPS;
	}
	feature.set("terminalTypeImage",pngUrl);
	imgArr.push(pngUrl);
	HthxMap.changeShipImg(feature, 0, imgArr);
};

/**
 * 改变到点名状态图片 闪烁三次 每秒一次
 * 
 * @param carrierName
 *            车牌号
 */
function changeToRollCallImg(carrierName,orgin){
	if(!carrierName){
		return;
	}
	var source = monitLayer.getSource();
	var feature = source.getFeatureById(carrierName);
	var imgArr = [];
	var pngUrl = null;
	pngUrl = HthxMap.Settings.configData.targetImg.rollCall;
	if("嘉兴位置服务平台" == orgin){
		pngUrl = HthxMap.Settings.configData.targetImg.rollCall;
	}else{
		pngUrl = HthxMap.Settings.configData.targetImg.rollGPSCall;
	}
		
	imgArr.push(pngUrl);
	feature.set("terminalTypeImage",pngUrl);
	HthxMap.changeShipImg(feature, 0, imgArr);
};






