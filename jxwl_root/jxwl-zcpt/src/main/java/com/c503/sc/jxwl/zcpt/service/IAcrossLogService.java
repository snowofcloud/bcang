package com.c503.sc.jxwl.zcpt.service;

import java.util.List;

import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.zcpt.bean.AcrossLogEntity;

/**
 * 〈一句话功能简述〉行政区域出入记录业务接口
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-10-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IAcrossLogService extends IBaseService<AcrossLogEntity> {
    
    /**
     * 〈一句话功能简述〉根据实时位置批量保存行政区域出入记录
     * 〈功能详细描述〉
     * 
     * @param locList 实时位置列表
     * @return 成功/失败
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    boolean batchSaveAcross(List<LocationEntity> locList)
        throws Exception;
    
}
