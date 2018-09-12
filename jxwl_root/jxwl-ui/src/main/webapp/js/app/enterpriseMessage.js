/**
 * 订单
 */
var ordersMng = (function ($) {
	// 订单数据区域
	var $orderData    = $("#order_data");
	// 订单搜索
	var $orderSearch  = $("#order_search");
	var $ordersSearch = $("#orders_search");
	// 清除搜索框文本框内容
	var $clearText    = $(".rela_span span[class='clear-x']");
	
	// 订单模板
	var $orderHtmlTemplate = $("#order_htmlTemplate");
	var $moreOrderBtn      = $("#more_order_btn");
	
	var clickMoreBtnCount = 1;
    var urlPath = {
    	findByPageeUrl : $.backPath + "/orderManage/findByPageForWeixin",
        findByIdUrl    : $.backPath + "/orderManage/findByIdForWeixin/"
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
                "corporateNo": $("#corporate_noo").text()
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
        // 列表拼接组装
        $.each(data, function (index, item) {
        	$orderHtmlTemplate.find(".content")
        		.attr("onclick", "ordersMng.detailList('" + item["ID"] + "', '" + item["HG_CORPORATE_NO"] + "')");
            // 模板拼接 
            htmlList += $orderHtmlTemplate.temp(item);
        });
        // 页面追加
        $orderData.empty().append(htmlList);
        // 根据交易状态改变颜色
        $(".order-NoStatus:contains('交易完成')").parent().css("color", "#17D045");
        $(".order-NoStatus:contains('已签订')").parent().css("color", "#0099FF");
        
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
        var tradeStatus = $("#trade-status").text();
        if (tradeStatus && tradeStatus == "110001006") {
        	$("#trade-status").text("交易完成").css("color", "#17D045");
        } else if (tradeStatus && tradeStatus == "110001005") {
        	$("#trade-status").text("已签订").css("color", "#0099FF");
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
        				$("#com_score").append('<div class="com_score">'+comscore+'</div>');
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
    
    return{
    	ordersList : _init,
    	detailList : _detailList
    };
})(jQuery);





/////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * 运单
 */
var waybillMng = (function ($) {
	// 运单数据区域
	var $waybillData   = $("#waybill_data");
	// 运单搜索
	var $waybillSearch = $("#waybill_search");
	var $wbiSearch     = $("#wbi_search");
	// 清除搜索框文本框内容
	var $clearText    = $(".rela_span span[class='clear-x']");
	
	// 运单模板
	var $waybillHtmlTemplate = $("#waybill_htmlTemplate");
	var $moreWaybillBtn      = $("#more_waybill_btn");
	
	var clickMoreBtnCount = 1;
    var urlPath = {
    	findByPageeUrl : $.backPath + "/wayBill/findByPageForWeixin",
        findByIdUrl    : $.backPath + "/wayBill/findById/",
        findGoodsUrl   : $.backPath + "/wayBill/goodsFindByPage/"
    };
	
    // 获取运单列表
    var _initData = function (page, rows, waybillNo) {
    	var template = '';
        var htmlList = '';
        // 清空历史数据
        $waybillData.empty();
    	var data = null;
        // 请求后端数据
        $.ajax({
            url: urlPath.findByPageeUrl,
            type: "POST",
            data: {
                "page"       : page,
                "rows"       : rows,
                "waybillNo"  : waybillNo,
                "corporateNo": $("#corporate_noo").text()
            },
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code === 1) {
                  	// 判断隐藏更多按钮
                    if (clickMoreBtnCount * 10 >= result.records) {
                    	$moreWaybillBtn.text("数据已加载完").attr("disabled", "disabled");
                    } else {
                    	$moreWaybillBtn.text("点击加载更多").removeAttr("disabled");
                    }
                    data = result.rows;
                }
            } 
        });
        // 列表拼接组装
        $.each(data, function (index, item) {
        	$waybillHtmlTemplate.find(".content")
        		.attr("onclick", "waybillMng.detailList('" + item["ID"] + "')");
            //模板拼接 
            htmlList += $waybillHtmlTemplate.temp(item);
        });
        // 页面追加
        $waybillData.empty().append(htmlList);
        // 根据运单状态改变颜色
        $(".waybill-pst:contains('已完成')").parent().css("color", "#17D045");
        $(".waybill-pst:contains('取货中')").parent().css("color", "#0099FF");
        
        $("#enterprise_waybill_panel").modal("open");
    };
    
    // 初始化
    var _init = function () {
    	_initData(1, 10, "");
        _bindMethod();
    };
    
    // 绑定方法
    var _bindMethod = function () {
    	// 企业名称搜索
    	// $waybillSearch.bind("blur", _searchHandel);
    	$wbiSearch.bind("click", _searchHandel);
    	// 清除搜索框内容
    	$clearText.click(function() {
    		$waybillSearch.val("");
    	});
    	// 更多运单
    	$moreWaybillBtn.bind("click", _initMoreData);
    };

    // 运单搜索
    var _searchHandel = function () {
        var waybillNo = $waybillSearch.val();
        _initData(1, 10, waybillNo);
    };
    
    // 运单详情
    var _detailList = function(id, tradeObjCode) {
		var obj   = $.getData(urlPath.findByIdUrl + id, "GET");
		var goods = null;
        $.ajax({
            url: urlPath.findGoodsUrl,
            type: "POST",
            data: {
                "page" : 1,
                "rows" : 100000000,
                "id"   : id
            },
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code === 1) {
                	goods = result.rows;
                }
            } 
        });
		
        var htmlTemplate = $("#waybill-detail-htmlTemplate").clone(true);
        var html = htmlTemplate.temp(obj.data);
        $("#waybill_detail_data").empty().append(html);
        // 运单物品
        var goodsTemplate = "";
        var $goodsHtml = $("#waybill_detail_goods_htmlTemplate");
        for (var index in goods) {
        	var item = goods[index];
        	goodsTemplate += $goodsHtml.temp(item);
        }
        $("#waybill_detail_data").append(goodsTemplate);
        
        $("#waybill_detail_panel").modal("open");

    };

    //加载更多数据
    var _initMoreData = function () {
        clickMoreBtnCount++;
        var clickNum = clickMoreBtnCount * 10;
        _initData(1, clickNum, "");
    };
    
    return{
    	waybillList : _init,
    	detailList  : _detailList
    };
})(jQuery);



