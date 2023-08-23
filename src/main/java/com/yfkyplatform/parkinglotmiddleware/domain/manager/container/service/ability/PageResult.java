package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability;

import lombok.Data;

import java.util.List;

/**
 * 分页返回结果
 *
 * @author Suhuyuan
 */
@Data
public class PageResult<T> {
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页的数量
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int total;
    /**
     * 返回记录集合
     */
    private List<T> list;

    public PageResult(int pageNum, int pageSize, int total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
