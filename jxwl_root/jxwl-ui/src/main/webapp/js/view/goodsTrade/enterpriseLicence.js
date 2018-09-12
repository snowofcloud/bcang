/**
 * @description:资质备案管理-企业资质
 * @author: wangwenhuan
 * @time: 2016-12-14
 */  
var enterpriseLicence = (function($){
	//tab变量 用于切换tab控制变量
	var $licenceManageTabs = $("#licenceManage-tabs"); 
	//表格                                                                              
	var $enterpriseTable = $("#enterpriseTable");
	//搜索栏
	var $enterpriseSearchBtn = $("#enterprise-searchBtn");
	var $enterpriseUploadBtn = $("#enterpriseUpload-addBtn");
	//违章查询按钮对象
	var $peccantSearchBtn1 = $("#peccant-searchBtn1");
	var $peccantSearchBtn2 = $("#peccant-searchBtn2");
	var $peccantSearchBtn3 = $("#peccant-searchBtn3");
	//表单
	var $enterprisePanel = $("#enterprisePanel");
	var $enterpriseForm = $("#enterpriseForm");
	var $verifyStatusRadio = $("input[name='enterpriseRadio']");
	var $searchName = $("#enterprise-search #enterpriseName-search");
	var userFlag = true;
	//请求路径
    var urlPath = {
        findByPageUrl: $.backPath + "/licenceInfo/findByPage",
        findByIdUrl: $.backPath + "/licenceInfo/findById/",
        uploadUrl: $.backPath + "/licenceInfo/upload",
        updateUrl: $.backPath + "/licenceInfo/update",
        verifyUrl: $.backPath + "/licenceInfo/verify",
        deleteUrl: $.backPath + "/licenceInfo/delete/",
        findEnterpriseUrl:$.backPath + "/licenceInfo/findEnterprise",
        findAllEnterpriseUrl: $.backPath + "/logisticst/findAllEnterprise",
        //数据字典
        findDicCodeUrl: $.backPath + '/licenceInfo/findDictByCode/151001'
       
    };
    
    //变量
    //资质类型
    var licenceType = "enterprise";
    var loadOnce = true;
    //数字字典
    var dicObj;
    
    //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
    
    
    //verify Upload  update 三个动作都使用同一个表单 设置变量以区分
    var actionName="upload";
    var actionArr = ["verify","upload","update"];
    var actionUrl = [urlPath.verifyUrl,urlPath.uploadUrl,urlPath.updateUrl];
  
    //初始化
    var _init = function(){
    	$("#ieuploadsfile_1").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,1);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(1, common4Licence.urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	$searchName.removeAttr("readonly");
		 userFlag = true;
    	//附件文件相关初始化
    	//初始化上传插件
    	uploadObject = common4Licence.upload_init($("#fileUploadEnterprise"));
    	//初始化上传ID值 该数组是放上传文件的id
    	fileUpValue_plugin = [];
    	
    	if(loadOnce){//第一次加载
    		/*var defaultTab = sessionStorage.getItem("active_li");
    	    if(defaultTab){
    	    	_showTabs(defaultTab);
    	    } else {
    	    	//默认执行第一个tab
    	        $("#licenceManage-tabs").find("li:eq(0)").find("a").click();
    	    }*/
    		
    		dicObj = $.getData(urlPath.findDicCodeUrl);
    		_initTable();
    		_bindClick();
    		loadOnce = !loadOnce;
    		/*//tabs切换调用事件
    		$licenceManageTabs.find('a').click(function () {
	            var $this = $(this);
	            var tabsName = $this.attr('href');
	            sessionStorage.setItem("active_li", tabsName);
	            _showTabs(tabsName);
	        });*/
    	}else{
    		_searchHandle();
    	}
    };
    
  //tabs切换事件函数
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
	        case '#enterprise':
	        	_init();
	        	break;
            case '#car':
            	carLicence.init();
                break;
            case '#person':
            	personLicence.init();
                break;
            case '#warn':
            	enterpriseWarn.init();
                break;
            default :
        }
    };
   
    
  //绑定事件
    var _bindClick = function() {
    	//搜索栏中上传按钮
    	$enterpriseUploadBtn.bind("click",_upload);
    	$enterpriseSearchBtn.bind("click",_searchHandle);
    	//审核表单中 radio切换事件
    	$verifyStatusRadio.bind("change",_rejectReason);
		// 表单事件绑定
    	$enterpriseForm.html5Validate(function() {
			_submitHandle();
			return false;
		});
    	 //违章查询按钮
    	$peccantSearchBtn1.bind("click",_searchPeccant);
    	$peccantSearchBtn2.bind("click",_searchPeccant);
    	$peccantSearchBtn3.bind("click",_searchPeccant);
	};
	//提交操作
	var _submitHandle = function(){
		var Filelen = $("#file_upload_1-queue>div").length;
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
	        $("#fileUpEnterprise").val(iefileidstring);
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
			$("#fileUpEnterprise").val(fileUpValue_plugin);
		}
		if(isIe){
			if( $('#showFileListMul_1').children().length == 0 && 
				$("#ie9_fileEnterprise").css("display") =="block"){
				Message.show({
					Msg : "请上传至少一个附件",
					isModal : false
				});
				return;
			}
		}else{
			if( $('#file_upload_1-queue').children().length == 0 && 
				$("#fileEnterprise").css("display") =="block"){
				Message.show({
					Msg : "请上传至少一个附件",
					isModal : false
				});
				return;
			}
		}
		
		//得到表单中的数据
	    /**
	     * 在base中 通过$.extend扩展
	     * packageFormData方法 得到调用该元素的jquery对象class=form-control的元素 name 和 value值
	     * 用$.each(data,function)遍历
	     */
		var formData = $enterpriseForm.packageFormData();
		formData['fileIds'] = $("#fileUpEnterprise").val();
		//判断是否是verify动作
		if(actionName == actionArr[0]){//是verify 则设置审核结果和不通过原因
			var verifyStatus = $("input[name='enterpriseRadio']:checked")[0].id;
			var rejectContent = $("#enterpriseContent").val();
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
		//ajax请求
		$.ajax({
			url : actionUrlPath,
			type : 'POST',
			data : formData
		}).done(function(json) {
			if (json.code === 1) {
				$enterprisePanel.closePanelModel();
				$enterpriseTable.jqGrid('setGridParam', {
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
	//跳转到违章查询页面
    var _searchPeccant = function() {
		window.open("http://jjzd.jxgaj.gov.cn/?from=singlemessage");
	}
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
    				url  : urlPath.deleteUrl+licenceType+'/' + id,
    				type : "delete",
    				async: false
    			}).done(function(json) {
                    if (json.code === 1) {
                    	$enterpriseTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        Message.alert({Msg: json.msg, isModal: false});
                    }
    			});
    		}
    	});
	};
	
    //查询
    var _searchHandle = function () {
    	$enterpriseTable.jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {
            		   "enterpriseName"     : $("#enterprise #enterpriseName-search").val(),
            		   "verifyStatus"     : $("#enterpriseStatus-search").val()
            		   
            },
            page    : 1
        }).trigger("reloadGrid");
    };
    //详情
    var _details = function(id){
    	$("#detailEnterpriseFiles_see").empty();
    	if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_fileEnterprise').addClass("hidden");
			$('form[target=multipatFile_1]').addClass("hidden");
			$("#fileUploadEnterprise_see").removeClass("hidden").appendTo("#enterpriseForm .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#fileEnterprise").addClass("hidden");
			$("#fileUploadEnterprise_see").removeClass("hidden").appendTo("#enterpriseForm .noie9filesmuliple");
		}
		
    	//获取相应数据
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	if (!rowObj || rowObj == ""){
    		return;
    	}
    	var verifyStatus = rowObj['verifyStatus'];
    	rowObj['verifyStatus'] = $.getDicNameByCode(verifyStatus, dicObj);
    	$enterprisePanel.showPanelModel('企业资质详情');
    	$enterprisePanel.saveOrCheck(false);
    	$enterpriseForm.setFormSingleObj(rowObj);
    	
    	_hiddenOrDisplay("detail");
    	if( rowObj['verifyStatus'] === "未通过"){
    		$(".forRejectReason").removeClass("hidden");
    	}else{
    		$(".forRejectReason").addClass("hidden");
    	}
    	
    	
    	//展示附件信息 详情
    	var target = "#detailEnterpriseFiles_see";
    	common4Licence.detailDisplay(rowObj,target);
    };
  //审核资质
    var _verify = function(id){
    	$("#detailEnterpriseFiles_see").empty();
		//$("#fileEnterprise").addClass("hidden");
    	if(isIe){
			//ie9上传文件控件隐藏
			$('#ie9_fileEnterprise').addClass("hidden");
			$('form[target=multipatFile_1]').addClass("hidden");
			$("#fileUploadEnterprise_see").removeClass("hidden").appendTo("#enterpriseForm .ie9filesmuliplebox");
		}else{
			//非ie9上传文件控件隐藏
			$("#fileEnterprise").addClass("hidden");
			$("#fileUploadEnterprise_see").removeClass("hidden").appendTo("#enterpriseForm .noie9filesmuliple");
		}

    	actionName = actionArr[0];
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	$enterprisePanel.showPanelModel('企业资质审核');
    	$enterprisePanel.saveOrCheck(false);
    	$enterpriseForm.setFormSingleObj(rowObj);
    	var $footer = $enterprisePanel.find(".panel-footer");
		$footer.removeClass("hidden");
    	_hiddenOrDisplay("verify");
    	_rejectReason();
    	
    	
    	//展示附件信息 详情
    	var target = "#detailEnterpriseFiles_see";
    	common4Licence.detailDisplay(rowObj,target);
    	
    };
    
    
  //上传资质
    var _upload = function(){
    	actionName = actionArr[1];
    	//自动带入部分字段
    	var data = $.getData(urlPath.findEnterpriseUrl);
    	if(data[0]){
    		$("#enterpriseName").val(data[0].enterpriseName);
        	$("#enterpriseName").attr("readonly","readonly");
        	$("#enterpriseBlackStatus").val(data[0].blackStatus);	
    	}
    	$enterprisePanel.showPanelModel('企业资质上传');
    	$enterprisePanel.saveOrCheck(true);
    	_hiddenOrDisplay("upload");
    	//重新设置最大上传数
    	uploadObject.settings("filesLen", 5);
    	//初始化上传ID值
    	fileUpValue_plugin = [];
    	$("#detailEnterpriseFiles_see").empty();
    	$("#fileUploadEnterprise_see").addClass("hidden");
    	if(isIe){
			//ie9上传文件控件显示
			$('#ie9_fileEnterprise').removeClass("hidden");
			$('form[target=multipatFile_1]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#fileEnterprise").removeClass("hidden");
	    	$("#fileEnterprise").css("display","block");
		}
		//清空上传数据记录
		$(".uploadify-queue").html("");
    };
    
  
    
    //修改资质
    var _update = function(id){
   	
    	actionName = actionArr[2];
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceType+'/'+id);
    	$enterprisePanel.showPanelModel('企业资质修改');
    	$enterprisePanel.saveOrCheck(true);

    	$("#enterpriseName").attr("readonly","readonly");
    	$enterpriseForm.setFormSingleObj(rowObj);
    	_hiddenOrDisplay("update");
    	
    	//修改初始化
    	$("#showFileListMul_1").empty();
		$('.uploadify-queue').empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_fileEnterprise').removeClass("hidden");
			$('form[target=multipatFile_1]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#fileEnterprise").removeClass("hidden");
		}
		$("#fileUploadEnterprise_see").addClass("hidden");
    	$("#enterprisePanel .uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - rowObj.fileIds.length;
		uploadObject.settings("filesLen", window.getOptionFileLen);
		for(var i = 0; i < rowObj.fileIds.length; i++){
			fileUpValue_plugin.push(rowObj.fileIds[i]);
		}
		//展示附件信息 修改
		var target = "#enterprisePanel .uploadify-queue";
		var ie9_target = "#showFileListMul_1";
    	common4Licence.updateDisplay(rowObj,editDeleFileOri,isIe,target,ie9_target);
		
    };
    
  
  //未通过原因展现与隐藏
    var _rejectReason = function(){
    	var verifyStatus = $("input[name='enterpriseRadio']:checked")[0].id;
		if (verifyStatus === '151001002') {
			$("#enterprisePanel #rejectRadio").removeClass("hidden");
		} else {
			$("#enterprisePanel #rejectRadio").addClass("hidden");
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
    	$enterpriseTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            colNames: ["企业名称", "资质名称", "证件到期时间", "发证机关", "黑名单","审核状态", "操作"],
            colModel: [
                {name: "enterpriseName", index: "1", align: "left", width: "30px", sortable: false,
                	formatter : function(cellValue,options, rowObject) {
						if(topMenuMag.isLogisticsEnterpriseUser() || topMenuMag.isChemicalEnterpriseUser() && userFlag){
							userFlag = false;
							$searchName.val(cellValue);
							$searchName.attr("readonly","readonly");
							
						}
						return cellValue;
					}
                },
                {name: "licenceName", index: "2", align: "center", width: "30px", sortable: false},
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
            pager: "#enterprisePager",
            gridComplete: function () {
                //表格中按钮权限配置
            	$.initPrivg("enterpriseTable");
            }
        }).resizeTableWidth();
    };
    
    //格式化操作按钮
    var _settingHandlerBtn = function(rowObject){
    	 var verifyStatus = rowObject['verifyStatus'];
         var id = rowObject['id'];
         if (verifyStatus === '151001001') {//待审核
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-verify" onclick="enterpriseLicence.verify(\'' + id + '\')" title="审核"><span class="img-ok"/></span>审核</label>'+
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-findById" onclick="enterpriseLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +    
             '</p>';
         } else if (verifyStatus === '151001002') {//未通过
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-update" onclick="enterpriseLicence.update(\'' + id + '\')" title="修改"><span class="img-edit"></span>修改</label>'+
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-delete" onclick="enterpriseLicence.del(\'' + id + '\')" title="删除"><span class="img-delete"></span>删除</label>'+
             '<span>&nbsp;&nbsp;</span>' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-findById" onclick="enterpriseLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         } else if (verifyStatus === '151001003') {//已通过
             return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-findById" onclick="enterpriseLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         } else if (verifyStatus === '151001004') {//已注销
        	 return '<p class="jqgrid-handle-p">' +
             '<label class="jqgrid-handle-text delete-link" data-func="enterpriseLicenceInfo-findById" onclick="enterpriseLicence.details(\'' + id + '\')" title="详情"><span class="img-details"></span>详情</label>' +      
             '</p>';
         }
    };
    
    //初始化
    _init();
    
    //返回接口
    return{
    	init   : _init,
    	details : _details,
    	update  : _update,
    	verify  : _verify,
    	del     : _del,
    	showTabs: _showTabs
    };
    
})(jQuery);

