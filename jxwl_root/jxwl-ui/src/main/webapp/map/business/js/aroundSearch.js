/**
 * <p>Description: 渔船周围搜索</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-08-28 </p>
 * @author: 孙耀
 * @extends：
 * @param：coor(渔船坐标)
 */
//渔船周围搜索
function aroundSearch(coor){
    var fLayer = HthxMap.getLayerById("featureLayer");

	//清除之前的周围搜索要素
	var src = fLayer.getSource();
	var oldFeature = src.getFeatureById("shipAroundSearchF");
	if(oldFeature){
		src.removeFeature(oldFeature);
		$(".shipAroundSearch").remove();    //删除中心点标记
	}
	
	var featureLayer = HthxMap.getLayerById("featureLayer");
	var src = featureLayer.getSource();
	if(featureLayer){		
		for(var i = 0; i < HthxMap.shipBufferQueryFeature.length; i++){
			src.removeFeature(HthxMap.shipBufferQueryFeature[i]);
		}
		HthxMap.shipBufferQueryFeature = [];
	}
	
	var coords = coor.split(",");
	var centerCoor = [parseFloat(coords[0]),parseFloat(coords[1])];
	HthxMap.aroundSearchCenter = centerCoor;
	var searchVal = $("#aroundSrchRadius option:selected").val();
	var searchRadius = parseFloat(searchVal);
	searchRadius = searchRadius*1.852/111;  //将距离转换为度
	var geoms = getAroundSrchPts(centerCoor,searchRadius);
	
	var feature = new ol.Feature({
		geometry:new ol.geom.Polygon([geoms])
	});
	feature.setId("shipAroundSearchF");
	feature.setStyle(
		new ol.style.Style({
			stroke:new ol.style.Stroke({
				color: '#0061D9', width:1.1
			}),
			fill:new ol.style.Fill({
				color: 'rgba(0,97,217,0.2)'
			})
		})
	)
	fLayer.getSource().addFeature(feature);
	addMarker(centerCoor);
	//显示控制提示
	var isDisplayControlledArray = isDisplayControlled();
    if(isDisplayControlledArray[0]){
    	createSearchTooltip();
    	searchTooltipElement.innerHTML = isDisplayControlledArray[1];
    	searchTooltip.setPosition(centerCoor);
    	setTimeout(function(){
    		map.removeOverlay(searchTooltip);
    	}, 3000);
    }
	
	bindConditionLabel(HthxMap.commonConstant.aroundSearch);
	getShipsByArea(feature.getGeometry());
}

function addMarker(coor){
	var markerElement = document.createElement('div');
	markerElement.className = "shipAroundSearch";
	$('body').append(markerElement);
	 
    var aroundSrchOverlay = new ol.Overlay({
		element: markerElement,
		offset: [0, -20],
		positioning: 'center-center'
	});
    aroundSrchOverlay.setProperties({type:"aroundSrchOverlay"});
    aroundSrchOverlay.setPosition(coor);
	map.addOverlay(aroundSrchOverlay);
}

//根据中心点坐标和搜索半径拟合圆要素
function getAroundSrchPts(pt, len) {
    var x, y, theta, resultPts = [];
    for (var i = 0; i <= 100; i++) {
        theta = Math.PI * 2 * i / 100;
        x = pt[0] + len * Math.cos(theta);
        y = pt[1] + len * Math.sin(theta);
        resultPts.push([x, y]);
    }
    return resultPts;
};