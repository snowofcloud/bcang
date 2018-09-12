/**
 * 文件名：LicenceInfoServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.bean.LicenceFileRelaEntity;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.dao.ILicenceFileRelaDao;
import com.c503.sc.jxwl.transacation.dao.ILicenceInfoDao;
import com.c503.sc.jxwl.transacation.service.ILicenceInfoService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉订单管理 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "licenceInfoService")
public class LicenceInfoServiceImpl implements ILicenceInfoService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LicenceInfoServiceImpl.class);
    
    /** 资质信息接口 */
    @Resource(name = "licenceInfoDao")
    private ILicenceInfoDao licenceInfoDao;
    
    /** IFileManageService 接口： 文件服务 持久层接口 */
    @Resource(name = "fileManageService")
    private IFileManageService fileManageService;
    
    /** 资质附件接口 */
    @Resource(name = "licenceFileRelaDao")
    private ILicenceFileRelaDao licenceFileRelaDao;
    
    /** 危险品车辆信息Dao */
    @SuppressWarnings("unused")
    @Resource
    private IDangerVehicleDao dangerVehicleDao;
    
    @Override
    public List<LicenceEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<LicenceEntity> result = null;
        map.put("remove", SystemContants.ON);
        result = this.licenceInfoDao.findByParams(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    @Override
    public LicenceEntity findById(String licenceType, String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        Map<String, Object> map = new HashMap<>();
        map.put("licenceType", licenceType);
        map.put("id", id);
        LicenceEntity result = new LicenceEntity();
        // 查询资质实体对象
        result = this.licenceInfoDao.findById(map);
        if (result == null) {
            throw new CustomException(BizExConstant.INFORMATION_DELETED);
        }
        // 查询资质附件对象
        List<String> fileIds = findFileId(id);
        String[] fileIdsArray = new String[fileIds.size()];
        fileIds.toArray(fileIdsArray);
        List<FileInfoEntity> fileInfoEntities = new ArrayList<FileInfoEntity>();
        if (NumberContant.ZERO < fileIdsArray.length) {
            fileInfoEntities =
                this.fileManageService.findFileInfoFromDB(fileIdsArray);
        }
        int sizeOfFile = fileInfoEntities.size();
        
        String[] fileNames = new String[sizeOfFile];
        String[] fileIDs = new String[sizeOfFile];
        if (NumberContant.ZERO < sizeOfFile) {
            for (int i = 0; i < sizeOfFile; i++) {
                FileInfoEntity fileInfo = fileInfoEntities.get(i);
                fileNames[i] = fileInfo.getFileName();
                fileIDs[i] = fileInfo.getId();
            }
        }
        result.setFileIds(fileIDs);
        result.setFileNames(fileNames);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return result;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取该企业的附件id
     * 〈功能详细描述〉
     * 
     * @param id 企业id
     * @return 附件List<String>
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private List<String> findFileId(String id)
        throws Exception {
        List<String> fileIds = new ArrayList<String>();
        fileIds = this.licenceFileRelaDao.findFileIdById(id);
        return fileIds;
    }
    
    @Override
    public Object save(LicenceEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        entity.setId(C503StringUtils.createUUID());
        entity.setVerifyStatus(DictConstant.LICENCE_VERIFY_WAIT);
        entity.setUpdateTime(new Date());
        // 保存之前 验证输入参数
        check(entity);
        // 资质实体对象
        this.licenceInfoDao.save(entity);
        // 资质附件对象
        String[] fileIds = entity.getFileIds();
        if (null != fileIds && 0 < fileIds.length) {
            List<LicenceFileRelaEntity> list =
                new ArrayList<LicenceFileRelaEntity>();
            this.createFileRelationVal(list, entity);
            this.licenceFileRelaDao.saves(list);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return entity.getId();
    }
    
    /**
     * 〈一句话功能简述〉创建危险品车辆附件关系 〈功能详细描述〉
     * 
     * @param licenceRelaVals List<DangerFileRelaEntity>
     * @param licenceEntity LicenceEntity
     * @see [类、类#方法、类#成员]
     */
    public void createFileRelationVal(
        List<LicenceFileRelaEntity> licenceRelaVals, LicenceEntity licenceEntity) {
        for (String fileId : licenceEntity.getFileIds()) {
            LicenceFileRelaEntity relaVal = new LicenceFileRelaEntity();
            relaVal.setId(C503StringUtils.createUUID());
            relaVal.setLicenceId(licenceEntity.getId());
            relaVal.setFileId(fileId);
            relaVal.setRemove(SystemContants.ON);
            
            licenceRelaVals.add(relaVal);
        }
    }
    
    @Override
    public int update(LicenceEntity entity, String action)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        int result = 0;
        // 资质对象更新
        entity.setUpdateTime(new Date());
        if (action.equals("update")) {
            check(entity);
            // 清楚拒绝原因
            entity.setRejectReason("");
            // 保存新的附件信息
            // 资质附件对象
            String[] fileIds = entity.getFileIds();
            if (null != fileIds && 0 < fileIds.length) {
                List<LicenceFileRelaEntity> list =
                    new ArrayList<LicenceFileRelaEntity>();
                this.createFileRelationVal(list, entity);
                this.licenceFileRelaDao.saves(list);
            }
        }
        result = this.licenceInfoDao.update(entity);
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return result;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean check(LicenceEntity entity)
        throws Exception {
        String licenceType = entity.getLicenceType();
        if (licenceType.equals("car")) {// 车牌号是否属于该物流企业
            // 法人代码 车牌号 在车辆表中查询 抛出异常 定义业务常量
            Map<String, Object> map = new HashMap<>();
            map.put("corporateNo", entity.getCorporateNo());
            map.put("licencePlateNo", entity.getLicencePlateNo());
            if (this.licenceInfoDao.findCar4match(map) == null) {
                throw new CustomException(BizExConstant.ENTERPRISE_CARNOEXIST);
            }
        } else if (licenceType.equals("person")) {// 从业人员是否属于该物流企业
            // 法人代码 人身份证 在从业人员表中查询 抛出异常 定义业务常量
            Map<String, Object> map = new HashMap<>();
            map.put("personName", entity.getPersonName());
            map.put("corporateNo", entity.getCorporateNo());
            map.put("identificationCardNo", entity.getIdentificationCardNo());
            if (this.licenceInfoDao.findPerson4match(map) == null) {
                throw new CustomException(
                    BizExConstant.ENTERPRISE_PERSONNOEXIST);
            }
        }
        return false;
    }
    
    @Override
    public int delete(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        int result = 0;
        // 删除资质对象
        result = this.licenceInfoDao.delete(id);
        
        // 删除相应的附件对象
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return result;
    }
    
    @Override
    public List<OccupationPersonEntity> findPerson(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<OccupationPersonEntity> list = this.licenceInfoDao.findPerson(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public List<EnterpriseEntity> findEnterprise(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<EnterpriseEntity> list = this.licenceInfoDao.findEnterprise(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public boolean isExit(Map<String, String> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        LicenceEntity entity = this.licenceInfoDao.isExit(map);
        if (entity == null) {
            return false;
        }
        String licenceType = map.get("licenceType");
        if (licenceType.equals("enterprise") && entity.getCorporateNo() != null) {
            return true;
        } else if (licenceType.equals("car") && entity.getCorporateNo() != null
            && entity.getLicencePlateNo() != null) {
            return true;
        } else if (licenceType.equals("person")
            && entity.getCorporateNo() != null
            && entity.getIdentificationCardNo() != null) {
            return true;
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return false;
    }
}
