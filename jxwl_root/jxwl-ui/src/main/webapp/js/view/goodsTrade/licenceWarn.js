/**
 * @description:资质备案管理-证件到期提醒
 * @author: wangwenhuan
 * @time: 2016-12-14
 */
var enterpriseWarn = (function ($) {
    var $warnTabs = $("#warn-tabs");
    // 表格
    var $enterpriseWarnTable = $("#enterpriseWarnTable");
    // 搜索栏
    var $enterpriseWarnSearchBtn = $("#enterpriseWarn-searchBtn");
    // 表单
    var $enterpriseWarnPanel = $("#enterpriseWarnPanel");
    var $enterpriseWarnForm = $("#enterpriseWarnForm");
    var $verifyStatusRadio = $("input[name='enterpriseRadio']");
    var $enterpriseWarnNameSearch = $("#enterpriseWarnName-search");
    // 请求路径
    var urlPath = {
        findByPageUrl: $.backPath + "/licenceWarn/findByPage",
        findByIdUrl: $.backPath + "/licenceInfo/findById/",
        reUploadUrl: $.backPath + "/licenceWarn/reUpload",
        amountUrl: $.backPath + "/licenceWarn/amount",
        findNameByNoUrl: $.backPath + "/logisticst/findNameByNo",
        // 数据字典
        findDicCodeUrl: $.backPath + '/licenceWarn/findDictByCode/151001'
    };

    // 变量
    var loadOnce = true;
    var licenceWarnType = "enterprise";
    var enterpriseId;
    var dicObj;
    userFlag =true;
  //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
    //用于table列显示用的变量
    var userInfo = $.getUserInfo();
    var roles = userInfo.roleCodes;
    var GOVERNMENT_USER ="e5348d777c2a48dd98cc7e19621d3193";
    var hideCol = ["handel"];
    var _init = function () {
    	$("#ieuploadsfile_4").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,4);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(4, common4Licence.urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	$enterpriseWarnNameSearch.removeAttr("readonly");
    	//计算过期资质总个数
    	common4Licence.amount(licenceWarnType);
    	common4Licence.amount("car");
    	common4Licence.amount("person");
    	//附件文件相关初始化
    	//初始化上传插件
    	uploadObject = common4Licence.upload_init($("#fileUploadEnterpriseWarn"));
    	//初始化上传ID值
    	fileUpValue_plugin = [];
        if (loadOnce) {
        	dicObj = $.getData(urlPath.findDicCodeUrl);
            _initTable();
            _bindClick();
            loadOnce = !loadOnce;
            // tab样式
            var bgColor = '#0A3561';
            var a_style = {
                'background-color': bgColor,
                'color': 'white',
                'border': 'none'
            };
            var li_style = {
                'background-color': bgColor,
                'border-color': 'white',
                'border-width': '1px'
            };
            var $firstA = $("#warn-tabs a").first();
            var bg_color = $firstA.css('background-color');
            // var borderColor = $firstA.css('border-color');
            var color = $firstA.css('color');
            // var boder = $firstA.css('border');

            $firstA.css(a_style);
            $firstA.parent().css(li_style);
            
            // tabs切换调用事件
            $warnTabs.find('a').click(function () {
                var $this = $(this);
                var tabsName = $this.attr('href');
                _showTabs(tabsName);
                sessionStorage.setItem("active_liTwo", tabsName);
                var $parent = $this.parent(); // li元素

                $this.css(a_style);
                $parent.css(li_style);
                var $siblings = $parent.siblings().find('a'); // 获取其他所有a元素
                $siblings.css({
                    'background-color': bg_color,
                    'color': color,
                    'border': 'none'
                }); // 将除自己以外的所有其他a元素背景还原回去
                $parent.siblings().css({
                    'background-color': bg_color,
                    'border-color': color
                }); // 背景还原

                // TODO
                // $("#accountVerify_circle").addClass("circleTwo");

            });
        } else {
            _searchHandle();
        }
    };

    // tabs切换事件
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
            case '#enterpriseWarn':
                _init();
                break;
            case '#carWarn':
                carWarn.init();
                break;
            case '#personWarn':
                personWarn.init();
                break;
            default :
        }
    };


    // 绑定事件
    var _bindClick = function () {
    	$enterpriseWarnSearchBtn.unbind("click").bind("click",_searchHandle);
        // 表单事件绑定
    	$enterpriseWarnForm.html5Validate(function () {
            _submitHandle();
            return false;
        });

    };
    //重新上传资质
    var _reUpload = function(id){
    	enterpriseId = id;
    	enterpriseName = $.getData(urlPath.findNameByNoUrl);
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceWarnType+'/'+id);
    	$enterpriseWarnPanel.showPanelModel('企业资质上传');
    	$enterpriseWarnPanel.saveOrCheck(true);
    	$("#enterpriseWarnPanel #enterpriseName").val(enterpriseName);
    	$("#enterpriseWarnPanel #enterpriseName").attr("readonly","readonly");
    	$enterpriseWarnForm.setFormSingleObj(rowObj);
    	
    	//修改初始化
    	$("#showFileListMul_4").empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_fileEnterpriseWarn').removeClass("hidden");
			$('form[target=multipatFile_4]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#fileEnterpriseWarn").removeClass("hidden");
		}
		$("#fileUploadEnterpriseWarn_see").addClass("hidden");
    	$("#enterpriseWarnPanel .uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - rowObj.fileIds.length;
		uploadObject.settings("filesLen", window.getOptionFileLen);
		
		
		//展示附件信息 修改
		var target = "#enterpriseWarnPanel .uploadify-queue";
    	var ie9_target = "#showFileListMul_4";
    	common4Licence.updateDisplay(rowObj,editDeleFileOri,isIe,target,ie9_target);
		
    };
    
    // 提交操作
    var _submitHandle = function () {
		if(isIe){
			// 附件id集合存放在隐藏域中 而且当新增的时候才使用
			var iefileid = [];
			var iefileidstring = "";
			var iefileLi = $("#showFileListMul_4 li");
			iefileLi.each(function(i){
				iefileid.push($(iefileLi[i]).attr("fileid"));
			});
			iefileidstring = iefileid.join(",");
			// 附件id集合存放在隐藏域中
	        $("#fileUpEnterpriseWarn").val(iefileidstring);
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
			$("#fileUpEnterpriseWarn").val(fileUpValue_plugin);
		}
        var formData = $enterpriseWarnForm.packageFormData();
        formData['fileIds'] = $("#fileUpEnterpriseWarn").val();
        formData['licenceType'] = licenceWarnType;
        formData['id'] = enterpriseId;
        $.ajax({
            url: urlPath.reUploadUrl,
            type: 'POST',
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
                $enterpriseWarnPanel.closePanelModel();
                $enterpriseWarnTable.jqGrid('setGridParam', {
                    postData: {},
                    page: 1
                }).trigger("reloadGrid");
                common4Licence.amount(licenceWarnType);
                enterpriseLicence.showTabs("#"+licenceWarnType);
            } else {
                Message.alert({
                    Msg: json.msg,
                    isModal: false
                });
            }
        });
    };
    // 查询
    var _searchHandle = function () {
        $enterpriseWarnTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {
                "licenceWarnType": licenceWarnType,
                "enterpriseName": $("#enterpriseWarnName-search").val(),
                "expiredStatus"	: $("#enterpriseWarnStatus-search").val()
                
            },
            page: 1
        }).trigger("reloadGrid");
    };

    

    // 初始化表格
    var _initTable = function () {
    	
        $enterpriseWarnTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            postData: {
                "licenceType": "enterprise",
                "enterpriseName": $("#enterpriseName-search").val()
            },
            colNames: ["企业名称", "资质名称", "证件到期时间", "发证机关", "黑名单", "审核状态", "操作"],
            colModel: [
                {name: "enterpriseName", index: "1", align: "left", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                			if(topMenuMag.isLogisticsEnterpriseUser() || topMenuMag.isChemicalEnterpriseUser() && userFlag){
                				userFlag = false;
                				$enterpriseWarnNameSearch.val(cellValue);
                				$enterpriseWarnSearchBtn.attr("readonly","readonly");
                				
                			}
                         return changeColor(rowObject, cellValue);
                     }	
                },
                {name: "licenceName", index: "2", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }
                },
                {
                    name: "expireDate", index: "3", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return changeColorWords(rowObject, cellValue);
                    }
                },
                {name: "licenceIssuingAuthority", index: "4", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }
                },
                {name: "blackStatus", index: "6", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }
                },
                {name: "verifyStatus", index: "6", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject,$.getDicNameByCode(cellValue, dicObj));
                    }
                },
                {name: "handel", index: "7", align: "center", width: "70px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        // 格式化操作按钮
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
            pager: "#enterpriseWarnPager",
            gridComplete: function () {
                // 表格中按钮权限配置
                $.initPrivg("enterpriseWarnTable");
                /*
                 * var ids = $enterpriseWarnTable.getDataIDs(); for(var i=0;i<ids.length;i++){
                 * var rowData = $enterpriseWarnTable.getRowData(ids[i]); if(
                 * rowData.expireFlag == "0"){
                 * $("#"+ids[i]).find("td").addCss("expired"); } }
                 */
            }
        }).resizeTableWidth();;
       //政府角色 隐藏操作列表
       if(roles.indexOf(GOVERNMENT_USER) != -1){
	       $enterpriseWarnTable.jqGrid('setGridParam', {
			}).jqGrid("hideCol", hideCol).trigger(
					"reloadGrid").setGridWidth($(window).width() - 280);
	   }
    };
    //改变颜色
    var changeColor = function (rowObject, cellVal) {
        if (rowObject.expireFlag == "0")
            return "<span style='color:#EB7073'>" + cellVal + "</span> ";
        else if (rowObject.expireFlag == "1") {
            return "<span style='color:rgb(144, 187, 45)'>" + cellVal + "</span>";
        }
        return cellVal;
    };
    //改变颜色 
    var changeColorWords = function (rowObject, cellVal) {
        if (rowObject.expireFlag == "0")
            return "<span style='color:#EB7073'>" + cellVal + "</span> &nbsp;&nbsp;&nbsp;<span>已过期</span>";
        else if (rowObject.expireFlag == "1") {
            return "<span style='color:rgb(144, 187, 45)'>" + cellVal + "</span>&nbsp;&nbsp;&nbsp;<span>即将过期</span>";
        }
        return cellVal;
    };

    // 格式化操作按钮
    var _settingHandlerBtn = function (rowObject) {
   	    var id = rowObject['id'];
        return '<p class="jqgrid-handle-p">' +
            '<label class="jqgrid-handle-text delete-link" data-func="enterpriseWarn-save" onclick="enterpriseWarn.reUpload(\'' + id + '\')" title="重新上传"><span class="img-ok"/></span>重新上传</label>' +
            '</p>';

    };

    // 返回接口
    return {
        init   : _init,
        reUpload : _reUpload
    };

})(jQuery);


