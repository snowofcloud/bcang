/**
 * <p>Description: 地图公共接口</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-06 </p>
 * @author: 张琴
 */

/**
 * <p>Description: 放大、缩小方法</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @param：map（传入的全局地图），num（正整数，地图放大/缩小的级数），out（boolean变量，表示是否缩小地图）
*/
HthxMap.zoom = function(map, num, out){
	if( !(/^[1-9][0-9]*$/).test(num)){
		console.log("地图缩放级别必须为整数");
		return;
	}
	var view = map.getView();
	if (out) {
		view.setZoom(view.getZoom() - num);
	} else {
		view.setZoom(view.getZoom() + num);
	}
};
//属性查询方法
HthxMap.searchFeature = function(){  

};
/**
 * <p> Description: 添加标记图标方法</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p> Date: 2015-07-06</p>
 * @author: 张琴
 * @param：map(全局地图),coordinate(坐标/经纬度),contentStr(标记内容),pngUrl(标记图标路径)，flag(是否是系统通用定位)
 * @return:
 */
HthxMap.markerNumber = 0;
HthxMap.deleteMarker = function(map, markerid){
	var overlays = map.getOverlays();
	var len = overlays.getLength();
	//删除标记图标
	for (var i = 0; i < len; i++) {
		if (overlays.item(i) && (overlays.item(i).get("markerId") == markerid)) {
			map.removeOverlay(overlays.item(i));
			break;
		} 
	}
	//删除弹出框
	for (var i = 0; i < len; i++) {
		if (overlays.item(i) && (overlays.item(i).get("markerOverlayId") == "overlayer"+markerid)) {
			map.removeOverlay(overlays.item(i));
			break;
		} 
	}
	return true;
};
HthxMap.addMarker = function(map,coordinate,contentStr,url,markerid,flag,isDelete) {
	HthxMap.markerNumber++;
	var markerId = markerid ? markerid : "marker"+HthxMap.markerNumber;
	var pngUrl = url ? url : HthxMap.Settings.IMG.marker[0];
	var markerelement = document.createElement("div");
	markerelement.id = markerId;
	
	markerelement.innerHTML = '<img src="'+pngUrl+'"/><span class="markerNameok" style="font: bold 12px red 宋体;color:#ff0000"></span><span class="markerContentok" style="display:none"></span>';
	var maker = new ol.Overlay({
		element : markerelement,
		offset: [-15, -5]
//		positioning: "bottom-center"
	});
	maker.set("markerId", markerId);

	if(isDelete){//如果是通用定位，可删除
		maker.set("clickPop", true);
	}
	map.addOverlay(maker);
	//HthxMap.removeUnusedPop();
	//保存弹框内容
	var id = $(contentStr).find("div[id='historyMarkerId']").attr("value");
	if(id){
		bizCommonVariable.markerObj[id].markerId = markerId;
	}
	maker.setPosition(coordinate);
	
	markerelement.addEventListener("click", function() {
		HthxMap.showPopup(map, coordinate, contentStr, markerId, flag, isDelete);
	}, false);
	var name = "";
	var input = $(contentStr).find("input[id='markerName']");
	if(input && !input.val()){
		name = "标记"+(++bizCommonSetting.maxMarkerNumner);
	}else if(input){
		name = input.val();
	}
	$($("#"+markerId).find("span[class='markerNameok']")).html(name);
	if((!markerid) && (!flag)){
		HthxMap.showPopup(map, coordinate, contentStr, markerId, flag, isDelete);
	}else{  //不显示弹框的情况下，显示名字
		HthxMap.markerFeatureId[markerId] = $(contentStr).find("div[id='featureId']").attr("value");   //保存弹框id和绘制featureId对应
	}
};
/**
 * <p> Description: 选择要素的弹出框</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p> Date: 2015-07-06</p>
 * @author: 张琴
 * @param：map(全局地图),coordinate(坐标/经纬度),contentStr(弹出框内容),markerId(关联的标记的唯一标识)
 * @return:
 */
