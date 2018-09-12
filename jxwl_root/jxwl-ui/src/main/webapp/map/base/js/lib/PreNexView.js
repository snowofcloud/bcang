/**
 * <p>Description: 地图前一视图、后一视图</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-11 </p>
 * @author: 张琴
 * @extends：{ol.control.Control}
 * @param：map(必选),displayClass(控件样式,可选),target(控件位置，可选), back(可选，唯一值true).
 */
HthxMap.PreNexView = function(opt_options) {
	var options = opt_options || {};
	var map = options.map;
	var back = options.back;
	if(back){
		displayClass = options.displayClass || "nexView";
		target = options.target || "nexView";
		//创建控件位置节点
		$("#toolbar").append('<div id="nexView"></div>');
	}else{
		displayClass = options.displayClass || "preView";
		target = options.target || "preView";
		//创建控件位置节点
		$("#toolbar").append('<div id="preView"></div>');
	}
	var displayClass = options.displayClass;
	var target = options.target;

	var button = document.createElement('button');
	if(back){
		button.title = "后一视图"
	}else{
		button.title = "前一视图";
	}
	map.on('moveend', onMoveEnd);
	//地图视图改变的时候，保存所有视图
	function onMoveEnd(evt) {

		if(HthxMap.allView.length >= 2){//如果是地图初次加载则不改变图标为可用				
			$("#preView > div").removeClass('preViewInactivate');
			$("#preView > div").addClass('preViewActivate');
		}			
		var map = evt.map;
		var view = map.getView();
		var center = view.getCenter();
		var zoom = view.getZoom();
		// 之前添加的视图不应该再添加
		var len = HthxMap.allView.length;
		var index = -1;
		for (var i = 0; i < len; i++) {
			var oCenter = HthxMap.allView[i][0];
			var oZoom = HthxMap.allView[i][1];
			if (center === oCenter && zoom === oZoom) {
				index = i;
				break;
			}
		}
		if (index < 0) {
			HthxMap.allView.push([ center, zoom ]);
		}
		if(index == 0){//不存在前一视图，则改变点击图标为不可用
			$("#preView > div").removeClass('preViewActivate');
			$("#preView > div").addClass('preViewInactivate');
		}
		if(index < HthxMap.allView.length -1 && HthxMap.allView.length > 1){//存在后一视图，则改变点击图标为可用
			$("#nexView > div").removeClass('nexViewInactivate');
			$("#nexView > div").addClass('nexViewActivate');
		}
		if(index == HthxMap.allView.length -1 && index != 0){//不存在后一视图，则改变点击图标为不可用
			$("#nexView > div").removeClass('nexViewActivate');
			$("#nexView > div").addClass('nexViewInactivate');
		}
	}
	
	var handler = function(e) {
		//移除地图事件
		HthxMap.removeMapEvents(map);

		var view = map.getView();
		var center = view.getCenter();
		var zoom = view.getZoom();

		var len = HthxMap.allView.length;
		var index = -1;
		for (var i = 0; i < len; i++) {
			var oCenter = HthxMap.allView[i][0];
			var oZoom = HthxMap.allView[i][1];
			if (center === oCenter && zoom === oZoom) {
				index = i;
				break;
			}
		}
		if (back) {
			if (index == len - 1) {// 不存在后一视图

			} else {
				var arr = HthxMap.allView[index + 1];
				view.setCenter(arr[0]);
				view.setZoom(arr[1]);

			}
		} else {
			if (index == 0) {// 不存在前一视图

			} else {
				var arr = HthxMap.allView[index - 1];
				view.setCenter(arr[0]);
				view.setZoom(arr[1]);
			}
		}

	};
	button.addEventListener('click', handler, false);

	var element = document.createElement('div');
	element.className = options.displayClass + 'Inactivate ol-unselectable ';
	element.appendChild(button);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});

};
ol.inherits(HthxMap.PreNexView, ol.control.Control);