package com.c503.sc.recvBasic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.c503.sc.jxwl.orgdata.dao.ISyncOrganDataDao;
import com.c503.sc.jxwl.orgdata.service.impl.SysOrgTypeServiceImpl;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.recvBasic.dao.ISyncEnterpriseDataDao;
import com.c503.sc.recvBasic.util.RecvBasicDataUtil;
import com.c503.sc.utils.common.SystemContants;

/**
 * 1、获取平台所有企业 
 * 2、获取业务系统所有化工企业 
 * 3、遍历对比法人代码（认证系统相同） 
 *       a、平台有业务系统无：业务系统新增
 *       b、平台有业务系统有：业务系统更新
 *       c、平台无业务系统有：业务系统删除
 * @author Administrator
 */
@Service
public class RecvBasicInfoServiceImpl implements IRecvBasicInfoService {
	/** 日志器 */
	private static final LoggingManager LOGGER = LoggingManager
			.getLogger(SysOrgTypeServiceImpl.class);

	@Resource
	private ISyncEnterpriseDataDao syncEnpDataDao;

	@Resource
	private ISyncOrganDataDao syncOrganDataDao;

	@SuppressWarnings("unchecked")
	@Override
	public void syncBasicData() throws Exception {
		Map<String, Object> recvMap = this.parseJsonArr();
		if (null != recvMap) {
			List<EnterpriseEntity> listEnps = (List<EnterpriseEntity>) recvMap
					.get("enp");
			List<SysOrganTypeEntity> listOrgs = (List<SysOrganTypeEntity>) recvMap
					.get("org");

			// 同步业务系统企业数据
			this.syncEnpreBasicInfo(listEnps);
			// 同步auth的机构数据
			this.syncOrgInfo2Auth(listOrgs);
		}
	}

	/**
	 * 同步业务系统企业基础数据
	 * 
	 * @param enps List<EnterpriseEntity>
	 * @throws Exception Exception
	 */
	private void syncEnpreBasicInfo(List<EnterpriseEntity> enps)
			throws Exception {
		int size = enps.size();
		if (0 < size) {
			// 获取业务系统所有化工企业
			List<String> sysChemCodes = this.syncEnpDataDao
					.findAllCorporateNo(DictConstant.ENTERPRISE_TYPE_CHEMICAL);
			// 若数据库中不存在化工企业，则直接保存
			if (sysChemCodes.isEmpty()) {
				for (int i = 0; i < size; i++) {
					EnterpriseEntity enp = enps.get(i);
					this.syncEnpDataDao.saveEnpInfo(enp);
				}
				// 数据库中化工企业存在
			} else {
				// 获取所有的公共平台所有的法人代码
//				List<String> platChemCodes = new ArrayList<>();
				List<String> platChemCodes = new ArrayList<String>();
				for (int i = 0; i < size; i++) {
					EnterpriseEntity enp = enps.get(i);
					platChemCodes.add(enp.getCorporateNo());
				}

				for (int i = 0; i < sysChemCodes.size(); i++) {
					String sysChemCode = sysChemCodes.get(i);
					for (int j = 0; j < size; j++) {
						EnterpriseEntity enp = enps.get(j);
						String enpcode = enp.getCorporateNo();
						// 若平台的企业法人代码==业务系统的企业法人代码, 则更新
						if (StringUtils.equals(sysChemCode, enpcode)) {
							this.syncEnpDataDao.updateEnpInfo(enp);
						}
						// 若业务系统中的企业法人代码不包含平台企业的法人代码,则保存
						else if (!sysChemCodes.contains(enpcode)) {
							this.syncEnpDataDao.saveEnpInfo(enp);
						}
						// 若业务系统中的企业法人代码在平台所有化工企业不存在,则删除
						else if (!platChemCodes.contains(sysChemCode)) {
							this.syncEnpDataDao.deleteEnpInfo(sysChemCode);
						}
					}
				}
			}
		}
	}

