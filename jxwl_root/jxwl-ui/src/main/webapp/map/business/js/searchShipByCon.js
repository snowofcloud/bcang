/**
 * <p>Description: 条件查询渔船</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-11-25 </p>
 * @author: 张琴
 * @param：
*/

//属性查询请求
function queryShip(pageIndex, pageSize){
	var options = {
			"shipName": "",
			"shipType":"",
			"terminalType":"",
			"terminalNum":"",
			"status":"",
			"deptId":"",
			"owner":"",
			"ownerCard":"",
			"ownerMobile":"",
			"satellitePhone":"",
			"page": 1,
			"rows": addPageSelect()	
	}
	var name = $("#conditionLabel .inquireItem-content").html();
	if(name == HthxMap.commonConstant.advanceSearch){//高级搜索
		var shipName = $("#advancedSearch input[name=shipName]").val();//船名
		$("#btn_shipName").val(shipName);
		var shipType = $("#advancedSearch select[name=shipType]").val();//船舶类型
		var terminalType = $("#advancedSearch select[name=terminalType]").val();//终端类型
		var terminalNum = $("#advancedSearch input[name=terminalNum]").val();//终端编号
		var status = $("#advancedSearch select[name=status]").val();//在线状态
		var deptId = $("#advancedSearch input[name=departmentId]").val();//管理机构
		var shipOwnerName = $("#advancedSearch input[name=shipOwnerName]").val();//船东姓名
		var shipOwnerCard = $("#advancedSearch input[name=shipOwnerCard]").val();//船东身份证
		var shipOwnerTel = $("#advancedSearch input[name=shipOwnerTel]").val();//船东手机
		var shipSatellitePhone = $("#advancedSearch input[name=shipSatellitePhone]").val();//卫星电话
		
		options.shipName = shipName;
		options.shipType = shipType;
		options.terminalType = terminalType;
		options.terminalNum = terminalNum;
		options.status = status;
		options.deptId = deptId;
		options.owner = shipOwnerName;
		options.ownerCard = shipOwnerCard;
		options.ownerMobile = shipOwnerTel;
		options.satellitePhone = shipSatellitePhone;
		options.page = pageIndex;
		options.rows = pageSize;
	}else{
		var shipName = $("#btn_shipName").val();
		options.shipName = shipName;
		options.page = pageIndex;
		options.rows = pageSize;
	}
	$.ajax({
	   type: "POST",
	   url: HthxMap.Settings.rest + "shipOrientate/findByPage",
	   data: options,
	   success: function(objList){
		   if(objList['code'] === 1){ 
			 //保存总数量
	           HthxMap.groupOptionDataLen = objList.records;
	           var featureResult = convertBoatInfo(objList.rows);
	           var features = featureResult.features;
	           var records = objList.records;
	           queryResults(features,records,pageIndex,pageSize,queryShip);
			   //渔船单个定位渲染调用
//			   queryShipDraw(features);
			   queryShipAlarm(features);
		   }else{
			   Messager.alert({Msg:objList['msg'],isModal: true}); 
		   }
	   },
	   error: function(){
		   Messager.alert({Msg:'获取数据失败',isModal: true});
	   }
	})
}

