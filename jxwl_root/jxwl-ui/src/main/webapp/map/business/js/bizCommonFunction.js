/**
 * <p>Description: 不同比例尺下，渔船的图片显示</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-11-26 </p>
 * @author: 何泽潘
 * @param: feature(属性),trackFlag(轨迹下不区分比例尺)
 * @return 
 */
HthxMap.createShipImg = function(feature, trackFlag){
	var pngUrl;
	var properties = feature.getProperties();
//	if(HthxMap.curMapLevel() || trackFlag){
//		if(HthxMap.curShipImgLevel()){    
//			pngUrl = HthxMap.Settings.configData.targetImg.normal;
//		}else{
//			pngUrl = HthxMap.Settings.configData.targetImg.Larger;
//		}
//	}
		//根据标志位判断当前车辆的 处于的状态	
	var alarmNum = 0;
	alarmNum = parseInt(feature.getProperties().alarmNum);
	if("嘉兴位置服务平台" == properties.orgin){
		pngUrl = HthxMap.Settings.configData.targetImg.normal;
	}else{
		pngUrl = HthxMap.Settings.configData.targetImg.normalGPS;
	}	
	feature.set("terminalTypeImage", pngUrl);
	return pngUrl;
}



/**
 * <p>Description: 在线终端类型对应图片偏移</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-03-16 </p>
 * @author: 何泽潘
 * @param: terminalType(终端类型,不传参表示离线)
 * @return 
 */
HthxMap.imgOffset = function(terminalType, size){
	var offset = [];
	if(size == 16 || size == 50){
		offset = [0,0];
		return offset;
	}
	switch(terminalType){
		case HthxMap.Settings.TerminalType.c90: offset = [0,0]; break;
		case HthxMap.Settings.TerminalType.Beidou: offset = [size*2,0]; break;
		case HthxMap.Settings.TerminalType.GSM: offset = [size*4,0]; break;
		case HthxMap.Settings.TerminalType.AISClassA: offset = [size*6,0]; break;
		case HthxMap.Settings.TerminalType.AISClassB: offset = [size*8,0]; break;		
		default: offset = [0,0];
	}
	return offset;
}
/**
 * <p>Description: 根据图片名判断图片在X方向的偏移</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-03-30 </p>
 * @author: 何泽潘
 * @param: shipImg(船舶图片数组)
 * @return 
 */
HthxMap.imgOffsetX = function(shipImg){
	var imgName = shipImg.split(".")[0].split("/");
	imgName = imgName[imgName.length-1];
	var size;
	switch(imgName){
		case "normal": size = 30; break;
		case "normalGPS": size = 30; break;
		case "normalSearch": size = 30; break;
		case "normal_Larger": size = 32; break;
		case "normal_LargerSearch": size = 40; break;	
		case "normalClicked": size = 38; break;
		case "normalGPSClicked": size = 38; break;
		case "alarm": size = 39; break;//报警状态
		case "alarmClicked": size = 45; break;//报警状态点击放大
		case "alarmGPS": size = 39; break;//报警状态
		case "alarmGPSClicked": size = 45; break;//报警状态点击放大
		case "offline": size = 30; break;//离线状态
		case "offlineClicked": size = 38; break;//离线状态点击放大
		case "tracking": size = 50; break; //跟踪
		case "tracking_Larger": size = 50; break; //跟踪
		case "normal_LargerClicked": size = 64; break;
		case "startP": size = 48; break;//起点图标
		case "endP": size = 48; break;//终点图标
		default: size = 100;	
	}
	return size;
}

/**
 * <p>Description: 根据终端拼接不同图片</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-11-26 </p>
 * @author: 何泽潘
 * @param: pngUrl：基础图片路径，pngSuffix：后缀图片路径,数组
 * @return:  pngResult：图片数组路径
 */
