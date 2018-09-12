/**
 * @description:监控管理-移动app
 * @author: xiaoqx
 * @time: 2016-7-19
 */
/** 移动App信息 */
var inforMagSuddenlyTroubleInfor = (function($) {
	// 模块变量
	var $removalAppTabs = $("#removalApp-tabs");
	var $suddenlyTroubleSearchBtn = $("#suddenlyTrouble-searchBtn");
	var $suddenlyTroubleTable = $("#suddenlyTroubleTable");
	var $suddenlyTroubleAddBtn = $("#suddenlyTrouble-addBtn");
	var $suddenlyTroublePanel = $("#suddenlyTrouble-panel");
	var $suddenlyTroubleForm = $("#suddenlyTrouble-form");
	var loadOne = false;
	var flag;

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/suddenlyTrouble/findByPage',
		findByIdUrl : $.backPath + '/suddenlyTrouble/findById/',
		saveUrl : $.backPath + '/suddenlyTrouble/save',
		updateUrl : $.backPath + '/suddenlyTrouble/update/',
		deleteUrl : $.backPath + '/suddenlyTrouble/delete/',
		findEnterpriseUrl : $.backPath + '/suddenlyTrouble/findEnterprise/',
		findDicCodeUrl : $.backPath + '/srcGoods/findDictByCode/004'
	};

	// 初始化
	var _init = function() {
		inforAccountVerifyInfor.amount();
		if (!loadOne) {
			_initTable();
			_bindClick();
			//添加样式
			var bgColor = '#0A3561';
			var a_style = {
				'background-color' : bgColor,
				'color' : 'white',
				'border' : 'none'
			};
			var li_style = {
				'background-color' : bgColor,
				'border-color' : 'white',
				'border-width' : '1px'
			};
			var $firstA = $("#removalApp-tabs a").first();
			var bg_color = $firstA.css('background-color');
			// var borderColor = $firstA.css('border-color');
			var color = $firstA.css('color');
			// var boder = $firstA.css('border');
				
			$firstA.css(a_style);
			$firstA.parent().css(li_style);

			// tabs切换调用事件
			$removalAppTabs.find('a').click(function () {
	            var $this = $(this);
	            var tabsName = $this.attr('href');
	            sessionStorage.setItem("active_liTwo", tabsName);
	            _showTabs(tabsName);
	            
	            var $parent = $this.parent(); // li元素

				$this.css(a_style);
				$parent.css(li_style);
				var $siblings = $parent.siblings().find('a'); // 获取其他所有a元素
				$siblings.css({
					'background-color' : bg_color,
					'color' : color,
					'border' : 'none'
				}); // 将除自己以外的所有其他a元素背景还原回去
				$parent.siblings().css({
					'background-color' : bg_color,
					'border-color' : color
				}); // 背景还原

				// TODO
				$("#accountVerify_circle").addClass("circleTwo");
	            
	        });
			
			loadOne = !loadOne;
		} else {
			_searchHandler();
		}
	};

	// 绑定click
	var _bindClick = function() {
		$suddenlyTroubleAddBtn.bind("click", _add);
		// 表单事件绑定
		$suddenlyTroubleForm.html5Validate(function() {
			_submitAddHandle();
			return false;
		});
		$suddenlyTroubleSearchBtn.bind("click", {
			type : "suddenlyTrouble"
		}, _searchHandler);

	};
	// 表格
	var _initTable = function() {
		$suddenlyTroubleTable
				.jqGrid(
						{
							url : urlPath.findByPageUrl,
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "标题", "内容", "信息来源", "发送次数", "发送频率", "有效时间", "发布时间", "操作" ],
							colModel : [
									{
										name : "title",
										index : "1",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "content",
										index : "2",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "source",
										index : "3",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "sendTimes",
										index : "4",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "sendFrequency",
										index : "5",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "validTime",
										index : "6",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "publishDate",
										index : "7",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "",
										index : "8",
										align : "center",
										width : "30px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id ;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text update-link" onclick="inforMagSuddenlyTroubleInfor.edit(\'' 
													+ id
													+ '\')"><span class="img-edit"></span>修改</label>'
													+ '<span>&nbsp&nbsp</span>'
													+ '<label class="jqgrid-handle-text update-link" data-func="suddenlyTrouble-delete" onclick="inforMagSuddenlyTroubleInfor.deletes(\''
													+ id
													+ '\')"><span class="img-delete"></span>删除</label>'
													+ '<span>&nbsp&nbsp</span>'
													+ '<label class="jqgrid-handle-text details-link" data-func="suddenlyTrouble-findById" onclick="inforMagSuddenlyTroubleInfor.detail(\''
													+ id
													+ '\')"><span class="img-details"></span>详情</label>'
													+ '</p>';
											return handlerTemp;
										}
									} ],
							loadonce : false,
							rownumbers : true,
							viewrecords : true,
							autowidth : true,
							rownumbers : true,
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#suddenlyTroublePager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("suddenlyTroubleTable");
							}
						}).resizeTableWidth();
	};

	// 新增
	var _add = function() {
		$("#showForSuddenDetail").addClass("hidden");
		flag = "";
		$suddenlyTroubleForm[0].reset();
		$("#emergency_content").val("");
		$suddenlyTroublePanel.showPanelModel('新增突发信息');
		$suddenlyTroublePanel.saveOrCheck(true);
	};

	// 编辑
	var _edit = function(id) {
		$("#showForSuddenDetail").addClass("hidden");
		flag = id;
		$suddenlyTroubleForm[0].reset();
		var updateObj = $.getData(urlPath.findByIdUrl + id);
		if (!updateObj) {
			return;
		}
		$suddenlyTroublePanel.saveOrCheck(true);
		$suddenlyTroublePanel.showPanelModel('修改突发信息').setFormSingleObj(
				updateObj);
	};

	// 详情
	var _detail = function(ids) {
		$suddenlyTroubleForm[0].reset();
		var rowObj = $.getData(urlPath.findByIdUrl + ids);
		$suddenlyTroublePanel.saveOrCheck(false);
		$suddenlyTroublePanel.showPanelModel('突发信息详情').setFormSingleObj(rowObj);
		$("#showForSuddenDetail").removeClass("hidden");
	};

	// 删除
	var _deletes = function(id) {
		Message.confirm({
			Msg : $.msg.sureDelete,
			okSkin : 'danger',
			iconImg : 'question',
			isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url : urlPath.deleteUrl + id,
					type : 'delete',
					async : false
				}).done(function(json) {
					if (json.code === 1) {
						$suddenlyTroubleTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					} else {
						// $.validateTip(json.code);
						Message.alert({
							Msg : json.msg,
							isModal : false
						});
						$suddenlyTroubleTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					}
				});
			}
		});
	};

	// 提交
	// 提交修改处理方法
	var _submitAddHandle = function(ids) {
		var formData = $suddenlyTroubleForm.serialize();
		$.ajax({
			url : flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$suddenlyTroublePanel.closePanelModel();
				$suddenlyTroubleTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 查询数据信息
	var _searchHandler = function() {
		$suddenlyTroubleTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"startTime" : $("#publishStartTime").val(),
				"endTime" : $("#publishEndTime").val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// tabs切换事件
	var _showTabs = function(arr) {
		$("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
		switch (arr) {
		case '#suddenlyTrouble':
			_init();
			break;
		case '#rulesRegulations':
			inforMagRulesRegulationsInfor.init();
			break;
		case '#urgentContacts':
			inforMagUrgentContactsInfor.init();
			break;
		case '#accountVerify':
			inforAccountVerifyInfor.init();
			break;
		default:
		}
	};

	// 返回函数
	return {
		init : _init,
		edit : _edit,
		detail : _detail,
		deletes : _deletes,
		searchHandler : _searchHandler
	};
})(jQuery);

// 移动App:规章制度
var inforMagRulesRegulationsInfor = (function($) {
	// 查询条件
	var $rulesRegulationsStartTime = $("#rulesRegulations_startTime");
	var $rulesRegulationsEndTime = $("#rulesRegulations_endTime");
	// 搜索栏操作按钮
	var $rulesRegulationsSearchBtn = $("#rulesRegulations-searchBtn");
	var $rulesRegulationsAddBtn = $("#rulesRegulations-addBtn");
	var $rulesRegulationsExportBtn = $("#rulesRegulations-exportBtn");
	// 表格
	var $rulesRegulationsTable = $("#rulesRegulationsTable");
	// 弹框
	var $rulesRegulationPanel = $("#rulesRegulation-panel");
	var $rulesRegulationForm = $("#rulesRegulation-form");

	// 变量
	var loadOne = false;
	var flag;

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/rulesRegulations/findByPage',
		findByIdUrl : $.backPath + '/rulesRegulations/findById/',
		saveUrl : $.backPath + '/rulesRegulations/save',
		updateUrl : $.backPath + '/rulesRegulations/update/',
		deleteUrl : $.backPath + '/rulesRegulations/delete/',
		exportUrl : $.backPath + '/rulesRegulations/export/'
	};
	// 初始化
	var _init = function() {
		if (!loadOne) {
			_initRulesRegulationsTable();
			_bindClick();
			loadOne = !loadOne;
		} else {
			_searchHandler();
		}
	};

	// 绑定click
	var _bindClick = function() {
		$rulesRegulationsSearchBtn.bind("click", _searchHandler);
		$rulesRegulationsAddBtn.bind("click", _addRulesRegulations);
		$rulesRegulationsExportBtn.bind("click", _exportRulesRegulations);
		// 表单事件绑定
		$rulesRegulationForm.html5Validate(function() {
			_submitHandle();
			return false;
		});
	};
	// 表格
	var _initRulesRegulationsTable = function() {
		$rulesRegulationsTable
				.jqGrid(
						{
							url : urlPath.findByPageUrl,
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "编号", "名称", "内容", "签发单位", "发布时间", "操作" ],
							colModel : [
									{
										name : "identifier",
										index : "1",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "ruleName",
										index : "2",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "sendContent",
										index : "3",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "company",
										index : "4",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "sendDate",
										index : "5",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "handel",
										index : "6",
										align : "center",
										width : "30px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text details-link" data-func="rulesRegulations-delete" onclick="inforMagRulesRegulationsInfor.deleteRulesRegulations(\''
													+ id
													+ '\')"><span class="img-delete"></span>删除</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text update-link" data-func="rulesRegulations-update" onclick="inforMagRulesRegulationsInfor.editRulesRegulations(\''
													+ id
													+ '\')"><span class="img-edit"></span>修改</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text details-link" data-func="rulesRegulations-findById" onclick="inforMagRulesRegulationsInfor.detailsRulesRegulations(\''
													+ id
													+ '\')"><span class="img-details"></span>详情</label>'
													+ '</p>';
											return handlerTemp;
										}
									} ],
							loadonce : false,
							rownumbers : true,
							viewrecords : true,
							autowidth : true,
							rownumbers : true,
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#rulesRegulationsPager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("rulesRegulationsTable");
							}
						}).resizeTableWidth();
	};

	// 查询
	var _searchHandler = function() {
		$rulesRegulationsTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"startDate" : $rulesRegulationsStartTime.val(),
				"endDate" : $rulesRegulationsEndTime.val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// 新增
	var _addRulesRegulations = function() {
		flag = "";
		$rulesRegulationPanel.showPanelModel('新增规章制度');
		$rulesRegulationPanel.saveOrCheck(true);
	};

	// 编辑
	var _editRulesRegulations = function(ids) {
		flag = ids;
		var updateObj = $.getData(urlPath.findByIdUrl + ids);
		if (updateObj) {
			$rulesRegulationPanel.saveOrCheck(true);
			$rulesRegulationPanel.showPanelModel('修改规章制度信息').setFormSingleObj(
					updateObj);
		}
	};

	// 提交
	var _submitHandle = function(ids) {
		var formData = $rulesRegulationForm.find(".form-control");
		// 提交申请
		$.ajax({
			url : flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$rulesRegulationPanel.closePanelModel();
				$rulesRegulationsTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				// $.validateTip(json);
				Message.alert({
					Msg : json.msg,
					isModal : false
				});
			}
		});
	};

	// 导出
	var _exportRulesRegulations = function() {
		var startTime = $rulesRegulationsStartTime.val();
		var endTime = $rulesRegulationsEndTime.val();
		window.location.href = urlPath.exportUrl + "?startDate=" + startTime
				+ "&endDate=" + endTime;
	};

	// 详情
	var _detailsRulesRegulations = function(ids) {
		var rowObj = $.getData(urlPath.findByIdUrl + ids);
		$rulesRegulationPanel.saveOrCheck(false);
		$rulesRegulationPanel.showPanelModel('规章制度信息详情');
		$rulesRegulationForm.setFormSingleObj(rowObj);
	};

	// 删除
	var _deleteRulesRegulations = function(id) {
		Message.confirm({
			Msg : $.msg.sureDelete,
			okSkin : 'danger',
			iconImg : 'question',
			isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url : urlPath.deleteUrl + id,
					type : 'delete',
					async : false
				}).done(function(json) {
					if (json.code === 1) {
						$rulesRegulationsTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					} else {
						// $.validateTip(json.code);
						Message.alert({
							Msg : json.msg,
							isModal : false
						});
						$rulesRegulationsTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					}
				});
			}
		});
	};

	// 返回函数
	return {
		init : _init,
		detailsRulesRegulations : _detailsRulesRegulations,
		editRulesRegulations : _editRulesRegulations,
		deleteRulesRegulations : _deleteRulesRegulations,
		searchHandler : _searchHandler
	};
})(jQuery);

