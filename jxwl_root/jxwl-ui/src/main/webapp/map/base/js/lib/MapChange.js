/**
 * <p>
 * Description: 图层切换
 * </p>
 * Company: 航天恒星科技有限公司
 * </p>
 * <p>
 * Date: 2017-04-24
 * </p>
 * 
 * @author:wwh
 */
HthxMap.mapArr = [];
HthxMap.MapChange = function(opt_options) {
	var options = opt_options || {};
	var map = options.map;
	var displayClass = options.displayClass || "mapChange";
	var target = options.target || "mapChange";
	// 创建控件位置节点
	$("#toolbar").append('<div id="mapChange"></div>');
	var button = document.createElement('button');
	button.title = "地图切换";
	function init() {
		if ($(".bufferPan").length) {
			return;
		}
	}
	var handler = function(e) {
		init();
		// 先清除当前激活控件样式，再添加即将要激活的控件样式
		if (HthxMap.curControl.target) {
			$("#" + HthxMap.curControl.target + " > div").removeClass(
					HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(
					HthxMap.curControl.displayClass + 'Inactivate');
		}

		if (HthxMap.panControl.target && HthxMap.panControl.target !== target) {
			
		}
		display();
		// 记录地图当前激活的控件
		HthxMap.panControl.target = target;

		if ($(".bufferPan").css("display") === 'block') {
			$(".bufferPan").css("display", 'none');
		} else if ($(".bufferPan").css("display") === 'none') {
			$(".bufferPan").css('display', "block");
			$("#btn_searchRadius").val("");
			$("#btn_searchRadius").attr("placeholder", "单位m");
		}
	};
	this.handler = handler;
	button.addEventListener('click', handler, false);
	//选择框 checkbox
	//<input name="mapChange" type="checkbox" value="1" class= "mapChangeinputOne" id="mapChangeCheckOne"/> 
	//<input name="mapChange" type="checkbox" value="2" class= "mapChangeinputTwo" id="mapChangeCheckTwo"/> 
	var str = '<div class="mapChangeSelectOne mapChangeinputOne mapChangeSelect hidden" id="mapChangeCheckOne"><img src="../../../JXWL/map/base/img/mapChange02.png" title="卫星地图" class = "mapChangeButtonOne"></img></div>'
			+ '<div class="mapChangeSelectTwo mapChangeSelect mapChangeinputTwo hidden" id="mapChangeCheckTwo"><img src="../../../JXWL/map/base/img/mapChange_01.png" title="普通地图" class = "mapChangeButtonTwo"></img></div>';
	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable';
	element.appendChild(button);
	$(element).append(str);
	$("#mapChange").append(element);
	
	//初始化时 设定被选中
	$(".mapChangeinputOne").prop("checked", "checked");
	/*var oneEL = document.getElementById("mapChangeCheckOne");
	oneEL.addEventListener('click', mapChangeOne, false);
	var twoEL = document.getElementById("mapChangeCheckTwo");
	twoEL.addEventListener('click', mapChangeTwo, false);*/
	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
};
ol.inherits(HthxMap.MapChange, ol.control.Control);
//显示或隐藏选择框
function display() {
	/*默认选中普通地图*/
	/*$("#mapChangeCheckTwo").click();
	if ($(".mapChangeSelectOne").hasClass("hidden")) {
		$(".mapChangeSelectOne").removeClass("hidden");
		$(".mapChangeSelectTwo").removeClass("hidden");
	} else {
		$(".mapChangeSelectTwo").addClass("hidden");
		$(".mapChangeSelectOne").addClass("hidden");
	}*/
	var projectionYX = ol.proj.get('EPSG:4326');
	var projectionExtentYX = projectionYX.getExtent();
	var ress = [1.40625, 0.703125, 0.3515625, 0.17578125, 0.087890625, 0.0439453125, 0.02197265625, 0.010986328125,
	        0.0054931640625, 0.00274658203125, 0.001373291015625, 0.0006866455078125, 0.00034332275390625, 0.000171661376953125,
	        0.0000858306884765625, 0.00004291534423828125, 0.000021457672119140625, 0.000010728836059570312,0.000005364418029785156,
	    0.00000268220901489258,0.00000134110450744629,0.000000670552253723144];
	var PingHu_YXM =   new ol.layer.Tile({
	                title:"影像地图",
	                source: new ol.source.WMTS({
	                    name: "中国矢量1-14级",
	                    url: 'http://t{0-6}.tianditu.com/img_c/wmts',
	                    layer: 'img',
	                    style: 'default',
	                    matrixSet: 'c',
	                    format: 'tiles',
	                    wrapX: true,
	                    tileGrid: new ol.tilegrid.WMTS({
	                        origin: ol.extent.getTopLeft(projectionExtentYX),
	                        resolutions: ress.slice(0, 15),
	                        matrixIds: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
	                    })
	                }),
	                minResolution: 0.00004291534423828125,
	                maxResolution: 1.40625
	            });
    var layerFlag = false;
    var layers = map.getLayers();

		for(var i=0; i<layers.getLength(); i++){
                if((layers.item(i).values_.title) && (layers.item(i).values_.title == "一般地图")){
                    layerFlag = true;
                    HthxMap.mapArr.push(layers.item(i));
                    map.removeLayer(layers.item(i));
                }
            }
            if(layerFlag){
                map.getLayers().insertAt(0, PingHu_YXM);
            }else{
               for(var i=0; i<layers.getLength(); i++){
                if((layers.item(i).values_.title) && (layers.item(i).values_.title == "影像地图")){
                    map.removeLayer(layers.item(i));
                    for(var j=0; j<HthxMap.mapArr.length; j++){
                        map.getLayers().insertAt(j, HthxMap.mapArr[j]);
                    }
                }
            }
              HthxMap.mapArr = [];
        }
}
//选择事件
/*function mapChangeOne() {
	$("#mapChangeCheckTwo .mapChangeButtonTwo").attr("src","../../../JXWL/map/base/img/mapChange_01.png");
	$("#mapChangeCheckOne .mapChangeButtonOne").attr("src","../../../JXWL/map/base/img/mapChange02_current.png");
	var WMSURL1 = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL1
		})
	});
	WMSURL1.setProperties({
		id : "WMSURL1"
	});

	var WMSURL2 = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL2
		})
	});
	WMSURL2.setProperties({
		id : "WMSURL2"
	});
	var oneEL = $(".mapChangeinputOne");
	var twoEL = $(".mapChangeinputTwo");
	if (oneEL.prop("checked")) {
		if( twoEL.prop("checked")){
			map.getLayers().insertAt(1, WMSURL1);
		}else{
			map.getLayers().insertAt(0, WMSURL1);
		}
	} else {
		// 另一个被选中
		if (twoEL.prop("checked")) {
			// 删除one图层
			var index = findLayer("WMSURL1");
			var layers = map.getLayers();
			map.removeLayer(layers.item(index));
			// 另一个没有被选中
		} else {
			oneEL.prop("checked", true);
			return;
		}
	}
};*/
//选择事件
/*function mapChangeTwo() {
	$("#mapChangeCheckTwo .mapChangeButtonTwo").attr("src","../../../JXWL/map/base/img/mapChange01_current.png");
	$("#mapChangeCheckOne .mapChangeButtonOne").attr("src","../../../JXWL/map/base/img/mapChange02.png");
	var WMSURL1 = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL1
		})
	});
	WMSURL1.setProperties({
		id : "WMSURL1"
	});

	var WMSURL2 = new ol.layer.Tile({
		source : new ol.source.TileArcGISRest({
			url : HthxMap.Settings.url.WMSURL2
		})
	});
	WMSURL2.setProperties({
		id : "WMSURL2"
	});
	
	var oneEL = $(".mapChangeinputOne");
	var twoEL = $(".mapChangeinputTwo");
	if (twoEL.prop("checked")) {
		map.getLayers().insertAt(0, WMSURL2);
	} else {
		// 另一个被选中
		if (oneEL.prop("checked")) {
			// 删除one图层
			var index = findLayer("WMSURL2");
			var layers = map.getLayers();
			map.removeLayer(layers.item(index));
			// 另一个没有被选中
		} else {
			twoEL.prop("checked", true);
			return;
		}
	}
};*/
//查询图层的位置
function findLayer(id){
	var layers = map.getLayers();
	for(var i=0; i<layers.getLength(); i++){
		if(layers.item(i).values_.id === id){
			return i;
		}
	}
}
