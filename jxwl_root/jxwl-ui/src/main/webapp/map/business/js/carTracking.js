/**
 * <p>Description: 渔船的跟踪/取消跟踪，当点击跟踪时对应点击对象变为“取消跟踪”，反之。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-17 </p>
 * @author: 张琴
 * @param：featureid(渔船要素id),_this(点击对象),map(必选),layer(渔船监控图层).
 * <p>修改: 2016-1-13，添加群跟踪 </p>
*/
function shipTracking(featureid, _this, layer, map) {
	pop_id = 1;
	ARROWPNG = HthxMap.Settings.IMG.trackReplay.ARROWPNG;
	var feature = layer.getSource().getFeatureById(featureid);
	var properties = feature.getProperties();
	var pngUrl = [];		
	var style = feature.getStyle();
	var styleLen = style.length - 1;
	if(!feature){
		Messager.alert({Msg:"该船舶在显示控制里进行了过滤,无法跟踪/取消跟踪!", isModal: true}); 
		return;
	}
	if(!style){
		Messager.alert({Msg:"该船舶在显示控制里进行了过滤,无法跟踪!", isModal: true}); 
		return;
	}
	if(!properties.shipStatus){  //properties.shipStatus 表示车辆当前是已被跟踪还是未被跟踪
		if(HthxMap.followingCar != ""){
			Messager.alert({Msg:"已有一辆车正在被跟踪，当前车辆无法跟踪！", isModal: true}); 
			return;
		}
		_this.text(HthxMap.commonConstant.unShipTrack);
		for(var i = 0; i < styleLen; i++){
			pngUrl.push(style[i].getImage().getSrc());			
		}
		pngUrl.push(HthxMap.Settings.configData.targetImg.track);
		HthxMap.changeShipImg(feature, properties.direction, pngUrl);
		feature.setProperties({shipStatus:true});
		HthxMap.followingCar = featureid;
		creatTrackingTips(map, featureid, layer);
	}else{
		_this.text(HthxMap.commonConstant.onShipTrack);
		for(var i = 0; i < styleLen-1; i++){
			pngUrl.push(style[i].getImage().getSrc());			
		}
		HthxMap.changeShipImg(feature, properties.direction, pngUrl);
		feature.setProperties({shipStatus: false});
		HthxMap.followingCar = "";
		deletTrackingTips(map, featureid); 
		//deletTrackingTips2(map, featureid, layer); 
	}	
}

//创建渔船跟踪时的弹出气泡
function creatTrackingTips(map, featureid, layer){
	var feature = layer.getSource().getFeatureById(featureid);
	if(!feature){	
		return;
	}
	var style = feature.getStyle();
	if(!style){	//轨迹
		return;
	}
	creatTrackingLay(map, feature, layer);	
	//drawPoint(feature, layer);
	/*if(sessionStorage.getItem("oldLocation") != null){
		var newLon,newLat,oldLon,oldLat;
		drawPath(feature.getProperties().longitude, feature.getProperties().latitude,
				 	sessionStorage.getItem("oldLocation").split(",")[0], sessionStorage.getItem("oldLocation").split(",")[1],layer);
	}
	sessionStorage.setItem("oldLocation", sessionStorage.getItem("newLocation"));*/
}