// 移动App:紧急联系人
var inforMagUrgentContactsInfor = (function($) {
	// 搜索条件
	// 搜索栏
	var $urgentContactsSearchBtn = $("#urgentContacts-searchBtn");
	var $urgentContactsAddBtn = $("#urgentContacts-addBtn");
	// 表格
	var $urgentContactsTable = $("#urgentContactsTable");
	// 弹窗
	var $contentPersonPanel = $("#contentPerson-panel");
	var $contentPersonForm = $("#contentPerson-form");

	var loadOne = false;
	var flag;

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/emergentLinkman/findByPage',
		findByIdUrl : $.backPath + '/emergentLinkman/findById/',
		saveUrl : $.backPath + '/emergentLinkman/save',
		updateUrl : $.backPath + '/emergentLinkman/update/',
		deleteUrl : $.backPath + '/emergentLinkman/delete/'
	};
	// 初始化
	var _init = function() {
		if (!loadOne) {
			_initUrgentContactsTable();
			_bindClick();
			loadOne = !loadOne;
			// 注册回车搜索事件
			$("#contacts-search-name").keyup(function(e) {
				e.preventDefault();
				if (e.keyCode === 13) {
					_searchHandler();
				}
				return false;
			});
		} else {
			_searchHandler();
		}
	};

	// 绑定click
	var _bindClick = function() {
		$urgentContactsSearchBtn.bind("click", _searchHandler);
		$urgentContactsAddBtn.bind("click", _addUrgentContacts);
		// 表单事件绑定
		$contentPersonForm.html5Validate(function() {
			_submitHandle();
			return false;
		});

	};
	// 表格
	var _initUrgentContactsTable = function() {
		$urgentContactsTable
				.jqGrid(
						{
							url : urlPath.findByPageUrl,
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "单位名称", "紧急联系人姓名", "手机号码", "固定号码",
									"操作" ],
							colModel : [
									{
										name : "companyName",
										index : "1",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "personName",
										index : "2",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "telephone",
										index : "3",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "fixedNo",
										index : "4",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "handel",
										index : "5",
										align : "center",
										width : "30px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text details-link" data-func="emergentLinkman-delete" onclick="inforMagUrgentContactsInfor.deleteUrgentContacts(\''
													+ id
													+ '\')"><span class="img-delete"></span>删除</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text update-link" data-func="emergentLinkman-update" onclick="inforMagUrgentContactsInfor.editUrgentContacts(\''
													+ id
													+ '\')"><span class="img-edit"></span>修改</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="emergentLinkman-findById" onclick="inforMagUrgentContactsInfor.detailsUrgentContacts(\''
													+ id
													+ '\')"><span class="img-details"></span>详情</label>'
													+ '</p>';
											return handlerTemp;
										}
									} ],
							loadonce : false,
							rownumbers : true,
							viewrecords : true,
							autowidth : true,
							rownumbers : true,
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#urgentContactsPager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("urgentContactsTable");
							}
						}).resizeTableWidth();
	};

	// 查询
	var _searchHandler = function() {
		$urgentContactsTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"personName" : $("#contacts-search-name").val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// 新增
	var _addUrgentContacts = function() {
		flag = "";
		$contentPersonPanel.showPanelModel('新增紧急联系人');
		$contentPersonPanel.saveOrCheck(true);
	};

	// 编辑
	var _editUrgentContacts = function(id) {
		flag = id;
		var updateObj = $.getData(urlPath.findByIdUrl + id);
		if (!updateObj) {
			return;
		}
		$contentPersonPanel.saveOrCheck(true);
		$contentPersonPanel.showPanelModel('修改紧急联系人信息').setFormSingleObj(
				updateObj);
	};

	var _detailsUrgentContacts = function() {
		var tempId = arguments[0];
		var gridData = $urgentContactsTable.jqGrid('getRowData', tempId);
		$contentPersonPanel.saveOrCheck(false);
		$contentPersonPanel.showPanelModel('紧急联系人信息详情');
		$contentPersonPanel.setFormSingleObj(gridData);
	};

	// 删除
	var _deleteUrgentContacts = function(id) {
		Message.confirm({
			Msg : $.msg.sureDelete,
			okSkin : 'danger',
			iconImg : 'question',
			isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url : urlPath.deleteUrl + id,
					type : 'delete',
					async : false
				}).done(function(json) {
					if (json.code === 1) {
						$urgentContactsTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					} else {
						// $.validateTip(json.code);
						Message.alert({
							Msg : json.msg,
							isModal : false
						});
						$urgentContactsTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					}
				});
			}
		});
	};

	// 提交
	var _submitHandle = function(ids) {
		var formData = $contentPersonForm.find(".form-control");
		// 提交申请
		$.ajax({
			url : flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$contentPersonPanel.closePanelModel();
				$urgentContactsTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				// $.validateTip(json);
				Message.alert({
					Msg : json.msg,
					isModal : false
				});
			}
		});
	};

	// 返回函数
	return {
		init : _init,
		editUrgentContacts : _editUrgentContacts,
		deleteUrgentContacts : _deleteUrgentContacts,
		detailsUrgentContacts : _detailsUrgentContacts
	};
})(jQuery);

