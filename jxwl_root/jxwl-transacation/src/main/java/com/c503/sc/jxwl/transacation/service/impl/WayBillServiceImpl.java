/**
 * 文件名：EnterpriseServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillDao;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsDao;
import com.c503.sc.jxwl.transacation.service.IWayBillService;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.ICarLocationDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IOccupationPersonDao;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉物流企业信息业务层 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "wayBillService")
public class WayBillServiceImpl implements IWayBillService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillServiceImpl.class);
    
    /** 运单信息Dao */
    @Resource(name = "wayBillDao")
    private IWayBillDao wayBillDao;
    
    /** 车辆信息Dao */
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    /** 企业信息信息Dao */
    @Resource(name = "enterpriseDao")
    private IEnterpriseDao enterpriseDao;
    
    /** 运单信息--货物信息Dao */
    @Resource(name = "wayBillGoodsDao")
    private IWayBillGoodsDao wayBillGoodsDao;
    
    /** 从业人员信息Dao */
    @Resource(name = "occupationPersonDao")
    private IOccupationPersonDao occupationPersonDao;
    
    /** 车辆信息--车辆信息Dao */
    @Resource(name = "carLocationDao")
    private ICarLocationDao carLocationDao;
    
    /** 产生随机数对象 */
    private static Random random;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<WayBillEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillEntity> list = null;
        map.put("remove", SystemContants.ON);
        try {
            list = this.wayBillDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public List<WayBillEntity> findForAppByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillEntity> list = null;
        map.put("remove", SystemContants.ON);
        try {
            list = this.wayBillDao.findForAppByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public List<WayBillEntity> findVehicleData(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillEntity> list = null;
        map.put("remove", SystemContants.ON);
        try {
            list = this.wayBillDao.findVehicleData(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public CarLocationEntity findByCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        CarLocationEntity entity = null;
        try {
            entity = this.carLocationDao.findByCarrierName(carrierName);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, carrierName);
        
        return entity;
    }
    
    @Override
    public String findCorporate(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        String corporateNo = null;
        try {
            corporateNo = this.wayBillDao.findCorporate(carrierName);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, corporateNo);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, carrierName);
        
        return corporateNo;
    }
    
    @Override
    public List<EnterpriseEntity> findCorporateNo(String enterpriseType)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, enterpriseType);
        List<EnterpriseEntity> enterprise = null;
        try {
            enterprise = this.enterpriseDao.findCorporateNo(enterpriseType);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, enterprise);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, enterpriseType);
        
        return enterprise;
    }
    
    @Override
    public List<OccupationPersonEntity> findPersonType(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<OccupationPersonEntity> personType = null;
        try {
            personType = this.occupationPersonDao.findPersonType(map);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, personType);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, personType);
        
        return personType;
    }
    
    @Override
    public WayBillEntity save(WayBillEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        /*
         * // 创建货单号并判断货单号是否已经重复 //
         * 货单号生成规则："Y16110800001":"H"表示货单种类;"161108"表示日期;"00001"随机数 String
         * checkNo =
         * entity.getCheckno()+(random.nextInt(NumberContant.NINETY_THOUSAND) +
         * NumberContant.TEN_THOUSAND); while
         * (StringUtils.isNotEmpty(this.wayBillDao
         * .findWaybilllNoHasExist(checkNo ))){ checkNo =entity.getCheckno() +
         * (random
         * .nextInt(NumberContant.NINETY_THOUSAND)+NumberContant.TEN_THOUSAND );
         * } entity.setCheckno(checkNo);
         */
        try {
            this.wayBillDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return entity;
    }
    
    @Override
    public WayBillEntity saveTemp(WayBillEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        try {
            String id = entity.getId();
            // 获取运单信息
            WayBillEntity wayBillInfor = this.findById(id);
            
            // 获取货物信息
            List<WayBillGoodsEntity> wayBillGoods =
                this.wayBillGoodsDao.findAllGoods(id);
            wayBillInfor.setTempname(entity.getTempname());
            wayBillInfor.setListBillGoods(wayBillGoods);
            String returnId = C503StringUtils.createUUID();
            wayBillInfor.setId(returnId);
            wayBillInfor.setCreateTime(new Date());
            wayBillInfor.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            wayBillInfor.setRemove(SystemContants.ON);
            // 保存业务数据库
            this.wayBillDao.saveTemp(wayBillInfor);
            for (WayBillGoodsEntity wayBillGoodsEntity : wayBillGoods) {
                wayBillGoodsEntity.setId(C503StringUtils.createUUID());
                wayBillGoodsEntity.setWaybilltempId(returnId);
            }
            if (!wayBillGoods.isEmpty()) {
                this.wayBillDao.saveTempVal(wayBillGoods);
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity;
    }
    
    @Override
    public int update(WayBillEntity goods)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, goods);
        int upLine = 0;
        // 创建货单号并判断货单号是否已经重复
        // 货单号生成规则："Y16110800001":"H"表示货单种类;"161108"表示日期;"00001"随机数
        String checkNo = goods.getCheckno();
        if (checkNo.length() != NumberContant.ONE_TWO) {
            Date now = new Date();
            String pattern = "yyMMdd";
            String nowString = new SimpleDateFormat(pattern).format(now);
            checkNo =
                "Y"
                    + nowString
                    + (random.nextInt(NumberContant.NINETY_THOUSAND) + NumberContant.TEN_THOUSAND);
            while (StringUtils.isNotEmpty(this.wayBillDao.findWaybilllNoHasExist(checkNo))) {
                checkNo =
                    "Y"
                        + nowString
                        + (random.nextInt(NumberContant.NINETY_THOUSAND) + NumberContant.TEN_THOUSAND);
            }
        }
        goods.setCheckno(checkNo);
        
        try {
            upLine = this.wayBillDao.update(goods);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goods);
        }
        LOGGER.debug(SystemContants.DEBUG_END, goods);
        
        return upLine;
    }
    
    @Override
    public int updateForApp(WayBillEntity goods)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, goods);
        int upLine = 0;
        try {
            upLine = this.wayBillDao.updateForApp(goods);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goods);
        }
        LOGGER.debug(SystemContants.DEBUG_END, goods);
        return upLine;
    }
    
    @Override
    public WayBillEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        WayBillEntity entity = null;
        try {
            entity = this.wayBillDao.findById(id);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return entity;
    }
    
    @Override
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        delLine = this.wayBillDao.delete(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return delLine;
    }
    
    @Override
    public List<WayBillGoodsEntity> findGoodsData(String carNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carNo);
        List<WayBillGoodsEntity> list = null;
        try {
            list = this.wayBillDao.findGoodsData(carNo);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, carNo);
        
        return list;
    }
    
    @Override
    public String findRegIdByWayBill(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        String regId = null;
        try {
            regId = this.wayBillDao.findRegIdByWayBill(id);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, regId);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, regId);
        
        return regId;
    }
    
    @Override
    public CarLocationEntity findLocByWaybillId(String id)
        throws Exception {
        return this.carLocationDao.findLocByWaybillId(id);
    }
    
    // ///////////////////////////////运单 （微信）////////////////////////////////
    
    @Override
    public List<Map<String, Object>> findWaybillForWeixinParams(
        Map<String, Object> map)
        throws Exception {
        return this.wayBillDao.findWaybillForWeixinParams(map);
    }
    
    @Override
    public WayBillEntity isCarExist(String id)
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        return this.wayBillDao.isCarExist(map);
    }
    
    @Override
    public boolean isDeal(String orderNo)
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("remove", "0");
        String result = this.wayBillDao.isDeal(map);
        if (result == null) {
            return false;
        }
        return true;
    }
    
    @Override
    public WayBillEntity findOrderMessage(String orderNo)
        throws Exception {
        WayBillEntity orderMessage = this.wayBillDao.findOrderMessage(orderNo);
        return orderMessage;
    }
    
    @Override
    public WayBillEntity findForBind(Map<String, String> map)
        throws Exception {
        map.put("remove", "0");
        WayBillEntity entity = this.wayBillDao.findForBind(map);
        return entity;
    }
    
    @Override
    public String isHasWayBillNo(Map<String, String> map)
        throws Exception {
        String entity = this.wayBillDao.isHasWayBillNo(map);
        return entity;
    }
    
    /**
     * 创建随机数对象
     */
    static {
        random = new Random();
    }
    
}
