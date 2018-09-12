/**
 * <p>Description: 条件查询车辆情况</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2016-07-28 </p>
 * @author:wl
 * @param：
*/
var mapIdexCarSearch = (function($){
		var $mapIdexCarSearchBigBtn = $("#mapIdexCarSearchBigBtn");
		var $mapSearchBtn = $("#mapSearch").find("button");
		var $searchContent = $(".search-content");
		var $searchCarInput = $("#searchCarInput");//搜索输入框
		var $carsearchContent = $("#carsearch-content");//搜索结果div
		var $panelClose = $(".panel-close");//关闭
		var $vehicleMessage = $("#vehicleMessage");
		var $vehicleMessageShow = $("#vehicleMessageShow");
		var $vehicleSearch= $("#vehicle-search");
		var urlPath={
			findVehicleDataUrl: $.backPath + '/dangerVehicle/findByLicencePlateNoOrWayBill',
			findVehicleByNameUrl: $.backPath + '/wayBill/findByCarrierName/'
		};
		
		//初始化
		var _init = function(){
			$mapIdexCarSearchBigBtn.bind("click", _showRightInput);
			$mapSearchBtn.bind("click",_showSearchResult);
			$vehicleMessageShow.delegate(".searchInfor","click",_findVehicleByName);
			
		};
		//显示右侧搜索栏
		var _showRightInput = function(){
			var flag = $("#mapSearch").attr("class");
			if(flag==='row'){
				$("#mapSearch").animate({width:"0px"}).addClass("hidden");
				$("#mapSearch").find("input").val("");
				$searchContent.animate({height:"0px"}).addClass("hidden");
			}else{
				$("#mapSearch").removeClass("hidden").animate({width:"392px"});
				$("#mapSearch").find("input").val("");
			}
			
		};
		//显示搜索结果
		function _showSearchResult (){
			//清空车辆信息历史数据
			$vehicleMessageShow.html("");
			$searchContent.removeClass("hidden");
			//请求后端数据
			var data="";
			$.ajax({
				url:urlPath.findVehicleDataUrl,
				type:"POST",
				dataType:"json",
				data:{
					"licencePlateNo":$vehicleSearch.val(),
					"page":1,
					"rows":5
				},
				async:false,
				success:function(result){
					if(result.code === 1){
						data = result.rows;
					}
				}
			});
			var dataTempHtml = "";
			if(data.length > 0){
				for (var i = 0, len = data.length; i < len; i++) {
					dataTempHtml += $.temp1($vehicleMessage.html(), data[i]);
				}
			}else{
				dataTempHtml = "没有车辆信息"
			}
			
			$vehicleMessageShow.html(dataTempHtml);
		};
		
		//关闭搜索结果按钮
		var _panelClose = function (){
			//清空车辆信息历史数据
			$vehicleMessageShow.html("");
			$searchContent.addClass("hidden");
		};
		
		//点击车辆信息在地图上显示车辆位置
		var _findVehicleByName = function(){
			/*var carName = $(this).find("span:eq(0)").text();
			//通过车牌号获取车辆位置信息
			var carLocation = "";
			$.ajax({
				url:urlPath.findVehicleByNameUrl+carName,
				type:"GET",
				dataType:"json",
				async:false,
				success:function(result){
					if(result.code === 1){
						carLocation = result.data;
					}
				}
			});*/
			
			//显示车辆位置
			var featureid = $(this).find("span:eq(0)").text();
	    	var e =  arguments.callee.caller.arguments[0] || event || window.event;
	    		//var feature = monitLayer.getSource().getFeatureById(featureid);
		    	if(!monitLayer.getSource().getFeatureById(featureid)){
		    		Message.alert({Msg: "无该车辆位置信息"});
		    		return;
		    	}
		    	topMonitorLayer.getSource().clear();
				topMonitorLayer.getSource().addFeature(monitLayer.getSource().getFeatureById(featureid));
				var feature = topMonitorLayer.getSource().getFeatureById(featureid);
    			var style = feature.getStyle();
    			if(style){	    				
					//闪一下
					feature.setStyle(featureStyles[featureid]);
					HthxMap.clickEvent(feature);
					setTimeout(function(){
    					HthxMap.removeShipStyle();
    				},700);
    				HthxMap.preFeature = feature;
    				var point = [feature.getProperties().longitude,feature.getProperties().latitude];
    				map.getView().setCenter(point);
    			}
		};
		
		_init();
		
		return{
			panelClose:_panelClose
		};
})(jQuery);


