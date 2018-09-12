package com.c503.sc.jxwl.pipelinemangnt.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineLocationEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineStatusEntity;
/**
 * 〈一句话功能简述〉IPipeLineService
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2017-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IPipeLineService {
    
    /**
     * 〈一句话功能简述〉findByParams
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<PipeLineEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<PipeLineEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findById
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return PipeLineEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    PipeLineEntity findById(Map<String, Object> map)
        throws Exception;
    
    /**
     * 获取所有的管道和储罐的位置信息
     * 
     * @param map 查询条件
     * @return 位置列表
     * @throws Exception 查询异常
     * @see [类、类#方法、类#成员]
     */
    List<PipeLineLocationEntity> findPipeLocations(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findLiquid
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<PipeLineEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<PipeLineStatusEntity> findLiquid(Map<String, Object> map)
        throws Exception;
    
}
