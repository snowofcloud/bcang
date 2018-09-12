/**
 * 文件名：GoodsSourceDetailController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-04
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.service.IGoodsSourceDetailService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉货源信息 controller
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-9-04]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/goodsSourceDetail")
public class GoodsSourceDetailController extends ResultController {
    
    /** 货源管理业务接口 */
    @Resource
    private IGoodsSourceDetailService goodsSourceDetailService;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(GoodsSourceDetailController.class);
    
    /**
     * 〈一句话功能简述〉分页查询货源信息入口
     * 〈功能详细描述〉
     * 
     * @param page 前台数据-页数
     * @param rows 前台数据-每一页显示的行数
     * @param waybilllNo 货单号
     * @param startTime 录入开始时间
     * @param endTime 录入结束时间
     * @return 返回查询的所有记录（分页数据）
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(Integer page, Integer rows, String waybilllNo,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date startTime,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date endTime)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        waybilllNo = waybilllNo == null ? null : waybilllNo.trim();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("waybilllNo", waybilllNo);
        endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        // 添加用户权限作为mapper中的控制依据
        String userCode = "";
        if (this.getUser()
            .getRoleCodes()
            .contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            userCode = this.getUser().getCorporateCode();
            
        }
        map.put("userCode", userCode);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        List<SrcGoods> list = this.goodsSourceDetailService.findByParams(map);
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看货源详情 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            Map<String, Object> map = new HashMap<String, Object>();
            String roleCode = "0";
            map.put("id", id);
            if (this.getUser()
                .getRoleCodes()
                .contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
                String logisticsEnterprise = this.getUser().getCorporateCode();
                map.put("logisticsEnterprise", logisticsEnterprise);
                roleCode = "1";
            }
            LeaveMessageEntity val =
                this.goodsSourceDetailService.findById(map);
            if (null == val) {
                val = new LeaveMessageEntity();
                val.setLogisticsEnterprise(roleCode);
            } else {
                val.setLogisticsEnterprise(roleCode);
            }
            this.sendData(val, CommonConstant.FIND_SUC_OPTION);
        } else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
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
    
}
