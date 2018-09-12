/**
 * <p>Description: 添加地图点标记</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-06 </p>
 * @author: 孙耀
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.AddPointMarker = function(opt_options){
	  var options = opt_options || {};
	  var map = options.map;
	  var displayClass = options.displayClass || "addPointMarker";
	  var target = options.target || "addPointMarker";
	  //创建控件位置节点
	  var button = document.createElement('button');
	  button.title = "点标记";
	  
	  var handler = function(e) {  
		    //移除地图绑定事件
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
			
			HthxMap.curMapOn = addMarkerCallback;
			// 清除地图之前的交互
			if(HthxMap.curMapInteraction){
			    map.removeInteraction(HthxMap.curMapInteraction);
			}
			if(!HthxMap.curFeatureInteraction){
				HthxMap.curFeatureInteraction = "active";
			}
			// 添加地图单击事件，在单击地图时添加标记
			map.on('singleclick', addMarkerCallback);
			function addMarkerCallback(evt) {
				var markers = HthxMap.Settings.IMG.marker;
				var name = HthxMap.Settings.IMG.markerName;
				var markerlist = '';
				for(var i=0; i< markers.length; i++){
					var pngUrl = markers[i];
					markerlist += '<li class="markerIconList"><img title="'+name[i]+'" src="'+pngUrl+'"/></li>';
				}
				
				var coordinate = HthxMap.keepCoorsLen([evt.coordinate], bizCommonVariable.coorsLen)[0];
				var contentStr = "";
				var strArr = [];
				strArr.push('<div class="panel" id="remarkPoint">');
				strArr.push('<div class="panel-heading">');
				strArr.push('<label class="panel-title">标记信息</label>');
				strArr.push('</div>');
				strArr.push('<div class="panel-padding">');
				strArr.push('<form class="form" id="">');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label must">名称</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<input type="text" id="markerName" class="form-control" placeholder="请输入1~20个字符" maxlength="20">');
				strArr.push('</div>');
				strArr.push('</div>');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label must">北纬</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<input type="text" id="markerLat" readonly class="form-control float-type" value="'+coordinate[1]+'">');
				strArr.push('<label class="">度</label>');
				strArr.push('</div>');
				strArr.push('</div>');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label must">东经</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<input type="text" id="markerLon" readonly class="form-control float-type" value="'+coordinate[0]+'">');
				strArr.push('<label class="">度</label>');
				strArr.push('</div>');
				strArr.push('</div>');
				strArr.push('<div>');
				strArr.push('<input id="convertDegrees" style="margin-left:50px;" type="checkbox" onclick="convertDegreesToStringHDMS()"/>'+'<label for="convertDegrees">显示转换</label> ');
				strArr.push('</div>');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label">图标</label>');
				strArr.push('<div class="inputout remark-icon">');
				strArr.push('<ul id="markerlist" style="">');
				strArr.push(markerlist);
				strArr.push('</ul></div></div>');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label">备注</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<textarea rows="3" cols="5" class="form-control" id="markerContent" placeholder="请输入0~200个字符" maxlength="200"></textarea>');
				strArr.push('</div></div>');
				strArr.push('<p class="p-center">');
				strArr.push('<input type="button" class="btn btn-primary" id="markerOK" value="确定"/>&nbsp;&nbsp;&nbsp;');
				strArr.push('<input type="button" class="btn btn-default"  id="markerCancel" value="删除" onclick="closePanel(this, true)"/>');
				strArr.push('</p>');
				strArr.push('</form></div></div>');			
				contentStr = strArr.join("");
				var pngUrl = HthxMap.Settings.IMG.marker[0];//默认图标
				HthxMap.addMarker(map, coordinate, contentStr, pngUrl);
				
				map.un('singleclick', addMarkerCallback);
				//使得标绘对象无效
				if(HthxMap.plotObject){
					HthxMap.plotObject.deactivate();
				}
				
				// 恢复控件未被激活的状态
				$("#" + target + " > div").removeClass(displayClass + 'Activate');
				$("#" + target + " > div").addClass(displayClass + 'Inactivate');
				HthxMap.curControl.target = "";
				HthxMap.curControl.displayClass = "";
				
				map.un('singleclick', HthxMap.curMapOn);
				map.once('pointermove',function(){
					HthxMap.curFeatureInteraction = null;
				});
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
ol.inherits(HthxMap.AddPointMarker, ol.control.Control);
