/**
 * 文件名：RouteOperate
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.util.network.JXWLHttpClientUtils;
import com.c503.sc.jxwl.transacation.bean.ParkArea;
import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.c503.sc.jxwl.transacation.service.IParkAreaService;
import com.c503.sc.jxwl.transacation.service.ISolveRouteService;
import com.c503.sc.jxwl.transacation.service.IWayBillService;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.jxwl.zcpt.bean.SolveRoute;
import com.c503.sc.jxwl.zcpt.bean.UserDefinedLine;
import com.c503.sc.jxwl.zcpt.constant.BizExConstants;
import com.c503.sc.jxwl.zcpt.pointInPolygon.IsPointInPolygon;
import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedLineService;
import com.c503.sc.jxwl.zcpt.utils.GeometricAlgorithmUtils;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉路径规划操作 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class RouteOperate {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(RouteOperate.class);
    
    /** 路径规划服务地址 */
    @Value("#{configProperties['solveRouteUrl']}")
    private String url = "";
    
    /** 运单信息业务接口 */
    @Resource
    private IWayBillService wayBillService;
    
    /** 园区区域service */
    @Resource(name = "parkAreaService")
    private IParkAreaService parkAreaService;
    
    /** 路径规划service */
    @Resource(name = "solveRouteService")
    private ISolveRouteService solveRouteService;
    
    /** 自定义线条 */
    @Resource(name = "userDefinedLineService")
    private IUserDefinedLineService userDefinedLineService;
    
    /**
     * 规划路径
     * 
     * @param waybillId 运单id
     * @return String
     * @throws Exception Exception
     */
    public String route(String waybillId)
        throws Exception {
        WayBillEntity waybill = wayBillService.findById(waybillId);
        if (C503StringUtils.isEmpty(waybill.getStartLng())
            || C503StringUtils.isEmpty(waybill.getStartLat())
            || C503StringUtils.isEmpty(waybill.getEndLng())
            || C503StringUtils.isEmpty(waybill.getEndLat())) {
            throw new CustomException(NumberContant.THREE, "起始坐标或终点坐标异常");
        }
        // 使用自定义线路充当路径
        List<UserDefinedLine> list =
            this.userDefinedLineService.findUserDefinedLines();
        for (UserDefinedLine userDefinedLine : list) {
            // 计算距离
            double startDistance1 =
                GeometricAlgorithmUtils.getDistance(Double.parseDouble(userDefinedLine.getStartLat()),
                    Double.parseDouble(userDefinedLine.getStartLng()),
                    Double.parseDouble(waybill.getEndLat()),
                    Double.parseDouble(waybill.getEndLng()));
            double endDistance1 =
                GeometricAlgorithmUtils.getDistance(Double.parseDouble(waybill.getStartLat()),
                    Double.parseDouble(waybill.getStartLng()),
                    Double.parseDouble(userDefinedLine.getEndLat()),
                    Double.parseDouble(userDefinedLine.getEndLng()));
            double startDistance2 =
                GeometricAlgorithmUtils.getDistance(Double.parseDouble(userDefinedLine.getStartLat()),
                    Double.parseDouble(userDefinedLine.getStartLng()),
                    Double.parseDouble(waybill.getStartLat()),
                    Double.parseDouble(waybill.getStartLng()));
            double endDistance2 =
                GeometricAlgorithmUtils.getDistance(Double.parseDouble(waybill.getEndLat()),
                    Double.parseDouble(waybill.getEndLng()),
                    Double.parseDouble(userDefinedLine.getEndLat()),
                    Double.parseDouble(userDefinedLine.getEndLng()));
            // 路径起点终点倒序后保存
            if (startDistance1 <= 500 && startDistance1 >= -500
                && endDistance1 <= 500 && endDistance1 >= -500) {
                JSONArray path =
                    JSONArray.parseArray(userDefinedLine.getPoints());
                JSONArray path2 = new JSONArray();
                int len = path.size();
                for (int i = 0; i < len; i++) {
                    path2.add(path.get(len - i - 1));
                }
                String path2Str = path2.toJSONString();
                this.saveRoute(waybillId, waybill, path2Str);
                return "ok";
            }
            // 保存路径·
            if (startDistance2 <= 500 && startDistance2 >= -500
                && endDistance2 <= 500 && endDistance2 >= -500) {
                this.saveRoute(waybillId, waybill, userDefinedLine.getPoints());
                return "ok";
            }
        }
        // 使用天地图api服务生成路径
        String stops =
            waybill.getStartLng() + "," + waybill.getStartLat() + ";"
                + waybill.getEndLng() + "," + waybill.getEndLat();
        LOGGER.debug(SystemContants.DEBUG_START, "规划坐标：" + stops);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("f", "json"));
        // 起点和终点 格式：lng,lat;lng,lat
        params.add(new BasicNameValuePair("stops", stops));
        // 区域障碍 组装区域障碍参数
        Map<String, Object> bas = solveRouteService.setBarriersArgs();
        if (bas != null && !bas.isEmpty()) {
            String fs = JSON.toJSONString(bas);
            params.add(new BasicNameValuePair("polygonBarriers", fs));
            LOGGER.debug(SystemContants.DEBUG_START, "区域障碍：" + fs);
        }
        else {
            LOGGER.debug(SystemContants.DEBUG_START, "无区域障碍");
        }
        params.add(new BasicNameValuePair("returnRoutes", "true"));
        String result = this.httpPost(params);
        LOGGER.debug(SystemContants.DEBUG_START, "规划结果：" + result);
        JSONObject obj = JSON.parseObject(result);
        JSONObject error = obj.getJSONObject("error");
        if (error != null) {
            LOGGER.info(SystemContants.DEBUG_START, result);
            return "fail";
        }
        JSONObject routes = obj.getJSONObject("routes");
        if (routes == null) {
            LOGGER.info(SystemContants.DEBUG_START, "解析routes失败" + result);
            return "fail";
        }
        
        JSONArray features = routes.getJSONArray("features");
        if (features == null || features.isEmpty()) {
            LOGGER.info(SystemContants.DEBUG_START, "解析routes->features失败"
                + result);
            return "fail";
        }
        // 获取第一个路径规划
        JSONObject feature = features.getJSONObject(0);
        JSONObject geometry = feature.getJSONObject("geometry");
        if (geometry == null) {
            LOGGER.info(SystemContants.DEBUG_START,
                "解析routes->features[0]->geometry失败" + result);
            return "fail";
        }
        JSONArray paths = geometry.getJSONArray("paths");
        if (paths == null || paths.isEmpty()) {
            LOGGER.info(SystemContants.DEBUG_START,
                "解析routes->features[0]->geometry->paths失败" + result);
            return "fail";
        }
        // 获取规划路径
        JSONArray path = paths.getJSONArray(0);
        if (path != null && !path.isEmpty()) {
            // 剔除z坐标,坐标只保留xy，如[x,y]
            int len = path.size();
            for (int i = 0; i < len; i++) {
                JSONArray point = path.getJSONArray(i);
                if (point != null) {
                    int size = point.size();
                    while (size >= NumberContant.THREE) {
                        point.remove(size - 1);
                        size = point.size();
                    }
                }
            }
            // 保存路径规划
            this.saveRoute(waybillId, waybill, path.toJSONString());
            return "ok";
        }
        return "fail";
    }
    
    /**
     * 〈一句话功能简述〉当前车在园区内还是外 〈功能详细描述〉
     * 
     * @param loc
     *            CarLocationEntity
     * @param waybillId
     *            运单id
     * @return true:在园区内、false:园区外
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    public boolean inOrOurParkArea(CarLocationEntity loc, String waybillId)
        throws Exception {
        boolean inout = false;
        Point point =
            new Point(loc.getLongitude().doubleValue(), loc.getLatitude()
                .doubleValue());
        // 2、获取园区区域
        ParkArea parkarea = null;
        try {
            parkarea = this.parkAreaService.findParkArea();
        }
        catch (Exception e) {
            throw new Exception("not find anyone parkArea!");
        }
        // 3、判断当前车辆在园区内还是在园区外
        List<Point> pts = new ArrayList<Point>();
        String points = parkarea.getPoints();
        JSONArray pointArrs = JSON.parseArray(points);
        for (Object pointArr : pointArrs) {
            JSONArray ars = JSONObject.parseArray(JSON.toJSONString(pointArr));
            double lng2 = Double.parseDouble(String.valueOf(ars.get(0)));
            double lat2 = Double.parseDouble(String.valueOf(ars.get(1)));
            Point point2 = new Point(lng2, lat2);
            pts.add(point2);
        }
        inout = IsPointInPolygon.isInPolygon(point, pts);
        
        return inout;
    }
    
    /**
     * 〈一句话功能简述〉保存规划路径
     * 〈功能详细描述〉
     * 
     * @param waybillId 运单ID
     * @param waybill 运单
     * @param routePoints 规划路径点集合
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private void saveRoute(String waybillId, WayBillEntity waybill,
        String routePoints)
        throws Exception {
        SolveRoute solveRoute = new SolveRoute();
        solveRoute.setId(C503StringUtils.createUUID())
            .setCarrierName(waybill.getCarno())
            .setCreateTime(new Date())
            .setPoints(routePoints)
            .setWaybillId(waybillId);
        this.solveRouteService.saveSolveRoute(solveRoute);
        LOGGER.info(CommonConstant.SAVE_SUC_OPTION,
            "保存路径规划:" + solveRoute.getId());
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param params
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private String httpPost(List<NameValuePair> params)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, params);
        if (StringUtils.isEmpty(url)) {
            CustomException e =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, url);
            e.setErrorMessage("尚未设置路径规划的服务地址");
            throw e;
        }
        String result = null;
        if (params != null) {
            result = JXWLHttpClientUtils.doPost(url, params);
        }
        else {
            result = JXWLHttpClientUtils.doPost(url, null);
        }
        if (StringUtils.isEmpty(result)) {
            CustomException e =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, url);
            e.setErrorMessage("位置服务平台异常,请求响应为空");
            throw e;
        }
        LOGGER.debug(SystemContants.DEBUG_END, result);
        
        return result;
    }
}
