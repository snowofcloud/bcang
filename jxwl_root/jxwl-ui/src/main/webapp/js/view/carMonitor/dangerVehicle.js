/**
 * @description:监控管理-危险品车辆信息
 * @author: xiaoqx
 * @time: 2016-7-19
 */
// 危险品车辆信息
var inforMagDangerCarInfor = (function($) {
	// 模块缓存变量
	var $dangerCarTable = $('#dangerCarTable');
	var $carSearchBtn = $("#car-searchBtn");
	var $carExport = $("#car-export");
	var $addCarInfor = $("#car-addBtn");
	var $saveOrUpdateCarInforPanel = $("#dangercarinfor-panel");
	var $dangercarinforForm = $("#dangercarinfor-form");
	var $previewDangercarFilePanel = $("#previewDangercarFile-panel");
	var $accStatus = $("#acc-status");
	var $crossType = $("#cross-type");
	var $searchName = $("#enterpriseName-search");
	var userFlag = true;
	// 请求路径
	var urlPath = {
		findByPageUrl : $.backPath + '/dangerVehicle/findByPage',
		updateUrl : $.backPath + '/dangerVehicle/update/',
		saveUrl : $.backPath + '/dangerVehicle/save',
		findByIdUrl : $.backPath + '/dangerVehicle/findById/',
		deleteUrl : $.backPath + '/dangerVehicle/delete/',
		exportUrl : $.backPath + '/dangerVehicle/export',
		findDictByCodeUrl : $.backPath + "/dangerVehicle/findDictByCode/001",
		uploadFileUrl : $.backPath + '/dangerVehicle/uploadFile',
		deleteFileUrl : $.backPath + '/dangerVehicle/deleteFile',
		downloadFileUrl : $.backPath + "/dangerVehicle/downloadFile/"
	};
	var loadOnce = false;
	// 数据初始化
	var _init = function() {
		$("#ieuploadsfile_1").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,1);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(1, urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
		
		$searchName.removeAttr("readonly");
		userFlag = true;
		if (!loadOnce) {
			// 下拉列表
			dicObj = $.getData(urlPath.findDictByCodeUrl);
			$("#acc-status").settingsOptions(dicObj["102001"], false);
			$("#cross-type").settingsOptions(dicObj["105001"], false);
			$("#vehicleType-add").settingsOptions(dicObj["109001"], false);
			$("#crossType-add").settingsOptions(dicObj["105001"], false);
			// 第一个tab标签 要先解除绑定在进行绑定事件
			$carExport.unbind("click").bind("click", _export);
			$carSearchBtn.unbind("click").bind("click", _searchHandler);
			$addCarInfor.unbind("click").bind("click", _addCarInfor);
			$accStatus.unbind("change").bind("change", _searchHandler);
			$crossType.unbind("change").bind("change", _searchHandler);
			_initTable();
			// 表单事件绑定
			$dangercarinforForm.html5Validate(function() {
				_submitHandle();
				return false;
			});
			_upload_1($("#fileUpload_0"));
			loadOnce = !loadOnce;
			// 回车搜索
			$("#dangerCar-search input").keyup(function(e) {
				if (e.keyCode === 13) {
					_searchHandler();
				}
			});
		} else {
			_searchHandler();
		}

	};
	// 变量
	var flag, uploadObj, editDeleteFile = "", editDeleFileOri = [];
	// 表格
	var _initTable = function() {
		$dangerCarTable.jqGrid(
						{
							url : urlPath.findByPageUrl,
							mtype : "POST",
							datatype : "JSON",
							colNames : ["企业名称", "车牌号", "车辆类型", "车辆排量(L)", "发动机号码",
									"底盘号码",  "跨域类型", "车辆状态", "操作" ],
							colModel : [
									{
										name : "enterpriseName",
										index : "1",
										align : "center",
										width : "30px",
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
										name : "licencePlateNo",
										index : "2",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "vehicleType",
										index : "3",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "vehicleOutput",
										index : "4",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "motorNo",
										index : "5",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "graduadedNo",
										index : "6",
										align : "center",
										width : "30px",
										sortable : false
									},
									{
										name : "crossDomainType",
										index : "7",
										align : "center",
										width : "20px",
										sortable : false
									},
									{
										name : "status",
										index : "8",
										align : "center",
										width : "20px",
										sortable : false,
										formatter:function(cellValue){
											if( cellValue == 1){
												return "离线";
											}else{
												return "在线";
											}
										}
									},
									{
										name : "handel",
										index : "9",
										align : "center",
										width : "45px",
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											var id = rowObject.id;
											var handlerTemp = '<p class="jqgrid-handle-p">'
													+ '<label class="jqgrid-handle-text delete-link" data-func="dangerVehicle-update" onclick="inforMagDangerCarInfor.editCarInfor(\''
													+ id
													+ '\')"><span class="img-edit"></span>修改</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="dangerVehicle-findById" onclick="inforMagDangerCarInfor.detailCarInfor(\''
													+ id
													+ '\')"><span class="img-details"></span>详情</label>'
													+ '<span>&nbsp;&nbsp;</span>'
													+ '<label class="jqgrid-handle-text delete-link" data-func="dangerVehicle-delete" onclick="inforMagDangerCarInfor.deleteCarInfor(\''
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
							height : true,
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#dangerCarPager",
							gridComplete : function() {
								// 配置权限
								$.initPrivg("dangerCarTable");
							}
						}).resizeTableWidth();
	};

	// 删除
	var _deleteCarInfor = function(id) {
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
						$dangerCarTable.jqGrid('setGridParam', {
							postData : {},
							page : 1
						}).trigger("reloadGrid");
						//调用topMenu.js的车辆数据计算函数
						topMenuMag.carAmountFunc(-1,0,-1);
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

	// 新增
	var _addCarInfor = function() {
		$("#fileUpload_see").addClass("hidden");
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_file_addOrUpdate').removeClass("hidden");
			$('form[target=multipatFile_1]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#file_addOrUpdate").removeClass("hidden");
		}
		flag = "";
		$saveOrUpdateCarInforPanel.showPanelModel('新增车辆');
		$saveOrUpdateCarInforPanel.saveOrCheck(true);
		$(".detailShow").addClass("hidden");

		fileUpValue_plugin = [];// 上传的文件的id集合
		uploadManager.filteredFiles = []; // 文件过滤
		$(".uploadify-queue").html("");
	};

	// 编辑
	var _editCarInfor = function(ids) {
		$(".detailShow").addClass("hidden");
		$("#fileUpload_see").addClass("hidden");
		$("#showFileListMul_1").empty();
		$('.uploadify-queue').empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_file_addOrUpdate').removeClass("hidden");
			$('form[target=multipatFile_1]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#file_addOrUpdate").removeClass("hidden");
		}
		flag = ids;
		$("#car-status").addClass("hidden");
		var updateObj = $.getData(urlPath.findByIdUrl + ids);
		if (!updateObj) {
			return;
		}
		$saveOrUpdateCarInforPanel.saveOrCheck(true);
		$saveOrUpdateCarInforPanel.showPanelModel('修改车辆信息').setFormSingleObj(
				updateObj);
		// 编辑时候对附件的处理
		$(".uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		var fileIds = [];
		var fileNames = [];
		if (updateObj.dangerFileRela.length) {
			for ( var i = 0, len = updateObj.dangerFileRela.length; i < len; i++) {
				fileIds.push(updateObj.dangerFileRela[i].fileId);
				fileNames.push(updateObj.dangerFileRela[i].fileName);
			}
			for ( var i = 0; i < fileIds.length; i++) {
				var content = '<li fileID='+fileIds[i]+'>' + 
				  		'<span class="iefilename">'+fileNames[i] + '</span>'+
				  		'<a href="javascript:void(0);" class="delfilebtn" onclick="ie9fileUpload.ie9fileDeleteEdit(this,\''+urlPath.deleteFileUrl+'\')">&nbsp;&nbsp;x</a></li>';
				if(isIe){
					//ie9填充已有的文件列表
					$("#showFileListMul_1").append(content);
				}else{
					//非ie9填充已有的文件列表
					$('#dangercarinfor-panel .uploadify-queue').append(content);
				}
				editDeleFileOri.push(fileIds[i]);
			}
		}
	};

	

	// 详情
	var _detailCarInfor = function(ids) {
		$dangercarinforForm.find("span").text("");
		
		if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_file_addOrUpdate').addClass("hidden");
			$('form[target=multipatFile_1]').addClass("hidden");
			$("#fileUpload_see").removeClass("hidden").appendTo("#dangercarinfor-form .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#file_addOrUpdate").addClass("hidden");
			$("#fileUpload_see").removeClass("hidden").appendTo("#dangercarinfor-form .noie9filesmuliple");
		}
		$(".detailShow").removeClass("hidden");
		var rowObj = $.getData(urlPath.findByIdUrl + ids);
		if( rowObj){
			if( rowObj.status==1){
				rowObj['status'] = '离线';
			}else{
				rowObj['status'] = '上线';
			}
		}
		$("#detailCarInfoFiles_see").empty();
		$saveOrUpdateCarInforPanel.saveOrCheck(false);
		$saveOrUpdateCarInforPanel.showPanelModel('车辆信息详情');
		$dangercarinforForm.setFormSingleObj(rowObj);
		if (rowObj.vehicleType) {
			$("#vehicleTypeStr").text(
					$.getDicNameByCode(rowObj.vehicleType, dicObj["109001"]));
		}
		if (rowObj.crossDomainType) {
			$("#crossDomainTypeStr").text(
					$
							.getDicNameByCode(rowObj.crossDomainType,
									dicObj["105001"]));
		}
		if (rowObj.status) {
			$("#statusStr").text(
					$.getDicNameByCode(rowObj.status, dicObj["102001"]));
		}
		var fileIds = [];
		var fileNames = [];
		if (rowObj.dangerFileRela.length) {
			for ( var i = 0, len = rowObj.dangerFileRela.length; i < len; i++) {
				fileIds.push(rowObj.dangerFileRela[i].fileId);
				fileNames.push(rowObj.dangerFileRela[i].fileName);
			}
			for ( var i = 0; i < fileIds.length; i++) {
				var docType = fileNames[i].split(".")[1];
				var id;
				if ((docType == "jpg") || (docType == "JPG")
						|| (docType == "png") || (docType == "PNG")) {
					id = fileIds[i];
					var fileDel = '<div class="fileDowloand"><small>'
							+ fileNames[i]
							+ '</small>'
							+ '<a class="" href="'
							+ urlPath.downloadFileUrl
							+ id
							+ '" download=fileIds[i]>'
							+ '<span class="img-download"></span></a><span class="img-priview" onclick="inforMagDangerCarInfor.priviewFile(\''
							+ id + '\');">' + '</span></div>';
				} else {
					id = fileIds[i];
					var fileDel = '<div class="fileDowloand"><small>'
							+ fileNames[i] + '</small>' + '<a class="" href="'
							+ urlPath.downloadFileUrl + id
							+ '" download=fileIds[i]>'
							+ '<span class="img-download"></span> </a>'
							+ '</div>';
				}
				$("#detailCarInfoFiles_see").append(fileDel);
			}
		}
	};

	// 预览图片
	var _priviewFile = function(id) {
		$previewDangercarFilePanel.showPanelModel('车辆照片');
		$previewDangercarFilePanel.find(".panel-close").bind("click",
				function() {
					$previewDangercarFilePanel.fadeOut(300);
					$(".panel-mask").css("z-index", "1000");
				});
		$("#dangercarFile").attr("src", urlPath.downloadFileUrl + id);
	};

	// 提交
	var _submitHandle = function() {
		
		if(isIe){
			// 附件id集合存放在隐藏域中 而且当新增的时候才使用
			var iefileid = [];
			var iefileidstring = "";
			var iefileLi = $("#showFileListMul_1 li");
			iefileLi.each(function(i){
				iefileid.push($(iefileLi[i]).attr("fileid"));
			});
			iefileidstring = iefileid.join(",");
			// 附件id集合存放在隐藏域中
	        $("#fileUp").val(iefileidstring);
		}else{
			// 附件id集合存放在隐藏域中 而且当编辑的时候才使用
			var fileLen = $(".fileDelete").length;
			if (fileLen != 0) {
				for ( var j = 0; j < fileLen; j++) {
					if ($(".fileDelete")[j].style.display === "none") {
						continue;
					} else {
						fileUpValue_plugin.push(editDeleFileOri[j]);
					}
				}
			}
			// 附件id集合存放在隐藏域中
			$("#fileUp").val(fileUpValue_plugin);
		}
		var formData = $dangercarinforForm.packageFormData();
		//判断车牌号是否符合规范
		var carrierName = formData.licencePlateNo;
		if(!standardCarrierName(carrierName)){
			$('#licencePlateNo-add').testRemind("您输入的车牌号格式不正确");
			$('#licencePlateNo-add').focus();
			$('#licencePlateNo-add').select();
			return;
		}
		formData['fileIds'] = $("#fileUp").val();
		var url = flag ? urlPath.updateUrl + flag : urlPath.saveUrl;
		$.ajax({
			url : url,
			type : 'POST',
			async : false,
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$saveOrUpdateCarInforPanel.closePanelModel();
				$dangerCarTable.jqGrid('setGridParam', {
					postData : {},
					page : 1
				}).trigger("reloadGrid");
				//调用topMenu.js的车辆数据计算函数
				topMenuMag.carAmountFunc(1,0,1);
			} else {
				Message.alert({
					Msg : json.msg,
					isModal : false
				});
			}
		});
	};
	
	var standardCarrierName = function(carrierName) {
		var placeArr = ['京','津','沪','渝','蒙','新','藏','宁','桂','港','澳',
		                '黑','吉','辽','晋','冀','青','鲁','豫','苏','皖','浙',
		                '闽','赣','湘','鄂','粤','琼','甘','陕','黔','滇','川'];
		for ( var i in placeArr) {
			if(placeArr.toString().indexOf(carrierName.charAt(0))>-1){
				return true;
			}
		}
		return false;
	}
	// 上传插件初始化
	var _upload_1 = function(fileUpload) {
		if (uploadObj) {
			uploadObj.destroy();
		}
		uploadObj = fileUpload.Huploadify({
			auto : true,
			fileTypeExts : "*.png;*.jpg;*.jpeg;*.PNG;*.JPG;*.JPEG;*.bmp;*.BMP",
			multi : true,
			deleteURL : urlPath.deleteFileUrl,
			fileSizeLimit : 10240,// 10M
			showUploadedPercent : true,
			showUploadedSize : true,
			removeTimeout : 9999999,
			uploader : urlPath.uploadFileUrl,
			filesLen : 100
		});
	};

	// 查询数据信息
	var _searchHandler = function() {
		$dangerCarTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"enterpriseName" : $("#enterpriseName-search").val(),
				"licencePlateNo" : $("#licenseNo-search").val(),
				"crossDomainType" : $("#cross-type").val()
			},
			page : 1
		}).trigger("reloadGrid");
	};

	/**
	 * 导出
	 */
	var _export = function() {
		var enterpriseName  = $("#enterpriseName-search").val();
		var crossDomainType = $("#cross-type").val();
		var licencePlateNo  = $("#licenseNo-search").val();
		
		$.ajax({
			url   : urlPath.findByPageUrl,
			type  : 'POST',
			async : false,
			data  : {
				"enterpriseName"  : enterpriseName,
				"crossDomainType" : crossDomainType,
				"licencePlateNo"  : licencePlateNo,
				"page" : 1,
				"rows" : 10
			}
		}).done(function(json) {
			if (json.code === 1 && json.rows && 0 < json.rows.length) {
				enterpriseName = encodeURI(enterpriseName, "UTF-8");
				licencePlateNo = encodeURI(licencePlateNo, "UTF-8");
				window.location.href = urlPath.exportUrl + "?enterpriseName="
					+ enterpriseName + "&crossDomainType=" + crossDomainType
					+ "&licencePlateNo=" + licencePlateNo;
			} else {
				Message.alert({Msg : "暂无数据，无法导出", isModal : false});
			}
		});
	};


	// 返回函数
	return {
		init : _init,
		editCarInfor : _editCarInfor,
		detailCarInfor : _detailCarInfor,
		priviewFile : _priviewFile,
		deleteCarInfor : _deleteCarInfor
	};
})(jQuery);
