/**
 * 文件名：EmergencyInfoServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EmergencyEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEmergencyInfoDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IEmergencyInfoService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.NumberContant;
import com.c503.sc.utils.common.SystemContants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 
 * 〈一句话功能简述〉突发信息业务实现
 * 〈功能详细描述〉
 * 
 * @author yuanyl
 * @version [版本号, 2016-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "emergencyInfoService")
public class EmergencyInfoServiceImpl implements IEmergencyInfoService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoServiceImpl.class);
    
    /** emergencyInfoDao */
    @Resource(name = "emergencyInfoDao")
    private IEmergencyInfoDao emergencyInfoDao;
    
    
    /** miPushService的实例 */
	@Resource(name = "miPushService")
	private IMiPushService miPushService;
    
    @Override
    public Object save(EmergencyEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        entity.setId(C503StringUtils.createUUID());
        try {
            this.emergencyInfoDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    @Override
    public List<EmergencyEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<EmergencyEntity> result = null;
        map.put("remove", SystemContants.ON);
        try {
            result = this.emergencyInfoDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    @Override
    public EmergencyEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        EmergencyEntity result = null;
        try {
            List<EmergencyEntity> tempResult =
                this.emergencyInfoDao.findById(id);
            if (tempResult.size() > 0) {
                result = tempResult.get(0);
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return result;
    }
    
    
    @Override
    public int update(EmergencyEntity emergency)
    	throws Exception {
	    LOGGER.debug(SystemContants.DEBUG_START, emergency);
	    int upLine = 0;
	    try {
	    	upLine = this.emergencyInfoDao.update(emergency);
	    }
	    catch (Exception e) {
	    	throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
	    			emergency);
	    }
	    LOGGER.debug(SystemContants.DEBUG_END, emergency);
	    return upLine;
    }
     
    @Override
    public Object delete(EmergencyEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        int result = 0;
        try {
            result = this.emergencyInfoDao.delete(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return result;
    }

	@Override
	public void miPushEmergency(EmergencyEntity entity)  {
		// 得到messagePayload
		Map<String, Object> map = this.getMsg(entity);
		String messagePayload = this.miPushService.getPayload(map, "2");
		// 得到message
		String title="临时消息";
		String des=  entity.getTitle();
		Message message = null;
		try {
			message = this.miPushService.messageHandle(title, des,
					messagePayload, false);
			// 发送message 并得到result
			Result result = this.miPushService.broadcastAll(message, NumberContant.FIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * 〈一句话功能简述〉封装msg 〈功能详细描述〉
	 * 
	 * @param entity
	 *            entity
	 * @return Map<String, Object>
	 * @see [类、类#方法、类#成员]
	 */
	private Map<String, Object> getMsg(EmergencyEntity entity){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "1");
		map.put("user", "user");
		map.put("num", "1234567");
		map.put("title", entity.getTitle());
		map.put("source", entity.getSource());
		map.put("content", entity.getContent());
		map.put("releaseTime",
				C503DateUtils.dateToStr(new Date(), "YYYY-MM-dd HH:mm:ss"));
		map.put("validTime", entity.getValidTime());
		map.put("publishDate", C503DateUtils.dateToStr(entity.getCreateTime(), 
				"YYYY-MM-dd HH:mm:ss"));
		return map;
	}
    
}
