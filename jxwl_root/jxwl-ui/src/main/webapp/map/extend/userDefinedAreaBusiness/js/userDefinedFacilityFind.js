/**
 * @description: 自定义设施 ---删除
 *  * @author: wl
 * @time: 2016-08-02
 */
var userDefinedFacilityFind = (function ($) {
    //模块缓存变量
	var $saveDefinedFacilityForm = $("#save-userDefinedFacility-form");
	var $addUserDefinedFacilityPanel = $("#addUserDefinedFacility-panel");
	var $cancel = $("#addUserDefinedFacility-panel .panel-close");
	var $cancelBtn = $("#addUserDefinedFacility-panel .cancel");
	var icon = null;
	var markerId = null;
	var changeIcon = null;
	var overlay = null;
	var content = null;
	var closer = null;
	var iconIndex = null;
    //请求路径
    var _urlPath = {
        findDefinedFacilityUrl: $.backPath + '/userDefinedFacility/findByPage',
        showDefinedFacilityUrl:$.backPath + '/userDefinedFacility/findByUserDefinedFacilityId/',
    	deleteDefinedFacilityUrl:$.backPath + '/userDefinedFacility/delUserDefinedFacility/',
    	detailDefinedFacilityUrl:$.backPath + '/userDefinedFacility/findByUserDefinedFacilityId/',
    	updateDefinedFacilityUrl:$.backPath + '/userDefinedFacility/update/',
    	saveDefinedFacilityUrl:$.backPath + '/userDefinedFacility/saveFacility'
    };
    //数据初始化
    var _init = function () {
    	$cancel.unbind("click").bind("click",_closeDefinedFacility);
        $cancelBtn.unbind("click").bind("click",_closeDefinedFacility);
        
        /*$("#save-userDefinedFacility-form").html5Validate(function (e) {
        	_submitElecRailToDB();
            return false;
    	});
        $("#markerFacilityOK").bind("click",_submitElecRailToDB());*/
    };
    //保存、更新标示  
    var _saveFlag = true; 
    var tempId="";
    var _isOpenOrClose =  true;        //默认true为开启，否false为取消常用联系人
    var tempPointList ="";//临时点数据---编辑限制设施用
	
    //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
	//显示限制设施
	var _setElectRail = function() {
		var definedFacilityMsg = null;
		$.ajax({
		        url:  $.backPath  + "/userDefinedFacility/findUserDefinedFacility",
		        dataType: 'json',
		        type: "GET",
		        async: false,
		        success: function (json) {
		     	   if (json["code"] === 1) {
		     		  definedFacilityMsg = json.data;
		     		 /*一条一条填充电子围栏*/
		     	    	$.each(definedFacilityMsg, function(index, electSet) {
		     	    			if(electSet.pointList != "[]"){
		     	    				/*围栏为打开状态--填充围栏到地图中*/
		     	    				var tmpPoint = JSON.parse(electSet.points);
		     	           		    var len = tmpPoint.length;
		     	           		    var coor;
		     	           		    if((electSet.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
		     	           		        coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
		     	           		    }else{
		     	           		        coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle()); //多边形
		     	           		    }
		     	           		    createElecNameUserDefined(electSet.areaName, coor, HthxMap.measureIndex, electSet.id);
					     			HthxMap.areaDefinedFacilityIndex[electSet.id] = HthxMap.measureIndex;
					     			HthxMap.areaDefinedFacilityRail[HthxMap.measureIndex] = electSet.id;    //用于区分标绘和测量
					     			HthxMap.measureIndex++;
		     	    			}
		     			});
		            } else {
		            	Message.show({Msg: "读取自定义设施信息出错", isModal: false});
	    	    		return false;
		            }
		        }
		});
	};
	/**
	 * 自定义设施标注名称
	 */
	/*function createFacilityNameElement(name,coor,index,id) {
		var deleteElement = document.createElement('div');
		var strs = '<div class="facilityTitleDiv"><label class="restrictDetail" onclick="userDefinedFacilityFind.showDetail(\''+id+'\')" id="'+index+'">'+name+'</label>'+
		'<div class="areaName openDefinedAreaFlag closered" id="'+id+'" onclick="markerRemove('+ "'" + id +"'" +')">x</div></div>';
		deleteElement.innerHTML = strs;
	    var deleteE = new ol.Overlay({
		    element: deleteElement,
		    offset: [10,0],
		    position: coor,
		    positioning: 'center-center'
	    });
//	    deleteE.setId(id);
	    deleteE.setProperties({id: index});
	    map.addOverlay(deleteE);
	};*/
	//显示设施限制详情
	var showDetail = function(id){
		var rowObj = $.getData(_urlPath.detailDefinedFacilityUrl + arguments[0]);
		var name = rowObj.facilityName;
		var coordinate1 = rowObj.points;
		markerId = rowObj.id;
		icon = rowObj.iconId;
		if (document.getElementById("popup") == null) {
			$("body").append('<div id="popup" class="ol-popup" style="z-index:1"><a href="#" id="popup-closer" class="ol-popup-closer"></a><div id="popup-content"></div></div>');
		}
		var oreid = $("#"+markerId).attr("ovelayId");
		var container = document.getElementById('popup');
		content = document.getElementById('popup-content');
		closer = document.getElementById('popup-closer');
		var markers = HthxMap.Settings.IMG.facility;
		var markertitle = HthxMap.Settings.IMG.facilityName;
		var contentStr = "";
		var markerlist = '';
		var coordinate = [rowObj.longitude,rowObj.latitude];
		for(var i=0; i< markers.length; i++){
			var pngUrl = markers[i];
			markerlist += '<li class="markerIconList"><img title="'+markertitle[i]+'" src="'+pngUrl+'"/></li>';
		}
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
		strArr.push('<input type="hidden" id="reaDefinedFacility-id" value="'+id+'">');
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
		content.innerHTML = contentStr;
		overlay = new ol.Overlay({
			element : container,
			autoPan : false,
			positioning: "bottom-center",
			autoPanAnimation : {
				duration : 250
			}
		});
		overlay.setPosition(coordinate);
		map.addOverlay(overlay);
		closer.onclick = function() {
			overlay.setPosition(undefined);
		};
		
		//切换图标
    	var pngUrl;
		$("#markerlist").unbind("click").on("click", function(e){
			pngUrl = e.target.src;
			iconIndex = HthxMap.Settings.IMG.facility.indexOf(HthxMap.Settings.root+pngUrl.split(HthxMap.Settings.root)[1]);
			var id =$("#reaDefinedFacility-id").val();
			$(".userDefinedFacility_"+id).find("img").attr("src",pngUrl);
		});
		
		var $confirmBtn = $("#markerFacilityOK");
        $confirmBtn.unbind("click").bind("click",updateDefinedFacility);
		
		//删除标记修改
	    $("#markerCancel").click(function(){
	    	deleteMarker();
	    });
	    
	    function deleteMarker(){
	    	markerRemove(markerId);
	    	overlay.setPosition(undefined);
			closer.blur();
			//清除popup的内容
			/*content.innerHTML = "";
			//清除标记
			var overlays = map.getOverlays();
			var len = overlays.getLength();
			for (var i = 0; i < len; i++) {
				if (overlays.item(i).get("id") == markerId) {
					map.removeOverlay(overlays.item(i));
					break;
				} 
			}*/
		}
	    $("#convertDegrees").click();
	};
	//删除标记
	 var markerRemove = function(id){
		 Message.confirm({Msg:prompt.sureDelete, iconImg:'question', 'isModal': true}).on(function (flag) {
			 if(flag){    
				$.ajax({
					type:'POST',
					url: userDefinedFacilityFind.urlPath.deleteDefinedFacilityUrl + id,
					data:{"id": id},
					success:function(obj){		
						if(obj['code'] === 1){	
							HthxMap.markerId[id] = null;
							var overlays = map.getOverlays();
							var len = overlays.getLength();
							for (var i = 0; i < len; i++) {
								if (overlays.item(i).get("id") == id) {
									map.removeOverlay(overlays.item(i));
									break;
								} 
							}
							//closer.blur();
						}else{
							Messager.alert({Msg:obj["msg"], isModal: true}); 
						}
					}
				});
			 }
		 });
	};
	//关闭正在输入的限制设施
	var _closeDefinedFacility = function(){
		$addUserDefinedFacilityPanel.closePanelModel();
		var layer = HthxMap.getLayerById("featureLayer");
		var source = layer.getSource();
		if(preDrawFeature){
			source.removeFeature(preDrawFeature);
		}
	};
	
	//展示限制设施的弹框
	var _definedFacilityShowPanel = function (id,title) {
		$addUserDefinedFacilityPanel.saveOrCheck(true);
        var $this = $(id);
        var $title = $this.find(".panel-title");
        var $heading = $this.find(".panel-heading");
        //设置参数
        $title.html(title || '');
		$("body").append('<div class="panel-mask"></div>');
        /*可以拖动*/
        $this.draggable({handle: '.panel-heading', containment: 'html'});
        /*js原生态设置居中显示*/
        var top = document.documentElement.scrollTop;
        var left = document.documentElement.scrollLeft;
        var width = document.documentElement.clientWidth;
        var height = document.documentElement.clientHeight;
        $this.css({
            'top': top + (height - $this.height()) / 2 + 'px',
            'left': left + (width - $this.width()) / 2 + 'px',
            'right': 'auto',
            'width': $this.width() + 'px'
        });
        $this.fadeIn(300);
        //回调函数
        return this;
    };	
	
    //保存标记内容
    var updateDefinedFacility = function(){
    	var name = $("#reaDefinedFacility-name").val();
		if ("" === name) {
			Message.alert({Msg:"请输入自定义设施名称",iconImg:"warning",isModal: false});
			return;
		}
		var markerLon = $("#markerLon").val();
		var markerLat = $("#markerLat").val();
		var coordinate1; 
		if(markerLon.indexOf('°') !== -1){
			coordinate1 = bizCommonVariable.markerFloat;    //度分秒转换为浮点数
		}else{
			coordinate1 = [markerLon, markerLat];
		}
		coordinate1 = [JSON.parse(coordinate1[0]), JSON.parse(coordinate1[1])];  //单个标记的时候可以修改
		if (iconIndex != null) {
			var iconIndexinput = iconIndex;
		}
		if(null == iconIndexinput || "" == iconIndexinput){
			iconIndexinput = icon;
		}
		markerSave(name,coordinate1,markerId,iconIndexinput,overlay);
		//清除标记
		/*var overlays = map.getOverlays();
		var len = overlays.getLength();
		for (var i = 0; i < len; i++) {
			if (overlays.item(i).get("id") == markerId) {
				//console("overlays.item(i)");
				map.removeOverlay(overlays.item(i));
				break;
			} 
		}*/
		var id = $("#reaDefinedFacility-id").val();
		$(".userDefinedFacility_"+id).find("span").html(name);
    };

	//删除标记修改
    var deleteDefinedFacility = function(){
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
    };
        
    var  _clearElectRail =function(index) {
    	$("#addUserDefinedFacility-panel").closePanelModel();
    	//找到对应的层，并做删除操作
    	var layer = HthxMap.getLayerById("featureLayer");
    	var source = layer.getSource();
    	if(preDrawFeature){
    		source.removeFeature(preDrawFeature);
    	}
    };
    _init();
    
    //返回函数
    return {
    	//editCarInfor: _editCarInfor,
    	isOpenOrClose:_isOpenOrClose,
    	/*definedFacilityDelete:_definedFacilityDelete,*/
    	setElectRail:_setElectRail,
    	showDetail:showDetail,
    	markerRemove:markerRemove,
    	definedFacilityShowPanel:_definedFacilityShowPanel,
    	saveFlag:_saveFlag,
    	updateDefinedFacility:updateDefinedFacility,
    	deleteDefinedFacility:deleteDefinedFacility,
    	/*createFacilityNameElement: createFacilityNameElement,*/
    	urlPath:_urlPath
    };
})(jQuery);

