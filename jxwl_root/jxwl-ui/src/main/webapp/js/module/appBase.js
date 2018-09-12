(function($) {
	// 通用方法扩展
	$.extend({
		backPath : '/JXWL/rest', // 后台使用路径
		frontPath : '/JXWL', // 前台资源请求
		// 格式化字符串
		formateString : function(str, obj) {
			return str.replace(/\{{(\w+)}\}/g, function(match, key) {
				return obj[key];
			});
		},
		getMenu : function() {
			var result = "";
			$.ajax({
				url : $.frontPath + '/js/template/appLeft-menu.html',
				type : 'post',
				cache : true,
				async : false
			}).done(function(json) {
				result = json;
			});
			return result;
		},
		getData : function(url) {
			var result = "";
			$.ajax({
				url : url,
				type : 'post',
				cache : false,
				async : false
			}).done(function(json) {
				result = json;
			});
			return result;
		},
		/**
		 * 根据数据字典编码返回数据字典名称
		 * 
		 * @param codeId
		 *            编码id
		 * @param dicObj
		 *            数据字典对象
		 * @returns {string} 数据字典名称
		 */
		getDicNameByCode : function(codeId, dicObj) {
			var name;
			if (codeId) {
				$.each(dicObj, function(index, item) {
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
		 * 获取数据字典全局方法
		 * 
		 * @param url_path
		 * @returns {string}
		 */
		getDataTwo : function(url_path, type, dataObj) {
			var TYPE = type || 'GET';
			var Obj = dataObj || {};
			var result = "";
			var cache = true;
			var explor = window.navigator.userAgent;
			if (explor.indexOf("Trident") >= 0 || explor.indexOf("MSIE") >= 0) {
				// ie浏览器
				if (TYPE == "POST") {
					cache = true;
				} else {
					cache = false;
				}
			} else {
				cache = true;
			}
			$.ajax({
				url : url_path,
				type : TYPE,
				cache : cache,
				data : Obj,
				async : false
			// 使用同步方式
			}).done(function(json) {
				if (json.code === 1 && json.data) {
					result = json.data;
				} else if ((json.code === 1) && (!json.data)) {
					Message.alert({
						Msg : "该数据不存在或为空",
						isModal : false
					});
					return;
				} else {
					Message.alert({
						Msg : json.msg,
						isModal : false
					});
					return;
				}

			});
			return result;
		}

	});

	$.fn.extend({
		// 生成左侧导航栏
		containerInit : function() {
			// 左侧导航栏
			var leftMenuHtml = $.getMenu();
			$('.mainContainer').before(leftMenuHtml);
			return this;
		},
		// 左侧导航栏高亮
		LeftMenuHighLight : function() {
			var $mainLeft = $("#leftMenuUl");
			var urlPath = location.pathname;
			var url = urlPath.substring(urlPath.lastIndexOf("/"),
					urlPath.length).split(",")[0];
			var $highLight = $mainLeft.find("a[href*='" + url + "']");
			$highLight.addClass("leftHighLight");
		},
		temp : function(obj) {
			var temp = this.html();
			return temp.replace(/\{\w+\}/gi, function(match) {
				var value = obj[match.substr(1, match.length - 2)];
				// TODO 注意返回是0的时候
				return value ? value : (value === 0 ? value : "");
			});
		},
		/**
		 * 表单填充数据信息
		 * 
		 * @param formData
		 */
		setFormSingleObj : function(formData) {
			var $this = $(this);
			var input = $this.find("[name]");
			if (!formData)
				return;
			$.each(input, function(index, item) {
				var _type = item.nodeName;
				var _input = $(item).attr("name");
				if (_type === 'INPUT' || _type === 'SELECT'
						|| _type === 'TEXTAREA') {
					$(this).val(
							formData[_input] !== null ? formData[_input] : '');
				} else {
					$(this).text(
							formData[_input] !== null ? formData[_input] : '');
				}

			});
			return this;
		}
	});
	// 初始化方法调用
	$('.mainContainer').containerInit().LeftMenuHighLight();
})(jQuery);