HthxMap.shipImgJoint = function(pngUrl, pngSuffix){
	if(!pngUrl){
		return [];
	}
	var pngResult = [];
	pngResult.push(pngUrl);
	var pngBase = pngUrl.split(".");
	var pngSuffixLen = pngSuffix.length;
	for(var i = 0; i < pngSuffixLen; i++){
		pngResult.push(pngBase[0] + pngSuffix[i] + "." + pngBase[1]);
	}
	return pngResult;
}
/**
 * <p>Description: 改变渔船图片样式，适用于新增</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-11-26 </p>
 * @author: 何泽潘
 * @param: feature,shipDir(航向),shipImg(图片数组)
 * @return 
 */
HthxMap.changeShipImg = function(feature, shipDir, shipImg){
	var properties = feature.getProperties();	
	var style = feature.getStyle();
	var shipImgLen = shipImg.length;
	var styleArr = [];
	var text = [];
	var newStyle, color, offset;
	var carStateFlag  = properties.carState;
	for(var i = 0; i < shipImgLen; i++){
		var size = HthxMap.imgOffsetX(shipImg[i]);
		offset = HthxMap.imgOffset(properties.terminalType, size);
		if(shipImg[i] === "/JXWL/map/business/img/ship/startP.png"||
				shipImg[i] === "/JXWL/map/business/img/ship/endP.png"){
			newStyle = new ol.style.Style({
				image: new ol.style.Icon({
			        opacity: HthxMap.opacity,
				    src:  shipImg[i],
					offset: [0,0],
					size: [size, size*2]
				}), 
				text: (style && style[i] && style[i].getText()) || undefined
			});
		}else{
			newStyle = new ol.style.Style({
				image: new ol.style.Icon({
			        opacity: HthxMap.opacity,
				    src:  shipImg[i],
					offset: [0,0],
					size: [size, size]
				}), 
				text: (style && style[i] && style[i].getText()) || undefined
			});
		}
		styleArr.push(newStyle);
	}
	styleArr.push(new ol.style.Style({
		image: new ol.style.Circle({
	        radius: 12
		})
	}));
	feature.setStyle(styleArr);
	
	
	
	
	/*var properties = feature.getProperties();	
	var style = feature.getStyle();
	var shipImgLen = shipImg.length;
	var styleArr = [];
	var text = [];
	var newStyle, color, offset;
	var online = (properties.status == HthxMap.Settings.onOffLine.onLine);
	if(online && properties.id !== properties.terminalID){   //注册在线
		color = HthxMap.getTypeColor(properties.shipType);
	}else if(properties.id === properties.terminalID){	//未注册
		color = HthxMap.Settings.unRegisterColor;
	}else{
		color = HthxMap.Settings.offLineColor;
	}
	if(style && style.length == undefined){
		style = [style];
	}
	for(var i = 0; i < shipImgLen; i++){
		var size = HthxMap.imgOffsetX(shipImg[i]);
		if(properties.id === properties.terminalID){  //未注册船舶
			if(size == 16 || size == 50){	//跟踪和轨迹箭头
				offset = [0, 0];
			}else{
				offset = [size*10, 0];
			}
		}else{
			offset = HthxMap.imgOffset(properties.terminalType, size);
		}
		newStyle = new ol.style.Style({
			image: new ol.style.Icon({
		        opacity: HthxMap.opacity,
		        rotation: HthxMap.transformImgRotation(shipDir),
			    src:  shipImg[i],
				offset: [0,0],
				size: [size, size]
			}), 
			text: (style && style[i] && style[i].getText()) || undefined
		});
		styleArr.push(newStyle);
	}
	styleArr.push(new ol.style.Style({
		image: new ol.style.Circle({
	        radius: 12
		})
	}));
	feature.setStyle(styleArr);*/
}


