/*
 * 文件名：ZtreeEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉所有树结构的封装类
 * 修改时间：2015年7月7日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.c503.sc.base.entity.common.OrganizationEntity;

/**
 * 〈一句话功能简述〉通用树形实体 〈功能详细描述〉所有在前台要以树的形式表现的 最后要将数据组装成正个实体类型
 * 
 * @author luocb
 * @version [版本号, 2015年7月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OrgZtreeVo extends OrganizationEntity implements Serializable {
    
    /** 序列化 */
    private static final long serialVersionUID = -3534773343699279817L;
    
    /** 树节点ID */
    private String id;
    
    /** 树节点父ID */
    private String pId;
    
    /** 树节点名字 */
    private String name;
    
    /** 树节点是否打开 */
    private boolean open = false;
    
    /** 机构类型 */
    private String typeCode;
    
    /** 子对象集合 */
    private List<OrgZtreeVo> subNodeList = new ArrayList<OrgZtreeVo>();
    
    /**
     * 〈一句话功能简述〉获得id
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉设置id
     * 〈功能详细描述〉
     * 
     * @param id id
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 〈一句话功能简述〉获得pId
     * 〈功能详细描述〉
     * 
     * @return 父Id
     * @see [类、类#方法、类#成员]
     */
    public String getpId() {
        return pId;
    }
    
    /**
     * 〈一句话功能简述〉设置pId
     * 〈功能详细描述〉
     * 
     * @param pId 父Id
     * @see [类、类#方法、类#成员]
     */
    public void setpId(String pId) {
        this.pId = pId;
    }
    
    /**
     * 〈一句话功能简述〉获得名称
     * 〈功能详细描述〉
     * 
     * @return 名称
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 〈一句话功能简述〉设置名称
     * 〈功能详细描述〉
     * 
     * @param name 名称
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 〈一句话功能简述〉获取是否打开
     * 〈功能详细描述〉
     * 
     * @return 是否打开
     * @see [类、类#方法、类#成员]
     */
    public boolean isOpen() {
        return open;
    }
    
    /**
     * 〈一句话功能简述〉设置是否打开
     * 〈功能详细描述〉
     * 
     * @param open 是否打开
     * @see [类、类#方法、类#成员]
     */
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    /**
     * 〈一句话功能简述〉获得机构类型
     * 〈功能详细描述〉
     * 
     * @return 机构类型
     * @see [类、类#方法、类#成员]
     */
    public String getTypeCode() {
        return getOrgType();
    }
    
    /**
     * 〈一句话功能简述〉设置机构类型
     * 〈功能详细描述〉
     * 
     * @param typeCode 机构类型
     * @see [类、类#方法、类#成员]
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
    /**
     * 〈一句话功能简述〉获得子对象集合
     * 〈功能详细描述〉
     * 
     * @return 子对象集合
     * @see [类、类#方法、类#成员]
     */
    public List<OrgZtreeVo> getSubNodeList() {
        return subNodeList;
    }
    
    /**
     * 〈一句话功能简述〉设置子对象集合
     * 〈功能详细描述〉
     * 
     * @param subNodeList 子对象集合
     * @see [类、类#方法、类#成员]
     */
    public void setSubNodeList(List<OrgZtreeVo> subNodeList) {
        this.subNodeList = subNodeList;
    }
    
    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String toString() {
        return "OrgZtreeVo [id=" + id + ", pId=" + pId + ", name=" + name
            + ", open=" + open + ",typeCode=" + typeCode + "]";
    }
}
