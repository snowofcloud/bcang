/**
 * @description: 船舶监控 > 电子围栏 > 编辑围栏规则
 * @author: txm
 * @time: 2015-12-9
 */
$(function() {
	//绑定按钮事件
	/*新增围栏规则事件*/
	$(".addRegular").bind("click",electFenceRegular.showPanelSave);
	
	/*编辑围栏规则事件*/
/*	$(".updataRegular").bind("click",electFenceRegular.showPanelUpdata);*/
	
	/*删除围栏规则事件*/
	$(".deleteRegular").bind("click", electFenceRegular.elecFencRegDelete);
	
	/*搜索船名事件*/
	$("#searchShipNamebtn").bind("click",electFenceRegular.shipNameSearch);
});

var electFenceRegular = {
	config:{
		electFenceRegularflagSaOrUpd:true,//新增或者编辑电子围栏规则信息，true新增，false更新
		electFenceRegular: curdPath.getUrl() + "electronicFence/getAllRegular", //电子围栏规则弹框默认加载方法
		electFenceRegularSave: curdPath.getUrl() + "electronicFence/create", //保存电子围栏规则
		electFenceRegularUpdata: curdPath.getUrl() + "electronicFence/updateRegular", //编辑电子围栏规则
		electFenceRegularDelete: curdPath.getUrl() + "electronicFence/remove",//删除电子围栏规则
		electRenceShipUrl: curdPath.getUrl() + "electronicFence/findShipNameByPage", //获取船名
		electFenceGetRegular:curdPath.getUrl() + "electronicFence/findById",//获取单条规则信息
		electFenceRegularFindSystemUser: curdPath.getUrl() + "electronicFence/findAllOrg",//获取管理结构所有部门
		electFenceId: null,  //暂存:新增则id为围栏id，
		RegularId: null,    //暂存:编辑围栏规则时，id为围栏规则id
		dataDictionaryAll:null,//定义数据字典
		initIds : new Array(), //暂时电子围栏规则中已选船舶id号
	    selectOptionDictionaryUrl :curdPath.getUrl()+"dutyCheck/findDictByCode",//数据字典
	    editShip: true, //编辑规则中限制船舶或者船舶类型，true：限制船舶，false：船舶类型
	    initEidtFlag : false,
	    treeFlag: false, //判断是否点击左侧树形菜单选择分支船舶
	    searchFlag: false, //判断是否是查询，不执行反选操作
	},
	
	/*显示弹框 新增电子围栏规则*/
	showPanelAddRegular: function(e, name){
		var ids = new Array();
		if (typeof e != "object") {
			ids.push(e);
		} else {
			ids = $("#electronicFenceTable").jqGrid("getGridParam", "selarrrow");
		}
	    var len = ids.length;
	    if (ids) {
	    	electFenceId = ids[0];
	    } else {
	    	electFenceId = e;
	    }
	    
	    if (len > 1) {
	    	Messager.alert({
	    		Msg: "添加围栏记录不能超过1条",
	            isModal: false
	        });
	        return;
	    } else if (len <= 0) {
	        Messager.alert({
	            Msg: "没有选择添加围栏项",
	            isModal: false
	        });
	        return;
	    } else {
	    	var dataForm = electronRail.getElecRailLimitListMsg(electFenceId);
	    	if(dataForm){
	    		/*判断页面中无表格标签时，添加相应表格标签元素*/
	    		if (0 === $("#outelectFenReguTable").length) {
	    			$("#lectFenReguPanel").append("<div id = 'outelectFenReguTable'>"+
	    											"<table id='electFenceRegularTable' class='lawcaseTable'></table>"+
	    											"<div id='electFenceRegularPage'></div>"+
	    										"</div>"
	    										);
	    		}
	    		toolMethod.showPanel("#electFenceRegular", "电子围栏("+dataForm.name+")报警规则");
	    	    electFenceRegular.initElecFencRegular(electFenceId); 
	    	}else{
	    		Messager.show({Msg: "无权限操作", isModal: false});
	    		return;
	    	}
	    }   
	},
	
	/*显示弹框  新增电子围栏规则*/
	showPanelSave: function() {
		var ids = $("#electFenceRegularTable").jqGrid("getDataIDs");
		/*判断是否在编辑阶段*/
		if ("-1" != ids.indexOf("-1")) {
			Messager.show({Msg: "请先编辑完，再点击新增按钮", isModal: false});
    		return;
		} else {
			electFenceRegular.config.electFenceRegularflagSaOrUpd = true;
			/*动态添加表格数据*/
			var data = {
					"ids":"<div class='row row-group'>" +
							"<div class='electronCss' style='float:left'>" +
								"<input type='hidden' class='form-control' id='shipNameAddId'>" +
								"<input type='input' class='form-control input-sm' readonly id='shipNameAdd'>" +
							"</div>" +
							"<div class=''>" +
								"<button id='btnShip' class='form-control input-sm' style='width:30px' type='button' onclick='electFenceRegular.initSearchBoat()'>" +
									"<span class='glyphicon glyphicon-folder-open'></span>"+
								"</button>" +
								"</div>" +
						"</div>",
					"types":"<select class='form-control input-sm electronCss' id='selectShipType' name='selectShipType' onclick='controlTypeSel()'>" +
								"<option value='-1'>请选择</option>" +
							"</select>",
					"alarm_type":"<div class='row row-group'>" +
									"<div class=''>" +
										"<select class='form-control input-sm electronCss' id='selectAlarm' name='selectAlarm'></select>" +
									"</div>" +
								"</div>",
					"delay_alarm_time": "<div class='row row-group'>" +
			                           "<div class='' style='float:left'>" +
			                               "<input type='input' class='form-control input-sm electronCss'  id='delayTime'>" +
		                               "</div>" +
		                               "<div class=''>" +
			                               "<input type='button' class='btn btn-default btn-sm' id='btn_addOk' onclick='electFenceRegular.addBoatAlarmRule()' value='保存'>" +
		                               "</div>" +
	                                 "</div>"
			}
			/*新增一行，并设置行号为-1*/
    		$("#electFenceRegularTable").jqGrid("addRowData", -1, data, "first"); 
    		/*将新增的一行默认选中*/
    		$("#electFenceRegularTable").jqGrid("setSelection",-1);
			elecFRegulDataDic.dictionaryAjax(electFenceRegular.config.selectOptionDictionaryUrl,"151");
			elecFRegulDataDic.selectOptionDictionary("#selectShipType","151001");
			/*报警类型数据字典添加*/
			elecFRegulDataDic.dictionaryAjax(electFenceRegular.config.selectOptionDictionaryUrl,"163");
			elecFRegulDataDic.selectOptionDictionary("#selectAlarm","163001");
		}
	},
	
	/*显示弹框 编辑电子围栏规则*/
	showPanelUpdata: function() {
		var ids = $("#electFenceRegularTable").jqGrid("getGridParam", "selarrrow");
	    var len = ids.length;
	    if (len > 1) {
	    	Messager.alert({
	    		Msg: prompt.moreSelectedEdit,
	            isModal: false
	        });
	        return;
	    } else if (len <= 0) {
	        Messager.alert({
	            Msg: prompt.noSelectedEdit,
	            isModal: false
	        });
	        return;
	    } else {
	    	var idsArr = $("#electFenceRegularTable").jqGrid("getDataIDs");
			/*判断是否在编辑阶段*/
			if ("-1" != idsArr.indexOf("-1")) {
				Messager.show({Msg: "请先编辑完，再点击编辑按钮", isModal: false});
	    		return;
			} else {
				RegularId = ids[0];
				var dataForm = electFenceRegular.getElecRailRugularMsg(RegularId);  //获得电子围栏规则信息
		    	var elecFenceMsg = $("#electFenceRegularTable").jqGrid("getRowData",RegularId);
		    	if(dataForm){
		    		/*获得电子围栏规则信息，并填充在表格里*/
		    		/*动态添加表格数据*/
		    		var data = {
		    				"ids":"<div class='row row-group'>" +
		    							"<div class='electronCss' style='float:left;'>" +
		    								"<input type='hidden' class='form-control input-sm' id='shipNameAddId'>" +
		    								"<input type='input' class='form-control input-sm' readonly id='shipNameAdd'>" +
		    								"</div>" +
		    								"<div>" +
			    								"<button id='btnShip' class='form-control input-sm' style='width:30px' type='button' onclick='electFenceRegular.initSearchBoat()'>" +
													"<span class='glyphicon glyphicon-folder-open'></span>"+
			    								"</button>" +
		    								"</div>" +
		    						"</div>",
		    				"types":"<select class='form-control input-sm electronCss' id='selectShipType' name='selectShipType' onclick='controlTypeSel()'>" +
		    							"<option value='-1'>请选择</option>" +
		    						"</select>",
		    				"alarm_type":"<div class='row row-group'>" +
		    								"<div class='col-xs-9'>" +
		    									"<select class='form-control input-sm electronCss' id='selectAlarm' name='selectAlarm'></select>" +
		    								"</div>" +
		    							"</div>",
		    				"delay_alarm_time": "<div class='row row-group'>" +
				                           "<div class='' style='float:left'>" +
				                               "<input type='input' class='form-control input-sm electronCss'  id='delayTime'>" +
			                               "</div>" +
			                               "<div class=''>" +
				                               "<input type='button' class='btn btn-default btn-sm' id='btn_addOk' onclick='electFenceRegular.addBoatAlarmRule()' value='确定'>" +
			                               "</div>" +
		                                 "</div>"
		    		}
		    		/*新增一行，并设置行号为-1*/
		    		$("#electFenceRegularTable").jqGrid("addRowData", -1, data, "first"); 
		    		/*将新增的一行默认选中*/
		    		$("#electFenceRegularTable").jqGrid("setSelection",-1);
		    		$("#electFenceRegularTable").jqGrid("delRowData",RegularId);  
		    		elecFRegulDataDic.dictionaryAjax(electFenceRegular.config.selectOptionDictionaryUrl,"163");
					elecFRegulDataDic.selectOptionDictionary("#selectAlarm","163001");
					/*将数据填充在表格中*/
		    		$("#shipNameAdd").val(elecFenceMsg.ids);
		    		$("#shipNameAddId").val(dataForm.shipId);
		    		$("#delayTime").val(elecFenceMsg.delay_alarm_time);
		    		if (null == dataForm.shipType) {
		    			$("select[name = 'selectShipType']").children()[0].text="";
		    		} else {
		    			elecFRegulDataDic.dictionaryAjax(electFenceRegular.config.selectOptionDictionaryUrl,"151");
			    		elecFRegulDataDic.selectOptionDictionary("#selectShipType","151001");
		    			$("select[name = 'selectShipType']").val(dataForm.shipType);
		    		}
		    		$("select[name = 'selectAlarm']").val(dataForm.alarmType);	
		    		/*判断编辑限制船舶还是船舶类型*/
		    		if(("" == $("#shipNameAdd").val()) && ("-1" != $("#selectShipType").val())) {
		    			electFenceRegular.config.editShip = false;
		    		} else {
		    			electFenceRegular.config.editShip = true;
		    		}
		    		electFenceRegular.config.electFenceRegularflagSaOrUpd = false;
		    		controlTypeSel();
		    	}else{
		    		Messager.show({Msg: "无权限操作", isModal: false});
		    		return;
		    	}
			};
	    }
	},
	
	/*删除围栏规则*/
	elecFencRegDelete: function() {
		var ids = $("#electFenceRegularTable").jqGrid("getGridParam", "selarrrow");
		var flag = electFenceRegular.config.electFenceRegularflagSaOrUpd;
		if (-1 == ids && !flag){
			Messager.show({Msg: "编辑状态下不能删除", isModal: false});
    		return;
		}
		var len = ids.length;
		if (len <= 0) {
			Messager.alert({Msg:prompt.noSelectedDelete,'isModal': false});
			return;
		} else {
			Messager.confirm({Msg: prompt.sureDelete, iconImg: 'question', isModal: true}).on(function (flag) {
				if(flag) {
					 $.ajax({
						 url: electFenceRegular.config.electFenceRegularDelete + "/" + ids,
						 type: 'POST',
						 dataType: 'json',
						 data:{
					     	   'id':ids ,
					        },
						 async: false,
						 success: function(json) {
							 if(json.code === 1) {
								 $("#electFenceRegularTable").jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
								 $("#boatSearchRegular").hide();
								 RegularId = null;
			                     Messager.show({Msg: json.msg, isModal: false});
							 }else if(json.code === 3){
								 Messager.alert({Msg:json.msg,iconImg:"warning", isModal: false});
								 $("#electFenceRegularTable").jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
							 }
						 },
					 });
				}
			});
		}
	},
	
	/*编辑围栏规则-根据管理机构-船名弹框显示*/
	initSearchBoat: function() {
		/*判断新增还是编辑规则*/
		if (!electFenceRegular.config.electFenceRegularflagSaOrUpd && !electFenceRegular.config.editShip) {
			controlTypeSel();
		} else {
			if ($("#boatSearchRegular").css("display")  == "none") {
				$("#boatSearchRegular").css("display","block");
				$(".customDialogtriangleUp").removeClass("hidden");
				/*判断页面中无船舶表格标签时，添加相应表格标签元素*/
	    		if (0 === $("#outshipNameTable").length) {
	    			$("#treeShipContain").append("<div class='col-xs-9' id='outshipNameTable'>"+		
	    											"<table id='shipNameTable' class='lawcaseTable'></table>"+
	    											"<div id='shipNamePage'></div>"+
	    										"</div>"
	    										);
	    			}
			} else {
				$("#boatSearchRegular").css("display","none");
				$(".customDialogtriangleUp").addClass("hidden");
				/*判断船舶表格存在的情况下，删除表格*/
		    	if ($("#outshipNameTable").length > 0) {
		    		$("#outshipNameTable").remove();
		    	}
			}
			/*设置shipSearch的位置*/
			var topDis = $("#btnShip").offset().top+$("#btnShip").height()+47+"px";
			var leftDis = $("#shipNameAdd").offset().left+"px";
			$("#boatSearchRegular").css("top",topDis);
			$("#boatSearchRegular").css("margin-left",leftDis);
			/*当浏览器移动时，更新船舶搜索panel*/
			$(window).resize(function(){
				var topDis = $("#btnShip").offset().top+$("#btnShip").height()+7+"px";
				var leftDis = $("#shipNameAdd").offset().left+"px";
				$("#boatSearchRegular").css("top",topDis);
				$("#boatSearchRegular").css("margin-left",leftDis);
			});
			/*如新增围栏规则，则可以在限制船舶和船舶类型之间转换*/
			if (electFenceRegular.config.electFenceRegularflagSaOrUpd) {
				/*若搜索船名弹框已弹出，则控制船舶类型不能选择*/
				if ($("#boatSearchRegular").css("display")  == "none") {
					var inputValue = $("#shipNameAdd").val();
                    if (inputValue !== "") {
                          $("#selectShipType").attr("disabled",true);
                    } else {
                          $("#selectShipType").attr("disabled",false);
                    }

				} else {
					$("#selectShipType").attr("disabled",true);
				}
			}
			
			/*加载管理机构下拉*/
			electFenceRegular.initPartyZtree();
			/*判断是否编辑 且判断限制船舶的输入框中是否已有值*/
			if (!electFenceRegular.config.electFenceRegularflagSaOrUpd ||
					$("#shipNameAdd").val().length != 0) {
				electFenceRegular.config.initEidtFlag = true;
				electFenceRegular.initShipName(","+$("#shipNameAddId").val());
			}else{
				electFenceRegular.initShipName();
				$("#shipNameTable").resetSelection();	
			}
		}		
	},
	
	/*编辑围栏规则-根据管理机构-显示船舶名称表格*/
	shipNameSearch: function() {		
		var shipName = $("#searchTermRegBoatName").val();
		$("#shipNameTable").jqGrid('setGridParam', {
			postData : {		
				'shipName': shipName,
			},
			page : 1
		}).trigger("reloadGrid");
		electFenceRegular.config.searchFlag = true;
	},
	
	/*初始化船舶名称*/
	initShipName: function(ids) {
		if (typeof ids != "undefined") {
			electFenceRegular.config.initIds = ids.split(",");
		}
		$("#shipNameTable").jqGrid({
	    	url:electFenceRegular.config.electRenceShipUrl,
	        mtype: "POST",
	        datatype: "JSON",
	        colNames: ["船ID","船名"],
	        colModel: [
	            {name: "id", index: 1, align: "center", width:"10px", hidden:true, sortable:false},
	            {name: "ship_name", index: 2, align: "center", width:"10px",sortable:false}
	        ],
	        loadonce: false,
	        viewrecords: true,
	        autowidth: true,
	        height: 400,
	        rownumbers: true,
	        multiselect: true,
	        multiboxonly: false,
	        rowNum: 10, // 每页显示记录数
	        rowList: [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
	        pager: "#shipNamePage",
	        onSelectRow: function(tid) {
	        	if(!electFenceRegular.config.initEidtFlag && 
	        			!electFenceRegular.config.treeFlag && 
	        			!electFenceRegular.config.searchFlag){//初始化编辑选中值时，不需要触发反选事件
	        		if (tid != "") {
	        			var shipId = $("#shipNameAddId").val().split(",");
	        			var shipName = $("#shipNameAdd").val().split(",");
        				var rowData=$("#shipNameTable").getRowData(tid);
	    	        	var index = electFenceRegular.searchSameId(shipId, rowData.id);
	    	        	if(index == -1){
	    	        		$("#shipNameAddId").val($("#shipNameAddId").val()+rowData.id+",");
		        			$("#shipNameAdd").val($("#shipNameAdd").val()+rowData.ship_name+",");
	    	        	} else {
	    	        		shipId.splice(index, 1);
		        			$("#shipNameAddId").val(shipId.join(","));
		        			shipName.splice(index, 1);
		        			$("#shipNameAdd").val(shipName.join(","));
	    	        	}
	    	        	electFenceRegular.config.initIds = $("#shipNameAddId").val().split(",");
	        		}
	        	}
	        },
	        onPaging: function() {
	        	electFenceRegular.config.initEidtFlag = true;
	        },
	        gridComplete: function() {	        	
        		$(electFenceRegular.config.initIds).each(function(index,value){
					$("#shipNameTable").jqGrid("setSelection",value);
				});			
	        	electFenceRegular.config.initEidtFlag = false;
	        	electFenceRegular.config.treeFlag = false;
	        	electFenceRegular.config.searchFlag = false;
	        },
	        onSelectAll: function(tid,status) {
	        	var arrId = $("#shipNameAddId").val().split(",");
    			var arrName = $("#shipNameAdd").val().split(",");
	        	if (status) {
	        		var len = tid.length;
	        		for (var i = 0; i < len; i++) {
	        			var rowData=$("#shipNameTable").getRowData(tid[i]);
	    	        	var index = electFenceRegular.searchSameId(arrId, rowData.id);
	        			if(index == -1){
	        				$("#shipNameAddId").val($("#shipNameAddId").val()+rowData.id+",");
		        			$("#shipNameAdd").val($("#shipNameAdd").val()+rowData.ship_name+","); 
	        			}
	        		};  		
	        	} else {
	        		var len = tid.length;
	        		for (var i = 0; i < len; i++) {
	        			var rowData=$("#shipNameTable").getRowData(tid[i]);
	    	        	var index = electFenceRegular.searchSameId(arrId, rowData.id);
	        			if(index != -1){
	        				arrId.splice(index, 1);
		        			$("#shipNameAddId").val(arrId.join(","));
		        			arrName.splice(index, 1);
		        			$("#shipNameAdd").val(arrName.join(","));
	        			}
	        		}
	        	}
	        	electFenceRegular.config.initIds = $("#shipNameAddId").val().split(",");
	        },
	    });  
	},
	
	searchSameId: function(arr, id) {
		var len = arr.length;
		for(var i=0; i<len; i++){
			if(arr[i] == id){
				return i;
			}
		}
		return -1;
	},
	
	/**
	 * 新增或者修改电子围栏规则信息,
	 * @param name 围栏名称，data 围栏坐标点集
	 * @returns
	 */
	submitElecFenceRegular: function(data) {
		var flag = electFenceRegular.config.electFenceRegularflagSaOrUpd;
    	$.ajax({
			url: flag ? electFenceRegular.config.electFenceRegularSave : electFenceRegular.config.electFenceRegularUpdata,
			async: false,
			dataType: "json",
			type : "POST",
			data: data,
			success: function(json){
				if (json["code"] === 1) {
					/*数据提交成功后将规则填充到表格中*/
					$("#electFenceRegularTable").jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
			    	/*清空信息*/
					$("#shipNameAddId").val("");
			    	$("#shipNameAdd").val("");
			    	$("#selectShipType option:eq(0)").attr("selected", true);
			    	$("#selectAlarm option:eq(0)").attr("selected", true);
			    	controlTypeSel();
			    	$("#boatSearchRegular").hide();
			    	/*反向选择第一行：取消选中行*/
			    	$("#electFenceRegularTable").jqGrid("resetSelection",-1);
			    	/*删除第一行：添加电子围栏规则*/
			    	$("#electFenceRegularTable").jqGrid("delRowData",-1);
			    	/*判断船舶表格存在的情况下，删除表格*/
			    	if ($("#outshipNameTable").length > 0) {
			    		$("#outshipNameTable").remove();
			    	}
			    	if (!flag) { 
			    		electFenceRegular.config.initEidtFlag = false;
						RegularId = null;
			    	}
			    	electFenceRegular.config.initIds = [];
			    	Messager.show({Msg: "操作成功", isModal: false});
                	return false;
                } else if (json["code"] === 3) {
                	Messager.alert({Msg: json["msg"], iconImg: "warning", isModal: false});
                    return false;
                } else if (json["code"] === 2){
//                	 Messager.alert({Msg: json["msg"], iconImg: "error", isModal: false});
                	Messager.show({Msg: "请输入0到32767之间的整数", isModal: true});
                    return false;      
                }
			}
		});
    	
	},
	
	/**
	 * 关闭电子围栏规则弹框,
	 */
	closePanel: function() {
		/*判断电子围栏规则表格存在的情况下，删除表格*/
    	if ($("#outelectFenReguTable").length > 0) {
    		$("#outelectFenReguTable").remove();
    	}
		toolMethod.closePanel("#electFenceRegular", true);
		$("#electFenceRegularTable").clearGridData();
		$("#boatSearchRegular").hide();
		electFenceId = null;
		RegularId = null;
		electFenceRegular.config.initIds = [];
	},
	
	/*初始化电子围栏新增规则*/
	initElecFencRegular: function(id) {
		$("#electFenceRegularTable").jqGrid({
			url:electFenceRegular.config.electFenceRegular,
	        mtype: "POST",
	        datatype: "JSON",
	        postData: {
	        	"id":id 
	        },
			colNames:["限制船舶", "船舶类型", "报警触发", "延迟时间(分钟)"],
			colModel:[
			          {name:"ids", index:"1", align: "center", width: "30px", sortable:false},
			          {name:"types", index:"2", align: "center", width: "30px",sortable:false,
			        	  formatter: function (cellvalue, options, rowObject) {
			        		  var shipType = "";
			        		  switch(rowObject.types) {
			    				case '151001000': shipType = "捕捞船";break;
			    				case '151001001': shipType = "辅助船";break;
			    				case '151001002': shipType = "执法船";break;
			    				case '151001003': shipType = "执法快艇";break;
			    				case '151001004': shipType = "采砂船";break;
			    				case '151001005': shipType = "商船";break;
			    				case '151001006': shipType = "抗法船";break;
			    				case '151001007': shipType = "南沙生产船";break;
			    				case '151001008': shipType = "港澳流动船（南沙组）";break;
			    				case '151001009': shipType = "港澳流动船（普通组）";break;
			    				default:shipType = "";
			        		  }
			        		  if ("-1" != rowObject.types.indexOf("select")) {
			        			  shipType = "<select class='form-control input-sm electronCss ' id='selectShipType' name='selectShipType' onclick='controlTypeSel()'>" +
									"<option value='-1'>请选择</option>" +
								"</select>";
			        		  }
			        		  return shipType; 
		                  }
			          },
			          {name:"alarm_type", index:"3", align: "center", width: "30px",sortable:false,
			        	  formatter: function (cellvalue, options, rowObject) {
			        		  var alarmType = "";
			        		  switch(rowObject.alarm_type) {
			    				case '163001001': alarmType = "驶入";break;
			    				case '163001005': alarmType = "跨界";break;
			    				default:alarmType = "驶出";
			        		  }
			        		  if ("-1" != rowObject.types.indexOf("select")) {
			        			  alarmType = "<div class='row row-group'>" +
				      			  					"<div class=''>" +
				      			  						"<select class='form-control input-sm input-sm electronCss' id='selectAlarm' name='selectAlarm'></select>" +
				      			  					"</div>" +	      			  					
		      			  						"</div>";
			        		  }
			        		  return alarmType; 
		                  }
			          },
			          {name:"delay_alarm_time", index:"4", align: "center", width: "30px", sortable:false}
			          ],
			loadonce: false,
	        viewrecords: true,
	        autowidth: true,
	        height: 300,
	        rownumbers: true,
	        scroll:30,
	        multiselect: true,
	        multiboxonly: true,
	        rowNum: 65535,
	        rowList: [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
	        pager: "#electFenceRegularPage",
		});
	},
	
	
	/*初始化限制船舶的树形菜单*/
	initPartyZtree: function() {
		var userTreeSetting = {
				check: {
					//enable: true,
		            //chkStyle: 'checkbox',
				},
				view: {
					dblClickExpand: false,
		            showLine: true,
		            selectedMulti: false
				},
				data: {
					simpleData: {
		                enable: true,
		                idKey: "id",
		                pIdKey: "pId",
		                rootPId: ""
		            }
				},
				/*回调函数*/
				callback: {
					onClick: electFenceRegular.gettreeValue,
				}
		};
		$.fn.zTree.init($("#treedepatment"),userTreeSetting,electFenceRegular.getmoudleZtreeJson());
		// 展开所有的节点
		var treeObj = $.fn.zTree.getZTreeObj("treedepatment");
		treeObj.expandAll(true);
	},
	
	/**
	 * 根据ajax请求返回数据信息
	 * @returns {Array} json
	 */
	getmoudleZtreeJson: function() {
		var zNodes;
		$.ajax({
			url:electFenceRegular.config.electFenceRegularFindSystemUser,
			dataType: 'json',
			type: "POST",
			async: false,
			success: function(json) {
				if(json["code"] == 1){
					zNodes = json["data"];
				}
			}
		});
		return zNodes;
	},
	
	/**
	 * 新增模块，从左边菜单选上级模块
	 * 查看
	 */
	gettreeValue: function() {
		electFenceRegular.config.treeFlag = true;
		var treeObj = $.fn.zTree.getZTreeObj("treedepatment");
		var retId = "";
		var nodes = treeObj.getSelectedNodes();
		for(var i = 0;i < nodes.length;i++){
			retId=nodes[i].id;
		}
		var dataArray = {
				"shipName": "",
				"deptId":retId,
				"page":1,
				"rows":10
		};
		/*新增规则时，初始化initIs*/
    	if (electFenceRegular.config.electFenceRegularflagSaOrUpd) {
    		var ids = $("#shipNameAddId").val();
    		electFenceRegular.config.initIds = ids.split(",");
    	}
		$("#shipNameTable").jqGrid('setGridParam', {
			postData : {		
				"deptId":retId,
			},
			page : 1
		}).trigger("reloadGrid");
		return retId;
	},
	
	/*添加围栏规则*/
	addBoatAlarmRule: function() {
		var addRegularData="";
		var shipType = $("#selectShipType option:selected").val();
		var alarmType = $("#selectAlarm option:selected").val();
		var shipIds = $("#shipNameAddId").val();
		var delayTime = $("#delayTime").val();
		delayTime = delayTime.replace(/(^\s*)|(\s*$)/g,"")
		if(delayTime && (!((/^\d*$/).test(delayTime)))){
			Messager.show({Msg: "请输入0到32767之间的整数", isModal: true});
			return false;
		}
		if(((/^\d*$/).test(delayTime)) && (delayTime > 32767)){
			Messager.show({Msg: "请输入0到32767之间的整数", isModal: true});
			return false;
		}
		var flag = electFenceRegular.config.electFenceRegularflagSaOrUpd;
		//请求单个电子围栏数据，判断是否是线电子围栏
		var eleInfo = electronRail.getElecRailLimitListMsg(electFenceId);
		var tmpPoint, len;
		if(eleInfo){
			tmpPoint = JSON.parse(eleInfo.pointList);
			len = tmpPoint.length;
		}else{
			Messager.show({Msg: "保存失败", isModal: true});
			return false;
		}
		
		if(("" == $("#shipNameAdd").val()) && ("-1" == $("#selectShipType").val())){
			Messager.show({Msg: "请先选择船舶或船舶类型", isModal: true});
			return false;
		} else if(("" == $("#shipNameAdd").val()) && ("-1" != $("#selectShipType").val())) {
    		if((eleInfo.drawMode == '1') && !HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]) && (alarmType == "163001002" || alarmType == "163001001")){//线电子围栏
    			Messager.show({Msg: "线性电子围栏只能选择跨界类型的报警，请重新选择", isModal: true});
    			return false;
    		}else if((eleInfo.drawMode == '0' || HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1])) && alarmType == "163001005"){
    			Messager.show({Msg: "非线性电子围栏不能选择跨界类型的报警，请重新选择", isModal: true});
    			return false;
    		}else{
    			if (flag) {//新增
    				addRegularData = {
    						"electronFenceId": electFenceId,
    						"shipType" : shipType,
    						"alarmType": alarmType,
    						"delayTime": delayTime
    						};
    			} else {//编辑
    				addRegularData = {
    						"id": RegularId,
    						"shipType" : shipType,
    						"alarmType": alarmType,
    						"delayTime": delayTime
    						};
    			}
    		}
			
		} else {
			if((eleInfo.drawMode == '1') && !HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]) && (alarmType == "163001002" || alarmType == "163001001")){//线电子围栏
    			Messager.show({Msg: "线性电子围栏只能选择跨界类型的报警，请重新选择", isModal: true});
    			return false;
    		}else if((eleInfo.drawMode == '0' || HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1])) && alarmType == "163001005"){
    			Messager.show({Msg: "非线性电子围栏不能选择跨界类型的报警，请重新选择", isModal: true});
    			return false;
    		}else{
    			if (flag) {//新增
    				addRegularData = {
    						"electronFenceId": electFenceId,
    						"shipId" : shipIds,
    						"alarmType": alarmType,
    						"delayTime": delayTime
    						};
    			} else {//编辑
    				addRegularData = {
    						"id": RegularId,
    						"shipId" : shipIds,
    						"alarmType": alarmType,
    						"delayTime": delayTime
    						};
    			}
    		}
		}    
		
    	/*将数据传输到后台*/
    	electFenceRegular.submitElecFenceRegular(addRegularData); 
	},
	
	/**
	 * 根据围栏id获取围栏规则信息,
	 * @param id 电子围栏的唯一ID返回查询出来的json数据
	 * @returnsdata:{
            			"ids":ids
            		},
	 */
	getElecRailRugularMsg: function(id) {
		var electRegularData = null;
		$.ajax({
		        url: electFenceRegular.config.electFenceGetRegular,
		        dataType: 'json',
		        data:{
			     	   'id':id ,
			        },
		        type: "POST",
		        async: false,
		        success: function (json) {
		     	   if (json["code"] === 1) {
		     		  electRegularData = json.data;
		            }
		        }
		});
		return electRegularData;
	},
};

