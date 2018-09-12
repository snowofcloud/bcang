
var synchronize = (function(){
    // 表格
    var $userSynchronizeTable = $("#userSynchronizeTable");
    
	var $userSynchronizeAddBtn = $("#userSynchronize-addBtn");
	var $userSynchronizeForm = $("#userSynchronizeForm");
	var $userSynchronizeUserNameSearch = $("#userSynchronize-userName-search");
	var $userSynchronizeSearchBtn = $("#userSynchronize-searchBtn");
	var loadOne = false;
    var urlPath = {
    		findByPageUrl: $.backPath + "/userSynchronize/findNewUserByPage",
    		//findByIdUrl : $.backPath + '/synchronize/findById/',
    		//saveUrl : $.backPath + '/synchronize/save/',
    		//deleteUrl: $.backPath + '/synchronize/delete/',
    		synchronizeUrl: $.backPath + '/userSynchronize/synchronize'
    };
    
    var _init = function () {
    	$userSynchronizeSearchBtn.bind("click",function() {
    		var userName = $userSynchronizeUserNameSearch.val()==""?null:$userSynchronizeUserNameSearch.val();
    		_searchHandler(userName);
		});
        _bindMethods();
        if (!loadOne) {
    	_initTable();
    	loadOne = !loadOne;
        }
    };
    
  // 初始化表格
    var _initTable = function () {
    	$userSynchronizeTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            colNames: ['id', "登录账号", "用户名", "手机号", "同步时间"],
            colModel: [
                {name: "userId", index: "1", align: "center",sortable: false, hidden: true},
                {name: "account", index: "2", align: "center",sortable: false},
                {name: "name", index: "3", align: "center", sortable: false},
                {name: "mobile", index: "4", align: "center",sortable: false},
                {name: "updateTime", index: "5", align: "center",sortable: false}
            ],
            loadonce: false,
            viewrecords: true,
            autowidth: true,
            height: true,
            multiselect: false,
            multiboxonly: false,
            rowNum: 10,
            rowList: [5, 10, 15],
            pager: "#userSynchronizePager",
            gridComplete:function(){
            	// 表格中按钮权限配置
            	 $.initPrivg();
            }
        }).resizeTableWidth();
    };
    // 绑定方法
    var _bindMethods = function(){
    	$userSynchronizeAddBtn.bind("click", _userSynchronize);
    	$userSynchronizeTable.trigger("reloadGrid");
    };
    
    //用户同步
    var _userSynchronize = function(){
    	var url = $.getData(urlPath.synchronizeUrl);
    	$userSynchronizeTable.trigger("reloadGrid");
    }
    
    //查询数据
    var _searchHandler = function(userName) {
    	$userSynchronizeTable.jqGrid('setGridParam', {
			url : urlPath.findByPageUrl,
			postData : {
				"name" : userName
			},
			page : 1
		}).trigger("reloadGrid");
	};
	_init();
	
	return {
		userSynchronize:_userSynchronize
	};
	
})(jQuery);