// 移动App:账号审核
var inforAccountVerifyInfor = (function($) {
	// 搜索栏区域变量
	var $accountVerifyName = $("#account-verify-name");
	var $accountVerifyIcad = $("#account-verify-icad");
	var $accountVerifyStatus = $("#account-verify-status");
	var $accountVerifySearchBtn = $("#account-verify-searchBtn");
	// 表格展示变量
	var $accountVerifyTable = $("#account-verify-table");
	var $accountVerifyPager = $("#account-verify-pager");

	// 弹窗变量
	var $accountVerifyPanel = $("#accountVerify-panel");
	var $accountVerifyForm = $("#accountVerify-form");
	var $verifyStatusRadio = $("input[name='verifyStatusRadio']");
	
	//不通过原因内容框
	var $rejectContent = $("#rejectContent");
	
	// 模块变量
	var loadOne = false;
	var flag;

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/accountVerify/findByPage',
		findByIdUrl : $.backPath + '/accountVerify/findByParameter/',// 通过参数查询
		verifyUrl : $.backPath + '/accountVerify/verify',
		updateUrl : $.backPath + '/accountVerify/update/',
		cancelUrl : $.backPath + '/accountVerify/cancel/',
		amountUrl : $.backPath + '/accountVerify/amount',
		findDicCodeUrl : $.backPath + '/accountVerify/findDictByCode/114001'
	};
	var dicObj = $.getData(urlPath.findDicCodeUrl);
	// 初始化
	var _init = function() {
		_amount();
		if (!loadOne) {
			_initAccountVerifyTable();
			_bindClick();
			loadOne = !loadOne;
			// 注册回车搜索事件
			$accountVerifyName.keyup(function(e) {
				e.preventDefault();
				if (e.keyCode === 13) {
					_searchHandler();
				}
				return false;
			});
		} else {
			_searchHandler();
		}
	};
	// 初始化表格
	var _initAccountVerifyTable = function() {
		$accountVerifyTable.jqGrid(
				{
					url : urlPath.findByPageUrl,
					mtype : "POST",
					datatype : "JSON",
					colNames : [ "id","用户名", "姓名", "身份证", "所属物流企业名称", "注册时间",
							"审核状态", "操作" ],
					colModel : [ 
					    {
					     hidden:true,
						 key:true    	 
					 }, {
						name : "account",
						index : "1",
						align : "center",
						width : "30px",
						sortable : false
					}, {
						name : "personName",
						index : "2",
						align : "center",
						width : "30px",
						sortable : false
					}, {
						name : "identificationCardNo",
						index : "3",
						align : "center",
						width : "30px",
						sortable : false
					}, {
						name : "enterpriseName",
						index : "4",
						align : "center",
						width : "30px",
						sortable : false
					}, {
						name : "registerTime",
						index : "5",
						align : "center",
						width : "30px",
						sortable : false
					}, {
						name : "verifyStatus",
						index : "6",
						align : "center",
						width : "30px",
						sortable : false,
						formatter : function(cellValue) {
							return $.getDicNameByCode(cellValue, dicObj);
						}
					}, {
						name : "handel",
						index : "7",
						align : "center",
						width : "30px",
						sortable : false,
						formatter : function(cellValue, options, rowObject) {
							return _settingHandlerBtn(rowObject);
						}
					} ],
					loadonce : false,
					rownumbers : true,
					viewrecords : true,
					autowidth : true,
					rownumbers : true,
					height : true,
					rowNum : 10,
					rowList : [ 5, 10, 15 ],
					pager : $accountVerifyPager,
					gridComplete : function() {
						// 配置权限
						$.initPrivg("account-verify-table");
					}
				}).resizeTableWidth();
	};
	var _settingHandlerBtn = function(rowObject) {
		var verifyStatus = rowObject['verifyStatus'];
		var registerId = rowObject['registerId'];
		var option = "";
		if ('114001001' == verifyStatus) { // 待审核
			option = '<p class="jqgrid-handle-p">'
					+ '<label class="jqgrid-handle-text delete-link" data-func="accountVerify-findById" onclick="inforAccountVerifyInfor.detail(\''
					+ registerId
					+ '\')"><span class="img-details"></span>详情</label>'
					+ '<span>&nbsp&nbsp</span>'
					/*+
					
					 * '<label class="jqgrid-handle-text delete-link"
					 * data-func="goodsSource-delete"
					 * onclick="srcGoodManage.del(\''+ registerId +'\')"><span
					 * class="img-delete"></span>删除</label>' +
					 */
					/*'<label class="jqgrid-handle-text delete-link" data-func="accountVerify-cancel"   onclick="inforAccountVerifyInfor.cancel(\''
					+ registerId
					+ '\')"><span class="img-delete"></span>注销</label>&nbsp&nbsp'*/
					+ '<label class="jqgrid-handle-text delete-link" data-func="accountVerify-verify"   onclick="inforAccountVerifyInfor.verify(\''
					+ registerId
					+ '\')"><span class="img-ok"></span>审核</label>' +

					'</p>';
		} else if ('114001003' == verifyStatus) { // 已注册
			option = '<p class="jqgrid-handle-p">'
					+ '<label class="jqgrid-handle-text delete-link" data-func="accountVerify-findById" onclick="inforAccountVerifyInfor.detail(\''
					+ registerId
					+ '\')"><span class="img-details"></span>详情</label>&nbsp&nbsp'
					+ '<label class="jqgrid-handle-text delete-link" data-func="accountVerify-cancel"   onclick="inforAccountVerifyInfor.cancel(\''
					+ registerId
					+ '\')"><span class="img-delete"></span>注销</label>' +

					'</p>';
		} else if ('114001002' == verifyStatus) { // 已拒绝
			option = '<p class="jqgrid-handle-p">'
					+ '<label class="jqgrid-handle-text delete-link" data-func="accountVerify-findById" onclick="inforAccountVerifyInfor.detail(\''
					+ registerId
					+ '\')"><span class="img-details"></span>详情</label>'
					+ '<span>&nbsp&nbsp</span>'
					/*+ '<label class="jqgrid-handle-text delete-link" data-func="accountVerify-cancel"   onclick="inforAccountVerifyInfor.cancel(\''
					+ registerId
					+ '\')"><span class="img-delete"></span>注销</label>'*/
					+ '</p>';
		}
		return option;
	};
	var _bindClick = function() {

		$accountVerifySearchBtn.bind("click", _searchHandler);
		$verifyStatusRadio.bind("change", _rejectReason);
		
		//$rejectContent.bind("click",_clearContent);
		// 表单事件绑定
		$accountVerifyForm.html5Validate(function() {
			_submitHandle();
			return false;
		});

	};
	
	var _clearContent = function(){
		$rejectContent.val("");
	};
	var _searchHandler = function() {
		$accountVerifyTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"account" : $accountVerifyName.val(),
				"identificationCardNo" : $accountVerifyIcad.val(),
				"verifyStatus" : $accountVerifyStatus.val()

			},
			page : 1
		}).trigger("reloadGrid");
	};
	var _detail = function(id) {

		var rowObj;
		$.ajax({
			url : urlPath.findByIdUrl+id,
			type : 'get',
			async : false
		}).done(function(json) {
			if (json.code === 1) {
				rowObj = json.data;
				  
			} else {
				Message.alert({Msg: json.msg, isModal: false});
        		return;
			}
		});
		if( rowObj != null){
		   var verifyStatus = rowObj["verifyStatus"];
			rowObj["verifyStatus"] = $.getDicNameByCode(verifyStatus, dicObj);
			$accountVerifyPanel.saveOrCheck(false);
			$("#accountVerifyForVerify").addClass("hidden");
			$("#accountVerifyForDetail").removeClass("hidden");
			if (verifyStatus === '114001002') {
				$("#rejectReason").removeClass("hidden");
			} else {
				$("#rejectReason").addClass("hidden");
			}
			$accountVerifyPanel.showPanelModel('APP账号信息详情')
					.setFormSingleObj(rowObj);
		}
		
	};
	var _amount = function() {
		$.ajax({
			url : urlPath.amountUrl,
			type : 'get',
			async : false
		}).done(function(json) {
			if (json.code === 1) {
				$("#accountVerify-amount").html(json.data);
			} else {

			}
		});
	};
	var _cancel = function(id) {
		Message.confirm({
			Msg : $.msg.sureDelete,
			okSkin : 'danger',
			iconImg : 'question',
			isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url : urlPath.cancelUrl + id,
					type : 'delete',
					async : false
				}).done(function(json) {
					if (json.code === 1) {
						$accountVerifyTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
						_amount();
					} else {
						// $.validateTip(json.code);
						Message.alert({
							Msg : json.msg,
							isModal : false
						});
						$accountVerifyTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					}
				});
			}
		});

	};
	var _verify = function(id) {
		flag = id;
		var rowObj = $.getData(urlPath.findByIdUrl + id);
		if( !rowObj ){
			return;
		}
		var verifyStatus = rowObj["verifyStatus"];
		rowObj["verifyStatus"] = $.getDicNameByCode(verifyStatus, dicObj);
		$accountVerifyPanel.saveOrCheck(false);
		$accountVerifyPanel.showPanelModel('APP账号信息审核')
				.setFormSingleObj(rowObj);
		var $footer = $accountVerifyPanel.find(".panel-footer");
		$footer.removeClass("hidden");
		$("#accountVerifyForVerify").removeClass("hidden");
		$("#accountVerifyForDetail").addClass("hidden");
		_rejectReason();

	};
	var _rejectReason = function() {
		var verifyStatus = $("input[name='verifyStatusRadio']:checked")[0].id;
		if (verifyStatus === '114001002') {
			$("#rejectRadio").removeClass("hidden");
			$rejectContent.val("注册信息与实际信息不符");
		} else {
			$("#rejectRadio").addClass("hidden");
			$rejectContent.val("");
		}

	};
	var _submitHandle = function() {
		var verifyStatus = $("input[name='verifyStatusRadio']:checked")[0].id;
		var rejectReason = $rejectContent.val();
		var formData = {
			"registerId" : flag,
			"verifyStatus" : verifyStatus,
			"rejectReason" : rejectReason
		};
		// 提交申请
		$.ajax({
			url : urlPath.verifyUrl,
			type : 'POST',
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$accountVerifyPanel.closePanelModel();
				_amount();
				$accountVerifyTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				// $.validateTip(json);
				Message.alert({
					Msg : json.msg,
					isModal : false
				});
			}
		});
	};
	return {
		amount : _amount,
		init : _init,
		detail : _detail,
		cancel : _cancel,
		verify : _verify
	};
})(jQuery);
