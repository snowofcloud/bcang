/**
 * @description: 工具类，基础base类
 * @author: 刘明明
 * @time: 2016-6-20
 */

$(function(){
    /*附件上传，ie9特殊处理*/
    ie9fileUpload.ie9filemultiple();
    /*多文件上传 选中文件就提交文件 将submit 按钮隐藏了 */
    $("#ieuploadsfile").click(function(){
    	document.getElementById("ieuploadsfile").value="";
    });
	
});

/*ie9附件处理*/
var ie9fileUpload = {
		 /*ie9单文件删除附件*/
	    ie9fileDeleteEdit : function(id, url){
	     	 var fileid = $(id).parent("li").attr("fileid");
	     	 Message.confirm({
	              Msg: $.msg.sureDelete,
	              okSkin: 'danger',
	              iconImg: 'question',
	              isModal: true
	          }).on(function (flag) {
	              if (flag) {
	                  $.ajax({
	                      url: url,
	                      dataType: 'json',
	                      type: "POST",
	                      async: false,
	                      data: {"ids":fileid},
	                      success: function (data) {
	                          if (data["code"] === 1) {
	                         	 $(id).parent("li").remove();
	                              Message.show({Msg: "删除成功", isModal: false});
	                          } else if (data["code"] === 3) {
	                              Message.alert({Msg: data["msg"], iconImg: "warning", isModal: false});
	                          } else {
	                              Message.alert({Msg: "删除失败", isModal: false});
	                          }
	                      },
	                      error: function () {
	                          Message.alert({Msg: "失败", isModal: false});
	                      }
	                  });
	              }
	          });
	     },
	     /*附件上传及返回数据处理*/
	     filemultipleform : function(showid, url){
	 		$('#fileIframe_'+showid+'').on('load',function(){
	     		var showName = $('#showFileListMul_'+showid+'');
	 			/*var responseTextbody = $(window.frames['multipatFile'].document.body);
	 			var responseText = responseTextbody.context.textContent;*/
	     		var responseTextbody = document.getElementById('fileIframe_'+showid+'');
	     		var responseText = $(responseTextbody.contentDocument.body).html();
	 			var responseData = JSON.parse(responseText);
	 			if(responseData.code == 1){
	 				//成功
	 				var filenameArray = responseData.data.names;
	 				var fileids = responseData.data.ids;
	 				for(var i=0;i<filenameArray.length;i++){
	 					if(filenameArray){
	 						var content = '<li fileID='+fileids[i]+'>' + 
	 									  '<span class="iefilename">'+filenameArray[i] + '</span>'+
	 									  '<a href="javascript:void(0);" class="delfilebtn" onclick="ie9fileUpload.ie9fileDeleteEdit(this,\''+url+'\')">&nbsp;&nbsp;x</a></li>';
	 						showName.append(content);
	 					}
	 				}
	 			}else{
	 				Message.alert({Msg:"上传失败，请重新上传", isModal: false});
	 			}
	 		});
	     },
		ie9filemultiple : function(){
			var userAgent = navigator.userAgent;
			var isIe = userAgent.indexOf("MSIE 9.0") != -1;
			//html附件方式显示隐藏
		   if(isIe){
				/*显示ie9附件上传html 隐藏非ie9附件上传html*/
				$(".noie9filesmuliple").addClass("hidden");
				$(".ie9filesmuliplebox").removeClass("hidden");
			}else{
				$(".ie9filesmuliplebox").addClass("hidden");
				$(".noie9filesmuliple").removeClass("hidden");
			}
		},
		/*附件验证   文件个数：filenums*/
		ie9fileValidate : function(filenums,nums){
			//验证文件名相同的文件
			if(nums){
				var ieallFilesName = $('#showFileListMul_'+nums+' .iefilename').text();
				var ienamepath = $('#ieuploadsfile_'+nums+'').val();
				 var iefileLength = $('#showFileListMul_'+nums+' li').length;
			}else{
				var ieallFilesName = $("#showFileListMul .iefilename").text();
				var ienamepath = $("#ieuploadsfile").val();
				var iefileLength = $("#showFileListMul li").length;
			}
			//重复的文件
			 var ierepeatedFiles = [];
	         var ienamepathLast = ienamepath.lastIndexOf("\\");
			 var iename = ienamepath.substring(ienamepathLast+1);
			
			 var iefiletype = iename.split(".").pop();
			 var iefilenameL = iename.split(".")[0].length;
	         var allowfileType = ["doc","docx","xls","xlsx","pdf","jpg","jpeg","png","PNG","bmp","rar","zip","gif","tiff"];
	         if (ieallFilesName.indexOf(iename) != -1) {
	             ierepeatedFiles.push(iename);
	         }
	         if(ierepeatedFiles.length > 0) {
	             Message.alert({Msg: ierepeatedFiles.join("/") + "文件已上传，不能重复上传", isModal: false});
	             return;
	         }else if(iefileLength >= filenums){//文件个数验证
	        	 Message.alert({Msg: '上传文件个数已达到上限，最多上传'+filenums+'个文件', isModal: false});
	        	 return;
	         }else if($.inArray(iefiletype,allowfileType)== -1){  //文件类型验证
	        	 Message.alert({Msg: "文件" +iename+ "类型不允许上传！", isModal: false});
	       	  	 return;
	         }else if(iefilenameL>30){//附件名字长度最大30个
	        	 Message.alert({Msg: "文件" +iename+ "名字长度不能超过30汉字或字符！", isModal: false});
	       	  	 return;
	         }else{
	        	 if(nums){
	        		//开始上传
	 	      	 	$('#iefilesubmit_'+nums+'').click();
	        	 }else{
	        		//开始上传
		 	      	$("#iefilesubmit").click();
	        	 }
	         }
		}	
};


