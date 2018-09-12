/**
 * 运单
 */
var waybillMng = (function ($) {
	// 运单数据区域
	var $waybillData   = $("#waybill_data");
	// 运单搜索
	var $waybillSearch = $("#waybill_search");
	var wbiSsearch     = $("#wbi_search");
	
	// 运单模板
	var $waybillHtmlTemplate = $("#waybill_htmlTemplate");
	var $moreWaybillBtn      = $("#more_waybill_btn");
	
	// 清除搜索框文本框内容
	var $clearText  = $(".rela_span span[class='clear-x']");
	
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
                "corporateNo": ""
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
    	wbiSsearch.bind("click", _searchHandel);
    	// 更多运单
    	$moreWaybillBtn.bind("click", _initMoreData);
    	// 清除搜索框内容
    	$clearText.click(function() {
    		$waybillSearch.val("");
    	});
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
    
    _init();
    
    return{
    	waybillList : _init,
    	detailList  : _detailList
    };
})(jQuery);