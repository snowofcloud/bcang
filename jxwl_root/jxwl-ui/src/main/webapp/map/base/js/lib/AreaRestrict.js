/**
 * <p>Description: 区域限制----添加工具栏</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-29 </p>
 * @author:wl
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.AreaRestrict = function(opt_options) {

  var options = opt_options || {};
  var map = options.map;
  var displayClass = options.displayClass || "areaRestrict";
  var target = options.target || "areaRestrict";
  //创建控件位置节点
  $("#toolbar").append('<div id="areaRestrict"></div>');
  var button = document.createElement('button');
  button.title = "区域限制";
  
  function init(){
	  if($(".bufferPan").length){
		  return;
	  }
	  //只使用多边形换区域时
	 $(".areaRestrictInactivate").on('click',{id:"polygonbuffer"},queryCar);
	  //添加缓冲区弹出框
//	  var strs = '<div class="bufferPan" style="height:0px;">' +
//	                 '<div class="BufferToolPan" style="height:0px;">'+
//	                     '<div class="bufferArrow" style="height:0px; left:5px;"></div>' +
//	                 '</div>' + 
//	                 '<div id="lowBuffer">'+
//		                 '<div style="width:100%; margin-top:2px; margin-left:2px;">' + 
//				       	  	  '<div id="multiBuffer" class="multiBuffer" style="width:142px;cursor:pointer;margin-left: -37px;">' +
//				       	  	  	 '<ul>' +		       	  	  	 	
//				       	  	  	 	'<li id="polygonBuffer" class="mulBuffer"><div id="polygonbuffer" title="多边形" class="polygonbufferInactivate"></div></li>' +
//				       	  	    	/*'<li id="boxBuffer" class="mulBuffer"><div id="boxbuffer" title="矩形" class="boxbufferInactivate"></div></li>' +
//				       	  	  	 	'<li id="circleBuffer" class="mulBuffer"><div id="circlebuffer" title="圆" class="circlebufferInactivate"></div></li>' +
//					       	  	  	'<li id="pointBuffer" class="mulBuffer"><div id="pointbuffer" title="点" class="pointbufferInactivate"></div></li>' +
//				       	  	  	 	'<li id="lineBuffer" class="mulBuffer"><div id="linebuffer" title="线" class="linebufferInactivate"></div></li>' +*/
//				       	  	  	 '</ul>' +		       	  	  
//				       	  	  '</div>' +		       	  	  
//				       	  '</div>' +
//		             '</div>'+
//	             '</div>';
//	  $(strs).insertBefore(".areaRestrictInactivate ");
//	  //缓冲区查询
//	  $(".mulBuffer").on('click', queryCar);
	 // $("#btn_searchRadius")[0].addEventListener("input", checkRadius);
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
  }
  
  this.handler = handler;
  button.addEventListener('click', handler, false);

  var element = document.createElement('div');
  element.className = options.displayClass+'Inactivate ol-unselectable';
  element.appendChild(button);
  
  //搜索半径验证
//  var checkRadius = function(){
//      var reg = /\d+(.\d+)?$/;
//  	  var str = $("#btn_searchRadius").val();
//  	  if(isNaN(str)){
//  		  $("#btn_searchRadius").val("");
//  		  $("#btn_searchRadius").attr("placeholder", "请输入非负数字！");
//  		  return;
//  	  }else{
//  		  var searchRadius = parseFloat(this.value);
//  		  if(searchRadius < 0 || searchRadius > 100000){
//  			  $("#btn_searchRadius").val("");
//  			  $("#btn_searchRadius").attr("placeholder", "请输入0到100千米之间的数字!");
//  			  return;
//  	  	  }
//      }
//  }
  ol.control.Control.call(this, {
    element: element,
    target: options.target
  });
};
ol.inherits(HthxMap.AreaRestrict, ol.control.Control);
