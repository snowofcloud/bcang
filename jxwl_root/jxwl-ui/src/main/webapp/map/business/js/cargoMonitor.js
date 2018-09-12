/**
 * @description:车辆监控
 * @author: wl
 * @time: 2016-7-27
 */

$(function() {
//	cargoMonitor.init();
	//上下线、报警初始化
	carOnlinStatus.init();
	//车辆调度
	mapCarDispatch.init();
	//历史视频
	mapHistoryVideo.init();
});

//车辆监控状态
var cargoMonitor = (function($) {
	//请求路径
    var urlPath = {
        findByPageAlarmUrl: $.backPath + '/alarmManage/findAlarmForDiTu', //获报警记录
        findAlarmNumUrl: $.backPath + '/alarmManage/findAlarmNumForDiTu', //获报警记录
        findByPageCargoUrl: $.backPath + '/logisticst/findById/',   //获取监控记录
        //车辆调度
        findDictByCodeUrl: $.backPath + "/employee/findDictByCode/" //数据字典
    };
    
	var carInfor;//车辆信息
	var postData;//向后台发送请求的数据
	var $cargoInfor  = $("#cargoInfor");//监控记录
	var $footerPanel = $("#footer-panel");//地图下侧展示
	//车辆调度
	var $saveCarDispatch = $("#save-carDispatch-form");//车辆调度信息
	//数据字典--处理情况
	var handleState;
	//数据字典--报警类型
	var alarmType;
	//报警条数
	var	alarmNum = 0;
	var num = 0;
	//初始化--根据ID查询相应信息 填充tab标签页中
	var _init = function(properties) {
		//var feature = monitLayer.getSource().getFeatureById(carrierName);
		//properties = feature.getProperties();
		_getDictionary(properties);
		carInfor = properties;
		//点击地图坐标打开或者是点击相应按钮功能打开
		var flag = arguments[1];
		if( num >0){
			properties.alarmState = "报警中";
			$("#alarmState").text("报警中");
		}else{
			properties.alarmState = "未报警";
			$("#alarmState").text("未报警");
		}
		//获取监控记录数据
		if (flag) {
			$footerPanel.css("display","block");
			//var getData = $.getData(urlPath.findByPageCargoUrl,'POST',postData);
			$("#realInfor-content").setFormSingleObj(properties);
			_initTable(properties);
		} else {
			//对传入的是数据格式化
			//表格初始化时必须先让表格最上面被隐藏的祖先以及表格本身显示出来！否则表格整体宽度不对
			$footerPanel.css("display","block");
			$('#alarmLog').addClass("active");
			properties.temperature = filterData(properties.temperature);
			properties.pressure = filterData(properties.pressure);
			properties.liquidLevel = filterData(properties.liquidLevel);
			properties.speed = filterData(properties.speed);
			properties.tirePressure = filterData(properties.tirePressure);
			$("#realInfor-content").setFormSingleObj(properties);
			_initTable(properties.carrierName);
			$footerPanel.css("display","none");
		}
		var classFlag = arguments[2];
		if( !classFlag){
			$("#alarmLog").removeClass("active");
			$("#alarmLog-tab").removeClass("active");
			$("#cargoMonitor").addClass("active");
			$("#cargoMonitor-tab").addClass("active");
		}else if(classFlag){
			$("#cargoMonitor").removeClass("active");
			$("#cargoMonitor-tab").removeClass("active");
			$("#alarmLog").addClass("active");
			$("#alarmLog-tab").addClass("active");
		}
		
		//tabs切换调用事件
        $("#footer-tabs").find('a').click(function() {
            var $this = $(this);
            var tabsName = $this.attr('href');
            $("a[aria-controls='" + tabsName.split("#")[1] + "']").tab('show');
        });
		
	};
	//过滤为空的数据
	var filterData= function() {
		var data = arguments[0];
		var returnData;
		if (data) {
			returnData = parseFloat(data).toFixed(1);
		} else {
			returnData = "";
		}
		return returnData;
	};
	//获取数据字典
	var _getDictionary = function(properties) {
		handleState = $.getData(urlPath.findDictByCodeUrl + "103002");
		alarmType   = $.getData(urlPath.findDictByCodeUrl + "103001");//此code下有10个报警类型
		alarmType2  = $.getData(urlPath.findDictByCodeUrl + "130001");//此code下有31个报警类型
		_alarmNum(properties);
	};
	
	var _alarmNum = function(properties){
		 var data = {
				 "carrierName":properties.carrierName
		 };
		 $.ajax({
	         url: urlPath.findAlarmNumUrl,
	         type: "POST",
	         data: data,
	         async: false //使用同步方式
	     }).done(function (json) {
	     	if(json.code === 1){
	     		num = json.data;
	     		return json.data;
	     	}else{
	     		Message.alert({Msg: json.msg, isModal: false});
        		return;
	     	}
	     });
	};
	
	
    var _reload = function(carrierName){
    	var data = {
        		"carrierName" : carrierName
        	};
    	$("#alarmLogTable").jqGrid('setGridParam', {
              url: urlPath.findByPageAlarmUrl,
              postData: data,
              page: 1
          }).trigger("reloadGrid");
    };
    
    
    var _initTable = function(){
    	//表格重加载
    	
    	var data = {
    		"carrierName" : arguments[0]
    	};
    	$("#alarmLogTable").jqGrid({
              url: urlPath.findByPageAlarmUrl,
              mtype: "POST",
              datatype: "JSON",
              postData: data,
              colNames: ["车牌号","驾驶员", "手机号","托运单号","报警时间","报警类型","报警简述","处理状态","操作"],
              colModel: [
                  {name: "carrierName",  index: "1", align: "center", width: "30px", sortable: false},
                  {name: "driver",       index: "2", align: "center", width: "30px", sortable: false},
                  {name: "telephone",    index: "3", align: "center", width: "30px", sortable: false},
                  {name: "waybillNo",    index: "4", align: "center", width: "30px", sortable: false},
                  {name: "alarmDate",    index: "5", align: "center", width: "30px", sortable: false},
                  {name: "alarmType",    index: "6", align: "center", width: "30px", sortable: false,
                	  formatter:function(cellvalue,options,rowObject){
                		  var returnType="";
	                		$.each(alarmType, function(index,content){
	                  			if(alarmType[index].code === rowObject.alarmType){
	                  				returnType = alarmType[index].name;
	                  			}
	                  		});
                    		$.each(alarmType2, function(index,content){
                    			if(alarmType2[index].code === rowObject.alarmType){
                    				returnType = alarmType2[index].name;
                    			}
                    		});
                    		return returnType;  
                	  }
                  },
                  {name: "alarmDetails",    index: "7", align: "center", width: "30px", sortable: false},
                  {name: "alarmDealStatus", index: "8", align: "center", width: "30px", sortable: false,
                	  formatter:function(cellvalue,options,rowObject){
                  		var returnState="";
                  		$.each(handleState,function(index,content){
                  			if(handleState[index].code === rowObject.alarmDealStatus){
                  				returnState = handleState[index].name;
                  			}
                  		});
                  		return returnState;
                  	} 
                  },
                  { name: "", index: "9", align: "center", width: "30px", sortable: false,
                      formatter: function (cellValue, options, rowObject) {
                    	  var handlerTemp ="";
//                    	  if(rowObject.alarmDealStatus === "103002002"){
//                    		  handlerTemp =
//                                  '<p class="jqgrid-handle-p">' +
//                                  '<label class="jqgrid-handle-text delete-link" onclick="cargoMonitor.handle(\'' + rowObject.id + '\')"><image class="img-edit">处理</label>' +
//                                  '&nbsp;&nbsp;&nbsp;&nbsp;' +
//                                  '<label class="jqgrid-handle-text delete-link" onclick="cargoMonitor.detailsalarmLog(\'' + rowObject.id + '\')"><img class="img-details">详情</label>' +
//                                  '</p>'; 
//                    	  }else{
                    	      var id = rowObject.id+'"';
                    		  handlerTemp =
                                  '<p class="jqgrid-handle-p">' +
                                  '<a class="jqgrid-handle-text delete-link" href="/JXWL/view/carMonitor/alarmManage.html?id='+ id + 'target="_blank"><img class="img-details">详情</a>' +
                                  '</p>';
//                    	  }
                          return handlerTemp;
                      }
                  }
              ],
              loadonce: false,
              viewrecords: true,
              autowidth: true,
              height: 120,
              rowNum: 5,
              gridComplete : function() {
            	  var records = $('#alarmLogTable').jqGrid('getGridParam', 'records');
            	  $("#recordArlam").text(records);
              },
              rowList: [5],
              pager: "#alarmLogPager"
    	  //setGridWidth($("#footer-panel").width() - 10) 不对
          //因为在1366*768分辨率下，$("#footer-panel").width() = 1487，
          //所以alarmLogTable的宽度就是1477px,超过了其父元素的宽度，所以展示不全 ！！
          //setGridWidth($("#footer-panel").width() - 10);
          });
          
    };
    
    //处理
    var _handle = function(ids){
    	 $("#alarmLogTable").jqGrid('setGridParam', {
             url: urlPath.findByPageUrl,
             postData: {"searchKey": rowObj.ids
                     },
             page: 1
         }).trigger("reloadGrid");
    };
	
    //车辆信息选项卡切换
    var _showTabPanle = function(){
    	 $("#collapseVenn a").click(function (e) {
             var $this = $(this);
             var tabsName = $this.attr('href');
             e.preventDefault();
             $(this).tab('show');
            switch (tabsName) {
             case '#detailInfor':
            	 
            	/* $("#carInfor-content .tab-pane:not(.tab-pane:eq(0))").removeClass("in active");
            	 $("#carInfor-content .tab-pane:eq(0)").addClass("in active");*/
                 break;
             case '#carInfor':
            	 /*$("#carInfor-content .tab-pane:not(.tab-pane:eq(1))").removeClass("in active");
            	 $("#carInfor-content .tab-pane:eq(1)").addClass("in active");*/
                 break;
             case '#teriminalInfor':
            	 /*$("#carInfor-content .tab-pane:not(.tab-pane:eq(2))").removeClass("in active");
            	 $("#carInfor-content .tab-pane:eq(2)").addClass("in active");*/
                 break;
             case '#waybillInfor':
            	/* $("#carInfor-content .tab-pane:not(.tab-pane:eq(3))").removeClass("in active");
            	 $("#carInfor-content .tab-pane:eq(3)").addClass("in active");*/
                 break;
             case '#shortcutInfor':
            	 /*$("#carInfor-content .tab-pane:not(.tab-pane:eq(4))").removeClass("in active");
            	 $("#carInfor-content .tab-pane:eq(4)").addClass("in active");*/
                 break;
         }
         });
    };
	return {
		init:_init,
		handle:_handle,
		showTabPanle:_showTabPanle,
		alarmNum: _alarmNum,
		reload:_reload
	};
})(jQuery);