//清除渔船图层上的标记 
function clearMonitorMarker(monitLayer){
	HthxMap.removeOverlay(map);
	var feature = monitLayer.getSource().getFeatures();
	var featureLen = feature.length;
	for(var j = 0; j < featureLen; j++){
		var style = feature[j].getStyle();
		if(!feature[j].getProperties().shipStatus){
			if(style && style.length == 3){
				feature[j].setStyle([style[0], style[2]]);
				if(feature[j].getProperties().shipSearch){  //删除搜索状态
					feature[j].setProperties({"shipSearch": false});	
				}
			}
		}else{
			if(style && style.length == 4){
				feature[j].setStyle([style[0], style[2], style[3]]);
			}
			if(feature[j].getProperties().shipSearch){  //删除搜索状态
				feature[j].setProperties({"shipSearch": false});	
			}
		}
	}
}

function removeShipSearchProperties(features){
	var featuresLen = features.length;
	for(var i = 0; i < featuresLen; i++){
		if(features[i].getProperties().shipSearch){
			features[i].setProperties({"shipSearch": false});
		}
	}
}



//添加wayBillId参数  为了删除路径规划中的线和图标
//渔船弹出框关闭
function removePopup(shipId,wayBillId){
    HthxMap.curFeatureInteraction = 1;
    setTimeout(function(){
    	HthxMap.curFeatureInteraction = null;
    },500);
	if(bizCommonSetting.atmosphereStyle){
		HthxMap.getLayerById("featureLayer").getSource().removeFeature(bizCommonSetting.atmosphereStyle);
		bizCommonSetting.atmosphereStyle = null;
	}
	if(wayBillId && wayBillId !=="undefined"){
		removeCarRoute(wayBillId);
	}
	removeClickPop();
	var layer = HthxMap.getLayerById("monitorLayer");
	var feature = layer.getSource().getFeatureById(shipId);
	if(feature){
		feature.set("clickPop", false);
		HthxMap.removeShipStyle();
	}
	if($(".map-default")){
		$(".map-default").removeClass("active"); 
	}
	clearAccountAndCarrierName();
}
//删除路径规划图标和线
function removeCarRoute(wayBillId){
	//删除起点和终点图标
	//图标在monitLayer层
	var source = monitLayer.getSource();
	var feature = source.getFeatureById(wayBillId+'0');
	if( feature && feature != null){
		source.removeFeature(feature);
	}
	feature = source.getFeatureById(wayBillId+'1');
	if( feature && feature != null){
		source.removeFeature(feature);
	}
	//删除线
	//线在featureLayer层
	var featureLayer =  HthxMap.getLayerById("featureLayer");
	source = featureLayer.getSource();
	feature = source.getFeatureById(wayBillId);
	if (feature && feature != null){
		source.removeFeature(feature);
	}
}
function removeClickPop(){
	var getShow = $("#popUp").css("display");    //点击弹框
	//移除点击弹框 
	if(getShow && (getShow=="inline" || getShow=="block")){
		//移除地图下侧检测信息栏
		$("#footer-panel").css("display","none");
    	$("#popUp").remove();
    	var overlays = map.getOverlays();
		var overlaysLen = overlays.getLength();
		for(var j = 0; j < overlaysLen; j++){
			var item = overlays.item(j);
			if(item.get("clickPop")){
				item.set("clickPop", false);
				map.removeOverlay(item);
				break;
			}
		}
	}
	HthxMap.removeShipStyle();
}

/**
 * 关闭地图下部弹窗
 * */
function removeBottom(){
	$("#footer-panel").css("display","none");
}

