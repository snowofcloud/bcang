/**
 * <p>Description: 常用数组操作构造函数</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 航天恒星科技有限公司</p>
 * <p>Date: 2015-06-25 </p>
 * @author: 何泽潘
 * @extends：
 * @param：
 */

/**常用数组操作类*/
HthxMap.ArrayUtil = function(){}

HthxMap.ArrayUtil.prototype = {
	/**获取对象在数组中的索引位置*/
	getIndexInArray: function(obj, arr){
		for(var i = 0; i < arr.length;i++){
			if(obj == arr[i]){
				return i;
			}
		}
		return -1;
	},

	/**判断对象是否在数组中*/
	isInArr: function(obj, arr){
		for(var i = 0; i < arr.length;i++){
			if(obj == arr[i]){
				return true;
			}
		}			
		return false;
	}
}