/**
 * 车辆上下线、车辆报警情况
 */
var carOnlinStatus = (function($) {
	// 请求路径
	var urlPath = {
		findStatusUrl : $.backPath + '/logisticst/findByPage'
	};
	var $onlineClose = $("#onlineStatus").find(".panel-close");
	var $offlineClose = $("#offlineStatus").find(".panel-close");
	var $alarmClose = $("#carAlarmStatus").find(".panel-close");
	var $onlineStatusForm = $("#onlineStatus-form");
	var $offlineStatusForm = $("#offlineStatus-form");
	var $carAlarmStatusForm = $("#carAlarmStatus-form");
	var $carAlarmMintor = $("#carAlarmMintor");
    var $onlineHtml = $("#onlineHtml");
    var $offlineHtml = $("#offlineHtml");
    
    
    //车辆上线依次弹出
    var _onlineNotify = function(data, length, timer, onlineIndex){
    	if(!onlineIndex){
    		onlineIndex = 0;
    	}
    	if( onlineIndex >= length){
    		onlineIndex = 0;
    		window.clearInterval(timer);
    	}else{
    		//组装弹框html
			if( $("#onlineStatus").queue("fx").length == 0){
				var temp = '';
				temp =	temp+'<div class="row">'+
				            '<span class="carinforfont" >车牌号:</span>'+
				            '<span class="look-formPanel carinforfont" >'+
				            data[onlineIndex].carrierName
				            +'</span>'+
			                '</div>'+
			                '<div class="row">'+
				            '<span class="carinforfont" >所属物流企业:</span>'+
				            '<span class="look-formPanel carinforfont" >'+
				            data[onlineIndex].enterpriseName
				            +'</span>'+
			                '</div>';
				  $onlineHtml.html("");
                  $onlineHtml.append(temp);
                  $("#onlineStatus").css("z-index", "3000").slideDown(2000).delay(5000).slideUp(2000);
                  var onlineCarArray = [];
					onlineCarArray.push(data[onlineIndex]);
                  initMonitLayer(map,monitLayer,onlineCarArray);
		            //调用topMenu.js的车辆数据计算函数
		            topMenuMag.carAmountFunc(0,1,-1);
		            $("#onlineMintor").attr("data", data[onlineIndex].carrierName);
		            onlineIndex++;
		            return onlineIndex;
			}
			
    	}
    };
    
	var _getInfor = function(json,privg) {
		if ( json.type == "on"  ) {
			updateLeftMenu();
			var data = json.data;
			if(privg){//有上下线弹框提示的权限
				 var onlineIndex = 0;
				 var timer = window.setInterval(function(){//开启定时器
					 onlineIndex = _onlineNotify(data, data.length, timer, onlineIndex);
				 },100);
			}else{
				 initMonitLayer(map,monitLayer,data);
		         //调用topMenu.js的车辆数据计算函数
		         topMenuMag.carAmountFunc(0,data.length,-data.length);
			}	
		}else if(json.type == "off"){
			updateLeftMenu();
			var datas = json.data;
			var temp = '';
			var offline = sessionStorage.offline;
			if(privg){
				for(var i=0;i<datas.length;i++){
					temp =	temp+'<div class="row">'+
					            '车牌号:'+
					            datas[i].carrierName+
				                '</div>'+
				                '<div class="row">'+
					            '所属物流企业:'+
					            datas[i].enterpriseName+
				                '</div>';
				}
				$offlineHtml.html("");
				temp = temp +'<div class="row">共有'
	              +datas.length
	  			  +'辆车辆下线'+
	  			  '</div>';
				$offlineHtml.append(temp);
				if(offline == "notRemind"){
					$("#offlineStatus").css("z-index", "3010").slideDown(2000).delay(5000).slideUp(2000);
				}
			}
			 //删除下线车辆图标
            removeMonitLayer(map,monitLayer,json.data);
            //调用topMenu.js的车辆数据计算函数
            topMenuMag.carAmountFunc(0,-datas.length,datas.length);
            //如果下线的车辆的 打开了资料卡
            var carrierName = $("#carInforDetail-Panel").attr("data-type");
            if(carrierName){
	            for(var i=0,len=datas.length;i<len;i++){
	            	if( carrierName ===  datas[i].carrierName ){
	            		removeClickPop();
	            		break;
	            	}
	            }
            }
		} else if (json.data.alarm) {
			$("#carAlarmMintor").attr("data", json.data.carrierName);
			$("#carAlarmStatus").css("z-index", "2990").slideDown(2000).delay(5000).slideUp(2000);
			$carAlarmStatusForm.setFormSingleObj(json.data);
//			_changeToAlarmImg(json.data.carrierName);
			//_showBtn(json.data.carrierName);
		}
	};
//	var _showBtn = function(carrierName){
//		var url = HthxMap.Settings.rest+"/dangerVehicle/findByCarrierName";
//		var status;
//		$.ajax({
//		   type: "POST",
//		   url: url,
//		   async:false,
//		   data: {"carrierName": carrierName},
//		   success: function(json){
//			  if(json['code'] === 1){
//				  status =  json.data;
//			  }
//		   }
//	    }); 
//		//在线
//		var carAlarmMintor =$("#carAlarmMintor");
//		if( status === "0"){
//			carAlarmMintor.removeClass("hidden");
//		}else{
//			carAlarmMintor.addClass("hidden");
//		}
//	};
	/**
	 * 改变到报警状态图片
	 * @param carrierName 车牌号
	 */
	var  _changeToAlarmImg= function(carrierName){
		if(!carrierName){
			return;
		}
		var source = monitLayer.getSource();
		var feature = source.getFeatureById(carrierName);
		if( !feature){
			return;
		}
		var imgArr = [];
		var properties = feature.getProperties();
		var pngUrl = null;
		if("嘉兴位置服务平台" == properties.orgin){
			pngUrl = HthxMap.Settings.configData.targetImg.alarm;
		}else{
			pngUrl = HthxMap.Settings.configData.targetImg.alarmGPS;
		}
		feature.set("terminalTypeImage",pngUrl);
		imgArr.push(pngUrl);
		HthxMap.changeShipImg(feature, 0, imgArr);
	};
	/**
	 * 改变到正常状态图片
	 * @param carrierName 车牌号
	 */
	var _changeToNormalImg= function(carrierName){
		if(!carrierName){
			return;
		}
		var source = monitLayer.getSource();
		var feature = source.getFeatureById(carrierName);
		var imgArr = [];
		var pngUrl;
		var properties = feature.getProperties();
		if("嘉兴位置服务平台" == properties.orgin){
			pngUrl = HthxMap.Settings.configData.targetImg.normal;
		}else{
			pngUrl = HthxMap.Settings.configData.targetImg.normalGPS;
		}
		feature.set("terminalTypeImage",pngUrl);
		imgArr.push(pngUrl);
		HthxMap.changeShipImg(feature, 0, imgArr);
	};

	var _updateLoginState = function(json){
		if( "0" == json.data){
			$("#loginState").text("已登录");
		}else{
			$("#loginState").text("未登录");
		}
	};
	
	var removeMonitLayer = function(map,layer,datas){
		var source = layer.getSource();
		if( datas instanceof Array){//如果传入的是数组
			for(var i=0;i<datas.length;i++){
				var data = datas[i];
				var feature = source.getFeatureById(data.carrierName);
				source.removeFeature(feature);
			}
		}else{//一个对象
			var feature = source.getFeatureById(datas.carrierName);
			source.removeFeature(feature);
		}
	};

	// 关闭弹窗
	var _closePanel = function() {
		$this = $(this);
		var panelId = $this.parents("div:eq(1)").attr("id");
		$("#" + panelId).stop();
	};

	var _init = function() {
		// 初始化加载
		// _getInfor();
		$onlineClose.unbind("click", _closePanel).bind("click", _closePanel);
		$offlineClose.unbind("click", _closePanel).bind("click", _closePanel);
		$alarmClose.unbind("click", _closePanel).bind("click", _closePanel);
		$carAlarmMintor.unbind("click", _carPosition).bind("click",
				_carPosition);
		$("#onlineMintor").unbind("click", _carPosition).bind("click",
				_onlinePosition);

	};
	/**
	 * 监控车辆定位 onlineMintor：车上线
	 */
	var _onlinePosition = function() {
		var data = $("#onlineMintor").attr("data");
		var carrierNames = data.split(",");
			var carrierName = carrierNames[0];
			var featureid = carrierName;
			var e = event || window.event;
			var feature = monitLayer.getSource().getFeatureById(featureid);
			if (feature) {
				var style = feature.getStyle();
				if (style) {
					feature.setStyle(featureStyles[featureid]);
					HthxMap.clickEvent(feature);
					HthxMap.preFeature = feature;
					map.getView().setCenter(feature.getProperties().point);
					window.setTimeout(function(){//延时5秒后执行图片缩小
						var imgArr = [];
						var shipImgBase = HthxMap.createShipImg(feature);  // 基础图片路径
						imgArr.push(shipImgBase);				
						HthxMap.changeShipImg(feature, 0, imgArr);
						},5000);
				}
			}
		
	};
	
	
	/**
	 * 监控车辆定位 alarmInfor：报警信息
	 */
	var _carPosition = function() {
		var alarmInfor = $carAlarmMintor.attr("data");
		var alarmArry = alarmInfor.split(",");
		var featureid = alarmArry[0];
		var e = event || window.event;
		//var feature = monitLayer.getSource().getFeatureById(featureid);
		topMonitorLayer.getSource().clear();
		topMonitorLayer.getSource().addFeature(monitLayer.getSource().getFeatureById(featureid));
		var feature = topMonitorLayer.getSource().getFeatureById(featureid);
		if (feature) {
			var style = feature.getStyle();
			if (style) {
				feature.setStyle(featureStyles[featureid]);
				HthxMap.clickEvent(feature);
				HthxMap.popDiv.createClickPop(feature, boatDiv);
				HthxMap.preFeature = feature;
				map.getView().setCenter(feature.getProperties().point);
			}
		}
	};

	return {
		init : _init,
		getInfor : _getInfor,
		updateLoginState:_updateLoginState,
		onlineNotify:_onlineNotify
	};
})(jQuery);



