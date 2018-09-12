/**
 * 文件名：EnterpriseServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sc.c503.authclient.service.AuthFactory;
import sc.c503.authclient.service.IAuthService;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.dict.bean.DictionaryValueEntity;
import com.c503.sc.dict.dao.IDictionaryValueDao;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.c503.sc.jxwl.orgdata.dao.SysUserDao;
import com.c503.sc.jxwl.orgdata.service.ISysOrgTypeService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseFileEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseFileDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IEnterpriseService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.exceltools.Title;

/**
 * 
 * 〈一句话功能简述〉物流企业信息业务层
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "enterpriseService")
public class EnterpriseServiceImpl implements IEnterpriseService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EnterpriseServiceImpl.class);
    
    /** 物流企业信息Dao */
    @Resource(name = "sysOrgTypeService")
    private ISysOrgTypeService sysOrgTypeService;
    
    /** 物流企业信息Dao */
    @Resource(name = "enterpriseDao")
    private IEnterpriseDao enterpriseDao;
    
    /** 数据字典接口 */
    @Resource(name = "dictionaryValueDao")
    private IDictionaryValueDao dictionaryValueDao;
    
    /** 附件关系接口 */
    @Resource(name = "enterpriseFileDao")
    private IEnterpriseFileDao enterpriseFileDao;
    
    /** IFileManageService 接口： 文件服务 持久层接口 */
    @Resource(name = "fileManageService")
    private IFileManageService fileManageService;
    
    /** 附件关系接口 */
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    /** 认证接口 */
    @SuppressWarnings("unused")
    @Resource(name = "sysUserDao")
    private SysUserDao sysUserDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public EnterpriseEntity save(EnterpriseEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        // 判断法人代码是否存在
        String no = entity.getCorporateNo();
        String str = this.enterpriseDao.findCorporateNoHasExist(no, null);
        if (StringUtils.isNotEmpty(str)) {
            throw new CustomException(BizExConstants.ENTERPRISE_CONRPORATREXIST);
        }
        entity.setId(C503StringUtils.createUUID());
        entity.setRemove(SystemContants.ON);
        Date curDate = new Date();
        entity.setCreateTime(curDate);
        entity.setUpdateTime(curDate);
        // 新增企业，车辆数量默认为0
        entity.setVehicleNum(new BigDecimal(NumberContant.ZERO));
        try {
            // 保存业务数据库
            this.enterpriseDao.save(entity);
            // 保存认证数据库
            SysOrganTypeEntity orgEntity = entity.bean2auth(entity);
            this.sysOrgTypeService.updateOrSave(orgEntity);
            // 保存附件关系
            String[] fileIds = entity.getFileIds();
            if (null != fileIds && NumberContant.ZERO < fileIds.length) {
                savaFileIds(fileIds, entity);
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(EnterpriseEntity entity)
        throws Exception {
        
        // 判断法人代码是否存在
        String no = entity.getCorporateNo();
        String str =
            this.enterpriseDao.findCorporateNoHasExist(no, entity.getId());
        if (StringUtils.isNotEmpty(str)) {
            throw new CustomException(BizExConstants.ENTERPRISE_CONRPORATREXIST);
        }
        //
        // if
        // (StringUtils.isNotEmpty(this.enterpriseDao.findCorporateNoHasExist(entity.getCorporateNo(),
        // entity.getId()))) {
        // throw new CustomException(BizExConstants.ENTERPRISE_CONRPORATREXIST);
        // }
        
        EnterpriseEntity entityFind = findById(entity.getId());
        String flag = entityFind.getEnterpriseTypeFlag();
        // 物流企业可以编辑
        if (DictConstant.ENTERPRISE_TYPE_LOGISTICS.equals(flag)) {
            // 同步车辆数量
            Integer n =
                dangerVehicleDao.getDangerVehicNumByCorporateNo(entityFind.getCorporateNo());
            BigDecimal num = new BigDecimal(n == null ? NumberContant.ZERO : n);
            entity.setVehicleNum(num);
            this.enterpriseDao.update(entity);
            // 保存认证数据库
            SysOrganTypeEntity orgEntity = entity.bean2auth(entity);
            this.sysOrgTypeService.updateOrSave(orgEntity);
            
            // 保存附件关系
            String[] fileIds = entity.getFileIds();
            this.enterpriseFileDao.deleteFileId(entityFind.getId());
            if (null != fileIds && NumberContant.ZERO < fileIds.length) {
                savaFileIds(fileIds, entity);
            }
        }
        // 测试化工企业编辑
        else if (DictConstant.ENTERPRISE_TYPE_CHEMICAL.equals(flag)) {
        	// 同步车辆数量
            Integer n =
                dangerVehicleDao.getDangerVehicNumByCorporateNo(entityFind.getCorporateNo());
            BigDecimal num = new BigDecimal(n == null ? NumberContant.ZERO : n);
            entity.setVehicleNum(num);
            this.enterpriseDao.update(entity);
            // 保存认证数据库
            SysOrganTypeEntity orgEntity = entity.bean2auth(entity);
            this.sysOrgTypeService.updateOrSave(orgEntity);
            String[] fileIds = entity.getFileIds();
            // if (isChange(entity.getFileIds(), entityFind.getFileIds())) {
            // if (null != fileIds && NumberContant.ZERO < fileIds.length) {
            // this.enterpriseFileDao.deleteFileId(entityFind.getId());
            // // 需要将以前的附件id清除一次
            // savaFileIds(fileIds, entity);
            // }
            // }
            this.enterpriseFileDao.deleteFileId(entityFind.getId());
            if (null != fileIds && NumberContant.ZERO < fileIds.length) {
            	//this.enterpriseFileDao.deleteFileId(entityFind.getId());
                // 需要将以前的附件id清除一次
                savaFileIds(fileIds, entity);
            }
            /*
             * else {
             * throw new CustomException(
             * BizConstants.ENTERPRISE_TYPE_CHEMICAL_UPDATE_NOT_ALLOW,
             * entity);
             * }
             */
            
        }
        return entity;
    }
    
    /**
     * 〈一句话功能简述〉判断是否改变
     * 〈功能详细描述〉
     * 
     * @param newFileIds newFileIds
     * @param oldFileIds oldFileIds
     * @return object
     * @see [类、类#方法、类#成员]
     */
    // private boolean isChange(String[] newFileIds, String[] oldFileIds) {
    // boolean isTure = false;
    // if (newFileIds.length != oldFileIds.length) {
    // isTure = true;
    // }
    // else {
    // // 遍历数组中所有元素
    // int j = oldFileIds.length;
    // for (String fileId : newFileIds) {
    // int count = 0;
    // // 对新数组的的所有元素进行比较，是否相同
    // for (int i = 0; i < j; i++) {
    // if (!fileId.equals(oldFileIds[i])) {
    // // 全部都不同，说明附件已经
    // if ((j - 1) == count++) {
    // isTure = true;
    // break;
    // }
    // continue;
    // }
    // }
    // }
    // }
    // return isTure;
    // }
    
    /**
     * 〈一句话功能简述〉保存附件信息
     * 〈功能详细描述〉
     * 
     * @param fileIds fileIds
     * @param entity entity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void savaFileIds(String[] fileIds, EnterpriseEntity entity)
        throws Exception {
        
        List<EnterpriseFileEntity> list = new ArrayList<EnterpriseFileEntity>();
        this.createFileRelationVal(list, entity);
        this.enterpriseFileDao.saves(list);
        
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<EnterpriseEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<EnterpriseEntity> list = null;
        map.put("remove", SystemContants.ON);
        list = this.enterpriseDao.findByParams(map);
        int size = list.size();
        if (0 < size) {
            for (EnterpriseEntity entity : list) {
                String type = findNameByCode(entity.getEnterpriseType());
                entity.setEnterpriseType(type);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public EnterpriseEntity findById(String id)
        throws Exception {
        EnterpriseEntity entity = new EnterpriseEntity();
        
        entity = this.enterpriseDao.findById(id);
        entity.setEnterpriseTypeFlag(entity.getEnterpriseType());
        String type = findNameByCode(entity.getEnterpriseType());
        entity.setEnterpriseType(type);
        // 查找附件id
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
        entity.setFileIds(fileIDs);
        entity.setFileNames(fileNames);
        
        return entity;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取该企业的附件id
     * 〈功能详细描述〉
     * 
     * @param id 企业id
     * @return 附件id
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private List<String> findFileId(String id)
        throws Exception {
        List<String> fileIds = new ArrayList<String>();
        fileIds = this.enterpriseDao.findFileIdById(id);
        return fileIds;
    }
    
    @Override
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        delLine = this.enterpriseDao.delete(map);
        // sysOrgTypeService.delete(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return delLine;
    }
    
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> exportExcel(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 查询满足条件的渔港信息集合
        map.put("remove", SystemContants.ON);
        List<EnterpriseEntity> list = null;
        list = this.enterpriseDao.findByParams(map);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            EnterpriseEntity entity = list.get(i);
            entity.setRowNum(i + NumberContant.ONE);
            String type = entity.getEnterpriseType();
            if (!C503StringUtils.isEmpty(type)) {
                entity.setEnterpriseType(findNameByCode(type));
            }
        }
        
        // 生成sheet对象
        ExportSheet sheet = createSheet(list);
        resultMap.put("sheet", sheet);
        resultMap.put("excelName", "企业信息表");
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return resultMap;
    }
    
    /**
     * 
     * 〈一句话功能简述〉将数据组装为excel表单
     * 〈功能详细描述〉
     * 
     * @param dataList 数据结合
     * @return excel表单
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createSheet(List<EnterpriseEntity> dataList) {
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Object[] headNames =
            new Object[] {"序号", "企业名称", "法人代码", "企业类型", "车辆数", "法人代表", "登记机关",
                "注册资本", "主营路线", "成立时间", "开户行", "工商注册号", "主营业务", "税务证号", "经营区域",
                "经营地址", "联系电话"};
        header.setHeadNames(headNames);
        // 设置表的标题
        Title title = new Title();
        title.setTitleName("物流企业信息表");
        // 设置表头对应的属性
        String[] fields =
            new String[] {"rowNum", "enterpriseName", "corporateNo",
                "enterpriseType", "vehicleNum", "corporateRep",
                "registerOffice", "logonCapital", "majorBusinessRoute",
                "establishTime", "openAccountBank", "registrationNo",
                "professionalWork", "taxationNo", "operateArea", "address",
                "telephone"};
        
        Content content = new Content();
        content.setFieldNames(fields);
        content.setDataList(dataList);
        sheet.setHeader(header);
        sheet.setTitle(title);
        sheet.setContent(content);
        return sheet;
    }
    
    /**
     * 〈一句话功能简述〉创建企业信息附件关系数据
     * 〈功能详细描述〉
     * 
     * @param entityRelaVals List<EnterpriseFileEntity>
     * @param entity EnterpriseEntity
     * @see [类、类#方法、类#成员]
     */
    private void createFileRelationVal(
        List<EnterpriseFileEntity> entityRelaVals, EnterpriseEntity entity) {
        // Date curDate = new Date();
        for (String fileId : entity.getFileIds()) {
            EnterpriseFileEntity relaVal = new EnterpriseFileEntity();
            relaVal.setId(C503StringUtils.createUUID());
            relaVal.setEnterpriseId(entity.getId());
            relaVal.setFileId(fileId);
            relaVal.setRemove(SystemContants.ON);
            entityRelaVals.add(relaVal);
        }
    }
    
    /**
     * 
     * 〈一句话功能简述〉根据字典的value值获取字典的名称 〈功能详细描述〉
     * 
     * @param value
     *            字典的value
     * @return 获取字典的名称
     * @throws Exception
     *             数据库异常
     * @see [类、类#方法、类#成员]
     */
    private String findNameByCode(String value)
        throws Exception {
        String name = "";
        if (C503StringUtils.isNotEmpty(value)) {
            DictionaryValueEntity dic =
                this.dictionaryValueDao.findDicByValue(value);
            name = null == dic ? "" : dic.getName();
        }
        return name;
    }
    
    @Override
    public int deleteLogisticst(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        delLine = this.enterpriseDao.deleteLogisticst(map);
        
        // 该企业以及其车辆和人员更新remove
        this.enterpriseDao.updatePersonAndCar(map);
        
        // 该企业以及其车辆和人员 在资质管理中的 审核状态为已注销
        this.enterpriseDao.updateLicenceInfo(map);
        
        // TODO sysOrgTypeService.delete(map);
        // authclientDelete(map);
        
        IAuthService authService = AuthFactory.getAuthService();
        int result = authService.deleteOrgUserByOrgId((String) map.get("id"));
        
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return delLine;
    }
    
    /*
     * public void authclientDelete(Map<String, Object> map) throws Exception{
     * String url="http://172.25.2.126:8081/AUTH/rest/sysUser/enable";
     * Map<String,Object> mapDelete = new HashMap<String,Object>();
     * String id = (String)map.get("id");
     * String[] ids = {id};
     * mapDelete.put("ids",ids);
     * mapDelete.put("status", 1);
     * mapDelete.put("sysId", 6);
     * ResultMessage mess = HttpClientUtils.doPost(url, mapDelete);
     * }
     */
    
    @Override
    public String findNameByNo(String corporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, corporateNo);
        String result = null;
        result = this.enterpriseDao.findNameByNo(corporateNo);
        // sysOrgTypeService.delete(map);
        LOGGER.debug(SystemContants.DEBUG_END, corporateNo);
        return result;
    }
    
    @Override
    public List<EnterpriseEntity> findAllName(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        return this.enterpriseDao.findAllName(map);
    }
    
    @Override
    public int batchSynchronize(List<EnterpriseEntity> entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        int i = this.enterpriseDao.batchSave(entity);
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return i;
    }
    
    @Override
    public String isExist(Map<String, String> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        String result = null;
        result = this.enterpriseDao.isExist(map);
        // sysOrgTypeService.delete(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
}
