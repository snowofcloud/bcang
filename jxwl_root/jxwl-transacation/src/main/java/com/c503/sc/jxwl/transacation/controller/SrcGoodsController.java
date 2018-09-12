/**
 * 文件名：SrcGoodsController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsForFull;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsInfo;
import com.c503.sc.jxwl.transacation.formbean.SrcGoodsForm;
import com.c503.sc.jxwl.transacation.formbean.SrcGoodsInfoForm;
import com.c503.sc.jxwl.transacation.service.ILeaveMessageService;
import com.c503.sc.jxwl.transacation.service.ILogManageService;
import com.c503.sc.jxwl.transacation.service.ISrcGoodsService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/srcGoods")
public class SrcGoodsController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(SrcGoodsController.class);
    
    /** 订单日志业务接口 */
    @Resource(name = "logManageService")
    private ILogManageService logManageService;
    
    /** 货源管理业务接口 */
    @Resource
    private ISrcGoodsService srcGoodsService;
    
    /** 留言管理信息业务接口 */
    @Resource
    private ILeaveMessageService leaveMessageService;
    
    /** 终端接口 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /** 危险品车辆信息业务接口 */
    @Resource
    private IDangerVehicleService dangerVehicleService;
    
    /**
     * 〈一句话功能简述〉分页查询货源信息入口 〈功能详细描述〉
     * 
     * @param page
     *            前台数据-页数
     * @param rows
     *            前台数据-每一页显示的行数
     * @param waybilllNo
     *            货单号
     * @param startTime
     *            发布开始时间
     * @param endTime
     *            发布结束时间
     * @return 返回查询的所有记录（分页数据）
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(Integer page, Integer rows, String waybilllNo,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date endTime)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        waybilllNo = waybilllNo == null ? null : waybilllNo.trim();
        Map<String, Object> map = new HashMap<>();
        map.put("waybilllNo", waybilllNo);
        map.put("startTime", startTime);
        endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("endTime", endTime);
        
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        if (this.getUser()
            .getRoleCodes()
            .contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            map.put("hgCorporateNo", this.getUser().getCorporateCode());
            
            List<SrcGoods> list = this.srcGoodsService.findByParams(map);
            setJQGrid(list,
                pageEntity.getTotalCount(),
                page,
                rows,
                CommonConstant.FIND_SUC_OPTION);
        }
        else {
            return null;
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉保存货源信息 〈功能详细描述〉
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
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object saveSrcGoods(@Validated(value = Save.class)
    @RequestBody
    SrcGoodsForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            String userId = this.getUser().getId();
            SrcGoods srcGoods = new SrcGoods();
            
            this.copyProperties(form, srcGoods, "srcGoodsInfo");
            this.createCommonVal(srcGoods, true, userId);
            List<SrcGoodsInfoForm> goodsInfofs = form.getGoodsInfos();
            List<SrcGoodsInfo> goodsInfos = new ArrayList<>();
            if (null != goodsInfofs && 0 < goodsInfofs.size()) {
                for (int i = 0; i < goodsInfofs.size(); i++) {
                    SrcGoodsInfoForm goodsInfof = goodsInfofs.get(i);
                    SrcGoodsInfo goodsInfo = new SrcGoodsInfo();
                    this.copyProperties(goodsInfof, goodsInfo);
                    this.createCommonVal(goodsInfo, true, userId);
                    goodsInfo.setGoodsSerialNo(i + 1 + "");
                    goodsInfo.setSrcGoodsId(srcGoods.getId());
                    goodsInfos.add(goodsInfo);
                }
            }
            
            srcGoods.setGoodsInfos(goodsInfos);
            srcGoods.setHgCorporateNo(this.getUser().getCorporateCode());
            
            // false : 保存
            if (!form.getPublishOrNot()) {
                srcGoods.setTradeStatus(DictConstant.SRC_GOODS1_NOT_PUBLISH);
            }
            else {
                // true : 保存并发布
                srcGoods.setTradeStatus(DictConstant.SRC_GOODS2_PUBLISHED);
                srcGoods.setPublishDate(new Date());
            }
            Object result = this.srcGoodsService.save(srcGoods);
            
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
     * 〈一句话功能简述〉修改货源信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param form
     *            SrcGoodsForm
     * @param bindingResult
     *            验错误信息结果集
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable
    String id, @Validated(value = Update.class)
    @RequestBody
    SrcGoodsForm form, BindingResult bindingResult)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, form);
        
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            if (C503StringUtils.isNotEmpty(id)) {
                String userId = this.getUser().getId();
                SrcGoods srcGoods = new SrcGoods();
                this.copyProperties(form, srcGoods, "srcGoodsInfo");
                this.createCommonVal(srcGoods, false, userId);
                srcGoods.setId(id);
                
                List<SrcGoodsInfoForm> goodsInfofs = form.getGoodsInfos();
                List<SrcGoodsInfo> goodsInfos = new ArrayList<>();
                
                if (null != goodsInfofs && 0 < goodsInfofs.size()) {
                    for (int i = 0; i < goodsInfofs.size(); i++) {
                        SrcGoodsInfo goodsInfo = new SrcGoodsInfo();
                        SrcGoodsInfoForm goodsInfof = goodsInfofs.get(i);
                        this.copyProperties(goodsInfof, goodsInfo);
                        this.createCommonVal(goodsInfo, true, userId);
                        goodsInfo.setSrcGoodsId(srcGoods.getId());
                        goodsInfo.setGoodsSerialNo(i + 1 + "");
                        goodsInfo.setUpdateBy(this.getUser().getId());
                        goodsInfo.setUpdateTime(new Date());
                        goodsInfos.add(goodsInfo);
                    }
                }
                
                srcGoods.setGoodsInfos(goodsInfos);
                srcGoods.setId(id);
                srcGoods.setHgCorporateNo(this.getUser().getCorporateCode());
                
                this.srcGoodsService.update(srcGoods);
                
                LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, srcGoods);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.UPDATE_SUC_OPTION,
                    srcGoods).recordLog();
                this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
            }
            else {
                this.sendCode(CommonConstant.ARGS_INVALID);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        // 发送响应消息
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看货源详情 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            SrcGoods val = this.srcGoodsService.findById(id);
            if (null != val && !val.getGoodsInfos().isEmpty()) {
                Collections.sort(val.getGoodsInfos(),
                    new Comparator<SrcGoodsInfo>() {
                        @Override
                        public int compare(SrcGoodsInfo o1, SrcGoodsInfo o2) {
                            int flag = 0;
                            if (StringUtils.isNotEmpty(o1.getGoodsSerialNo())
                                && StringUtils.isNotBlank(o2.getGoodsSerialNo())) {
                                int a = Integer.parseInt(o1.getGoodsSerialNo());
                                int b = Integer.parseInt(o2.getGoodsSerialNo());
                                if (a - b > 0) {
                                    flag = 1;
                                }
                                else if (a - b < 0) {
                                    flag = -1;
                                }
                            }
                            
                            return flag;
                        }
                    });
            }
            
            this.sendData(val, CommonConstant.FIND_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除货源 〈功能详细描述〉
     * 
     * @param id
     *            货源id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            this.srcGoodsService.delete(id, this.getUser().getId());
            
            this.sendCode(CommonConstant.DELETE_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉发布 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object publish(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            this.srcGoodsService.publishOrSign(id,
                DictConstant.SRC_GOODS2_PUBLISHED,
                this.getUser().getId(),
                "0");
            
            this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉签订 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param wlCorporateNo
     *            wlCorporateNo
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/sign/{id}/{wlCorporateNo}", method = RequestMethod.PUT)
    @ResponseBody
    public Object sign(@PathVariable
    String id, @PathVariable
    String wlCorporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(wlCorporateNo)) {
            this.srcGoodsService.publishOrSign(id,
                DictConstant.SRC_GOODS3_WAIT_SURE,
                this.getUser().getId(),
                "1",
                wlCorporateNo);
            this.logManageService.saveLog(id,
                this.getUser().getCorporateCode(),
                DictConstant.SRC_GOODS3_WAIT_SURE,
                this.getUser().getName() + "操作后订单状态由待确认变为：待确认");
            this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /****************************************** 查询企业信息 **************************************/
    /**
     * 〈一句话功能简述〉分页查询企业信息 〈功能详细描述〉
     * 
     * @param enterpriseName
     *            enterpriseName
     * @param enterpriseType
     *            enterpriseType
     * @param matchCondition
     *            查询时候匹配的条件
     * @param page
     *            page
     * @param rows
     *            rows
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findEnpInfos", method = RequestMethod.POST)
    @ResponseBody
    public Object findEnpInfos(String enterpriseName, String enterpriseType,
        String matchCondition, String vehicleType, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<>();
        enterpriseName =
            StringUtils.isNotEmpty(enterpriseName) ? StringUtils.trim(enterpriseName)
                : null;
        map.put("searchKey", enterpriseName);
        map.put("enterpriseType", enterpriseType);
        map.put("matchCondition", matchCondition);
        map.put("vehicleType", vehicleType);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // 2、接口调用
        List<EnterpriseEntity> list = this.srcGoodsService.findEnpInfos(map);
        
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /****************************************** 企业相关车辆 **************************************/
    /**
     * 〈一句话功能简述〉分页查询当前选中企业的所有车辆 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @param page 前台数据-页数
     * @param rows 前台数据-每一页显示的行数
     * @return 返回查询的所有记录（分页数据）
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findCarsByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findCars(Integer page, Integer rows, String corporateNo,
        String vehicleType)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<>();
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        map.put("corporateNo", corporateNo);
        map.put("vehicleType", vehicleType);
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findCars(map);
        // 查询终端状态
        for (int i = 0, len = list.size(); i < len; i++) {
            DangerVehicleEntity dangerVehicleEntity = list.get(i);
            String carrierName = dangerVehicleEntity.getLicencePlateNo();
            TerminalEntity entity =
                this.terminalService.findByCarrierName(carrierName);
            if (entity != null) {
                list.get(i).setStatus(entity.getTerminalState());
            }
            else {
                list.get(i).setStatus("");
            }
        }
        
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看车辆详情 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findCarInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findCarInfo(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            DangerVehicleEntity val =
                this.dangerVehicleService.findCarInfoById(id);
            this.sendData(val, CommonConstant.FIND_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看货源详情 〈功能详细描述〉
     * 
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public Object findAll()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        String corporateNo = "";
        List<SrcGoodsForFull> val = this.srcGoodsService.findAll(corporateNo);
        
        this.sendData(val, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.srcGoodsService;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    /**
     * 〈一句话功能简述〉查看货源详情 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLeaveExistById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findLeaveExistById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            // 是否留言
            String wlEnterprise = this.getUser().getCorporateCode();
            if (leaveMessageService.findLeaveMessageExist(wlEnterprise, id)) {
                int result = 1;
                this.sendData(result, CommonConstant.FIND_SUC_OPTION);
            }
        }
        
        return this.sendMessage();
    }
    
}
