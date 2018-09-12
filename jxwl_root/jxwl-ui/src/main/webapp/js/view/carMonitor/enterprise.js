/**
 * @description:监控管理-物流企业信息
 * @author: wl
 * @time: 2016-7-19
 */
var inforMagLogisticstInfor = (function($) {
	var $inforMagTabs = $('#inforMag-tabs');
	// 定义搜索变量
	var $logisticstSearchBtn = $("#logisticst-searchBtn");
	// 定义导出excel
	var $logisticstExport = $("#logisticst-export");

	var $logisticstTable = $("#logisticstTable");
	var $logisticstAddBtn = $("#logisticst-addBtn");
	var $logisticstPanel = $("#logisticst-panel");
	var $logisticstForm = $("#logisticst-form");
	var $driverSearchBtn = $("#driver-searchBtn");
	var $driverExport = $("#driver-export");
	var $driverTable = $("#driverTable");
	var $escortSearchBtn = $("#escort-searchBtn");
	var $escortExport = $("#escort-export");
	var $escortTable = $("#escortTable");
	var $employeeDeatil = $("#employeeDeatil");
	var $searchName = $("#logisticst-search-name");
	var loadOne = false;
	var flag;
	var userFlag = true;
	var uploadObj;
	
	var $synchronizeAddBtn = $("#logisticst-synchronizeBtn");

	// 请求路径
	var urlPath = {
		// 调用搜索接口
		findByPageUrlForLogisticst : $.backPath + '/logisticst/findByPage',
		findByIdUrl : $.backPath + '/logisticst/findById/',
		saveUrl : $.backPath + '/logisticst/save/',
		updateUrl : $.backPath + '/logisticst/update/',
		exportUrl : $.backPath + '/logisticst/export',
		findDictByCodeUrl : $.backPath + "/logisticst/findDictByCode/105001",
		uploadFileUrl : $.backPath + "/logisticst/uploadFile",
		downloadFileUrl : $.backPath + "/logisticst/downloadFile/",
		deleteFileUrl : $.backPath + '/logisticst/deleteFile',
		findEmployeeByPageUrl : $.backPath + '/employee/findByPage',
		deleteLogisticstUrl: $.backPath + '/logisticst/deleteLogisticst/',
		synchronizeUrl: $.backPath + '/logisticst/synchronize/'
	};
	// 变量
	var flag, uploadObj, editDeleteFile = "", editDeleFileOri = [];
	var dicObj;
	
	// 初始化
	var _init = function() {
		$("#ieuploadsfile_2").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,2);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(2, urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	$searchName.removeAttr("readonly");
		userFlag = true;
		dicObj = $.getData(urlPath.findDictByCodeUrl);
		if (!loadOne) {
			fileUpValue_plugin = [];
			filteredFiles = []; // 文件过滤
			editDeleFileOri = [];
			_initLogisticstTable();
			_bindClick();
			loadOne = !loadOne;
			// 注册回车搜索事件
			$("#logisticst-search-name").keyup(function(e) {
				e.preventDefault();
				if (e.keyCode === 13) {
					_searchHandler();
				}
				return false;
			});
			_upload_1($("#business-fileUpload"));
			//tabs切换调用事件
	        $inforMagTabs.find('a').click(function () {
	            var $this = $(this);
	            var tabsName = $this.attr('href');
        		sessionStorage.setItem("active_li", tabsName);
	            _showTabs(tabsName);
	        });
	        var activeLi = sessionStorage.getItem("active_li");
        	if(activeLi){
        		_showTabs(activeLi);
        		var activeLiTwo = sessionStorage.getItem("active_liTwo");
        		if(activeLiTwo){
        			$("#removalApp-tabs").find("li a[href="+activeLiTwo+"]").click();
        		}
        	}
		} else {
			_searchHandler();
		}
	};

	// 绑定click
	var _bindClick = function() {
		$logisticstAddBtn.bind("click", _addLogisticst);
		$synchronizeAddBtn.bind("click", _enterpriseSynchronize);
		// 表单事件绑定
		$logisticstForm.html5Validate(function() {
			_submitAddHandle();
			return false;
		});
		$logisticstSearchBtn.bind("click", {
			type : "logistics"
		}, _searchHandler);
		$logisticstExport.bind("click", {
			type : "logistics"
		}, _export);

	};
	
	//tabs切换事件
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
	        case '#logisticstCompany':
	        	$("#enterprise-search-name").val("");
	        	_init();
	        	break;
            case '#dangerCar':
            	$("#enterprise-search-name").val("");
            	inforMagDangerCarInfor.init();
                break;
            case '#employee':
            	//$("#enterprise-search-name").val("");
            	inforMagEmployeeInfor.init();
                break;
            case '#terminalMag':
            	$("#enterprise-search-name").val("");
            	terminalManage.init();
                break;
            case '#removalApp':
            	$("#enterprise-search-name").val("");
            	inforMagSuddenlyTroubleInfor.init();
                break;
            case '#insertPlatform':
            	$("#enterprise-search-name").val("");
            	inforMagInsertPlatformInfor.init();
                break;
            default :
        }
    };
	
	// 表格
	var _initLogisticstTable = function() {
		$logisticstTable.jqGrid(
						{
							url : urlPath.findByPageUrlForLogisticst,
							mtype : "POST",
							datatype : "JSON",
							colNames : [ "企业名称", "法人代码", "主营业务", "企业类型", "车辆数量","从业人员", "操作" ],
							colModel : [
									{
										name : "enterpriseName",
										index : "1",
										align : "center",
										width : "40px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											if(topMenuMag.isLogisticsEnterpriseUser() || topMenuMag.isChemicalEnterpriseUser() && userFlag){
												userFlag = false;
												$searchName.val(cellValue);
												$searchName.attr("readonly","readonly");
												
											}
											return cellValue;
										}
									},
									{
										name : "corporateNo",
										index : "2",
										align : "center",
										width : "25px",
										sortable : false
									},
									{
										name : "professionalWork",
										index : "3",
										align : "left",
										width : "40px",
										sortable : false
									},
									{
										name : "enterpriseType",
										index : "4",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "vehicleNum",
										index : "5",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "",
										index : "6",
										align : "center",
										width : "30px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var handlerTemp;
											if (rowObject.enterpriseType === "化工企业") {
												handlerTemp = "";
											} else {
												handlerTemp = '<p class="jqgrid-handle-p">'
														+ '<label class="jqgrid-handle-text update-link" onclick="inforMagLogisticstInfor.detailEnterprise(\''
														+ rowObject.id
														+ '\')"><image src="../../images/icon/details.png">从业人员详情</label>'
														+ '</p>';
											}
											return handlerTemp;
										}
									},
									{
										name : "",
										index : "7",
										align : "center",
										width : "40px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text update-link" data-func="logisticst-update" onclick="inforMagLogisticstInfor.editLogisticst(\''
													+ id
													+ '\')"><image src="../../images/icon/edit.png">修改</label>' 
													+ '<span>&nbsp&nbsp</span>'
													+ '<label class="jqgrid-handle-text details-link" data-func="logisticst-findById" onclick="inforMagLogisticstInfor.detailsLogisticst(\''
													+ id
													+ '\')"><image src="../../images/icon/details.png">详情</label>'
													+ '<span>&nbsp&nbsp</span>'
													+ '<label class="jqgrid-handle-text details-link" data-func="logisticst-delete" onclick="inforMagLogisticstInfor.deleteLogisticst(\''
													+ id
													+ '\')"><image src="../../images/icon/delete.png">删除</label>'
										            +'</p>';
											return handlerTemp;
										}
									} ],
							loadonce : false,
							rownumbers : true,
							viewrecords : true,
							autowidth : true,
							height : true,
							shrinkToKit: false,
							autoScroll: false,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#logisticstPager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("logisticstTable");
							}
						}).resizeTableWidth();
	};

	// 新增
	var _addLogisticst = function() {
		$('#enterpriseType select').attr("disabled",false);//企业类型需还原为可编辑
		$("#corporateNo_enterprise").removeAttr("readonly");
		$(".showForEnterpriseDetail").addClass("hidden");
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_business-file_addOrUpdate').removeClass("hidden");
			$('form[target=multipatFile_2]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#business-file_addOrUpdate").removeClass("hidden");
		}
		$("#business-fileUpload_see").addClass("hidden");
		flag = "";
		$logisticstPanel.showPanelModel('新增企业信息');
		$logisticstPanel.saveOrCheck(true);
		$(".uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		uploadObj.settings("filesLen", 5);
	};
	
	//删除企业信息
	var _deleteLogisticst = function(id){
		//确认删除提示框
		//删除与否
		Message.confirm({
    		Msg     : $.msg.sureDelete,
            okSkin  : 'danger',
            iconImg : 'question',
            isModal : true
    	}).on(function(flag) {
    		if (flag) {
    			$.ajax({
    				url  : urlPath.deleteLogisticstUrl + id,
    				type : "delete",
    				async: false
    			}).done(function(json) {
                    if (json.code === 1) {
                    	$logisticstTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                    }
    			});
    		}
    	});
	};
	// 编辑企业信息
	var _editLogisticst = function(ids) {
		$("#corporateNo_enterprise").attr("readonly","readonly");
		//隐藏车辆数量
		$(".showForEnterpriseDetail").addClass("hidden");
		$("#showFileListMul_2").empty();
		$('.uploadify-queue').empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_business-file_addOrUpdate').removeClass("hidden");
			$('form[target=multipatFile_2]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#business-file_addOrUpdate").removeClass("hidden");
		}
		//隐藏详情的文件查看
		$("#business-fileUpload_see").addClass("hidden");//到底隐藏还是显示呢
		flag = ids;
		var updateObj = $.getData(urlPath.findByIdUrl + ids);
		if(!updateObj){
			return;
		}
		/*if (updateObj.enterpriseType === "化工企业") {
			$logisticstPanel.saveOrCheck(false);
			//化工企业 隐藏跨域类型
			$("#crossDomainType4Enter").addClass("hidden");
		} else {
			$logisticstPanel.saveOrCheck(true);
			$("#crossDomainType4Enter").removeClass("hidden");
		}*/
		if (updateObj.enterpriseType === "化工企业") {
			updateObj.enterpriseType = "106001002";
		} else if (updateObj.enterpriseType === "物流企业") {
			updateObj.enterpriseType = "106001001";
		}
		$('#enterpriseType select').attr("disabled",true);//企业类型不可修改
		$(".panel-footer").removeClass("hidden");
		$logisticstPanel.saveOrCheck(true);
		$logisticstPanel.showPanelModel('修改企业信息').setFormSingleObj(updateObj);
		// 编辑时候对附件的处理
		$(".uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - updateObj.fileIds.length;
		uploadObj.settings("filesLen", window.getOptionFileLen);
		
		for ( var i = 0; i < updateObj.fileIds.length; i++) {
			var content = '<li fileID='+updateObj.fileIds[i]+'>' + 
			  		'<span class="iefilename">'+updateObj.fileNames[i] + '</span>'+
			  		'<a href="javascript:void(0);" class="delfilebtn" onclick="ie9fileUpload.ie9fileDeleteEdit(this,\''+urlPath.deleteFileUrl+'\')">&nbsp;&nbsp;x</a></li>';
			if(isIe){
				//ie9填充已有的文件列表
				$("#showFileListMul_2").append(content);
			}else{
				//非ie9填充已有的文件列表
				$('#logisticst-panel .uploadify-queue').append(content);
			}
			editDeleFileOri.push(updateObj.fileIds[i]);
		}
		
	};

	
	// 详情
	var _detailsLogisticst = function(id) {
		$(".showForEnterpriseDetail").removeClass("hidden");
		$("#business-Files_see").empty();
		if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_business-file_addOrUpdate').addClass("hidden");
			$('form[target=multipatFile_2]').addClass("hidden");
		}else{
			//非ie9上传文件控件隐藏
			$("#business-file_addOrUpdate").addClass("hidden");
			$("#business-fileUpload_see").removeClass("hidden").appendTo("#logisticst-form .noie9filesmuliple");
		}
		
		var rowObj = $.getData(urlPath.findByIdUrl + id);
		if( !rowObj){
			return;
		}
		if( rowObj['crossDomainType4Enter'] == '105001001'){
			rowObj['crossDomainType4Enter'] = "港区";
		}else{
			rowObj['crossDomainType4Enter'] = "非港区";
		}
		$("#logisticst-panel").saveOrCheck(false);
		$("#logisticst-panel").showPanelModel('企业信息详情');
		$("#logisticst-form").setFormSingleObj(rowObj);
		//化工企业 隐藏跨域类型
		if(rowObj['enterpriseType'] == '化工企业'){
			$("#crossDomainType4Enter").addClass("hidden");
		}else{
			$("#crossDomainType4Enter").removeClass("hidden");
		}
		for ( var i = 0; i < rowObj.fileIds.length; i++) {
			var fileId = rowObj.fileIds[i];
			var fileDel = '<div class="fileDowloand"><small>'
					+ rowObj.fileNames[i]
					+ '</small><a class="" href="'
					+ urlPath.downloadFileUrl
					+ fileId
					+ '" download=fileIds[i]><span class="img-download"></span></a><span class="img-priview" onclick="inforMagLogisticstInfor.priviewFile(\''
					+ fileId + '\');"></span></div>';
			$("#business-Files_see").append(fileDel);
		}
	};
	// 预览图片
	var _priviewFile = function(id) {
		$("#enterpriseFile").attr("src", urlPath.downloadFileUrl + id);
		$("#previewEnterpriseFile-panel").showPanelModel('图片预览');
		$("#previewEnterpriseFile-panel").find(".panel-close").bind("click",function(){
			$("#previewEnterpriseFile-panel").fadeOut(300);
	    	$(".panel-mask").css("z-index","1000");
		});
	};
	// 从业人员信息详情
	var _detailEnterprise = function(ids) {
		var rowObj = $.getData(urlPath.findByIdUrl + ids);
		$("#enterprise-search-name").val(rowObj.enterpriseName);
		$("#enterpriseTabLi").removeClass("active");
		$("#employeeTabLi").find("a").click();
		$("#enterprise-search-name").val(rowObj.enterpriseName);
		// 清空历史查询条件
		var postData = $("#employeeTable").jqGrid("getGridParam", "postData");
		$.each(postData, function(i, d) {
			delete postData[i];
		});
		// 重新加载从业人员表格
		$("#employeeTable").jqGrid('setGridParam', {
			url : urlPath.findEmployeeByPageUrl,
			postData : {
				"id" : ids,
				"enterpriseName" : $("#enterprise-search-name").val(),
				"personName" : "",
				"occupationPersonType" : ''
			},
			page : 1
		}).trigger("reloadGrid");
	};

	// 提交修改处理方法
	var _submitAddHandle = function(ids) {
		if(isIe){
			// 附件id集合存放在隐藏域中 而且当新增的时候才使用
			var iefileid = [];
			var iefileidstring = "";
			var iefileLi = $("#showFileListMul_2 li");
			iefileLi.each(function(i){
				iefileid.push($(iefileLi[i]).attr("fileid"));
			});
			iefileidstring = iefileid.join(",");
			// 附件id集合存放在隐藏域中
	        $("#business-fileUp").val(iefileidstring);
		}else{
			// 附件id集合存放在隐藏域中 而且当编辑的时候才使用
			var fileLen = $("#business-fileUp").length;
			if (fileLen != 0) {
				for ( var j = 0; j < fileLen; j++) {
					if ($("#business-fileUp")[j].style.display === "none") {
						continue;
					} else {
						fileUpValue_plugin.push(editDeleFileOri[j]);
					}
				}
			}
			// 附件id集合存放在隐藏域中
			$("#business-fileUp").val(fileUpValue_plugin);
		}
		var formData = $logisticstForm.packageFormData();
		formData['fileIds'] = $("#business-fileUp").val();
		var url;
		if (flag) {
			url = urlPath.updateUrl + flag;
		} else {
			url = urlPath.saveUrl;
		}
		// 提交申请
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$logisticstPanel.closePanelModel();
				$logisticstTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
	};

	// 上传插件初始化
	var _upload_1 = function(fileUpload) {
		if (uploadObj) {
			uploadObj.destroy();
		}
		uploadObj = fileUpload
				.Huploadify({
					auto : true,
					fileTypeExts : "*.png;*.jpg;*.jpeg;*.PNG;*.JPG;*.JPEG;*.bmp;*.BMP",
					multi : true,
					deleteURL : urlPath.deleteFileUrl,
					fileSizeLimit : 10240,// 10M
					showUploadedPercent : true,
					showUploadedSize : true,
					removeTimeout : 9999999,
					uploader : urlPath.uploadFileUrl,
					filesLen : 5
				});
	};

	// 查询数据信息
	var _searchHandler = function() {
		$logisticstTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"enterpriseName" : $("#logisticst-search-name").val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	/**
	 * 导出
	 */
	var _export = function() {
		var enterpriseName = $("#logisticst-search-name").val();
		$.ajax({
			url   : urlPath.findByPageUrlForLogisticst,
			type  : 'POST',
			async : false,
			data  : {
				"enterpriseName"  : enterpriseName,
				"page" : 1,
				"rows" : 10
			}
		}).done(function(json) {
			if (json.code === 1 && json.rows && 0 < json.rows.length) {
				enterpriseName = encodeURI(enterpriseName, "UTF-8");
				window.location.href = urlPath.exportUrl + "?enterpriseName=" + enterpriseName;
			} else {
				Message.alert({Msg : "暂无数据，无法导出", isModal : false});
			}
		});
	};
	
	//企业信息同步
    var _enterpriseSynchronize = function(){
    	var url = $.getData(urlPath.synchronizeUrl);
    	$logisticstTable.trigger("reloadGrid");
    };

	
	_init();
	// 返回函数
	return {
		detailEnterprise : _detailEnterprise,
		editLogisticst : _editLogisticst,
		detailsLogisticst : _detailsLogisticst,
		deleteLogisticst : _deleteLogisticst,
		priviewFile : _priviewFile,
		_enterpriseSynchronize : _enterpriseSynchronize
	};
})(jQuery);
