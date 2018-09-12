/**
 * <p>Description: 自定义区域----添加工具栏</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-29 </p>
 * @author:wl
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.UserDefinedArea = function(opt_options) {

  var options = opt_options || {};
  var map = options.map;
  var displayClass = options.displayClass || "userDefinedArea";
  var target = options.target || "userDefinedArea";
  //创建控件位置节点
  $("#toolbar").append('<div id="userDefinedArea"></div>');
  var button = document.createElement('button');
  button.title = "自定义区域";
  
  function init(){
	  if($(".bufferPan").length){
		  return;
	  }
	  //只使用多边形换区域时
	 $(".userDefinedAreaInactivate").on('click',{id:"polygonbuffer"},queryUserDefined);
	  //添加缓冲区弹出框
  }
  
  var handler = function(e) { 
	    init();
	    //先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		if(HthxMap.panControl.target && HthxMap.panControl.target !== target){
//			if($("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") && $("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") === 'block'){
//				$("#" + HthxMap.panControl.target + " > div:nth(0)").css("display",'none');
//			}
//			if($(".class_plot").css('display') == 'block'){
//	        	$(".class_plot").css('display',"none");
//	        }
		}
		//记录地图当前激活的控件
		HthxMap.panControl.target = target;
		
		if($(".bufferPan").css("display") === 'block'){
			$(".bufferPan").css("display",'none');
		}else if($(".bufferPan").css("display") === 'none'){
			$(".bufferPan").css('display', "block");
			$("#btn_searchRadius").val("");
	  		  $("#btn_searchRadius").attr("placeholder", "单位m");
		}
  };
  
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
ol.inherits(HthxMap.UserDefinedArea, ol.control.Control);
