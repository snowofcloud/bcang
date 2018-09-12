/**
 * @description:进出口管理--园区卡口审核
 * @author: huw
 * @time: 2016-12-8
 */

(function($){
	var $bayonetVerifyPanel = $("#bayonetVerify-panel");
	var $bayonetVerifyFind = $("#bayonetVerifyFind");
	var $wayBillGoodsTable = $("#wayBillGoodsTable");
	var $verifyTable = $("#verifyTable");
	var $bayonetVerifyForm = $("#bayonetVerify-form");
	var $wayBillSubmmitBtn = $("#wayBill-submmitBtn");
	var $wayBillCancelBtn = $("#wayBill-cancelBtn");
	var $bayonetVerifyPanel = $("#bayonetVerify-panel");
	var $serch = $("#serch");
	var config = {
			bayOnetfindById:$.backPath + '/bayOnet/findById/',	
			bayOnetsave:$.backPath + '/bayOnet/save',	
			goodsfindByPage:$.backPath + '/wayBill/goodsFindByPage',
			bayonetfindByPage:$.backPath + '/bayOnet/findByPage',
			findDicCodeUrl: $.backPath + '/wayBill/findDictByCode/150',
			findbayonetport: $.backPath + '/wayBill/findDictByCode/170'
			
	};
	var wayBill;
	var updateTime;
	var init = function() {
		$serch.html5Validate(function () {
			_wayBillMagShowDetail();
	            return false;
	        });
		_bindEvent();
		 dictData = $.getData(config.findDicCodeUrl);
		 bayonetport = $.getData(config.findbayonetport);
		 $("#bayonetport").settingsOptions(bayonetport['170001'],false);
		 $("#bayonetSerch").focus();
	};
	
	var _bindEvent = function() {
		$wayBillSubmmitBtn.bind("click",_bayonetVerify);
		$wayBillCancelBtn.bind("click",_bayonetVerify);
	};
	
	var object;
	
	
	/*url: config.goodsfindByPage,
	mtype: "POST",
	datatype: "JSON",
	postData:{"id":arguments[0]},*/
	//审核记录
	
	var _verifyRecodr = function() {
		$verifyTable.jqGrid({
			url: config.bayonetfindByPage,
			mtype: "POST",
			datatype: "JSON",
			postData:{"wayBillNo":arguments[0]},
			colNames: ["审核状态", "卡口号", "审核人", "审核时间"],
			colModel: [
				{name: "verifystatus", index: "1", align: "center", width: "30px", sortable: false,
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
				{name: "bayonetport", index: "2", align: "center", width: "30px", sortable: false,
					formatter:function(cellValue, options, rowObject){
						var returnType="";
						var data=bayonetport["170001"];
                		$.each(data,function(index,content){
                			if(data[index].code === cellValue){
                				returnType = data[index].name;
                			}
                		});
                		return returnType;  
					}
				},
				{name: "verifyperson", index: "3", align: "center", width: "30px", sortable: false},
				{name: "verifytime", index: "4", align: "center", width: "30px", sortable: false}
			],
			loadonce: false,
			rownumbers:true,
			viewrecords: true,
			width:1000,
			height: true,
			rowNum: 5,
			pager: "#verifyTablePage"
		});
		
		
	};
	
	var _verifyTableReload = function(wayBillNo){
		$verifyTable.jqGrid('setGridParam',{
				postData:{
					"wayBillNo":wayBillNo
				},
				page:1
			}).trigger("reloadGrid");
	};
	//详情
	var _wayBillMagShowDetail = function(){
		var wayBillNo = $("#bayonetSerch").val();
		var obj = $.getData(config.bayOnetfindById + wayBillNo);
		object = obj;
		$("#bayverify").removeClass("hidden");
		$("#baybut").removeClass("hidden");
		$("#succ").addClass("hidden");
		$("#verifyRecoed").removeClass("hidden");
		  if(obj && obj.length != 0){
			  	wayBill = wayBillNo;
			  	updateTime = obj[0].updateTime;
			    var a = obj[0].verifyperson;
				var b = obj[0].verifystatus;
				var c = obj[0].verifytime;
				var d = obj[0].bayonetport;
			  
			  if(obj[0].verifystatus == "150001001" || obj[0].checkstatus == "111001004") {
				  //同意入园
				  $("#bayverify").addClass("hidden");
				  $("#baybut").addClass("hidden");
				  $("#succ").removeClass("hidden");
				  $("#bayonetport").val(obj[0].bayonetport);
				  _verifyRecodr(wayBillNo);
				  _verifyTableReload(wayBillNo);
			  }else if(obj[0].verifystatus == "150001002"){
				  //不同意入园
				  $("#bayverify").removeClass("hidden");
				  $("#baybut").removeClass("hidden");
				  $("#succ").addClass("hidden");
				  _verifyRecodr(wayBillNo);
				  _verifyTableReload(wayBillNo);
			  }else{
				  $("#recoderTitle").addClass("hidden");
				  $("#verifyRecoed").addClass("hidden");
			  } 
			  $("#bayId").data("id",obj[0].id);
			  $bayonetVerifyForm.saveOrCheck(false);
			  $bayonetVerifyPanel.showPanelModel('运单信息详情').setFormSingleObj(obj[0]);
		        _initGoodsTable(obj[0].id);
		        $verifyTable.jqGrid('setGridParam',{
					page:1
				}).trigger("reloadGrid");
		        $wayBillGoodsTable.jqGrid('setGridParam',{
					postData:{
						"id":obj[0].id
					},
					page:1
				}).trigger("reloadGrid");
		      var returnType="";
			  var data=dictData["150001"];
		      $.each(data,function(index,content){
      			 if(data[index].code === obj[0].verifystatus){
      			  	returnType = data[index].name;
      			  }
      		   });
		      if(!(a && b && c && d)) {
				  $("#bayonetport option:eq(0)").attr("selected","selected");
				  $("#recoderTitle").addClass("hidden");
			  }
      		$("#verifystatus + span").text(returnType);
		  }else{
			  Message.alert({Msg: prompt.checkAndDo, isModal: false});
		  }
 	};
 	var _bayonetVerify = function() {
 		var time ;
 		
 		$.ajax({
            url  :config.bayOnetfindById + wayBill,
            type : 'GET',
            data : {},
            async: false
        }).done(function(json) {
            if (json.code === 1) {
            	if(json.data[0] !== null){
            		time = json.data[0].updateTime;
            	}
            } else {
                $.validateTip(json);
            }
        });	
 		
 		if( time > updateTime){
	   		 Message.alert({Msg: prompt.checkUpODel, isModal: false});
	   		 return;
 		}
 		var text = this.innerText;
 		var verifystatus = "";
 		if(text == "同意入园") {
 			verifystatus = "150001001";
 		}else {
 			verifystatus = "150001002";
 		}
		$.ajax({
            url  :config.bayOnetsave,
            type : 'POST',
            data : {
            	"id":$("#bayId").data("id"),
            	"verifystatus":verifystatus,
            	"bayonetport":$("#bayonetport").val()
            }
        }).done(function(json) {
            if (json.code === 1) {
            	$("#enterprise-search").val("");
            	$bayonetVerifyPanel.closePanelModel();
            } else {
                $.validateTip(json);
            }
        });	
 		
 		
 	};
	
	//初始化货物表
	var _initGoodsTable = function(){
		$wayBillGoodsTable.jqGrid({
			url: config.goodsfindByPage,
			mtype: "POST",
			datatype: "JSON",
			postData:{"id":arguments[0]},
			colNames: ["品名", "包装", "数量", "重量(吨)", "体积(立方米)",  "规格", "批次"],
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
			width:1000,
			height: true,
			rowNum: 5,
			pager: "#wayBillGoodsTablePage"
		});
		
	};
	
	
	init();
	
}($));