var timeoutDelayArray = [];	// 船舶延迟渲染
var moveDelay = null;	// 延迟一秒加载
var carImgId = null;
var FirstMapLoad =true;//地图首次加载

/**
 * 点击后获取业务库数据
 * @param carName 车牌名称
 */
function creatMapBusiness(carName){
	var url = HthxMap.Settings.rest+"/dangerVehicle/findMapData";
	var carData;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"carNo": carName},
	   success: function(objList){
		  if(objList['code'] === 1){
			  carData =  objList;
		  }
	   }
    }); 
	return carData;
}

/**
 * 保存返回的车辆数据
 * 
 * @param objList
 *            返回的数据
 * @param monitLayer
 *            车辆监控层
 */
function saveMapCarInfor(map,carData,monitLayer){
	// 车辆图片
	// carImgId = objList.data[0].carPicId;
	shipOnMap = [];
	var id, properties;
	// 保存地图上所有的车辆对象
	var features = monitLayer.getSource().getFeatures();
	var featureLen = features.length;
	if (carData) {
		realMonitLayer(map, monitLayer, carData, extentTemp, true);
	} else {
		// 删掉剩余的船舶
		clearExtentShip(monitLayer, extentTemp);
	}
}

/**
 * 实时显示车辆监控层
 * 
 * @param map
 *            地图
 * @param layer
 *            监控层
 * @param objList
 *            取到的车辆数据
 */
function realMonitLayer(map, layer, carData){
	var feature, shipImgBase, curShipData, ff, id;
	var features = [];
	var layerSource = layer.getSource();
	var id = carData.carrierName;
	var point=[carData.longitude,carData.latitude];
	point = ol.proj.transform([point[0],point[1]], "EPSG:4326",HthxMap.Settings.projection);
	/*var lon;
	var lat;
	 lon = toolMethod.floatToGreenMs(properties.tempLatLon[0]);
	 lat = toolMethod.floatToGreenMs(properties.tempLatLon[1]);
	lon = toolMethod.floatToGreenMs(point[0]);
	lat = toolMethod.floatToGreenMs(point[1]);*/
	// 转换坐标
	//TODO 正常使用 去掉注释
	/**正常状态*****start*/
	var transResult = initProjTransform(carData.longitude,carData.latitude,true);
	carData.tempLatLon = [carData.longitude,carData.latitude];
	//  推送数据 4326坐标，地图显示为2439 ，需要将 4326转化为2439 
	//carData.longitude =   transResult[0][0];
    //carData.latitude  =   transResult[0][1];																																					
   // carData.longitude =   transResult[0];
	//carData.latitude  =   transResult[1];
	/**正常状态*****end*/
	carData.longitude = point[0];
	carData.latitude = point[1];
	carData.tempLatLon = point;
	// 设置feature的样式
	ff = layer.getSource().getFeatureById(id);
	//判断图层是否有该辆车有更新，没有创造
	if(ff){
		shipOnMap[id] = false;
		ff.setProperties({"location": false});
		// 渔船弹出框移动
		if(ff.get("clickPop")){
			HthxMap.popDiv.constant.overlay.setPosition([carData.longitude, carData.latitude]);
		}
		// 新数据赋值给地图上的feature
		var ffProperties = ff.getProperties();
		var ffOnline  = ffProperties.status;// 以前状态
		var curOnline = carData.status;	// 最新状态
		var imgArr    = [];
		var popPanel  = "";
		var styleLen  = ff.getStyle().length;
		for(var k = 0; k < styleLen; k++){
			var image = ff.getStyle()[k].getImage();
			if(image && image.getRotation()){
				image.setRotation(HthxMap.transformImgRotation(carData.trueheading));
			}
		}
		
		ff.setProperties(createProperties(carData));
		// 根据实时数据修改弹出框的数据
		if($("#carInforDetail-Panel")[0]){
			popPanel = $("#carInforDetail-Panel").attr("data-type");
		}
		if(id == popPanel){
			//弹框现实的实时数据
			carData.longitude = toolMethod.floatToGreenMs(point[0]).replace('&quot;','"');
			carData.latitude  = toolMethod.floatToGreenMs(point[1]).replace('&quot;','"');
			carData.speed     = parseFloat(carData.speed).toFixed(1);
			carData.direction = mapAngleTrans(carData.direction);
			$("#carInforDetail-Panel").setFormSingleObj(carData);
			$("#realInfor-content").setFormSingleObj(carData);
		}
	}else{	
			var imgArr = [];
			feature = createFeature(carData);
			shipImgBase = HthxMap.createShipImg(feature);  // 基础图片
			imgArr.push(shipImgBase);				
			HthxMap.changeShipImg(feature, 0, imgArr);
			features.push(feature);
		}
	// 第一次加载车辆渲染到地图上，再进行显示控制过滤
	if(features.length){
		layer.getSource().addFeatures(features);
	}	
}