//车辆调度情况
var mapCarDispatch =(function($) {
	//车辆调度
	var $saveCarDispatch = $("#save-carDispatch-form");
	var $close = $("#carDispatch-panel .panel-close");
//定时器
	interval22 = null;
	//车牌号
	var carrierName;
	//初始化
	var _init = function() {
		 //表单事件绑定
		$saveCarDispatch.html5Validate(function () {
        	_submitHandle();
            return false;
        });
	};
	var _showPanel = function() {
		$("#carDispatch-panel").showPanelModel("车辆调度");
		$close.unbind("click").bind("click",function(){
			window.clearInterval(interval22);
		});
		carrierName = arguments[0];
		_renderDispathContent();
		interval22 = window.setInterval(_renderDispathContent,15000);
	}; 
	
	var _renderDispathContent = function(){
		
		$("#mapCarDispatchMsg").empty();
 		$("#carDisp").addClass("hidden");
 		var disps = "";
		$.ajax({
			type : 'POST',
			url  :  $.backPath + '/shortcutOperation/carDispVal',
			data : {"carrierName": carrierName},
			async: false,
			success : function(json) {
				disps = json.data;
			}
		});
		
		if (disps && 0 < disps.length) {
 			$("#carDisp").removeClass("hidden");
 			var html = "";
 			for (var index in disps) {
 				var disp = disps[index];
 				var time = disp.MSG_DATE;
 				var cont = disp.CONTENT;
 				var tel  = disp.TERMINAL_TEL;
 				var plate= disp.CARRIER_NAME;
 				var sign = disp.SIGN;
 				var useName =disp.USER_NAME;
 				if(sign == 3){
 					html += "<div><span class='plate'>"+plate+"</span>&nbsp;<span class='telTag'>(TEL:"+tel+"):</span>&nbsp;<div class='contTag02'>"+cont+"</div>&nbsp;<span class='timeTag02'>"+time+"</span></div>";
 				} 
 				if(sign == 1){
 					html += "<div id='ourContent' class='clearfix'><div class='NameTag'>"+useName+"</div>&nbsp;<div class='contTag01'>"+cont+"</div>&nbsp;<div class='timeTag01'>"+time+"</div></div>";
 				};
 			}
	 		$("#mapCarDispatchMsg").append(html);
	 		//让滚动条滚动到最底部
	 		document.getElementById("mapCarDispatchMsg").scrollTop = document.getElementById("mapCarDispatchMsg").scrollHeight;
 		}
	};
	
	
	//提交
	var _submitHandle =function() {	
		
		/*var flag = true;
		var functionIdsArray = [];
		var funcName = [];
		//遍历选中的项
		var checkBoxs = $("#docNameDiv .doc[type=checkbox]");
		$.each(checkBoxs, function (index, value) {
			if(value.checked){
				functionIdsArray.push(value["value"]);
				funcName.push(value.getAttribute("data-name"));
			}
		});*/
		
		var mark = _getMarkValue();
	 	var formData = {
	 			"content":$("#mapCarDispatchInfor").val(),
	 			"carrierName":carrierName,
	 			"mark":mark,
	 			"sign":"1",
	 			"carDispatchFlag":$('#carDispatch-panel form input[name=CarDispatchFlag]').val()
	 	};
	 	var url = "";
	 	//提交申请
        $.ajax({
            url:$.backPath+"/shortcutOperation/carDispatch",
            type: 'POST',
            async: false,
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
            	_renderDispathContent();
            	$("#mapCarDispatchInfor").val("");
            } else {
                $.validateTip(json);
            }
        });
	};
	//得到文本信息标志值
	var _getMarkValue = function(){
		var checkBoxs = $('input[name="CarDispatchFlag"]:checked');
		var mark = 0;
		for(var i=0,len=checkBoxs.size();i<len;i++){
			mark += parseInt(checkBoxs[i].value);
		}
		return mark;
	};
	
	return{
		init:_init,
		showPanel:_showPanel
	};
})(jQuery);

