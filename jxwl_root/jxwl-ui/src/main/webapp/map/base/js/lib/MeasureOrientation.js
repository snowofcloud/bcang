/**
 * <p>Description: 测方位构造函数</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-11 </p>
 * @author: 孙耀
 * @extends：{ol.Control.Control}
 * @param：{Object} (opt_options),map（必选参数）
*/
var map;
HthxMap.MeasureOrientation = function(opt_options){
	var options = opt_options || {};
	map = options.map;
	var target = options.target;
	var displayClass = options.displayClass;
	var featureLayer = HthxMap.getLayerById("featureLayer");
	//创建控件位置节点
//	$("#toolbar").append('<div id="measureOrientation"></div>');
	var button = document.createElement('button');
	button.title = "方位";
	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element: element,
		target: options.target
	});

	button.addEventListener('click',activateDraw,false);
	HthxMap.measureOrientation = new measureOrientation.tool.PlotDraw(map);
	HthxMap.measureOrientation.on(measureOrientation.event.PlotDrawEvent.DRAW_END, onDrawEnd, false, this);
	
	function activateDraw(){
        //移除地图绑定事件
		HthxMap.removeMapEvents(map);
		if(!HthxMap.curFeatureInteraction){
			HthxMap.curFeatureInteraction = "active";
		}
		//移除地图双击放大事件
		var interactionLen = map.getInteractions().getLength();
		for(var i = 0; i < interactionLen; i++){
			var tempItem = map.getInteractions().item(i);
        	if(tempItem instanceof ol.interaction.DoubleClickZoom){
        		map.removeInteraction(tempItem);
        		break;
        	}
        }
		initTargetClass();
		//HthxMap.plotObject.activate(plot.PlotTypes.Orientation);
		HthxMap.measureOrientation.activate(measureOrientation.PlotTypes.Orientation);
		HthxMap.mouseUnbindEvent(map, HthxMap.Settings.IMG.orientation);
	}
    
	//测量方位结束
	function onDrawEnd(event){
	    var source = featureLayer.getSource();
	    var feature = event.feature;
	    feature.setStyle(new ol.style.Style({
	    	stroke:new ol.style.Stroke({color: '#0061D9', width:1.1}),
	    	fill:new ol.style.Fill({color: 'rgba(0,97,217,0.2)'})
	    }));
	    source.addFeature(feature);

		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
//	    feature.set("clear", true);
	    feature.setId(HthxMap.measureIndex);
        var coor = feature.getGeometry().getLastCoordinate();
//        HthxMap.createDeleteElement(coor, HthxMap.measureIndex);
        var strs = '<div style="cursor:pointer;margin-left:48%;" onclick="HthxMap.clearMeasure('  + HthxMap.measureIndex + ')">x</div>';
        $(orientationTooltipElement.parentNode).append(strs);
        orientationTooltipElement = null;
        HthxMap.measureIndex++;
        
		HthxMap.mouseBindEvent(map);
		
		//添加鼠标双击放大事件
        setTimeout(function(){
	        map.once('pointermove',doubleClickZoom);
        },500);
		setTimeout(function(){
    		HthxMap.curFeatureInteraction = null;
    	},300);
		//map.once('pointermove',doubleClick);
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
}
ol.inherits(HthxMap.MeasureOrientation, ol.control.Control);

goog.provide('measureOrientation.a');
goog.provide('measureOrientation.PlotTypes');
goog.provide('measureOrientation.p.p');
measureOrientation.a = {
    TWO_PI: Math.PI * 2,
    HALF_PI: Math.PI / 2,
    FITTING_COUNT: 100,
    ZERO_TOLERANCE: 0.0001
};
measureOrientation.PlotTypes = {
    Orientation: 'orientation'
};

//构造函数，返回鼠标点击时点的坐标
measureOrientation.p.p = function (pt) {
    this.fa(pt); //measureOrientation.p.p.prototype.fa
};

//鼠标点击时捕获坐标点
measureOrientation.p.p.prototype.fa = function (pt) {
    this.p = pt ? pt : [];  //定义坐标点
    if (this.p.length >= 2) {
        this.fb(); // measureOrientation.p.cn.prototype.fb,鼠标移动时开始作图
    }
};
//与坐标点有关
measureOrientation.p.p.prototype.fc = function () {
    return this.p.slice(0); //返回一个表示点集合的数组
};
 //返回点的个数
measureOrientation.p.p.prototype.fd = function () {
    return this.p.length;
};
measureOrientation.p.p.prototype.fe = function (pt, len) {
    if (len >= 0 && len < this.p.length) {
        this.p[len] = pt;
        this.fb();
    }
};
measureOrientation.p.p.prototype.ff = function (pt) {
    this.fe(pt, this.p.length - 1);
};

