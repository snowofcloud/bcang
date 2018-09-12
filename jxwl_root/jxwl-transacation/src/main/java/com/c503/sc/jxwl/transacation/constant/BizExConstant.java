/**
 * 文件名：BizExConstant.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.constant;
  
/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BizExConstant {
    /************************************* 订单异常码 **********************************/
    /** 货源管理--交易状态已被发布 */
    public static final int ORDER1_STATUS_PUBLISHED_E = 311000001;
    
    /** 货源管理--交易状态已被签订 */
    public static final int ORDER2_STATUS_SIGNED_E = 311000002;
    
    /** 订单管理——只有待确认的订单才可以拒绝 */
    public static final int SRCGOODS_REJECT_NOTALLOWD = 311000003;
    
    /** 订单管理——只有物流用户才可以拒绝该订单 */
    public static final int WLENTERPRISE_REJECT_ALLOW = 311000004;
    
    /** 订单管理——只有物流用户才可以确认该订单 */
    public static final int WLENTERPRISE_CONFIRM_ALLOW = 311000005;
    
    /** 订单管理——只有化工用户才可以完成该订单 */
    public static final int HGENTERPRISE_CONFIRM_ALLOW = 311000006;
    
    /** 订单管理——只有已拒绝的订单才可以重新发布 */
    public static final int HGENTERPRISE_PUSHREJECT_ALLOW = 311000007;
    
    /** 订单管理——只有化工用户才可以重新发布订单 */
    public static final int HGENTERPRISE_PUSH_ALLOW = 311000008;
    
    /** 订单管理——只有待确认的订单才可以撤销 */
    public static final int SRCGOODS_CANCEL_NOTALLOWD = 311000010;
    
    /** 订单管理——只有化工用户才可以撤销该订单 */
    public static final int HGENTERPRISE_CANCEL_ALLOW = 311000011;
    
    /** 货源管理--交易状态非未发布或已发布，不能删除 */
    public static final int ORDER3_NOT_DELETE_E = 311000009;
    
    /** 留言管理--只有已发布的货源才可以留言。 */
    public static final long LEAVEMESSAGE_STATUS_NOT_ALLOW = 311000012;
    
    /** 留言管理--同一物流企业对同一个货源只能留言一次。 */
    public static final long LEAVEMESSAGE_TIMES_NOT_ALLOW = 311000013;
    
    /** 留言管理--只有物流企业用户人员才能新增。 */
    public static final long LEAVEMESSAGE_ADD_NOT_ALLOW = 311000014;
    
    /** 该信息已经删除。 */
    public static final long SRCGOODS_DELETE = 311000015;
	
    /** 订单管理--只有待确认的订单才可以被确认订单。 */
    public static final long SRCGOODS_CONFIRM_NOTALLOWD = 311000016;
    
    /** 订单管理--只有已签订的订单才可以被确认完成订单。 */
    public static final long SRCGOODS_FINISH_NOTALLOWD = 311000017;   
	
    /** 留言管理--对不起，此留言已经被删除。 */
    public static final long LEAVEMESSAGE_DELETE_NOTALLOWD = 311000018;
	
    /************************************* 运单 **********************************/
    /** 货物存在状态 */
    public static final int WAYBILL_GOODS_EXIST = 312000001;
    
    /** 存在未完成的运单 */
    public static final int WAYBILL_STATUSEXIST = 312000002;
    
    /** 运单信息不存在，请查询后在操作 */
    public static final int WAYBILL_NOEXIST = 312000003;
    
    /** 模板信息不存在，请查询后在操作 */
    public static final int WAYBILLTEMP_NOEXIST = 312000004;
    
    /************************************* 形象展示 **********************************/
    /** 查询输入不能为空**/
    public static final long IMAGESHOW_SEARCH_EMPTY = 313000001;
   
    /** 查询结果不存在 **/
    public static final long IMAGESHOW_NOT_EXIST = 313000002;
    
    /*************************************资质信息备案 **********************************/
    /**该企业不存在该车牌号 **/
    public static final long ENTERPRISE_CARNOEXIST = 315000001;
    
    /**该企业不存在该人员 **/
    public static final long ENTERPRISE_PERSONNOEXIST = 315000002;
    
    /**该信息被删除 **/
    public static final long INFORMATION_DELETED = 315000003;
    
    /**该信息已被审核过 **/
    public static final long INFORMATION_VERIFIED = 315000004;
    
    /**该信息已被注销 **/
    public static final long INFORMATION_CANCELED = 315000005;
    
    
    
    
    
    
}
