/**
 * 文件名：Result.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-11
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

/**
 * 〈一句话功能简述〉响应消息
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-16]
 * @param <T>
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public class RespondResult<T> {
    
    /** 结果*/
   private String result;
    
    /** 数据*/
   private T data;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @return result
     * @see [类、类#方法、类#成员]
     */
    public String getResult() {
        return result;
    }
    
    /**
     * 〈一句话功能简述〉result
     * 〈功能详细描述〉
     * 
     * @param result result
     * @see [类、类#方法、类#成员]
     */
    public void setResult(String result) {
        this.result = result;
    }
    
    /**
     * 〈一句话功能简述〉 data
     * 〈功能详细描述〉
     * 
     * @return data
     * @see [类、类#方法、类#成员]
     */
    public T getData() {
        return data;
    }
    
    /**
     * 〈一句话功能简述〉data
     * 〈功能详细描述〉
     * 
     * @param data data
     * @see [类、类#方法、类#成员]
     */
    public void setData(T data) {
        this.data = data;
    }
    
}
