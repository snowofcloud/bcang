/**
 * 文件名：IInOutAreaDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;

@Repository(value = "inOutAreaDao")
public interface IInOutAreaDao extends IBaseDao<InOutAreaEntity> {
    
    int save(InOutAreaEntity inOutAreaEntity);
    
    int update(InOutAreaEntity inOutAreaEntity);
    
    List<InOutAreaEntity> findByParams(Map<String, Object> map);
}