/**
 * 初始化车辆监控层————
 * 				初始化数据从业务库中车辆实时表中读取相关实时数据，然后在点击弹框时在关联库中其他业务数据
 * 
 * @param map 地图
 * @param layer  监控层
 * @param objList 取到的车辆数据
 */
function initMonitLayer(map, layer, carData){
	FirstMapLoad = false;
	var feature, shipImgBase, curShipData, ff, id;
	var features = [];
	var layerSource = layer.getSource();
	var carDataLen = carData.length;
	for(var i = 0; i < carDataLen; i++){
		detaliData = carData[i];
		id = detaliData.carrierName;
		detaliData.tempLatLon = [detaliData.longitude,detaliData.latitude];
		var point=[detaliData.longitude,detaliData.latitude];
		point = ol.proj.transform([point[0],point[1]], "EPSG:4326",HthxMap.Settings.projection);
		/*var lon;
		var lat;
		 lon = toolMethod.floatToGreenMs(properties.tempLatLon[0]);
		 lat = toolMethod.floatToGreenMs(properties.tempLatLon[1]);
		lon = toolMethod.floatToGreenMs(point[0]);
		lat = toolMethod.floatToGreenMs(point[1]);*/
		// 转换坐标
		//TODO 正常使用 去掉注释
		/**正常状态*****start*/
		var transResult = initProjTransform(detaliData.longitude,detaliData.latitude,true);
		detaliData.tempLatLon = [detaliData.longitude,detaliData.latitude];
		//  推送数据 4326坐标，地图显示为2439 ，需要将 4326转化为2439 
		//carData.longitude =   transResult[0][0];
	    //carData.latitude  =   transResult[0][1];																																					
	   // carData.longitude =   transResult[0];
		//carData.latitude  =   transResult[1];
		/**正常状态*****end*/
		detaliData.longitude = point[0];
		detaliData.latitude = point[1];
		detaliData.tempLatLon = point;
		// 转换坐标
//		var transResult = initProjTransform(detaliData.longitude,detaliData.latitude,true);
//		//待确认最终坐标系， 入库数据为 4326坐标，地图显示为2439 ，需要将 4326转化为2439 ，现阶段数据库为 2439坐标
//		detaliData.longitude =   transResult[0][0];
//		detaliData.latitude  =   transResult[0][1];	
		
		// 设置feature的样式
		ff = layer.getSource().getFeatureById(id);
		var imgArr = [];
		//创建feature 添加属性 properties
		feature = createFeature(detaliData);
		shipImgBase = HthxMap.createShipImg(feature);  // 基础图片
		imgArr.push(shipImgBase);				
		HthxMap.changeShipImg(feature, 0, imgArr);
		features.push(feature);
	}
	// 第一次加载车辆渲染到地图上，再进行显示控制过滤
	if(features.length){
		layer.getSource().addFeatures(features);
	}	
}

/**
 * 转换坐标函数
 * 
 * @param lon  经度
 * @param lat  纬度
 * @param flag "EPSG:4326", HthxMap.Settings.projection配置的默认显示坐标系 转换标志
 */
function initProjTransform(lon,lat,flag){
	//var points = [];
	//TODO 演示用数据转换
	//数据补零
	var lon=addZero(lon);
	var lar=addZero(lat);
	var points= [lon,lat].join(",");
	var result;
	if(flag){
		
//		return tempMapData[points];
//		var resultData;
//		for(var i in tempMapData){
//			if(points == i){
//				result = tempMapData[i];
//				break;
//			}
//		}
		//points.push(ol.proj.transform([parseFloat(lon),parseFloat(lat)], "EPSG:4326", HthxMap.Settings.projection));
	}else{
		//points.push(ol.proj.transform([parseFloat(lon),parseFloat(lat)],  HthxMap.Settings.projection,"EPSG:4326"));
	}
	
}

