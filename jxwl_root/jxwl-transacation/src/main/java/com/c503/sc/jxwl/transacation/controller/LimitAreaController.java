/**
 * 文件名：RealTimeLocationController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-3
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.common.CommonConstants;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.common.thrpool.ThreadPool;
import com.c503.sc.jxwl.transacation.formbean.LimitAreaForm;
import com.c503.sc.jxwl.transacation.service.IWayBillService;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.jxwl.zcpt.service.ILimitAreaService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 实时位置数据
 * 
 * @author zz
 * @version [版本号, 2016-8-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/limitArea")
public class LimitAreaController extends ResultController {
    /** 路径规划 */
    @Resource
    private RouteOperate routeOperate;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LimitAreaController.class);
    
    /** 限制区域 */
    @Resource(name = "limitAreaService")
    private ILimitAreaService limitAreaService;
    
    /** miPushService的实例 */
    @Resource(name = "miPushService")
    private IMiPushService miPushService;
    
    /** 运单信息Service */
    @Resource(name = "wayBillService")
    private IWayBillService wayBillService;
    
    /**
     * 〈一句话功能简述〉区域限制
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLimitArea", method = RequestMethod.GET)
    @ResponseBody
    public Object findLimitArea()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<LimitArea> list = null;
        try {
            list = this.limitAreaService.findAllAreaLimit();
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看区域限制
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByLimitAreaId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findByLimitAreaId(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        LimitArea limitArea = null;
        try {
            limitArea = this.limitAreaService.findByLimitAreaId(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(limitArea, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉区域限制
     * 〈功能详细描述〉
     * 
     * @param limitName limitName
     * @param points pointList
     * @param pointsJSON pointSet
     * @param limitType limitType
     * @param limitSpeed limitSpeed
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    @ResponseBody
    public Object limitArea(LimitAreaForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        String limitName = form.getLimitName();
        String points = form.getPointList();
        String limitType = form.getLimitType();
        BigDecimal limitSpeed = form.getLimitSpeed();
        if (StringUtils.isNotEmpty(points) && StringUtils.isNotEmpty(limitName)
            && StringUtils.isNotEmpty(limitType)) {
            String id = C503StringUtils.createUUID();
            LimitArea limitArea =
                new LimitArea(id, this.getUser().getId(), points, limitName,
                    limitType, limitSpeed);
            limitArea.setCreateTime(new Date());
            limitArea.setUpdateTime(new Date());
            this.limitAreaService.save(limitArea);
            if (C503StringUtils.equals(limitType, "112001003")) {
                reRoute();
            }
            this.sendData(id, CommonConstant.SAVE_SUC_OPTION);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, limitName);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, limitName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉区域限制
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delLimitArea/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delLimitArea(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id)) {
            try {
                
                this.limitAreaService.delLimitArea(id);
                
                LimitArea limitArea =
                    this.limitAreaService.findByLimitAreaId(id);
                if (C503StringUtils.equals(limitArea.getLimitType(),
                    "112001003")) {
                    reRoute();
                }
            }
            catch (Exception e) {
                throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
            }
            this.sendCode(CommonConstant.DELETE_SUC_OPTION);
            LOGGER.info(CommonConstants.SUC_OPTION, id);
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉区域限制
     * 〈功能详细描述〉
     * 
     * @param form 限制区域表单
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateLimitArea(LimitAreaForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        String id = form.getId();
        String limitName = form.getLimitName();
        String points = form.getPointList();
        String limitType = form.getLimitType();
        BigDecimal limitSpeed = form.getLimitSpeed();
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(limitName)
            && StringUtils.isNotEmpty(limitType)) {
            LimitArea limitArea =
                new LimitArea(id, this.getUser().getId(), points, limitName,
                    limitType, limitSpeed);
            limitArea.setUpdateTime(new Date());
            this.limitAreaService.update(limitArea);
            if (C503StringUtils.equals(limitType, "112001003")) {
                reRoute();
            }
            this.sendData(id, CommonConstant.UPDATE_SUC_OPTION);
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, id);
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, limitName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.limitAreaService;
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
     * 〈一句话功能简述〉重新规划所有路径
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void reRoute()
        throws Exception, InterruptedException, ExecutionException {
        List<String> waybillIds =
            this.limitAreaService.findAllWaybillIdByTransport();
        for (final String waybillId : waybillIds) {
            Future<String> fu =
                ThreadPool.getThreadPool().submit(new Callable<String>() {
                    @Override
                    public String call()
                        throws Exception {
                        return routeOperate.route(waybillId);
                    }
                });
            LOGGER.debug(3, fu);
            if (fu.get() == "ok") {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            miPushRuleRegulation(waybillId);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
    
    /**
     * 〈一句话功能简述〉小米推送 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void miPushRuleRegulation(String id)
        throws Exception {
        // 根据运单id查询驾驶员 然后账号注册表查询regid
        String regId = this.wayBillService.findRegIdByWayBill(id);
        String msg = "ok";
        String messagePayload = this.miPushService.getPayload(msg, "3");
        String title = "路径规划";
        String des = "route";
        // 得到message
        Message message = null;
        Result result = null;
        try {
            message =
                this.miPushService.messageHandle(title,
                    des,
                    messagePayload,
                    false);
            LOGGER.info(SystemContants.DEBUG_END, "message: " + message);
            // 发送message 并得到result
            result =
                this.miPushService.sendByRegId(message,
                    regId,
                    NumberContant.FIVE);
            LOGGER.info(SystemContants.DEBUG_END, "result: " + result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
