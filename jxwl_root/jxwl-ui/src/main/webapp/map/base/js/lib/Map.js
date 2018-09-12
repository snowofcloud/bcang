/**
 * <p>Description: 全局地图构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @extends：{ol.Map}
 * @param：DOM节点id（必选），layers（可选，地图图层数组），renderer（可选，渲染方式），view（可选，地图视图），controls（可选，地图默认控件）
*/
/*HthxMap.Map = function(opt_options) {
	var options = opt_options || {};
	// //地图渲染的div
	var targertID = options.target;
	// //创建工具栏位置节点
	$("body").append('<div id="toolbar" class="olToolbar"></div>');
	// //地图的初始图层
	var initLayers = [ new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL1
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	}), new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	}), ];
	initLayers = options.layers || initLayers;
	initLayers[0].setProperties({id:"WMSURL1"});
	initLayers[1].setProperties({id:"WMSURL"});
	// 地图渲染方式
	var render = options.renderer || "canvas";
	// 地图初始视图，把center和zoom单独提出来是为了地图复位功能使用
	var center = options.view ? options.view.getCenter : [ 0, 0 ];
	var zoom = HthxMap.Settings.Zoom;
	var initialView = options.view || new ol.View({
		projection : HthxMap.Settings.projection,
		center : center,
		zoom : zoom
	});

	var attribution = new ol.control.Attribution({
		collapsible : false
	});

	// 地图初始控件
	var mp = new ol.control.MousePosition({
		coordinateFormat : ol.coordinate.toStringHDMS()

	});
	var initControls = options.controls
			|| ol.control.defaults({
				attribution : false
			}).extend(
					[
							new ol.control.ScaleLine(),
							new ol.control.ZoomSlider({}),
							new ol.control.MousePosition({
								coordinateFormat : function(coordinate) {
									// var position =
									// ol.proj.transform([coordinate[0],coordinate[1]],"EPSG:3857","EPSG:4326");
									return ol.coordinate
											.toStringHDMS(coordinate);
								}
							}), new ol.control.OverviewMap({
								layers : initLayers,
								view : new ol.View({
									projection : HthxMap.Settings.projection
								}),
								collapseLabel : '\u00BB',
								label : '\u00AB'
							}) ]);

	// 构造地图
	ol.Map.call(this, {
		target : targertID,
		layers : initLayers,
		renderer : render,
		view : initialView,
		controls : initControls,
		loadTilesWhileAnimating : true,
		loadTilesWhileInteracting : true
	});
};*/