	/**
	 * 同步企业数据到auth中的机构表中（T_SYS_ORGAN_TYPE）
	 * 
	 * @param organs List<SysOrganTypeEntity>
	 * @throws Exception Exception
	 */
	private void syncOrgInfo2Auth(List<SysOrganTypeEntity> organs)
			throws Exception {
		int size = organs.size();
		if (0 < size) {
			// 获取auth所有的化工企业id
			List<String> authChemCodes = this.syncOrganDataDao.findAllOrgId(
					DictConstant.ENTERPRISE_TYPE_CHEMICAL, DictConstant.SYS_ID);
			// 若auth中不存在化工企业，则直接保存
			if (authChemCodes.isEmpty()) {
				for (int i = 0; i < size; i++) {
					SysOrganTypeEntity org = organs.get(i);
					this.syncOrganDataDao.saveOrgInfo(org);
				}
				// auth中化工企业存在
			} else {
				// 获取所有的公共平台中的所有的法人代码
				List<String> platChemCodes = new ArrayList<String>();
//				List<String> platChemCodes = new ArrayList<>();
				for (int i = 0; i < size; i++) {
					SysOrganTypeEntity org = organs.get(i);
					platChemCodes.add(org.getId());
				}

				for (int i = 0; i < authChemCodes.size(); i++) {
					String authChemCode = authChemCodes.get(i);
					for (int j = 0; j < size; j++) {
						SysOrganTypeEntity org = organs.get(j);
						String enpcode = org.getId();
						// 若平台的企业法人代码==auth的企业法人代码, 则更新
						if (StringUtils.equals(authChemCode, enpcode)) {
							this.syncOrganDataDao.updateOrgInfo(org);
						}
						// 若auth中的企业法人代码不包含平台企业的法人代码,则保存
						else if (!authChemCodes.contains(enpcode)) {
							this.syncOrganDataDao.saveOrgInfo(org);
						}
						// 若auth中的企业法人代码在平台所有化工企业不存在,则删除
						else if (!platChemCodes.contains(authChemCode)) {
							this.syncOrganDataDao.deleteOrgInfo(authChemCode);
						}
					}
				}
			}
		}
	}

	/**
	 * 解析从公共应用服务平台获取的企业数据jsonArr
	 * 
	 * @return Map<String, Object>
	 */
	private Map<String, Object> parseJsonArr() {
		// 用来装入业务系统企业信息和auth中的机构信息
		Map<String, Object> map = new HashMap<>();
		List<EnterpriseEntity> listEnps = new ArrayList<>();
		List<SysOrganTypeEntity> listOrgs = new ArrayList<>();
		// 公共应用服务平台获取的企业数据jsonArr
		String[] datas = RecvBasicDataUtil.getData();
		if (null != datas && 0 < datas.length) {
			int len = datas.length;
			for (int i = 0; i < len; i++) {
				String data = datas[i];
				// FIXME 暂不知道公共服务平台返回企业信息具体的字段
				EnterpriseEntity enpre = JSON.parseObject(data,
						EnterpriseEntity.class);
				enpre.setRemove(SystemContants.ON);
				listEnps.add(enpre);

				SysOrganTypeEntity organ = JSON.parseObject(data,
						SysOrganTypeEntity.class);
				organ.setPid(DictConstant.CHEMICAL_PID);
				organ.setSysId(DictConstant.SYS_ID);
				organ.setRemove(SystemContants.ON);
				listOrgs.add(organ);
			}
			map.put("enp", listEnps);
			map.put("org", listOrgs);
		} else {
			LOGGER.error(SystemContants.DEBUG_END, new Exception(
					"not any enterprise infos..."));
			// 释放句柄
			map = null;
			listEnps = null;
			listOrgs = null;
		}

		return map;
	}

	// private void createEnpreData(List<EnterpriseEntity> list, String data) {
	//
	// }
	//
	// private void createOrganData(List<EnterpriseEntity> list, String data) {
	//
	// }

}
