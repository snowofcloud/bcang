/**
 * 调用轨迹回放功能，初始化播放器窗口，初始化参数
 * 对象：播放器
 *       播放；停止；快播；快退；重置 有相对应的函数实现
 *     历史轨迹图层 trackReplayLayer
 *       3个feature：1.marker
 *                 2. arrow
 *                 3. path
 *     
 * map : 地图对象
 * g_trackData ： 轨迹数据数组
 */
var trackPopArray = [];
HthxMap.trackReplay = function(map, g_trackData, trackDiv, leadingOut){
	var MARKERPNG;
	//箭头图片
	var ARROWPNG = HthxMap.Settings.IMG.trackReplay.ARROWPNG;
	var ORIGINPNG = HthxMap.Settings.IMG.trackReplay.ORIGINPNG;
	var DESTINATIONPNG = HthxMap.Settings.IMG.trackReplay.DESTINATIONPNG;
	var REPPLAYPNG = HthxMap.Settings.IMG.trackReplay.REPPLAYPNG;
	var REPSTOPPNG = HthxMap.Settings.IMG.trackReplay.REPSTOPPNG;
	var BSLIP = HthxMap.Settings.IMG.trackReplay.BSLIP;
	var ASLIP = HthxMap.Settings.IMG.trackReplay.ASLIP;
	var DBUTTON = HthxMap.Settings.IMG.trackReplay.DBUTTON;
	var ALARMPNG = HthxMap.Settings.IMG.trackReplay.ALARMPNG;
	var TIMEINTERVAL = 1000;    //播放速度(1秒每个点)
	var g_index = -1;
	var g_speed_f = 1;
	var g_speed_b = 1;
	var g_timeoutTrack = 0;
	var playFlag = false;  //true为播放状态,false为暂停播放
	var popup = null;
	var featureArrow = null;//箭头feature
	var pop_id = 1;
	var trackPopIndex = 0; //弹框间隔点数
	var showFlag = true;  //弹框默认显示
	/**
	 * 初始化：创建播放器窗口、初始化播放条、初始化layer 然后自动播放
	 */
	if(g_trackData && map) {
		handlerRepClose();
		//加载css文件
		if(!$("head").find("link[href *='bootstrap-slider.min.css']").length){
			HthxMap.include.linkStyle(["map/extend/trackReplay/css/bootstrap-slider.min.css"]);
		}
		//初始化历史轨迹layer图层  
		HthxMap.trackReplayLayer = initLayer();
		//只有一个点的情况
		if(g_trackData.length == 1){
			g_index = 0;
			drawMarker(g_trackData[0].x, g_trackData[0].y);
			map.getView().setCenter([g_trackData[0].x, g_trackData[0].y]);
		}
		//地图大小级别
		if(map.getView().getZoom() !== 19){
			map.getView().setZoom(19); 
		}
		createDialog();
		initSlide();
		//自动播放
		handlerPlay();
	}
	
	
	/**
	 * 初始化layer，并给layer注册一系列feature相关的鼠标事件
	 */
	function initLayer() {
		var stroke = new ol.style.Stroke({color: '#FF0000', width:2});
	    var fill = new ol.style.Fill({color: 'rgba(0,255,0,0.4)'});
		var trackLayer = new ol.layer.Vector({
			source: new ol.source.Vector(),
			style:new ol.style.Style({
				fill:fill,
				stroke:stroke
			})
		});
		trackLayer.setProperties({id:"trackLayer"});
		map.addLayer(trackLayer);
		//HthxMap.mouseEvent(map, "click", "trackLayer", trackDiv);
		return trackLayer;
	}
	
	/**
	 * 初始播放滑动条
	 */
	function initSlide() {
		$('#slider').slider({
		    min : 0,
			max : 100,
			step : 1,
			tooltip: 'always',
			formatter: handlerTipFormatter,
			natural_arrow_keys: true
		});
		$(".slider-track").css({"background-image":BSLIP}).css("height","5px");
		$(".slider-selection").css({"background-image":ASLIP});
		$(".slider-handle").css({"background-image":DBUTTON}).css("margin-top","-7px");
		$(".tooltip-inner").css("width","100px");
		$(".tooltip-inner").css("color","#177EE6");
		$(".tooltip-inner").removeClass("tooltip-inner");
		$(".tooltip-arrow").removeClass("tooltip-arrow");
		$(".tooltip-max").css("width", "0px");
		$(".tooltip-min").css("width", "0px");
		$(".tooltip-main").css("font-family","micro yahei").css("margin-left","-26px").css("font-size","12px").css("margin-top","-22px");
		$("#slider").on("slideStart", function(slideEvt) {
			handlerSlideStart(slideEvt.value);
		});
		$("#slider").on("slideStop", function(slideEvt) {
			handlerSlideEnd(slideEvt.value);
		});
	}
	
	/**
	 * 滑动条开始拖动事件
	 * val : 拖动前的当前值
	 */
	function handlerSlideStart(val) {
		clearInterval(g_timeoutTrack);
	};
	/**
	 * 滑动条拖动结束事件
	 * val : 拖动后的当前值
	 */
	function handlerSlideEnd(val){
		removeFeature(HthxMap.trackReplayLayer);
		deleteDefaultPop(); //删除所有默认弹框
		switch(val){
			case 0 : {
				playByStat("drag_start_play_forward");
			}break;
			case 100 : {
				playTrack(val*0.01);
				if(g_speed_b != 1 ){
					playByStat("drag_end_play_back");
				}
				else{
					playByStat("drag_end");
				}
			}break;
			default: {
				playTrack(val*0.01);
				if(g_speed_b != 1){
					playByStat("drag_center_play_back");
				}
				else{
					playByStat("drag_center_play_forward");
				}
			}break;
		}
	};
	/**
	 * 移除图层上的feature
	 */
	function removeFeature(layer){
		var source = HthxMap.trackReplayLayer.getSource();
		var features = source.getFeatures();
		if(features.length){
			for(var i=0; i<features.length; i++){
				source.removeFeature(features[i]);
			}
		}
	}
	/**
	 * 格式化滑动条的tip显示字符串
	 */
	function handlerTipFormatter(value){
		var len = g_trackData.length - 1;
		var totalTime = g_trackData[len].recordTime - g_trackData[0].recordTime;
		var startTime = g_trackData[0].recordTime - 0;
		var currentTime = startTime + totalTime*value*0.01;
		var t = new Date(currentTime);
		return convertTime(t);
	}
	/**
	 * 播放按钮回调
	 */
	function handlerPlay(){
		if($("#slider").slider("getValue") == 100){
			return;
		}
		playFlag = !playFlag;
		if(playFlag){
			playByStat("play_forward");
		}
		else{
			playByStat("stop");
		}
	};
	/**
	 * 快进播放回调
	 */
	function handlerForward(){
		if($("#slider").slider("getValue") == 100){
			return;
		}
		g_speed_f *= 2;
		g_speed_f = g_speed_f == 64?1:g_speed_f;
		var s = "x" + g_speed_f;
		if(g_speed_f == 1){
			s = "";
		}
		$("#label_kj").css("display","block").text(s);
		playByStat("play_forward");
	}
	/**
	 * 快退播放回调
	 */
	function handlerBack(){
		if($("#slider").slider("getValue") == 0){
			return;
		}
		g_speed_b *= 2;
		g_speed_b = g_speed_b == 64?1:g_speed_b;
		var s = "x" + g_speed_b;
		if(g_speed_b == 1){
			s = "";
		}
		$("#label_kt").css("display","block").text(s);
		playByStat("play_back");
		HthxMap.removeOverlay(map);
	};
	/**
	 * 重新播放回调
	 */
	function handlerReplay(){
		pop_id = 1;
		featureArrow = null;
		$('#slider').slider("setValue",0);
		HthxMap.removeOverlay(map);
		setShipToCenter([g_trackData[0].x, g_trackData[0].y]);
//		clearInterval(g_timeoutTrack);
		playFlag = false;
		playByStat('stop');
		playTrack(0);
	};
	/**
	 * 关闭播放窗口回调
	 */
	function handlerRepClose(){
		if(HthxMap.trackReplayLayer){
			removeFeature(HthxMap.trackReplayLayer);
			map.removeLayer(HthxMap.trackReplayLayer);
			HthxMap.trackReplayLayer = null;
			deleteDefaultPop(); //删除所有默认弹框
		}
		g_index = -1;
		g_speed_f = 1;
		g_speed_b = 1;
		clearInterval(g_timeoutTrack);
		HthxMap.unMouseEvent();
		if($("#slider").length){
			$("#slider").slider("destroy");
			$("#dgRep").remove();
		}
	}
	/**
	 * 根据不同播放状态，设定对应定时器，作出相应播放轨迹动画
	 */
	function playByStat(stat){
		switch(stat){
			/*
			 * 向前播放
			 */
	    	case "play_forward" : {
	    		g_speed_b = 1;
	    		$("#label_kt").css("display","none");
	    		if(g_speed_f == 1){
	    			$("#label_kj").css("display","none");
	    		}
	    		playFlag = true;
				$("#play").css({"background-image":REPSTOPPNG});
				document.getElementById("play").title = "暂停";
	    		clearInterval(g_timeoutTrack);
	    		g_timeoutTrack = setInterval(updatePosition,TIMEINTERVAL/g_speed_f);
	    		
	    	}break;
	    	/*
	    	 * 向前播放到终点
	    	 */
	    	case "play_forward_end" : {
	    		g_speed_f = 1;
	    		playFlag = false;
	    		$("#play").css({"background-image":REPPLAYPNG});
	    		document.getElementById("play").title = "播放";
				$("#label_kj").css("display","none");
				clearInterval(g_timeoutTrack);
	    	}break;
	    	/*
	    	 * 暂停
	    	 */
	    	case "stop" : {
	    		g_speed_f = 1;
	    		g_speed_b = 1;
				$("#label_kj").css("display","none");
				$("#label_kt").css("display","none");
				clearInterval(g_timeoutTrack);
				$("#play").css({"background-image":REPPLAYPNG});
				document.getElementById("play").title = "播放";
	    	}break;
	    	/*
	    	 * 向后播放
	    	 */
	    	case "play_back" : {
	    		g_speed_f = 1;
	    		$("#label_kj").css("display","none");
	    		if(g_speed_b == 1){
	    			$("#label_kt").css("display","none");
	    			clearInterval(g_timeoutTrack);
	    			g_timeoutTrack = setInterval(updatePosition,TIMEINTERVAL/g_speed_f);
	    			return;
	    		}
	    		playFlag = true;
				$("#play").css({"background-image":REPSTOPPNG});
				document.getElementById("play").title = "暂停";
				clearInterval(g_timeoutTrack);
				g_timeoutTrack = setInterval(updatePosition_KT,TIMEINTERVAL/g_speed_b);
	    	}break;
	    	/*
	    	 * 向后播放到起点
	    	 */
	    	case "play_back_start" : {
	    		g_speed_b = 1;
	    		g_speed_f = 1;
	    		pop_id = 1;
	    		featureArrow = null;
	    		playFlag = false;
	    		$("#label_kj").css("display","none");
	    		$("#label_kt").css("display","none");
	    		clearInterval(g_timeoutTrack);
	    		$("#play").css({"background-image":REPPLAYPNG});
	    		document.getElementById("play").title = "播放";
	    	}break;
	    	/*
	    	 * 拖动进度条到起点并向前播放
	    	 */
	    	case "drag_start_play_forward" : {
	    		g_index = -1;
	    		pop_id = 1;
	    		featureArrow = null;
	    		playFlag = true;
	    		$("#play").css({"background-image":REPSTOPPNG});
	    		document.getElementById("play").title = "暂停";
	    		g_timeoutTrack = setInterval(updatePosition,TIMEINTERVAL/g_speed_f);
	    	}break;
	    	case "drag_start_stop" : {
	    		
	    	}break;
	    	/*
	    	 * 拖动进度条到终点
	    	 */
	    	case "drag_end" : {
	    		g_speed_f = 1;
	    		playFlag = false;
	    		$("#play").css({"background-image":REPPLAYPNG});
	    		document.getElementById("play").title = "播放";
				$("#label_kj").css("display","none");
	    	}break;
	    	/*
	    	 * 拖动进度条到终点，并向后播放
	    	 */
	    	case "drag_end_play_back" : {
	    		playFlag = true;
	    		$("#play").css({"background-image":REPSTOPPNG});
	    		document.getElementById("play").title = "暂停";
	    		g_timeoutTrack = setInterval(updatePosition_KT,TIMEINTERVAL/g_speed_b);
	    	}break;
	    	/*
	    	 * 拖动进度条到中间任意位置，并向前播放
	    	 */
	    	case "drag_center_play_forward" : {
	    		playFlag = true;
	    		$("#play").css({"background-image":REPSTOPPNG});
	    		document.getElementById("play").title = "暂停";
	    		g_timeoutTrack = setInterval(updatePosition,TIMEINTERVAL/g_speed_f);
	    	}break;
	    	/*
	    	 * 拖动进度条到中间任意位置，并向后播放
	    	 */
	    	case "drag_center_play_back" : {
	    		playFlag = true;
	    		$("#play").css({"background-image":REPSTOPPNG});
	    		document.getElementById("play").title = "暂停";
	    		g_timeoutTrack = setInterval(updatePosition_KT,TIMEINTERVAL/g_speed_b);
	    	}break;
	    	case "drag_center_stop" : {
	    		
	    	}break;
		}
	};
	/**
	 * 按照拖动的百分比播放轨迹
	 */
	function playTrack(percent){
		removeFeature(HthxMap.trackReplayLayer);
		deleteDefaultPop();   //删除所有默认弹框
		var currentPoint = parseInt(g_trackData.length*percent);
		g_index = 0;
		for(;g_index < currentPoint;g_index++){
			var lon = g_trackData[g_index].x;
			var lat = g_trackData[g_index].y;
			drawPath(lon, lat);
			drawPoint();
			//定点默认弹框
		 	if(g_index && (g_index !== g_trackData.length-1) && !(g_index % trackPopIndex)){
//		 		defaultPop(g_index);
		 	}
		}
		if(percent == 0){
			getTrackPopIndex(0);
			drawPoint();
		}
		if(g_index != 0){
			g_index -= 1;
		}
		if(currentPoint != 0){
			setShipToCenter([g_trackData[g_index].x,g_trackData[g_index].y]);
		}
		drawMarker(g_trackData[g_index].x, g_trackData[g_index].y);
	};
	
	/**
	 * 更新位置
	 */
	function updatePosition(){
		g_index += 1;
		if(g_index == g_trackData.length){
			g_index -= 1;
			playByStat("play_forward_end");
			return;
		}
		var lon = g_trackData[g_index].x;
		var lat = g_trackData[g_index].y;
		setShipToCenter([lon,lat]);
		drawPoint();
		drawPath(lon, lat);
		drawMarker(lon, lat);
	 	$('#slider').slider("setValue", parseInt(g_index/(g_trackData.length - 1)*100));
	 	//定点默认弹框
	 	if(g_index && (g_index !== g_trackData.length-1) && !(g_index % trackPopIndex)){
	 		//defaultPop(g_index);
	 	}
	}
	/**
	 * 快退模式更新位置
	 */
	function updatePosition_KT(){
		//依次删除默认弹框
	 	deleteDefaultPop(g_index);
		g_index -= 1;
		if(g_index == -1){
			playByStat("play_back_start");
			return;
		}
		var lon = g_trackData[g_index].x;
		var lat = g_trackData[g_index].y;
		setShipToCenter([lon,lat]);
		var features = HthxMap.trackReplayLayer.getSource().getFeatures();
		var len = features.length;
		
		for(var i=0; i<3; i++){
			var features = HthxMap.trackReplayLayer.getSource().getFeatures();
			if(features){
				var len = features.length;
				var beforeId = features[0].getProperties().pop_id;
				for(var j=1; j<len; j++){
					var nextId = features[j].getProperties().pop_id;
					if(beforeId < nextId){
						var pop_id = features[0];
						features[0] = features[j];
						features[j] = pop_id;
						beforeId = nextId;
					}
				}
				HthxMap.trackReplayLayer.getSource().removeFeature(features[0]);
			}
		}
		drawMarker(lon, lat);
	 	$('#slider').slider("setValue",parseInt(g_index/(g_trackData.length - 1)*100));	 	 	
	}
	/**
	 * 绘制轨迹回放目标对象marker,小船
	 * lon ： 经度
	 * lat ： 纬度
	 */
	function drawMarker(lon,lat){
		var feature = HthxMap.trackReplayLayer.getSource().getFeatureById("target");
		if(feature){
			HthxMap.trackReplayLayer.getSource().removeFeature(feature);
		}
		var featureMarker = new ol.Feature(new ol.geom.Point([lon,lat]));
		featureMarker.setId("target");
		featureMarker.setProperties({
			carName : g_trackData[g_index].carName,
			//shipType : g_trackData[g_index].shipType,
			shipDir : g_trackData[g_index].orientation,
			pop_carSpeed : g_trackData[g_index].pop_carSpeed,
			pop_lon : lon,
			pop_lat : lat,
			terminalType: g_trackData[g_index].terminalType,
			status: g_trackData[g_index].status,
			heading : g_trackData[g_index].heading,
			terminalID: g_trackData[g_index].terminalId,
			pop_date : new Date(g_trackData[g_index].recordTime),
			id: g_trackData[g_index].id,
			pop_id: pop_id++
		});
		MARKERPNG = HthxMap.createShipImg(featureMarker, true);
		HthxMap.changeShipImg(featureMarker, g_trackData[g_index].shipDir, [MARKERPNG]);
		if(g_index == g_trackData.length - 1){
			if(featureArrow){
				featureArrow.setProperties({"pop_id": pop_id++});
				HthxMap.trackReplayLayer.getSource().addFeature(featureArrow);
				featureArrow = null;
			}
			return;
		}
		HthxMap.trackReplayLayer.getSource().addFeature(featureMarker);
	};
	/**
	 * 绘制轨迹点,航向箭头
	 */
	function drawPoint(){
		if(featureArrow != null){
			featureArrow.setProperties({"pop_id": pop_id++});
			HthxMap.trackReplayLayer.getSource().addFeature(featureArrow);
		}
		var lon = g_trackData[g_index].x;
		var lat = g_trackData[g_index].y;
//		var radius = 5;
		var labelContent = "";
		var pngurl = ARROWPNG;
		
		var longitude;
		var latitude;
		var point;
		//坐标转换
		var len = mapBaseMethod.coordinateLength(lon);
		if(len<3){
			longitude = mapBaseMethod.degreesToStringHDMS(JSON.parse(lon));
			latitude = mapBaseMethod.degreesToStringHDMS(JSON.parse(lat));
		}else{
			point = mapBaseMethod.initProjTransform(lon,lat,false);
			longitude = mapBaseMethod.degreesToStringHDMS(point[0][0]);
			latitude = mapBaseMethod.degreesToStringHDMS(point[0][1]);
		}
		if(g_index == 0){
			labelContent = "起点:北纬:"+latitude+",东经:"+longitude;
//			pngurl = ORIGINPNG;
//			radius = 10;
		}
		else if(g_index == g_trackData.length - 1){
			labelContent = "终点:北纬:"+latitude+",东经:"+longitude;
//			pngurl = DESTINATIONPNG;
//			radius = 10;
		}
		var feature = new ol.Feature(new ol.geom.Point([lon,lat]));
		feature.setStyle(new ol.style.Style({
					image: new ol.style.Icon({
					    opacity: HthxMap.opacity,
					    rotation: HthxMap.transformImgRotation(g_trackData[g_index].shipDir),
						src: pngurl
					}),
					text: new ol.style.Text({
						text:  labelContent,
						textAlign: "lm",
						font:"15px Microsoft YaHei",
						fill: new ol.style.Stroke({
							color: "red"
						})
					})
				}
		));
		feature.setProperties({
			carName : g_trackData[g_index].carName,
			shipType : g_trackData[g_index].shipType,
			terminalType : g_trackData[g_index].terminalType,
			status: g_trackData[g_index].status,
			shipDir : g_trackData[g_index].shipDir,
			pop_carSpeed : g_trackData[g_index].pop_carSpeed,
			pop_lon : lon,
			pop_lat : lat,
			heading : g_trackData[g_index].heading,
			id: g_trackData[g_index].id,
			pop_date : new Date(g_trackData[g_index].recordTime)
		});
		feature.set("terminalTypeImage", pngurl);
		featureArrow = feature;
//		TODO: 绘制报警点
		drawAlarm(g_trackData[g_index].alarmRecords, g_trackData[g_index].x, g_trackData[g_index].y);
	};
	
	//绘制报警点
	function drawAlarm(alarmRecords, lon, lat){
		for(var i = 0;i<alarmRecords.length;i++){
			var feature = new ol.Feature(new ol.geom.Point([lon,lat]));
			feature.setStyle(new ol.style.Style({
						image: new ol.style.Icon({
						    opacity: HthxMap.opacity,
							src: ALARMPNG
						}),
						text: new ol.style.Text({
							text:  alarmRecords[i].alarmDetails,//报警详细信息文字
							textAlign: "lm",
							font:"15px Microsoft YaHei",
							fill: new ol.style.Stroke({
								color: "red"
							})
						})
					}
			));
			/*feature.setProperties({
				carName : g_trackData[g_index].carName,
				shipType : g_trackData[g_index].shipType,
				terminalType : g_trackData[g_index].terminalType,
				status: g_trackData[g_index].status,
				shipDir : g_trackData[g_index].shipDir,
				pop_carSpeed : g_trackData[g_index].pop_carSpeed,
				pop_lon : lon,
				pop_lat : lat,
				heading : g_trackData[g_index].heading,
				id: g_trackData[g_index].id,
				pop_date : new Date(g_trackData[g_index].recordTime)
			});*/
			feature.setProperties({"pop_id": pop_id++});
			feature.set("terminalTypeImage", ALARMPNG);
			HthxMap.trackReplayLayer.getSource().addFeature(feature);
			
		}
	};
	
	/**
	 * 绘制轨迹路径
	 * lon ： 经度
	 * lat ： 纬度
	 */
	function drawPath(lon, lat){
		if(g_index != 0){
			var lineArr = [];
			var count = 50;
			var countX = (lon - g_trackData[g_index - 1].x)/count;
			var countY = (lat - g_trackData[g_index - 1].y)/count;
			for(var i=0; i<count; ){
				var pointArr = [];
				pointArr.push([g_trackData[g_index - 1].x+countX*i,g_trackData[g_index - 1].y+countY*i]);
				pointArr.push([g_trackData[g_index - 1].x+countX*(i+1),g_trackData[g_index - 1].y+countY*(i+1)]);
				lineArr.push(pointArr);
				i+=2;
			}
			var path = new ol.Feature(new ol.geom.MultiLineString(lineArr));
			path.setStyle(new ol.style.Style({
				stroke: new ol.style.Stroke({
					width: 2,
		            color: '#3399CC'
				})
			})) 
			path.setProperties({"carName":"line"});
			path.setProperties({"pop_id": pop_id++});
			path.set("noClickEvent", true);
			HthxMap.trackReplayLayer.getSource().addFeature(path);
		}
	}
	
	/**
	 * 绘制轨迹路径
	 * routeData  路径数组
	 */
//	function drawPath(routeData){
//		var path = new ol.Feature(new ol.geom.MultiLineString(routeData));
//		path.setStyle(new ol.style.Style({
//			stroke: new ol.style.Stroke({
//				width: 2,
//	            color: '#3399CC'
//			})
//		}));
//		path.setProperties({"carName":"line"});
//		path.setProperties({"pop_id": pop_id++});
//		path.set("noClickEvent", true);
//		HthxMap.trackReplayLayer.getSource().addFeature(path);
//	}
	
	/*function drawPathData(routeData){
			var path = new ol.Feature(new ol.geom.MultiLineString(routeData));
			path.setStyle(new ol.style.Style({
				stroke: new ol.style.Stroke({
					width: 2,
		            color: '#3399CC'
				})
			}));
			path.setProperties({"carName":"line"});
			path.setProperties({"pop_id": pop_id++});
			path.set("noClickEvent", true);
			HthxMap.trackReplayLayer.getSource().addFeature(path);
		}
	}*/
	
	/*根据船舶位置，重新将其置于地图中心*/
	function setShipToCenter(coordinate){
		var extent = map.getView().calculateExtent(map.getSize());
		var extent_0 = extent[0] + (extent[2] - extent[0])/3;
		var extent_1 = extent[1] + (extent[3] - extent[1])/3;
		var extent_2 = extent[2] - (extent[2] - extent[0])/3;
		var extent_3 = extent[3] - (extent[3] - extent[1])/3;
		var extentTemp = [extent_0, extent_1, extent_2, extent_3];
		
		var isContains = ol.extent.containsCoordinate(extentTemp, coordinate);
		if(!isContains){
			 map.getView().setCenter(coordinate);
		}
	}
	
	/*船舶默认弹框*/
	function defaultPop(g_index){
		var longitude;
		var latitude;
		var point;
		//坐标转换
		var len = mapBaseMethod.coordinateLength(g_trackData[g_index].x);
		if(len<3){
			longitude = mapBaseMethod.degreesToStringHDMS(JSON.parse(g_trackData[g_index].x));
			latitude = mapBaseMethod.degreesToStringHDMS(JSON.parse(g_trackData[g_index].y));
		}else{
			point = mapBaseMethod.initProjTransform(g_trackData[g_index].x,g_trackData[g_index].y,false);
			longitude = mapBaseMethod.degreesToStringHDMS(point[0][0]);
			latitude = mapBaseMethod.degreesToStringHDMS(point[0][1]);
		}
		
		var strs = '<div class="panel" style="margin-bottom: 8px;">'+
						'<div class="panel-body bubbleboxcontent" style="padding: 1px;">'+
							'<ul class="bubbleboxMsg clearfix" style="margin-left: -40px; margin-bottom: 0px;font-size: 12px;">'+
								//'<li>时间：' + convertDateToTime(new Date(g_trackData[g_index].recordTime)) + '</li>' + 
								'<li>北纬:'+latitude +'\n东经:' + longitude + '</li>'+
							'</ul>'+	
						'</div>'+
					'</div>';
		
		var element = document.createElement("div");
		element.id = "trackPop"+g_index;
		element.className = "trackPop";
		$('body').append(element);
		element.innerHTML = strs;
		if(showFlag){
			$(".trackPop").css("display", "block");
		}else{
			$(".trackPop").css("display", "none");
		}
		var popup = new ol.Overlay({    //保证船只弹框随船移动而移动，应该修改
			element: element,
			positioning: 'bottom-center',
			autoPan: false,
		    stopEvent: false
		});
		trackPopArray[g_index] = popup;
		map.addOverlay(popup);
	    popup.setPosition([g_trackData[g_index].x, g_trackData[g_index].y]);
	}
	
	/*船舶回放，删除默认弹框*/
	function deleteDefaultPop(g_index){
		if(g_index){
			if(trackPopArray[g_index]){
				map.removeOverlay(trackPopArray[g_index]);
				trackPopArray[g_index] = null;
			}
		}else{
			for(var i = 0; i < trackPopArray.length; i++){
				if(trackPopArray[i]){
					map.removeOverlay(trackPopArray[i]);
					trackPopArray[i] = null;
				} 
			}
		}
	}
	
	//计算间隔点数
	function getTrackPopIndex(index){
		var totalTime;
		if(index >= 0){
			totalTime = g_trackData[g_trackData.length-1].recordTime - g_trackData[index].recordTime;
		}else{
			totalTime = g_trackData[g_trackData.length-1].recordTime - g_trackData[g_index].recordTime;
		}
		
		var intervalPoint = Math.ceil(totalTime/($("#popTime").val()*60*1000));
		trackPopIndex = Math.floor(g_trackData.length / intervalPoint);
	}
	
	//控制弹框的显示隐藏
	function showOrHidden(){
		var check = $("#showOrHidden")[0].checked;
		if(check){
			$(".trackPop").css("display", "block");
		}else{
			$(".trackPop").css("display", "none");
		}
		return check;
	}
	
	/**
	 * 时间转换
	 */
	function convertTime(str) {
		var month = str.getMonth() + 1;
		var day = str.getDate();
		var hour = str.getHours();
		var min = str.getMinutes();
		if(hour < 10){
			hour = '0' + hour;
		}
		if(min < 10){
			min = '0' + min;
		}
		return month + "/" + day + " " + hour + ":" + min;
	}
	/**
	 * 创建播放器窗口
	 */
	function createDialog() {
		var len = g_trackData.length - 1;
		var totalTime = g_trackData[len].recordTime - g_trackData[0].recordTime;
		var part = parseInt(totalTime/4);
		var startTime = new Date(parseInt(g_trackData[0].recordTime));
		var endTime = new Date(parseInt(g_trackData[len].recordTime));
		if(document.getElementById("dgRep") != null){
			handlerRepClose();
			return;
		}
		var startYear = startTime.getFullYear();
		var endYear = endTime.getFullYear();
		if(startYear != endYear){
			startYear += ("-" + endYear + "年");
		}else{
			startYear += "年";
		}		
		var trackPlay = '<div id="dgRep" class="rep_bg div_dragg">'+
							'<div class="panel-heading">'+
								//'<span class="panel-title">'+g_trackData[0].carName+"\r\r\r"+startYear+
								'<span class="panel-title">'+startYear+
								'<span class="panel-close"></span>'+
							'</div>'+
							'<div style="">'+
								'<div id="slider_Container">'+
									'<input id="slider" data-slider-id="ex1Slider" type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="0"/>'+
									'<div id="beginDate">'+convertTime(startTime)+'</div>'+
									'<div id="endDate">'+convertTime(endTime)+'</div>'+
								'</div>'+
								'<div class="sliderBtn">'+  
									'<div id="kt" title="快退" class="rep_button rep_kt" ></div>'+
									'<div id="play" title="播放" class="rep_button  rep_play"></div>'+
									'<div id="kj" title="快进" class="rep_button  rep_kj"></div>'+
									'<div id="replay" title="重置" class="rep_button  rep_re"></div>'+
								'</div>'+
//								'<input id="out" class="btn btn-primary btn-sm" type="button" value="导 出" />'+
//								'<div style="width: 100%;">'+
//								    '<input id="showOrHidden" type="checkbox" style="margin-left: 43px;" checked/>'+
//									'<label style="margin-left: 3px; margin-top: 9px;">间隔时间：</label>'+'<input id="popTime" style="width:60px;height:28px;padding:6px 5px;border:1px solid #ccc;border-radius:4px;" value="240"/>分钟'+
//								'</div>'+
								'<div id="label_kt">x2</div>'+
								'<div id="label_kj">x2</div>'+	
							'</div>'+
						'</div>';
		$("body").append(trackPlay);
		HthxMap.dragDiv("dgRep");
	 	$("#play").unbind("click").bind("click",handlerPlay);//播放按钮事件
		$("#kj").unbind("click").bind("click",handlerForward);//快进按钮事件
		$("#kt").unbind("click").bind("click",handlerBack);//快退按钮事件
		$("#replay").unbind("click").bind("click",handlerReplay);//重播按钮事件
		//$("#rep_close").unbind("click").bind("click",handlerRepClose);
		$("#dgRep .panel-close").unbind("click").bind("click",handlerRepClose);//关闭对话框事件
		$("#out").unbind("click").bind("click",function(){
			var shipId = g_trackData[0].id;
			var startTime = convertDateToTime(new Date(g_trackData[0].recordTime));
			var endTime = convertDateToTime(new Date(g_trackData[g_trackData.length-1].recordTime));
			var terminalType = g_trackData[0].terminalType;
			leadingOut(shipId, startTime, endTime, terminalType);			
		});//没有使用
		$("#popTime").change(getTrackPopIndex);
		$("#showOrHidden").unbind("click").bind("click", function(){
			showFlag = showOrHidden();
		});
		//计算间隔点数
		getTrackPopIndex(0);
	}
}
