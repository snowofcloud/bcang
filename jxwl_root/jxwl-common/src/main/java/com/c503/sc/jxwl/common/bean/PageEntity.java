package com.c503.sc.jxwl.common.bean;

import java.util.Collection;

import com.c503.sc.base.entity.Page;

/**
 * 〈一句话功能简述〉PageEntity
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-28]
 * @param <T>
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PageEntity<T> extends Page {
    /** serialVersionUID */
    private static final long serialVersionUID = -246175936562734578L;
    
    /** {@inheritDoc} */
    private Collection<T> rows;
    
    /** {@inheritDoc} */
    public Collection<T> getRows() {
        return rows;
    }
    
    /** {@inheritDoc} */
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param rows Collection<T>
     * @see [类、类#方法、类#成员]
     */
    public void setRows(Collection<T> rows) {
        this.rows = rows;
    }
}
