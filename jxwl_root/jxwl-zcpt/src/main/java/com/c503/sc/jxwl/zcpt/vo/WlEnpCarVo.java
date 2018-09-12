/**
 * 文件名：AlarmNumVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.vo;

import java.util.List;

/**
 * 
 * 〈一句话功能简述〉物流企业下的车辆
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-7-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WlEnpCarVo {
    /** 企业名称 */
    private String enterpriseName;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 总车辆数 */
    private int carNum;
    
    /** 当前企业下的所有车辆 */
    private List<WlCarVo> cars;
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @return 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @param enterpriseName 企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @return 法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateNo 法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉总车辆数
     * 〈功能详细描述〉
     * 
     * @return 总车辆数
     * @see [类、类#方法、类#成员]
     */
    public int getCarNum() {
        return carNum;
    }
    
    /**
     * 〈一句话功能简述〉总车辆数
     * 〈功能详细描述〉
     * 
     * @param carNum 总车辆数
     * @see [类、类#方法、类#成员]
     */
    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }
    
    /**
     * 〈一句话功能简述〉当前企业下的所有车辆
     * 〈功能详细描述〉
     * 
     * @return 当前企业下的所有车辆
     * @see [类、类#方法、类#成员]
     */
    public List<WlCarVo> getCars() {
        return cars;
    }
    
    /**
     * 〈一句话功能简述〉当前企业下的所有车辆
     * 〈功能详细描述〉
     * 
     * @param cars 当前企业下的所有车辆
     * @see [类、类#方法、类#成员]
     */
    public void setCars(List<WlCarVo> cars) {
        this.cars = cars;
    }
    
}