HthxMap.showPopup = function(map, coordinate, contentStr, markerId, flag, isDelete){
	if(markerId == "startoverPlan"){
		clearTimeout(startoverPlan.config.requestStartoverPlan);
	}
	if (document.getElementById("popup") == null) {
		$("body").append('<div id="popup" class="ol-popup" style="z-index:1"><a href="#" id="popup-closer" class="ol-popup-closer"></a><div id="popup-content"></div></div>');
	}
	var id = $(contentStr).find("div[id='historyMarkerId']").attr("value");
	var oreid = $("#"+markerId).attr("ovelayId");
	var container = document.getElementById('popup');
	var content = document.getElementById('popup-content');
	var closer = document.getElementById('popup-closer');
	var overlay = new ol.Overlay({
		element : container,
		autoPan : false,
		positioning: "bottom-center",
		autoPanAnimation : {
			duration : 250
		}
	});
	if(isDelete){//如果是通用定位，可删除
		overlay.set("clickPop", true);
	}
	overlay.set("markerOverlayId","overlayer"+markerId);//供删除使用：在删除标记图标时，若弹出框打开，则删除
	map.addOverlay(overlay);
	//HthxMap.removeUnusedPop();
	closer.onclick = function() {
		/*startoverPlan.config.requestStartoverPlan = setTimeout(function(){startoverPlan.showTableTop();},1000);*/
		overlay.setPosition(undefined);
		/*closer.blur();*/
		//清除popup的内容
		//content.innerHTML = "";
	};
	content.innerHTML = contentStr;
	overlay.setPosition(coordinate);
	/*if(oreid){
		userDefinedFacilityFind.showDetail(oreid);
		return;
	}*/
	if($("#featureId").attr("value")){   //如果有绘制的图形
		HthxMap.markerFeatureId[markerId] = $("#featureId").attr("value");   //保存弹框id和绘制featureId对应
	}  
	
	if($("#historyMarkerId").attr("value")){//历史标记时，保存id，删除时用
		HthxMap.markerId[markerId] = $("#historyMarkerId").attr("value");
	}
	//如果有半径，则显示
	if($("#markerRadius").val()){
		if($("#marker").hasClass("hidden")){
			$("#marker").removeClass("hidden");
		}
	}
	if($("#"+markerId+" .markerNameok").html()){    //保存标记名称
		$("#markerName").val($("#"+markerId+" .markerNameok").html());
	}
	if( $("#"+markerId+" .markerContentok").html()){  //保存标记内容
		$("#markerContent").val($("#"+markerId+" .markerContentok").html());
	}
	//切换图标
	var pngUrl;
	var iconIndex;
	if(id && bizCommonVariable.markerObj[id].icon){
		var icon = bizCommonVariable.markerObj[id].icon;
		if(!(icon instanceof Number)){
			iconIndex = parseInt(icon);
		}
		pngUrl = HthxMap.Settings.IMG.facility[iconIndex];
	}else{  //默认图标
		pngUrl = HthxMap.Settings.IMG.facility[0];
		iconIndex = 1;
	}
	$("#markerlist").unbind("click").on("click", function(e){
		pngUrl = e.target.src;
		iconIndex = HthxMap.Settings.IMG.facility.indexOf(HthxMap.Settings.root+pngUrl.split(HthxMap.Settings.root)[1]);
		$(".ol-overlay-container #"+markerId).find("img").attr("src", pngUrl);
		$("#iconids").val(iconIndex);
	});
	
	//保存标记内容
	$("#markerFacilityOK").bind("click",function(){
		var name = $("#reaDefinedFacility-name").val();
		if ("" === name) {
			Message.alert({Msg:"请输入自定义设施名称",iconImg:"warning",isModal: false});
			return;
		}
		var markerLon = $("#markerLon").val();
		var markerLat = $("#markerLat").val();
		var coordinate1; 
		if(markerLon.indexOf('°') !== -1){
			coordinate1 = bizCommonVariable.markerFloat;    //度分秒转换为浮点数
		}else{
			coordinate1 = [markerLon, markerLat];
		}
		coordinate1 = [JSON.parse(coordinate1[0]), JSON.parse(coordinate1[1])];  //单个标记的时候可以修改
		var iconIndexinput = $("#iconids").val();
		if(null == iconIndexinput || "" == iconIndexinput){
			Message.alert({Msg:"请选择图标类型",iconImg:"warning",isModal: false});
			return;
		}
		markerSave(name,coordinate1,markerId,iconIndexinput,overlay);
		/*if(!$("#markerCoordinates").attr("value")){*/
			
			//在保存标记时，先删除原来的标记，再添加改变了经纬度的标记
			/*if(coordinate1.toString() != coordinate.toString()){
				HthxMap.addMarker(map, coordinate1, contentStr, pngUrl, markerId);
				if(HthxMap.markerFeatureId[markerId]){
					HthxMap.addSingleDraw(HthxMap.markerFeatureId[markerId]);	
				}
				deleteMarker();
			}*/
		/*}
		*/
		/*var radius = $("#markerRadius").val();*/
		/*if(!name || !coordinate[0] || !coordinate[1]){
			return;
		}*/
		/*if($("#markerCoordinates").val()){  //还没有保存的情况下多个点
			coordinate1 = $("#markerCoordinates").val();
		}*/
	});
	
	//删除标记修改
    $("#markerCancel").click(function(){
    	var overlayIdd = $("#"+markerId).attr("ovelayId");
    	if(overlayIdd){
    		Message.confirm({Msg:prompt.sureDelete, iconImg:'question', 'isModal': true}).on(function (flag) {
       		 if(flag){
       			deleteMarker();
       		 }
       	 });
    	}else{
    		deleteMarker();
    	}
    	
    });
    
    function deleteMarker(){
    	markerRemove(markerId);
    	overlay.setPosition(undefined);
		closer.blur();
		//清除popup的内容
		content.innerHTML = "";
		//清除标记
		var overlays = map.getOverlays();
		var len = overlays.getLength();
		for (var i = 0; i < len; i++) {
			if (overlays.item(i).get("markerId") == markerId) {
				map.removeOverlay(overlays.item(i));
				break;
			} 
		}
	}
    $("#convertDegrees").click();
};
HthxMap.getOverlayById = function(key,id){
	var overlays = map.getOverlays();
	var len = overlays.getLength();
	for (var i = 0; i < len; i++) {
		if (overlays.item(i).get(key) == id) {
			return overlays.item(i);
		} 
	}
	return null;
};

/**
 * <p>Description: 通用图层点击弹框绑定事件</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-21 </p>
 * @author: 何泽潘
 * @param: map(全局地图), evet(需要绑定的事件形式，如singleclick等),layerId(图层id,用于指定图层点击事件),popDiv(构造的弹框div)  ,popup(接受构造出的overlay对象)
 * @return popUp(生成的overlay对象)
 */