HthxMap.Map = function(opt_options) {
	var options = opt_options || {};
	// //地图渲染的div
	var targertID = options.target;
	// //创建工具栏位置节点
	$("body").append('<div id="toolbar" class="olToolbar"></div>');
	var projection = ol.proj.get('EPSG:4326');
    var projectionExtent = projection.getExtent();
    var res = [1.40625, 0.703125, 0.3515625, 0.17578125, 0.087890625, 0.0439453125, 0.02197265625, 0.010986328125,
        0.0054931640625, 0.00274658203125, 0.001373291015625, 0.0006866455078125, 0.00034332275390625, 0.000171661376953125,
        0.0000858306884765625, 0.00004291534423828125, 0.000021457672119140625, 0.000010728836059570312,0.000005364418029785156,
    0.00000268220901489258,0.00000134110450744629,0.000000670552253723144];
	// //地图的初始图层
	/*var initLayers = [ new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL1
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	}), new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	}), ];*/
    
    var initLayers = [
          			new ol.layer.Tile({
          				title:"一般地图",
          				source: new ol.source.WMTS({
          					name: "中国矢量1-14级",
          					url: 'http://t{0-6}.tianditu.com/vec_c/wmts',
          					layer: 'vec',
          					style: 'default',
          					matrixSet: 'c',
          					format: 'tiles',
          					wrapX: true,
          					tileGrid: new ol.tilegrid.WMTS({
          						origin: ol.extent.getTopLeft(projectionExtent),
          						resolutions: res.slice(0, 15),
          						matrixIds: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
          					})
          				}),
          				minResolution: 0.00004291534423828125,
          				maxResolution: 1.40625
          			}),
          			new ol.layer.Tile({
          				source: new ol.source.WMTS({
          					name: "中国矢量注记1-14级",
          					url: 'http://t{0-6}.tianditu.com/cva_c/wmts',
          					layer: 'cva',
          					style: 'default',
          					matrixSet: 'c',
          					format: 'tiles',
          					wrapX: true,
          					tileGrid: new ol.tilegrid.WMTS({
          						origin: ol.extent.getTopLeft(projectionExtent),
          						resolutions: res.slice(0, 15),
          						matrixIds: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
          					})
          				}),
          				minResolution: 0.00004291534423828125,
          				maxResolution: 1.40625
          			}),
          			new ol.layer.Tile({
          				source: new ol.source.WMTS({
          					name: "浙江矢量15-17级",
          					url: 'http://srv{0-6}.zjditu.cn/ZJEMAP_2D/wmts',
          					layer: 'TDT_ZJEMAP',
          					style: 'default',
          					matrixSet: 'TileMatrixSet0',
          					format: 'image/png',
          					wrapX: true,
          					tileGrid: new ol.tilegrid.WMTS({
          						origin: ol.extent.getTopLeft(projectionExtent),
          						resolutions: res.slice(15,18),
          						matrixIds: [15, 16, 17]
          					})
          				}),
          				minResolution: 0.000005364418029785156,
          				maxResolution: 0.0000858306884765625
          			}),
          			new ol.layer.Tile({
          				source: new ol.source.WMTS({
          					name: "浙江矢量注记15-17级",
          					url: 'http://srv{0-6}.zjditu.cn/ZJEMAPANNO_2D/wmts',
          					layer: 'ZJEMAPANNO',
          					style: 'default',
          					matrixSet: 'TileMatrixSet0',
          					format: 'image/png',
          					wrapX: true,
          					tileGrid: new ol.tilegrid.WMTS({
          						origin: ol.extent.getTopLeft(projectionExtent),
          						resolutions: res.slice(15,18),
          						matrixIds: [15, 16, 17]
          					})
          				}),
          				minResolution: 0.000005364418029785156,
          				maxResolution: 0.0000858306884765625
          			}),
          			new ol.layer.Tile({
          			source: new ol.source.WMTS({
          			 name: "平湖矢量注记18-20级",
          			 url: 'http://map.pinghu.gov.cn/geoservices/PHEMAPANNO/service/WMTS',
          			 layer: 'phemapanno',
          			 style: 'default',
          			 matrixSet: 'TileMatrixSet0',
          			 format: 'image/png',
          			 wrapX: true,
          			 tileGrid: new ol.tilegrid.WMTS({
          				 origin: ol.extent.getTopLeft(projectionExtent),
          				 resolutions: res.slice(18,21),
          				 matrixIds: [18, 19, 20]
          			 })
          			}),
          			minResolution: 0.000000670552253723144,
          			maxResolution: 0.000005364418029785156
          			}),
          			new ol.layer.Tile({
          			 source: new ol.source.WMTS({
          				 name: "平湖矢量18-20级",
          				 url: 'http://map.pinghu.gov.cn/geoservices/PHEMAP/service/WMTS',
          				 layer: 'phemap',
          				 style: 'default',
          				 matrixSet: 'TileMatrixSet0',
          				 format: 'image/png',
          				 wrapX: true,
          				 tileGrid: new ol.tilegrid.WMTS({
          					 origin: ol.extent.getTopLeft(projectionExtent),
          					 resolutions: res.slice(18,21),
          					 matrixIds: [18, 19, 20]
          				 })
          			 }),
          			 minResolution: 0.000000670552253723144,
          			 maxResolution: 0.000005364418029785156
          			})];
	initLayers = options.layers || initLayers;
	/*initLayers[0].setProperties({id:"WMSURL1"});
	initLayers[1].setProperties({id:"WMSURL"});*/
	// 地图渲染方式
	var render = options.renderer || "canvas";
	// 地图初始视图，把center和zoom单独提出来是为了地图复位功能使用
	var center = options.view ? options.view.getCenter : [ 0,0 ];
	var zoom = HthxMap.Settings.Zoom;
	var initialView = options.view || new ol.View({
		projection : HthxMap.Settings.projection,
		center : center,
		zoom : zoom
	});

	var attribution = new ol.control.Attribution({
		collapsible : false
	});

	// 地图初始控件
	var mp = new ol.control.MousePosition({
		coordinateFormat : ol.coordinate.toStringHDMS()

	});
	var initControls = options.controls
			|| ol.control.defaults({
				attribution : false
			}).extend(
					[
							new ol.control.ScaleLine(),
							new ol.control.ZoomSlider({}),
							new ol.control.MousePosition({
								coordinateFormat : function(coordinate) {
									// var position =
									// ol.proj.transform([coordinate[0],coordinate[1]],"EPSG:3857","EPSG:4326");
									return ol.coordinate
											.toStringHDMS(coordinate);
								}
							}), new ol.control.OverviewMap({
								layers : initLayers,
								view : new ol.View({
									projection : HthxMap.Settings.projection
								}),
								collapseLabel : '\u00BB',
								label : '\u00AB'
							}) ]);

	// 构造地图
	ol.Map.call(this, {
		target : targertID,
		layers : initLayers,
		renderer : render,
		view : initialView,
		controls : initControls,
		loadTilesWhileAnimating : true,
		loadTilesWhileInteracting : true
	});
};

