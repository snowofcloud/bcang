/**
 * 文件名：ICommentComplainService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-12-21
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;

/**
 * 〈一句话功能简述〉评价投诉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ICommentComplainService extends IBaseService<CommentComplain> {
    
    /**
     * 〈一句话功能简述〉根据法人代码平均分
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return BigDecimal
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object findAvgScore(String corporateNo)
        throws Exception;
    
}