measureOrientation.p.p.prototype.fb = function () {
};

goog.provide('measureOrientation.util');
goog.require('measureOrientation.a');
//计算两点间的距离
measureOrientation.util.fa = function (pt1, pt2) {
    return Math.sqrt(Math.pow((pt1[0] - pt2[0]), 2) + Math.pow((pt1[1] - pt2[1]), 2));
};

//计算偏移的角度
measureOrientation.util.fg = function (pt1, pt2) {
    var result;
    var ang = Math.asin(Math.abs(pt2[1] - pt1[1]) / measureOrientation.util.fa(pt1, pt2));
    if (pt2[1] >= pt1[1] && pt2[0] >= pt1[0]) {
        result = ang + Math.PI;
    } else {
        if (pt2[1] >= pt1[1] && pt2[0] < pt1[0]) {
            result = measureOrientation.a.TWO_PI - ang;
        } else {
            if (pt2[1] < pt1[1] && pt2[0] < pt1[0]) {
                result = ang;
            } else {
                if (pt2[1] < pt1[1] && pt2[0] >= pt1[0]) {
                    result = Math.PI - ang;
                }
            }
        }
    }
    return result;
};

//计算箭头的坐标
measureOrientation.util.fl = function (pt1, pt2, ang, len, bl) {
    var offsetAng = measureOrientation.util.fg(pt1, pt2);
    var resuleAng = bl ? offsetAng + ang : offsetAng - ang;
    var dx = len * Math.cos(resuleAng);
    var dy = len * Math.sin(resuleAng);
    return [pt2[0] + dx,pt2[1] + dy];
};

//画直线箭头
goog.provide('measureOrientation.p.cn');
goog.require('measureOrientation.p.p');
goog.require('measureOrientation.util');
goog.require('ol.geom.LineString');
//构造函数
measureOrientation.p.cn = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 2;
    this.maxArrowLength = 3000000;
    this.arrowLengthScale = 5;
    this.fa(pt);  //调用measureOrientation.p.p.prototype.fa
    //标绘覆盖物 ol.Overlay
    this.orientationTooltipElement = null;
    this.orientationTooltip = null;
};
goog.inherits(measureOrientation.p.cn, ol.geom.LineString);
goog.mixin(measureOrientation.p.cn.prototype, measureOrientation.p.p.prototype);
//作图算法,鼠标移动事件
measureOrientation.p.cn.prototype.fb = function () {
    if (this.fd() < 2) {   //调用measureOrientation.p.p.prototype.fd
        return;
    }
    var pnts = this.fc();  //调用measureOrientation.p.p.prototype.fc
    var pnt1 = pnts[0];
    var pnt2 = pnts[1];
    var len = measureOrientation.util.fa(pnt1, pnt2);
  //  var lent = len / this.arrowLengthScale;
    var zoom = map.getView().getZoom();
    var zoomLen = Math.pow(2,zoom);
    if(zoom != 0){
        if(len < zoom*16/zoomLen){
        	var lent = len / this.arrowLengthScale;
        	this.tempLen = lent;
        }else{
        	lent = this.tempLen;
        }
    }else{
    	lent = len / this.arrowLengthScale;
    }
    lent = lent > this.maxArrowLength ? this.maxArrowLength : lent;
    var arrowPt1 = measureOrientation.util.fl(pnt1, pnt2, Math.PI / 6, lent, false);
    var arrowPt2 = measureOrientation.util.fl(pnt1, pnt2, Math.PI / 6, lent, true);
    this.setCoordinates([pnt1,
    pnt2,
    arrowPt1,
    pnt2,
    arrowPt2]);

    //作为方位标记
    this.createOrientationTooltip();
    var orientation = this.getOrientation(pnt1,pnt2);
    var tooltipCoord = [(pnt1[0] + pnt2[0])/2,(pnt1[1] + pnt2[1])/2];
	this.orientationTooltipElement.innerHTML = orientation;
	this.orientationTooltip.setPosition(tooltipCoord);
};

