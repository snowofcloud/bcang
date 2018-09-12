/**
 * <p>Description: 地图复位</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.MapReset = function(opt_options) {
	var options = opt_options || {};
	var map = options.map;
	var displayClass = options.displayClass || "mapReview";
	var target = options.target || "mapReview";
	
	//创建控件位置节点
	$("#toolbar").append('<div id="mapReview"></div>');

	var button = document.createElement('button');
	button.title = "复位";

	var handler = function(e) {
		//移除地图事件
		HthxMap.removeMapEvents(map);
		
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
			HthxMap.curControl.target = "";
			HthxMap.curControl.displayClass = "";
		}
		
		var zoom = options.zoom;
		var center = options.center;
		//var center = [121.0875065624714,30.61816818080843];
		var view = map.getView();
		view.setCenter(center);
		view.setZoom(zoom);
	};
	this.handler = handler;
	button.addEventListener('click', handler, false);

	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});

};
ol.inherits(HthxMap.MapReset, ol.control.Control);

