/**
 * <p>Description: 空间查询</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-07-14 </p>
 * @author: 张琴
 * @param：map(全局地图，必选),featureLayer(标绘图层，必选),layers(查询图层,必选)
*/
function queryByArea(map,featureLayer,layers){
	//地图形状
	var typeSelect = document.getElementById('queryAreaType');
	//是否添加缓冲区
	var check = document.getElementById("isCache");
	var cache = document.getElementById("cacheDistance");
	check.onclick = function(){
		//如果添加缓冲区的复选框选中，则允许输入缓冲区距离，否则，缓冲区距离输入框不可输入
		if(this.checked){
			cache.disabled = false;
		}else{
			cache.disabled = true;
		}
	}
	/**
	 * 改变选择地理形状
	 * @param 下拉框change事件.
	 */
	typeSelect.onchange = function(e) {
		//移除地图事件
		HthxMap.removeMapEvents(map);	
		addInteraction();
	};
	var source = featureLayer.getSource();

	function addInteraction() {
		var draw;
		var value = typeSelect.value;
		if (value !== 'None') {
			draw = new ol.interaction.Draw({
				source : source,
				type : (value)
			});
			map.addInteraction(draw);
		}
		HthxMap.curMapInteraction = draw;
		
		draw.on('drawend',function(e){
			var cacheDistance = 0;
			if(check.checked){
				cacheDistance = cache.value;
			}
			map.removeInteraction(draw);
			var geom = e.feature.getGeometry();
			var bufferUtil = new HthxMap.BufferUtil();
			var fea = bufferUtil.getBuffer(geom, cacheDistance,value);
			featureLayer.getSource().addFeature(fea);
			geom = fea.getGeomtry();
			//HthxMap.Functions.queryByArea1(map, geom, value, cacheDistance,layers,featureLayer);
			
		})
	}
}