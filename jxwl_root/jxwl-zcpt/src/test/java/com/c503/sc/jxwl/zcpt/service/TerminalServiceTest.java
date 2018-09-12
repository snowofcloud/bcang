/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.c503.sc.jxwl.zcpt.base.BaseTest;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalUpgrade;
import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
import com.c503.sc.jxwl.zcpt.service.impl.TerminalServiceImpl;
import com.c503.sc.utils.basetools.C503StringUtils;

public class TerminalServiceTest extends BaseTest {
    @InjectMocks
    private TerminalServiceImpl terminalService = new TerminalServiceImpl();
    
    @Mock
    @Resource(name = "carInfoDao")
    private ICarInfoDao carInfoDao;
    
    LimitArea limitArea = null;
    
    TerminalEntity terminal = new TerminalEntity();
    
    TerminalUpgrade terup = new TerminalUpgrade();
    
    String pk = null;
    
    List<NameValuePair> params = new ArrayList<>();
    
    Class<TerminalServiceImpl> clazz = TerminalServiceImpl.class;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        // terminalService.setUrl("http://172.25.1.114:8080/cast503_lbs/");
        
        terminal.setId(C503StringUtils.createUUID());
        terminal.setBalance("balance");
        terminal.setCarcolor("蓝色");
        terminal.setCardNum("18328644399");
        terminal.setCarrierName("浙FJ1237");
        terminal.setExpenses("500");
        terminal.setManufactureID("9876543210");
        terminal.setNextrepairDate("2017-11-11");
        terminal.setOperator("operator");
        terminal.setProductionDate("2011-12-12");
        terminal.setRegisterDate("2015-10-10");
        terminal.setSimPass("111111");
        terminal.setTerminalSerialID("65536639901");
        
