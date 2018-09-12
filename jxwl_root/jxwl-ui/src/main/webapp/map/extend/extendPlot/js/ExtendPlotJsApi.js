/**
*
* <p>Description: 标绘的顶级命名空间</p>
* <p>Copyright: Copyright (c) 2009</p>
* <p>Company:航天天绘四川分公司</p>
* <p>Date:2015-01-15 </p>
* @author:张琴
*
*/

(function(){
	window.ExtendPlot={

		scriptName : 'ExtendPlotJsApi.js',
		

		getScriptLocation : function(){ 
	
		    var scriptLocation = "";            
		    var isOL = new RegExp("(^|(.*?\\/))(" + ExtendPlot.scriptName + ")(\\?|$)");         
		    var scripts = document.getElementsByTagName('script');
		    for (var i=0, len=scripts.length; i<len; i=i+1 ) {
		        var src = scripts[i].getAttribute('src');           
		        if (src) {
		            var match = src.match(isOL);
		            if(match) {
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
//		"lib/commonPlot.js",
		"lib/plot.colorpicker.js",
		"lib/plotLine.js",
		"lib/plotStraightArrow.js",
		"lib/plotCurve.js",
		"lib/plotARC.js",
		"lib/plotRegularPolygon.js",
		"lib/plotPolygon.js",
		"lib/plotCircle.js",
		"lib/plotEllipse.js",
		"lib/plotClosedCurve.js",
		"lib/plotLune.js",
		"lib/plotSector.js",
		"lib/plotLineArrow.js",
		"lib/plotAssaultDirection.js",
		"lib/plotDoubleArrow.js",
		"lib/plotAttackArrow.js",
		"lib/plotTailAttackArrow.js",
		"lib/plotSquadCombat.js",
		"lib/plotTailSquadCombat.js",
		"lib/draw.js"
);
	
	host = ExtendPlot.getScriptLocation();
	 var scriptTags = new Array(jsFiles.length);
     //var host = OpenLayers._getScriptLocation() + "lib/";
     for (var i=0, len=jsFiles.length; i<len; i++) {
         scriptTags[i] = "<script src='" + host + jsFiles[i] +
                                "'></script>"; 
     }
     if (scriptTags.length > 0) {
         document.write(scriptTags.join(""));
     }
})();
ExtendPlot.VERSION = "0.1";