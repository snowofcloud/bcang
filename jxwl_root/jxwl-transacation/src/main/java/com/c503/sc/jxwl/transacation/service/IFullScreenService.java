/**
 * 文件名：IImageShowController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.transacation.bean.Announcement4FullScreenEntity;
import com.c503.sc.jxwl.transacation.bean.FullScreenData;
import com.c503.sc.jxwl.transacation.bean.News4FullScreenEntity;

/**
 * 〈一句话功能简述〉形象展示接口
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IFullScreenService {
    
    
    /**
     * 
     * 〈一句话功能简述〉获取所有信息
     * 
     * @param map 查询参数
     * @return PageEntity<Announcement4FullScreenEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> findAllInfo(Map<String, Object> map)
            throws Exception;
    
    
    
}
