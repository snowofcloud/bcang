/**
 * <p>Description: 渔船缓冲区查询</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-28 </p>
 * @author: 孙耀  何泽潘修改
 * @extends：
 * @param:
 */
  
//渔船缓冲区查询入口
function queryBufferShip(){
	var id = $(this).attr("id");
	var start = id.indexOf("B");
	var str = id.slice(0, start);
	var str1 = str.slice(0,1).toUpperCase();
	var searchType = str1 + str.slice(1);
	
	beginSearch(searchType);
}

//渔船缓冲区查询
function beginSearch(searchType){
	removeMapEvents();
	var id = searchType.slice(0,1).toLowerCase() + searchType.slice(1);
	var typeId = id  + "buffer";
	//先清除当前激活控件样式，再添加即将要激活的控件样式
	if(HthxMap.curControl.target){
		$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
		$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
	}	
	$("#" + typeId).removeClass(typeId + "Inactivate");
	$("#" + typeId).addClass(typeId + "Activate");
	//记录地图当前激活的控件
	HthxMap.curControl.target = id + "Buffer";
	HthxMap.curControl.displayClass = typeId;
	
    addInteraction(searchType, typeId);
}

function addInteraction(searchType, typeId){	
	if(!HthxMap.curFeatureInteraction){
		HthxMap.curFeatureInteraction = "active";
	}
	var layers = map.getLayers();
	var featureLayer =  HthxMap.getLayerById("featureLayer");

	//先移除之前添加的feature//此类相关的删除事件，应该提取出来
	if(HthxMap.shipBufferQueryFeature.length){
		var index = HthxMap.shipBufferQueryFeature[0].getId();
    	HthxMap.clearMeasure(index);
	}
//    HthxMap.shipBufferQueryFeature = [];
    
	var geometryFunction, maxPoints;
	var boxType = '';
	var searchRadius;
    if('Box' === searchType){
		searchType = 'LineString';
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
	if('Line' === searchType){
		searchType = 'LineString';
	}
	
	var	drawboatSearch = new ol.interaction.Draw({
			source:featureLayer.getSource(),
			type:searchType,
		    geometryFunction: geometryFunction,
		    maxPoints: maxPoints,
			style:creatStyle.interactionDraw()
		});
	
	
	HthxMap.curMapInteraction = drawboatSearch;
	map.addInteraction(drawboatSearch);
	
	var queryBoatfeatures = [];  //用于渔船缓冲区查询分页功能
    var bu = [];
    
    isDisplayControlledArray = isDisplayControlled();
    if(isDisplayControlledArray[0]){
    	createSearchTooltip();    //查询提示
    }
    
    HthxMap.mouseUnbindEvent(map, "crosshair");
	drawboatSearch.on('drawstart',function(e){	
		sketch = e.feature;	
		if(searchTooltip){
			map.on('pointermove', searchMoveHandler);
			HthxMap.curMapOnPointerMove = searchMoveHandler;
		}
		
		remVectorDrawedFeature();
		if($("#btn_searchRadius").val() !== ""){
			searchRadius = parseFloat($("#btn_searchRadius").val());
			if(isNaN(searchRadius)){
				searchRadius = 0;
			}else{
			    //将距离转换为度
				searchRadius = searchRadius/111000;
			}
		}else{
			searchRadius = 0;
		}
		
	});
	drawboatSearch.on('drawend', function(e){
		if(searchTooltip){    //移除提示
			map.removeOverlay(searchTooltip);
			sketch = null;
		}
		
		HthxMap.mouseBindEvent(map);
		e.feature.set("clear", true);
		var geomSketch = e.feature.getGeometry();		
		var bufferUtil = new HthxMap.BufferUtil();
		
		e.feature.setId(HthxMap.measureIndex);
        var coor = e.feature.getGeometry().getLastCoordinate();
        HthxMap.createDeleteElement(coor, HthxMap.measureIndex);
        HthxMap.measureIndex++;
	        
		HthxMap.shipBufferQueryFeature.push(e.feature);
		//缓冲区半径不为0
		if(searchRadius){
			if(!boxType){
			    bu = bufferUtil.getBuffer(geomSketch, searchRadius, searchType);
			}else{
				bu = bufferUtil.getBuffer(geomSketch, searchRadius, boxType);
			}
			featureLayer.getSource().addFeatures(bu);
			var geoms = [];
			var tempGeoms;
			if('Circle' === searchType || "Point" === searchType){
				bu[0].set("clear", true);	//缓冲区可删除
				tempGeoms = bu[0].getGeometry();
				HthxMap.shipBufferQueryFeature.push(bu[0]);
			}else{
				for(var i = 0;i < bu.length;i++){
					bu[i].set("clear", true);	
					HthxMap.shipBufferQueryFeature.push(bu[i]);  //保存要素，再次绘制时删除
					var featureGeom = bu[i].getGeometry().getCoordinates();
					if(featureGeom.length === 2){
						geoms.push(featureGeom[0]);
						geoms.push(featureGeom[1]);
					}else{
						var featureGeomT = featureGeom[0].length;
							for(var k = 0;k < featureGeomT;k++){
								geoms.push(featureGeom[0][k]);     //因为每一段的坐标点为同一个，所以只取一个
						}
					}
				}
				
		        var tempFeature = new ol.Feature({
					geometry: new ol.geom.Polygon([geoms])
		        });
			    tempGeoms = tempFeature.getGeometry();
			}
			geomSketch = tempGeoms;
		}else{
			//缓冲区半径为0,直接获取画出的多边形
			
		}
		bindConditionLabel(HthxMap.commonConstant.bufferSearch);
		getShipsByArea(geomSketch);
        
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
}
//渔船区域搜索
function getShipsByArea(geoms){
	var queryBoatfeatures = [];  //用于渔船缓冲区查询分页功能
	HthxMap.shipIdArr = [];     //保存shipIds，供群操作
	if(!HthxMap.curMapLevel()){//小比例尺下，放大地图
		var data = '';
		if(geoms instanceof ol.geom.Circle){
			var center = geoms.getCenter();
			var radius = geoms.getRadius();
			for(var i = 0; i < 2;) {
				var x = center[0] + radius * Math.cos(Math.PI * i);
				var y = center[1] + radius * Math.sin(Math.PI * i);
				var obj = x.toString().slice(0,10)+" "+y.toString().slice(0,10) + ',';
				data += obj;
				i += 1/180;
			}
			data = data.substring(0, data.length-1);
		}else{
			var coors = geoms.getCoordinates()[0];
			for(var i=0; i<coors.length; i++){
				if(i == (coors.length-1)){
					data += coors[i][0]+" "+coors[i][1];
					break;
				}
				data += coors[i][0]+" "+coors[i][1] + ',';
			}
		}
		//请求小比例尺下的区域查询
		var mapUrl = HthxMap.Settings.rest+"shipOrientate/findShipByPoints";
		$.ajax({
	 		   type: "POST",
	 		   url: mapUrl,
	 		   data: {"points": JSON.stringify(data), 'isOnline': 'true', 'isRegistered': 'true'},
	 		   success: function(objList){
				  if(objList['code'] === 1){
					  var shipArr = objList['data'];
					  
					  var len = 0;
					  if(shipArr){
						  len = shipArr.length
					  }
					  var temArr = [];             //保存注册终端，未注册终端不需要查询
					  //保存所有的船舶ID，供群操作
					  for(var i=0; i<len; i++){
						  if(shipArr[i].shipId != shipArr[i].terminalId){
							  HthxMap.shipIdArr.push(shipArr[i].shipId);
							  temArr.push(shipArr[i]);
						  }
						  
					  }
					  //保存总数量
				      HthxMap.groupOptionDataLen = temArr.length;
					  var featureResult = convertBoatInfo(temArr);
				      var features = featureResult.features;
				      
				      //渔船信息分页查询
				      queryPageShip(features);
				      openLeftMenuOnSearch();
				  }else{
//					  console.log(objList['msg']);
				  }
			   }
		})
	}else{
		var mLayer = HthxMap.getLayerById("monitorLayer");
		var monitorFeatures = mLayer.getSource().getFeatures();
		var monitorFeatureLen = monitorFeatures.length;
		for(var i = 0;i < monitorFeatureLen;i++){
			var style = monitorFeatures[i].getStyle();  //显示控制过滤
			if(!style){
				continue;
			}
			var monitGeo = monitorFeatures[i].getGeometry().getCoordinates();
			var x = monitGeo[0];
			var y = monitGeo[1];
			if(geoms.containsXY(x,y)){
				var properties = monitorFeatures[i].getProperties();
				if(properties.id != properties.terminalID){	//查询已注册终端  
				    queryBoatfeatures.push(monitorFeatures[i]);
				    //保存所有的船舶ID，供群操作
				    HthxMap.shipIdArr.push(monitorFeatures[i].getId());	
				}
			}
		}	
		//保存总数量
	     HthxMap.groupOptionDataLen = queryBoatfeatures.length;
		//渔船信息分页查询
	    queryPageShip(queryBoatfeatures);
	    
	    openLeftMenuOnSearch();
	}
}
//打开左侧栏
function openLeftMenuOnSearch(){
	//打开渔船侧栏
//    var lis = $(".left-mainCard ul li");
	var div = $("#left-group >div >div");
    //先隐藏
//	if(!$("#left-group").hasClass("hidden") && !$(div[0]).hasClass("active")){
//		$("#left").animate({width:59+"px"}, 500);
//		$("#left-group").addClass("hidden");
//		$("#shrink").removeClass("left-show");
//		$("#shrink").addClass("left-hidden");
//		lis.removeClass("active");
//		div.removeClass("active");
//		$(".left-mainCard ul li a")[0].click();
//	}
	//显示
	if($("#left-group").hasClass("hidden") || !$(div[0]).hasClass("active")){
//		$(lis[0]).addClass("active");
//		$(div[0]).addClass("active");
//		$("#left").animate({width:360+"px"},500);
//		$("#left-group").removeClass("hidden");
//		$("#shrink").removeClass("left-hidden");
//		$("#shrink").addClass("left-show");
		$(".left-mainCard ul li a")[0].click();
	}
}
//渔船缓冲区分页查询
function queryPageShip(queryBoatfeatures){
	var total = queryBoatfeatures.length;  //总的记录个数
	var pageIndex = 1;   //第一页
	var pageSize = parseInt(addPageSelect());  //每一页大小
	bufferShipFeature = [];
	var tempArr = [];
	for(var i = 0; i < total; i++){
        if(i == 0){
    	    tempArr.push(queryBoatfeatures[i]);
        }else{
    	    if(i % pageSize == 0){
    		    bufferShipFeature.push(tempArr);
    		    tempArr = [];
    		    tempArr.push(queryBoatfeatures[i]);
    	    }else{
    		    tempArr.push(queryBoatfeatures[i]);
    	    }
        }   
	} 
	if(tempArr){
		bufferShipFeature.push(tempArr);
	}	
	queryFeatureShip(pageIndex, pageSize);
}

function queryFeatureShip(pageIndex, pageSize) {
	var total = 0;
	for(var i = 0; i < bufferShipFeature.length; i++) {
		total += bufferShipFeature[i].length;
	}
	clearMonitorMarker(monitLayer);
	initBoatInfo(map, bufferShipFeature[pageIndex-1]);
	
	if(bufferShipFeature[pageIndex-1] && bufferShipFeature[pageIndex-1].length) {
		setPage(ID$("allShip_1Page"), total, pageIndex, pageSize, queryFeatureShip);
	}
}

function doubleClick(){
	map.addInteraction(new ol.interaction.DoubleClickZoom);
}

//移除地图事件
function removeMapEvents(){
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
	
	var interactions = map.getInteractions();
	var interactionLen = interactions.getLength();
	var tempInteraction;
	var tempInteractionArr = [];
	for(var i = 0; i< interactionLen; i++){
		tempInteraction = interactions.item(i);
    	if(tempInteraction instanceof ol.interaction.DoubleClickZoom){
    		tempInteractionArr.push(tempInteraction);
    	}
    }
	for(var i = 0;i < tempInteractionArr.length;i++){
		map.removeInteraction(tempInteractionArr[i]);
	}
}

function searchMoveHandler(e) {
	if(e.dragging){
		return;
	}
	
    if(sketch){
    	var geom = sketch.getGeometry();
    	var tooltipCoord = geom.getLastCoordinate();

    	searchTooltipElement.innerHTML = isDisplayControlledArray[1];
		searchTooltip.setPosition(tooltipCoord);
    }
}

var isDisplayControlledArray;
var searchTooltipElement;
var searchTooltip;
var sketch;
function createSearchTooltip() {
    searchTooltipElement = document.createElement('div');
    searchTooltipElement.className = 'mtooltip mtooltip-static';
    searchTooltip = new ol.Overlay({
	    element: searchTooltipElement,
	    offset: [0, -15],
	    positioning: 'bottom-center'
    });
    map.addOverlay(searchTooltip);
}