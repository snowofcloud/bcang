/**
 * 文件名：AccountVerifyController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sc.c503.authclient.model.SysUserRelRoleEntity;
import sc.c503.authclient.service.AuthFactory;
import sc.c503.authclient.service.IAuthService;
import sc.c503.authclient.utils.InitSysResource;

import com.alibaba.fastjson.JSON;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.formbean.BaseForm;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.orgdata.bean.BizUserEntity;
import com.c503.sc.jxwl.orgdata.service.ISysUserService;
import com.c503.sc.jxwl.vehiclemonitor.bean.AccountVerifyEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.formbean.AccountVerifyForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IAccountVerifyService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.system.model.SysUserEntity;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;
import com.c503.sc.utils.response.ResultMessage;

/**
 * 
 * 〈一句话功能简述〉账号审核Controller 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/accountVerify")
public class AccountVerifyController extends ResultController {
	/** 日志器 */
	private static final LoggingManager LOGGER = LoggingManager
			.getLogger(AccountVerifyController.class);

	/** 账号审核业务接口 */
	@Resource
	private IAccountVerifyService accountVerifyService;

	/** 账号审核业务接口 */
	@Resource
	private ISysUserService sysUserService;

	/**
	 * 
	 * 〈一句话功能简述〉 〈功能详细描述〉注册信息控制层
	 * 
	 * @param form
	 *            实体
	 * @param bindingResult
	 *            表单验证错误集合
	 * @return object
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object register(
			@Validated(value = Save.class) AccountVerifyForm form,
			BindingResult bindingResult) throws Exception {
		boolean isResult = this.addErorrs(bindingResult);
		// 后台表单验证
		if (isResult) {
			this.loggerFormValidate(LOGGER, form);
		} else {
			// 基本信息是否匹配并得到相应的从业人员ID 真实姓名 身份证号 所属物流企业法人代码
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("personName", form.getPersonName());
			map.put("identificationCardNo", form.getIdentificationCardNo());
			map.put("corporateNo", form.getCorporateNo());
			map.put("occupationPersonType",
					DictConstant.OCCUPATION_PERSON_DRIVER);
			// 查找从业人员信息对象
			List<AccountVerifyEntity> accountVerifyList = this.accountVerifyService
					.findOccupationPerson(map);

			// 信息不匹配 返回信息不匹配
			if (accountVerifyList.size() == 0) {
				sendCode(BizExConstants.INFORMATION_CONFLICT);
				return this.sendMessage();
			}
			// 查询结果大于一个
			if (accountVerifyList.size() > 1) {
				sendCode(CommonConstant.SYS_EXCEPTION);
				return this.sendMessage();
			}
			// 通过occupationId 查找注册对象
			String occupationId = accountVerifyList.get(0).getOccupationId();
			map.clear();
			map.put("occupationId", occupationId);
			map.put("repeat", "repeat");
			accountVerifyList = this.accountVerifyService.findRegisterInfo(map);
			// 查询结果大于一个
			if (accountVerifyList.size() > 1) {
				sendCode(CommonConstant.SYS_EXCEPTION);
				return this.sendMessage();
			}
			// 如果注册对象存在 依据审核状态返回
			if (accountVerifyList.size() > 0) {
				String verifyStatus = accountVerifyList.get(0)
						.getVerifyStatus();
				// 待审核
				if (verifyStatus.equals(DictConstant.ACCOUNT_VERIFY_WAIT)) {
					sendCode(BizExConstants.ACCOUNT_VERIFY_WAIT);
					return this.sendMessage();
				}
				// 已同意
				if (verifyStatus.equals(DictConstant.ACCOUNT_VERIFY_PASS)) {
					sendCode(BizExConstants.ACCOUNT_VERIFY_PASS);
					return this.sendMessage();
				}
			}
			// 如果注册对象不存在
			// 添加注册信息 判断account是否重复
			map.clear();
			map.put("account", form.getAccount());
			// 已拒绝的账号不用于判断account是否重复
			map.put("repeat", "repeat");
			accountVerifyList = this.accountVerifyService.findRegisterInfo(map);
			map.put("sysId", DictConstant.SYS_ID);
			BizUserEntity user = this.accountVerifyService.findBizUser(map);
			// account重复
			if (accountVerifyList.size() > 0 || user != null) {
				sendCode(BizExConstants.ACCOUNT_REPEATED);
				return this.sendMessage();
			}
			// 如果不重复 保存至业务数据库 待审核
			AccountVerifyEntity entity = new AccountVerifyEntity();
			entity.setRegisterId(C503StringUtils.createUUID());
			entity.setAccount(form.getAccount());
			entity.setOccupationId(occupationId);
			entity.setCreateTime(new Date());
			// 生成密盐
			Random ran = new Random();
			int tmp = ran.nextInt(NumberContant.ONE_ZERO_ZERO)
					+ NumberContant.ONE_THOUSAND;
			String salt = String.valueOf(tmp);
			entity.setSalt(salt);
			entity.setPassword(form.getPassword());
			// 在设计数据库表时，设置了审核状态默认值 待审核'114001001'
			this.accountVerifyService.register(entity);
			// 打印日志
			LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
			/*
			 * controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
			 * CommonConstant.SAVE_SUC_OPTION, entity).recordLog();
			 */
			sendCode(CommonConstant.SAVE_SUC_OPTION);

		}

		LOGGER.debug(SystemContants.DEBUG_END, form);
		return this.sendMessage();

	}

	/**
	 * 〈一句话功能简述〉分页账号信息 〈功能详细描述〉
	 * 
	 * @param form
	 *            form
	 * @param bindingResult
	 *            bindingResult
	 * @return 数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/findByPage", method = RequestMethod.POST)
	@ResponseBody
	public Object findByPage(AccountVerifyForm form, BindingResult bindingResult)
			throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		boolean isResult = this.addErorrs(bindingResult);
		// 后台表单验证
		if (isResult) {
			this.loggerFormValidate(LOGGER, form);
		} else {
			// 角色校验
			List<String> userRoles = this.getUser().getRoleCodes();
			if (userRoles.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)
					|| userRoles.contains(DictConstant.GOVERNMENT_USER)) {
				// 参数设置
				Map<String, Object> map = form.handlePageParas();
				if (userRoles.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
					map.put("corporateNo", this.getUser().getCorporateCode());
					map.put("personName", form.getPersonName() == null ? null
							: form.getPersonName().trim());
					map.put("account", form.getAccount() == null ? null : form
							.getAccount().trim());
					map.put("identificationCardNo", form
							.getIdentificationCardNo() == null ? null : form
							.getIdentificationCardNo().trim());
					map.put("verifyStatus",
							form.getVerifyStatus() == null ? null : form
									.getVerifyStatus().trim());
				} else {
					map.put("verifyStatus", DictConstant.ACCOUNT_VERIFY_PASS);
					map.put("personName", form.getPersonName() == null ? null
							: form.getPersonName().trim());
					map.put("account", form.getAccount() == null ? null : form
							.getAccount().trim());
					map.put("identificationCardNo", form
							.getIdentificationCardNo() == null ? null : form
							.getIdentificationCardNo().trim());
				}
				// 权限校验
				List<AccountVerifyEntity> accountVerifyList = this.accountVerifyService
						.findByParams(map);
				Page page = (Page) map.get("page");
				Page pageEntity = new Page();
				pageEntity.setPageSize(form.getRows());
				setJQGrid(accountVerifyList, page.getTotalCount(),
						page.getCurrentPage(), form.getRows(),
						CommonConstant.FIND_SUC_OPTION);
			}

		}
		LOGGER.debug(SystemContants.DEBUG_END, form);
		return (ResultJQGrid) this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉通过registerId查询账号信息 〈功能详细描述〉
	 * 
	 * @param registerId
	 *            registerId
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/findByParameter/{registerId}", method = RequestMethod.GET)
	@ResponseBody
	public Object findByParameter(@PathVariable String registerId)
			throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		if (C503StringUtils.isNotEmpty(registerId)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("registerId", registerId);
			AccountVerifyEntity accountVerifyEntity = this.accountVerifyService
					.findByParameter(map);
			if (accountVerifyEntity == null) {
				this.sendCode(CommonConstant.NOT_INFO_E);
				return sendMessage();
			}
			sendData(accountVerifyEntity, CommonConstant.FIND_SUC_OPTION);
		} // 传入参数无效
		else {
			this.sendCode(CommonConstant.ARGS_INVALID, registerId);
		}

		LOGGER.debug(SystemContants.DEBUG_END);
		return this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉移动app接口 查看跟人信息
	 * 
	 * @param accountForApp
	 *            accountForApp
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/findByParameterForAPP", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findByParameterForAPP(String accountForApp) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		if (C503StringUtils.isNotEmpty(accountForApp)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accountForApp", accountForApp);
			map.put("verifyStatus", "114001003");
			AccountVerifyEntity accountVerifyEntity = this.accountVerifyService
					.findByParameter(map);
			if (accountVerifyEntity == null) {
				this.sendCode(CommonConstant.NOT_INFO_E);
				return sendMessage();
			}
			if (accountVerifyEntity.getVerifyStatus().equals(
					DictConstant.ACCOUNT_VERIFY_PASS)) {
				sendData(accountVerifyEntity, CommonConstant.FIND_SUC_OPTION);
			} else {
				sendCode(CommonConstant.CHECK_NOALLOW_EXCEPTION);
			}
		} // 传入参数无效
		else {
			this.sendCode(CommonConstant.ARGS_INVALID, accountForApp);
		}

		LOGGER.debug(SystemContants.DEBUG_END);
		return this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉通过id查询账号信息 〈功能详细描述〉
	 * 
	 * @param form
	 *            form
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public Object verify(AccountVerifyForm form) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		// 保存审核信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("registerId", form.getRegisterId());

		AccountVerifyEntity entity = this.accountVerifyService
				.findByParameter(map);
		
		// 校验该账号所属人员是否存在 或该账号所属企业是否存在
		if (entity == null || !this.accountVerifyService.isExist(map)) {
			this.sendCode(CommonConstant.NOT_ACCOUNT_E);
			return sendMessage();
		}
		
		
		// 判断该信息是否被审核过的
		if (entity.getVerifyStatus() != null
				&& !entity.getVerifyStatus().endsWith(
						DictConstant.ACCOUNT_VERIFY_WAIT)) {
			this.sendCode(CommonConstant.ACCOUNT_HAS_VERIFY);
			return sendMessage();
		}

		map.put("registerId", form.getRegisterId());
		map.put("verifyStatus", form.getVerifyStatus());
		map.put("rejectReason", form.getRejectReason());
		int line = this.accountVerifyService.verify(map);
		// 通过审核，同步用户信息到认证系统
		if (form.getVerifyStatus().equals(DictConstant.ACCOUNT_VERIFY_PASS)) {
			map.clear();
			map.put("registerId", form.getRegisterId());
			AccountVerifyEntity entity1 = this.accountVerifyService
					.findByParameter(map);
			SysUserEntity sysUserEntity = new SysUserEntity();

			// 封装用户信息
			sysUserEntity.setSysId(DictConstant.SYS_ID);
			sysUserEntity.setPassword(entity1.getPassword());
			sysUserEntity.setType(SysCommonConstant.COMMON_USER_TYPE);
			sysUserEntity.setAccount(entity1.getAccount());
			sysUserEntity.setOrgId(entity1.getOrgId());

			// 封装用户角色关联信息
			SysUserRelRoleEntity sysUserRelRoleEntity = new SysUserRelRoleEntity();
			sysUserRelRoleEntity.setRoleId(DictConstant.DRIVER_USER);

			String sysUserString = JSON.toJSONString(sysUserEntity);
			String sysUserRelRoleString = JSON
					.toJSONString(sysUserRelRoleEntity);
			IAuthService authService = AuthFactory.getAuthService();
			ResultMessage result = authService.addUser(sysUserString,
					sysUserRelRoleString);
			System.out.println(InitSysResource.getAddUser());

			/*
			 * map.clear(); map.put("registerId", form.getRegisterId());
			 * AccountVerifyEntity entity =
			 * this.accountVerifyService.findByParameter(map);
			 * this.createCommonVal(entity, true, this.getUser()
			 * .getCorporateCode()); map.clear(); map = entity.handleParas(); //
			 * T_SYS_USER // 設置系統id String password =
			 * C503UserUtil.createPassword(entity.getPassword(),
			 * entity.getSalt()); map.put("password", password); map.put("salt",
			 * entity.getSalt()); map.put("sysId", DictConstant.SYS_ID);
			 * map.put("type", SysCommonConstant.COMMON_USER_TYPE);
			 * map.put("id", C503StringUtils.createUUID()); Date beginTime = new
			 * Date(); Date endTime =
			 * C503DateUtils.getDay(BizConstant.FIVE_YEARS, beginTime);
			 * map.put("beginTime", beginTime); map.put("endTime", endTime); if
			 * (map.get("personSex")
			 * .equals(DictConstant.OCCUPATION_PERSON_SEX_MAN)) {
			 * map.put("personSex", "0"); } else { map.put("personSex", "1"); }
			 * // T_USER_REL_ROLE map.put("userRoleId",
			 * C503StringUtils.createUUID()); map.put("roleId",
			 * DictConstant.DRIVER_USER); map.put("userId", map.get("id")); line
			 * = this.sysUserService.saveSysUser(map);
			 */

		}
		sendData(line, CommonConstant.FIND_SUC_OPTION);

		LOGGER.debug(SystemContants.DEBUG_END);
		return this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉通过id注销账号信息 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/cancel/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object cancel(@PathVariable String id) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		if (C503StringUtils.isNotEmpty(id)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			// 查找注册信息对象 得到要被注册的account
			List<AccountVerifyEntity> list = this.accountVerifyService
					.findRegisterInfo(map);
			if (list.size() == 0) {
				sendCode(CommonConstant.NOT_INFO_E);
				return sendMessage();
			}
			String registerId = list.get(0).getRegisterId();
			map.put("registerId", registerId);
			// 校验该账号所属人员是否存在 或该账号所属企业是否存在
			if (!this.accountVerifyService.isExist(map)) {
				this.sendCode(CommonConstant.NOT_ACCOUNT_E);
				return sendMessage();
			}

			String account = list.get(0).getAccount();
			int line = this.accountVerifyService.cancel(map);
			// 判断注销对象是否是通过审核的 如果是则到认证系统中注销该账号
			String verifyStatus = list.get(0).getVerifyStatus();
			if (verifyStatus.equals(DictConstant.ACCOUNT_VERIFY_PASS)) {
				map.clear();
				map.put("account", account);
				map.put("remove", SystemContants.OFF);
				this.sysUserService.updateInfo(map);
			}
			sendData(line, CommonConstant.FIND_SUC_OPTION);

		} // 传入参数无效
		else {
			this.sendCode(CommonConstant.ARGS_INVALID, id);
		}

		LOGGER.debug(SystemContants.DEBUG_END);
		return this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉计算未被审核的总条数 〈功能详细描述〉
	 * 
	 * 
	 * @return 返回成功 数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/amount", method = RequestMethod.GET)
	@ResponseBody
	public Object amount() throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> userRoles = this.getUser().getRoleCodes();
		String corporateNo = this.getUser().getCorporateCode();
		// 权限校验
		if (userRoles.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
			map.put("corporateNo", corporateNo);
			map.put("verifyStatus", DictConstant.ACCOUNT_VERIFY_WAIT);
			int result = this.accountVerifyService.amount(map);
			sendData(result, CommonConstant.FIND_SUC_OPTION);
		} else {
			sendCode(CommonConstant.CHECK_NOALLOW_EXCEPTION);
		}
		LOGGER.debug(SystemContants.DEBUG_END);
		return this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉通过id查询账号信息 〈功能详细描述〉
	 * 
	 * @param form
	 *            form
	 * @param bindingResult
	 *            bindingResult
	 * @return 返回成功 的数据信息
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object update(AccountVerifyForm form, BindingResult bindingResult)
			throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START);
		boolean isResult = this.addErorrs(bindingResult);
		// 后台表单验证
		if (isResult) {
			sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
			// 记录操作失败信息（存入文件）
			LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
			// 保存操作日志 记录操作失败（存入数据库）
		} else {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("account", form.getAccount());
			List<AccountVerifyEntity> accountVerifyList = this.accountVerifyService
					.findRegisterInfo(paraMap);
			paraMap.clear();
			paraMap.put("registerId", accountVerifyList.get(0).getRegisterId());
			// 校验该账号所属人员是否存在 或该账号所属企业是否存在
			if (!this.accountVerifyService.isExist(paraMap)) {
				this.sendCode(CommonConstant.NOT_ACCOUNT_E);
				return sendMessage();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map = form.handleParas();
			// 更新业务数据库
			int line = this.accountVerifyService.update(map);
			// 同步认证数据库
			line = this.sysUserService.updateInfo(map);
			sendData(line, CommonConstant.UPDATE_SUC_OPTION);
		}
		LOGGER.debug(SystemContants.DEBUG_END);
		return this.sendMessage();
	}

	/**
	 * 
	 * 〈一句话功能简述〉公共函数用于保存 〈功能详细描述〉
	 * 
	 * @param e
	 *            e
	 * @param saveOrUpdate
	 *            saveOrUpdate
	 * @param userId
	 *            userId
	 * @see [类、类#方法、类#成员]
	 */
	public void createCommonVal(BaseForm e, Boolean saveOrUpdate, String userId) {
		Date curDate = new Date();
		e.setUpdateBy(userId);
		e.setUpdateTime(curDate);
		if (saveOrUpdate) {
			e.setId(C503StringUtils.createUUID());
			e.setCreateBy(userId);
			e.setCreateTime(curDate);
			e.setRemove(SystemContants.ON);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> IBaseService<T> getBaseService() {
		return (IBaseService<T>) this.accountVerifyService;
	}

	@Override
	protected Object show() {
		return null;
	}

	@Override
	protected LoggingManager logger() {
		return LOGGER;
	}

}
