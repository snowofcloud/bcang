package com.xly.spring.boot.mybatisplus.server.base.model;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @auther xuxq
 * @date 2018/11/23 15:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> implements Serializable {

    private List<T> list;//当前页数据
    private Integer pageIndex;//当前页码
    private Integer pageSize;//当前每页数量
    private Long count;//当前数据总数
    private Long pageTotal;//当前数据总页数

    public static <T> PageInfo<T> format(List<T> list, Integer pageIndex, Integer pageSize, Long count, Long pageTotal) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setCount(count);
        pageInfo.setPageIndex(pageIndex);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageTotal(pageTotal);
        return pageInfo;
    }

    public PageInfo(Page<T> page) {
        super();
        this.list = page.getRecords();
        this.pageIndex = page.getCurrent();
        this.pageSize = page.getSize();
        this.count = page.getTotal();
        this.pageTotal = page.getPages();
    }


}
