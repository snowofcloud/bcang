/**
 * 文件名：EnterpriseServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.c503.sc.jxwl.orgdata.dao.ISysOrganTypeDao;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseFileEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseFileDao;
import com.c503.sc.jxwl.vehiclemonitor.service.impl.EnterpriseServiceImpl;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.zx.framework.net.examples.nntp.newsgroups;

/**
 * 
 * 〈一句话功能简述〉物流企业信息测试 〈功能详细描述〉
 * 
 * @author yangjh
 * @version [版本号, 2016-8-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnterpriseServiceTest extends BaseTest {
    @InjectMocks
    @Resource(name = "enterpriseService")
    private IEnterpriseService enterpriseService;
    
    String pk = null;
    
    EnterpriseEntity entity = new EnterpriseEntity();
    
    @Resource(name = "enterpriseDao")
    private IEnterpriseDao enterpriseDao;
    
    @Resource(name = "sysOrganTypeDao")
    private ISysOrganTypeDao sysOrganTypeDao;
    
    /** IFileManageService 接口： 文件服务 持久层接口 */
    @Mock
    @Resource(name = "fileManageService")
    private IFileManageService fileManageService;
    
    Class<EnterpriseServiceImpl> enterpriseServiceImplClazz =
        EnterpriseServiceImpl.class;
    
    /** 附件关系接口 */
    @Resource(name = "enterpriseFileDao")
    private IEnterpriseFileDao enterpriseFileDao;
    
    private List<String> fileIds = new ArrayList<String>();
    
    private List<FileInfoEntity> multiList = new ArrayList<FileInfoEntity>();
    
    /**
     * 
     * 〈一句话功能简述〉 初始化条件 〈功能详细描述〉
     * 
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @Before
    public void setBefore()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.when(fileManageService.saveFileAndInfo(singleFileUpload()))
            .thenReturn(singleFiles);
        PowerMockito.when(fileManageService.saveFileAndInfo(multiFileUpload()))
            .thenReturn(multiList);
        String[] fileidls = new String[fileIds.size()];
        PowerMockito.when(fileManageService.findFileInfoFromDB(fileIds.toArray(fileidls)))
            .thenReturn(multiList);
        entity = initInfo();
    }
    
    public EnterpriseEntity initInfo() {
        // 存入机构信息
        SysOrganTypeEntity entity2 = new SysOrganTypeEntity();
        entity2.setId(C503StringUtils.createUUID());
        entity2.setCode(C503StringUtils.createUUID());
        entity2.setOrganTypeId("106001001");
        entity2.setName("盛通物流");
        // 物流系系统id为6
        entity2.setSysId("6");
        entity2.setPid("1000000");
        entity2.setRemove("0");
        this.sysOrganTypeDao.save(entity2);
        entity.setId(C503StringUtils.createUUID());
        entity.setAddress("成都");
        entity.setCorporateNo("1111");
        entity.setEnterpriseName("嘉兴");
        entity.setRegistrationNo("2222");
        entity.setRemove("0");
        entity.setTaxationNo("3333");
        
        entity.setAddress("成都");
        entity.setCorporateNo(entity2.getCode());
        entity.setEnterpriseName("嘉兴");
        entity.setRegistrationNo("2222");
        entity.setRemove("0");
        entity.setTaxationNo("3333");
        entity.setEnterpriseType("106001001");
        String[] fileId = new String[fileIds.size()];
        
        entity.setFileIds(fileIds.toArray(fileId));
        try {
            this.enterpriseDao.save(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] fileIds = entity.getFileIds();
        if (null != fileIds && NumberContant.ZERO < fileIds.length) {
            List<EnterpriseFileEntity> list =
                new ArrayList<EnterpriseFileEntity>();
            this.createFileRelationVal(list, entity);
            try {
                this.enterpriseFileDao.saves(list);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return entity;
    }
    
    /**
     * 
     * 〈一句话功能简述〉初始化化工企业信息 〈功能详细描述〉
     * 
     * @return EnterpriseEntity
     * @see [类、类#方法、类#成员]
     */
    public EnterpriseEntity initHGInfo() {
        // 存入机构信息
        SysOrganTypeEntity entity2 = new SysOrganTypeEntity();
        entity2.setId(C503StringUtils.createUUID());
        entity2.setCode(C503StringUtils.createUUID());
        entity2.setOrganTypeId("106001002");
        entity2.setName("盛通化工企业");
        // 物流系系统id为6
        entity2.setSysId("6");
        entity2.setPid("1000000");
        entity2.setRemove("0");
        this.sysOrganTypeDao.save(entity2);
        entity.setId(C503StringUtils.createUUID());
        entity.setAddress("成都");
        entity.setCorporateNo("1111");
        entity.setEnterpriseName("嘉兴");
        entity.setRegistrationNo("2222");
        entity.setRemove("0");
        entity.setTaxationNo("3333");
        entity.setAddress("成都");
        entity.setCorporateNo(entity2.getCode());
        entity.setEnterpriseName("嘉兴");
        entity.setRegistrationNo("2222");
        entity.setRemove("0");
        entity.setTaxationNo("3333");
        entity.setEnterpriseType("106001002");
        String[] fileId = new String[fileIds.size()];
        
        entity.setFileIds(fileIds.toArray(fileId));
        try {
            this.enterpriseDao.save(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] fileIds = entity.getFileIds();
        if (null != fileIds && NumberContant.ZERO < fileIds.length) {
            List<EnterpriseFileEntity> list =
                new ArrayList<EnterpriseFileEntity>();
            this.createFileRelationVal(list, entity);
            try {
                this.enterpriseFileDao.saves(list);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return entity;
    }
    
    /**
     * 〈一句话功能简述〉创建企业信息附件关系数据 〈功能详细描述〉
     * 
     * @param entityRelaVals
     *            List<EnterpriseFileEntity>
     * @param entity
     *            EnterpriseEntity
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
    
    @Test
    public void testBatchSave()
        throws Exception {
        List<EnterpriseEntity> list = new ArrayList<EnterpriseEntity>();
        EnterpriseEntity entityOut = null;
        for (int i = 0; i < 5; i++) {
            entityOut = new EnterpriseEntity();
            entityOut.setId(C503StringUtils.createUUID());
            entityOut.setAddress("成都" + i);
            entityOut.setCorporateNo("100" + i);
            entityOut.setEnterpriseName("嘉兴" + i);
            entityOut.setRegistrationNo("2222" + i);
            entityOut.setRemove("0");
            entityOut.setTaxationNo("3333" + i);
            entityOut.setEnterpriseType("106001002");
            list.add(entityOut);
        }
        this.enterpriseService.batchSynchronize(list);
    }
    
    @Test
    public void testSave()
        throws Exception {
        
        SysOrganTypeEntity entity2 = new SysOrganTypeEntity();
        entity2.setId(C503StringUtils.createUUID());
        entity2.setCode(C503StringUtils.createUUID());
        entity2.setOrganTypeId("106001002");
        entity2.setName("盛通物流");
        // 物流系系统id为6
        entity2.setSysId("6");
        entity2.setPid("1000000");
        entity2.setRemove("0");
        this.sysOrganTypeDao.save(entity2);
        entity.setId(C503StringUtils.createUUID());
        entity.setAddress("成都");
        entity.setCorporateNo(entity2.getOrganTypeId());
        entity.setEnterpriseName("嘉兴");
        entity.setRegistrationNo("2222");
        entity.setRemove("0");
        entity.setTaxationNo("3333");
        entity.setEnterpriseType("106001002");
        entity.setAddress("成都");
        entity.setCorporateNo(C503StringUtils.createUUID());
        
        entity.setEnterpriseName("嘉兴");
        entity.setRegistrationNo("2222");
        entity.setRemove("0");
        entity.setTaxationNo("3333");
        EnterpriseEntity entityOut = new EnterpriseEntity();
        
        // 上传文件不为空，法人代码存在
        String[] fileIDs = new String[fileIds.size()];
        fileIds.toArray(fileIDs);
        entity.setFileIds(fileIDs);
        try {
            entityOut = this.enterpriseService.save(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(entityOut.getId());
        
        // 上传文件为空
        EnterpriseEntity entityOut1 = new EnterpriseEntity();
        entity.setCorporateNo("222222220ss");
        entity.setFileIds(null);
        try {
            entityOut1 = this.enterpriseService.save(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(entityOut1.getId());
        
        // 法人代码不存在
        entity.setCorporateNo("111");
        try {
            entityOut = this.enterpriseService.save(entity);
        }
        catch (CustomException e) {
            Assert.assertTrue(e instanceof CustomException);
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testFindById()
        throws Exception {
        EnterpriseEntity enenrgyManager = new EnterpriseEntity();
        try {
            enenrgyManager = this.enterpriseService.findById(entity.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(enenrgyManager.getId());
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        map.put("corporateNo", entity.getCorporateNo());
        List<EnterpriseEntity> enterprise =
            this.enterpriseService.findByParams(map);
        Assert.assertNotNull(enterprise);
        map.put("corporateNo", null);
        List<EnterpriseEntity> enterprise2 =
            this.enterpriseService.findByParams(map);
        Assert.assertNotNull(enterprise2);
        // size等于0
        Map<String, Object> map1 = new HashMap<>();
        map1.put("remove", "0");
        map1.put("corporateNo", "546867462");
        List<EnterpriseEntity> enterprise1 =
            this.enterpriseService.findByParams(map1);
        Assert.assertNotNull(enterprise1);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        entity.setAddress("成都111");
        entity.setCorporateNo("1111222");
        entity.setEnterpriseName("嘉兴11");
        entity.setRegistrationNo("2222111");
        entity.setTaxationNo("333311");
        String[] fileId = new String[fileIds.size()];
        entity.setFileIds(fileIds.toArray(fileId));
        // 正常测试 物流企业
        this.enterpriseService.update(entity);
        // 存入化工企业信息
        EnterpriseEntity entityHG = initHGInfo();
        entityHG.setAddress("成都");
        entityHG.setCorporateNo("1111");
        entityHG.setEnterpriseName("嘉兴");
        entityHG.setRegistrationNo("2222");
        entityHG.setRemove("0");
        entityHG.setTaxationNo("3333");
        entityHG.setAddress("成都");
        entityHG.setEnterpriseName("嘉兴");
        entityHG.setRegistrationNo("2222");
        entityHG.setRemove("0");
        entityHG.setTaxationNo("3333");
        entityHG.setEnterpriseType("106001002");
        entityHG.setEnterpriseTypeFlag("106001002");
        
        // 法人代码不存在
        entity.setCorporateNo("1111222");
        try {
            this.enterpriseService.update(entity);
        }
        catch (CustomException e) {
            Assert.assertTrue(e instanceof CustomException);
            e.printStackTrace();
        }
        
        // 测试化工无权限修改：附加没变
        try {
            
            this.enterpriseService.update(entityHG);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof CustomException);
        }
        // 修改了照片具有权限：张数相同
        EnterpriseEntity entityHG2 = initHGInfo();
        String[] fileId3 = new String[fileIds.size()];
        fileIds.remove(0);
        fileIds.add("b294f8e000000000000001000000");
        entityHG2.setFileIds(fileIds.toArray(fileId3));
        this.enterpriseService.update(entityHG2);
        fileIds.remove(0);
        // 附件个数不同的情况
        // 修改了照片具有权限：张数相同
        EnterpriseEntity entityHG3 = initHGInfo();
        String[] fileId4 = new String[fileIds.size() - 1];
        fileIds.remove(0);
        entityHG2.setFileIds(fileIds.toArray(fileId4));
        this.enterpriseService.update(entityHG3);
        fileIds.remove(0);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", "1");
        map.put("updateTime", new Date());
        map.put("remove", "1");
        map.put("ids", new String[] {"1"});
        this.enterpriseService.delete(map);
    }
    
    @Test
    public void testExportExcel()
        throws Exception {
        // 有数据
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> recvMap = this.enterpriseService.exportExcel(map);
        Assert.assertNotNull(recvMap);
        
        // 无数据
        Map<String, Object> map1 = new HashMap<String, Object>();
        EnterpriseEntity testNull = new EnterpriseEntity();
        testNull.setEnterpriseType("");
        testNull.setCorporateNo("111ssssss1");
        
        this.enterpriseService.save(testNull);
        Map<String, Object> recvMap1 = this.enterpriseService.exportExcel(map1);
        Assert.assertNotNull(recvMap1);
        
    }
    
    @Test
    public void testDeleteLogisticst()
        throws Exception {
        // 有数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("remove", "0");
        this.enterpriseService.deleteLogisticst(map);
        
    }
    
    @Test
    public void testFindNameByNo()
        throws Exception {
        // 有数据
        String corporateNo = "111111";
        this.enterpriseService.findNameByNo(corporateNo);
        
    }
    
    @Test
    public void testisExist()
        throws Exception {
        // 有数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "112333");
        this.enterpriseService.isExist(map);
        
    }
    
    @Test
    public void testfindNameByCode()
        throws Exception {
        Method m =
            enterpriseServiceImplClazz.getDeclaredMethod("findNameByCode",
                String.class);
        m.setAccessible(true);
        String value = "";
        m.invoke(this.enterpriseService, value);
        value = "101";
        m.invoke(this.enterpriseService, value);
    }
    
    // @Test
    // public void testIsChange() throws Exception {
    // String[] newFileIds1 = {"1231231231","12333333"};
    // String[] oldFileIds1 = {"555555542","23674321"};
    // //长度相等--文件id不相等
    // Class<?> clazz = EnterpriseServiceImpl.class;
    // Method m = clazz.getDeclaredMethod("isChange", String[].class,
    // String[].class);
    // m.setAccessible(true);
    // try {
    // m.invoke(this.enterpriseService, newFileIds1, oldFileIds1);
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    //
    // //长度相等--文件id相等
    // String[] newFileIds3 = {"1231231231","12333333"};
    // String[] oldFileIds3 = {"12333333","111111"};
    // try {
    // m.invoke(this.enterpriseService, newFileIds3, oldFileIds3);
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // //长度不相等
    // String[] newFileIds2 = {"1231231231","12333333"};
    // String[] oldFileIds2 = {"555555542"};
    // Class<?> clazz2 = EnterpriseServiceImpl.class;
    // Method m2 = clazz2.getDeclaredMethod("isChange", String[].class,
    // String[].class);
    // m2.setAccessible(true);
    // try {
    // m.invoke(this.enterpriseService, newFileIds2, oldFileIds2);
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    //
    //
    // }
    
    private List<FileInfoEntity> singleFiles = new ArrayList<FileInfoEntity>();
    
    private List<FileInfoEntity> singleFileUpload() {
        singleFiles.clear();
        FileInfoEntity fileEntity = new FileInfoEntity();
        fileEntity.setFileName(C503StringUtils.createUUID() + "_测试后台校验.docx");
        fileEntity.setOrgFileName("测试后台校验.docx");
        fileEntity.setId(C503StringUtils.createUUID());
        fileEntity.setCreateBy("dhy");
        fileEntity.setRemove(SystemContants.ON);
        singleFiles.add(fileEntity);
        return singleFiles;
    }
    
    private List<FileInfoEntity> multiFileUpload() {
        multiList.clear();
        for (int i = 0; i < 5; i++) {
            FileInfoEntity fileEntity = new FileInfoEntity();
            fileEntity.setFileName(C503StringUtils.createUUID() + "_测试后台校验" + i
                + ".docx");
            fileEntity.setOrgFileName("测试后台校验" + i + ".docx");
            String fileId = C503StringUtils.createUUID();
            fileEntity.setId(fileId);
            fileEntity.setCreateBy("dhy");
            fileEntity.setRemove(SystemContants.ON);
            multiList.add(fileEntity);
            fileIds.add(fileId);
        }
        return multiList;
    }
    
}
