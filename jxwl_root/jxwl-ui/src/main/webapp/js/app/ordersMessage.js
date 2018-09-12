/**
 * 订单
 */
var orders = (function ($) {
	// 订单数据区域
	var $orderData    = $("#order_data");
	// 订单搜索
	var $orderSearch  = $("#order_search");
	var $ordersSearch = $("#orders_search");
	
	// 订单模板
	var $orderHtmlTemplate = $("#order_htmlTemplate");
	var $moreOrderBtn      = $("#more_order_btn");
	
	// 清除搜索框文本框内容
	var $clearText  = $(".rela_span span[class='clear-x']");
	
	var clickMoreBtnCount = 1;
    var urlPath = {
    	findByPageeUrl : $.backPath + "/orderManage/findByPageForWeixin",
        findByIdUrl    : $.backPath + "/orderManage/findByIdForWeixin/",
        findTradeObj   : $.backPath + "/orderManage/findTradeObj/"
    };
	
    // 获取订单列表
    var _initData = function (page, rows, orderNo) {
    	var template = '';
        var htmlList = '';
        //清空历史数据
        $orderData.empty();
    	var data = null;
        //请求后端数据
        $.ajax({
            url: urlPath.findByPageeUrl,
            type: "POST",
            data: {
                "page"       : page,
                "rows"       : rows,
                "orderNo"    : orderNo,
                "corporateNo": ""
            },
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code === 1) {
                	// 判断隐藏更多按钮
                    if (clickMoreBtnCount * 10 >= result.records) {
                    	$moreOrderBtn.text("数据已加载完").attr("disabled", "disabled");
                    } else {
                    	$moreOrderBtn.text("点击加载更多").removeAttr("disabled");
                    }
                    data = result.rows;
                }
            }
        });
        //列表拼接组装
        $.each(data, function (index, item) {
        	$orderHtmlTemplate.find(".content")
        		.attr("onclick", "orders.detailList('" + item["ID"] + "', '" + item["HG_CORPORATE_NO"] + "')");
            //模板拼接 
            htmlList += $orderHtmlTemplate.temp(item);
        });
        //页面追加
        $orderData.empty().append(htmlList);
        
        $("#enterprise_order_panel").modal("open");
    };
    
    // 初始化
    var _init = function () {
    	_initData(1, 10, "");
        _bindMethod();
    };
    
    // 绑定方法
    var _bindMethod = function () {
    	// 企业名称搜索
    	// $orderSearch.bind("blur", _searchHandel);
    	$ordersSearch.bind("click", _searchHandel);
    	// 更多订单
    	$moreOrderBtn.bind("click", _initMoreData);
    	// 清除搜索框内容
    	$clearText.click(function() {
    		$orderSearch.val("");
    	});
    };

    // 订单搜索
    var _searchHandel = function () {
        var orderNo = $orderSearch.val();
        _initData(1, 10, orderNo);
    };
    
    // 订单详情
    var _detailList = function(id, tradeObjCode) {
		var obj = $.getData(urlPath.findByIdUrl + id + "/" + tradeObjCode, "get");
        var htmlTemplate = $("#order-detail-htmlTemplate").clone(true);
        var html = htmlTemplate.temp(obj.data.order);
        $("#order_detail_data").empty().append(html);
        // 订单物品
        var goodsTemplate = "";
        var $goodsHtml = $("#order_detail_goods_htmlTemplate");
        var goodsInfos = obj.data.order.goodsInfos;
        for (var index in goodsInfos) {
        	var item = goodsInfos[index];
        	goodsTemplate += $goodsHtml.temp(item);
        }
        $("#order_detail_data").append(goodsTemplate);
        // 根据交易状态改变颜色
        var tradeStatus = $("#trade_status").text();
        if (tradeStatus && tradeStatus == "110001006") {
        	$("#trade_status").text("交易完成").css("color", "#17D045");
        } else if (tradeStatus && tradeStatus == "110001005") {
        	$("#trade_status").text("已签订").css("color", "#0099FF");
        }
        // 订单评价、投诉
        var comcom = obj.data.comcom;
        var comscore = 0;
        if (comcom && 0 < comcom.length) {
        	for (var index in comcom) {
        		var o = comcom[index];
        		if (o) {
        			o.tradeObject = obj.data.order.tradeObject;
        			if ("0" == o.type) { // 评价
        				var commentHtml = $("#order_comment_htmlTemplate").temp(o);
        				$("#order_detail_data").append(commentHtml);
        				comscore = o.score;
        				$("#com_score").raty({readOnly: true, score: comscore });
        				$("#com_score").append('<span class="com_score">'+comscore+'</span>');
        			} else if ("1" == o.type) {
        				var complainHtml = $("#order_complain_htmlTemplate").temp(o);
        				$("#order_detail_data").append(complainHtml);
        			}
        		}
        	}
        }
        
        $("#order_detail_panel").modal("open");

    };

    //加载更多数据
    var _initMoreData = function () {
        clickMoreBtnCount++;
        var clickNum = clickMoreBtnCount * 10;
        _initData(1, clickNum, "");
    };
    
    //初始化调用
    _init();
    
    return{
    	ordersList : _init,
    	detailList : _detailList
    };
})(jQuery);