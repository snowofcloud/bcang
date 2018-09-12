package com.c503.sc.jxwl.transacation.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.dao.IGoodsSourceDetailDao;
import com.c503.sc.jxwl.transacation.service.IGoodsSourceDetailService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;


/**
 * 〈一句话功能简述〉货源信息service
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class GoodsSourceDetailServiceImpl implements IGoodsSourceDetailService {

	/** 货源dao */
	@Autowired
	private IGoodsSourceDetailDao goodsSourceDetailDao;

	/** 日志器 */
	private static final LoggingManager LOGGER = LoggingManager
			.getLogger(GoodsSourceDetailServiceImpl.class);

	@Override
	public List<SrcGoods> findByParams(Map<String, Object> map)
			throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		List<SrcGoods> listSrcGoods = null;
		try {
			listSrcGoods = this.goodsSourceDetailDao.findByParams(map);
			LOGGER.info(CommonConstant.FIND_SUC_OPTION, listSrcGoods);
		} catch (Exception e) {
			throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
		}
		LOGGER.debug(SystemContants.DEBUG_END, map);

		return listSrcGoods;
	}

	@Override
	public LeaveMessageEntity findById(Map<String, Object> map)
			throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		LeaveMessageEntity leaveMessage = null;
		try {
			leaveMessage = this.goodsSourceDetailDao.findById(map);
			LOGGER.info(CommonConstant.FIND_SUC_OPTION, leaveMessage);
		} catch (Exception e) {
			throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
		}
		LOGGER.debug(SystemContants.DEBUG_END, map);

		return leaveMessage;
	}

}
