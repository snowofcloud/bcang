/**
 * <p>Description: 地图参数配置</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-30 </p>
 * @author: 张琴
 * @param：url:地图中所有的服务器地址，layer：地图图层相关的一些参数，
 */

HthxMap.Settings = {};
//服务器定位到webapp下的目录路径
HthxMap.Settings.root = "/JXWL/";
HthxMap.Settings.projection = 'EPSG:4326';
//webSocket求情地址
//HthxMap.Settings.webSocketURL = "ws://172.25.6.99:8080";
HthxMap.Settings.webSocketURL = "ws://localhost:8080";
HthxMap.Settings.rest = $.backPath;
HthxMap.Settings.SuperMapURL = "http://172.31.2.18:6080";
HthxMap.Settings.url = {
		//WMSURL: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/PHMAP/MapServer",//标注地图
		WMSURL: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/PHAnno/MapServer",//标注地图
		WMSURL1: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/LUWANG/MapServer",//底层地图
		WMSURL2: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/PHMAP/MapServer",//
		WMSURL3: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/PHIMG/MapServer",//
		WMSURL4: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/PHMAP/MapServer",//
		WMSURL5: HthxMap.Settings.SuperMapURL+"/arcgis/rest/services/SampleWorldCities/MapServer"//
};
HthxMap.Settings.layer = {
		baselayers: {
			mLayer: {LAYERS: 'china4326',VERSION: '1.3.0'} //瓦片地图SuperMap
		}
};

//地图级别界限
HthxMap.Settings.Zoom = 13;
//地图视图参数
HthxMap.Settings.initialView = {
		projection : HthxMap.Settings.projection,
		//center:[121.1965069510329, 30.74434858146516],原坐标
		center:[121.0875065624714,30.61816818080843],//乍浦镇为中心坐标
		zoom : HthxMap.Settings.Zoom,
		maxZoom: 23,
		minZoom: 2
}
//更新时间
HthxMap.Settings.updateTime = 15000;

HthxMap.Settings.IMG = {
		PanDown: "url('"+HthxMap.Settings.root+"map/base/img/PanDown.cur'),auto",
		distance: "url('"+HthxMap.Settings.root+"map/base/img/distance.cur'),auto",
		orientation: "url('"+HthxMap.Settings.root+"map/base/img/orientation.cur'),auto",
		marker: [
                HthxMap.Settings.root+"map/base/img/markerFlag.png",
                HthxMap.Settings.root+"map/base/img/markerShip.png",
                HthxMap.Settings.root+"map/base/img/marker1.png",
                HthxMap.Settings.root+"map/base/img/marker2.png",
                HthxMap.Settings.root+"map/base/img/marker3.png",
                HthxMap.Settings.root+"map/base/img/marker4.png",
                HthxMap.Settings.root+"map/base/img/marker5.png",
                HthxMap.Settings.root+"map/base/img/marker6.png",
                HthxMap.Settings.root+"map/base/img/marker7.png"
		],
		facility: [
		            HthxMap.Settings.root+"map/base/img/faci_01.png",
	                HthxMap.Settings.root+"map/base/img/faci_02.png",
	                HthxMap.Settings.root+"map/base/img/faci_03.png",
	                HthxMap.Settings.root+"map/base/img/faci_04.png",
	                HthxMap.Settings.root+"map/base/img/faci_05.png"
		],
		facilityName :["其它", "化工企业", "物流公司", "加油站", "车场"],/*车场、加油站、物流公司、化工企业、其它*/
		markerName: ["红旗", "渔船", "触礁", "船损", "搁浅", "故障", "火灾","落水", "碰撞"],
		fishPortMarker: HthxMap.Settings.root+"map/base/img/fishPort.png",
		mLayer: HthxMap.Settings.root+"map/base/img/imagemap.png",  //瓦片地图图标
		mLayer1: HthxMap.Settings.root+"map/base/img/imagemap_1.png",  //瓦片地图图标
		wLayer: HthxMap.Settings.root+"map/base/img/moonmap.png",    //卫星地图图标
		wLayer1: HthxMap.Settings.root+"map/base/img/moonmap_1.png" ,   //卫星地图图标
		//轨迹回放
		trackReplay: {
			ARROWPNG : HthxMap.Settings.root+"map/extend/trackReplay/img/arrows.png",
			ALARMPNG: HthxMap.Settings.root+"map/business/img/ship/alarm.png",
//		    ORIGINPNG:HthxMap.Settings.root+"map/extend/trackReplay/img/origin.png",
//		    DESTINATIONPNG: HthxMap.Settings.root+"map/extend/trackReplay/img/destination.png",
		    REPPLAYPNG: "url("+HthxMap.Settings.root+"map/extend/trackReplay/img/rep_play_a.png)",
		    REPSTOPPNG: "url("+HthxMap.Settings.root+"map/extend/trackReplay/img/rep_stop.png)",
		    BSLIP: "url("+HthxMap.Settings.root+"map/extend/trackReplay/img/_normal.png)",
		    ASLIP: "url("+HthxMap.Settings.root+"map/extend/trackReplay/img/normal.png)",
		    DBUTTON: "url("+HthxMap.Settings.root+"map/extend/trackReplay/img/drag_button.png)"
		}
}

