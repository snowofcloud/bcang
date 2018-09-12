/**
 * @description:监控管理-从业人员信息和接入平台
 * @author: xiaoqx
 * @time: 2016-7-19
 */
// 从业人员信息
var inforMagEmployeeInfor = (function($) {
	// 模块变量
	var $employeeSearchBtn = $("#employee-searchBtn");
	var $employeeExport = $("#employee-export");
	var $employeeTable = $("#employeeTable");
	var $employeeAddBtn = $("#employee-addBtn");
	var $employeePanel = $("#employee-panel");
	var $employeeForm = $("#employee-form");
	var $enterpriseSelect = $("#enterprise-select");
	var $driverSearchBtn = $("#driver-searchBtn");
	var $driverExport = $("#driver-export");
	var $driverTable = $("#driverTable");
	var $escortSearchBtn = $("#escort-searchBtn");
	var $escortExport = $("#escort-export");
	var $escortTable = $("#escortTable");
	var $employeeDeatil = $("#employeeDeatil");
	var $searchName = $("#enterprise-search-name");
	var userFlag = true;
	var dicObj;
	var loadOne = false;
	var flag;

	// 请求路径
	var urlPath = {
		findByPageUrlForEmployee : $.backPath + '/employee/findByPage',
		findByIdUrl : $.backPath + '/employee/findById/',
		saveUrl : $.backPath + '/employee/save/',
		updateUrl : $.backPath + '/employee/update/',
		deleteUrl : $.backPath + '/employee/delete/',
		exportUrl : $.backPath + '/employee/export',
		findEnterpriseUrl : $.backPath + '/employee/findEnterprise/',
		findDictByCodeUrl : $.backPath + "/employee/findDictByCode/108"
	};
	// 初始化
	var _init = function() {
		//$("#enterprise-search-name").val("");
		$searchName.removeAttr("readonly");
		userFlag = true;
		if (!loadOne) {
			dicObj = $.getData(urlPath.findDictByCodeUrl);
			_initEmployeeTable();
			_bindClick();
			loadOne = !loadOne;
			$("#occupationPersonType-employee").settingsOptions(
					dicObj["100001"], false);
			$("#personSex-employee").settingsOptions(dicObj["107001"], false);
			$("#personType-search-name")
					.settingsOptions(dicObj["100001"], true);
			$("#personType-search-name >*").eq(0).text('全部');
			/*// 犯罪记录
			$("#crimeRecord-employee").settingsOptions(dicObj["002001"], false);*/
			// 交通事故
			$("#trafficAccident-employee").settingsOptions(dicObj["003001"],
					false);
			// 文化程度
			/*$("#educationDegree-employee").settingsOptions(dicObj["006001"],
					false);*/
			/*// 驾照类型
			$("#driveVehicleType-employee").settingsOptions(dicObj["007001"],
					false);*/
			// 车型
			// $("#driveVehicleType-employee").settingsOptions(dicObj["109001"],
			// false);
			dicObj = $.getData(urlPath.findDictByCodeUrl);
		} else {
			_searchHandler();
		}
	};
	// 获取企业名称
	/*
	 * var _getEnterpriseName = function(){ var returnData; var url; url =
	 * urlPath.findEnterpriseUrl; $.ajax({ url: url, type: 'POST', async: false,
	 * dataType:"JSON", data: returnData }).done(function(json){ returnData =
	 * json; }); var enterpriseSelect =""; $.each(returnData.rows, function
	 * (index, content) { if(returnData.rows[index].enterpriseName){
	 * enterpriseSelect += '<option value= "' + returnData.rows[index].id +
	 * '">' + returnData.rows[index].enterpriseName + '</option>'; } });
	 * $("#enterprise-select").append(enterpriseSelect); };
	 */

	// 绑定click
	var _bindClick = function() {
		$employeeAddBtn.bind("click", _addEmployee);
		// 表单事件绑定
		$employeeForm.html5Validate(function() {
			_submitAddHandle();
			return false;
		});
		$employeeSearchBtn.bind("click", {
			type : "employee"
		}, _searchHandler);
		$employeeExport.bind("click", {
			type : "employee"
		}, _export);
		// 注册回车搜索事件
		$("#enterprise-search-name").keyup(function(e) {
			e.preventDefault();
			if (e.keyCode === 13) {
				_searchHandler();
			}
			return false;
		});
		$("#personName-search-name").keyup(function(e) {
			e.preventDefault();
			if (e.keyCode === 13) {
				_searchHandler();
			}
			return false;
		});
		$("#personType-search-name").bind("change", _searchHandler);
		$("#occupationPersonType-employee").bind("change", function() {
			_showDivHandel(false, this);
		});
	};
	// 表格
	var _initEmployeeTable = function() {
		var searchType = "101001001";
		$employeeTable
				.jqGrid(
						{
							url : urlPath.findByPageUrlForEmployee,
							postData : {
								"enterpriseName" : $("#enterprise-search-name")
										.val()
							},
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "企业名称", "姓名", "人员类型", "性别", "民族",
									"联系方式", "操作" ],
							colModel : [
									{
										name : "enterpriseName",
										index : "1",
										align : "center",
										width : "20px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											if (topMenuMag
													.isLogisticsEnterpriseUser()
													|| topMenuMag
															.isChemicalEnterpriseUser()
													&& userFlag) {
												userFlag = false;
												$searchName.val(cellValue);
												$searchName.attr("readonly",
														"readonly");

											}
											return cellValue;
										}
									},
									{
										name : "personName",
										index : "2",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "occupationPersonType",
										index : "3",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "personSex",
										index : "4",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "nation",
										index : "5",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "telephone",
										index : "6",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "handel",
										index : "10",
										align : "center",
										width : "40px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text delete-link" data-func="employee-update" onclick="inforMagEmployeeInfor.editEmployee(\''
													+ id
													+ '\')"><span class="img-edit"></span>修改</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="employee-findById" onclick="inforMagEmployeeInfor.detailsEmployee(\''
													+ id
													+ '\')"><span class="img-details"></span>详情</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="employee-delete" onclick="inforMagEmployeeInfor.deleteEmployee(\''
													+ id
													+ '\')"><span class="img-delete"></span>删除</label>'
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
							pager : "#employeePager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("employeeTable");
							}
						}).resizeTableWidth();
		_showTab(searchType);
	};

	// 根据人员类型显示不同字段
	var _showDivHandel = function(flag, type) {
		if (flag) {
			handel = type;
		} else {
			handel = $(type).val();
		}
		if (handel === "101001001") {// 驾驶员
			$("#forDriverShow").removeClass("hidden");
			// $("#forSupercargoShow").addClass("hidden");
		} else if (handel === "101001002") {// 押运员
			$("#forDriverShow").addClass("hidden");
			$("#forSupercargoShow").removeClass("hidden");
		}
	};

	// 新增
	var _addEmployee = function() {
		flag = "";
		$employeePanel.saveOrCheck(true);
		$employeePanel.showPanelModel('新增从业人员信息');
		var type = $("#occupationPersonType-employee").val();
		_showDivHandel(true, type);
		$(".detailShowForEmployee").addClass("hidden");
		// 身份证号在新增时 可编辑
		$("#identificationCardNo-employee").removeAttr("readonly");
	};

	// 编辑
	var _editEmployee = function(ids) {
		flag = ids;
		var updateObj = $.getData(urlPath.findByIdUrl + ids);
		if (!updateObj) {
			return;
		}
		$employeePanel.saveOrCheck(true);
		$employeePanel.showPanelModel('修改从业人员信息').setFormSingleObj(updateObj);
		_showDivHandel(true, updateObj.occupationPersonType);
		$(".detailShowForEmployee").addClass("hidden");
		// 身份证号在编辑时 不可编辑
		$("#identificationCardNo-employee").attr("readonly", "readonly");
	};

	// 删除
	var _deleteEmployee = function(id) {
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
						$employeeTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					} else {
						// $.validateTip(json.code);
						Message.alert({
							Msg : json.msg,
							isModal : false
						});
					}
				});
			}
		});
	};

	// 详情
	var _detailsEmployee = function(ids) {
		var rowObj = $.getData(urlPath.findByIdUrl + ids);
		if (rowObj) {
			// 获得crimeRecord,trafficAccident和 driveVehicleType 以及文化程度、驾照类型的数据字典的值
			var crimeRecord = rowObj['crimeRecord'];
			crimeRecord = $.getDicNameByCode(crimeRecord, dicObj["002001"]);
			rowObj['crimeRecord'] = crimeRecord;

			var trafficAccident = rowObj['trafficAccident'];
			trafficAccident = $.getDicNameByCode(trafficAccident,
					dicObj["003001"]);
			rowObj['trafficAccident'] = trafficAccident;
			
			var educationDegree = rowObj['educationDegree'];
			educationDegree = $.getDicNameByCode(educationDegree, dicObj["006001"]);
			rowObj['educationDegree'] = educationDegree;
			
			var driveVehicleType = rowObj['driveVehicleType'];
			driveVehicleType = $.getDicNameByCode(driveVehicleType, dicObj["007001"]);
			rowObj['driveVehicleType'] = driveVehicleType;

			$employeePanel.saveOrCheck(false);
			$employeePanel.showPanelModel('从业人员信息详情');
			$employeeForm.setFormSingleObj(rowObj);
			if (rowObj.occupationPersonType) {
				$("#occupationPersonTypeStr").text(
						$.getDicNameByCode(rowObj.occupationPersonType,
								dicObj["100001"]));
			}
			if (rowObj.personSex) {
				$("#personSexStr").text(
						$.getDicNameByCode(rowObj.personSex, dicObj["107001"]));

			}
			if (rowObj.crimeRecord) {
				$("#crimeRecordStr").text(
						$.getDicNameByCode(rowObj.personSex, dicObj["002001"]));

			}
			if (rowObj.trafficAccident) {
				$("#trafficAccidentStr").text(
						$.getDicNameByCode(rowObj.personSex, dicObj["003001"]));

			}
			if (rowObj.educationDegree) {
				$("#educationDegreeStr").text(
						$.getDicNameByCode(rowObj.personSex, dicObj["006001"]));

			}
			if (rowObj.driveVehicleType) {
				$("#driveVehicleTypeStr").text(
						$.getDicNameByCode(rowObj.personSex, dicObj["007001"]));

			}

			_showDivHandel(true, rowObj.occupationPersonType);
			$(".detailShowForEmployee").removeClass("hidden");
		} else {
			Message.alert({
				Msg : '暂无数据',
				isModal : false
			});
		}

	};

	// 提交
	var _submitAddHandle = function(ids) {
		var formData = $employeeForm.find(".form-control");
		// 提交申请
		$.ajax({
			url : flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$employeePanel.closePanelModel();
				$employeeTable.jqGrid('setGridParam', {
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

	// 查询数据信息
	var _searchHandler = function() {
		$employeeTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrlForEmployee,
			postData : {
				"id" : "",
				"enterpriseName" : $("#enterprise-search-name").val(),
				"personName" : $("#personName-search-name").val(),
				"occupationPersonType" : $("#personType-search-name").val()
			},
			page : 1
		}).trigger("reloadGrid");

		var personType = $("#personType-search-name").val();
		_showTab(personType);
	};

	// 根据查询条件控制表格字段的显示
	var _showTab = function(type) {
		if (type === "101001002") {
			$employeeTable.jqGrid("hideCol", "driveVehicleType");
			$employeeTable.jqGrid("hideCol", "driveCardNo");
			$employeeTable.jqGrid("showCol", "occupationNo");
		} else {
			$employeeTable.jqGrid("showCol", "driveVehicleType");
			$employeeTable.jqGrid("showCol", "driveCardNo");
			$employeeTable.jqGrid("hideCol", "occupationNo");
		}
		$employeeTable.jqGrid({
			autowidth : false
		}).setGridWidth($(window).width() - 280);
	};

	/**
	 * 导出
	 */
	var _export = function() {
		var enterpriseName = $("#enterprise-search-name").val();
		var personName = $("#personName-search-name").val();
		var occupationPersonType = $("#personType-search-name").val();
		$.ajax({
			url : urlPath.findByPageUrlForEmployee,
			type : 'POST',
			async : false,
			data : {
				"enterpriseName" : enterpriseName,
				"personName" : personName,
				"occupationPersonType" : occupationPersonType,
				"page" : 1,
				"rows" : 10
			}
		}).done(
				function(json) {
					if (json.code === 1 && json.rows && 0 < json.rows.length) {
						enterpriseName = encodeURI(enterpriseName, "UTF-8");
						personName = encodeURI(personName, "UTF-8");
						window.location.href = urlPath.exportUrl
								+ "?enterpriseName=" + enterpriseName
								+ "&personName=" + personName
								+ "&occupationPersonType="
								+ occupationPersonType;
					} else {
						Message.alert({
							Msg : "暂无数据，无法导出",
							isModal : false
						});
					}
				});
	};

	// 返回函数
	return {
		init : _init,
		detailsEmployee : _detailsEmployee,
		editEmployee : _editEmployee,
		deleteEmployee : _deleteEmployee,
		searchHandler : _searchHandler
	};
})(jQuery);

// 接入平台展示
var inforMagInsertPlatformInfor = (function($) {
	// 模块变量
	var $insertPlatformSearchBtn = $("#insertPlatform-searchBtn");
	var $platformSearchName = $("#platform-search-name");
	var $insertPlatformTable = $("#insertPlatformTable");

	var loadOne = false;
	var flag;

	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/platform/findByPage'
	};

	// 初始化
	var _init = function() {
		if (!loadOne) {
			_initInsertPlatformTable();
			_bindClick();
			loadOne = !loadOne;
			// 注册回车搜索事件
			$platformSearchName.keyup(function(e) {
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
		$insertPlatformSearchBtn.bind("click", _searchHandler);
	};
	// 表格
	var _initInsertPlatformTable = function() {
		$insertPlatformTable.jqGrid({
			url : urlPath.findByPageUrl,
			mtype : "POST",
			datatype : "JSON",
			colNames : [ "平台名称", "主要数据" ],
			colModel : [ {
				name : "platformName",
				index : "1",
				align : "center",
				width : "30px",
				sortable : false
			}, {
				name : "mainData",
				index : "2",
				align : "center",
				width : "30px",
				sortable : false
			} ],
			loadonce : false,
			rownumbers : true,
			viewrecords : true,
			autowidth : true,
			rownumbers : true,
			height : true,
			rowNum : 10,
			rowList : [ 5, 10, 15 ],
			pager : "#insertPlatformPager",
			gridComplete : function() {
				// 配置权限
				// $.initPrivg();
			}
		}).resizeTableWidth();
	};

	// 查询数据信息
	var _searchHandler = function() {
		$insertPlatformTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"platformName" : $platformSearchName.val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// 返回函数
	return {
		init : _init
	};
})(jQuery);
