/**
 * @description:货源查看
 * @author: 
 * @time: 2016-9-2
 */
var goodsSourceDetail = (function($) {
	//数据字典对象，用于转码
	var dicObj;
	
	// 是否有留言标识：0:no;1:yes
	var hasMessage = 0;
	
	// 货源信息表
	var $goodsSourceDetailTable = $("#goodsSourceDetailTable");
	var $goodsSourceDetailPage = $("#goodsSourceDetailPage");
	
	// 选择的货源的ID
	var $id = '';
	
	// 货源信息留言板表单
	var $goodsMessageForm = $("#goodsMessageForm");
	var $goodsMessageFormPanel = $("#goodsMessageForm-panel");
	var $footerForMessage = $("#footerForMessage");

	// 新建留言表单
	var $creatMessageForm = $("#creatMessageForm");
	var $creatMessageFormPanel = $("#creatMessageForm-panel");
	//有留言时提示对话框
	var $hasMessageForm = $("#hasMessageForm");
	var $hasMessageFormPanel = $("#hasMessageForm-panel");
	
/*	// 查看留言
	var $TimeForCheck = $("#TimeForCheck");
	var $messageDate = $("#messageDate");
	var $messageTime = $("#messageTime");
	var $contentForCheck = $("#contentForCheck");*/
	
	// 保存留言按钮
	var $saveBtnForMessage = $("#saveBtnForMessage");
	// 查询按钮
	var $goodsSourceSearch = $("#goodsSource_search");

	// 判断是否第一次加载
	var loadOne = false;

	var urlPath = {
		// 查询货源信息(指已发布的货源)路径：依据查询条件查询
		findByPageUrl : $.backPath + '/goodsSourceDetail/findByPage',
		findGoodsByIdUrl : $.backPath + '/srcGoods/findById/',
		// 查询得到相应的数字字典
		findDicCodeUrl : $.backPath + '/srcGoods/findDictByCode/004',
		// 保存留言路径
		saveUrl : $.backPath + '/leaveMessage/save',
		// 查询留言通过货源号
		findMessageByGoodsIdUrl : $.backPath + '/srcGoods/findLeaveExistById/'
	};

	var _init = function() {
		if (!loadOne) {
			// 查询按钮绑定事件
			$goodsSourceSearch.unbind("click").bind("click", _searchHandler);

			// 留言表单验证
			$creatMessageForm.html5Validate(function() {
				_saveForMessage();
				return false;
			});

			// 注册回车搜索事件
			$("#waybilllNo_forCheck").keyup(function(e) {
				if (e.keyCode === 13) {
					_searchHandler();
				}
				return false;
			});

			// 得到数字字典
			dicObj = $.getData(urlPath.findDicCodeUrl);

			// 初始化货源信息表
			_goodsSourceDetailTable();

			loadOne = true;
		} else {
			_searchHandler();
		}
	};

	// 保存留言 调用 留言管理的接口
	var _saveForMessage = function() {
		var message = {
			'goodsNo' : $id,
			'messageContent' : $("#contentForMessageCreate").val()
		};
		$.ajax({
			url : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : message
		}).done(function(json) {
			if (json.code === 1) {
				$creatMessageFormPanel.closePanelModel();
				$goodsSourceDetailTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 查询功能
	var _searchHandler = function() {
		$goodsSourceDetailTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"waybilllNo" : $("#waybilllNo_forCheck").val(),
				"startTime" : $("#serach_startTimeForMessage").val(),
				"endTime" : $("#serach_endTimeForMessage").val()
			},
			page : 1
		}).trigger("reloadGrid");
		// 去除首尾空格
		$.removeTrim();
	};

	// jqGrid初始化货源表
	var _goodsSourceDetailTable = function() {
		$goodsSourceDetailTable.jqGrid(
			{
				url : urlPath.findByPageUrl,
				mtype : "POST",
				datatype : "JSON",
				colNames : [ "货单号", "品名", "发货单位", "收货单位", "所需运输车辆类型",
						"发布时间", "操作" ],
				colModel : [ 
				    {name : "waybilllNo",      index : "1", align : "left",   width : "30px", sortable : false}, 
				    {name : "allGoodsName",    index : "2", align : "left",   width : "30px", sortable : false}, 
				    {name : "senderCompany",   index : "3", align : "left",   width : "30px", sortable : false},
				    {name : "receiverCompany", index : "4", align : "left",   width : "30px", sortable : false},
				    {name : "carType",         index : "5", align : "center", width : "30px", sortable : false,
				    	formatter: function(cellValue, options, rowObject) {
				    		return $.getDicNameByCode(cellValue, dicObj["109001"]);
				    	}
				    },
				    {name : "publishDate",     index : "7", align : "center", width : "30px", sortable : false},
				    {name : "keepTalks",       index : "8", align : "center", width : "30px", sortable : false,
						formatter : function(cellValue, options, rowObject) {
							// 格式化操作按钮
							return _settingHandlerBtn(cellValue,rowObject);
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
	            pager: "#goodsSourceDetailPage",
	            gridComplete:function(){
	                //配置权限
	                $.initPrivg("goodsSourceDetailTable");
	            }
			}).resizeTableWidth();
	};
	 /*操作的内容html
	 cellValue参数为keepTalks留言对象
	 在配置权限时，新建留言的权限只赋予给物流角色
	 根据登录用户信息（corporateCode）确定改物流企业是否有留言，进而是否显示新建按钮*/
	var _settingHandlerBtn = function(cellValue,rowObject) {
		/*base.js公共方法
		var useInfor = $.$.getUserInfo();*/
		//JSON.parse将字符串转为json对象
		var useInfor = JSON.parse(sessionStorage.getItem("userInfo"));
		var userRole = useInfor.roleCodes;
		var userCode = useInfor.corporateCode;
		var keepTalks = cellValue;
		//是否是物流企业角色标识
		var logisticsroleFlag = false;
		var logisticsrole = "7545f7983ac84654951f64efba29677a";
		logisticsroleFlag = _hasRole(userRole,logisticsrole);
		//是否有留言标识
		var hasMessageFlag = false;
		//登录企业有物流企业角色时，判断是否有该企业的留言
		if( logisticsroleFlag && rowObject.messageContent){
			hasMessageFlag = true;
		}
		var option ='<p class="jqgrid-handle-p">';
		var id = rowObject['id'];
		if(!hasMessageFlag){
			option = option
			+ '<label id="createMessage" class="jqgrid-handle-text delete-link" data-func="goodsSourceDetail-save" onclick="goodsSourceDetail.create(\''
			+ id + '\')"><span class="img-edit"></span>留言</label>'
			+ '<span>&nbsp;&nbsp;</span>' ;
		}
		option = option
				+ '<label id="detailMessage" class="jqgrid-handle-text delete-link" data-func="goodsSourceDetail-findById" onclick="goodsSourceDetail.detail(\''
				+ id + '\')"><span class="img-details"></span>详情</label>'
		        + '</p>';
		return option;
	};

	// 新建留言对话框： 展示货单号和留言输入框
	var _create = function(id) {
		$id = id;
		//是否有留言： 0没有；1有
		var flag = 0;
		var url = urlPath.findMessageByGoodsIdUrl + id;
		$.ajax({
			url : url,
			type : 'GET',
			async : false
		}).done(function(json) {
			if (json.code === 1) {
				var obj = json.data;
				if (obj == 1){
						flag = 1;
				}
			}
		});
		if( flag === 1){
			Message.alert({Msg: "该货单已留言", isModal: false});
			return;
		}
		var obj = $.getData(urlPath.findGoodsByIdUrl + id);
		$creatMessageForm.saveOrCheck(true);
		$creatMessageFormPanel.showPanelModel('新建留言').setFormSingleObj(obj);
	};

	// 货源信息和留言对话框：展示货源信息、留言时间和留言内容
	var _detail = function(id) {
		$id = id;
		// 得到货源信息数据
		var obj = $.getData(urlPath.findGoodsByIdUrl + id);
    	// 加载货物表格
        _goodsTable4Detail();
    	$("#lgoodsTable").jqGrid('clearGridData');
    	if (obj.goodsInfos) {
    		var gobj = obj.goodsInfos;
    		if (gobj && 0 < gobj.length) {
    			for (var index in gobj) {
    				var data = gobj[index];
    				$("#lgoodsTable").jqGrid("addRowData", data.id, data, "last");
    			}
    		}
    	}
    	$("#lgoodsTable").jqGrid({autowidth: false}).setGridWidth(850);
		
		// 获得tradeStatus的数字字典的值
		obj['tradeStatus'] = $.getDicNameByCode(obj['tradeStatus'], dicObj["110001"]);
		obj['carType'] = $.getDicNameByCode(obj['carType'], dicObj["109001"]);
		// 封装货源信息内容
		$goodsMessageForm.saveOrCheck(false);
		
        
		var keepTalks = obj['keepTalks'];
		var result = [];
		var user = $.getUserInfo();
		var code = user.corporateCode;
		for (var i=0;i<keepTalks.length;i++) {
		    if ( keepTalks[i].messageContent && code == keepTalks[i].logisticsEnterprise) {   
		    	result.push(keepTalks[i]);
		    }
		}
		//隐藏其它页面的留言
		$("#leaveMessage-panel").addClass("hidden");
		$("#keepTalksForgoodMessage").removeClass("hidden");
		$('#keepTalksForgoodMessage').empty();
		if (result && 0 < result.length) {
			var html = '<div class="moduleLine">留言记录信息<div class="moduleSpanLine"></div></div>';
			html +='<div class="row centerSytle">'
				+ '<div class= "col-xs-2">' 
				+'<span>'+'留言企业'+'</span>'
				+'</div>'
				+ '<div class= "col-xs-3">' 
				+'<span>'+'留言时间'+'</span>'
				+'</div>'
				+ '<div class= "col-xs-7">' 
				+'<span>'+'留言内容'+'</span>'
				+'</div>'
				+'</div>';
			$('#keepTalksForgoodMessage').append(html);
			for (var j=0; j<result.length; j++) {
				html = '<div class="row centerSytle">'
					+ '<div class= "col-xs-2">' 
					+'<span>'+result[j].enterpriseName
					+'</span>'
					+'</div>'
					+ '<div class= "col-xs-3">' 
					+'<span>'+result[j].keepTalkTime+'</span>'
					+'</div>'
					+ '<div class= "col-xs-7">' 
					+'<span>'+result[j].messageContent+'</span>'
					+'</div>'
					+'</div>';
    			$('#keepTalksForgoodMessage').append(html);
			}
        }else {
			$("#keepTalksForgoodMessage").addClass("hidden");
			
		}
		$goodsMessageFormPanel.showPanelModel('货物详情').setFormSingleObj(obj);
	};
	//判断是否有相应的角色
	var _hasRole = function(userRoleCodes,roleCode){
		if( !userRoleCodes || !roleCode){
			return false;
		}
		for(var i=0;i<userRoleCodes.length;i++){
			if( userRoleCodes[i] === roleCode){
				return true;
			}
		}
		return false;
	};
	
    /**
     * 查看详情时货物表格
     */ 
     var _goodsTable4Detail = function() {
    	 $("#lgoodsTable").jqGrid({
               mtype    : "POST",
               datatype : "JSON",
               colNames : ["货物序号", "品名", "包装", "数量", "重量(t)", "体积(m3)", "单价(万元)", "货物价值(万元)", "id"],
               colModel : [
                   {name: "goodsSerialNo",  index: "1", align: "center", width: "10px", sortable: false},
                   {name: "goodsName",      index: "2", align: "left",   width: "15px", sortable: false},
                   {name: "pack",           index: "3", align: "left",   width: "15px", sortable: false},
                   {name: "amount",         index: "4", align: "right",  width: "15px", sortable: false},
                   {name: "weight",         index: "5", align: "right",  width: "15px", sortable: false},
                   {name: "volume",         index: "6", align: "right",  width: "15px", sortable: false},
                   {name: "unitPrice",      index: "7", align: "right",  width: "15px", sortable: false},
                   {name: "goodsWorth",     index: "8", align: "right",  width: "15px", sortable: false},
                   {name: "id",             index: "11", align: "left",  width: "1px",  sortable: false, hidden: true}
               ],
               loadonce   : false,
   			   rownumbers : false,
   			   viewrecords: true,
   			   width : 1066,
   			   height: true,
   			   rowNum: 5
//               pager : "#lgoodsTablePage"
           });
     };
	
	
	
	// 返回该对象的属性
	return {
		init : _init,
		create : _create,
		detail : _detail
	};

})(jQuery);