/*控制限制船舶和船舶类型只能而选一*/
function controlTypeSel(){
	/*判断新增还是编辑*/
	if(electFenceRegular.config.electFenceRegularflagSaOrUpd) {
		if ("-1" == $("#selectShipType option:selected").val()) {
			$("#btnShip").attr("disabled",false);
		} else{
			$("#btnShip").attr("disabled",true);
		} 
		/*若船名为空，则控制船舶类型可选*/
		if ("" == $("#shipNameAdd").val()) {
			$("#selectShipType").attr("disabled",false);
		} else {
			$("#selectShipType").attr("disabled",true);
		} 
	} else {
		/*判断编辑限制船舶还是船舶类型*/
		if(electFenceRegular.config.editShip) {
			$("#btnShip").attr("disabled",false);
			$("#selectShipType").attr("disabled",true);
		} else {
			$("#selectShipType").attr("disabled",false);
			$("#btnShip").attr("disabled",true);
		}
	}
}
/************************************数据字典****************************************************/
var elecFRegulDataDic = {
		/**
	     *数据字典 
	     * @url   请求路径
	     * @datatype  最外层编码
	     * @addressListManager.config.dataDictionaryAll 全局变量
	     */
		dictionaryAjax: function(url, dataType) {
			var data = null;
			$.ajax({
				url: url,
				async: false,
				dataType: "json",
				type : "POST",
				data: {
					"code" : dataType
				},
				success: function(json){
					electFenceRegular.config.dataDictionaryAll = json["data"];
				}
			});
		},
		
		/**
	     *数据字典填充  
	     * @param  新增面板对应的选项
	     * @selectId   填充选择器ID
	     * @selectDataType 内部编码
	     */
		selectOptionDictionary: function(selectId, selectDataType) {
			var selectOptiond = "";
			var length = electFenceRegular.config.dataDictionaryAll[selectDataType].length;
			var dataDictionary = electFenceRegular.config.dataDictionaryAll[selectDataType];
			for(var i=0;i < length ;i++){
				selectOptiond += '<option value= "'+ dataDictionary[i].code +'">'+ dataDictionary[i].name + '</option>';
			}
			$(selectId).append(selectOptiond);
		},
};

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,"");  //去掉字符串两端空格
};

//判断两个经纬度是否相等
HthxMap.isTwoCoorEqual = function(arr1, arr2){
	var flag = false;
	if((arr1[0] == arr2[0]) && (arr1[1] == arr2[1])){
		flag = true;
	}
	return flag;
}