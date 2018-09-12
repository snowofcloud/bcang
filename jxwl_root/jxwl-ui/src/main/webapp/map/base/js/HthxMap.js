/**
*
* <p>Description: 航天恒星基础地图顶级域名</p>
* <p>Copyright: Copyright (c) 2009</p>
* <p>Company:航天恒星科技有限公司</p>
* <p>Date:2015-06-04 </p>
* @author:张琴
*
*/
(function(){
	window.HthxMap = {
		scriptName : 'HthxMap.js',

		getScriptLocation : function() {
			var scriptLocation = "";
			var isOL = new RegExp("(^|(.*?\\/))(" + HthxMap.scriptName
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
			"../ol3.13.1/ol-debug.js",
			"../../setting.js",
			"lib/Utils.js",
			"lib/Functions.js",
			"lib/Map.js",
			"lib/ZoomBox.js",
			"lib/PreNexView.js",			
			"lib/BufferSearch.js",			
			"lib/Measure.js",
			"lib/MeasureDistance.js",
			"lib/MeasureArea.js",
			"lib/MeasureOrientation.js",
			/*自定义区域*/
			"lib/UserDefinedArea.js",
			/*自定义设施*/
			"lib/UserDefinedFacility.js",
			/*自定义线条*/
			"lib/UserDefinedLine.js",
			/*区域限制*/
			"lib/AreaRestrict.js",
			
			
			"lib/Pan.js",
			"lib/MapReset.js",
			"lib/AddPointMarker.js",
			"lib/AddAreaMarker.js",
			"lib/AddMarker.js",
			"lib/AddImportMarker.js",
			"lib/ClearVector.js",
			"lib/SearchLocation.js",
			"lib/SearchFeature.js",
			"lib/ChangeBaseLayer.js",
			"lib/queryByArea.js",
			
			"lib/AddFacilityMarker.js",
			"lib/AddLineMarker.js",
			
			"lib/bufferUtils/Point.js",
			"lib/bufferUtils/ArrayUtil.js",
			"lib/bufferUtils/ArcStruct.js",
			"lib/bufferUtils/GeomUtil.js",
			"lib/bufferUtils/LineStruct.js",
			"lib/bufferUtils/UnionPoint.js",
			"lib/bufferUtils/BufferUtil.js",
			"lib/bufferUtils/com.js",
			
			"lib/style.js"
	);

	host = HthxMap.getScriptLocation();
	var scriptTags = new Array(jsFiles.length);
	//var host = OpenLayers._getScriptLocation() + "lib/";
	for (var i = 0, len = jsFiles.length; i < len; i++) {
		scriptTags[i] = "<script src='" + host + jsFiles[i] + "'></script>";
	}
	if (scriptTags.length > 0) {
		document.write(scriptTags.join(""));
	}
})();
HthxMap.VERSION = "0.3";


