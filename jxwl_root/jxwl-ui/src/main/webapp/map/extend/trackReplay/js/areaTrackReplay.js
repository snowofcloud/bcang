/**
 * 调用轨迹回放功能，初始化播放器窗口，初始化参数
 * map: 地图对象
 * areaTrackData: 轨迹数据数组（一维为船，二维为每条船对应的轨迹点）
 * timeInterval: 播放时间间隔 * 
 * trackDiv: 弹框样式
 */
HthxMap.areaTrackReplay = function(map, areaTrackData, trackDiv){
//	var keepPoints = 5;    //包含当前点
	var MARKERPNG;
	var ARROWPNG = HthxMap.Settings.IMG.trackReplay.ARROWPNG;
	var REPPLAYPNG = HthxMap.Settings.IMG.trackReplay.REPPLAYPNG;
	var REPSTOPPNG = HthxMap.Settings.IMG.trackReplay.REPSTOPPNG;
	var BSLIP = HthxMap.Settings.IMG.trackReplay.BSLIP;
	var ASLIP = HthxMap.Settings.IMG.trackReplay.ASLIP;
	var DBUTTON = HthxMap.Settings.IMG.trackReplay.DBUTTON;
	var TIMEINTERVAL = 1000;
	var g_index = -1;
	var g_speed_f = 1;
	var g_speed_b = 1;
	var g_timeoutTrack = 0;
	var playFlag = false;
	var popup = null;
	var featureArrow = [];
	var pop_id = [];
	var startTime;    //所有船舶开始时间
	var endTime;    //所有船舶结束时间	
	var currentTime;    //当前时间
	var totalTime;    //间隔时间
	var timeIndex_Date;
	var point_index = [];
	var timeIndex = 0;
	var flag = false;
	var pointValue = 0;
	var playEnd = false;   
	var playEnd_interval;
	var kt_flag = false;
	var shipFeatures = [];  //每个点和线对象的feature
	var markerFeatures = []; //每个船舶对象的feature
	var checkPopArray = [];	//选择框对象
	var areaTrackDataTemp = [];	//保存所有船舶数据
	/**
	 * 初始化：创建播放器窗口、初始化播放条、初始化layer
	 */
	if(areaTrackData && map) {
		handlerRepClose();
		if(!$("head").find("link[href *='bootstrap-slider.min.css']").length){
			HthxMap.include.linkStyle(["map/extend/trackReplay/css/bootstrap-slider.min.css"]);
		}
		areaTrackDataTemp = areaTrackData.concat();
		HthxMap.trackReplayLayer = initLayer();
		createDialog();
		initParam();
		initSlide();
		//绘制起始位置
		drawAllPoint();				
//		drawPath();
		drawAllMarkers();
	}
	//参数初始化
	function initParam(){
		var g_trackLength = areaTrackData.length;
		for(var i = 0; i < g_trackLength; i++){
			pop_id[i] = 1;
			featureArrow[i] = null;
			point_index[i] = 0;
			
			shipFeatures[i] = [];
			markerFeatures[i] = [];
		}
		timeValue = 0;
		timeIndex = getTimeInterval();
		drag_flag = false;
	}
	//获取播放间隔时间
	function getTimeInterval(){
		return $("#timeInterval").val()*1000;
	}
	/**
	 * 创建播放器窗口、绑定事件
	 */
	function convertTime(str) {
		var month = str.getMonth() + 1;
		var day = str.getDate();
		var hour = str.getHours();
		var min = str.getMinutes();
		var sec = str.getSeconds();
		if(hour < 10){
			hour = '0' + hour;
		}
		if(min < 10){
			min = '0' + min;
		}
		if(sec < 10){
			sec = '0' + sec;
		}
		return month + "/" + day + " " + hour + ":" + min + ":" + sec;
	}
	/*
	 * 找出开始时间和结束时间
	 **/
	function findStartEndTime(areaTrackData){
		var startEndTime;
		//找出开始时间和结束时间
		var areaTrackDataLen = areaTrackData.length;	
		var timeDataTempLen = timeDataTemp? timeDataTemp.length : 0;
		if((timeDataTempLen == areaTrackDataLen) || !timeDataTempLen){
			startEndTime = areaTrackData.concat();
		}else{
			startEndTime = timeDataTemp.concat();
		}
		
		startEndTimeLen = startEndTime.length;
		startTime = startEndTime[0][0].recordTime;
		for(var i = 1; i < startEndTimeLen; i++){			
			if(startTime < startEndTime[i][0].recordTime){
				continue;
			}else{
				startTime = startEndTime[i][0].recordTime;				
			}
		}
		
		var childLen = startEndTime[0].length - 1;
		endTime = startEndTime[0][childLen].recordTime;
		for(var i = 1; i < startEndTimeLen; i++){
			var childLen_1 = startEndTime[i].length - 1;
			if(endTime < startEndTime[i][childLen_1].recordTime){
				endTime = startEndTime[i][childLen_1].recordTime;
			}else{
				continue;
			}
		}
		totalTime = endTime - startTime;
		var start_Time = new Date(startTime);
		var end_Time = new Date(endTime);
		var startYear = startTime.getFullYear();
		var endYear = endTime.getFullYear();
		if(startYear != endYear){
			startYear += ("-" + endYear + "年");
		}else{
			startYear += "年";
		}
		
		$('#dgRepLabel').html("区域回放\r\r\r"+startYear);
		$('#beginDate').html(convertTime(start_Time));
		$('#endDate').html(convertTime(end_Time));
	}
	
	function createDialog() {
		if(document.getElementById("dgRep")){
			handlerRepClose();
			return;
		}
		
		var trackPlay = '<div id="dgRep" class="rep_bg div_dragg" style="position:absolute;z-index:10000;right:430px;bottom: 252px;width:361px">'+
							'<div class="timePopHeader">'+
								'<label id="dgRepLabel" style="font-family: sans-serif;font-size: 14px;font-weight: bold;color: white;margin-left: 10px;margin-top: 5px;"></label>'+
//								'<i class="glyphicon glyphicon-new-window" onclick="bizTrackReplay.newTrackWindow()" title="弹出新窗口播放"></i>'+
								'<div id="rep_close">×</div>'+
							'</div>'+
							'<div style="">'+
								'<div id="slider_Container">'+
									'<input id="slider" data-slider-id="ex1Slider" type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="0"/>'+
									'<div id="beginDate"></div>'+
									'<div id="endDate"></div>'+
								'</div>'+
								'<div style="width: 100%;">'+  //height: 43px; 
									'<div id="kt" title="快退" class="rep_button rep_kt" ></div>'+
									'<div id="play" title="播放" class="rep_button  rep_play"></div>'+
									'<div id="kj" title="快进" class="rep_button  rep_kj"></div>'+
									'<div id="replay" title="重置" class="rep_button  rep_re"></div>'+
								'</div>'+
								'<div class="input-group" style="padding:8px;">'+
									'<select class="form-control select" id="timeInterval" style="width:61px;margin-left:22px;margin-top:-3px;height:30px;padding:0px;" >'+
								   		  '<option value="15">15s</option>'+
								   		  '<option value="30">0.5m</option>'+
								   		  '<option value="1800">0.5h</option>'+
									'</select>'+
								'</div>'+
								'<div id="label_kt">x2</div>'+
								'<div id="label_kj">x2</div>'+	
							'</div>'+
						'</div>';
		$("body").append(trackPlay);
		findStartEndTime(areaTrackData);
		HthxMap.dragDiv("dgRep");
	 	$("#play").unbind("click").bind("click", function(){
	 		removeUnCheckedFeatures(handlerPlay);
	 	});
		$("#kj").unbind("click").bind("click", function(){
	 		removeUnCheckedFeatures(handlerForward);
	 	});
		$("#kt").unbind("click").bind("click", handlerBack);
		$("#replay").unbind("click").bind("click", handlerReplay);
		$("#rep_close").unbind("click").bind("click", handlerRepClose);
	}
	
	/**
	 * 初始化layer，并给layer注册一系列feature相关的鼠标事件
	 */
	function initLayer() {
		var trackLayer;
		if(!HthxMap.trackReplayLayer){
			trackLayer = new ol.layer.Vector({
							source: new ol.source.Vector(),
						})
			map.addLayer(trackLayer);
			trackLayer.setProperties({id:"areaShipLayer"});
		}else{
			trackLayer = HthxMap.trackReplayLayer;
		}
		HthxMap.mouseEvent(map, "click", "areaShipLayer", trackDiv);
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
			natural_arrow_keys: true,
		});
		newSliderStyle();
		$("#slider").on("slideStart", function(slideEvt) {
			removeUnCheckedFeatures(handlerSlideStart);
		});
		$("#slider").on("slideStop", function(slideEvt) {
			handlerSlideEnd(slideEvt.value);
		});
	}
	function newSliderStyle(){
		$(".slider-track").css({"background-image":BSLIP}).css("height","5px");
		$(".slider-selection").css({"background-image":ASLIP});
		$(".slider-handle").css({"background-image":DBUTTON}).css("margin-top","-7px");
		$(".tooltip-inner").css("width","88px");
		$(".tooltip-inner").css("color","#177EE6");
		$(".tooltip-inner").removeClass("tooltip-inner");
		$(".tooltip-arrow").removeClass("tooltip-arrow");
		$(".tooltip-max").css("width", "0px");
		$(".tooltip-min").css("width", "0px");
		$(".tooltip-main").css("font-family","micro yahei").css("font-size","12px").css("margin-top","-22px");
	}
	/**
	 * 滑动条开始拖动事件
	 * val : 拖动前的当前值
	 */
	function handlerSlideStart(val) {
		drag_flag = true;
		clearInterval(g_timeoutTrack);
	};
	/**
	 * 滑动条拖动结束事件
	 * val : 拖动后的当前值
	 */
	function handlerSlideEnd(val){
		drag_flag = false;
		kt_flag = false;
		switch(val){
			case 0 : {
				playByStat("drag_start_play_forward");
			}break;
			case 100 : {
				if(g_speed_b != 1 ){
//					playByStat("drag_end_play_back");
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
		var id, terminalID;
		var source = layer.getSource();
		var features = source.getFeatures();
		if(features.length){
			for(var i = 0; i < features.length; i++){
				id = features[i].getProperties().id;
				terminalID = features[i].getProperties().terminalID;
				source.removeFeature(features[i]);
				map.removeOverlay(checkPopArray[id]);
				checkPopArray[id] = null;
				$('.'+terminalID).parent().remove();
			}	
		}
	}
	/**
	 * 格式化滑动条的tip显示字符串
	 */
	var drag_flag = false;
	function handlerTipFormatter(value){
		if(drag_flag){    //拖动的情况下
			currentTime = new Date(totalTime*value*0.01 + startTime.getTime());
		}else{
			if(!kt_flag){    //前进			
				if(value == 100){
					currentTime = endTime;
				}else if(timeValue > 0){
					currentTime = new Date(currentTime.getTime() + getTimeInterval());
				}else if(value == 0){
					currentTime = startTime;
				} 	
			}else{    //后退
				if(value == 100){
					currentTime = new Date(playEnd_time);
				}else if(value == 0){
					currentTime = startTime;
				}else{
					currentTime = new Date(currentTime.getTime() - getTimeInterval());
				}
			}
		}
		return convertTime(currentTime);
	};
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
		}else{
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
		g_speed_f = g_speed_f == 32?1:g_speed_f;
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
		}else if(g_speed_b == 2){  //每个船舶自减一个点
			var g_trackLength = areaTrackData.length;
			for(var i = 0; i < g_trackLength; i++){
				point_index[i]--;
			}
		}
		
		$("#label_kt").css("display","block").text(s);
		playByStat("play_back");
		HthxMap.removeOverlay(map);
	};
	/**
	 * 重新播放回调
	 */
	function handlerReplay(){
		areaTrackData = areaTrackDataTemp.concat();
//		initParam();
		playEnd = false;
		playFlag = false;
		playByStat('stop');
		timeValue = 0;
		timeDataTemp = [];
		$('#slider').slider("setValue", 0);
		HthxMap.removeOverlay(map);
		playTrack(0);
	};
	/**
	 * 关闭播放窗口回调
	 */
	function handlerRepClose(){
		if(HthxMap.trackReplayLayer && $("#dgRep").length){
		    removeFeature(HthxMap.trackReplayLayer);
		    map.removeLayer(HthxMap.trackReplayLayer);
		    HthxMap.trackReplayLayer = null;
		    HthxMap.removeOverlay(map);
			HthxMap.unMouseEvent();
			$("#slider").slider("destroy");
			$("#dgRep").remove();
			checkPopArray = [];
			//删除轨迹绘制层
			var areaTrackLayer = HthxMap.getLayerById("areaTrackLayer");
			if(areaTrackLayer){
				removeFeature(areaTrackLayer);
				map.removeLayer(areaTrackLayer);
			}
			//显示隐藏的跟踪船舶
			removeHidden();
		}
		g_speed_f = 1;
		g_speed_b = 1;
		clearInterval(g_timeoutTrack);
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
	    		kt_flag = false;
	    		playFlag = true;
	    		g_speed_b = 1;
	    		$("#label_kt").css("display","none");
	    		if(g_speed_f == 1){
	    			$("#label_kj").css("display","none");
	    		}
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
	    		clearInterval(g_timeoutTrack);
	    		$("#play").css({"background-image":REPPLAYPNG});
	    		document.getElementById("play").title = "播放";
				$("#label_kj").css("display", "none");
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
	    		kt_flag = true;
	    		playFlag = true;
				$("#play").css({"background-image":REPSTOPPNG});
				document.getElementById("play").title = "暂停";
				clearInterval(g_timeoutTrack);
				g_timeoutTrack = setInterval(updatePosition_KT, TIMEINTERVAL/g_speed_b);
	    	}break;
	    	/*
	    	 * 向后播放到起点
	    	 */
	    	case "play_back_start" : {
	    		g_speed_b = 1;
	    		g_speed_f = 1;
	    		playFlag = false;
	    		clearInterval(g_timeoutTrack);
	    		$("#label_kt").css("display","none");
	    		$("#play").css({"background-image":REPPLAYPNG});
	    		document.getElementById("play").title = "播放";
	    	}break;
	    	/*
	    	 * 拖动进度条到起点并向前播放
	    	 */
	    	case "drag_start_play_forward" : {
	    		playTrack(0);
	    	}break;
	    	case "drag_start_stop" : {
	    		
	    	}break;
	    	/************************
	    	 * 拖动进度条到终点
	    	 *************************/
	    	case "drag_end" : {
	    		g_speed_f = 1;
	    		playFlag = false;
	    		playEnd = true;
	    		$("#play").css({"background-image":REPPLAYPNG});
	    		document.getElementById("play").title = "播放";
				$("#label_kj").css("display","none");
				
				playTrack(1);
	    	}break;
	    	/***********
	    	 * 拖动进度条到终点，并向后播放
	    	 *****************/
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
	    		kt_flag = false;
	    		playFlag = true;
	    		$("#play").css({"background-image":REPSTOPPNG});
	    		document.getElementById("play").title = "暂停";
	    		g_timeoutTrack = setInterval(updatePosition,TIMEINTERVAL/g_speed_f);
	    	}break;
	    	/***************
	    	 * 拖动进度条到中间任意位置，并向后播放
	    	 *****************/
	    	case "drag_center_play_back" : {
	    		playFlag = true;
	    		$("#play").css({"background-image":REPSTOPPNG});
	    		document.getElementById("play").title = "暂停";
	    		g_timeoutTrack = setInterval(updatePosition_KT,TIMEINTERVAL/g_speed_b);
	    	}break;
	    	case "drag_center_stop" : {
	    		
	    	}break;
		}
	}
	/**
	 * 按照拖动的百分比播放轨迹
	 */
	function playTrack(percent){
		if(percent == 0){	//初始化船舶，可以重新选择想要播放的船舶
			playEnd = false;
			playFlag = false;
			playByStat('stop');
			
			timeValue = 0;
//			timeDataTemp = [];
			checkPopArray = [];
			findStartEndTime(areaTrackData);
		}else if(percent == 1){
			playFlag = true;
		}
		HthxMap.removeOverlay(map);
		removeFeature(HthxMap.trackReplayLayer);
		initParam();
		drawAllPoint();				
//		drawPath();
		drawAllMarkers();
		if(percent == 0){
			$('.checkedShip').attr('checked', false);
		}else{
			$('.checkedShip').attr('checked', 'checked');
		}
		
		var timeIndexTemp = totalTime*percent;
		for(;timeIndex <= (timeIndexTemp + getTimeInterval());){
			drawFeatures();
		}
	}
	
	function drawFeatures(){
		timeIndex += getTimeInterval();
		var g_trackLength = areaTrackData.length;
		if(timeIndex >= (totalTime + getTimeInterval())){
			playEnd = true;
			playEnd_interval = timeIndex - 2*getTimeInterval();
			playEnd_time = currentTime;
			playByStat("play_forward_end");
			if($('#slider').slider("getValue") != 100){
				$('#slider').slider("setValue", 100);
			}	
		}		
		while(true){
			for(var i = 0; i < g_trackLength; i++){
				if(areaTrackData[i] && (point_index[i] <= (areaTrackData[i].length-1)) && (areaTrackData[i][point_index[i]].recordTime <= currentTime)){    //绘制未执行完成 
					flag = false;
					break;
				}else{
					flag = true;    //执行完成
				}
			}
			if(flag){
				break;
			}else{
				drawPoint();				
				drawPath();
				drawMarker();
			}
		}
		if($('#slider').slider("getValue") == 100){
			kt_flag = true;
		}
	}
	/**
	 * 更新位置
	 */
	var playEnd_time, timeValue;
	function updatePosition(){
		drawFeatures();
		if(timeIndex > getTimeInterval() && $('#slider').slider("getValue") < 100){
			timeValue = 100*(timeIndex-getTimeInterval())/totalTime;
			$('#slider').slider("setValue", timeValue);
		}	 	
	}
	/**
	 * 快退模式更新位置
	 */
	function updatePosition_KT(){
		if(playEnd){
			timeIndex = playEnd_interval;
			currentTime = new Date(playEnd_time);
			playEnd = false;
		}else{
			timeIndex -= getTimeInterval();
		}
		
		var g_trackLength = areaTrackData.length;
		if(timeIndex <= 0){
			playByStat("play_back_start");
			if($('#slider').slider("getValue") != 0){
				$('#slider').slider("setValue", 0);
			}
		}
		
		while(true){
			for(var i = 0; i < g_trackLength; i++){
				if(areaTrackData[i] && point_index[i] >= 0 && (areaTrackData[i][point_index[i]].recordTime > currentTime)){    //未执行完成 
					flag = false;
					break;
				}else{
					flag = true;    //执行完成
				}
			}
			if(flag){
				break;
			}else{
				var areaTrackDataTmp;
				for(var i = 0; i < g_trackLength; i++){
					if(!areaTrackData[i]){
						continue;
					}
					areaTrackDataTmp = areaTrackData[i][point_index[i]];
					if(point_index[i] >= 0 && areaTrackDataTmp.recordTime > currentTime){
						var source = HthxMap.trackReplayLayer.getSource();
						//删除当前船
						var currentMarker = source.getFeatureById("target" + i + "_" + point_index[i]);
						if(currentMarker && point_index[i] > 0){
							source.removeFeature(currentMarker);
							//增加前一个船
							var hiddenMarker = source.getFeatureById("target" + i + "_" + (point_index[i]-1));
							var markerFeatureTmp = markerFeatures[i][point_index[i]];
							if(!hiddenMarker && markerFeatureTmp){
								source.addFeature(markerFeatureTmp);
								var lon = markerFeatureTmp.getProperties().pop_lon;
								var lat = markerFeatureTmp.getProperties().pop_lat;
								checkPopArray[areaTrackDataTmp.id].setPosition([lon, lat]);
								markerFeatures[i][point_index[i]] = null;
							}
							
							//删除当前点和线
							var currentPoint = source.getFeatureById("Point" + i + "_" + (point_index[i]-1));
							var currentPath = source.getFeatureById("Path" + i + "_" + (point_index[i]-1));
							if(currentPoint){  
								source.removeFeature(currentPoint);
							}
							if(currentPath){
								source.removeFeature(currentPath);
							}
						}
						point_index[i]--;							
					}				
				}
			}
		}	
		if($('#slider').slider("getValue") == 0){
			initParam();
			kt_flag = false;
			return;
		}
		$('#slider').slider("setValue", 100*timeIndex/totalTime);
	}
	/**
	 * 绘制轨迹回放目标对象marker,小船
	 * lon ： 经度
	 * lat ： 纬度
	 */
	function drawMarker(){
		var markerData, historyTime;
		var g_trackLength = areaTrackData.length;
		for(var i = 0; i < g_trackLength; i++){    //循环船的个数
			if(areaTrackData[i] && (areaTrackData[i].length-1) >= point_index[i] && point_index[i] >= 0){ 
				markerData = areaTrackData[i][point_index[i]];
				historyTime = markerData.recordTime;    //当前点的时间
			}else{
				continue;
			}
			
			if(historyTime <= currentTime){    //执行绘制,每次只绘制一个点
				var lon = markerData.x;
				var lat = markerData.y;
				var source = HthxMap.trackReplayLayer.getSource();
				var feature = source.getFeatureById("target" + i + "_" + (point_index[i]-1));
				if(feature){
					markerFeatures[i][point_index[i]] = feature;
					source.removeFeature(feature);
				}
				
				var featureMarker = new ol.Feature(new ol.geom.Point([lon,lat]));
				checkPopArray[markerData.id].setPosition([lon,lat]);
				featureMarker.setId("target" + i + "_" + point_index[i]);
				featureMarker.setProperties({
					pop_shipName : markerData.pop_shipName,
					shipType : markerData.shipType,
					shipDir : markerData.shipDir,
					pop_shipSpeed : markerData.pop_shipSpeed,
					pop_lon : lon,
					pop_lat : lat,
					terminalType: markerData.terminalType,
					status: markerData.status,
					pop_date : markerData.recordTime,
					heading : markerData.heading,
					terminalID: markerData.terminalId,
					id: markerData.id,
					pop_id: pop_id[i]++,    //一个点绘制完成
					pop_index: i
				});
				MARKERPNG = HthxMap.createShipImg(featureMarker, true);
				HthxMap.changeShipImg(featureMarker, markerData.shipDir, [MARKERPNG]);
				source.addFeature(featureMarker);
				if(!kt_flag){
					point_index[i]++;
				}
			}
		}
	}
	/**
	 * 绘制轨迹点,航向箭头
	 */
	function drawPoint(){
		var pointData, historyTime, id;
		var g_trackLength = areaTrackData.length;
		for(var i = 0; i < g_trackLength; i++){	
			if(areaTrackData[i] && (areaTrackData[i].length-1) >= point_index[i]){
				pointData = areaTrackData[i][point_index[i]];
				historyTime = pointData.recordTime;    //当前点的时间
			}else{
				continue;
			}
			
			if(historyTime <= currentTime){    //执行绘制				
				if(featureArrow[i]){
					featureArrow[i].setProperties({"pop_id": pop_id[i]++, "pop_index": i});
					HthxMap.trackReplayLayer.getSource().addFeature(featureArrow[i]);
				}
				
				var lon = pointData.x;
				var lat = pointData.y;
				var pngurl = ARROWPNG;
				var feature = new ol.Feature(new ol.geom.Point([lon, lat]));
				feature.set("terminalTypeImage", pngurl);
				feature.setId("Point" + i + "_" + point_index[i]);
				feature.setStyle(new ol.style.Style({
					image: new ol.style.Icon({
					    opacity: HthxMap.opacity,
					    rotation: HthxMap.transformImgRotation(pointData.shipDir),
						src: pngurl
					})						
				}))
				feature.setProperties({
					pop_shipName : pointData.pop_shipName,
					shipType : pointData.shipType,
					shipDir : pointData.shipDir,
					pop_shipSpeed : pointData.pop_shipSpeed,
					pop_lon : lon,
					pop_lat : lat,
					terminalType: pointData.terminalType,
					status: pointData.status,
					heading : pointData.heading,
					id: pointData.id,
					pop_date : pointData.recordTime,
				})
				featureArrow[i] = feature;
			}
		}
	};
	/**
	 * 绘制轨迹路径
	 * lon ： 经度
	 * lat ： 纬度
	 */
	function drawPath(){		
		var g_trackLength = areaTrackData.length;
		var historyTime, pathData;
		for(var i = 0; i < g_trackLength; i++){	
			if(point_index[i] != 0){
				if(areaTrackData[i] && (areaTrackData[i].length-1) >= point_index[i]){
					pathData = areaTrackData[i][point_index[i]];
					historyTime = pathData.recordTime;    //当前点的时间
				}else{
					continue;
				}
				
				if(historyTime <= currentTime){    //执行绘制
					var lon = pathData.x;
					var lat = pathData.y;
					
					var lineArr = [];
					var count = 50;
					var countX = (lon - areaTrackData[i][point_index[i] - 1].x)/count;
					var countY = (lat - areaTrackData[i][point_index[i] - 1].y)/count;
					for(var j = 0; j < count; ){
						var pointArr = [];
						pointArr.push([areaTrackData[i][point_index[i] - 1].x+countX*j, areaTrackData[i][point_index[i] -1 ].y+countY*j]);
						pointArr.push([areaTrackData[i][point_index[i] - 1].x+countX*(j+1), areaTrackData[i][point_index[i] - 1].y+countY*(j+1)]);
						lineArr.push(pointArr);
						j += 2;
					}
					
					var path = new ol.Feature(new ol.geom.MultiLineString(lineArr));
					path.setStyle(new ol.style.Style({
						stroke: new ol.style.Stroke({
							width: 2,
				            color: '#3399CC'
						})
					})) 
					path.setProperties({
						id: pathData.id,
						"pop_shipName": "line",
						"pop_id": pop_id[i]++, 
						"pop_index": i});
					path.setId("Path" + i + "_" + (point_index[i]-1));
					path.set("noClickEvent", true);
					HthxMap.trackReplayLayer.getSource().addFeature(path);
				}
			}
		}
	}
	/**
	 * 绘制轨迹回放所有目标对象marker起始点,小船
	 */
	function drawAllMarkers(){
		var allMarkersData;
		var g_trackLength = areaTrackData.length;
		for(var i = 0; i < g_trackLength; i++){    //循环船的个数
			if(!areaTrackData[i]){
				continue;
			}
			allMarkersData =  areaTrackData[i][point_index[i]];
			var lon = allMarkersData.x;
			var lat = allMarkersData.y;
			var featureMarker = new ol.Feature(new ol.geom.Point([lon,lat]));
			featureMarker.setId("target" + i + "_" + point_index[i]);
			featureMarker.setProperties({
				pop_shipName : allMarkersData.pop_shipName,
				shipType : allMarkersData.shipType,
				shipDir : allMarkersData.shipDir,
				pop_shipSpeed : allMarkersData.pop_shipSpeed,
				pop_lon : lon,
				pop_lat : lat,
				terminalType: allMarkersData.terminalType,
				status: allMarkersData.status,
				pop_date : allMarkersData.recordTime,
				heading : allMarkersData.heading,
				terminalID: allMarkersData.terminalId,
				id: allMarkersData.id,
				pop_id: pop_id[i]++,    //一个点绘制完成
				pop_index: i
			});
			markerFeatures[i][point_index[i]] = featureMarker;
			checkPop(featureMarker, areaTrackDataTemp.indexOf(areaTrackData[i]));	//显示选择框
			MARKERPNG = HthxMap.createShipImg(featureMarker, true);
			HthxMap.changeShipImg(featureMarker, allMarkersData.shipDir, [MARKERPNG]);
			HthxMap.trackReplayLayer.getSource().addFeature(featureMarker);
			point_index[i]++;
		}
	}
	/**
	 * 绘制所有航向箭头
	 */
	function drawAllPoint(){
		var pointData;
		var g_trackLength = areaTrackData.length;
		for(var i = 0; i < g_trackLength; i++){	
			if(!areaTrackData[i]){
				continue;
			}
			pointData = areaTrackData[i][point_index[i]];
			var lon = pointData.x;
			var lat = pointData.y;
			var pngurl = ARROWPNG;
			var feature = new ol.Feature(new ol.geom.Point([lon, lat]));
			feature.set("terminalTypeImage", pngurl);
			feature.setId("Point" + i + "_" + point_index[i]);
			feature.setStyle(new ol.style.Style({
				image: new ol.style.Icon({
				    opacity: HthxMap.opacity,
				    rotation: HthxMap.transformImgRotation(pointData.shipDir),
					src: pngurl
				}),
				text: new ol.style.Text({
					text:  '起点：'+pointData.pop_shipName,
					font: '"Microsoft YaHei","微软雅黑"',
					textAlign: "lm",
					fill: new ol.style.Stroke({
						color: "white"
					})
				})
			}))
			feature.setProperties({
				pop_shipName : pointData.pop_shipName,
				shipType : pointData.shipType,
				shipDir : pointData.shipDir,
				pop_shipSpeed : pointData.pop_shipSpeed,
				pop_lon : lon,
				pop_lat : lat,
				terminalType: pointData.terminalType,
				status: pointData.status,
				heading : pointData.heading,
				id: pointData.id,
				pop_date : pointData.recordTime,
			})
			featureArrow[i] = feature;
		}
	};
	/*
	 *  船舶选择框
	 **/
	function checkPop(feature, index){
		var featureProperties = feature.getProperties();
		var strs = '<div class="'+featureProperties.terminalID+'" style="margin-bottom: 8px;">'+
						'<div class="panel-body bubbleboxcontent" style="padding: 1px;">'+
							'<ul class="bubbleboxMsg clearfix" style="margin-left: -40px; margin-bottom: 0px;font-size: 12px;color: white;">'+
								'<li><input id="'+featureProperties.terminalID+'" class="checkedShip '+featureProperties.id+'"type="checkbox"><label for="'+featureProperties.terminalID+'">'+featureProperties.pop_shipName+'</label></li>' + 
							'</ul>'+	
						'</div>'+
					'</div>';
		
		var element = document.createElement("div");
		element.className = "trackPop";
		$('body').append(element);
		element.innerHTML = strs;
		var popup = new ol.Overlay({    //保证船只弹框随船移动而移动，应该修改
			element: element,
			positioning: 'bottom-center',
			autoPan: false,
		    stopEvent: false
		});
		checkPopArray[featureProperties.id] = popup;
		map.addOverlay(popup);
		//绑定船舶选择框
		$(".trackPop ul ."+featureProperties.id).unbind('click').bind("click", function(){
			playOrRemove(featureProperties.id, index);
		});
		
	    popup.setPosition([featureProperties.pop_lon, featureProperties.pop_lat]);
	}
	
	/*
	 * 播放过程中取消船舶勾选
	 */
	var timeDataTemp = [];
	function playOrRemove(id, index){
		var check = $(".trackPop ul ."+id)[0].checked;
		if(check){	//选择
			timeDataTemp.push(areaTrackDataTemp[index]);
			findStartEndTime(timeDataTemp);
			$('#slider').slider('setValue', 0);
		}else if(!check && $('#slider').slider("getValue")){	//移除船舶
			//暂停
			playByStat("stop");
			if(timeDataTemp.length == 1){
				Messager.alert({Msg:'您不能移除唯一的一条船舶！', isModal: true}).on(function(){
					//播放
					$(".trackPop ul ."+id)[0].checked = true;
					playByStat("play_forward");
					return;
				});
			}else{
				Messager.confirm({Msg:'您正在移除该船舶，是否继续?', icon:'question'}).on(function(flag){
	        		if(flag){
	        			removeFeatureById(id);
	        			timeDataTemp.splice(timeDataTemp.indexOf(areaTrackDataTemp[index]), 1);
	        			areaTrackData[areaTrackData.indexOf(areaTrackDataTemp[index])] = null;
	        			
	        			findStartEndTime(timeDataTemp);
	        			if(currentTime < startTime){
	        				timeValue = 0;
	        				$('#slider').slider('setValue', 0);
	        				HthxMap.removeOverlay(map);
	        				playTrack(0);
	        				$('.checkedShip').attr('checked', 'checked');
	        			}else{
	        				handlerSlideEnd($('#slider').slider('getValue'));
	        				return;
	        			}
	        		}else{
	        			 $(".trackPop ul ."+id)[0].checked = true;
	        		}
	        		//播放
	        		playByStat("play_forward");
				})
			}
		}else if(!check){
			timeDataTemp.splice(timeDataTemp.indexOf(areaTrackDataTemp[index]), 1);
			
			if(timeDataTemp.length){
				findStartEndTime(timeDataTemp);
			}else{
				findStartEndTime(areaTrackData);
			}
			$('#slider').slider('setValue', 0);
		}
	}
	
	/*
	 * 根据id删除feature
	 */
	function removeFeatureById(featureId){
		var source = HthxMap.trackReplayLayer.getSource();
		var features = source.getFeatures();
		if(features.length){
			for(var i = 0; i < features.length; i++){
				if(features[i].getProperties().id == featureId){
					source.removeFeature(features[i]);
					map.removeOverlay(checkPopArray[featureId]);	
        			checkPopArray[featureId] = null;
        			$('.'+featureId).parent().remove();
				}
			}
		}
	}
	
	/*
	 *播放前检测没有勾选的船舶，并移除没有勾选的船舶
	 **/
	function removeUnCheckedFeatures(func){
		//删掉没有勾选的船舶
		if(!timeDataTemp.length){
			Messager.confirm({Msg:"您没有进行选择，默认播放所有船舶，是否继续？", icon:'question'}).on(function(flag){
				if(flag){
					$('.checkedShip').attr('checked', 'checked');
					timeDataTemp = areaTrackData.concat();
					func();
				}/*else if(func === handlerSlideStart){
					handlerReplay();
				}*/
			}); 
		}else{
			for(var i = 0; i < areaTrackData.length; i++){    //循环船的个数
				if(!areaTrackData[i]){
					continue;
				}
				var id = areaTrackData[i][0].id;
				var check = $(".trackPop ul ."+id)[0].checked;
				if(!check){
	    			//移除feature
					removeFeatureById(id);
					areaTrackData[i] = null;
				}
			}
			func();
		}
	}
}
