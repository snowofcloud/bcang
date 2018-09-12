/**
 * @description:车辆资质
 * @author: wangwenhuan
 * @time: 2016-12-14
 */  
var carLicence = (function($){
	
	//表格                                                                              
	var $carTable = $("#carTable");
	//搜索栏
	var $carSearchBtn = $("#car-searchBtn");
	var $carUploadBtn = $("#carUpload-addBtn");
	//表单
	var $carPanel = $("#carPanel");
	var $carForm = $("#carForm");
	var $verifyStatusRadio = $("input[name='carRadio']");
	var $searchName = $("#car-search #enterpriseName-search");
	var userFlag = true;
	//请求路径
    var urlPath = {
    		    //资质备案管理基本功能
    		    findByPageUrl: $.backPath + "/licenceInfo/findByPage",
    	        findByIdUrl: $.backPath + "/licenceInfo/findById/",
    	        uploadUrl: $.backPath + "/licenceInfo/upload",
    	        updateUrl: $.backPath + "/licenceInfo/update",
    	        verifyUrl: $.backPath + "/licenceInfo/verify",
    	        deleteUrl: $.backPath + "/licenceInfo/delete/",
    	        findAllEnterpriseUrl: $.backPath + "/logisticst/findAllEnterprise",//查询所有企业
    	        findVehicleUrl: $.backPath + '/licenceInfo/findVehicle',
    	        //查询企业名字
    	        findNameByNoUrl:$.backPath + "/logisticst/findNameByNo",
    	        //数据字典
    	        findDicCodeUrl: $.backPath + '/licenceInfo/findDictByCode/151001'
    };
    
    //变量
    var licenceType = "car";
    var rowId;
    var loadOnce = true;
    var enterpriseName = "";
    var isUpload = false;
    //verify Upload  update 三个动作用同一个表单 设置变量以区分
    var actionName="upload";
    var actionArr = ["verify","upload","update"];
    var actionUrl = [urlPath.verifyUrl,urlPath.uploadUrl,urlPath.updateUrl];
    
    //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
    
    var _init = function(){	
    	$("#ieuploadsfile_2").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,2);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(2, common4Licence.urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	$searchName.removeAttr("readonly");
		 userFlag = true;
    	//附件文件相关初始化
    	//初始化上传插件
    	uploadObject = common4Licence.upload_init($("#fileUploadCar"));
    	//初始化上传ID值
    	fileUpValue_plugin = [];
    	
      //联想查询初始化
	   var target = "#carLicencePlateNo";
       _associatefunc(target);
    	
    	if(loadOnce){
    		_initTable();
    		dicObj = $.getData(urlPath.findDicCodeUrl);
    		_bindClick();
    		loadOnce = !loadOnce;		
    	}else{
    		_searchHandle();
    	}
     
    };
  
  //绑定事件
    var _bindClick = function() {
    	//搜索栏中上传按钮
    	$carUploadBtn.bind("click",_upload);
    	$carSearchBtn.bind("click",_searchHandle);
		//审核表单中 radio切换事件
    	$verifyStatusRadio.bind("change", _rejectReason);
		// 表单事件绑定
    	$carForm.html5Validate(function() {
			_submitHandle();
			return false;
		});

	};
	//提交操作
	var _submitHandle = function(){
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
	        $("#fileUpCar").val(iefileidstring);
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
			$("#fileUpCar").val(fileUpValue_plugin);
		}
		if(isIe){
			if( $('#showFileListMul_2').children().length == 0 && 
				$("#ie9_fileCar").css("display") =="block"){
				Message.show({
					Msg : "请上传至少一个附件",
					isModal : false
				});
				return;
			}
		}
		else{
			if( $('#file_upload_1-queue').children().length == 0 &&
				$("#fileCar").css("display") =="block"){
				Message.show({
					Msg : "请上传至少一个附件",
					isModal : false
				});
				return;
			}
		}
		var formData = $carForm.packageFormData();
		formData['fileIds'] = $("#fileUpCar").val();
		//判断是否是verify
		if(actionName == actionArr[0]){//是verify 则设置审核结果和不通过原因
			var verifyStatus = $("input[name='carRadio']:checked")[0].id;
			var rejectContent = $("#carContent").val();
			formData['verifyStatus'] = verifyStatus;
			formData['rejectReason'] = rejectContent;
		}
		 formData['licenceType'] = licenceType;
		//得到action相应的url
		var actionUrlPath ="";
		for(var i=0;i<actionArr.length;i++){
			if(  actionName == actionArr[i] ){
				actionUrlPath = actionUrl[i];
				break;
			}
		}
		$.ajax({
			url : actionUrlPath,
			type : 'POST',
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$carPanel.closePanelModel();
				$carTable.jqGrid('setGridParam', {
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
	};
	//删除
	var _del = function(id){
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
    				url  : urlPath.deleteUrl +licenceType+'/'+ id,
    				type : "delete",
    				async: false
    			}).done(function(json) {
                    if (json.code === 1) {
                    	$carTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                    }
    			});
    		}
    	});
	};
	
    //查询
    var _searchHandle = function () {
    	$carTable.jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {
            	       "licenceType"        :  "car",
            		   "enterpriseName"     : $("#car #enterpriseName-search").val(),
            		   "carNo"              : $("#carNo-search").val(),
            		   "verifyStatus"       : $("#carStatus-search").val()
            		   
            },
            page    : 1
        }).trigger("reloadGrid");
    };
    //详情
    var _details = function(id){
    	$("#detailCarFiles_see").empty();
		if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_fileCar').addClass("hidden");
			$('form[target=multipatFile_2]').addClass("hidden");
			$("#fileUploadCar_see").removeClass("hidden").appendTo("#carForm .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#fileCar").addClass("hidden");
			$("#fileUploadCar_see").removeClass("hidden").appendTo("#carForm .noie9filesmuliple");
		}
    	//获取相应数据
    	var rowObj =$.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	if (!rowObj || rowObj == ""){
    		return;
    	}
    	var verifyStatus = rowObj['verifyStatus'];
    	rowObj['verifyStatus'] = $.getDicNameByCode(verifyStatus, dicObj);
    	$carPanel.showPanelModel('车辆资质详情');
    	$carPanel.saveOrCheck(false);
    	$carForm.setFormSingleObj(rowObj);
    	
    	_hiddenOrDisplay("detail");
    	if( rowObj['verifyStatus'] === "未通过"){
    		$(".forRejectReason").removeClass("hidden");
    	}else{
    		$(".forRejectReason").addClass("hidden");
    	}
    	//展示附件信息 详情
    	var target = "#detailCarFiles_see";
    	common4Licence.detailDisplay(rowObj,target);
    };
  //审核资质
    var _verify = function(id){
    	$("#detailCarFiles_see").empty();
		//$("#fileCar").addClass("hidden");
    	if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_fileCar').addClass("hidden");
			$('form[target=multipatFile_2]').addClass("hidden");
			$("#fileUploadCar_see").removeClass("hidden").appendTo("#carForm .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#fileCar").addClass("hidden");
			$("#fileUploadCar_see").removeClass("hidden").appendTo("#carForm .noie9filesmuliple");
		}
    	actionName = actionArr[0];
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	$carPanel.showPanelModel('车辆资质审核');
    	$carPanel.saveOrCheck(false);
    	$carForm.setFormSingleObj(rowObj);
    	var $footer = $carPanel.find(".panel-footer");
		$footer.removeClass("hidden");
    	_hiddenOrDisplay("verify");
    	_rejectReason();
    	//展示附件信息 详情
    	var target = "#detailCarFiles_see";
    	common4Licence.detailDisplay(rowObj,target);
    };
    
    
  //上传资质
    var _upload = function(){
    	
    	
    	actionName = actionArr[1];
    	enterpriseName = $.getData(urlPath.findNameByNoUrl);
    	$carPanel.showPanelModel('车辆资质上传');
    	$carPanel.saveOrCheck(true);
    	$("#carPanel #enterpriseName").val(enterpriseName);
    	$("#carPanel #enterpriseName").attr("readonly","readonly");
    	_hiddenOrDisplay("upload");
    	
    	//初始化上传ID值
    	fileUpValue_plugin = [];
    	$("#detailCarFiles_see").empty();
    	if(isIe){
			//ie9上传文件控件显示
			$('#ie9_fileCar').removeClass("hidden");
			$('form[target=multipatFile_2]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#fileCar").removeClass("hidden");
	    	$("#fileCar").css("display","block");
		}
		$("#fileUploadCar_see").addClass("hidden");
		$(".uploadify-queue").html("");
		//重新设置最大上传数
    	uploadObject.settings("filesLen", 5);
    };
    
  
    
    //修改资质
    var _update = function(id){
    	actionName = actionArr[2];
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	$carPanel.showPanelModel('车辆资质修改');
    	$carPanel.saveOrCheck(true);
    	$("#carPanel #enterpriseName").attr("readonly","readonly");
    	
    	$carForm.setFormSingleObj(rowObj);
    	_hiddenOrDisplay("update");
    	
    	$("#showFileListMul_2").empty();
		$('.uploadify-queue').empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_fileCar').removeClass("hidden");
			$('form[target=multipatFile_2]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#fileCar").removeClass("hidden");
		}
		$("#fileUploadCar_see").addClass("hidden");
		
		
		$(".uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - rowObj.fileIds.length;
		uploadObject.settings("filesLen", window.getOptionFileLen);
		for(var i = 0; i < rowObj.fileIds.length; i++){
			fileUpValue_plugin.push(rowObj.fileIds[i]);
		}
		//展示附件信息 修改
		var target = "#carPanel .uploadify-queue";
    	var ie9_target = "#showFileListMul_2";
    	common4Licence.updateDisplay(rowObj,editDeleFileOri,isIe,target,ie9_target);
		
    };
    
  
  //未通过原因展现与隐藏
    var _rejectReason = function(){
    	var verifyStatus = $("input[name='carRadio']:checked")[0].id;
		if (verifyStatus === '151001002') {
			$("#carPanel #rejectRadio").removeClass("hidden");
		} else {
			$("#carPanel #rejectRadio").addClass("hidden");
		}
    };
    
    
    //部分字段自适应展现与隐藏
    var _hiddenOrDisplay = function(flag){
    	if(flag == "update" || flag == "upload"){
    		$(".forDetail").addClass("hidden");
    		$(".forRejectReason").addClass("hidden");
    		$(".forVerify").addClass("hidden");
    	}else if(flag == "detail"){
    		$(".forDetail").removeClass("hidden");
    		$(".forVerify").addClass("hidden");
    	}else if(flag == "verify"){
    		$(".forVerify").removeClass("hidden");
    		$(".forDetail").addClass("hidden");
    		$(".forRejectReason").addClass("hidden");
    	}
    };
    
    //联想查询函数
    var _associatefunc = function(target){
    	//初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url =urlPath.findVehicleUrl;
		associateObj = $.associate(config);
		
		//$(target).attr("pattern","");
		//重写联想下拉框getData方法
		associateObj.getData = function(val,obj){
			var list = $.getData(urlPath.findVehicleUrl,"POST",{"licencePlateNo":val});
			if(list && list.length > 0){
				obj.setData(list);
				obj.associateDiv.show();
				obj.associateDiv.empty();
				
				for(var i = 0; i < list.length && obj.showCnt; i++){
					var data = list[i];
					var str = "<div style='cursor:pointer;padding:2px;10px;'>"+data.licencePlateNo+
					"</div>";
					obj.associateDiv.append(str);
				}
			}
		};
		//重写联想下拉框selectData方法
		associateObj.selectData = function(data,divObj,obj){
			var name = divObj.text();
			if(data && data.name){
				name = data.name;
			}
			$(obj.inputObj).val(name);
			if( target == "#carLicencePlateNo"){
			  $("#carBlackStatus").val(data.blackStatus);
			}else{
			 $("#carWarnBlackStatus").val(data.blackStatus);
			}
		};
    };
    
    //初始化表格
    var _initTable = function () {
    	$carTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            postData: {
     	       "licenceType"        :  "car",
     		   "enterpriseName"     : $("#enterpriseName-search").val()
            },
            colNames: ["车牌号", "资质名称", "所属物流企业名称", "证件到期时间","发证机关", "黑名单","审核状态", "操作"],
            colModel: [
                {name: "licencePlateNo", index: "1", align: "left", width: "30px", sortable: false},
                {name: "licenceName", index: "2", align: "center", width: "30px", sortable: false},
                {name: "enterpriseName", index: "2", align: "center", width: "30px", sortable: false,
                	formatter : function(cellValue,options, rowObject) {
						if(topMenuMag.isLogisticsEnterpriseUser() || topMenuMag.isChemicalEnterpriseUser() && userFlag){
							userFlag = false;
							$searchName.val(cellValue);
							$searchName.attr("readonly","readonly");
							
						}
						return cellValue;
					}
                },
                {name: "expireDate", index: "3", align: "center", width: "30px", sortable: false},
                {name: "licenceIssuingAuthority", index: "4", align: "center", width: "30px", sortable: false},
                {name: "blackStatus", index: "6", align: "center", width: "30px", sortable: false},
                {name: "verifyStatus", index: "6", align: "center", width: "30px", sortable: false,
                	formatter : function(cellValue) {
						return $.getDicNameByCode(cellValue, dicObj);
					}
                },
                {name: "handel", index: "7", align: "center", width: "70px", sortable: false,
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
            pager: "#carPager",
            gridComplete: function () {
                //表格中按钮权限配置
                $.initPrivg("carTable");
            }
        }).resizeTableWidth();
    };
    
    //格式化操作按钮
    var _settingHandlerBtn = function(rowObject){
    	 var verifyStatus = rowObject['verifyStatus'];
         var id = rowObject['id'];
         if (verifyStatus === '151001001') {//待审核
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="carLicenceInfo-verify" onclick="carLicence.verify(\'' + id + '\')" title="审核"><span class="img-ok"/></span>审核</label>' +
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="carLicenceInfo-findById" onclick="carLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +    
             '</p>';
         } else if (verifyStatus === '151001002') {//未通过
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="carLicenceInfo-update" onclick="carLicence.update(\'' + id + '\')" title="修改"><span class="img-edit"></span>修改</label>' +
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="carLicenceInfo-delete" onclick="carLicence.del(\'' + id + '\')" title="删除"><span class="img-delete"></span>删除</label>' +
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="carLicenceInfo-findById" onclick="carLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         } else if (verifyStatus === '151001003') {//已通过
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="carLicenceInfo-findById" onclick="carLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         } else if (verifyStatus === '151001004') {//已注销
        	 return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-findById" onclick="enterpriseLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         }
    };
    
    //初始化
   // _init();
    
    //返回接口
    return{
    	init : _init,
    	details : _details,
    	update  : _update,
    	verify  : _verify,
    	del     : _del,
    	//抛出给car到期提醒使用
    	associatefunc:_associatefunc
    };
    
})(jQuery);