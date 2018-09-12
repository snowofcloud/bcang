/**
 * <p>Description: 地图平移（漫游）</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：map(必选),displayClass(控件样式,可选),target(控件位置，可选).
*/
HthxMap.Pan = function(opt_options) {
    //初始化参数
	var options = opt_options || {};
	var map = options.map;
	var displayClass = options.displayClass || "pan";
	var target = options.target || "pan";
	
	//创建控件位置节点
	$("#toolbar").append('<div id="pan"></div>');

	var button = document.createElement('button');
	button.title = "漫游";
    
	var handler = function(e) {
		//移除地图事件
		HthxMap.removeMapEvents(map);
		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		$("#" + target + " > div").removeClass(displayClass + 'Inactivate');
		$("#" + target + " > div").addClass(displayClass + 'Activate');
		//记录地图当前激活的控件
		HthxMap.curControl.target = target;
		HthxMap.curControl.displayClass = displayClass;

		var dragPan = new ol.interaction.DragPan();
		//给地图添加交互事件，先清除之前添加的事件再添加
		HthxMap.curMapInteraction = dragPan;
		map.addInteraction(dragPan);

	};
	button.addEventListener('click', handler, false);

	var element = document.createElement('div');
	element.className = 'ol-unselectable  ' + displayClass + 'Inactivate';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : target
	});

};
ol.inherits(HthxMap.Pan, ol.control.Control);

