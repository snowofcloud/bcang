/**
 * 文件名：IDseMessageService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年12月30日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

/**
 * 
 * 〈一句话功能简述〉消息处理业务接口〈功能详细描述〉接收kafka上传的实时位置信息，通过此位置信息进行
 * 1.车辆位置信息保存或更新
 * 2.记录出入行政区域
 * 3.偏离路线记录
 * 4.报警记录
 * 5.报警处理
 * 
 * @author duanhy
 * @version [版本号, 2017年10月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IDseMessageService {
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param message 实时位置信息
     * @return true 成功, false 失败
     * @throws Exception 数据库异常
     * @see [类、类#方法、类#成员]
     */
    boolean saveMessage(String message)
        throws Exception;
    
}
