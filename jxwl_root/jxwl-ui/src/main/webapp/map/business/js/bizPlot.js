var plotEdit = {
		//保存标会
		save: function(pointSet, style, index){
			$.ajax({
				type:'POST',
				url: HthxMap.Settings.rest + "plotting/save",
				data:{"pointSet": JSON.stringify(pointSet), "style":JSON.stringify(style)},
				success:function(obj){		
					if(obj['code'] === 1){
						bizCommonSetting.plotId[index] = obj['data'];
					}
				}
			})
		},
		
		//删除标绘
		remove: function(index){
			$.ajax({
				type:'POST',
				url: HthxMap.Settings.rest + "plotting/delete",
				data:{"id": bizCommonSetting.plotId[index]},
				success:function(obj){		
					if(obj['code'] === 1){	
						bizCommonSetting.plotId[index] = null;
					}
				}
			})
		},
        //获取标绘
		getAll: function(){
			$.ajax({
				type:'POST',
				url: HthxMap.Settings.rest+"plotting/findByDept",
				success:function(obj){		
					if(obj['code'] === 1){
						var data = obj['data'];
						if(data){
							for(var i = 0; i < data.length; i++){
								var interiorPoint;
								bizCommonSetting.plotId[HthxMap.measureIndex] = data[i].id;
								var style = JSON.parse(data[i].style);
								var points = JSON.parse(data[i].pointSet);
								if(points.length === 1){ //非多边形  
									interiorPoint = HthxMap.drawLineString(points, creatStyle.plot(style[0], style[1]));
								}else{    //多边形
									interiorPoint = HthxMap.drawMultiPolygon(points, creatStyle.plot(style[0], style[1]));
								}							
								HthxMap.createDeleteElement(interiorPoint, HthxMap.measureIndex);
						        HthxMap.measureIndex++;
							}
						}
					}
				}
			})
		}
}
