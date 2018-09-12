/**
 * <p>Description: 地图上需要设置的样式对象</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-12-24 </p>
 * @author: hezp
 * @param: 
 * @return 
 */
var creatStyle = {
		//标记图形
		markerDrawStyle: function(geometry){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(0,97,217,0.2)'
				}),
				stroke:new ol.style.Stroke({
					color: '#0061D9', 
					width:1.1
				})
			})
			return style;
		},
		
		//电子围栏填充样式
		electDrawStyle: function(geometry){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(126, 94, 44, 0.2)'
				}),
				stroke:new ol.style.Stroke({
					color: ' #FF6633', 
					width:3
				})
			})
			return style;
		},
		
		routeDrawStyle: function(geometry){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(126, 94, 44, 0.2)'
				}),
				stroke:new ol.style.Stroke({
					color: ' #00FF00', 
					width:8
				})
			})
			return style;
		},
		
		userDefinedStyle: function(){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(16, 87, 143,0.6)'
				}),
				stroke:new ol.style.Stroke({
					color: 'rgba(120, 30, 226,0.9)', 
					width:3
				})
			})
			return style;
		},		
		userClosetypeStyle: function(){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(16, 87, 143,0)'
				}),
				stroke:new ol.style.Stroke({
					color: '#EF7A00', 
					width:3
				})
			})
			return style;
		},
		userClosetypeStyle2: function(){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(16, 87, 143,0)'
				}),
				stroke:new ol.style.Stroke({
					color: '#319FD3', 
					width:3
				})
			})
			return style;
		},
		//绘图层样式
		interactionDraw: function(geometry){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color:'rgba(255,255,255,0.2)'
				}),
				stroke:new ol.style.Stroke({
					color: 'rgba(0, 0, 0, 0.5)',
			        lineDash: [10, 10],
			        width: 2
				})
			})
			return style;
		},
		
		//场景重现层样式
		areaTrack: function(geometry){
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: 'rgba(98, 98, 98, 0.8)'
				}),
				stroke:new ol.style.Stroke({
					color: 'rgba(0, 0, 0, 0.5)', 
					width:2
				})
			})
			return style;
		},
		//标绘样式
		plot: function(stroke, fill){    //分支
			var geometry = null || geometry;
			var style = new ol.style.Style({
				geometry: geometry,
				fill:new ol.style.Fill({
					color: fill
				}),
				stroke:new ol.style.Stroke({
					color: stroke, 
					width:2
				})
			})
			return style;
		}
};

/**
 * 电子围栏填充颜色
 * 
*/
var creatElectStyle = {
		
};