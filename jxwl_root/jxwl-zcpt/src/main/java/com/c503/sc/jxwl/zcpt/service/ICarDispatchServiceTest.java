/**
 * 文件名：ICarDispatchServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

public class ICarDispatchServiceTest {
    
    @Resource(name = "carDispatchService")
    private ICarDispatchService carDispatchService;
    
    @Test
    public void testSendTextMsg() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("terminalTel", "15536986532");
            map.put("mark", "2");
            map.put("content", "xxxxx");
            map.put("msgId", System.currentTimeMillis() + "");
            map.put("msgFlowId", System.currentTimeMillis() + "");
            map.put("carrierName", "浙A12345");
            this.carDispatchService.sendTextMsg(map);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
        
        // 空参
        try {
            Map<String, Object> map = null;
            this.carDispatchService.sendTextMsg(map);
        }
        catch (Exception e) {
            e.getMessage();
            // e.printStackTrace();
        }
    }
}
