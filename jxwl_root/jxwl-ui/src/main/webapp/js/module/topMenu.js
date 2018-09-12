/**
 * @description:  功能类，顶部菜单栏
 * @author: wl
 * @time: 2016-07-25
 */



var topMenuMag = (function(){
	//模块缓存变量
    var $exit = $('.exit');
    var $changePwd = $('#updatePwd');
    var $updatePwdform = $("#updatePwd-form");
    var $fullScreenSpan = $("#fullScreenSpan");
    
    var $onOffRemind = $('.onOffRemind');
    
    //车辆状态（总共 在线 离线）统计 变量
    var $allCar = $("#allCar");
    var $onLineCar = $("#onLineCar");
    var $offLineCar = $("#offLineCar");
    //请求路径
    var urlPath = {
    	exitLoginUrl: '/JXWL/rest/user/exit',
    	changePwdUrl:'/JXWL/rest/user/modifypassword',
    	allCarUrl:'/JXWL/rest/dangerVehicle/findTotal',//车辆总数
    	onLineCarUrl:'/JXWL/rest/dangerVehicle/findOnLine'//在线车辆
    };
    //数据初始化
    var _init = function () {
    	$exit.bind("click",_exitLogin);
    	$onOffRemind.bind("click",_onOffRemind); //车辆上下线提醒
    	$changePwd.bind("click",_changePwd);
    	
    	$fullScreenSpan.bind("click",_homeTofull);
    	
    	
    	_carAmountinit();
    	 //表单事件绑定
    	$updatePwdform.html5Validate(function () {
    		
    		_submitNewPwd();
        },{
            validate: function() {
                // 下面这些就是额外的一些特殊验证
                if ($("#psdNewpassword").val() !== $("#psdSurePassword").val()) {
                    $("#psdSurePassword").testRemind("前后密码不一致");
                    return false;  
                }
                return true;
           }
        });
    };
    var _carAmountinit = function(){
    	 var allAmount = 0;
    	 var onLineAmount = 0;
    	 $.ajax({
             url: urlPath.allCarUrl,
             type: 'POST',
             async: false
         }).done(function (json) {
             if (json.code === 1) {
            	 allAmount = json.data;
            	 $allCar.text(json.data);
             } else {
            	 $.validateTip(json);
             }
         });
    	 
    	 $.ajax({
             url: urlPath.onLineCarUrl,
             type: 'POST',
             async: false
         }).done(function (json) {
             if (json.code === 1) {
            	 $onLineCar.text(json.data);
            	 $offLineCar.text(allAmount - json.data);
             } else {
            	 $.validateTip(json);
             }
         });
    }
    //首页和大屏之间跳转
    var _homeTofull = function(){
    	var fullUrl = location.pathname;
    	if(fullUrl!='/JXWL/view/goodsTrade/fullScreen.html' && fullUrl != '/JXWL/view/goodsTrade/fullScreenTwo.html' 
    		&& fullUrl != '/JXWL/view/goodsTrade/fullScreenThree.html'){
    		location.pathname = '/JXWL/view/goodsTrade/fullScreen.html';
    	}else{
    		location.pathname = '/JXWL/view/index.html';
    	}
    }
   //注销登录 
   var _exitLogin =function(){
	   Message.confirm({
         Msg: "确定退出系统?",
         okSkin: 'danger',
         iconImg: 'question',
         isModal: true
     }).on(function (flag) {
         if (flag) {
             $.ajax({
                 url: $.exitLoginUrl,
                 type: 'post',
                 async: false
             }).done(function (json) {
                 if (json.code === 1) {
                     window.sessionStorage.clear();
//                   window.location.href = $.frontPath;
                   window.location.href = 'http://172.31.2.193:8888/sso-server/logout?service=http://localhost:8080/JXWL/';
                 } else {
                     $.validateTip(json);
                 }
             });
         }
     });
   };
   
   //关闭或开启车辆上下线提醒 
   var _onOffRemind =function(){	   
	   var flag = sessionStorage.offline;
		 if(flag == "remind"){
			 sessionStorage.setItem("offline", 'notRemind');			 
			 }
		 else {
			 sessionStorage.setItem("offline", 'remind');			 
		 }
	     //本次登录不再提醒   新加
   };
   
   //修改密码弹框
   var _changePwd =function(){
	   $("#changePwd-panel").showPanelModel('修改密码'); 
   };
 //修改密码提交   
  var  _submitNewPwd = function(){
	  var data= $("#updatePwd-form").serialize();
	  $.ajax({
		  url:urlPath.changePwdUrl,
		  data:data,
		  type:'post',
		  async:false
	  }).done(function (json){
		  if(json.code === 1){
			  $("#changePwd-panel").closePanelModel("false");
			  Message.alert({Msg: "修改密码成功"}).on(function(){
				  localStorage.clear();
				  window.location.href = $.frontPath;
            });
		  } else{
			  Message.alert({Msg: "修改失败，请联系管理员。"});
			  //Message.alert({Msg: json.msg});
		  } 
	  });
  };
  //修改车辆总数 在线数量 离线数量
  var _carAmountFunc = function(all, on, off) {
		//获取对象
		var $all = $("#allCar");
		var $on = $("#onLineCar");
		var $off = $("#offLineCar");
		//得到目前值
		var oldAll = parseInt($all.text());
		var oldOn = parseInt($on.text());
		var oldOff = parseInt($off.text());
      //计算
		$all.text(oldAll + all);
		$on.text(oldOn + on);
		$off.text(oldOff + off);
	};
  //政府角色
  var GOVERNMENT_USER = "e5348d777c2a48dd98cc7e19621d3193";
  //物流企业角色
  var LOGISTICS_ENTERPRISE_USER ="7545f7983ac84654951f64efba29677a";
  //化工企业角色
  var CHEMICAL_ENTERPRISE_USER ="f9be1f18fdea412cbf7bcf4a4a6809af";
  //判断是否是政府角色
  var _isGovernmentUser = function(){
      	var user = $.getUserInfo();
      	var roleCode = user.roleCodes;
      	if( roleCode.indexOf(GOVERNMENT_USER) != -1){
      		return true;
      	}
      	return false; 
      };
  //判断是否是物流角色    
  var _isLogisticsEnterpriseUser = function(){
    	var user = $.getUserInfo();
    	var roleCode = user.roleCodes;
    	if( roleCode.indexOf(LOGISTICS_ENTERPRISE_USER)!= -1){
    		return true;
    	}
    	return false; 
    };
    //判断是否是化工角色
    var _isChemicalEnterpriseUser = function(){
      	var user = $.getUserInfo();
      	var roleCode = user.roleCodes;
      	if( roleCode.indexOf(CHEMICAL_ENTERPRISE_USER)!= -1){
      		return true;
      	}
      	return false; 
      };    
    _init();
    
    //返回函数
    return {
    	exitLogin: _exitLogin,
    	onOffRemind: _onOffRemind,
    	carAmountFunc: _carAmountFunc,
    	isGovernmentUser:_isGovernmentUser,
    	isLogisticsEnterpriseUser:_isLogisticsEnterpriseUser,
    	isChemicalEnterpriseUser:_isChemicalEnterpriseUser
    };
})(jQuery);


