/**
 * <p>Description: 清除图层中的要素</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-11 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：map(必选),displayClass(控件样式,可选),target(控件位置，可选),layers(要清除要素的图层，必选).
*/
HthxMap.ClearVector = function(opt_options) {
  var options = opt_options || {};
  var layers = options.layers;
  var displayClass = options.displayClass || "clearVector";
  var target = options.target || "clearVector";
  var map = options.map;
  
  //创建控件位置节点
  $("#toolbar").append('<div id="clearVector"></div>');

  var button = document.createElement('button');
  button.title = "清除";
  
  var handler = function(e) {
	  //移除地图绑定事件
	  HthxMap.removeMapEvents(map);
	  
	  if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
			HthxMap.curControl.target = "";
			HthxMap.curControl.displayClass = "";
		}
      HthxMap.distanceIndex = 0;
      
        var $openRestrictId = $(".openRestrictFlag");
		var ids =[];
		$.each($openRestrictId,function(index,content){
			ids.push($openRestrictId[index].id);
		})
		var dataID = new Array();
		//隐藏限制区域
		if (0 != ids.length) {
			_isOpenOrClose = false;
			 Message.confirm({Msg:"确定隐藏限制区域", iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 for (var i = 0; i < ids.length; i++) {
							//清除地图中相应的围栏
							HthxMap.clearMeasure(HthxMap.areaRestrictIndex[ids[i]]);
						}
				 }
			 });
		} else {
			//显示限制区域
			areaRestrictFind.setElectRail();
		}
      
      
//      //跟踪、报警、台风圈等不移除，快速定位应该移除
//      HthxMap.removeOverlay(map);
//      //移除绘图层，标记等不应该移除
//      HthxMap.removeInteraction(layers);
//      //移除搜索状态
//      var name = $("#conditionLabel .inquireItem-content").html();//分支
//	  if(name === "动态查询"){
//		  clearBoatInfo();
//		  $("#conditionLabel").html("");
//	  }
//	  //删除测量，不删除标绘和标记
//	  for(var i = 0; i <= HthxMap.measureIndex; i++){
//		  if(!bizCommonSetting.plotId[i] && !HthxMap.electronRail[i] && !HthxMap.markerIndex[i]){ //不是标绘
//			  HthxMap.clearMeasure(i);
//		  }
//	  }
  };
  button.addEventListener('click', handler, false);

  var element = document.createElement('div');
  element.className = options.displayClass+'Inactivate ol-unselectable ';
  element.appendChild(button);

  ol.control.Control.call(this, {
    element: element,
    target: options.target
  })
}
ol.inherits(HthxMap.ClearVector, ol.control.Control);