//创建标记弹框及内容flag,true:不创建弹框
function createMarkerPop(map, markerObj, flag){
	var lon, lat;
	var featureId = "";
	var markerlist = "";
	var name = markerObj.name;
	var points = markerObj.points;
	var desc = markerObj.desc;
	var icon = markerObj.icon;
	var id = markerObj.id;
	var radius = markerObj.radius;
	if(points[0] instanceof Array){
		var lonArray = [];
		var latArray = [];
		points = HthxMap.keepCoorsLen(points, bizCommonVariable.coorsLen);
		for(var i = 0; i < points.length; i++){
			lonArray.push(points[i][0]);
			latArray.push(points[i][1]);
		}
		lon = lonArray;
		lat = latArray;
		featureId = HthxMap.measureIndex;
		radius = "";
	}else{
		var point = HthxMap.keepCoorsLen([points], bizCommonVariable.coorsLen);
		lon = point[0][0];
		lat = point[0][1];
		points = [lon, lat];
		if(radius){
			featureId = HthxMap.measureIndex;
		}else{
			radius = "";
		}
	}
	var coordinates = points;
	var markers = HthxMap.Settings.IMG.marker;
	var markertitle = HthxMap.Settings.IMG.markerName;
	for(var i=0; i< markers.length; i++){
		var pngUrl = markers[i];
		markerlist += '<li class="markerIconList"><img title="'+markertitle[i]+'" src="'+pngUrl+'"/></li>';
	}
	var strArr = [];
	strArr.push('<div class="panel" id="remarkPoint">');
	strArr.push('<div class="panel-heading">');
	strArr.push('<label class="panel-title">标记信息</label>');
	strArr.push('</div>');
	strArr.push('<div class="panel-padding">');
	strArr.push('<form class="form" id="markerForm">');
	strArr.push('<div class="row row-group">');
	strArr.push('<label class="dialog-label must">名称</label>');
	strArr.push('<div class="inputout input-group-sm">');
	strArr.push('<input type="text" id="markerName" class="form-control" placeholder="请输入1~20个字符" maxlength="20" value="'+name+'">');
	strArr.push('</div>');
	strArr.push('</div>');
	strArr.push('<div class="row row-group">');
	strArr.push('<label class="dialog-label must">北纬</label>');
	strArr.push('<div class="inputout input-group-sm">');
	strArr.push('<input type="text" id="markerLat" readonly class="form-control float-type" value="'+lat+'">');
	strArr.push('<label class="">度</label>');
	strArr.push('</div>');
	strArr.push('</div>');
	strArr.push('<div class="row row-group">');
	strArr.push('<label class="dialog-label must">东经</label>');
	strArr.push('<div class="inputout input-group-sm">');
	strArr.push('<input type="text" id="markerLon" readonly class="form-control float-type" value="'+lon+'">');
	strArr.push('<label class="">度</label>');
	strArr.push('</div>');
	strArr.push('</div>');
	strArr.push('<div>');
	strArr.push('<input id="convertDegrees" style="margin-left:50px;" type="checkbox" onclick="convertDegreesToStringHDMS()"/>'+'<label for="convertDegrees">显示转换</label> ');
	strArr.push('</div>');
	strArr.push('<div id="marker" class="row row-group hidden">');
	strArr.push('<label class="dialog-label">半径</label>');
	strArr.push('<div class="inputout input-group-sm">');
	strArr.push('<input type="text" id="markerRadius" readonly class="form-control float-type" value="'+radius+'">');
	strArr.push('<label class="">米</label></div></div>');
	strArr.push('<div class="row row-group">');
	strArr.push('<label class="dialog-label">图标</label>');
	strArr.push('<div class="inputout remark-icon">');
	strArr.push('<ul id="markerlist" style="">');
	strArr.push(markerlist);
	strArr.push('</ul></div></div>');
	strArr.push('<div class="row row-group">');
	strArr.push('<label class="dialog-label">备注</label>');
	strArr.push('<div class="inputout input-group-sm">');
	strArr.push('<textarea rows="3" cols="5" class="form-control" id="markerContent" placeholder="请输入0~200个字符" maxlength="200">'+desc+'</textarea>');
	strArr.push('</div></div>');
	strArr.push('<div id="featureId" value="'+featureId+'" class="hidden"></div>');
	strArr.push('<div id="markerCoordinates" value="'+coordinates+'" class="hidden"></div>');
	//strArr.push('<div id="historyMarkerId" class="hidden" value="'+id+'"></div>');
	strArr.push('<p class="p-center">');
	strArr.push('<input type="button" class="btn btn-primary" id="markerOK" value="确定"/>&nbsp;&nbsp;&nbsp;');
	strArr.push('<input type="button" class="btn btn-default"  id="markerCancel" value="删除" onclick="closePanel(this, true)"/>');
	strArr.push('</p>');
	strArr.push('</form><div id="featureId" value="'+HthxMap.measureIndex+'" style="display:none"></div></div></div>');			
	var contentStr = strArr.join("");
	if(!(icon instanceof Number)){
		icon = parseInt(icon);
	}
	var png = HthxMap.Settings.IMG.marker[icon];
	HthxMap.markerIndex[featureId] = true;
	if(points[0] instanceof Array){
		HthxMap.addMarker(map, bizCommonVariable.markerObj[id].interiorPoint, contentStr, png, "", flag);
	}else{
		HthxMap.addMarker(map, coordinates, contentStr, png, "", flag);
	}
	if(featureId >= 0){
		HthxMap.measureIndex++;
	}
}

