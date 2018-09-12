/**
 * 文件名：IImageShowController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import com.c503.sc.jxwl.transacation.bean.ParkArea;

/**
 * 〈一句话功能简述〉园区区域
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public interface IParkAreaService {
    /**
     * 
     * 〈一句话功能简述〉查询园区区域 〈功能详细描述〉
     * 
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    ParkArea findParkArea()
        throws Exception;
    
}
