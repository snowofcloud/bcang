/**
 * @description:评价管理--加载页面
 * @author: zz
 * @time: 2016-12-19
 */
$(function(){
	//文档加载完成执行
	var $evaluateTabs = $("#evaluate-tabs");
	//tabs切换事件
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
	        case '#evaluate':
	        	commentM.init();
	        	break;
            case '#complain':
            	complainM.init();
                break;
            default :
            	break;
        }
    };
    
    //注册tab点击事件
    $evaluateTabs.find('a').click(function () {
        var $this = $(this);
        var tabsName = $this.attr('href');
		sessionStorage.setItem("active_li", tabsName);
        _showTabs(tabsName);
    });
    var defaultTab = sessionStorage.getItem("active_li");
    if(defaultTab){
    	_showTabs(defaultTab);
    } else {
    	//默认执行第一个tab
        $evaluateTabs.find("li:eq(0)").find("a").click();
    }
});