//将浮点数转换为度分秒
function convertDegreesToStringHDMS(){
	var checkedParent = $("#convertDegrees").parent();
	if($("#convertDegrees")[0].checked){
		var tempCoor = [];
		bizCommonVariable.markerFloat[0] = $("#markerLon").val();
		bizCommonVariable.markerFloat[1] = $("#markerLat").val();
		if(bizCommonVariable.markerFloat[0].indexOf(",") === -1){    //单个点
			$("#markerLon").val(layerControlArray.degreesToStringHDMS(JSON.parse(bizCommonVariable.markerFloat[0])));
			$("#markerLat").val(layerControlArray.degreesToStringHDMS(JSON.parse(bizCommonVariable.markerFloat[1])));
		}else{
			var lonArray = [];
			var latArray = [];
			tempCoor[0] = bizCommonVariable.markerFloat[0].split(",");
			tempCoor[1] = bizCommonVariable.markerFloat[1].split(",");
			for(var i = 0; i < tempCoor[0].length; i++){
				lonArray.push(layerControlArray.degreesToStringHDMS(JSON.parse(tempCoor[0][i])));
				latArray.push(layerControlArray.degreesToStringHDMS(JSON.parse(tempCoor[1][i])));
			}
			$("#markerLon").val(lonArray);
			$("#markerLat").val(latArray);
		}
		checkedParent.prev().prev().find('label').last().text("");
		checkedParent.prev().find('label').last().text("");
	}else{
		if(bizCommonVariable.markerFloat[0]){
			$("#markerLon").val(bizCommonVariable.markerFloat[0]);
			$("#markerLat").val(bizCommonVariable.markerFloat[1]);
			
			checkedParent.prev().prev().find('label').last().text("度");
			checkedParent.prev().find('label').last().text("度");
		}
	}
}

//访问等待刷新
function loading(){
	//获取浏览器页面可见宽度和高度
	var _PageHeight = document.documentElement.clientHeight;
	var _PageWidth = document.documentElement.clientWidth;
	//计算显示框距离顶部和左部的距离
	var _LoadingTop = _PageHeight > 61 ? (_PageHeight-61)/2 : 0;
	var _LoadingLeft = _PageWidth > 251 ? (_PageWidth-251)/2 : 0;
	//loading页面内容
	var loadingHtml = '<div id="loadingDiv" style="position: absolute; left:0; width:100%; height:'+_PageHeight+
						 'px; top:0; background:#f3f8ff; opacity:0.8; filter:alpha(opacity=80); z-index:1000;">'+
						 '<div style="position: absolute;cursor1:wait; left:'+_LoadingLeft+'px; top:'+_LoadingTop+
						 'px;width:auto; height:57px; line-height:57px; padding-left:50px; padding-right:5px; font-size: 25px;'+
						 'corlor:#696969; background:#fff url('+HthxMap.Settings.root+'map/business/img/loading.gif) no-repeat scroll 5px 10px;; font-family:\'Microsoft YaHei\';">查询中，请等待……</div>'+
					  '</div>';
	$('body').append(loadingHtml);
}