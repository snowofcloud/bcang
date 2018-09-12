
//根据ID得到位置

function getLiquid(s1,s2) {
	 var obj = $.getData($.backPath + '/pipeline/findById/' + s1);
	 if(obj){
		 /*管道*/
		 $("#pipe-coen span[name=pipeNo]").text(obj.pipeNo);
		 $("#pipe-coen span[name=pipeName]").text(obj.pipeName);
		 if(obj.flow){
			 $("#pipe-coen span[name=flow]").text(obj.flow);
		 }else{
			 $("#pipe-coen span[name=flow]").text("");
		 }
		 $("#pipe-coen span[name=dangerName]").text(obj.dangerName);
		 $("#pipe-coen span[name=dangerType]").text(obj.dangerType);
		 $("#pipe-coen span[name=dutyPerson]").text(obj.dutyPerson);
		 if(obj.telephone){
			 $("#pipe-coen span[name=telephone]").text(obj.telephone);
		 }else{
			 $("#pipe-coen span[name=telephone]").text("");
		 }
		 /*储罐*/
		 $("#storagetank span[name=pipeNo]").text(obj.pipeNo);
		 $("#storagetank span[name=pipeName]").text(obj.pipeName);
		 $("#storagetank span[name=flow]").text(obj.flow);
		 $("#storagetank span[name=dangerName]").text(obj.dangerName);
		 $("#storagetank span[name=dangerType]").text(obj.dangerType);
		 $("#storagetank span[name=dutyPerson]").text(obj.dutyPerson);
		 $("#storagetank span[name=telephone]").text(obj.telephone);
		 $("#storagetank span[name=material]").text(obj.material);
		 if(obj.stockAllowance){
			 $("#storagetank span[name=stockAllowance]").text(obj.stockAllowance);
		 }else{
			 $("#storagetank span[name=stockAllowance]").text("");
		 }
		
		 if(obj.stockVolume){
			 $("#storagetank span[name=stockVolume]").text(obj.stockVolume);
		 }else{
			 $("#storagetank span[name=stockVolume]").text("");
		 }
		 $("#storagetank span[name=address]").text(obj.address);
		 if(obj.liquidLevel){
			 $("#storagetank span[name=liquidLevel]").text(obj.liquidLevel); 
		 }else{
			 $("#storagetank span[name=liquidLevel]").text("");
		 }
		
	 }
	/*var result = "";
	 $.ajax({
        url: $.backPath + '/pipeline/findLiquid',
        type: "POST",
        data:{
        	id:s1,
        	pipeType:s2
        },
        cache: true,
        async: false //使用同步方式
    }).done(function (json) {
    	var data = json.rows;
    	var arr = [];
    	if(data[0].pipeType == "2") {
    		$.each(data,function(index,item) {
    			if(item) {
    				arr.push(item.stockAllowance);
    			}
    		});
    	}else if(data[0].pipeType == "1") {
    		$.each(data,function(index,item) {
    			if(item) {
    				arr.push(item.flow);
    			}
    		});
    	}
    	result = arr;
        
    });
	 return result;*/
}

/**
 * 绘制管道和储罐
 */
function initPipeAndTank() {
	/*用户角色*/
	var userRoles = $("#userRoleid").text();
	var result = "";
	 $.ajax({
       url: $.backPath + '/pipeline/findPipeLocations',
       type: "GET",
       cache: false   /*强制不从缓存中读取数据*/   //cache: true
   }).done(function (json) {
	   var pipes = json.data;
	   for(var i=0; i<pipes.length;i++){
		   var pipe = pipes[i];
		   if(pipe.pipeType == "1") {
			   /*企业用户不能看到储罐和管道信息*/
			   if(userRoles == "政府用户|"){
				   drawPipeLine(pipe);
			   }
		   }else  {
			   if(userRoles == "政府用户|"){
				   drawStorageTank(pipe); 
			   }
		   }
	   }
   });
}
/**
 * 定位：对传入id所属的管道或储罐定位
 * @param id 管道或储罐id
 */
function position(id){
	 var postData = {'id':id};
	 $.ajax({
	       url: $.backPath + '/pipeline/findPipeLocations',
	       type: "POST",
	       cache: false,
	       data: postData
	   }).done(function (json) {
		   var objects = json.data;
		   for(var i=0; i<objects.length;i++){
			   var object = objects[i];
			   if(object.pipeType == "1") {
				   positionPipeLine(object);
			   }else  {
				   positionTank(object);
			   }
		   }
	   });
}
/**
 * 管道定位
 * @param pipe
 */
function positionPipeLine(pipe){
	var arr = JSON.parse(pipe.points);
	var middle = arr[Math.floor(arr.length/2)];
	map.getView().setCenter([middle[0],middle[1]]);
}
/**
 * 储罐定位
 * @param tank
 */
function positionTank(tank){
	map.getView().setCenter([tank.longitude,tank.latitude]);
}

//绘制管道位置
function drawPipeLine(pipe) {
	var arr = JSON.parse(pipe.points);
	var pipeLineLayer  = new ol.layer.Vector({
		source: new ol.source.Vector()
	});
	pipeLineLayer.setProperties({id:"pipeLineLayer"});
	var pipeLineFea = new ol.Feature(new ol.geom.MultiLineString([arr]));
	pipeLineFea.setStyle(new ol.style.Style({	
		stroke: new ol.style.Stroke({							
			width: 6,				
			color: "#ff0000"	
		})
	}));
	pipeLineFea.setId(pipe.pipeNo);
	map.addLayer(pipeLineLayer);
	pipeLineLayer.getSource().addFeature(pipeLineFea);
	HthxMap.mouseEvent(map, "singleclick", "pipeLineLayer");
}


