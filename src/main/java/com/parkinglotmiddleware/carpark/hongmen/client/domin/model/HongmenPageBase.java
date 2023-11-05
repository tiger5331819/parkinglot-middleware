package com.parkinglotmiddleware.carpark.hongmen.client.domin.model;

import lombok.Data;

/**
 * 红门分页请求模型
 *
 * @author Suhuyuan
 */
@Data
public class HongmenPageBase<TRequestData> {
    /**
     * 停车场编号
     */
    private String parkingId;

    /**
     * 第几页数据
     * <p>
     * 从 0 开始，0 表示第一页数据。
     */
    private Integer currentPage;

    /**
     * 最多返回多少条记录
     */
    private Integer pageSize;

    /**
     * 定义查询条件
     */
    private TRequestData condition;
}
