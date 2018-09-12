/**
 * @description:管道/储罐管理
 * @author: huw
 * @time: 2016-12-16
 */


var  pipelineManage = (function($){
	//管道
	var $pipelineTable = $("#pipelineTable");
	var $pipelineform = $("#pipeline-form");
	var $pipelinepanel = $("#pipeline-panel");
	var $pipelinesearchBtn = $("#pipeline-searchBtn");
	
	//储罐
	var $storagetankpanel = $("#storagetank-panel");
	var $storagetankform = $("#storagetank-form");
	var $storagetankTable = $("#storagetankTable");
	var $storagetanksearchBtn = $("#storagetank-searchBtn");
	var $pipelinetabs = $("#pipeline-tabs");
	
	var config = {
			findByPage:			$.backPath + '/pipeline/findByPage',
			findById:			$.backPath + '/pipeline/findById/',
			findLocationById:	$.backPath + '/pipeline/findLocationById/'
	}; 
	
	var _init = function() {
		
		_initPipelineTable();
		//tab切换
		$pipelinetabs.find("a").click(function(){
			var $this = $(this);
			var tabName = $this.attr("href");
			_showTabs(tabName);
		});
		$pipelinesearchBtn.on("click",_pipelinesearchHandler);
		$storagetanksearchBtn.on("click",_storagesearchHandler);
		
	};
	//tab切换
	var _showTabs = function(name){
		$("a[aria-controls='" + name.split("#")[1] + "']").tab('show');
		if(name == "#pipeline"){
			_initPipelineTable();
		}else{
			_initStoragetankTable();
		}
	};
	
	var _getData = function() {
		var result = "";
		 $.ajax({
             url: config.findLocationById + arguments[0],
             type: "POST",
             cache: true,
             async: false //使用同步方式
         }).done(function (json) {
         	if(json.code === 1 && json.rows){
         		result = json.rows;
         	}else if((json.code === 1) && (!json.data)){
         		Message.alert({Msg: "该数据不存在或为空", isModal: false});
         		return;
         	}else{
         		Message.alert({Msg: json.msg, isModal: false});
         		return;
         	}
             
         });
		 return result;
	};
	//管道表格
	var _initPipelineTable = function() {
		$pipelineTable.jqGrid({
			url: config.findByPage,
			mtype: "POST",
			datatype: "JSON",
			postData:{
				"pipeType":"1",
				"pipeName":$("#pipeline-search").val()
			},
			colNames: ["管道编号", "管道名称","危化品名称", "危化品类型", "流量(m3/h)","责任人", "联系电话", "操作",""],
			colModel: [
			    {name: "pipeNo", index: "2", align: "center", width: "30px", sortable: false},
				{name: "pipeName", index: "1", align: "center", width: "30px", sortable: false},
				{name: "dangerName", index: "1", align: "center", width: "30px", sortable: false},
				{name: "dangerType", index: "3", align: "center", width: "30px", sortable: false},
				{name: "flow", index: "4", align: "center", width: "30px", sortable: false},
				{name: "dutyPerson", index: "4", align: "center", width: "30px", sortable: false},
				{name: "telephone", index: "5", align: "center", width: "30px", sortable: false},
				{name: "", index: "7", align: "center", width: "100px", sortable: false,
					formatter: function (cellValue, options, rowObject) {
						var id = rowObject.id;
						var handlerTemp = '<p class="jqgrid-handle-p">' +
											'<label class="jqgrid-handle-text delete-link" data-func="pipeline-findById" onclick="pipelineManage.pipelineShowDetail(\'' + id + '\')"><span class="img-details"></span>详情</label>' +
											'<span>&nbsp;&nbsp;</span>' +
											'<label class="jqgrid-handle-text delete-link" data-func="pipeline-findLocationById" onclick="pipelineManage.pipelineLocation(\'' + id + '\');"><span class="img-details"></span>监控</label>'+
										   '</p>';	
						
						return handlerTemp;
					}
				},
				 {name: "id", index: "2", align: "center", width: "30px", hidden: true}
			],
			loadonce: false,
			rownumbers:true,
			viewrecords: true,
			autowidth: true,
			height: true,
			rowNum: 10,
			rowList: [5, 10, 15],
			pager: "#pipelinePage",
			gridComplete:function(){
				//配置权限
				$.initPrivg("pipelineTable");
			}
		}).resizeTableWidth();
		
	};
	//管道详情
	var _pipelineShowDetail = function()	{
		var obj = $.getData(config.findById + arguments[0]);
		  if(obj){
			  $pipelineform.saveOrCheck(false);
			  $pipelinepanel.showPanelModel('管道详情').setFormSingleObj(obj);
		  }else{
			  Message.alert({Msg: prompt.checkAndDo, isModal: false});
		  }
	};
	
	//管道位置信息
	var _pipelineLocation = function()	{
		var obj = arguments[0];
		window.location = "/JXWL/view/index.html?pipeId=" + obj;
	};
	
	//管道搜索
	var _pipelinesearchHandler = function(){
		$pipelineTable.jqGrid('setGridParam',{
			postData:{
				"pipeType":"1",
				"pipeName":$("#pipeline-search").val(),
				"dangerName":$("#dangerName4Pipe-search").val()
			},
			page:1
		}).trigger("reloadGrid");
	};
	
	//储罐表格
	var _initStoragetankTable = function() {
		$storagetankTable.jqGrid({
			url: config.findByPage,
			mtype: "POST",
			postData:{
				"pipeType":"2",
				"pipeName":$("#storagetank-search").val()
			},
			datatype: "JSON",
			colNames: ["储罐编号", "储罐名称","液位", "存量（m3）", "危化品名称","储罐容量", "材质", "操作",""],
			colModel: [
			    {name: "pipeNo", index: "2", align: "center", width: "30px", sortable: false},
				{name: "pipeName", index: "1", align: "center", width: "30px", sortable: false},
				{name: "stockVolume", index: "1", align: "center", width: "30px", sortable: false},
				{name: "stockAllowance", index: "3", align: "center", width: "30px", sortable: false},
				{name: "dangerName", index: "4", align: "center", width: "30px", sortable: false},
				{name: "stockVolume", index: "4", align: "center", width: "30px", sortable: false},
				{name: "material", index: "5", align: "center", width: "30px", sortable: false},
				{name: "", index: "7", align: "center", width: "100px", sortable: false,
					formatter: function (cellValue, options, rowObject) {
						var id = rowObject.id;
						var handlerTemp = '<p class="jqgrid-handle-p">' +
											'<label class="jqgrid-handle-text delete-link" data-func="pipeline-findById" onclick="pipelineManage.storagetankShowDetail(\'' + id + '\')"><span class="img-details"></span>详情</label>' +
										'<span>&nbsp;&nbsp;</span>' +
										'<label class="jqgrid-handle-text delete-link" data-func="pipeline-findLocationById" onclick="pipelineManage.storagesearchLocation(\'' + id + '\')"><span class="img-details"></span>监控</label>' +
										'</p>';	
						
						return handlerTemp;
					}
				},
				{name: "id", index: "2", align: "center", width: "30px", hidden: true}
			],
			loadonce: false,
			rownumbers:true,
			viewrecords: true,
			autowidth: true,
			height: true,
			rowNum: 10,
			rowList: [5, 10, 15],
			pager: "#storagetankPage",
			gridComplete:function(){
				//配置权限
				$.initPrivg("storagetankTable");
			}
		}).resizeTableWidth();
		
	};
	
	//储罐详情
	var _storagetankShowDetail = function() {
		var obj = $.getData(config.findById + arguments[0]);
		  if(obj){
			  $storagetankform.saveOrCheck(false);
			  $storagetankpanel.showPanelModel('储罐详情').setFormSingleObj(obj);
		  }else{
			  Message.alert({Msg: prompt.checkAndDo, isModal: false});
		  }
	};
	
	//储罐搜索
	var _storagesearchHandler = function(){
		$storagetankTable.jqGrid('setGridParam',{
			postData:{
				"pipeType":"2",
				"pipeName":$("#storagetank-search").val(),
				"dangerName":$("#dangerName4Tank-search").val()
			},
			page:1
		}).trigger("reloadGrid");
	};
	
	//储罐位置信息
	var _storagesearchLocation = function()	{
		var obj = arguments[0];
		window.location = "/JXWL/view/index.html?pipeId=" + obj;
	};
	
	_init();
	
	return {
		"pipelineShowDetail":_pipelineShowDetail,
		"storagetankShowDetail":_storagetankShowDetail,
		"pipelineLocation":_pipelineLocation,
		"storagesearchLocation":_storagesearchLocation
	};
	
})($);