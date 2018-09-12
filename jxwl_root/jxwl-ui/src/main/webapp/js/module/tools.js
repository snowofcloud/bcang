/**
 * @description: 工具类，一些常用的工具方法，字符串日期，数据格式化等等
 * @author: 刘明明
 * @time: 2015-07-26
 */
var toolMethod = {
    /**
     * 路径配置
     */
    errorPath: {
        baseUrl: "/GDYY/view/error/"
    },
    /**
     * 获取用户权限信息
     * @param id 当前传入的数据信息
     * @returns 返回具有的权限实体
     */
    getModuleEntity: function (id) {
        var list = JSON.parse(localStorage.getItem("gdyy_list"));
        var result;
        if (!list) {
            window.location.href = "/GDYY";
        } else {
            if (id === undefined) {
                result = list["id" + toolMethod.getHrefValue()["id"]];
            } else {
                result = list["id" + id];
            }
        }
        return result;
    },
    /**
     * 获取用户登录的信息
     * @param id 取字段的任意一个值
     */
    getUserEntity: function (id) {
        var userInfo = JSON.parse(decodeURI(localStorage.getItem("gdyy_userInfo")));
        if (id) {
            return userInfo["id"];
        } else {
            return userInfo;
        }
    },
    /**
     * 序列数组转换成对象
     * @param array
     */
    getSerializeArrayChangeObject: function (array) {
        var formData = {};
        for (var key in array) {
            formData[array[key].name] = array[key].value;
        }
        return formData;
    },
    
    
    /**
     * 数据字典转换成文本信息
     * @param target 定位布标
     * @param dicArray 数据集合
     */
    changeDicToText: function (target, dicArray) {
        $.each(target, function (index, value) {
            var $this = $(this);
            var $code = $this.text();
            var id = $code.substr(0, 6);
            var obj = dicArray[id];
            $.each(obj, function (indexChild, valueChild) {
                if (obj[indexChild].code === $code) {
                    $this.text(obj[indexChild].name);
                    return;
                }
            });
        });
    },

    /**
     * 数据信息
     */
    windowPrint: function (url, dataResult,dataItem) {
        window.sessionStorage.setItem("url", url);
        window.sessionStorage.setItem("data", JSON.stringify(dataResult));
        window.sessionStorage.setItem("dataItem", JSON.stringify(dataItem));
    },
    connFun:function(){
    	 var _iframePrevious = $("#print-window");
         if (_iframePrevious) {
             _iframePrevious.remove();
         }
         window.open("/JXWL/view/print/print.html");
    },
    /**
     * 数据信息
     */
    
    /**
     * 浮点数转为度分秒
     * @param data 传入的浮点数据
     */
	floatToGreenMs : function(data) {
		var getValueString = data.toString();
		var getValue = getValueString.split(".");
		var getLen = getValue.length;
		var degreeLon = 0 ,minuteLon = 0, secondLon = 0, result = 0;
		var bIsNeg = false; 
		if( -1 !== getValue[0].indexOf("-")){
			bIsNeg = true;
		}
		if (1 == getLen) {// 整数
			degreeLon = Math.abs(data);
			secondLon = secondLon.toFixed(4);
		} else if (2 == getLen) {// 小数
			degreeLon = Math.abs(parseInt(getValue[0]));
			var getLocationMinuteE = '0.' + getValue[1];
			getLocationMinuteE = parseFloat(getLocationMinuteE) * 60;
			getLocationMinuteE = getLocationMinuteE.toString().split(".");
			var len = getLocationMinuteE.length;
			if (1 == len) {
				minuteLon = parseInt(getLocationMinuteE);
				secondLon = parseFloat(secondLon).toFixed(4);
			} else if (2 == len) {
				minuteLon = parseInt(getLocationMinuteE[0]);
				var getLocationSecondE = '0.' + getLocationMinuteE[1];
				getLocationSecondE = parseFloat(getLocationSecondE) * 60;
				secondLon = getLocationSecondE.toFixed(4);
			}
		}
		if(bIsNeg){
			  degreeLon = "-" + degreeLon;
		}
		return result = degreeLon + "°" + minuteLon + "'" + secondLon + "&quot;";
	},
    
    /**
     * 精确小数位数
     * @param x 传入的数据 1.256521
     * @param num 精确小数点后几位 2
     * @return 返回的数据信息
     */
    toFixed: function (x, num) {
        return Math.round(x * Math.pow(10, num)) / Math.pow(10, num);
    },
    /**
     * 除去重复的元素
     * @param data 需要删除的数据
     * @returns {Array} 返回数组内容 1 2 3 4 5 6
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
     * 格式化输出错误信息
     * @param obj 传入对象
     * @returns {String} 返回字符串
     */
    writeObj: function (obj) {
        var description = "";
        for (var i in obj) {
            var property = obj[i];
            description += property + "<br>";
        }
        return description;
    },

    /**
     * 获取url的值中
     * @returns {Array}
     */
    getHrefValue: function () {
        var search = {};
        search.pathName = window.location.pathname;
        var tabName = (window.location.href).split("#")[1];
        if (tabName) {
            search.tabName = tabName;
        }
        if (window.location.search) {
            var queryStr = window.location.search.split("?");
            gets = queryStr[1].split("&");
            for (var i = 0; i < gets.length; i++) {
                temp_arr = gets[i].split("=");
                search[temp_arr[0]] = temp_arr[1];
            }
        }
        return search;
    },

    /**
     * 关闭弹框的公共方法
     * @param target 当前需要关闭的表单id 或者 面板id
     * @param flag 是否是模态框
     * @param isRemove 是否将节点删除(单独用于js生成的弹框使用)
     * @returns {Boolean} 显示弹框
     */
    closePanel: function (target, flag, isRemove) {
        var args = arguments.length;
        var panel = $(target).parents(".panel");
        var modal = $("#modal-bg");
        if (!target || args <= 1) {
            throw new Error("你输入的target为:" + target + ";参数个数为：" + args);
        } else {
            var _panel = panel.length !== 0 ? panel : $(target);
            // 重置表单
            if (_panel.find("form")[0]) {
                _panel.find("form")[0].reset();
            }
            // 判断是否需要移除模态层
            if (flag) {
                // 新增需求，同时探出几层对话框的时候使用 层层覆盖使用
                if (modal.attr("style")) {
                    modal.removeAttr("style");
                } else {
                    modal.removeClass("modal-backdrop fade in");
                }
            }
            if (isRemove === true) {
                _panel.remove();
                return;
            }
            panel.find(".panel-shrink").unbind("click", toolMethod.closePanelBody);
            // 非模态弹框和模态弹框进行隐藏
            _panel.fadeOut(300);
            return {
                // 回调函数
                on: function (callback) {
                    if (callback && callback instanceof Function) {
                        callback();
                    }
                }
            };
        }
    },
    /*关闭地图气泡*/
    /*closeMarker :function (){
    	$('#popup').hide();
    },*/
    /**
     * 点击显示按钮显示对话框
     * @param target 传入当前上下文
     * @param domName 需要修改的dom节点
     * @param titleName 弹出框的名字
     */
    showPanel: function (domName, titleName, isModel) {
        var args = arguments.length;
        if (!domName) {
            throw new Error("你输入的target为:" + domName + ";参数个数为：" + args);
        } else {
            var $domName = $(domName);
            $domName.find(".panel-title").html(titleName || '');
            //是否需要模态
            if (!isModel) {
                $("body").append('<div class="panel-mask"></div>');
            }
            /*可以拖动*/
            $domName.draggable({handle: '.panel-heading', containment: 'html'});
            /*js原生态设置居中显示*/
            var left = document.documentElement.scrollLeft;
            var width = document.documentElement.clientWidth;
            $domName.css({
                'top': '86px',
                'left': left + (width - $domName.width()) / 2 + 'px',
                'right': 'auto',
                'width': $domName.width() + 'px'
            });
            $domName.fadeIn(300);
        }
    },

    /**
     * input表单只能输入数字
     * @param e 传入输入的值
     */
    onlyNum: function (obj, flag) {
        if (!flag) {
            obj.value = obj.value.replace(/[^\d]/g, "");
        } else {
            obj.value = obj.value.replace(/[^\d.]/g, "");
            obj.value = obj.value.replace(/^\./g, "");
            obj.value = obj.value.replace(/^\.{2,}/g, ".");
            obj.value = obj.value.replace(".", "#$#").replace(/\./g, "").replace("#$#", ".");
        }
    },

    /**
     * ajax全局配置使用,主要用于没有权限的操作
     */
    onSuccess: function (a, b, c, d) {
        // 无权限访问资源,将被拦截
        if (d.code === 4) {
            window.location.href = '';
        } else if (d.code === 5) {//没有权限
            window.location.href = '';
        }
    },

    /**
     * 使用javascript模拟MAP集合 主要的方法有
     * put、get、size、remove、isEmpty、containsKey、containsValue、clear、keys、values
     */
    MapUtil: {
        dataMap: {},

        /**
         * 根据key值 取出对应的值
         *
         * @param key
         * @returns {*}
         */
        get: function (key) {
            return this.dataMap[key];
        },

        /**
         * 存放一个值
         *
         * @param key
         * @param value
         */
        put: function (key, value) {
            this.dataMap[key] = value;
        },

        /**
         * 获取map数组的大小
         *
         * @returns {*}
         */
        size: function () {
            return this.values().length;
        },

        /**
         * 根据key的值移除对应的value值
         *
         * @param key
         */
        remove: function (key) {
            delete this.dataMap[key];
        },

        /**
         * 判断map集合是否为空
         *
         * @returns {boolean}
         */
        isEmpty: function () {
            return this.size === 0;
        },

        /**
         * 判断所给的key是否在集合中
         *
         * @param key
         * @returns {boolean}
         */
        containsKey: function (key) {
            return key in this.dataMap;
        },

        /**
         * 判断所给的value是否在集合中
         *
         * @param value
         * @returns {boolean}
         */
        containsValue: function (value) {
            return this.values().indexOf(value) > -1;
        },

        /**
         * 返回所给的value在集合中的位置，需要走过containsValue方法判断是否在集合中才能调用
         * @param value
         * @returns {boolean}
         */
        positionValue: function (value) {
            return this.values().indexOf(value);
        },

        /**
         * 清除集合里面的数据
         */
        clear: function () {
            for (key in this.dataMap) {
                delete this.dataMap[key];
            }
        },

        /**
         * 返回出所有的key值
         *
         * @param key
         * @returns {boolean}
         */
        keys: function () {
            keyList = [];
            for (key in this.dataMap) {
                keyList.push(key);
            }
            return keyList;
        },

        /**
         * 返回所有的value值
         *
         * @param value
         * @returns {boolean}
         */
        values: function () {
            valueList = [];
            for (key in this.dataMap) {
                valueList.push(this.dataMap[key]);
            }
            return valueList;
        }
    }
};

