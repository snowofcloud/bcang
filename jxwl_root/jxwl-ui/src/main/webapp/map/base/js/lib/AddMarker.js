/**
 * <p>Description: 添加地图标记</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.AddMarker = function(opt_options) {

  var options = opt_options || {};
  var map = options.map;
  var displayClass = options.displayClass || "addMarker";
  var target = options.target || "addMarker";
  //创建控件位置节点
  $("#toolbar").append('<div id="addMarker"></div>');
  var button = document.createElement('button');
  button.title = "标记";
  
  var handler = function(e) { 
	  init();
	    //先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		if(HthxMap.panControl.target && HthxMap.panControl.target !== target){
			if($("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") && $("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") === 'block'){
				$("#" + HthxMap.panControl.target + " > div:nth(0)").css("display",'none');
			}
			if($(".class_plot").css('display') == 'block'){
	        	$(".class_plot").css('display',"none");
	        }
		}
		//记录地图当前激活的控件
		HthxMap.panControl.target = target;
		
		
		if($(".addMarkerPan").css("display") === 'block'){
			$(".addMarkerPan").css("display",'none');
		}else if($(".addMarkerPan").css("display") === 'none'){
            $(".addMarkerPan").css('display', "block");
		}
  }
  
  function init(){
	  if($(".addMarkerPan").length){
		  return;
	  }
	  //添加标记弹出框
	  var strs = '<div class="addMarkerPan" style="height:0px;">' +
	                 '<div class="markerToolPan" style="height:0px;">'+
	                     '<div class="addMarkerArrow" style="height:0px;"></div>' +
	                 '</div>' + 
	                 '<div id="lowMarker">'+
		                 '<div id="addPointMarker" class="addPointMarker"></div>' +
		                 '<div id="addAreaMarker" class="addAreaMarker"></div>' +
		                 '<div id="addImportMarker" class="addImportMarker"></div>' +
		             '</div>'+
	             '</div>';
	  $(strs).insertBefore(".addMarkerInactivate");
	  //点标记
	  var pointMarker = new HthxMap.AddPointMarker({
		  displayClass:'addPointMarker',
		  target:'addPointMarker',
		  map:map
	  });
	  map.addControl(pointMarker);
	  
	  //区域标记
	  var areaMarker = new HthxMap.AddAreaMarker({
		  displayClass:'addAreaMarker',
		  target:'addAreaMarker',
		  map:map
	  });
	  map.addControl(areaMarker);
	  
	  //输入坐标标记
	  var importMarker = new HthxMap.AddImportMarker({
		  displayClass:'addImportMarker',
		  target:'addImportMarker',
		  map:map
	  });
	  map.addControl(importMarker);
  }
  
  
  this.handler = handler;
  button.addEventListener('click', handler, false);

  var element = document.createElement('div');
  element.className = options.displayClass+'Inactivate ol-unselectable';
  element.appendChild(button);

  ol.control.Control.call(this, {
    element: element,
    target: options.target
  });
};
ol.inherits(HthxMap.AddMarker, ol.control.Control);