measureOrientation.p.cn.prototype.getOrientation = function(pt1,pt2){
    var dx = pt2[0] - pt1[0];
    var dy = pt2[1] - pt1[1];
   	var piValue = Math.atan2(Math.abs(dy),Math.abs(dx));
   	var retGapT = piValue*(180/Math.PI);  //将弧度转换为角度
   //	var retGap = retGapT.toFixed(2);

   	//计算两点之间的距离
   	var wgsSphere = new ol.Sphere(6378137);
   	var sourceProj = map.getView().getProjection();
    var c1 = ol.proj.transform(pt1, sourceProj, 'EPSG:4326');
    var c2 = ol.proj.transform(pt2, sourceProj, 'EPSG:4326');
    var length = wgsSphere.haversineDistance(c1,c2);
    
   	//格式化两点间距离
    length = Math.round(length * 100) / 100;
    var output;   
    var distance;
    if (length > 100) {
    	distance = (Math.round(length / 1000 * 100) / 100);
    	output = distance/1.852 ; 
    	output = output.toFixed(3);
    	output +=  '海里';	
    	distance = distance + ' ' + "km";
	    
    } else {
    	distance = (Math.round(length * 100) / 100);   
    	output = distance/1852; 
    	output = output.toFixed(3);
    	output +=  '海里';
    	distance = distance + ' ' + 'm';
    }
    
    output = output  + '/' + distance + ')';    
    var orientation;
    var retGap;
   	if(dx >= 0 && dy >= 0){
   		if(dx == 0 && dy == 0){
   			orientation = "开始测量方位";
   		}else if(dx == 0 && dy > 0){
   			orientation = "(正北方向" + " " + output;
   		}else if(dx > 0 && dy == 0){
   			orientation = "正东方向" + " " + output;
   		}else if(dx > 0 && dy > 0){
   			retGap = (90 - retGapT).toFixed(2);
   			orientation = retGap + "度" + "(东北方向" + " " +output;
   		}
   	}else if(dx >= 0 && dy < 0){
   		if(dx == 0){
   			orientation = "正南方向" + " " + output;
   		}else{
   			retGap = (retGapT + 90).toFixed(2);
		   	orientation = retGap + "度" + "(东南方向" + " " +output;
   		}
   	}else if(dx < 0 && dy >= 0){
   		if(dy == 0){
   			orientation = "正西方向" + " " + output;
   		}else if(dx < 0 && dy > 0){
   			retGap = (retGapT + 270).toFixed(2);
	   		orientation = retGap + "度" + "(西北方向" + " " +output;
   		}
   	}else if (dx < 0 && dy < 0){
   		    retGap = (270 - retGapT).toFixed(2);
   		    orientation = retGap + "度" + "(西南方向" + " " +output;
   	}
    
    return orientation;
}

measureOrientation.p.cn.prototype.createOrientationTooltip = function(){
    if (this.orientationTooltipElement) {
    	this.orientationTooltipElement.parentNode.removeChild(this.orientationTooltipElement);
    }
    this.orientationTooltipElement = document.createElement('div');
    this.orientationTooltipElement.className = 'mtooltip mtooltip-static';
    orientationTooltipElement = this.orientationTooltipElement;
    this.orientationTooltip = new ol.Overlay({
	    element: this.orientationTooltipElement,
	    offset: [0, -15],
	    positioning: 'bottom-center'
    });
    this.orientationTooltip.setProperties({id: HthxMap.measureIndex});
    map.addOverlay(this.orientationTooltip);
}

goog.provide('measureOrientation.Object');
goog.require('measureOrientation.PlotTypes');
goog.require('measureOrientation.p.cn');

//绘图函数 2
measureOrientation.Object.createObject = function (type, pt) {
    switch (type) {
	    case measureOrientation.PlotTypes.Orientation:
	        return new measureOrientation.p.cn(pt);
    }
    return null;
}

goog.provide('measureOrientation.event.PlotDrawEvent');
goog.provide('measureOrientation.tool.PlotDraw');
goog.require('goog.events.Event');
goog.require('ol.Map');
goog.require('ol.Observable');
goog.require('ol.Feature');
goog.require('ol.geom.Point');
goog.require('ol.layer.Vector');
goog.require('ol.style.Stroke');
goog.require('ol.style.Fill');
goog.require('ol.style.Style');
goog.require('ol.Collection');
goog.require('ol.interaction.DoubleClickZoom');
goog.require('measureOrientation.PlotTypes');
goog.require('measureOrientation.Object');
measureOrientation.event.PlotDrawEvent = function (type, feature) {
    goog.base(this, type);
    this.feature = feature;
};
goog.inherits(measureOrientation.event.PlotDrawEvent, goog.events.Event);
measureOrientation.event.PlotDrawEvent.DRAW_START = 'draw_start';
measureOrientation.event.PlotDrawEvent.DRAW_END = 'draw_end';

//标绘构造函数
measureOrientation.tool.PlotDraw = function (map) {
    goog.base(this);
    this.p = null;
    this.plot = null;
    this.feature = null;
    this.plotType = null;
    this.plotParams = null;
    this.mapViewport = null;
    this.aa = null; //interaction.DoubleClickZoom
    var stroke = new ol.style.Stroke({
        color: '#000000',
        width: 1.25
    });
    var fill = new ol.style.Fill({
        color: 'rgba(0,0,0,0.4)'
    });
    this.style = new ol.style.Style({
        fill: fill,
        stroke: stroke
    });
    this.featureOverlay = new ol.layer.Vector({
    	source: new ol.source.Vector(),
    	style: this.style
    });
//    this.featureOverlay.setStyle(this.style);
    this.setMap(map);
};
goog.inherits(measureOrientation.tool.PlotDraw, ol.Observable);

