/**
 * 文件名：SrcGoodsController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsInfo;
import com.c503.sc.jxwl.transacation.formbean.CommentComplainSearch;
import com.c503.sc.jxwl.transacation.service.ICommentComplainService;
import com.c503.sc.jxwl.transacation.service.ISrcGoodsService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉 评价、投诉〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/commentComplain")
public class CommentComplainController extends ResultController {
    
    /** 评价、投诉业务接口 */
    @Resource
    private ICommentComplainService commentComplainService;
    
    /** 货源接口 */
    @Resource
    private ISrcGoodsService srcGoodsService;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(CommentComplainController.class);
    
    /**
     * 〈一句话功能简述〉订单分页
     * 〈功能详细描述〉
     * 
     * @param search CommentComplainSearch
     * @return json
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(CommentComplainSearch search)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = search.handlePageParas();
        BizUserEntity usr = this.getUser();
        if (StringUtils.isEmpty(search.getCorporateNo())) {
            map.put("corporateNo", usr.getCorporateCode());
        }
        // 化工可查看已删除的物流企业对化工企业的评价、投诉
        if (usr.getRoleCodes().contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            map.put("remove", null);
        } else {
            map.put("remove", "0");
        }
        
        List<CommentComplain> list =
            this.commentComplainService.findByParams(map);
        Page page = (Page) map.get("page");
        setJQGrid(list,
            page.getTotalCount(),
            search.getCurrentPage(),
            search.getPageSize(),
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改评价、投诉 〈功能详细描述〉
     * 
     * @param commentComplain CommentComplain
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(CommentComplain commentComplain)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, commentComplain);
        String userId = this.getUser().getId();
        this.createCommonVal(commentComplain, false, userId);
        this.commentComplainService.update(commentComplain);
        LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, commentComplain);
        controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
            CommonConstant.UPDATE_SUC_OPTION,
            commentComplain).recordLog();
        this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, commentComplain);
        
        // 发送响应消息
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看评价、投诉 〈功能详细描述〉
     * 
     * @param id id
     * @param srcGoodsId srcGoodsId
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}/{srcGoodsId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable String id,
        @PathVariable String srcGoodsId)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(srcGoodsId)) {
            Map<String, Object> map = new HashMap<String, Object>();
            CommentComplain commentComplain =
                this.commentComplainService.findById(id);
            SrcGoods srcGoods = this.srcGoodsService.findById(srcGoodsId);
            if (null != srcGoods && !srcGoods.getGoodsInfos().isEmpty()) {
                Collections.sort(srcGoods.getGoodsInfos(),
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
                                } else if (a - b < 0) {
                                    flag = -1;
                                }
                            }
                            
                            return flag;
                        }
                    });
            }
            
            map.put("commentComplain", commentComplain);
            map.put("srcGoods", srcGoods);
            
            this.sendData(map, CommonConstant.FIND_SUC_OPTION);
        } else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉根据法人代码查看评价平均分 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAvgScore/{corporateNo}", method = RequestMethod.GET)
    @ResponseBody
    public Object findAvgScore(@PathVariable String corporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(corporateNo)) {
            Object val = this.commentComplainService.findAvgScore(corporateNo);
            
            this.sendData(val, CommonConstant.FIND_SUC_OPTION);
        } else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.commentComplainService;
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
