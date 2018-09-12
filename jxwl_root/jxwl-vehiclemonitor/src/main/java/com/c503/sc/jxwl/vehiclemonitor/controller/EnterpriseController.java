/**
 * 文件名：EnterpriseController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.IFileUploadValidate;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.formbean.EnterpriseFrom;
import com.c503.sc.jxwl.vehiclemonitor.service.IEnterpriseService;
import com.c503.sc.jxwl.vehiclemonitor.vo.EnterpriseForAPPVo;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503BeanUtils;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.response.ResultJQGrid;
import com.zx.exch.core.ws.AutoWebService;
import com.zx.framework.util.json.DealJSON;
import com.zx.framework.util.sercurity.BaseCodeUtil;

/**
 * 
 * 〈一句话功能简述〉物流企业信息Controller
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/logisticst")
public class EnterpriseController extends ResultController implements
    IFileUploadValidate {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EnterpriseController.class);
    
    /** 物流企业信息业务接口 */
    @Resource
    private IEnterpriseService enterpriseService;
    
    /** 附件接口 */
    @Autowired
    private IFileManageService fileManageService;
    
    /** 同步企业信息接口地址地址 */
    private String enterpriseUrl;
    
    /** 接口命名空间 */
    private String enterpriseQnme;
    
    /** 接口名称 */
    private String serviceName;
    
    /** 读取propertis文件对象 */
    private Properties propertis = new Properties();
    
    /** 物流企业信息Dao */
    // private IEnterpriseDao enterpriseDao;
    
    /**
     * 
     * 〈一句话功能简述〉 构造方法 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public EnterpriseController() {
        
        try {
            InputStream synchronizeIn =
                EnterpriseController.class.getResourceAsStream("/synchronize.properties");
            propertis.load(synchronizeIn);
            enterpriseUrl = propertis.getProperty("enterprise.url");
            enterpriseQnme = propertis.getProperty("enterprise.name");
            serviceName = propertis.getProperty("enterprise.service");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉同步企业信息
     * 〈功能详细描述〉
     * 
     * @param request request
     * @return String String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/synchronize", method = RequestMethod.GET)
    public String synchronize(HttpServletRequest request)
        throws Exception {
        // 返回数据企业数据信息array集合
        String data = null;
        
        try {
            // 请求服务路径，根据实际情况修改
            // 外网
            URL url = new URL(enterpriseUrl); // 请求服务路径，根据实际情况修改
            // 内网
            // URL url = new URL("http://172.31.2.195:5004/nyqyxxjk?wsdl");
            
            QName qnme = new QName(enterpriseQnme, serviceName); // ws.core.exch.zx.com/
            Service service = Service.create(url, qnme);
            AutoWebService port = service.getPort(AutoWebService.class);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            // 授权的节点账号密码，如有授权必须填写
            paramMap.put("nodeuser", "1");
            paramMap.put("nodepwd", "1");
            // 连接服务参数
            paramMap.put("usercode", "1"); // 可写,如果服务配置了口令，那么这里必须要对应
            paramMap.put("userpwd", "1"); // 可写
            paramMap.put("issecrity", "1"); // 是否加密传输
            
            // 条件参数（根据服务数据等标准）有两种方法，封装成List或Map。根据服务配置更改，服务的数据标准配置为Map则封装为Map，反之为List.
            // 方法一：把参数封装到Map,支持多个数据标准集合的参数
            Map<String, Object> setMap = new HashMap<String, Object>();
            Map<String, Object> param = new HashMap<String, Object>();
            // param.put("sensor_id", "");
            // param.put("name", "张三");
            // param.put("dept", "工商局");
            // param.put("sex", "男");
            // 把参数放进paramMap,key为数据标准的名称
            //param.put("year", "");
            param.put("etime", "");
            setMap.put("page", param);
            
            // 方法二：把参数封装到List,支持多个数据标准集合的参数
            /*
             * List<Map<String,Object>> datas = new
             * ArrayList<Map<String,Object>>();
             * Map<String,Object> data = new HashMap<String, Object>();
             * data.put("id", "20151218100413130869");
             * datas.add(data);
             * setMap.put("params", datas);
             */
            
            String setStr = DealJSON.encodeObject2Json(setMap);
            if ("1".equals(paramMap.get("issecrity"))) {
                try {
                    setStr = BaseCodeUtil.encrypt(setStr);
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            paramMap.put("data", setStr);
            String jsonStr = DealJSON.encodeObject2Json(paramMap);
            // 得到数据集合
            System.out.println(new Date());
            data = port.execute(jsonStr);
            String aString =
                data.substring(NumberContant.TEN, data.length() - 1);
            List<Map<String, String>> list = JsonUtil.jsonToList(aString);
            
            // 已经存在的用户不在同步 start
            Map<String, Object> map = new HashMap<String, Object>();
            // 同步标志 “0”代表为从其他系统同步的用户数据
            map.put("flag", SystemContants.ON);
            map.put("remove", SystemContants.ON);
            List<EnterpriseEntity> listPre =
                this.enterpriseService.findAllName(map);
            if (null != listPre) {
                for (EnterpriseEntity entity : listPre) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i)
                            .get("enterprise_name")
                            .equals(entity.getName())) {
                            list.remove(i);
                        }
                    }
                }
            }
            // 已经存在的用户不在同步 end
            
            List<EnterpriseEntity> enterprises =
                new ArrayList<EnterpriseEntity>();
            // String code = null;
            for (int i = 0; i < list.size(); i++) {
                EnterpriseEntity enterprise = new EnterpriseEntity();
                enterprise.setId(C503StringUtils.createUUID());
                enterprise.setRegistrationNo(list.get(i).get("legal_repr_code"));
                enterprise.setAddress(list.get(i).get("reg_address"));
                enterprise.setProfessionalWork(list.get(i)
                    .get("business_scope"));
                // enterprise.setCorporateCode(list.get(i).get("org_code"));
                enterprise.setEnterpriseName(list.get(i).get("enterprise_name"));
                enterprise.setTelephone(list.get(i).get("contact_tel"));
                enterprise.setCreateBy(this.getUser().getId());
                enterprise.setCreateTime(new Date());
                enterprise.setUpdateBy(this.getUser().getId());
                enterprise.setUpdateTime(new Date());
                enterprise.setRemove(SystemContants.ON);
                // BizUserEntity userList = user.bean2auth(user);
                enterprises.add(enterprise);
            }
            
            if (enterprises.size() > 0) {
                this.enterpriseService.batchSynchronize(enterprises);
            }
            System.out.println(enterprises);
            System.out.println(new Date());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        return data;
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉保存物流企业信息
     * 
     * @param form 实体
     * @param bindingResult 表单验证错误集合
     * @return object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@Validated(value = Save.class)
    EnterpriseFrom form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
            // 记录操作失败信息（存入文件）
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
            // 保存操作日志 记录操作失败（存入数据库）
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                getValidErorrs()).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            // 2、实体赋值
            EnterpriseEntity entity = new EnterpriseEntity();
            this.copyProperties(form, entity);
            entity.setId(C503StringUtils.createUUID());
            entity.setCreateBy(this.getUser().getId());
            entity.setCreateTime(new Date());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            // if
            // (StringUtils.isNotEmpty(this.enterpriseDao.findCorporateNoHasExist(entity.getCorporateNo(),
            // null))) {
            // throw new
            // CustomException(BizExConstants.ENTERPRISE_CONRPORATREXIST);
            // }
            // 3、调用接口信息
            if (isSame()) {
                this.enterpriseService.save(entity);
                
            }
            else {
                throw new CustomException(
                    BizConstants.ENTERPRISE_ADD_ENTERPRISE_IF_NOT_ALLOW, entity);
            }
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            /*
             * controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
             * CommonConstant.SAVE_SUC_OPTION,
             * entity).recordLog();
             */
            sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否有权限删除或者新增或者编辑（新增只有政府用户才有权限）
     * 〈功能详细描述〉
     * 
     * @return 该用户是否是政府用户
     * @see [类、类#方法、类#成员]
     */
    private boolean isSame() {
        
        boolean isRole = false;
        List<String> userRoles = new ArrayList<String>();
        try {
            userRoles = this.getUser().getRoleCodes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int j = userRoles.size();
        for (int i = 0; i < j; i++) {
            if (DictConstant.GOVERNMENT_USER.equals(userRoles.get(i))) {
                isRole = true;
                break;
            }
            
        }
        return isRole;
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉修改数据信息
     * 
     * @param id id
     * @param form 实体信息
     * @param bindingResult 错误集合
     * @return object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable
    String id, @Validated(value = Update.class)
    EnterpriseFrom form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        // 权限判断
        // this.enterpriseEx();
        boolean isResult = this.addErorrs(bindingResult);
        // TODO 临时代码，解决化工企业餐所校验失败的问题
        if (isResult) {
            EnterpriseEntity entityFind = this.enterpriseService.findById(id);
            // 化工企业暂时不校验
            if (null != entityFind
                && DictConstant.ENTERPRISE_TYPE_CHEMICAL.equals(entityFind.getEnterpriseTypeFlag())) {
                isResult = false;
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        String corporateNo = this.enterpriseService.isExist(map);
        if (corporateNo == null) {
            throw new CustomException(BizExConstants.COMPANYDELETED);
        }
        // 1、后台表单验证
        if (isResult) {
            sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
            // 记录操作失败信息（存入文件）
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
            // 保存操作日志 记录操作失败（存入数据库）
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                getValidErorrs()).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            
            // 2、实体赋值
            EnterpriseEntity entity = new EnterpriseEntity();
            this.copyProperties(form, entity);
            entity.setId(id);
            BizUserEntity uer = this.getUser();
            entity.setUpdateBy(uer.getId());
            entity.setUpdateTime(new Date());
            // 判断法人代码是否存在
            // if
            // (StringUtils.isNotEmpty(this.enterpriseDao.findCorporateNoHasExist(entity.getCorporateNo(),
            // entity.getId()))) {
            // throw new
            // CustomException(BizExConstants.ENTERPRISE_CONRPORATREXIST);
            // }
            // 3、调用接口
            this.enterpriseService.update(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
            sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询企业信息
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @param enterpriseType enterpriseType
     * @param page page
     * @param rows rows
     * @param isPC isPC
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String enterpriseName, String enterpriseType,
        String isPC, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        enterpriseName = StringUtils.trim(enterpriseName);
        map.put("searchKey", StringUtils.isEmpty(enterpriseName) ? null : "%"
            + enterpriseName + "%");
        map.put("remove", SystemContants.ON);
        map.put("enterpriseType", enterpriseType);
        map.put("isPC", isPC);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        if (isSame()) {
            // 政府用户查询所有，
            map.put("corporateNo", null);
        }
        else if (StringUtils.isNotEmpty(enterpriseType)) {
            map.put("corporateNo", null);
        }
        else {
            // 企业用户查询本企业
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        List<EnterpriseEntity> list = null;
        // 2、接口调用
        try {
            list = this.enterpriseService.findByParams(map);
            
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询所有企业信息
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAllEnterprise", method = {RequestMethod.GET,
        RequestMethod.POST})
    @ResponseBody
    public Object findAllEnterprise(String enterpriseName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        enterpriseName = StringUtils.trim(enterpriseName);
        map.put("searchKey", StringUtils.isEmpty(enterpriseName) ? null : "%"
            + enterpriseName + "%");
        map.put("remove", SystemContants.ON);
        
        /*
         * if (isSame()) {
         * // 政府用户查询所有，
         * map.put("corporateNo", null);
         * }
         * else {
         * // 企业用户查询本企业
         * map.put("corporateNo", this.getUser().getCorporateCode());
         * }
         */
        
        List<EnterpriseEntity> list = null;
        // 2、接口调用
        try {
            list = this.enterpriseService.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询所有企业信息
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAll", method = {RequestMethod.GET,
        RequestMethod.POST})
    @ResponseBody
    public Object findAll()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("remove", SystemContants.ON);
        List<EnterpriseEntity> list = null;
        // 2、接口调用
        try {
            list = this.enterpriseService.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        List<EnterpriseForAPPVo> items = new ArrayList<EnterpriseForAPPVo>();
        int itemsSize = list.size();
        for (int j = 0; j < itemsSize; j++) {
            EnterpriseForAPPVo dict = new EnterpriseForAPPVo();
            C503BeanUtils.copyProperties(dict, list.get(j));
            items.add(dict);
        }
        // 3、数据返回
        this.sendData(items, CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询企业信息 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("id", id);
            String corporateNo = this.enterpriseService.isExist(map1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            if (corporateNo == null) {
                throw new CustomException(BizExConstants.COMPANYDELETED);
            }
            EnterpriseEntity val = this.enterpriseService.findById(id);
            
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询企业信息 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findNameByNo", method = RequestMethod.GET)
    @ResponseBody
    public Object findNameByNo(String corporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(corporateNo)) {
            String val = this.enterpriseService.findNameByNo(corporateNo);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            String no = this.getUser().getCorporateCode();
            String val = this.enterpriseService.findNameByNo(no);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除企业信息
     * 〈功能详细描述〉
     * 
     * @param id 企业id
     * @return 响应到前台的数据
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/deleteLogisticst/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteLogisticst(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1.参数校验
        if (C503StringUtils.isNotEmpty(id)) {
            // 2.权限校验
            if (isSame()) {
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("id", id);
                String corporateNo = this.enterpriseService.isExist(map1);
                if (corporateNo == null) {
                    throw new CustomException(BizExConstants.COMPANYDELETED);
                }
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", id);
                // 判断被删除企业的类型
                EnterpriseEntity val = this.enterpriseService.findById(id);
                // 注释：测试提出政府用户需要对化工企业进行删除操作
                /*if (val.getEnterpriseTypeFlag()
                    .equals(DictConstant.ENTERPRISE_TYPE_CHEMICAL)) {
                    throw new CustomException(BizConstants.DELETE_NOT_ALLOW, id);
                }*/
                map.put("updateBy", this.getUser().getId());
                map.put("updateTime", new Date());
                map.put("conporateNo", this.getUser().getCorporateCode());
                // 被删除的物流企业的法人代码
                map.put("wlConporateNo", val.getCorporateNo());
                // 如果用户为政府即conporateNo为空可以进行删除操作，如果不为空即化工企业无删除权限
                if (this.getUser().getCorporateCode() != null) {
                        throw new CustomException(BizConstants.DELETE_NOT_ALLOW, id);
                }
                int line = this.enterpriseService.deleteLogisticst(map);
                
                LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
                // 保存操作日志 记录操作成功
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.DELETE_SUC_OPTION,
                    map).recordLog();
                sendCode(CommonConstant.DELETE_SUC_OPTION);
            }
            else {
                throw new CustomException(BizConstants.GOVERNMENT_DELETE_ALLOW,
                    id);
            }
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉导出企业信息
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @param response 响应对象
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void exportExcel(String enterpriseName, HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, enterpriseName);
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.isNotEmpty(enterpriseName)) {
            try {
                enterpriseName = URLDecoder.decode(enterpriseName, "UTF-8");
                enterpriseName =
                    new String(enterpriseName.getBytes("ISO-8859-1"), "UTF-8");
            }
            catch (Exception e) {
                LOGGER.info(NumberContant.THREE,
                    "decode enterpriseName exception");
            }
        }
        map.put("searchKey", enterpriseName);
        map.put("remove", SystemContants.ON);
        
        if (this.getUser()
            .getRoleCodes()
            .contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", null);
        }
        else {
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        Map<String, Object> recvMap = new HashMap<String, Object>();
        recvMap = this.enterpriseService.exportExcel(map);
        
        ExportSheet sheet = (ExportSheet) recvMap.get("sheet");
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        // 设置响应文本类型
        response.setContentType("application;charset=gb2312");
        // 设置响应文件名
        String excelName = (String) recvMap.get("excelName");
        response.setHeader("Content-Disposition", "attachment;filename="
            + compatibilityFileName(request, excelName,".xls"));
        /*new String(excelName.getBytes("gb2312"), "ISO-8859-1") + ".xls"*/
        
        excelUtils.writeToStream(out);
        out.close();
        response.flushBuffer();
        // 记录操作成功信息
        LOGGER.info(CommonConstant.EXPORT_SUC_OPTION, recvMap);
        // 保存操作日志 记录操作成功
        controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
            CommonConstant.EXPORT_SUC_OPTION,
            recvMap).recordLog();
        LOGGER.debug(SystemContants.DEBUG_END, enterpriseName);
    }
    
    /**
     * 〈一句话功能简述〉上传附件
     * 〈功能详细描述〉
     * 
     * @param request request
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public String uploadFile(MultipartHttpServletRequest request)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START);
        
        // enterpriseEx();
        
        Object obj = this.uploadFiles(this.getFileUploadService(), request);
        if (obj instanceof HashMap) {
            sendData(obj, CommonConstant.FORMVALID_FAIL_OPTION);
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, obj);
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                obj).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            List<FileInfoEntity> fileInfoes = (List<FileInfoEntity>) obj;
            JSONObject json = new JSONObject();
            List<String> ids = new ArrayList<String>();
            List<String> names = new ArrayList<String>();
            
            if (0 < fileInfoes.size()) {
                for (FileInfoEntity fie : fileInfoes) {
                    FileInfoEntity refie =
                        this.fileManageService.saveFileAndInfo(fie);
                    ids.add(refie.getId());
                    names.add(fie.getOrgFileName());
                }
                json.put("ids", ids);
                json.put("names", names);
            }
            // 设置响应消息
            this.sendData(json, CommonConstant.UPLOAD_SUC_OPTION);
            // 记录操作成功信息
            LOGGER.info(CommonConstant.UPLOAD_SUC_OPTION, json);
            // 保存操作日志 记录操作成功
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPLOAD_SUC_OPTION,
                json).recordLog();
            LOGGER.debug(SystemContants.DEBUG_END);
        }
        
//        return this.sendMessage();
        return JSON.toJSONString(this.sendMessage());

    }
    
    /** {@inheritDoc} */
    @Override
    public boolean validFile(MultipartFile file, Map<String, Object> map) {
        // file.getSize()>50M
        boolean isValid = false;
        if (file.getSize() > NumberContant.FIFTY_MILLION) {
            // 文件的大小 不对。
            String msg =
                ResourceManager.getMessage("accidentProgressForm.fileSize");
            map.put("fileSize", msg);
            isValid = true;
        }
        
        String fileName = file.getOriginalFilename();
        String fileType =
            fileName.substring(fileName.lastIndexOf("."), fileName.length());
        boolean typeControl =
            ".docx".equalsIgnoreCase(fileType)
                || ".doc".equalsIgnoreCase(fileType) || ".pdf".equals(fileType)
                || ".xlsx".equalsIgnoreCase(fileType)
                || ".xls".equalsIgnoreCase(fileType)
                || ".png".equalsIgnoreCase(fileType)
                || ".jpg".equalsIgnoreCase(fileType)
                || ".bmp".equalsIgnoreCase(fileType)
                || ".jpeg".equalsIgnoreCase(fileType);
        if (!typeControl) {
            // 文件的类型 格式不对。
            String msg =
                ResourceManager.getMessage("accidentProgressForm.fileType");
            map.put("fileType", msg);
            isValid = true;
        }
        // int fileNameSize =
        // fileName.substring(0, fileName.lastIndexOf(".") - 1).length();
        // if (fileNameSize > NumberContant.THIRTY) {
        // String msg = ResourceManager.getMessage("caseForm.fileNameSize");
        // map.put("fileNameSize", msg);
        // isValid = true;
        // }
        
        return isValid;
    }
    
    /**
     * 〈一句话功能简述〉实现文件上传验证的接口 〈功能详细描述〉
     * 
     * @return 当前对象作为实现类
     * @see [类、类#方法、类#成员]
     */
    private IFileUploadValidate getFileUploadService() {
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.enterpriseService;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
}
