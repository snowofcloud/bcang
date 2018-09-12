/**
 * <p>Description: 坐标定位构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-11 </p>
 * @author: 何泽潘
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
 */
HthxMap.SearchLocation = function(opt_options) {
	
	var options = opt_options || {};
	var map = options.map;
	var openGrade = options.openGrade || "7";

	var button = document.createElement('button');
	button.title = "定位";

	//添加button控件按钮
	var element = document.createElement('div');
	element.className = options.displayClass + ' ol-unselectable ol-control';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});

	button.addEventListener('click', handle, false);

	function handle(){
		if($("body div[id=searchLocation_div]")[0] != null){
			$(".searchLocation_div").remove();
			return;
		}
		HthxMap.creatDialog("popBox");
	}
};
ol.inherits(HthxMap.SearchLocation, ol.control.Control);

/**
 * <p>Description: 坐标定位弹出框样式函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-09 </p>
 * @author: 何泽潘
 * @param：style(自定义css样式)
 * @return:
 */
HthxMap.creatDialog = function(style){
	var dia;
	dia = document.createElement("div");
	dia.id = "searchLocation_div";
	dia.className = "searchLocation_div";
	$("body").append(dia);

	if(style == ""){
		$("#" + dia.id).addClass("popBox");
	}else{
		$("#" + dia.id).addClass(style);
	}
	
	//头部
	var head = document.createElement("div");
	head.id = "searchLocation_head";
	head.className = "searchLocation_head";
	var search_Title = '<span style="font-size:14px;">坐标搜索定位</span><label class="searchLocation-close" id="searchLocation_close" style="cursor:pointer;position:absolute;left:207px;">✖</label>';
	$("#searchLocation_head").css("background-color","rgba(206, 138, 123,0.82)");
	head.innerHTML = search_Title;

	//内容
	var content = document.createElement("div");
	content.id = "searchLocation_content";
	content.className = "searchLocation_content";
	var search_Content = '<table style="margin-top:5px;background-color:white;height:74px;"><tr><td style="font-size:12px;">坐标：</td><td><input type="text" id="searchText" style="width:128px;"/></td><td>'+
	'<input type="button" id="search_button" style="width:36px;cursor:pointer;" value="查询"/></td></tr>'+
	'<tr><td colspan="3"><font style="font-size:12px;color:red">输入坐标以英文逗号","隔开</font></td></tr></table>';
	content.innerHTML = search_Content;

	$("#searchLocation_div").append(head).append(content);
	//绑定搜索和关闭事件
	$("#search_button").bind("click", HthxMap.doSearch);
	var getDialog = document.getElementById("searchLocation_close");
	getDialog.onclick = function(){
		$(".searchLocation_div").remove();
	};

	//弹出框DIV拖动效果
	$(".searchLocation_div").mousedown(function(e){    //e鼠标事件
		$(this).css("cursor", "move");//改变鼠标指针的形状

		var offset = $(this).offset();//DIV在页面的位置
		var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离
		var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离
		$(document).bind("mousemove", function(ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
		{
			$(".searchLocation_div").stop();

			var _x = ev.pageX - x;//获得X轴方向移动的值
			var _y = ev.pageY - y;//获得Y轴方向移动的值

			$(".searchLocation_div").animate({
				left : _x + "px",
				top : _y + "px"
			}, 10);
		});
	});
	$(document).mouseup(function() {
		$(".searchLocation_div").css("cursor", "default");
		$(this).unbind("mousemove");
	});
}
/**
 * <p>Description: 坐标定位搜索回调函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-09 </p>
 * @author: 何泽潘
 * @param：
 * @return:
 */
HthxMap.doSearch = function(){
	var lon;
	var lat;
	var num = 0;
	var value = document.getElementById("searchText").value;
	if(value.indexOf(",") < 0){
		alert("输入格式错误！请输入经纬度数字，中间以逗号分隔。");
		return;
	}
	var valueArr = value.split(",");
	lon = Number.parseInt(valueArr[0]);
	lat = Number.parseInt(valueArr[1]);
	var marker = document.createElement("div");
	marker.id = "marker"+num;
	marker.innerHTML = '<a href="#"><img src="../img/measureDistance.png"/></a>';
	var overlay = new ol.Overlay({
		element: marker,
		offset: [18, 0],
	    positioning: 'center-left',
		autoPan: true,
		autoPanAnimation: {
		   duration: 250
		}
	});
	map.addOverlay(overlay);
	overlay.setPosition([lon,lat]);
	map.getView().setCenter([lon,lat]);
	map.getView().setZoom(openGrade);
	$("#marker"+num).on("click",HthxMap.showTips);
	num++;
	$(".searchLocation_div").remove();
}
/**
 * <p>Description: 坐标定位搜索回调函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-09 </p>
 * @author: 何泽潘
 * @param：
 * @return:
 */
HthxMap.showTips = function(){
	var getTips = $("#showTips").css("display");
	if(getTips && getTips=="none"){
		$("#showTips").css("display","block");
	}else if(getTips && getTips=="block"){
		$("#showTips").css("display","none");
	}else{
		var tips = document.createElement("div");
		tips.id = "showTips";
	    tips.innerHTML = "坐标: " + lon +", " + lat;
		var overlay = new ol.Overlay({
			element: tips,
			offset: [18, 0],
		    positioning: 'center-left',
			autoPan: true,
			autoPanAnimation: {
			  duration: 250
			}
	    });
		map.addOverlay(overlay);
		overlay.setPosition([lon,lat]);
	}
}
