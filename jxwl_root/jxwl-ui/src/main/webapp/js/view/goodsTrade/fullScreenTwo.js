/**
 * @description:大屏展示
 * @author: 肖琼仙
 * @time: 2016-9-2
 */

var fullScreen = (function(){
	var $importantNewData = $("#importantNewData");
	var $rulesData        = $("#rulesData");
	var $announcementData = $("#announcementData");
	var images = [];
	// 请求路径
	var urlPath = {
		findAllInfoUrl: $.backPath + "/fullScreen/findAllInfo",
		fullScreenImgPath: "http://192.168.10.30:8080"
	};
	
	// 初始化
	var _init = function(){
		//初始化数据
		_init_data();
		_init_images_slider();
		_init_play();
		start();
		if(location.pathname == '/JXWL/view/goodsTrade/fullScreenTwo.html'){
			$(".button_top > a:nth(1)").addClass("buttonActive");
		}
		var timerFull = window.setTimeout(function(){
			location.pathname = '/JXWL/view/goodsTrade/fullScreenThree.html';
		},90000);
	};
	
	var _init_data = function(){
		var postData =  {
				"page":1,
	        	"rows":12
	        };
		$.ajax({
            url  : urlPath.findAllInfoUrl,
            type : 'POST',
            async: false,
            data : postData
        }).done(function(json) {
            if (json.code === 1) {
            	data = json.data;
            	_init_importantNew(data["news"]);
            	_init_rules(data["announcements"]);
            	_init_announcement(data["rules"]);
            	images = data["images"];
            } else {
               // $.validateTip(json);
            }
        });
	};
	
	// 加载重大新闻数据
	var _init_importantNew = function(data) {
		if( data){
			data = _dataOrder(data,"release_date");
		}
		_create_html($importantNewData, data);
	};
	
	// 加载规章制度数据
	var _init_rules = function(data){
		if( data){
			data = _dataOrder(data,"release_date");
		}
		_create_html($rulesData, data);
	};
	
	// 加载公告通知
	var _init_announcement = function(data){
		if( data){
			for (var i = 0; i < data.length; i++) {
				$announcementData.append('<div class="row">'
						+ '<div class="col-xs-7 data_row_padding ellispsis" title="'+data[i].title+'">\>>&nbsp;'+data[i].title+'</div>'
						+ '<div class="col-xs-5 data_row_padding " title="'+ data[i].release_date +'">&nbsp;'+ data[i].release_date+ '</div>'
						+ '</div>');
			}
		}
	};
	
	var _init_images_slider = function(){
		var html = '';
		var imgPath = '';
		//此处只能实现轮播images中前3张图片，其余图片无法轮播
		for(var i = 0; i < 3; i++){
			imgPath = images[i];
			if(imgPath){
				html += '<li class="slider-panel"><img alt="" src="'+ urlPath.fullScreenImgPath + imgPath +'" class="img-responsive img-rounded" /></li>';
			}else{
				html += '<li class="slider-panel"><img alt="" src="../../images/no_img.png" class="img-responsive img-rounded" /></li>';
			}
		}
		$('#slider ul.slider-main').append(html);
		length = $('.slider-panel').length; // 图片张数
	};
	
	//数据排序
	var _dataOrder = function(data,key) {
		if(!data) return;
		return data.sort(function(a,b) {
			var x = a[key],y=b[key];
			return ((x > y)? -1:((x < y) ? 1:0));
		});
		
	};
	
	// append html
	var _create_html = function($this, data) {
		var len  = !data? 0:data.length;
		// 奇数
		if (0 != (len & 1)) {
			for (var i = 0; i < len-1;) {
				$this.append('<div class="row">'
						+ '<div class="col-xs-3 data_row_padding ellispsis" title="'+data[i].title+'">\>>&nbsp;'+'<div class="title-Style">'+data[i].title+'</div>'+'</div>'
						+ '<div class="col-xs-3 data_row_padding " title="'+ data[i].release_date +'">&nbsp;'+ data[i].release_date+ '</div>'
						+ '<div class="col-xs-3 data_row_padding ellispsis" title="'+data[i+1].title+'">\>>&nbsp;&nbsp;'+'<div class="title-Style">' +data[i+1].title+'</div>'+'</div>'
						+ '<div class="col-xs-3 data_row_padding " title="'+ data[i+1].release_date +'">&nbsp;'+ data[i+1].release_date+ '</div>'
						+ '</div>');
				i += 2;
			}
			$this.append('<div class="row">'
					+ '<div class="col-xs-3 data_row_padding ellispsis" title="'+data[len-1].title+'">\>>&nbsp;&nbsp;'+'<div class="title-Style">'+data[len-1].title+'</div>'+'</div>'
					+ '<div class="col-xs-3 data_row_padding " title="'+ data[len-1].release_date +'">&nbsp;'+ data[len-1].release_date+ '</div>'
					+ '</div>');
		} else {
			for (var i = 0; i < len; i+=2) {
				$this.append('<div class="row">'
						+ '<div class="col-xs-3 data_row_padding ellispsis" title="'+data[i].title+'">\>>&nbsp;'+'<div class="title-Style">'+data[i].title+'</div>'+'</div>'
						+ '<div class="col-xs-3 data_row_padding " title="'+ data[i].release_date +'">&nbsp;'+ data[i].release_date+ '</div>'
						+ '<div class="col-xs-3 data_row_padding ellispsis" title="'+ data[i+1].title +'">\>>&nbsp;&nbsp;'+'<div class="title-Style">' +data[i+1].title+'</div>'+ '</div>'
						+ '<div class="col-xs-3 data_row_padding " title="'+ data[i+1].release_date +'">&nbsp;'+ data[i+1].release_date+ '</div>'
						+ '</div>');
			}
		}
	};
	

	
	
	
	var currentIndex = 0;   // 当前图片索引
	var interval;           // 定时器句柄
	var hasStarted = false; //是否已经开始轮播
	var t = 3000;           // 轮播间隔时间 
	var length = 0;
	
	// 轮播初始化
	var _init_play = function() {
		// 将除了第一张图片隐藏
		$('.slider-panel:not(:first)').hide();
		// 将第一个slider-item设为激活状态
		$('.slider-item:first').addClass('slider-item-selected');
		// 隐藏向前向后按钮
		$('.slider-page').hide();
		
		// 鼠标上悬时显示向前、向后按钮，停止滑动；鼠标离开时隐藏向前、向后按钮，开始滑动
		$('.slider-panel, .slider-pre, .slider-next').hover(function() {
			stop();
			$('.slider-page').show();
		}, function() {
			$('.slider-page').hide();
			start();
		});
		
		// 鼠标悬浮在slider-item上是,显示该数字对应的图片
		$('.slider-item').hover(function() {
			stop();
			var preIndex = $('.slider-item').filter('.slider-item-selected').index();
			currentIndex = $(this).index();
			play(preIndex, currentIndex);
		}, function() {
			start();
		});
		
		// 为向前翻页、向后翻页进行绑定和解绑
		$('.slider-pre').unbind('click');
		$('.slider-pre').bind('click', pre);
		$('.slider-next').unbind('click');
		$('.slider-next').bind('click', next);
	};
	
	// 向前翻页
	var pre = function() {
		var preIndex = currentIndex;
		currentIndex = (--currentIndex + length) % length;
		play(preIndex, currentIndex);
	};
	
	// 向后翻页
	var next = function() {
		var preIndex = currentIndex;
		currentIndex = ++currentIndex % length;
		play(preIndex, currentIndex);
	};
	
	// 从preIndex页翻到currentIndexye
	var play = function(preIndex, currentIndex) {
		$('.slider-panel').eq(preIndex).fadeOut(500).parent().children().eq(currentIndex).fadeIn(1000);
		$('.slider-item').removeClass('slider-item-selected');
		$('.slider-item').eq(currentIndex).addClass('slider-item-selected');
	};
	
	// 开始轮播
	var start = function() {
		if (!hasStarted) {
			hasStarted = true;
			interval = setInterval(next, t);
		}
	};
	
	// 停止轮播
	var stop = function() {
		clearInterval(interval);
		hasStarted = false;
	};
	
	_init();
	
})();