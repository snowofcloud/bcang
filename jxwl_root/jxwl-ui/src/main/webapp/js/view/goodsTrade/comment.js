/**
 * 评价管理-订单评价
 * @author zz
 */
var commentM = (function($) {
	// 搜索
	var $evaluateSearchBtn = $("#evaluate_search_btn");
	// 修改
	var $commentUpForm   = $("#comment_up_form");
	var $upGoodsTable    = $("#comment_up_goods_table");
	var $evaluateUpPanel = $("#comment_up_panel");
	
	// 详情
	var $commentViewForm   = $("#comment_view_form");
	var $viewGoodsTable    = $("#comment_viewgoods_table");
	var $evaluateViewPanel = $("#comment_view_panel");
	
    var loadOne = false;
    
    
    // 请求路径
    var urlPath = {
    	// 货源路径
    	findByPageUrl : $.backPath + '/commentComplain/findByPage',
    	updateUrl     : $.backPath + '/commentComplain/update',
    	findByIdUrl   : $.backPath + '/commentComplain/findById/'
    };
    
    // 初始化
    var _init = function() {
    	if (!loadOne) {
    		loadOne = !loadOne;
	        // 初始化评价表格
    		_initCommentTable();
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
    	$('input[name="evaluate_obj"]').change(function() {
    		_initCommentTable();
    	});
    	
    	// 搜索
    	$evaluateSearchBtn.bind("click", _searchHandler);

    	
    };
    
    // 评价信息表格
    var _initCommentTable = function() {
    	$("#storeTable").empty().append('<table id="evaluate_table"></table><div id="evaluate_pager"></div>');
    	var checked = $('input[name="evaluate_obj"]:checked').val();
    	$("#evaluate_table").jqGrid({
            url      : urlPath.findByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            postData : {
            	"moudle" : "0",
            	"who"    : checked
            },
            colNames : ["企业名称", "订单编号", "评价时间", "星级评分", "操作"],
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
                {name: "score",          index: "4", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject) {
                		return _showStar(cellValue);
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
                    	if (checked === "1") { // 给他人的评价
                    		option = '<p class="jqgrid-handle-p">' +
                    			'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" onclick="commentM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>'
                    		  + '<span>&nbsp;&nbsp;</span>';
                    		if (!(+remove)) {
                    			option += '<label class="jqgrid-handle-text delete-link" data-func="evaluate-update" onclick="commentM.update(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\');"><span class="img-edit"></span>修改</label>';
                    		}
                    		option += '</p>';
                    	} else { // 来之企业的评价
                    		option = '<p class="jqgrid-handle-p">' +
                    		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" onclick="commentM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>' +
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
            pager      : "#evaluate_pager",
            gridComplete:function(){
            	//配置权限
            	$.initPrivg("evaluate_table");
            }
        }).resizeTableWidth();
    };
    
    // 修改评价
    var _update = function() {
    	$commentUpForm[0].reset();
    	var obj = _aassembleVal4view($upGoodsTable, arguments);
    	var score  = obj.commentComplain.score + "";
    	$("#up_score").empty().append('<input id="comment_up_score" value="'+score+'" type="number" class="rating" min=0 max=5 step=0.5 data-size="xs">');
    	$("#comment_up_score").rating();
    	
    	$evaluateUpPanel.showPanelModel('修改评价').setFormSingleObj(obj);
    	
		// 提交评价信息表单验证绑定
    	var id = arguments[0];
    	$commentUpForm.html5Validate(function () {
		    _submitHandle(id);
		    return false;
		});
    };
    // 货源表单提交
    var _submitHandle = function(id) {
    	var form = {};
    	form.id      = id;
    	form.content = $("#comment_up_content").val();
    	form.score   = $("#comment_up_score").val();
		$.ajax({
			url  : urlPath.updateUrl,
			type : 'POST',
			async: false,
			data : form
		}).done(function(json) {
			if (json.code === 1) {
				$evaluateUpPanel.closePanelModel();
				$("#evaluate_table").jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
			} else {
				$.validateTip(json);
			}
		});
    };
    
    // 货源详情
    var _detail = function() {
    	$commentViewForm[0].reset();
    	var obj = _aassembleVal4view($viewGoodsTable, arguments);
    	$evaluateViewPanel.showPanelModel('评价信息').setFormSingleObj(obj);
        // 追加查看的评分
        $("#view_score").empty().append(_showStar(obj.commentComplain.score));
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
        obj.commentTime    = obj.commentComplain.createTime;
        obj.content        = obj.commentComplain.content;
        obj.startPoint     = obj.srcGoods.startPoint;
        obj.endPoint       = obj.srcGoods.endPoint;
        _addRowData2JqGrid($this, obj.srcGoods.goodsInfos);
        $this.jqGrid({autowidth: false}).setGridWidth(850);
        
        return obj;
    };
    
    
    // 评价搜索
    var _searchHandler = function () {
    	$("#evaluate_table").jqGrid('setGridParam', {
            url     : urlPath.findByPageUrl,
            postData: {"moudle"    : "0",
            	       "who"       : $('input[name="evaluate_obj"]:checked').val(),
            	       "orderNo"   : $("#evaluate_order_no").val(),
            		   "startTime" : $("#start_time_evaluate").val(),
            		   "endTime"   : $("#end_time_evaluate").val()
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
     * 评分星星显示
     * @param score 分数
     */
    var _showStar = function(score) {
    	var score2 = "" + score;
    	if (1 == score2.length) {
    		score2 = score2 + ".0";
    	}
    	var percent = score * 20 + "%"; 
    	var str =
	    	' <div class="rating-container rating-xs rating-animate">' +
	    	'   <div class="rating">' +
	    	'	    <span class="empty-stars">' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star-empty"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star-empty"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star-empty"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star-empty"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star-empty"></i></span>' +
	    	'	    </span>' +
	    	'	    <span class="filled-stars" style="width: '+ percent +';">' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star"></i></span>' +
	    	'		    <span class="star"><i class="glyphicon glyphicon-star"></i></span>' +
	    	'	    </span>' +
	    	'   </div>' +
	    	'   <div class="caption">' +
	    	'	    <span class="label label-success">'+ score2 +'</span>' +
	    	'   </div>' +
	        ' </div>';
    	
    	return str;
    };
    
    
    
    
    
    /**
     * 调用初始化函数
     */
    _init();
    
    /**
     * 返回函数
     */
    return {
        init     : _init,
        update   : _update,
        detail   : _detail,
        showStar : _showStar
    };
    
})(jQuery);