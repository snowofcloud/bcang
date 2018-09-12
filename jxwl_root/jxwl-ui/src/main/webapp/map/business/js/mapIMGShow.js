/**
 * <p>Description: 条件查询车辆情况</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-07-28 </p>
 * @author:wl
 * @param：
*/
var mapIMGShow = (function($){
	
	var WMSURL = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	});
	WMSURL.setProperties({id:"WMSURL"});
	
	
	var WMSURL1 = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL1
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	});
	WMSURL1.setProperties({id:"WMSURL1"});
	
	
	var WMSURL2 = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL2
		// params : HthxMap.Settings.layer.baselayers.mLayer
		})
	});
	WMSURL2.setProperties({id:"WMSURL2"});
	
	var $mapIMGShowBtn1 = $("#mapIMGShowBtn1");
	var $mapIMGShowBtn2 = $("#mapIMGShowBtn2");
	var $mapIMGShow = $("#mapIMGShow");
	
	var change1Flag = true;
	var change2Flag = false;
	
	//初始化
	var _init = function(){
		$mapIMGShowBtn1.bind("click", _change1);
		$mapIMGShowBtn2.bind("click",_change2);
		$mapIMGShow.bind("click",_show);
	};
	var _show = function(){
		$("#mapSearch").removeClass("hidden");
	};
	var _change1 = function(){
		_changeShow(change1Flag);
	};
	
	var _change2 = function(){
		_changeShow(change2Flag);
	};
	
	var _changeShow = function(flag,WMSURL){
		flag = !flag;
		if(flag){
			map.addLayer(WMSURL);
		}else{
			var id = WMSURL.getProperties().id;
			var w = HthxMap.getLayerById(id);
			map.removeLayer(w);
		}
	};
	
	
	_init();
})(jQuery);

