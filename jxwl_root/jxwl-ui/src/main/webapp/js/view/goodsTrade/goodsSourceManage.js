/**
 * 交易大厅--货源管理
 * @author zz
 */
var srcGoodManage = (function($) {
	// 搜索
	var $srcGoodsSearchBtn = $("#srcGoods-searchBtn");
	// 新增
	var $srcGoodsAddBtn    = $("#srcGoods-addBtn");

	// 货源新增弹框
    var $srcGoodsPanel     = $("#src-goods-panel");
    var $srcGoodsForm      = $("#srcGoodsForm");
    var $saveBtn           = $("#saveBtn");
    var $saveAndPublishBtn = $("#saveAndPublishBtn");
    
    // 货源信息table
    var $srcGoodsTable     = $("#srcGoodsTable");
    // 企业信息table
    var $enterpriseTable   = $("#wlEnterpriseTable");
    var $enterpriseName    = $("#enterpriseName-search");
    var $matchCondition    = $("#match-condition");
    var $enterpriseNameSear= $("#enpre-searchBtn");
    
    // 留言记录
    var $keepTalks         = $('#keepTalks');
    
    // 新增货物
    var $goddsAddBtn       = $('#goodAdd');
    // 货物表格、弹框面板、表单form、新增、取消
    var $goodsTable        = $('#goodsTable');
    var $goodsPanel        = $('#goods-panel');
    var $goodsForm         = $('#goods-form');
    var $goddsSubmitBtn    = $('#goodsSubmitBtn');
    var $goodsCancelBtn    = $('#goodsCancleBtn');
    
    // 货单号
    var $goodsWaybillno    = $('#goods-waybilllno');
    
    var loadOne = false;
    var flag , publishOrNot = false; // publishOrNot(false:保存、true:保存并发布)
    
    // 签订时当前的货源id
    var globalSrcGoodId = '';
    // 选中当期行的货源状态
    var status = null;
    
    // 选中当期行的车辆类型
    var carType = null;
    
    // 创建当前日期
    var d = new Date();
    var curDate = d.toISOString().split("T")[0].replace(/-/g, "");
    
    // 新增货物的个数
    var goodsNum = 0;
    //更新动作标识符
    var updateAction;
    // 请求路径
    var urlPath = {
    	// 货源路径
    	findByPageUrl : $.backPath + '/srcGoods/findByPage',
    	saveUrl       : $.backPath + '/srcGoods/save',
    	updateUrl     : $.backPath + '/srcGoods/update/',
    	findByIdUrl   : $.backPath + '/srcGoods/findById/',
    	deleteUrl     : $.backPath + '/srcGoods/delete/',
    	publishUrl    : $.backPath + '/srcGoods/publish/',
    	findDicCodeUrl: $.backPath + '/srcGoods/findDictByCode/004',
    	// 企业信息路径
    	signUrl       : $.backPath + '/srcGoods/sign/',
    	findEnpreUrl  : $.backPath + '/srcGoods/findEnpInfos',
    	findEnpreDetailUrl : $.backPath + '/logisticst/findById/',
    	// 企业车辆信息路径
    	findEnpCarsUrl: $.backPath + '/srcGoods/findCarsByPage',
    	findCarInfoUrl: $.backPath + '/srcGoods/findCarInfo/',
    	findOrderNoUrl : $.backPath + '/wayBillTemp/placeAndPoi'
    };
    
    var dicObj = $.getData(urlPath.findDicCodeUrl);
    
    // 初始化
    var _init = function() {
        $("#carType").settingsOptions(dicObj["109001"], false);
        _initEnpCars();
    	if (!loadOne) {
	        // 初始化货源表格
        	_initSrcGoodsTable();
        	// 绑定点击事件
            _bindClick();
            loadOne = !loadOne;
            // 注册回车搜索事件
            $("#goods_search input, #enpre_search input").keyup(function (e) {
                if (e.keyCode === 13) {
                    _searchHandler();
                }
                return false;
            });
            // 禁用回车提交表单
			$(this).keydown(function(e) {
				var key = window.event ? e.keyCode : e.which;
				if (key.toString() == "13") {
					return false;
				}
			});
			
            // 禁用企业信息中的搜索
            _disableEnpreSear();
            // 初始化企业表格
            _init_enterprise_tab();
            // 强行清楚企业表格数据
            setTimeout(function() {$enterpriseTable.jqGrid('clearGridData');}, 100);
            
            _associateSAndE("#startPoint");
        	_associateSAndE("#endPoint");
        } else {
            _searchHandler();
        }
    };
    
    //起点、到点联想框
	 var _associateSAndE = function(target){
			//初始化联想下拉框
			var config = {};
			config.inputObj = target;
			config.url =urlPath.findOrderNoUrl;
			associateObj = $.associate(config);
			
			//重写联想下拉框getData方法
			associateObj.getData = function(val,obj){
				var list = $.getData(urlPath.findOrderNoUrl,"POST",{"orderNo":val});
				//模拟数据
				//var list = [{"name":"经开区一号","longitude":"10","latitude":"10"},{"name":"经开区2号","longitude":"11","latitude":"11"}]
				if(list && list.length > 0){
					obj.setData(list);
					obj.associateDiv.show();
					obj.associateDiv.empty();
					
					for(var i = 0; i < list.length && obj.showCnt; i++){
						var data = list[i];
						var str = "<div style='cursor:pointer;padding:2px;10px;'>"+data.name+
						"</div>";
						obj.associateDiv.append(str);
					}
				}
			};
			//重写联想下拉框selectData方法
			associateObj.selectData = function(data,divObj,obj){
				var name = divObj.text();
				if(data && data.name){
					name = data.name;
				}
				$(target).val(name);
				//缓存经纬度
				if(target === "#startPoint"){
					$("#startLngInput").val(data.longitude);
					$("#startLatInput").val(data.latitude);
				}else{
					$("#endLngInput").val(data.longitude);
					$("#endLatInput").val(data.latitude);
				}
				
				
			};
	   };
    
    // 绑定click
    var _bindClick = function(){
 	    $srcGoodsAddBtn.bind("click", _insert);
 	    $srcGoodsSearchBtn.bind("click", _searchHandler);
 	    // 保存
 	    $saveBtn.click(function(){
 		    publishOrNot = false;
 	    });
 	    // 保存并发布
 	    $saveAndPublishBtn.click(function(){
 		    publishOrNot = true;
	    });
 	    // 新增货物
 	    $goddsAddBtn.bind("click", _addGoods);
        // 新增货物：绑定关闭、和取消按钮
 	    $goodsCancelBtn.bind("click", _closeGoodsDialog);
 	    
		// 提交货源信息表单验证绑定
		$srcGoodsForm.html5Validate(function () {
		    _submitHandle();
		    return false;
		},{
        	validate: function () {
        		if(!updateAction && (!$("#startLngInput").val() || !$("#startLatInput").val())){
        			$("#startPoint").testRemind("请重新选择起点");
        			return false;
        		}
        		
        		if(!updateAction &&(!$("#endLngInput").val() || !$("#endLatInput").val())){
        			$("#endPoint").testRemind("请重新选择到点");
        			return false;
        		}
        		if(!updateAction &&($("#startPoint").val() == $("#endPoint").val())){
        			$("#endPoint").testRemind("起点和到点不可以相同");
        			return false;
        		}
        		if(!updateAction && $('#carLength').val()==0){
        			$("#carLength").testRemind("车长不可以为0");
        			return false;
        		}
        		if( updateAction){
        			updateAction = false;
        		}
        		return true;
        	}
        });
 	    // 企业搜索
 	    $("#enpre-searchBtn").bind("click", _searchEnterpriseHandler);
    };
    
    // 货源信息表格
    var _initSrcGoodsTable = function() {
    	$srcGoodsTable.jqGrid({
            url      : urlPath.findByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            colNames : ["货单号", "品名", "发货单位", "收货单位", "所需运输车辆类型", "货源状态", "录入时间", "发布时间", "操作"],
            colModel : [
                {name: "waybilllNo",      index: "1", align: "left",   width: "30px", sortable: false},
                {name: "allGoodsName",    index: "2", align: "left",   width: "20px", sortable: false},
                {name: "senderCompany",   index: "3", align: "left",   width: "30px", sortable: false},
                {name: "receiverCompany", index: "4", align: "left",   width: "30px", sortable: false},
                {name: "carType",         index: "5", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue) {
                        return $.getDicNameByCode(cellValue, dicObj["109001"]);
                    }	
                },
                {name: "tradeStatus",     index: "6", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue) {
                		var val = '';
                		var psta = $.getDicNameByCode(cellValue, dicObj["110001"]);
                		if ('未发布' == psta) {
                			val = '<span class="jqgridNewFont">NEW</span>' + psta;
                		} else {
                			val = psta;
                		}
                		
                        return val;
                    }
                },
                {name: "createTime",      index: "7", align: "center", width: "30px", sortable: false},
                {name: "publishDate",     index: "8", align: "center", width: "30px", sortable: false},
                {name: "opt",             index: "9", align: "center", width: "70px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        // 格式化操作按钮
                        return _settingHandlerBtn(rowObject);
                    }
                }
            ],
            loadonce   : false,
            forceFit: true,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 5,
            rowList    : [5, 10],
            pager      : "#srcGoodsPage",
			onSelectRow: function(rowid) {
				globalSrcGoodId = rowid;
				var obj = $.getData(urlPath.findByIdUrl + rowid);
				// 选中加载企业信息表格
				status = obj['tradeStatus'];
				carType = obj['carType'];
				_searchEnterpriseHandler();
				$("#srcGoodsTable,#wlEnterpriseTable").jqGrid({autowidth: false}).setGridWidth($(window).width() - 280);
			},
            gridComplete:function(){
            	//配置权限
            	$.initPrivg();
            }
        }).resizeTableWidth();
    };
    /**
     * 货源信息表格中操作按钮
     */
    var _settingHandlerBtn = function (rowObject) {
        var tradeStatus = rowObject['tradeStatus'];
        var id = rowObject['id'];
        var option = "";
        if ('110001002' == tradeStatus) { // 已发布
        	option =  '<p class="jqgrid-handle-p">' +
        	'<label class="jqgrid-handle-text delete-link" data-func="goodsSource-findById" onclick="srcGoodManage.detail(\''+ id +'\')"><span class="img-details"></span>详情</label>&nbsp&nbsp' +
            '<label class="jqgrid-handle-text delete-link" data-func="goodsSource-delete"   onclick="srcGoodManage.del(\''+ id +'\')"><span class="img-delete"></span>删除</label>' +
            '</p>';
        } else if ('110001001' == tradeStatus) { // 未发布
        	option = '<p class="jqgrid-handle-p">' +
        	'<label class="jqgrid-handle-text delete-link" data-func="goodsSource-publish"  onclick="srcGoodManage.publish(\''+ id +'\')"><span class="img-edit"></span>发布</label>&nbsp&nbsp' +
        	'<label class="jqgrid-handle-text delete-link" data-func="goodsSource-findById" onclick="srcGoodManage.detail(\''+ id +'\')"><span class="img-details"></span>详情</label>&nbsp&nbsp' +
            '<label class="jqgrid-handle-text delete-link" data-func="goodsSource-update"   onclick="srcGoodManage.update(\''+ id +'\')"><span class="img-edit"></span>修改</label>&nbsp&nbsp' +
            '<label class="jqgrid-handle-text delete-link" data-func="goodsSource-delete"   onclick="srcGoodManage.del(\''+ id +'\')"><span class="img-delete"></span>删除</label>' +
            '</p>';
        } else if ('110001004' == tradeStatus) {
        	option = '<p class="jqgrid-handle-p">' +
        	'<label class="jqgrid-handle-text delete-link" data-func="goodsSource-findById" onclick="srcGoodManage.detail(\''+ id +'\')"><span class="img-details"></span>详情</label>&nbsp&nbsp' +
        	'<label class="jqgrid-handle-text delete-link" data-func="goodsSource-delete"   onclick="srcGoodManage.del(\''+ id +'\')"><span class="img-delete"></span>删除</label>' +
            '</p>';
        } 
        else {
        	option = '<p class="jqgrid-handle-p">' +
        	'<label class="jqgrid-handle-text delete-link" data-func="goodsSource-findById" onclick="srcGoodManage.detail(\''+ id +'\')"><span class="img-details"></span>详情</label>' +
        	'</p>';
        }
        
        return option;
    };
    
    // 禁用企业信息中的搜索
    var _disableEnpreSear = function() {
    	$enterpriseName.attr("readonly", true);
    	$matchCondition.attr("disabled", "disabled");
        $enterpriseNameSear.attr("disabled", "disabled");
    };
    
    // 释放企业信息中的搜索
    var _freeEnpreSear = function() {
    	$enterpriseName.attr("readonly", false);
    	$matchCondition.removeAttr("disabled");
        $enterpriseNameSear.removeAttr("disabled");
    };
    
    // 新增货源
    var _insert = function() {
    	flag = "";
    	$keepTalks.addClass("hidden");
    	$goodsWaybillno.addClass("hidden");
    	$goddsAddBtn.removeClass("hidden");
    	$saveAndPublishBtn.removeClass("hidden");
    	
    	$srcGoodsForm[0].reset();
    	$srcGoodsForm.saveOrCheck(true);
    	_disableEnpreSear();
    	// 加载货物表格
    	_initGoodsTab();
    	$goodsTable.jqGrid("clearGridData");
    	// 显示货物表格的操作列
    	$("#goodsTable").setGridParam().showCol("opt").jqGrid({autowidth: false}).setGridWidth(1030);
        $srcGoodsPanel.showPanelModel('新增货源');
        var usrer = $.getUserInfo();
        $("#senderCompany").val(usrer.name);
        //$("#senderCompany").attr("readonly","readonly");
    };
    
    // 修改货源
    var _update = function(id){
    	updateAction = true;
        flag = id;
        $keepTalks.addClass('hidden');
        $saveAndPublishBtn.addClass("hidden");
        $goodsWaybillno.addClass("hidden");
        $goddsAddBtn.removeClass("hidden");
        $srcGoodsForm[0].reset();

        var updateObj = $.getData(urlPath.findByIdUrl + id);
        // 是否能够被修改
        if ('110001001' == updateObj.tradeStatus || '110001002' == updateObj.tradeStatus) {
        	$srcGoodsForm.saveOrCheck(true);
        	_disableEnpreSear();
        	_addRowData2JqGrid(updateObj.goodsInfos);
            // 显示货物表格的操作列
        	$("#goodsTable").setGridParam().showCol("opt").jqGrid({autowidth: false}).setGridWidth(1030);
        	$srcGoodsPanel.showPanelModel('修改货源信息').setFormSingleObj(updateObj);
        } else {
        	Message.alert({Msg: '当前状态非未发布或已发布，不能进行修改', isModal: false});
        }
        
    };
    
    // 货源表单提交
    var _submitHandle = function() {
    	// 货源基本信息
    	var srcGoods = $("#module1").packageFormData();
    	srcGoods['waybilllNo'] = curDate;
    	// 所有货物信息
    	var goodsObj = $goodsTable.jqGrid("getRowData");
    	if (goodsObj && 0 < goodsObj.length) {
    		srcGoods["goodsInfos"] = goodsObj;
    		if (!publishOrNot) {
    			srcGoods.publishOrNot = false;
    		} else {
    			srcGoods.publishOrNot = true;
    		}
    		$.ajax({
    			url  : flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
				type : 'POST',
				async: false,
				data : JSON.stringify(srcGoods),
				contentType: 'application/json;charset=utf-8'
    		}).done(function(json) {
    			if (json.code === 1) {
    				$srcGoodsPanel.closePanelModel();
    				$srcGoodsTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
    			} else {
    				$.validateTip(json);
    			}
    		});
    	} else {
    		Message.alert({Msg:'请至少添加一个货物',iconImg: 'warning',isModal: false});
    	}
    };
    
    // 货源详情
    var _detail = function(id) {
    	$srcGoodsForm[0].reset();
    	$goddsAddBtn.addClass("hidden");
    	$keepTalks.removeClass('hidden');
    	$goodsWaybillno.removeClass("hidden");
    	
        var obj = $.getData(urlPath.findByIdUrl + id);
        $srcGoodsForm.saveOrCheck(false);
        if(obj != "") {
        	var num = 0;
        	$("#keepTalks").removeClass("hidden"); 
	        var keepTalks = obj['keepTalks'];
	        $('#talkMsgs').empty();
	        if (keepTalks && 0 < keepTalks.length) {
	        	for (var attr in keepTalks) {
	        		if (keepTalks[attr].messageContent && keepTalks[attr].enterpriseName) {
	        			num++;
	        			$('#talkMsgs').append('<div class="row">'
	        				+ '<div class="col-xs-2">'
	        				+ '<span class="centerSytle">'+keepTalks[attr].enterpriseName+'</span>'
	        				+'</div>'
	        				+ '<div class="col-xs-3">'
	        				+ '<span class="centerSytle">'+keepTalks[attr].keepTalkTime+'</span>'
	        				+'</div>'
	        				+ '<div class="col-xs-7">'
	        				+ '<span class="centerSytle">'+keepTalks[attr].messageContent+'</span>'
	        				+'</div>'
	        				+'</div>');
	        		}
	        	}
	        }
	        if (num == 0){
	        	$("#keepTalks").addClass("hidden");
	        }
	        _addRowData2JqGrid(obj.goodsInfos);
	        // 隐藏货物表格的操作列
	    	$("#goodsTable").setGridParam().hideCol("opt").jqGrid({autowidth: false}).setGridWidth(1030);
	        
	        obj.carType = $.getDicNameByCode(obj.carType, dicObj["109001"]);
	        $srcGoodsPanel.showPanelModel('货源信息详情').setFormSingleObj(obj);
        }
    };
    
    
    // 发布货源
    var _publish = function(id) {
    	Message.confirm({
    		Msg     : $.msg.surePublish,
            okSkin  : 'danger',
            iconImg : 'question',
            isModal : true
    	}).on(function(flag) {
    		if (flag) {
				$.ajax({
					url  : urlPath.publishUrl + id,
					type : "put",
					async: false
				}).done(function(json) {
		            if (json.code === 1) {
		            	$srcGoodsTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
		            } else {
		                Message.alert({Msg: json.msg, isModal: false});
                    }
    			});
    		}
    	});        	
    };
    
    // 删除货源
    var _delete = function(id) {
    	Message.confirm({
    		Msg     : $.msg.sureDelete,
            okSkin  : 'danger',
            iconImg : 'question',
            isModal : true
    	}).on(function(flag) {
    		if (flag) {
    			$.ajax({
    				url  : urlPath.deleteUrl + id,
    				type : "delete",
    				async: false
    			}).done(function(json) {
                    if (json.code === 1) {
                    	$srcGoodsTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    	$enterpriseTable.jqGrid('clearGridData');
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                    }
    			});
    		}
    	});
    };
    
    // 货源搜索
    var _searchHandler = function () {
    	$srcGoodsTable.jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {"waybilllNo" : $("#waybilllNo_search").val(),
            		   "startTime"  : $("#serach_startTime").val(),
            		   "endTime"    : $("#serach_endTime").val()
            },
            page    : 1
        }).trigger("reloadGrid");
    	$enterpriseTable.jqGrid('clearGridData');
    	//去除首尾空格
    	$.removeTrim();
    	_disableEnpreSear();
    };
    
    
    /**************************************************************** 企业信息 ********************************************************************/
    // 企业信息表格
    var _init_enterprise_tab = function() {
    	$enterpriseTable.jqGrid({
          //url      : urlPath.findEnpreUrl,
            mtype    : "POST",
            datatype : "JSON",
            colNames : ["物流企业名称", "主营业务", "车辆数量", "联系方式", "操作"],
            colModel : [
                {name: "enterpriseName",   index: "1", align: "left",   width: "30px", sortable: false},
                {name: "professionalWork", index: "2", align: "left",   width: "30px", sortable: false},
                {name: "vehicleNum",       index: "3", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                	    return '<p class="jqgrid-handle-p">'
                	         + '<label class="jqgrid-handle-text delete-link" onclick="srcGoodManage.showGenpreCarsPanel(\'' + rowObject.corporateNo + '\', \'' + rowObject.vehicleNum + '\')">'+cellValue+'</label>'
                             + '</p>';
                    }
                },
                {name: "telephone",        index: "4", align: "left",   width: "30px", sortable: false},
                {name: "",                 index: "5", align: "center", width: "45px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                        var handlerTemp = '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text delete-link" onclick="srcGoodManage.detailEnprise(\'' + rowObject.id + '\')"><span class="img-details"/></span>详情</label>' +
                            '&nbsp;&nbsp;&nbsp;' +
                            '<label class="jqgrid-handle-text update-link" onclick="srcGoodManage.sign(\''+ globalSrcGoodId +'\', \''+ rowObject.corporateNo +'\')"><span class="img-edit"/></span>签订</label>' +
                            '</p>';
                        
                        return handlerTemp;
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 5,
            rowList    : [5],
            pager      : "#wlEnterprisePage",
            gridComplete:function(){
            	if (!status) {
            		//$enterpriseTable.jqGrid('clearGridData');
            	}
            }
        }).resizeTableWidth();
    };
    
    // 搜索企业
    var _searchEnterpriseHandler = function () {
    	if ('110001002' == status) {
			// 释放企业按钮
			_freeEnpreSear();
			
    		$enterpriseTable.jqGrid('setGridParam', {
    			url     : urlPath.findEnpreUrl,
    			postData: {"enterpriseName" : $("#enterpriseName-search").val(),
    					   "enterpriseType" : "106001001",
    					   "matchCondition" : $("#match-condition").val(),
    					   "vehicleType" : carType
    			},
    			page    : 1
    		}).trigger("reloadGrid");
    	} else {
    		_disableEnpreSear();
    		// 清空企业数据
        	$enterpriseTable.jqGrid('clearGridData');
    		return;
    	}
    };
    
   // 物流企业详情 
    var _detailEnprise = function(enterpriseId) {
        var obj = $.getData(urlPath.findEnpreDetailUrl + enterpriseId, 'GET', false);
        $('#enterprise-msg-form').saveOrCheck(false);
        $('#enterprise-msg-panel').showPanelModel(obj.enterpriseName).setFormSingleObj(obj);
    };
   
    /**
     * 化工企业签订
     */
    var _sign = function(wlCorporateNo, globalSrcGoodId) {
	   	Message.confirm({
			Msg     : $.msg.sureSign,
	        okSkin  : 'danger',
	        iconImg : 'question',
	        isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url  : urlPath.signUrl + wlCorporateNo + '/' + globalSrcGoodId,
					type : "put",
					async: false
				}).done(function(json) {
	                if (json.code === 1) {
	                	globalSrcGoodId = '';
	                	$srcGoodsTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
	                	// 清空企业数据
	                	$enterpriseTable.jqGrid('clearGridData');
	                	var href = document.location.href;
	                	var newhref = href.substring(0,href.lastIndexOf("/")) + "/orderManage.html";
	                	
	                	window.document.location.assign(newhref);
	                } else {
	                    Message.alert({Msg: json.msg, isModal: false});
	                }
				});
			}
		});
    };
    
    
    
    
