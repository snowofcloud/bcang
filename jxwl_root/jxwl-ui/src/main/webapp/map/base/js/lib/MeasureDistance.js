/**
 * <p>Description: 测距构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-12 </p>
 * @author: 何泽潘
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
 */
HthxMap.MeasureDistance = function(opt_options) {

	var options = opt_options || {};
	var displayClass = options.displayClass;
	var map = options.map;
	//创建控件位置节点
	var button = document.createElement('button');
	button.title = "测距";

	var sketch;
	var measureTooltipElement;
	var measureTooltip;
	var deleteElement;
	var deleteE;
    var flag;
    var distanceKey;
    
	//添加button控件按钮
	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
	button.addEventListener('click', addInteraction, false);

	var tempLayers = map.getLayers();
	var featureLayer = HthxMap.getLayerById("featureLayer");
	var source = featureLayer.getSource();

	var draw;
	function addInteraction(){
		//移除地图事件
		HthxMap.removeMapEvents(map);
		if(!HthxMap.curFeatureInteraction){
			HthxMap.curFeatureInteraction = "active";
		}
		var interactions = map.getInteractions();
		var interactionLen = interactions.getLength();;
		//移除地图双击放大事件
		var tempInteraction;
		var tempInteractionArr = [];
		for(var i = 0; i< interactionLen; i++){
			tempInteraction = interactions.item(i);
        	if(tempInteraction instanceof ol.interaction.DoubleClickZoom){
        		tempInteractionArr.push(tempInteraction);
        	}
        }
		for(var i = 0;i < tempInteractionArr.length;i++){
			map.removeInteraction(tempInteractionArr[i]);
		}

		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		$("#" + options.target + " > div").removeClass(displayClass + 'Inactivate');
		$("#" + options.target + " > div").addClass(displayClass + 'Activate');
		//记录地图当前激活的控件
		HthxMap.curControl.target = options.target;
		HthxMap.curControl.displayClass = displayClass;
		
		draw = new ol.interaction.Draw({
			source:source,
			type:'LineString',
			style:new ol.style.Style({
				fill:new ol.style.Fill({
					color:'rgba(255,255,255,0.2)'
				}),
				stroke:new ol.style.Stroke({
					color: 'rgba(0, 0, 0, 0.2)',
			        lineDash: [10, 10],
			        width: 2
				}),
				image:new ol.style.Circle({
					radius:3,
					stroke: new ol.style.Stroke({
			            color: 'rgba(0, 0, 0, 0.7)'
			        }),
			        fill: new ol.style.Fill({
			            color: 'rgba(255, 255, 255, 0.2)'
			        })
				})
			})
		});
		flag = true;
		HthxMap.curMapInteraction = draw;
		map.addInteraction(draw);
		//增加测量结果和帮助提示
		createMeasureTooltip();
		HthxMap.mouseUnbindEvent(map, HthxMap.Settings.IMG.distance);
		draw.on('drawstart',function(e){			
			sketch = e.feature;			
			map.on('pointermove', pointerMoveHandler);
			HthxMap.curMapOnPointerMove = pointerMoveHandler;			
			
			distanceKey = map.on('click', function(e){   
        		var output;
            	var geom = sketch.getGeometry();           	
            	if(flag){
            		output = "起点";
            		flag = false;
            	}else{
            		output = formatLength(geom);    
            	}            	
            	
            	tooltipCoord = geom.getLastCoordinate();
        		
	   			var measureTooltip_Element = document.createElement('div');
	   		    var measure_Tooltip = new ol.Overlay({
	   			    element: measureTooltip_Element,
	   			    offset: [0, -5],
	   			    positioning: 'bottom-center'
	   		    });
	   		    map.addOverlay(measure_Tooltip);
	   		    measureTooltip_Element.innerHTML = output;
	   		    measure_Tooltip.setPosition(tooltipCoord); 
	   		    measure_Tooltip.setProperties({id: HthxMap.measureIndex});
   		   });
		},this);

		draw.on('drawend', function(e){
			HthxMap.mouseBindEvent(map);
			
			//根据属性，删除最后一个overlayer
			var overlays = map.getOverlays();
			var overlaysLength = overlays.getLength();
			for(var i = overlaysLength-1; i >= 0; i--){
				var item = overlays.item(i);
				if(item && item.getProperties() && item.getProperties().id === HthxMap.measureIndex){
					map.removeOverlay(item);
					break;
				}
			}
			
			measureTooltipElement.className = 'mtooltip mtooltip-static';
			measureTooltip.setOffset([0, -17]);
			sketch = null;
			measureTooltipElement = null;
			map.unByKey(distanceKey);

	        map.un('pointermove', pointerMoveHandler);
	        setTimeout(function(){
		        map.once('pointermove', doubleClickZoom);
	        },500);
	        map.removeInteraction(HthxMap.curMapInteraction);
	        HthxMap.curFeatureInteraction = null;
//	        e.feature.set("clear", true);
	        e.feature.setId(HthxMap.measureIndex);
	        var coor = e.feature.getGeometry().getLastCoordinate();
	        HthxMap.createDeleteElement(coor, HthxMap.measureIndex);
	        HthxMap.measureIndex++;
	        
	        //恢复控件未被激活的状态
			$("#" + options.target + " > div").removeClass(displayClass + 'Activate');
			$("#" + options.target + " > div").addClass(displayClass + 'Inactivate');
			HthxMap.curControl.target = "";
			HthxMap.curControl.displayClass = "";
		},this);
	}
	var pointerMoveHandler = function(e) {
		if(e.dragging){
			return;
		}
        var tooltipCoord = e.coordinate;

        //开始测量
        if(sketch){
        	var output;
        	var geom = sketch.getGeometry();
        	output = formatLength(geom);    //0m
        	tooltipCoord = geom.getLastCoordinate();

        	measureTooltipElement.innerHTML = output;
 		    measureTooltip.setPosition(tooltipCoord);
        }
	}
	
	function doubleClickZoom(){
		map.addInteraction(new ol.interaction.DoubleClickZoom);
	}
	
	/*function createHelpTooltip() {
	    if (helpTooltipElement) {
	    	helpTooltipElement.parentNode.removeChild(helpTooltipElement);
	    }
	    helpTooltipElement = document.createElement('div');
	    helpTooltipElement.className = 'mtooltip';
	    helpTooltip = new ol.Overlay({
		    element: helpTooltipElement,
		    offset: [15, 0],
		    positioning: 'center-left'
	    });
	    map.addOverlay(helpTooltip);
	}*/

	function createMeasureTooltip() {
	    if (measureTooltipElement) {
	    	measureTooltipElement.parentNode.removeChild(measureTooltipElement);
	    }
	    measureTooltipElement = document.createElement('div');
	    measureTooltip = new ol.Overlay({
		    element: measureTooltipElement,
		    offset: [0, -5],
		    positioning: 'bottom-center'
	    });
	    measureTooltip.setProperties({id: HthxMap.measureIndex});
	    map.addOverlay(measureTooltip);
	}
	
	var formatLength = function(line) {
	    var length=0;
	    var coordinates = line.getCoordinates();
	    var wgs84Sphere= new ol.Sphere(6378137);

	    var sourceProj = map.getView().getProjection();
	    for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
	      var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
	      var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
	      length += wgs84Sphere.haversineDistance(c1, c2);
	    }

	    length = Math.round(length * 100) / 100;
	    var output;
	    if (length > 100) {
	    	var ukm = (Math.round(length / 1000 * 100) / 100);
	    	var uhl = HthxMap.Utils.lengthUnitShift("km",ukm);
		    output = uhl + '海里(' + ukm + '公里)' ;
	    } else {
	    	output = (Math.round(length * 100) / 100) + ' ' + 'm';
	    }
	    return output;
	};
};

ol.inherits(HthxMap.MeasureDistance, ol.control.Control);

