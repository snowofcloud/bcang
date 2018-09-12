/**
 * @description:大屏展示
 * @author: 肖琼仙
 * @time: 2016-9-2
 */

var fullScreenFour = (function(){
	//模板
	var $vehicleSourceDataTemp = $("#vehicleSourceDataTemp");
	var $vehicleSourceData = $("#vehicleSourceData");
	//请求路径
	var urlPath = {
			findVehicleSourceUrl:$.backPath + "/vehicleSource/findByPage"
	};
	//变量
	vehicleSourceDataLoagNum = 20,vehicleSourceDataLoagPage = 1;
	//初始化
	var _init = function(){
		//初始化数据
		_initVehicleSourceData(vehicleSourceDataLoagPage,vehicleSourceDataLoagNum);
		//每隔半小时更新一次车源信息数据函数
		window.setInterval(_ajaxVehicleSourceData, 1000*60*30);
		//全屏页面切换
		//_changeFullScreen();
		if(location.pathname == '/JXWL/view/goodsTrade/fullScreenFour.html') {
			$(".button_top > a:nth(3)").addClass("buttonActive");
		}
		var timerFull = window.setTimeout(function(){
			location.pathname = '/JXWL/view/goodsTrade/fullScreen.html';
		},90000);
		
		var timerData = window.setTimeout(_dataScroll,2000);
		
	};
	
	var _dataScroll = function(){
		var vehicleSourceData = document.getElementById("vehicleSourceDataContainer");
		var vehicleSourceData1 = document.getElementById("vehicleSourceData");
		var vehicleSourceData2 = document.getElementById("vehicleSourceData2");
		vehicleSourceData2.innerHTML = vehicleSourceData1.innerHTML;
		vehicleSourceData1.style.height = vehicleSourceData.offsetHeight+"px";
		vehicleSourceData2.style.height = vehicleSourceData.offsetHeight+"px";
		window.setInterval(function(){
			if(vehicleSourceData.scrollTop>=vehicleSourceData.offsetHeight){
				vehicleSourceData.scrollTop = 0;
			}else{
				vehicleSourceData.scrollTop++;
			}
		},50);
	};
	
	//初始化加载车源信息数据
	var _initVehicleSourceData = function(page,rows){
		$vehicleSourceData.html("");
		var data="";
		//请求后端数据
		$.ajax({
			url:urlPath.findVehicleSourceUrl,
			type:"POST",
			dataType:"json",
			data:{
				"page":page,
				"rows":rows
			},
			async:false,
			success:function(result){
				if(result.code === 1){
					data = result.rows;
				}
			}
		});
		var vehicleSourceTempHtml = "";
		var s = '';
		for (var i = 0, len = data.length; i < len; i++) {
			if(data[i].goodstype != null && data[i].quantity != null){
				s = '';
				var types = data[i].goodstype.split(',');
				var quantitys = data[i].quantity.split(',');
				for(var j = 0; j < types.length; j++){
					if(types[j] != '' || quantitys[j] != ''){
						s += types[j] + ':' + quantitys[j] + '吨； ';
					}
        		}
				//data[i].goodsTypeAndQuantity = types[0]+':'+quantitys[0]+'吨； '+types[1]+':'+quantitys[1]+'吨； '+types[2]+':'+quantitys[2]+'吨 ';
				data[i].goodsTypeAndQuantity = s;
			}else{
				data[i].goodsTypeAndQuantity='';
			}
			vehicleSourceTempHtml += $.temp1($vehicleSourceDataTemp.html(), data[i]);
		}
		//vehicleSourceTempHtml += "<div class='' style='height:50px;width:100px;'>fdsfsfdsfdsfsfsf&nbsp;<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>";
		$vehicleSourceData.html(vehicleSourceTempHtml);
	};
	
	//加载车源信息数据
	var _ajaxVehicleSourceData = function(){
		_initVehicleSourceData(vehicleSourceDataLoagPage,vehicleSourceDataLoagNum);
	};
	_init();
})();