//报警查询请求
function queryShipByAlarm(pageIndex, pageSize){
	//清空分页信息
	addPageSelect();
	$.ajax({
		type:'GET',
		url: HthxMap.Settings.rest+"shipOrientate/findAllAlarms",
		data:{
			"page":pageIndex,
			"rows":pageSize
		},
		success:function(alarmBoatInfo){
	        if(alarmBoatInfo["code"] === 1) {
	        	//保存总数量
	        	 HthxMap.groupOptionDataLen = alarmBoatInfo.records;
		         var featureResult = convertBoatInfo(alarmBoatInfo.rows);
		         var features = featureResult.features;
		         var records = alarmBoatInfo.records;
		         queryResults(features,records,pageIndex,pageSize,queryShipByAlarm);
		    	 
		    	 //将报警渔船设置标准为true
		    	 var featuresLen = features.length;
		    	 for(var i = 0; i < featuresLen; i++){
		    		 var id = features[i].getProperties().id;
		    		 if(!alarmOverlay[id]){
		    			 alarmOverlay[id] = true;
		    		 }
		    	 }
	          }else {
	        	 Messager.alert({Msg: alarmBoatInfo["msg"], isModal: true});          
	          }
			
		},	
		error:function(msg){
			
		}
	})
}
//在线/离线查询请求
function queryShipByOnlineStatus(pageIndex,pageSize){	
	//清空分页信息
	addPageSelect();
	var online = "161003002";
	var name = $("#conditionLabel .inquireItem-content").html();
	if(name == HthxMap.commonConstant.onLine){
		online = "161003001";
	}
	$.ajax({
		type:'POST',
		url: HthxMap.Settings.rest+"shipOrientate/findByPage",
		data:{
			'status':online,
			"page":pageIndex,
			"rows":pageSize
		},
		success:function(onlineBoatInfo){		
	        if(onlineBoatInfo["code"] === 1) {
	        	//保存总数量
		         HthxMap.groupOptionDataLen = onlineBoatInfo.records;
	        	 var featureResult = convertBoatInfo(onlineBoatInfo.rows);
		         var features = featureResult.features;
		         var records = onlineBoatInfo.records; 
		         queryResults(features,records,pageIndex,pageSize,queryShipByOnlineStatus);
	          }else {
	             Messager.alert({Msg: onlineBoatInfo["msg"], isModal: true});  
	          }
		},	
		error:function(msg){
			
		}
	})
}
//跟踪查询请求
function queryShipByTrack(pageIndex,pageSize){
	//清空分页信息
	addPageSelect();
	$.ajax({
		type:'GET',
		url: HthxMap.Settings.rest+"shipTrack/findTrackedShipInfo",
		data:{
			"page":pageIndex,
			"rows":pageSize
		},
		success:function(alarmBoatInfo){
	        if(alarmBoatInfo["code"] === 1) {
	        	//保存总数量
	        	 HthxMap.groupOptionDataLen = alarmBoatInfo.records;
		         var featureResult = convertBoatInfo(alarmBoatInfo.rows);
		         var features = featureResult.features;
		         var records = alarmBoatInfo.records;
		         queryResults(features,records,pageIndex,pageSize,queryShipByTrack);
	          }else {
	        	 Messager.alert({Msg: alarmBoatInfo["msg"], isModal: true});          
	          }
			
		},	
		error:function(msg){
			
		}
	})
}
//将渔船信息组装成feature
function convertBoatInfo(data){
	   var features = [];
	   var shipData = data;
	   if(!shipData){
		   shipData = [];
	   }
	   var length = shipData.length;

	   for(var i = 0; i < length; i++) {
		   var lon = shipData[i].longitude;
		   var lat = shipData[i].latitude;		   
		   if((shipData.length == 1) && lon && lat){    //如果只有一艘船，定位到地图中心
			   map.getView().setCenter([lon, lat]);
		   }
		   //封装成feature
		   var feature = createFeature(shipData[i]);
		   features.push(feature);	
		}
	   return {"features":features};
}
//显示查询条件label
function bindConditionLabel(name){
	$("#conditionLabel").html('<label class="conditionLabel pull-left">查询条件：</label>'+
		'<div class="inquireItem">'+			
		 	'<span class="inquireItem-content">'+name+'</span>'+
	  	 	'<span class="inquireItem-off" onclick="leftmeunpannel.delTab(this)">X</span>'+
	  	'</div>');
}