//历史视频
var mapHistoryVideo=(function($) {
	var $historyVideoTable     = $("#historyVideoTable");
	var $historyVideoSearchBtn = $("#historyVideo_searchBtn");
	var $historyVideoPanel     = $("#historyVideo-panel");
	var $showVideoPanel        = $("#showVideo-panel");
	//初始化
	
	var _init =function(){
		$historyVideoSearchBtn.bind("click",_searchHandler);
	};
	
	var _showPanel = function(){
		$historyVideoPanel.showPanelModel("历史视频");
		_initTable();
	}; 
	
	 var _initTable = function(){
	    	var data = {
	    			"terminalID":arguments[0]
	    	};
	    	$historyVideoTable.jqGrid({
	              //url:  $.backPath + '/logisticst/findByPage',
	    		  url:  '../js/temporary/historyVideo.json',
	              mtype: "POST",
	              datatype: "JSON",
	              postData: data,
	              colNames: ["车牌号","记录时间", "记录时长","操作"],
	              colModel: [
	                  {name: "licencePlateNo", index: "1", align: "center", width: "30px", sortable: false},
	                  {name: "vehicleType", index: "2", align: "center", width: "20px", sortable: false},
	                  {name: "vehicleOutput", index: "3", align: "center", width: "20px", sortable: false},
	                  { name: "", index: "4", align: "center", width: "30px", sortable: false,
	                      formatter: function (cellValue, options, rowObject) {
	                    	  var handlerTemp ="";
	                    		  handlerTemp =
	                                  '<p class="jqgrid-handle-p">' +
	                                  '<label class="jqgrid-handle-text delete-link" onclick="mapHistoryVideo.showVideo(\'' + rowObject.licencePlateNo + '\',\''+rowObject.vehicleType+'\',\''+rowObject.vehicleOutput+'\')">'+
	                                  		'<img class="img-edit">播放</label>' +
	                                  '</p>';
	                          return handlerTemp;
	                      }
	                  }
	              ],
	              loadonce: false,
	              viewrecords: true,
	              autowidth: false,
	              rowNum: 5,
	              height:230,
	              width:550,
	             // rowList: [5],
	              pager: "#historyVideoPager"
	          }).setGridWidth($("#historyVideo-panel").width());
	    		 
	    };
	
	    //查询数据信息
	    var _searchHandler = function () {
	    	$historyVideoTable.jqGrid('setGridParam', {
	            postData: {
	            	       " ": $("#historyVideo_searchBtn").val(),
	            	       " ": $("#historyVideo_startTime").val(),
	            	       " ": $("#historyVideo_endTime").val()
	            },
	            page: 1
	        }).trigger("reloadGrid");
	    }; 
	    
	    //播放视频
	    var _showVideo = function(){
	    	var videoInfor = arguments;
	    	$("#voideLicencePlateNo").text(videoInfor[0]);
	    	$("#voideRecodTime").text(videoInfor[1]);
	    	$showVideoPanel.showPanelModel("视频播放");
	    	$showVideoPanel.find(".panel-close").bind("click",_closeVideo);
	    };
	    //关闭视频页弹框
	    var _closeVideo= function(){
	    	$showVideoPanel.fadeOut(300);
	    	$(".panel-mask").css("z-index","1000");
	    };
	
	return{
		init:_init,
		showPanel:_showPanel,
		showVideo:_showVideo
	};
})(jQuery);

