/**
 * 文件名：EnterpriseService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-6-28
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.service;

import java.util.List;

import com.c503.sc.jxwl.common.bean.EnterpriseEntity;
import com.c503.sc.jxwl.common.bean.ManagerEntity;

/**
 * 〈一句话功能简述〉查询企业相关信息
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IEnterpriseInfoService {
    /**
     * 〈一句话功能简述〉根据用户id查询企业法人代码
     * 〈功能详细描述〉
     * 
     * @param id 用户id
     * @return ManagerEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    ManagerEntity findCorporateCode(String id)
        throws Exception;
    
    List<EnterpriseEntity> findEnterpriseByCorporateCode(String corporateNo)
        throws Exception;

}
