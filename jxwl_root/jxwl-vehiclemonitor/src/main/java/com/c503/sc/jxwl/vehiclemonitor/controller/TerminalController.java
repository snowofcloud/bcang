/**
 * 文件名：TerminalController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalParamEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalUpgrade;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉终端信息controller
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/terminal")
public class TerminalController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(TerminalController.class);
    
    /** 终端业务处理 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /** 车辆service */
    @Resource
    private IDangerVehicleService dangerVehicleService;
    
    /**
     * 
     * 〈一句话功能简述〉分页查询终端
     * 〈功能详细描述〉
     * 
     * @param manufactureID manufactureID
     * @param terminalSerialID terminalSerialID
     * @param carrierName carrierName
     * @param simNum 手机号
     * @param page 页数
     * @param rows 行数
     * @return 返回数据信息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String manufactureID, String terminalSerialID,
        String carrierName, String simNum,
        @RequestParam(value = "page", defaultValue = "1")
        Integer page, @RequestParam(value = "rows", defaultValue = "10")
        Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "manufactureID = "
            + manufactureID + "terminalSerialID = " + terminalSerialID
            + "carrierName = " + carrierName);
        manufactureID =
            StringUtils.isNotEmpty(manufactureID) ? manufactureID.trim() : null;
        terminalSerialID =
            StringUtils.isNotEmpty(terminalSerialID) ? terminalSerialID.trim()
                : null;
        simNum = StringUtils.isNotEmpty(simNum) ? simNum.trim() : null;
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page.toString());
        map.put("rows", rows.toString());
        // 制造商ID
        map.put("manufactureID", manufactureID);
        // 厂商终端ID
        map.put("terminalSerialID", terminalSerialID);
        // 车牌号
        map.put("carrierName", carrierName);
        // 手机号
        map.put("simNum", simNum);
        
        PageEntity<com.c503.sc.jxwl.zcpt.bean.TerminalEntity> pageEntity =
            this.terminalService.findByParams(map);
        
        // 若是非政府企业：查看自己企业的终端
        List<String> roleCodes = this.getUser().getRoleCodes();
        if (null != roleCodes && 0 < roleCodes.size()
            && !roleCodes.contains(DictConstant.GOVERNMENT_USER)) {
            List<String> carNames =
                dangerVehicleService.findCarNamesBycorporateNo(this.getUser()
                    .getCorporateCode());
            List<TerminalEntity> list = new ArrayList<>();
            Collection<TerminalEntity> ters = pageEntity.getRows();
            for (TerminalEntity ter : ters) {
                String carName = ter.getCarrierName();
                if (carNames.contains(carName)) {
                    list.add(ter);
                }
            }
            pageEntity.setTotalCount(list.size());
            setJQGrid(list,
                pageEntity.getTotalCount(),
                page,
                rows,
                CommonConstant.FIND_SUC_OPTION);
        }
        else {
            setJQGrid(pageEntity.getRows(),
                pageEntity.getTotalCount(),
                page,
                rows,
                CommonConstant.FIND_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉终端开户
     * 〈功能详细描述〉
     * 
     * @param terminal TerminalEntity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(TerminalEntity terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (null != terminal) {
            String cardNum = terminal.getCardNum();
            int len = cardNum.length();
            while (len < NumberContant.ONE_TWO) {
                cardNum = "0" + cardNum;
                ++len;
            }
            terminal.setCardNum(cardNum);
            terminalService.save(terminal);
            this.sendCode(CommonConstant.OPEN_SUC_OPTION);
        }
        else {
            throw new CustomException(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉终端注册(绑定)
     * 〈功能详细描述〉
     * 
     * @param terminal TerminalEntity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(TerminalEntity terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        // 绑定操作来自于pc端
        terminal.setTerminalSource("pc");
        terminalService.bind(terminal);
        this.sendCode(CommonConstant.REGISTER_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉修改终端开户
     * 〈功能详细描述〉
     * 
     * @param terminal TerminalEntity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(TerminalEntity terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (null != terminal) {
            String cardNum = terminal.getCardNum();
            int len = cardNum.length();
            while (len < NumberContant.ONE_TWO) {
                cardNum = "0" + cardNum;
                ++len;
            }
            terminal.setCardNum(cardNum);
            com.c503.sc.jxwl.zcpt.bean.TerminalEntity t =
                terminalService.update(terminal);
            
            // 4、打印日志
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, t);
            this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPDATE_SUC_OPTION,
                t).recordLog();
            this.sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        else {
            throw new CustomException(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除
     * 〈功能详细描述〉
     * 
     * @param terminalSerialID terminalSerialID
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable(value = "id")
    String terminalSerialID)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(terminalSerialID)) {
            terminalService.delete(terminalSerialID);
            this.sendCode(CommonConstant.DELETE_SUC_OPTION);
        }
        else {
            throw new CustomException(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉注销（解绑）
     * 〈功能详细描述〉
     * 
     * @param terminalSerialID terminalSerialID
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/cancel/{id}")
    @ResponseBody
    public Object cancel(@PathVariable(value = "id")
    String terminalSerialID)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(terminalSerialID)) {
            terminalService.unbind(terminalSerialID);
            this.sendCode(CommonConstant.DELETE_SUC_OPTION);
        }
        else {
            throw new CustomException(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询终端 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            TerminalEntity terminal = terminalService.findById(id);
            if (null != terminal) {
                String cardNum = terminal.getCardNum();
                if (StringUtils.isNotEmpty(cardNum) && cardNum.startsWith("0")) {
                    terminal.setCardNum(cardNum.substring(1, cardNum.length()));
                }
            }
            this.sendData(terminal, CommonConstant.FIND_SUC_OPTION);
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
     * 〈一句话功能简述〉通过id查询终端参数设置 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findTerminalParamById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findTerminalParamById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            TerminalParamEntity terminal = null;
            try {
                terminal = terminalService.findTerminalParamById(id);
            }
            catch (Exception e) {
                this.sendCode(CommonConstant.FINDPARAM_FAIL_OPTION);
                return sendMessage();
            }
            terminal = terminal == null ? new TerminalParamEntity() : terminal;
            this.sendData(terminal, CommonConstant.FIND_SUC_OPTION);
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
     * 〈一句话功能简述〉设置终端参数 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/setTerminalParam/", method = RequestMethod.POST)
    @ResponseBody
    public Object setTerminalParam(TerminalParamEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        
        try {
            entity.setId(C503StringUtils.createUUID());
            terminalService.setTerminalParam(entity);
            this.sendCode(CommonConstant.SETPARAM_SUC_OPTION);
        }
        catch (CustomException ce) {
            ce.printStackTrace();
            this.sendCode(NumberContant.TWO, ce.getErrorMessage());
            // return sendMessage();
        }
        catch (Exception e) {
            e.printStackTrace();
            this.sendCode(CommonConstant.SETPARAM_FAIL_OPTION);
            // return sendMessage();
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉终端升级
     * 〈功能详细描述〉
     * 
     * @param terminal TerminalUpgrade
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.POST)
    @ResponseBody
    public Object upgrade(TerminalUpgrade terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        
        if (null == terminal) {
            // 传入参数无效
            this.sendCode(CommonConstant.ARGS_INVALID, terminal);
        }
        else {
            this.terminalService.upgrade(terminal);
            // 记录操作成功信息
            LOGGER.info(CommonConstant.UPGRADE_SUC_OPTION);
            // 保存操作日志 记录操作成功
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPGRADE_SUC_OPTION).recordLog();
            this.sendCode(CommonConstant.UPGRADE_SUC_OPTION);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉终端一键升级
     * 〈功能详细描述〉
     * 
     * @param terminal TerminalUpgrade
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/upgradeAll", method = RequestMethod.POST)
    @ResponseBody
    public Object upgradeAll(TerminalUpgrade terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (null != terminal) {
            this.terminalService.upgradeAll(terminal);
            this.sendCode(1, "upgrade all success!");
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, terminal);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉导出
     * 〈功能详细描述〉
     * 
     * @param manufactureID manufactureID
     * @param terminalSerialID terminalSerialID
     * @param carrierName carrierName
     * @param simNum simNum
     * @param response response
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export2Excel(String manufactureID, String terminalSerialID,
        String carrierName, String simNum, HttpServletResponse response,
        HttpServletRequest request)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        try {
            carrierName = URLDecoder.decode(carrierName, "UTF-8");
            carrierName =
                new String(carrierName.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (Exception e) {
            LOGGER.info(NumberContant.THREE, "decode carrierName exception!");
        }
        Map<String, String> map = new HashMap<String, String>();
        // 制造商ID
        map.put("manufactureID", manufactureID);
        // 厂商终端ID
        map.put("terminalSerialID", terminalSerialID);
        // 车牌号
        map.put("carrierName", carrierName);
        // 手机号
        map.put("simNum", simNum);
        
        PageEntity<TerminalEntity> pageEntity =
            this.terminalService.findByParams(map);
        if (null != pageEntity && 0 < pageEntity.getRows().size()) {
            List<TerminalEntity> list =
                (List<TerminalEntity>) pageEntity.getRows();
            // 若是非政府企业：查看自己企业的终端
            List<String> roleCodes = this.getUser().getRoleCodes();
            if (null != roleCodes && 0 < roleCodes.size()
                && !roleCodes.contains(DictConstant.GOVERNMENT_USER)) {
                List<String> carNames =
                    dangerVehicleService.findCarNamesBycorporateNo(this.getUser()
                        .getCorporateCode());
                List<TerminalEntity> list2 = new ArrayList<>();
                Collection<TerminalEntity> ters = pageEntity.getRows();
                for (TerminalEntity ter : ters) {
                    String carName = ter.getCarrierName();
                    if (carNames.contains(carName)) {
                        list2.add(ter);
                    }
                }
                list = list2;
            }
            
            for (TerminalEntity terminal : list) {
                String carname = terminal.getCarrierName();
                String terminalState = terminal.getTerminalState();
                if (StringUtils.isNotEmpty(carname)) {
                    terminal.setTerRegisterState("已注册");
                }
                else {
                    terminal.setTerRegisterState("未注册");
                }
                if (StringUtils.equals("0", terminalState)) {
                    terminal.setTerminalState("在线");
                }
                else if (StringUtils.equals("1", terminalState)) {
                    terminal.setTerminalState("离线");
                }
                else {
                    terminal.setTerminalState("");
                }
            }
            
            ExportSheet sheet = this.createVal2Excel(list);
            C503ExcelUtils excelUtils = new C503ExcelUtils();
            excelUtils.addSheet(sheet);
            OutputStream out = response.getOutputStream();
            
            String excelName = "终端信息";
            // 设置响应文本类型
            response.setContentType("application;charset=gb2312");
            // 设置响应头
            response.setHeader("Content-Disposition", "attachment;filename="
                + compatibilityFileName(request, excelName, ".xls"));
            excelUtils.writeToStream(out);
            out.close();
            response.flushBuffer();
            
            LOGGER.info(CommonConstant.EXPORT_SUC_OPTION, sheet);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.EXPORT_SUC_OPTION,
                sheet).recordLog();
        }
        LOGGER.debug(SystemContants.DEBUG_END);
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return null;
    }

    /**
     * 〈一句话功能简述〉为Excel创建数据
     * 〈功能详细描述〉
     * 
     * @param list 终端配备情况list对象
     * @return 返回ExportSheet
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createVal2Excel(List<TerminalEntity> list) {
        // 创建所需要的对象
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Content content = new Content();
        
        // 表头字段
        Object[] headNames =
            new Object[] {"终端编号", "制造商ID", "车牌号", "车牌颜色", "注册日期", "下次检修日期",
                "出厂日期", "运营商", "SIM卡号", "SIM卡密码", "终端注册状态", "设备状态", "资费"};
        header.setHeadNames(headNames);
        header.setIsShowTitle(false);
        String[] fieldNames =
            new String[] {"terminalSerialID", "manufactureID", "carrierName",
                "carcolor", "registerDate", "nextrepairDate", "productionDate",
                "operator", "cardNum", "simPass", "terRegisterState",
                "terminalState", "expenses"};
        content.setFieldNames(fieldNames);
        
        content.setDataList(list);
        sheet.setContent(content);
        sheet.setHeader(header);
        
        return sheet;
    }
}