//绘制储罐位置
function drawStorageTank(tank) {
	
	var point= new ol.geom.Point([tank.longitude,tank.latitude]);
	var DeviceStyle = new ol.style.Style({
		image: new ol.style.Icon({
	        opacity: 1,
		    src: '../map/business/img/shipPop/aroundSearch.png'
		})
	});
	var storageLayer  = new ol.layer.Vector({
		source: new ol.source.Vector()
	});
	storageLayer.setProperties({id:"storageLayer"});
	var feature = new ol.Feature({
		geometry: point
	});
	feature.setId(tank.pipeNo);
	feature.setStyle(DeviceStyle);
	map.addLayer(storageLayer);
	storageLayer.getSource().addFeature(feature);
	HthxMap.mouseEvent(map, "singleclick", "storageLayer");
}


///地图弹窗

function pipeLineClickPop(feature, layerId) {
	var id = feature.getId();
	if(layerId == "pipeLineLayer") {
		openPanel(true,"pipeLine");
		var data = getLiquid(id,"1");
		dynPipeLine(data);
		getInfo(id,"pipeLineLayer");
	}else if(layerId == "storageLayer" ) {
		openPanel(true,"storagetank");
	    var data = getLiquid(id,"2");
	    dynStorageTank(data);
	    getInfo(id,"storageLayer");
	}
   
}
//
function openPanel(s1,s2) {
	var panelId = document.getElementById(s2);
	if(s1) {
		panelId.style.display ="block";
	}else{
		panelId.style.display ="none";
	}
	
}
//关闭弹窗
function removePanel(s1,s2) {
	openPanel(s1,s2);
}
//详情
function getInfo(s1,s2) {
	var obj = $.getData($.backPath + '/pipeline/findById/' + arguments[0]);
	  if(obj){
		 if(s2 == "pipeLineLayer") {
			 $("#pipeLine div span").each(function(i,n) {
					var v = $(n).attr("name");
					if(v && obj[v]){
						$(n).text(obj[v]);
					}
					
				});
		 }else if(s2 == "storageLayer") {
			$("#stor-coen div span").each(function(i,n) {
				var v = $(n).attr("name");
				if(v && obj[v]){
					$(n).text(obj[v]);
				}
				
			});
		 }
	  }else{
		  Message.alert({Msg: prompt.checkAndDo, isModal: false});
	  }
}
	
//管道曲线
function dynPipeLine(date) {
	var date = new Date();
	date.setHours(date.getHours()-24);
	$('#pipe-dyn').highcharts({
		chart:{
			backgroundColor:'#0F4576'
		},
        title: {
        	style:{
        		color:'#cccccc'
        	},
            text: '管道流量曲线图',
            x: -20 //center
        },
        xAxis: {
           type:'datetime',
           dateTimeLabelFormats:{
        	    millisecond:'%H:%M:%S.%L',
        	    second:'%H%M%S',
        	    minute:'%H:%M',
        	    hour:'%H:%M',
	        	day:'%Y-%m-%d',
	        	week:'%m-%d',
	        	month:'%Y-%d',
	        	year:'%Y'
	        }
        },
        tooltip:{
        	
        	xDateFormat:'%Y-%m-%d %H:%M'
        	/*millisecond:'%H:%M:%S.%L',
     	    second:'%H%M%S',
     	    minute:'%H:%M',
     	    hour:'%H:%M',
	        	day:'%Y-%m-%d',
	        	week:'%m-%d',
	        	month:'%Y-%d',
	        	year:'%Y'*/
        },
        yAxis: {
            title: {
                text: '流量(m³/h)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '流量',
            /*data: data.reverse(),*/
            data:[1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,1,1,3],
            pointStart:Date.UTC(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours()),
            pointInterval:3600*1000
        }]
    });

}

//储罐曲线
function dynStorageTank(data) {
	var date = new Date();
	date.setDate(date.getDate()-29);
	$('#stor-dyn').highcharts({
		chart:{
			backgroundColor:'#0F4576'
		},
		tooltip:{
			 valueSuffix: 'm³',
			/* millisecond:'%H:%M:%S.%L',
     	    second:'%H%M%S',
     	    minute:'%H:%M',
     	    hour:'%H:%M',
	        	day:'%Y-%m-%d',
	        	week:'%m-%d',
	        	month:'%Y-%d',
	        	year:'%Y'*/
			 xDateFormat:'%Y-%m-%d'
	        },
	       
        title: {
        	style:{
        		color:'#cccccc'
        	},
        	
            text: '储罐存量曲线动态图',
            x: -20 //center
        },
        xAxis: {
        	 type:'datetime',
        	dateTimeLabelFormats:{
	            millisecond:'%H:%M:%S.%L',
	     	    second:'%H%M%S',
	     	    minute:'%H:%M',
	     	    hour:'%H:%M',
		        day:'%Y-%m-%d',
		        week:'%m-%d',
		        month:'%Y-%d',
		        year:'%Y'
        		 }
        },
        yAxis: {
            title: {
                text: '存量(m³)'
            },
            plotLines: [{
                color: '#808080'
            }]
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '存量',
            /*data: data.reverse(),*/
            data:[1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,1,1,3],
            pointStart:Date.UTC(date.getFullYear(),date.getMonth(),date.getDate()),
            pointInterval:24*3600*1000
        }]
    });

}