        terup.setAddress("192.168.1.100");
        terup.setAuthenticationNo("007");
        terup.setCommandWord("orderWord");
        terup.setConnCtrl("connectCtrl");
        terup.setDialPassword("122334");
        terup.setDialPointName("upgrade");
        terup.setDialUserName("who");
        terup.setFirmwareVersion("001");
        terup.setHardwareVersion("1.0");
        terup.setManufactureID("22222222");
        terup.setTcpPort("8000");
        terup.setTerminalSerialID("xxxxxx");
        terup.setTerminalTelNo("19329466321");
        terup.setUrl("http://www.baidu.com");
    }
    
    @Test
    public void testFindByParams() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");// 查询第几页
        map.put("rows", "10");// 每页条数
        map.put("manufactureID", "1369703201");// 制造商ID
        map.put("terminalSerialID", "196596231");// 厂商终端ID
        map.put("carrierName", "浙A55555");// 车牌号
        map.put("simNum", "18328644391");// 手机号
        
        try {
            // map 内存在字段
            this.terminalService.findByParams(map);
            
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        try {
            // map 内存在字段
            map.clear();
            this.terminalService.findByParams(map);
            
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testSave() {
        // terminal 不空 carrierName存在
        String carrierName = "浙FJ2977";
        try {
            PowerMockito.when(this.carInfoDao.carrierNameIsExist(carrierName))
                .thenReturn(2);
            this.terminalService.save(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        // terminal 不空 carrierName不存在
        try {
            terminal.setCarrierName("浙A123456");
            this.terminalService.save(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        // terminal 空
        try {
            TerminalEntity terminal = null;
            this.terminalService.save(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testBind()
        throws Exception {
        // 终端为NULL
        try {
            this.terminalService.bind(null);
        }
        catch (Exception e) {
            e.getMessage();
        }
        // 终端不为null
        // 终端carrierName不存在
        try {
            this.terminalService.bind(terminal);
            // 终端carrierName存在
            PowerMockito.when(this.carInfoDao.carrierNameIsExist(terminal.getCarrierName()))
                .thenReturn(2);
            this.terminalService.bind(terminal);
            // 终端来源为app
            terminal.setTerminalSource("app");
            this.terminalService.bind(terminal);
            // 通过findByCarrierNameAndcardNum查询出来的终端不为null
            PowerMockito.when(this.terminalService.findByCarrierNameAndcardNum(terminal.getCarrierName(),
                this.terminalService.fillSimCard(terminal.getCardNum())))
                .thenReturn(terminal);
            this.terminalService.bind(terminal);
            // 通过findByCarrierName查询为不为null
            PowerMockito.when(this.terminalService.findByCarrierName(terminal.getCarrierName()))
                .thenReturn(terminal);
            this.terminalService.bind(terminal);
            // 通过findBySimNum查询为不为null
            PowerMockito.when(this.terminalService.findBySimNum(this.terminalService.fillSimCard(terminal.getCardNum())))
                .thenReturn(terminal);
            this.terminalService.bind(terminal);
            // 通过findByCarrierName查询为为null
            PowerMockito.when(this.terminalService.findByCarrierName(terminal.getCarrierName()))
                .thenReturn(null);
            this.terminalService.bind(terminal);
        }
        catch (Exception e) {
            e.getMessage();
        }
        
    }
    
    @Test
    public void testUpdate() {
        // terminal 不空
        try {
            this.terminalService.update(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // terminal 空
        try {
            TerminalEntity terminal = null;
            this.terminalService.update(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testFindById() {
        // id 不空
        try {
            this.terminalService.findById(terminal.getId());
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // id 空
        try {
            String id = null;
            this.terminalService.findById(id);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void findVal()
        throws Exception {
        Map<String, String> map = null;
        Method m = clazz.getDeclaredMethod("findVal", Map.class);
        m.setAccessible(true);
        m.invoke(terminalService, map);
    }
    
    @Test
    public void updateVal()
        throws Exception {
        TerminalEntity terminalEntity = null;
        Method m = clazz.getDeclaredMethod("updateVal", TerminalEntity.class);
        m.setAccessible(true);
        m.invoke(terminalService, terminalEntity);
    }
    
    @Test
    public void findALLVal()
        throws Exception {
        Map<String, String> map = null;
        Method m = clazz.getDeclaredMethod("findALLVal", Map.class);
        m.setAccessible(true);
        m.invoke(terminalService, map);
        map = new HashMap<String, String>();
        map.put("manufactureID", null);
        m.invoke(terminalService, map);
        map.put("manufactureID", "id");
        m.invoke(terminalService, map);
        map.put("terminalSerialID", null);
        m.invoke(terminalService, map);
        map.put("terminalSerialID", "sid");
        m.invoke(terminalService, map);
        map.put("carrierName", null);
        m.invoke(terminalService, map);
        map.put("carrierName", "川A2334");
        m.invoke(terminalService, map);
        map.put("simNum", null);
        m.invoke(terminalService, map);
        map.put("simNum", "1233");
        m.invoke(terminalService, map);
        map.put("page", null);
        m.invoke(terminalService, map);
        map.put("page", "1");
        m.invoke(terminalService, map);
        map.put("rows", null);
        m.invoke(terminalService, map);
        map.put("rows", "1");
        m.invoke(terminalService, map);
    }
    
    @Test
    public void testDelete() {
        // id 不空
        try {
            this.terminalService.delete(terminal.getTerminalSerialID());
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // id 空
        try {
            String id = null;
            this.terminalService.delete(id);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testUnbind() {
        try {
            this.terminalService.unbind(terminal.getTerminalSerialID());
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testGetCarHistoryLocation() {
        String startTime = "2016-11-29 19:11:58";
        String endTime = "2016-11-29 19:12:58";
        String carrierName = "川A11801";
        try {
            this.terminalService.getCarHistoryLocation(carrierName,
                startTime,
                endTime);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 车牌号为空
        try {
            carrierName = null;
            this.terminalService.getCarHistoryLocation(carrierName,
                startTime,
                endTime);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 开始时间为空
        try {
            carrierName = "川A11801";
            startTime = null;
            this.terminalService.getCarHistoryLocation(carrierName,
                startTime,
                endTime);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 结束时间为空
        try {
            startTime = "2016-11-29 19:11:58";
            endTime = null;
            this.terminalService.getCarHistoryLocation(carrierName,
                startTime,
                endTime);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testSetTerminalParam() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("overSpeedValue", "60");// 最高速度
            map.put("speedContinueValue", "4");// 超速持续时间
            map.put("fatigueDriveValue", "120");// 连续驾驶时间上限
            map.put("overtimeParkValue", "30");// 超时停车阈值
            this.terminalService.setTerminalParam(map);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // map 空
        try {
            Map<String, String> map = null;
            this.terminalService.setTerminalParam(map);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testUpgrade() {
        try {
            this.terminalService.upgrade(terup);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 空参
        try {
            TerminalUpgrade terminal = null;
            this.terminalService.upgrade(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testUpgradeAll() {
        try {
            this.terminalService.upgradeAll(terup);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 空参
        try {
            TerminalUpgrade terminal = null;
            this.terminalService.upgradeAll(terminal);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    
    
    @Test
    public void testfindByCarrierName() {
        try {
            String carrierName = "川A11801";
            this.terminalService.findByCarrierName(carrierName);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 空参
        try {
            String carrierName = null;
            this.terminalService.findByCarrierName(carrierName);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
    
    @Test
    public void testFillSimCard() {
        String[] simCards = {null, "", "18328644399", "018328644399"};
        for (String simCard : simCards) {
            try {
                Method m = clazz.getDeclaredMethod("fillSimCard", String.class);
                m.setAccessible(true);
                m.invoke(terminalService, simCard);
            }
            catch (Exception e) {
                e.getMessage();
                // e.printStackTrace();
            }
        }
    }
    
    @Test
    public void testSaveVal()
        throws Exception {
        // null
        Method m = clazz.getDeclaredMethod("saveVal", TerminalEntity.class);
        m.setAccessible(true);
        TerminalEntity entity = null;
        m.invoke(terminalService, entity);
        // !null
        m.invoke(terminalService, terminal);
        
    }
    
    @Test
    public void testHandleException()
        throws Exception {
        String result = "002";
        Map<String, String> expMsgs = new HashMap<>();
        Method m =
            clazz.getDeclaredMethod("handleException", String.class, Map.class);
        m.setAccessible(true);
        try {
            m.invoke(terminalService, result, expMsgs);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        try {
            expMsgs.put(result, "field error");
            m.invoke(terminalService, result, expMsgs);
        }
        catch (Exception e) {
        }
        
        try {
            result = "\"001\"";
            m.invoke(terminalService, result, expMsgs);
        }
        catch (Exception e) {
        }
        
    }
    
    @Test
    public void getAllTerminalTel()
        throws Exception {
        this.terminalService.getAllTerminalTel();
    }
    
    @Test
    public void findBySimNum()
        throws Exception {
        try {
            this.terminalService.findBySimNum("0123455655");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.terminalService.findBySimNum(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void cardNumToTerminalSerialID()
        throws Exception {
        Method m = null;
        try {
            m =
                clazz.getDeclaredMethod("cardNumToTerminalSerialID",
                    String.class);
            m.setAccessible(true);
            m.invoke(terminalService, "01234567891");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            m.invoke(terminalService, "33");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void isNumeric()
        throws Exception {
        this.terminalService.isNumeric("0123455655");
        this.terminalService.isNumeric("01234ddd55655");
        this.terminalService.isNumeric(null);
    }
    
    @Test
    public void carrierNameIsExist()
        throws Exception {
        this.terminalService.carrierNameIsExist("黑A12345");
    }
    
    @Test
    public void bindVal()
        throws Exception {
        TerminalEntity terminal = new TerminalEntity();
        terminal.setTerminalSerialID("D333");
        terminal.setCarcolor("red");
        terminal.setCarrierName("黑A12345");
        try {
            Method m = clazz.getDeclaredMethod("bindVal", TerminalEntity.class);
            m.setAccessible(true);
            m.invoke(terminalService, terminal);
        }
        catch (Exception e) {
            e.getMessage();
        }
    }
    
    @Test
    public void testFindByCarrierNameAndcardNum()
        throws Exception {
        this.terminalService.findByCarrierNameAndcardNum("川A11801",
            "13604237664");
        try {
            this.terminalService.findByCarrierNameAndcardNum(null,
                "13604237664");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
}