//轨迹回放 对象
var historyTrack = (function($){
	var $trackPanel = $("#track-panel");
	var $trackForm=$("#track-form");
	var tempId="";
	//请求路径
    var urlPath = {
    		getTrackDataUrl: $.backPath + '/shortcutOperation/historyTrack'//轨迹数据
    };
	//点击时就初始化，获取车牌号
	var _init =function(){
		tempId = arguments[0];
		$trackPanel.showPanelModel("轨迹回放");
		$trackForm.html5Validate(function(){
			_submitHistroyTrack();
			return false;
		});	
	};
	//提交轨迹查询数据
	var _submitHistroyTrack =function(){
		var startTime = $("#historyTrack_startTime").val();
		var endTime = $("#historyTrack_endtTime").val();
		var interval = (new Date(endTime) - new Date(startTime))/(1000*60*60);
		if(interval > 24){
			Message.alert({Msg:"查询历史轨迹时长不能超过24小时!",isModal: true}); 
			return;
		}
		var data ={
				"carrierName":tempId,
				"startTime":$("#historyTrack_startTime").val(),
				"endTime":$("#historyTrack_endtTime").val()
		};
		$.ajax({
			url : urlPath.getTrackDataUrl,
			type : 'POST',
			dataType : 'JSON',
			data: data,  
			success : function(result) { 
				$("#loadingDiv").remove();
				if(result['code'] === 1){
					//关闭弹框
					removePopup(data.carrierName);
					//查询成功则关闭地图其他弹框（除车辆信息弹框）
					$("#track-panel").closePanelModel();
					$("#footer-panel").css("display","none");
					_generateTrackData(result);
				}else{
					Message.alert({Msg:"暂无该车辆历史轨迹!",isModal: true}); 
				}
			},
			error: function(e) {
				Message.alert({Msg:'获取数据失败!',isModal: true}); 
			}
		});
	};
	//绘制历史轨迹 :封装数据并传入HthxMap.trackReplay中
	var _generateTrackData = function(result){
		var relPointArr = [];
		var shipArr = [];
		if(result.data.length>0){
			var carTraces = result.data.reverse();
			var dataLen = carTraces.length;
			for(var i = 0; i < dataLen; i++){
//				var transResult = initProjTransform(carTraces[i].longitude, carTraces[i].latitude,true);
				var transResult = ol.proj.transform([parseFloat(carTraces[i].longitude),parseFloat(carTraces[i].latitude)], "EPSG:4326", HthxMap.Settings.projection);
			//	var coor = HthxMap.keepCoorsLen([[carTraces[i].longitude, carTraces[i].latitude]], bizCommonVariable.coorsLen);
				var coor = HthxMap.keepCoorsLen([[transResult[0], transResult[1]]], bizCommonVariable.coorsLen);
				var arr = {
							"x":coor[0][0],
							"y":coor[0][1],	
						    "recordTime":carTraces[i].gpstime,
							carName : carTraces[i].carrierName,
							//shipType : carTraces[i].shipType,
							terminalType : carTraces[i].terminalType,
							status: HthxMap.Settings.onOffLine.onLine,
							shipDir :parseFloat(carTraces[i].direction),
							//shipDir :parseFloat(carTraces[i].direction),
							pop_carSpeed : carTraces[i].speed,
							//heading : carTraces[i].trueHeading,
							id: carTraces[i].carrierName,
							terminalId: carTraces[i].terminalId,
							"alarmRecords":carTraces[i].alarms  //历史轨迹播放时增加报警图标显示及报警信息查看 xinzw
						};
				relPointArr.push(arr);
			}
		}else{
			Message.alert({Msg:'没有该车辆轨迹数据!',isModal: true}); 
			return;
		}
		var resultTmp = {
			pointsArray: JSON.stringify(relPointArr),
			features: null
		};
//		bizTrackReplay.saveResult(resultTmp);
		HthxMap.trackReplay(map, relPointArr, _trackDiv, _leadingOut);
	};
	
	//轨迹渔船弹出框
	var _trackDiv = function(feature){
		var properties = feature.getProperties();
		var transResult = initProjTransform(properties.pop_lon,properties.pop_lat,false);
		if(properties.pop_lon){
			lon = toolMethod.floatToGreenMs(transResult[0][0]);
			lat = toolMethod.floatToGreenMs(transResult[0][1]);
		}
		//var name = getTerminalTypeName(properties);
		//请求渔船图片
	//	var imgURL = getImgForShip(properties.id);
		var strs = '<div id="boatpopup" class="panel seapositionDialog">'+
					'<div id="boatpopup-header" class="panel-heading">'+
//						'<label id="boatname" class="panle-title">'+ properties.carName +
						'<label id="boatname" class="panle-title">'+
			    		'</label>'+
			    		'<label id="boatpopup-closer" class="panle-head-css panle-close" onclick="removeClickPop();">×</label>'+
					'</div>'+
					'<div id="boatpopup-content" class="panel-body bubbleboxcontent">'+
						'<div class="customDialogtriangle"></div>'+
			    		'<ul id="boatinfolist" class="bubbleboxMsg clearfix trackReplayPop" style="border:1px solid grey;border-radius:2px;margin-top:5px">'+
			    			'<li style="float:none;font-size:13px;">北纬：'+lat+',  东经：'+lon+'</li>'+
			    			'<li style="font-size:13px;">方向：'+properties.shipDir+'(度)</li>'+
			    			'<li style="float:none;font-size:13px;">速度：'+parseFloat(properties.pop_carSpeed).toFixed(1)+'(km/h)</li>'+
			    		'</ul>'+	
					'</div>'+
				'</div>';
		return strs;
	};
	
	
	
	//导出轨迹点
	var _leadingOut = function(shipId, startTime, endTime, terminalType){
		window.location.href = HthxMap.Settings.rest+'shipOrientate/exportTrace?shipId='+shipId+'&beginTime='+startTime+'&endTime='+endTime+'&terminalType='+terminalType;
	};	
		
	return {
		init:_init
	};
})(jQuery);
//终端尚未开放
var notFinal=(function($) {
	var $notFinalPanel     = $("#notFinal-panel");
	var _showPanel = function(){
		$notFinalPanel.showPanelModel("详情");
	}; 
	return{
		showPanel:_showPanel
	};
})(jQuery);



