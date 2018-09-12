/**
 * 文件名：DutyConstants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年9月29日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.constant;

/**
 * 〈一句话功能简述〉字典常量 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年10月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DictConstant {
    
    /** 渔船分类--执法装备系统使用 */
    public static final String SHIP_TYPE = "310002";
    
    /********************************** 用户角色 *****************************/
    /** 政府用户角色 */
    public static final String GOVERNMENT_USER =
        "e5348d777c2a48dd98cc7e19621d3193";
    
    /** 物流企业角色 */
    public static final String LOGISTICS_ENTERPRISE_USER =
        "7545f7983ac84654951f64efba29677a";
    
    /** 化工企业角色 */
    public static final String CHEMICAL_ENTERPRISE_USER =
        "f9be1f18fdea412cbf7bcf4a4a6809af";
    
    /** 驾驶员角色 */
    public static final String DRIVER_USER = "b0bf7c2cff8641e68e2aeec2840c483f";
    
    /** 卡口员角色 */
    public static final String GUARD_USER = "27a5403c65d44f90bbebe990bf076721";
    
    /********************************** 认证系统机构信息 *****************************/
    
    /** 认证系统中物流系统的系统ID */
    public static final String SYS_ID = "6";
    
    /** 物流企业 上级机构（pid） */
    public static final String LOGISTICS_PID = "2";
    
    /** 化工企业上级机构（pid） */
    public static final String CHEMICAL_PID = "1000000";
    
    /********************************** 企业信息 *****************************/
    /** 企业信息--企业类型--物流企业 */
    public static final String ENTERPRISE_TYPE_LOGISTICS = "106001001";
    
    /** 企业信息--企业类型--化工企业 */
    public static final String ENTERPRISE_TYPE_CHEMICAL = "106001002";
    
    // /** 物流企业信息--主营业务--化工类运输 */
    // public static final String ENTERPRISE_BUSINESS_CHEMICAL = "100002001";
    //
    // /** 物流企业信息--主营业务--危险品运输 */
    // public static final String ENTERPRISE_BUSINESS_DANGEROUS = "100002002";
    
    /** 从业人员信息--驾驶员类型--驾驶员 */
    public static final String OCCUPATION_PERSON_DRIVER = "101001001";
    
    /** 从业人员信息--驾驶员类型--押运员 */
    public static final String OCCUPATION_PERSON_ESCOIRT = "101001002";
    
    /** 从业人员信息--性别--男 */
    public static final String OCCUPATION_PERSON_SEX_MAN = "101002001";
    
    /** 从业人员信息--性别--女 */
    public static final String OCCUPATION_PERSON_SEX_WOMAN = "101002002";
    
    /******************************* 报警 **************************/
    /** 报警状态--已处理 */
    public static final String ALARM_DEALED = "103002001";
    
    /** 报警状态--未处理 */
    public static final String ALARM_NOT_DEALED = "103002002";
    
    /** 紧急报警 */
    public static final String ALARM_URGENT = "103001001";
    
    /** 设备故障报警 */
    public static final String ALARM_EQUIPMENT_BREAKDOWN = "103001002";
    
    /** 路线偏离报警 */
    public static final String ALARM_ROUTE_DEVIATE = "103001003";
    
    /** 区域报警 */
    public static final String ALARM_AREA = "103001004";
    
    /** 超速报警 */
    public static final String ALARM_OVERSPEED = "103001005";
    
    /** 疲劳驾驶报警 */
    public static final String ALARM_FATIGUE_DRIVING = "103001006";
    
    /** 断电报警 */
    public static final String ALARM_OUTAGE = "103001007";
    
    /** 超时停车报警 */
    public static final String ALARM_OVERTIME_PARK = "103001008";
    
    /** 移动app一键报警 */
    public static final String ALARM_IMMEDIATELY_APP = "103001009";
    
    /******************************* 订单管理状态 *****************************/
    /** 订单管理--未发布 */
    public static final String SRC_GOODS1_NOT_PUBLISH = "110001001";
    
    /** 订单管理--已发布 */
    public static final String SRC_GOODS2_PUBLISHED = "110001002";
    
    /** 订单管理--待确认 */
    public static final String SRC_GOODS3_WAIT_SURE = "110001003";
    
    /** 订单管理--已拒绝 */
    public static final String SRC_GOODS4_REFUSED = "110001004";
    
    /** 订单管理--已签订 */
    public static final String SRC_GOODS5_SIGNED = "110001005";
    
    /** 订单管理--已发布 */
    public static final String SRC_GOODS6_FILISHED = "110001006";
    
    /******************************* 运单管理状态 *****************************/
    /** 运单管理--待运输 */
    public static final String WAYBILL_WAIT = "111001001";
    
    /** 运单管理--取货中 */
    public static final String WAYBILL_GET = "111001002";
    
    /** 运单管理--送货中 */
    public static final String WAYBILL_SEND = "111001003";
    
    /** 运单管理--运输完成 */
    public static final String WAYBILL_FINISH = "111001004";
    
    // TODO 未定义
    /** 位置数据来源808协议 */
    public static final String LOCATION_ORIGIN_JT808 = "";
    
    // TODO 未定义
    /** 位置数据来源809协议(第三平台) */
    public static final String LOCATION_ORIGIN_JT809 = "";
    
    /********************************** 限制区域 *********************************/
    /** 限速 */
    public static final String LIMIT_SPEED = "112001001";
    
    /** 限停 */
    public static final String LIMIT_STOP = "112001002";
    
    /** 限驶入 */
    public static final String LIMIT_DRIVE_IN = "112001003";
    
    /******************************* APP账号审核状态 *****************************/
    /** APP账号审核--待审核 */
    public static final String ACCOUNT_VERIFY_WAIT = "114001001";
    
    /** APP账号审核--已注册 */
    public static final String ACCOUNT_VERIFY_PASS = "114001003";
    
    /** APP账号审核--已拒绝 */
    public static final String ACCOUNT_VERIFY_REJECT = "114001002";
    
    
    /******************************* 资质备案管理审核状态 *****************************/
    /** 资质备案管理审核--待审核 */
    public static final String LICENCE_VERIFY_WAIT = "151001001";
    
    /** 资质备案管理审核--通过 */
    public static final String LICENCE_VERIFY_PASS = "151001003";
    
    /** 资质备案管理审核--未通过 */
    public static final String LICENCE_VERIFY_REJECT = "151001002";
}
