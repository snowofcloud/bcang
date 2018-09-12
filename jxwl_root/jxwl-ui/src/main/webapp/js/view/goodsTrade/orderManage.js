/**
 * @description:订单管理
 * @author: 肖琼仙
 * @time: 2016-8-31
 */  
var orderManage = (function($){
	//表格
	var $orderManageTable     = $("#orderManageTable");
	//搜索栏
	var $orderManageStartTime = $("#orderManageStartTime");
	var $orderManageEndTime   = $("#orderManageEndTime");
	var $orderManageSearchBtn = $("#orderManage-searchBtn");
	var $orderDetailPanel     = $("#orderDetailPanel");
	var $orderDetailForm      = $("#orderDetailForm");
	var $logRemindTable       = $("#logRemindTable");
	var $logRemindPanel       = $("#logRemindPanel");
	var $logRemindForm        = $("#logRemindForm");
	var $logRemindSubmitBtn   = $("logRemind-submitBtn");
	
	// 评分
	var $commentPanel   = $("#comment_panel");
	var $commentForm    = $("#comment_form");
	
	// 投诉
	var $complainPanel  = $("#complain_panel");
	var $complainForm   = $("#complain_form");
	
	
	//请求路径
    var urlPath = {
        findByPageUrl     : $.backPath + "/orderManage/findByPage",
        findByIdUrl       : $.backPath + "/orderManage/findById/",
        //流程
        rejectOrderUrl    : $.backPath + "/orderManage/rejectOrder/",   /*物流用户拒接订单*/
        pushOrderUrl      : $.backPath + "/orderManage/pushOrder/",     /*化工用户重新发布货源*/
        confirmOrderUrl   : $.backPath + "/orderManage/confirmOrder/",  /*物流用户确认订单*/
        cancelOrderUrl    : $.backPath + "/orderManage/cancelOrder/",   /*化工用户确认撤销*/
        hgConfirmOrderUrl : $.backPath + "/orderManage/hgConfirmOrder/",/*化工用户确认订单*/
        findDicCodeUrl    : $.backPath + '/orderManage/findDictByCode/004',
        hgConfirmOrderStatus:$.backPath + "/orderManage/hgConfirmOrderStatus/",/*化工用户确认订单之前判断运单状态*/
        // 评价、投诉
        commentComplainUrl: $.backPath + '/orderManage/commentComplain',
        findLogByPageUrl  : $.backPath + '/orderManage/findLogByPage',  /*订单日志提醒*/
        updateLogRemindUrl  : $.backPath + '/orderManage/updateLogRemind'  /*订单日志已读设置*/
    };
    
    //变量
    var loadOnce = true;
    var dicObj ="";
    
    var _init = function(){
    	if(loadOnce){
    		dicObj = $.getData(urlPath.findDicCodeUrl);
    		_initTable();
    		$orderManageSearchBtn.bind("click", _searchHandle);
    		loadOnce = !loadOnce;
    		islogalarm();
    	}else{
    		_searchHandle();
    	}
    	$logRemindForm.html5Validate(function () {
    		_logManageRead();
            return false;
        });
    };
    //判断日志是否提醒
    var islogalarm = function(){
    	$.ajax({
 		   type: "POST",
 		   url:urlPath.findLogByPageUrl ,
 		   data: {"rows": 10,"page":1},
 		   success: function(objList){
 			   var code = objList['code'];
 			  if(code === 1){
 				  var row = objList['rows'];
 				  if(row){
 					  if(row.length > 0){
 						  _logRemind();
 					  }
 				  }
 			  }
 		   }
 	    });
    };
    //初始化表格
    var _initTable = function () {
    	$orderManageTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            colNames: ["订单号", "货单号", "品名", "交易状态", "交易对象", "订单生成日期", "操作"],
            colModel: [
                {name: "orderNo",      index: "1", align: "left",   width: "30px", sortable: false},
                {name: "waybilllNo",   index: "2", align: "center", width: "30px", sortable: false},
                {name: "allGoodsName", index: "3", align: "center", width: "30px", sortable: false},
                {name: "tradeStatus",  index: "4", align: "center", width: "30px", sortable: false,
                	formatter:function(cellvalue,options,rowObject){
                		return $.getDicNameByCode(cellvalue, dicObj["110001"]);
                	}
                },
                {name: "tradeObject",  index: "5", align: "center", width: "30px", sortable: false,
                	formatter:function(cellvalue,options,rowObject){
                		var html = '<p class="jqgrid-handle-p">' + cellvalue;
                		if(rowObject.remove == '1'){
                			html = html +'&nbsp;&nbsp;<span style="color: red;">此企业已注销</span>';
                		}
                		html = html +'</p>';
                		return html;
                	}
                },
                {name: "orderDate", index: "6", align: "center", width: "30px", sortable: false},
                {name: "handel",    index: "7", align: "center", width: "70px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        //格式化操作按钮
                        return _settingHandlerBtn(rowObject);
                    }
                }
            ],
            loadonce: false,
            viewrecords: true,
            autowidth: true,
            rownumbers: true,
            height: true,
            multiselect: false,
            multiboxonly: true,
            rowNum: 10,
            rowList: [5, 10, 15],
            pager: "#orderManagePage",
            gridComplete: function () {
                //表格中按钮权限配置
                $.initPrivg("orderManageTable");
            }
        }).resizeTableWidth();
    };
    
    //控制操作按钮
    var _settingHandlerBtn = function (rowObject) {
        var status = rowObject['tradeStatus'];
        var id     = rowObject['id'];
        var tradeobj_no   = rowObject['tradeCorpprateNo'];
        var commentTimes  = rowObject.commentTimes;
        var complainTimes = rowObject.complainTimes;
        var remove        = rowObject.remove;
        var opt = "";
        // 待确认
        if (status === '110001003') {
        	opt = '<p class="jqgrid-handle-p">'
        		  + '<label class="jqgrid-handle-text delete-link" data-func="orderManage-findById"'
        		  + 'onclick="orderManage.orderDetails(\'' + id + '\', \'' + tradeobj_no + '\')" title="详情"><span class="img-details"></span>详情</label>'
        		  + '<span>&nbsp;&nbsp;</span>'
        		  + '<label class="jqgrid-handle-text delete-link" data-func="orderManage-rejectOrder"'
        		  + 'onclick="orderManage.rejectOrder(\'' + id + '\')" title="拒绝订单"><span class="img-edit"/></span>拒绝订单</label>'
        		  + '<span>&nbsp;&nbsp;</span>'
        		  + '<label class="jqgrid-handle-text delete-link" data-func="orderManage-cancelOrder"'
        		  + 'onclick="orderManage.cancelOrder(\'' + id + '\')" title="撤销订单"><span class="img-edit"/></span>撤销订单</label>'
        		  + '<span>&nbsp;&nbsp;</span>'
        		  + '<label class="jqgrid-handle-text delete-link" data-func="orderManage-confirmOrder"'
        		  + 'onclick="orderManage.confirmOrder(\'' + id + '\')" title="确认订单"><span class="img-edit"/></span>确认订单</label>'
        		  + '<span>&nbsp;&nbsp;</span>'
        		  + '</p>';
          // 已拒绝
        } else if (status === '110001004') {
        	opt = '<p class="jqgrid-handle-p">'
        		 +'<label class="jqgrid-handle-text delete-link" data-func="orderManage-findById" onclick="orderManage.orderDetails(\'' + id + '\', \'' + tradeobj_no + '\')" title="详情"><span class="img-details"></span>详情</label>'
        		 +'<span>&nbsp;&nbsp;</span>'
        		 +'<label class="jqgrid-handle-text delete-link" data-func="orderManage-pushOrder" onclick="orderManage.pushOrder(\'' + id + '\')" title="重新发布货源"><span class="img-edit"></span>重新发布货源</label>'
        		 +'<span>&nbsp;&nbsp;</span>'
        		 +'</p>';
          // 已签订
        } else if (status === '110001005') {
        	opt = '<p class="jqgrid-handle-p">'
        		 +'<label class="jqgrid-handle-text delete-link" data-func="orderManage-findById" onclick="orderManage.orderDetails(\'' + id + '\', \'' + tradeobj_no + '\')" title="详情"><span class="img-details"></span>详情</label>'
         	 	 +'<span>&nbsp;&nbsp;</span>'
         	 	 +'<label class="jqgrid-handle-text delete-link" data-func="orderManage-hgConfirmOrder" onclick="orderManage.hgConfirmOrder(\'' + id + '\', \'' + tradeobj_no + '\', \'' + complainTimes + '\')" title="订单完成"><span class="img-edit"/></span>订单完成</label>'
        	 	 +'<span>&nbsp;&nbsp;</span>';
            if (0 == complainTimes && !(+remove)) {
            	opt += '<label class="jqgrid-handle-text delete-link" data-func="orderManage-complain"'
            		+'onclick="orderManage.complain(\'' + id + '\', \'' + tradeobj_no + '\', \'' + complainTimes + '\')" title="投诉"><span class="img-edit"/></span>投诉</label>'
            		+'<span>&nbsp;&nbsp;</span>';
            }
        	opt += '</p>';
          // 交易完成
        } else if (status === '110001006') {
    		opt = 
    			'<p class="jqgrid-handle-p">' +
    			'<label class="jqgrid-handle-text delete-link" data-func="orderManage-findById" onclick="orderManage.orderDetails(\'' + id + '\', \'' + tradeobj_no + '\')" title="详情"><span class="img-details"></span>详情</label>'
           	 	 +'<span>&nbsp;&nbsp;</span>';
        	if (0 == commentTimes && !(+remove)) {
        		opt +=
        			'<label class="jqgrid-handle-text delete-link" data-func="orderManage-comment" onclick="orderManage.comment(\'' + id + '\', \'' + tradeobj_no + '\', \'' + commentTimes + '\')"  title="评价"><span class="img-edit"/></span>评价</label>'
               	   +'<span>&nbsp;&nbsp;</span>';
        	}
        	if (0 == complainTimes && !(+remove)) {
        		opt += 
        			'<label class="jqgrid-handle-text delete-link" data-func="orderManage-complain" onclick="orderManage.complain(\'' + id + '\', \'' + tradeobj_no + '\', \'' + complainTimes + '\')" title="投诉"><span class="img-edit"/></span>投诉</label>'
               	   +'<span>&nbsp;&nbsp;</span>';
        	}
        	opt += '</p>';
        } else {
        	opt = '';
        }
        
        return opt;
    };
    
    //查询
    var _searchHandle = function () {
    	$orderManageTable.jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {
            		   "startTime"     : $orderManageStartTime.val(),
            		   "endTime" : $orderManageEndTime.val()
            },
            page    : 1
        }).trigger("reloadGrid");
    };
    
    //详情
    var _orderDetails = function(id, tradeObjCode){
    	$("#comment").hide();  // 隐藏评价div
    	$("#complain").hide(); // 隐藏投诉div
    	
        var rowObj = $.getData(urlPath.findByIdUrl + id + "/" + tradeObjCode);
        if (rowObj) {
        	// 加载货物表格并添加数据到表格
        	_goodsTable4Detail();
        	$("#goodsTable").jqGrid('clearGridData');
        	if (rowObj.srcGoods.goodsInfos) {
        		var obj = rowObj.srcGoods.goodsInfos;
        		if (obj && 0 < obj.length) {
        			for (var index in obj) {
        				var data = obj[index];
        				$("#goodsTable").jqGrid("addRowData", data.id, data, "last");
        			}
        		}
        	}
        	$("#goodsTable").jqGrid({autowidth: false}).setGridWidth(850);
        	
        	// 为评价投诉添加信息
        	_viewCommentComplain4Detail(rowObj.comcom, rowObj.srcGoods.tradeObject);
        	
        	$orderDetailPanel.showPanelModel("订单详情");
        	$orderDetailForm.setFormSingleObj(rowObj.srcGoods);
        	if(rowObj.srcGoods.tradeStatus){
        		$("#tradeStatusId").text($.getDicNameByCode(rowObj.srcGoods.tradeStatus, dicObj["110001"]));
        	}
        } else {
        	Message.alert({Msg: "暂无详细信息", isModal: false});
        }
    };
    
    /**
     * 对评价、投诉创建数据
     */
    var _viewCommentComplain4Detail = function(arr, tradeObject) {
    	if(arr) {
    		for (var index in arr) {
    			var o = arr[index];
    			var type = o.type;
    			if (type === "0") { // 评价
    				$("#comment").show();
    				$("#comment_view_score").empty().append(commentM.showStar(o.score));
    				if (o.enterpriseName) { // 政府查看（化工企业对物流进行的评价）
    					$("#comment_enp_name").text(o.enterpriseName + "的评价");
    				} else { // 交易方的评价
    					$("#comment_enp_name").text(tradeObject + "的评价");
    				}
    				$("#comment span[name='comment_time']").text(o.complainDate);
    				$("#comment span[name='comment_content']").text(o.content);
    			} else if (type === "1") {  // 投诉 
    				$("#complain").show();
    				if (o.enterpriseName) { // 政府查看（化工企业对物流进行的投诉）
    					$("#complain_enp_name").text(o.enterpriseName + "的投诉");
    				} else { // 交易方的投诉
    					$("#complain_enp_name").text(tradeObject + "的投诉");
    				}
    				$("#complain span[name='complain_time']").text(o.complainDate);
    				$("#complain span[name='complain_content']").text(o.content);
    			}
    		}
    	}
    };
    
    // 撤销订单
    var _cancelOrder = function(id){
    	Message.confirm({
            Msg: "确定撤销该订单吗？",
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.cancelOrderUrl + id,
                    type: 'POST',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	$orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                        $orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    }
                });
            }
        });
    };
    
    // 拒绝订单
    var _rejectOrder = function(id){
    	Message.confirm({
            Msg: "确定拒绝该订单吗？",
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.rejectOrderUrl + id,
                    type: 'POST',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	$orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                        $orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    }
                });
            }
        });
    };
    
    
    // 重新发布货源
    var _pushOrder = function(id){
    	Message.confirm({
            Msg: "确定重新发布该货源吗？",
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.pushOrderUrl + id,
                    type: 'POST',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	$orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                        $orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    }
                });
            }
        });
    };
    
    
    // 确认订单（物流企业）
    var _confirmOrder = function(id){
    	Message.confirm({
            Msg: "确定确认该订单吗？",
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.confirmOrderUrl + id,
                    type: 'POST',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	$orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                        $orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    }
                });
            }
        });
    };
    
    
    // 订单完成（化工企业）
    var _hgConfirmOrder = function(id, tradeobj_no, commentTimes){
    	var data; 
    	  $.ajax({
              url: urlPath.hgConfirmOrderStatus + id,
              type: 'POST',
              async: false
          }).done(function (json) {
              if (json.code === 1) {
            	  data = json.data;
              } else {
                  Message.alert({Msg: json.msg, isModal: false});
                  $orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
              }
          });
    	  if( data === 0){
        		 Message.alert({Msg: prompt.waybillUndone, isModal: false});
        		 return;
        	}
    	Message.confirm({
            Msg: "确定完成该订单吗？",
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.hgConfirmOrderUrl + id,
                    type: 'POST',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	_comment(id, tradeobj_no, commentTimes);
                    	$orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                        $orderManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    }
                });
            }
        });
    };
    
    
    /**
     * 查看详情时货物表格
     */ 
     var _goodsTable4Detail = function() {
    	 $("#goodsTable").jqGrid({
               mtype    : "POST",
               datatype : "JSON",
               colNames : ["序号", "品名", "包装", "数量", "重量(t)", "体积(m3)", "单价(万元)", "货物价值(万元)", "id"],
               colModel : [
                   {name: "goodsSerialNo",  index: "1", align: "center", width: "10px", sortable: false},
                   {name: "goodsName",      index: "2", align: "center", width: "19px", sortable: false},
                   {name: "pack",           index: "3", align: "center", width: "19px", sortable: false},
                   {name: "amount",         index: "4", align: "center", width: "15px", sortable: false},
                   {name: "weight",         index: "5", align: "center", width: "15px", sortable: false},
                   {name: "volume",         index: "6", align: "center", width: "15px", sortable: false},
                   {name: "unitPrice",      index: "7", align: "center", width: "15px", sortable: false},
                   {name: "goodsWorth",     index: "8", align: "center", width: "17px", sortable: false},
                   {name: "id",             index: "9", align: "center", width: "1px",  sortable: false, hidden: true}
               ],
               loadonce   : false,
   			   rownumbers : false,
   			   viewrecords: true,
   			   width : 1066,
   			   height: true,
   			   rowNum: 5
           });
     };
     
     
     /**
      * 评价
      */
 	var _comment = function(orderId, tradeobj_no, commentTimes) {
 		if (commentTimes && 0 < commentTimes) {
 			Message.alert({Msg: "该订单已评价", isModal: false});
 		} else {
 			$("#comment_content").val("");
 			$commentPanel.showPanelModel('订单评价');
 			// 表单验证绑定
 			$commentForm.html5Validate(function() {
 				_submitHandle(orderId, tradeobj_no);
 				return false;
 			});
 		}
 	};
 	var _submitHandle = function() {
 		var formData         = $commentForm.packageFormData();
 		formData.orderId     = arguments[0];
 		formData.otherSideNo = arguments[1];
 		formData.score       = $("#comment_score").val();
 		formData.type        = "0"; 
 		$.ajax({
 			url : urlPath.commentComplainUrl,
 			type : 'POST',
 			async : false,
 			data : formData
 		}).done(function(json) {
 			if (json.code === 1) {
 				$commentPanel.closePanelModel();
 				$orderManageTable.jqGrid('setGridParam', {
 					postData : {},
 					page : 1
 				}).trigger("reloadGrid");
 			} else {
 				$.validateTip(json);
 			}
 		});
 	};
     
 	
 	/**
 	 * 投诉
 	 */
 	var _complain = function(orderId, tradeobj_no, complainTimes) {
 		if (complainTimes && 0 < complainTimes) {
 			Message.alert({Msg: "该订单已投诉", isModal: false});
 		} else {
 			$("#complain_content").val("");
 			$complainPanel.showPanelModel('订单投诉');
 			// 表单验证绑定
 			$complainForm.html5Validate(function() {
 				_submitHandle2(orderId, tradeobj_no);
 				return false;
 			});
 		}
 	};
 	var _submitHandle2 = function() {
 		var formData         = $complainForm.packageFormData();
 		formData.orderId     = arguments[0];
 		formData.otherSideNo = arguments[1];
 		formData.type        = "1";
 		$.ajax({
 			url : urlPath.commentComplainUrl,
 			type : 'POST',
 			async : false,
 			data : formData
 		}).done(function(json) {
 			if (json.code === 1) {
 				$complainPanel.closePanelModel();
 				$orderManageTable.jqGrid('setGridParam', {
 					postData : {},
 					page : 1
 				}).trigger("reloadGrid");
 			} else {
 				$.validateTip(json);
 			}
 		});
 	};
 	
    // 订单日志提醒弹窗
 	var _logRemind = function() {
 		var obj = $.post(_logRemindTable());
 		if (obj) {
		$("#logRemindTable").jqGrid('clearGridData');
			if (obj && 0 < obj.length) {
				for (var index in obj) {
					var data = obj[index];
					$("#logRemindTable").jqGrid("addRowData", data.id, data, "last");
				}
			}
			$("#logRemindTable").jqGrid({autowidth: false}).setGridWidth(850);
			$logRemindPanel.showPanelModel("订单日志提醒");
			$logRemindForm.setFormSingleObj(obj);
	 	} else {
	    	Message.alert({Msg: "暂无提醒信息", isModal: false});
	    }
 	};
 	
    // 日志提醒表格
	var _logRemindTable = function() {
		$("#logRemindTable").jqGrid({
			url : urlPath.findLogByPageUrl,
			mtype : "POST",
			datatype : "JSON",
			colNames : [ "订单号", "记录时间", "日志内容"],
			colModel : [ 
			    {name : "orderNo", index : "1", align : "left", width : "30px", sortable : false}, 
				{name : "recordTime", index : "2", align : "center", width : "30px", sortable : false}, 
				{name : "content", index : "3", align : "center", width : "60px", sortable : false}],
			loadonce : false,
			viewrecords : true,
			autowidth : true,
			rownumbers : true,
			height : true,
			multiselect : false,
			multiboxonly : true,
			rowNum : 10,
			rowList : [ 5, 10, 15 ],
			pager : "#logRemindPage",
			gridComplete : function() {
				// 表格中按钮权限配置
				$.initPrivg();
			}
		}).resizeTableWidth();
	};
	
	// 更新已读日志，全部已读
	var _logManageRead = function() {
		var formData         = $logRemindForm.packageFormData();
 		$.ajax({
 			url : urlPath.updateLogRemindUrl,
 			type : 'POST',
 			async : false,
 			data : formData
 		}).done(function(json) {
 			if (json.code === 1) {
 				$logRemindPanel.closePanelModel();
 				$logRemindTable.jqGrid('setGridParam', {
 					postData : {},
 					page : 1
 				}).trigger("reloadGrid");
 			} else {
 				$.validateTip(json);
 			}
 		});
	};
	
    //初始化
    _init();
    
    //返回接口
    return{
    	orderDetails   : _orderDetails,
    	rejectOrder    : _rejectOrder,
    	pushOrder      : _pushOrder,
    	confirmOrder   : _confirmOrder,
    	hgConfirmOrder : _hgConfirmOrder,
    	cancelOrder    : _cancelOrder,
    	comment        : _comment,
    	complain       : _complain,
    	logRemind      : _logRemind
    };
    
})(jQuery);