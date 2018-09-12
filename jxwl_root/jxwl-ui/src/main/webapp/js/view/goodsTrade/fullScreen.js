/**
 * @description:大屏展示
 * @author: 肖琼仙
 * @time: 2016-9-2
 */

var fullScreen = (function(){
	//模板
	var $tradeTrendsDataTemp1 = $("#tradeTrendsDataTemp1");
	var $tradeTrendsDataTemp2 = $("#tradeTrendsDataTemp2");
	var $tradeTrendsData = $("#tradeTrendsData");
	var $goodsSourceDataTemp = $("#goodsSourceDataTemp");
	var $goodsSourceData = $("#goodsSourceData");
	//请求路径
	var urlPath = {
			findTradeTrendUrl:$.backPath + "/orderManage/findAllOrders",
			findGoodsSourceUrl:$.backPath + "/srcGoods/findAll"
	};
	//变量
	var tradeTrendsLoadNum = 20, tradeTrendsLoadPage = 1,goodsSourceDataLoagNum = 20,goodsSourceDataLoagPage = 1;
	//初始化
	var _init = function(){
		//初始化数据
		_initTradeTrendData(tradeTrendsLoadPage,tradeTrendsLoadNum);
		_initGoodsSourceData(goodsSourceDataLoagPage,goodsSourceDataLoagNum);
		//每隔半小时更新一次物流交易数据函数
		window.setInterval(_ajaxTradeTrendData,1000*60*30);
		//每隔半小时更新一次货源信息数据函数
		window.setInterval(_ajaxGoodsSourceData,1000*60*30);
		//全屏页面切换
		//_changeFullScreen();
		if(location.pathname == '/JXWL/view/goodsTrade/fullScreen.html') {
			$(".button_top > a:nth(0)").addClass("buttonActive");
		}
		/*var timerFull = window.setTimeout(function(){
			location.pathname = '/JXWL/view/goodsTrade/fullScreenTwo.html';
		},90000);*/
		
		var timerData = window.setTimeout(_dataScroll,2000);
		
		$('.main-container .main-right').css({'height':'536px'});
		
	};
	
	var _dataScroll = function(){
		var tradeTrendsData = document.getElementById("tradeTrendsDataContainer");
		var tradeTrendsData1 = document.getElementById("tradeTrendsData");
		var tradeTrendsData2 = document.getElementById("tradeTrendsData2");
		tradeTrendsData2.innerHTML = tradeTrendsData1.innerHTML;
		tradeTrendsData1.style.height = tradeTrendsData.offsetHeight+"px";
		tradeTrendsData2.style.height = tradeTrendsData.offsetHeight+"px";
		window.setInterval(function(){
			if(tradeTrendsData.scrollTop>=tradeTrendsData.offsetHeight){
				tradeTrendsData.scrollTop = 0;
			}else{
				tradeTrendsData.scrollTop++;
			}
		},50);
		
		var goodsSourceData = document.getElementById("goodsSourceDataContainer");
		var goodsSourceData1 = document.getElementById("goodsSourceData");
		var goodsSourceData2 = document.getElementById("goodsSourceData2");
		goodsSourceData2.innerHTML = goodsSourceData1.innerHTML;
		goodsSourceData1.style.height = goodsSourceData.offsetHeight+"px";
		goodsSourceData2.style.height = goodsSourceData.offsetHeight+"px";
		window.setInterval(function(){
			if(goodsSourceData.scrollTop>=goodsSourceData.offsetHeight){
				goodsSourceData.scrollTop = 0;
			}else{
				goodsSourceData.scrollTop++;
			}
		},50);
	}
	
	
	//初始化加载物流交易数据
	var _initTradeTrendData = function(page,rows){
		var data="";
		//请求后端数据
		$.ajax({
			url:urlPath.findTradeTrendUrl,
			type:"POST",
			dataType:"json",
			data:{
				"page":page,
				"rows":rows
			},
			async:false,
			success:function(result){
				if(result.code === 1){
					data = result.data;
				}
			}
		});
		var dataTempHtml = "";
		for (var i = 0, len = data.length; i < len; i++) {
			if( data[i].tradeStatus == "110001005"){//已签订订单
				dataTempHtml += $.temp1($tradeTrendsDataTemp1.html(), data[i]);
			}else if( data[i].tradeStatus == "110001006"){//交易完成订单
				dataTempHtml += $.temp1($tradeTrendsDataTemp2.html(), data[i]);
			}
			
		}
		//dataTempHtml += "<div class='tradeTrendsRow row'>&nbsp;</div>";
		$tradeTrendsData.html(dataTempHtml);
	};
	
	//加载物流交易数据
	var _ajaxTradeTrendData = function(){
		_initTradeTrendData(tradeTrendsLoadPage,tradeTrendsLoadNum);
	};
	
	
	//初始化加载货源信息数据
	var _initGoodsSourceData = function(page,rows){
		$goodsSourceData.html("");
		var data="";
		//请求后端数据
		$.ajax({
			url:urlPath.findGoodsSourceUrl,
			type:"GET",
			dataType:"json",
			data:{
				"page":page,
				"rows":rows
			},
			async:false,
			success:function(result){
				if(result.code === 1){
					data = result.data;
				}
			}
		});
		var goodsSourceTempHtml = "";
		for (var i = 0, len = data.length; i < len; i++) {
			goodsSourceTempHtml += $.temp1($goodsSourceDataTemp.html(), data[i]);
		}
		//goodsSourceTempHtml += "<div class='' style='height:50px;width:100px;'>fdsfsfdsfdsfsfsf&nbsp;<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>";
		$goodsSourceData.html(goodsSourceTempHtml);
	};
	
	//加载货源信息数据
	var _ajaxGoodsSourceData = function(){
		_initGoodsSourceData(goodsSourceDataLoagPage,goodsSourceDataLoagNum);
	};
	_init();
})();