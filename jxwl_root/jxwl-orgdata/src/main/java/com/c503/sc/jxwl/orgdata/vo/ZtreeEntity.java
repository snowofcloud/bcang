/*
 * 文件名：ZtreeEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉所有树结构的封装类
 * 修改时间：2015年7月7日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.vo;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉通用树形实体 〈功能详细描述〉所有在前台要以树的形式表现的 最后要将数据组装成正个实体类型
 * 
 * @author luocb
 * @version [版本号, 2015年7月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ZtreeEntity implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = -3534773343699279817L;
    
    /** 树节点ID */
    private String id;
    
    /** 树节点父ID */
    private String pId;
    
    /** 树节点名字 */
    private String name;
    
    /** 树节点是否打开 */
    private boolean open = false;
    
    /** 该节点的状态 0：启用，1：停用 */
    private String status;
    
    /** 该节点是否选中true:选中 false：没选中 */
    private boolean checked = false;
    
    /** 该节点的特定值 比如菜单拥有的操作 和某一个操作的操作值 **/
    private int value;
    
    /** 拓展属性判断 该节点是否有下级节点 0：没有子节点; 1：有子节点 */
    private int end;
    
    /** 树节点类型的标示字段 ;0:系统节点；1:机构节点 2:角色节点 ； 3:用户节点；4:模块节点；5：功能节点 */
    private int type;
    
    /** 菜单的URL */
    private String url;
    
    /**
     * 
     * 〈一句话功能简述〉获取 树节点ID 〈功能详细描述〉
     * 
     * @return 树节点ID
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置 树节点ID 〈功能详细描述〉
     * 
     * @param id 树节点ID
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取该节点的父节点id 〈功能详细描述〉
     * 
     * @return 该节点的父节点id
     * @see [类、类#方法、类#成员]
     */
    public String getpId() {
        return pId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置该节点的父节点id 〈功能详细描述〉
     * 
     * @param pId 该节点的父节点id
     * @see [类、类#方法、类#成员]
     */
    public void setpId(String pId) {
        if ("0".equals(pId) || "000000".equals(pId)) {
            setOpen(true);
        }
        this.pId = pId;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取节点的名字 〈功能详细描述〉
     * 
     * @return 节点的名字
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置节点的名字 〈功能详细描述〉
     * 
     * @param name 节点的名字
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取是否倒开 〈功能详细描述〉
     * 
     * @return true:打开，false:不打开
     * @see [类、类#方法、类#成员]
     */
    public boolean isOpen() {
        return open;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置是否打开 〈功能详细描述〉
     * 
     * @param open true:打开，false:不打开
     * @see [类、类#方法、类#成员]
     */
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取是否选中 〈功能详细描述〉
     * 
     * @return true:选中，false:没选中
     * @see [类、类#方法、类#成员]
     */
    public boolean isChecked() {
        return checked;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置是否选中 〈功能详细描述〉
     * 
     * @param checked true:选中，false:没选中
     * @see [类、类#方法、类#成员]
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取树节点的类型 〈功能详细描述〉 树节点类型的标示字段 1:地区节点 2：机构节点 3：部门节点 4：菜单节点
     * 5：菜单操作节点
     * 
     * @return 树节点的类型
     * @see [类、类#方法、类#成员]
     */
    public int getType() {
        return type;
    }
    
    /**
     * 〈一句话功能简述〉设置书节点的类型 〈功能详细描述〉 树节点类型的标示字段 1:地区节点 2：机构节点 3：部门节点 4：菜单节点
     * 5：菜单操作节点
     * 
     * @param type 树节点的类型
     * @see [类、类#方法、类#成员]
     */
    public void setType(int type) {
        this.type = type;
    }
    
    /**
     * 〈一句话功能简述〉获取菜单操作值 〈功能详细描述〉
     * 
     * @return 菜单操作值
     * @see [类、类#方法、类#成员]
     */
    public int getValue() {
        return value;
    }
    
    /**
     * 〈一句话功能简述〉设置菜单拥有的操作 〈功能详细描述〉
     * 
     * @param value 菜单操作值
     * @see [类、类#方法、类#成员]
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * 〈一句话功能简述〉获取当前节点是否有子节点 〈功能详细描述〉0：没有子节点 1：有子节点
     * 
     * @return 0：没有子节点; 1：有子节点
     * @see [类、类#方法、类#成员]
     */
    public int getEnd() {
        return end;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置当前节点是否有子节点 〈功能详细描述〉
     * 
     * @param end 0：没有子节点; 1：有子节点
     * @see [类、类#方法、类#成员]
     */
    public void setEnd(int end) {
        this.end = end;
    }
    
    /**
     * 〈一句话功能简述〉设置该节点的状态值 〈功能详细描述〉
     * 
     * @return 该节点的状态值
     * @see [类、类#方法、类#成员]
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 
     * 〈一句话功能简述>获取该节点的url 〈功能详细描述〉
     * 
     * @return 该节点的url
     * @see [类、类#方法、类#成员]
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置该节点的url 〈功能详细描述〉
     * 
     * @param url 该节点的url
     * @see [类、类#方法、类#成员]
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置该节点的状态值 〈功能详细描述〉
     * 
     * @param status 该节点的状态值
     * @see [类、类#方法、类#成员]
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ZtreeEntity [id=" + id + ", pId=" + pId + ", name=" + name
            + ", open=" + open + ", status=" + status + ", checked=" + checked
            + ", value=" + value + ", end=" + end + ", type=" + type + ", url="
            + url + "]";
    }
    
}