ol.inherits(HthxMap.Map, ol.Map);

//地图当前的交互事件,实现地图操作互斥,全局唯一
HthxMap.curMapInteraction = "";
//地图feature事件判断,实现操作互斥,全局唯一
HthxMap.curFeatureInteraction = null;
//地图工具栏当前激活控件对象，全局唯一
HthxMap.curControl = {"target":"","displayClass":""};
//二次操作展开控制
HthxMap.panControl = {"target":""};
//地图当前绑定对象，全局唯一
HthxMap.curMapOn = "";
//地图所有视图
HthxMap.allView = [];
//保存当前世界地图层,实现底图切换操作,全局唯一
HthxMap.curMap = "";
HthxMap.curLandMap = null;
//标绘对象，全局唯一
HthxMap.plotObject = "";
//测量方位对象，全局唯一
HthxMap.measureOrientation = "";
//鼠标移动绑定事件，全局唯一
HthxMap.curMapOnPointerMove = "";
//底图控制
HthxMap.controlRemoveLayers = [];
//地图绑定事件返回值
HthxMap.key = null;
HthxMap.boatKey = "";
//鼠标点击事件在小船图层第一次加载有用，并且不被其它临时图层影响
HthxMap.booleanKey = true;
//地图小手生成返回值
HthxMap.Pointer = null;
//渔船缓冲区查询返回的要素
HthxMap.shipBufferQueryFeature = [];
//轨迹层
HthxMap.trackReplayLayer = null;
//台风层
HthxMap.typhoonLayer = null;
//台风层地图绑定事件
HthxMap.tlClickKey = "";
HthxMap.tlPointermoveKey = "";

//地图绑定事件返回值
HthxMap.mouseMoveKey = null;
//保证小手生成执行一次
HthxMap.flag = true;
//鼠标样式生成未完成
HthxMap.mouseUnbind = false;
//地图小手生成返回值
HthxMap.Pointer = "";
//渔船缓冲区查询返回的要素
HthxMap.shipBufferQueryFeature = [];
//空间查询返回的要素
HthxMap.shipSpaceQueryFeature = [];
HthxMap.spaceLayer = null;
//航迹图层
HthxMap.trackLayer = null;
//运动速度线
HthxMap.speedLayer = null;
//航迹历史数据
HthxMap.historyPoint = [];
//测距、测面、测方位等删除索引
HthxMap.measureIndex = 0;
//天气图层
HthxMap.atmosphereLayer = null;
//前一个渔船的feature
HthxMap.preFeature = null;
//当前绘制的图形，例如实现周围搜索和缓冲区的互斥
HthxMap.shapFeatureOnMap = null;
//渔船切片数据图层
HthxMap.wmtsLayer = null;
//保存船舶ID，供群操作
HthxMap.shipIdArr = [];
//群操作记录数据长度
HthxMap.groupOptionDataLen = 0;
//小比例尺下是否有区域查询
HthxMap.searchShipOnMin = false;
//保存小比例尺下的区域点集
HthxMap.pointsOnMin = null;
///*区域限制删除索引*/
HthxMap.areaRestrictIndex = [];
//区域限制索引，用于区分标绘和测量
HthxMap.areaRestrictRail = [];
//自定义区域索引
HthxMap.areaDefinedAreaIndex = [];
//自定义区域限制索引，用于区分标绘和测量
HthxMap.areaDefinedAreaRail = [];

//自定义线条索引
HthxMap.areaDefinedLineIndex = [];
//自定义线条限制索引，用于区分标绘和测量
HthxMap.areaDefinedLineRail = [];

//自定义设施索引
HthxMap.areaDefinedFacilityIndex = [];
//自定义设施限制索引，用于区分标绘和测量
HthxMap.areaDefinedFacilityRail = [];

///*电子围栏区删除索引*/
HthxMap.electronRailIndex = [];
//电子围栏索引，用于区分标绘和测量
HthxMap.electronRail = [];
//保存标记后台返回的id值
HthxMap.markerId = [];
//保存标记索引，用于和标绘、测量区别
HthxMap.markerIndex = [];
//保存每个绘制对应有一个id，并和弹框id对应
HthxMap.markerFeatureId = [];
//记录图层选择的前一个状态
HthxMap.preLayerStatus = [];
//图层单选择
HthxMap.radioLayer = null;

//所有点击弹框只允许有一个
HthxMap.clickPop = null;



//船舶样式
HthxMap.opacity = 1;
//
HthxMap.facilityId = 0;