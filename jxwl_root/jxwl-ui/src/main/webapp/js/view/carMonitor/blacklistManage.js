/**
 * @description:黑名单管理
 * @author: guqh
 * @time: 2016-7-26
 */

// 报警管理信息
var blacklistManageInfo = (function($) {
	// 模块变量
	var $blacklistSearchBtn = $("#blacklist-searchBtn");
	var $blacklistExport = $("#blacklist-export");
	var $inforMagTabs = $('#inforMag-tabs');
	var $blacklistTable = $("#blacklistTable");
	var $blacklistAddBtn = $("#blacklist-addBtn");
	var $blacklistPanel = $("#blacklist-panel");
	// 查看黑名单的详情表单头
	var $blacklistPanel2 = $("#blacklist-panel2");
	var $blacklistForm2 = $("#blacklist-form2");

	var $autoBlacklistPanel = $("#auto-blacklist-panel");
	var $autoBlacklistBtn = $("#autoBlacklist-btn");
	var $blacklistForm = $("#blacklist-form");
	var $autoBlacklistForm = $("#auto-blacklist-form");

	// 搜索栏blacklistType
	var $objectType = $("#objectType-search-name");
	var $searchItem = $("#search-item");

	// 加入黑名单弹窗
	var $blacklistObjectType = $("#blacklist-objectType");
	var $searchItemTwo = $("#search-item-two");
	var $addEnterpriseItem = $("#enterpriseName-add-name");

	var $typeFlagHidden = $("#typeFlag-hidden");
	var $typeFlagAddName = $("#typeFlag-add-name");

	// 判断类型
	var typeFlag;

	var loadOne = false;
	var flag;
	var associateObj;
	var submitChangeVal = null, corporateNoVal = null;

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/blacklistManage/findByPage',
		updateUrl : $.backPath + '/blacklistManage/update',
		saveUrl : $.backPath + '/blacklistManage/save',
		deleteUrl : $.backPath + '/blacklistManage/delete/',
		findByIdUrl : $.backPath + '/blacklistManage/findById/',
		findDictByCodeUrl : $.backPath
				+ "/blacklistManage/findDictByCode/104001",
		findDictTypeByCodeUrl : $.backPath
				+ "/blacklistManage/findDictByCode/104002",
		findAlarmNumByIdUrl : $.backPath + "/blacklistManage/findAlarmNumById",
		updateAlarmNumUrl : $.backPath + "/blacklistManage/updateAlarmNum/",
		findEnterpriseUrl : $.backPath + '/logisticst/findAllEnterprise',// 查询所有企业
		findDriverUrl : $.backPath + '/employee/findAllDriver',// 查询所有驾驶员
		findVehicleUrl : $.backPath + '/dangerVehicle/findAllVehicle'// 查询所有车辆

	};
	// 数据初始化
	var _init = function() {
		// 第一个tab标签 要先解除绑定在进行绑定事件
		$blacklistExport.unbind("click").bind("click", _export);
		$blacklistSearchBtn.unbind("click").bind("click", _searchHandler);
		$blacklistAddBtn.unbind("click").bind("click", _addBlacklist);
		$autoBlacklistBtn.unbind("click").bind("click", _setAutoBlacklist);
		_initBlacklistTable();

		// 搜索栏驾驶员类型显示
		// _select(urlPath.findDictByCodeUrl, "objectType-search-name");
		_select(urlPath.findDictByCodeUrl, "blacklist-objectType");
		_select(urlPath.findDictTypeByCodeUrl, "blacklist-type-name");

		// 表单事件绑定
		$blacklistForm.html5Validate(function() {
			_submitAddHandle();
			return false;
		});

		// 绑定事件
		_bindClick();

		// 初始化联想下拉框
		var config = {};
		config.inputObj = "#blackItem-add";
		config.url = urlPath.findEnterpriseUrl;
		associateObj = $.associate(config);
	};

	// 绑定click
	var _bindClick = function() {
		// 表单事件绑定
		$autoBlacklistForm.html5Validate(function() {
			_submitAutoBlacklist();
			return false;
		});

		$blacklistSearchBtn.bind("click", {
			type : "blacklist"
		}, _searchHandler);
		$blacklistExport.bind("click", {
			type : "blacklist"
		}, _export);
		$objectType.bind("change", _switchByType);
		$blacklistObjectType.bind("change", _switchByTypeTwo);
		// 注册回车搜索事件
		$("#enterpriseName-search-flag").keyup(function(e) {
			e.preventDefault();
			if (e.keyCode === 13) {
				_searchHandler();
			}
			return false;
		});
	};
	// 黑名单弹窗切换类型
	var _switchByTypeTwo = function() {
		var secrchTypeTwo = $blacklistObjectType.val();
		if (secrchTypeTwo === "104001001") {
			// 清空拉黑原因
			$("#blacklistReason_add").val("");
			// 清空拉黑对象
			$("#blackItem-add").val("");
			corporateNoVal = null;
			typeFlag = 1;
			$("#enterpriseName-add-name").text("企业名称");
			$("#blackItem-add").attr("pattern", "");
			// 重写联想下拉框getData方法
			associateObj.getData = function(val, obj) {
				var list = $.getData(urlPath.findEnterpriseUrl, "POST", {
					"enterpriseName" : val
				});
				submitChangeVal = "";
				if (list && list.length > 0) {
					obj.setData(list);
					obj.associateDiv.show();
					obj.associateDiv.empty();

					for ( var i = 0; i < list.length && obj.showCnt; i++) {
						var data = list[i];
						var str = "<div style='cursor:pointer;padding:2px;10px;'>"
								+ data.name + "</div>";
						obj.associateDiv.append(str);
					}
				}
			};
			// 重写联想下拉框selectData方法
			associateObj.selectData = function(data, divObj, obj) {
				var name = divObj.text();
				if (data && data.name) {
					name = data.name;
				}
				$(obj.inputObj).val(name);
				submitChangeVal = data.corporateNo;
			};
		} else if (secrchTypeTwo === "104001002") {
			// 清空拉黑原因
			$("#blacklistReason_add").val("");
			// 清空拉黑对象
			$("#blackItem-add").val("");
			corporateNoVal = null;
			typeFlag = 2;
			$("#enterpriseName-add-name").text("驾驶员姓名");
			$("#blackItem-add").attr("pattern", "");
			// 重写联想下拉框getData方法
			associateObj.getData = function(val, obj) {
				var list = $.getData(urlPath.findDriverUrl, "POST", {
					"personName" : val
				});
				submitChangeVal = "";
				if (list && list.length > 0) {
					obj.setData(list);
					obj.associateDiv.show();
					obj.associateDiv.empty();
					for ( var i = 0; i < list.length && obj.showCnt; i++) {
						var data = list[i];
						var str = "<div style='cursor:pointer;padding:2px;10px;'>"
								+ data.name
								+ '('
								+ data.identificationCardNo
								+ ')' + "</div>";
						obj.associateDiv.append(str);
					}
				}
			};
			// 重写联想下拉框selectData方法
			associateObj.selectData = function(data, divObj, obj) {
				var name = divObj.text();
				if (data && data.name) {
					name = data.name;
				}
				$(obj.inputObj).val(name);
				submitChangeVal = data.id;
				corporateNoVal = data.corporateNo;
			};
		} else if (secrchTypeTwo === "104001003") {
			// 清空拉黑原因
			$("#blacklistReason_add").val("");
			// 清空拉黑对象
			$("#blackItem-add").val("");
			corporateNoVal = null;
			typeFlag = 3;
			$("#enterpriseName-add-name").text("车牌号");
			// $("#blackItem-add").attr("pattern","^(?![0-9]+$)(?![!@#$^_]+$)(?![a-zA-Z]+$)[0-9a-zA-Z!@#$^_]{8,16}$");
			// 重写联想下拉框getData方法
			associateObj.getData = function(val, obj) {
				var list = $.getData(urlPath.findVehicleUrl, "POST", {
					"licenseNo" : val
				});
				submitChangeVal = "";
				if (list && list.length > 0) {
					obj.setData(list);
					obj.associateDiv.show();
					obj.associateDiv.empty();
					for ( var i = 0; i < list.length && obj.showCnt; i++) {
						var data = list[i];
						var str = "<div style='cursor:pointer;padding:2px;10px;'>"
								+ data.name + "</div>";
						obj.associateDiv.append(str);
					}
				}
			};
			// 重写联想下拉框selectData方法
			associateObj.selectData = function(data, divObj, obj) {
				var name = divObj.text();
				if (data && data.name) {
					name = data.name;
				}
				$(obj.inputObj).val(name);
				submitChangeVal = data.id;
				corporateNoVal = data.corporateNo;
			};
		}
	};

	// 根据搜索类型加载表格和另一个搜索条件
	var _switchByType = function() {
		// 隐藏的列
		var hideCol = [];
		// 显示的列
		var showCol = [];
		var postdata = {
			"enterpriseName" : "",
			"driver" : "",
			"vehicle" : ""
		};
		$("#enterpriseName-search-flag").val("");
		var secrchType = $objectType.val();
		if (secrchType === "104001001") {
			$searchItem.text("企业名称");
			hideCol = [ "driver", "vehicle", "enterpriseNameTwo" ];
			showCol = [ "enterpriseName" ];
			postdata.objectType = "104001001";
			typeFlag = 1;
		} else if (secrchType === "104001002") {
			$searchItem.text("驾驶员姓名");
			hideCol = [ "enterpriseName", "vehicle" ];
			showCol = [ "driver", "enterpriseNameTwo" ];
			postdata.objectType = "104001002";
			typeFlag = 2;
		} else if (secrchType === "104001003") {
			$searchItem.text("车牌号");
			hideCol = [ "enterpriseName", "driver" ];
			showCol = [ "vehicle", "enterpriseNameTwo" ];
			postdata.objectType = "104001003";
			typeFlag = 3;
		}
		$blacklistTable.jqGrid('setGridParam', {
			postData : postdata,
			page : 1
		}).jqGrid("hideCol", hideCol).jqGrid("showCol", showCol).trigger(
				"reloadGrid").setGridWidth($(window).width() - 280);
	};
	// 表格
	var _initBlacklistTable = function() {
		$searchItem.clear;
		$("#enterpriseName-search-flag").empty();
		$blacklistTable
				.jqGrid(
						{
							url : urlPath.findByPageUrl,
							postData : {
								"objectType" : 104001001
							},
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "企业名称", "驾驶员姓名", "车牌号", "所属物流企业",
									"拉黑时间", "拉黑类型", "操作人", "拉黑原因", "操作" ],
							colModel : [
									{
										name : "enterpriseName",
										index : "1",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "driver",
										index : "2",
										align : "left",
										width : "30px",
										sortable : false,
										hidden : true
									},
									{
										name : "vehicle",
										index : "3",
										align : "center",
										width : "30px",
										sortable : false,
										hidden : true
									},
									{
										name : "enterpriseNameTwo",
										index : "4",
										align : "center",
										width : "30px",
										sortable : false,
										hidden : true,
										formatter : function(cellValue,
												options, rowObject) {
											if (rowObject.enterpriseName) {
												return rowObject.enterpriseName;
											} else {
												return "";
											}

										}
									},
									{
										name : "blacklistDate",
										index : "5",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "blacklistType",
										index : "6",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "operatePerson",
										index : "7",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "blacklistReason",
										index : "8",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "",
										index : "9",
										align : "center",
										width : "50px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text delete-link" data-func="blacklistManage-delete" onclick="blacklistManageInfo.deleteBlacklist(\''
													+ id
													+ '\')"><span class="img-delete"></span>移出</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text details-link" data-func="blacklistManage-findById" onclick="blacklistManageInfo.detailsBlacklist(\''
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
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#blacklistPager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("blacklistTable");
							}
						}).resizeTableWidth();
	};

	// 自动拉黑设置
	var _setAutoBlacklist = function() {
		var updateObj;
		// 提交申请
		$.ajax({
			url : urlPath.findAlarmNumByIdUrl,
			type : 'GET',
			async : false
		}).done(function(json) {
			if (json.code === 1) {
				updateObj = json;
			} else {
				$.validateTip(json);
			}
		});
		updateObj;
		$autoBlacklistPanel.saveOrCheck(true);
		$autoBlacklistPanel.showPanelModel('自动拉黑设置').setFormSingleObj(
				updateObj.data);
	};

	// 提交自动拉黑设置
	var _submitAutoBlacklist = function() {
		var formData = $autoBlacklistForm.packageFormData();
		var url;
		url = urlPath.updateAlarmNumUrl + formData.id;
		// 提交申请
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$autoBlacklistPanel.closePanelModel();
				$blacklistTableTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 新增
	var _addBlacklist = function() {
		flag = "";
		$blacklistPanel.showPanelModel('新增黑名单信息');
		$blacklistPanel.saveOrCheck(true);
		_switchByTypeTwo();
	};

	// 编辑
	var _editBlacklist = function(ids) {
		flag = ids;
		var updateObj = $.getData(urlPath.findByIdUrl + ids);
		$blacklistPanel.saveOrCheck(true);
		$blacklistPanel.showPanelModel('黑名单信息').setFormSingleObj(updateObj);
	};

	// 详情
	var _detailsBlacklist = function(ids) {
		var rowObj = $.getData(urlPath.findByIdUrl + ids);
		if (rowObj != null) {
			$blacklistPanel2.saveOrCheck(false);
			$blacklistPanel2.showPanelModel('黑名单信息详情');

			$blacklistForm2.setFormSingleObj(rowObj);
			$("#blacklistTypeDiv").removeClass("hidden");
			var secrchType = $objectType.val();
			if (secrchType === "104001002") {
				$("#detailDriver").removeClass("hidden");
				$("#detailCarNo").addClass("hidden");
			} else if (secrchType === "104001003") {
				$("#detailDriver").addClass("hidden");
				$("#detailCarNo").removeClass("hidden");
			} else {
				$("#detailDriver").addClass("hidden");
				$("#detailCarNo").addClass("hidden");
			}

		} else {
			Message.alert({
				Msg : "查找的记录已经被删除",
				isModal : false
			});

		}
		;
	};
	// 删除记录
	var _deleteBlacklist = function(id) {
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
						$blacklistTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					} else {
						$.validateTip(json);
					}
				});
			}
		});
	};

	// 提交
	var _submitAddHandle = function(ids) {
		var formData = $blacklistForm.packageFormData();
		if (typeFlag === 1) {
			// 拉黑对象为企业时提交法人代码
			formData.enterpriseName = $("#blackItem-add").val();
			/*
			 * if(!formData.corporateNo){ Message.alert({ Msg : "该企业不存在",
			 * isModal : false }); return; }
			 */
		} else if (typeFlag === 2) {
			// 拉黑对象为驾驶员时提交驾驶员姓名和驾驶员所在企业的法人代码
			formData.driver = $("#blackItem-add").val();
			/*
			 * formData.blackListno = corporateNoVal; if(!corporateNoVal){
			 * Message.alert({ Msg : "该驾驶员不存在", isModal : false }); return; }
			 */
		} else if (typeFlag === 3) {
			// 拉黑对象为车辆时提交车牌号和车辆所属企业的车牌号
			formData.vehicle = $("#blackItem-add").val();
			/*
			 * formData.blackListno = corporateNoVal; if(!corporateNoVal){
			 * Message.alert({ Msg : "该车牌号不存在", isModal : false }); return; }
			 */
		}
		// 提交申请
		$.ajax({
			url : flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$blacklistPanel.closePanelModel();
				$blacklistTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 上传插件初始化
	/*
	 * var _upload_1 = function (fileUpload) { if (uploadObj) {
	 * uploadObj.destroy(); } uploadObj = fileUpload.Huploadify({ auto: true,
	 * fileTypeExts:
	 * '*.xls;*.xlsx;*.png;*.jpg;*.jpeg;*.PNG;*.JPG;*.JPEG;*.doc;*.docx;*.pdf',
	 * multi: true, //formData: {"fileCode": fileCode}, deleteURL:
	 * urlPath.deleteFileUrl, fileSizeLimit: 51200,// 50M showUploadedPercent:
	 * true, showUploadedSize: true, removeTimeout: 9999999, uploader:
	 * urlPath.uploadFileUrl, filesLen: 5 }); };
	 */

	// 查询数据信息
	var _searchHandler = function() {

		// typeFlag = 1;
		// 拉黑类型
		var t = $("#objectType-search-name").val();
		// 模糊查询条件
		var a = $("#enterpriseName-search-flag").val();
		var postData = {
			"enterpriseName" : "",
			"driver" : "",
			"vehicle" : "",
			"blacklistDateStart" : $("#blacklistStartTime").val(),
			"blacklistDateEnd" : $("#blacklistEndTime").val(),
			"objectType" : t
		};

		// 驾驶员
		if (t == "104001002") {
			postData.enterpriseName = "";
			postData.driver = a;
			postData.vehicle = "";
			// 车牌
		} else if (t == "104001003") {
			postData.enterpriseName = "";
			postData.driver = "";
			postData.vehicle = a;
		} else {
			postData.enterpriseName = a;
			postData.driver = "";
			postData.vehicle = "";
		}
		$blacklistTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
		postData = "";
	};

	// 导出
	var _export = function() {
		if (arguments.length !== 0) {
			var flag = arguments[0].data.type;
			var id;
			if (flag === "blacklist") {
				id = $blacklistTable.jqGrid("getGridParam", "selrow");
			} else {
				id = $escortTable.jqGrid("getGridParam", "selrow");
			}
			window.location.href = urlPath.exportUrl + "/" + id;
		}

	};

	/**
	 * @selectId 下拉框的ID
	 * @url 数据字典的地址 以及对应的值 根据返回值填充select
	 */
	var _select = function(url, selectId) {
		var select = "";
		dicObj = $.getData(url);
		$.each(dicObj, function(index, content) {
			select += '<option value= "' + dicObj[index].code + '">'
					+ dicObj[index].name + '</option>';
		});
		$("#" + selectId).append(select);
	};

	_init();

	// 返回函数
	return {
		// init: _init,
		detailsBlacklist : _detailsBlacklist,
		deleteBlacklist : _deleteBlacklist,
		editBlacklist : _editBlacklist,
		select : _select,
		setAutoBlacklist : _setAutoBlacklist
	};
})(jQuery);
