/*
 * 文件名：ResultController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年6月12日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.c503.sc.base.entity.Page;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.response.ResultJQGrid;
import com.c503.sc.utils.response.ResultMessage;

/**
 * 〈一句话功能简述〉 结果响应的控制层 〈功能详细描述〉
 * 
 * @author chenl
 * @version [版本号, 2015年 6月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressWarnings("rawtypes")
public abstract class ResultController extends BaseController {
    /** Controller中结果响应实体类的信息 */
    private ResultMessage message;
    
    /**
     * 
     * 〈一句话功能简述〉发送消息码以及数据至前端 〈功能详细描述〉主要用于下拉框查询、者表单验证不通过时数据的快捷响应入口
     * 
     * @param data 发送到前端的数据对象
     * @param msgCode 消息码
     * @param args 消息阵列
     * @return ResultMessage
     * @see [类、类#方法、类#成员]
     */
    protected ResultMessage sendData(Object data, int msgCode, Object... args) {
        message = sendCode(msgCode, args);
        message.setData(data);
        return message;
    }
    
    /**
     * 
     * 〈一句话功能简述〉发送前台响应消息码 〈功能详细描述〉
     * 
     * @param msgCode 消息码
     * @param args 消息参数
     * @return ResultMessage
     * @see [类、类#方法、类#成员]
     */
    protected ResultMessage sendCode(int msgCode, Object... args) {
        changeResult(ResultMessageType.MESSAGE);
        message.setCode(Integer.valueOf(String.valueOf(msgCode).substring(0, 1)));
        // 获取消息
        String msg = ResourceManager.getMessage(String.valueOf(msgCode), args);
        message.setMsg(msg);
        
        return message;
    }
    
    /**
     * 
     * 〈一句话功能简述〉发送前台响应消息码 〈功能详细描述〉
     * 
     * @param msgCode 1, 2, 3
     * @param msg 错误消息
     * @return ResultMessage
     * @see [类、类#方法、类#成员]
     */
    protected ResultMessage sendCode(int msgCode, String msg) {
        changeResult(ResultMessageType.MESSAGE);
        message.setCode(msgCode);
        message.setMsg(msg);
        
        return message;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 一次查询所有数据 无分页 〈功能详细描述〉
     * 
     * @param msgCode 消息码
     * @param data 数据
     * @return jqgrid插件结果集对象
     * @see [类、类#方法、类#成员]
     */
    protected ResultJQGrid setJQGridNoPage(int msgCode, Object data) {
        int size = 0;
        
        if (data instanceof Collection) {
            size = ((Collection) data).size();
        }
        
        return setJQGrid(data, size, 1, size, msgCode);
    }
    
    /**
     * 〈一句话功能简述〉除非必须使用此方法，否则推荐优先使用 setJQGrid(Object data, int records, int page)
     * 方法 〈功能详细描述〉
     * 
     * @param data 数据
     * @param records 总数（total）
     * @param page 当前页数
     * @param num 每页显示行数
     * @param sendCode 状态码
     * @return jqgrid插件结果集对象
     * @see [类、类#方法、类#成员]
     */
    protected ResultJQGrid setJQGrid(Object data, int records, int page,
        int num, int sendCode) {
        changeResult(ResultMessageType.JQGRID);
        sendCode(sendCode);
        ResultJQGrid jqGrid = (ResultJQGrid) message;
        jqGrid.setRecords(records);
        jqGrid.setRows(data);
        jqGrid.setPage(page);
        jqGrid.setNum(num);
        return jqGrid;
    }
    
    /**
     * 〈一句话功能简述〉除非必须使用此方法，否则推荐优先使用 setJQGrid(Object data, int records, int page)
     * 方法 〈功能详细描述〉
     * 
     * @param data 数据
     * @param page 当前页数
     * @return jqgrid插件结果集对象
     * @see [类、类#方法、类#成员]
     */
    protected ResultJQGrid setJQGrid(Object data, Page page) {
        changeResult(ResultMessageType.JQGRID);
        sendCode(CommonConstant.FIND_SUC_OPTION);
        ResultJQGrid jqGrid = (ResultJQGrid) message;
        jqGrid.setRecords(page.getTotalPage());
        jqGrid.setRows(data);
        jqGrid.setPage(page.getCurrentPage());
        jqGrid.setNum(page.getPageSize());
        return jqGrid;
    }
    
    /**
     * 〈一句话功能简述〉发送在Controller中结果响应实体信息 〈功能详细描述〉
     * 
     * @return ResultMessage
     * @see [类、类#方法、类#成员]
     */
    protected ResultMessage sendMessage() {
        return message;
    }
    
    /**
     * 〈一句话功能简述〉改变结果 〈功能详细描述〉
     * 
     * @param change 响应结果对象类型
     * @see [类、类#方法、类#成员]
     */
    private void changeResult(ResultMessageType change) {
        switch (change) {
            case JQGRID:
                
                if (message == null || !(message instanceof ResultJQGrid)) {
                    message = new ResultJQGrid();
                }
                
                break;
            case MESSAGE:
                
                if (message == null) {
                    message = new ResultMessage();
                }
                
                break;
            default:
                break;
        }
    }
    
    /**
     * 
     * 〈一句话功能简述〉 响应结果对象类型 〈功能详细描述〉
     * 
     * @author chenl
     * @version [版本号, 2015年6月9日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public enum ResultMessageType {
        /** 索引值 */
        MESSAGE(0), JQGRID(1);
        /** 枚举中值的索引 */
        private int type;
        
        /**
         * 
         * 〈一句话功能简述〉构造器 〈功能详细描述〉传入索引创建实例
         * 
         * @param type 枚举中值的索引
         * @see [类、类#方法、类#成员]
         */
        private ResultMessageType(int type) {
            this.type = type;
        }
        
        /**
         * 
         * 〈一句话功能简述〉获取类型中枚举中值的索引 〈功能详细描述〉
         * 
         * @return 类型中枚举中值的索引
         * @see [类、类#方法、类#成员]
         */
        public int getType() {
            return type;
        }
    }
    
    /**
     * 〈一句话功能简述〉记录form表单验证错误信息
     * 〈功能详细描述〉
     * 
     * @param logger LoggingManager
     * @param object Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    protected void loggerFormValidate(LoggingManager logger, Object object)
        throws Exception {
        this.sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
        logger.info(CommonConstant.FORMVALID_FAIL_OPTION, object);
        this.controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
            CommonConstant.FORMVALID_FAIL_OPTION,
            getValidErorrs())
            .setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
            .recordLog();
    }
}
