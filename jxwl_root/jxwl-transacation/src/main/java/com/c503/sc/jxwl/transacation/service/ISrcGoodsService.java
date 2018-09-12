package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsForFull;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;

/**
 * 〈一句话功能简述〉货源接口
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ISrcGoodsService {
    
    /**
     * 〈一句话功能简述〉查询所有货源信息
     * 〈功能详细描述〉
     * 
     * @param map 货单号, 发布开始时间、结束时间
     * @return List<SrcGoods>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存货源
     * 〈功能详细描述〉
     * 
     * @param srcGoods SrcGoods
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(SrcGoods srcGoods)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除货源
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param updateBy updateBy
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(String id, String updateBy)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改货源
     * 〈功能详细描述〉
     * 
     * @param srcGoods SrcGoods
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object update(SrcGoods srcGoods)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询货源
     * 〈功能详细描述〉
     * 
     * @param id 货源id
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    SrcGoods findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉发布或签订
     * 〈功能详细描述〉
     * 
     * @param id 货源id
     * @param status 交易状态
     * @param updateBy updateBy
     * @param pubOrSigns pubOrSigns[0]："0"==发布、"1"==签订、pubOrSigns[1]：物流企业法人代码
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int publishOrSign(String id, String status, String updateBy,
        String... pubOrSigns)
        throws Exception;
    
    /****************************************** 企业信息相关 **************************************/
    /**
     * 〈一句话功能简述〉分页查询企业信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findEnpInfos(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查货物数据for大屏展示
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return List<SrcGoodsForFull>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoodsForFull> findAll(String corporateNo)
        throws Exception;
}
