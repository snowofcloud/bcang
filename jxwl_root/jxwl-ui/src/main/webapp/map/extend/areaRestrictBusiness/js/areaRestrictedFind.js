/**
 * @description: 区域限制 ---删除
 *  * @author: wl
 * @time: 2016-08-02
 */
var areaRestrictFind = (function ($) {
    //模块缓存变量
	var $reaRestrictType = $("#reaRestrict-type");
	var $reaRestrictSpeedPlace = $("#reaRestrict-speed-place");
	var $saveEnterpriseRightForm = $("#save-enterpriseRight-form");
	var $addAreaRestrictPanel = $("#addAreaRestrict-panel");
	var $cancel = $("#addAreaRestrict-panel .panel-close");
	var $cancelBtn = $("#addAreaRestrict-panel .cancel");
    //请求路径
    var urlPath = {
    	deleteRestrictUrl:$.backPath + '/limitArea/delLimitArea/',
    	detaliRestrictUrl:$.backPath + '/limitArea/findByLimitAreaId/',
    	updateRestrictUrl:$.backPath + '/limitArea/update',
    	saveRestrictUrl:$.backPath + '/limitArea/limit'
    	
    };
    //数据初始化
    var _init = function () {
    	$reaRestrictType.unbind("click").bind("change",_change);
    	$cancel.unbind("click").bind("click",_closeRestrict);
        $cancelBtn.unbind("click").bind("click",_closeRestrict);
        $("#save-enterpriseRight-form").html5Validate(function (e) {
        	_submitElecRailToDB();
            return false;
    	});
        
    };
    //保存、更新标示  
    var _saveFlag = true; 
    var tempId="";
    var _isOpenOrClose =  true;        //默认true为开启，否false为取消常用联系人
    var tempPointList ="";//临时点数据---编辑限制区域用
	
	/**
	 *限制类型选择
	 */
	var _change = function(){
		var type = $reaRestrictType.val();
		if(type === "112001001"){
			$reaRestrictSpeedPlace.removeClass("hidden");
		}else{
			$reaRestrictSpeedPlace.addClass("hidden");
		}
		$("#reaRestrict-speed").val("");
	};
	
	/**
	 *关闭区域限制
	 */
	var restrictClose = function(){
		var $openRestrictId = $(".openRestrictFlag");
		var ids =[];
		$.each($openRestrictId,function(index,content){
			ids.push($openRestrictId[index].attr("id"));
		})
		var dataID = new Array();
		//隐藏限制区域
		if (0 != ids.length) {
			_isOpenOrClose = false;
			 Message.confirm({Msg:"确定隐藏限制区域", iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 for (var i = 0; i < ids.length; i++) {
							//清除地图中相应的围栏
							HthxMap.clearMeasure(HthxMap.electronRailIndex[ids[i]]);
						}
				 }
			 });
		} else {
			//显示限制区域
			_setElectRail();
		}
	};
		
	//显示限制区域
	var _setElectRail = function() {
		var restrictMsg = null;
		$.ajax({
		        url:  $.backPath  + "/limitArea/findLimitArea",
		        dataType: 'json',
		        type: "GET",
		        async: false,
		        success: function (json) {
		     	   if (json["code"] === 1) {
		     		  restrictMsg = json.data;
		     		 /*一条一条填充电子围栏*/
		     	    	$.each(restrictMsg, function(index, electSet) {
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
					     			createElecNameElement(electSet.limitName, coor, HthxMap.measureIndex, electSet.id);
					     			HthxMap.areaRestrictIndex[electSet.id] = HthxMap.measureIndex;
					     			HthxMap.areaRestrictRail[HthxMap.measureIndex] = electSet.id;    //用于区分标绘和测量
					     			HthxMap.measureIndex++;
		     	    			}
		     			});
		            } else {
		            	Message.show({Msg: "读取限制区域信息出错", isModal: false});
	    	    		return false;
		            }
		        }
		});
	};
	
	
    /**
	 *删除区域限制
	 * @param arr 要删除的Id
	 */
	var _restrictDelete =  function (arr) {
		var ids = arr;
		var deleteId = $("#"+arr).prev().attr("id");
			 Message.confirm({Msg:prompt.sureDelete, iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 $.ajax({
						 url: urlPath.deleteRestrictUrl  + ids,
						 type: 'POST',
						 dataType: 'json',
						 data:{
					     	   'id':ids 
					        },
						 async: false,
						 success: function(json) {
							 if(json.code === 1) {
								 //清除地图中相应的围栏
									 HthxMap.clearMeasure(deleteId);
									 HthxMap.areaRestrictRail[HthxMap.areaRestrictIndex[ids]] = null;    //用于区分标绘和测量
									 
							 }else if(json.code === 3){
								 Message.alert({Msg:json.msg,iconImg:"warning", isModal: false});
							 }
						 }
					 });
				 }
			 });
	};
	
	
	
	/*提交新增/更新限制区域到数据库*/
	var _submitElecRailToDB = function () {
		var name = $("#reaRestrict-name").val();
		var text = $("#electronRailText").text();
		/*验证围栏名称是否为空*/
		if ("" === name) {
			name.val("");
			name.testRemind("您尚未输入"+ text);
			name.focus();
			return;
		}
		var data = {
				"id":tempId,
				"limitName": name,
				"pointSet": pointSet,
				"pointList": pointList,
				"limitType":$("#reaRestrict-type").val(),
				"limitSpeed":$("#reaRestrict-speed").val()
				};
		var url = _saveFlag?urlPath.saveRestrictUrl:urlPath.updateRestrictUrl;
		/*将限制区域数据传输到后台*/
		$.ajax({
	        url: url,
	        type: "POST",
	        dataType: 'json',
	        data: data,
	        async: false,
	        success: function (json) {
	            if (json["code"] === 1) {
	        		_clearElectRail();
	    			if(!_saveFlag){
	    				$("#"+json.data).prev().text(name);
	    			}else{
	    				/*围栏为打开状态--填充围栏到地图中*/
		        		var tmpPoint = JSON.parse(pointList);
		        		var len = tmpPoint.length;
	    				var coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle(),json.data); //多边形
	    				/*在地图中显示围栏名称*/
		    			createElecNameElement(name, coor, HthxMap.measureIndex, json.data);
		    			HthxMap.areaRestrictIndex[json.data] = HthxMap.measureIndex;
		    			HthxMap.areaRestrictRail[HthxMap.measureIndex] = json.data;    //用于区分标绘和测量
		     			HthxMap.measureIndex++;
		     			map.getView().setCenter(coor);
	    			}
	    			/*清空缓存的坐标点集*/
	    			pointSet = null;
	    			pointList = null;
	    			$("#addAreaRestrict-panel").closePanelModel();
	    			//在地图中填充点
//	    			electFenceRegular.showPanelAddRegular(json.data, name);
//	                //清空围栏名称文本内容
//	        		$("#electronRailName").val("");
//	        		toolMethod.closePanel("#electronRailAdd", true);
//	        		Message.show({Msg: json["msg"], isModal: false});
	    			data={};
	    			_saveFlag = true;
	            	return false;
	            } else if (json["code"] === 3) {
	            	Message.alert({Msg: json["msg"], iconImg: "warning", isModal: false});
	                return false;
	            } else {
	            	 Message.alert({Msg: json["msg"], iconImg: "error", isModal: false});
	                 return false;      
	            }
	        }
	    });
	};
	//显示区域限制详情
	var _showDetail = function(){
		_saveFlag = false;
		var rowObj = $.getData(urlPath.detaliRestrictUrl + arguments[0]);
		//var rowObj = $.getData(urlPath.detaliRestrictUrl);//模拟数据
		tempId =arguments[0];
		tempPointList = rowObj.points;
		$addAreaRestrictPanel.saveOrCheck(true);
        $saveEnterpriseRightForm.setFormSingleObj(rowObj);
        if(rowObj.limitType == "112001001"){
			$reaRestrictSpeedPlace.removeClass("hidden");
		}else{
			$reaRestrictSpeedPlace.addClass("hidden");
		}
        $addAreaRestrictPanel.showPanelModel("限制区域详情");
	};
	
	//关闭正在输入的限制区域
	var _closeRestrict = function(){
		$addAreaRestrictPanel.closePanelModel();
		var layer = HthxMap.getLayerById("featureLayer");
		var source = layer.getSource();
		if(preDrawFeature){
			source.removeFeature(preDrawFeature);
		}
	};
	
	//展示限制区域的弹框
	var _restrictShowPanel = function (id,title) {
		$addAreaRestrictPanel.saveOrCheck(true);
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
    
    
    var  _clearElectRail =function(index) {
    	$("#addAreaRestrict-panel").closePanelModel();
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
    	restrictDelete:_restrictDelete,
    	setElectRail:_setElectRail,
    	showDetail:_showDetail,
    	restrictShowPanel:_restrictShowPanel,
    	saveFlag:_saveFlag
    };
})(jQuery);

