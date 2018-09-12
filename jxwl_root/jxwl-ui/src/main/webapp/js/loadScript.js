;(function(){
	var baseUrl = "/JXWL";
	var jsArray=[];
	/**引用插件类*/
	jsArray.push("/js/lib/jquery/jquery-1.11.2.min.js");	 
	jsArray.push("/js/lib/jquery/jquery-ui.min.js");	 
	jsArray.push("/js/lib/bootstrap-3.3.4/js/bootstrap.min.js");	 
	jsArray.push("/js/lib/jqGrid/jquery.jqGrid.min.js");	 
	jsArray.push("/js/lib/jqGrid/grid.locale-cn.js");	 
	jsArray.push("/js/lib/datePicker/WdatePicker.js")	; 
	jsArray.push("/js/lib/uploadFile/jquery.Huploadify.js")	 ;
	jsArray.push("/js/module/jquery-html5Validate.js");
	/**自己定义工具类*/
	jsArray.push("/js/module/message-2.0.0.js");
	jsArray.push("/js/module/base.js");	  
	jsArray.push("/js/module/tools.js");
	jsArray.push("/js/module/topMenu.js");
	var len =jsArray.length;
	for(var i =0;i< len;i++){
		document.write('<script src="'+ baseUrl + jsArray[i] +'"></script>');
	}
})()