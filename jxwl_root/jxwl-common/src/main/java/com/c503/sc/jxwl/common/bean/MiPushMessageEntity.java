package com.c503.sc.jxwl.common.bean;

/**
 * 〈一句话功能简述〉MiPushMessageEntity
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2017-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MiPushMessageEntity {
    /** code */
    private String code;
    
    /** msg */
    private Object msg;
    
    /**
     * 〈一句话功能简述〉code
     * 〈功能详细描述〉
     * 
     * @return code
     * @see [类、类#方法、类#成员]
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 〈一句话功能简述〉code
     * 〈功能详细描述〉
     * 
     * @param code code
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public MiPushMessageEntity setCode(String code) {
        this.code = code;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉msg
     * 〈功能详细描述〉
     * 
     * @return msg
     * @see [类、类#方法、类#成员]
     */
    public Object getMsg() {
        return msg;
    }
    
    /**
     * 〈一句话功能简述〉msg
     * 〈功能详细描述〉
     * 
     * @param msg msg
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public MiPushMessageEntity setMsg(Object msg) {
        this.msg = msg;
        return this;
    }
    
}
