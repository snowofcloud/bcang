/*
 * 文件名：RegexpContant.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉表单验证正则表达式常量类
 * 修改时间：2015年7月23日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.constant;

/**
 * 〈一句话功能简述〉表单验证正则表达式常量类 〈功能详细描述〉
 * 
 * @author LCB
 * @version [版本号, 2015年7月23日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class RegexpContant {
    /** 外键验证-必填 */
    public static final String FK_REGEXP_MUST = "^[a-zA-Z0-9]{1,32}$";
    
    /** 外键验证 */
    public static final String FK_REGEXP = "^([a-zA-Z0-9]{1,32})?$";
    
    /** 外键数组 */
    public static final String FK_ARR_REGEXP =
        "^(([0-9a-zA-Z]{1,32})(,)){0,}((([0-9a-zA-Z]{1,32})){0,1})$";
    
    /** 字典类型 -必填 */
    public static final String TYPECODE_MUST = "^[\\d]{9}$";
    
    /** 字典类型 */
    public static final String TYPECODE = "^([\\d]{9})?$";
    
    /** 名字验证-必填 */
    public static final String NAME_REGEXP_MUST =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{1,30}$";
    
    /** 名字验证 */
    public static final String NAME_REGEXP =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{0,30}$";
    
    /** 内容验证 -必填 */
    public static final String CONTENT_REGEXP_MUST =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{1,300}$";
    
    /** 内容验证 */
    public static final String CONTENT_REGEXP =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{0,300}$";
    
    /** 账号验证-必填 */
    public static final String ACCOUNT_REGEXP_MUST = "^[a-zA-Z0-9]{3,20}$";
    
    /** 手机验证-必填 */
    public static final String MOBILETELEPHONE_REGEXP_MUST =
        "^(13[0-9]|15[0-9]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    
    /** 手机验证-选填 */
    public static final String MOBILETELEPHONE_REGEXP =
        "^(13[0-9]|15[0-9]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    
    /** 邮箱验证 */
    public static final String EMAIL_REGEXP =
        "(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)?";
    
    /** 邮政编码验证 */
    public static final String POST_CODE = "^([1-9]\\d{5}(?!\\d))?$";
    
    /** qq验证 */
    public static final String QQ_REGEXP = "^([1-9][0-9]{4,})?$";
    
    /** qq验证-必验 */
    public static final String QQ_REGEXP_MUST = "^[1-9][0-9]{4,}$";
    
    /** 身份证验证 */
    public static final String IDCARD_REGEXP_MUST =
        "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|x)$";
    
    /** 身份证验证--非必填 */
    public static final String IDCARD_REGEXP =
        "^([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|x))?$";
    
    /** 座机号正则验证-必验 */
    public static final String TELEPHONE_REGEXP =
        "^([0-9]{3,4}\\-[0-9]{3,8}|[0-9]{6,12}|\\([0-9]{3,4}\\)[0-9]{3,8})?$";
    
    /** 座机号正则验证 */
    public static final String TELEPHONE_REGEXP_MUST =
        "^[0-9]{3,4}\\-[0-9]{3,8}|[0-9]{6,12}|\\([0-9]{3,4}\\)[0-9]{3,8}$";
    
    /** 男女或启停用和是否删除状态验证 */
    public static final String STATUS_MUST = "^[0-1]$";
    
    /** 公文管理-公文名称 */
    public static final String OFFICAL_NAME_MUST =
        "^[\\w\u4E00-\u9FA5A|(【】)|(《》)|(（）)|(、)]{1,30}$";
    
    /** 预案 -- 预案编号 */
    public static final String CASE_CASENO =
        "^[\\d\\p{Lower}\\p{Upper}]{0,30}$";
    
    /** 预案-- 预案名称 */
    public static final String CASE_CASENAME_MUST =
        "^[\\u4e00-\\u9fa5\\d\\p{Lower}\\p{Upper}]{1,30}$";
    
    /** 预案-- 内容 */
    public static final String CASE_CONTENT_MUST =
        "^[\\u4e00-\\u9fa5\\d\\p{Lower}\\p{Upper}]{1,300}$";
    
    /** 值班人员名字 */
    public static final String DUTY_USER_NAME_REGEXP_MUST =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{1,64}$";
    
    /** 值班人员表备注信息 */
    public static final String DUTY_USER_REMARK_REGEXP = "^[.*]{0,200}$";
    
    /** 排班-- 排班年 */
    public static final String DUTY_YEAR_REGEXP = "^\\d{4}$";
    
    /** 排班-- 排班周 */
    public static final String DUTY_WEEK_REGEXP = "^\\d{1,2}-\\d{1,2}$";
    
    /** 值班管理 --值班事件 -- 来电单位 */
    public static final String DUTY_FROMCOMPANY_REGEXP =
        "^[a-zA-Z0-9_\u4E00-\u9FA5A]{0,30}$";
    
    /** 值班管理 -- 时间-精确到分 */
    public static final String TO_MINUTE_TIME_MUST =
        "^(\\d{4})-(\\d{1}|0\\d{1}|1[0-2])-(\\d{1}|0\\d{1}|[12]\\d{1}|3[01]) (\\d{1}|0\\d{1}|1\\d{1}|2[0-3]):(\\d{1}|[0-5]\\d{1})$";
    
    /** 值班管理 -- 时间-精确到分 */
    public static final String TO_MINUTE_TIME =
        "^((\\d{4})-(\\d{1}|0\\d{1}|1[0-2])-(\\d{1}|0\\d{1}|[12]\\d{1}|3[01]) (\\d{1}|0\\d{1}|1\\d{1}|2[0-3]):(\\d{1}|[0-5]\\d{1}))?$";
    
    /** 值班抽查记录人 */
    public static final String DUTY_CHECK_RECORDUSER_MUST =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{1,10}$";
    
    /** 通讯录-名字验证(1到20位) */
    public static final String CONTACT_NAME_REGEXP =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{1,20}$";
    
    /** 通讯录-qq验证(1到15位) */
    public static final String CONTACT_QQ = "^([0-9]{1,14})?$";
    
    /** 通讯录-座机(20位以内) */
    public static final String CONTACT_TELEPHONE_REGEXP =
        "((0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,4})?|\\d{1,4})?$";
    
    /** 通讯录-手机 */
    public static final String CONTACT_MOBILE_REGEXP = "^(1\\d{10})?$";
    
    /** 北斗系统的终端id(必填项) */
    public static final String BEIDOU_ID = "^[0-9]{6}?$";
    
    /** 突发事件搜索中的值班员 */
    public static final String EMETGENCY_DUTYUSER =
        "^[a-zA-Z0-9\u4E00-\u9FA5A]{1,10}$";
    
    /** 突发事件Handleid 验证 */
    public static final String EMETGENCY_HANDLE_UUIDMATCH =
        "^[\\d\\p{Lower}]{32}";
    
    /** 手机或(区号)座机或(区号)座机-分机号或者区号-座机或者区号-座机-分机号或者分机号验证-必填 */
    // public static final String MOBELANDTEL_REGEXP_MUST =
    // "^1\\d{10}|(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,4})?|\\d{1,4}$";
    public static final String MOBELANDTEL_REGEXP_MUST =
        "^\\d{1,20}$|^(0\\d{2,3}-?\\d{1,8})(-\\d{1,4})?$";
    
    /** 手机或(区号)座机或(区号)座机-分机号或者区号-座机或者区号-座机-分机号或者分机号验证-非必填 */
    public static final String MOBELANDTEL_REGEXP =
        "^(\\d{1,20}$|^(0\\d{2,3}-?\\d{1,8})(-\\d{1,4})?)?$";
    
    /** 信息播报只能为0 1 2三个字符串中的一个 */
    public static final String BROADCAST_SPEED = "^[012]$";
    
    /** 终端注册-SIM卡号-可选 */
    public static final String SIM_NO = "^([0-9]{11})?$";
    
    /** 金额数量 xxxx.xx 可以输入xxxx|xxxx.xx|xxxx.x */
    public static final String MONEY_FOUR_PONIT_TWO =
        "^\\d{0,4}(\\.\\d{1,2})?$";
    
    /** 金额数量 xxxxx.xx 可以输入xxxxx|xxxxx.xx|xxxxx.x */
    public static final String MONEY_FIVE_PONIT_TWO_MUST =
        "^\\d{0,5}(\\.\\d{1,2})?$";
    
    /** 金额数量 xxxxx.xx 可以输入xxxxx|xxxxx.xx|xxxxx.x */
    public static final String MONEY_FIVE_PONIT_TWO =
        "^(\\d{0,5}(\\.\\d{1,2})?)?$";
    
    /** 金额数量 xxxx.xx 可以输入xxx|xxx.xx|xxx.x */
    public static final String MONEY_THREE_PONIT_TWO =
        "^\\d{0,3}(\\.\\d{1,2})?$";
    
    /** AIS终端注册-9位MMSI码(必填项) */
    public static final String MMSI = "^[0-9]{9}$";
    
    /** AIS终端注册-12位工作频率 可以输入xxxx|xxxx.x|xxxx.xx */
    public static final String AIS_FREQUENCY = "^\\d{0,10}(\\.\\d{1,2})?$";
    
    /** 海图位置标记-名称 */
    public static final String MARK_POSITION_NAME = "^[[\\s|\\S]*]{1,20}$";
    
    /** 海图位置标记-备注 */
    public static final String MARK_POSITION_DESC = "^[[\\s|\\S]*]{0,200}$";
    
    /** 电子围栏 - 名称 **/
    public static final String ELECTRON_FENCE_NAME = "^[[\\s|\\S]*]{1,15}$";
    
    /** 匹配字符 -船id */
    public static final String SHIPID_COMMUNICATION = "^[a-zA-Z0-9]{1,32}$";
    
    /** 经度度分秒 */
    public static final String LONGITUDE =
        "^(-?)((\\d|[1-9]\\d|1[0-7][0-9]|180)°(\\d|[0-5]\\d)′((\\d|[0-5]\\d)(.\\d{1,4})|(\\d|[0-5]\\d))″|(\\d|[1-9]\\d|1[0-7][0-9]|180)°|(\\d|[1-9]\\d|1[0-7][0-9]|180)°(\\d|[0-5]\\d)′)$";
    
    /** 经度度分秒 */
    public static final String LONGITUDE_NOT_MUST =
        "^((-?)((\\d|[1-9]\\d|1[0-7][0-9]|180)°(\\d|[0-5]\\d)′((\\d|[0-5]\\d)(.\\d{1,4})|(\\d|[0-5]\\d))″|(\\d|[1-9]\\d|1[0-7][0-9]|180)°|(\\d|[1-9]\\d|1[0-7][0-9]|180)°(\\d|[0-5]\\d)′))?$";
    
    /** 纬度度分秒 */
    public static final String LATITUDE =
        "^(-?)((\\d|[1-9]\\d)°(\\d|[0-5]\\d)′((\\d|[0-5]\\d)(.\\d{1,4})|(\\d|[0-5]\\d))″|(\\d|[1-9]\\d)°|(\\d|[1-9]\\d)°(\\d|[0-5]\\d)′)$";
    
    /** 纬度度分秒 */
    public static final String LATITUDE_NOT_MUST =
        "^((-?)((\\d|[1-9]\\d)°(\\d|[0-5]\\d)′((\\d|[0-5]\\d)(.\\d{1,4})|(\\d|[0-5]\\d))″|(\\d|[1-9]\\d)°|(\\d|[1-9]\\d)°(\\d|[0-5]\\d)′))?$";
    
    /** 东经度度分秒-（0-180） */
    public static final String WEST_LONGITUDE =
        "^((([0-9])|([1-9][0-9])|(1[0-7][0-9]))°([0-9]|([1-5][0-9]))′(([0-9]|([1-5][0-9](.[0-9]{1,4})?)))″)|(180°0′0″)$";
    
    /** 北纬度分秒 （0-90） */
    public static final String NORTH_LATITUDE =
        "^((([0-9])|([1-8][0-9]))°([0-9]|([1-5][0-9]))′(([0-9]|([1-5][0-9](.[0-9]{1,4})?)))″)|(90°0′0″)$";
    
    /** 执法车辆 - 车牌号 */
    public static final String VEHICLE_NUMBER = "^[[\\s|\\S]*]{1,10}$";
    
    /** 执法车辆 - 归属单位 */
    public static final String VEHICLE_BELONGS = "^[[\\s|\\S]*]{1,50}$";
    
    /** 执法车辆 - 使用单位 */
    public static final String VEHICLE_USE = "^([[\\s|\\S]*]{1,50})?$";
    
    /** 执法飞机 - 飞机编号 */
    public static final String AIRPLANE_NUMBER = "^[[\\s|\\S]*]{1,20}$";
    
    /** 20个任意字符 */
    public static final String ANY_20_CHAR = "^([[\\s|\\S]*]{1,20})?$";
    
    /** 多个以逗号分隔的25个任意字符 */
    public static final String ANY_25_MORE_CHAR =
        "^(([[\\s|\\S]*]{1,25})(,)){0,}((([[\\s|\\S]*]{1,25})){0,1})$";
    
    /** 20个任意字符,必填 */
    public static final String ANY_20_CHAR_MUST = "^[[\\s|\\S]*]{1,20}$";
    
    /** 100个任意字符 */
    public static final String ANY_100_CHAR = "^([[\\s|\\S]*]{1,100})?$";
    
    /** 100个任意字符,必填 */
    public static final String ANY_100_CHAR_MUST = "^[[\\s|\\S]*]{1,100}$";
    
    /** 30个任意字符 */
    public static final String ANY_30_CHAR = "^[[\\s|\\S]*]{0,30}$";
    
    /** 30个任意字符,必填 */
    public static final String ANY_30_CHAR_MUST = "^[[\\s|\\S]*]{1,30}$";
    
    /** 50个任意字符 */
    public static final String ANY_50_CHAR = "^[[\\s|\\S]*]{0,50}$";
    
    /** 10个任意字符 */
    public static final String ANY_10_CHAR = "^[[\\s|\\S]*]{0,10}$";
    
    /** 50个任意字符,必填 */
    public static final String ANY_50_CHAR_MUST = "^[[\\s|\\S]*]{1,50}$";
    
    /** 500个任意字符 */
    public static final String ANY_500_CHAR = "^[[\\s|\\S]*]{0,500}$";
    
    /** 500个任意字符 -必填 */
    public static final String ANY_500_CHAR_MUST = "^[[\\s|\\S]*]{1,500}$";
    
    /** 400个任意字符 -必填 */
    public static final String ANY_400_CHAR_MUST = "^[[\\s|\\S]*]{1,400}$";
    
    /** 15个任意字符 */
    public static final String ANY_15_CHAR = "^[[\\s|\\S]*]{0,15}$";
    
    /** 5个任意字符 */
    public static final String ANY_5_CHAR = "^[[\\s|\\S]*]{0,5}$";
    
    /** 10个任意字符 */
    public static final String ANY_10_CHAR_MUST = "^[[\\s|\\S]*]{1,10}$";
    
    /** 200个任意字符,必填 */
    public static final String ANY_200_CHAR_MUST = "^[[\\s|\\S]*]{1,200}$";
    
    /** 200个任意字符 */
    public static final String ANY_200_CHAR = "^[[\\s|\\S]*]{0,200}$";
    
    /** 2000个任意字符 */
    public static final String ANY_2000_CHAR = "^[[\\s|\\S]*]{0,2000}$";
    
    /** 1000个任意字符 */
    public static final String ANY_1000_CHAR = "^[[\\s|\\S]*]{0,1000}$";
    
    /** 1000个任意字符 */
    public static final String ANY_1000_CHAR_MUST = "^[[\\s|\\S]*]{1,1000}$";
   
    
    /** 长10位并精确到4位小数 */
    public static final String INT_5_FRACTION_4 =
        "^((0|[1-9][0-9]{0,4})(\\.\\d{1,4})*)?$";
    
    /** 10位整数 */
    public static final String INT_10 = "^\\d{10}$";
    
    /** 0-10位整数 */
    public static final String INT_MAX_10 = "^\\d{0,10}$";
    
    /** 长20精确到6位的小数 **/
    public static final String INT_13_FRACTION_6 =
        "^((0|[1-9][0-9]{0,12})(\\.\\d{1,6})*)?$";
    
    /** 长10并精确到2位的小数 */
    public static final String INT_7_FRACTION_2 =
        "^((0|[1-9][0-9]{0,6})(\\.\\d{1,2})*)?$";
    
    /** 时间精确到月 */
    public static final String TIME_MONTH =
        "^(\\d{4}-(\\d{1}|0\\d{1}|1[0-2]))?$";
    
    /** 手机号码验证，必填 */
    public static final String TELEPHONE_MUST = "^\\d{1,20}$";
    
    /** 手机号码验证 */
    public static final String TELEPHONE = "^\\d{0,20}$";
    
    /** 手机/座机验证 */
    public static final String TELPHONE_OR_MOBILE =
        "^(\\d*(\\(\\d+\\))*(((\\(\\d+\\)|\\d)+)(-)((\\(\\d+\\)|\\d)+))*(\\(\\d+\\))*\\d*)?$";
    
    /** 手机/座机必填验证 */
    public static final String TELPHONE_OR_MOBILE_MUST =
        "^\\d*(\\(\\d+\\))*(((\\(\\d+\\)|\\d)+)(-)((\\(\\d+\\)|\\d)+))*(\\(\\d+\\))*\\d*$";
    
    /** 网址的匹配 */
    // public static final String URL = "[a-zA-Z]+\:\/\/[^\s]*";
    /*****************************移动app账号审核*********************************/
    /** 账号匹配 */
    public static final String ACCOUNT_REGEXP = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9a-zA-Z]{1,20}$";
    
}
