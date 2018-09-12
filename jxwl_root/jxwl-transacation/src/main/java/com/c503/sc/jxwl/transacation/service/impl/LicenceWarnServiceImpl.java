/**
 * 文件名：EnterpriseLicenceServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.bean.LicenceFileRelaEntity;
import com.c503.sc.jxwl.transacation.dao.ILicenceFileRelaDao;
import com.c503.sc.jxwl.transacation.dao.ILicenceWarnDao;
import com.c503.sc.jxwl.transacation.service.ILicenceInfoService;
import com.c503.sc.jxwl.transacation.service.ILicenceWarnService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉订单管理 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "licenceWarnService")
public class LicenceWarnServiceImpl implements ILicenceWarnService {
	/** 日志器 */
	private static final LoggingManager LOGGER = LoggingManager
			.getLogger(LicenceWarnServiceImpl.class);

	/** 订单管理接口 */
	@Resource(name = "licenceWarnDao")
	private ILicenceWarnDao licenceWarnDao;

	/** 资质附件接口 */
	@Resource(name = "licenceFileRelaDao")
	private ILicenceFileRelaDao licenceFileRelaDao;
	
	/** 企业资质业务接口 */
	@Resource(name = "licenceInfoService")
	private ILicenceInfoService licenceInfoService;
	
	@Override
	public List<LicenceEntity> findByParams(Map<String, Object> map)
			throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		List<LicenceEntity> result = null;
		map.put("remove", SystemContants.ON);
		result = this.licenceWarnDao.findByParams(map);
		LOGGER.debug(SystemContants.DEBUG_END, map);
		return result;
	}

	@Override
	public int update(LicenceEntity entity) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, entity);
		int result = 0;
		this.licenceInfoService.check(entity);
		entity.setUpdateTime(new Date());
		entity.setVerifyStatus(DictConstant.LICENCE_VERIFY_WAIT);
		//  更新之前校验输入
		
		result = this.licenceWarnDao.update(entity);
		
		// 删除旧的附件
        //依据licenceId删除旧的附件信息
        this.licenceFileRelaDao.deleteFileId(entity.getId());
        //保存新的附件信息
      //资质附件对象
  		String[] fileIds = entity.getFileIds();
  		if (null != fileIds && 0 < fileIds.length) {
  			List<LicenceFileRelaEntity> list = new ArrayList<LicenceFileRelaEntity>();
  			this.licenceInfoService.createFileRelationVal(list, entity);
  			this.licenceFileRelaDao.saves(list);
  		}
		
		LOGGER.debug(SystemContants.DEBUG_END, entity);
		return result;
	}

	@Override
	public int amount(Map<String, Object> map) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		int result = 0;
		result = this.licenceWarnDao.amount(map);
		LOGGER.debug(SystemContants.DEBUG_END, map);
		return result;
	}
}
