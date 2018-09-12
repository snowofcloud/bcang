/**
 * <p>Description: 测量面积</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 孙耀
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/
HthxMap.MeasureArea = function(opt_options){
	var options = opt_options || {};	
	var map = options.map;
	var displayClass = options.displayClass;
	var target = options.target;
	var featureLayer = HthxMap.getLayerById("featureLayer");
	//创建控件位置节点
	var button = document.createElement('button');
	button.title = "测面";
	var element = document.createElement('div');
	element.className = displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element: element,
		target: options.target
	});

	button.addEventListener('click',addInteraction,false);

	var sketch;
	var measureTooltipElement;
	var measureTooltip;

    /**
	 * 鼠标移动处理事件
	 * @param {ol.MapBrowerEvent}
	 *            evt
	 */
	var pointerMoveHandler = function(evt){
	    if (evt.dragging) {
		    return;
		}
		  /** @type {string} */
		 var helpMsg = '点击开始测量';
		  /** @type {ol.Coordinate|undefined} */
		 var tooltipCoord = evt.coordinate;
		 if (sketch) {
		    var output;
		    var geom = (sketch.getGeometry());
		    if (geom instanceof ol.geom.Polygon) {
		       output = formatArea(/** @type {ol.geom.Polygon} */ (geom));
		       tooltipCoord = geom.getInteriorPoint().getCoordinates();
		    }
		    measureTooltipElement.innerHTML = output;
		    measureTooltip.setPosition(tooltipCoord);
		  }
	};

	var source = featureLayer.getSource();
	var draw;
	function addInteraction() {
		//移除地图事件
		HthxMap.removeMapEvents(map);
		if(!HthxMap.curFeatureInteraction){
			HthxMap.curFeatureInteraction = "active";
		}
		//移除地图双击放大事件
		var interactions = map.getInteractions();
		var interactionLen = interactions.getLength();
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
		
        initTargetClass();
		draw = new ol.interaction.Draw({
		    source: source,
		    type: 'Polygon',
		    style: new ol.style.Style({
		        fill: new ol.style.Fill({
		        color: 'rgba(255, 255, 255, 0.2)'
		      }),
		    stroke: new ol.style.Stroke({
		        color: 'rgba(0, 0, 0, 0.2)',
		        lineDash: [10, 10],
		        width: 2
		      }),
		    image: new ol.style.Circle({
		        radius: 3,
		        stroke: new ol.style.Stroke({
		          color: 'rgba(0, 0, 0, 0.7)'
		        }),
		        fill: new ol.style.Fill({
		          color: 'rgba(255, 255, 255, 0.2)'
		        })
		      })
		    })
		  });
		HthxMap.curMapInteraction = draw;
		map.addInteraction(draw);

		createMeasureTooltip();
		HthxMap.mouseUnbindEvent(map, HthxMap.Settings.IMG.distance);
		draw.on('drawstart', function(evt) {
		        // set sketch
		        map.on('pointermove', pointerMoveHandler);
		        HthxMap.curMapOnPointerMove = pointerMoveHandler;
		        sketch = evt.feature;
		      }, this);

		draw.on('drawend', function(evt) {
			    HthxMap.mouseBindEvent(map);
		        measureTooltipElement.className = 'mtooltip mtooltip-static';
		        measureTooltip.setOffset([0, -7]);

		        sketch = null;
//		        evt.feature.set("clear", true);
		        evt.feature.setId(HthxMap.measureIndex);
		        var coor = evt.feature.getGeometry().getLastCoordinate();
		        var strs = '<div style="cursor:pointer;margin-left:47%;" onclick="HthxMap.clearMeasure('  + HthxMap.measureIndex + ')">x</div>';
		        $(measureTooltipElement.parentNode).append(strs);
		        measureTooltipElement = null;
		        HthxMap.measureIndex++;
		        
		        map.un('pointermove',pointerMoveHandler);
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
	}
	
	function doubleClickZoom(){
		map.addInteraction(new ol.interaction.DoubleClickZoom);
	}

	function initTargetClass(){
		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		$("#" + options.target + " > div").removeClass(displayClass + 'Inactivate');
		$("#" + options.target + " > div").addClass(displayClass + 'Activate');

		//记录地图当前激活的控件
		HthxMap.curControl.target = target;
		HthxMap.curControl.displayClass = displayClass;
	}

	/**
	 * Creates a new measure tooltip
	 */
	function createMeasureTooltip() {
	    if (measureTooltipElement) {
	        measureTooltipElement.parentNode.removeChild(measureTooltipElement);
	    }
	    measureTooltipElement = document.createElement('div');
//	    measureTooltipElement.className = 'mtooltip mtooltip-measure';
	    measureTooltip = new ol.Overlay({
	       element: measureTooltipElement,
	       offset: [0, -15],
	       positioning: 'bottom-center'
	    });
	    measureTooltip.setProperties({id: HthxMap.measureIndex});
	    map.addOverlay(measureTooltip);
	}

	/**
	 * format length output
	 * @param {ol.geom.Polygon} polygon
	 * @return {string}
	 */
	var formatArea = function(polygon) {
	    var area;
	    var wgs84Sphere= new ol.Sphere(6378137);
	    var sourceProj = map.getView().getProjection();
	    var geom = /** @type {ol.geom.Polygon} */(polygon.clone().transform(
	        sourceProj, 'EPSG:4326'));
	    var coordinates = geom.getLinearRing(0).getCoordinates();
	    area = Math.abs(wgs84Sphere.geodesicArea(coordinates));
		var output;
		if (area > 10000) {
		    output = (Math.round(area / 1000000 * 100) / 100) +
		        ' ' + '平方公里';
		} else {
		    output = (Math.round(area * 100) / 100) +
		        ' ' + 'm<sup>2</sup>';
		}
		    return output;
		};
}
ol.inherits(HthxMap.MeasureArea, ol.control.Control);
