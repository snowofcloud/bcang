/**
 * 终端管理
 */
var terminalManage = (function($) {
	// 搜索
	var $terminalSearchBtn = $("#terminalSearchBtn");
	// 新增
	var $terminalInsertBtn = $("#terminalInsertBtn");
	// 终端一键升级
	var $terUpgradeAllBtn = $("#terminalUpgradeAll");
	// 导出
	var $terminalExportBtn = $("#terminalExportBtn");

	// 终端开户弹框、form
	var $terminalOpenPanel = $("#terminal-panel");
	var $terminalOpenForm = $("#terminalOpenForm");

	// 终端注册弹框、form
	var $terminalRegPanel = $("#terminalRgisterPanel");
	var $terminalRegFrom = $("#terminalRegForm");

	// 终端升级弹框、form
	var $terRegUpgradPanel = $("#terminal-upgreade-panel");
	var $terRegUpgradeForm = $("#terminalUpgradeForm");
	
	//终端参数设置弹框、form
	var $terminalSetParamPanel = $('#terminal-set-param-panel');
	var $terminalSetParamForm = $('#terminalSetParamForm');

	// 终端页面
	var $terminalTable = $("#terminalFormTable");

	var loadOne = false;
	var flag;

	var cardNumLength = 12;
	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/terminal/findByPage',
		saveUrl       : $.backPath + '/terminal/save',
		registerUrl   : $.backPath + '/terminal/register',
		updateUrl     : $.backPath + '/terminal/update/',
		exportUrl     : $.backPath + '/terminal/export',
		upgradeUrl    : $.backPath + '/terminal/upgrade',
		upgradeAllUrl : $.backPath + '/terminal/upgradeAll',
		deleteUrl     : $.backPath + '/terminal/delete/',
		findByIdUrl   : $.backPath + '/terminal/findById/',
		cancleUrl     : $.backPath + '/terminal/cancel/',
		findTerminalParamByIdUrl: $.backPath + '/terminal/findTerminalParamById/',
		setTerminalParamUrl: $.backPath + '/terminal/setTerminalParam/',
		findVehicleUrl : $.backPath + '/dangerVehicle/findAllVehicle'// 查询所有驾驶员
	};
	var configfunc = function(id) {
		// 初始化联想下拉框
		var config = {};
		// config.inputObj = "#terReg_carNo";
		config.inputObj = "#" + id;

		config.url = urlPath.findVehicleUrl;
		associateObj = $.associate(config);
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
			//$("#terReg_carColor").val(data.vehicleColor);
			//$("#carcolor").val(data.vehicleColor);
			submitChangeVal = data.corporateNo;
		};
	};
	// 初始化
	var _init = function() {

		if (!loadOne) {
			_initTerminalTable();

			_bindClick();

			loadOne = !loadOne;
			// 注册回车搜索事件
			$("#terminal-search input").keyup(function(e) {
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
		$terminalInsertBtn.bind("click", _insert);
		$terminalSearchBtn.bind("click", _searchHandler);
		$terUpgradeAllBtn.bind("click", _upgradeAll);
		$("#terminalExportBtn").bind("click", _export);

		// 表单验证绑定
		$terminalOpenForm.html5Validate(function() {
			_submitHandle(flag);
			return false;
		});
	};

	// 表格
	var _initTerminalTable = function() {
		$terminalTable.jqGrid(
				{
					url : urlPath.findByPageUrl,
					mtype : "POST",
					datatype : "JSON",
					colNames : [ "id","终端编号", "终端手机号", "车牌号", "注册状态", "下次检修日期",
							"设备状态", "操作" ],
					colModel : [ 
					{
						hidden:true,
						key:true
					},
                    {
						name : "terminalSerialID",
						index : "1",
						align : "center",
						width : "20px",
						sortable : false
						//key:true
					}, {
						name : "cardNum",
						index : "1",
						align : "center",
						width : "20px",
						sortable : false,
						formatter : function(cellValue, options, rowObject) {
							if (cellValue.length == cardNumLength)
								return cellValue.substring(1);
							else
								return cellValue;
						}
					}, {
						name : "carrierName",
						index : "3",
						align : "center",
						width : "20px",
						sortable : false
					}, {
						name : "terRegisterState",
						index : "4",
						align : "center",
						width : "15px",
						sortable : false,
						formatter : function(cellValue, options, rowObject) {
							if (rowObject.carrierName)
								return '已注册';
							else
								return '未注册';
						}
					}, {
						name : "nextrepairDate",
						index : "5",
						align : "center",
						width : "20px",
						sortable : false
					}, {
						name : "terminalState",
						index : "6",
						align : "center",
						width : "15px",
						sortable : false,
						formatter : function(cellValue) {
							if ("0" == cellValue) {
								return '在线';
							} else if ("1" == cellValue) {
								return '离线';
							} else {
								return "";
							}
						}
					}, {
						name : "",
						index : "7",
						align : "center",
						width : "70px",
						sortable : false,
						formatter : function(cellValue, options, rowObject) {
							// 格式化操作按钮
							return _settingHandlerBtn(rowObject);
						}
					} ],
					loadonce : false,
					rownumbers : true,
					viewrecords : true,
					autowidth : true,
					height : true,
					rowNum : 10,
					rowList : [ 5, 10, 15 ],
					pager : "#terminalFormPager",
					gridComplete : function() {
						// 配置权限
						$.initPrivg("terminalFormTable");
					}
				}).resizeTableWidth();
	};

	var _settingHandlerBtn = function(rowObject) {
		var carrierName = rowObject['carrierName'];
		var id = rowObject['terminalSerialID'];
		var cardNum = rowObject['cardNum'];
		//如果终端号=手机号前三位+后四位，则该终端为手机终端，并不可进行升级、参数设置
		var isPhoneTerminal = _isPhoneTerminal(id, cardNum);
		var option = "";
		if("嘉兴位置服务平台" == rowObject.terminalSource){
			if (carrierName) { // 已注册
				option = '<p class="jqgrid-handle-p">'
						+ '<label class="jqgrid-handle-text delete-link" data-func="terminal-update" onclick="terminalManage.update(\''
						+ id
						+ '\')"><span class="img-edit"></span>修改</label>'
						+ '<span>&nbsp&nbsp</span>'
						+ '<label class="jqgrid-handle-text delete-link" data-func="terminal-register" onclick="terminalManage.cancel(\''
						+ id
						+ '\', \''
						+ false
						+ '\')"><span class="img-details"></span>注销</label>'
						+ '<span>&nbsp&nbsp</span>'
						;
			} else { // 未注册
				option = '<p class="jqgrid-handle-p">'
						+ '<label class="jqgrid-handle-text delete-link" data-func="terminal-update" onclick="terminalManage.update(\''
						+ id
						+ '\')"><span class="img-edit"></span>修改</label>'
						+ '<span>&nbsp&nbsp</span>'
						+ '<label class="jqgrid-handle-text delete-link" data-func="terminal-cancel" onclick="terminalManage.register(\''
						+ id
						+ '\')"><span class="img-details"></span>注册</label>'
						+ '<span>&nbsp&nbsp</span>'
						+ '<label class="jqgrid-handle-text delete-link" data-func="terminal-delete" onclick="terminalManage.deletes(\''
						+ id
						+ '\', \''
						+ true
						+ '\')"><span class="img-delete"></span>删除</label>'
						+ '<span>&nbsp&nbsp</span>'
						;
			}
			//只有终端来源是‘嘉兴位置服务平台’的才有参数设置操作
			if(!isPhoneTerminal){
				option += '<label class="jqgrid-handle-text delete-link" data-func="terminal-upgrade" onclick="terminalManage.upgrade(\''
						+ id + '\', \'' + cardNum
						+ '\')">'
						+'<span class="img-edit"></span>升级</label>'
					    + '<span>&nbsp&nbsp</span>';
				
				option += '<label class="jqgrid-handle-text delete-link" data-func="terminal-setParam" onclick="terminalManage.setParam(\''
				        + id + '\',\''+ cardNum +'\')"><span class="img-edit"></span>参数设置</label><span>&nbsp&nbsp</span>' ;
			}
			option += '<label class="jqgrid-handle-text delete-link" data-func="terminal-findById" onclick="terminalManage.detail(\''
					+ id + '\')"><span class="img-details"></span>详情</label>' + '</p>';
		}else {
			option = '<label class="jqgrid-handle-text delete-link" data-func="terminal-findById" onclick="terminalManage.detail(\''
				   + id + '\')"><span class="img-details"></span>详情</label>' + '</p>';
		}
		return option;
	};
	//是否手机终端（true:手机终端  false:非手机终端）
	var _isPhoneTerminal = function(terminalSerialID, cardNum){
		var isPhoneTerminal = false;
		if(terminalSerialID && cardNum){
			var len = cardNum.length;
			var tmp = cardNum;
			if (len == cardNumLength){
				tmp = cardNum.substring(1);
			}
			var tmpLen = tmp.length;
			if(tmpLen >= 5){
				var preThree = tmp.substring(0,3);
				var lastFour = tmp.substring(tmpLen-4,tmpLen);
				if(terminalSerialID == (preThree+lastFour) ){
					isPhoneTerminal = true;
				}
			}
		}
		return isPhoneTerminal;
	};
	// 终端开户
	var _insert = function() {
		flag = "";
		$terminalOpenForm[0].reset();
		$("#terminalSerialID").attr("readonly", false);
		open_update_addHidden();
		$terminalOpenForm.saveOrCheck(true);
		$terminalOpenPanel.showPanelModel('终端开户');
		openOrUpdate(false);
		configfunc("carrierName-register");
		var now = new Date();
		//$("#registerDateSpan").removeClass("hidden");
		$("#registerDateInput").val(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate());
	};

	// 终端修改
	var _update = function(id) {
		flag = id;
		$terminalOpenForm[0].reset();
		$("#terminalSerialID").attr("readonly", true);
		open_update_addHidden();
		var updateObj = $.getData(urlPath.findByIdUrl + id);
		if (updateObj) {
			if (updateObj.carrierName) {
				obj.terregistestate = '已注册';
				$(".carrierNameState").removeClass("hidden");
			} else {
				obj.terregistestate = '未注册';
				$(".carrierNameState").addClass("hidden");
			}
			
			if( updateObj.cardNum){
				var cardNum = updateObj.cardNum;
				if( cardNumLength == cardNum.length){
					updateObj.cardNum = cardNum.substring(1);
				}
			}
		}

		$terminalOpenForm.saveOrCheck(true);
		$terminalOpenPanel.showPanelModel('修改终端信息').setFormSingleObj(updateObj);
		openOrUpdate(true);
		//$("#registerDateSpan").removeClass("hidden");
	};

	// 提交：终端开户、修改
	var _submitHandle = function(flag) {
		var formData = $terminalOpenForm.packageFormData();
		$.ajax({
			url : flag ? urlPath.updateUrl : urlPath.saveUrl,
			type : 'POST',
			async : false,
			data : formData,
			beforeSend : function(){
				Message.alert({Msg : "请求/处理中，请稍后...", isModal : true});
			}
		}).done(function(json) {
			if (json.code === 1) {
				$("#messager-dialogue").modal("hide");//模态框隐藏
				$terminalOpenPanel.closePanelModel();
				$terminalTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 终端注册
	var _register = function(terminalSerialID) {
		$terminalRegFrom[0].reset();
		$("#terReg_no").text(terminalSerialID);
		$("#terReg_no_span").val(terminalSerialID);

		$terminalRegPanel.showPanelModel('终端注册');
		configfunc("terReg_carNo");
		// 表单验证绑定
		$terminalRegFrom.html5Validate(function() {
			_submitHandle2();
			return false;
		});
	};
	// 提交：终端注册
	var _submitHandle2 = function() {
		var formData = $terminalRegFrom.serialize();
		$.ajax({
			url : urlPath.registerUrl,
			type : 'POST',
			async : false,
			data : formData,
			beforeSend : function(){
				Message.alert({Msg : "请求/处理中，请稍后...", isModal : true});
			}
		}).done(function(json) {
			if (json.code === 1) {
				$("#messager-dialogue").modal("hide");//模态框隐藏
				$terminalRegPanel.closePanelModel();
				$terminalTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 终端详情查询
	var _detail_view = function(id) {
		flag = id;
		$terminalOpenForm[0].reset();
		$("#terminalSerialID").attr("readonly", true);
		detail_removeHidden();
		var obj = $.getData(urlPath.findByIdUrl + id);
		if (obj) {
			// 根据车牌号是否存在决定注册状态 以及是否显示车牌号和颜色
			if (obj.carrierName) {
				obj.terregistestate = '已注册';
				$(".carrierNameState").removeClass("hidden");
			} else {
				obj.terregistestate = '未注册';
				$(".carrierNameState").addClass("hidden");
			}
			if ('0' == obj.terminalState)
				obj.terminalState = '在线';
			else if ('1' == obj.terminalState)
				obj.terminalState = '离线';
			else if ('2' == obj.terminalState)
				obj.terminalState = '故障';
			//如果长度为12，则截取后十一位
			var cardNum = obj.cardNum;
			if( cardNum && cardNumLength == cardNum.length){
				obj.cardNum = cardNum.substring(1);
			}
			
			$terminalOpenForm.saveOrCheck(false);
			$terminalOpenPanel.showPanelModel('终端信息详情').setFormSingleObj(obj);
		}
	};

	// 终端开户、修改（显示隐藏）
	var open_update_addHidden = function() {
		$("#hidden1").addClass("hidden");
		$("#hidden2").addClass("hidden");
	};

	var openOrUpdate = function(flag) {
		if (flag) {
			$("#carrierName-register").addClass("hidden");
			$("#manufactureID-register").addClass("hidden");
			$("#carrierName-update").removeClass("hidden");
			$("#manufactureID-update").removeClass("hidden");
		} else {
			$("#carrierName-register").removeClass("hidden");
			$("#manufactureID-register").removeClass("hidden");
			$("#carrierName-update").addClass("hidden");
			$("#manufactureID-update").addClass("hidden");
		}
	};

	// 终端详情（显示隐藏）
	var detail_removeHidden = function() {
		$("#hidden1").removeClass("hidden");
		$("#hidden2").removeClass("hidden");
	};

	/**
	 * 终端升级
	 */
	var _upgrade = function(id, simNum) {
		$terRegUpgradeForm[0].reset();
		$terRegUpgradPanel.showPanelModel('终端升级');
		//$("#terminalUpgradeForm_terminalTelNo").val(simNum);//终端升级所有手机号不用前端填写，后端已做了数据请求并填充
		// 表单验证绑定
		$terRegUpgradeForm.html5Validate(function() {
			_submitHandle3(id, urlPath.upgradeUrl);
			return false;
		});
	};

	/**
	 * 一键升级
	 */
	var _upgradeAll = function() {
		$terRegUpgradeForm[0].reset();
		$terRegUpgradPanel.showPanelModel('终端一键升级');
		_getData(urlPath.findByPageUrl, "POST");

		// 表单验证绑定
		$terRegUpgradeForm.html5Validate(function() {
			_submitHandle3(null, urlPath.upgradeAllUrl);
			return false;
		});
	};

	var _setParam = function(id, cardNum){
		//请求后台已有的参数数据，并填充到表单中
		//编辑状态
		$terminalSetParamForm[0].reset();
		//		var obj = {
		//		"mainServerAddr": '1.1.1.1',
		//		"backupServerAddr": '2.2.2.2',
		//		"mainServerTcpPort": '2000',
		//		"backupServerTcpPort": '3000'
		//}
		//$terminalSetParamForm.saveOrCheck(true);
		//$terminalSetParamPanel.showPanelModel('终端参数设置').setFormSingleObj(obj);

		
		var obj = $.getData(urlPath.findTerminalParamByIdUrl + id);
		$terminalSetParamForm.saveOrCheck(true);
		$terminalSetParamPanel.showPanelModel('终端参数设置').setFormSingleObj(obj);
		// 表单验证绑定
		$terminalSetParamForm.html5Validate(function() {
			_submitHandle4(id, cardNum, urlPath.setTerminalParamUrl);
			return false;
		});
	};
	
	/**
	 * 终端参数设置 提交
	 */
	var _submitHandle4 = function(id, cardNum, url) {
		var formData = $terminalSetParamForm.serialize();
		formData +="&terminalSerialId="+id; //终端编号
		formData +="&phone="+cardNum; //终端手机号
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData,
			beforeSend : function(){
				Message.alert({Msg : "请求/处理中，请稍后...", isModal : true});
			}
		}).done(function(json) {
			if (json.code === 1) {
				$("#messager-dialogue").modal("hide");//模态框隐藏
				$terminalSetParamPanel.closePanelModel();
				Message.alert({
					Msg : "参数设置成功！",
					isModal : false
				});
			} else {
				Message.alert({
					Msg : json.msg,
					isModal : false
				});
			}
		});
	};
	
	
	var _getData = function(url_path, TYPE, data) {
		var list;
		var Obj = data || {};
		$.ajax({
			url : url_path,
			type : TYPE,
			data : Obj
		}).done(function(json) {
			if (json.code === 1) {
				list = json.rows;
				var str = list[0].cardNum;
				for ( var i = 1; i < list.length; i++) {
					str = str + "," + list[i].cardNum;
				}
				//$("#terminalUpgradeForm_terminalTelNo").val(str);//终端升级所有手机号不用前端填写，后端已做了数据请求并填充
			} else if (json.code === 1) {
				Message.alert({
					Msg : "该数据不存在或为空",
					isModal : false
				});
				return;
			}
		});
	};

	/**
	 * 终端升级 提交
	 */
	var _submitHandle3 = function(id, url) {
		var formData = $terRegUpgradeForm.serialize();
		if (id) {
			formData = formData + "&terminalSerialID=" + id;
		}
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData,
			beforeSend : function(){
				Message.alert({Msg : "请求/处理中，请稍后...", isModal : true});
			}
		}).done(function(json) {
			if (json.code === 1) {
				$("#messager-dialogue").modal("hide");//模态框隐藏
				$terRegUpgradPanel.closePanelModel();
				$terminalTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};
	
	
	

	// 删除、注销(boolean=true：删除、反之注销(解绑))
	var _deleteOrCancle = function(id, boolean) {
		var url = "";
		if (boolean == "true") {
			url = urlPath.deleteUrl + id;
		} else {
			url = urlPath.cancleUrl + id;
		}
		Message.confirm({
			Msg : boolean == 'true' ? $.msg.sureDelete : $.msg.sureCancle,
			okSkin : 'danger',
			iconImg : 'question',
			isModal : true
		}).on(function(flag) {
			if (flag) {
				$.ajax({
					url : url,
					type : "delete",
					async : false,
					beforeSend : function(){
						Message.alert({Msg : "请求/处理中，请稍后...", isModal : true});
					}
				}).done(function(json) {
					if (json.code === 1) {
						$("#messager-dialogue").modal("hide");//模态框隐藏
						$terminalTable.jqGrid('setGridParam', {
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

	// 搜索
	var _searchHandler = function() {
		// $.removeTrim();
		var carNumSearch = $("#card_numSearch").val();
		//判断手机号第一个数字是否是0,没有就加0，有就不加0
		carNumSearch = carNumSearch === '' ?  carNumSearch : carNumSearch.substr(0,1)==='0' ? carNumSearch : '0'+carNumSearch; 
		$terminalTable.jqGrid(
				'setGridParam',
				{
					url : urlPath.findByPageUrl,
					postData : {
						"terminalSerialID" : $("#terminalNoSearch").val(),
						"carrierName" : $("#carrierNameSearch").val().replace(
								/^\s+|\s+$/g, ''),
						"manufactureID" : $("#manufactureIDSearch").val(),
						"simNum" : carNumSearch
					},
					page : 1
				}).trigger("reloadGrid");

	};

	// 导出
	var _export = function() {
		var carrierName = $("#carrierNameSearch").val();
		var simNum      = $("#card_numSearch").val();
		$.ajax({
			url   : urlPath.findByPageUrl,
			type  : 'POST',
			async : false,
			data  : {
				"carrierName" : carrierName,
				"simNum"      : simNum
			},
			beforeSend : function(){
				Message.alert({Msg : "请求/处理中，请稍后...", isModal : true});
			}
		}).done(function(json) {
			if (json.code === 1 && json.rows && 0 < json.rows.length) {
				$("#messager-dialogue").modal("hide");//模态框隐藏
				carrierName = encodeURI(carrierName, "UTF-8");
				window.location.href = urlPath.exportUrl + "?carrierName="
				+ carrierName + "&simNum=" + simNum;
			} else {
				Message.alert({Msg : "暂无数据，无法导出", isModal : false});
			}
		});
	};

	// 返回函数
	return {
		init : _init,
		update : _update,
		register : _register,
		cancel : _deleteOrCancle,
		deletes : _deleteOrCancle,
		upgrade : _upgrade,
		detail : _detail_view,
		setParam: _setParam
	};
})(jQuery);