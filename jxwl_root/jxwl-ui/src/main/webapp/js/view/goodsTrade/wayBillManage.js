/**
 * @description:运单管理--运单管理
 * @author: wl
 * @time: 2016-8-30
 */

// 货源管理
var wayBillMag = (function($) {
	var $wayBillTab = $("#wayBill-tabs");
	var loadOne = false;
	var $wayBillMagTable = $("#wayBillMagTable");
	var $wayBillMagSearchBtn = $("#wayBillMag-searchBtn");
	var $wayBillMagAddBtn = $("#wayBillMag-addBtn");
	
	//打印
	var $wayBillMagPrint = $("#wayBillMag-print");
	
	var $wayBillPanel = $("#wayBill-panel");
	var $wayBillForm = $("#wayBill-form");
	var $wayBillStatusSpan = $("#wayBillStatusSpan");
	var $wayBillSubmmitTempBtn = $("#wayBill-submmitTempBtn");
	var $wayBillCancelBtn = $("#wayBill-cancelBtn");
	var $wayBillDriver = $("#wayBillDriver");
	var $wayBillEscort = $("#wayBillEscort");
	var $wayBillGoodsTempNamePanel = $("#wayBillGoods-tempName-panel");
	var $wayBillGoodsTempNameForm = $("#wayBillGoods-tempName-form");
	var $wayBillLogisticsId = $("#wayBillLogisticsId");
	var dictData;// 数据字典返回数据
	var wayBillFlag = true;// 保存或更新的标示
	var wayBillBeforeFlag = true;// 点击新增货物前--运单走save，点击新增货物后update
	var wayBillTempId = "";// 货物修改临时Id
	var wayBillSaveTempID = "";// 模板id

	var countAddGoodsBtn = 0;// 记录点击新增的次数
	var checkFlag = true;
	var returnData = {};// 返回的企业相关信息

	/** 货物表相关信息 */
	var $wayBillGoodsTable = $("#wayBillGoodsTable");
	var $wayBillGoodsPanel = $("#wayBillGoods-panel");
	var $wayBillGoodsForm = $("#wayBillGoods-form");
	var $wayBillGoodsAddBtn = $("#wayBillGoodsAddBtn");
	var $wayBillGoodCanel = $("#wayBillGoodCanel");
	
	/** 添加驾驶员和押运员姓名获取的值*/
	var $driver = $("#wayBillDriver");
	var $escort = $("#wayBillEscort");

	var goodFlag = true;// 保存或更新的标示
	var goodsTempId = "";// 货物修改临时Id
	var goodsReload = true;

	var urlPath = {
		wayBillfindByPage : $.backPath + '/wayBill/findByPage', // 查询接口
		wayBillsave : $.backPath + '/wayBill/save', // 新增接口
		wayBillsaveTemp : $.backPath + '/wayBill/saveTemp', // 新增模板接口
		wayBillupdate : $.backPath + '/wayBill/update/', // 更新接口
		wayBilldelete : $.backPath + '/wayBill/delete/', // 删除接口
		wayBillfindById : $.backPath + '/wayBill/findById/', // 查询接口
		findDicCodeUrl : $.backPath + '/wayBill/findDictByCode/111',
		// 查询企业信息相关
		findLogistics : $.backPath + '/wayBill/findLogistics/106001001', // 查询物流企业信息
		wayBillfindByEmployee : $.backPath + '/wayBill/findPersonType', // 查询从业人员接口
		wayBillfindCar : $.backPath + '/wayBill/findLicencePlateNo/', // 查询车辆信息接口
		/** 货物相关 */
		goodsfindByPage : $.backPath + '/wayBill/goodsFindByPage', // 查询接口
		goodsfindByPagePrint : $.backPath + '/wayBill/goodsFindByPagePrint',
		goodssave : $.backPath + '/wayBill/goodsSave', // 新增接口
		goodsupdate : $.backPath + '/wayBill/goodsupdate/', // 更新接口
		goodsdelete : $.backPath + '/wayBill/goodsDelete/', // 删除接口
		goodsfindbyid : $.backPath + '/wayBill/goodsFindById/', // 删除接口
		// 模糊查询url
		findOrderNoUrl : $.backPath + '/orderManage/findOrderNo/', // 查询货单号接口
		findOrderMessage : $.backPath + '/wayBill/findOrderMessage/', // 查询运单信息
		//isHasWayBillNo : $.backPath + '/wayBill/isHasWayBillNo/',
		findAccurateOrderNoUrl : $.backPath
				+ '/orderManage/findAccurateOrderNo/', // 精确查询货单号接口
		findPlaceAndPoi : $.backPath + '/wayBillTemp/placeAndPoi'
				
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

			// tab切换
			$wayBillTab.find("a").click(function() {
				var $this = $(this);
				var tabName = $this.attr("href");
				sessionStorage.setItem("active_li", tabName);
				_showTabs(tabName);
			});
			var activeLi = sessionStorage.getItem("active_li");
			if (activeLi) {
				_showTabs(activeLi);
			}
		} else {
			_searchHandler();
		}
		var target = "#orderNo";
		_associatefunc(target);
		_associateS("#wayBillstartpoint");
		_associateE("#wayBillendpoint");
	};

	// 运单号联想框
	var _associatefunc = function(target) {
		// 初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url = urlPath.findOrderNoUrl;
		associateObj = $.associate(config);
		// $(target).attr("pattern","");
		// 重写联想下拉框getData方法
		associateObj.getData = function(val, obj) {
			var list = $.getData(urlPath.findOrderNoUrl, "POST", {
				"orderNo" : val
			});
			if (list && list.length > 0) {
				obj.setData(list);
				obj.associateDiv.show();
				obj.associateDiv.empty();

				for ( var i = 0; i < list.length && obj.showCnt; i++) {
					var data = list[i];
					var str = "<div style='cursor:pointer;padding:2px;10px;'>"
							+ data.orderNo + "</div>";
					obj.associateDiv.append(str);
				}
			} else {
				_readOnlyFalseOrder();
				$("#wayBill-form").find("input[id!=orderNo]").val("");
				$("#wayBillLogisticsId").attr("disabled", false);
				/* 清空单元格 */
				$("#wayBillLogisticsId").val("");
				$("#wayBillCarNo").val("");
				$("#wayBillDriver").val("");
				$("#wayBillEscort").val("");
			}
		};
		// 重写联想下拉框selectData方法
		associateObj.selectData = function(data, divObj, obj) {
			var name = divObj.text();
			if (data && data.name) {
				name = data.name;
			}
			$(obj.inputObj).val(name);
			var existOrder = $.getData(urlPath.findOrderMessage, "GET", {
				"orderNo" : name
			});
			if (existOrder) {
				$("#wayBill-orderMessage").setFormSingleObj(existOrder);
				//兼容ie11
				$("#wayBillLogisticsId").each(function() {
					for(var i = 0; i<this.options.length; i++){
						if(this.options[i].value == existOrder.logisticsId){
							this.options[i].selected = "selected";
							break;
						}
					}
				});
				//$("#wayBillLogisticsId").val(existOrder.logisticsId);
				_readOnlyOrder();
				_getEmployeeData();
				_logisticsId();
			}
		};
	};

	// 设置运单信息可编辑
	var _readOnlyFalseOrder = function() {
		$("#wayBillstartpoint").attr("readonly", false);
		$("#wayBillendpoint").attr("readonly", false);
		$("#alarmNo-add").attr("readonly", false);
		$("#wayBilldeliveryunitperson").attr("readonly", false);
		$("#wayBilldeliveryunitphone").attr("readonly", false);
		$("#alarmNo-addr").attr("readonly", false);
		$("#wayBillachieveuint").attr("readonly", false);
		$("#wayBillachievepersons").attr("readonly", false);
		$("#wayBillachievephone").attr("readonly", false);
		$("#wayBillachieveaddress").attr("readonly", false);
		$("#wayBillLogisticsId").attr("disabled", false);
	};

	// 设置运单信息不可编辑
	var _readOnlyOrder = function() {
		$("#wayBillstartpoint").attr("readonly", "readonly");
		$("#wayBillendpoint").attr("readonly", "readonly");
		$("#alarmNo-add").attr("readonly", "readonly");
		$("#wayBilldeliveryunitperson").attr("readonly", "readonly");
		$("#wayBilldeliveryunitphone").attr("readonly", "readonly");
		$("#alarmNo-addr").attr("readonly", "readonly");
		$("#wayBillachieveuint").attr("readonly", "readonly");
		$("#wayBillachievepersons").attr("readonly", "readonly");
		$("#wayBillachievephone").attr("readonly", "readonly");
		$("#wayBillachieveaddress").attr("readonly", "readonly");
		$("#wayBillLogisticsId").attr("disabled", "disabled");
	};

	// 起点联想框
	var _associateS = function(target) {
		// 初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url = urlPath.findOrderNoUrl;
		associateObj = $.associate(config);
		// 重写联想下拉框getData方法
		associateObj.getData = function(val, obj) {
			 var list =
			 $.getData(urlPath.findPlaceAndPoi,"POST",{"orderNo":val});
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
			if (target === "#wayBillstartpoint") {
				$("#startLngInput").val(data.longitude);
				$("#startLatInput").val(data.latitude);
			} else {
				$("#endLngInput").val(data.longitude);
				$("#endLatInput").val(data.latitude);
			}

		};
	};
	//到点联想框
	var _associateE = function(target) {
		// 初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url = urlPath.findOrderNoUrl;
		associateObj = $.associate(config);
		// 重写联想下拉框getData方法
		associateObj.getData = function(val, obj) {
			 var list =
			 $.getData(urlPath.findPlaceAndPoi,"POST",{"orderNo":val});
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
			if (target === "#wayBillstartpoint") {
				$("#startLngInput").val(data.longitude);
				$("#startLatInput").val(data.latitude);
			} else {
				$("#endLngInput").val(data.longitude);
				$("#endLatInput").val(data.latitude);
			}

		};
	};

	// 绑定事件函数
	var _bindEvent = function() {
		$wayBillMagSearchBtn.bind("click", _searchHandler);
		$wayBillMagPrint.bind("click",_print);
		$wayBillMagAddBtn.unbind("click").bind("click", _wayBillMagShowAdd);
		$wayBillSubmmitTempBtn.bind("click", _saveTemp);
		$wayBillGoodsAddBtn.bind("click", _wayBillGoodsShowAdd);
		$wayBillGoodCanel.bind("click", _closeSecondPanel);
		$wayBillDriver.bind("change", _driverChange);
		$wayBillEscort.bind("change", _supercargoChange);
		$wayBillLogisticsId.bind("change", _logisticsId);
		// 绑定表单验证事件
		// 提交货物
		$wayBillGoodsForm.html5Validate(function() {
			/*window.fl = !window.fl;
			if (window.fl) {*/
				_submitGoogs();
			//}
			return false;
		});
		$wayBillForm.html5Validate(function() {
			_submitWayBillMag();
			return false;
		}, {
			validate : function() {
				if (!$("#startLngInput").val() || !$("#startLatInput").val()) {
					$("#wayBillstartpoint").testRemind("请重新选择起点");
					return false;
				}

				if (!$("#endLngInput").val() || !$("#endLatInput").val()) {
					$("#wayBillendpoint").testRemind("请重新选择到点");
					return false;
				}

				return true;
			}
		});
		$wayBillGoodsTempNameForm.html5Validate(function() {
			_saveTemp();
			return false;
		});

		dictData = $.getData(urlPath.findDicCodeUrl);
		$("#wayBillStatus-search").settingsOptions(dictData['111001'], true);
		$("#wayBillStatus-search >*").eq(0).text('全部');
		// 企业相关信息
		returnData.logistics = $.getData(urlPath.findLogistics, 'POST');
		returnData.driverData = [];
		returnData.supercargoData = [];
		_fillSelect("#wayBillLogisticsId", returnData.logistics, 'logistics');

	};
	// 物流企业change事件
	var _logisticsId = function() {
		$("#wayBillEnterInfor").find("input").val("");
		var selectValue = $("#wayBillLogisticsId").val();
		returnData.carData = $.getData(urlPath.wayBillfindCar + selectValue,
				'POST');
		_fillSelect("#wayBillCarNo", returnData.carData, "carInfor");
		// 驾驶员信息
		returnData.driverData = _getEmployeeData(selectValue, "101001001");
		// 押运员信息
		returnData.supercargoData = _getEmployeeData(selectValue, "101001002");
		_fillSelect("#wayBillDriver", returnData.driverData, "driver");
		_fillSelect("#wayBillEscort", returnData.supercargoData, "supercargo");

	};
	
	
	// 获取物流企业下驾驶员、押运员
	var _getEmployeeData = function() {
		var returnData;
		var data = {
			"personType" : arguments[1],
			"corporateNo" : arguments[0]
		};
		$.ajax({
			url : urlPath.wayBillfindByEmployee,
			type : 'POST',
			async : false,
			data : data
		}).done(function(json) {
			if (json.code === 1) {
				returnData = json.data;
				var data = {
					"personType" : '',
					"corporateNo" : ''
				};
			} else {
				$.validateTip(json);
			}
		});
		return returnData;
	};

	// 车牌change事件
	var _wayBillCarNoChange = function() {
		var selectValue = $("#wayBillLogisticsId").val();

	};

	// 驾驶员change事件
	var _driverChange = function() {
		var onselectValue = $wayBillDriver.val();
		$("#wayBillDriverChange").find("input").val("");
		$.each(returnData.driverData, function(index, content) {
			if (content.id === onselectValue) {
				$("#wayBillDriverid").val(content.identificationCardNo);
				$("#wayBillDriverno").val(content.occupationNo);
				$("#wayBillDriverphone").val(content.telephone);
			}
		});
	};

	// 押运员change事件
	var _supercargoChange = function() {
		var onselectValue = $wayBillEscort.val();
		$("#wayBillEscortChange").find("input").val("");
		$.each(returnData.supercargoData, function(index, content) {
			if (content.id === onselectValue) {
				$("#wayBillEscortid").val(content.identificationCardNo);
				$("#wayBillEscortno").val(content.occupationNo);
				$("#wayBillEscortphone").val(content.telephone);
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
			$this.find("option").remove();
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
		$("#wayBillchecknoSpans").addClass("hidden");
		$("#wayBillchecknoDiv").addClass("hidden");
		var str = "Y"
				+ new Date().toISOString().split("T")[0].replace(/-/g, "")
						.substring(2);
		/*var wayBillPart = $.randomCount(5).join("");
		var wayBillNo = str + wayBillPart;
		var isHas = $.getData(urlPath.isHasWayBillNo + wayBillNo, 'POST');
		while (isHas === true) {
			wayBillPart = $.randomCount(5).join("");
			wayBillNo = str + wayBillPart;
			isHas = $.getData(urlPath.isHasWayBillNo + wayBillNo, 'POST');
		}*/
		// var randomNum =$.randomCount(5).join("");
		$("#wayBillchecknoInput").val(str);
		$("#wayBillcheckno").removeClass("hidden").html(str + "+(5位自动生成序号)");
		// 查看时不能进行编辑等操作
		checkFlag = true;
		$("#wayBillGoodsAddBtn").removeClass("hidden");
		$wayBillForm.saveOrCheck(true);
		$wayBillPanel.showPanelModel("新增运单信息");
		wayBillFlag = true;
		wayBillTempId = "";
		_initGoodsTable();
		$wayBillGoodsTable.jqGrid('clearGridData');
		$("#wayBill-panel").find('.panel-close').bind("click", _wayBillCancel);
		$wayBillCancelBtn.bind("click", _wayBillCancel);
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
					$wayBillMagTable.jqGrid('setGridParam', {
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
		$("#wayBill-panel").find('.panel-close')
				.unbind("click", _wayBillCancel);
		$wayBillCancelBtn.unbind("click", _wayBillCancel);
	};

	// 修改
	var _wayBillMagShowUpdate = function() {

		$("#wayBillcheckno").addClass("hidden");
		// $("#wayBillchecknoSpan").removeClass("hidden");
		$("#wayBillchecknoDiv").removeClass("hidden");
		checkFlag = true;
		$("#wayBillGoodsAddBtn").removeClass("hidden");// 去掉新增货物按钮
		wayBillFlag = false;
		wayBillTempId = arguments[0];
		var updateObj = $.getData(urlPath.wayBillfindById + wayBillTempId);

		// 先判断数据是否存在在打开详情弹框
		if (updateObj) {
			// 运单所属车辆的所属企业 被删除
			if (updateObj.enterpriseRemove == '1') {
				Message.alert({
					Msg : prompt.enterDel4WayBill,
					isModal : false
				});
				return;
			}
			// 运单所属车辆 被删除
			if (updateObj.carRemove == '1') {
				Message.alert({
					Msg : prompt.carDel4WayBill,
					isModal : false
				});
				return;
			}
			$wayBillForm.saveOrCheck(true);
			$wayBillPanel.showPanelModel("修改运单信息").setFormSingleObj(updateObj);
			$("#wayBillchecknoSpan").removeClass("hidden");
			// 获取企业信息
			_logisticsId();
			$("#wayBillhandcarno").val(updateObj.handcarno);
			// 给企业信息赋值
			$("#wayBillCarNo").val(updateObj.carno);
			$("#wayBillDriver").val(updateObj.driverId);
			$("#wayBillEscort").val(updateObj.escortId);
			_driverChange();
			_supercargoChange();
			// 判断是否为物流用户
			var useInfor = JSON.parse(sessionStorage.getItem("userInfo")).roleCodes;
			// 为物流用户则不能修改物流企业选择项
			$.each(useInfor, function(index, content) {
				if (content == "7545f7983ac84654951f64efba29677a") {
					$('#wayBillLogisticsId').find("option").remove();
					var str = '<option value="' + updateObj.logisticsId + '">'
							+ updateObj.logisticsId + '</option>';
					$('#wayBillLogisticsId').append(str);
				}
			});
			
			// 判断是否带入的信息
			var list = $.getData(urlPath.findAccurateOrderNoUrl, "POST", {
				"orderNo" : $("#orderNo").val()
			});
			if (list && list.length > 0) {
				_readOnlyOrder();
			} else {
				_readOnlyFalseOrder();
			}

			// 点击编辑是相当于先执行初始化表格数据，在执行到查询表格
			_initGoodsTable();
			
			$wayBillGoodsTable.jqGrid('setGridParam', {
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
							$wayBillMagTable.jqGrid('setGridParam', {
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
		var updateObj = $.getData(urlPath.wayBillfindById + arguments[0]);
		if (updateObj) {
			$wayBillGoodsTempNamePanel.showPanelModel("模板信息");
			wayBillSaveTempID = arguments[0];
		} else {
			Message.alert({
				Msg : prompt.checkAndDo,
				isModal : false
			});
		}
	};

	// 保存模板
	var _saveTemp = function() {
		var formData = $wayBillGoodsTempNameForm.packageFormData();
		formData.id = wayBillSaveTempID;
		$.ajax({
			url : urlPath.wayBillsaveTemp,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				Message.alert({
					Msg : '模板保存成功',
					isModal : false
				});
				$wayBillGoodsTempNamePanel.closePanelModel();
				wayBillSaveTempID = "";
			} else {
				$.validateTip(json);
			}
		});
	};

	// 详情
	var _wayBillMagShowDetail = function() {
		$("#wayBillcheckno").addClass("hidden");
		$("#wayBillchecknoSpans").removeClass("hidden");
		$("#wayBillchecknoDiv").removeClass("hidden");
		var obj = $.getData(urlPath.wayBillfindById + arguments[0]);
		if (obj) {
			$wayBillForm.saveOrCheck(false);
			$wayBillPanel.showPanelModel('运单信息详情').setFormSingleObj(obj);
			$wayBillStatusSpan.detailSpan(dictData['111001'], obj.checkstatus);
			wayBillTempId = arguments[0];
			_initGoodsTable();
			$wayBillGoodsTable.jqGrid('setGridParam', {
				postData : {
					"id" : arguments[0]
				},
				page : 1
			}).trigger("reloadGrid");
			// 查看时不能进行编辑等操作
			checkFlag = false;
			$("#wayBillGoodsAddBtn").addClass("hidden");
			wayBillTempId = "";
		} else {
			Message.alert({
				Msg : prompt.checkAndDo,
				isModal : false
			});
		}
	};
	/*打印*/
    var _print = function () {
    	var ids = $.checkOneMsg("#wayBillMagTable", "选中打印的记录不能超过1条", "没有选中要打印的数据");
        if (ids) {
       	var result = $.getData(urlPath.wayBillfindById+ids);
       	var item = $.getData(urlPath.goodsfindByPagePrint,"post",{"id":ids});
      	if (!item) return false;
//    	if(item){
//    		var html = "/print/goodsTrade/wayBillManage/waybill.html";
//    		
//    	}
       };
       	
       	if (!result) return false;
        if(result && item){
    		var html = "/print/goodsTrade/wayBillManage/waybill.html";
        	toolMethod.windowPrint(html, result,item);
        }
        toolMethod.connFun();
    };
    
	
	// 新增或修改提交
	var _submitWayBillMag = function() {
		var goodsData = $("#wayBillGoodsTable").jqGrid("getRowData").length;
		if (goodsData != 0) {
			var formData = $wayBillForm.packageFormData();
			formData.driver = $driver.find("option:selected").text();
			formData.escort = $escort.find("option:selected").text();
			var url;
			if (wayBillFlag) {
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
					if (json.data === false) {
						Message.alert({
							Msg : '不能与所选订单关联，请选择其他订单!',
							iconImg : 'warning',
							isModal : false
						});
						return;
					}

					if (wayBillFlag) {
						if (wayBillBeforeFlag) {
							if (countAddGoodsBtn === 0) {
								$wayBillPanel.closePanelModel();
							} else {
								wayBillTempId = json.data;
							}
						} else {
							wayBillTempId = "";
							$wayBillPanel.closePanelModel();
						}
					} else {
						wayBillTempId = "";
						$wayBillPanel.closePanelModel();
					}

					// 成功
					wayBillFlag = true;
					wayBillBeforeFlag = true;
					wayBillTempId = "";
					wayBillSaveTempID = "";
					countAddGoodsBtn = 0;

					$wayBillMagTable.jqGrid('setGridParam', {
						postData : {},
						page : 1
					}).trigger("reloadGrid");
				} else {
					$.validateTip(json);
				}
			});
		} else {
			Message.alert({
				Msg : '请添加货物信息后再提交运单信息!',
				iconImg : 'warning',
				isModal : false
			});
		}
	};
	// 点击新增货物时提交运单表单
	var _submitAddWayBillGoods = function() {
		//window.fl = false;
		var formData = $wayBillForm.packageFormData();
		formData.driver = $driver.find("option:selected").text();
		formData.escort = $escort.find("option:selected").text();
		//alert(formData.driver+'-----'+formData.escort);
		var url = urlPath.wayBillsave;
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				wayBillTempId = json.data;
				$wayBillMagTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	/**
	 * tab切换函数
	 * 
	 * @param name
	 *            tab名称
	 */
	var _showTabs = function(name) {
		$("a[aria-controls='" + name.split("#")[1] + "']").tab('show');
		if (name == "#wayBillMag") {
			_init();
		} else {
			wayBillTempMag.init();
		}
	};

	// 初始化表格
	var _initWayBillMagTable = function() {
		$wayBillMagTable
				.jqGrid(
						{
							url : urlPath.wayBillfindByPage,
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "托运单号", "车牌号码", "起点", "到点", "品名",
									"运单状态", "装货时间", "操作" ],
							colModel : [
									{
										name : "checkno",
										index : "1",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "carno",
										index : "2",
										align : "center",
										width : "35px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											if (rowObject.enterpriseRemove == '1') {
												return cellValue
														+ "  "
														+ "<span style='color: #ff0000'>该企业已删除</span>";
											} else if (rowObject.carRemove == '1') {
												return cellValue
														+ "  "
														+ "<span style='color: #ff0000'>该车辆已删除</span>";
												;
											} else {
												if (cellValue) {
													return cellValue;
												} else {
													return "";
												}
											}
										}
									},
									{
										name : "startpoint",
										index : "3",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "endpoint",
										index : "4",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "goodsname",
										index : "4",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "checkstatus",
										index : "5",
										align : "center",
										width : "30px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var returnType = "";
											var data = dictData["111001"];
											$
													.each(
															data,
															function(index,
																	content) {
																if (data[index].code === rowObject.checkstatus) {
																	returnType = data[index].name;
																}
															});
											return returnType;
										}
									},
									{
										name : "deliveryunittime",
										index : "6",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "",
										index : "7",
										align : "center",
										width : "100px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var handlerTemp = "";
											if (rowObject.checkstatus == "111001001") {
												handlerTemp = '<p class="jqgrid-handle-p">';
												if (rowObject.enterpriseRemove == '0') {
													handlerTemp += '<label class="jqgrid-handle-text delete-link" data-func="wayBillMag-update" onclick="wayBillMag.wayBillMagUpdate(\''
															+ rowObject.id
															+ '\')"><span class="img-edit"></span>修改</label>'
															+ '&nbsp;&nbsp;&nbsp;&nbsp;';
												}
												handlerTemp += '<label class="jqgrid-handle-text delete-link" data-func="wayBillMag-findById" onclick="wayBillMag.wayBillMagDetail(\''
														+ rowObject.id
														+ '\')"><span class="img-details"></span>详情</label>'
														+ '&nbsp;&nbsp;&nbsp;&nbsp;'
														+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillMag-delete" onclick="wayBillMag.wayBillMagDel(\''
														+ rowObject.id
														+ '\')"><span class="img-delete"></span>删除</label>'
														+ '&nbsp;&nbsp;&nbsp;&nbsp;'
														+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillMag-saveTemp" onclick="wayBillMag.showTempPanel(\''
														+ rowObject.id
														+ '\')"><span class="img-edit"></span>生成模板</label>'
														+ '</p>';
											} else {
												handlerTemp = '<p class="jqgrid-handle-p">'
														+ '<label class="jqgrid-handle-text delete-link" data-func="wayBillMag-findById" onclick="wayBillMag.wayBillMagDetail(\''
														+ rowObject.id
														+ '\')"><span class="img-details"></span>详情</label>'
														+ '&nbsp;&nbsp;&nbsp;&nbsp;'
														+
														// '<label
														// class="jqgrid-handle-text
														// delete-link"
														// data-func="wayBillMag-saveTemp"
														// onclick="wayBillMag.showTempPanel(\''
														// + rowObject.id +
														// '\')"><span
														// class="img-edit"></span>生成模板</label>'
														// +
														'</p>';
											}
											/**/

											return handlerTemp;
										}
									} ],
							loadonce : false,
							rownumbers : true,
							viewrecords : true,
							multiselect: true,
					        multiboxonly: true,
							autowidth : true,
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#wayBillMagPage",
							gridComplete : function() {
								// 配置权限
								$.initPrivg();
							}
						}).resizeTableWidth();
		//console.log("在给驾驶员分配运单时，驾驶员的身份证号码应该和门户中驾驶员的身份证号相同");
	};
	// 搜索
	var _searchHandler = function() {
		$wayBillMagTable.jqGrid('setGridParam', {
			postData : {
				"startTime" : $("#wayBillMagStartTime").val(),
				"endTime" : $("#wayBillMagEndTime").val(),
				"checkstatus" : $("#wayBillStatus-search").val(),
				"carno" : $("#wayBillCarNo-search").val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// 初始化货物表
	var _initGoodsTable = function() {
		$wayBillGoodsTable
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
												+ '<label class="jqgrid-handle-text delete-link" onclick="wayBillMag.updateGoods(\''
												+ rowObject.id
												+ '\')"><span class="img-edit"></span>修改</label>'
												+ '<label class="jqgrid-handle-text delete-link" onclick="wayBillMag.deleteGoods(\''
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
					pager : "#wayBillGoodsTablePage",
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

	// 新增--货物
	var _wayBillGoodsShowAdd = function() {
		var selectValue = $("#wayBillLogisticsId").val();
		if (selectValue) {
			// 点击新增货物时---提交保存运单
			if (countAddGoodsBtn === 0) {
				if (wayBillFlag) {
					_submitAddWayBillGoods();
					countAddGoodsBtn++;
				}
			}
			wayBillBeforeFlag = false;
			$("#wayBillGoods-form")[0].reset();
			goodFlag = true;
			$wayBillGoodsForm.saveOrCheck(true);
			$wayBillGoodsPanel.showPanelModel("货物信息");
			$wayBillGoodsPanel.find(".panel-close").bind("click",
					_closeSecondPanel);
		} else {
			Message.alert({
				Msg : '请选择物流企业后再新增货物信息',
				iconImg : 'warning',
				isModal : false
			});
		}
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
						$wayBillGoodsTable.jqGrid('setGridParam', {
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
		goodFlag = false;
		goodsTempId = arguments[0];
		var updateObj = $.getData(urlPath.goodsfindbyid + goodsTempId);
		$wayBillGoodsForm.saveOrCheck(true);
		$wayBillGoodsPanel.showPanelModel('修改货物信息').setFormSingleObj(updateObj);
		$wayBillGoodsPanel.find(".panel-close")
				.bind("click", _closeSecondPanel);
	};

	var _submitGoogs = function() {
		var formData = $wayBillGoodsForm.packageFormData();
		formData.waybillId = wayBillTempId;
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
				$wayBillGoodsTable.jqGrid('setGridParam', {
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
		$wayBillGoodsPanel.fadeOut(300);
		$(".panel-mask").css("z-index", "1000");
	};

	_init();

	return {
		wayBillMagUpdate : _wayBillMagShowUpdate,
		wayBillMagDel : _wayBillMagShowDel,
		wayBillMagDetail : _wayBillMagShowDetail,
		searchHandler : _searchHandler,
		showTempPanel : _showTempPanel,
		updateGoods : _updateGoods,
		deleteGoods : _deleteGoods,
		initGoodsTable : _initGoodsTable,
		getEmployeeData : _getEmployeeData

	};

})(jQuery);