/**
 * 车辆到期提醒
 */
var carWarn = (function ($) {
    // 表格
    var $carWarnTable = $("#carWarnTable");
    // 搜索栏
    var $carWarnSearchBtn = $("#carWarn-searchBtn");
    // 表单
    var $carWarnPanel = $("#carWarnPanel");
    var $carWarnForm = $("#carWarnForm");
    var $verifyStatusRadio = $("input[name='carRadio']");
    // 请求路径
    var urlPath = {
        findByPageUrl: $.backPath + "/licenceWarn/findByPage",
        findByIdUrl: $.backPath + "/licenceInfo/findById/",
        reUploadUrl: $.backPath + "/licenceWarn/reUpload",
        findNameByNoUrl: $.backPath + "/logisticst/findNameByNo",
        // 数据字典
        findDicCodeUrl: $.backPath + '/licenceWarn/findDictByCode/151001'
    };
    // 变量
    var loadOnce = true;
    var carId;
    var licenceWarnType = "car";
    
    var dicObj;
  //用于table列显示用的变量
    var userInfo = $.getUserInfo();
    var roles = userInfo.roleCodes;
    var GOVERNMENT_USER ="e5348d777c2a48dd98cc7e19621d3193";
    var hideCol = ["handel"];
  //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
    
    var _init = function () {
    	$("#ieuploadsfile_5").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,5);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(5, common4Licence.urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
     	common4Licence.amount(licenceWarnType);
     	//附件文件相关初始化
    	//初始化上传插件
    	uploadObject = common4Licence.upload_init($("#fileUploadCarWarn"));
    	//初始化上传ID值
    	fileUpValue_plugin = [];
        if (loadOnce) {
            dicObj = $.getData(urlPath.findDicCodeUrl);
            _initTable();
             _bindClick();
            loadOnce = !loadOnce;
        } else {
            _searchHandle();
        }
        var target = "#carWarnLicencePlateNo";
        carLicence.associatefunc(target);
        
    };
    // 绑定事件
    var _bindClick = function () {
    	$carWarnSearchBtn.unbind("click").bind("click",_searchHandle);
        // 表单事件绑定
        $carWarnForm.html5Validate(function () {
            _submitHandle();
            return false;
        });

    };
  //上传资质
    var _reUpload = function(id){
    	carId = id;
    	enterpriseName = $.getData(urlPath.findNameByNoUrl);
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceWarnType+'/'+id);
    	$carWarnPanel.showPanelModel('车辆资质上传');
    	$carWarnPanel.saveOrCheck(true);
    	$("#carWarnPanel #enterpriseName").val(enterpriseName);
    	$("#carWarnPanel #enterpriseName").attr("readonly","readonly");
    	$carWarnForm.setFormSingleObj(rowObj);
    	
    	//修改初始化
    	$("#showFileListMul_5").empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_fileCarWarn').removeClass("hidden");
			$('form[target=multipatFile_5]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#fileCarWarn").removeClass("hidden");
		}
		$("#fileUploadCarWarn_see").addClass("hidden");
    	$("#carWarnPanel .uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - rowObj.fileIds.length;
		uploadObject.settings("filesLen", window.getOptionFileLen);
		//展示附件信息 修改
		var target = "#carWarnPanel .uploadify-queue";
    	var ie9_target = "#showFileListMul_5";
    	common4Licence.updateDisplay(rowObj,editDeleFileOri,isIe,target,ie9_target);
    };
    // 提交操作
    var _submitHandle = function () {
		if(isIe){
			// 附件id集合存放在隐藏域中 而且当新增的时候才使用
			var iefileid = [];
			var iefileidstring = "";
			var iefileLi = $("#showFileListMul_5 li");
			iefileLi.each(function(i){
				iefileid.push($(iefileLi[i]).attr("fileid"));
			});
			iefileidstring = iefileid.join(",");
			// 附件id集合存放在隐藏域中
	        $("#fileUpCarWarn").val(iefileidstring);
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
			$("#fileUpCarWarn").val(fileUpValue_plugin);
		}
        var formData = $carWarnForm.packageFormData();
        formData['fileIds'] = $("#fileUpCarWarn").val();
        formData['licenceType'] = licenceWarnType;
        formData['id'] = carId;
        $.ajax({
            url: urlPath.reUploadUrl,
            type: 'POST',
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
                $carWarnPanel.closePanelModel();
                $carWarnTable.jqGrid('setGridParam', {
                    postData: {},
                    page: 1
                }).trigger("reloadGrid");
                common4Licence.amount(licenceWarnType);
                enterpriseLicence.showTabs("#"+licenceWarnType);
                
            } else {
                Message.alert({
                    Msg: json.msg,
                    isModal: false
                });
            }
        });
    };
    // 查询
    var _searchHandle = function () {
        $carWarnTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {
                "licenceWarnType": licenceWarnType,
                "enterpriseName": $("#enterpriseName-search").val(),
                "expiredStatus"	: $("#carWarnStatus-search").val(),
                "carNo"         : $("#carWarnName-search").val()
            },
            page: 1
        }).trigger("reloadGrid");
    };
    // 初始化表格
    var _initTable = function () {
        $carWarnTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            postData: {
                "licenceWarnType": "car",
                "enterpriseName": $("#enterpriseName-search").val()
            },
            colNames: ["车牌号", "资质名称", "所属物流企业名称", "证件到期时间", "发证机关", "黑名单", "审核状态", "操作"],
            colModel: [
                {name: "licencePlateNo", index: "1", align: "left", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }	
                },
                {name: "licenceName", index: "2", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }	
                },
                {name: "enterpriseName", index: "2", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }	
                },
                {name: "expireDate", index: "3", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         // 格式化操作按钮
                         return changeColorWords(rowObject,cellValue);
                     }	
                },
                {name: "licenceIssuingAuthority", index: "4", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }	
                },
                {name: "blackStatus", index: "6", align: "center", width: "30px", sortable: false,
                	 formatter: function (cellValue, options, rowObject) {
                         return changeColor(rowObject, cellValue);
                     }	
                },
                {name: "verifyStatus", index: "6", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject,$.getDicNameByCode(cellValue, dicObj));
                    }
                },
                {name: "handel", index: "7", align: "center", width: "70px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        // 格式化操作按钮
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
            pager: "#carWarnPager",
            gridComplete: function () {
                // 表格中按钮权限配置
                $.initPrivg();
            }
        }).resizeTableWidth();
        if(roles.indexOf(GOVERNMENT_USER) != -1){
 	       $carWarnTable.jqGrid('setGridParam', {
 			}).jqGrid("hideCol", hideCol).trigger(
 					"reloadGrid").setGridWidth($(window).width() - 280);
 	   }
    };
    var changeColor = function (rowObject, cellVal) {
        if (rowObject.expireFlag == "0")
            return "<span style='color:#EB7073'>" + cellVal + "</span> ";
        else if (rowObject.expireFlag == "1") {
            return "<span style='color:rgb(144, 187, 45)'>" + cellVal + "</span>";
        }
        return cellVal;
    };
    var changeColorWords = function (rowObject, cellVal) {
        if (rowObject.expireFlag == "0")
            return "<span style='color:#EB7073'>" + cellVal + "</span> &nbsp;&nbsp;&nbsp;<span>已过期</span>";
        else if (rowObject.expireFlag == "1") {
            return "<span style='color:rgb(144, 187, 45)'>" + cellVal + "</span>&nbsp;&nbsp;&nbsp;<span>即将过期</span>";
        }
        return cellVal;
    };

    // 格式化操作按钮
    var _settingHandlerBtn = function (rowObject) {
    	 var id = rowObject['id'];
        return '<p class="jqgrid-handle-p">' +
            '<label class="jqgrid-handle-text delete-link" data-func="carWarn-save" onclick="carWarn.reUpload(\'' + id + '\')" title="重新上传"><span class="img-ok"/></span>重新上传</label>&nbsp;&nbsp;&nbsp;&nbsp;' +
            '</p>';
    };
    // 返回接口
    return {
        init     : _init,
        reUpload : _reUpload
    };

})(jQuery);


