/**
 * 、 *
 * 
 * @description:运单管理--模板管理
 * @author: wl
 * @time: 2016-8-30
 */

var wayBillTempMag = (function($) {
	var $wayBillTab 			= $("#wayBill-tabs");
	var loadOne 				= false;
	var $wayBillTempMagTable 	= $("#wayBillTempMagTable");
	var $wayBillTempMagSearchBtn= $("#wayBillTempMag-searchBtn");
	var $wayBillTempMagAddBtn 	= $("#wayBillTempMag-addBtn");
	var $wayBillForm 			= $("#wayBill-form");
	var $wayBillPanel 			= $("#wayBill-panel");
	var $wayBillTempPanel 		= $("#wayBillTemp-panel");
	var $wayBillTempForm 		= $("#wayBillTemp-form");
	var $wayBillStatusSpan 		= $("#wayBillStatusSpan");
	var $wayBillSubmmitTempBtn 	= $("#wayBill-submmitTempBtn");
	var $wayBillCancelBtn 		= $("#wayBillTemp-cancelBtn");
	var $wayBillTempDriver 		= $("#wayBillTempDriver");
	var $wayBillTempEscort 		= $("#wayBillTempEscort");
	var $wayBillGoodsTempNamePanel 	= $("#wayBillGoods-tempName-panel");
	var $wayBillGoodsTempNameForm 	= $("#wayBillGoods-tempName-form");
	var $wayBillTempLogisticsId 	= $("#wayBillTempLogisticsId");
	var dictData;// 数据字典返回数据
	var wayBillFlag = true;// 保存或更新的标示
	var wayBillBeforeFlag = true;// 点击新增货物前--运单走save，点击新增货物后update
	var wayBillTempId = "";// 货物修改临时Id
	var wayBillSaveTempID = "";// 模板id
	var saveWayBillFlag = false;// 生成运单
	var tempNum = 0;

	var countAddGoodsBtn = 0;// 记录点击新增的次数
	var checkFlag = true;
	var returnData = {};// 返回的企业相关信息
	var str = "Y"
			+ new Date().toISOString().split("T")[0].replace(/-/g, "")
					.substring(2);

	/** 货物表相关信息 */
	var $wayBillTempGoodsTable 	= $("#wayBillTempGoodsTable");
	var $wayBillGoodsTempPanel 	= $("#wayBillGoodsTemp-panel");
	var $wayBillGoodsTempForm 	= $("#wayBillGoodsTemp-form");
	var $wayBillTempGoodsAddBtn = $("#wayBillTempGoodsAddBtn");
	var $wayBillTempGoodCanel 	= $("#wayBillGoodTempCanel");

	var goodFlag = true;// 保存或更新的标示
	var goodsTempId = "";// 货物修改临时Id
	var goodsReload = true;

	var urlPath = {
		wayBillfindByPage : 	$.backPath + '/wayBillTemp/findByPage', // 查询接口
		wayBillsave : 			$.backPath + '/wayBillTemp/save', // 新增接口
		wayBillsaveWayBill : 	$.backPath + '/wayBillTemp/saveWayBill/', // 新增运单
		wayBillupdate : 		$.backPath + '/wayBillTemp/update/', // 更新接口
		wayBilldelete : 		$.backPath + '/wayBillTemp/delete/', // 删除接口
		wayBillfindById : 		$.backPath + '/wayBillTemp/findById/', // 查询接口
		findDicCodeUrl : 		$.backPath + '/wayBillTemp/findDictByCode/111',

		isExistTempName : 		$.backPath + '/wayBillTemp/isExistTempName/',

		// 查询企业信息相关
		findLogistics : 		$.backPath + '/wayBill/findLogistics/106001001', // 查询物流企业信息
		wayBillfindByEmployee : $.backPath + '/wayBill/findPersonType', // 查询从业人员接口
		wayBillfindCar : 		$.backPath + '/wayBill/findLicencePlateNo/', // 查询车辆信息接口

		/** 货物相关 */
		goodsfindByPage : 		$.backPath + '/wayBillTemp/goodsFindByPage', // 查询接口
		goodssave : 			$.backPath + '/wayBillTemp/goodsSave', // 新增接口
		goodsupdate : 			$.backPath + '/wayBillTemp/goodsupdate/', // 更新接口
		goodsdelete : 			$.backPath + '/wayBillTemp/goodsDelete/', // 删除接口
		goodsfindbyid : 		$.backPath + '/wayBillTemp/goodsFindById/', // 删除接口
		findOrderNoUrl : 		$.backPath + '/wayBillTemp/placeAndPoi'

	};

	var _init = function() {
		_bindEvent();
		if (!loadOne) {
			_initWayBillMagTable();
			loadOne = !loadOne;
			// 注册回车搜索事件
			$("#wayBillMag-searchBtn").keyup(function(e) {
				e.preventDefault();
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

			_associateTempSAndE("#wayBillTempstartpoint");
			_associateTempSAndE("#wayBillTempendpoint");
		} else {
			_searchHandler();
		}
	};

	// 起点、到点联想框
	var _associateTempSAndE = function(target) {
		// 初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url = urlPath.findOrderNoUrl;
		associateObj = $.associate(config);

		// 重写联想下拉框getData方法
		associateObj.getData = function(val, obj) {
			var list =
				$.getData(urlPath.findOrderNoUrl,"POST",{"orderNo":val});
			// 模拟数据
			/*var list = [ {
				"name" : "经开区一号",
				"longitude" : "10",
				"latitude" : "10"
			}, {
				"name" : "经开区2号",
				"longitude" : "11",
				"latitude" : "11"
			} ]*/
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
			$(target).val(name);
			// 缓存经纬度
			if (target === "#wayBillTempstartpoint") {
				$("#startLngTempInput").val(data.longitude);
				$("#startLatTempInput").val(data.latitude);
			} else {
				$("#endLngTempInput").val(data.longitude);
				$("#endLatTempInput").val(data.latitude);
			}

		};
	};

	// 绑定事件函数
	var _bindEvent = function() {
		$wayBillTempMagSearchBtn.bind("click", _searchHandler);
		$wayBillTempMagAddBtn.unbind("click").bind("click", _wayBillMagShowAdd);
		// $wayBillSubmmitTempBtn.bind("click",_saveTemp);
		$wayBillTempGoodsAddBtn.unbind("click").bind("click",
				_wayBillGoodsShowAdd);
		$wayBillTempGoodCanel.bind("click", _closeSecondPanel);
		$wayBillTempDriver.bind("change", _driverChange);
		$wayBillTempEscort.bind("change", _supercargoChange);
		$wayBillTempLogisticsId.bind("change", _logisticsId);

		// 绑定表单验证事件
		$wayBillGoodsTempForm.html5Validate(function() {
			window.fg = !window.fg;
			if (window.fg) {
				_submitGoogsTemp();
			}
			return false;
		});
		$wayBillTempForm.html5Validate(function() {
			_submitWayBillMag();
			return false;
			/*
			 * },{ validate: function () { if(!$("#startLngTempInput").val() ||
			 * !$("#startLatTempInput").val()){
			 * $("#wayBillTempstartpoint").testRemind("请重新选择起点"); return false; }
			 * 
			 * if(!$("#endLngTempInput").val() || !$("#endLatTempInput").val()){
			 * $("#wayBillTempendpoint").testRemind("请重新选择到点"); return false; }
			 * 
			 * return true; }
			 */
		});

		dictData = $.getData(urlPath.findDicCodeUrl);
		returnData.logistics = $.getData(urlPath.findLogistics, 'POST');
		returnData.driverData = [];
		returnData.supercargoData = [];
		_fillSelect("#wayBillTempLogisticsId", returnData.logistics,
				'logistics');

	};

	// 物流企业change事件
	var _logisticsId = function() {
		$("#wayBillTempEtperInfor").find("input").val("");
		var selectValue = $("#wayBillTempLogisticsId").val();
		if (selectValue) {
			returnData.carData = $.getData(
					urlPath.wayBillfindCar + selectValue, 'POST');
			_fillSelect("#wayBillTempCarNo", returnData.carData, "carInfor");
			// 驾驶员信息
			returnData.driverData = wayBillMag.getEmployeeData(selectValue,
					"101001001");
			// 押运员信息
			returnData.supercargoData = wayBillMag.getEmployeeData(selectValue,
					"101001002");
			_fillSelect("#wayBillTempDriver", returnData.driverData, "driver");
			_fillSelect("#wayBillTempEscort", returnData.supercargoData,
					"supercargo");
		}
	};

	// 驾驶员change事件
	var _driverChange = function() {
		var onselectValue = $wayBillTempDriver.val();
		$("#wayBillTempDrivChange").find("input").val(" ");
		$.each(returnData.driverData, function(index, content) {
			if (content.id === onselectValue) {
				$("#wayBillTempDriverid").val(content.identificationCardNo);
				$("#wayBillTempDriverno").val(content.driveCardNo);
				$("#wayBillTempDriverphone").val(content.telephone);
			}
		});
	};

	// 押运员change事件
	var _supercargoChange = function() {
		var onselectValue = $wayBillTempEscort.val();
		$("#wayBillTempEscortChange").find("input").val(" ");
		$.each(returnData.supercargoData, function(index, content) {
			if (content.id === onselectValue) {
				$("#wayBillTempEscortid").val(content.identificationCardNo);
				$("#wayBillTempEscortno").val(content.occupationNo);
				$("#wayBillTempEscortphone").val(content.telephone);
			}
		});
	};

	/**
	 * 填充select选择框
	 * 
	 * @param DomID
	 *            dom节点ID
	 * @param objData
	 *            传入数据
	 * @param flag
	 *            标示
	 */
	var _fillSelect = function(DomID, objData, flag) {
		var $this = $(DomID);
		var optionVal = $this.find("option:eq(0)").val();
		if (optionVal === "请选择" || optionVal === "") {
			$this.find("option:not(:eq(0))").remove();
			$this.find("option").remove()
		} else {
			$this.find("option").remove();
		}
		var flagOption = "<option value=''></option>";
		$this.append(flagOption);
		$.each(objData, function(index, item) {
			if (flag == 'carInfor') {
				$this.append('<option value="' + item.name + '">' + item.name
						+ '</option>');
			} else if (flag == "logistics") {
				$this.append('<option value="' + item.corporateNo + '">'
						+ item.enterpriseName + '</option>');
			} else if (flag == "supercargo") {
				$this.append('<option value="' + item.id + '">'
						+ item.personName + '</option>');
			} else if (flag == "driver") {
				$this.append('<option value="' + item.id + '">'
						+ item.personName + '</option>');
			}
		});
	};

	// 新增
	var _wayBillMagShowAdd = function() {
		// tempNum++;
		// $("#wayBillTemptempname").text('运单模板'+ tempNum).val('运单模板'+ tempNum);
		_checknoFlag();

		// 查看时不能进行编辑等操作
		checkFlag = true;
		saveWayBillFlag = false;
		$("#wayBillTempGoodsAddBtn").removeClass("hidden");
		$("#wayBillTemp-submmitBtn").html("保存");
		$("#wayBillTemp-cancelBtn").html("取消");
		$wayBillTempForm.saveOrCheck(true);
		$wayBillTempPanel.showPanelModel("新增模板信息");
		$wayBillTempForm.find(".mustFlag").removeClass("must");
		$("#tempNameMustAdd").addClass("must");
		$(".inputMust").removeAttr("required");
		$("#wayBillTempconsumerno").removeAttr("required");
		$("#wayBillTempNoRow").addClass("hidden");
		wayBillFlag = true;
		wayBillTempId = "";
		_initGoodsTable();
		$wayBillTempGoodsTable.jqGrid('clearGridData');
		$("#wayBillTemp-panel").find('.panel-close').bind("click",
				_wayBillCancel);
		$wayBillCancelBtn.bind("click", _wayBillCancel);
		$("#wayBillTempCarNo").val("");
		$("#wayBillTempDriver").val("");
		$("#wayBillTempEscort").val("");
	};

	// 取消按钮绑定
	var _wayBillCancel = function() {
		if (wayBillTempId) {
			var url = urlPath.wayBilldelete + wayBillTempId;
			$.ajax({
				url : url,
				type : "delete",
				async : false
			}).done(function(json) {
				if (json.code === 1) {
					$wayBillTempMagTable.jqGrid('setGridParam', {
						postData : {},
						page : 1
					}).trigger("reloadGrid");
				} else {
					Message.alert({
						Msg : json.msg,
						isModal : false
					});
				}
			});
		}
		$("#wayBillTemp-panel").find('.panel-close').unbind("click",
				_wayBillCancel);
		$wayBillCancelBtn.unbind("click", _wayBillCancel);
	};

	// 修改
	var _wayBillMagShowUpdate = function() {
		_checknoFlag();
		checkFlag = true;
		saveWayBillFlag = false;
		$("#wayBillTempGoodsAddBtn").removeClass("hidden");// 去掉新增货物按钮
		wayBillFlag = false;
		wayBillTempId = arguments[0];
		var updateObj = $.getData(urlPath.wayBillfindById + wayBillTempId);
		if (updateObj) {
			$("#wayBillTemp-submmitBtn").html("保存");
			$("#wayBillTemp-cancelBtn").html("取消");
			$wayBillTempForm.saveOrCheck(true);
			$wayBillTempPanel.showPanelModel("修改模板信息").setFormSingleObj(
					updateObj);
			$wayBillTempForm.find(".mustFlag").removeClass("must");
			$(".inputMust").removeAttr("required");
			$("#tempNameMustAdd").addClass("must");
			$("#wayBillTempNoRow").addClass("hidden");
			$("#wayBillchecknoSpan").removeClass("hidden");
			$("#wayBillTempLogisticsId").val(updateObj.logisticsId);

			// 获取企业信息
			_logisticsId();
			$("#wayBillTemphandcarno").val(updateObj.handcarno);
			// 给企业信息赋值
			$("#wayBillTempCarNo").val(updateObj.carno);
			$("#wayBillTempDriver").val(updateObj.driverId);
			$("#wayBillTempEscort").val(updateObj.escortId);
			_driverChange();
			_supercargoChange();

			// 点击编辑是相当于先执行初始化表格数据，在执行到查询表格
			_initGoodsTable();
			$wayBillTempGoodsTable.jqGrid('setGridParam', {
				postData : {
					"id" : arguments[0]
				},
				page : 1
			}).trigger("reloadGrid");
			$wayBillCancelBtn.unbind("click", _wayBillCancel);
		} else {
			Message.alert({
				Msg : prompt.checkAndDo,
				isModal : false
			});
		}
	};

	// 删除
	var _wayBillMagShowDel = function() {
		var url = urlPath.wayBilldelete + arguments[0];
		var updateObj = $.getData(urlPath.wayBillfindById + arguments[0]);
		if (updateObj) {
			Message.confirm({
				Msg : $.msg.sureDelete,
				okSkin : 'danger',
				iconImg : 'question',
				isModal : true
			}).on(function(flag) {
				if (flag) {
					$.ajax({
						url : url,
						type : "delete",
						async : false
					}).done(function(json) {
						if (json.code === 1) {
							$wayBillTempMagTable.jqGrid('setGridParam', {
								postData : {},
								page : 1
							}).trigger("reloadGrid");
						} else {
							Message.alert({
								Msg : json.msg,
								isModal : false
							});
						}
					});
				}
			});
		} else {
			Message.alert({
				Msg : prompt.checkAndDo,
				isModal : false
			});
		}
	};

	// 模板弹窗
	var _showTempPanel = function() {
		$wayBillGoodsTempNamePanel.showPanelModel("模板信息");
		wayBillSaveTempID = arguments[0];
	};

	// 生成运单信息
	var _saveWayBill = function() {
		$wayBillTempForm.saveOrCheck(true);
		_checknoFlag();
		saveWayBillFlag = true;
		// var randomNum =$.randomCount(5).join("");
		$("#wayBillTempcheckno").addClass("hidden");
		$("#wayBillTempSaveWayBill").html("").removeClass("hidden").html(
				str + '(5位自动生成序号)');
		checkFlag = true;
		$("#wayBillTempGoodsAddBtn").removeClass("hidden");// 去掉新增货物按钮
		wayBillFlag = false;
		wayBillTempId = arguments[0];
		var updateObj = $.getData(urlPath.wayBillfindById + wayBillTempId);
		if (updateObj) {
			$(".inputMust").attr("required", "required");
			$("#wayBillTemp-submmitBtn").html("保存");
			$("#wayBillTemp-cancelBtn").html("取消");
			/* $wayBillTempForm.saveOrCheck(true); */
			$wayBillTempPanel.showPanelModel("生成运单信息").setFormSingleObj(
					updateObj);
			if (updateObj.carno) {
				_selectedById("#wayBillTempCarNo", updateObj.carno);
			} else {
				$("#wayBillTempCarNo").find("option").remove();
			}
			if (updateObj.driver) {
				_selectedById("#wayBillTempDriver", updateObj.driver);
			} else {
				$("#wayBillTempDriver").find("option").remove();
			}
			if (updateObj.escort) {
				_selectedById("#wayBillTempEscort", updateObj.escort);
			} else {
				$("#wayBillTempEscort").find("option").remove();
			}
			$("#wayBillchecknoSpan, #wayBillTempNoRow").removeClass("hidden");
			// 点击编辑是相当于先执行初始化表格数据，在执行到查询表格
			_initGoodsTable();
			$wayBillTempGoodsTable.jqGrid('setGridParam', {
				postData : {
					"id" : arguments[0]
				},
				page : 1
			}).trigger("reloadGrid");

		} else {
			Message.alert({
				Msg : prompt.checkAndDo,
				isModal : false
			});
		}
	};

	// 选中
	var _selectedById = function(id, param2) {
		var option = ' <option value=' + param2 + ' selected>' + param2
				+ '</option>';
		if ($(id).find("option").length == 0) {
			$(id).append(option);
		} else {
			$(id).find("option").remove();
			$(id).append(option);
		}

	};

	// 详情
	var _wayBillMagShowDetail = function() {
		_checknoFlag();
		var obj = $.getData(urlPath.wayBillfindById + arguments[0]);
		if (obj) {
			$wayBillTempForm.saveOrCheck(false);
			$wayBillTempPanel.showPanelModel('运单模板详情').setFormSingleObj(obj);
			$wayBillStatusSpan.detailSpan(dictData['111001'], obj.checkstatus);
			$("span[name=logisticsId]").text(
					obj.wlEnterpriseName == null ? '' : obj.wlEnterpriseName);
			wayBillTempId = arguments[0];
			_initGoodsTable();
			$wayBillTempGoodsTable.jqGrid('setGridParam', {
				postData : {
					"id" : arguments[0]
				},
				page : 1
			}).trigger("reloadGrid");
			// 查看时不能进行编辑等操作
			checkFlag = false;
			$("#wayBillTempGoodsAddBtn").addClass("hidden");
			wayBillTempId = "";
		} else {
			Message.alert({
				Msg : prompt.checkAndDo,
				isModal : false
			});
		}
	};
	// 生成托运单号-----新增、修改、查询 为201010+（八位字符），生成运单时为正常
	var _checknoFlag = function() {
		$("#wayBillTempcheckno").removeClass("hidden")
				.html(str + "+(5位自动生成序号)");
		$("#wayBillTempSaveWayBill").addClass("hidden");
	};

	// 新增或修改提交
	var _submitWayBillMag = function() {
		// var goodsData =
		// $("#wayBillTempGoodsTable").jqGrid("getRowData").length;
		// if(goodsData == 0){
		// Message.alert({Msg: '请添加货物信息后再提交运单信息!',iconImg: 'warning',isModal:
		// false});
		// return;
		// }
		// 提交运单信息
		if (saveWayBillFlag) {
			_submitWayBill();
		} else {
			// 提交模板信息
			var formData = $wayBillTempForm.packageFormData();
			var url;
			if (wayBillFlag) {
				// 判断模板信息是否重复
				var name = $.getData(urlPath.isExistTempName, "POST", {
					"tempName" : $("#wayBillTemptempname").val()
				});
				if (name) {
					Message.show({
						Msg : "模板名称已存在！",
						isModal : false
					});
					return false;
				}
				if (wayBillBeforeFlag) {
					url = urlPath.wayBillsave;
				} else {
					url = urlPath.wayBillupdate + wayBillTempId;
				}
			} else {
				url = urlPath.wayBillupdate + wayBillTempId;
			}
			$.ajax({
				url : url,
				type : 'POST',
				async : false,
				data : formData
			}).done(function(json) {
				if (json.code === 1) {
					if (wayBillFlag) {
						if (wayBillBeforeFlag) {
							if (countAddGoodsBtn === 0) {
								$wayBillTempPanel.closePanelModel();
							} else {
								wayBillTempId = json.data;
							}
						} else {
							wayBillTempId = "";
							$wayBillTempPanel.closePanelModel();
						}
					} else {
						wayBillTempId = "";
						$wayBillTempPanel.closePanelModel();
					}

					// 成功
					wayBillFlag = true;
					wayBillBeforeFlag = true;
					wayBillTempId = "";
					wayBillSaveTempID = "";
					countAddGoodsBtn = 0;
					// ie浏览器修改保存时卡死解决
					window.setTimeout(function() {
						$wayBillTempMagTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					}, 500);

				} else {
					$.validateTip(json);
				}
			});
		}

	};
	// 提交运单
	var _submitWayBill = function() {
		var goodsInfor = $("#wayBillTempGoodsTable").jqGrid("getRowData").length;
		if (goodsInfor != 0) {
			var formData = $wayBillTempForm.packageFormData();
			formData.checkstatus = '111001001';
			formData.checkno = $("#wayBillTempSaveWayBill").text();
			$.ajax({
				url : urlPath.wayBillsaveWayBill + wayBillTempId,
				type : 'POST',
				async : false,
				data : formData
			}).done(function(json) {
				if (json.code === 1) {
					Message.alert({
						Msg : "运单已生成"
					});
					$wayBillTempPanel.closePanelModel();
					saveWayBillFlag = false;
					wayBillSaveTempID = "";
				} else {
					$.validateTip(json);
				}
			});
		} else {
			Message.alert({
				Msg : "请添加货物信息后再提交运单信息!",
				iconImg : 'warning',
				isModal : false
			});
		}
	};

	// 点击新增货物时提交运单表单
	var _submitAddWayBillGoods = function() {
		var formData = $wayBillTempForm.packageFormData();
		var url = urlPath.wayBillsave;
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				wayBillTempId = json.data;
				$wayBillTempMagTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 初始化表格
	var _initWayBillMagTable = function() {
		var width = $("#wayBillMag").width();
		$wayBillTempMagTable
				.jqGrid(
						{
							url : urlPath.wayBillfindByPage,
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "模板名称", "起点", "到点", "品名", "驾驶员",
									"车牌号码", "创建时间", "操作" ],
							colModel : [
									{
										name : "tempname",
										index : "1",
										align : "center",
										sortable : false
									},
									{
										name : "startpoint",
										index : "2",
										align : "center",
										sortable : false
									},
									{
										name : "endpoint",
										index : "3",
										align : "center",
										sortable : false
									},
									{
										name : "goodsname",
										index : "4",
										align : "center",
										sortable : false
									},
									{
										name : "driver",
										index : "5",
										align : "center",
										sortable : false
									},
									{
										name : "carno",
										index : "5",
										align : "center",
										sortable : false
									},
									{
										name : "createTime",
										index : "7",
										align : "center",
										sortable : false
									},
									{
										name : "",
										index : "8",
										title : "hidden",
										align : "center",
										width : "400px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillTempMag-update" title="修改" onclick="wayBillTempMag.wayBillMagUpdate(\''
													+ rowObject.id
													+ '\')"><span class="img-edit"></span>修改</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillTempMag-findById" title="详情" onclick="wayBillTempMag.wayBillMagDetail(\''
													+ rowObject.id
													+ '\')"><span class="img-details"></span>详情</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillTempMag-delete" title="删除" onclick="wayBillTempMag.wayBillMagDel(\''
													+ rowObject.id
													+ '\')"><span class="img-delete"></span>删除</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillTempMag-saveWayBill" title="生成运单"  onclick="wayBillTempMag.saveWayBill(\''
													+ rowObject.id
													+ '\')"><span class="img-delete"></span>生成运单</label>'
													+ '</p>';
											return handlerTemp;
										}
									} ],
							loadonce : false,
							rownumbers : true,
							viewrecords : true,
							// autowidth: false,
							width : width,
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#wayBillTempMagPage",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("wayBillTempMagTable");
							}
						}).resizeTableWidth();
	};
	// 搜索
	var _searchHandler = function() {
		var data = {
			"startTime" : $("#wayBillTempMagStartTime").val(),
			"endTime" : $("#wayBillTempMagEndTime").val()
		};
		$wayBillTempMagTable.jqGrid('setGridParam', {
			postData : data,
			page : 1
		}).trigger("reloadGrid")
	};

	// 初始化货物表
	var _initGoodsTable = function() {
		$wayBillTempGoodsTable
				.jqGrid({
					url : urlPath.goodsfindByPage,
					mtype : "POST",
					datatype : "JSON",
					postData : {
						"id" : wayBillTempId
					},
					colNames : [ "品名", "包装", "数量", "重量(吨)", "体积(立方米)", "规格",
							"批次", "操作" ],
					colModel : [
							{
								name : "goodsname",
								index : "1",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "pack",
								index : "2",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "quantity",
								index : "3",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "weight",
								index : "4",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "volume",
								index : "5",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "format",
								index : "6",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "batch",
								index : "7",
								align : "center",
								width : "30px",
								sortable : false
							},
							{
								name : "",
								index : "8",
								align : "left",
								width : "30px",
								sortable : false,
								formatter : function(cellValue, options,
										rowObject) {
									if (checkFlag) {
										var handlerTemp = '<p class="jqgrid-handle-p">'
												+ '<label class="jqgrid-handle-text delete-link" onclick="wayBillTempMag.updateGoods(\''
												+ rowObject.id
												+ '\')"><span class="img-edit"></span>修改</label>'
												+ '<label class="jqgrid-handle-text delete-link" onclick="wayBillTempMag.deleteGoods(\''
												+ rowObject.id
												+ '\')"><span class="img-delete"></span>删除</label>'
												+ '</p>';
									} else {
										handlerTemp = '';
									}

									return handlerTemp;
								}
							} ],
					loadonce : false,
					rownumbers : true,
					viewrecords : true,
					// autowidth: true,
					width : 1000,
					height : true,
					rowNum : 5,
					// rowList: [5, 10, 15],
					pager : "#wayBillTempGoodsTablePage",
					gridComplete : function() {
						// 配置权限
						// $.initPrivg();
						if (checkFlag) {
							$("#goodsTablePlace .delete-link").removeClass(
									"hidden");
						} else {
							$("#goodsTablePlace .delete-link").addClass(
									"hidden");
						}
					}
				});

	};

	/**
	 * 货物信息新增
	 */
	var _wayBillGoodsShowAdd = function() {
		window.fg = false;
		if (saveWayBillFlag) {
			// 新增运单时，物流企业必须先选择
			$wayBillGoodsTempForm.find(".mustFlag").addClass("must");
			$(".inputMust").attr("required", "required");
			var selectValue = $("#wayBillTempLogisticsId").val();
			if (selectValue) {
				_wayBillGoodsAdd();
			} else {
				Message.alert({
					Msg : '请选择物流企业后再新增货物信息',
					iconImg : 'warning',
					isModal : false
				});
			}
		} else {
			_wayBillGoodsAdd();
			// $wayBillGoodsTempForm.find(".mustFlag").removeClass("must");
			// $(".inputMust").removeAttr("required");
			// $("#tempGoodsNameMustAdd").addClass("must");

		}
	};

	// 新增-货物 -生成运单的判断
	var _wayBillGoodsAdd = function() {
		// 点击新增货物时---提交保存运单
		if (countAddGoodsBtn === 0) {
			if (wayBillFlag) {
				_submitAddWayBillGoods();
				countAddGoodsBtn++;
			}
		}
		countAddGoodsBtn;
		wayBillBeforeFlag = false;
		$("#wayBillGoodsTemp-form")[0].reset();
		goodFlag = true;
		// $wayBillGoodsTempForm.saveOrCheck(true);
		$wayBillGoodsTempPanel.showPanelModel("货物信息");
		$wayBillGoodsTempPanel.find(".panel-close").bind("click",
				_closeSecondPanel);
	};

	// 删除--货物
	var _deleteGoods = function() {
		var url = urlPath.goodsdelete + arguments[0];
		Message.confirm({
			Msg : $.msg.sureDelete,
			okSkin : 'danger',
			iconImg : 'question',
			isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url : url,
					type : "delete",
					async : false
				}).done(function(json) {
					if (json.code === 1) {
						$wayBillTempGoodsTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
					} else {
						Message.alert({
							Msg : json.msg,
							isModal : false
						});
					}
				});
			}
		});
	};

	// 更新--货物
	var _updateGoods = function() {
		var goodsTempId = arguments[0];
		if (saveWayBillFlag) {
			// 新增运单时，物流企业必须先选择
			$wayBillGoodsTempForm.find(".mustFlag").addClass("must");
			$(".inputMust").attr("required", "required");
			_updateGoodsMust(goodsTempId);
		} else {
			_updateGoodsMust(goodsTempId);
			// $wayBillGoodsTempForm.find(".mustFlag").removeClass("must");
			// $(".inputMust").removeAttr("required");
		}
	};

	// 更新--货物--打开弹窗去除必填项与否
	var _updateGoodsMust = function() {
		goodFlag = false;
		goodsTempId = arguments[0];
		var updateObj = $.getData(urlPath.goodsfindbyid + goodsTempId);
		$wayBillGoodsTempForm.saveOrCheck(true);
		$wayBillGoodsTempPanel.showPanelModel('修改货物信息').setFormSingleObj(
				updateObj);
		$wayBillGoodsTempPanel.find(".panel-close").bind("click",
				_closeSecondPanel);
	};

	var _submitGoogsTemp = function() {
		var formData = $wayBillGoodsTempForm.packageFormData();
		formData.waybillTempId = wayBillTempId;
		var url = goodFlag ? urlPath.goodssave : urlPath.goodsupdate
				+ goodsTempId;
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				goodsTempId = "";
				_closeSecondPanel();
				$wayBillTempGoodsTable.jqGrid('setGridParam', {
					postData : {
						"id" : wayBillTempId
					},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 关闭货物信息弹窗
	var _closeSecondPanel = function() {
		$wayBillGoodsTempPanel.fadeOut(300);
		$(".panel-mask").css("z-index", "1000");
	};
	_init();
	return {
		init : _init,
		wayBillMagUpdate : _wayBillMagShowUpdate,
		wayBillMagDel : _wayBillMagShowDel,
		wayBillMagDetail : _wayBillMagShowDetail,
		searchHandler : _searchHandler,
		showTempPanel : _showTempPanel,
		updateGoods : _updateGoods,
		deleteGoods : _deleteGoods,
		saveWayBill : _saveWayBill

	};

})(jQuery);
