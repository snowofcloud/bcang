/**
 * @description:报警管理
 * @author: guqh
 * @time: 2016-7-26
 */

//报警管理信息
var ptStatistical = (function ($) {
	var $ptTable = $("#ptTable");
	var $ptPager = $("#ptPager");
    var urlPath = {
        findByPageUrl: $.backPath + '/statisticalAnalysis/findPtStatistics'
    };
    //数据初始化
    var _init = function () {
    	//加载表格
        _initPtTable();
        //注册回车搜索事件
        $("#").keyup(function (e) {
            e.preventDefault();
            if (e.keyCode === 13) {
                _searchHandler();
            }
            return false;
        });
    };
   //表格
    var _initPtTable = function () {
    	$ptTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
//            postData: {
//      	       "alarmId":alarmId
//            },
            colNames: ["平台名称", "是否在线", "入网车辆数", "在线车辆数"],
            colModel: [
                {name: "name", index: "1", align: "center", width: "300px", sortable: false},
                {name: "isOnline", index: "1", align: "center", width: "300px", sortable: false},
                {name: "joinCarNum", index: "3", align: "center", width: "300px", sortable: false},
                {name: "onlineCarNum", index: "4", align: "center", width: "300px", sortable: false}
            ],
            loadonce: false,
            rownumbers:true,
            viewrecords: true,
            autowidth: true,
            height: true,
            rowNum: 10,
            rowList: [5, 10, 15],
            pager: "#ptPager",
            gridComplete:function(){
            	//配置权限
            	 $.initPrivg();
            }
        }).resizeTableWidth();
    };

    //返回函数
    return {
    	init:_init
       
    };
})(jQuery);
