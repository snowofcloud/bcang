/**
 * <p>Description: 在地图上拉框放大、缩小</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-04 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：map(必选),displayClass(控件样式,可选),target(控件位置，可选), out(可选，唯一值true).
*/
HthxMap.ZoomBox = function(opt_options) {
    //初始化参数
	var options = opt_options || {};
	var map = options.map;
	var displayClass, target;
	if(options.out){
		displayClass = options.displayClass || "zoomOut";
		target = options.target || "zoomOut";
		//创建控件位置节点
		$("#toolbar").append('<div id="zoomOut"></div>');
	}else{
		displayClass = options.displayClass || "zoomIn";
		target = options.target || "zoomIn";
		//创建控件位置节点
		$("#toolbar").append('<div id="zoomIn"></div>');
	}

	var button = document.createElement('button');
	if(options.out){
		button.title = "缩小"
	}else{
		button.title = "放大";
	}
	var handler = function(e) {
		HthxMap.rightClick(map, HthxMap.curControl);
		//移除地图事件
		HthxMap.removeMapEvents(map);
		
		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		$("#" + options.target + " > div").removeClass(displayClass + 'Inactivate');
		$("#" + options.target + " > div").addClass(displayClass + 'Activate');
		//记录地图当前激活的控件
		HthxMap.curControl.target = target;
		HthxMap.curControl.displayClass = displayClass;

		var dragBox = new ol.interaction.DragBox({
			condition : ol.events.condition.always,
			style : new ol.style.Style({
				stroke : new ol.style.Stroke({
					color : [ 0, 0, 255, 1 ]
				})
			})
		});
		var xy1, xy2;
		dragBox.on('boxstart', function(e) {
			xy1 = e.coordinate;
		});
		dragBox.on('boxend', function(e) {
			xy2 = e.coordinate;
			var minx = xy1[0]<xy2[0]?xy1[0]:xy2[0];
			var miny = xy1[1]<xy2[1]?xy1[1]:xy2[1];
			var maxx = xy1[0]>xy2[0]?xy1[0]:xy2[0];
			var maxy = xy1[1]>xy2[1]?xy1[1]:xy2[1];;
			var extent = [minx,miny,maxx,maxy];
			//HthxMap.zoom(map, 1, options.out)
			HthxMap.zoombox(map,extent,options.out)
		});

		HthxMap.curMapInteraction = dragBox;
		map.addInteraction(dragBox);

	};
	this.handler = handler;
	button.addEventListener('click', handler, false);

	var element = document.createElement('div');
	element.className = displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);


	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});

}
ol.inherits(HthxMap.ZoomBox, ol.control.Control);

/**
 * <p>Description: 放大、缩小方法</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @param：map（传入的全局地图），num（正整数，地图放大/缩小的级数），out（boolean变量，表示是否缩小地图）
*/
HthxMap.zoom = function(map, num, out) {
	if( !(/^[1-9][0-9]*$/).test(num)){
		console.log("地图缩放级别必须为整数");
		return;
	}
	var view = map.getView();
	if (out) {
		view.setZoom(view.getZoom() - num);
	} else {
		view.setZoom(view.getZoom() + num);
	}
}
/**
 * <p>Description: 拉框放大、缩小方法</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @param：map（传入的全局地图），extent(拉出的矩形框的边界)，out（boolean变量，表示是否缩小地图）
*/
HthxMap.zoombox = function(map,extent,out){
	var size,ext;
	if (!out) {//放大
		size = map.getSize();
		ext = extent;
    } else {//缩小
    	ext = map.getView().calculateExtent(map.getSize());
    	var width = HthxMap.Utils.getDistance(extent[1],extent[0],extent[1],extent[2]) / 1000;
    	var height = HthxMap.Utils.getDistance(extent[3],extent[0],extent[3],extent[2]) / 1000;
        size = [width,height];
    }
    // always zoom in/out 
    var view = map.getView();
    view.fit(ext,size);
}