//搜索后的渔船渲染
function initBoatInfo(map, features){
	bizCommonVariable.pageFeatures = [];
	var strs = '', shipIndex=1;
	if(features && features.length){
		queryShipDraw(features);
		//如果是周围搜索，要计算距离
		var name = $("#conditionLabel .inquireItem-content").html();
		var centerLonlat, distance=0, distanceStr="";
		if(name == HthxMap.commonConstant.aroundSearch){
			centerLonlat = HthxMap.aroundSearchCenter;
		}
					
		removeShipSearchProperties(monitLayer.getSource().getFeatures());
		var featuresLen = features.length;		
		for(var i=0; i<featuresLen; i++){
			var properties = features[i].getProperties();
			bizCommonVariable.pageFeatures[properties.id] = features[i];
			//计算周围搜索距离
			if(name == HthxMap.commonConstant.aroundSearch){
				distance = HthxMap.Utils.getDistance(properties.lat, properties.lon, parseFloat(centerLonlat[1]), parseFloat(centerLonlat[0]));			
				
				distanceStr = '&nbsp;<span class="">'+HthxMap.Utils.lengthUnitShift("m",distance)+'海里</span>';
			}			
			//跟踪标签
			var text = "";			
			var ff = monitLayer.getSource().getFeatureById(properties.id);
			if((ff && ff.getProperties().shipStatus) || (trackRecords.indexOf(properties.id) !== -1)){
				text = "取消";
			}else{
				text = "跟踪";
			}
			//在线/离线标记
			var classOnline;
			if(properties.status == "161003001"){
				classOnline = 'glyphicon-line';
			}else{				
				classOnline = 'glyphicon-unline';
			}
			//转换渔船类型数据字典
			var shipType = alarmInfo.changeShipTypeToStr("", "", properties);
			var shipDir = properties.shipDir || "0";
			var shipSpeed = properties.shipSpeed || "0";
			var str = "";
			if(properties.id != properties.terminalID){
				var strArr = [];
				strArr.push('<div class="message-block" onclick="shipLocation('+ i + ",'" + features[i].getId()+"',"+properties.lon+","+properties.lat+')">');
				strArr.push('<span><i class="map-default">'+shipIndex+'</i>');
				strArr.push('<a href="javascript:void(0)" title="'+properties.shipName+'" onclick="shipLocation('+ i + ",'" + features[i].getId()+"',"+properties.lon+","+properties.lat+')">'+properties.shipName+'</a></span>');
				strArr.push('<span class="message-num">');
//				strArr.push('<i class="glyphicon glyphicon-earphone"></i>');
				strArr.push('<i class="glyphicon glyphicon-envelope" style="cursor:pointer;margin-right:5px" onclick="showSendMessageWin(\''+properties.id+'\',\''+properties.shipName+'\')"></i>');
				strArr.push('<span class='+classOnline+'></span>'+distanceStr+'</span>');
				strArr.push('<ul class="unControl msg-title clearfix">');
				strArr.push('<li title="'+shipType+'">类型：'+shipType+' </li><li title="'+shipDir+'">航向：'+shipDir+' </li><li title="'+shipSpeed+'">航速：'+shipSpeed+'节</li></ul>');
				strArr.push('<div class="msg-title clearfix sub-detail">');
				strArr.push('<ul class="clearfix">');
				strArr.push('<li><i class="glyphicon glyphicon-duplicate"></i><a href="javascript:shipsboatDetaill(\''+properties.id+'\')" onclick="">详情</a></li>');
				strArr.push('<li><i class="glyphicon glyphicon-share-alt"></i><a href="javascript:void(0)" onclick="bizTrackReplay.timePop(\''+properties.id+'\',\''+properties.shipName+'\')">轨迹</a></li>');
				strArr.push('<li><i class="glyphicon glyphicon-volume-up"></i><a href="javascript:void(0)" onclick="callOnShip(\''+properties.id+'\')">点名</a></li>');
				strArr.push('<li><i class="glyphicon glyphicon-send"></i><a href="javascript:void(0)" coor="'+properties.lon+','+properties.lat+'" class="shipTrack" id="'+features[i].getId()+'">'+text+'</a></li>');
				strArr.push('</ul></div></div>');
				strArr.push();
				str = strArr.join("");
				shipIndex++;
				//为每个渔船添加标记
				if(HthxMap.curMapLevel() && ff){
					var style = ff.getStyle();
					if(style){
						var shipImgArr = HthxMap.shipImgJoint(style[0].getImage().getSrc(), ["Search"]);
						//有跟踪渔船,跟踪图片显示在最上层		
						if(ff.getProperties().shipStatus){    
							shipImgArr.push(HthxMap.Settings.configData.targetImg.track);
						}
						HthxMap.changeShipImg(ff, properties.shipDir, shipImgArr);	
						ff.setProperties({"shipSearch": true});
					}
				}else if(!HthxMap.curMapLevel()){   //小比例尺
//					var shipImg = HthxMap.createShipImg(features[i]);
//					HthxMap.changeShipImg(features[i], properties.shipDir, shipImg);
//					features[i].setProperties({"shipSearch": true});  //可以不用，看情况
				}
				strs = strs + str;
			}	
		}
		if(!strs){
			clearBoatInfo();
			strs = "<div id='left_hint_div'><font class='left_hint'>无船舶数据</font><div>";
		}
		document.getElementById("shipManagement_content").innerHTML=strs;
		//渔船跟踪/取消跟踪
		$("#shipManagement_content .shipTrack").click(function(){
			var coor = $(this).attr('coor').split(",");
			if(!coor[0] || !coor[1] || coor[0]=="null" || coor[1]=="null"){
				Messager.alert({Msg:"该船舶没有上传位置信息,无法跟踪/取消跟踪", isModal: true});
				return;
			}
			coor = [JSON.parse(coor[0]), JSON.parse(coor[1])];
			
			if(!HthxMap.curMapLevel()){	//小比例尺下的跟踪
				if(map.getView().getCenter() !== coor){
					map.getView().setCenter(coor);
				}
				bizCommonSetting.location = true;
				bizCommonSetting.shipTracking = $(this);
				setZoomByHand();
			}else if(!isContainsIn(coor)){
				bizCommonSetting.shipTracking = $(this);
				map.getView().setCenter(coor);
			}else{
				shipTracking($(this).attr("id"), $(this), monitLayer, map);
			}
		});
	}else{
		clearBoatInfo();
		strs = "<div id='left_hint_div'><font class='left_hint'>无船舶数据，未注册船舶不可查询!</font><div>";
		document.getElementById("shipManagement_content").innerHTML=strs;
	}
}
//关闭搜索条件调用
function clearBoatInfo() {
	clearMonitorMarker(monitLayer);	
	$("#shipManagement_content").html("");    //删除查询内容
	$("#fishQueryPage").html("");
	if(ID$("allShip_1Page")){
		ID$("allShip_1Page").innerHTML = "";
	}
	HthxMap.shipIdArr = [];
}
function setZoomByHand(){
	 map.getView().setZoom(HthxMap.Settings.Zoom);
	 return true;
}
//点击渔船名字，地图上定位到渔船
function shipLocation(i, featureid, lon, lat, properties) {
	var e = event || window.event;
	if((e.target == e.currentTarget) || (e.target.className.indexOf('unControl') != -1)){
		if(!lon || !lat){
			Messager.alert({Msg:"该船舶没有上传位置信息,无法定位!", isModal: true});
			return;

		}
		$(".map-default").removeClass("active");
		$(".map-default:eq("+ i +")").addClass("active");
		
		if(!HthxMap.curMapLevel()){
			if(map.getView().getCenter() !== [lon, lat]){
				map.getView().setCenter([lon, lat]);
			}
			bizCommonSetting.location = true;
			bizCommonSetting.featureid = featureid;
			setZoomByHand();
		}else{
			var feature = monitLayer.getSource().getFeatureById(featureid);
			if(!feature){  //如果左侧栏搜索出的结果海图上不存在，则先移动地图
				var isContains = ol.extent.containsCoordinate(extentTemp, [lon, lat]);
				if(isContains){
//					Messager.alert({Msg:"该船舶在显示控制里进行了过滤,无法定位!", isModal: true});
//					return;
					feature = bizCommonVariable.pageFeatures[featureid];
					feature.setProperties({"location": true});
					HthxMap.getLayerById("monitorLayer").getSource().addFeatures([feature]);
					var imgArr = [];
					imgArr.push(HthxMap.createShipImg(feature));
					HthxMap.changeShipImg(feature, feature.getProperties().shipDir, imgArr);
					
					HthxMap.clickEvent(feature);
					HthxMap.popDiv.createClickPop(feature, boatDiv);
					HthxMap.preFeature = feature;
					map.getView().setCenter([lon, lat]);
					return;
				}
				bizCommonSetting.featureid = featureid;
				map.getView().setCenter([lon, lat]);
			}else{
				var style = feature.getStyle();
				if(!style){
//					Messager.alert({Msg:"该船舶在显示控制里进行了过滤,无法定位!", isModal: true}); 
//					return;
					feature.setStyle(featureStyles[featureid]);
				}
				HthxMap.clickEvent(feature);
				HthxMap.popDiv.createClickPop(feature, boatDiv);
				HthxMap.preFeature = feature;
				map.getView().setCenter([lon, lat]);
			}
		}
	}
}