// 车辆图片路径
HthxMap.Settings.configData = {
	targetImg : {
		//正常状态
		normal: HthxMap.Settings.root+"map/business/img/ship/normal.png",
		//正常状态GPS
		normalGPS: HthxMap.Settings.root+"map/business/img/ship/normalGPS.png",
		//离线状态
		offline: HthxMap.Settings.root+"map/business/img/ship/offline.png",
		//点名状态
		rollCall: HthxMap.Settings.root+"map/business/img/ship/normalClicked.png",
		//点名状态GPS
		rollGPSCall: HthxMap.Settings.root+"map/business/img/ship/normalGPSClicked.png",
		//报警状态
		alarm: HthxMap.Settings.root+"map/business/img/ship/alarm.png",
		//报警状态GPS
		alarmGPS: HthxMap.Settings.root+"map/business/img/ship/alarmGPS.png",
		//点击报警状态
		alarmClicked: HthxMap.Settings.root+"map/business/img/ship/alarmClicked.png",
		//点击报警状态GPS
		alarmGPSClicked: HthxMap.Settings.root+"map/business/img/ship/alarmGPSClicked.png",
		//大号离线状态
		LargerNormal: HthxMap.Settings.root+"map/business/img/ship/LargerNormal.png",
		//大号报警状态
		LargerAlarm: HthxMap.Settings.root+"map/business/img/ship/LargerAlarm.png",
		//起点位置
		startP:HthxMap.Settings.root+"map/business/img/ship/startP.png",
		//结束位置
		endP:HthxMap.Settings.root+"map/business/img/ship/endP.png",
		
		terminalImg: HthxMap.Settings.root+"map/business/img/ship/terminalImg.png",
		
		track: HthxMap.Settings.root+"map/business/img/ship/tracking.png"
	}
};

//渔船终端类型
HthxMap.Settings.TerminalType = {
		Beidou: "150001001",
		GSM: "150001002",
		M230: "150001003",
		c90: "150001004",
		AIS: "150001005",
		AISClassA: "150001006",
		AISClassB:"150001007"
}

//在线离线类型
HthxMap.Settings.onOffLine = {
		onLine: "161003001",
		offLine: "161003002"
}
//常量配置
HthxMap.commonConstant = {
		onShipTrack: "跟踪",
		unShipTrack: "取消",
		aroundSearch: "周围搜索",
		onLine: "在线",
		offLine: "离线",
		alarm: "报警",
		track: "跟踪",
		advanceSearch: "高级搜索",
		shipName: "船名",
		bufferSearch: "动态查询",
		unRegisterName: "未注册"
		
};
/*
 * 当前地图状态
 * 正常（normal）、跟踪中（following）
 */
HthxMap.mapStatus="normal";
HthxMap.followingCar="";//当前跟踪的车辆
HthxMap.Settings.kkyh="27a5403c65d44f90bbebe990bf076721";