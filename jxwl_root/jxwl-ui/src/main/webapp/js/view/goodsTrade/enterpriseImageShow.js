/**
 * @description:形象展示
 * @author: wangwenhuan
 * @time: 2016-8-31
 */
var enterpriseImageShow = (function($){
	//搜索栏：查询按钮、返回企业按钮对象
	var $enterpriseSearchBtn = $("#enterprise-searchBtn");
	var $returnBtn = $("#return-Btn");
	
	// 投诉记录信息表
    var $complainsTable = $("#complainsTable");
    // 评价信息表
    var $commentsTable  = $("#comments_table");
    
    // 当前显示企业的corporateNo
    var corporateNo = null;
    
    //访问路径
    var urlPath ={
    	//用于搜索栏中，依据输入的参数查询路径
    	findEnterpriseInfByNameOrCodeUrl : $.backPath + '/enterpriseImageShow/findByNameOrCode',
    	//用于依据登录企业的法人代码  查询相应的企业信息路径
    	findEnterpriseInfByCodeUrl :$.backPath + '/enterpriseImageShow/findByCode',
    	downloadFileUrl : $.backPath + "/enterpriseImageShow/downloadFile/",
    	
    	// 投诉
    	findComplainsByPageUrl : $.backPath + '/commentComplain/findByPage',
    	// 评价
    	findCommentsByPageUrl  : $.backPath + '/commentComplain/findByPage',
    	// 评价平均分
    	findAvgScoreUrl        : $.backPath + '/commentComplain/findAvgScore/' 
    };
    
    //初始化
    var _init =function(){
    	// 利用bootstrap中的carousel插件 自动播放图片
        $("#imageShowCarousel").carousel('cycle');
        // 查询按钮
        $enterpriseSearchBtn.unbind("click").bind("click", _searchHandler);
        // 回车查询
        $("#enterprise-search").keyup(function (e) {
            if (e.keyCode === 13) {
            	_searchHandler();
            }
        });
        // 返回按钮
        $returnBtn.unbind("click").bind("click", _return);
    	// 企业信息初始化
    	_initEnterpriseInfTable(0);
    	// 投诉记录表格初始化
    	_initcomplainTable();
    	// 评价记录表格初始化
    	_initCommentTable();
    	// 投诉记录查看更多表格初始化
    	_initcomplainMoreTable();
    	// 评价记录查看更多表格初始化
    	_initCommentMoreTable();
    	// 绑定点击事件
    	_bindClickEvent();
    };
    
    // 搜索栏中查询事件函数
    var _searchHandler = function(){
    	_initEnterpriseInfTable(1);
    	// 投诉记录表格初始化
    	_searchComplainHandler();
    	// 评价记录表格初始化
    	_searchCommentHandler();
    };
    // 返回事件函数
    var _return = function(){
    	_initEnterpriseInfTable(0, 0);
    	$("#enterprise-search").val('');
    	// 投诉记录表格初始化
    	_searchComplainHandler();
    	// 评价记录表格初始化
    	_searchCommentHandler();
    };
    
    /**
     * 点击事件
     */
    var _bindClickEvent = function() {
    	// 投诉查看更多
    	$("span[class='left-down-title2']").bind("click", _complainMore);
    	// 投诉查看更多搜索
    	$("#complain_search_btn").bind("click", _searchComplainMoreHandler);
    	// 评价查看更多
    	$("span[class='right-down-title2']").bind("click", _commentMore);
    	// 评价查看更多搜索
    	$("#comment_search_btn").bind("click", _searchCommentMoreHandler);
    };
    
   /* 企业信息初始化
    * 参数为0时，为查询登录企业的信息；
    * 参数为1时，为依据搜索栏的输入数据查询企业信息
    */
    var _initEnterpriseInfTable = function(flag,returnBack) {
    	var enterpriseInf = '';
    	    data = '';
    	    url = '';
    	if (flag === 0) {
    		// 根据登录的企业的法人代码查询企业信息 精确查询
        	url = urlPath.findEnterpriseInfByCodeUrl;
        	enterpriseInf =$.getData(url,'GET');
        	corporateNo = enterpriseInf.corporateNo;
        	if(returnBack === 0){
        		$("#enterprise-search").val(corporateNo);
        	}
    	} else {
    		// 搜索栏中的查询企业信息  模糊查询
    		url = urlPath.findEnterpriseInfByNameOrCodeUrl;
        	data = {
        		"queryStr":$("#enterprise-search").val()
        	};
        	var result = $.getData(url,"POST",data);
        	if (result && 0 < result.length) {
        		corporateNo = result[0].corporateNo;
        		//人为设定取返回的第一个
        		enterpriseInf = result[0];
        	}
    	}
        if (enterpriseInf && enterpriseInf['enterpriseType']) {
	    	if ( enterpriseInf['enterpriseType'] === "106001001") {
	    		enterpriseInf['enterpriseType'] = "物流企业";
	        } else if ( enterpriseInf['enterpriseType'] === "106001002" ) {
	        	enterpriseInf['enterpriseType'] = '化工企业';
	        }
        }
        
    	//填充企业信息数据
    	var names =['enterpriseName','corporateRep','establishDate',
    	            'logonCapital','enterpriseType','openAccountBank',
    	            'registerOffice','registrationNo','taxationNo',
    	            'address','enterpriseIntroduce','professionalWork','majorBusinessRoute'];
    	//填充数据函数
    	if (enterpriseInf) {
    		_fillData(names,enterpriseInf);
    	}
    	
        //评分
    	var score =$.getData(urlPath.findAvgScoreUrl + corporateNo, 'GET');
    	score = score ? score.toFixed(1) : 0.0;
    	$("#comment_score").empty().append('<input id="comment_up_score" value="'+score+'" type="number" class="rating" min=0 max=5 step=0.5 data-size="xs">');
    	$("#comment_up_score").rating({displayOnly: true});
        
        //展示企业形象图片
        // bug! 企业用户删除本企业所有形象图片（多张）后，在形象展示页面查看企业图片，应该给出无形象图片的提示，且不会左右循环滚动。
        // 现在给出无图片数据且左右循环滚动。
        //var enterpriseImgs = enterpriseInf.fileIds2;  // bug: 不是"fileIds2"属性，是"fileIds"属性！！
        if(enterpriseInf){
	    	var enterpriseImgs = enterpriseInf["fileIds2"];
	        if (enterpriseImgs && enterpriseImgs.indexOf(";") > 0) {//多张图片
	        	var imgArr = enterpriseImgs.split(";");
	        	var imgStr = "";
	        	var olStr = "";
	        	for (var i = 0, len = imgArr.length; i < len; i++) {
	        		imgStr += '<div class="item"><img  src="'+urlPath.downloadFileUrl + imgArr[i]+'" ></div>';
	        		var indicator = ""+i;
	        		olStr += '<li data-target="#carousel-example-generic" data-slide-to="'+indicator +'"></li>';
	        	}
	        }
	        if (enterpriseImgs && enterpriseImgs.indexOf(";") < 0) {//一张图片
	        	imgStr = '<div class="item"><img src="'+urlPath.downloadFileUrl + enterpriseImgs+'"></div>';
	        	
	        }
	        if (!enterpriseImgs) {//没有图片
	        	imgStr = '<div class="item">'
	        			+ '<img src="../../images/noPicture.png" alt="暂无图片">'
	        			+'</div>';
	        }
	        $("#image").empty();
	        $("#image").append(imgStr);
	        $("#image").find("div:eq(0)").addClass("active"); 
	        $("#image").find('img').css({'height':400,'width':788});
	        $("#carouselIndicators").empty();
	        $("#carouselIndicators").append(olStr);
	        $("#carouselIndicators").find("li:eq(0)").addClass("active");
	        var lis = $("#carouselIndicators li");
	        for (var i=0;i<lis.length;i++) {
	        	$("#carouselIndicators li:eq("+ i +")").click(function() {
	        		var $this = $(this);
	        		$("#imageShowCarousel").carousel($this.data("slide-to"));
	        	});
	        }
        }
    };
    
    //企业信息填充函数
    var _fillData = function(array,enterpriseInf) {
    		for (var i=0;i<array.length;i++){
    			var obj = $("#"+array[i]);
    			if ( enterpriseInf[array[i]] == null) {
    				obj.text("");
    			} else {
    				obj.text(enterpriseInf[array[i]]);
    			}
    			obj.attr("title",obj.text());
    		}
    };
    
    
    /**
     * 投诉信息表格
     */
    var _initcomplainTable = function() {
    	$complainsTable.jqGrid({
            url      : urlPath.findComplainsByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            postData : {
            	"moudle" : "1",
            	"who"    : "0",
            	"corporateNo" : corporateNo
            },
            colNames : ["投诉单位", "订单编号", "投诉时间", "投诉内容", "操作"],
            colModel : [
                {name: "enterpriseName", index: "1", align: "left",   width: "30px", sortable: false},
                {name: "orderNo",        index: "2", align: "center", width: "30px", sortable: false},
                {name: "updateTime",     index: "3", align: "center", width: "30px", sortable: false},
                {name: "content",        index: "4", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject) {
                		return cellValue;
                	}
                },
                {name: "opt",            index: "9", align: "center", width: "30px", sortable: false, hidden:true,
                    formatter: function (cellValue, options, rowObject) {
                    	var id = rowObject.id;
                    	var srcGoodsId = rowObject.orderId;
                    	var enpName = rowObject.enterpriseName;
                    	var orderNo = rowObject.orderNo;
                    	
                    	var option = "";
                		option = '<p class="jqgrid-handle-p">' +
                		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" '
                			  + 'onclick="complainM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>' +
                		'</p>';
                		
                        return option;
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 5,
            rowList    : [5],
            pager      : "#complainsPager"
        });
	    $(window).resize(function () {
		    $complainsTable.jqGrid({autowidth: false}).setGridWidth($("#complains").width());
        });
    };
    
    // 投诉搜索
    var _searchComplainHandler = function () {
    	$complainsTable.jqGrid('setGridParam', {
            url     : urlPath.findComplainsByPageUrl,
            postData: {"moudle" : "1",
		               "who"    : "0",
		               "corporateNo" : corporateNo
            },
            page    : 1
        }).trigger("reloadGrid");
    	//去除首尾空格
    	$.removeTrim();
    };
    
    
    /**
     * 评价信息表格
     */
    var _initCommentTable = function() {
    	$commentsTable.jqGrid({
            url      : urlPath.findCommentsByPageUrl,
            mtype    : "POST",
            datatype : "JSON",
            postData : {
            	"moudle" : "0",
            	"who"    : "0",
            	"corporateNo" : corporateNo
            },
            colNames : ["评价用户", "订单编号", "评价时间", "星级评分", "操作"],
            colModel : [
                {name: "enterpriseName", index: "1", align: "left",   width: "30px", sortable: false},
                {name: "orderNo",        index: "2", align: "center", width: "15px", sortable: false},
                {name: "updateTime",     index: "3", align: "center", width: "20px", sortable: false},
                {name: "score",          index: "4", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject) {
                		return _showStar(cellValue);
                	}
                },
                {name: "opt",            index: "9", align: "center", width: "30px", sortable: false, hidden:true,
                    formatter: function (cellValue, options, rowObject) {
                    	var id = rowObject.id;
                    	var srcGoodsId = rowObject.orderId;
                    	var enpName = rowObject.enterpriseName;
                    	var orderNo = rowObject.orderNo;
                    	
                    	var option = "";
                		option = '<p class="jqgrid-handle-p">' +
                		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" '
                			  + 'onclick="commentM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>' +
                		'</p>';
                    	
                        return option;
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 5,
            rowList    : [5],
            pager      : "#comments_pager"
        });
	    $(window).resize(function () {
	    	$commentsTable.jqGrid({autowidth: false}).setGridWidth($("#comments_show").width());
        });
    };
    
    // 评价搜索
    var _searchCommentHandler = function () {
    	$commentsTable.jqGrid('setGridParam', {
            url     : urlPath.findCommentsByPageUrl,
            postData: {"moudle"    : "0",
		            	"who"      : "0",
		            	"corporateNo" : corporateNo
            },
            page    : 1
        }).trigger("reloadGrid");
    	//去除首尾空格
    	$.removeTrim();
    };
 
    
    
    
    /***************************************************************投诉记录查看更多********************************************************************/
    /**
     * 显示投诉查看更多
     */
    var _complainMore = function() {
    	_searchComplainMoreHandler();
    	$("#complain_more_table").jqGrid({autowidth: false}).setGridWidth(865);
    	$("#complain_more").showPanelModel('投诉记录');
    };
    
    /**
     * 投诉信息表格
     */
    var _initcomplainMoreTable = function() {
    	$("#complain_more_table").jqGrid({
            mtype    : "POST",
            datatype : "JSON",
            postData : {
            	"moudle" : "1",
            	"who"    : "0",
            	"corporateNo" : corporateNo
            },
            colNames : ["投诉单位", "订单编号", "投诉时间", "投诉内容", "操作"],
            colModel : [
                {name: "enterpriseName", index: "1", align: "left",   width: "30px", sortable: false},
                {name: "orderNo",        index: "2", align: "center", width: "30px", sortable: false},
                {name: "updateTime",     index: "3", align: "center", width: "30px", sortable: false},
                {name: "content",        index: "4", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject) {
                		return cellValue;
                	}
                },
                {name: "opt",            index: "9", align: "center", width: "30px", sortable: false, hidden:true,
                    formatter: function (cellValue, options, rowObject) {
                    	var id = rowObject.id;
                    	var srcGoodsId = rowObject.orderId;
                    	var enpName = rowObject.enterpriseName;
                    	var orderNo = rowObject.orderNo;
                    	
                    	var option = "";
                		option = '<p class="jqgrid-handle-p">' +
                		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" '
                			  + 'onclick="complainM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>' +
                		'</p>';
                		
                        return option;
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 5,
            rowList    : [5],
            pager      : "#complain_more_page"
        });
	    $(window).resize(function () {
	    	$("#complain_more_table").jqGrid({autowidth: false}).setGridWidth($("#complain_more").width()-30);
        });
    };
    
    /**
     * 投诉搜索
     */
    var _searchComplainMoreHandler = function () {
    	$("#complain_more_table").jqGrid('setGridParam', {
            url     : urlPath.findComplainsByPageUrl,
            postData: {"moudle"    : "1",
		               "who"       : "0",
		               "corporateNo" : corporateNo,
            	       "orderNo"   : $("#complain_order_no").val(),
            		   "startTime" : $("#complain_start_time").val(),
            		   "endTime"   : $("#complain_end_time").val()
            },
            page    : 1
        }).trigger("reloadGrid");
    	//去除首尾空格
    	$.removeTrim();
    };
    
    
    
    
    /***************************************************************评价记录查看更多********************************************************************/
    /**
     * 显示评价查看更多
     */
    var _commentMore = function() {
    	_searchCommentMoreHandler();
    	$("#comment_more_table").jqGrid({autowidth: false}).setGridWidth(1060);
    	$("#comment_more").showPanelModel('评价记录');
    };
    
    /**
     * 评价信息表格
     */
    var _initCommentMoreTable = function() {
    	$("#comment_more_table").jqGrid({
            mtype    : "POST",
            datatype : "JSON",
            postData : {
            	"moudle" : "0",
            	"who"    : "0",
            	"corporateNo" : corporateNo
            },
            colNames : ["评价用户", "订单编号", "评价时间", "星级评分", "评价内容", "操作"],
            colModel : [
                {name: "enterpriseName", index: "1", align: "left",   width: "30px", sortable: false},
                {name: "orderNo",        index: "2", align: "center", width: "15px", sortable: false},
                {name: "updateTime",     index: "3", align: "center", width: "20px", sortable: false},
                {name: "score",          index: "4", align: "center", width: "40px", sortable: false,
                	formatter: function(cellValue, options, rowObject) {
                		return _showStar(cellValue);
                	}
                },
                {name: "content",        index: "5", align: "center", width: "20px", sortable: false},
                {name: "opt",            index: "6", align: "center", width: "30px", sortable: false, hidden:true,
                    formatter: function (cellValue, options, rowObject) {
                    	var id = rowObject.id;
                    	var srcGoodsId = rowObject.orderId;
                    	var enpName = rowObject.enterpriseName;
                    	var orderNo = rowObject.orderNo;
                    	
                    	var option = "";
                		option = '<p class="jqgrid-handle-p">' +
                		'<label class="jqgrid-handle-text delete-link" data-func="evaluate-findById" '
                			  + 'onclick="commentM.detail(\''+ id +'\', \''+ srcGoodsId +'\', \''+ enpName +'\', \''+ orderNo +'\')"><span class="img-details"></span>详情</label>' +
                		'</p>';
                    	
                        return option;
                    }
                }
            ],
            loadonce   : false,
            rownumbers : true,
            viewrecords: true,
            autowidth  : true,
            height     : true,
            rowNum     : 5,
            rowList    : [5],
            pager      : "#comment_more_page"
        });
	    $(window).resize(function () {
	    	$("#comment_more_table").jqGrid({autowidth: false}).setGridWidth($("#comment_more").width()-30);
        });
    };
    
    /**comment_more
     * 评价搜索 
     */
    var _searchCommentMoreHandler = function () {
    	$("#comment_more_table").jqGrid('setGridParam', {
            url     : urlPath.findCommentsByPageUrl,
            postData: {"moudle"    : "0",
		               "who"       : "0",
		               "corporateNo" : corporateNo,
            	       "orderNo"   : $("#comment_order_no").val(),
            		   "startTime" : $("#comment_start_time").val(),
            		   "endTime"   : $("#comment_end_time").val()
            },
            page    : 1
        }).trigger("reloadGrid");
    	//去除首尾空格
    	$.removeTrim();
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
    
    
      
      //初始化
    _init();
    
})(jQuery);