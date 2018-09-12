/*车辆列表*/
var carMessage = (function ($) {
	//变量
	var $carMessageData         = $("#carMessageData");
	var $businessNameSearch     = $("#carNumberSearch");
	var $carMessageHtmlTemplate = $("#carMessageHtmlTemplate");
	var $moreMsgBtn = $("#moreMsg-btn");
	var $carSsearch = $("#car_search");
	// 清除搜索框文本框内容
	var $clearText  = $(".rela_span span[class='clear-x']");
	
	var clickMoreBtnCount = 1;
    //请求路径
    var urlPath = {
        findByPageUrl: $.backPath + "/dangerVehicle/findByPage",
        findByIdUrl: $.backPath + "/dangerVehicle/findById/"
    };
    //初始化数据
    var _initData = function (page, row, licenseNo) {
    	var template = '';
        var htmlList = '';
        //清空历史数据
        $carMessageData.empty();
        var data = [];
        //请求后端数据
        $.ajax({
            url: urlPath.findByPageUrl,
            type: "POST",
            data: {
                "page": page,
                "rows": row,
                "licencePlateNo": licenseNo
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
      //列表凭借组装
        $.each(data, function (index, item) {
        	$carMessageHtmlTemplate.find(".content").attr("onclick", "carMessage.detailList('" + item["id"] + "')");
            //模板拼接
            htmlList += $carMessageHtmlTemplate.temp(item);
        });
        //页面追加
        $carMessageData.empty().append(htmlList);
    };

    //搜索
    var _searchHandel = function () {
        var businessName = $businessNameSearch.val();
        _initData(1, 10, businessName);  //按车牌号查找应该调用的是findById啊，就是只查出一条而不是多条。。。。。。
    };


    //初始化
    var _init = function () {
    	_initData(1, 10, "");
        _bindMethod();
    };

    //绑定方法
    var _bindMethod = function () {
    	// $businessNameSearch.bind("blur", _searchHandel);
    	$carSsearch.bind("click", _searchHandel);
    	$moreMsgBtn.bind("click", _initMoreData);
    	// 清除搜索框内容
    	$clearText.click(function() {
    		$businessNameSearch.val("");
    	});
    };
    
    //详情
    var _detailList = function(id){
		var obj = $.getDataTwo(urlPath.findByIdUrl + id, "get");
        var htmlTemplate = $("#carDetailHtmlTemplate").clone(true);
        var html = htmlTemplate.temp(obj);  //var html = htmlTemplate.temp(obj.data);
        $("#carDetailBd").empty().append(html);
        $("#carDetailPanel").modal("open");
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

