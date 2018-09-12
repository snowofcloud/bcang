/*
 * 文件名：BaseController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年6月12日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sc.c503.authclient.service.AuthFactory;
import sc.c503.authclient.service.IAuthService;
import sc.c503.authclient.service.IBizHandleService;
import sc.c503.authclient.service.impl.BizHandleServiceImpl;

import com.alibaba.fastjson.JSON;
import com.c503.sc.base.common.CommonConstants;
import com.c503.sc.base.entity.BaseEntity;
import com.c503.sc.base.entity.common.UserEntity;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.dict.service.IDictionaryService;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.orgdata.dao.ISysOrganTypeDao;
import com.c503.sc.jxwl.orgdata.service.ISysOrgTypeService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503BeanUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.NumberContant;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultMessage;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;

/**
 * 〈一句话功能简述〉 控制层基类，定义一些共有的方法，也可以是抽象方法，在各个子类中具体实现 〈功能详细描述〉
 * 
 * @author chenl
 * @version [版本号, 2015年6月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseController implements IFileUploadValidate {
    /** 多文件上传处理器实例 */
    // @Resource(name = "multipartResolver")
    private CommonsMultipartResolver commonsMultipartResolver;
    
    /** 操作文件服务的实例 */
    @Autowired
    private IFileManageService fileManageService;
    
    /** 操作数据字典的dictionaryService的实例 */
    @Resource(name = "dictionaryService")
    private IDictionaryService dictionaryService;
    
    /** 机构信息 */
    @Resource(name = "sysOrganTypeDao")
    private ISysOrganTypeDao sysOrganTypeDao;
    
    /** 机构信息 */
    @Resource(name = "sysOrgTypeService")
    private ISysOrgTypeService sysOrgTypeService;
    
    /** 企业相关信息服务 */
    // @Resource(name = "enterpriseInfoService")
    // private IEnterpriseInfoService enterpriseInfoService;
    
    /** 错误验证消息集合 */
    private Map<String, Object> validErorrs = new HashMap<String, Object>();
    
    /** 业务系统缓存接口， */
    @SuppressWarnings("unused")
    private IBizHandleService bizHandleService = new BizHandleServiceImpl();
    
    /**
     * 〈一句话功能简述〉commonsMultipartResolver
     * 〈功能详细描述〉
     * 
     * @param commonsMultipartResolver commonsMultipartResolver
     * @see [类、类#方法、类#成员]
     */
    @Autowired
    public void setCommonsMultipartResolver(
        CommonsMultipartResolver commonsMultipartResolver) {
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.commonsMultipartResolver.setMaxUploadSize(CommonConstant.MAX_FILE_UPLOAD);
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param userToken userToken
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public BizUserEntity getUser(String userToken)
        throws Exception {
        BizUserEntity user = new BizUserEntity();
        if (C503StringUtils.isNotEmpty(userToken)) {
            IAuthService authService = AuthFactory.getAuthService();
            
            UserEntity authUser = authService.getUserInfo(userToken);
            if (authUser == null) {
                System.out.println("获取登陆用户失败");
                return null;
            }
            
            // 获取roleCode，和roleCode
            
            this.copyProperties(authUser, user, "createTime", "updateTime");
            String corporateCode =
                this.sysOrgTypeService.findEnCodeByOrgId(user.getOrgId());
            
            List<String> roleCodes =
                this.sysOrgTypeService.findUserRoleByUserId(user.getId());
            
            String idCard = this.sysOrganTypeDao.findIdCard(user.getId());
            
            user.setCorporateCode(corporateCode);
            user.setRoleCodes(roleCodes);
            user.setIdCard(idCard);
        }
        return user;
    }
    
    /**
     * 〈一句话功能简述〉 从shiro的session中获得用户信息 〈功能详细描述〉
     * 
     * @return 用户信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public BizUserEntity getUser()
        throws Exception {
        return getUser(getToken());
    }
    
    /**
     * 〈一句话功能简述〉 从shiro的session中获得用户信息 〈功能详细描述〉
     * 
     * @param uerId uerId
     * @return 用户信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public List<String> getRoleById(String uerId)
        throws Exception {
        
        return this.sysOrganTypeDao.findUserRoleNameByUserId(uerId);
    }
    
    /**
     * 〈一句话功能简述〉通过code获取数据字典对象 〈功能详细描述〉
     * 
     * @param code 字典code
     * @return success :{"code":1,msg:"查询成功",data:null}
     *         exception:{"code":3,"msg":"xxx",data:null}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findDictByCode/{code}", method = RequestMethod.GET)
    @ResponseBody
    public ResultMessage findDictByCode(@PathVariable
    String code)
        throws Exception {
        String source = ResourceManager.getMessage("source");
        ResultMessage result = new ResultMessage();
        if ("1".equals(source)) {
            result.setCode(NumberContant.ONE);
            result.setData(this.dictionaryService.findDictFromCache(code));
        }
        else if ("2".equals(source)) {
            result.setCode(NumberContant.ONE);
            result.setData(this.dictionaryService.findDictFromDB(code));
        }
        else {
            result.setCode(NumberContant.TWO);
        }
        return result;
    }
    
    /**
     * 〈一句话功能简述〉获得错误信息集合 〈功能详细描述〉
     * 
     * @return 错误信息集合
     * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> getValidErorrs() {
        return validErorrs;
    }
    
    /**
     * 〈一句话功能简述〉设置错误信息集合 〈功能详细描述〉
     * 
     * @param validErorrs
     *            错误信息集合
     * @see [类、类#方法、类#成员]
     */
    public void setValidErorrs(Map<String, Object> validErorrs) {
        this.validErorrs = validErorrs;
    }
    
    /**
     * 〈一句话功能简述〉全局异常处理 〈功能详细描述〉
     * 
     * @param request 请求
     * @param response 响应
     * @param e 异常
     * @return 消息体
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("deprecation")
    @ExceptionHandler()
    public String exception(HttpServletRequest request,
        HttpServletResponse response, Exception e) {
        // 这里进行通用处理，如日志记录等...
        logger().error(0, e);
        // 文件过大异常
        if (e instanceof MaxUploadSizeExceededException) {
            return "error";
        }
        // 导出模板报错
        if (e instanceof TemplateException) {
            return "error";
        }
        // 如果是json格式的ajax请求
        String accept = request.getHeader("accept");
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (accept.indexOf("application/json") > -1
            || accept.indexOf("text/html") > -1
            || (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") > -1)) {
            // response.setStatus(500);
            response.setContentType("application/json;charset=utf-8");
            JsonGenerator jsonGenerator = null;
            OutputStream out = null;
            
            ResultMessage message = new ResultMessage();
            try {
                // 默认异常信息委系统内部错误
                String errorMsg = "Internal system error.";
                int exceptCode = NumberContant.THREE;
                if (e instanceof CustomException) {
                    
                    CustomException baseException = (CustomException) e;
                    errorMsg = baseException.getErrorMessage();
                    message.setCode(Integer.valueOf(String.valueOf(baseException.getErrorId())
                        .substring(0, 1)));
                    exceptCode = (int) baseException.getErrorId();
                }
                controlErrorLog(ControlLogModel.CONTROL_RESULT_FAIL,
                    exceptCode,
                    errorMsg).recordLog();
                
                message.setCode(NumberContant.THREE);
                message.setMsg(errorMsg);
                
                out = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                jsonGenerator =
                    mapper.getJsonFactory().createJsonGenerator(out,
                        JsonEncoding.UTF8);
                jsonGenerator.writeObject(message);
                jsonGenerator.flush();
            }
            catch (Exception e1) {
                logger().error(0, new CustomException(0, e1));
            }
            finally {
                if (jsonGenerator != null) {
                    try {
                        jsonGenerator.close();
                    }
                    catch (IOException e1) {
                        logger().error(0, e1);
                    }
                }
                
                if (out != null) {
                    try {
                        out.close();
                    }
                    catch (IOException e1) {
                        logger().error(0, e1);
                    }
                }
            }
            logger().info(0);
            
            return null;
        }
        else {
            // 如果是普通请求
            request.setAttribute("exceptionMessage", e.getMessage());
            
            logger().info(0);
            // 根据不同的异常类型可以返回不同界面
            return "error";
        }
    }
    
    /**
     * 〈一句话功能简述〉多文件上传接口
     * 〈功能详细描述〉（注意：）这儿不是真正的文件上传，只是在上传到文件服务器上之前读取所有的文件信息
     * 
     * @param validate 文件验证接口
     * @param request http请求
     * @return 返回读取上传文件的所有信息对象集合
     * @throws Exception 系统异常
     * @see [类#方法]
     */
    public Object uploadFiles(IFileUploadValidate validate,
        HttpServletRequest request)
        throws Exception {
        List<FileInfoEntity> lists = new ArrayList<FileInfoEntity>();
        
        // 判断是否有上传文件请求
        if (this.commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest =
                (MultipartHttpServletRequest) request;
            
            Map<String, List<MultipartFile>> mapList =
                multiRequest.getMultiFileMap();
            if (mapList.size() > NumberContant.FIVE) {
                // 文件数量超过5个。
                String msg = ResourceManager.getMessage("fileNum");
                validErorrs.put("fileNum", msg);
                return validErorrs;
            }
            for (Map.Entry<String, List<MultipartFile>> m : mapList.entrySet()) {
                Iterator<MultipartFile> mulIt = m.getValue().iterator();
                while (mulIt.hasNext()) {
                    // 获取上传的文件
                    MultipartFile file = mulIt.next();
                    // if (!file.isEmpty()) {
                    boolean isLegal = validate.validFile(file, validErorrs);
                    if (isLegal) {
                        return validErorrs;
                    }
                    // 获取上传文件的原始文件名
                    String originalFilename = file.getOriginalFilename();
                    // String newFileName =
                    // C503StringUtils.createUUID() + "_" + originalFilename;
                    
                    FileInfoEntity fileInfoEntity = new FileInfoEntity();
                    fileInfoEntity.setFile(file);
                    fileInfoEntity.setOrgFileName(originalFilename);
                    fileInfoEntity.setFileName(originalFilename);
                    fileInfoEntity.setFilePath(null);
                    fileInfoEntity.setFileSize(file.getSize());
                    BizUserEntity user = this.getUser();
                    fileInfoEntity.setCreateBy(user.getId());
                    fileInfoEntity.setCreateTime(new Date());
                    fileInfoEntity.setRemove(SystemContants.ON);
                    fileInfoEntity.setUpdateBy(user.getId());
                    fileInfoEntity.setUpdateTime(new Date());
                    lists.add(fileInfoEntity);
                    // } else {
                    // // 文件为0字节。
                    // String msg = ResourceManager.getMessage("emptyFile");
                    // validErorrs.put("emptyFile", msg);
                    // return validErorrs;
                    // }
                }
            }
        }
        return lists;
    }
    
    /**
     * 〈一句话功能简述〉单文件下载 〈功能详细描述〉
     * 
     * @param request http请求
     * @param response http响应
     * @param id 文件id
     * @throws Exception 自定义异常 300910000：文件找不到 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/downloadFile/{id}", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request,
        HttpServletResponse response, @PathVariable
        String id)
        throws Exception {
        Map<String, Object> stream =
            this.fileManageService.returnOutputStream(id);
        String orgFileName = (String) stream.get("orgFileName");
        byte[] byt = (byte[]) stream.get("byte");
        ServletOutputStream out = response.getOutputStream();
        orgFileName = this.compatibilityFileName(request, orgFileName, "");
        // 设置响应文件名
        response.setHeader("Content-Disposition", "attachment;filename="
            + orgFileName);
        // 设置响应文本类型
        response.setContentType("application;charset=UTF-8");
        out.write(byt);
        response.flushBuffer();
    }
    
    /**
     * 〈一句话功能简述〉根据文件类型码下载模板 〈功能详细描述〉
     * 
     * @param request http请求
     * @param response http响应
     * @param fileCode fileCode
     * @throws Exception 自定义异常 300910000：文件找不到 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/downloadTemplate/{fileCode}", method = RequestMethod.GET)
    public void downloadTemplateFile(HttpServletRequest request,
        HttpServletResponse response, @PathVariable
        String fileCode)
        throws Exception {
        Map<String, Object> stream =
            this.fileManageService.returnOutputStreamByFileCode(fileCode);
        String orgFileName = (String) stream.get("orgFileName");
        byte[] byt = (byte[]) stream.get("byte");
        ServletOutputStream out = response.getOutputStream();
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            orgFileName =
                URLEncoder.encode(orgFileName.replace(" ", ""), "UTF-8");
        }
        else {
            orgFileName =
                new String(orgFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        // 设置响应文件名
        response.setHeader("Content-Disposition", "attachment;filename="
            + orgFileName);
        // 设置响应文本类型
        response.setContentType("application;charset=UTF-8");
        out.write(byt);
        response.flushBuffer();
    }
    
    /**
     * 〈一句话功能简述〉多文件打包下载 〈功能详细描述〉
     * 
     * @param request http请求
     * @param response http响应
     * @param ids 可变参数
     * @return responseEntity
     * @see [类#方法]
     */
    @RequestMapping(value = "/downloadFilePackage", method = RequestMethod.POST)
    public Object downloadFilePackage(HttpServletRequest request,
        HttpServletResponse response, String... ids) {
        ResponseEntity<byte[]> responseEntity = null;
        File file = null;
        
        try {
            file = this.fileManageService.findFileFromFastdfs(ids);
            byte[] cache = FileUtils.readFileToByteArray(file);
            com.c503.sc.filemanage.utils.C503FileUtils.delete(file.getPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity =
                new ResponseEntity<byte[]>(cache, headers, HttpStatus.CREATED);
        }
        catch (Exception e) {
            logger().error(0, e);
        }
        return responseEntity;
    }
    
    /**
     * 〈一句话功能简述〉 图片预览 〈功能详细描述〉
     * 
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @param id
     *            文件id
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/imagePreview", method = RequestMethod.GET)
    public void imagePreview(HttpServletRequest request,
        HttpServletResponse response, String id)
        throws Exception {
        Map<String, Object> stream =
            this.fileManageService.returnOutputStream(id);
        byte[] byt = (byte[]) stream.get("byte");
        // 设置响应文件名
        response.setHeader("Content-Type", "image/jpg");
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application;charset=gb2312");
        out.write(byt);
        response.flushBuffer();
    }
    
    /**
     * 〈一句话功能简述〉 文件删除〈功能详细描述〉
     * 
     * @param ids 文件id数组
     * @return true：删除成功
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage deleteFile(String... ids)
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        map.put("updateBy", this.getUser().getUpdateBy());
        ResultMessage result = new ResultMessage();
        boolean isDel = false;
        if (ids.length > 0) {
            isDel = this.fileManageService.deleteFileAndInfo(map);
        }
        int code = CommonConstant.DELETE_FAIL_OPTION;
        if (isDel) {
            code = CommonConstant.DELETE_SUC_OPTION;
        }
        result.setCode(Integer.valueOf(String.valueOf(code).substring(0, 1)));
        String msg = ResourceManager.getMessage(String.valueOf(code));
        result.setMsg(msg);
        return result;
    }
    
    /**
     * 〈一句话功能简述〉获取service接口 〈功能详细描述〉
     * 
     * @param <T> 泛型参数
     * 
     * @return 该接口的实现类
     * @see [类、类#方法、类#成员]
     */
    protected abstract <T> IBaseService<T> getBaseService();
    
    /**
     * 〈一句话功能简述〉 显示页面接口 〈功能详细描述〉
     * 
     * @return 页面对象
     * @see [类、类#方法、类#成员]
     */
    protected abstract Object show();
    
    /**
     * 〈一句话功能简述〉获取子类日志管理对象 〈功能详细描述〉
     * 
     * @return 日志管理对象
     * @see [类、类#方法、类#成员]
     */
    protected abstract LoggingManager logger();
    
    /**
     * 〈一句话功能简述〉得到request对象 〈功能详细描述〉
     * 
     * @return request对象
     * @see [类、类#方法、类#成员]
     */
    protected static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (null != servletRequestAttributes) {
            request = servletRequestAttributes.getRequest();
        }
        return request;
    }
    
    /**
     * 
     * 〈一句话功能简述〉记录操作日志 〈功能详细描述〉
     * 
     * @param resultCode 是否操作成功
     * @param msgCode 消息码
     * @param args 消息码参数
     * @return 操作日志对象
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    protected ControlLogModel controlLog(int resultCode, int msgCode,
        Object... args)
        throws Exception {
        BizUserEntity user = getUser();
        String msg =
            ResourceManager.getMessage(String.valueOf(msgCode)) + ":"
                + JSON.toJSONString(args);
        ControlLogModel log =
            new ControlLogModel(null, user.getName(), resultCode, logger());
        log.setControlMessage(msg);
        return log;
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉记录操作日志 〈功能详细描述〉
     * 
     * @param resultCode 是否操作成功
     * @param msgCode 消息码
     * @param args 消息码参数
     * @return 操作日志对象
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    protected ControlLogModel controlErrorLog(int resultCode, int msgCode,
        Object... args)
        throws Exception {
        BizUserEntity user = getUser();
        String msg = JSON.toJSONString(args);
        ControlLogModel log =
            new ControlLogModel(null, user.getName(), resultCode, logger());
        log.setControlMessage(msg);
        return log;
    }
    
    /**
     * 〈一句话功能简述〉封装表单验证错误信息 〈功能详细描述〉主要存储表单错误信息
     * 
     * @param errors Spring绑定错误接口
     * @return true:表单验证不通过;false:表单验证通过
     * @see [类、类#方法、类#成员]
     */
    protected boolean addErorrs(Errors errors) {
        if (errors.hasErrors()) {
            List<ObjectError> all = errors.getAllErrors();
            int length = all.size();
            for (int i = 0; i < length; i++) {
                ObjectError obj = all.get(i);
                String fieldName = obj.getCode();
                String errorMsg = obj.getDefaultMessage();
                if (obj instanceof FieldError) {
                    FieldError fe = (FieldError) obj;
                    fieldName = fe.getField();
                    errorMsg = fe.getDefaultMessage();
                }
                validErorrs.put(fieldName, errorMsg);
            }
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 复制 source 的值给 target 对象，如果 target 对象里面有对应的值的话 〈功能详细描述〉
     * 
     * @param source 源对象
     * @param target 目标对象
     * @param filter 需过滤字段
     * @throws Exception 运行异常
     * @see [类、类#方法、类#成员]
     */
    protected void copyProperties(Object source, Object target,
        String... filter)
        throws Exception {
        C503BeanUtils.copyBeanProperties(source, target, filter);
    }
    
    /**
     * 〈一句话功能简述〉获取token 后期用filter拦截器实现 〈功能详细描述〉
     * 
     * @return 响应消息
     * @see [类、类#方法、类#成员]
     */
//     public static String getToken() {
//    
//     String token = null;
//     // 退出之前或跳转之前先获取cookie 后续用filter拦截器实现
//     Cookie[] cookies =
//     getRequest() == null ? null : getRequest().getCookies();
//     if (null != cookies) {
//     String tokenName =
//     com.c503.sc.base.common.CommonConstants.COOKIE_TOKEN;
//     for (Cookie cookie : cookies) {
//     if (C503StringUtils.equals(tokenName, cookie.getName())) {
//     token = cookie.getValue();
//     break;
//     }
//     }
//     }
//     return token;
//     }
    
    /**
     * 〈一句话功能简述〉获取token 后期用filter拦截器实现 〈功能详细描述〉
     * 
     * @return 响应消息
     * @see [类、类#方法、类#成员]
     */
    public static String getToken() {
        return getToken(getRequest());
    }
    
    /**
     * 〈一句话功能简述〉获取token 〈功能详细描述〉
     * 
     * @param request HttpServletRequest
     * @return token
     * @see [类、类#方法、类#成员]
     */
    public static String getToken(HttpServletRequest request) {
        String token = null;
        
        Object obj = request.getSession().getAttribute("tokenTemp");
        if (obj instanceof String) {
            String str = (String) obj;
            if (!C503StringUtils.isEmpty(str)) {
                // 第一次登录cookie里面没有，通过request缓存获取
                return str;
            }
        }
        
        return token;
    }
    
    /**
     * 〈一句话功能简述〉设置cookie 〈功能详细描述〉
     * 
     * @param response
     *            响应对象
     * @param userToken
     *            用户令牌
     * @see [类、类#方法、类#成员]
     */
    public static void setCookie(HttpServletResponse response, String userToken) {
        // 3.1将token放cooker.
        Cookie cookie = new Cookie(CommonConstants.COOKIE_TOKEN, userToken);
        // 设置cookie时间周期为1小时
        String outTime =
            ResourceManager.getMessage(String.valueOf(SysCommonConstant.COOKIE_TIME));
        cookie.setMaxAge(Integer.parseInt(outTime));
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean validFile(MultipartFile file, Map<String, Object> map) {
        // file.getSize()>50M
        boolean isValid = false;
        if (file.getSize() > NumberContant.FIFTY_MILLION) {
            // 文件的大小 不对。
            String msg = ResourceManager.getMessage("common.fileType");
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
                || ".ppt".equalsIgnoreCase(fileType)
                || ".png".equalsIgnoreCase(fileType)
                || ".jpg".equalsIgnoreCase(fileType)
                || ".bmp".equalsIgnoreCase(fileType)
                || ".jpeg".equalsIgnoreCase(fileType);
        if (!typeControl) {
            // 文件的类型 格式不对。
            String msg = ResourceManager.getMessage("common.fileType");
            map.put("fileType", msg);
            isValid = true;
        }
        
        return isValid;
    }
    
    /**
     * 〈一句话功能简述〉创建ID,createBy,updateBy,createTime,updateTime值
     * 〈功能详细描述〉
     * 
     * @param e BaseEntity
     * @param saveOrUpdate true:保存、false:修改
     * @param userId userId
     * @see [类、类#方法、类#成员]
     */
    public void createCommonVal(BaseEntity e, Boolean saveOrUpdate,
        String userId) {
        Date curDate = new Date();
        e.setUpdateBy(userId);
        e.setUpdateTime(curDate);
        if (saveOrUpdate) {
            e.setId(C503StringUtils.createUUID());
            e.setCreateBy(userId);
            e.setCreateTime(curDate);
            e.setRemove(SystemContants.ON);
        }
    }
    
    /**
     * 〈一句话功能简述〉过滤html、JavaScript、sql注入 〈功能详细描述〉
     * 
     * @param binder
     *            WebDataBinder
     * @see [类、类#方法、类#成员]
     */
    // @InitBinder
    // public void initBinder(WebDataBinder binder) {
    // binder.registerCustomEditor(String.class, new XssEscapeEditorSupport(
    // true, false, false));
    // }
    /**
     * 〈一句话功能简述〉兼容文件名
     * 
     * @param request
     * @param String 文件名
     * @param String 后缀
     * @return String 文件名
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    protected String compatibilityFileName(HttpServletRequest request,
        String pfileName, String postfix)
        throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");
        String fileName = null;
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {
                // 火狐浏览器
                fileName =
                    "=?UTF-8?B?"
                        + (new String(
                            org.apache.commons.codec.binary.Base64.encodeBase64(pfileName.getBytes("UTF-8"))))
                        + "?=" + postfix;
            }
            else if (-1 != agent.indexOf("Chrome")) {
                // Chrome浏览器
                fileName =
                    new String(pfileName.getBytes(), "ISO-8859-1") + postfix;
            }
            else {
                // ie
                fileName = URLEncoder.encode(pfileName, "UTF8") + postfix;
            }
        }
        else {
            fileName = pfileName;
        }
        return fileName;
    }
}
