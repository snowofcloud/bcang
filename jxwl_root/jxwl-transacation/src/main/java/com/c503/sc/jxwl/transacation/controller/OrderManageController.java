/**
 * 文件名：OrderManageController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.bean.LogManage;
import com.c503.sc.jxwl.transacation.bean.OrderForFull;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.service.ILogManageService;
import com.c503.sc.jxwl.transacation.service.IOrderManageService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉订单管理controller 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/orderManage")
public class OrderManageController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(OrderManageController.class);
    
    /** 订单管理业务接口 */
    @Resource(name = "orderManageService")
    private IOrderManageService orderManageService;
    
    /** 订单日志业务接口 */
    @Resource(name = "logManageService")
    private ILogManageService logManageService;
    
    /**
     * 〈一句话功能简述〉分页查询订单信息 〈功能详细描述〉
     * 
     * @param startTime
     *            startTime
     * @param endTime
     *            endTime
     * @param page
     *            page
     * @param rows
     *            rows
     * @return SrcGoodsEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date endTime, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("endTime", endTime);
        map.put("remove", SystemContants.ON);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // 2、判断是物流用户还是化工用户（本企业只能查看自己企业的信息）
        List<String> roleCode = this.getUser().getRoleCodes();
        String corporateNo = this.getUser().getCorporateCode();
        if (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            map.put("wlCorporateNo", corporateNo);
        }
        else if (roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            map.put("hgCorporateNo", corporateNo);
        }
        
        // 3、接口调用
        List<SrcGoods> list = this.orderManageService.findByParams(map);
        
        // 4、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询订单 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAllOrders", method = RequestMethod.POST)
    @ResponseBody
    public Object findAllOrders()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("remove", SystemContants.ON);
        /*
         * // 2、判断是物流用户还是化工用户（本企业只能查看自己企业的信息） List<String> roleCode =
         * this.getUser().getRoleCodes(); String corporateNo =
         * this.getUser().getCorporateCode(); if
         * (StringUtils.isNotEmpty(corporateNo)) { if
         * (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
         * map.put("wlCorporateNo", corporateNo); } else if
         * (roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
         * map.put("hgCorporateNo", corporateNo); }
         */
        
        // 3、接口调用
        List<OrderForFull> list = this.orderManageService.findAllOrders(map);
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        
        // 4、数据返回
        LOGGER.debug(SystemContants.DEBUG_END);
        /*
         * } else { return null; }
         */
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过id查询订单信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param tradeObjCode
     *            tradeObjCode
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}/{tradeObjCode}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String id, @PathVariable
    String tradeObjCode)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        
        // 获取登录用户角色
        List<String> roleCode = this.getUser().getRoleCodes();
        String corporateNo = this.getUser().getCorporateCode();
        if (C503StringUtils.isNotEmpty(id)) {
            if (StringUtils.isNotEmpty(corporateNo)) {
                // 登录用户为物流企业时交易对象为对应化工企业
                if (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
                    Object val =
                        this.orderManageService.findById(id,
                            null,
                            corporateNo,
                            tradeObjCode);
                    sendData(val, CommonConstant.FIND_SUC_OPTION);
                }
                // 登录用户为化工企业和政府时交易对象为对应物流企业
                else {
                    Object val =
                        this.orderManageService.findById(id,
                            corporateNo,
                            null,
                            tradeObjCode);
                    sendData(val, CommonConstant.FIND_SUC_OPTION);
                }
                // 政府查看
            }
            else {
                Object val =
                    this.orderManageService.findById(id,
                        null,
                        null,
                        tradeObjCode);
                sendData(val, CommonConstant.FIND_SUC_OPTION);
            }
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
        
    }
    
    /**
     * 〈一句话功能简述〉（化工用户）撤销订单 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/cancelOrder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelOrder(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (C503StringUtils.isNotEmpty(id)) {
            
            // 撤销订单之前先判断该条信息的状态是否是待确认
            String nowStatu = this.orderManageService.findTradeStatus(id);
            if (!StringUtils.equals(nowStatu, DictConstant.SRC_GOODS3_WAIT_SURE)) {
                throw new CustomException(
                    BizExConstant.SRCGOODS_CANCEL_NOTALLOWD);
            }
            
            if (isSameHg()) {
                this.orderManageService.updateTradeStatus(id,
                    DictConstant.SRC_GOODS2_PUBLISHED,
                    this.getUser().getCorporateCode(),
                    new Date());
                this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
                this.logManageService.saveLog(id,
                    this.getUser().getCorporateCode(),
                    DictConstant.SRC_GOODS2_PUBLISHED,
                    this.getUser().getName() + "操作后订单状态由待确认变为：已发布");
            }
            else {
                throw new CustomException(
                    BizExConstant.HGENTERPRISE_CANCEL_ALLOW, id);
            }
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉模糊订单信息 〈功能详细描述〉
     * 
     * @param orderNo
     *            orderNo
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findOrderNo", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderNo(String orderNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, orderNo);
        if (C503StringUtils.isNotEmpty(orderNo)) {
            if (isSameHg()) {
                Map<String, Object> map = new HashMap<>();
                map.put("orderNo", orderNo);
                map.put("corporateNo", this.getUser().getCorporateCode());
                List<SrcGoods> list = this.orderManageService.findOrderNo(map);
                LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
                sendData(list, CommonConstant.FIND_SUC_OPTION);
            }
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, orderNo);
        }
        LOGGER.debug(SystemContants.DEBUG_END, orderNo);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉精确查询订单信息 〈功能详细描述〉
     * 
     * @param orderNo
     *            orderNo
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAccurateOrderNo", method = RequestMethod.POST)
    @ResponseBody
    public Object findAccurateOrderNo(String orderNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, orderNo);
        if (isSameHg()) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderNo", orderNo);
            map.put("corporateNo", this.getUser().getCorporateCode());
            List<SrcGoods> list =
                this.orderManageService.findAccurateOrderNo(map);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
            sendData(list, CommonConstant.FIND_SUC_OPTION);
        }else{
        	 List<SrcGoods> list = new ArrayList<SrcGoods>();
        	 sendData(list, CommonConstant.FIND_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, orderNo);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉物流用户拒绝待确认的订单 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/rejectOrder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object rejectOrder(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (C503StringUtils.isNotEmpty(id)) {
            
            // 拒绝订单之前先判断该条信息的状态是否是待确认
            String nowStatu = this.orderManageService.findTradeStatus(id);
            if (!StringUtils.equals(nowStatu, DictConstant.SRC_GOODS3_WAIT_SURE)) {
                throw new CustomException(
                    BizExConstant.SRCGOODS_REJECT_NOTALLOWD);
            }
            
            if (isSame()) {
                this.orderManageService.updateTradeStatus(id,
                    DictConstant.SRC_GOODS4_REFUSED,
                    this.getUser().getCorporateCode(),
                    null);
                this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
                this.logManageService.saveLog(id,
                    this.getUser().getCorporateCode(),
                    DictConstant.SRC_GOODS4_REFUSED,
                    this.getUser().getName() + "操作后订单状态由待确认变为：已拒绝");
            }
            else {
                throw new CustomException(
                    BizExConstant.WLENTERPRISE_REJECT_ALLOW, id);
            }
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉化工用户重新发布货源 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/pushOrder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object pushOrder(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (C503StringUtils.isNotEmpty(id)) {
            
            // 重新发布该订单之前先判断该条信息的状态是否是已拒绝
            String nowStatu = this.orderManageService.findTradeStatus(id);
            if (!StringUtils.equals(nowStatu, DictConstant.SRC_GOODS4_REFUSED)) {
                throw new CustomException(
                    BizExConstant.HGENTERPRISE_PUSHREJECT_ALLOW);
            }
            // 化工用户才有权限重新发布货源
            if (isSameHg()) {
                this.orderManageService.updateTradeStatus(id,
                    DictConstant.SRC_GOODS2_PUBLISHED,
                    this.getUser().getCorporateCode(),
                    new Date());
                this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
                this.logManageService.saveLog(id,
                    this.getUser().getCorporateCode(),
                    DictConstant.SRC_GOODS2_PUBLISHED,
                    this.getUser().getName() + "操作后订单状态由已拒绝变为：已发布");
            }
            else {
                throw new CustomException(
                    BizExConstant.HGENTERPRISE_PUSH_ALLOW, id);
            }
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉物流企业确认待确认的订单 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/confirmOrder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object confirmOrder(@PathVariable
    String id)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (C503StringUtils.isNotEmpty(id)) {
            // 拒绝订单之前先判断该条信息的状态是否是待确认
            String nowStatu = this.orderManageService.findTradeStatus(id);
            if (!StringUtils.equals(nowStatu, DictConstant.SRC_GOODS3_WAIT_SURE)) {
                throw new CustomException(
                    BizExConstant.SRCGOODS_CONFIRM_NOTALLOWD);
            }
            
            if (isSame()) {
                this.orderManageService.updateTradeStatus(id,
                    DictConstant.SRC_GOODS5_SIGNED,
                    this.getUser().getCorporateCode(),
                    null);
                this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
                this.logManageService.saveLog(id,
                    this.getUser().getCorporateCode(),
                    DictConstant.SRC_GOODS5_SIGNED,
                    this.getUser().getName() + "操作后订单状由待确认态变为：已签订");
            }
            else {
                throw new CustomException(BizConstants.GOVERNMENT_UPDATE_ALLOW,
                    id);
            }
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉化工用户确认订单之前判断运单状态， 返回1或0 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/hgConfirmOrderStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object hgConfirmOrderStatus(@PathVariable
    String id)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (C503StringUtils.isNotEmpty(id)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            List<String> list =
                this.orderManageService.hgConfirmOrderStatus(map);
            // 1表示该订单的运单都完成 0 表示没有完成
            int result = 1;
            for (int i = 0, len = list.size(); i < len; i++) {
                if (!list.get(i).equals(DictConstant.WAYBILL_FINISH)) {
                    result = 0;
                    break;
                }
            }
            sendData(result, CommonConstant.FIND_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉化工企业确认完成已签订的订单 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/hgConfirmOrder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object hgConfirmOrder(@PathVariable
    String id)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (C503StringUtils.isNotEmpty(id)) {
            // 化工企业确认订单之前先判断该条信息的状态是否是已签订
            String nowStatu = this.orderManageService.findTradeStatus(id);
            if (!StringUtils.equals(nowStatu, DictConstant.SRC_GOODS5_SIGNED)) {
                throw new CustomException(
                    BizExConstant.SRCGOODS_FINISH_NOTALLOWD);
            }
            
            if (isSameHg()) {
                this.orderManageService.updateTradeStatus(id,
                    DictConstant.SRC_GOODS6_FILISHED,
                    this.getUser().getCorporateCode(),
                    null);
                this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
                this.logManageService.saveLog(id,
                    this.getUser().getCorporateCode(),
                    DictConstant.SRC_GOODS6_FILISHED,
                    this.getUser().getName() + "操作后订单状态由已签订变为：已完成");
            }
            else {
                throw new CustomException(
                    BizExConstant.HGENTERPRISE_CONFIRM_ALLOW, id);
            }
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否有权限操作（拒绝订单、确认订单只有物流用户才有权限） 〈功能详细描述〉
     * 
     * @return 该用户是否是物流用户
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
            if (DictConstant.LOGISTICS_ENTERPRISE_USER.equals(userRoles.get(i))) {
                isRole = true;
                break;
            }
            
        }
        return isRole;
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否有权限操作（化工用户有权确认已签订的订单） 〈功能详细描述〉
     * 
     * @return 该用户是否是化工用户
     * @see [类、类#方法、类#成员]
     */
    private boolean isSameHg() {
        
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
            if (DictConstant.CHEMICAL_ENTERPRISE_USER.equals(userRoles.get(i))) {
                isRole = true;
                break;
            }
            
        }
        return isRole;
        
    }
    
    /**
     * 〈一句话功能简述〉评价 〈功能详细描述〉
     * 
     * @param commentComplain
     *            CommentComplain
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/commentComplain", method = RequestMethod.POST)
    @ResponseBody
    public Object commentComplain(CommentComplain commentComplain)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, commentComplain);
        BizUserEntity usr = this.getUser();
        
        this.createCommonVal(commentComplain, true, usr.getId());
        commentComplain.setCorporateNo(usr.getCorporateCode());
        
        this.orderManageService.commentComplain(commentComplain);
        this.sendCode(CommonConstant.SAVE_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END, commentComplain);
        
        // 发送响应消息
        return sendMessage();
    }
    
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return null;
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
     * 〈一句话功能简述〉分页查询订单信息 〈功能详细描述〉
     * 
     * @param orderNo
     *            orderNo
     * @param corporateNo
     *            corporateNo
     * @param page
     *            page
     * @param rows
     *            rows
     * @return SrcGoodsEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPageForWeixin")
    @ResponseBody
    public Object findByPageForWeixin(String orderNo, String corporateNo,
        Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<?> list = null;
        
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("remove", SystemContants.ON);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // 2、判断是物流用户还是化工用户（本企业只能查看自己企业的信息）
        List<String> roleCode = this.getUser().getRoleCodes();
        if (roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", corporateNo);
            
            // 3、接口调用
            list = this.orderManageService.findDataForWeixinByParams(map);
            
            // 4、数据返回
            setJQGrid(list,
                pageEntity.getTotalCount(),
                page,
                rows,
                CommonConstant.FIND_SUC_OPTION);
            LOGGER.debug(SystemContants.DEBUG_END);
            
        }
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询详情（微信） 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param tradeObjCode
     *            tradeObjCode
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByIdForWeixin/{id}/{tradeObjCode}")
    @ResponseBody
    public Object findByIdForWeixin(@PathVariable
    String id, @PathVariable
    String tradeObjCode)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        
        // 获取登录用户角色
        List<String> roleCode = this.getUser().getRoleCodes();
        if (C503StringUtils.isNotEmpty(id)) {
            // 登录用户为物流企业时交易对象为对应化工企业
            if (roleCode.contains(DictConstant.GOVERNMENT_USER)) {
                Object val =
                    this.orderManageService.findByIdForWeixin(id, tradeObjCode);
                sendData(val, CommonConstant.FIND_SUC_OPTION);
            }
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉分页查询订单日志信息
     * 〈功能详细描述〉
     * 
     * @param page 页数
     * @param rows 列数
     * @return 日志信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLogByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findLogByPage(Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        List<LogManage> list = null;
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        // 本企业只能查看自己企业的信息
        List<String> roleCode = this.getUser().getRoleCodes();
        String corporateNo = this.getUser().getCorporateCode();
        map.put("corporateNo", corporateNo);
        if (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            map.put("remindLogistics", SystemContants.ON);
            // 3、接口调用
            list = this.logManageService.findByParams(map);
        }
        else if (roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            map.put("remindChemical", SystemContants.ON);
            // 3、接口调用
            list = this.logManageService.findByParams(map);
        }
        // 4、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉更新已读订单日志信息
     * 〈功能详细描述〉
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateLogRemind", method = RequestMethod.POST)
    @ResponseBody
    public Object updateLogRemind()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        // 本企业只能查看自己企业的信息
        List<String> roleCode = this.getUser().getRoleCodes();
        String corporateNo = this.getUser().getCorporateCode();
        map.put("corporateNo", corporateNo);
        if (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            map.put("remindLogistics", SystemContants.ON);
        }
        else if (roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            map.put("remindChemical", SystemContants.ON);
        }
        // 3、接口调用
        List<LogManage> list = this.logManageService.findByParams(map);
        
        // 2、判断是物流用户还是化工用户（本企业只能查看自己企业的信息）
        if (list.size() > 0
            && roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            for (LogManage log : list) {
                log.setRemindLogistics(SystemContants.OFF);
            }
            this.logManageService.updateBatch(list);
        }
        else if (list.size() > 0
            && roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            for (LogManage log : list) {
                log.setRemindChemical(SystemContants.OFF);
            }
            this.logManageService.updateBatch(list);
        }
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
}
