/**
 * @description:运单统计
 * @author: mjw
 * @time: 2017-7-25
 */

// 报警管理信息
var wayBillStatistical = (function($) {
	// 图表对象
	var $statisticsPieEnp = $("#wayBillStatistical_pie_lac");
	var $statisticsPieCar = $("#wayBillStatistical_pie_cac");
	var $statisticsPieWbi = $("#wayBillStatistical_pie_has");
	
	// 表格对象
	var $statisticsTable = $("#wayBillStatisticalTable");
	var $statisticsPager = $("#wayBillStatisticalPager");
	
	// table 列表视图
	var $tableView  = $("#table_view2");

	
	var d = new Date(); // 时间对象
	var s = d.toISOString().split("-", 2).join("-") + "-01"; // 当月开始日期
	var e = d.toISOString().split("T")[0]; // 当前时间
	
	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/wayBillStatistical/findByPage',
		statisticsUrl : $.backPath + '/wayBillStatistical/statistics'
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
//		$("#back-list").bind("click", _backList);
		$('#wayBillTimeChoice').change(function(){
			_getPieData();
		});
	};

	/**
	 * 初始化表格
	 */
    var _initTable = function() {
    	$statisticsTable.jqGrid({
            url      : urlPath.findByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            colNames : ["车牌号", "本周", "本月", "本年", "本周", "本月", "本年", "本周", "本月", "本年"],
            colModel : [
                {name: "licencePlateNo",      index: "1", align: "center", width: "30px", sortable: false},
                {name: "wayBillWeek",         index: "2", align: "center", width: "30px", sortable: false},
                {name: "wayBillMonth",        index: "3", align: "center", width: "30px", sortable: false},
                {name: "wayBillYear",         index: "4", align: "center", width: "30px", sortable: false},
                {name: "dangerousTypeWeek",   index: "5", align: "center", width: "30px", sortable: false},
                {name: "dangerousTypeMonth",  index: "6", align: "center", width: "30px", sortable: false},
                {name: "dangerousTypeYear",   index: "7", align: "center", width: "30px", sortable: false},
                {name: "dangerousWeightWeek", index: "8", align: "center", width: "30px", sortable: false},
                {name: "dangerousWeightMonth",index: "9", align: "center", width: "30px", sortable: false},
                {name: "dangerousWeightYear", index: "10",align: "center", width: "45px", sortable: false}
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 8,
            rowList    : [8],
            pager      : "#wayBillStatisticalPager",
			onSelectRow: function(rowid) {
			},
            gridComplete:function(){
            	//配置权限
            	$.initPrivg();
            }
        }).resizeTableWidth();
    	
    	//$statisticsTable.jqGrid("destroyGroupHeaders",false);
    	if ($('#table_view2 .ui-jqgrid-hdiv .jqg-second-row-header').length == 0) {
    		$statisticsTable.jqGrid("setGroupHeaders",{
  			 useColSpanStyle : true,
  			 groupHeaders : [
  	              {startColumnName:"wayBillWeek",         numberOfColumns:3, titleText:"运单数量"},
  	              {startColumnName:"dangerousTypeWeek",   numberOfColumns:3, titleText:"危险品种类数量 "},
  	              {startColumnName:"dangerousWeightWeek", numberOfColumns:3, titleText:"危险品总吨数 "}
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
		var wayBillTimeChoice = $("#wayBillTimeChoice").val();
		// 提交申请
		$.ajax({
			url : urlPath.statisticsUrl + '/' + wayBillTimeChoice,
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
	 * 组装物流企业运单数据
	 */
	var _assembleEnpData = function(arr) {
		var enpData = [];
		if (arr && 0 < arr.length) {
			var o = arr[0];
			var moreWbi = {};
			moreWbi.name = o.ENTERPRISE_NAME;
			moreWbi.y = o.TIMES;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			enpData.push(moreWbi);
			
			for (var i=1; i<arr.length; i++) {
				var wbi = [];
				var obj = arr[i];
				wbi.push(obj.ENTERPRISE_NAME);
				wbi.push(obj.TIMES);
				
				enpData.push(wbi);
			} 
			return enpData;
		}else {
			var moreWbi = {};
			moreWbi.name = 'a';
			moreWbi.y = 1;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			enpData.push(moreWbi);
			return enpData;
		}
	};
	
	
	/**
	 * 组装化工企业运单数据
	 */
	var _assembleCarData = function(arr) {
		var carData = [];
		if (arr && 0 < arr.length) {
			var o = arr[0];
			var moreWbi = {};
			moreWbi.name = o.ENTERPRISE_NAME;
			moreWbi.y = o.TIMES;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			carData.push(moreWbi);
			
			for (var i=1; i<arr.length; i++) {
				var wbi = [];
				var obj = arr[i];
				wbi.push(obj.ENTERPRISE_NAME);
				wbi.push(obj.TIMES);
				
				carData.push(wbi);
			}
			return carData;
		}else{
			var moreWbi = {};
			moreWbi.name = 'a';
			moreWbi.y = 1;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			carData.push(moreWbi);
			return carData;
		}
	};
	
	
	/**
	 * 组装各种危险品数据
	 */
	var _assembleWbiData = function(arr) {
		var wbiData = [];
		if (arr && 0 < arr.length) {
			var o = arr[0];
			var moreWbi = {};
			moreWbi.name = o.GOODSNAME;
			moreWbi.y = o.TIMES;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			wbiData.push(moreWbi);
			
			for (var i=1; i<arr.length; i++) {
				var wbi = [];
				var obj = arr[i];
				wbi.push(obj.GOODSNAME);
				wbi.push(obj.TIMES);
				
				wbiData.push(wbi);
			} 
			return wbiData;
		}else{
			var moreWbi = {};
			moreWbi.name = 'a';
			moreWbi.y = 1;
			moreWbi.sliced = true;
			moreWbi.selected = true;
			wbiData.push(moreWbi);
			return wbiData;
		}
	};
	
	
	/**
	 * 物流企业运单数量
	 */
	var _enpPie = function(data) {
		if(data[0].name == 'a'){
			$('#wayBillStatistical_pie_lac').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            backgroundColor:"rgba(0, 0, 0, 0)"
		        },
		        colors: ['rgba(255, 255, 255, 1)'],
		        title: {
		        	text: '物流企业运单暂无数据',
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
		    	    	return '<b>' + this.point.name + '</b>：' + Highcharts.numberFormat(this.percentage, 1) + '% (' +Highcharts.numberFormat(this.y, 0, ',') + '单)';
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
		                    	color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'white'
		                    }
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            data: []
		        }]
		    });
		}else{
			$('#wayBillStatistical_pie_lac').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            backgroundColor:"rgba(0, 0, 0, 0)"
		        },
		        colors: ['rgba(19, 174, 103, 1)', 'rgba(180, 249, 197, 1)',
		                 'rgba(163, 201, 3, 1)',   'rgba(0, 134, 255, 1)',
		                 'rgba(125, 216, 217, 1)', 'rgba(255, 115, 0, 1)',
		                 'rgba(255, 76, 41, 1)',   'rgba(240, 186, 7, 1)',
		                 'rgba(144, 237, 125)',    'rgba(128, 133, 233)'],
		        title: {
		        	text: '物流企业运单数量',
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
		    	    	return '<b>' + this.point.name + '</b>：' + Highcharts.numberFormat(this.percentage, 1) + '% (' +Highcharts.numberFormat(this.y, 0, ',') + '单)';
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
		}
	    
	};
	
	
	
	/**
	 * 化工企业运单数量
	 */
	var _carPie = function(data) {
		if(data[0].name == 'a'){
			$('#wayBillStatistical_pie_cac').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            backgroundColor:"rgba(0, 0, 0, 0)"
		        },
		        colors: ['rgba(0, 0, 0, 1)'],
		        title: {
		        	text: '化工企业运单暂无数据',
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
		            data: []
		        }]
		    });
		}else{
			$('#wayBillStatistical_pie_cac').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            backgroundColor:"rgba(0, 0, 0, 0)"
		        },
		        colors: ['rgba(248, 182, 45, 1)', 'rgba(255, 107, 49, 1)',
		                 'rgba(163, 201, 3, 1)',   'rgba(0, 134, 255, 1)',
		                 'rgba(125, 216, 217, 1)', 'rgba(255, 115, 0, 1)',
		                 'rgba(255, 76, 41, 1)',   'rgba(240, 186, 7, 1)',
		                 'rgba(144, 237, 125)',    'rgba(128, 133, 233)'],
		        title: {
		        	text: '化工企业运单数量',
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
		}
	    
	};	
	
	
	/**
	 * 各类危险品运单数量
	 */
	var _wbiPie = function(data) {
		if(data[0].name == 'a'){
			$('#wayBillStatistical_pie_has').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            backgroundColor:"rgba(0,0,0,0)"
		        },
		        colors: ['rgba(0, 0, 0, 1)'],
		        title: {
		        	text: '各类危险品运单暂无数据',
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
		            data: [] 
		        }]
		    });
		}else{
			$('#wayBillStatistical_pie_has').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            backgroundColor:"rgba(0,0,0,0)"
		        },
		        colors: ['rgba(163, 201, 3, 1)',   'rgba(0, 134, 255, 1)',
		                 'rgba(125, 216, 217, 1)', 'rgba(255, 115, 0, 1)',
		                 'rgba(255, 76, 41, 1)',   'rgba(240, 186, 7, 1)',
		                 'rgba(144, 237, 125, 1)', 'rgba(128, 133, 233, 1)',
		                 'rgba(186, 2, 125, 1)', 'rgba(0, 133, 87, 1)', 'rgba(61, 30, 233, 1)'],
		        title: {
		        	text: '各类危险品运单数量',
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
		}
	    
	};
	

	// 返回函数
	return {
		init: _init,
		getPieData: _getPieData
	};
	
})(jQuery);
