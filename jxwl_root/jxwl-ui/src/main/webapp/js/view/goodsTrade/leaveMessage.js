/**
 * Created by xinzw on 2016/8/30.
 */
/**
 * @description:物流交易-交易大厅-留言管理
 * @author: xinzw
 * @time: 2016-8-30
 */

//物流交易-交易大厅-留言管理
var leaveMessage = (function ($) {

    //查询条件
    var $SearchStartTime = $("#search_startTime");
    var $SearchEndTime = $("#search_endTime");

    //留言列表、留言弹框、留言表单
    var $leaveMessageTable = $("#leaveMessageTable");
    var $leaveMessagePanel = $('#leaveMessage-panel');
    var $leaveMessageForm =  $('#leaveMessage-form');
    var $leaveMessageSearchBtn = $('#leaveMessage-searchBtn');
    
    //化工企业登录查看一条货源下的所有留言（多条）
    var $multipleLeaveMessage = $('#keepTalksForgoodMessage');
    
    //货源已被删除时弹框
    var $srcGoodsDeletedPanel = $('#srcGoodsDeletedPanel');
    var $srcGoodsDeletedPanelBodyContainer = $('#srcGoodsDeletedPanelBodyContainer');
    var $goodsMessageForm = $('#goodsMessageForm');
    
    // 点击留言详情触发的货源弹框
    var $srcGoodsPanel     = $("#goodsMessageForm-panel");
    var $srcGoodsForm      = $("#goodsMessageForm");
    
    // 点击货单号触发的货源弹框
    var $srcGoodsPanel2     = $("#src-goods-panel");
    var $srcGoodsForm2      = $("#srcGoodsForm");
    
    //编辑留言弹框
    var $leaveMessageEditPanel = $('#leaveMessageEdit-Panel');
    var $leaveMessageEditForm = $('#leaveMessageEdit-Form');
    
    var isExist = 0;
    //判断是否第一次加载
    var loadOne = false;
    
    var dicObj;
    
    //请求路径
    var urlPath = {
        findByPageUrl: $.backPath + '/leaveMessage/findByPage',
        findByIdUrl: $.backPath + '/leaveMessage/findById/', //查看详情
        findGoodsSourceByIdUrl: $.backPath + '/srcGoods/findById/', //查看货源详情
        //查询得到相应的数字字典
        findDicCodeUrl: $.backPath + '/goodsSourceDetail/findDictByCode/004',
        saveUrl:$.backPath + '/leaveMessage/save',
        updateUrl:$.backPath + '/leaveMessage/update/',
        deleteUrl: $.backPath + '/leaveMessage/delete/'

        
    };
    
    
    //数据初始化
    var _init = function () {
    	if(!loadOne){
    		//得到数字字典
        	dicObj = $.getData(urlPath.findDicCodeUrl);
        	
	        //第一个tab标签 要先解除绑定在进行绑定事件
	        $leaveMessageSearchBtn.unbind("click").bind("click",_searchHandler);
	        /*$SearchStartTime.unbind("select").bind("select",_searchHandler);
	        $SearchEndTime.unbind("select").bind("select",_searchHandler);*/
	        _initleaveMessageTable();
	        
	        //表单事件绑定
	        $leaveMessageEditForm.html5Validate(function () { //$leaveMessageForm.html5Validate(function () {
	        	_submitHandle();
	            return false;
	        });
	        
	        //注册回车搜索事件
	        $("#vehicleNo-search-name").keyup(function (e) {
	            e.preventDefault();
	            if (e.keyCode === 13) {
	                _searchHandler();
	            }
	            return false;
	        });
	        
	        loadOne = true;
	        
    	}else{
        	_searchHandler();
        }
    };

    //绑定click

    
    var flag = "";
    //表格
    var _initleaveMessageTable = function () {
        $leaveMessageTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            colNames: ["留言的物流企业", "货单号", "留言内容", "化工企业", "留言时间", "操作"],
            colModel: [
                {name: "logisticsEnterprise", index: "1", align: "center", width: "50px", sortable: false},
                {name: "waybilllNo", index: "2", align: "center", width: "50px", sortable: false,
                	formatter: function(cellValue, options, rowObject){
                		var status;
                		var tradeStatus = $.getDicNameByCode(rowObject["tradeStatus"], dicObj["110001"]);
                		if(rowObject["srcGoodsRemove"]=="1"){
                			status = "货源已删除";
                		}else{
                			status = "货源"+tradeStatus;
                		}
                		return '<p class="jqgrid-handle-p">' +
                		'<label class="jqgrid-handle-text details-link"  onclick="leaveMessage.detailsGoodsSource(\'' + rowObject.goodsSourceId + '\',\''  + rowObject.srcGoodsRemove + '\')">'+cellValue+'</label>' +
                        '&nbsp;&nbsp;' + status + '</p>';
                	}},
                {name: "messageContent", index: "3", align: "center", width: "40px", sortable: false},
                {name: "chemicalEnterprise", index: "4", align: "center", width: "60px", sortable: false},
                {name: "createdTime", index: "5", align: "center", width: "40px", sortable: false},
                {name: "handle", index: "6", align: "center", width: "58px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var handlerTemp;
                        if(rowObject["srcGoodsRemove"]=="1"||rowObject["tradeStatus"]!='110001002'){
                        	// 货源已删除或不是‘已发布’状态时，留言都不能修改与删除
                        	handlerTemp = '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text details-link" data-func="leaveMessage-findById" onclick="leaveMessage.detailsMessage(\'' + rowObject.goodsSourceId + '\',\''  + rowObject.id + '\',\''  + rowObject.srcGoodsRemove + '\')"><span class="img-details"></span>详情</label>' +
                            '</p>';
                        }else{
                        	handlerTemp = '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text details-link" data-func="leaveMessage-findById" onclick="leaveMessage.detailsMessage(\'' + rowObject.goodsSourceId + '\',\''  + rowObject.id + '\',\''  + rowObject.srcGoodsRemove + '\')"><span class="img-details"></span>详情</label>' +
                            '&nbsp;&nbsp;&nbsp;&nbsp;' +
                            '<label class="jqgrid-handle-text details-link" data-func="leaveMessage-update" onclick="leaveMessage.editMessage(\'' + rowObject.id + '\')"><span class="img-edit"></span>修改</label>' +
                            '&nbsp;&nbsp;&nbsp;&nbsp;' +
                            '<label class="jqgrid-handle-text details-link" data-func="leaveMessage-delete" onclick="leaveMessage.delMessage(\'' + rowObject.id + '\')"><span class="img-delete"></span>删除</label>' +
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
            pager: "#leaveMessagePage",
            gridComplete:function(){
                //配置权限
                $.initPrivg();
            }
        }).resizeTableWidth();
    };


    
    //查看留言
    var _detailsMessage = function(goodsSourceId, ids, srcGoodsRemove){
    	if(srcGoodsRemove == '1'){
    		var leaveMessagerowObj = $.getData(urlPath.findByIdUrl + ids);
    		$leaveMessagePanel.removeClass("hidden");
            $multipleLeaveMessage.addClass("hidden");
            $leaveMessagePanel.saveOrCheck(false);
            //$leaveMessagePanel.showPanelModel('留言详情');
            $srcGoodsPanel.showPanelModel('留言详情');
            /* bug：货源删除提示会覆盖其他‘已发布’，‘待确认’等等状态下的货源信息div
        	 * var html = $srcGoodsDeletedPanel.find('.panel-body-content').html();
        	   $('#goodsMessageForm').find('.panel-body-content').html(html);
        	*/
        	var element = $srcGoodsDeletedPanel.find('.panel-body');
        	var e_copy = element.clone();// 这里拷贝DOM元素的副本非常重要，否则element被使用before或append方法插入到goodsMessageForm前面时就会被从原有的位置移除掉！！
        	/*$goodsMessageForm.before(e_copy);
        	isExist = !isExist;*/
        	$srcGoodsDeletedPanelBodyContainer.empty().append(e_copy);
        	$goodsMessageForm.hide();
        	$leaveMessageForm.setFormSingleObj(leaveMessagerowObj);
    		return;
    	}
    	
        var leaveMessagerowObj = $.getData(urlPath.findByIdUrl + ids);
    	var obj = $.getData(urlPath.findGoodsSourceByIdUrl + goodsSourceId);
    	
    	// 加载货物表格
        _goodsTable4Detail($("#lgoodsTable"));
    	$("#lgoodsTable").jqGrid('clearGridData');
    	if (obj.goodsInfos) {
    		var gobj = obj.goodsInfos;
    		if (gobj && 0 < gobj.length) {
    			for (var index in gobj) {
    				var data = gobj[index];
    				$("#lgoodsTable").jqGrid("addRowData", data.id, data, "last");
    			}
    		}
    	}
    	$("#lgoodsTable").jqGrid({autowidth: false}).setGridWidth(865);
    	
        //获得tradeStatus的数字字典的值
        obj['tradeStatus'] = $.getDicNameByCode(obj['tradeStatus'], dicObj["110001"]);
        obj['carType'] = $.getDicNameByCode(obj['carType'], dicObj["109001"]);
        
        $leaveMessagePanel.removeClass("hidden");
        $multipleLeaveMessage.addClass("hidden");
        
        $srcGoodsPanel.saveOrCheck(false);
        $leaveMessagePanel.saveOrCheck(false);
        $srcGoodsPanel.showPanelModel('留言详情');
        	/*if(isExist){
        		$goodsMessageForm.prev().remove();
        		isExist = !isExist;
        	}*/
        $srcGoodsDeletedPanelBodyContainer.empty();
    	$goodsMessageForm.show();
    	//$("#goodsMessageForm-panel").css("overflow-y","auto").css("overflow-x","hidden");//如果添加.css("height","898px")就会出现bug，即留言详情弹框底部出现一块空白，如果去掉高度设置则bug消失
        //$("#goodsMessageForm > div").css("overflow-y","hidden");
    
    	$srcGoodsForm.setFormSingleObj(obj);
        $leaveMessageForm.setFormSingleObj(leaveMessagerowObj);
    };
    
    
    //修改留言
    var _editMessage = function(ids){
    	flag = ids;
        var rowObj = $.getData(urlPath.findByIdUrl + ids);  //修改留言首先会请求一次findById,然后返回待编辑的留言的所有字段信息
//        $srcGoodsPanel.saveOrCheck(false);
//        $('#leaveMessageTime').hide();
//        $('#leaveMessagePanelFooter').show();
        /*$leaveMessagePanel.saveOrCheck(false);
        $leaveMessagePanel.showPanelModel('修改留言');
        $leaveMessageForm.setFormSingleObj(rowObj);*/
        
        $leaveMessageEditPanel.saveOrCheck(true);
        $leaveMessageEditPanel.showPanelModel('修改留言');
        $leaveMessageEditForm.setFormSingleObj(rowObj);
        
    };
    
    
    // 提交：编辑留言
    var _submitHandle = function() {
        var formData = $leaveMessageEditForm.serialize();//$leaveMessageForm.serialize();
        $.ajax({
            url  : flag ? urlPath.updateUrl+flag : urlPath.saveUrl,
            type : 'POST',
            async: false,
            data : formData
        }).done(function(json) {
            if (json.code === 1) {
            	$leaveMessageEditPanel.closePanelModel();//$leaveMessagePanel.closePanelModel();
            	$leaveMessageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
            	//flag ? Message.alert({Msg: json.msg, isModal: false}) :  Message.alert({Msg: '留言编辑成功', isModal: false});;
            } else {
                $.validateTip(json);
            }
        });
    };
    
    
    //删除留言
    var _delMessage = function(ids){
    	Message.confirm({
            Msg: $.msg.sureDelete,
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.deleteUrl + ids,
                    type: 'delete',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                    	$leaveMessageTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        //$.validateTip(json.code);
                        Message.alert({Msg: json.msg, isModal: false});
                    }
                });
            }
        });
    };

    
    // 查询留言
    var _searchHandler = function () {
        $leaveMessageTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {
                "startTime": $SearchStartTime.val(),
                "endTime": $SearchEndTime.val()
            },
            page: 1
        }).trigger("reloadGrid");

    };

    // 点击货单号--查看货源详情
    var _detailsGoodsSource = function(goodsSourceId,srcGoodsRemove){
		if( srcGoodsRemove == "1" ){
			$srcGoodsDeletedPanel.showPanelModel('货源详情');
			return;
		}
    	//查询货源详情不要调用货源信息的接口，要调用留言信息接口，否则用户若查看一条状态为‘已删除’的货源详情时返回为空！
    	var obj = $.getData(urlPath.findGoodsSourceByIdUrl + goodsSourceId);
        
    	// 隐藏新增多个货物按钮
    	$("#goodAdd").addClass("hidden");
    	$("#keepTalks").addClass("hidden");
    	// 加载货物表格
        _goodsTable4Detail($("#goodsTable"));
    	$("#goodsTable").jqGrid('clearGridData');
    	if (obj.goodsInfos) {
    		var gobj = obj.goodsInfos;
    		if (gobj && 0 < gobj.length) {
    			for (var index in gobj) {
    				var data = gobj[index];
    				$("#goodsTable").jqGrid("addRowData", data.id, data, "last");
    			}
    		}
    	}
    	$("#goodsTable").jqGrid({autowidth: false}).setGridWidth(1030);
        obj['carType'] = $.getDicNameByCode(obj['carType'], dicObj["109001"]);
    	$srcGoodsPanel2.saveOrCheck(false);
    	$srcGoodsPanel2.showPanelModel('货源详情');
    	$srcGoodsForm2.setFormSingleObj(obj);
    };
    
    /**
     * 查看详情时货物表格
     */ 
     var _goodsTable4Detail = function($arr) {
    	 $arr.jqGrid({
               mtype    : "POST",
               datatype : "JSON",
               colNames : ["货物序号", "品名", "包装", "数量", "重量(t)", "体积(m3)", "单价(万元)", "货物价值(万元)", "id"],
               colModel : [
                   {name: "goodsSerialNo",  index: "1", align: "center", width: "10px", sortable: false},
                   {name: "goodsName",      index: "2", align: "left",   width: "15px", sortable: false},
                   {name: "pack",           index: "3", align: "left",   width: "15px", sortable: false},
                   {name: "amount",         index: "4", align: "right",  width: "15px", sortable: false},
                   {name: "weight",         index: "5", align: "right",  width: "15px", sortable: false},
                   {name: "volume",         index: "6", align: "right",  width: "15px", sortable: false},
                   {name: "unitPrice",      index: "7", align: "right",  width: "15px", sortable: false},
                   {name: "goodsWorth",     index: "8", align: "right",  width: "15px", sortable: false},
                   {name: "id",             index: "11", align: "left",  width: "1px",  sortable: false, hidden: true}
               ],
               loadonce   : false,
   			   rownumbers : false,
   			   viewrecords: true,
   			   width : 1066,
   			   height: true,
   			   rowNum: 5
               // pager : "#lgoodsTablePage"
           });
     };
    
    
    //返回函数
    return {
        detailsMessage:_detailsMessage,
        editMessage:_editMessage,
        delMessage:_delMessage,
        searchHandler:_searchHandler,
        detailsGoodsSource:_detailsGoodsSource,
        init:_init
    };
})(jQuery);
