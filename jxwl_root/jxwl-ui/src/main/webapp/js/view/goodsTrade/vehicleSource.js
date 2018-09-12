/**
 * @description:交易大厅-车源信息
 * @author: xiaoqx
 * @time: 2016-7-19
 */
//车源信息
var vehicleSource = (function ($) {
    //模块缓存变量
    var $dangerCarTable = $('#vehicleSourceTable');
    var $carSearchBtn= $("#car-searchBtn");
    var $carExport=$("#car-export");
    var $addCarInfor = $("#car-addBtn");
    var $saveOrUpdateCarInforPanel = $("#dangercarinfor-panel");
    var $dangercarinforForm = $("#dangercarinfor-form");
    var $previewDangercarFilePanel = $("#previewDangercarFile-panel");
    var $accStatus = $("#acc-status");
    var $crossType = $("#cross-type");
    var $searchName = $("#enterpriseName-vehicleSource");
	var userFlag = true;
    //请求路径
    var urlPath = {
        findByPageUrl: $.backPath + '/vehicleSource/findByPage',
    	updateUrl:$.backPath + '/vehicleSource/update/',
    	saveUrl:$.backPath + '/vehicleSource/save',
        findByIdUrl: $.backPath + '/vehicleSource/findById/',
        deleteUrl: $.backPath + '/vehicleSource/delete/',
        exportUrl: $.backPath + '/vehicleSource/export',
        findDictByCodeUrl: $.backPath + "/vehicleSource/findDictByCode/001",
        uploadFileUrl: $.backPath + '/vehicleSource/uploadFile',
        deleteFileUrl: $.backPath + '/vehicleSource/deleteFile',
        downloadFileUrl: $.backPath + "/vehicleSource/downloadFile/",
        findNameByConporateNo: $.backPath + "/vehicleSource/findNameByConporateNo",
        findCarrierNameUrl: $.backPath + "/dangerVehicle/findCarrierName",
        findByCarrierName:  $.backPath + "/dangerVehicle/findByCarrierName"
    };
    var loadOnce = false;
    //数据初始化
    var _init = function () {
    	$("#ieuploadsfile_1").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,100);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(1, urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	 $searchName.removeAttr("readonly");
		 userFlag = true;
    	 if (!loadOnce) {
    		//下拉列表
    	        dicObj = $.getData(urlPath.findDictByCodeUrl);
    	        $("#acc-status").settingsOptions(dicObj["102001"], false);
    	        $("#cross-type").settingsOptions(dicObj["105001"], false);
    	        $("#vehicleType-add").settingsOptions(dicObj["109001"], false);
    	        $("#crossType-add").settingsOptions(dicObj["105001"], false);
    	    	//第一个tab标签 要先解除绑定在进行绑定事件
    	    	 $carExport.unbind("click").bind("click",_export);
    	         $carSearchBtn.unbind("click").bind("click",_searchHandler);
    	         $addCarInfor.unbind("click").bind("click",_addCarInfor);
    	         $accStatus.unbind("change").bind("change",_searchHandler);
    	         $crossType.unbind("change").bind("change",_searchHandler);
    	         _initTable();
    	        //表单事件绑定
    	        $dangercarinforForm.html5Validate(function () {
    	        	_submitHandle();
    	            return false;
    	        });
    	        _upload_1($("#fileUpload_0"));
//    	        $(".noie9filesmuliple").hide();
//    			$(".ie9filesmuliplebox").hide();
    	        
    	        loadOnce = !loadOnce;
    	        //回车搜索
    	        $("#dangerCar-search input").keyup(function (e) {
    	            if (e.keyCode === 13) {
    	            	_searchHandler();
    	            }
    	        });
    	        _associatefunc("#licencePlateNo-add");//车牌号联想搜索
    	 }else{
    		 _searchHandler();
    	 }
    	
    };
    
    
    //变量
    var flag,uploadObj,editDeleteFile = "",editDeleFileOri = [],VEHICLE_ID;
    var userInfor = JSON.parse(sessionStorage.getItem("userInfo"));
    
    // 车牌号联想框
	var _associatefunc = function(target) {
		// 初始化联想下拉框
		var config = {};
		config.inputObj = target;
		config.url = urlPath.findCarrierNameUrl;//搜索本企业下的所有车牌号
		associateObj = $.associate(config);
		// 重写联想下拉框getData方法
		associateObj.getData = function(val, obj) {
			var list = $.getData(urlPath.findCarrierNameUrl, "POST", {//搜索本企业下的所有车牌号
				"carrierName" : val
			});
			if (list && list.length > 0) {
				obj.setData(list);
				obj.associateDiv.show();
				obj.associateDiv.empty();
				for ( var i = 0; i < list.length && obj.showCnt; i++) {
					var str = "<div style='cursor:pointer;padding:2px;10px;'>"
							+ list[i] + "</div>";
					obj.associateDiv.append(str);
				}
			} else {
				obj.associateDiv.empty();
				_readOnlyFalseOrder();
				$("#dangercarinfor-form").find("input[id!=licencePlateNo-add]").val("");
				/* 清空单元格 */
				$("#vehicleType-add").val("");
				$("#crossType-add").val("");
				$("#transportRoute-add").val("");
			}
		};
		// 重写联想下拉框selectData方法
		associateObj.selectData = function(data, divObj, obj) {
			var name = divObj.text();
			if (data && data.name) {
				name = data.name;
			}
			$(obj.inputObj).val(name);
			var existOrder = $.getData(urlPath.findByCarrierName, "POST", {
				"carrierName" : name
			});
			if (existOrder) {
				$("#dangercarinfor-form").setFormSingleObj(existOrder);
				_readOnlyOrder();
				VEHICLE_ID = existOrder.id;
				//填充车源对应的车辆的附件信息
				editDeleFileOri = [];
		        var fileIds = [];
		        var fileNames = [];
		        if (existOrder.dangerFileRela.length) {
		        	for(var i = 0, len = existOrder.dangerFileRela.length; i < len; i++){
		        		fileIds.push(existOrder.dangerFileRela[i].fileId);
		        		fileNames.push(existOrder.dangerFileRela[i].fileName);
		        	}
		            for (var i = 0; i < fileIds.length; i++) {
		            	var content = '<li fileID='+fileIds[i]+'>' + 
						  		'<span class="iefilename">'+fileNames[i] + '</span>'+
						  		'<a href="javascript:void(0);" class="delfilebtn" onclick="ie9fileUpload.ie9fileDeleteEdit(this,\''+urlPath.deleteFileUrl+'\')">&nbsp;&nbsp;x</a></li>';
						if(isIe){
							//ie9填充已有的文件列表
							$("#showFileListMul_1").append(content);
						}else{
							//非ie9填充已有的文件列表
							$('.uploadify-queue').append(content);
						}
		                editDeleFileOri.push(fileIds[i]);
		            }
		        }
			}
		};
	};
	
	// 设置车辆信息可编辑
	var _readOnlyFalseOrder = function() {
		$("#vehicleBrand-add").attr("readonly", false);
		$("#vehicleBrandType-add").attr("readonly", false);
		$("#vehicleOutput-add").attr("readonly", false);
		$("#motorNo-add").attr("readonly", false);
		$("#graduadedNo-add").attr("readonly", false);
		$("#vehicleColor-add").attr("readonly", false);
		$("#vehicleType-add").attr("disabled",false);
		$("#crossType-add").attr("disabled",false);
	};

	// 设置车辆信息不可编辑
	var _readOnlyOrder = function() {
		$("#vehicleBrand-add").attr("readonly", "readonly");
		$("#vehicleBrandType-add").attr("readonly", "readonly");
		$("#vehicleOutput-add").attr("readonly", "readonly");
		$("#motorNo-add").attr("readonly", "readonly");
		$("#graduadedNo-add").attr("readonly", "readonly");
		$("#vehicleColor-add").attr("readonly", "readonly");
		$("#vehicleType-add").attr("disabled",true);
		$("#crossType-add").attr("disabled",true);
	};
	
    //表格
    var _initTable = function () {
        $dangerCarTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            colNames: ["企业名称","车牌号","车辆类型","运输线路", "可运输货物类别和数量", "最大载重", "联系方式", "发布日期", "跨域类型", "操作"],
            colModel: [
                {name: "enterpriseName", index: "6", align: "center", width: "30px", sortable: false,
                	formatter : function(cellValue,options, rowObject) {
						if(topMenuMag.isLogisticsEnterpriseUser() || topMenuMag.isChemicalEnterpriseUser() && userFlag){
							userFlag = false;
							$searchName.val(cellValue);
							$searchName.attr("readonly","readonly");
							
						}
						return cellValue;
					}
                },
                {name: "licencePlateNo", index: "1", align: "center", width: "30px", sortable: false},
                {name: "vehicleType", index: "1", align: "center", width: "30px", sortable: false},
                {name: "transportRoute", index: "2", align: "center", width: "30px", sortable: false},
                {name: "", index: "2", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject){
                		var s = '';
                		if(rowObject.goodstype != null && rowObject.quantity != null){
                			var types =     rowObject.goodstype.split(',');
                    		var quantitys = rowObject.quantity.split(',');
                    		for(var i = 0; i < types.length; i++){
                    			if(types[i] != '' || quantitys[i] != ''){
                    				s += types[i] + ':' + quantitys[i] + '吨； ';
                    			}
                    		}
                    		//return types[0]+':'+quantitys[0]+'吨； '+types[1]+':'+quantitys[1]+'吨； '+types[2]+':'+quantitys[2]+'吨 ';
                		}else{
                			return "";
                		}
                		return s;
                	}
                },
                {name: "maxLoadWeight", index: "2", align: "center", width: "20px", sortable: false},
                {name: "contactPhone", index: "2", align: "center", width: "30px", sortable: false},
                {name: "publishDate", index: "2", align: "center", width: "30px", sortable: false},
                {name: "crossDomainType", index: "7", align: "center", width: "30px", sortable: false},
                { name: "handel", index: "9", align: "center", width: "58px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                    	var id = rowObject.id;
                        var handlerTemp =
                            '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text delete-link" data-func="vehicleSource-update" onclick="vehicleSource.editCarInfor(\'' + id + '\')"><span class="img-edit"></span>修改</label>' +
                            '<span>&nbsp;&nbsp;</span>' +
                            '<label class="jqgrid-handle-text delete-link" data-func="vehicleSource-findById" onclick="vehicleSource.detailCarInfor(\'' + id + '\')"><span class="img-details"></span>详情</label>' +
                            '<span>&nbsp;&nbsp;</span>' +
                            '<label class="jqgrid-handle-text delete-link" data-func="vehicleSource-delete" onclick="vehicleSource.deleteCarInfor(\'' + id + '\')"><span class="img-delete"></span>删除</label>' +
                            '</p>';
                        return handlerTemp;
                    }
                }
            ],
            loadonce: false,
            rownumbers:true,
            viewrecords: true,
            autowidth: true,
            height: true,
            rowNum: 10,
            rowList: [5, 10, 15],
            pager: "#vehicleSourcePage",
            gridComplete:function(){
            	//配置权限
            	 $.initPrivg("vehicleSourceTable");
            }
        }).resizeTableWidth();
    };
    
    //删除
    var _deleteCarInfor = function(id){
    	Message.confirm({
            Msg: $.msg.sureDelete,
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.deleteUrl + id,
                    type: 'delete',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	$dangerCarTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        //$.validateTip(json.code);
                        Message.alert({Msg: json.msg, isModal: false});
                    }
                });
            }
        });
    };
    
  //新增
    var _addCarInfor = function(){
//    	$(".noie9filesmuliple").hide();
//		$(".ie9filesmuliplebox").hide();
    	$('#publishDateAdd').addClass("hidden");
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
    	$saveOrUpdateCarInforPanel.showPanelModel('新增车源');
    	$saveOrUpdateCarInforPanel.saveOrCheck(true);
    	_readOnlyFalseOrder();
    	$(".detailShow").removeClass("hidden");
    	$(".detailShow").find("span").removeClass("hidden");
    	$("#carSourceName").html(userInfor.name);
    	$("#carSourceNo").html(userInfor.corporateCode);
    	
    	fileUpValue_plugin = [];//上传的文件的id集合
    	uploadManager.filteredFiles = []; //文件过滤
    	$(".uploadify-queue").html("");
	   	 $.ajax({
	         url: urlPath.findNameByConporateNo,
	         dataType: 'json',
	         type: "POST",
	         success: function (data) {
	            $("#carSourceName").text(data.data);
	         }
		 });
    };
    
  //编辑
	var _editCarInfor = function(ids){
//		$(".noie9filesmuliple").hide();
//		$(".ie9filesmuliplebox").hide();
		$('#publishDateAdd').addClass("hidden");
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
        if(!updateObj){
        	return;
        }
        $saveOrUpdateCarInforPanel.saveOrCheck(true);
        $saveOrUpdateCarInforPanel.showPanelModel('编辑车源').setFormSingleObj(updateObj);
        _readOnlyOrder();
        var goodstype = updateObj.goodstype.split(",");
        var quantity  = updateObj.quantity.split(",");
        var arr = $dangercarinforForm.find('.goodstype_and_quantity input.form-control');
        $(arr[0]).val(goodstype[0]);
        $(arr[1]).val( quantity[0]);
        $(arr[2]).val(goodstype[1]);
        $(arr[3]).val( quantity[1]);
        $(arr[4]).val(goodstype[2]);
        $(arr[5]).val( quantity[2]);
        $(".detailShow").removeClass("hidden");
    	$(".detailShow").find("span").removeClass("hidden");
    	//$("#carSourceName").html(userInfor.name);
    	$("#carSourceNo").html(userInfor.corporateCode);
        // 编辑时候对附件的处理
        $(".uploadify-queue").html("");
        fileUpValue_plugin = [];
        filteredFiles = []; // 文件过滤
        editDeleFileOri = [];
        var fileIds = [];
        var fileNames = [];
        if (updateObj.dangerFileRela.length) {
        	for(var i = 0, len = updateObj.dangerFileRela.length; i < len; i++){
        		fileIds.push(updateObj.dangerFileRela[i].fileId);
        		fileNames.push(updateObj.dangerFileRela[i].fileName);
        	}
            for (var i = 0; i < fileIds.length; i++) {
            	var content = '<li fileID='+fileIds[i]+'>' + 
				  		'<span class="iefilename">'+fileNames[i] + '</span>'+
				  		'<a href="javascript:void(0);" class="delfilebtn" onclick="ie9fileUpload.ie9fileDeleteEdit(this,\''+urlPath.deleteFileUrl+'\')">&nbsp;&nbsp;x</a></li>';
				if(isIe){
					//ie9填充已有的文件列表
					$("#showFileListMul_1").append(content);
				}else{
					//非ie9填充已有的文件列表
					$('.uploadify-queue').append(content);
				}
                editDeleFileOri.push(fileIds[i]);
            }
        }
	};
	
	

    //详情
    var _detailCarInfor = function(ids){
    	$('#publishDateAdd').removeClass("hidden");
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
        $("#detailCarInfoFiles_see").empty();
        $saveOrUpdateCarInforPanel.saveOrCheck(false);
        $saveOrUpdateCarInforPanel.showPanelModel('详情');
        $dangercarinforForm.setFormSingleObj(rowObj);
        var goodstype = rowObj.goodstype.split(",");
        var quantity  = rowObj.quantity.split(",");
        var arr = $dangercarinforForm.find('.goodstype_and_quantity span.look-formPanel');
        $(arr[0]).text(goodstype[0]);
        $(arr[1]).text( quantity[0]);
        $(arr[2]).text(goodstype[1]);
        $(arr[3]).text( quantity[1]);
        $(arr[4]).text(goodstype[2]);
        $(arr[5]).text( quantity[2]);
        if(rowObj.vehicleType){
        	$("#vehicleTypeStr").text($.getDicNameByCode(rowObj.vehicleType, dicObj["109001"]));
        }
        if(rowObj.crossDomainType){
        	$("#crossDomainTypeStr").text($.getDicNameByCode(rowObj.crossDomainType, dicObj["105001"]));
        }
        if(rowObj.status){
        	$("#statusStr").text($.getDicNameByCode(rowObj.status, dicObj["102001"]));
        }
        var fileIds = [];
        var fileNames = [];
        if (rowObj.dangerFileRela.length) {
            for(var i = 0, len = rowObj.dangerFileRela.length; i < len; i++){
                fileIds.push(rowObj.dangerFileRela[i].fileId);
                fileNames.push(rowObj.dangerFileRela[i].fileName);
            }
            for (var i = 0; i < fileIds.length; i++) {
            	var docType =  fileNames[i].split(".")[1];
            	 var id ;
            	if((docType =="jpg") ||(docType =="JPG")||(docType =="png")||(docType == "PNG")){
            		id = fileIds[i];
                    var fileDel = '<div class="fileDowloand"><small>' + fileNames[i] + '</small>'+
                    				'<a class="" href="' + urlPath.downloadFileUrl + id + '" download=fileIds[i]>'+
                    				'<span class="img-download"></span></a><span class="img-priview" onclick="vehicleSource.priviewFile(\''+id+'\');">'+
                    				'</span></div>';
            	}else{
            		id = fileIds[i];
                    var fileDel = '<div class="fileDowloand"><small>' + fileNames[i] + '</small>'+
                    				'<a class="" href="' + urlPath.downloadFileUrl + id + '" download=fileIds[i]>'+
                    				'<span class="img-download"></span> </a>'+
                    				'</div>';
            	}
                $("#detailCarInfoFiles_see").append(fileDel);
            }
        }
    };
    
    //预览图片
    var _priviewFile = function(id){
    	$previewDangercarFilePanel.showPanelModel('车辆照片');
    	$previewDangercarFilePanel.find(".panel-close").bind("click",function(){
    		$previewDangercarFilePanel.fadeOut(300);
	    	$(".panel-mask").css("z-index","1000");
		});
    	$("#dangercarFile").attr("src",urlPath.downloadFileUrl + id);
    };
    
	//提交
	var _submitHandle = function(){
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
//			var ids = $('#fileUpload_0 li').attr('fileid');
//			$("#fileUp").val(ids);
		}
		
		/*var formData = $dangercarinforForm.packageFormData(); //packageFormData方法存在的问题是新增和编辑时无法把goodstype和quantity的值传到后台保存！
		var goodstype = $dangercarinforForm.find('.goodstype_and_quantity').find('input[name=goodstype]');
		var quantity =  $dangercarinforForm.find('.goodstype_and_quantity').find('input[name=quantity]');
		var arr1 = [], arr2 = [];
		for(var i=0;i<goodstype.length;i++){
			if(goodstype[i].val() !=""){
				arr1.push(goodstype[i].val()); 
			}
		}
		for(var i=0;i<quantity.length;i++){
			if(quantity[i].val() != ""){
				arr2.push(quantity[i].val()); 
			}
		}
		formData['goodstype'] = arr1.join(",");
		formData['quantity']  = arr2.join(",");
		formData['fileIds'] = $("#fileUp").val();
		formData['id'] = VEHICLE_ID;*/
		var formData = $dangercarinforForm.serialize();
		//将原有的fileIds删除掉，保证提交到后台只有一个fileIds而不是两个！否则导致地图资料卡查询车辆信息时车辆附件查询查出两个附件id而不是一个，跟接口定义(返回单个附件id)冲突，后台直接报错
		formData = formData.replace(/&fileIds=.*/, '');
		formData += '&fileIds=' + $("#fileUp").val() + '&id=' + VEHICLE_ID;
		var url = flag ? urlPath.updateUrl + flag: urlPath.saveUrl;
        $.ajax({
            url: url,
            type: 'POST',
            async: false,
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
            	$saveOrUpdateCarInforPanel.closePanelModel();
            	$dangerCarTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
            } else {
                //$.validateTip(json.code);
                Message.alert({Msg: json.msg, isModal: false});
                $dangerCarTable.trigger("reloadGrid");
            }
        });
	};

  //上传插件初始化
	var _upload_1 = function (fileUpload) {
        if (uploadObj) {
            uploadObj.destroy();
        }
        uploadObj = fileUpload.Huploadify({
            auto: true,
            fileTypeExts: "*.png;*.jpg;*.jpeg;*.PNG;*.JPG;*.JPEG;*.bmp;*.BMP",
            multi: true,
            deleteURL: urlPath.deleteFileUrl,
            fileSizeLimit: 10240,// 10M
            showUploadedPercent: true,
            showUploadedSize: true,
            removeTimeout: 9999999,
            uploader: urlPath.uploadFileUrl,
            filesLen: 100
        });
    };
    
    //查询数据信息
    var _searchHandler = function () {
        $dangerCarTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {
            	"enterpriseName": $("#enterpriseName-vehicleSource").val(),
            	"licenseNo": $("#licenseNo-search").val(),
                "crossDomainType":$("#cross-type").val()
            },
            page: 1
        }).trigger("reloadGrid");
    };

    //导出
    var _export = function(){
        var vehicleStatusVal = $("#acc-status").val();
    	var crossDomainTypeVal = $("#cross-type").val();
    	window.location.href = urlPath.exportUrl + "?status=" + vehicleStatusVal + "&crossDomainType=" + crossDomainTypeVal;
    };
    
    //返回函数
    return {
    	init: _init,
    	editCarInfor: _editCarInfor,
    	detailCarInfor:_detailCarInfor,
    	priviewFile:_priviewFile,
    	deleteCarInfor: _deleteCarInfor
    };
})(jQuery);