/**
 *经纬度补零
 * @param data 
 */
function addZero(data){
	var latS = data;
	var latA = latS.split(".")[1].split("");
	var latB = latA.length;
	if (latB === 4 ) {
		latA.push("0");
		latA.join("");
		return latA.join("");
	} else {
		return data;
	}
	
}
/**
 * 终端类型转换
 * @param type 
 */
function mapTerimType(type) {
	var result = "";
	if (type === 1) {
		result = "普通终端";
	} else if (type === 2) {
		result = "高精度终端";
	} else if (type === 3) {
		result = "北斗终端";
	}
	return result;
}


/**
 * 方向转换
 * @param type  
 */
function mapAngleTrans(angle) {
	var result = "";
	if (angle === 0 ||angle === 360) {
		result = "正西方向";
	} else if (angle === 90) {
		result = "正北方向";
	} else if (angle === 180) {
		result = "正东方向";
	} else if (angle === 270) {
		result = "正南方向";
	} else if (angle > 0 && angle < 90) {
		result = "西北方向";
	} else if (angle > 90 && angle < 180) {
		result = "东北方向";
	} else if (angle > 180 && angle < 270) {
		result = "东南方向";
	} else if (angle > 270 && angle < 360) {
		result = "西南方向";
	}
	
	return result;
}


/**
 * 车辆状态转换
 * 
 * @param state
 *            车辆状态 0-在线；1-离线
 */
function carState (state) {
	var result;
	if(state) {
		result = "离线";
	} else {
		result = "在线";
	}
	return result;
}

// 创建feature
function createFeature(carData) {
	var feature = new ol.Feature(createProperties(carData));
	feature.setId(carData.carrierName);
	return feature;
}


/**
 * 创建properties 为实时数据
 */
function createProperties(carData) {
	//非首次加载
	if(FirstMapLoad) {
		//业务库数据
		var businessData = creatMapBusiness(carData.carrierName);
	}
	var point = new ol.geom.Point([carData.longitude, carData.latitude]);
	var properties = {
			// 基础信息
			geometry: point// 位置点
			 //carState:carData.carState,//车辆状态
			// //车辆信息
			// carcolor:carData.carInfo.vehicleColor,//车牌颜色（可选）
			// vehicleBrand:carData.carInfo.vehicleBrand,//车辆品牌
			// vehicleBrandType: carData.vehicleBrandType,//车辆品牌类型
			// vehicleOutput: carData.vehicleOutput,//车辆排量
			// motorNo: carData.motorNo,//发动机号码
			// crossDomainType: carData.crossDomainType,//跨域类型
			// graduadedNo: carData.graduadedNo,//底盘号码
			// //终端信息
			// cardNum:carData.cardNum//SIM卡号
		};
	
	if(carData) {
		/* 位置数据 */
		properties.tempLatLon    = carData.tempLatLon;  // 弹框显示经纬度
		properties.id            = carData.carrierName; // 车辆Id
		properties.direction     = carData.direction;   // 终端上报位置时的方向 (度)
		properties.gpstime       = carData.gpstime;     // 终端上报位置时的时间
		properties.point         = carData.tempLatLon; // 经纬度
		properties.longitude     = carData.longitude;   // 经度
		properties.latitude      = carData.latitude;    // 纬度
		properties.protocoltype  = carData.protocoltype;// 终端类型
		properties.speed         = carData.speed;       // 终端上报位置时的速度
		properties.terminalID    = carData.terminalID;  // 终端ID
		properties.carrierName   = carData.carrierName; // 车牌号
		properties.temperature   = carData.temperature; // 温度
		properties.pressure      = carData.pressure;    // 压力
		properties.liquidLevel   = carData.liquidLevel; // 液位
		properties.tirePressure  = carData.tirePressure;// 胎压
		properties.simNum        = carData.simNum;      //终端手机号
		properties.elevation     = carData.elevation;      //终端手机号
		properties.orgin         = carData.orgin;       //终端来源
		properties.alarmNum      = carData.alarmNum;  //未处理的报警数量
		properties.enterpriseName= carData.enterpriseName;//企业名
		
	}
	return properties;
}

/**
 * 过滤运单、车辆信息
 */
