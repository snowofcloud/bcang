/**
 * 文件名：BizConstants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016年5月10日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.constant;

/**
 * 
 * 〈一句话功能简述〉业务异常常量类
 * 〈功能详细描述〉
 * 
 * @author zhaolj
 * @version [版本号, 2016年5月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class BizConstants {
    
    /** 渔业区域-顶级节点的id -'0' */
    public static final String GDYY_AREA_ROOT = "0";
    
    /** 渔业高级搜索树-顶级节点的id -'1' */
    public static final String SHIP_SEARCH_TREE_ROOT = "1";
    
    /** 渔业高级搜索树-条件节点的id -'1' */
    public static final String SHIP_SEARCH_NODE_TYPE_CONDITION = "1";
    
    /** 渔业高级搜索树-群组节点的id -'2' */
    public static final String SHIP_SEARCH_NODE_TYPE_GROUP = "2";
    
    /** 渔业高级搜索树-空格-' ' */
    public static final String SQL_BLANK = " ";
    
    /** 渔业高级搜索树-关系 -'and' （别去掉空格喔，用于分隔单词） */
    public static final String REL_TYPE_AND = BizConstants.SQL_BLANK + " AND "
        + BizConstants.SQL_BLANK;
    
    /** 渔业高级搜索树-关系 -'OR' （别去掉空格喔，用于分隔单词） */
    public static final String REL_TYPE_OR = BizConstants.SQL_BLANK + " OR "
        + BizConstants.SQL_BLANK;
    
    /** 渔船-统计-权限异常 */
    public static final long SHIP_INFO_COUNT_AUTH_EXCEPTION = 325010001;
    
    /** 渔船-查询-只能查询自己区域及下属区域的渔船信息 */
    public static final long SHIP_INFO_FIND_AREA_NO_AUTH_EXCEPTION = 325010002;
    
    /** 只有物流企业用户人员才能新增从业人员信息。 */
    public static final long ENTERPRISE_ADD_NOT_ALLOW = 300110009;
    
    /** 只有物流企业用户人员才能删除从业人员信息。 */
    public static final long ENTERPRISE_DELETE_NOT_ALLOW = 300110012;
    
    /** 只有物流企业用户人员才能编辑从业人员信息。 */
    public static final long ENTERPRISE_UPDATE_NOT_ALLOW = 300110013;
    
    /** 只有物流企业用户人员才能新增。 */
    public static final int DANGERCAR_ADD = 310000214;
    
    /** 只有物流企业用户人员才能编辑。 */
    public static final int DANGERCAR_UPDATE = 310000215;
    
    /** 只有物流企业用户人员才能删除。 */
    public static final int DANGERCAR_DELETE = 310000216;
    
    /** 只有政府用户才能导出 */
    public static final long ENTERPRISE_EXPORTS_NOT_ALLOW = 300110010;
    
    /** 只有政府用户才能新增企业信息 */
    public static final long ENTERPRISE_ADD_ENTERPRISE_IF_NOT_ALLOW = 300110011;
    
    /** 只有政府用户才能新增。 */
    public static final long GOVERNMENT_ADD_ALLOW = 300110014;
    
    /** 只有政府用户才能编辑。 */
    public static final long GOVERNMENT_UPDATE_ALLOW = 300110015;
    
    /** 只有政府用户才能删除。 */
    public static final long GOVERNMENT_DELETE_ALLOW = 300110016;
    
    /** 化工企业只能修改本企业的形象图片 */
    public static final int ENTERPRISE_TYPE_CHEMICAL_UPDATE_NOT_ALLOW =
        300110017;
    
    /** 只有政府用户才能添加黑名单 */
    public static final int ADD_BLACK_NOT_ALLOW = 300110018;
    
    /** 记录也被删除 */
    public static final int ADD_BLACK_NOT_EXIST = 300110019;
    
    /** 只有政府用户才能删除黑名单 */
    public static final int ADD_BLACK_NOT_DELETE = 300110020;
    
    /** 化工企业不能被删除。 */
    public static final long DELETE_NOT_ALLOW = 300110021;
    
    /********************************** 移动app账号审核信息 ******************************/

	/** 账号重复 */
	public static final int ACCOUNT_REPEATED = 311410000;

	/** 个人信息不匹配 */
	public static final int INFORMATION_CONFLICT = 311410001;
	
	/** 账号正在审核中*/
	public static final int ACCOUNT_VERIFY_WAIT = 311410002;
	
	/** 已注册 */
	public static final int ACCOUNT_VERIFY_PASS = 311410003;
	
	/** 已拒绝 请联系管理员 */
	public static final int ACCOUNT_VERIFY_REJECTION = 311410004;
	
	/** 账号不存在 */
	public static final int ACCOUNT_NOT_EXIST = 311410005;
	
	/** 用户名或密码错误 */
	public static final int ACCOUNT_PASSWOR_CONFLICT = 311410006;
	
	/** 系统异常 */
	public static final int SYSTEM_WRONG = 311410007;
	
	/** 原始密码错误 */
	public static final int ACCOUNT_PASSWOR_WRONG = 311410008;
	
	/** 密码修改成功 */
	public static final int ACCOUNT_PASSWOR_RIGHT = 111410009;

    
}
