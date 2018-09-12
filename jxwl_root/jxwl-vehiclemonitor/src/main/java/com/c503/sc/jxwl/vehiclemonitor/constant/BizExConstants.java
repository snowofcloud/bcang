/**
 * 文件名：CommonConstants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年9月29日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.constant;

/**
 * 〈一句话功能简述〉业务异常码常量定义 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016年7月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BizExConstants {
	/********************************** 物流企业信息 ******************************/

	/** 物流企业信息--非政府用户无法操作 */
	public static final int ENTERPRISE_NO_AUTHORITY_E = 310300101;

	/** 物流企业信息--非政府、企业用户不能删除 */
	public static final int DELETE_NO_AUTHORITY_E = 310300102;

	/** 物流企业信息--非当前企业用户不能操作 */
	public static final int UPDATE_NO_AUTHORITY_E = 310300103;

	/** 物流企业信息--非企业用户不能申请 */
	public static final int APPLY_NO_AUTHORITY_E = 310300104;

	/** 手动拉黑 */
	public static final String BLACKLISTTYPE = "104002001";

	/** 企业信息--法人代码重复 */
	public static final int ENTERPRISE_CONRPORATREXIST = 310300105;

	/** 车辆信息--车牌号重复 */
	public static final int VEHICLE_LICENCEPLATENOEXIST = 310300106;

	/** 从业人员信息--身份证号码重复 */
	public static final int OCCUPATIONPERSON_IDCARDNOEXIST = 310300107;

	/** 该条数据已经被删除 */
	public static final int DATAISNOTEXIST = 310300108;

	/** 该条数据已处理 */
	public static final int DATA_DEL_ALREADY = 310300109;

	/** 从业人员信息--驾驶员数据不存在 */
	public static final int OCCUPATIONPERSON_DRIVERNOEXIST = 310300110;

	/** 车辆信息--车牌号不存在 */
	public static final int VEHICLE_CARNOEXIST = 310300111;

	/** 企业信息--该企业不存在 */
	public static final int COMPANYNOEXIST = 310300112;
	
	/** 企业信息--该企业已经被删除*/
    public static final int COMPANYDELETED = 310300113;
    
    /** 车辆信息--车辆附件只能为一个 */
    public static final int VEHICLE_FILE_ONLY_ONE = 310300114;

	/********************************** 物流企业信息 ******************************/

	/** 黑名单已有该企业 */
	public static final int BLACKLIST_HAS = 311300101;

	/** 物流企业已在黑名单 **/
	public static final long BLACK_COMPANY = 311300102;

	/** 驾驶员已在黑名单 **/
	public static final long BLACK_DRIVER = 311300103;

	/** 车辆已在黑名单 **/
	public static final long IBLACK_CAR = 311300104;

	/********************************** 移动app账号审核信息 ******************************/

	/** 账号重复 */
	public static final int ACCOUNT_REPEATED = 311410000;

	/** 个人信息不匹配 */
	public static final int INFORMATION_CONFLICT = 311410001;
	
	/** 待审核中 */
	public static final int ACCOUNT_VERIFY_WAIT = 311410002;
	
	/** 已注册 */
	public static final int ACCOUNT_VERIFY_PASS = 311410003;
	
	/** 已拒绝 */
	public static final int ACCOUNT_VERIFY_REJECTION = 311410004;
	
	/********************************** 报警app ******************************/
	/** 报警成功 */
	public static final int ALARM_SUC = 111510001;
	
	/** 不可报警 运单不在取货或送货中  */
	public static final int ALARM_NO_RIGHT = 311510002;
	
	
	/**账号不对*/
	public static final int ACCOUNT_WRONG = 311510003;

}