/**************************************************************** 企业车辆展示相关 FIXME ********************************************************************/
    // 显示企业车辆信息
    var _showGenpreCarsPanel = function(id, vehicleNum){
    	if (vehicleNum && 0 < vehicleNum) {
    		$("#scarsTable").jqGrid().clearGridData().jqGrid('setGridParam', {
    			url: urlPath.findEnpCarsUrl,
    			postData: {
    				"corporateNo" : id,
    				"vehicleType" : carType
    			},
    			page: 1
    		}).trigger("reloadGrid");
    		$("#selectedEnpCars").showPanelModel('企业车辆');
    	} else {
    		Message.alert({Msg: "当前企业暂无车辆", isModal: false});
    	}
    };
    
    /**
     * 当前id对应企业的所有车辆信息
     * @param id 企业id
     */
    var _initEnpCars = function(id) {
    	$("#scarsTable").jqGrid({
            mtype    : "POST",
            datatype : "JSON",
            colNames : ["企业名称", "车牌号", "车辆类型", "终端状态", "id", "操作"],
            colModel : [
                {name: "enterpriseName", index: "1", align: "center", width: "30px", sortable: false},
                {name: "licencePlateNo", index: "2", align: "center", width: "25px", sortable: false},
                {name: "vehicleType",    index: "3", align: "center", width: "25px", sortable: false,
                	formatter: function (cellValue) {
                        return $.getDicNameByCode(cellValue, dicObj["109001"]);
                    }
                },
                {name: "status",         index: "4", align: "center", width: "15px", sortable: false,
                	formatter: function (cellValue) {
                		if( 0 ===  cellValue ){
                			return "在线";
                		}else if(1 ===  cellValue){
                			return "离线";
                		}else if(2 ===  cellValue){
                			return "故障";
                		}else{
                			return cellValue;
                		}
                	}
                },
                {name: "id",             index: "5", align: "center", width: "15px", sortable: false, hidden: true},
                {name: "opt",            index: "6", align: "left",   width: "15px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                    	return '<p class="jqgrid-handle-p">'
                             + '<label class="jqgrid-handle-text delete-link" onclick="srcGoodManage.carDetail(\'' + rowObject.id + '\')"><span class="img-details"/></span>详情</label>'
                             + '</p>';
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            width      : 840,
            height     : true,
            rowNum     : 5,
            rowList    : [5],
            pager      : "#scarsTablePage"
        });
    };
    
    
    /**
     * 获取车辆详情
     */
    var _carDetail = function(id) {
    	$("#scarDetailForm")[0].reset(); 
    	var obj = $.getData(urlPath.findCarInfoUrl + id);
    	obj.vehicleType = $.getDicNameByCode(obj.vehicleType, dicObj["109001"]);
    	obj.crossDomainType = $.getDicNameByCode(obj.crossDomainType, dicObj["105001"]);
    	if( obj.status === 'ON'){
    		obj.status = "在线";
    	}else{
    		obj.status = "离线";
    	}
    	$("#scarDetailPanel").showPanelModel('车辆详情').setFormSingleObj(obj);
    	$("#scarDetailPanel").find('.panel-close').bind("click", _closeCarInfoDialog);
    };
    
    
    /**
     * 关闭车辆详情弹框
     */
    var _closeCarInfoDialog = function() {
    	$("#scarDetailPanel").fadeOut(300);
    	$(".panel-mask").css("z-index","1000");
    };
    
    
    
    
/**************************************************************** 新增多个货源相关 FIXME ********************************************************************/
    /**
     * 货物表格初始化
     */
    var _initGoodsTab = function() {
    	$goodsTable.jqGrid({
              mtype    : "POST",
              datatype : "JSON",
              colNames : ["货物序号", "品名", "包装", "数量", "重量(t)", "体积(m3)", "单价(万元)", "货物价值(万元)", "id", "操作"],
              colModel : [
                  {name: "goodsSerialNo",  index: "1", align: "center", width: "15px", sortable: false},
                  {name: "goodsName",      index: "2", align: "left",   width: "20px", sortable: false},
                  {name: "pack",           index: "3", align: "left",   width: "20px", sortable: false},
                  {name: "amount",         index: "4", align: "right",  width: "20px", sortable: false},
                  {name: "weight",         index: "5", align: "right",  width: "20px", sortable: false},
                  {name: "volume",         index: "6", align: "right",  width: "20px", sortable: false},
                  {name: "unitPrice",      index: "7", align: "right",  width: "20px", sortable: false},
                  {name: "goodsWorth",     index: "8", align: "right",  width: "20px", sortable: false},
                  {name: "id",             index: "11", align: "left",  width: "15px", sortable: false, hidden: true},
                  {name: "opt",            index: "12", align: "left",  width: "30px", sortable: false,
                      formatter: function (cellValue, options, rowObject) {
                          var handlerTemp = '<p class="jqgrid-handle-p">'
                        	  + '<label class="jqgrid-handle-text delete-link" onclick="srcGoodManage.updateGoods(\'' + rowObject.id + '\')"><span class="img-edit"/></span>修改</label>'
                        	  + '&nbsp;&nbsp;'
                        	  + '<label class="jqgrid-handle-text update-link" onclick="srcGoodManage.deleteGoods(\''+ rowObject.id +'\')"><span class="img-delete"/></span>删除</label>'
                        	  + '</p>';
                          
                          return handlerTemp;
                      }
                  }
              ],
              loadonce   : false,
  			  rownumbers : false,
  			  viewrecords: true,
  			  width : 1066,
  			  height: true,
  			  rowNum: 5
//              pager : "#goodsTablePage"
          });
    };
    
    /**
     * 新增货物信息
     */
    var _addGoods = function() {
    	window.flag = false;
    	goodsNum = _getGoodsNum() + 1;
    	if (goodsNum > 10) {
    		Message.alert({Msg:'最多添加10个货物',iconImg: 'warning',isModal: false});
    	} else {
    		$goodsForm[0].reset();
    		$goodsForm.saveOrCheck(true);
    		$("#goods-form span[name=goodsSerialNo]").text(goodsNum + '').removeClass("hidden");
    		$goodsPanel.showPanelModel('新增货物');
    		$goodsPanel.find('.panel-close').bind("click", _closeGoodsDialog);
    		$goodsForm.html5Validate(function () {
    			window.flag = !window.flag;
    			if(window.flag) {
    				_submitGoods2JQgrid();
    			}
    			return false;
    		});
    	}
    };
    
    /**
     * 修改货物信息
     */
    var _updateGoods = function(rowid) {
    	$goodsForm[0].reset();
    	$goodsForm.saveOrCheck(true);
    	$("#goods-form span[name=goodsSerialNo]").text(goodsNum + '').removeClass("hidden");
    	var rowData = $goodsTable.jqGrid("getRowData", rowid);
    	$goodsPanel.showPanelModel('修改货物信息').setFormSingleObj(rowData);
    	$goodsPanel.find('.panel-close').bind("click", _closeGoodsDialog);
		$goodsForm.html5Validate(function () {
			_submitGoods2JQgrid(rowid);
	        return false;
	    });
    };
    
    /**
     * 删除货物
     */
    var _deleteGoods = function(rowid) {
    	
    	Message.confirm({
    		Msg     : $.msg.sureDelete,
            okSkin  : 'danger',
            iconImg : 'question',
            isModal : true
    	}).on(function(flag) {
    		if (flag) {
    			$goodsTable.jqGrid("delRowData", rowid);
    	    	--goodsNum;
    	    	if (0 == _getGoodsNum()) {
    	    		goodsNum = 0;
    	    	}
    	    	// 货物序号重置
    	    	var goodsAll = $goodsTable.jqGrid("getRowData");
    	    	if (goodsAll && 0 < goodsAll.length) {
    	    		for (var index in goodsAll) {
    	    			var goods = goodsAll[index];
    	    			goods.goodsSerialNo = index - 0 + 1 + "";
    	    			$goodsTable.jqGrid("setRowData", goods.id, goods, false);
    	    		}
    	    	}
    		}
    	});
    	
    	
    };
    
    /**
     * 提交货物信息到GoodsTab
     */
    var _submitGoods2JQgrid = function(rowid) {
    	var goodsJson =$goodsForm.packageFormData();
    	goodsJson.goodsSerialNo = $("#goods-form span[name=goodsSerialNo]").text();
    	if (rowid) {// 货物修改提交
    		$goodsTable.jqGrid("setRowData", rowid, goodsJson, false);
    	} else {    // 货物新增提交
    		goodsJson.id = _uuid();
    		$goodsTable.jqGrid("addRowData", goodsJson.id, goodsJson, "last");
    	}
    	_closeGoodsDialog();
    };
    
    /**
     * 关闭货物弹框
     */
    var _closeGoodsDialog = function() {
        $goodsPanel.fadeOut(300);
    	$(".panel-mask").css("z-index","1000");
    };
    
    
    /**
     * 修改、查看时数据传入表格中
     */
    var _addRowData2JqGrid = function(obj) {
    	// 加载货物表格
    	_initGoodsTab();
    	$goodsTable.jqGrid('clearGridData');
    	if (obj && 0 < obj.length) {
    		for (var index in obj) {
    			var data = obj[index];
    			$goodsTable.jqGrid("addRowData", data.id, data, "last");
    		}
    	}
    };
    
    /**
     * 获取货物表格中有多少行
     */
    var _getGoodsNum = function() {
    	return $("#goodsTable").getDataIDs().length;
    };
    
    /**
     * 获取32位uuid
     */
    var _uuid = function() {
    	var uo = Math.uuid().split('-');
    	var uuid = "";
    	for (var index in uo) {
    	    uuid = uuid + uo[index];
    	}
    	
    	return uuid;
    };
    
    /**
     * 调用初始化函数
     */
    _init();
    
    /**
     * 返回函数
     */
    return {
        init    : _init,
        publish : _publish,
        update  : _update,
        detail  : _detail,
        del     : _delete,
        sign    : _sign,
        detailEnprise : _detailEnprise,
        
        // 货物相关
        updateGoods : _updateGoods,
        deleteGoods : _deleteGoods,
        
        // 企业车辆信息相关
        showGenpreCarsPanel  : _showGenpreCarsPanel,
        carDetail   : _carDetail
    };
    
})(jQuery);