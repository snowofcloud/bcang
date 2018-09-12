/**
 * @description: 自定义线条 ---删除
 *  * @author: wl
 * @time: 2016-08-02
 */
var userDefinedLineFind = (function ($) {
    //模块缓存变量
	var $saveDefinedLineForm = $("#save-userDefinedLine-form");
	var $addUserDefinedLinePanel = $("#addUserDefinedLine-panel");
	var $cancel = $("#addUserDefinedLine-panel .panel-close");
	var $cancelBtn = $("#addUserDefinedLine-panel .cancel");
    //请求路径
    var urlPath = {
    	deleteDefinedLineUrl:$.backPath + '/userDefinedLine/delUserDefinedLine/',
    	detailDefinedLineUrl:$.backPath + '/userDefinedLine/findByUserDefinedLineId/',
    	updateDefinedLineUrl:$.backPath + '/userDefinedLine/update',
    	saveDefinedLineUrl:$.backPath + '/userDefinedLine/saveLine',
    	findUserDefinedLineUrl:$.backPath  +'/userDefinedLine/findUserDefinedLine'
    };
    //数据初始化
    var _init = function () {
    	$cancel.unbind("click").bind("click",_closeDefinedLine);
        $cancelBtn.unbind("click").bind("click",_closeDefinedLine);
        $("#save-userDefinedLine-form").html5Validate(function (e) {
        	_submitElecRailToDB();
            return false;
    	});
        
    };
    //保存、更新标示  
    var _saveFlag = true; 
    var tempId="";
    var _isOpenOrClose =  true;        //默认true为开启，否false为取消常用联系人
    var tempPointList ="";//临时点数据---编辑限制线条用
	
	/**
	 *关闭线条限制
	 */
	var definedLineClose = function(){
		var $openDefinedLineId = $(".openDefinedLineFlag");
		var ids =[];
		$.each($openDefinedLineId,function(index,content){
			ids.push($openDefinedLineId[index].attr("id"));
		});
		var dataID = new Array();
		//隐藏限制线条
		if (0 != ids.length) {
			_isOpenOrClose = false;
			 Message.confirm({Msg:"确定隐藏自定义线条", iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 for (var i = 0; i < ids.length; i++) {
							//清除地图中相应的围栏
							HthxMap.clearMeasure(HthxMap.electronRailIndex[ids[i]]);
						}
				 }
			 });
		} else {
			//显示限制线条
			_setElectRail();
		}
	};
		
	//显示限制线条
	var _setElectRail = function() {
		var definedLineMsg = null;
		$.ajax({
		        url:  $.backPath  + "/userDefinedLine/findUserDefinedLine",
		        dataType: 'json',
		        type: "GET",
		        async: false,
		        success: function (json) {
		     	   if (json["code"] === 1) {
		     		  definedLineMsg = json.data;
		     		 /*一条一条填充电子围栏*/
		     	    	$.each(definedLineMsg, function(index, electSet) {
		     	    			if(electSet.pointList != "[]"){
		     	    				/*围栏为打开状态--填充围栏到地图中*/
		     	    				var tmpPoint = JSON.parse(electSet.points);
		     	           		    var len = tmpPoint.length;
		     	           		    var coor;
		     	           		        coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
		     	           		    createElecNameUserLine(electSet.lineName, coor, HthxMap.measureIndex, electSet.id);
					     			//HthxMap.areaDefinedLineIndex[electSet.id] = HthxMap.measureIndex;
					     			//HthxMap.areaDefinedLineRail[HthxMap.measureIndex] = electSet.id;    //用于区分标绘和测量
					     			HthxMap.measureIndex++;
		     	    			}
		     			});
		            } else {
		            	Message.show({Msg: "读取自定义线条信息出错", isModal: false});
	    	    		return false;
		            }
		        }
		});
	};
	
	
    /**
	 *删除线条限制
	 * @param arr 要删除的Id
	 */
	var _definedLineDelete =  function (arr) {
		var ids = arr;
		var deleteId = $("#"+arr).prev().attr("id");
			 Message.confirm({Msg:prompt.sureDelete, iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 $.ajax({
						 url: urlPath.deleteDefinedLineUrl  + ids,
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
									 HthxMap.areaDefinedLineRail[HthxMap.areaDefinedLineIndex[ids]] = null;    //用于区分标绘和测量
									 
							 }else if(json.code === 3){
								 Message.alert({Msg:json.msg,iconImg:"warning", isModal: false});
							 }
						 }
					 });
				 }
			 });
	};
	
	
	
	/*提交新增/更新限制线条到数据库*/
	var _submitElecRailToDB = function () {
		$.ajax({
	        url: urlPath.findUserDefinedLineUrl,
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        success: function (json) {
		        	if(json["code"] === 1){
		        		//console.log(json["data"].length);
		        		if(json["data"].length < 20){
		        			var name = $("#userDefinedLine-name").val();
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
		        						"lineName": name,
		        						"pointSet": pointSet,
		        						"pointList": pointList,
		        						"startLat": startLat,
		        						"startLng": startLng,
		        						"endLat": endLat,
		        						"endLng": endLng
		        					};
		        			var url = _saveFlag?urlPath.saveDefinedLineUrl:urlPath.updateDefinedLineUrl;
		        			/*将限制线条数据传输到后台*/
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
		        		    				var coor = HthxMap.drawLineString([tmpPoint], creatStyle.userDefinedStyle(),json.data); //直线
		        		    				/*在地图中显示围栏名称*/
		        		    				createElecNameUserLine(name, coor, HthxMap.measureIndex, json.data);
		        			    			HthxMap.areaDefinedLineIndex[json.data] = HthxMap.measureIndex;
		        			    			HthxMap.areaDefinedLineRail[HthxMap.measureIndex] = json.data;    //用于区分标绘和测量
		        			     			HthxMap.measureIndex++;
		        			     			map.getView().setCenter(coor);
		        		    			}
		        		    			/*清空缓存的坐标点集*/
		        		    			pointSet = null;
		        		    			pointList = null;
		        		    			$("#addUserDefinedLine-panel").closePanelModel();
		        		    			//重新显示车辆
		        		    			var monitorLayer = HthxMap.getLayerById("monitorLayer");
		        		    			initAllCarInfor(map, monitLayer);
		        		    			isUserDefinedLineDrawing = false;
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
		        		}else{
		        			Message.show({Msg:"预设线路已达最大条数", iconImg: "warning", isModal: false});
		        		}
		        	}else{
		        		Message.show({Msg: "读取自定义线条信息出错", isModal: false});
	    	    		return false;
		        	}
	        	}
	        });
	};
	
	/**
	 * 地图围栏中添加围栏名称
	 */
	function createElecNameUserLine(name,coor,index, id) {
		var deleteElement = document.createElement('div');
		var strs = '<div><label class="definedLineDetail" style="color:#0000ff" onclick="userDefinedLineFind.showDetail(\''+id+'\')" id="'+index+'">'+name+'</label>'+
					'<div class="lineName openDefinedLineFlag" id="'+id+'" style="color:#0000ff" onclick="userDefinedLineFind.definedLineDelete('+ "'" + id +"'" + ')">x</div></div>';	
		deleteElement.innerHTML = strs;
	    var deleteE = new ol.Overlay({
		    element: deleteElement,
		    offset: [0, 0],
		    position: coor,
		    positioning: 'center-center'
	    });
	    deleteE.setProperties({id: index});
	    map.addOverlay(deleteE);
	};

	
	//显示线条限制详情
	var _showDetail = function(){
		_saveFlag = false;
		var rowObj = $.getData(urlPath.detailDefinedLineUrl + arguments[0]);
		//var rowObj = $.getData(urlPath.detailDefinedLineUrl);//模拟数据
		tempId =arguments[0];
		tempPointList = rowObj.points;
		$addUserDefinedLinePanel.saveOrCheck(true);
        $saveDefinedLineForm.setFormSingleObj(rowObj);
        $addUserDefinedLinePanel.showPanelModel("自定义线条详情");
	};
	
	//关闭正在输入的限制线条
	var _closeDefinedLine = function(){
		$addUserDefinedLinePanel.closePanelModel();
		var layer = HthxMap.getLayerById("featureLayer");
		var source = layer.getSource();
		if(preDrawFeature){
			source.removeFeature(preDrawFeature);
		}
		//重新显示车辆
		var monitorLayer = HthxMap.getLayerById("monitorLayer");
		initAllCarInfor(map, monitLayer);
		isUserDefinedLineDrawing = false;
		
	};
	
	//展示限制线条的弹框
	var _definedLineShowPanel = function (id,title) {
		$.ajax({
	        url: urlPath.findUserDefinedLineUrl,
	        dataType: 'json',
	        type: "GET",
	        async: false,
	        success: function (json) {
		        	if(json["code"] === 1){
		        		var routeNum = json["data"].length+1;
		        		$("#userDefinedLine-name").val('预设路线'+routeNum+'');
		        	}
	        }
	        });
		$addUserDefinedLinePanel.saveOrCheck(true);
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
    	$("#addUserDefinedLine-panel").closePanelModel();
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
    	definedLineDelete:_definedLineDelete,
    	setElectRail:_setElectRail,
    	showDetail:_showDetail,
    	definedLineShowPanel:_definedLineShowPanel,
    	saveFlag:_saveFlag
    };
})(jQuery);

