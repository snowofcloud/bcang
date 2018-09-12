//保存标记
function markerSave(name,coordinate1,markerId,icon,overlay){
	//var id = HthxMap.markerFeatureId[markerId];    //修改使用
	//保存、更新标示  
    var _saveFlag = false;
    //经纬度分别保存
    var longitude = coordinate1[0];
    var latitude = coordinate1[1];
    //注标上是否有存在id
    var overlayIdd = $("#"+markerId).attr("ovelayId");
    if(markerId.indexOf("marker")>=0&&!overlayIdd){//存在id则编辑
    	_saveFlag = true;
    }else if(!(markerId.indexOf("marker")>=0)){
    	overlayIdd = markerId;
    }
	$.ajax({
		type:'POST',
		url: _saveFlag?userDefinedFacilityFind.urlPath.saveDefinedFacilityUrl:userDefinedFacilityFind.urlPath.updateDefinedFacilityUrl,
		data:{"id":overlayIdd,"facilityName": name,"points":JSON.stringify(coordinate1),"iconId":icon, "longitude":longitude, "latitude":latitude},
		success:function(obj){		
			if(obj['code'] === 1){
				//清除popup的内容
				overlay.setPosition(undefined);
				HthxMap.markerId[markerId] = obj['data'];
				var marId = HthxMap.markerId[markerId];
				$("#"+markerId).attr("ovelayId",marId);
				$("#"+markerId+" .markerNameok").html(name);
				$('#popup-closer').blur();
			}else{
				Messager.alert({Msg:obj["msg"], isModal: true}); 
			}
		}
	});
};
//删除标记
function markerRemove(markerId){
	var id = $("#"+markerId).attr("ovelayId");
	if(id){ 
		$.ajax({
			type:'POST',
			url: userDefinedFacilityFind.urlPath.deleteDefinedFacilityUrl + id,
			data:{"id": id},
			success:function(obj){		
				if(obj['code'] === 1){
					HthxMap.markerId[markerId] = null;
				
				}else{
					Messager.alert({Msg:obj["msg"], isModal: true}); 
				}
			}
		});
	}
}