//////////draw start ,step 1 （绘图入口函数）
measureOrientation.tool.PlotDraw.prototype.activate = function (type, plotParam) {
    this.deactivate();  //step 2
    this.removeDbClickZoom();   //step 5
    map.on('click', this.beginDraw, this);
    this.plotType = type;
    this.plotParams = plotParam;
    this.map.addLayer(this.featureOverlay);
};
//step 2
measureOrientation.tool.PlotDraw.prototype.deactivate = function () {
    this.disconnectEventHandlers(); //step 3
    this.map.removeLayer(this.featureOverlay);  //this.ab,类型 ol.FeatureOverlay
    this.p = [
    ];
    this.plot = null;
    this.feature = null;
   // this.removeDbClickZoomInteraction();  //step 4
};
measureOrientation.tool.PlotDraw.prototype.setMap = function (map) {
    this.map = map;
    this.mapViewport = this.map.getViewport();
};

//绘图函数 1
measureOrientation.tool.PlotDraw.prototype.beginDraw = function (e) {
    this.p.push(e.coordinate);
    this.plot = measureOrientation.Object.createObject(this.plotType, this.p, this.plotParams);  //绘图函数 2 （绘图构造函数）
    this.feature = new ol.Feature(this.plot);
    this.featureOverlay.getSource().addFeature(this.feature);  //类型 ol.FeatureOverlay();
    this.map.un('click', this.beginDraw, this);
    this.map.on('click', this.continueDraw, this);
    this.map.on('dblclick', this.finishDraw, this);
   // goog.events.listen(this.mapViewport, goog.events.EventType.MOUSEMOVE, this.fb, false, this);
    this.map.on('pointermove', this.drawing, this); 
};

//绘图方法
measureOrientation.tool.PlotDraw.prototype.drawing = function (e) {
   // var coordinate = map.getCoordinateFromPixel([e.clientX,e.clientY]);
    if (measureOrientation.util.fa(e.coordinate, this.p[this.p.length - 1]) < measureOrientation.a.ZERO_TOLERANCE) {
        return;
    }
    var pnts = this.p.concat([e.coordinate]);
    this.plot.fa(pnts); //调用measureOrientation.p.p.fa
};
measureOrientation.tool.PlotDraw.prototype.continueDraw = function (e) {
    if (measureOrientation.util.fa(e.coordinate, this.p[this.p.length - 1]) < measureOrientation.a.ZERO_TOLERANCE) {
        return;
    }
    this.p.push(e.coordinate);
    this.plot.fa(this.p);
    if (this.plot.fixPointCount == this.plot.fd()) {
        this.finishDraw(e);
    }
};
measureOrientation.tool.PlotDraw.prototype.finishDraw = function (e) {
	this.removeDbClickZoom();
    this.disconnectEventHandlers();
    e.preventDefault();
    this.removeFeature();
};
//step 3
measureOrientation.tool.PlotDraw.prototype.disconnectEventHandlers = function () {
    this.map.un('click', this.beginDraw, this);
    this.map.un('click', this.continueDraw, this);
   // goog.events.unlisten(this.mapViewport, goog.events.EventType.MOUSEMOVE, this.fb, false, this);
    this.map.un('pointermove', this.drawing, this);
    this.map.un('dblclick', this.finishDraw, this);
};

//标绘结束处理
measureOrientation.tool.PlotDraw.prototype.removeFeature = function () {
    this.dispatchEvent(new measureOrientation.event.PlotDrawEvent(measureOrientation.event.PlotDrawEvent.DRAW_END, this.feature));
    this.featureOverlay.getSource().removeFeature(this.feature);
  //  this.removeDbClickZoomInteraction();
    this.disconnectEventHandlers();
    this.map.removeLayer(this.featureOverlay);
    this.p = [
    ];
    this.plot = null;
    this.feature = null;
};

//从交互集合中移除ol.interaction.DoubleClickZoom事件并将ol.interaction.DoubleClickZoom保存到this.aa变量中
measureOrientation.tool.PlotDraw.prototype.removeDbClickZoom = function () {
	var interactions = map.getInteractions();
	var interactionLen = interactions.getLength();;
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
};
//移除ol.interaction.DoubleClickZoom事件
/*
measureOrientation.tool.PlotDraw.prototype.removeDbClickZoomInteraction = function () {
    if (this.aa != null) {
        map.getInteractions().push(this.aa);
        this.aa = null;
    }
};*/