(function ($, undefined) {

    //通用方法扩展
    $.extend({
    	//格式化字符串
        formateString: function (str, obj) {
            return str.replace(/\{{(\w+)}\}/g, function (match, key) {
            	
                return obj[key];
            });
        },
        
        temp1: function (str,obj) {
            var temp = str;
            return temp.replace(/\{[a-zA-Z0-9.]+}/gi, function (match) {
                var tempObj = obj;
                var value = match.substr(1, match.length - 2);
                var tempArray = value.split(".");
                if (tempArray.length > 1) {
                    //有多层对象
                    for (var i = 0; i < tempArray.length; i++) {
                        if (tempObj && !tempObj.hasOwnProperty(tempArray[i])) {
                            return "";
                        } else if(tempObj) {
                            tempObj = tempObj[tempArray[i]];
                            if (tempArray.length === i + 1) {
                                return tempObj ? tempObj : "";
                            }
                        }
                    }
                } else {
                    //没有多层对象
                	if(tempObj[value]){
                		return tempObj[value];
                	}else{
                		return "";
                	}
                    //return tempObj[value] ? tempObj[value] : "";
                }
            });
        },
        
        
    	//联想下拉框
    	associate:function(config){
    		var random = new Date().getTime();//用途。每调用一次就会创建一个联想对象
    		var associate_div_id = "associate_div" + random;
    		var div= "<div style='position:absolute;display:none;background-color:#0A365F;overflow-x:hidden;z-index:2201' id='"+associate_div_id+"'></div>";
    		$("body").append(div);
    		var obj = {};
    		obj.showCnt = 10;
    		obj.associateDiv = $("#"+associate_div_id);
    		obj.width = undefined;
    		obj.offsetX = 0;
    		obj.offsetY = 0;
    		obj.minInterval = 200;
    		//定义按键时间
    		obj.keyupfn = function(event){
    			var i = $(obj.inputObj).data("associate_index");
    			var cnt = 0;
    			var datas = $(obj.inputObj).data("htw_data");
    			if(datas){
    				cnt = datas.length;
    			};
    			cnt = cnt<obj.showCnt ? cnt:obj.showCnt;
    			switch(event.keyCode){
    			case 13:
    				//obj.submitData($(obj.inputObj).val(),obj);
    				break;
    			case 38://上
    				i = (i == undefined || i == null) ? cnt-1 : --i < 0 ? cnt-1 : i;
    				$("#"+associate_div_id+"div").css("background","#fff");;
    				var divObj = $("#"+associate_div_id+"div").eq(i);
    				divObj.css("background","#efeef");
    				var datas = $(obj.inputObj).data("htw_data");
    				$(obj.inputObj).data("associate_index",i);
    				obj.selectData(datas[i],divObj,obj);
    				break;
    			case 40://下
    				i = (i == undefined || i == null) ? 0 : ++i > cnt-1 ? 0 : i;
    				$("#"+associate_div_id+"div").css("background","#fff");
    				var divObj = $("#"+associate_div_id+"div").eq(i);
    				divObj.css("background","#efeef");
    				var datas = $(obj.inputObj).data("htw_data");
    				$(obj.inputObj).data("associate_index",i);
    				obj.selectData(datas[i],divObj,obj);
    				break;
    			default:
    				var val = $(obj.inputObj).val();
    				if(val){
    					var old = $(obj.inputObj).data("htw_old");
    					var lastTime = $(obj.inputObj).data("htw_lastTime");//最后请求数据的时间
    					lastTime = lastTime ? lastTime : now;
    					var now = new Date().getTime();
    					if(old != val){
    						var time = lastTime + obj.minInterval-now;
    						if(time <= 0){
    							$(obj.inputObj).data("htw_old",val);
    							$(obj.inputObj).data("htw_lastTime",now);
    							obj.position(obj);
    							obj.getData(val,obj);
    						}else{
    							setTimeout(function(){
    								var val0 = $(obj.inputObj).val();
    								var old0 = $(obj.inputObj).data("htw_old");
    								var now0 = new Date().getTime();
    								if(val0 && old0 != val0){
    									$(obj.inputObj).data("htw_old",val0);
    									$(obj.inputObj).data("htw_lastTime",now0);
    									obj.position(obj);
    									obj.getData(val0,obj);
    								}
    							},time);
    						}
    					}
    				}
    				break;
    			}
    		};
    		//定义失去焦点时间
    		obj.blurfn = function(){
    			setTimeout(function(){
    				obj.associateDiv.hide();
    			},400);
    		};
    		
    		//设置联想数据
    		obj.setData = function(data,obj){
    			if(!obj){
    				obj = this;
    			}
    			var array = [];
    			if(data){
    				array = data;
    			}
    			$(obj.inputObj).data("htw_data",array);
    		};
    		
    		//获取联想数据
    		obj.getData = function(val,obj){
    			var associateObj = this;
    			var param = {};
    			param["associate_key"] = val;
    			//param["enterpriseName"] = val;
    			$.post(associateObj.url,param,function(list){
    				if(list && list.length > 0){
    					associateObj.setData(list);
    					associateObj.associateDiv.show();
    					associateObj.associateDiv.empty();
    					for(var i = 0; i < list.length && associateObj.showCnt; i++){
    						var data = list[i];
    						var str = "<div style='cursor:pointer;padding:2px;10px;'>"+data.name+
    						"</div>";
    						associateObj.associateDiv.append(str);
    					}
    				}
    			},"json");
    		};
    		//提交数据回调
    		obj.submitData = function(data,obj){};//提交函数
    		//选择数据回调
    		obj.selectData  = function(data,divObj,obj){
    			var name = divObj.text();
    			if(data && data.name){
    				name = data.name;
    			}
    			$(obj.inputObj).val(name);
    		};
    		//定位
    		obj.position = function(obj){//定位
    			if(!obj){
    				obj = this;
    			}
    			var position = $(obj.inputObj).offset();
    			obj.associateDiv.css("left",position.left+obj.offsetX);
    			obj.associateDiv.css("top",position.top+$(obj.inputObj).outerHeight()+obj.offsetY);
    			obj.associateDiv.width(obj.getWidth(obj));
    		};
    		
    		obj.getWidth = function(obj){//获取宽度
    			if(!obj){
    				obj = this;
    			}
    			var width = $(obj.inputObj).outerWidth();
    			if(obj.width){
    				width = obj.width;
    			}
    			return width;
    		};
    		
    		if(config){
    			for(var key in config){
    				obj[key] = config[key];
    			}
    		}
    		obj.position(obj);
    		//鼠标移动效果
//    		$("#"+associate_div_id+"div").bind("mouseover",function(){
//    			$("#"+associate_div_id+"div").css("background","#fff");
//    			$(this).css("background","#efeeff");
//    			var i = $(this).index();
//    			$(obj.inputObj).data("associate_index",i);
//    		});
    		$("#"+associate_div_id).delegate("div","click",function(){
    			var i = $(this).index();
    			$(obj.inputObj).data("associate_index",i);
    			var datas = $(obj.inputObj).data("htw_data");
    			obj.selectData(datas[i],$(this),obj);
    			obj.associateDiv.hide();
    		});
    		$(obj.inputObj).keyup(obj.keyupfn);
    		$(obj.inputObj).blur(obj.blurfn);
    		$(window).resize(function(){
    			obj.position(obj);
    		});
    		return obj;
    	},
    	
        //全局消息数据信息
        msg: {
            noSelectedDelete: "没有选中要删除的数据",
            sureDelete      : "确认删除数据吗?",
            editSuccess     : "编辑成功",
            deleteSuccess   : "删除成功",
            saveSuccess     : "保存成功",
            sureCancle      : "是否确认注销?",
            surePublish     :"是否确认发布?",
            sureSign        :"是否确认签订?"
        },
        
        backPath: '/JXWL/rest', //后台使用路径
        frontPath: '/JXWL', //前台资源请求
        exitLoginUrl: '/JXWL/rest/user/exit',
        updatePassUrl: '',
        validateTip: function (json) {
            if (json.code === 2) {
                alert("表单验证错误");
            } else if (json.code === 3) {
                Message.alert({Msg: json.msg, iconImg: 'error'});
            }
        },
        /**
         * 编辑或者查看一条信息
         * @param msg1 大于两条信息
         * @param msg2 没有选择信息
         * @returns {行的id}
         */
        checkOneMsg: function (tableId, msg1, msg2) {
            var ids = $(tableId).jqGrid("getGridParam", "selarrrow");
            var len = ids.length;
            if (len > 1) {
                Message.alert({Msg: msg1, isModal: false});
                return false;
            } else if (len <= 0) {
                Message.alert({Msg: msg2, isModal: false});
                return false;
            } else {
                return $(tableId).jqGrid("getGridParam", "selrow");
            }
        },
        /**
         * 根据id获取单条数据信息
         * @param id
         * @returns {string}
         */
        getOneMsg: function (url, id) {
            var oneMsg = "";
            // 请求用户信息数据
            $.ajax({
                url: url,
                dataType: "json",
                type: "POST",
                data: {"id": id},
                async: false,
                success: function (json) {
                    if (json["code"] === 1) {
                        oneMsg = json["data"];
                    } else {
                        Message.alert({Msg: json["msg"], iconImg: "error", isModal: false});
                    }
                }
            });
            return oneMsg;
        },
        /**
         * 用户数据信息
         */
        getUserInfo: function () {
            return JSON.parse(window.sessionStorage.getItem("userInfo"));
        },
        
        /**
         * 去掉重复的数据信息
         * @param data 数据
         * @returns {Array}
         */
        removeRepeatArray: function (data) {
            var result = [], hash = [];
            for (var i = 0, elem; (elem = data[i]) != null; i++) {
                if (!hash[elem]) {
                    result.push(elem);
                    hash[elem] = true;
                }
            }
            return result;
        },

        /**
         * 获取数据字典全局方法
         * @param url_path
         * @returns {string}
         */
        getData: function (url_path, type, dataObj) {
            var TYPE = type || 'GET';
            var Obj = dataObj || {};
            var result = "";
            var cache = true;
            var explor =window.navigator.userAgent;
           if(explor.indexOf("Trident")>=0||explor.indexOf("MSIE")>=0){
            	//ie浏览器
        	   if(TYPE == "POST"){
        		   cache =true;
        	   }else{
        		   cache =false;
        	   }
            }else{
            	cache =true;
            }
            $.ajax({
                url: url_path,
                type: TYPE,
                cache: cache,
                data: Obj,
                async: false //使用同步方式
            }).done(function (json) {
            	if(json.code === 1 && (json.data || json.data == 0)){
            		result = json.data;
            	} else if ((json.code === 1) && (!json.data)) {
            		Message.alert({Msg: "该数据不存在或为空", isModal: false});
            		return;
            	}else{
            		Message.alert({Msg: json.msg, isModal: false});
            		return;
            	}
                
            });
            return result;
        },

        /**
         * 根据数据字典编码返回数据字典名称
         * @param codeId 编码id
         * @param dicObj 数据字典对象
         * @returns {string} 数据字典名称
         */
        getDicNameByCode: function (codeId, dicObj) {
            var name;
            if (codeId) {
                $.each(dicObj, function (index, item) {
                    if (item.code === codeId) {
                        name = item.name;
                    }
                });
                return name;
            } else {
                return "";
            }
        },
        
        /**
         * 获取权限模块
         * @param moduleMethodName 模块方法名
         */
        getModule: function (moduleMethodName) {
            if (moduleMethodName == null || moduleMethodName == undefined) {
                return null;
            }
            var module = JSON.parse(window.sessionStorage.getItem("module"));
            //递归查询是否存在权限
            var find = function (m, mmn) {
                //没有模块
                if (m == null || m == undefined) {
                    return null;
                }
                //模块链接相同
                if (m.method == mmn && m.privg === "1") {
                    return m;
                }
                //检查子模块
                var ms = m.moduleEntity;
                for (var i in ms) {
                    var m0 = find(ms[i], mmn);
                    if (m0 != null) {
                        return m0;
                    }
                }
                //所有子模块都不相同
                return null;
            };
            return find(module, moduleMethodName);
        },

        
        
        
        /**
         * 获取权限(模块功能)
         * @param moduleMethodName 模块URL
         * @param identify 功能identify
         */
        getModuleFunction: function (moduleMethodName, identify) {
            var module = $.getModule(moduleMethodName);
            if (module != null && identify != null && identify != undefined) {
                var fnc = module.functions;
                if (fnc) {
                    for (var i in fnc) {
                        if (fnc[i] && fnc[i].identifying == identify && fnc[i].privg == 1) {
                            return fnc[i];
                        }
                    }
                }
            }
            return null;
        },

        /**
         * 权限初始化
         */
        initPrivg: function (tbName) {
            //权限过滤
            $("[data-module]").each(function () {
                //获取模块方法名
                var moduleMethodName = $(this).attr("data-module");
                var module = $.getModule(moduleMethodName);
                //没有此模块的权限
                if (module == null) {
                    $(this).remove();
                } else {
                    $("[data-module-name=" + moduleMethodName + "]").text(module.name);
                }
            });

            $("[data-func]").each(function () {
                var mid = $(this).attr("data-func");
                var strs = mid.split("-");
                var func = $.getModuleFunction(strs[0], strs[1]);
                if (func == null) {
                	$(this).next("span").remove();
                    $(this).remove();
                } else {
                    $("[data-func-name=" + mid + "]").text(func.name);
                }
            });
            //修改title问题
            var $tr = $("#" + tbName).find(".ui-widget-content > td:last-child");
            $tr.each(function(index,item){
            	// 正则替换&nbsp;
            	var title = $(item).text();
            	title = title.replace(/^\s+|\s+$/g,'');
            	//修改title
               $(item).attr("title", title);
            });
        },
        
        /**
         * 地图权限初始化
         */
        mapInitPrivg :function(){
        	//根据的值判断地图是否具有相关权限
            var mapModule = JSON.parse(window.sessionStorage.getItem("module"));
            var flag;//返回是否具有权限
            var mapMethod = arguments;//地图相关的操作方法
            var mapMethodInfor;//地图相关的操作方法信息
            var returnObject={}; 
            if(mapModule == null){
            	return flag = false;
            }else{
            	var len = mapMethod.length;
            	mapMethodInfor = $.getModule("rtLocation");
            	$.each(mapMethod,function(index,content){
            		if(mapMethodInfor && mapMethodInfor.functions){
            			for(var i in mapMethodInfor.functions){
            				if(mapMethodInfor.functions[i].identifying == mapMethod[index]){
            					if(mapMethodInfor.functions[i] && mapMethodInfor.functions[i].privg == "1"){
            						returnObject[mapMethod[index]]= true;
            					}else{
            						returnObject[mapMethod[index]]= false;
            					}
            				}
            			}
            		}
            	});
            	return returnObject;
            }
        },
        
        /**
         * 根据权限加载左侧侧边栏
         */
        setLeftMenu: function () {
            var module = JSON.parse(window.sessionStorage.getItem("module"));
            var leftMenuHtml = ['<ul class="list-group" id="list-group-LeftMenu">',
                '<li class="list-group-item navigation-text">导&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;航<!-- <span class="open-handler"></span> --></li>'];
            //第一级菜单
            if(module){
            	var oneLevelNode = module.moduleEntity;
            	var len = oneLevelNode.length;
            	if (!len) {
            		return;
            	} else {
            		for (var i = 0; i < len; i++) {
            			//大屏展示模块权限特殊处理
            			if(oneLevelNode[i] && oneLevelNode[i].url === "/view/goodsTrade/fullScreen.html"){
            				continue;
            			}
            			//父节点有权限生成父节点
            			if (oneLevelNode[i] && oneLevelNode[i].privg === "1") {
            				leftMenuHtml.push('<li class="list-group-item collapsed" data-toggle="collapse" data-target="#left-menu-' + i + '">',
            						'<a class="">' + oneLevelNode[i].name + '</a>',
            						'</li><li class="collapse" id="left-menu-' + i + '"><ul class="list-subgroup">');
            				//第二级菜单
            				var twoLeavelNode = oneLevelNode[i].moduleEntity;
            				var twoLeavelNodeLen = twoLeavelNode.length;
            				//子模块有权限生成子节点
            				for (var j = 0; j < twoLeavelNodeLen; j++) {
            					if (twoLeavelNode[j].url && twoLeavelNode[j].privg === "1") {
            						leftMenuHtml.push('<li class="list-group-secondItem"><a href="/JXWL' + twoLeavelNode[j].url + '" class="">' + twoLeavelNode[j].name + '</a></li>');
            					}
            				}
            				leftMenuHtml.push('</ul></li>');
            			}
            		}
            	}
            }
            leftMenuHtml.push('</ul>');
            return leftMenuHtml.join("");
        },
        
        //全屏页面顶部
        fullScreenTop: function(){
        	var fullScreenTopHtml = ['<div class="button_top">',
			'<a class="tradeTrends-btn col-xs-offset-11" href="fullScreen.html">1</a>',
			'<a class="notice-btn" href="fullScreenTwo.html">2</a>','<a class="notice-btn" href="fullScreenThree.html">3</a>',
			'<a class="notice-btn" href="fullScreenFour.html">4</a>','</div>'].join("");
        	return fullScreenTopHtml;
        },
        
        
        /**
         * 生成指定位数的随机数
         * @param count 指定的位数
         * @param arry  指定的数组 
         * */
        randomCount :function(count){
        	var arry = new Array;
        	for(var i =0; i< count;i++){
        		arry[i]=i+1;
        	}
        	arry.sort(function(){
        		return 0.5-Math.random();
        	});
        	return arry;
        },
        
        /**
         * 去除搜索栏首尾空格
         * */
        removeTrim :function(){
	    	var input = $(".searchBox").find('input');
	    	$.each(input,function(index,content){
	    		var value = $(content).val().replace(/^\s+|\s+$/g,'');
	    		$(content).val(value);
	    	});
        },
        /**
         * 模糊查询企业
         * @param target
         * @param url
         */
        typeahead :function(target,url){
        	//模糊搜索数据信息 企业名称和企业代码
            var _corporateNameCode = $("input[name="+target+"]");
            _corporateNameCode.attr("autocomplete", "off");
            if (_corporateNameCode.length) {
                _corporateNameCode.typeahead({
                    source: function (query, process) {
                        $.post(url ,{"enterpriseName":query},function (json) {
                        	if(json.code === 1 && json.data !== null){
                				var arr = [];
                				if(target == "corporateName-Code"){
                					$.each(json.data,function(i,d){
                    					var no = json.data[i].corporateNo;
                    					var name = json.data[i].enterpriseName;
                    					if( $.inArray(no,arr)=== -1){//去重
                    						arr.push(no);
                    					};
                    					if( $.inArray(name,arr)=== -1){//去重
                    						arr.push(name);
                    					};
                    				});
                				}else if( target == "vehicleName"){
                					$.each(json.data,function(i,d){
                    					var name = json.data[i].licencePlateNo;
                    					if( $.inArray(name,arr)=== -1){//去重
                    						arr.push(name);
                    					};
                    				});
                				}else if( target == "personName"){
                					$.each(json.data,function(i,d){
                    					var name = json.data[i].personName;
                    					if( $.inArray(name,arr)=== -1){//去重
                    						arr.push(name);
                    					};
                    				});
                				}
                    			process(arr);
                			}
                        });
                    }
                });
            }
        },
        /**
         * 模糊查询车辆
         * @param target
         * @param url
         */
        typeahead4Vehicle :function(target,url){
        	//模糊搜索数据信息 企业名称和企业代码
            var _corporateNameCode = $("input[name="+target+"]");
            _corporateNameCode.attr("autocomplete", "off");
            if (_corporateNameCode.length) {
                _corporateNameCode.typeahead({
                    source: function (query, process) {
                        $.post(url ,{"enterpriseName":query},function (json) {
                        	if(json.code === 1 && json.data !== null){
                				var arr = [];
                				$.each(json.data,function(i,d){
                					var name = json.data[i].licencePlateNo;
                					if( $.inArray(name,arr)=== -1){//去重
                						arr.push(name);
                					};
                				});
                    			process(arr);
                			}
                        });
                    }
                });
            }
        },
        /**
         * 模糊查询人员
         * @param target
         * @param url
         */
        typeahead4Vehicle :function(target,url){
        	//模糊搜索数据信息 企业名称和企业代码
            var _corporateNameCode = $("input[name="+target+"]");
            _corporateNameCode.attr("autocomplete", "off");
            if (_corporateNameCode.length) {
                _corporateNameCode.typeahead({
                    source: function (query, process) {
                        $.post(url ,{"enterpriseName":query},function (json) {
                        	if(json.code === 1 && json.data !== null){
                				var arr = [];
                				$.each(json.data,function(i,d){
                					var name = json.data[i].licencePlateNo;
                					if( $.inArray(name,arr)=== -1){//去重
                						arr.push(name);
                					};
                				});
                    			process(arr);
                			}
                        });
                    }
                });
            }
        }
        
        
    });
    
    

    var base = {
        logo_title: '版权所有 © 2016 浙江航天恒嘉数据科技有限公司',
        left_menu_url: $.frontPath + "/js/template/left-menu.html",
        updatePwd_url: $.frontPath + "/js/template/updatePwd.html",
        ajax: function (url_path) {
            var result = "";
            $.ajax({
                url: url_path,
                type: 'post',
                cache: true,
                async: false //使用同步方式
            }).done(function (json) {
                result = json;
            });
            return result;
        }
    };

    $.fn.extend({

        /**
         * 打开弹窗
         * @param title 弹窗名称
         * @param isModel 是否模态
         * @param isClose 是否有关闭按钮
         */
        showPanelModel: function (title, isModel, isClose) {
            var $this = $(this);
            var $title = $this.find(".panel-title");
            var $heading = $this.find(".panel-heading");
            var maskFlag = $(".panel-mask")[0];
            var closeHtml;
            //设置参数
            $title.html(title || '');
            //是否需要模态---多个弹窗判断
            if (!isModel) {
        		if(maskFlag){//设置第二个弹框模态
        			$(".panel-mask").css("z-index","2500");
        		}else{
        			$("body").append('<div class="panel-mask"></div>');
        		}
            }
            if (!isClose) {
            	if(maskFlag){//设置第二个弹框关闭---取消委托
        			closeHtml = $('<span class="panel-close"></span>');
                    $heading.append(closeHtml);
        		}else{
        			 closeHtml = $('<span class="panel-close"></span>');
                    $heading.append(closeHtml);
                    $heading.delegate(".panel-close", "click", function (e) {
                        e.preventDefault();
                        $this.closePanelModel();
                    });
        		}
            }
             //设置第二个弹框关闭---取消委托
    			 
    		if(!maskFlag){
            	//取消按钮事件
                $this.delegate("button.cancel", "click", function (e) {
                    e.preventDefault();
                    $this.closePanelModel();
                });
            }
            /*可以拖动*/
            $this.draggable({handle: '.panel-heading', containment: 'html'});
            /*js原生态设置居中显示*/
            var top = document.documentElement.scrollTop;
            var left = document.documentElement.scrollLeft;
            var width = document.documentElement.clientWidth;
            var height = document.documentElement.clientHeight;
            $this.css({
                'top': top + (height - $this.height()) / 2 + 'px',
                'left': left + (width - $this.width()) / 2 + 'px',
                'right': 'auto',
                'width': $this.width() + 'px'
            });
            $this.fadeIn(300);
            //回调函数
            return this;
        },
        

        /**
         * 表单填充数据信息
         * @param formData
         */
        setFormSingleObj: function (formData) {
            var $this = $(this);
            var input = $this.find("[name]");
            $.each(input, function (index, item) {
                var _type = item.nodeName;
                var _input = $(item).attr("name");
                if (_type === 'INPUT' || _type === 'SELECT' || _type === 'TEXTAREA') {
                    $(this).val(formData[_input] !== null ? formData[_input] : '');
                } else {
                    $(this).text(formData[_input] !== null ? formData[_input] : '');
                }

            });
            return this;
        },

        /**
         * 不显示某些字段
         * @returns {hideRowPanelModel}
         */
        hiddenRowPanelModel: function () {
            var $this = $(this);
            var array = arguments[0];
            try {
                $.each(array, function (index, item) {
                    var $inputId = /^#/.test(item) ? item : '#' + item;
                    var $row = $($inputId).parents('div.row');
                    //隐藏还是显示
                    $row.addClass('hidden');
                });
            } catch (e) {
                console.error(e + ";参数传入有误,请注意核查!");
            }
            return this;
        },

        /**
         * 显示某些字段
         * @returns {showRowPanelModel}
         */
        showRowPanelModel: function () {
            var $this = $(this);
            var array = arguments[0];
            try {
                $.each(array, function (index, item) {
                    var $inputId = /^#/.test(item) ? item : '#' + item;
                    var $row = $($inputId).parents('div.row');
                    //显示还是显示
                    $row.removeClass('hidden');
                });
            } catch (e) {
                console.error(e + ";参数传入有误,请注意核查!");
            }
            return this;
        },

        /**
         * 查看模式
         * @returns {lookRowsPanel}
         */
        lookRowsPanel: function () {
            var $this = $(this);
            var array = arguments[0];
            $.each(array, function (index, item) {
                var $inputId = /^#/.test(item) ? item : '#' + item;
                var inputId = $($inputId);
                if (!inputId.siblings('span[name=' + inputId.attr("name") + ']').length) {
                    inputId.addClass('hidden').before('<span class="look-formPanel" name="' + inputId.attr("name") + '"></span>');
                }
            });
            return this;
        },

        /**
         * 编辑模式
         * @returns {editRowsPanel}
         */
        editRowsPanel: function () {
            var $this = $(this);
            var array = arguments[0];
            $.each(array, function (index, item) {
                var $inputId = /^#/.test(item) ? item : '#' + item;
                var inputId = $($inputId);
                if (inputId.siblings('span[name=' + inputId.attr("name") + ']').length) {
                    inputId.siblings('span[name=' + inputId.attr("name") + ']').remove();
                    inputId.removeClass('hidden');
                } else {
                    inputId.removeClass('hidden');
                }
            });
            return this;
        },

        /**
         * 关闭弹框
         */
        closePanelModel: function () {
            var $this = $(this);
            var $form = $this.find("form");
            var $close = $this.find(".panel-close");
            //取消代理
            $close.undelegate("click").remove();
        	 //移除模态框
            $(".panel-mask").remove();
            //消失弹框
            $this.fadeOut(300);
            //表单清空
            $form[0] && $form[0].reset();
        },

        /**
         * 左侧菜单关闭和展开
         */
        toggleLeftMenu: function () {
            var $this = $(this);
            var $mainLeft = $this.find('.main-left');
            var $openHandler = $mainLeft.find('.open-handler');
            var $rightContent = $this.find('.right-content');
            $openHandler.on("click", function () {
                if ($mainLeft.hasClass('open-left')) {
                    $mainLeft.addClass('close-left').removeClass('open-left');
                    $rightContent.addClass('close-right').removeClass('open-right');
                } else {
                    $mainLeft.addClass('open-left').removeClass('close-left');
                    $rightContent.addClass('open-right').removeClass('close-right');
                }
            });
        },

        /**
         * 整体框架搭建
         */
        containerInit: function () {
            //当前元素
            var $this = $(this).addClass('open-right');
            //创建Dom元素
            var $footer = $('<div class="footer"></div>').text(base.logo_title);
            var $mainContainer = $('<div class="main-container"></div>');
            var $leftMenu = $('<div class="main-left open-left"></div>');
            var $mainRight = $('<div class="main-right"></div>');
            var $updatePwd =  $('<div class="panel panel-gray-xs" id="changePwd-panel"></div>');
            $this.wrap($mainRight);
            $('.main-right').wrap($mainContainer);
            //ajax请求，获取左侧菜单栏
            $leftMenu.append($.setLeftMenu());
            $updatePwd.append(base.ajax(base.updatePwd_url));
            $('.main-container').before(this.headerMenu()).append($leftMenu).append($updatePwd).after($footer);
            return this;
        },

        /**
         * 左侧侧边栏高亮显示
         */
        leftMenuHighLight: function(){
        	var $mainLeft = $(".main-left");
        	var urlPath = location.pathname;
        	var url = urlPath.substring(urlPath.lastIndexOf("/"), urlPath.length).split(",")[0];
        	var $highLight = $mainLeft.find(".list-group-secondItem a[href*='"+url+"']");
        	var $ulMenu = $highLight.parents('ul.list-subgroup');
            if ($ulMenu.length) {
                var $li_id = $highLight.parents('li.collapse');
                var $listGroupItem = $mainLeft.find('.list-group-item[data-target=#' + $li_id.attr("id") + ']');
                $li_id.addClass("in");
                $listGroupItem.removeClass("collapsed");
                $highLight.parent("li").addClass('menu-li-active');
            } else {
                $highLight.parent("li").addClass('menu-li-active');
            }
        },
        
        /**
         * 顶部菜单按钮栏
         * @returns {*|HTMLElement}
         */
        headerMenu: function () {
            var userName;
            userName = $.getUserInfo() ? $.getUserInfo()['name'] : '未登录';
            userRole = $.getUserInfo() ? $.getUserInfo()['roleCodes'][0] : '未知用户';
            userRoleFlag = userRole === "e5348d777c2a48dd98cc7e19621d3193" ? "政府用户" : 
            	userRole === "7545f7983ac84654951f64efba29677a" ? "物流企业用户" : 
            		userRole === "f9be1f18fdea412cbf7bcf4a4a6809af" ? "化工企业用户" : 
            			userRole === "27a5403c65d44f90bbebe990bf076721" ? "卡口审核用户": "普通用户";
            var onOff ="";
            if(userRoleFlag === "政府用户"){
            	onOff="<li id='onOffRemindLi'><b><input type='checkbox' class='onOffRemind' id='onOffRemind'>关闭下线提醒 |</b></li>";
            }
            var offOnCar = '<ul class="testfull2">'+ onOff+'<li><b>当前园区共有  <span class ="carNumber" id="allCar">0</span>  辆货车|</b></li><li ><b>在线   <span class ="carNumber" id="onLineCar">0</span>  辆|</b></li><li><b>离线 <span class ="carNumber" id="offLineCar">0</span>  辆|</b></li></ul>';
            if(userRoleFlag === "卡口审核用户"){
            	offOnCar = "";
            }
            var $header = $('<div class="header">'+
    					'<div class="header-usermsg pull-right">'+
//    						' <ul><li><b>'+ userName +'|</b></li><li class="updatePwd" id="updatePwd">修改密码|</li>'+
    						' <ul class="testfull"><li><b>'+ userName +'|</b></li><li id="userRoleid"><b>'+ userRoleFlag +'|</b></li><li class="updatePwd" id="updatePwd"></li>'+
    							'<li class="exit" id="systemTime">注销|</li>'+
    							/*'<li class="icon infoReceive"><i class="glyphicon glyphicon-envelope"><b>100|</b></i></li>'+*/
    							'<li id="fullScreen" data-module="fullScreen"><span class="fullScreenIcon" id="fullScreenSpan"></span></li>'+
    							'</ul>'+
    							offOnCar+
							' <div style="top: 20px; right: 10px; position: absolute;">'+
								/*' <i class="normalAlarmIMG hidden" onclick="leftmeunpannel.shwoAllAlarmDetail()"></i> '+*/
								' <li class="twoDimensionCode"></li>	'+	
								' <li class="appWarnFont" style="list-style-type: none">扫二维码下载app</li>	'+	
							'</div>'+
						' </div>'+
  					'</div>');
            return $header;
        },
        
        /**
         * 随着浏览器宽度
         * 动态控制表格宽度
         */
        resizeTableWidth: function (width) {
            var $this = $(this);
            var defaultWidth = width || 280;
            $(window).resize(function () {
                $this.jqGrid({autowidth: false}).setGridWidth($(window).width() - defaultWidth);
            });
            return this;
        },

        /**
         * 设置下拉列表
         * @param dicObj 数据字典对象
         */
        settingsOptions: function (dicObj, flag) {
            var $this = $(this);
            var optionVal = $this.find("option:eq(0)").val();
            if(optionVal === "请选择" || optionVal === ""){
            	$this.find("option:not(:eq(0))").remove();
            }else{
            	$this.find("option").remove();
            }
            var flagOption = "<option value=''></option>";
            flag ? $this.append(flagOption) : '';
            $.each(dicObj, function (index, item) {
                $this.append('<option value="' + item.code + '">' + item.name + '</option>');
            });
        },
        
        /**
         * 详情时与数据字典对应
         * @param dicObj 数据字典对象
         *@param flag  对应数据字典码
         */
        detailSpan: function (dicObj, flag) {
        	if(flag){
        		 var $this = $(this);
                 var returnData;
                  $.each(dicObj, function (index, item) {
                      if(item.code === flag){
                      	returnData = item.name;
                      }
                  });
                  $this.text(returnData);
        	}
        },
        
        /**
         * 查看planel
         * @param flag 编辑或者新增:true; 查看: false 
         */
        saveOrCheck: function (flag) {
        	 var $this = $(this);
             var $input = $this.find(".saveInput");
             var $span = $this.find(".look-formPanel");
             var $footer = $this.find(".panel-footer");
             var $mustFlag = $this.find(".mustFlag");
        	if(flag){
        		$input.removeClass("hidden").addClass("must");
        		$span.addClass("hidden");
                $footer.removeClass("hidden");
                $mustFlag.addClass("must");
        	}else{
        		$input.addClass("hidden").removeClass("must");
        		$span.removeClass("hidden");
                $footer.addClass("hidden");
                $mustFlag.removeClass("must");
        	}
            return this;
        },

        /**
         *封装表单中想后台提交的数据
         *@param divId form表单中指定的Id
         * @param  flag 传入的 数据
         *  @param  id 传入数据类型
         */
        packageFormData: function (){
            var formdata = $(this).find(".form-control");
            var dataObj={};
            var name;
            var value;
            $.each(formdata,function(index,content){
                name  =	$(content).attr("name");
                value = $(content).val();
                dataObj[name]=value;
            });
            return dataObj;
        }
    });

	/*var getUserInfoByAjax = function( i ){
		window.location.href = "/JXWL/view/index.html";
		var userInfor = sessionStorage.getItem("userInfo");
	   	if(!userInfor && i<3){
		   console.log('第'+i+'次递归调用getUserInfoByAjax')
		   getUserInfoByAjax(++i);
		}
    };*/
   
    //初始化方法
    var _init = function () {
    	var userInfor = sessionStorage.getItem("userInfo");
    	//使用if(!userInfor)还是有问题，因为userInfor="undefined"时!userInfor值为false，导致无法进入条件发送ajax请求/user/loginUser
    	if(userInfor=="undefined"||userInfor==null||userInfor==""){  
    		//重新获取 
            $.ajax({ 
	            type: "POST", 
	            url: $.backPath+"/user/loginUser?t="+new Date().getTime(), 
	            async: false, 
	            dataType: "JSON",
	            cache:false
	        }).done(function (json) {
	            var data = json.data; 
	            if (json.code === 1) { 
	                //参考 doLogin.storage(json.data);
	            	sessionStorage.setItem("userInfo", JSON.stringify(data["userInfo"])); 
	            	sessionStorage.setItem("module", JSON.stringify(data["module"])); 
//	            	if(location.pathname != data["url"]){//如果当前页面和要跳转的页面地址相同，则不跳转，否则体验不好
//	            		window.location.href = data["url"];
//	            	}
	            	window.location.href = data.url+ "?id=" +data.module.id;
	            } 
	        });
    	}
    	//设置搜索栏中的input输入长度
    	var inputs = $(".item-searchBox input:not(.Wdate)");
    	inputs.attr("maxlength","200");
        //默认调用方法
        $(".right-content").containerInit().leftMenuHighLight();
        //追加logo
        $(document.head).append('<link rel="shortcut icon" href="' + $.frontPath + '/images/logo-ico.ico" type="image/x-icon">');
        //初始化权限
        $.initPrivg();
        //迟延显示框架
        $(document.body).addClass("addShow");
        //控制全屏页面全屏展示
        $(".fullScreenContainer").parent().parent().find("div[class*=main-left]").remove();
        $(".fullScreenContainer").find("div:eq(0)").before($.fullScreenTop());
        //给车辆监控添加点击事件
        $("a:contains('车辆监控')").bind("click",function(){
        	var pn = location.pathname;
        	if(pn!='/JXWL/view/index.html'){
        		location.pathname = '/JXWL/view/index.html';
        	}
        });
        //初始化订单日志提醒
        $("a:contains('订单日志提醒')").bind("click",function(){
        	var pn = location.pathname;
        	if(pn!='/JXWL/view/orderManage.html'){
        		location.pathname = '/JXWL/view/orderManage.html';
        	}
        });
        //模糊搜索数据信息 企业名称和企业代码
        var _corporateNameCode = $("input[name=corporateName-Code]");
        _corporateNameCode.attr("autocomplete", "off");
        if (_corporateNameCode.length) {
            _corporateNameCode.typeahead({
                source: function (query, process) {
                    $.post($.backPath + "/logisticst/findAllEnterprise",{"enterpriseName":query},function (json) {
                    	if(json.code === 1 && json.data !== null){
            				var arr = [];
            				$.each(json.data,function(i,d){
            					var no = json.data[i].corporateNo;
            					var name = json.data[i].enterpriseName;
            					if( $.inArray(no,arr)=== -1){
            						arr.push(no);
            					};
            					if( $.inArray(name,arr)=== -1){
            						arr.push(name);
            					};
            				});
                			process(arr);
            			}
                    });
                }
            });
        }
        
        compatibleIENine();
    };

    /*兼容ie9不支持flex*/
    var compatibleIENine = function() {
		if(navigator.userAgent.indexOf("MSIE 9.0")>0 || navigator.userAgent.indexOf("MSIE 10.0")>0){
			$("._bottom").addClass("_bottomCss");
			$(".searchBox .line-searchBox .item-searchBox").addClass("_item-searchBox");
			$(".left-span").addClass("_left-span");
		}
	}
    //初始化方法
    _init();

})(jQuery);

