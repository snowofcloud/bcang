/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.c503.sc.jxwl.common.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.bean.LogRemind;
import com.c503.sc.log.LoggingManager;

/**
 * 〈一句话功能简述〉Websocket
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ServerEndpoint(value = "/monitor.ws")
public class MonitorWebsocket {
    
    /** 日志器 */
    @SuppressWarnings("unused")
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(MonitorWebsocket.class);
    
    /** Set<MonitorWebsocket> */
    private static final Set<MonitorWebsocket> CONNECTIONS =
        new CopyOnWriteArraySet<MonitorWebsocket>();
    
    /** session */
    private Session session;
    
    /**
     * 〈一句话功能简述〉start
     * 〈功能详细描述〉
     * 
     * @param session Session
     * @see [类、类#方法、类#成员]
     */
    @OnOpen
    public void start(Session session) {
        this.session = session;
        CONNECTIONS.add(this);
    }
    
    /**
     * 〈一句话功能简述〉end
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    @OnClose
    public void end() {
        CONNECTIONS.remove(this);
    }
    
    /**
     * 〈一句话功能简述〉onMessage
     * 〈功能详细描述〉
     * 
     * @param message String
     * @see [类、类#方法、类#成员]
     */
    @OnMessage
    public void onMessage(String message) {
    }
    
    /**
     * 〈一句话功能简述〉onError
     * 〈功能详细描述〉
     * 
     * @param t Throwable
     * @throws Throwable Throwable
     * @see [类、类#方法、类#成员]
     */
    @OnError
    public void onError(Throwable t)
        throws Throwable {
        session.close();
    }
    
    /**
     * 〈一句话功能简述〉通知浏览器车辆最新位置信息
     * 〈功能详细描述〉
     * 
     * @param loc LocationEntity
     * @see [类、类#方法、类#成员]
     */
    public static void notifyLocation(LocationEntity loc) {
        if (loc != null) {
            JSONObject json = new JSONObject();
            json.put("type", "location");
            List<AlarmEntity> alarms = loc.getAlarms();
            // 不包括报警信息
            loc.setAlarms(null);
            json.put("data", loc);
            broadcast(json.toJSONString());
            // 复原
            loc.setAlarms(alarms);
        }
    }
    
    /**
     * 通知浏览器车辆最新的报警信息
     * 
     * @param alarm AlarmEntity
     */
    public static void notifyAlarm(AlarmEntity alarm) {
        if (alarm != null) {
            JSONObject json = new JSONObject();
            json.put("type", "alarm");
            json.put("data", alarm);
            broadcast(json.toJSONString());
        }
    }
    
    /**
     * 〈一句话功能简述〉通知浏览器车辆上线下线信息
     * 〈功能详细描述〉
     * 
     * @param map Map<String, List<LocationEntity>>
     * @see [类、类#方法、类#成员]
     */
    public static void notifyOnOff(Map<String, List<LocationEntity>> map) {
        List<LocationEntity> onNOticeList =
            (List<LocationEntity>) map.get("on");
        // 下线推送集合
        List<LocationEntity> offNOticeList =
            (List<LocationEntity>) map.get("off");
        
        if (onNOticeList.size() > 0) {
            JSONObject json = new JSONObject();
            json.put("type", "on");
            json.put("data", onNOticeList);
            broadcast(json.toJSONString());
        }
        if (offNOticeList.size() > 0) {
            JSONObject json = new JSONObject();
            json.put("type", "off");
            json.put("data", offNOticeList);
            broadcast(json.toJSONString());
        }
    }
    
    /**
     * 〈一句话功能简述〉向浏览器发送文本信息
     * 〈功能详细描述〉
     * 
     * @param msg msg
     * @see [类、类#方法、类#成员]
     */
    private static void broadcast(String msg) {
        for (MonitorWebsocket client : CONNECTIONS) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            }
            catch (IOException e) {
                CONNECTIONS.remove(client);
                try {
                    client.session.close();
                }
                catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        }
    }
    
    /**
     * 〈一句话功能简述〉通知浏览器app登录状态 websocket通知
     * 〈功能详细描述〉
     * 
     * @param loginState 登录状态 1为离线； 0位在线
     * @see [类、类#方法、类#成员]
     */
    public static void notifyLoginState(String loginState) {
        if (loginState != null) {
            JSONObject json = new JSONObject();
            json.put("type", "loginState");
            json.put("data", loginState);
            broadcast(json.toJSONString());
        }
    }
    
    /**
     * 〈一句话功能简述〉通知浏览器报警处理状态 websocket通知
     * 〈功能详细描述〉
     * 
     * @param loginState 登录状态 1为离线； 0位在线
     * @see [类、类#方法、类#成员]
     */
    public static void notifyAlarmDealStatus(AlarmEntity alarmEntity) {
        if (alarmEntity != null) {
            JSONObject json = new JSONObject();
            json.put("type", "alarmDealStatus");
            json.put("data", alarmEntity);
            broadcast(json.toJSONString());
        }
    }
    
    /**
     * 〈一句话功能简述〉订单日志提醒通知
     * 〈功能详细描述〉
     * 
     * @param logRemind 提醒信息
     * @see [类、类#方法、类#成员]
     */
    public static void notifyLogRemind(List<LogRemind> logRemind) {
        if (logRemind != null) {
            JSONObject json = new JSONObject();
            json.put("type", "logRemind");
            json.put("data", logRemind);
            broadcast(json.toJSONString());
        }
    }
    
}
