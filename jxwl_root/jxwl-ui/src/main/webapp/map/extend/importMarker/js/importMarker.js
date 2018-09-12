/**
 * @description: 输入坐标点标记
 * @author: hzp
 * @time: 2015-12-9
 */
var coordinateArray;
var points, interiorPoint;

var importMarker = {	
	 config: { 
		 flagFloatOrDeg: false //判断是浮点数输入还是度分秒， true为浮点数，false为度分秒
	 },
	 
	 init: function(){
		 $("#importMarker").load("/GDYY/view/base/dispatchsystem/importMarker.html", function(){
			 importMarker.showPanelAdd();
			 /*新增经纬度*/
			 $("#markerPanle input[type=radio]").click(function() {
				var val = $(this).val();
				var flag = true;
				if("importMarkerForm" === val) { //浮点数
					if (importMarker.config.flagFloatOrDeg) {
						flag = false;
					}
					$("#importMarkerSecForm").css("display","none");
					$("#"+val).css("display","block");
					importMarker.config.flagFloatOrDeg = true;
				}else{
					if (!importMarker.config.flagFloatOrDeg) {
						flag = false;
					}
					$("#importMarkerForm").css("display","none");
					$("#"+val).css("display","block");
					importMarker.config.flagFloatOrDeg = false;
				}
				if (flag) {
					coordinateArray = layerControlArray.floatAndLatLon("#importMarkerTable", importMarker.config.flagFloatOrDeg, layerControlArray.lonlatToFloat, toolMethod.changeToDMS);
				}
			});
			
			/*表单验证进行提交数据*/
			$("#importMarkerForm").html5Validate(function(){
				layerControlArray.addCoordinates("importMarker", points, coordinateArray);
//				importMarker.addCoordinates(this);
				$("#markerSubmit").removeClass("disabled");
			});
			$("#importMarkerSecForm").html5Validate(function(){
				layerControlArray.addCoordinates("importMarker", points, coordinateArray);
//				importMarker.addCoordinates(this);
				$("#markerSubmit").removeClass("disabled");
			});
			
			$("#importAllMarkerForm").html5Validate(function(){
				importMarker.submitCoordinates(this);
			});
		 })
	 },
	 
	/*显示弹框 新增输入坐标*/
    showPanelAdd: function () {
    	importMarker.config.flagFloatOrDeg = false;
    	//初始化
    	importMarker.config.flagFloatOrDeg = false;
    	$("#importMarkerTable").clearGridData();
    	$("#markerSubmit").addClass("disabled");
    	coordinateArray = [];
    	var coordinateObj = {};
    	points = [];
    	interiorPoint = "";
    	$("#ImportName").val("标记"+(++bizCommonSetting.maxMarkerNumner));
    	$("#markerContent").val("");
    
    	$("#importMarkerTable").jqGrid({
    		data: {},
    		datatype : "local",
            colNames: ["北纬", "东经"],
            colModel: [
                {name: "lantitu", index: "1", align: "center", width:"300"},
                {name: "lontitu", index: "2", align: "center", width:"300"},
            ],
            loadonce: false,
            viewrecords: true,
            autowidth: false,
            height: 300,
            rownumbers: true,
            multiselect: false,
            multiboxonly: true,
            rowNum: 10, // 每页显示记录数
            rowList:  [10, 15, 20], // 用于改变显示行数的下拉列表框的元素数组
            pager: "#markerPage"
        }); 
        toolMethod.showPanel("#importMarkerAdd", "新增坐标集");
    	$("#importMarkerAdd").removeClass("hidden");
    },
    
	/*关闭输入坐标弹框*/
    closePanel: function() {
    	toolMethod.closePanel("#importMarkerAdd", true);
//    	if ($("#outimportMarker").length > 0) {
//    		$("#outimportMarker").remove();
//    	}
//    	$(".electronHidden").removeClass("hidden");
    },
    
    /*将度分秒转换为浮点数*/
    /*getFloatData: function(arr) {
    	var lonSet = importMarker.lonlatToFloat(arr[0].join());
    	var latSet = importMarker.lonlatToFloat(arr[1].join());
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
    */
    /*浮点数与度分秒互转*/
    /*floatAndLatLon: function() {
    	先转换pointSet为度分秒格式
    	var leng = $("#markerTable").jqGrid("getDataIDs");
    	var coordinateObj;
    	if(importMarker.config.flagFloatOrDeg) {
    		for (var i = 0; i < leng.length; i++) {
    			coordinateObj = {};
    			var cellLon = $("#markerTable").jqGrid("getCell", leng[i], "lontitu");
    			var cellLat = $("#markerTable").jqGrid("getCell", leng[i], "lantitu");
    			var longitude = importMarker.lonlatToFloat(cellLon);
    			var latitude = importMarker.lonlatToFloat(cellLat);
    			var coordSet = ol.coordinate.toStringXY([longitude, latitude], bizCommonVariable.coorsLen);  //保留小数点后6位
    			coordSet = coordSet.split(",");
    			$("#markerTable").jqGrid("setCell", leng[i], "lontitu", JSON.parse(coordSet[0]));
    			$("#markerTable").jqGrid("setCell", leng[i], "lantitu", JSON.parse(coordSet[1]));
    			coordinateObj.lontitu = JSON.parse(coordSet[0]);
    			coordinateObj.lantitu = JSON.parse(coordSet[1]);
    			coordinateArray[i] = coordinateObj;
    		}
    	}else {
    		for(var i = 0; i < leng.length; i++) {
    			coordinateObj = {};
    			var cellLon = $("#markerTable").jqGrid("getCell", leng[i], "lontitu");
    			var cellLat = $("#markerTable").jqGrid("getCell", leng[i], "lantitu");
    			var longitude = importMarker.degreesToStringHDMS(JSON.parse(cellLon));
    			var latitude = importMarker.degreesToStringHDMS(JSON.parse(cellLat));
    			$("#markerTable").jqGrid("setCell", leng[i], "lontitu", longitude);
    			$("#markerTable").jqGrid("setCell", leng[i], "lantitu", latitude);
    			coordinateObj.lontitu = longitude;
    			coordinateObj.lantitu = latitude;
    			coordinateArray[i] = coordinateObj;
    		}
    	}    	
    },*/
    
    /*degreesToStringHDMS: function(degrees) {
	   	 var normalizedDegrees = goog.math.modulo(degrees + 180, 360) - 180;
	   	 var x = Math.abs(Math.round(3600 * normalizedDegrees));
	   	 if (degrees < 0){
	   		 return '-' + Math.floor(x / 3600) + '\u00b0 ' +
	     				Math.floor((x / 60) % 60) + '\u2032 ' +
	     				Math.floor(x % 60) + '\u2033 '; 
	   	 }else{
	   		 return Math.floor(x / 3600) + '\u00b0 ' +
	  	      			Math.floor((x / 60) % 60) + '\u2032 ' +
	  	      			Math.floor(x % 60) + '\u2033 '; 
	   	 } 
   },*/
	
	/**
	 * 每次增加一条坐标点
	 */
    /*addCoordinates :function(){
    	var coordinateObj = {};  
    	//浮点数
    	if($("#importMarkerForm").css("display") === "block"){
    		coordinateObj.lontitu = $("#importMarkerLon").val();
        	coordinateObj.lantitu = $("#importMarkerLat").val();
        	$("#importMarkerLon").val("");
        	$("#importMarkerLat").val("");
        	points.push([coordinateObj.lontitu, coordinateObj.lantitu]);  //保存所有坐标点
    	}else{  //度分秒
    		var degreeLon = $("#importMarkerDegreeLon").val();  //经 度
    		var minuteLon = $("#importMarkerMinuteLon").val();  //经 分
    		var secondLon = $("#importMarkerSecondLon").val();  //经 秒
    		
    		var degreeLat = $("#importMarkerDegreeLat").val();  //度 纬
    		var minuteLat = $("#importMarkerMinuteLat").val();  //分 纬
    		var secondLat = $("#importMarkerSecondLat").val();  //秒 纬
    		if(degreeLon == 180 && (parseInt(minuteLon) || parseInt(secondLon))){
    			$("#importMarkerMinuteLon").val("");
    			$("#importMarkerSecondLon").val("");
    			return;
    		}
    		if(degreeLat == 90 && (parseInt(minuteLat) || parseInt(secondLat))){
    			$("#importMarkerMinuteLat").val("");
    			$("#importMarkerSecondLat").val("");
    			return;
    		}
    		coordinateObj.lontitu = degreeLon+"\u00b0 "+minuteLon+"\u2032 "+secondLon+"\u2033 ";
        	coordinateObj.lantitu = degreeLat+"\u00b0 "+minuteLat+"\u2032 "+secondLat+"\u2033 ";
        	//转换坐标
        	var floatCoordinates = layerControlArray.getFloatData([[degreeLon, minuteLon, secondLon], [degreeLat, minuteLat, secondLat]]);
        	floatCoordinates = ol.coordinate.toStringXY(floatCoordinates, 6);  //保留小数点后6位
        	floatCoordinates = floatCoordinates.split(",");
        	floatCoordinates = [JSON.parse(floatCoordinates[0]), JSON.parse(floatCoordinates[1])];
        	points.push(floatCoordinates);  //保存所有坐标点
        	//重置
        	$("#importMarkerDegreeLon").val("");  
    		$("#importMarkerMinuteLon").val("");  
    		$("#importMarkerSecondLon").val("");    		
    		$("#importMarkerDegreeLat").val(""); 
    		$("#importMarkerMinuteLat").val("");
    		$("#importMarkerSecondLat").val("");
    	}    	
    	coordinateArray.push(coordinateObj);
    	
    	$("#markerTable").jqGrid('setGridParam', {
    		data: coordinateArray,
    		datatype: "local",
			page : 1
		}).trigger("reloadGrid");
	},	*/
	
	submitCoordinates: function(){
		$("#icon").val(0);
		$("#coordinates").val(JSON.stringify(points));
		$.ajax({
			url : HthxMap.Settings.rest+"markPosition/saveOrUpdate",
			dataType : 'json',
			type : "POST",
			async : false,
			data : $("#importAllMarkerForm").serialize(),
			success : function(obj) {
				if (obj["code"] === 1) {
					HthxMap.markerId[obj['data']] = obj['data'];
			        interiorPoint = HthxMap.drawMultiPolygon(points);
			        importMarker.addMarker(points, obj["data"]);
			        
			        var name = $("#ImportName").val();
			        if($(".historyMarkerList").find("a[id="+obj['data']+"]").length){  //如果存在改变名字
			        	$(".historyMarkerList").find("a[id="+obj['data']+"]").text(name);   //左侧栏历史标记变化
					}else{
						var strs = '<li><a href="#" id='+obj['data']+' onclick="drawHistoryMarker(this);">'+name+'</a></li>';
						$(strs).insertBefore($($(".historyMarkerList").find("li")[0]));
						bizCommonVariable.markerObj[obj['data']] = {};
						bizCommonVariable.markerObj[obj['data']].markerId = obj['data'];
					}
			        
			        if(HthxMap.curControl.target){
						$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
						$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
					}
			        toolMethod.closePanel($("#importMarkerAdd"), true);
			     } else {
			    	 Messager.alert({Msg:obj["msg"], isModal: true}); 
			     }			
			}
		});
	},
	
	cancelMarker: function(arr){
		toolMethod.closePanel(arr, true);
		if(HthxMap.curControl.target){
			$("#" + HthxMap.curControl.target + " > div").removeClass(HthxMap.curControl.displayClass + 'Activate');
			$("#" + HthxMap.curControl.target + " > div").addClass(HthxMap.curControl.displayClass + 'Inactivate');
		}
	},
	
	addMarker: function(coordinates, markerId){
		var markers = HthxMap.Settings.IMG.marker;
		var markertitle = HthxMap.Settings.IMG.markerName;
		var markerlist = '';
		var lonArray = [];
		var latArray = [];
		for(var i=0; i< markers.length; i++){
			var pngUrl = markers[i];
			markerlist += '<li class="markerIconList"><img title="'+markertitle[i]+'" src="'+pngUrl+'"/></li>';
		}
		
		//经纬度保留6位小数
		coordinates = HthxMap.keepCoorsLen(coordinates, bizCommonVariable.coorsLen);		
		for(var i = 0; i < coordinates.length; i++){
			lonArray.push(coordinates[i][0]);
			latArray.push(coordinates[i][1]);
		}
		
		var name = $("#ImportName").val();
		var content = $("#markerContent").val();
		var contentStr = "";
		var strArr = [];
		strArr.push('<div class="panel" id="remarkPoint">');
		strArr.push('<div class="panel-heading">');
		strArr.push('<label class="panel-title">标记信息</label>');
		strArr.push('</div>');
		strArr.push('<div class="panel-padding">');
		strArr.push('<form class="form" id="">');
		strArr.push('<div class="row row-group">');
		strArr.push('<label class="dialog-label must">名称</label>');
		strArr.push('<div class="inputout input-group-sm">');
		strArr.push('<input type="text" id="markerName" class="form-control" placeholder="请输入1~20个字符" maxlength="20" value="'+name+'">');
		strArr.push('</div>');
		strArr.push('</div>');
		strArr.push('<div class="row row-group">');
		strArr.push('<label class="dialog-label must">北纬</label>');
		strArr.push('<div class="inputout input-group-sm">');
		strArr.push('<input type="text" id="markerLat" readonly class="form-control float-type" value="'+latArray+'">');
		strArr.push('<label class="">度</label>');
		strArr.push('</div>');
		strArr.push('</div>');
		strArr.push('<div class="row row-group">');
		strArr.push('<label class="dialog-label must">东经</label>');
		strArr.push('<div class="inputout input-group-sm">');
		strArr.push('<input type="text" id="markerLon" readonly class="form-control float-type" value="'+lonArray+'">');
		strArr.push('<label class="">度</label>');
		strArr.push('</div>');
		strArr.push('</div>');
		strArr.push('<div>');
		strArr.push('<input id="convertDegrees" style="margin-left:50px;" type="checkbox" onclick="convertDegreesToStringHDMS()"/>'+'<label for="convertDegrees">显示转换</label> ');
		strArr.push('</div>');
		strArr.push('<div class="row row-group">');
		strArr.push('<label class="dialog-label">图标</label>');
		strArr.push('<div class="inputout remark-icon">');
		strArr.push('<ul id="markerlist">');
		strArr.push(markerlist);
		strArr.push('</ul></div></div>');
		strArr.push('<div class="row row-group">');
		strArr.push('<label class="dialog-label">备注</label>');
		strArr.push('<div class="inputout input-group-sm">');
		strArr.push('<textarea rows="3" cols="5" class="form-control" id="markerContent" placeholder="请输入0~200个字符" maxlength="200">'+content+'</textarea>');
		strArr.push('</div></div>');
		strArr.push('<div id="featureId" value='+HthxMap.measureIndex+' style="display:none"></div>');
		strArr.push('<div id="markerCoordinates" value='+coordinates+' style="display:none"></div>');
		strArr.push('<p class="p-center">');
		strArr.push('<input type="button" class="btn btn-primary" id="markerOK" value="确定"/>&nbsp;&nbsp;&nbsp;');
		strArr.push('<input type="button" class="btn btn-default"  id="markerCancel" value="删除" onclick="closePanel(this, true)"/>');
		strArr.push('</p>');
		strArr.push('</form></div></div>');			
		contentStr = strArr.join("");
		
		HthxMap.measureIndex++;
		var pngUrl = HthxMap.Settings.IMG.marker[0];//默认图标
		HthxMap.addMarker(map, interiorPoint, contentStr, pngUrl, markerId);
		map.getView().setCenter(interiorPoint);
	}
}