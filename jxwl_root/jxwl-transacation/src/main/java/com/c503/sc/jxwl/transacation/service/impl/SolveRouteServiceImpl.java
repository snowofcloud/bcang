/**
 * 文件名：FullScreenServiceImp.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.transacation.service.ISolveRouteService;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.jxwl.zcpt.bean.SolveRoute;
import com.c503.sc.jxwl.zcpt.dao.IShortcutDao;
import com.c503.sc.jxwl.zcpt.dao.ISolveRouteDao;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉园区区域service 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "solveRouteService")
public class SolveRouteServiceImpl implements ISolveRouteService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(SolveRouteServiceImpl.class);
    
    /** 路径规划dao */
    @Resource(name = "solveRouteDao")
    private ISolveRouteDao solveRouteDao;
    
    /** 区域限制dao */
    @Resource(name = "shortcutDao")
    private IShortcutDao shortcutDao;
    
    @Override
    public void saveSolveRoute(SolveRoute solveRoute)
        throws Exception {
        this.solveRouteDao.saveSolveRoute(solveRoute);
    }
    
    @Override
    public SolveRoute findLastRoute(String carrierName)
        throws Exception {
        return this.solveRouteDao.findLastRoute(carrierName);
    }
    
    @Override
    public SolveRoute findRouteByWaybillId(String waybillId)
        throws Exception {
        return this.solveRouteDao.findRouteByWaybillId(waybillId);
    }
    
    @Override
    public Map<String, Object> setBarriersArgs() {
        Map<String, Object> m = new HashMap<>();
        // 装入多个几何区域
        List<Map<String, Object>> features = new ArrayList<>();
        List<LimitArea> limits = null;
        try {
            limits = this.shortcutDao.findAllNotInAreas();
        }
        catch (Exception e) {
            CustomException ce =
                new CustomException(CommonConstant.SYS_EXCEPTION, e, limits);
            ce.setErrorMessage("查询限制区域错误");
            LOGGER.error(CommonConstant.SYS_EXCEPTION, ce);
        }
        if (limits != null) {
            for (LimitArea limit : limits) {
                String points = "[" + limit.getPoints() + "]";
                if (!StringUtils.isEmpty(points)) {
                    JSONArray ps = null;
                    try {
                        ps = JSONArray.parseArray(points);
                    }
                    catch (Exception e) {
                        CustomException ce =
                            new CustomException(CommonConstant.SYS_EXCEPTION,
                                e, limit);
                        ce.setErrorMessage("限制区域坐标格式化失败：points=" + points);
                        LOGGER.error(CommonConstant.SYS_EXCEPTION, ce);
                        continue;
                    }
                    if (ps != null && !ps.isEmpty()) {
                        // 几何区域
                        Map<String, Object> geometry = new HashMap<>();
                        geometry.put("rings", ps);
                        Map<String, Object> spatialReference = new HashMap<>();
                        spatialReference.put("wkid", "4490");
                        geometry.put("spatialReference", spatialReference);
                        // 属性
                        Map<String, Object> attributes = new HashMap<>();
                        attributes.put("Name", limit.getLimitName());
                        
                        // 设置对象属性并加入集合
                        Map<String, Object> feature = new HashMap<>();
                        feature.put("geometry", geometry);
                        feature.put("attributes", attributes);
                        features.add(feature);
                    }
                    else {
                        CustomException ce =
                            new CustomException(CommonConstant.SYS_EXCEPTION,
                                limit);
                        ce.setErrorMessage("限制区域坐标为空：points="
                            + JSON.toJSONString(ps));
                        LOGGER.error(CommonConstant.SYS_EXCEPTION, ce);
                    }
                }
                else {
                    CustomException ce =
                        new CustomException(CommonConstant.SYS_EXCEPTION, limit);
                    ce.setErrorMessage("限制区域坐标为空：points=" + points);
                    LOGGER.error(CommonConstant.SYS_EXCEPTION, ce);
                }
            }
        }
        else {
            LOGGER.info(SystemContants.DEBUG_END, "没有获取到限制区域");
        }
        if (!features.isEmpty()) {
            m.put("features", features);
        }
        return m;
    }
}