/////////////////////////////////////////////////////////////////////////////////////////////////




/**
 * 企业列表
 */
var enterpriseMessage = (function ($) {
	// 企业列表数据
	var $enterpriseMessageData  = $("#enterpriseMessageData");
	// 企业列表搜索
	var $businessNameSearch     = $("#businessName-search");
	var $enpSearch              = $("#enp_search");
	// 企业列表数据模板
	var $enterpriseHtmlTemplate = $("#enterpriseMessage-htmlTemplate");
	// 企业列表查看更多
	var $moreMsgBtn             = $("#moreMsg-btn");
	// 订单按钮
	var $orderSearchBtn   = $("#order_search_btn");
	// 运单按钮
	var $waybillSearchBtn = $("#waybill_search_btn");
	// 清除搜索框文本框内容
	var $clearText    = $(".rela_span span[class='clear-x']");
	
	var clickMoreBtnCount = 1;
    //请求路径
    var urlPath = {
        findByPageUrl    : $.backPath + "/logisticst/findByPage",
        findByIdUrl      : $.backPath + "/logisticst/findById/",
        findByDictUrl    : $.backPath + "/logisticst/findDictByCode/105001"
    };
    //var crossDomainType4Enter = $.getData(urlPath.findByDictUrl, "get"); //这个发送的仍然是post请求！
    var crossDomainType4Enter = $.getDataTwo(urlPath.findByDictUrl);
    // 企业列表初始化
    var _initData = function (page, rows, searchKey) {
    	var template = '';
        var htmlList = '';
        //清空历史数据
    	$enterpriseMessageData.empty();
    	var data = null;
        //请求后端数据
        $.ajax({
            url: urlPath.findByPageUrl,
            type: "POST",
            data: {
                "page" : page,
                "rows" : rows,
                "enterpriseType" : "106001001",
                "enterpriseName" : searchKey,
                "isPC": isPC()
            },
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code === 1) {
                	// 判断隐藏更多按钮
                    if (clickMoreBtnCount * 10 >= result.records) {
                    	$moreMsgBtn.text("数据已加载完").attr("disabled", "disabled");
                    } else {
                    	$moreMsgBtn.text("点击加载更多").removeAttr("disabled");
                    }
                    data = result.rows;
                }
            }
        });
        //列表拼接组装
        $.each(data, function (index, item) {
        	$enterpriseHtmlTemplate.find(".content")
        		.attr("onclick", "enterpriseMessage.detailList('" + item["id"] + "')");
            //模板拼接
            htmlList += $enterpriseHtmlTemplate.temp(item);
        });
        //页面追加
        $enterpriseMessageData.empty().append(htmlList);
        
    };
    
    var isPC = function() {
    	var userAgent = navigator.userAgent.toLowerCase();
    	var isIpad = userAgent.match(/ipad/i) == 'ipad';
    	var isIphoneOS = userAgent.match(/iphone os/i) == 'iphone os';
    	var isMidp =  userAgent.match(/midp/i) == 'midp';
    	var isUC7 =  userAgent.match(/rv:1.2.3.4/i) == 'rv:1.2.3.4';
    	var isUC = userAgent.match(/ucweb/i) == 'ucweb';
    	var isAndroid = userAgent.match(/android/i) == 'android';
    	var isCE = userAgent.match(/windows ce/i) == 'windows ce';
    	var isWM = userAgent.match(/windows mobile/i) == 'windows mobile';
    	if(isIpad || isIphoneOS || isMidp || isUC7 || isUC || isAndroid || isCE || isWM){
    		return 0;
    	}else{
    		return 1;
    	}
    };
    
    //初始化
    var _init = function () {
    	_initData(1, 10, "");
        _bindMethod();
        
    };

    //绑定方法
    var _bindMethod = function () {
    	// 企业名称搜索
    	$enpSearch.bind("click", _searchHandel);
    	// $businessNameSearch.bind("blur", _searchHandel);
    	// 更多企业
    	$moreMsgBtn.bind("click", _initMoreData);
    	// 清除搜索框内容
    	$clearText.click(function() {
    		$businessNameSearch.val("");
    	});
    	// 订单查询click
    	$orderSearchBtn.bind("click", function() {
    		ordersMng.ordersList();
    	});
    	// 运单查询click
    	$waybillSearchBtn.bind("click", function() {
    		waybillMng.waybillList();
    	});
    };
    
    // 企业名称搜索
    var _searchHandel = function () {
        var businessName = $businessNameSearch.val();
        _initData(1, 10, businessName);
    };

    //详情
    var _detailList = function(id){
		var obj = $.getData(urlPath.findByIdUrl + id, "get");
        var htmlTemplate = $("#enterprise-detail-htmlTemplate").clone(true);
        obj.data["crossDomainType4Enter"]  = $.getDicNameByCode(obj.data["crossDomainType4Enter"], crossDomainType4Enter);
        var html = htmlTemplate.temp(obj.data);
        $("#enterprise-detail-bd").empty().append(html);
        $("#enterprise-detail-panel").modal("open");
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
    	detailList : _detailList
    };
})(jQuery);



(function() {
	var _init = function() {
		_checkLogin();
	};
    //判断登录是否失效失效重新加载
    var _checkLogin = function() {
    	setInterval(function() {
    		var cookieName = "token-hx";
    		var cookieString = document.cookie;
    		var flag = cookieString.indexOf(cookieName);
    		if(flag == -1){
    			//内网环境
    			window.location.href = $.frontPath;
    			//大数据中心
    			/*window.location.href ='http://172.31.2.193:8888/sso-server/logout?service=http://localhost:8080/JXWL/appview/enterpriseMessage.html';*/
    		}
		}, 20000);
	};
    _init();
    return{
    	checkLogin : _checkLogin,
    	init       : _init
    };
})(jQuery);



