package com.c503.sc.jxwl.pipelinemangnt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.CommonConstants;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.util.network.JXWLHttpClientUtils;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineLocationEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineStatusEntity;
import com.c503.sc.jxwl.pipelinemangnt.dao.IPipeLineDao;
import com.c503.sc.jxwl.pipelinemangnt.service.IPipeLineService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 〈一句话功能简述〉 PipeLineServiceImpl
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2017-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "pipeLineService")
public class PipeLineServiceImpl implements IPipeLineService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(PipeLineServiceImpl.class);
    
    /** 管道/储罐Dao */
   @Resource(name = "pipeLineDao")
   /* @Autowired
    @Qualifier(name = "pipeLineDao")*/
    private IPipeLineDao pipeLineDao;
    
    private String uri = "";
    
    private String ak = "";
    
    private String start = "";
    
    private String end = "";
    
    private String m = "";
    
    public PipeLineServiceImpl() {
        super();
        init();
    }
    
    /**
     * 初始化公共应用平台配置
     */
    private void init() {
        try {
            Properties cfg = new Properties();
            InputStream is =
                PipeLineServiceImpl.class.getResourceAsStream("/ggyypt-cfg.properties");
            cfg.load(is);
            uri = cfg.getProperty("ggyypt-gz-uri");
            ak = cfg.getProperty("ggyypt-gz-ak");
            start = cfg.getProperty("ggyypt-gz-start");
            end = cfg.getProperty("ggyypt-gz-end");
            m = cfg.getProperty("ggyypt-gz-m");
        }
        catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(CommonConstants.SYS_EXCEPTION, e, "读取报警描述的配置失败");
        }
    }
    
    @Override
    public List<PipeLineEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        List<PipeLineEntity> list = this.pipeLineDao.findByParams(map);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
    
    @Override
    public PipeLineEntity findById(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        PipeLineEntity pipeLine = this.pipeLineDao.findById(map);
        LOGGER.debug(SystemContants.DEBUG_END, pipeLine);
        return pipeLine;
    }
    
    @Override
    public List<PipeLineLocationEntity> findPipeLocations(
        Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        List<PipeLineLocationEntity> list =
            this.pipeLineDao.findPipeLocations(map);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
    
    @Override
    public List<PipeLineStatusEntity> findLiquid(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        String pipeType = (String) map.get("pipeType");
        String pipeid = (String) map.get("pipeid");
        map.put("remove", SystemContants.ON);
        List<PipeLineStatusEntity> list = new ArrayList<PipeLineStatusEntity>();
        JSONObject data = getLiquid(pipeid);
        if (data != null) {
            Set<String> keys = data.keySet();
            if (keys != null) {
                for (String key : keys) {
                    Double val = data.getDouble(key);
                    long ut = Long.parseLong(key);
                    val = val == null ? 0 : val;
                    PipeLineStatusEntity pipe = new PipeLineStatusEntity();
                    pipe.setPipeid(pipeid);
                    pipe.setPipeType(pipeType);
                    pipe.setUpdateTime(new Date(ut));
                    if (pipeType.equalsIgnoreCase("1")) {// 管道
                        pipe.setFlow((int) Math.round(val));
                    }
                    else if (pipeType.equalsIgnoreCase("2")) {// 储罐
                        val = Math.round(val * 100) / 100.0;
                        pipe.setLiquiDlevel(val + "");
                    }
                    list.add(pipe);
                }
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
    
    /**
     * 获取管道或储罐采集数据的统计信息
     * 
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    private JSONObject getLiquid(String id)
        throws UnsupportedEncodingException {
        String url = uri + "?AK=" + URLEncoder.encode(ak, "UTF-8");
        if (StringUtils.isNotEmpty(start)) {
            url += "&start=" + URLEncoder.encode(start, "UTF-8");
        }
        if (StringUtils.isNotEmpty(end)) {
            url += "&end=" + URLEncoder.encode(end, "UTF-8");
        }
        if (StringUtils.isNotEmpty(m)) {
            m = m.replaceAll("\\{SID\\}", id);
            url += "&m=" + URLEncoder.encode(m, "UTF-8");
        }
        
        try {
            String str = JXWLHttpClientUtils.doGet(url);
            if (StringUtils.isEmpty(str)) {
                CustomException ce =
                    new CustomException(CommonConstant.SYS_EXCEPTION, url);
                ce.setErrorMessage("公共应用服务平台接口响应信息为空");
                LOGGER.error(CommonConstant.SYS_EXCEPTION, ce);
                return null;
            }
            JSONObject json = JSON.parseObject(str);
            Integer code = json.getInteger("statusCode");
            if (code == null || code != 200) {
                CustomException ce =
                    new CustomException(CommonConstant.SYS_EXCEPTION, url);
                ce.setErrorMessage(json.toJSONString());
                LOGGER.error(CommonConstant.SYS_EXCEPTION, ce);
                return null;
            }
            String content = json.getString("content");
            if (StringUtils.isNotEmpty(content)) {
                JSONArray arr = JSON.parseArray(content);
                if (arr != null && arr.size() >= 0) {
                    JSONObject obj = arr.getJSONObject(0);
                    if (obj != null) {
                        return obj.getJSONObject("dps");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