/**
 * 车辆路径
 */
var carRoute = (function($) {
	// 请求路径
	var urlPath = {
		getCarRouteUrl : $.backPath + '/wayBill/findRoute/'
	};
	
	var _getRoute = function (waybillId) {
		if (waybillId ){
			$.ajax({
				type : 'get',
				url  :  urlPath.getCarRouteUrl + waybillId,
				async: false,
				success : function(json) {
					var data = json.data;
					if (data) {
						var tmpPoint = JSON.parse(data.points);
					    var len = tmpPoint.length;
					    var coor;
					    coor = drawLine([tmpPoint], creatStyle.routeDrawStyle(),waybillId);   //线
					    //起点和终点图标
					    initCarRouteLayer(map, monitLayer, data);
					    
					    //画线
					    //coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
	
					}
				}
			});
		}
	};
   
	
	var drawLine = function(points, featureStyle,waybillId){
		var pts = [];    //存放所有转化的点
		var geo = []; 
		var style = featureStyle || creatStyle.markerDrawStyle();
		points = points[0];
		//转换成浮点数
		for(var i = 0; i < points.length; i++){
			var lon = points[i][0];
			var lat = points[i][1];
			if(!(lon instanceof Number)){
				lon = parseFloat(lon);
			}
			if(!(lat instanceof Number)){
				lat = parseFloat(lat);
			}
			pts.push([lon, lat]);
		}
		if(!pts){
			pts = points;
		}
		var a = [];
		for(var i = 0; i < pts.length; i++){
			a.push([pts[i][0], pts[i][1]]);
		}
		geo.push(a);
		if(geo[0] && geo[0].length <= 1){
			return;
		}
		var ls = new ol.Feature(new ol.geom.LineString(geo[0]));
		ls.setStyle(style);	
		ls.set("clear", false);  //不可删除
		ls.setId(waybillId);
		var featureLayer =  HthxMap.getLayerById("featureLayer");
		featureLayer.getSource().addFeature(ls);
		var interiorPoint = ls.getGeometry().getCoordinates()[0];
		return interiorPoint;
	};

	return {
		getRoute : _getRoute
	};
})(jQuery);






