var user = {
	getUserInfoByAjax: function (i) {
//		window.location.href = "/JXWL/view/index.html";
//		window.setTimeout(function(){
//			var userInfor = sessionStorage.getItem("userInfo");
//			if(!userInfor && i<3){
//			   getUserInfoByAjax(++i);
//			}
//		},2000);
		
//		window.location.href = "/JXWL/view/index.html";
//		window.setTimeout(function(){
//			var userInfor = sessionStorage.getItem("userInfo");
//			var txt = $('.testfull li b').text().substring(0,3);
//			if(txt == "未登录"){
//			   getUserInfoByAjax(++i);
//			}
//		},2000);
		
//		window.setTimeout(function(){
//			var userInfor = sessionStorage.getItem("userInfo");
//			var txt = $('.testfull li b').text().substring(0,3);
//			if(txt == "未登录"){
//				window.location.href = "/JXWL/view/index.html";
//			}
//		},2000);
		
//		var userInfor = sessionStorage.getItem("userInfo");
//		var txt = $('.testfull li b').text().substring(0,3);
//		if(txt == "未登录"){
//		   window.location.href = "/JXWL/view/index.html";
//		}
		
//		var userInfor = sessionStorage.getItem("userInfo");
//		var txt = $('.testfull li b').text().substring(0,3);
//		if(txt == "未登录"){
//			location.reload(true);
//		}
		
		var userInfor = sessionStorage.getItem("userInfo");
		if(userInfor=="undefined"||userInfor==null||userInfor==""){
			location.reload(true);
		}
    }
};

$(function(){
		console.log('进来了');
		user.getUserInfoByAjax(0);
   });
