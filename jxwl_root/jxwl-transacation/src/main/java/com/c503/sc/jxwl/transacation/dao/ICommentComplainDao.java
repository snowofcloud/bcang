/**
 * 文件名：ISrcGoodsDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface ICommentComplainDao extends IBaseDao<CommentComplain> {
    
    /**
     * 〈一句话功能简述〉查询来自企业的评价或投诉
     * 〈功能详细描述〉
     * 
     * @param map corporateNo、type
     * @return List<CommentComplain>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<CommentComplain> findOtherByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新
     * 〈功能详细描述〉
     * 
     * @param arg CommentComplain
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(CommentComplain arg)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据法人代码平均分
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return BigDecimal
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    BigDecimal findAvgScore(@Param("corporateNo") String corporateNo)
        throws Exception;
    
}
