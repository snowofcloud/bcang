/**
 * @description: 船舶监控 > 电子围栏 > 新增
 * @author: txm
 * @time: 2015-12-9
 */
$(function () {
	electronRail.addEleTools();

	//绑定按钮事件
	/*新增电子围栏事件*/
	$(".saveElecRail").bind("click",electronRail.showHideEleTools);
	
	/*修改电子围栏事件*/
	$(".updataElecRail").bind("click",electronRail.showPanelUpdate);
	
	/*编辑电子围栏规则事件*/
	$(".elecRailRegular").bind("click",electFenceRegular.showPanelAddRegular);
	
	/*删除电子围栏事件*/
	$(".deleteElecRail").bind("click",electronRail.elecRailDelete);
	
	/*定位电子围栏事件*/
	$(".locateElecRail").bind("click",electronRail.elecRailLocate);
	
	/*打开电子围栏事件*/
	$(".openElecRail").bind("click",electronRail.setElectOpen);
	
	/*关闭电子围栏事件*/
	$(".closeElecRail").bind("click",electronRail.cancelElectOpen);
	
	/*新增围栏经纬度*/
	$("#reloadElecTable input[type=radio]").click(function() {
		var val = $(this).val();
		var flag = true;
		if ( "floatInput"===val) {
			if (electronRail.config.flagFloatOrDeg) {
				flag = false;
			}
			$("#notFloatinput").css("display","none");
			$("#"+val).css("display","block");
			$(".lonLatInput").removeAttr("required");
			$(".floatInput").attr("required","true");
			electronRail.config.flagFloatOrDeg = true;
		} else {
			if (!electronRail.config.flagFloatOrDeg) {
				flag = false;
			}
			$("#floatInput").css("display","none");
			$("#"+val).css("display","block");
			$(".floatInput").removeAttr("required");
			$(".lonLatInput").attr("required","true");
			electronRail.config.flagFloatOrDeg = false;
		}
		if (flag) {
			electronRail.floatAndLatLon();
		}
	});
	
	/*新增经纬度验证*/
	$("#electronRailFloatForm").html5Validate(function (e) {
		electronRail.updateCoordinate();
		/*判断输入3个坐标点才能提交*/
		var len = $("#electronRailTabel").jqGrid('getDataIDs');
		if (len.length>2) {
			$("#submitElectRailbtn").removeClass("disabled");
		}	
        return false;
    });
	
	/*围栏名称验证*/
	$("#electronRailNameForm").html5Validate(function (e) {
		electronRail.submitElectRail();
        return false;
    });
});

