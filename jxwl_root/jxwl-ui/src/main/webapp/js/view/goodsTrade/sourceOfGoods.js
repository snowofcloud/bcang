/**
 * @description:物流交易-交易大厅
 * @author: xiaoqx
 * @time: 2016-8-29
 */
$(function(){
	//文档加载完成执行
	var $tradeHallTabs = $("#tradeHall-tabs");
	//tabs切换事件
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
	        case '#goodsSource':
	        	srcGoodManage.init();
	        	break;
            case '#goodsSourceDetail':
            	goodsSourceDetail.init();
                break;
            case '#vehicleSource':
            	vehicleSource.init();
                break;
            case '#leaveMessage':
            	leaveMessage.init();
                break;
            default :
            	break;
        }
    };
    //注册tab点击事件
    $tradeHallTabs.find('a').click(function () {
        var $this = $(this);
        var tabsName = $this.attr('href');
        sessionStorage.setItem("active_li", tabsName);
        _showTabs(tabsName);
    });
    //默认执行第一个tab
    var defaultTab = sessionStorage.getItem("active_li");
    if(defaultTab){
    	_showTabs(defaultTab);
    } else {
    	//默认执行第一个tab
    	$tradeHallTabs.find("li:eq(0)").find("a").click();
    }
});