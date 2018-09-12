/**
 * 文件名：IEnterpriseFileDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-2
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseFileEntity;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-8-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository("enterpriseFileDao")
public interface IEnterpriseFileDao extends IBaseDao<EnterpriseFileEntity> {
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param list List<EnterpriseFileEntity>
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int saves(List<EnterpriseFileEntity> list)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉删除企业的附件id
     * 〈功能详细描述〉
     * 
     * @param id 企业的id
     * @return 删除的行数
     * @throws Exception 数据库异常
     * @see [类、类#方法、类#成员]
     */
    int deleteFileId(String id)
        throws Exception;
}