var filterDate = function() {
	var businessData = creatMapBusiness(arguments[0]).data;
	businessData.WayBillInfoISNULL = false;
	businessData.carInfoISNULL = false;
	var carInfo = {}, WayBillInfo = {};
	//无运单数据
	if (businessData.WayBillInfo) {
		//运单数据中有为unll或者undefined
		$.each(businessData.WayBillInfo,function(index,content) {
	        if (!businessData.WayBillInfo[index]) {
	        	businessData.WayBillInfo[index] = '';
	        }
		});
		WayBillInfo = businessData.WayBillInfo;
	} else {
		WayBillInfo.consumerno  = "";
		WayBillInfo.consumerno  = "";		 
		WayBillInfo.checkno     = "";
		WayBillInfo.driver      = "";
		WayBillInfo.driverphone ="";
		WayBillInfo.carno       = "";
		WayBillInfo.handcarno   = "";
		WayBillInfo.deliveryunit     = "";
		WayBillInfo.achieveuint      = "";
		WayBillInfo.deliveryunittime = "";
		WayBillInfo.achievetime      = "";
		businessData.WayBillInfoISNULL = true;
	}
	
	//无车辆数据
	if (businessData.carInfo) {
		$.each(businessData.carInfo,function(index,content) {
	        if (!businessData.carInfo[index]) {
	        	businessData.carInfo[index] = '';
	        }
		});
		carInfo = businessData.carInfo;
	} else {
		//车辆数据中有为unll或者undefined
		carInfo.vehicleBrand   = "";
		carInfo.vehicleType    = "";
		carInfo.vehicleColor   = "";
		carInfo.enterpriseName = "";
		businessData.carInfoISNULL = true;
	}
	businessData.WayBillInfo = WayBillInfo;
	businessData.carInfo = carInfo;
	return businessData;
};


function getTerminalInfo(carName){
	var url = HthxMap.Settings.rest+"/terminal/findByPage";
	var carData;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"carrierName": carName},
	   success: function(objList){
		  if(objList['code'] === 1){
			  if(objList.rows){
				  carData =  objList.rows[0];
			  }
		  }
	   }
    }); 
	return carData;
}
//通过查询卡口对象 查询进园区的时间  
function getBayOneInfo(carName){
	var url = HthxMap.Settings.rest+"/bayOnet/findByCarName";
	var data;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"carName": carName},
	   success: function(obj){
		  if(obj['code'] === 1){
			  data =  obj.data;
		  }
	   }
    }); 
	return data;
}

//通过app账号 查询app登录状态
function findLoginState(driverid){
	var url = HthxMap.Settings.rest+"/appLogin/findLoginState";
	var data;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"driverid": driverid},
	   success: function(obj){
		  if(obj['code'] === 1){
			  data =  obj.data;
		  }
	   }
    }); 
	return data;
}
/**
 * 注入app账号
 * @param driverId
 */
function setAppAccount(driverId){
	var url = HthxMap.Settings.rest+"/appLogin/setAppAccount";
	var data;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"driverId": driverId},
	   success: function(obj){
		  if(obj['code'] === 1){
			  data =  obj.data;
		  }
	   }
    }); 
}
/**
 * 注入车牌号
 * @param driverId
 */
function setCarrierName(carrierName){
	var url = HthxMap.Settings.rest+"/appLogin/setCarrierName";
	var data;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"carrierName": carrierName},
	   success: function(obj){
		  if(obj['code'] === 1){
			  data =  obj.data;
		  }
	   }
    }); 
}

function clearAccountAndCarrierName(){
	var url = HthxMap.Settings.rest+"/appLogin/clearAccountAndCarrierName";
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   success: function(obj){
		  if(obj['code'] === 1){
			  data =  obj.data;
		  }
	   }
    }); 
}
//通过车牌号查询运单状态的时间   返回值data中：0为待运输或已完成 或没有运单   1为取货中  2送货中 
function findCheckStatus(carrierName){
	var url = HthxMap.Settings.rest+"/wayBill/findCheckStatus";
	var data;
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   data: {"carrierName": carrierName},
	   success: function(obj){
		  if(obj['code'] === 1){
			  data =  obj.data;
		  }
	   }
    }); 
	if( data ===3 ){
		return "送货中";
	}else if(data ===2){
		return "取货中";
	}else{
		return "空闲中";
	}
}