HthxMap.mouseEvent = function(map, event, layerId, popDiv){
	HthxMap.key = map.on(event, function(evt) {
		if(evt.dragging){
			return;
		}
	    var feature = map.forEachFeatureAtPixel(evt.pixel,
	  	      function(feature, layer) {
	  	    	  if(layer && layer.getProperties() && layer.getProperties().id == layerId && !HthxMap.curFeatureInteraction){
	  	  	  		  if(feature.get("noClickEvent") == undefined){
	  	  	  			  return feature;
	  	  	  		  }
	  	  	  	  }
	  	  	  	  return null;
	  	      });
	  	  if (feature) {
	  		  if(event == "pointermove"){
//	  			  HthxMap.popDiv.createfloatPop(feature, popDiv);
	  		  }else{
	  			  
	  			  if(layerId == "pipeLineLayer" || layerId == "storageLayer") {
	  				pipeLineClickPop(feature,layerId);
	  			  }else {
	  				//点击,图片变化
		  			  HthxMap.clickEvent(feature);
	  				  HthxMap.popDiv.createClickPop(feature, popDiv,layerId);
	  			  }
	  			  var doublePoint = [];
	  			  var putPoint=[];
	  			 /* var routeData = feature.getProperties().route;//feature.values_.route;
	  			  for(var i = 0; i < routeData.length-1 ; i++){
	  				doublePoint.push(routeData[i]);
	  				doublePoint.push(routeData[i+1]);
	  				putPoint.push(doublePoint);
	  				var path = new ol.Feature(new ol.geom.MultiLineString(putPoint));
	  				path.setStyle(new ol.style.Style({
	  					stroke: new ol.style.Stroke({
	  						width: 12,
	  			            color: '#3399CC'
	  					})
	  				}));
	  				HthxMap.getLayerById("featureLayer").getSource().addFeature(path);
	  				doublePoint=[];
	  			  }
	  			  
	  			  //起始位置
	  			  var startFeature = new ol.Feature(new ol.geom.Point(routeData[0]));
	  			  var startStyle = new ol.style.Style({
	  				image: new ol.style.Icon({
	  			        opacity: HthxMap.opacity,
	  				    src:  HthxMap.Settings.configData.targetImg.startP
	  				})
	  			});
	  			startFeature.setStyle(startStyle);
	  			HthxMap.getLayerById("featureLayer").getSource().addFeature(startFeature);
	  			
	  			//结束位置
	  			  var endFeature = new ol.Feature(new ol.geom.Point(routeData[8]));
	  			  var endStyle = new ol.style.Style({
	  				image: new ol.style.Icon({
	  			        opacity: HthxMap.opacity,
	  				    src:  HthxMap.Settings.configData.targetImg.endP
	  				})
	  			});
	  			endFeature.setStyle(endStyle);
	  			HthxMap.getLayerById("featureLayer").getSource().addFeature(endFeature);*/
	  		  }
	  		  HthxMap.preFeature = feature;
	  	  }
	  });
	if(HthxMap.booleanKey) {
		HthxMap.key = null;
		HthxMap.booleanKey = false;
	}
}
HthxMap.unMouseEvent = function(){
	if(HthxMap.key){
		map.unByKey(HthxMap.key);
	}
}

/**
 * <p> Description: 弹出框DIV拖动效果</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p> Date: 2015-07-17</p>
 * @author: 何泽潘
 * @param：
 * @return:
 */
