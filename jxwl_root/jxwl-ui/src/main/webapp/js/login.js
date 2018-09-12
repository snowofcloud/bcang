$(function () {
    var $inputError = $("#input-error");
    $("input").on("keyup", function (e) {
        var input = $(this);
        if (input.val()) {
            $inputError.hide();
        }
    });
    document.onkeyup = function (e) {
        if (e == null) {
            _key = event.keyCode;
        } else {
            _key = e.which;
        }
        if (_key == 13) {
            $("#submitBtn").text("登录中...").addClass("disabled");
            setTimeout("doLogin.submit()", 500);
        }
    };
    //验证码
    document.getElementById('codeImg').src = doLogin.config.validationIcon();
});

var doLogin = {
    config: {
        temp_array: {},
        LoginUrl: '/JXWL/rest/user/login',
        validationIcon: function(){return "/JXWL/rest/user/validCode?hostId=" + doLogin.randomDateString().replace('.', '');}
    },
    isPC: function(){
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
    },
    randomDateString : function() {
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth();
		var day = date.getDate();
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		var milliseconds = date.getMilliseconds();
		var random = Math.random(1000);
		return year + "" + month + "" + day + "" + hours + "" + minutes + "" + seconds + "" + milliseconds + "" + random;
	},
    refresh: function(obj) {
        obj.src = doLogin.config.validationIcon();
    },
    storage: function (json) {
        sessionStorage.setItem("userInfo", JSON.stringify(json["userInfo"]));
        sessionStorage.setItem("module", JSON.stringify(json["module"]));
        sessionStorage.setItem("offline", 'remind'); //车辆下线提醒标志
    },
    submit: function () {
        var $loginForm = $("#login-form");
        var $inputError = $("#input-error");
        var $submitBtn = $("#submitBtn");
        var userName = $loginForm.find('input[name=userName]');
        var userPass = $loginForm.find('input[name=userPass]');
        var validCode = $loginForm.find('input[name=validCode]');

        if (!userName.val()) {
            $inputError.show().html("用户名必须填写");
            $submitBtn.text("登录").removeClass("disabled");
            userName.focus();
            return;
        }
        if (!userPass.val()) {
            $inputError.show().html("密码必须填写");
            $submitBtn.text("登录").removeClass("disabled");
            userPass.focus();
            return;
        }
        if (!validCode.val()) {
            $inputError.show().html("验证码必须填写");
            $submitBtn.text("登录").removeClass("disabled");
            validCode.focus();
            return;
        }

        $.ajax({
            type: "POST",
            url: doLogin.config.LoginUrl,
            data: {
            	account: userName.val(),
            	password: userPass.val(),
            	validateCode: validCode.val(),
            	//"isPC": doLogin.isPC(),
            	"hostId": $('#codeImg').attr("src").split("=")[1]
            },
            async: false,
            dataType: "JSON",
            beforeSend: function () {
            	$submitBtn.text("登录中...").addClass("disabled");
            }
        }).done(function (json) {
        	var data = json.data;
            if (json.code === 1) {
                if (data.module) {
                	doLogin.storage(json.data);
                    window.location.href = data.url + "?id=" + data.module.id;
                } else {
                    $submitBtn.text("登录").removeClass("disabled");
                    window.location.href = data.url;
                }
            } else{ //json.code = 2,3,5 的情况
            	document.getElementById("codeImg").src = doLogin.config.validationIcon();
                $inputError.show().html(json["msg"]);
                validCode.focus();
                $submitBtn.text("登录").removeClass("disabled");
                window.location.href = data.url;
            }
        }).fail(function (json) {
            $submitBtn.text("登录").removeClass("disabled");
            Message.alert({ Msg : "网络异常请重试！",isModal : false });
        });
    }
};
