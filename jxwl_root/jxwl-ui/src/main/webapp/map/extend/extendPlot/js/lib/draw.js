/**
 * <p>Description:标绘面板</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-09 </p>
 * @author: 孙耀
 * @extends：{ol.control.Control}
 * @param：{Object=} opt_options Control options.
*/

HthxMap.Draw = function(opt_options){
	var options = opt_options || {};
	var map = options.map;
	var displayClass = options.displayClass || "draw";
	var target = options.target || "draw";
	var featureLayer = HthxMap.getLayerById("featureLayer");
	var strokeColor, fillColor;
	//创建控件位置节点
	$("#toolbar").append('<div id="'+target+'"></div>');

	var button = document.createElement('button');
	button.title = "标绘";
	var element = document.createElement('div');
	element.className = displayClass + 'Inactivate ol-unselectable';
	element.appendChild(button);
	$("#draw").append('<div style="height: 0px;"></div>');
	ol.control.Control.call(this, {
		element: element,
		target: options.target
	});
	
	button.addEventListener('click', includeScript, false);
	//点击后增加js文件
	function includeScript(){
		var baseUrl = "map/extend/extendPlot/js/lib/";
		var scriptArray = ["commonPlot.js"];
		scriptArray = HthxMap.include.getScriptArray(baseUrl, scriptArray);
		HthxMap.include.script(scriptArray, handle);
	}
	//第一次加载执行
	function init(){
		if(!$(".class_plot").length){
			button.removeEventListener('click', includeScript);
			button.addEventListener('click', handle, false);
			HthxMap.include.linkStyle(["map/extend/extendPlot/css/extendPlot.css"]);
			//创建标绘位置节点
			$("body").append('<div class="class_plot" style="display: none;">'+			
					'<div id="toolPan" class="toolPan">'+
						'<div class="arrow"></div>'+
		                '<span>线条色</span>'+
		                '<input id="featureStroke" type="text"></input>'+
		                '<span>填充色</span>'+
		                '<input id="featureFill" type="text"></input>'+
		           '</div>'+
		           '<div id="plotLine" class="plotLine"></div>'+
		           '<div id="plotCurve" class="plotCurve"></div>'+
		           '<div id="plotARC" class="plotARC"></div>'+
		           '<div id="plotPolygon" class="plotPolygon"></div>'+
		           '<div id="plotRegularPolygon" class="plotRegularPolygon"></div>'+
		           '<div id="plotEllipse" class="plotEllipse"></div>'+
		           '<div id="plotCircle" class="plotCircle"></div>'+
		           '<div id="plotClosedCurve" class="plotClosedCurve"></div>'+
		           '<div id="plotLune" class="plotLune"></div>'+	
		           '<div id="plotStraightArrow" class="plotStraightArrow"></div>'+
		           '<div id="plotLineArrow" class="plotLineArrow"></div>'+
		           '<div id="plotAssaultDirection" class="plotAssaultDirection"></div>'+
		           '<div id="plotDoubleArrow" class="plotDoubleArrow"></div>'+
			       '<div id="plotAttackArrow" class="plotAttackArrow"></div>'+
		           '<div id="plotTailAttackArrow" class="plotTailAttackArrow"></div>'+
		           '<div id="plotSquadCombat" class="plotSquadCombat"></div>'+
		           '<div id="plotTailSquadCombat" class="plotTailSquadCombat"></div>'+
				'</div>');
		    //初始化标绘对象
		    HthxMap.plotObject = new plot.tool.PlotDraw(map);   //保存当前标绘对象，全局唯一
			HthxMap.plotObject.on(plot.event.PlotDrawEvent.DRAW_END, onDrawEnd, false, this);
			
			//创建标绘控件
			//画直线
		    var plotLine = new HthxMap.PlotLine({
		    	displayClass:'plotLine',
		    	target:'plotLine',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotLine);
		    
		    //画直箭头
		    var plotStraightArrow = new HthxMap.PlotStraightArrow({
		    	displayClass:'plotStraightArrow',
		    	target:'plotStraightArrow',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotStraightArrow);
		    //画曲线
		    var plotCurve = new HthxMap.PlotCurve({
		    	displayClass:'plotCurve',
		    	target:'plotCurve',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotCurve);
		    //画弧线
		    var plotARC = new HthxMap.PlotARC({
		    	displayClass:'plotARC',
		    	target:'plotARC',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotARC);
		    //画正方形
		    var plotRegularPolygon = new HthxMap.PlotRegularPolygon({
		    	displayClass:'plotRegularPolygon',
		    	target:'plotRegularPolygon',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotRegularPolygon);
		    //画多边形
		    var plotPolygon = new HthxMap.PlotPolygon({
		    	displayClass:'plotPolygon',
		    	target:'plotPolygon',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotPolygon);
		    //画圆
		    var plotCircle = new HthxMap.PlotCircle({
		    	displayClass:'plotCircle',
		    	target:'plotCircle',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotCircle);
		    //画椭圆
		    var plotEllipse = new HthxMap.PlotEllipse({
		    	displayClass:'plotEllipse',
		    	target:'plotEllipse',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotEllipse);
		    //画封闭曲线
		    var plotClosedCurve = new HthxMap.PlotClosedCurve({
		    	displayClass:'plotClosedCurve',
		    	target:'plotClosedCurve',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotClosedCurve);
		    //画拱形
		    var plotLune = new HthxMap.PlotLune({
		    	displayClass:'plotLune',
		    	target:'plotLune',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotLune);
		    //画直线箭头
		    var plotLineArrow = new HthxMap.PlotLineArrow({
		    	displayClass:'plotLineArrow',
		    	target:'plotLineArrow',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotLineArrow);
		    //突击方向
		    var plotAssaultDirection = new HthxMap.PlotAssaultDirection({
		    	displayClass:'plotAssaultDirection',
		    	target:'plotAssaultDirection',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotAssaultDirection);
		    //钳击
		    var plotDoubleArrow = new HthxMap.PlotDoubleArrow({
		    	displayClass:'plotDoubleArrow',
		    	target:'plotDoubleArrow',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotDoubleArrow);
		    //攻击方向
		    var plotAttackArrow = new HthxMap.PlotAttackArrow({
		    	displayClass:'plotAttackArrow',
		    	target:'plotAttackArrow',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotAttackArrow);
		    //攻击方向(尾)
		    var plotTailAttackArrow = new HthxMap.PlotTailAttackArrow({
		    	displayClass:'plotTailAttackArrow',
		    	target:'plotTailAttackArrow',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotTailAttackArrow);
		    //分战斗队形行动
		    var plotSquadCombat = new HthxMap.PlotSquadCombat({
		    	displayClass:'plotSquadCombat',
		    	target:'plotSquadCombat',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotSquadCombat);
		    //分战斗队形行动(尾)
		    var plotTailSquadCombat = new HthxMap.PlotTailSquadCombat({
		    	displayClass:'plotTailSquadCombat',
		    	target:'plotTailSquadCombat',
		    	map:map,
		    	layer:featureLayer
		    });
		    map.addControl(plotTailSquadCombat);
			//添加颜色插件
			strokeColor = "#cc6600";
			fillColor = "rgba(225,225,0,0.4)";
			$('.class_plot input[type="text"]').each(function() {
				var _this = $(this);
				var id = $(this).attr("id");
				_this.colorpicker({
					success : function(color) {
						_this.css("background", color);
						//把rgb转化为rgba
						var colort = color.split("(")[1].split(")")[0];
						var color1 = "rgba("+colort+",0.4)"
						if (id == "featureStroke")
							strokeColor = color;
						else {
							fillColor = color1;
						}
						
						$("#colorpanel").hide();
					},
					map : map
				});
			});
		}
	}
	
	function handle(){
		init();
		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		if(HthxMap.panControl.target && HthxMap.panControl.target !== options.target){
			if($("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") && $("#" + HthxMap.panControl.target + " > div:nth(0)").css("display") === 'block'){
				$("#" + HthxMap.panControl.target + " > div:nth(0)").css("display",'none');
			}
			
		}
		//记录地图当前激活的控件
		HthxMap.panControl.target = options.target;
		// 添加标绘面板
        if($(".class_plot").css('display') == 'block'){
        	$(".class_plot").css('display',"none");
        }else if($(".class_plot").css('display') == 'none'){
        	var ttop  = $("#draw").offset().top;     //控件的定位点高
            var thei  = $("#draw").height();  //控件本身的高
            var twidth =  $("#draw").width();
            var tleft = $("#draw").offset().left;    //控件的定位点宽
            $(".class_plot").css({
                top:ttop + thei,
                left:tleft - 100,
                display: "block",
            })
        }
        $('.class_plot input[type="text"]').each(function() {
    		var id = $(this).attr("id");
    		if (id == "featureStroke"){
    			$(this).css("background",strokeColor);
    		}   			
    		else {
    			$(this).css("background",fillColor);
    		}
        })
        if($(".class_plot").css("display") == "none"){       	
        	$("#colorpanel").hide();
        }
	}
	
	function onDrawEnd(event){
	    var source = featureLayer.getSource();
	    var feature = event.feature;
	    feature.setStyle(new ol.style.Style({
	    	stroke:new ol.style.Stroke({color: strokeColor, width:2}),
	    	fill: new ol.style.Fill({color: fillColor})
	    }));
	    source.addFeature(feature);
	  
	    feature.setId(HthxMap.measureIndex);
	    var coordinates = feature.getGeometry().getCoordinates();  //分支
	    if(coordinates.length === 1){    //多边形
	    	plotEdit.save(coordinates[0], [strokeColor, fillColor], HthxMap.measureIndex);
	    }else if(coordinates.length !== 1){  //
	    	plotEdit.save([coordinates], [strokeColor, fillColor], HthxMap.measureIndex);
	    }
	    	    
        var coor = feature.getGeometry().getLastCoordinate();
        HthxMap.createDeleteElement(coor, HthxMap.measureIndex);
        HthxMap.measureIndex++;
        
		//先清除当前激活控件样式，再添加即将要激活的控件样式
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
		
		HthxMap.mouseBindEvent(map);
		
		//添加鼠标双击放大事件
        setTimeout(function(){
	        map.once('pointermove',doubleClickZoom);
        },500);
		setTimeout(function(){
    		HthxMap.curFeatureInteraction = null;
    	},300);
	}
	
	function doubleClickZoom(){
		map.addInteraction(new ol.interaction.DoubleClickZoom);
	}
};
ol.inherits(HthxMap.Draw,ol.control.Control);