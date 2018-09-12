/**
 * 文件名：enterpriseLicence.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.formbean.LicenceForm;
import com.c503.sc.jxwl.transacation.service.ILicenceWarnService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/licenceWarn")
public class LicenceWarnController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LicenceWarnController.class);
    
    /** 企业资质业务接口 */
    @Resource(name = "licenceWarnService")
    private ILicenceWarnService licenceWarnService;
    
    /**
     * 〈一句话功能简述〉分页查询企业资质入口
     * 〈功能详细描述〉
     * 
     * @param page page
     * @param rows rows
     * @param enterpriseName enterpriseName
     * @param carNo carNo
     * @param licenceWarnType licenceWarnType
     * @param expiredStatus expiredStatus
     * @param personName personName
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = {RequestMethod.POST,
        RequestMethod.GET})
    @ResponseBody
    public Object findByPage(Integer page, Integer rows, String enterpriseName,
        String carNo, String licenceWarnType, String expiredStatus,
        String personName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 封装数据
        Map<String, Object> map = new HashMap<>();
        enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
        licenceWarnType =
            licenceWarnType == null ? null : licenceWarnType.trim();
        expiredStatus = expiredStatus == null ? null : expiredStatus.trim();
        carNo = carNo == null ? null : carNo.trim();
        personName = personName == null ? null : personName.trim();
        map.put("enterpriseName", enterpriseName);
        map.put("carNo", carNo);
        map.put("licenceWarnType", licenceWarnType);
        map.put("expiredStatus", expiredStatus);
        map.put("personName", personName);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        List<String> roleList = new ArrayList<String>();
        BizUserEntity user = this.getUser();
        if( null != user){
        	roleList = user.getRoleCodes();
        	
        }
        // 权限判断
        if (null != user && roleList.size() > 0 && !roleList.contains(DictConstant.GOVERNMENT_USER)) {// 非政府权限
            String code = user.getCorporateCode();
        	map.put("corporateNo", code);
        }
        List<LicenceEntity> list = this.licenceWarnService.findByParams(map);
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉保存资质信息 〈功能详细描述〉
     * 
     * @param form
     *            项目管理信息
     * @param bindingResult
     *            校验错误信息结果集
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/reUpload", method = RequestMethod.POST)
    @ResponseBody
    public Object reUpload(@Validated(value = Save.class) LicenceForm form,
        BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        } else {
            LicenceEntity entity = new LicenceEntity();
            this.copyProperties(form, entity);
            entity.setId(form.getId());
            entity.setCorporateNo(this.getUser().getCorporateCode());
            int result = this.licenceWarnService.update(entity);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
            // 保存操作日志 记录操作成功
            this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.SAVE_SUC_OPTION,
                result).recordLog();
            
            this.sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉保存资质信息 〈功能详细描述〉
     * 
     * @param licenceWarnType licenceWarnType
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/amount", method = RequestMethod.POST)
    @ResponseBody
    public Object amount(String licenceWarnType)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, licenceWarnType);
        Map<String, Object> map = new HashMap<>();
        map.put("licenceWarnType", licenceWarnType);
        List<String> roleList = new ArrayList<String>();
        BizUserEntity user = this.getUser();
        if( null != user){
        	
        	 roleList = user.getRoleCodes();
        }
        // 权限判断
        if (roleList.size() > 0 && !roleList.contains(DictConstant.GOVERNMENT_USER)) {// 非政府权限
			
        	if( null != user){
        		map.put("corporateNo", user.getCorporateCode());
        	}
        }
        int result = this.licenceWarnService.amount(map);
        LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
        
        
        this.sendData(result, CommonConstant.SAVE_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, licenceWarnType);
        
        return this.sendMessage();
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.licenceWarnService;
    }
}
