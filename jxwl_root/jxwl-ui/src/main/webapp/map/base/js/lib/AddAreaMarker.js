/**
 * <p>Description: 添加地图区域标记</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-09-06 </p>
 * @author: 孙耀
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.AddAreaMarker = function(opt_options){
	 var options = opt_options || {};
	  var map = options.map;
	  var displayClass = options.displayClass || "addAreaMarker";
	  var target = options.target || "addAreaMarker";
	  var button = document.createElement('button');
	  button.title = "区域标记";
	  
	  var drawArea;
	  function addInteraction(){
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
			
		  if(!HthxMap.curFeatureInteraction){
		      HthxMap.curFeatureInteraction = "active";
		   }
		  var featureLayer =  HthxMap.getLayerById("featureLayer");
		  drawArea = new ol.interaction.Draw({
			  source:featureLayer.getSource(),
			  type:'Circle',
			  style:new ol.style.Style({
				  fill:new ol.style.Fill({
					  color:'rgba(255,255,255,0.2)'
				  }),
				  stroke:new ol.style.Stroke({
				  color: 'rgba(0, 0, 0, 0.5)',
				  lineDash: [10, 10],
				  width: 2
				})
			})
		  });
		  HthxMap.curMapInteraction = drawArea;
		  map.addInteraction(drawArea);
		  HthxMap.mouseUnbindEvent(map, "crosshair");
		  drawArea.on('drawend', function(evt){
			  HthxMap.mouseBindEvent(map);
		        evt.feature.set("clear", false);
		        evt.feature.setId(HthxMap.measureIndex);
		        var coor = evt.feature.getGeometry().getLastCoordinate();
		        
		        var coors = evt.feature.getGeometry().getCenter();
		        coors = HthxMap.keepCoorsLen([coors], bizCommonVariable.coorsLen)[0];
		        var radius = HthxMap.Utils.getDistance(coor[1],coor[0],coors[1],coors[0]);
		        addMarkerCallback(coors, radius);
		        //移除交互
		        map.removeInteraction(HthxMap.curMapInteraction);
		        HthxMap.curFeatureInteraction = null;
		        
				//先清除当前激活控件样式，再添加即将要激活的控件样式
				if(HthxMap.curControl.target){
					$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
					$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
				}
                
		        setTimeout(function(){
			        map.once('pointermove',doubleClickZoom);
		        },500);
		      }, this);
		};
		  
		function doubleClickZoom(){
			map.addInteraction(new ol.interaction.DoubleClickZoom);
		}	
	 
		//添加标记
		function addMarkerCallback(coor,radius) {
			/*var markerObj = {
        		name: "",
    	        id: "",
    	        points: coor,
    	        radius: radius,
    	        desc: "",
    	        icon: 0,
    	        interiorPoint: ""
	        }
			createMarkerPop(map, markerObj, false);*/
			
			var markers = HthxMap.Settings.IMG.marker;
			var name = HthxMap.Settings.IMG.markerName;
			var markerlist = '';
			for(var i=0; i< markers.length; i++){
				var pngUrl = markers[i];
				markerlist += '<li class="markerIconList"><img title="'+name[i]+'" src="'+pngUrl+'"/></li>';
			}
			var coordinate = coor;
			var contentStr = "经纬度：" + coordinate;
			var strArr = [];
			strArr.push('<div class="panel" id="remarkPoint">');
			strArr.push('<div class="panel-heading">');
			strArr.push('<label class="panel-title">标记信息</label>');
			strArr.push('</div>');
			strArr.push('<div class="panel-padding">');
			strArr.push('<form class="form" id="markerForm">');
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
			strArr.push('<label class="dialog-label">半径</label>');
			strArr.push('<div class="inputout input-group-sm">');
			strArr.push('<input type="text" id="markerRadius" readonly class="form-control float-type" value="'+radius+'">');
			strArr.push('<label class="">米</label></div></div>');
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
			strArr.push('<div id="radius" value="'+radius+'"></div>');
			strArr.push('</div></div>');
			strArr.push('<p class="p-center">');
			strArr.push('<input type="button" class="btn btn-primary" id="markerOK" value="确定"/>&nbsp;&nbsp;&nbsp;');
			strArr.push('<input type="button" class="btn btn-default"  id="markerCancel" value="删除" onclick="closePanel(this, true)"/>');
			strArr.push('</p>');
			strArr.push('</form><div id="featureId" value="'+HthxMap.measureIndex+'" style="display:none"></div></div></div>');			
			contentStr = strArr.join("");

			var pngUrl = HthxMap.Settings.IMG.marker[0];//默认图标
			HthxMap.addMarker(map, coordinate, contentStr, pngUrl);
			HthxMap.measureIndex++;
			//使得标绘对象无效
			if(HthxMap.plotObject){
				HthxMap.plotObject.deactivate();
			}
			
			// 恢复控件未被激活的状态
			$("#" + target + " > div").removeClass(displayClass + 'Activate');
			$("#" + target + " > div").addClass(displayClass + 'Inactivate');
			HthxMap.curControl.target = "";
			HthxMap.curControl.displayClass = "";
			
			map.once('pointermove',function(){
				HthxMap.curFeatureInteraction = null;
			});
	  }
	  button.addEventListener('click', addInteraction, false);

	  var element = document.createElement('div');
	  element.className = options.displayClass+'Inactivate ol-unselectable';
	  element.appendChild(button);

	  ol.control.Control.call(this, {
	    element: element,
	    target: options.target
	  });
};
ol.inherits(HthxMap.AddAreaMarker, ol.control.Control);
