/**
*
* <p>Description: 广东渔业GIS JS已引入文件顶级域名</p>
* <p>Copyright: Copyright (c) 2009</p>
* <p>Company:航天恒星科技有限公司</p>
* <p>Date:2015-07-31 </p>
* @author:张琴
*
*/
(function(){
	window.JXWLMapJs = {
		scriptName : 'JXWLMapJs.js',

		getScriptLocation : function() {
			var scriptLocation = "";
			var isOL = new RegExp("(^|(.*?\\/))(" + JXWLMapJs.scriptName
					+ ")(\\?|$)");
			var scripts = document.getElementsByTagName('script');
			for (var i = 0, len = scripts.length; i < len; i = i + 1) {
				var src = scripts[i].getAttribute('src');
				if (src) {
					var match = src.match(isOL);
					if (match) {
						scriptLocation = match[1];
						break;
					}
				}
			}
			return scriptLocation;
		}

	};
	/**
	 *
	 *add js files to here
	 *
	 **/
	var jsFiles = new Array(
			"base/js/HthxMap.js",
			"extend/extendPlot/js/ExtendPlotJsApi.js",
			"business/js/mapBaseMethod.js",
			"extend/trackReplay/js/trackReplay.js",
			"extend/trackReplay/js/bootstrap-slider.min.js",
			"extend/areaRestrictBusiness/js/areaRestrictedFind.js",
			"extend/areaRestrictBusiness/js/areaRestrictQuery.js",
			//自定义区域
			"extend/userDefinedAreaBusiness/js/userDefinedAreaFind.js",
			"extend/userDefinedAreaBusiness/js/userDefinedAreaQuery.js",
			
			//自定义设施
			"extend/userDefinedAreaBusiness/js/userDefinedFacilityFind.js",
			
			//自定义线条
			"extend/userDefinedAreaBusiness/js/userDefinedLineFind.js",
			"extend/userDefinedAreaBusiness/js/userDefinedLineQuery.js",

			"business/js/bizCommonFunction.js",
			"business/js/bizCommonVariable.js",
			"business/js/bizInitCar.js",
			"business/js/cargoMonitor.js",
			"business/js/carTracking.js",
			"business/js/carRoutePoint.js",
			
			"extend/importMarker/js/importMarker.js",
			"extend/capture/niuniucapture.js",
			"business/js/aroundSearch.js",
			"business/js/shipBufferQuery.js",
			"business/js/searchShipByCon.js",
			"business/js/bizSetting.js",
			"business/js/bizPlot.js",
			//"business/js/capturewrapper.js",
			//坐标系转换
			"proj4.js"
			
	);

	host = JXWLMapJs.getScriptLocation();
	var scriptTags = new Array(jsFiles.length);
	//var host = OpenLayers._getScriptLocation() + "lib/";
	for (var i = 0, len = jsFiles.length; i < len; i++) {
		scriptTags[i] = "<script src='" + host + jsFiles[i] + "'></script>";
	}
	if (scriptTags.length > 0) {
		document.write(scriptTags.join(""));
	}
})();
JXWLMapJs.VERSION = "0.1";


