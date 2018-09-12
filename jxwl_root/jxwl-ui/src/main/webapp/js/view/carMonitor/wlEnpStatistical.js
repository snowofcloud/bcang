/**
 * @description:黑名单管理
 * @author: guqh
 * @time: 2016-7-26
 */

// 报警管理信息
var wlEnpStatistical = (function($) {
	// 图表对象
	var $statisticsPieEnp = $("#statistics_pie_enp");
	var $statisticsPieCar = $("#statistics_pie_car");
	var $statisticsPieWbi = $("#statistics_pie_wbi");
	
	// 表格对象
	var $statisticsTable = $("#statisticsTable");
	var $statisticsPager = $("#statisticsPager");
	
	// 柱状图对象
	var $statisticsColCar = $("#statistics_col_car");
	var $statisticsColAlr = $("#statistics_col_alr");
	var $statisticsColWbi = $("#statistics_col_wbi");
	
	// table 列表视图
	var $tableView  = $("#table_view");
	// 柱状图视图
	var $columnView = $("#column_view");
	// 柱状图搜索
	var $searchBtn  = $("#searchBtn");
	
	var d = new Date(); // 时间对象
	var s = d.toISOString().split("-", 2).join("-") + "-01"; // 当月开始日期
	var e = d.toISOString().split("T")[0]; // 当前时间
	
	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/statisticalAnalysis/findByPage',
		statisticsUrl : $.backPath + '/statisticalAnalysis/statistics',
		countColUrl   : $.backPath + '/statisticalAnalysis/countColumn'
	};
	
	// 数据初始化
	var _init = function() {
		// 获取图表统计数据
		_getPieData();
		
		// 初始化表格
		_initTable();

		// 绑定事件
		_bindClick();
	};

	// 绑定click
	var _bindClick = function() {
		// 返回列表
		$("#back-list").bind("click", _backList);
		// 车辆柱状图
		$("#car_triangle").bind("click", _clickCarTriangle);
		// 报警柱状图
		$("#alr_triangle").bind("click", _clickAlrTriangle);
		// 运单柱状图
		$("#wbi_triangle").bind("click", _clickWbiTriangle);
	};

	/**
	 * 初始化表格
	 */
    var _initTable = function() {
    	$statisticsTable.jqGrid({
            url      : urlPath.findByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            colNames : ["企业名称", "港区分布", "车载终端安装率", "在线", "离线", "本月", "本季度", "本年", "本月", "本季度", "本年"],
            colModel : [
                {name: "enterpriseName",    index: "1", align: "center", width: "30px", sortable: false},
                {name: "portArea",          index: "2", align: "center", width: "30px", sortable: false, 
                	formatter:function(cellvalue,options,rowObject) {
                		if (cellvalue == "105001001") {
                			return "本地";
                		} else if (cellvalue = "105001002") {
                			return "外地";
                		}
                	}
                },
                {name: "installRate",       index: "2", align: "center", width: "30px", sortable: false},
                {name: "onlineCar",         index: "3", align: "center", width: "30px", sortable: false},
                {name: "offlineCar",        index: "4", align: "center", width: "30px", sortable: false},
                {name: "curMonthAlarm",     index: "5", align: "center", width: "30px", sortable: false},
                {name: "curQuarterAlarm",   index: "6", align: "center", width: "30px", sortable: false},
                {name: "curYearAlarm",      index: "7", align: "center", width: "30px", sortable: false},
                {name: "curMonthWaybill",   index: "8", align: "center", width: "30px", sortable: false},
                {name: "curQuarterWaybill", index: "9", align: "center", width: "30px", sortable: false},
                {name: "curYearWaybill",    index: "10",align: "center", width: "45px", sortable: false}
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 8,
            rowList    : [8],
            pager      : "#statisticsPager",
			onSelectRow: function(rowid) {
			},
            gridComplete:function(){
            	//配置权限
            	$.initPrivg();
            }
        }).resizeTableWidth();
    	
    	//$statisticsTable.jqGrid("destroyGroupHeaders",false);
    	if($('#table_view .ui-jqgrid-hdiv .jqg-second-row-header').length == 0){
    		$statisticsTable.jqGrid("setGroupHeaders",{
  			 useColSpanStyle : true,
  			 groupHeaders : [
  	              {startColumnName:"onlineCar",       numberOfColumns:2, titleText:"车辆总数 <span id='car_triangle' class='triangle'>"},
  	              {startColumnName:"curMonthAlarm",   numberOfColumns:3, titleText:"报警次数 <span id='alr_triangle' class='triangle'>"},
  	              {startColumnName:"curMonthWaybill", numberOfColumns:3, titleText:"运单总数 <span id='wbi_triangle' class='triangle'>"}
  			 ]
  		 }); 
    	}
    	 
    	  	
    };	
	
    
	// 返回列表
    var _backList = function () {
		$columnView.addClass("hidden");
		$columnView.find("div").addClass("hidden");
		$(".col_search *").addClass("hidden");
		$tableView.removeClass("hidden");
    	$statisticsTable.jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {},
            page    : 1
        }).trigger("reloadGrid");
    };
    
	// 获取图表统计数据
	var _getPieData = function() {
		// 提交申请
		$.ajax({
			url : urlPath.statisticsUrl,
			type : 'GET',
			async : false
		}).done(function(json) {
			if (json.code == 1) {
				var data = json.data;
				_enpPie(_assembleEnpData(data.enp));
				_carPie(_assembleCarData(data.car));
				_wbiPie(_assembleWbiData(data.wbi));
			} else {
				$.validateTip(json);
			}
		});
	};

	/**
	 * 组装企业数据
	 */
	var _assembleEnpData = function(json) {
		// 企业图
		var enpData = [];
		// 港区数据
		var localEnp = {};
		localEnp.name = "港区企业";
		localEnp.y = json.LOCALENP;
		localEnp.sliced = true;
		localEnp.selected = true;
		
		// 非港区数据
		var noLocalEnp = [];
		noLocalEnp.push('非港区企业');
		noLocalEnp.push(json.NOLOCALENP);
		
		enpData.push(localEnp);
		enpData.push(noLocalEnp);
		
		return enpData;
	};
	
	
	/**
	 * 组装车辆数据
	 */
	var _assembleCarData = function(json) {
		// 车辆图
		var carData = [];
		// 本地车辆数据
		var localCar = {};
		localCar.name = "本地车辆";
		localCar.y = json.LOCALCAR;
		localCar.sliced = true;
		localCar.selected = true;
		
		// 外地车辆数据
		var noLocalCar = [];
		noLocalCar.push('外地车辆');
		noLocalCar.push(json.NOLOCALCAR);
		
		carData.push(localCar);
		carData.push(noLocalCar);
		
		return carData;
	};
	
	
	/**
	 * 组装运单数据
	 */
	var _assembleWbiData = function(arr) {
		var wbiData = [];
		if (arr && 0 < arr.length) {
			var o = arr[0];
			var moreWbi = {};
			moreWbi.name = o.ENTERPRISE_NAME;
			moreWbi.y = o.TIMES;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			wbiData.push(moreWbi);
			
			for (var i=1; i<arr.length; i++) {
				var wbi = [];
				var obj = arr[i];
				wbi.push(obj.ENTERPRISE_NAME);
				wbi.push(obj.TIMES);
				
				wbiData.push(wbi);
			} 
		}
		
		return wbiData;
	};
	
	
	/**
	 * 物流企业港区分布图
	 */
	var _enpPie = function(data) {
	    $('#statistics_pie_enp').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            backgroundColor:"rgba(0, 0, 0, 0)"
	        },
	        colors: ['rgba(19, 174, 103, 1)', 'rgba(180, 249, 197, 1)'],
	        title: {
	        	text: '物流企业分布统计图',
	            style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	height:'300px',
	            	fontFamily: 'Microsoft YaHei'
	            }
	        },
	        tooltip: {
	    	    formatter: function() {
	    	    	return '<b>' + this.point.name + '</b>：' + 
	    	    		Highcharts.numberFormat(this.percentage, 1) + '% (' +
	    	    		Highcharts.numberFormat(this.y, 0, ',') + '家)';
	    	    }
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    formatter: function() {
	                    	return '<b>' + this.point.name + '</b> ：' + Highcharts.numberFormat(this.y, 0, ',') + '家';
	                    },
	                    style: {
	                    	color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'white'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            data: data
	        }]
	    });
	};
	
	
	
	/**
	 * 车辆分布统计图
	 */
	var _carPie = function(data) {
	    $('#statistics_pie_car').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            backgroundColor:"rgba(0, 0, 0, 0)"
	        },
	        colors: ['rgba(248, 182, 45, 1)', 'rgba(255, 107, 49, 1)'],
	        title: {
	        	text: '车辆分布统计图',
	            style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	fontFamily: 'Microsoft YaHei'
	            }
	        },
	        tooltip: {
	    	    formatter: function() {
	    	    	return '<b>' + this.point.name + '</b>：' + 
	    	    		Highcharts.numberFormat(this.percentage, 1) + '% (' +
	    	    		Highcharts.numberFormat(this.y, 0, ',') + '辆)';
	    	    }
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    formatter: function() {
	                    	return '<b>' + this.point.name + '</b> ：' + Highcharts.numberFormat(this.y, 0, ',') + '辆';
	                    },
	                    style: {
	                         color: 'white'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            data: data 
	        }]
	    });
	};	
	
	
	/**
	 * 各物流企业本年度运单占园区总运单数百分比
	 */
	var _wbiPie = function(data) {
	    $('#statistics_pie_wbi').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            backgroundColor:"rgba(0,0,0,0)"
	        },
	        colors: ['rgba(163, 201, 3, 1)',   'rgba(0, 134, 255, 1)',
	                 'rgba(125, 216, 217, 1)', 'rgba(255, 115, 0, 1)',
	                 'rgba(255, 76, 41, 1)',   'rgba(240, 186, 7, 1)',
	                 'rgba(144, 237, 125)',    'rgba(128, 133, 233)'],
	        title: {
	        	text: '各物流企业本年度运单占园区总运单数百分比',
	            style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	fontFamily: 'Microsoft YaHei'
	            }
	        },
	        tooltip: {
	    	    formatter: function() {
	    	    	return '<b>' + this.point.name + '</b>：' + 
	    	    		Highcharts.numberFormat(this.percentage, 1) + '% (' +
	    	    		Highcharts.numberFormat(this.y, 0, ',') + '单)';
	    	    }
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    formatter: function() {
	                    	return '<b>' + this.point.name + '</b> ：' + Highcharts.numberFormat(this.y, 0, ',') + '单';
	                    },
	                    style: {
	                         color: 'white'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            data: data 
	        }]
	    });
	};
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////柱状图  FIXME ///////////////////////////////////////////////////
	/**
	 * 点击车辆在线、离线三角图
	 */
	var _clickCarTriangle = function() {
		var search  = {};
		search.flag = "car";
		var vals = _ajax4Data(search);
		if (vals && 0 < vals.length) {
			var objs   = {};
			var names  = [];
			var onval  = [];
			var offval = [];
			for (var index in vals) {
				var obj = vals[index];
				names.push(obj.ENTERPRISE_NAME);
				onval.push(obj.ONS);
				offval.push(obj.OFFS);
			}
			objs.names  = names;
			objs.onval  = onval;
			objs.offval = offval;
			
			$columnView.removeClass("hidden");
			$statisticsColCar.removeClass("hidden");
			$statisticsColAlr.addClass("hidden");
			$statisticsColWbi.addClass("hidden");
			$tableView.addClass("hidden");
			$(".col_search *").addClass("hidden");
			
			_carColumn(objs);
		}
	};
	
	/**
	 * 点击报警三角图
	 */
	var _clickAlrTriangle = function() {
		$searchBtn.unbind("click");
		$("#serach_startTime").val(s);
		$("#serach_endTime").val(e);
		$searchBtn.bind("click", _clickAlrTriangleInit);
		_clickAlrTriangleInit();
	};
	var _clickAlrTriangleInit = function() {
		var search  = {};
		search.flag = "alarm";
		search.startTime = $("#serach_startTime").val();
		search.endTime   = $("#serach_endTime").val();
		var vals = _ajax4Data(search);
		if (vals && 0 < vals.length) {
			var objs  = {};
			var names = [];
			var times = [];
			for (var index in vals) {
				var obj = vals[index];
				names.push(obj.ENTERPRISE_NAME);
				times.push(obj.CUR_ALARM);
			}
			objs.names = names;
			objs.times = times;
			
			$columnView.removeClass("hidden");
			$statisticsColAlr.removeClass("hidden");
			$(".col_search *").removeClass("hidden");
			$statisticsColCar.addClass("hidden");
			$statisticsColWbi.addClass("hidden");
			$tableView.addClass("hidden");
			
			_alrColumn(objs);
		}
	};
	
	/**
	 * 点击运单三角形
	 */
	var _clickWbiTriangle = function() {
		$searchBtn.unbind("click");
		$("#serach_startTime").val(s);
		$("#serach_endTime").val(e);
		$searchBtn.bind("click", _clickWbiTriangleInit);
		_clickWbiTriangleInit();
	};
	var _clickWbiTriangleInit = function(){
		var search = {};
		search.flag = "waybill";
		search.startTime = $("#serach_startTime").val();
		search.endTime   = $("#serach_endTime").val();
		var vals = _ajax4Data(search);
		if (vals && 0 < vals.length) {
			var objs = {};
			var names = [];
			var times = [];
			for (var index in vals) {
				var obj = vals[index];
				names.push(obj.ENTERPRISE_NAME);
				times.push(obj.CUR_WAYBILL);
			}
			objs.names = names;
			objs.times = times;
			
			$columnView.removeClass("hidden");
			$statisticsColWbi.removeClass("hidden");
			$(".col_search *").removeClass("hidden");
			$statisticsColCar.addClass("hidden");
			$statisticsColAlr.addClass("hidden");
			$tableView.addClass("hidden");
			
			_wbiColumn(objs);
		}
	};
	
	/**
	 * 获取柱状图数据
	 */
	var _ajax4Data = function(searchData) {
		var val = null;
        $.ajax({
            url: urlPath.countColUrl,
            type: 'GET',
            async: false,
            data: searchData
        }).done(function (json) {
            if (json.code === 1) {
            	val = json.data;
            } else {
                $.validateTip(json);
            }
        });
        
        return val;
	};
	
	
	
	/**
	 * 车辆柱状图
	 */
	var _carColumn = function(data) {
		$statisticsColCar.highcharts({
            chart: {
                type: 'column',
	            backgroundColor:"rgba(0, 0, 0, 0)"
            },
            colors: ['rgba(19, 174, 103, 1)', 'rgba(180, 249, 197, 1)'],
            title: {
                text: '各企业车辆在线离线情况对比图',
	            style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	height:'300px',
	            	fontFamily: 'Microsoft YaHei'
	            }
            },
            xAxis: {
                categories: data.names,
	            lineColor:"#fff",
				labels:{
					rotation: -45,
					align:'right',
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
                name: '在线',
                data: data.onval
            }, {
                name: '离线',
                data: data.offval
            }]
        });
    };
    
    
    
	/**
	 * 报警柱状图
	 */
	var _alrColumn = function(data) {
       $statisticsColAlr.highcharts({
            chart: {
                type: 'column',
	            backgroundColor:"rgba(0, 0, 0, 0)"
            },
            colors: ['rgba(19, 174, 103, 1)', 'rgba(180, 249, 197, 1)'],
            title: {
                text: '各企业报警次数对比统计图',
	            style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	height:'300px',
	            	fontFamily: 'Microsoft YaHei'
	            }
            },
            xAxis: {
                categories: data.names,
	            lineColor:"#fff",
				labels:{
					rotation: -45,
					align:'right',
					style:{
						color:"#fff",
						fontFamily: 'Microsoft YaHei'
					}
				}
            },
            yAxis: {
                min: 0,
                title: {
                    text: '报警次数( 次 )'
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
                name: '报警次数',
                data: data.times
            }]
        });
    };    
	
	
    
	/**
	 * 报警柱状图
	 */
	var _wbiColumn = function(data) {
       $statisticsColWbi.highcharts({
            chart: {
                type: 'column',
	            backgroundColor:"rgba(0, 0, 0, 0)"
            },
            colors: ['rgba(19, 174, 103, 1)', 'rgba(180, 249, 197, 1)'],
            title: {
                text: '各企业完成运单数量统计对比图',
	            style:{
	            	color:"#fff",
	            	border:"3px solid #26AF6D",
	            	backgroundColor:"#26AF6D",
	            	height:'300px',
	            	fontFamily: 'Microsoft YaHei'
	            }
            },
            xAxis: {
                categories: data.names,
	            lineColor:"#fff",
				labels:{
					rotation: -45,
					align:'right',
					style:{
						color:"#fff",
						fontFamily: 'Microsoft YaHei'
					}
				}
            },
            yAxis: {
                min: 0,
                title: {
                    text: '运单次数( 次 )'
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
                name: '运单次数',
                data: data.times
            }]
        });
    };      

	

	

	// 返回函数
	return {
		init: _init
	};
	
})(jQuery);