// 车辆资料卡点击弹框
function boatDiv(feature) {
	var properties = feature.getProperties();
	var remove = properties.remove;
	var orgin=properties.orgin;
	//运单、车辆信息
	var businessData = new filterDate(properties.carrierName);
	var carInfo = businessData.carInfo;
	var wayBillInfo = businessData.WayBillInfo;
	//注入车牌号
	setCarrierName(properties.carrierName);
	//登录状态
	var loginState="未登录";
	if(wayBillInfo.driverid !== '' && wayBillInfo.driverid !==null && wayBillInfo.driverid){
		//通过驾驶员身份证去查询app是否登录
		loginState = findLoginState(wayBillInfo.driverid);
		if( loginState=='0'){
			loginState="已登录";
		}else{
			loginState="未登录";
		}
		//车辆驾驶员app账号注入 driverid驾驶员身份证号
		setAppAccount(wayBillInfo.driverid);
	}
	var checkStatus = findCheckStatus(properties.carrierName);
	//进入卡口信息
	var bayOne = getBayOneInfo(properties.carrierName);
	var inTime = '';
	if( bayOne){
		inTime = bayOne.verifytime;
	}else{
		inTime = "未进园";
	}
	//终端信息
	var terminalInfo;
	var terminalSerialID = "";
	var manufactureID = "";
	var cardNum ="";
	terminalInfo =  getTerminalInfo(properties.carrierName);
	if(terminalInfo ){
		terminalSerialID = terminalInfo.terminalSerialID;
		manufactureID = terminalInfo.manufactureID;
		cardNum = terminalInfo.cardNum;
		if(cardNum.length ==12){
			cardNum = cardNum.substring(1);
		}
	};
	var coor = [properties.lon, properties.lat];	
	feature.set("clickPop", true);
	var strs = "";
	var point = ol.proj.transform([parseFloat(properties.longitude),parseFloat(properties.latitude)], HthxMap.Settings.projection,"EPSG:4326");
	var lon;
	var lat;
	lon = toolMethod.floatToGreenMs(point[0]);
	lat = toolMethod.floatToGreenMs(point[1]);
	// 终端类型转换
	var terimType = mapTerimType(properties.protocoltype);
	// 方向转换
	var angle = mapAngleTrans(properties.direction);
	// 车辆状态转换
	var state = carState(properties.state);
	var strArr = [];
	/*if(remove == "1" || businessData.WayBillInfoISNULL || businessData.carInfoISNULL){
		strArr.push('<div class="panel panel-gray-sm " data-type="'+properties.carrierName+'" id="carInforDetail-Panel">');
		// 左侧标导航栏
		strArr.push('<div id="carInforDeatil" style="text-align:center;">'+properties.carrierName+'</div></div>');
	}else{*/
		strArr.push('<div class="panel panel-gray-sm " data-type="'+properties.carrierName+'" id="carInforDetail-Panel"><div class="">');
		// 左侧标导航栏
		strArr.push('<div id="carInforDeatil"><div id="carInforLeftPanel"><div class="carInforLeftPanel-line"><div class="arrow-left"></div></div>');
		strArr.push('<div class="carInforLeftPanel-icon"><ul class="list-group" id="collapseVenn">');
		strArr.push('<li class="list-group-item active"><a href="#detailInfor" class="" data-toggle="tab">');
		strArr.push('<i class="list-icon1"><img src="../map/business/img/carInfor/list-icon1.png"></i></a></li>');
		strArr.push('<li class="list-group-item"><a href="#carInfor" class="" data-toggle="tab">');
		strArr.push('<i class="list-icon2"><img src="../map/business/img/carInfor/list-icon2.png"></i></a></li>');
		strArr.push('<li class="list-group-item"><a href="#teriminalInfor" class="" data-toggle="tab">');
		strArr.push('<i class="list-icon3"><img src="../map/business/img/carInfor/list-icon3.png" ></i></a></li>');
		strArr.push('<li class="list-group-item"><a href="#waybillInfor" class="" data-toggle="tab">');
		strArr.push('<i class="list-icon4"><img src="../map/business/img/carInfor/list-icon4.png"></i></a></li>');
		strArr.push('<li class="list-group-item" style="height:68px"><a href="#shortcutInfor" class="" data-toggle="tab">');
		strArr.push('<i class="list-icon5"><img src="../map/business/img/carInfor/list-icon5.png"></i></a></li></ul></div></div>');
		
		// 右侧内容1(车辆信息)
		strArr.push('<div class="tab-content" id="carInfor-content"><div class="tab-pane fade in active" id="detailInfor">');
		strArr.push('<div class="panel-heading " style="height: 24px;"><span class="panel-title">');
		strArr.push('</span><span class="panel-close" onclick="removePopup(\''+properties.carrierName+ '\',\'' + wayBillInfo.id + '\')"></span></div>');
		if (businessData.carPicId) {
			strArr.push('<img id="carPhoto" src="'+$.backPath+'/dangerVehicle/downloadFile/'+businessData.carPicId+'">');
		} else {//暂无车辆图片TODO 测试用图片
			strArr.push('<img id="carPhoto" src="/JXWL/images/test.png">');
		}
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">车牌号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+properties.carrierName+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">车辆品牌 :</label><span class="look-formPanel carinforfont" >'+carInfo.vehicleBrand+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">车辆类型 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+carInfo.vehicleType+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">车辆归属类型:</label>');
		strArr.push('<span class="look-formPanel carinforfont" >本地</span></div></div>');
		strArr.push('<div class="row"><div style="width:434px;float:left;margin-left:10px;"><label class="carinforfont">时速 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" name="speed">'+parseFloat(properties.speed).toFixed(1)+'</span><span>km/h</span></div></div>');
		strArr.push('<div class="row" ><div style="width:434px;float:left;margin-left:10px;"><label class="carinforfont">所属企业 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+carInfo.enterpriseName+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">驾驶员姓名 :</label>');
		strArr.push('<span class="look-formPanel  carinforfont" >'+wayBillInfo.driver+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">驾驶员电话 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.driverphone+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">车辆运输状态 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+checkStatus+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont" >报警状态 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" id="alarmState" >'+properties.alarmState+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">终端通讯状态 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >在线</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">移动APP通讯状态 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" id ="loginState" >'+loginState+'</span></div></div></div>');
		strArr.push('<div class="tab-pane fade" id="carInfor"><div class="panel-heading " style="height: 32px;">');
		
		// 右侧内容2---车辆信息
		strArr.push('<span class="">车辆信息</span><span class="panel-close" onclick="removePopup(\''+properties.carrierName+'\',\'' + wayBillInfo.id + '\')"></span></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">车牌号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+properties.carrierName+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">车辆品牌 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+carInfo.vehicleBrand+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">车辆类型 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+carInfo.vehicleType+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">车身颜色:</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+carInfo.vehicleColor+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">车辆运输状态 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+checkStatus+' </span></div><div class="row-second">');
		strArr.push('<label class="carinforfont">所属企业 :</label> <span class="look-formPanel carinforfont" >'+carInfo.enterpriseName+'</span></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">驾驶员姓名 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.driver+'</span></div><div class="row-second">');
		strArr.push('<label class="carinforfont">驾驶员电话 :</label><span class="look-formPanel carinforfont" >'+wayBillInfo.driverphone+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">身份证 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.driverId+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">从业证号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.driverno+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-single"><label class="carinforfont">进园时间 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+inTime+'</div></div>');
		strArr.push('</div></div>');
		
		// 右侧内容3---终端信息
		strArr.push('<div class="tab-pane fade" id="teriminalInfor"><div class="panel-heading " style="height: 32px;">');
		strArr.push('<span class="">终端信息</span><span class="panel-close" onclick="removePopup(\''+properties.carrierName+'\',\'' + wayBillInfo.id + '\')"></span></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">终端编号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+terminalSerialID+'</span></div><div class="row-second">');
		strArr.push('<label class="carinforfont">终端厂商 :</label><span class="look-formPanel carinforfont" >'+manufactureID+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">经度 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" name="longitude">'+lon+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">纬度:</label>');
		strArr.push('<span class="look-formPanel carinforfont" name="latitude" >'+lat+'</span></div></div><div class="row">');
		strArr.push('<div class="row-first"><label class="carinforfont">时速 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" name="speed"> '+parseFloat(properties.speed).toFixed(1)+'</span><span>km/h</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">SIM卡号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" id="_cardNum">'+cardNum+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">方向 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" name="direction" >'+angle+'</span></div><div class="row-second">');
		strArr.push('<label class="carinforfont">高程 :</label><span class="look-formPanel carinforfont" >'+properties.elevation+'</span></div></div>');
		strArr.push('');
		strArr.push('</div>');
		
		// 右侧内容4--运单信息
		strArr.push('<div class="tab-pane fade" id="waybillInfor"><div class="panel-heading " style="height: 32px;">');
		strArr.push('<span class="">运单信息</span><span class="panel-close" onclick="removePopup(\''+wayBillInfo.carrierName+'\',\'' + wayBillInfo.id + '\')"></span></div>');
		strArr.push('<div class="row"><div class="row-single"><label class="carinforfont">托运单号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.checkno+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-first"><label class="carinforfont">驾驶员姓名 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.driver+'</span></div><div class="row-second">');
		strArr.push('<label class="carinforfont">驾驶员电话:</label><span class="look-formPanel carinforfont" >'+wayBillInfo.driverphone+'</span></div></div><div class="row">');
		strArr.push('<div class="row-first"><label class="carinforfont">车牌号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.carno+'</span></div>');
		strArr.push('<div class="row-second"><label class="carinforfont">挂车牌号 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.handcarno+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-single"><label class="carinforfont">发货单位 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.deliveryunit+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-single"><label class="carinforfont">收货单位 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.achieveuint+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-single"><label class="carinforfont">装货时间 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.deliveryunittime+'</span></div></div>');
		strArr.push('<div class="row"><div class="row-single"><label class="carinforfont">卸货时间 :</label>');
		strArr.push('<span class="look-formPanel carinforfont" >'+wayBillInfo.achievetime+'</span></div></div> </div>');
		
		// 右侧内容--快捷操作
		strArr.push('<div class="tab-pane fade" id="shortcutInfor"><div class="panel-heading " style="height: 32px;">');
		strArr.push('<span class="">快捷操作</span><span class="panel-close" onclick="removePopup(\''+properties.carrierName+'\',\'' + wayBillInfo.id + '\')"></span></div>');
		strArr.push('<div class="shortcutiFirstLine"><div class="eachIcon">');
		//如何使嘉兴则加此按钮
		if("嘉兴位置服务平台" == orgin){
			strArr.push('<div><i><img class="shortcuticon" id="carDispatch" onclick="mapCarDispatch.showPanel(\''+properties.id+'\',\'' + properties.carrierName +'\')" src="../map/business/img/carInfor/shortcut-icon1.png"></i></div>');
			strArr.push('<span>车辆调度</span></div><div class="eachIcon">');
		}	
		strArr.push('<div><i><img class="shortcuticon" onclick="historyTrack.init(\''+properties.id+'\')"  src="../map/business/img/carInfor/shortcut-icon3.png"></i></div>');
		strArr.push('<span>历史轨迹</span></div><div class="eachIcon">'); 
		strArr.push('<div><i><img class="shortcuticon" id="cargoMonitor-img" onclick="cargoMonitor.init(\''+properties.id+'\','+true+ ','+false+')" src="../map/business/img/carInfor/shortcut-icon6.png"></i></div>');
		strArr.push('<span>货物监控</span></div><div class="eachIcon">');
		strArr.push('<div><i><img class="shortcuticon" id="alarmLog-img"     onclick="cargoMonitor.init(\''+properties.id+'\','+true + ','+true+')" src="../map/business/img/carInfor/shortcut-icon5.png"></i></div>');
		strArr.push('<span>报警记录</span></div><div class="eachIcon">');
		strArr.push('<div><i><img class="shortcuticon" onclick="notFinal.showPanel()" src="../map/business/img/carInfor/shortcut-icon7.png"></i></div>');
		strArr.push('<span>车辆监听</span></div><div class="eachIcon">');
		strArr.push('<div><i><img class="shortcuticon" onclick="notFinal.showPanel()" src="../map/business/img/carInfor/shortcut-icon9.png"></i></div>');
		strArr.push('<span>远程控制</span></div><div class="eachIcon">');
		strArr.push('<div><i><img class="shortcuticon" onclick="notFinal.showPanel()" src="../map/business/img/carInfor/shortcut-icon8.png"></i></div>');
		strArr.push('<span>车辆抓拍</span>');
		strArr.push('</div></div></div></div></div>');
		strs = strArr.join("");
//	}
	return strs;
}
