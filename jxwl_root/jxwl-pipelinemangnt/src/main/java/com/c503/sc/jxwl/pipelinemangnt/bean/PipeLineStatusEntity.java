/**
 * 文件名：PipeLineStatusEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-1-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.pipelinemangnt.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author huw
 * @version [版本号, 2017-1-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PipeLineStatusEntity extends BaseEntity {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /** 储罐存量 */
    private Integer stockAllowance;
    
    /** 流量 */
    private Integer flow;
    
    /** 管道OR储罐 */
    private String pipeType;
    
    /** 关联 */
    private String pipeid;
    
    /** 体积 */
    private String stockVolume;
    
    /** 液位 */
    private String liquiDlevel;
    
    /**
     * 〈一句话功能简述〉体积
     * 〈功能详细描述〉
     * 
     * @return stockVolume
     * @see [类、类#方法、类#成员]
     */
    public String getStockVolume() {
        return stockVolume;
    }
    
    /**
     * 〈一句话功能简述〉体积
     * 〈功能详细描述〉
     * 
     * @param stockVolume 体积
     * @see [类、类#方法、类#成员]
     */
    public void setStockVolume(String stockVolume) {
        this.stockVolume = stockVolume;
    }
    
    /**
     * 〈一句话功能简述〉液位
     * 〈功能详细描述〉
     * 
     * @return liquiDlevel
     * @see [类、类#方法、类#成员]
     */
    public String getLiquiDlevel() {
        return liquiDlevel;
    }
    
    /**
     * 〈一句话功能简述〉液位
     * 〈功能详细描述〉
     * 
     * @param liquiDlevel 液位
     * @see [类、类#方法、类#成员]
     */
    public void setLiquiDlevel(String liquiDlevel) {
        this.liquiDlevel = liquiDlevel;
    }
    
    /**
     * 〈一句话功能简述〉储罐存量
     * 〈功能详细描述〉
     * 
     * @return stockAllowance
     * @see [类、类#方法、类#成员]
     */
    public Integer getStockAllowance() {
        return stockAllowance;
    }
    
    /**
     * 〈一句话功能简述〉储罐存量
     * 〈功能详细描述〉
     * 
     * @param stockAllowance 储罐存量
     * @see [类、类#方法、类#成员]
     */
    public void setStockAllowance(Integer stockAllowance) {
        this.stockAllowance = stockAllowance;
    }
    
    /**
     * 〈一句话功能简述〉流量
     * 〈功能详细描述〉
     * 
     * @return flow
     * @see [类、类#方法、类#成员]
     */
    public Integer getFlow() {
        return flow;
    }
    
    /**
     * 〈一句话功能简述〉流量
     * 〈功能详细描述〉
     * 
     * @param flow 流量
     * @see [类、类#方法、类#成员]
     */
    public void setFlow(Integer flow) {
        this.flow = flow;
    }
    
    /**
     * 〈一句话功能简述〉 管道OR储罐
     * 〈功能详细描述〉
     * 
     * @return pipeType
     * @see [类、类#方法、类#成员]
     */
    public String getPipeType() {
        return pipeType;
    }
    
    /**
     * 〈一句话功能简述〉 管道OR储罐
     * 〈功能详细描述〉
     * 
     * @param pipeType 管道OR储罐
     * @see [类、类#方法、类#成员]
     */
    public void setPipeType(String pipeType) {
        this.pipeType = pipeType;
    }
    
    /**
     * 〈一句话功能简述〉关联
     * 〈功能详细描述〉
     * 
     * @return pipeid
     * @see [类、类#方法、类#成员]
     */
    public String getPipeid() {
        return pipeid;
    }
    
    /**
     * 〈一句话功能简述〉关联
     * 〈功能详细描述〉
     * 
     * @param pipeid 关联
     * @see [类、类#方法、类#成员]
     */
    public void setPipeid(String pipeid) {
        this.pipeid = pipeid;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
