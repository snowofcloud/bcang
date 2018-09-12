/**
 * 文件名：CommentComplainServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-12-21
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.base.service.impl.BaseServiceImpl;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.dao.ICommentComplainDao;
import com.c503.sc.jxwl.transacation.service.ICommentComplainService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉评价、投诉service
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class CommentComplainServiceImpl extends
    BaseServiceImpl<CommentComplain> implements ICommentComplainService {
    
    /** 评价投诉dao */
    @Resource
    private ICommentComplainDao commentComplainDao;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(CommentComplainServiceImpl.class);
    
    @Override
    public List<CommentComplain> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        String moudle = (String) map.get("moudle");
        String who = (String) map.get("who");
        
        List<CommentComplain> commentComplains = null;
        
        // 评价
        if (StringUtils.equals(moudle, "0")) {
            map.put("type", "0");
            // 投诉
        } else if (StringUtils.equals(moudle, "1")) {
            map.put("type", "1");
        }
        // 来自企业评价、投诉
        if (StringUtils.equals(who, "0")) {
            commentComplains = this.commentComplainDao.findOtherByParams(map);
            // 给他人评价、投诉
        } else {
            commentComplains = this.commentComplainDao.findByParams(map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return commentComplains;
    }
    
    @Override
    public CommentComplain findById(Serializable arg0)
        throws Exception {
        return super.findById(arg0);
    }
    
    @Override
    public CommentComplain update(CommentComplain arg0)
        throws Exception {
        this.commentComplainDao.update(arg0);
        
        return arg0;
    }
    
    @Override
    public Object findAvgScore(String corporateNo)
        throws Exception {
        return this.commentComplainDao.findAvgScore(corporateNo);
    }
    
    @Override
    public IBaseDao<CommentComplain> getBaseDao() {
        return this.commentComplainDao;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
}