// 统一提示语
var prompt = {
    noSelectedLook: "没有选中要查看的数据",
    noSelectedEdit: "没有选中要编辑的数据",
    noSelectedDelete: "没有选中要删除的数据",
    noSelectedVerify: "没有选中要审核的数据",
    moreSelectedLook: "选中查看的记录不能超过1条",
    moreSelectedEdit: "选中编辑的记录不能超过1条",
    moreSelectedExport: "选中导出的记录不能超过1条",
    moreSelectedVerify: "选中审核的记录不能超过1条",
    moreSelectedDelete: "选中删除的记录不能超过1条",
    noSelectedExport: "没有选中要导出的数据",
    sureDelete: "确认删除数据吗?",
    editSuccess: "编辑成功",
    deleteSuccess: "删除成功",
    saveSuccess: "保存成功",
    handleSuccess: "操作成功",
    importSuccess: "导入成功",
    checkAndDo:"该运单不存在",
    enterDel4WayBill:"企业已被删除，不可修改",
    carDel4WayBill:"车辆已被删除，不可修改",
    checkUpODel:"该运单已修改或删除，请重试",
    waybillUndone:"当前订单还关联有未完成运单，无法确认完成",
    fileType: '*.png;*.PNG;*.jpg;*.JPG;*.jpeg;*.JPEG;*.doc;*.xlsx;*.xls;*.pdf;*.docx;*.xls'
};
/**
 * 全局配置ajax方法
 */
$(document).ajaxSuccess(toolMethod.onSuccess).ajaxComplete(toolMethod.onComplete);