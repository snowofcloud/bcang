///**
// * 文件名：TerminalServiceImpl.java
// * 版权： 航天恒星科技有限公司
// * 描述：〈描述〉
// * 修改时间：2016-7-28
// * 修改内容：〈修改内容〉
// */
//package com.c503.sc.jxwl.zcpt.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.c503.sc.jxwl.zcpt.service.IPostTerminalInfoService;
//
///**
// * 
// * 〈一句话功能简述〉
// * 〈功能详细描述〉
// * 
// * @author qianxq
// * @version [版本号, 2016-7-26]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//@Deprecated
//@Service(value = "postTerminalInfoServic")
//public class PostTerminalInfoServiceImpl implements IPostTerminalInfoService {
//    
//    @Override
//    public String inputToLocationPlatform(String param, String flag)
//        throws Exception {
//        final String action = "TerminalInfor.action";
//        
//        SendHttpRequest sendHttpRequest = new SendHttpRequest();
//        return sendHttpRequest.doPost(action, param + "&flag=" + flag);
//    }
//    
//    @Override
//    public String setTerminalParam(String param)
//        throws Exception {
//        final String action = "terminalparaset.action";
//        
//        SendHttpRequest sendHttpRequest = new SendHttpRequest();
//        return sendHttpRequest.doPost(action, param);
//    }
//    
//    @Override
//    public String registerTerminal(String param)
//        throws Exception {
//        final String action = "terminalRegister.action";
//        
//        SendHttpRequest sendHttpRequest = new SendHttpRequest();
//        return sendHttpRequest.doPost(action, param);
//    }
//    
//    @Override
//    public String deleteTerminal(String terminalId)
//        throws Exception {
//        final String action = "deleteTerminal.action";
//        SendHttpRequest sendHttpRequest = new SendHttpRequest();
//        String response =
//            sendHttpRequest.doPost(action, "terminalSerialID=" + terminalId);
//        return response;
//    }
//    
//    @Override
//    public String cancelTerminal(String terminalId)
//        throws Exception {
//        final String action = "cancelTerminal.action";
//        SendHttpRequest sendHttpRequest = new SendHttpRequest();
//        String response =
//            sendHttpRequest.doPost(action, "terminalSerialID=" + terminalId);
//        return response;
//    }
//    
//    @Override
//    public String upgrade(String param)
//        throws Exception {
//        final String action = "upgradeTerminal.action";
//        SendHttpRequest sendHttpRequest = new SendHttpRequest();
//        String response = sendHttpRequest.doPost(action, param);
//        return response;
//    }
//}
