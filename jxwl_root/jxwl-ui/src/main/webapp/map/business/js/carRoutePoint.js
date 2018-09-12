/**
 * 地图车辆路径的起始点、终点
 */

/**
 * 点击车辆查看路线 ————
 * 				初始化数据从业务库中车辆实时表中读取相关实时数据，然后在点击弹框时在关联库中其他业务数据
 * 
 * @param map 地图
 * @param layer  监控层
 * @param objList 
 */
function initCarRouteLayer(map, layer, carData) {
	var feature, pointImgBase, ff, id;
	var features = [];
	if (carData) {
		if (carData.points && 0 < carData.points.length) {
			var carps = JSON.parse(carData.points);
			var spoint = carps[0];
			var epoint = carps[carps.length - 1];
			var ps = [];
			ps.push(spoint);
			ps.push(epoint);
			for ( var i = 0; i < ps.length; i++) {
				var imgArr = [];
				var obj = {};
				obj.id = carData.waybillId + i;
				obj.point = ps[i];
				feature = createFeaturea(obj);
				if (i == 0) {
					pointImgBase = HthxMap.Settings.root+"map/business/img/ship/startP.png";
				} else {
					pointImgBase = HthxMap.Settings.root+"map/business/img/ship/endP.png";
				}
				feature.set("terminalTypeImage", pointImgBase);
				imgArr.push(pointImgBase);			
				HthxMap.changeShipImg(feature, 0, imgArr);
				features.push(feature);
			}
		}
	}
	// 第一次加载车辆渲染到地图上，再进行显示控制过滤
	if(features.length) {
		layer.getSource().addFeatures(features);
	}	
}



// 创建feature
function createFeaturea(obj) {
	var feature = new ol.Feature(createPropertiesa(obj));
	feature.setId(obj.id);
	return feature;
}


/**
 * 创建properties 为实时数据
 */
function createPropertiesa(obj) {
	var point = new ol.geom.Point(obj.point);
	var properties = {
			geometry: point// 位置点
		};
	
	return properties;
}

