/**
 * @description:进出口管理--园区卡口记录
 * @author: huw
 * @time: 2016-12-8
 */

var bayonetRecoder = (function($){
	
	var $bayonetRecoderPanel = $("#bayonetRecoder-panel");
	var $bayonetRecoderForm = $("#bayonetRecoder-form");
	var $bayonetRecoderTable = $("#bayonetRecoderTable");
	var $bayonetRecoderPage = $("#bayonetRecoderPage");
	var $bayonetRecordSearchBtn = $("#bayonetRecord-searchBtn");
	var $wayBillGoodsTable = $("#wayBillGoodsTable");
	var $serch = $("#serch");
	
	var config = {
			findByPage:$.backPath + '/bayOnet/findByPage',
			findDicCodeUrl: $.backPath + '/wayBill/findDictByCode/150',
			bayOnetfindById:$.backPath + '/bayOnet/findById/',
			findRecordById:$.backPath + '/bayOnet/findRecordById/',
			findbayonetport: $.backPath + '/wayBill/findDictByCode/170',
			goodsfindByPage:$.backPath + '/wayBill/goodsFindByPage'
	};
	
	var init = function() {
		$serch.html5Validate(function () {
			_searchHandler();
	            return false;
	        });
		_initWayBillMagTable();
		 dictData = $.getData(config.findDicCodeUrl);
		 bayonetport = $.getData(config.findbayonetport);
		$("#bayonetRecord-verify").settingsOptions(dictData['150001'],true);
	};
	
	//详情
	var _bayonetRecoderShowDetail = function(){
		  var data = arguments[0].split(",");
		  var obj = $.getData(config.findRecordById,"POST", {"id":data[0],"orders":data[1]});
		  if(obj){
			  $bayonetRecoderForm.saveOrCheck(false);
			  _initGoodsTable(obj.id);
			  $wayBillGoodsTable.jqGrid('setGridParam',{
					postData:{
						"id":obj.id
					},
					page:1
				}).trigger("reloadGrid");
			 
			  $bayonetRecoderPanel.showPanelModel('运单信息详情').setFormSingleObj(obj);
			  _setoptionforselect("#verifystatus + span",dictData,"150001",obj,"verifystatus");
			  _setoptionforselect("#bayonetport",bayonetport,"170001",obj,"bayonetport");
			  
		  }else{
			  Message.alert({Msg: prompt.checkAndDo, isModal: false});
		  }
 	};
 	//为下拉框设置值
 	
 	var _setoptionforselect = function(id,data,code,obj,pro) {
 		var returnType="";
		  var data=data[code];
		  $.each(data,function(index,content){
			 if(data[index].code === obj[pro]){
			  	returnType = data[index].name;
			  }
		  });
		$(id).text(returnType);
 	};
 	
	//初始化表格
	var _initWayBillMagTable = function(){
		$bayonetRecoderTable.jqGrid({
			url: config.findByPage,
			mtype: "POST",
			datatype: "JSON",
			postData:{
				"verifystatus":$("#bayonetRecord-verify").val(),
				"carno":$("#bayonetRecord-carno").val(),
				"checkno":$("#bayonetRecord-checkno").val()
			},
			colNames: ["","车牌号码", "托运单号","托运货物", "起点", "到点","审核状态", "操作时间", "操作"],
			colModel: [
			    {name: "id", index: "2", align: "center", width: "30px", hidden:true,sortable: false},
			    {name: "carno", index: "2", align: "center", width: "30px", sortable: false},
				{name: "checkno", index: "1", align: "center", width: "30px", sortable: false},
				{name: "goodsname", index: "1", align: "center", width: "30px", sortable: false},
				{name: "startpoint", index: "3", align: "center", width: "30px", sortable: false},
				{name: "endpoint", index: "4", align: "center", width: "30px", sortable: false},
				{name: "verifystatus", index: "4", align: "center", width: "30px", sortable: false,
					formatter:function(cellValue, options, rowObject){
						var returnType="";
						var data=dictData["150001"];
                		$.each(data,function(index,content){
                			if(data[index].code === cellValue){
                				returnType = data[index].name;
                			}
                		});
                		return returnType;  
					}
				},
				{name: "verifytime", index: "5", align: "center", width: "30px", sortable: false},
				{name: "", index: "7", align: "center", width: "100px", sortable: false,
					formatter: function (cellValue, options, rowObject) {
						var handlerTemp = '<p class="jqgrid-handle-p">' +
											'<label class="jqgrid-handle-text delete-link" data-func="bayonetRecord-findById" onclick="bayonetRecoder.bayonetRecoderShowDetail(\'' + rowObject.id + ','+rowObject.checkno + '\')"><span class="img-details"></span>详情</label>' +
										'&nbsp;&nbsp;&nbsp;&nbsp;' +
										'</p>';	
						
						return handlerTemp;
					}
				}
			],
			loadonce: false,
			rownumbers:true,
			viewrecords: true,
			autowidth: true,
			height: true,
			rowNum: 10,
			rowList: [5, 10, 15],
			pager: "#bayonetRecoderPage",
			gridComplete:function(){
				//配置权限
				$.initPrivg();
			}
		}).resizeTableWidth();
	};
	//搜索
	var _searchHandler = function(){
		$bayonetRecoderTable.jqGrid('setGridParam',{
			postData:{
				"verifystatus":$("#bayonetRecord-verify").val(),
				"carno":$("#bayonetRecord-carno").val(),
				"checkno":$("#bayonetRecord-checkno").val()
			},
			page:1
		}).trigger("reloadGrid");
	};

	//初始化货物表
	var _initGoodsTable = function(para){
		 $wayBillGoodsTable.jqGrid({
			url: config.goodsfindByPage,
			mtype: "POST",
			postData:{"id":para},
			datatype: "JSON",
			colNames: ["品名", "包装", "数量", "重量(吨)", "体积(立方米)",  "规格", "批次",],
			colModel: [
				{name: "goodsname", index: "1", align: "center", width: "30px", sortable: false},
				{name: "pack", index: "2", align: "center", width: "30px", sortable: false},
				{name: "quantity", index: "3", align: "center", width: "30px", sortable: false},
				{name: "weight", index: "4", align: "center", width: "30px", sortable: false},
				{name: "volume", index: "5", align: "center", width: "30px", sortable: false},
				{name: "format", index: "6", align: "center", width: "30px", sortable: false},
				{name: "batch", index: "7", align: "center", width: "30px", sortable: false}
			],
			loadonce: false,
			rownumbers:true,
			viewrecords: true,
			//autowidth: true,
			width:1000,
			height: true,
			rowNum: 5,
			pager: "#wayBillGoodsTablePage"
		});
		
	};
	init();
	return {
		"bayonetRecoderShowDetail":_bayonetRecoderShowDetail
	};
	
	
}($));