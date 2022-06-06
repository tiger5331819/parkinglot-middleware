package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.carport;

import lombok.Data;

import java.util.List;

/**
 * 车辆入场或离场结果
 *
 * @author Suhuyuan
 */
@Data
public class CarInOrOutResult<T> {
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