//创建跟踪车辆图层
function creatTrackingLay(map, feature, layer){
	var properties = feature.getProperties();
	var featureid = properties.id;
	var coordinate = [properties.longitude, properties.latitude];
	sessionStorage.setItem("newLocation", properties.longitude+","+properties.latitude);
	/*var arr = [];
	arr.push("车辆名："+properties.carrierName+"<br/>");
	arr.push("类型："+"<br/>");
	arr.push("纬度："+toolMethod.floatToGreenMs(properties.latitude)+"<br/>");
	arr.push("经度："+toolMethod.floatToGreenMs(properties.longitude)+"<br/>");
	arr.push("方向："+properties.direction+"<br/>");
	arr.push("速度："+properties.speed+"<br/>");
	var contentStr = arr.join("");
	var popup = document.createElement('div');
	popup.innerHTML = contentStr;
	popup.className = 'mtooltip mtooltip-measure';
	var overlay = new ol.Overlay({
       element: popup,
       offset: [0,-15],
       positioning: 'bottom-center'
    });
	overlay.setPosition(coordinate);
	overlay.set("isTrackship",true);
	overlay.set("id",featureid);
	map.addOverlay(overlay);*/
	var overlay = new ol.Overlay({});
	overlay.setPosition(coordinate);
	overlay.set("isTrackship",true);
	overlay.set("id",featureid);
	map.addOverlay(overlay);
	//如果之前已经存在，则先删除
	//deletTrackingTips(map, featureid);
    HthxMap.removeUnusedPop();
	
}



/**
 * 绘制箭头
 */
function drawPoint(feature, layer){
	var lon = feature.getProperties().longitude;
	var lat = feature.getProperties().latitude;
	var properties = feature.getProperties();
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
	var labelContent = "北纬:"+latitude+",东经:"+longitude;
	var feature = new ol.Feature(new ol.geom.Point([lon,lat]));
	feature.setStyle(new ol.style.Style({
				image: new ol.style.Icon({
				    opacity: HthxMap.opacity,
				    rotation: HthxMap.transformImgRotation(properties.direction),//行驶方向
					src: pngurl
				})
				/*text: new ol.style.Text({
					text:  labelContent,
					textAlign: "lm",
					font:"15px Microsoft YaHei",
					fill: new ol.style.Stroke({
						color: "red"
					})
				})*/
			}
	));
	feature.setProperties({
		carName : properties.carrierName,
		terminalType : properties.terminalType,
		status: properties.status,
		shipDir : parseFloat(properties.direction),
		pop_carSpeed : properties.speed,
		pop_lon : lon,
		pop_lat : lat,
		heading : properties.heading,
		id: properties.carrierName,
		pop_date : properties.gpstime
	});
	feature.set("terminalTypeImage", pngurl);
	feature.setProperties({"pop_id": pop_id++});
	layer.getSource().addFeature(feature);
};

/**
 * 绘制轨迹路径
 */
function drawPath(lon, lat, preLon, preLat, layer){
	var lineArr = [];
	var count = 50;
	var countX = (lon - preLon)/count;
	var countY = (lat - preLat)/count;
	for(var i=0; i<count; ){
		var pointArr = [];
		pointArr.push([preLon + countX*i, preLat + countY*i]);
		pointArr.push([preLon + countX*(i+1), preLat + countY*(i+1)]);
		lineArr.push(pointArr);
		i+=2;
	}
	var path = new ol.Feature(new ol.geom.MultiLineString(lineArr));
	path.setStyle(new ol.style.Style({
		stroke: new ol.style.Stroke({
			width: 2,
            color: '#3399CC'
		})
	}));
	path.setProperties({"carName":"line"});
	path.setProperties({"pop_id": pop_id++});
	path.set("noClickEvent", true);
	layer.getSource().addFeature(path);
}

//根据船舶id删除跟踪时的弹出气泡,不传id则删除所有的跟踪气泡！！
function deletTrackingTips(map, featureid){
	var overlays = map.getOverlays();
	var len = overlays.getLength();
	for(var i=0; i<len; i++){
  	    if(overlays.item(i).get("isTrackship")){
  	    	if(featureid && overlays.item(i).get("id") == featureid){
  	    		map.removeOverlay(overlays.item(i));
  	  		    break;
  	    	}else if(!featureid){
  	    		map.removeOverlay(overlays.item(i));
  	    		i--;
  	    		len--;
  	    	}  		    
  	    }
    }
}

function deletTrackingTips2(map, featureid, layer){
	var source = layer.getSource();
	var feature = source.getFeatureById(featureid);
	source.removeFeature(feature);
}

