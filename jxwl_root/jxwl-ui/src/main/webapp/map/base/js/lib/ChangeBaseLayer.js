/**
 * <p>Description: 底图转换构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-12 </p>
 * @author: 何泽潘
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
 */
HthxMap.ChangeBaseLayer = function(opt_options) {
	var options = opt_options || {};
	var map = options.map;

	var target = options.target || "changeBaseLayer";
	var displayClass = options.displayClass || "changeBaseLayer";
	
	var changetype = options.changetype || "0";         //要切换的地图类型(0表示地图，1表示影像图)

	//增加影像图层
//	var shijie_region =  options.layers || new ol.layer.Tile({
//		title : '影像地图',
//		source : new ol.source.TileWMS({
//			url : HthxMap.Settings.url.WMSURL1,
//			params : HthxMap.Settings.layer.baselayers.mLayer1 
//		})
//	});
	var shijie_region =  options.layers || new ol.layer.Tile({
		title : '影像地图',
		source : new ol.source.XYZ({
			url : HthxMap.Settings.url.WMSURL2
		})
	});

	var button = document.createElement('button');
	button.title = "地图切换";
	
	button.addEventListener('click', handle, false);
	//动态设置样式，以便于修改
	button.setAttribute("class","beforeClick afterClick");
	button.style.width="40px";
	button.style.height="42px";
	
	$("body").append('<div id="changeBaseLayer"></div>');
	
	function handle(){
		//移除地图事件
		HthxMap.removeMapEvents(map);
		
		if(document.getElementById("ulBaseLayerList") != null){
			if($("ul#ulBaseLayerList").css("display") != "none"){
				$("ul#ulBaseLayerList").css("display","none");
			}else{
				$("ul#ulBaseLayerList").css("display","block");
			}
		}else{
			$("#" + target).after("<ul class='ulBaseLayerList ol-control' id='ulBaseLayerList'></ul>");
			$("ul#ulBaseLayerList").append("<li id='mLayer'><img/></li><li id='wLayer'><img/></li>");
//			$("#mLayer img").attr("src",HthxMap.Settings.IMG.mLayer);
//			$("#wLayer img").attr("src",HthxMap.Settings.IMG.wLayer);
		}
		
		var layers = map.getLayers();
		$("li#mLayer").click(function(){
			$(".changeBaseLayer button").removeClass("beforeClick1");
			$(".changeBaseLayer button").addClass("beforeClick");
			$(".changeBaseLayer button").removeClass("afterClick1");
			$(".changeBaseLayer button").addClass("afterClick");
			$("ul#ulBaseLayerList").hide();
			for(var i=0; i<layers.getLength(); i++){
    			if((layers.item(i).values_.title) && (layers.item(i).values_.title == "影像地图")){
    				map.removeLayer(layers.item(i));
    				map.getLayers().insertAt(0, HthxMap.curMap);
    				map.getLayers().insertAt(1, HthxMap.curLandMap);	//陆地地图
    				break;
    			}
    		}
		})
		$("li#wLayer").click(function(){
			$(".changeBaseLayer button").removeClass("beforeClick");
			$(".changeBaseLayer button").addClass("beforeClick1");
			$(".changeBaseLayer button").removeClass("afterClick");
			$(".changeBaseLayer button").addClass("afterClick1");
			$("ul#ulBaseLayerList").hide();
			for(var i=0; i<layers.getLength(); i++){
    			if((layers.item(i).values_.title) && (layers.item(i).values_.title == "中国地图")){
    				HthxMap.curMap = layers.item(i);
    				HthxMap.curLandMap = layers.item(i+1);	//陆地地图
    				map.removeLayer(layers.item(i));
    				map.removeLayer(layers.item(i));
    				map.getLayers().insertAt(0,shijie_region);
    				break;
    			}
    		}
		})

	}
	var element = document.createElement('div');
	element.className = options.displayClass + ' ol-unselectable ol-control';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
};
ol.inherits(HthxMap.ChangeBaseLayer, ol.control.Control);