var electronRail = {		 
	/*数据参数配置*/
    config: { 
    	elecRailflagSaOrUpd:true,//新增或者编辑电子围栏信息，true新增，false更新
    	isOpenOrClose: true,         //默认true为开启，否false为取消常用联系人
    	elecRailadd: curdPath.getUrl() + "electronicFence/findByPage", //电子围栏弹框默认加载方法
		elecRailSave: curdPath.getUrl() + "electronicFence/save", //保存新增电子围栏
		elecRailUpdata: curdPath.getUrl() + "electronicFence/update", //编辑新增电子围栏
		elecRailDelete: curdPath.getUrl() + "electronicFence/delete",//删除电子围栏信息
		elecRailFindByIdLimitPageUrl: curdPath.getUrl() + "electronicFence/findById", //根据ID获取电子围栏信息
	    elecRailStatus: curdPath.getUrl() + "electronicFence/modify", //电子围栏开启或关闭状态
		id: null, //临时存储访问表格id号
		flagFloatOrDeg: false, //判断是浮点数输入还是度分秒， true为浮点数，false为度分秒
		DegreeArray: null, //临时存储编辑围栏坐标点时度分秒标记
	    pointSet: new Array(), //暂存手绘围栏经纬度坐标点（对象型）
	    pointList: new Array(),  //暂存手绘围栏经纬度坐标点（数组型）
	    coordinateArray: new Array(), //暂存输入围栏经纬度坐标点
	    isdrawMode: false,  //存储绘图标记
	    elecRailOpenOrClose: true, //判断围栏在地图中的状态：true:已开启，false:已关闭
	    keepPage: $('#input_electronicFencePage input').val()
	},
	/*显示弹框 新增电子围栏*/
    showPanelSave: function () {
    	electronRail.config.elecRailflagSaOrUpd = true;
    	electronRail.config.isdrawMode = false;
    	/*判断页面中无表格标签时，添加相应表格标签元素*/
		if (0 === $("#outElectronRail").length) {
			$("#electronRailNameForm").prepend("<div id='outElectronRail' class='panelOverFlow'" +
				" style='width:95%; margin:5px auto;'>"+
		        "<table id='electronRailTabel'></table>"+
		        "<!-- 电子围栏-新增经纬度  表格 翻页 -->"+
		        "<div id='electronRailPage'></div>"+
		        "</div>");
			}
        toolMethod.showPanel("#electronRailAdd", "新增电子围栏");
        $("#lonLatBtn").click();
        $(".drawHidden").css("display","block");/*显示经纬度输入框*/  
        if ($("#lonLatBtn").is(":checked")) {
        	$("#floatInput").css("display","none"); /*浮点数输入框默认隐藏*/
        } else {
        	$("#notFloatinput").css("display","none"); /*度分秒输入框默认隐藏*/
        }
        $("#submitElectRailbtn").addClass("disabled");
        /*加载完页面后，再初始化表格*/
        electronRail.initialization();
        electronRail.config.coordinateArray = [];
    	electronRail.config.pointSet = [];
    	electronRail.config.pointList = [];
    },

    /**
	 * 显示弹框 编辑电子围栏
	 * @param 当前对象
	 */ 
	showPanelUpdate: function (arr) {
		var ids = $("#electronicFenceTable").jqGrid("getGridParam", "selarrrow");
	    var len = ids.length;
	    electronRail.config.id = ids[0];
	    if (len > 1) {
	    	Messager.alert({
	    		Msg: prompt.moreSelectedEdit,
	            isModal: false
	        });
	        return;
	    } else if (len <= 0) {
	        Messager.alert({
	            Msg: prompt.noSelectedEdit,
	            isModal: false
	        });
	        return;
	    } else {
	    	electronRail.config.elecRailflagSaOrUpd = false;
	    	var dataForm = electronRail.getElecRailLimitListMsg(electronRail.config.id);
	    	if(dataForm){
	    		toolMethod.showPanel("#electronRailAdd", "编辑电子围栏");
	    		$("#lonLatBtn").click();
	    		/*获得电子围栏坐标点集，并填充在表格里*/
	            electronRail.fillelecRialTable(dataForm);
	            $("#submitElectRailbtn").removeClass("disabled");
	            /*判断围栏时开启还是关闭*/
	            if ("163001003" === dataForm.status) {
	            	electronRail.config.elecRailOpenOrClose = true;
	            } else {
	            	electronRail.config.elecRailOpenOrClose = false;
	            }
	            
	    		/*判断是手动输入的电子围栏还是绘图的电子围栏*/
	    		if (1 ==dataForm.drawMode) {
	    			electronRail.config.isdrawMode = true;
	    			$(".drawHidden").css("display","none"); /*隐藏经纬度输入框*/
	    		} else {
	    			electronRail.config.coordinateArray = JSON.parse(dataForm.pointSet);
	    			electronRail.config.isdrawMode = false;
	    			$(".drawHidden").css("display","block");/*显示经纬度输入框*/
	    			if ($("#lonLatBtn").is(":checked")) {
	    	        	$("#floatInput").css("display","none"); /*浮点数输入框默认隐藏*/
	    	        } else {
	    	        	$("#notFloatinput").css("display","none"); /*度分秒输入框默认隐藏*/
	    	        }			
	    		}	
	    		electronRail.config.pointSet = JSON.parse(dataForm.pointSet);
	        	electronRail.config.pointList = JSON.parse(dataForm.pointList);
	    		/*将围栏名称填充到页面中*/
	        	$("#electronRailName").val(dataForm.name);
	        	electronRail.floatAndLatLon();
	    	}else{
	    		Messager.show({Msg: "页面过期，请重新登录", isModal: false});
	    		return;
	    	}
	    }
	},
    
   
	
	/*将后台获取的数据填充到表格中*/
	fillelecRialTable: function(Set) {	
		/*判断页面中无表格标签时，添加相应表格标签元素*/
		if (0 === $("#outElectronRail").length) {
			$("#electronRailNameForm").prepend("<div id='outElectronRail' class='panelOverFlow'" +
				" style='width:95%; margin:5px auto;'>"+
		        "<table id='electronRailTabel'></table>"+
		        "<!-- 电子围栏-新增经纬度  表格 翻页 -->"+
		        "<div id='electronRailPage'></div>"+
		        "</div>");
		}
		/*判断是手绘围栏还是输入的围栏*/	
		if (1 == Set.drawMode) {//手绘
			$("#electronRailTabel").jqGrid({
				data:JSON.parse(Set.pointSet),
				datatype: "local",
				colNames: ["北纬", "东经"],
				colModel: [
                    {name: "latitude", index: "1", align: "center", width:"20px", sortable:false,
                    	formatter: function (cellvalue, options, rowObject) {
                    		return toolMethod.changeToDMS(rowObject["latitude"]);
                        }
                    },
				    {name: "longitude", index: "2", align: "center", width:"20px", sortable:false,
				    	formatter: function (cellvalue, options, rowObject) {
				    		return toolMethod.changeToDMS(rowObject["longitude"]);
		                }
                    }
				    
				],
				loadonce: false,
	            viewrecords: true,
	            autowidth: true,
	            height: 300,
	            rownumbers: true,
	            multiselect: false,
	            rowNum: 10, // 每页显示记录数
				rowList:  [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
				pager: "#electronRailPage"
			}); 
		} else {
			$("#electronRailTabel").jqGrid({
				data:JSON.parse(Set.pointSet),
				datatype: "local",
				colNames: ["北纬", "东经"],
				colModel: [
                    {name: "latitude", index: "1", align: "center", width:"20px", sortable:false},
				    {name: "longitude", index: "2", align: "center", width:"20px", sortable:false}
				    
				],
				loadonce: false,
	            viewrecords: true,
	            autowidth: true,
	            height: 300,
	            rownumbers: true,
	            multiselect: true,
	            scroll:30,
	            multiboxonly: true,
	            rowNum: 65535,
				rowList:  [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
				pager: "#electronRailPage"
			}); 
		}
	},
	
	/**
	 *删除终端注册
	 * @param 当前对象 
	 */
	elecRailDelete: function (arr) {
		var ids = $("#electronicFenceTable").jqGrid('getGridParam','selarrrow');
		var len = ids.length;
		if(len <= 0) {
			Messager.alert({Msg:prompt.noSelectedDelete,'isModal': false});
			return;
		}else {	 
			 Messager.confirm({Msg:prompt.sureDelete, iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 $.ajax({
						 url: electronRail.config.elecRailDelete + "/" + ids,
						 type: 'POST',
						 dataType: 'json',
						 data:{
					     	   'id':ids ,
					        },
						 async: false,
						 success: function(json) {
							 if(json.code === 1) {
								 $("#electronicFenceTable").jqGrid('setGridParam', {postData: {}, page: electronRail.config.keepPage}).trigger("reloadGrid");
								 //清除地图中相应的围栏
								 for (var i = 0; i < len; i++) {
									 HthxMap.clearMeasure(HthxMap.electronRailIndex[ids[i]]);
									 HthxMap.electronRail[HthxMap.electronRailIndex[ids[i]]] = null;    //用于区分标绘和测量
									 Messager.show({Msg: json.msg, isModal: false});
								 };
							 }else if(json.code === 3){
								 Messager.alert({Msg:json.msg,iconImg:"warning", isModal: false});
								 $("#electronicFenceTable").jqGrid('setGridParam', {postData: {}, page: electronRail.config.keepPage}).trigger("reloadGrid");
							 }
						 },
					 });
				 }
			 });
		}
	},
	
	/**
	 * 根据电子围栏id定位电子围栏
	 */
	elecRailLocate:function(){
		var ids = $("#electronicFenceTable").jqGrid("getGridParam", "selarrrow");
	    var len = ids.length;
	    electronRail.config.id = ids[0];
	    var msg1 = "定位记录不能选中超过1条";
	    var msg2 = "没有选中要定位的报警数据";
	    var msg3 = "该电子围栏已停用，请开启后再定位";
	    if (len > 1) {
	    	Messager.alert({
	    		Msg: msg1,
	            isModal: false
	        });
	        return;
	    } else if (len <= 0) {
	        Messager.alert({
	            Msg:msg2,
	            isModal: false
	        });
	        return;
	    } else {
	    	electronRail.config.elecRailflagSaOrUpd = false;
	    	var rowData = $("#electronicFenceTable").jqGrid("getRowData",electronRail.config.id);
	    	if("已停用" === rowData.status){
		    	Messager.alert({
		    		Msg: msg3,
		            isModal: false
		        });
		        return;
	    	}
	    	var dataForm = electronRail.getElecRailLimitListMsg(electronRail.config.id);
	    	if(dataForm){
	    		//根据坐标点定位电子围栏
	    		var obj = electronRail.getLonLatAndName(dataForm);
				var coordinate = [obj.lon, obj.lat];
				var coor1 = ol.coordinate.toStringHDMS(coordinate);
				var contentStr = '<div style="width:200px" align="center">'+coor1+'<input type="hidden" id="markerName" value="'+ obj.eleFencename+'"></div>';
				var url = HthxMap.Settings.IMG.marker[6];
				
				locateByPlace(coordinate, contentStr,url); 
				//收缩弹出框
        		$("#electronicFence .panel-shrink").click();
	    	}else{
	    		Messager.show({Msg: "页面过期，请重新登录", isModal: false});
	    		return;
	    	}
	    }
	},
	
	/**
	 * 根据电子围栏的坐标点计算中心点坐标
	 */
	getLonLatAndName:function(data){
		var name = data.name;
		var pts = JSON.parse(data.pointList);
		var ptsLen = pts.length;

		if(ptsLen < 1){
			return;
		}
		if(ptsLen === 1){
			var lon = parseFloat(pts[0][0]);
			var lat = pts[0][1];
		}else if(ptsLen === 2){
			lon = (parseFloat(pts[0][0]) + parseFloat(pts[1][0]))/2;
			lat = (parseFloat(pts[0][1]) + parseFloat(pts[1][1]))/2;
		}else if(ptsLen >= 3){
			var points = pts;
			var polygonGeo = new ol.geom.Polygon([pts]);
			var interPoint = polygonGeo.getInteriorPoint().getCoordinates();
			lon = interPoint[0];
			lat = interPoint[1];
		}
		
		return {"lon":lon,"lat":lat,"eleFencename":name};
	},
	
	/**
	 * 根据围栏id获取围栏信息,
	 * @param id 电子围栏的唯一ID返回查询出来的json数据
	 * @returns
	 */
	getElecRailLimitListMsg: function(id) {
		var electData = null;
	    $.ajax({
	        url: electronRail.config.elecRailFindByIdLimitPageUrl + "/" + id,
	        dataType: 'json',
	        data:{
		     	   'id': id,
		        },
	        type: "POST",
	        async: false,
	        success: function (json) {
	     	   if (json["code"] === 1) {
	     		  electData = json.data
	            }
	        }
	    });
	    return electData;
	},
    
	/*关闭电子围栏弹框*/
    closePanel: function() {
    	toolMethod.closePanel("#electronRailAdd", true);
    	if ($("#outElectronRail").length > 0) {
    		$("#outElectronRail").remove();
    	}
    	//清空围栏名称文本内容
		$("#electronRailName").val("");
		$(".clearSet").val("");
		/*清空地图中的围栏*/
		clearElectRail();
    },
    
    /*关闭电子围栏弹框*/
    closeElecPanel: function() {
    	if ($("#outElectFenceTable").length > 0) {
    		$("#outElectFenceTable").remove();
    	}
    	$("#electronicFence").addClass("hidden");
    },
    
    /*将度分秒转换为浮点数*/
    getFloatData: function(arr) {
//    	var lonSet = electronRail.lonlatToFloat(arr[0].join());
//    	var latSet = electronRail.lonlatToFloat(arr[1].join());
    	var lonSet = toolMethod.changeToFloat(arr[0]);
    	var latSet = toolMethod.changeToFloat(arr[1]);
    	
    	return [lonSet, latSet];
    },

    lonlatToFloat: function(origin) {
    	var arr = "";
    	if (-1 === origin.indexOf("°")) {
    		arr = origin.split(",");
    	} else {
    		arr = [];
			arr.push(origin.split("°")[0]);
			arr.push(origin.split("°")[1].split("′")[0]); 
			arr.push(origin.split("°")[1].split("′")[1].slice(1,-2));
			
    	}
        var target = 0.0;
        var sec = parseFloat(arr[2])/(60*60);
        var min = parseFloat(arr[1])/60;
        if (parseInt(arr[0]) < 0) {
        	arr[0] = Math.abs(parseInt(arr[0]));
        	target = -(sec + min + arr[0]);
        }else{
        	target = sec + min + parseInt(arr[0]);
        }
        return target;
    },
    
    /**
     *
     * 新增经纬度坐标*/
    updateCoordinate: function() {
    	var coordinateObj = {};
    	var coordinateObj1 = {};
    	//浮点数
    	if($("#floatInput").css("display") === "block"){
    		coordinateObj1.longitude = parseFloat($("#electronLon").val());
        	coordinateObj1.latitude = parseFloat($("#electronLat").val());
        	/*新增电子围栏，则保持所有坐标点*/
        	coordinateObj.longitude = parseFloat($("#electronLon").val()).toFixed(6);
        	coordinateObj.latitude = parseFloat($("#electronLat").val()).toFixed(6);
    		electronRail.config.coordinateArray.push(coordinateObj1); //保存所有坐标点，填充到表格中
    		electronRail.config.pointSet.push(coordinateObj1); //保存所有坐标点（对象型）
        	electronRail.config.pointList.push([parseFloat(coordinateObj1.longitude), 
        	                                    parseFloat(coordinateObj1.latitude)]);  //保存所有坐标点（数组型）     	
    	}else{  //度分秒
    		var degreeLon = parseFloat($("#electronDegreeLon").val());
        	var minuteLon = parseFloat($("#electronMinuteLon").val());
        	var secondLon = parseFloat($("#electronSecondLon").val());
        	var arrayLon = [degreeLon, minuteLon, secondLon];
        	//var lonSet = electronRail.lonlatToFloat(arrayLon.join());
        	var lonSet = toolMethod.changeToFloat(arrayLon);
        	if ((180 == Math.abs(degreeLon) && (parseInt(minuteLon)|| parseInt(secondLon))) ||
        			(Math.abs(lonSet) > 180) ) {
    			Messager.alert({Msg:"经度填写错误,不能超过最大值正负180",'isModal': false});
    			$("#electronDegreeLon").focus();
    			return;
    		}
        	
        	/*纬度坐标拼接*/
        	var degreeLat = parseFloat($("#electronDegreeLat").val());
        	var minuteLat = parseFloat($("#electronMinuteLat").val());
        	var secondLat = parseFloat($("#electronSecondLat").val());
        	var arrayLat = [degreeLat, minuteLat, secondLat]; 
        	//var latSet = electronRail.lonlatToFloat(arrayLat.join());
        	var latSet = toolMethod.changeToFloat(arrayLat);
        	if ((90 == Math.abs(degreeLat) &&(parseInt(minuteLat)|| parseInt(secondLat))) || 
        			(Math.abs(latSet) > 90)) {
    			Messager.alert({Msg:"纬度填写错误,不能超过最大值正负90",'isModal': false});
    			$("#electronDegreeLat").focus();
    			return;
    		}
        	
    		coordinateObj.longitude = degreeLon+'\u00b0 '+minuteLon+'\u2032 '+secondLon.toFixed(4)+'\u2033 ';
        	coordinateObj.latitude = degreeLat+'\u00b0 '+minuteLat+'\u2032 '+secondLat.toFixed(4)+'\u2033 ';
        	//转换坐标
        	var floatCoordinates = electronRail.getFloatData([[degreeLon, minuteLon, secondLon], [degreeLat, minuteLat, secondLat]]);
        	floatCoordinates = ol.coordinate.toStringXY(floatCoordinates, 6);  //保留小数点后6位
        	floatCoordinates = floatCoordinates.split(",");
        	floatCoordinates = [JSON.parse(floatCoordinates[0]), JSON.parse(floatCoordinates[1])]; 	
	    	/*度分秒转换成浮点数存入对象型数组中*/
	    	var tempCoordiObj = {};
	    	tempCoordiObj.longitude = floatCoordinates[0];
	    	tempCoordiObj.latitude = floatCoordinates[1];
	    	electronRail.config.coordinateArray.push(coordinateObj); //保存所有坐标点，填充到表格中
	    	electronRail.config.pointSet.push(tempCoordiObj); //保存所有坐标点（对象型）
	    	electronRail.config.pointList.push(floatCoordinates);  //保存所有坐标点（数组型）
    	} 
    	/*填充经纬度到表格中*/
    	electronRail.addCoordinate(coordinateObj);
    	/*清空经纬度输入框*/
	 	$(".clearSet").val("");
    },
    
    /**
     * 参数：0：纬度，1：经度
     * 填充经纬度坐标到表格中*/
    addCoordinate: function(coordinateObj) {
//    	/*将数据添加到表格中*/
//    	$("#electronRailTabel").jqGrid('setGridParam', {
//    		data: electronRail.config.coordinateArray,
//    		datatype: "local",
//			page : 1
//		}).trigger("reloadGrid");
    	
    	/*将经纬度数据填充到表格中*/
//    	var selectedId = $("#electronRailTabel").jqGrid('getGridParam', 'selrow');
//    	var ids = $("#electronRailTabel").jqGrid('getDataIDs');
    	var rowid = Math.random();
//    	if (0 != ids.length) {
//    		rowid = Math.max.apply(Math,ids) + 1;
//    	} else {
//    		rowid = 0;
//    	};
    	$("#electronRailTabel").jqGrid("addRowData", rowid, coordinateObj, "last");
    },
    

    /*浮点数与度分秒互转*/
    floatAndLatLon: function() {
    	/*先转换pointSet为度分秒格式*/
    	var leng = $("#electronRailTabel").jqGrid("getDataIDs");
    	if (electronRail.config.flagFloatOrDeg) {
    		for (var i = 0; i < leng.length; i++) {
    			var cellLon = $("#electronRailTabel").jqGrid("getCell", leng[i], "longitude");
    			var cellLat = $("#electronRailTabel").jqGrid("getCell", leng[i], "latitude");
//    			var longitude = electronRail.lonlatToFloat(cellLon);
//    			var latitude = electronRail.lonlatToFloat(cellLat);
    			var longitude = toolMethod.changeToFloat(cellLon);
    			var latitude = toolMethod.changeToFloat(cellLat);
    			var coordSet = ol.coordinate.toStringXY([longitude, latitude], 6);  //保留小数点后6位
    			coordSet = coordSet.split(",");
    			$("#electronRailTabel").jqGrid("setCell",leng[i],"longitude", coordSet[0]);
    			$("#electronRailTabel").jqGrid("setCell",leng[i],"latitude", coordSet[1]);
    		}
    	} else {
    		for (var i = 0; i < leng.length; i++) {
    			var cellLon = $("#electronRailTabel").jqGrid("getCell", leng[i], "longitude");
    			var cellLat = $("#electronRailTabel").jqGrid("getCell", leng[i], "latitude");
    			var longitude = toolMethod.changeToDMS(JSON.parse(cellLon));
    			var latitude = toolMethod.changeToDMS(JSON.parse(cellLat));
    			$("#electronRailTabel").jqGrid("setCell",leng[i],"longitude", longitude);
    			$("#electronRailTabel").jqGrid("setCell",leng[i],"latitude", latitude);
    		}
    	}
    	
    },
    
    degreesToStringHDMS: function(degrees) {
    	 var normalizedDegrees = goog.math.modulo(degrees + 180, 360) - 180;
    	 var x = Math.abs(Math.round(3600 * normalizedDegrees));
    	 if (degrees < 0) {
    		 return '-' + Math.floor(x / 3600) + '\u00b0 ' +
      				Math.floor((x / 60) % 60) + '\u2032 ' +
      				Math.floor(x % 60) + '\u2033 '; 
    	 } else {
    		 return Math.floor(x / 3600) + '\u00b0 ' +
   	      			Math.floor((x / 60) % 60) + '\u2032 ' +
   	      			Math.floor(x % 60) + '\u2033 '; 
    	 } 
    },

    /*删除单个围栏的坐标点*/
    deleteSet: function() {
    	var selectedRowIds = $("#electronRailTabel").jqGrid('getGridParam','selarrrow');
    	var len = selectedRowIds.length;
    	if(len <= 0) {
			Messager.alert({Msg:prompt.noSelectedDelete,'isModal': false});
			return;
		}else {	
			Messager.confirm({Msg:prompt.sureDelete, iconImg:'question', 'isModal': true}).on(function (flag) {
				 if(flag){
					 for (var i = 0; i < len; i++) {
						 var arr = $("#electronRailTabel").jqGrid('getDataIDs');
						 var index = electFenceRegular.searchSameId(arr, selectedRowIds[0]);
						 if(index != -1){
							 electronRail.config.pointSet.splice(index, 1);
							 electronRail.config.pointList.splice(index, 1);
							 electronRail.config.coordinateArray.splice(index, 1);
		        		}
						 $("#electronRailTabel").jqGrid("delRowData",selectedRowIds[0]);  
					 }
					 var length = $("#electronRailTabel").jqGrid('getDataIDs');
					 if (length.length < 3) {
						 $("#submitElectRailbtn").addClass("disabled");							 
					 }
				 }
			 });
		}
    },
    
    /**
	 * 添加或者修改电子围栏信息,
	 * @param name 围栏名称，data 围栏坐标点集
	 * @returns
	 */
    submitElectRail: function() {
    	var flag = electronRail.config.elecRailflagSaOrUpd;
    	var control = $("#electronRailName");
    	var text = $("#electronRailText").text();
    	/*验证围栏名称是否为空*/
		var electName = control.val();
		electName = electName.replace(/(^\s*)|(\s*$)/g, "");
		if ("" === electName) {
			control.val("");
			control.testRemind("您尚未输入"+ text);
    		control.focus();
			return;
		}
		var drawModeSta = "0";
		/*判断是手动输入电子围栏还是手绘电子围栏*/
    	if(electronRail.config.isdrawMode) {
    		drawModeSta = "1";
    	} 
    	var data = {
				"id":electronRail.config.id,
    			"name": electName,
    			"pointSet": JSON.stringify(electronRail.config.pointSet),
    			"pointList": JSON.stringify(electronRail.config.pointList),
    			"drawMode":drawModeSta
    		};
    	
    	$.ajax({
    		url: flag ? electronRail.config.elecRailSave : electronRail.config.elecRailUpdata,
    		type: "POST",
    		dataType: "json",
    		data: data,
    		async: false,
    		success: function(json) {
    			var coor;
    			if (json["code"] === 1) { 
					if (flag) {
						/*围栏为打开状态--填充围栏到地图中，且获得中心点*/
						var tmpPoint = electronRail.config.pointList;
		        		var len = tmpPoint.length;
		        		if((data.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
		        			coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
		        		}else{
		        			coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle()); //多边形
		        		}	   	
					} else {
						/*判断围栏开启状态下，先关闭围栏*/
    					if (electronRail.config.elecRailOpenOrClose) {
    						HthxMap.clearMeasure(HthxMap.electronRailIndex[json.data]);	
    					}		
    					dataList = electronRail.config.pointList;    					
 	    				/*围栏为打开状态--填充围栏到地图中，且获得中心点*/
    					var tmpPoint = dataList;
    	        		var len = tmpPoint.length;
    	        		if((data.drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
    	        			coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
    	        		}else{
    	        			coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle()); //多边形
    	        		}	 
					}
					/*围栏为打开状态--填充围栏名称*/
					createElecNameElement(electName, coor,HthxMap.measureIndex, json.data);
	     			HthxMap.electronRailIndex[json.data] = HthxMap.measureIndex;
	     			HthxMap.measureIndex++;
	     			map.getView().setCenter(coor);
	     			if (flag) {
	     				$("#electronicFenceTable").jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
	     			}else{	//编辑
	     				$("#electronicFenceTable").jqGrid('setGridParam', {postData: {}, page: electronRail.config.keepPage}).trigger("reloadGrid");
	     			}
					
                    //清空围栏名称文本内容
            		$("#electronRailName").val("");
            		if ($("#outElectronRail").length > 0) {
                		$("#outElectronRail").remove();
                	}
            		toolMethod.closePanel("#electronRailAdd", true);
            		electFenceRegular.showPanelAddRegular(json.data, electName);
                    Messager.show({Msg:json["msg"], isModal: false});
                	return false;					
    			}else if (json["code"] === 3) {
                	Messager.alert({Msg: json["msg"], iconImg: "warning", isModal: false});
                    return false;
                } else {
                	 Messager.alert({Msg: json["msg"], iconImg: "error", isModal: false});
                     return false;      
                }
    		}
    	});
    },
    
    
    /**
	 * 电子围栏-弹框
	 */
    shwoElectronicFence :function(){
    	/*判断页面中无表格标签时，添加相应表格标签元素*/
		if (0 === $("#outElectFenceTable").length) {
			$("#elecFencePanel").append("<div id = 'outElectFenceTable' class='panelOverFlow'>"+
											"<table id='electronicFenceTable' class='lawcaseTable'></table>"+
											"<div id='electronicFencePage'></div>"+
										"</div>"
										);
		}
		//显示弹框
		//showPanelOnMap("#electronicFence");
		toolMethod.showPanel("#electronicFence", "电子围栏", true);
		var url = electronRail.config.elecRailadd;
		$("#electronicFenceTable").jqGrid({
	        	url:url,
	            mtype: "POST",
	            datatype: "JSON",
	            colNames: ["围栏ID","名称", "新增时间","添加人","状态"],
	            colModel: [
	                {name: "id", index: "1", align: "center", width:"20px",hidden:true, sortable:false},
	                {name: "name", index: "2", align: "center", width:"20px", sortable:false},
	                {name: "updateTime", index: "3", align: "center", width:"20px", sortable:false},
	                {name: "updateBy", index: "4", align: "center", width: "20px", sortable:false},
	                {name: "status", index: "5", align: "center", width:"20px", sortable: false,
	                	formatter: function (cellvalue, options, rowObject) {
			        		  var statusType = "";
			        		  switch(rowObject.status) {
			    				case '163001003': statusType = "已启用";break;
			    				default:statusType = "已停用";
			        		  }
			        		  return statusType; 
		                  }
	                }
	            ],
	            loadonce: false,
	            viewrecords: true,
	            autowidth: true,
	            height: 350,
	            rownumbers: true,
	            multiselect: true,
	            multiboxonly: true,
	            rowNum: 10, // 每页显示记录数
	            rowList:  [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
	            pager: "#electronicFencePage",
	        }); 
	},
    
	/*初始化电子围栏表格*/
    initialization:function () {
    	$("#electronRailTabel").jqGrid({
			mtype: "POST",
			datatype: "local",
			colNames: ["北纬", "东经"],
			colModel: [
                {name: "latitude", index: "1", align: "center", width:"20px", sortable:false},
			    {name: "longitude", index: "2", align: "center", width:"20px", sortable:false}
			    
			],
			loadonce: false,
            viewrecords: true,
            autowidth: true,
            height: 300,
            rownumbers: true,
            multiselect: true,
            scroll:30,
            multiboxonly: true,
            rowNum: 65535,
			rowList:  [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
			pager: "#electronRailPage"
    	}); 
    },
	
	/************************************************设置围栏开关******************************************************/
    /**
	 *打开电子围栏按钮事件
	 *  
	 */
    setElectOpen: function() {
		var ids = $("#electronicFenceTable").jqGrid('getGridParam','selarrrow');
		var len = ids.length;
		if(len <= 0) {
			Messager.alert({Msg:"没有选择显示项", isModal: false});
			return;
		}else{
			var dataID = new Array();
			for (var i = 0; i < len; i++) {				
				/*获取单条围栏信息*/
				var dataForm = $("#electronicFenceTable").jqGrid('getRowData', ids[i]);
				if (dataForm) {
					/*判断该是否打开围栏*/
					if ("已停用" === dataForm.status) {
						dataID.push(dataForm.id);
					}
				} else {
					Messager.show({Msg: "该围栏已停用，请重新选择", isModal: false});
					return;
				}					
			}
			if(0 != dataID.length) {
				electronRail.config.isOpenOrClose = true; 
				electronRail.setElectRail(dataID);
			} else {
				var data = dataForm.pointList;/*获得单条围栏的坐标集*/
				if ("[]" == data) {
					Messager.show({Msg: "该条围栏无数据，请重新选择", isModal: false});
				} else {
					Messager.show({Msg: "该围栏已启用，请重新选择", isModal: false});
				}
			}
		}
	}, 
	
	/*关闭电子围栏按钮事件*/
	cancelElectOpen:function(index) {
		var ids = new Array();
		if (typeof index != "object") {
			ids.push(index);
		} else {
			ids = $("#electronicFenceTable").jqGrid("getGridParam", "selarrrow");
		}
		var len = ids.length;
		if (len <= 0) {
			Messager.alert({Msg:"没有选择停用项", isModal: false});
			return;
		}else{
			var dataID = new Array();
			/*获取单条围栏信息*/
			if (typeof index === "object") {
				//清除地图中相应的围栏
				for (var i = 0; i < len; i++) {
					var dataForm = $("#electronicFenceTable").jqGrid('getRowData', ids[i]);
						/*判断该是否打开围栏*/
					if ("已启用" == dataForm.status) {
						dataID.push(dataForm.id);	
					}
				};
			} else {
				var dataForm = electronRail.getElecRailLimitListMsg(ids);
				if ("163001003" == dataForm.status) {  
					dataID.push(dataForm.id);	
				}
			}
			if (0 != dataID.length) {
				electronRail.config.isOpenOrClose = false;
				electronRail.setElectRail(dataID);
			} else {
				Messager.show({Msg: "该围栏已停用，请重新选择", isModal: false});
			}
		}
	},
	
	/*设置围栏开关*/
	setElectRail: function(ids) {
		var elecstatus = {
				"ids" : ids.join(","),
				"status": electronRail.config.isOpenOrClose ? 163001003 : 163001004
		}
		$.ajax({            
    		url: electronRail.config.elecRailStatus,
     		type: "POST",
            async: false,
            data: elecstatus,
            success: function (json) { 
            	if (json["code"] === 1) { 
            		/*判断打开围栏还是关闭围栏*/
            		if (electronRail.config.isOpenOrClose) {
            			$("#electronicFenceTable").jqGrid('setGridParam', {postData: {}, page: electronRail.config.keepPage}).trigger("reloadGrid");
            			/*循环在地图中绘制多条围栏*/
            			for (var i = 0; i < ids.length; i++) {
            				var tmpPoint = JSON.parse(json.data[i].pointList);
     	           		    var len = tmpPoint.length;
     	           		    var coor;
     	           		    if((json.data[i].drawMode == '1') && (!HthxMap.isTwoCoorEqual(tmpPoint[0], tmpPoint[len-1]))){
     	           		        coor = HthxMap.drawLineString([tmpPoint], creatStyle.electDrawStyle());   //线
     	           		    }else{
     	           		        coor = HthxMap.drawMultiPolygon(tmpPoint, creatStyle.electDrawStyle()); //多边形
     	           		    }
            				createElecNameElement(json.data[i].name, coor, HthxMap.measureIndex, json.data[i].id);
    		     			HthxMap.electronRailIndex[json.data[i].id] = HthxMap.measureIndex;
    		     			HthxMap.electronRail[HthxMap.measureIndex] = json.data[i].id;    //用于区分标绘和测量
    		     			HthxMap.measureIndex++;
    		     			if (ids.length < 2) {
    		     				/*将地图中心点移动到围栏处*/
    		     				map.getView().setCenter(coor);
    		     			}
            			};
            		} else {
            			$("#electronicFenceTable").jqGrid('setGridParam', {postData: {}, page: electronRail.config.keepPage}).trigger("reloadGrid");
            			for (var i = 0; i < ids.length; i++) {
            				//清除地图中相应的围栏
        					HthxMap.clearMeasure(HthxMap.electronRailIndex[ids[i]]);
            			}
            		}
//            		if (electronRail.config.isOpenOrClose) {
//            			Messager.show({Msg:"启用成功",'isModal': false});
//                		return;
//            		} else {
//            			Messager.show({Msg:"停用成功",'isModal': false});
//                		return;
//            		}
            		
            	}else{
     			   Messager.alert({Msg:json['msg'],isModal: true}); 
     		   }
            }  
         });
	},
	/************************************************新增的工具栏(圆、矩形、多边形、线、输入)******************************************************/
	addEleTools: function(){
		var strs = '<div class="addElectRailPan">' +
	    '<div class="ElectRailToolPan" style="height:0px;">'+
	        '<div class="addElectRailArrow" style="height:0px;"></div>' +
	    '</div>' + 
	    '<div id="lowElectRai">' +
		    '<div id="circleElectRail" class="electRailArea circleElectRailInactivate" title="圆"></div>' +
		    '<div id="boxElectRail" class="electRailArea boxElectRailInactivate" title="矩形"></div>' +
		    '<div id="polygonElectRail" class="electRailArea polygonElectRailInactivate" title="多边形"></div>' +
		    '<div id="lineElectRail" class="electRailArea lineElectRailInactivate" title="线"></div>' +
		    '<div id="textElectRail" class="textAreaInactivate" title="输入"></div>' +
		'</div>'+
	    '</div>';
        $("#electRail").append(strs);
        //电子围栏区域入口
        $(".electRailArea").on('click', electronRailArea);
        $("#textElectRail").on('click',electronRail.showPanelSave);
	},
	showHideEleTools:function(){
		$(".addElectRailPan").slideToggle();
	}
};