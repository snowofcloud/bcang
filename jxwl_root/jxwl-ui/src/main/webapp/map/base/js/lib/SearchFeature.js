/**
 * <p>Description: 空间属性查询</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-30 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
 */
var featureLayer = null;
HthxMap.SearchFeature = function(opt_options) {

	var options = opt_options || {};
	var displayClass = options.displayClass;
	var map = options.map;
	featureLayer = options.layer;
	var source = featureLayer.getSource();

	var button = document.createElement('button');
	button.title = "查询";

	//添加button控件按钮
	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ol-control';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
	button.addEventListener('click', handler, false);

	function handler(){
		//移除地图事件
		HthxMap.removeMapEvents(map);
		//移除地图双击放大事件
		for(var i=0; i<map.getInteractions().getLength(); i++){
        	if(map.getInteractions().item(i) instanceof ol.interaction.DoubleClickZoom){
        		map.removeInteraction(map.getInteractions().item(i));
        		break;
        	}
        }

		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		$("#" + options.target + " > div").removeClass(displayClass + 'Inactivate');
		$("#" + options.target + " > div").addClass(displayClass + 'Activate');
		//记录地图当前激活的控件
		HthxMap.curControl.target = options.target;
		HthxMap.curControl.displayClass = displayClass;

		if(HthxMap.curMapOn){
			map.un('singleclick',HthxMap.curMapOn);
		}
		
		var draw = new ol.interaction.Draw({
			source : source,
			type : "Polygon"
		});
		HthxMap.curMapInteraction = draw;
		map.addInteraction(draw);

		var sketch = null;
		draw.on('drawstart',function(e){
			sketch = e.feature;
		},this);

		draw.on('drawend',function(e){
	        map.removeInteraction(HthxMap.curMapInteraction);
	        //恢复控件未被激活的状态
			$("#" + options.target + " > div").removeClass(displayClass + 'Activate');
			$("#" + options.target + " > div").addClass(displayClass + 'Inactivate');
			HthxMap.curControl.target = "";
			HthxMap.curControl.displayClass = "";
			HthxMap.curMapOn = "";
			
			var geom = sketch.getGeometry();
			//alert(sketch);
			var sourceProj = map.getView().getProjection();
		    geom = /** @type {ol.geom.Polygon} */(geom.clone().transform(sourceProj, 'EPSG:4326'));

		    var type = geom.getType();
		    var points = geom.getCoordinates(true);
		    var str = type+"("+points+")";
		    geom = "POLYGON((112.03741810547 22.827898027344,112.46588490234 22.245622636719,111.70233509766 22.349992753906,112.03741810547 22.827898027344))";
			HthxMap.searchFeature(geom);

		},this);
	}
};
ol.inherits(HthxMap.SearchFeature, ol.control.Control);
/**
 * <p>Description: 属性查询方法</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-30 </p>
 * @author: 张琴
 * @param：layer(查询图层),geom(查询形状),cql_txt(查询条件),
 * @return: 集合(ol.Vector.Feature)
 */
HthxMap.searchFeature = function(geom,cql_txt) {
	var requestUrl = HthxMap.Settings.url.WFSURL;
	var para = {};
	para.service = HthxMap.Settings.WFS.service;
	para.version = HthxMap.Settings.WFS.version;
	para.request = HthxMap.Settings.WFS.request;
	//var dd = new ol.format.WKT().writeGeometry(JSON.stringify(geom.toString()));
	cql_txt = 'within(the_geom,' + geom + ')';
	if(cql_txt != "" && cql_txt != null){
		para.cql_filter=HthxMap.Utils.decToHex(cql_txt);
	}
	para.outputFormat = HthxMap.Settings.WFS.outputFormat;
	para.srsname = HthxMap.Settings.WFS.srsname;
	para.typename = HthxMap.Settings.WFS.typename;
	para.format_options = "callback:loadFeatures";

	$.ajax({
		url : requestUrl,  
		dataType: "jsonp",
		data : para,
		jsonp: false
	});
}

function loadFeatures(response){
	var geojsonFormat = new ol.format.GeoJSON();
	var vectorSource = featureLayer.getSource();
	vectorSource.addFeatures(geojsonFormat.readFeatures(response));
}