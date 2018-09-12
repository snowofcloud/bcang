/**
 * @description:日志管理
 * @author: 肖琼仙
 * @time: 2016-8-31
 */
var logManage = (function($) {
    // 表格
	var $logManageTable = $("#logManageTable");
	// 搜索栏
	var $logManageStartTime = $("#logManageStartTime");
	var $logManageEndTime = $("#logManageEndTime");
	var $logManageSearchBtn = $("#logManage-searchBtn");
	var $logDetailPanel = $("#logDetailPanel");
	var $logDetailForm = $("#logDetailForm");
	var $logManageOrderNo = $("#logManage-orderNo");

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + "/logManage/findByPage",
		findByIdUrl : $.backPath + "/logManage/findById/",
		// 流程
		saveLogUrl : $.backPath + "/logManage/saveLog/", /* 保存日志信息 */
		findDicCodeUrl : $.backPath + '/logManage/findDictByCode/004'
	};

	// 变量
	var loadOnce = true;
	var dicObj = "";

	var _init = function() {
		if (loadOnce) {
			dicObj = $.getData(urlPath.findDicCodeUrl);
			_initTable();
			$logManageSearchBtn.bind("click", _searchHandle);
			loadOnce = !loadOnce;
		} else {
			_searchHandle();
		}
	};

	// 初始化表格
	var _initTable = function() {
        var width = $("#orderManage").width();
		$logManageTable.jqGrid({
			url : urlPath.findByPageUrl,
			mtype : "POST",
			datatype : "JSON",
			colNames : [ "订单号", "化工企业名称", "物流企业名称", "变更后状态",  "记录时间", "日志内容", "操作"],
			colModel : [ 
			    {name : "orderNo", index : "1", align : "left", width : "30px", sortable : false}, 
			    {name : "hgEnterpriseName", index : "2", align : "center", width : "30px", sortable : false}, 
			    {name : "wlEnterpriseName", index : "3", align : "center", width : "30px", sortable : false}, 
			    {name : "tradeStatus", index : "4", align : "center", width : "30px", sortable : false,
					formatter : function(cellvalue, options, rowObject) {
						return $.getDicNameByCode(cellvalue, dicObj["110001"]);
					}}, 
				{name : "recordTime", index : "5", align : "center", width : "30px", sortable : false}, 
				{name : "content", index : "6", align : "center", width : "60px", sortable : false},
			    {name: "handel", index: "7", align: "center", width: "30px", sortable: false,
					formatter : function(cellValue, options, rowObject) {
						var id = rowObject.id;
						var handlerTemp = '<p class="jqgrid-handle-p">'
								+ '<label class="jqgrid-handle-text delete-link" data-func="logManage-delete" onclick="logManage.deleteBlacklist(\''
								+ id
								+ '\')"><span class="img-delete"></span>移出</label>'
								+ '<span>&nbsp;&nbsp;</span>'
								+ '<label class="jqgrid-handle-text details-link" data-func="logManage-findById" onclick="logManage.logDetails(\''
								+ id
								+ '\')"><span class="img-details"></span>详情</label>'
								+ '</p>';
						return handlerTemp;
					}}],
			loadonce : false,
			viewrecords : true,
			width : width,
			rownumbers : true,
			height : true,
			multiselect : false,
			multiboxonly : true,
			rowNum : 10,
			rowList : [ 5, 10, 15 ],
			pager : "#logManagePage",
			gridComplete : function() {
				// 表格中按钮权限配置
				$.initPrivg("logManageTable");
			}
		}).resizeTableWidth();
	};

	// 查询
	var _searchHandle = function() {
		$logManageTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"orderNo" : $logManageOrderNo.val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// 详情
	var _logDetails = function(id) {
		var rowObj = $.getData(urlPath.findByIdUrl + id);
		if (rowObj) {
			$logDetailPanel.showPanelModel("订单日志提醒详情");
			$logDetailForm.setFormSingleObj(rowObj);
			if (rowObj.tradeStatus) {
				$("#tradeStatusLog").text(
						$.getDicNameByCode(rowObj.tradeStatus,
								dicObj["110001"]));
			}
		} else {
			Message.alert({
				Msg : "暂无详细信息",
				isModal : false
			});
		}
	};
	

	// 初始化
	_init();

	// 返回接口
	return {
		logDetails : _logDetails
	};

})(jQuery);