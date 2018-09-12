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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsTempEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillTempEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillDao;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsTempDao;
import com.c503.sc.jxwl.transacation.dao.IWayBillTempDao;
import com.c503.sc.jxwl.transacation.service.IWayBillTempService;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.ICarLocationDao;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉运单--模板管理
 * 〈功能详细描述〉
 * 
 * @author WL
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "wayBillTempService")
public class WayBillTempServiceImpl implements IWayBillTempService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillTempServiceImpl.class);
    
    /** 运单信息Dao */
    @Resource(name = "wayBillTempDao")
    private IWayBillTempDao wayBillTempDao;
    
    /** 运单信息--货物信息Dao */
    @Resource(name = "wayBillGoodsTempDao")
    private IWayBillGoodsTempDao wayBillGoodsTempDao;
    
    /** 车辆信息--车辆信息Dao */
    @Resource(name = "carLocationDao")
    private ICarLocationDao carLocationDao;
    
    /** 运单信息Dao */
    @Resource(name = "wayBillDao")
    private IWayBillDao wayBillDao;
    
    /** 产生随机数对象 */
    private static Random random;
    
    /**
     * 创建随机数对象
     */
    static {
        random = new Random();
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<WayBillTempEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillTempEntity> list = null;
        map.put("remove", SystemContants.ON);
        try {
            list = this.wayBillTempDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public List<WayBillTempEntity> findVehicleData(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillTempEntity> list = null;
        map.put("remove", SystemContants.ON);
        try {
            list = this.wayBillTempDao.findVehicleData(map);
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
    public WayBillTempEntity save(WayBillTempEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        try {
            this.wayBillTempDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return entity;
    }
    
    @Override
    public WayBillTempEntity saveWayBill(WayBillTempEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        try {
            
            // 创建货单号并判断货单号是否已经重复
            // 货单号生成规则："Y16110800001":"H"表示货单种类;"161108"表示日期;"00001"随机数
            Date now = new Date();
            String pattern = "yyMMdd";
            String nowString = new SimpleDateFormat(pattern).format(now);
            String checkNo =
                "Y"
                    + nowString
                    + (random.nextInt(NumberContant.NINETY_THOUSAND) + NumberContant.TEN_THOUSAND);
            while (StringUtils.isNotEmpty(this.wayBillDao.findWaybilllNoHasExist(checkNo))) {
                checkNo =
                    "Y"
                        + nowString
                        + (random.nextInt(NumberContant.NINETY_THOUSAND) + NumberContant.TEN_THOUSAND);
            }
            entity.setCheckno(checkNo);
            // 获取运单模板ID提供查询模板货物的ID
            String id = entity.getId();
            String returnId = C503StringUtils.createUUID();
            entity.setId(returnId);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            
            // 获取货物信息
            List<WayBillGoodsTempEntity> wayBillGoodsTemp =
                this.wayBillGoodsTempDao.findAllGoods(id);
            entity.setListBillGoodsTemp(wayBillGoodsTemp);
            
            // 保存业务数据库
            this.wayBillTempDao.saveWayBill(entity);
            for (WayBillGoodsTempEntity wayBillGoodsTempEntity : wayBillGoodsTemp) {
                wayBillGoodsTempEntity.setId(C503StringUtils.createUUID());
                wayBillGoodsTempEntity.setWaybillId(returnId);
            }
            if (!wayBillGoodsTemp.isEmpty()) {
                this.wayBillTempDao.saveWayBillVal(wayBillGoodsTemp);
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity;
    }
    
    @Override
    public int update(WayBillTempEntity goods)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, goods);
        int upLine = 0;
        
        try {
            upLine = this.wayBillTempDao.update(goods);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goods);
        }
        LOGGER.debug(SystemContants.DEBUG_END, goods);
        
        return upLine;
    }
    
    @Override
    public WayBillTempEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        WayBillTempEntity entity = null;
        try {
            entity = this.wayBillTempDao.findById(id);
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
        delLine = this.wayBillTempDao.delete(map);
        // sysOrgTypeService.delete(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return delLine;
    }
    
    @Override
    public boolean isExistTempName(String tempName)
        throws Exception {
        boolean flag = false;
        String name = this.wayBillTempDao.isExistTempName(tempName);
        if (name != null) {
            flag = true;
        }
        return flag;
    }

	@Override
	public List<Map<String, Object>> placeAndPoi(String orderNo)
			throws Exception {
		List<Map<String, Object>> list = this.wayBillTempDao.placeAndPoi(orderNo);
		for (Map<String, Object> map : list) {
			map.put("name",map.get("NAME"));
			map.remove("NAME");
			map.put("longitude",map.get("LONGITUDE"));
			map.remove("LONGITUDE");
			map.put("latitude",map.get("LATITUDE"));
			map.remove("LATITUDE");
		}
		
		return list;
	}
    
}