/**
 * 人员到期提醒
 */
var personWarn = (function ($) {
    // 表格
    var $personWarnTable = $("#personWarnTable");
    // 搜索栏
    var $personWarnSearchBtn = $("#personWarn-searchBtn");
    // 表单
    var $personWarnPanel = $("#personWarnPanel");
    var $personWarnForm = $("#personWarnForm");
    var $verifyStatusRadio = $("input[name='personRadio']");
    // 请求路径
    var urlPath = {
        findByPageUrl: $.backPath + "/licenceWarn/findByPage",
        findByIdUrl: $.backPath + "/licenceInfo/findById/",
        reUploadUrl: $.backPath + "/licenceWarn/reUpload",
        findNameByNoUrl: $.backPath + "/logisticst/findNameByNo",
        // 数据字典
        findDicCodeUrl: $.backPath + '/licenceWarn/findDictByCode/005'
    };
    // 变量
    var licenceWarnType = "person";
    var personId;
    var loadOnce = true;
    var dicObj;
    
  //用于table列显示用的变量
    var userInfo = $.getUserInfo();
    var roles = userInfo.roleCodes;
    var GOVERNMENT_USER ="e5348d777c2a48dd98cc7e19621d3193";
    var hideCol = ["handel"];
    
  //附件文件相关变量
    var editDeleFileOri = [];
    var uploadObject;
    
    var _init = function () {
    	$("#ieuploadsfile_6").change(function(){
    		//验证附件
    		ie9fileUpload.ie9fileValidate(5,6);
    	});
    	/*上传附件后，upload请求后返回的信息*/
		ie9fileUpload.filemultipleform(6, common4Licence.urlPath.deleteFileUrl);//传入的是删除文件的url，不是上传文件url
		
		userAgent = navigator.userAgent;
	    isIe = userAgent.indexOf("MSIE 9.0") != -1;
    	//附件文件相关初始化
    	//初始化上传插件
    	uploadObject = common4Licence.upload_init($("#fileUploadPersonWarn"));
    	//初始化上传ID值
    	fileUpValue_plugin = [];
    	
        if (loadOnce) {
            _initTable();
            dicObj = $.getData(urlPath.findDicCodeUrl);
            _bindClick();
            loadOnce = !loadOnce;
        } else {
            _searchHandle();
        }
        var target = "#personNameWarn";
        personLicence.associatefunc(target);
    };
    // 绑定事件
    var _bindClick = function () {
    	$personWarnSearchBtn.unbind("click").bind("click",_searchHandle);
        // 表单事件绑定
        $personWarnForm.html5Validate(function () {
            _submitHandle();
            return false;
        });

    };
    //重新上传资质
    var _reUpload = function(id){
    	enterpriseId = id;
    	enterpriseName = $.getData(urlPath.findNameByNoUrl);
    	var rowObj = $.getData(urlPath.findByIdUrl + licenceWarnType+'/'+id);
    	$personWarnPanel.showPanelModel('企业资质上传');
    	$personWarnPanel.saveOrCheck(true);
    	
    	var verifyStatus = rowObj['verifyStatus'];
    	rowObj['verifyStatus'] = $.getDicNameByCode(verifyStatus, dicObj["151001"]);
    	var occupationPersonType = rowObj['occupationPersonType'];
    	rowObj['occupationPersonType'] =  $.getDicNameByCode(occupationPersonType, dicObj["100001"]);
    	
    	$("#personWarnPanel #enterpriseName").val(enterpriseName);
    	$("#personWarnPanel #enterpriseName").attr("readonly","readonly");
    	$personWarnForm.setFormSingleObj(rowObj);
    	//修改初始化
    	$("#showFileListMul_6").empty();
		if(isIe){
			//ie9上传文件控件显示
			$('#ie9_filePersonWarn').removeClass("hidden");
			$('form[target=multipatFile_6]').removeClass("hidden");
		}else{
			//非ie9上传文件控件显示
			$("#filePersonWarn").removeClass("hidden");
		}
		$("#fileUploadPersonWarn_see").addClass("hidden");
    	$("#personWarnPanel .uploadify-queue").html("");
		fileUpValue_plugin = [];
		filteredFiles = []; // 文件过滤
		editDeleFileOri = [];
		window.getOptionFileLen = 5 - rowObj.fileIds.length;
		uploadObject.settings("filesLen", window.getOptionFileLen);
		//展示附件信息 修改
		var target = "#personWarnPanel .uploadify-queue";
    	var ie9_target = "#showFileListMul_6";
    	common4Licence.updateDisplay(rowObj,editDeleFileOri,isIe,target,ie9_target);
    };
    
    // 提交操作
    var _submitHandle = function () {
		if(isIe){
			// 附件id集合存放在隐藏域中 而且当新增的时候才使用
			var iefileid = [];
			var iefileidstring = "";
			var iefileLi = $("#showFileListMul_6 li");
			iefileLi.each(function(i){
				iefileid.push($(iefileLi[i]).attr("fileid"));
			});
			iefileidstring = iefileid.join(",");
			// 附件id集合存放在隐藏域中
	        $("#fileUpPersonWarn").val(iefileidstring);
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
			$("#fileUpPersonWarn").val(fileUpValue_plugin);
		}
        var formData = $personWarnForm.packageFormData();
        formData['fileIds'] = $("#fileUpPersonWarn").val();
        formData['licenceType'] = licenceWarnType;
        $.ajax({
            url: urlPath.reUploadUrl,
            type: 'POST',
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
                $personWarnPanel.closePanelModel();
                $personWarnTable.jqGrid('setGridParam', {
                    postData: {},
                    page: 1
                }).trigger("reloadGrid");
                common4Licence.amount(licenceWarnType);
                enterpriseLicence.showTabs("#"+licenceWarnType);
            } else {
                Message.alert({
                    Msg: json.msg,
                    isModal: false
                });
            }
        });
    };
    // 查询
    var _searchHandle = function () {
        $personWarnTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {
                "licenceWarnType": licenceWarnType,
                "personName"    : $("#personWarnPersonName-search").val(),
                "expiredStatus" : $("#personWarnStatus-search").val()
            },
            page: 1
        }).trigger("reloadGrid");
    };
    // 初始化表格
    var _initTable = function () {
        $personWarnTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            postData: {
                "licenceWarnType": "person",
                "enterpriseName": $("#enterpriseName-search").val()
            },
            colNames: ["姓名", "从业资格类别", "资质名称", "所属物流企业名称", "证件到期时间", "发证机关", "黑名单", "审核状态", "操作"],
            colModel: [
                {name: "personName", index: "1", align: "left", width: "30px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject, cellValue);
                    }	
                },
                {
                    name: "occupationPersonType", index: "1", align: "left", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject,$.getDicNameByCode(cellValue, dicObj['100001']));
                    }
                },
                {name: "licenceName", index: "2", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject, cellValue);
                    }	
                },
                {name: "enterpriseName", index: "2", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject, cellValue);
                    }	
                },
                {name: "expireDate", index: "3", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        // 格式化操作按钮
                        return changeColorWords(rowObject,cellValue);
                    }

                },
                {name: "licenceIssuingAuthority", index: "4", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject, cellValue);
                    }	
                },
                {name: "blackStatus", index: "6", align: "center", width: "30px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject, cellValue);
                    }	
                },
                {
                    name: "verifyStatus", index: "6", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return changeColor(rowObject,$.getDicNameByCode(cellValue, dicObj['151001']));
                    }
                },
                {
                    name: "handel", index: "7", align: "center", width: "70px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        // 格式化操作按钮
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
            pager: "#personWarnPager",
            gridComplete: function () {
                // 表格中按钮权限配置
                $.initPrivg();
            }
        }).resizeTableWidth();
        if(roles.indexOf(GOVERNMENT_USER) != -1){
 	       $personWarnTable.jqGrid('setGridParam', {
 			}).jqGrid("hideCol", hideCol).trigger(
 					"reloadGrid").setGridWidth($(window).width() - 280);
 	   }
    };
    var changeColor = function (rowObject, cellVal) {
        if (rowObject.expireFlag == "0")
            return "<span style='color:#EB7073'>" + cellVal + "</span> ";
        else if (rowObject.expireFlag == "1") {
            return "<span style='color:rgb(144, 187, 45)'>" + cellVal + "</span>";
        }
        return cellVal;
    };
    var changeColorWords = function (rowObject, cellVal) {
        if (rowObject.expireFlag == "0")
            return "<span style='color:#EB7073'>" + cellVal + "</span> &nbsp;&nbsp;&nbsp;<span>已过期</span>";
        else if (rowObject.expireFlag == "1") {
            return "<span style='color:rgb(144, 187, 45)'>" + cellVal + "</span>&nbsp;&nbsp;&nbsp;<span>即将过期</span>";
        }
        return cellVal;
    };
    // 格式化操作按钮
    var _settingHandlerBtn = function (rowObject) {
   	    var id = rowObject['id'];
        return '<p class="jqgrid-handle-p">' +
            '<label class="jqgrid-handle-text delete-link" data-func="personWarn-save" onclick="personWarn.reUpload(\'' + id + '\')" title="重新上传"><span class="img-ok"/></span>重新上传</label>&nbsp;&nbsp;&nbsp;&nbsp;' +
            '</p>';

    };
    // 返回接口
    return {
        init     : _init,
        reUpload : _reUpload
    };
})(jQuery);




