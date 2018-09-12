/**
 * 文件名：WayBillStatisticalController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnpEveryTimes;
import com.c503.sc.jxwl.vehiclemonitor.bean.WayBillStatisticalEntity;
import com.c503.sc.jxwl.vehiclemonitor.service.IWayBillStatisticalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉运单统计action
 * 〈功能详细描述〉
 * 
 * @author mjw
 * @version [版本号, 2016-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/wayBillStatistical")
public class WayBillStatisticalController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillStatisticalController.class);
    
    /** 统计分析service */
    @Resource
    private IWayBillStatisticalService wayBillStatisticalService;
    
    /**
     * 〈一句话功能简述〉查询所有车辆
     * 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/statistics/{wayBillTimeChoice}", method = RequestMethod.GET)
    @ResponseBody
    public Object statistics(@PathVariable String wayBillTimeChoice)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            return sendCode(NumberContant.THREE, "not permission access");
        }
        if (wayBillTimeChoice.equals("1")) {
        	// 接口调用
            Map<String, Object> obj = this.wayBillStatisticalService.findPieVal();
            @SuppressWarnings("unchecked")
    		List<Map<String, Object>> list = (List<Map<String, Object>>)obj.get("wbi");
            List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
            int len = list.size();
            int sum = 0;
            for(int i=0; i<len; i++){
            	
            	if(i<=9){
            		newList.add(list.get(i));
            	}else{
            		BigDecimal times2 = (BigDecimal) list.get(i).get("TIMES");
            		sum += Integer.valueOf(times2.toString());
            		
            	}
            }
            /*Map<String, Object> others = new HashMap<String, Object>();
            others.put("GOODSNAME", "其它");
            others.put("TIMES", sum);
            newList.add(others);
            
            obj.put("wbi", newList);*/
            
            // 3、数据返回
            this.sendData(obj, CommonConstant.FIND_SUC_OPTION);
            LOGGER.debug(SystemContants.DEBUG_END, obj);
        } else if (wayBillTimeChoice.equals("2")) {
        	// 接口调用
            Map<String, Object> obj = this.wayBillStatisticalService.findPieValMon();
            @SuppressWarnings("unchecked")
    		List<Map<String, Object>> list = (List<Map<String, Object>>)obj.get("wbi");
            List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
            int len = list.size();
            int sum = 0;
            for(int i=0; i<len; i++){
            	
            	if(i<=9){
            		newList.add(list.get(i));
            	}else{
            		BigDecimal times2 = (BigDecimal) list.get(i).get("TIMES");
            		sum += Integer.valueOf(times2.toString());
            		
            	}
            }
           /* Map<String, Object> others = new HashMap<String, Object>();
            others.put("GOODSNAME", "其它");
            others.put("TIMES", sum);
            newList.add(others);
            
            obj.put("wbi", newList);*/
            
            // 3、数据返回
            this.sendData(obj, CommonConstant.FIND_SUC_OPTION);
            LOGGER.debug(SystemContants.DEBUG_END, obj);
        } else if (wayBillTimeChoice.equals("3")) {
        	// 接口调用
            Map<String, Object> obj = this.wayBillStatisticalService.findPieValWeek();
            @SuppressWarnings("unchecked")
    		List<Map<String, Object>> list = (List<Map<String, Object>>)obj.get("wbi");
            List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
            int len = list.size();
            int sum = 0;
            for(int i=0; i<len; i++){
            	
            	if(i<=9){
            		newList.add(list.get(i));
            	}else{
            		BigDecimal times2 = (BigDecimal) list.get(i).get("TIMES");
            		sum += Integer.valueOf(times2.toString());
            		
            	}
            }
            /*Map<String, Object> others = new HashMap<String, Object>();
            others.put("GOODSNAME", "其它");
            others.put("TIMES", sum);
            newList.add(others);
            
            obj.put("wbi", newList);*/
            
            // 3、数据返回
            this.sendData(obj, CommonConstant.FIND_SUC_OPTION);
            LOGGER.debug(SystemContants.DEBUG_END, obj);
        }
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询平台信息
     * 〈功能详细描述〉
     * 
     * @param page page
     * @param rows rows
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<>();
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            return sendCode(NumberContant.THREE, "not permission access");
        }
        
        // 2、接口调用
        List<WayBillStatisticalEntity> list =
            this.wayBillStatisticalService.countAllByParams(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }    
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.wayBillStatisticalService;
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
