/**
 * <p>Description: 电子围栏</p>
 * <p>Copyright: </p> 
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-08 </p>
 * @author: txm
 * @extends：{ol.control.Control}
 * @param：.
*/
HthxMap.ElectRail = function(opt_options) {

	var options = opt_options || {};
	var displayClass = options.displayClass || "electRail";
	var target = options.target || "electRail";
	var map = options.map;
	var featureLayer = HthxMap.getLayerById("featureLayer");
	//创建控件位置节点
	$("#bufferSearch").after('<div id="electRail"></div>');
	var button = document.createElement('button');
	button.title = "电子围栏";
	
	var strs = '<div class="addElectRailPan" style="height:0px;">' +
				    '<div class="ElectRailToolPan" style="height:0px;">'+
				        '<div class="addElectRailArrow" style="height:0px;"></div>' +
				    '</div>' + 
				    '<div id="lowElectRai">' +
					    '<div id="circleElectRail" class="electRailArea circleElectRailInactivate"></div>' +
					    '<div id="boxElectRail" class="electRailArea boxElectRailInactivate"></div>' +
					    '<div id="polygonElectRail" class="electRailArea polygonElectRailInactivate"></div>' +
					    '<div id="lineElectRail" class="electRailArea lineElectRailInactivate"></div>' +
					'</div>'+
				'</div>';
	$("#electRail").append(strs);
	//电子围栏区域入口
	//$(".electRailArea").on('click', electronRail.electronRailArea);
	$(".electRailArea").on('click', electronRailArea);
	//添加button控件按钮
	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
	button.addEventListener('click', addInteraction, false);

	function addInteraction(){

		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		if(HthxMap.panControl.target && HthxMap.panControl.target !== options.target){
			if($("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") && $("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") === 'block'){
				$("#" + HthxMap.panControl.target + " > div:nth(0)").css("display",'none');
			}
			if($(".class_plot").css('display') == 'block'){
	        	$(".class_plot").css('display',"none");
	        }
		}
		//记录地图当前激活的控件
		HthxMap.panControl.target = options.target;
		
		
		if($(".addElectRailPan").css("display") === 'block'){
			$(".addElectRailPan").css("display",'none');
		}else if($(".addElectRailPan").css("display") === 'none'){
            $(".addElectRailPan").css('display', "block");
		}
	};
};

ol.inherits(HthxMap.ElectRail, ol.control.Control);