HthxMap.dragDiv = function(id){
	$("#"+id).mousedown(function(e){    //e鼠标事件
		$(this).css("cursor", "move");//改变鼠标指针的形状

		var offset = $(this).offset();//DIV在页面的位置
		var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离
		var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离
		$(document).bind("mousemove", function(ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
		{
			$("#"+id).stop();

			var _x = ev.pageX - x;//获得X轴方向移动的值
			var _y = ev.pageY - y;//获得Y轴方向移动的值

			$("#"+id).animate({
				left : _x + "px",
				top : _y + "px"
			}, 10);
		});
	});
	$(document).mouseup(function() {
		$("#"+id).css("cursor", "default");
		$(this).unbind("mousemove");
	});
}

/**
 * <p>Description: 延迟加载文件到head里</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-03-23 </p>
 * @author: hezp
 * @param: 
 * @return 
 */
HthxMap.include = {
	linkStyle: function(url){
		var link;
		for(var i = 0; i < url.length; i++){
			link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = HthxMap.Settings.root + url[i];
			$("head").append(link);
		}
	},

	script: function(url, callback){
		var script;
		var scriptArray=[];
		for(var i = 0; i < url.length; i++){
			script = document.createElement("script");
			script.type = "text/javascript";
			script.src = HthxMap.Settings.root + url[i];
			scriptArray.push(script);
			document.getElementsByTagName("body")[0].appendChild(script);
		}
		if(script.readyState){	//IE
			script.onreadystatechange = function(){
				if(script.readyState == "loaded" || script.readyState == "complete"){
					script.onreadystatechange = null;
					callback();
				}
			}
		}else{
			script.onload = function(){
				callback();
			}
		}
	},

	//js文件数组当前路径
	getScriptArray: function(baseUrl, scriptArray){
		for(var i = 0; i < scriptArray.length; i++){
			scriptArray[i] = baseUrl + scriptArray[i];
		}
		return scriptArray;
	}
}
/**
 * <p>Description: 点击渔船，改变渔船样式</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-28 </p>
 * @author: 何泽潘
 * @param: feature(渔船feature)
 * @return 
 */
HthxMap.clickEvent = function(feature){
	var properties = feature.getProperties();
	var pngSrc = feature.get("terminalTypeImage");
	var shipImg = HthxMap.shipImgJoint(pngSrc, ["Clicked"]);
	HthxMap.changeShipImg(feature, properties.shipDir, shipImg);	
}
/**
 * <p>Description: 移除渔船样式</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-28 </p>
 * @author: 何泽潘
 * @param: 
 * @return 
 */
HthxMap.removeShipStyle = function(){
	var pngSrc = [];
	if(HthxMap.preFeature){
		var feature = HthxMap.preFeature;
		var properties = feature.getProperties();
		var terminalType = feature.get("terminalTypeImage");
		pngSrc.push(terminalType);
		//是否搜索状态
		if(properties.shipSearch){    
			pngSrc = (HthxMap.shipImgJoint(terminalType, ["Search"]));
		}
		//有跟踪渔船,跟踪图片显示在最上层		
		if(properties.shipStatus){    
			pngSrc.push(HthxMap.Settings.configData.targetImg.track);
		}
		HthxMap.changeShipImg(feature, properties.shipDir, pngSrc);
		HthxMap.preFeature = null;
	}
}
/*******************************车辆弹框开始*******************************************/

HthxMap.popDiv = {
		//唯一常量，保存弹框overlay
		constant: {
			overlay: null,
			floatOverlay: null
		},
		//点击弹框
		createClickPop: function(feature, popDiv, layerId){
			//移除点击弹框
			HthxMap.popDiv.closeClickPop();
			var offset;
			var properties = feature.getProperties();
			if(layerId == "featureLayer"){
				offset = [-10, -400];
			}else if(properties.id && properties.id == properties.terminalID){//未注册渔船弹出框
				offset = [-10, -350];
			}else if(layerId == "trackLayer"){
				offset = [-10, -150];
			}else{
				offset = [-10, -390];
			}
			//判断是否为政府用户
			var privFlag;
			var useInfor = JSON.parse(sessionStorage.getItem("userInfo")).roleCodes;
			//非政府用户则不显示弹框
			$.each(useInfor,function(index,content){
				if(content !="e5348d777c2a48dd98cc7e19621d3193"){
					privFlag = true;
				}else{
					privFlag = false;
				}
			});
			if(privFlag){
				//在8秒后放大的图标缩小为原来大小
				setTimeout(function(){HthxMap.removeShipStyle();},8000);
				return;
			}
			var strs = popDiv(feature);
			//创建点击弹框
			var element = document.createElement("div");
			element.id = "popUp";
			element.style.zIndex = 1;
			element.style.position = "absolute";
			$('body').append(element);
			element.innerHTML = strs;
			var popup = new ol.Overlay({    //保证船只弹框随船移动而移动
				element: element,
				positioning: 'bottom-left',
				autoPan: true,
				offset: offset
			});
			HthxMap.popDiv.constant.overlay = popup;
			popup.set("clickPop", true);
			feature.set("clickPop", true);
			map.addOverlay(popup);
			HthxMap.removeUnusedPop();
			var geometry = feature.getGeometry();
		    var coord = geometry.getCoordinates();
		    if(layerId == "trackLayer"){//车辆轨迹弹窗
		    	popup.setPosition(coord);
		    }else{//车辆状态弹窗
		    	//显示弹框
				var panelId = document.getElementById("carInforDetail-Panel");
				panelId.style.display ="block";
				
				//显示选中车辆货物数据
				var goodsData = "";
				var carName = feature.values_.carrierName;
				$.ajax({
					url:$.backPath+"/wayBill/findGoodsData/"+carName,
					type:"POST",
					dataType:"json",
					async:false,
					success:function(result){
						var len = result.data.length;
						if(result.code === 1 && len > 0){
							goodsData = result.data;
						}
					}
				});
				var str = "";
				var strLi = "";
				for (var i = 0, length = goodsData.length; i < length; i++) {
					var obj = goodsData[i];
					for(var temp in goodsData[i]){
						if( goodsData[i][temp] == null){
							goodsData[i][temp] = "";
						}
					}
					str +=  '<div role="tabpanel" class="tab-pane" id="goodsDataLi'+goodsData[i].id+'">'+
							'<div class="row">'+
							'<div class="row-first">'+
							'<label class="carinforfont">货物品名 :&nbsp;</label>'+
							'<span class="look-formPanel carinforfont">'+goodsData[i].goodsname+'</span>'+
							'</div>'+
							'<div class="row-second">'+
							'<label class="carinforfont">货物体积(m³) :&nbsp;</label>'+
							'<span class="look-formPanel carinforfont">'+goodsData[i].volume+'</span>'+
							'</div>'+
							'</div>'+
							'<div class="row">'+
							'<div class="row-first">'+
							'<label class="carinforfont">货物数量 :&nbsp;</label>'+
						 	'<span class="look-formPanel carinforfont">'+goodsData[i].quantity+'</span>'+
						 	'</div>'+
						 	'<div class="row-second">'+
						 	'<label class="carinforfont">货物规格 :&nbsp;</label>'+
						 	'<span class="look-formPanel carinforfont">'+goodsData[i].format+'</span>'+
						 	'</div>'+
						 	'</div>'+
						 	'<div class="row">'+
						 	'<div class="row-first">'+
						 	'<label class="carinforfont">货物包装 :&nbsp;</label>'+
						 	'<span class="look-formPanel carinforfont">'+goodsData[i].pack+'</span>'+
						 	'</div>'+
						 	'<div class="row-second">'+
							'<label class="carinforfont">货物重量(t) :&nbsp;</label>'+
							'<span class="look-formPanel carinforfont">'+goodsData[i].weight+'</span>'+
							'</div>'+
							'</div>'+
							'<div class="row">'+
							'</div>'+
							'</div>';
					
					strLi += '<li role="presentation">'+
							 '<a href="#goodsDataLi'+goodsData[i].id+'" aria-controls="goodsDataLi'+goodsData[i].id+'" role="tab" data-toggle="tab">货物'+[i+1]+'</a>'+
							 '</li>';
				}
				if(!goodsData){
					$("#footer-subpanel").find(".tab-content").css("height","0px");
				}
				$("#cargoInfor").empty();
				$("#footer-subtabs").empty();
				$("#cargoInfor").append(str);
				$("#footer-subtabs").append(strLi);
				$("#cargoInfor").find("div[class='tab-pane']").eq(0).addClass("active");
				$("#footer-subtabs").find("li").eq(0).addClass("active");
				//document.getElementById("footer-panel").style.display ="block";
				//将未查到数据置为空
				var span = $($("#carInforDetail-Panel").find(".look-formPanel"));
				$.each(span,function(index,content){
					if($(span[index]).text() == "undefined"){
						$(span[index]).text("");
					}
				}); 
			    
			    popup.setPosition(coord);
				//注册tab页切换事件
			    cargoMonitor.showTabPanle();
			    //初始化监控信息
				cargoMonitor.init(properties);
				//cargoMonitor.init(feature.values_.carrierName);
				// 车规划的路径
				var properties = feature.getProperties();
				// 获取运单id
				var businessData = new filterDate(properties.carrierName);
				var wayBillId = businessData.WayBillInfo.id;
				carRoute.getRoute(wayBillId);
		    }
		},
		
		//关闭点击弹框
		closeClickPop: function(){
			if(HthxMap.popDiv.constant.overlay){
		    	map.removeOverlay(HthxMap.popDiv.constant.overlay);
		    	$("#popUp").remove();
		    	
		    	if(HthxMap.preFeature){
					HthxMap.preFeature.set("clickPop", false);
				}
		    	HthxMap.removeShipStyle();
		    	
		    	return true;
			}
		},
		
		//悬浮弹框
		createfloatPop: function(feature, popDiv){
			//移除悬浮弹框
			if(HthxMap.popDiv.constant.floatOverlay){
				$("#floatPopUp").remove();
				map.removeOverlay(HthxMap.popDiv.constant.floatOverlay);
		    }
			var strs = popDiv(feature);
			var element = document.createElement("div");
			element.id = "floatPopUp";
			  
			element.innerHTML = strs;
			$('body').append(element);
			var floatPopUp = new ol.Overlay({    
				element: element,
				positioning: 'bottom-center',
				autoPan: true
			});
			HthxMap.popDiv.constant.floatOverlay = floatPopUp;
			map.addOverlay(floatPopUp);
			HthxMap.removeUnusedPop();
			var geometry = feature.getGeometry();
		    var coord = geometry.getCoordinates();
		    floatPopUp.setPosition(coord);
		}
}
/*******************************船舶弹框结束*******************************************/

/**
 * <p>Description: 通用图层feature处小手生成函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-04 </p>
 * @author: 何泽潘
 * @param: map(全局地图), popFlag(true:设置悬浮弹框),floatPopDiv(悬浮弹窗样式,可以不传)
 * @return
 */

HthxMap.mousePointer = function(map, popFlag, floatPopDiv){
	HthxMap.Pointer = map.on('pointermove', function(e) {
		  if(e.dragging){
			  return;
		  }
		  var feature = map.forEachFeatureAtPixel(e.pixel,
	  	  	  function(feature, layer) {
	    	  	  if(layer && layer.getProperties() && layer.getProperties().id !== "featureLayer" && layer.getProperties().id !== "areaTrackLayer"){
	    	  		  if(popFlag && layer.getProperties().id == "monitorLayer" && !HthxMap.curFeatureInteraction){
//	    	  		      HthxMap.popDiv.createfloatPop(feature, floatPopDiv);
	    	  		  }
	    	  		  if(feature.get("noClickEvent") == undefined){
	    	  			  return feature;
	    	  		  }
	    	  	  }
	    	  	  return null;
	  	  	  });
	  	  	  if (feature) {
	  	  		  var pixel = map.getEventPixel(e.originalEvent);
	  		      var hit = map.hasFeatureAtPixel(pixel);
	  		      if(HthxMap.flag && !HthxMap.mouseUnbind){
	  		    	  HthxMap.mouseUnbindEvent(map, 'pointer');
	  		    	  HthxMap.flag = false;
	  		      }
	  	  	  }else{
	  	  		  if(!HthxMap.flag){
	  	  			  HthxMap.mouseBindEvent(map);
	  	  		      HthxMap.flag = true;
	  	  		  }
	  	  		  
	  	  		  if($(".floatPopUp")){
	  		          $(".floatPopUp").remove();
	  		      }
	  	  	  } 
	});
}
HthxMap.unMousePointer = function(){
	if(HthxMap.Pointer){
	    map.unByKey(HthxMap.Pointer);
	}
}

/**
 * <p>Description: 移除地图事件</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-23 </p>
 * @author: 孙耀
 * @param: 无
 * @return 无
 */
HthxMap.removeMapEvents = function(map){
	//移除测量方位对象
	if(HthxMap.measureOrientation){
		HthxMap.measureOrientation.deactivate();
	}
	//移除鼠标单击绑定事件
	if(HthxMap.curMapOn){
		map.un('singleclick',HthxMap.curMapOn);
	}
	//移除鼠标移动绑定事件
	if(HthxMap.curMapOnPointerMove){
		map.un('pointermove',HthxMap.curMapOnPointerMove);
	}
	//移除交互
	if(HthxMap.curMapInteraction){
	    map.removeInteraction(HthxMap.curMapInteraction);
	}
	//使得标绘对象无效
	if(HthxMap.plotObject){
		HthxMap.plotObject.deactivate();
	}
	//移除鼠标新生成样式
	if(HthxMap.mouseUnbind){
		HthxMap.mouseBindEvent(map);
	}
	//移除渔船缓冲区点击样式
	if($(".ReliefIcon ul li").hasClass('active')){
		$(".ReliefIcon ul li").removeClass('active');
	}
}

//移除图层中的绘制图，如果不应被删除的则置clear为false
HthxMap.removeInteraction = function(layers){
	for(var i=0; i<layers.length; i++){
		var source = layers[i].getSource();
		var features = source.getFeatures();
		var featuresLen = features.length;
		for(var j = 0; j < featuresLen; j++){
			if(features[j].get("clear")){
				source.removeFeature(features[j]);
			}else{
				continue;
			}
		}
  	  HthxMap.shipBufferQueryFeature = [];
    }
}
//移除覆盖物，目前只移除点击弹框，其它如跟踪、报警、台风圈等不移除
HthxMap.removeOverlay = function(map){
    var overlays = map.getOverlays();
    if(overlays){
    	var overlaysLength = overlays.getLength();
    	for(var i = 0; i < overlaysLength; i++){
    		var item = overlays.item(i);
    		if(item.get("clickPop")){
    			map.removeOverlay(item);
    			HthxMap.removeShipStyle();
    			--i;
    			--overlaysLength;
    		}
    	}
    }
    if(bizCommonSetting.atmosphereStyle){
		HthxMap.getLayerById("featureLayer").getSource().removeFeature(bizCommonSetting.atmosphereStyle);
		bizCommonSetting.atmosphereStyle = null;
	}
}
/**
 * <p>Description: 点击鼠标右键取消地图当前事件</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-10 </p>
 * @author: 何泽潘
 * @param: map, curControl(当前激活样式,可选)
 * @return 无
 */
HthxMap.rightClick = function(map, curControl){
	$(window).mousedown(function(e){
//		$(window).bind('contextmenu', function(){
//			return false;
//		})
		if(e.which == 3){
			HthxMap.removeMapEvents(map);
			if(curControl.target){
				$("#" + curControl.target + " > div").removeClass(curControl.displayClass + 'Activate');
				$("#" + curControl.target + " > div").addClass(curControl.displayClass + 'Inactivate');
			}
		}
	})
}

/**
 * <p>Description: 鼠标图标事件</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-11 </p>
 * @author: 何泽潘
 * @param: map
 * @return 无
 */
HthxMap.mouseBindEvent = function(map){
	HthxMap.mouseUnbind = false;
	if(HthxMap.mouseMoveKey){
		$("#"+map.getTarget()).css("cursor", "");
		$(window).unbind("mousemove");
	}
	$(window).mousedown(function(e){
		$("#"+map.getTarget()).css("cursor", HthxMap.Settings.IMG.PanDown);
		$(window).mousemove(function(e){
			$("#"+map.getTarget()).css("cursor", HthxMap.Settings.IMG.PanDown);
		})
	}).mouseup(function(e){
		$("#"+map.getTarget()).css("cursor","");
		$(window).unbind("mousemove");
	})
}
/**
 * <p>Description: 鼠标图标事件</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-11 </p>
 * @author: 何泽潘
 * @param: map, imgSrc(想要变成的鼠标样式)
 * @return 无
 */
HthxMap.mouseUnbindEvent = function(map, imgSrc){
	HthxMap.mouseUnbind = true;
	HthxMap.flag = true;
	HthxMap.mouseMoveKey = $(window).mousemove(function(e){
		$(window).unbind("mousedown");
		$(window).unbind("mouseup");
		$("#"+map.getTarget()).css("cursor", imgSrc);
	})
}

/**
 * <p>Description: 单个删除绘图事件</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-24 </p>
 * @author: 何泽潘
 * @param: coor(删除点坐标),index(每个绘图对应的索引)
 * @return 无
 */
HthxMap.createDeleteElement = function(coor, index) {
	var deleteElement = document.createElement('div');
	var strs = '<div style="cursor:pointer;" onclick="HthxMap.clearMeasure('  + index + ')">x</div>';
	deleteElement.innerHTML = strs;
    var deleteE = new ol.Overlay({
	    element: deleteElement,
	    offset: [0, 0],
	    position: coor,
	    positioning: 'bottom-center'
    });
    deleteE.setProperties({id: index});
    map.addOverlay(deleteE);
}

HthxMap.clearMeasure = function(index){
	//获取地图上的图层
	var overlays = map.getOverlays();
	var overlaysLength = overlays.getLength();
	for(var i = 0; i < overlaysLength; i++){
		var item = overlays.item(i);
		if(item && item.getProperties() && item.getProperties().id == index){
			map.removeOverlay(item);
			--i;
			--overlaysLength;
		}
	}
	//找到对应的层，并做删除操作
	HthxMap.removeSingleDraw(index);
	//如果是标绘，则删除库
//	if(bizCommonSetting.plotId[index]){    //分支
//		plotEdit.remove(index);
//	}
}

//删除单个绘制
HthxMap.removeSingleDraw = function(index){
	//找到对应的层，并做删除操作
	var layer = HthxMap.getLayerById("featureLayer");
	var source = layer.getSource();
	var item = source.getFeatureById(index);
	
	if(item){
		source.removeFeature(item);	
	}
	
	//删除缓冲区查询
	HthxMap.shipBufferQueryFeature.splice(item, 1);
	HthxMap.shipBufferQueryFeature.forEach(function(feature){
		source.removeFeature(feature);
	})
	
	HthxMap.shipBufferQueryFeature = [];
	//移除搜索状态
    var name = $("#conditionLabel .inquireItem-content").html();//分支
	if(name === "动态查询"){
		clearBoatInfo();
		$("#conditionLabel").html("");
	}
}
//增加单个绘制
HthxMap.addSingleDraw = function(index){
	//找到对应的层，并做删除操作
	var layer = HthxMap.getLayerById("featureLayer");
	var source = layer.getSource();
	var item = source.getFeatureById(index);
	source.addFeature(item);	
}
/**
 * <p>Description: 找到对应给定id的矢量图层</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-28 </p>
 * @author: 孙耀
 * @param: layerId(矢量图层标识,字符串类型)
 * @return layer(矢量图层)
 */
HthxMap.getLayerById = function(layerId){
	var layers = map.getLayers();
	var len = layers.getLength();
	for(var i = 0; i < len; i++){
		if(layers.item(i).getProperties().id === layerId){
			var layer = layers.item(i);
			break;
		}
	}
	return layer;
}
/**
 * <p>Description: 转换船舶图片方向</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-04 </p>
 * @author: hezp
 * @param: trueheading(船舶方向,360度)
 * @return 船舶方向，2PI
 */
HthxMap.transformImgRotation = function(trueheading){
	return 2*Math.PI*trueheading/360;
}

/**
 * <p>Description: 根据坐标点集绘制图形</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-23 </p>
 * @author: hezp
 * @param: points(坐标点集)
 * @return 
 */
HthxMap.drawMultiPolygon = function(points, featureStyle){
	var pts = [];    //存放所有转化的点
	var geo = []; 
	var style = featureStyle || creatStyle.markerDrawStyle();
	//转换成浮点数
	for(var i = 0; i < points.length; i++){
		var lon = points[i][0];
		var lat = points[i][1];
		if(!(lon instanceof Number)){
			lon = parseFloat(lon);
		}
		if(!(lat instanceof Number)){
			lat = parseFloat(lat);
		}
		pts.push([lon, lat]);
	}
	if(!pts){
		pts = points;
	}
	var a = [];
	for(var i = 0; i <= pts.length; i++){
		if(i == pts.length){
			a.push([pts[0][0], pts[0][1]]);
			break;
		}
		a.push([pts[i][0], pts[i][1]]);
	}
	geo.push(a);
	if(geo[0] && geo[0].length <= 1){
		return;
	}
	var ls = new ol.Feature(new ol.geom.Polygon(geo));
	ls.setStyle(style);	
	ls.set("clear", false);  //不可删除
	ls.setId(++HthxMap.measureIndex);
	var featureLayer =  HthxMap.getLayerById("featureLayer");
	featureLayer.getSource().addFeature(ls);
	var interiorPoint = ls.getGeometry().getInteriorPoint().getCoordinates();
	return interiorPoint;
}

/**
 * <p>Description: 根据坐标点集绘制图形,非多边形</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-23 </p>
 * @author: hezp
 * @param: points(坐标点集)
 * @return 
 */
HthxMap.drawLineString = function(points, featureStyle){
	var pts = [];    //存放所有转化的点
	var geo = []; 
	var style = featureStyle || creatStyle.markerDrawStyle();
	points = points[0];
	//转换成浮点数
	for(var i = 0; i < points.length; i++){
		var lon = points[i][0];
		var lat = points[i][1];
		if(!(lon instanceof Number)){
			lon = parseFloat(lon);
		}
		if(!(lat instanceof Number)){
			lat = parseFloat(lat);
		}
		pts.push([lon, lat]);
	}
	if(!pts){
		pts = points;
	}
	var a = [];
	for(var i = 0; i < pts.length; i++){
		a.push([pts[i][0], pts[i][1]]);
	}
	geo.push(a);
	if(geo[0] && geo[0].length <= 1){
		return;
	}
	var ls = new ol.Feature(new ol.geom.LineString(geo[0]));
	ls.setStyle(style);	
	ls.set("clear", false);  //不可删除
	ls.setId(++HthxMap.measureIndex);//为什么用++保证编号不一致，不要用重复不然第一个绘制会无法出现
	var featureLayer =  HthxMap.getLayerById("featureLayer");
	featureLayer.getSource().addFeature(ls);
	var interiorPoint = ls.getGeometry().getCoordinates()[0];
	return interiorPoint;
}

/**
 * <p>Description: 保留坐标有效数字</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-24 </p>
 * @author: hezp
 * @param: coordinates(坐标点集),length(需要保留的长度)
 * @return 
 */
HthxMap.keepCoorsLen = function(coordinates, length){
	var coorsArray = [];
	var coorsLen = coordinates.length;
	for(var i = 0; i < coorsLen; i++){
		var lon = coordinates[i][0];
		var lat = coordinates[i][1];
		//转化成字符串
		lon = lon.toString();
		lat = lat.toString();
		
		if(lon.indexOf(".") != -1){
			var lenTemp = lon.split(".")[1].length - length;
			var lenTemp1 = lon.length - lenTemp;
			lon = lon.slice(0, lenTemp1);
		}
		if(lat.indexOf(".") != -1){
			var latTemp = lat.split(".")[1].length - length;
			var latTemp1 = lat.length - latTemp;
			lat = lat.slice(0, latTemp1); 
		}
		coorsArray.push([parseFloat(lon), parseFloat(lat)])
	}
	return coorsArray;
}

/**
 * <p>Description: 清除overlay痕迹，即把没有内容的overlay清除掉</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-04-14 </p>
 * @author: zq
 * @param: 
 * @return 
 */
HthxMap.removeUnusedPop = function(){
	$(".ol-overlay-container").each(function(index){
		if(!$(this).html()){
			$(this).remove();
		}
	})
};

/**
 * <p>Description: 要素(feature)使用时间(time)从坐标(src)移动到坐标(to)</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2017-03-10 </p>
 * @author: 黄途文
 * @param  params={
 * 				src:[lng,lat],//源坐标 （必须）
 * 				to:[lng,lat],//移动的目标坐标（必须）
 * 				feature:ol.Feature,//移动的要素（必须）
 * 				time:5 //移动的时间,单位秒,大于0
 * 			}
 */
HthxMap.featureMove = function(params) {
	// TODO 参数检测,未完成
	params.time = isNaN(params.time) ? 5 : params.time;
	params.time = params.time <= 0 ? 5 : params.time;

	/*
	 * 检测并初始化缓冲数据
	 * HthxMap.featureMove.features 需要移动的要素集合，
	 */
	var id = params.feature.getId();
	if (!HthxMap.featureMove.features) {
		HthxMap.featureMove.features = {};
	}
	if (!HthxMap.featureMove.features[id]) {
		HthxMap.featureMove.features[id] = {
			// 移动缓冲参数
			buffer : [],
			// 移动状态
			moving : false
		};
	}
	// 加入缓冲
	HthxMap.featureMove.features[id].buffer.push(params);

	// 开始移动
	if (!HthxMap.featureMove.features[id].moving) {
		HthxMap._featureMove._move(id);
	}
};
/**
 * <p>HthxMap.featureMove函数的依赖函数，不对外提供</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2017-03-10 </p>
 * @author: 黄途文
 */
HthxMap._featureMove = {
	/*
	 * 计算旋转方向
	 * 此方向只针对ol.style.Image.setRotation有效
	 */
	_getRotation : function(src, to) {
		var r = Math.atan2(to[1] - src[1], to[0] - src[0]);
		if (r < 0) {
			return 0 - r;
		} else if (r > 0) {
			return Math.PI * 2 - r;
		}
		return 0;
	},
	/*
	 * 将要素(feature)旋转rotation弧度
	 */
	_rotation : function(feature, rotation) {
		var style = feature.getStyle();
		var type = Object.prototype.toString.call(style);
		if (type === "[object Array]") {
			for ( var i = 0; i < style.length; i++) {
				var image = style[i].getImage();
				image.setRotation(rotation);
			}
		} else if (style instanceof ol.style.Style) {
			var image = style.getImage();
			image.setRotation(rotation);
		} else {
			// TODO ol.FeatureStyleFunction 未处理
		}
	},
	/*
	 * 要素移动的主流程函数
	 * fid已缓冲的要素(feature)ID
	 */
	_move : function(fid) {
		var obj = HthxMap.featureMove.features[fid];
		if (obj.buffer.length <= 0) {
			// 没有缓冲数据,结束移动，并标记为未移动
			obj.moving = false;
			return;
		}

		// 标记为移动
		obj.moving = true;

		// 旋转
		var rotation = HthxMap._featureMove._getRotation(obj.buffer[0].src,
				obj.buffer[0].to);// 旋转的角度
		HthxMap._featureMove._rotation(obj.buffer[0].feature, rotation);

		// 移动
		HthxMap._featureMove.__move(fid);
	},
	/*
	 * 实际操作要素移动的函数
	 */
	__move : function(fid) {
		var obj = HthxMap.featureMove.features[fid];
		var p = obj.buffer[0];
		var i = 25;// 每秒移动的次数
		var times = i * p.time;// 总共移动次数
		var delay = 1000 / i;
		var avg0 = (p.to[0] - p.src[0]) / (times * 1.0);
		var avg1 = (p.to[1] - p.src[1]) / (times * 1.0);
		var temp = [ p.src[0], p.src[1] ];
		var si = setInterval(function() {
			temp = [ temp[0] + avg0, temp[1] + avg1 ];
			p.feature.setGeometry(new ol.geom.Point(temp));
			times--;
			if (times <= 0) {
				clearInterval(si);
				if (obj.buffer.length > 0) {
					obj.buffer.shift();
					// 继续下一个坐标的移动
					HthxMap._featureMove._move(fid);
				}

			}
		}, delay);
	}
};