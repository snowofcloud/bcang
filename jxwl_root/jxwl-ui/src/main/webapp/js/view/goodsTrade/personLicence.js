/**
 * @description:人员资质
 * @author: wangwenhuan
 * @time: 2016-12-14
 */  
var personLicence = (function($){
	
	//表格                                                                              
	var $personTable = $("#personTable");
	//搜索栏
	var $personSearchBtn = $("#person-searchBtn");
	var $personUploadBtn = $("#personUpload-addBtn");
	//表单
	var $personPanel = $("#personPanel");
	var $personForm = $("#personForm");
	var $verifyStatusRadio = $("input[name='personRadio']");
	var $searchName = $("#person-search #enterpriseName-search");
	var userFlag = true;
	//请求路径
    var urlPath = {
    		    findByPageUrl: $.backPath + "/licenceInfo/findByPage",
    	        findByIdUrl: $.backPath + "/licenceInfo/findById/",
    	        uploadUrl: $.backPath + "/licenceInfo/upload",
    	        updateUrl: $.backPath + "/licenceInfo/update",
    	        verifyUrl: $.backPath + "/licenceInfo/verify",
    	        deleteUrl: $.backPath + "/licenceInfo/delete/",
    	        findNameByNoUrl:$.backPath + "/logisticst/findNameByNo",
    	        findAllEnterpriseUrl: $.backPath + "/logisticst/findAllEnterprise",
    	        findPersonUrl: $.backPath + '/licenceInfo/findPerson',//查询所有驾驶员
    	        //数据字典
    	        findDicCodeUrl: $.backPath + '/licenceInfo/findDictByCode/005'
    };
    
    //变量
    var  licenceType= "person";
    var rowId;
    var formData;
    var loadOnce = true;
    var dicObj;
    var enterpriseName = "";
    //verify Upload  update 三个动作用同一个表单 设置变量以区分
    var actionName="upload";
    var actionArr = ["verify","upload","update"];
    var actionUrl = [urlPath.verifyUrl,urlPath.uploadUrl,urlPath.updateUrl];
    
    //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
    
    var _init = function(){
    	$("#ieuploadsfile_3").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,3);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(3, common4Licence.urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	$searchName.removeAttr("readonly");
		userFlag = true;
    	//附件文件相关初始化
    	//初始化上传插件
    	uploadObject = common4Licence.upload_init($("#fileUploadPerson"));
    	//初始化上传ID值
    	fileUpValue_plugin = [];
    	
    	if(loadOnce){
    		dicObj = $.getData(urlPath.findDicCodeUrl);
    		_initTable();
    		_bindClick();
    		loadOnce = !loadOnce;
    	}else{
    		_searchHandle();
    	}
    	var target = "#personNameOne";
    	_associatefunc(target);
    };
    
   var _associatefunc = function(target){
		//初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url =urlPath.findPersonUrl;
		associateObj = $.associate(config);
		
		//$(target).attr("pattern","");
		//重写联想下拉框getData方法
		associateObj.getData = function(val,obj){
			var list = $.getData(urlPath.findPersonUrl,"POST",{"personName":val});
			if(list && list.length > 0){
				obj.setData(list);
				obj.associateDiv.show();
				obj.associateDiv.empty();
				
				for(var i = 0; i < list.length && obj.showCnt; i++){
					var data = list[i];
					var str = "<div style='cursor:pointer;padding:2px;10px;'>"+data.personName+
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
			
			if(target == "#personNameOne"){
			$("#personIdentificationCardNo").val(data.identificationCardNo);
			$("#personBlackStatus").val(data.blackStatus);
			$("#personOccupationType").val($.getDicNameByCode(data.occupationPersonType, dicObj["100001"]));
			
			}else{
				$("#personWarnIdentificationCardNo").val(data.identificationCardNo);
				$("#personWarnBlackStatus").val(data.blackStatus);
				$("#personWarnOccupationType").val($.getDicNameByCode(data.occupationPersonType, dicObj["100001"]));
			}
		};
   };
    
  //绑定事件
    var _bindClick = function() {
    	//搜索栏中上传按钮
    	$personUploadBtn.bind("click",_upload);
    	$personSearchBtn.bind("click",_searchHandle);
		//审核表单中 radio切换事件
    	$verifyStatusRadio.bind("change", _rejectReason);
		// 表单事件绑定
    	$personForm.html5Validate(function() {
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
			var iefileLi = $("#showFileListMul_3 li");
			iefileLi.each(function(i){
				iefileid.push($(iefileLi[i]).attr("fileid"));
			});
			iefileidstring = iefileid.join(",");
			// 附件id集合存放在隐藏域中
	        $("#fileUpPerson").val(iefileidstring);
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
			$("#fileUpPerson").val(fileUpValue_plugin);
		}
		if(isIe){
			if( $('#showFileListMul_3').children().length == 0 && 
				$("#ie9_filePerson").css("display") =="block"){
				Message.show({
					Msg : "请上传至少一个附件",
					isModal : false
				});
				return;
			}
		}
		else{
			if( $('#file_upload_1-queue').children().length == 0 && 
				$("#filePerson").css("display") =="block"){
				Message.show({
					Msg : "请上传至少一个附件",
					isModal : false
				});
				return;
			}
		}
		//formData = $personForm.serialize();
		
		formData = $personForm.packageFormData();
		formData['fileIds'] = $("#fileUpPerson").val();
		//判断是否是verify
		if(actionName == actionArr[0]){//是verify 则设置审核结果和不通过原因
			var verifyStatus = $("input[name='personRadio']:checked")[0].id;
			var rejectContent = $("#personContent").val();
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
				$personPanel.closePanelModel();
				$personTable.jqGrid('setGridParam', {
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
                    	$personTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                    }
    			});
    		}
    	});
	};
	
    //查询
    var _searchHandle = function () {
    	$personTable.jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {
            	       "licenceType"        :  "person",
            		   "enterpriseName"     : $("#person #enterpriseName-search").val(),
            		   "personName"         : $("#personName-search").val(),  
            		   "verifyStatus"         : $("#personStatus-search").val()  
            },
            page    : 1
        }).trigger("reloadGrid");
    };
    //详情
    var _details = function(id){
    	$("#detailPersonFiles_see").empty();
		if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_filePerson').addClass("hidden");
			$('form[target=multipatFile_3]').addClass("hidden");
			$("#fileUploadPerson_see").removeClass("hidden").appendTo("#personForm .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#filePerson").addClass("hidden");
			$("#fileUploadPerson_see").removeClass("hidden").appendTo("#personForm .noie9filesmuliple");
		}
    	//获取相应数据
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	if (!rowObj || rowObj == ""){
    		return;
    	}
    	var verifyStatus = rowObj['verifyStatus'];
    	rowObj['verifyStatus'] = $.getDicNameByCode(verifyStatus, dicObj["151001"]);
    	var occupationPersonType = rowObj['occupationPersonType'];
    	rowObj['occupationPersonType'] =  $.getDicNameByCode(occupationPersonType, dicObj["100001"]);
    	$personPanel.showPanelModel('人员资质详情');
    	$personPanel.saveOrCheck(false);
    	$personForm.setFormSingleObj(rowObj);
    	
    	_hiddenOrDisplay("detail");
    	if( rowObj['verifyStatus'] === "未通过"){
    		$(".forRejectReason").removeClass("hidden");
    	}else{
    		$(".forRejectReason").addClass("hidden");
    	}
    	

    	//展示附件信息 详情
    	var target = "#detailPersonFiles_see";
    	common4Licence.detailDisplay(rowObj,target);
    };
  //审核资质
    var _verify = function(id){
    	$("#detailPersonFiles_see").empty();
		//$("#filePerson").addClass("hidden");
		if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_filePerson').addClass("hidden");
			$('form[target=multipatFile_3]').addClass("hidden");
			$("#fileUploadPerson_see").removeClass("hidden").appendTo("#personForm .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#filePerson").addClass("hidden");
			$("#fileUploadPerson_see").removeClass("hidden").appendTo("#personForm .noie9filesmuliple");
		}
    	actionName = actionArr[0];
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	if(!rowObj){
    		return;
    	}
    	$personPanel.showPanelModel('人员资质审核');
    	$personPanel.saveOrCheck(false);
    	$personForm.setFormSingleObj(rowObj);
    	var $footer = $personPanel.find(".panel-footer");
		$footer.removeClass("hidden");
    	_hiddenOrDisplay("verify");
    	_rejectReason();
    	
    	//展示附件信息 详情
    	var target = "#detailPersonFiles_see";
    	common4Licence.detailDisplay(rowObj,target);
    };
    
    
  //上传资质
    var _upload = function(){
    	actionName = actionArr[1];
    	enterpriseName = $.getData(urlPath.findNameByNoUrl);
    	$personPanel.showPanelModel('人员资质上传');
    	$personPanel.saveOrCheck(true);
    	$("#personPanel #enterpriseName").val(enterpriseName);
    	$("#personPanel #enterpriseName").attr("readonly","readonly");
    	$("#personPanel #identificationCardNo").attr("readonly","readonly");
    	_hiddenOrDisplay("upload");
    	
    	//重新设置最大上传数
    	uploadObject.settings("filesLen", 5);
    	//初始化上传ID值
    	fileUpValue_plugin = [];
    	$("#detailPersonFiles_see").empty();
    	if(isIe){
			//ie9上传文件控件显示
			$('#ie9_filePerson').removeClass("hidden");
			$('form[target=multipatFile_3]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#filePerson").removeClass("hidden");
	    	$("#filePerson").css("display","block");
		}
    	$("#fileUploadPerson_see").addClass("hidden");
		$(".uploadify-queue").html("");
    };
    
  
    
    //修改资质
    var _update = function(id){
    	actionName = actionArr[2];
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	$personPanel.showPanelModel('人员资质修改');
    	$personPanel.saveOrCheck(true);
    	$("#personPanel #enterpriseName").attr("readonly","readonly");
    	$personForm.setFormSingleObj(rowObj);
    	$("#personOccupationType").val($.getDicNameByCode(rowObj.occupationPersonType, dicObj["100001"]));
    	_hiddenOrDisplay("update");
    	
    	//修改初始化
    	$("#showFileListMul_3").empty();
		$('.uploadify-queue').empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_filePerson').removeClass("hidden");
			$('form[target=multipatFile_3]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#filePerson").removeClass("hidden");
		}
		$("#fileUploadPerson_see").addClass("hidden");
    	$("#personPanel .uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - rowObj.fileIds.length;
		uploadObject.settings("filesLen", window.getOptionFileLen);
		for(var i = 0; i < rowObj.fileIds.length; i++){
			fileUpValue_plugin.push(rowObj.fileIds[i]);
		}
		
		//展示附件信息 修改
		var target = "#personPanel .uploadify-queue";
    	var ie9_target = "#showFileListMul_3";
    	common4Licence.updateDisplay(rowObj,editDeleFileOri,isIe,target,ie9_target);
		
    };
    
  
  //未通过原因展现与隐藏
    var _rejectReason = function(){
    	var verifyStatus = $("input[name='personRadio']:checked")[0].id;
		if (verifyStatus === '151001002') {
			$("#personPanel #rejectRadio").removeClass("hidden");
		} else {
			$("#personPanel #rejectRadio").addClass("hidden");
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
    
    //初始化表格
    var _initTable = function () {
    	$personTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            postData: {
     	       "licenceType"        :  "person",
     		   "enterpriseName"     : $("#enterpriseName-search").val()
            },
            colNames: ["姓名","从业资格类别", "资质名称", "所属物流企业名称", "证件到期时间","发证机关", "黑名单","审核状态", "操作"],
            colModel: [
                {name: "personName", index: "1", align: "left", width: "30px", sortable: false},
                {name: "occupationPersonType", index: "1", align: "left", width: "30px", sortable: false,
                	formatter : function(cellValue) {
						return $.getDicNameByCode(cellValue, dicObj["100001"]);
					}
                },
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
						return $.getDicNameByCode(cellValue, dicObj["151001"]);
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
            pager: "#personPager",
            gridComplete: function () {
                //表格中按钮权限配置
                $.initPrivg("personTable");
            }
        }).resizeTableWidth();
    };
    
    //格式化操作按钮
    var _settingHandlerBtn = function(rowObject){
    	 var verifyStatus = rowObject['verifyStatus'];
         var id = rowObject['id'];
         if (verifyStatus === '151001001') {//待审核
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="personLicenceInfo-verify" onclick="personLicence.verify(\'' + id + '\')" title="审核"><span class="img-ok"/></span>审核</label>' +
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="personLicenceInfo-findById" onclick="personLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +    
             '</p>';
         } else if (verifyStatus === '151001002') {//未通过
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="personLicenceInfo-update" onclick="personLicence.update(\'' + id + '\')" title="修改"><span class="img-edit"></span>修改</label>' +
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="personLicenceInfo-delete" onclick="personLicence.del(\'' + id + '\')" title="删除"><span class="img-delete"></span>删除</label>' +
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="personLicenceInfo-findById" onclick="personLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         } else if (verifyStatus === '151001003') {//已通过
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="personLicenceInfo-findById" onclick="personLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
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
    	associatefunc: _associatefunc
    };
    
})(jQuery);