/**
 * tabs切换事件
 */

$(function(){
	//文档加载完成执行
	var $statisticalTabs = $("#statistical-tabs");
	//tabs切换事件
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
	        case '#wlEnpStatistical':
	        	wlEnpStatistical.init();
	        	break;
            case '#carDistributeStatistical':
            	carDistributeStatistical.init();
                break;
            case '#wayBillStatistical':
            	wayBillStatistical.init();
                break;
            case '#ptStatistical':
            	ptStatistical.init();
                break;
            default :
            	break;
        }
    };
    //注册tab点击事件
    $statisticalTabs.find('a').click(function () {
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
    	$statisticalTabs.find("li:eq(0)").find("a").click();
    }
});