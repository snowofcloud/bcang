package com.c503.sc.jxwl.transacation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.common.thrpool.ThreadPool;
import com.c503.sc.jxwl.transacation.service.IBayOnetService;
import com.c503.sc.jxwl.transacation.service.IWayBillService;
import com.c503.sc.jxwl.transacation.vo.BayOne;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 〈一句话功能简述〉卡口审核 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/bayOnet")
public class BayOnetController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillController.class);
    
    /** 卡口审核service */
    @Resource(name = "bayOnetService")
    private IBayOnetService bayOnetService;
    
    /** 运单信息业务层 */
    @Resource(name = "wayBillService")
    private IWayBillService wayBillService;
    
    /** 路径规划操作 */
    @Resource
    private RouteOperate routeOperate;
    
    /** miPushService的实例 */
    @Resource(name = "miPushService")
    private IMiPushService miPushService;
    
    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param checkno
     *            checkno
     * @param carno
     *            carno
     * @param verifystatus
     *            verifystatus
     * @param page
     *            page
     * @param rows
     *            rows
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String id, String checkno, String carno,
        String verifystatus, Integer page, Integer rows, String wayBillNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("checkno", checkno);
        map.put("carno", carno);
        map.put("verifystatus", verifystatus);
        map.put("id", id);
        map.put("wayBillNo", wayBillNo);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        List<BayOne> list = this.bayOnetService.findByParams(map);
        
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
     * 〈一句话功能简述〉通过id查询运单信息 〈功能详细描述〉
     * 
     * @param orders
     *            orders
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{orders}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String orders)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(orders)) {
            Map<String, Object> map = new HashMap<>();
            map.put("orders", orders);
            List<BayOne> list = this.bayOnetService.findByOrders(map);
            sendData(list, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, orders);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询运单信息 〈功能详细描述〉
     * 
     * @param orders
     *            orders
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findRecordById", method = RequestMethod.POST)
    @ResponseBody
    public Object findRecordById(String id, String orders)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("orders", orders);
            BayOne bayone = this.bayOnetService.findRecordById(map);
            sendData(bayone, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉审核 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param verifystatus
     *            verifystatus
     * @param bayonetport
     *            bayonetport
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST,
        RequestMethod.GET})
    @ResponseBody
    public Object verify(String id, String verifystatus, String bayonetport)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, verifystatus);
        final String waybillId = id;
        BizUserEntity user = this.getUser();
        Map<String, Object> map = new HashMap<>();
        map.put("orders", id);
        map.put("verifystatus", verifystatus);
        map.put("bayonetport", bayonetport);
        map.put("updateBy", user.getId());
        map.put("createBy", user.getId());
        map.put("verifyperson", user.getName());
        // 3、调用接口信息
        try {
            this.bayOnetService.save(map);
        }
        catch (Exception e) {
            e.getMessage();
        }
        // 通过卡口审核
        if (StringUtils.equals(verifystatus, "151001003")) {
            try {
                Future<String> fu =
                    ThreadPool.getThreadPool().submit(new Callable<String>() {
                        @Override
                        public String call()
                            throws Exception {
                            return routeOperate.route(waybillId);
                        }
                    });
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
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 4、打印日志
        LOGGER.info(CommonConstant.SAVE_SUC_OPTION, verifystatus);
        sendCode(CommonConstant.SAVE_SUC_OPTION);
        return this.sendMessage();
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
    public void miPushRuleRegulation(String id)
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
    
    /**
     * 〈一句话功能简述〉根据车牌号查询信息 〈功能详细描述〉
     * 
     * @param carName
     *            carName
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByCarName", method = RequestMethod.POST)
    @ResponseBody
    public Object findByCarName(String carName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carName);
        Map<String, Object> map = new HashMap<>();
        map.put("carName", carName);
        BayOne bayone = this.bayOnetService.findByCarName(map);
        
        // 4、打印日志
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, carName);
        sendData(bayone, CommonConstant.FIND_SUC_OPTION);
        return this.sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.bayOnetService;
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
