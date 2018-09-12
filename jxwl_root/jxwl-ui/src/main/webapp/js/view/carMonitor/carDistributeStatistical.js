/**
 * @description:黑名单管理
 * @author: guqh
 * @time: 2016-7-26
 */

// 报警管理信息
var carDistributeStatistical = (function($) {
	// 表格对象
	var $statisticsCarTable = $("#statisticsCarTable");
	var $statisticsCarPager = $("#statisticsCarPager");
	// 柱状图对象
	var $statisticsColCarArea = $("#statistics_col_carArea");
//	搜索栏
	var $carStatisticalSearch = $("#carStatistical-search");
	var $carSearchClick = $("#carSearchClick");
	var $searchName = $("#plateNumber-search");
	var $chooseAreaSearch = $("#chooseArea-search");
	var loadOne = false;
	
	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/statisticalAnalysis/findVehicleInOutAreaStatistics',
		countColUrl   : $.backPath + '/statisticalAnalysis/findVehicleAreaStatistics',
		findDictByCodeUrl : $.backPath + "/statisticalAnalysis/findDictByCode/008001"
	};
	
	// 数据初始化
	var _init = function() {
		//初始化柱状图
		_carTriangle();
	    // 初始化表格
 	    _initTable();
 	   if (!loadOne) {
    		//下拉列表
			dicObj = $.getData(urlPath.findDictByCodeUrl);
			$chooseAreaSearch.settingsOptions(dicObj, false);
			//点击查询事件
			$carSearchClick.bind("click", _searchHandler);
			//回车搜索
	        $("#carStatistical-search input").keyup(function (e) {
	            if (e.keyCode === 13) {
	            	_searchHandler();
	            }
	        });
    	 }else{
    		 _searchHandler();
    	 }
		 loadOne = !loadOne;
	};

	/**
	 * 初始化表格
	 */

    var _initTable = function() {
    	$statisticsCarTable.jqGrid({
            url      : urlPath.findByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            colNames : ["车牌号", "所属企业", "区域", "进入时间", "离开时间", "进/出情况"],
            colModel : [
                {name: "carrierName",index: "1", align: "center", width: "300px", sortable: false,formatter : function(cellValue,
												options, rowObject) {
											if(topMenuMag.isLogisticsEnterpriseUser() || topMenuMag.isChemicalEnterpriseUser() && userFlag){
												userFlag = false;
												$searchName.val(cellValue);
												$searchName.attr("readonly","readonly");
												
											}
											return cellValue;
										}},
                {name: "enpName",         index: "3", align: "center", width: "300px", sortable: false},
                {name: "areaName",        index: "4", align: "center", width: "300px", sortable: false},
                {name: "inTime",     index: "5", align: "center", width: "200px", sortable: false},
                {name: "outTime",   index: "6", align: "center", width: "200px", sortable: false},
                {name: "inOutStatus",      index: "7", align: "center", width: "223px", sortable: false}
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 8,
            rowList    : [8],
            pager      : "#statisticsCarPager",
			onSelectRow: function(rowid) {
				
			},
            gridComplete:function(){
            	//配置权限
            	$.initPrivg();
            }
        }).resizeTableWidth();
    };	
	
 // 查询数据信息
	var _searchHandler = function() {
		$statisticsCarTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"carrierName":$searchName.val(),
				"areaId":$chooseAreaSearch.val()
			},
			page : 1
		}).trigger("reloadGrid");
	};
	
	/**
	 * 获取柱状图数据
	 */
   
    var _ajax4Data = function() {
		var val = null;
        $.ajax({
            url: urlPath.countColUrl,
            type: 'POST',
            async: false
        }).done(function (json) {
            if (json.code === 1) {
            	val = json.data;
            } else {
                $.validateTip(json);
            }		
        });
        
        return val;
	};

//	组装柱状图数据
	var _carTriangle = function() {
		var vals = _ajax4Data();
		if (vals && 0 < vals.length) {
			var objs   = {};
			var names  = [];
			var totals  = [];
			for (var index in vals) {
				var obj = vals[index];
				names.push(obj.areaName);
				totals.push(obj.totalCar);
			}
			objs.names  = names;
			objs.totals = totals;
			_carColumn(objs);
		}
	};
	
	
	
	
	/**
	 * 车辆柱状图
	 */

	var _carColumn = function(data) {
		$statisticsColCarArea.highcharts({
            chart: {
                type: 'column',
                backgroundColor:"rgba(0, 0, 0, 0)"
            },
            colors: ['rgba(180, 249, 197, 1)'],
            title: {
                text: '车辆区域分布统计图',
                style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	height:'300px',
	            	fontFamily: 'Microsoft YaHei'
	            }
	        },
	        
            xAxis: {
                categories:data.names,
                lineColor:"#fff",
				labels:{
					rotation: 0,
					align:'center',
					style:{
						color:"#fff",
						fontFamily: 'Microsoft YaHei'
					}
				}
	        },
            yAxis: {
                min: 0,
                title: {
                    text: '车辆数 ( 辆 )'
                },
                lineColor:"#fff",
	            labels:{
	            	style:{
	            		color:"#fff"
	            	}
	            }
	        },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [{
                name: '车辆数',
                showInLegend:false,//不显示图例
               data:data.totals
            }]
        });
    };
    
  // 返回函数
	return {
		init:_init
	};
})(jQuery);
