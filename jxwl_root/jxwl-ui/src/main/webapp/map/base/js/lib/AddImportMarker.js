/**
 * <p>Description: 添加地图输入坐标标记</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-11 </p>
 * @author: chenm
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.AddImportMarker = function(opt_options){
	var options = opt_options || {};
	var map = options.map;
	var displayClass = options.displayClass || "addImportMarker";
	var target = options.target || "addImportMarker";
	var button = document.createElement('button');
	button.title = "输入坐标区域标记";
	
	var element = document.createElement('div');
	element.className = displayClass + 'Inactivate ol-unselectable';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element: element,
		target: options.target
	});
    
	button.addEventListener('click', handler, false);
	
	//激活标绘功能
	function handler(){
		//移除地图事件
		HthxMap.removeMapEvents(map);
		initTargetClass();
		$("#addImportMarker").on("click",function() {
			importMarker.init();
		});
	}

	function initTargetClass(){
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
	}
}
ol.inherits(HthxMap.AddImportMarker, ol.control.Control);
