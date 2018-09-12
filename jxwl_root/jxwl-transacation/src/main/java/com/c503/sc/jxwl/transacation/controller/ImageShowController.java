/**
 * 文件名：ImageShowController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.service.IImageShowService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/enterpriseImageShow")
public class ImageShowController extends ResultController {
	/** 日志器 */
	private static final LoggingManager LOGGER = LoggingManager
			.getLogger(ImageShowController.class);

	/** 形象展示业务接口 */
	@Resource(name = "imageShowService")
	private IImageShowService imageShowService;

	/**
	 * 
	 * 〈一句话功能简述〉通过企业名字或法人代码查询企业信息 〈功能详细描述〉
	 * 
	 * @param queryStr
	 *            查询参数
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/findByNameOrCode", method = RequestMethod.POST)
	@ResponseBody
	public Object findByNameOrCode(String queryStr) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		queryStr = queryStr == null ? null : queryStr.trim();
		if (C503StringUtils.isNotEmpty(queryStr) && null != queryStr) {
			List<EnterpriseEntity> val = this.imageShowService
					.findByNameOrCode(queryStr);
			if (val.size() == 0) {
				throw new CustomException(BizExConstant.IMAGESHOW_NOT_EXIST,
						queryStr);
			}
			sendData(val, CommonConstant.FIND_SUC_OPTION);
		} // 传入参数为空
		else {
			throw new CustomException(BizExConstant.IMAGESHOW_SEARCH_EMPTY,
					queryStr);
		}
		LOGGER.debug(SystemContants.DEBUG_END);

		return sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉通过法人代码查询企业信息 〈功能详细描述〉
	 * 
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/findByCode", method = RequestMethod.GET)
	@ResponseBody
	public Object findByCode() throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		// 1 角色判断
		if (isSame()) {
			EnterpriseEntity val = this.imageShowService.findByGOVER();
			sendData(val, CommonConstant.FIND_SUC_OPTION);
		} else {
			String code = this.getUser().getCorporateCode();
			if (C503StringUtils.isNotEmpty(code)) {
				EnterpriseEntity val = this.imageShowService.findByCode(code);
				sendData(val, CommonConstant.FIND_SUC_OPTION);
			} else {
				this.sendCode(CommonConstant.ARGS_INVALID, code);
			}
		}

		LOGGER.debug(SystemContants.DEBUG_END);

		return sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉判断该用户是否是政府用户 〈功能详细描述〉
	 * 
	 * @return 该用户是否是政府企业用户
	 * @see [类、类#方法、类#成员]
	 */
	private boolean isSame() {

		boolean isRole = false;
		List<String> userRoles = new ArrayList<String>();
		try {
			userRoles = this.getUser().getRoleCodes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int j = userRoles.size();
		for (int i = 0; i < j; i++) {
			if (DictConstant.GOVERNMENT_USER.equals(userRoles.get(i))) {
				isRole = true;
				break;
			}

		}
		return isRole;

	}

	@Override
	protected Object show() {
		return null;
	}

	@Override
	protected LoggingManager logger() {
		return LOGGER;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> IBaseService<T> getBaseService() {
		return (IBaseService<T>) this.imageShowService;
	}
}
