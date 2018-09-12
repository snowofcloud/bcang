/**
 * <p>Description: 测量构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-08 </p>
 * @author: 何泽潘
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
 */
HthxMap.Measure = function(opt_options) {

	var options = opt_options || {};
	var displayClass = options.displayClass;
	var map = options.map;
	//创建控件位置节点
	$("#toolbar").append('<div id="measure"></div>');
	var button = document.createElement('button');
	button.title = "测量";
	
	//添加button控件按钮
	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
	button.addEventListener('click', addInteraction, false);
    function init(){
    	if($(".addMeasurePan").length){
    		return;
    	}
    	var strs = '<div class="addMeasurePan" style="height:0px;">' +
			    '<div class="measureToolPan" style="height:0px;">'+
			        '<div class="addMeasureArrow" style="height:0px;"></div>' +
			    '</div>' + 
			    '<div id="lowMeasure">' +
				    '<div id="measureDistance" class="measureDistance"></div>' +
				    '<div id="measureArea" class="measureArea"></div>' +
				    '<div id="measureOrientation" class="measureOrientation"></div>' +
				'</div>'+
			'</div>';
		$(strs).insertBefore(".measureInactivate");
		//测距
	    var measureDistance = new HthxMap.MeasureDistance({
	    	displayClass:'measureDistance',
	    	target:'measureDistance',
	    	map:map
	    });
	    map.addControl(measureDistance);
	    //测面积
	    var measureArea = new HthxMap.MeasureArea({
	    	displayClass:'measureArea',
	    	target:'measureArea',
	    	map:map
	    });
	    map.addControl(measureArea);
	    //测方位
	    var measureOrientation = new HthxMap.MeasureOrientation({
	    	displayClass:'measureOrientation',
	    	target:'measureOrientation',
	    	map:map
	    });
	    map.addControl(measureOrientation);
    }
    
	function addInteraction(){
		init();
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
//		$("#" + options.target + " > div").removeClass(displayClass + 'Inactivate');
//		$("#" + options.target + " > div").addClass(displayClass + 'Activate');
//		//记录地图当前激活的控件
		HthxMap.panControl.target = options.target;
		
//		HthxMap.curControl.target = options.target;
//		HthxMap.curControl.displayClass = displayClass;
		
		if($(".addMeasurePan").css("display") === 'block'){
			$(".addMeasurePan").css("display",'none');
		}else if($(".addMeasurePan").css("display") === 'none'){
//			var ttop  = $("#measure").offset().top;     //控件的定位点高
//            var thei  = $("#measure").height();  //控件本身的高
//            var twidth =  $("#measure").width();
//            var tleft = $("#measure").offset().left;    //控件的定位点宽
            $(".addMeasurePan").css('display', "block");
		}
	}
};

ol.inherits(HthxMap.Measure, ol.control.Control);