//设置船只查询后地图中需要第一次渲染的渔船
function queryShipDraw(features){
	var featuresLen = features.length;
	var feature, data, lon, lat;
	for(var i = 0; i < featuresLen; i++){
		data = features[i].getProperties();
		feature = monitLayer.getSource().getFeatureById(data.id);
		if(!feature){
			drawShip[data.id] = true;
		}else if(feature && !HthxMap.curMapLevel()){	//小比例尺下删掉对应船舶
			monitLayer.getSource().removeFeature(feature);
			drawShip[data.id] = true;
		}else{
			drawShip[data.id] = false;
		}
	}
}

//设置船只查询后的报警渔船
function queryShipAlarm(features){
	var featuresLen = features.length;
	for(var i = 0; i < featuresLen; i++){
		var data = features[i].getProperties();
		if(data.alarmStatus){
			alarmOverlay[data.id] = true;
		}
	}
}
//判断某渔船是否在可视范围内
function isContainsIn(coordinate){
	var isContains = ol.extent.containsCoordinate(extentTemp, coordinate);
	return isContains;
}
/**********************************************************************************************************/
//获取部门列表
function getSystemDeptOnMap(advancedSearch) {   	
	var urlPath = HthxMap.Settings.rest + "shipOrientate/findAllOrg";
    $.ajax({
        type: "POST",
        dataType: 'json',
        data: {
        	"authorityFlag":false
        },
        async: false,
        url: urlPath,
        success: function (json) {
            orSearchPanel.showBoxSearch(advancedSearch, '部门列表', true, json.data, urlPath);
        }
    });
}