/**
 * <p>Description: 分队战斗行动</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 孙耀
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.PlotSquadCombat = function(opt_options){
	var options = opt_options || {};
	var map = options.map;
	var target = options.target;
	var displayClass = options.displayClass;
	var featureLayer = options.layer;

	var button = document.createElement('button');
	button.attributes.className = displayClass || ('');
	button.title = "分战斗队形(头)";
	var element = document.createElement('div');
	element.className = displayClass + 'Inactivate ol-unselectable ol-controlPlot';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element: element,
		target: options.target
	});

	button.addEventListener('click',handle,false);

	function handle(){
		//移除地图事件
		HthxMap.removeMapEvents(map);
		
		initTargetClass();
		HthxMap.plotObject.activate(plot.PlotTypes.SQUAD_COMBAT);
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
ol.inherits(HthxMap.PlotSquadCombat,ol.control.Control);