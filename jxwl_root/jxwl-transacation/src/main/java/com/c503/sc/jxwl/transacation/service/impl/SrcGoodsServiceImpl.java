package com.c503.sc.jxwl.transacation.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsForFull;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.dao.ISrcGoodsDao;
import com.c503.sc.jxwl.transacation.service.ISrcGoodsService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉货源管理service
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class SrcGoodsServiceImpl implements ISrcGoodsService {
    
    /** 货源dao */
    @Autowired
    private ISrcGoodsDao srcGoodsDao;
    
    /** 产生随机数对象 */
    private static Random random;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(SrcGoodsServiceImpl.class);
    
    @Override
    public List<SrcGoods> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<SrcGoods> listSrcGoods = null;
        try {
            listSrcGoods = this.srcGoodsDao.findByParams(map);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, listSrcGoods);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return listSrcGoods;
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object save(SrcGoods srcGoods)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, srcGoods);
        // 创建货单号并判断货单号是否已经重复
        // 货单号生成规则："H161108001":"H"表示货单种类;"161108"表示日期;"001"随机数
        String waybilllNo =
            "H"
                + srcGoods.getWaybilllNo().substring(2)
                + (random.nextInt(NumberContant.NINE_HUNDRED) + NumberContant.HUNDRED);
        if (StringUtils.isNotEmpty(this.srcGoodsDao.findWaybilllNoHasExist(waybilllNo,
            null))) {
            this.save(srcGoods);
        }
        else {
            try {
                srcGoods.setWaybilllNo(waybilllNo);
                // 保存货源基本信息
                this.srcGoodsDao.save(srcGoods);
                // 保存货物
                this.srcGoodsDao.saveGoods(srcGoods.getGoodsInfos());
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION);
            }
            catch (Exception e) {
                throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, srcGoods);
        
        return srcGoods.getId();
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int delete(String id, String updateBy)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        int delLine = 0;
        String curStatus = this.srcGoodsDao.findTradeStatus(id);
        
        List<String> delStatus = new LinkedList<String>();
        delStatus.add(DictConstant.SRC_GOODS1_NOT_PUBLISH);
        delStatus.add(DictConstant.SRC_GOODS2_PUBLISHED);
        delStatus.add(DictConstant.SRC_GOODS4_REFUSED);
        if (StringUtils.equals("1", this.srcGoodsDao.findExsitById(id))) {
            throw new CustomException(BizExConstant.SRCGOODS_DELETE);
        }
        // 状态不在 未发布、已发布、已拒绝 内则不能进行删除
        if (!delStatus.contains(curStatus)) {
            throw new CustomException(BizExConstant.ORDER3_NOT_DELETE_E);
        }
        
        try {
            delLine = this.srcGoodsDao.delete(id, updateBy, new Date());
            delStatus = null;
            LOGGER.info(CommonConstant.DELETE_SUC_OPTION);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return delLine;
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(SrcGoods srcGoods)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, srcGoods);
        try {
            // 修改货源基本信息
            this.srcGoodsDao.update(srcGoods);
            // 删除该货源下的所有货物
            this.srcGoodsDao.delGoodsBySrcGoodsId(srcGoods.getId());
            // 保存该货源下的货物信息
            this.srcGoodsDao.saveGoods(srcGoods.getGoodsInfos());
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, srcGoods);
        
        return srcGoods;
    }
    
    @Override
    public SrcGoods findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        SrcGoods srcGoods = null;
        if (StringUtils.equals("1", this.srcGoodsDao.findExsitById(id))) {
            throw new CustomException(BizExConstant.SRCGOODS_DELETE);
        }
        try {
            srcGoods = this.srcGoodsDao.findById(id);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, srcGoods);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return srcGoods;
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int publishOrSign(String id, String status, String updateBy,
        String... pubOrSigns)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        Date date = new Date();
        if (StringUtils.equals("0", pubOrSigns[0])) {
            this.publish(id, status, updateBy, date);
        }
        else {
            this.sign(id, status, updateBy, date, pubOrSigns);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return 1;
    }
    
    /****************************************** 企业信息相关 **************************************/
    /**
     * 〈一句话功能简述〉分页查询企业信息
     * 〈功能详细描述〉
     * 
     * @param map map 根据企业名称（含法人代码）、匹配条件
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<EnterpriseEntity> findEnpInfos(Map<String, Object> map)
        throws Exception {
        List<EnterpriseEntity> list = null;
        String matchCondition = (String) map.get("matchCondition");
        // 交易数
        if (StringUtils.equals("003", matchCondition)) {
            list = this.srcGoodsDao.findEnpsByTradeNumParams(map);
            // 总评分
        }
        else if (StringUtils.equals("002", matchCondition)) {
            list = this.srcGoodsDao.findEnpsByCommentScoreParams(map);
            // 车辆数
        }
        else {
            list = this.srcGoodsDao.findEnpsByCarNumParams(map);
        }
        
        return list;
    }
    
    /**
     * 〈一句话功能简述〉大屏展示货物
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return List<SrcGoodsForFull>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<SrcGoodsForFull> findAll(String corporateNo)
        throws Exception {
        return this.srcGoodsDao.findAll(corporateNo);
    }

    /**
     * 〈一句话功能简述〉发布
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param tradeStatus 110001002
     * @param updateBy updateBy
     * @param date date
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void publish(String id, String tradeStatus, String updateBy,
        Date date)
        throws Exception {
        // 若已经被发布过，则异常
        if (!StringUtils.equals(DictConstant.SRC_GOODS1_NOT_PUBLISH,
            this.srcGoodsDao.findTradeStatus(id))) {
            throw new CustomException(BizExConstant.ORDER2_STATUS_SIGNED_E);
        }
        if (StringUtils.equals("1", this.srcGoodsDao.findExsitById(id))) {
            throw new CustomException(BizExConstant.SRCGOODS_DELETE);
        }
        
        try {
            this.srcGoodsDao.updateByPublish(id, tradeStatus, updateBy, date);
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
    }

    /**
     * 〈一句话功能简述〉签订
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param tradeStatus 110001003
     * @param updateBy updateBy
     * @param date date
     * @param strs strs[0]："0"==发布、"1"==签订、strs[1]：物流企业法人代码
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void sign(String id, String tradeStatus, String updateBy,
        Date date, String... strs)
        throws Exception {
        // 若已经被签订过，则异常
        if (!StringUtils.equals(DictConstant.SRC_GOODS2_PUBLISHED,
            this.srcGoodsDao.findTradeStatus(id))) {
            throw new CustomException(BizExConstant.ORDER1_STATUS_PUBLISHED_E);
        }
        
        try {
            
            // 创建订单号并判断货单号是否已经重复
            // 货单号生成规则："D161108001":"D"表示货单种类;"161108"表示日期;"001"随机数
            
            Date now = new Date();
            String pattern = "yyMMdd";
            String nowString = new SimpleDateFormat(pattern).format(now);
            String orderNo =
                "D"
                    + nowString
                    + (random.nextInt(NumberContant.NINE_HUNDRED) + NumberContant.HUNDRED);
            while (StringUtils.isNotEmpty(this.srcGoodsDao.findWaybilllNoHasExist(orderNo,
                null))) {
                orderNo =
                    "D"
                        + nowString
                        + (random.nextInt(NumberContant.NINE_HUNDRED) + NumberContant.HUNDRED);
                // TODO 没有判断所有记录用完了 如何解决
            }
            this.srcGoodsDao.updateBySign(orderNo,
                tradeStatus,
                strs[1],
                updateBy,
                id,
                date);
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION);
            
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
    }

    /**
     * 创建随机数对象
     */
    static {
        random = new Random();
    }
    
    
}
