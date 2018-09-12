/**
 * @description:报警管理
 * @author: guqh
 * @time: 2016-7-26
 */

//报警管理信息
var alarmManageInfo = (function ($) {
	//处理状态
	var $alarmDealStatusAdd=$("#alarmDealStatus_add");
	//查询条件
	var $serachtartTime = $("#serach_startTime");
	var $searchEndTime = $("#serach_endTime");
    //模块变量
    var $alarmManageSearchBtn =$("#alarmManage-searchBtn");
    var $alarmManageExport = $("#alarmManage-export");
    var $inforMagTabs = $('#inforMag-tabs');
    var $alarmManageTable = $("#alarmManageTable");
    
    //var $alarmManageDealBtn =$("#alarmManage-dealBtn");
    var $alarmManageBeforeSetBtn =$("#alarmManage-before-setBtn");
    
    var $alarmManagePanel = $("#alarmManage-panel");
    var $alarmManageForm = $("#alarmManage-form");
    var $alarmDetailsPanel = $("#alarmDetails-panel");
    var $alarmDetailsForm = $("#alarmDetails-form");
    
    //搜索栏
    var $dealStatus = $("#dealStatus-search-name");
    
    var loadOne = false;
    var flag;
    var alarmId;
    //请求路径
    var urlPath = {
        findByPageUrlForAlarmManage: $.backPath + '/alarmManage/findByPage',
        findByIdUrl: $.backPath + '/alarmManage/findById/',
        saveUrl: $.backPath + '/alarmManage/save/',
        updateUrl: $.backPath + '/alarmManage/update/',
        deleteAlarmInfoUrl: $.backPath + '/alarmManage/deleteAlarmInfo/',
        findDictByCodeUrl: $.backPath + "/alarmManage/findDictByCode/103002",
        findDictTypeByCodeUrl: $.backPath + "/alarmManage/findDictByCode/103001",
        exportUrl: $.backPath + '',
        findThresholdByIdUrl:$.backPath + '/alarmManage/findThresholdById',
        updateThresholdUrl:$.backPath + '/alarmManage/updateThreshold/'
    };
    //数据初始化
    var _init = function () {
    	//第一个tab标签 要先解除绑定在进行绑定事件
    	$alarmManageExport.unbind("click").bind("click",_export);
    	$alarmManageSearchBtn.unbind("click").bind("click",_searchHandler);
    	//$alarmManageDealBtn.unbind("click").bind("click",_dealAlarmManage);
    	$alarmManageBeforeSetBtn.unbind("click").bind("click",_setAlarmManage);
    	//每隔三十天清除一次数据库报警信息
		//window.setInterval(_deleteAlarmInfo,1000*60*60*24*30);//1000*60*60*24*30
        //tabs切换调用事件
        $inforMagTabs.find('a').click(function () {
            var $this = $(this);
            var tabsName = $this.attr('href');
            _showTabs(tabsName);
        });
        //表单事件绑定
        $alarmManageForm.html5Validate(function () {
        	_submitAlarmSet();
            return false;
        });
        
        //判断是否从车辆监控的报警记录跳转过来 
        var isHas = window.location.href.indexOf('?');
        if( isHas !== -1){
        	//是车辆监控跳转过来 获得alarmId
            alarmId = window.location.href.split('?')[1].split("=")[1];
        }
        //加载表格
        _initAlarmManageTable();
        alarmId = '';
        
       //表单事件绑定
 	   $alarmDetailsForm.html5Validate(function () {
            _submitAddHandle();
            return false;
        });
        
        //搜索栏处理状态显示
        _select(urlPath.findDictByCodeUrl,"dealStatus-search-name");
        //报警处理显示数据字典
        _select(urlPath.findDictByCodeUrl,"dealStatus-type-name");
        _select(urlPath.findDictTypeByCodeUrl,"alarmManage-type-name");
        $("#dealStatus-search-name").bind("change",_searchHandler);
      //注册回车搜索事件
        $("#vehicleNo-search-name").keyup(function (e) {
            e.preventDefault();
            if (e.keyCode === 13) {
                _searchHandler();
            }
            return false;
        });
    };
    
    // 删除
	/*var _deleteAlarmInfo = function() {
		$.ajax({
			url : urlPath.deleteAlarmInfoUrl,
			type : 'delete',
			async : false
		}).done(function(json) {
			if (json.code === 1) {
				_initAlarmManageTable();
			} else {
				Message.alert({
					Msg : json.msg,
					isModal : false
				});
			} 
		});
	};*/

    //绑定click
   var _bindClick = function(){
	   $alarmManageDealBtn.bind("click",_dealAlarmManage);
	   $alarmManageBeforeSetBtn.bind("click",_setAlarmManage);
       $alarmManageSearchBtn.bind("click",{type:"alarmManage"},_searchHandler);
       $alarmManageExport.bind("click",{type:"alarmManage"},_export);

   };
    //表格
    var _initAlarmManageTable = function () {
        $alarmManageTable.jqGrid({
            url: urlPath.findByPageUrlForAlarmManage,
            mtype: "POST",
            datatype: "JSON",
            postData: {
      	       "alarmId":alarmId
            },
            colNames: ["企业名称", "记录编号", "终端编号", "车牌号", "运单编号", "报警类型", "报警时间", "报警简述", "处理状态", "操作"],
            colModel: [
                {name: "enterpriseName", index: "1", align: "center", width: "30px", sortable: false},
                {name: "alarmNo", index: "1", align: "center", width: "30px", sortable: false},
                {name: "terminalId", index: "2", align: "left", width: "30px", sortable: false},
                {name: "carrierName", index: "3", align: "center", width: "30px", sortable: false},
                {name: "waybillNo", index: "4", align: "center", width: "30px", sortable: false},
                {name: "alarmType", index: "5", align: "center", width: "30px", sortable: false},
                {name: "alarmDate", index: "6", align: "center", width: "30px", sortable: false},
                {name: "alarmDetails", index: "7", align: "center", width: "30px", sortable: false},
                {name: "alarmDealStatus", index: "8", align: "center", width: "30px", sortable: false},
                {name: "", index: "9", align: "center", width: "50px", sortable: false,
                	formatter: function (cellValue, options, rowObject) {
                		var id = rowObject.id;
                        var handlerTemp;
                        if(rowObject.alarmDealStatus === "已处理"){
                        	handlerTemp = '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text details-link" data-func="alarmManage-findById" onclick="alarmManageInfo.detailsAlarmManage(\'' + id + '\')"><span class="img-details"></span>详情</label>' +
                            '</p>';
                        }else{
                        	handlerTemp = '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text delete-link" data-func="alarmManage-update" onclick="alarmManageInfo.dealAlarmManage(\'' + id + '\')"><span class="img-edit"></span>处理</label>' +
                            '<span>&nbsp;&nbsp;</span>' +
                            '<label class="jqgrid-handle-text details-link" data-func="alarmManage-findById" onclick="alarmManageInfo.detailsAlarmManage(\'' + id + '\')"><span class="img-details"></span>详情</label>' +
                            '</p>';
                        }
                            
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
            pager: "#alarmManagePager",
            gridComplete:function(){
            	//配置权限
            	 $.initPrivg("alarmManageTable");
            }
        }).resizeTableWidth();
    };
    
    //报警设置
    var _setAlarmManage = function(){
    	//flag = ids;
        //var updateObj = $.getData(urlPath.findThresholdByIdUrl + ids);
    	 var updateObj ;/*= $.getData(urlPath.findThresholdByIdUrl + ids);*/
         //提交申请
         $.ajax({
             url: urlPath.findThresholdByIdUrl,
             type: 'GET',
             async: false
         }).done(function (json) {
             if (json.code === 1) {
            	 updateObj = json;
             } else {
                 $.validateTip(json);
             }
         });
         updateObj;
         $alarmManagePanel.saveOrCheck(true);
         $alarmManagePanel.showPanelModel('报警设置').setFormSingleObj(updateObj.data);
    };
    
    //提交报警设置
    var _submitAlarmSet = function () {
        var formData = $alarmManageForm.packageFormData();
        var url;
        url = urlPath.updateThresholdUrl+formData.id;
        //提交申请
        $.ajax({
            url: url,
            type: 'POST',
            async: false,
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
                $alarmManagePanel.closePanelModel();
                $alarmManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
            } else {
                $.validateTip(json);
            }
        });
    };

    //处理
    var _dealAlarmManage = function(ids){        
    	$("#alarmDealStatusDiv").removeClass("hidden");
        flag = ids;
        var updateObj = $.getData(urlPath.findByIdUrl + ids);
        $alarmDetailsPanel.saveOrCheck(true);
        $alarmDetailsPanel.showPanelModel('报警处理').setFormSingleObj(updateObj);
        $("#showForDetail").addClass("hidden");
        $alarmDealStatusAdd.removeClass("hidden");
    };

    //详情
    var _detailsAlarmManage = function(ids){
    	$("#showForDetail").removeClass("hidden");
    	$("#alarmDealStatusDiv").removeClass("hidden");
        var rowObj = $.getData(urlPath.findByIdUrl + ids);
        /*if(rowObj.alarmDealStatus == "未处理") {
        	$("#handleSituation span").addClass("hidden");
        }else {
        	rowObj.alarmRegisterHandle = "自动解除报警";
        	$("#handleSituation label").removeClass("must");
        	$("#handleSituation span").removeClass("hidden");
        }*/
        $alarmDetailsPanel.saveOrCheck(false);
        $alarmDetailsPanel.showPanelModel('报警信息详情');
        $alarmDetailsForm.setFormSingleObj(rowObj);
        $alarmDealStatusAdd.addClass("hidden").removeClass("must");
        if(rowObj.alarmDealStatus == "未处理") {
        	$("#handleSituation span").addClass("hidden");
        }else {
        	$("#handleSituation label").removeClass("must");
        	$("#handleSituation span").removeClass("hidden");
        }
    };


    //提交
    //提交修改处理方法
    var _submitAddHandle = function (ids) {
        var formData = $alarmDetailsPanel.packageFormData();
        var url;
        if (flag) {
            url = urlPath.updateUrl + flag ;
        } else {
            url = urlPath.saveUrl;
        }
        //提交申请
        $.ajax({
            url: url,
            type: 'POST',
            async: false,
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
            	$alarmDetailsPanel.closePanelModel();
                $alarmManageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                
            } else {
                $.validateTip(json);
            }
        });
    };

    //查询数据信息
    var _searchHandler = function () {
        $alarmManageTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {
            	       "alarmDealStatus": $("#dealStatus-search-name").val(),
            	       "licencePlateNo": $("#vehicleNo-search-name").val(),
            	       "starAlarmDate": $serachtartTime.val(),
            	       "endAlarmDate": $searchEndTime.val(),
            	       "alarmId":alarmId
            },
            page: 1
        }).trigger("reloadGrid");
     // 去除首尾空格
		$.removeTrim();
    };
    
    //tabs切换事件
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
            case '#alarmManage':
            	 _init();
                break;
            case '#blacklistManage':
            	blacklistManageInfo.init();
                break;
            default :
        }
    };

    //导出
    var _export =function(){
    	if(arguments.length !== 0){
    		 var flag = arguments[0].data.type;
    	        var id;
    	        if(flag ==="alarmManage"){
    	             id = $alarmManageTable.jqGrid("getGridParam", "selrow");
    	        }else{
    	            id = $escortTable.jqGrid("getGridParam", "selrow");
    	        }
    	        window.location.href = urlPath.exportUrl + "/" + id;
    	}
       
    };
    /**
     * @selectId 下拉框的ID
     * @url 数据字典的地址 以及对应的值
     * 根据返回值填充select
     * */
    var _select = function(url,selectId){
      var select ="";
      dicObj = $.getData(url);
       $.each(dicObj, function (index, content) {
    	   select += '<option value= "' + dicObj[index].code + '">' + dicObj[index].name + '</option>';   
        });
       $("#"+selectId).append(select);
    };
    
    _init();
    
    //返回函数
    return {
        detailsAlarmManage:_detailsAlarmManage,
        dealAlarmManage:_dealAlarmManage,
        setAlarmManage:_setAlarmManage
    };
})(jQuery);
