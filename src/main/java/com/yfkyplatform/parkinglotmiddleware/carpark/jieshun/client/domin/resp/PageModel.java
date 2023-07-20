package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.resp;

import lombok.Data;

import java.util.List;

/**
 * 车辆入场或离场结果
 *
 * @author Suhuyuan
 */
@Data
public class PageModel<T> {
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
     * 总页数
     */
    private int pages;
    /**
     * 返回记录集合
     */
    private List<T> list;
}
