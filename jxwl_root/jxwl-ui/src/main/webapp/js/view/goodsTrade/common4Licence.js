/**
 /**
 * 公共部分 供资质信息管理模块使用
 */
var common4Licence = (function($){
	
	// 请求路径
	var urlPath = {
		//附件相关
		uploadFileUrl : $.backPath + '/licenceInfo/uploadFile',
		deleteFileUrl : $.backPath + '/licenceInfo/deleteFile',
		downloadFileUrl : $.backPath + "/licenceInfo/downloadFile/",
		//计算过期个数
		amountUrl  : $.backPath + "/licenceWarn/amount"
	};
	
	//计算过期个数
	var _amount = function(licenceWarnType){
		  var formData = {};
		  formData['licenceWarnType'] = licenceWarnType;
		  $.ajax({
	        url: urlPath.amountUrl,
	        type: 'POST',
	        data: formData
	    }).done(function (json) {
	        if (json.code === 1) {
	            $("#"+licenceWarnType+"WarnAmount").html(json.data);
	        } else {
	            Message.alert({
	                Msg: json.msg,
	                isModal: false
	            });
	        }
	    });
	};	
		
		
	//附件变量
	var uploadObj;
	// 插件初始化
	var _upload_init = function(fileUpload) {
		if (uploadObj) {
			uploadObj.destroy();
		}
		//初始化参数
		uploadObj = fileUpload.Huploadify({
			auto : true,
			fileTypeExts : "*.png;*.jpg;*.jpeg;*.PNG;*.JPG;*.JPEG;*.bmp;*.BMP;*.doc;*.docx",
			multi : true,
			deleteURL : urlPath.deleteFileUrl,
			fileSizeLimit : 10240,// 10M
			showUploadedPercent : true,
			showUploadedSize : true,
			removeTimeout : 9999999,
			uploader : urlPath.uploadFileUrl,
			//最大文件上传数
			filesLen : 5
		});
		return uploadObj;
	};
	
	// 预览图片
	var _priviewFile = function(id) {
		$("#preview-panel").showPanelModel('图片预览');
		$("#preview-panel").find(".panel-close").bind("click",function(){
			$("#preview-panel").fadeOut(300);
	    	$(".panel-mask").css("z-index","1000");
		});
		//下载图片至img中
		$("#previewFile").attr("src", urlPath.downloadFileUrl + id);
	};
	
	//展示附件信息 详情 rowObj 
	/**
	 * 展示附件信息 用于详情中
	 * rowObj 展示的实体对象
	 * target 弹框的位置元素
	 */
	var _detailDisplay = function(rowObj,target){
		for ( var i = 0; i < rowObj.fileIds.length; i++) {
			var fileId = rowObj.fileIds[i];
			var fileDel = '<div class="fileDowloand"><small>'
					+ rowObj.fileNames[i]
					+ '</small><a  href="'
					+ common4Licence.urlPath.downloadFileUrl
					+ fileId
					+ '" download=fileIds[i]><span class="img-download"></span></a>';
				//if( !(rowObj.fileNames[i].endsWith(".doc") || rowObj.fileNames[i].endsWith(".docx")) ){
			     //ie不支持endsWith方法 
			     //文档类型文件不预览
				  var suffixName = rowObj.fileNames[i].split(".")[1];
			      if( !(suffixName === 'doc' || suffixName === 'docx')){
			        fileDel = fileDel
					+'<span class="img-priview" onclick="common4Licence.priviewFile(\''
					+ fileId + '\');"></span>';
				}
				fileDel= fileDel +'</div>';
			$(target).append(fileDel);
		}
	};
	//展示附件信息 修改
	/**
	 * 展示附件信息 用于修改
	 * rowObj 展示的实体对象
	 * editDeleFileOri 查询得到的附件数组
	 * target 弹框的位置元素
	 */
	var _updateDisplay = function(rowObj,editDeleFileOri,isIe,target,ie9_target){
		
		for ( var i = 0; i < rowObj.fileIds.length; i++) {
//			var enterpriseFileDel = '<div class="fileDelete"><small>' //fileDelete是放置附件文件位置元素
//					+ rowObj.fileNames[i]
//					+ '</small><span style="cursor:pointer;" onclick="common4Licence.fileDeleteEdit('
//					+ i + ',\'' + rowObj.fileIds[i] + '\')">&nbsp&nbspx</span></div>';
//			$(target).append(enterpriseFileDel);
			
			var content = '<li fileID='+rowObj.fileIds[i]+'>' + 
			  		'<span class="iefilename">'+rowObj.fileNames[i] + '</span>'+
			  		'<a href="javascript:void(0);" class="delfilebtn" onclick="ie9fileUpload.ie9fileDeleteEdit(this,\''+urlPath.deleteFileUrl+'\')">&nbsp;&nbsp;x</a></li>';
			if(isIe){
				//ie9填充已有的文件列表
				$(ie9_target).append(content);
			}else{
				//非ie9填充已有的文件列表
				$(target).append(content);
			}
			editDeleFileOri.push(rowObj.fileIds[i]);
		}
	};
	
	// 编辑：单文件附件删除
	/**
	 * 单文件附件删除
	 * i  被删除文件的位置个数  
	 * id 被删除文件的id
	 * 
	 */
	var _fileDeleteEdit = function(i, id) {
		Message.confirm({
					Msg : $.msg.sureDelete,
					okSkin : 'danger',
					iconImg : 'question',
					isModal : true
				}).on(
					function(flag) {
						if (flag) {
							$.ajax({
								url : urlPath.deleteFileUrl,
								dataType : 'json',
								type : "POST",
								async : false,
								data : {
									"ids":id  
								},
								success : function(data) {
									if (data["code"] === 1) {
										//fileDelete 附件文件位置class元素
										$($(".fileDelete")[i]).css("display","none");
										Message.show({
											Msg : "删除成功",
											isModal : false
										});
										//删除一个后  重设这次的最大上传文件数量
										window.getOptionFileLen = window.getOptionFileLen + 1;
										uploadObj.settings("filesLen",window.getOptionFileLen);
									} else if (data["code"] === 3) {
										Message.alert({
											Msg : data["msg"],
											iconImg : "warning",
											isModal : false
										});
									} else {
										Message.alert({
											Msg : "删除失败",
											isModal : false
										});
									}
								},
								error : function() {
									Message.alert({
										Msg : "失败",
										isModal : false
									});
								}
							});
						}
					});
	};
	
	return{
		upload_init : _upload_init,
		amount      : _amount,
		urlPath     : urlPath,
		priviewFile :_priviewFile,
		uploadObj   : uploadObj,
		fileDeleteEdit : _fileDeleteEdit,
		detailDisplay  : _detailDisplay,
		updateDisplay :_updateDisplay
	};
	
	
})(jQuery);
/**
 * @description:评价管理--加载页面
 * @author: zz
 * @time: 2016-12-19
 */
$(function(){
	//文档加载完成执行
	var $licenceManageTabs = $("#licenceManage-tabs");
	//tabs切换事
    var _showTabs = function (arr) {
        $("a[aria-controls='" + arr.split("#")[1] + "']").tab('show');
        switch (arr) {
	        case '#enterprise':
	        	enterpriseLicence.init();
	        	break;
            case '#car':
            	carLicence.init();
                break;
            case '#person':
            	personLicence.init();
                break;
            case '#warn':
            	enterpriseWarn.init();
                break;
            default :
        }
    };
    
    //注册tab点击事件
    $licenceManageTabs.find('a').click(function () {
        var $this = $(this);
        var tabsName = $this.attr('href');
		sessionStorage.setItem("active_li", tabsName);
        _showTabs(tabsName);
    });
    var defaultTab = sessionStorage.getItem("active_li");
    if(defaultTab){
    	_showTabs(defaultTab);
    	var activeLiTwo = sessionStorage.getItem("active_liTwo");
		if(activeLiTwo){
			$("#warn-tabs").find("li a[href="+activeLiTwo+"]").click();
		}
    } else {
    	//默认执行第一个tab
    	$licenceManageTabs.find("li:eq(0)").find("a").click();
    }
});