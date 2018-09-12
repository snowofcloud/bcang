/**
 * 文件名：ReturnMsgCode.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-16
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.constant;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.log.resource.ResourceManager;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ReturnMsg {
    
    /** 存放实时数据信息 */
    private static Map<String, Object> alarmMap = new HashMap<String, Object>();
    
    /**
     * 〈一句话功能简述〉通过远程返回消息码获取消息含有
     * 〈功能详细描述〉
     * 
     * @param code 消息码
     * @return Object
     * @see [类、类#方法、类#成员]
     */
    public static Object getMsgByCode(String code) {
        return ResourceManager.getMessage(code);
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param result result
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public static void handleException(String result)
        throws Exception {
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            Object code = jsonObject.get("result");
            // 判断是否有异常
            ReturnMsg.remoteException((String) code);
        }
        else {
            System.out.println("Not any data");
        }
    }
    
    /**
     * 〈一句话功能简述〉远程返回结果异常
     * 〈功能详细描述〉
     * 
     * @param code code
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public static void remoteException(String code)
        throws Exception {
        switch (code) {
            case CommonConstant.REMOTE1_OPERATE_SUCCESS:
                break;
            
            // 关键字段输入异常
            case CommonConstant.REMOTE2_KEYWORD_FIELD:
                throw new CustomException(CommonConstant.IMPORTANT_KEY_ENTER_E);
                
                // 关键字段未填写
            case CommonConstant.REMOTE3_KEYWORD_NOT_WRITE:
                throw new CustomException(
                    CommonConstant.IMPORTANT_KEY_NOT_ENTER_E);
                
                // 数据重复录入
            case CommonConstant.REMOTE4_VAL_ENTER_REPEAT:
                throw new CustomException(CommonConstant.DATA_ENTER_REPEAT_E);
                
                // 其他异常错误
            case CommonConstant.REMOTE5_OPERATE_OTHER_ERROR:
                throw new CustomException(CommonConstant.OTHER_E);
                
            default:
                break;
        }
    }
    
    /**
     * 〈一句话功能简述〉存放实时数据信息
     * 〈功能详细描述〉
     * 
     * @return 存放实时数据信息
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, Object> getAlarmMap() {
        return alarmMap;
    }
    
    /**
     * 〈一句话功能简述〉存放实时数据信息
     * 〈功能详细描述〉
     * 
     * @param alarmMap alarmMap
     * @see [类、类#方法、类#成员]
     */
    public static void setAlarmMap(Map<String, Object> alarmMap) {
        ReturnMsg.alarmMap = alarmMap;
    }
    
}
