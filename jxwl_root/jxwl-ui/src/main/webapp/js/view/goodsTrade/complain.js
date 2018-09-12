/**
 * 评价管理-订单投诉
 * @author zz
 */
var complainM = (function($) {
	// 搜索
	var $complainSearchBtn = $("#complain_search_btn");
	// 修改、详情
	var $complainForm    = $("#complain_form");
	var $goodsTable      = $("#complain_goods_table");
	var $complainPanel   = $("#complain_panel");
	
	// 选中radio状态
	var checked = null;
	
    var loadOne = false;
    
    
    // 请求路径
    var urlPath = {
    	findByPageUrl : $.backPath + '/commentComplain/findByPage',
    	updateUrl     : $.backPath + '/commentComplain/update',
    	findByIdUrl   : $.backPath + '/commentComplain/findById/'
    };
    
    // 初始化
    var _init = function() {
    	if (!loadOne) {
    		loadOne = !loadOne;
	        // 初始化评价表格
    		_initComplainTable();
        	// 绑定点击事件
            _bindClick();
            // 注册回车搜索事件
            $("#goods_search input, #enpre_search input").keyup(function (e) {
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
    var _bindClick = function(){
    	// radio 选中事件
    	$('input[name="complain_obj"]').change(function() {
    		_initComplainTable();
    	});
    	
    	// 搜索
    	$complainSearchBtn.bind("click", _searchHandler);

    };
    
    // 投诉信息表格
    var _initComplainTable = function() {
    	$("#storeTable2").empty().append('<table id="complain_table"></table><div id="complain_pager"></div>');
    	var checked = $('input[name="complain_obj"]:checked').val();
    	
    	$("#complain_table").jqGrid({
            url      : urlPath.findByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            postData : {
            	"moudle" : "1",
            	"who"    : checked
            },
            colNames : ["企业名称", "订单编号", "投诉时间", "投诉内容", "操作"],
            colModel : [
                {name: "enterpriseName", index: "1", align: "left",   width: "30px", sortable: false,
                	formatter : function(cellvalue, options, rowObject) {
                		var html = '<p class="jqgrid-handle-p">' + cellvalue;
                		if (rowObject.remove == '1') {
                			html = html +'&nbsp;&nbsp;<span style="color: red;">此企业已注销</span>';
                		}
                		html = html +'</p>';
                		return html;
                	}	
                },
                {name: "orderNo",        index: "2", align: "left",   width: "30px", sortable: false},
                {name: "updateTime",     index: "3", align: "center", width: "30px", sortable: false},
                {name: "content",        index: "4", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject) {
                		return cellValue;
                	}
                },
                {name: "opt",            index: "9", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                    	var id = rowObject.id;
                    	var srcGoodsId = rowObject.orderId;
                    	var enpName = rowObject.enterpriseName;
                    	var orderNo = rowObject.orderNo;
                    	var remove  = rowObject.remove;
                    	
                    	var option = "";
                    	if (checked === "1") { // 给他人的投诉
                    		option = '<p class="jqgrid-handle-p">' +

                    		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" '
                    			  + 'onclick="complainM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>'
                    			  + '<span>&nbsp;&nbsp;</span>';
                    		if (!(+remove)) {
                    			option += '<label class="jqgrid-handle-text delete-link" data-func="evaluate-update" '   
                    				+ 'onclick="complainM.update(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\');"><span class="img-edit"></span>修改</label>';
                    		}
                    		option += '</p>';
                    	} else { // 来之企业的投诉
                    		option = '<p class="jqgrid-handle-p">' +
                    		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" '
                    			  + 'onclick="complainM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>' +
                    		'</p>';
                    	}
                        return option;
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 10,
            rowList    : [5, 10, 15],
            pager      : "#complain_pager",
            gridComplete:function(){
            	//配置权限
            	$.initPrivg("complain_table");
            }
        }).resizeTableWidth();
    };
    
    // 修改投诉
    var _update = function() {
    	$("#complainEditBtns").removeClass("hidden");
    	$complainForm[0].reset();
    	$("#complain_up").show();
    	$("#complain_view").hide();
    	var obj = _aassembleVal4view($goodsTable, arguments);
        
    	$complainPanel.showPanelModel('修改投诉').setFormSingleObj(obj);
    	
		// 提交评价信息表单验证绑定
    	var id = arguments[0];
    	$complainForm.html5Validate(function () {
		    _submitHandle(id);
		    return false;
		});
    };
    // 货源表单提交
    var _submitHandle = function(id) {
    	var form = {};
    	form.id      = id;
    	form.content = $("#complain_content").val();
		$.ajax({
			url  : urlPath.updateUrl,
			type : 'POST',
			async: false,
			data : form
		}).done(function(json) {
			if (json.code === 1) {
				$complainPanel.closePanelModel();
				$("#complain_table").jqGrid('setGridParam', {
					postData: {}, 
					page: 1}
				).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
    };
    
    // 投诉详情
    var _detail = function() {
    	$("#complainEditBtns").addClass("hidden");
    	$complainForm[0].reset();
    	$("#complain_up").hide();
    	$("#complain_view").show();
    	$("#complain_view span").removeClass("hidden");
    	var obj = _aassembleVal4view($goodsTable, arguments);
    	$complainPanel.showPanelModel('投诉信息').setFormSingleObj(obj);
    };
    
    
    /**
     * @param $this 当前表格对象
     * @param args  arguments
     * 为修改和详情组装数据
     */
    var _aassembleVal4view = function($this, args) {
    	var id         = args[0];
    	var srcGoodsId = args[1];
        var obj = $.getData(urlPath.findByIdUrl + id + "/" + srcGoodsId);
        obj.enterpriseName = args[2];
        obj.orderNo        = args[3];
        obj.complainTime   = obj.commentComplain.createTime;
        obj.content        = obj.commentComplain.content;
        obj.startPoint     = obj.srcGoods.startPoint;
        obj.endPoint       = obj.srcGoods.endPoint;
        _addRowData2JqGrid($this, obj.srcGoods.goodsInfos);
        $this.jqGrid({autowidth: false}).setGridWidth(850);
        
        return obj;
    };
    
    
    // 货源搜索
    var _searchHandler = function () {
    	$("#complain_table").jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {"moudle"    : "1",
            	       "who"       : $('input[name="complain_obj"]:checked').val(),
            	       "orderNo"   : $("#complain_order_no").val(),
            		   "startTime" : $("#start_time_complain").val(),
            		   "endTime"   : $("#end_time_complain").val()
            },
            page    : 1
        }).trigger("reloadGrid");
    	//去除首尾空格
    	$.removeTrim();
    };
    
    
/**************************************************************** 货源相关 FIXME ********************************************************************/
    /**
     * @param $this 当前表格对象
     * 货物表格初始化
     */
    var _initGoodsTab = function($this) {
    	$this.jqGrid({
               mtype    : "POST",
               datatype : "JSON",
               colNames : ["序号", "品名", "包装", "数量", "重量(t)", "体积(m3)", "单价(万元)", "货物价值(万元)", "id"],
               colModel : [
                   {name: "goodsSerialNo",  index: "1", align: "center", width: "10px", sortable: false},
                   {name: "goodsName",      index: "2", align: "center", width: "20px", sortable: false},
                   {name: "pack",           index: "3", align: "center", width: "18px", sortable: false},
                   {name: "amount",         index: "4", align: "center", width: "13px", sortable: false},
                   {name: "weight",         index: "5", align: "center", width: "15px", sortable: false},
                   {name: "volume",         index: "6", align: "center", width: "15px", sortable: false},
                   {name: "unitPrice",      index: "7", align: "center", width: "15px", sortable: false},
                   {name: "goodsWorth",     index: "8", align: "center", width: "17px", sortable: false},
                   {name: "id",             index: "9", align: "center", width: "1px",  sortable: false, hidden: true}
               ],
               loadonce   : false,
   			   rownumbers : false,
   			   viewrecords: true,
   			   width : 1066,
   			   height: true,
   			   rowNum: 5
           });
     };
    
    
    /**
     * 修改、查看时数据传入表格中
     */
    var _addRowData2JqGrid = function($this, obj) {
    	// 加载货物表格
    	_initGoodsTab($this);
    	$this.jqGrid('clearGridData');
    	if (obj && 0 < obj.length) {
    		for (var index in obj) {
    			var data = obj[index];
    			$this.jqGrid("addRowData", data.id, data, "last");
    		}
    	}
    };
    
    
    /**
     * 返回函数
     */
    return {
        init    : _init,
        update  : _update,
        detail  : _detail
    };
    
})(jQuery);