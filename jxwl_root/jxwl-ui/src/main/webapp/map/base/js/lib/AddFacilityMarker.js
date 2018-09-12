/**
 * <p>Description: 添加地图点标记</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-06 </p>
 * @author: 孙耀
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.AddFacilityMarker = function(opt_options){
	  var options = opt_options || {};
	  var map = options.map;
	  var displayClass = options.displayClass || "addFacilityMarker";
	  var target = options.target || "addFacilityMarker";
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
			//HthxMap.mouseEvent(map, "singleclick", "addMarkerCallback");
			map.on('singleclick', addMarkerCallback);
			function addMarkerCallback(evt) {
				//var coordinate = HthxMap.keepCoorsLen([evt.coordinate], bizCommonVariable.coorsLen)[0];
				var coordinate = [evt.coordinate][0];
				var contentStr = "";
				var markers = HthxMap.Settings.IMG.facility;
				var markertitle = HthxMap.Settings.IMG.facilityName;
				var markerlist = '';
				for(var i=0; i< markers.length; i++){
					var pngUrl = markers[i];
					markerlist += '<li class="markerIconList"><img title="'+markertitle[i]+'" src="'+pngUrl+'"/></li>';
				}
				//var name = HthxMap.facilityName;
				var strArr = [];
				strArr.push('<div class="panel" id="remarkPoint">');
				strArr.push('<div class="panel-heading">');
				strArr.push('<label class="panel-title">自定义设施信息</label>');
				strArr.push('</div>');
				strArr.push('<div class="panel-padding">');
				strArr.push('<form class="form" id="markerForm">');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label must">自定义设施名称</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<input type="text" name="facilityName" id="reaDefinedFacility-name" class="form-control " placeholder="请输入1~20个字符" maxlength="20" value="'+name+'">');
				strArr.push('</div>');
				strArr.push('</div>');
				strArr.push('<div class="row row-group">');
				strArr.push('<label class="dialog-label">图标</label>');
				strArr.push('<div class="inputout remark-icon">');
				strArr.push('<ul id="markerlist" class="clearfix">');
				strArr.push(markerlist);
				strArr.push('</ul><input type="text" id="iconids" class="hidden"></div></div>');
				strArr.push('<div class="row row-group hidden">');
				strArr.push('<label class="dialog-label must">北纬</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<input type="text" id="markerLat" readonly class="form-control float-type" value="'+coordinate[1]+'">');
				strArr.push('<label class="">度</label>');
				strArr.push('</div>');
				strArr.push('</div>');
				strArr.push('<div class="row row-group hidden">');
				strArr.push('<label class="dialog-label must">东经</label>');
				strArr.push('<div class="inputout input-group-sm">');
				strArr.push('<input type="text" id="markerLon" readonly class="form-control float-type" value="'+coordinate[0]+'">');
				strArr.push('<label class="">度</label>');
				strArr.push('</div>');
				strArr.push('</div>');
				strArr.push('<p class="text-center">');
				strArr.push('<input type="button" class="btn btn-primary" id="markerFacilityOK" value="确定"/>&nbsp;&nbsp;&nbsp;');
				strArr.push('<input type="button" class="btn btn-default" id="markerCancel" value="删除"/>');
				strArr.push('</p>');
				strArr.push('</form><div id="featureId" value="'+HthxMap.measureIndex+'" style="display:none"></div></div></div>');			
				contentStr = strArr.join("");
				
				//新增弹框
				/*userDefinedFacilityFind.definedFacilityShowPanel("#addUserDefinedFacility-panel","新增自定义设施");*/
				var pngUrl = HthxMap.Settings.IMG.facility[0];//默认图标
				HthxMap.addMarker(map, coordinate, contentStr, pngUrl);
				
				map.un('singleclick',addMarkerCallback);
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
ol.inherits(HthxMap.AddFacilityMarker, ol.control.Control);
