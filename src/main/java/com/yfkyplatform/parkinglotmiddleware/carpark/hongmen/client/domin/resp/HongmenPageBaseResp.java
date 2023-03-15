package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp;

import lombok.Data;

import java.util.List;

/**
 * 红门分页请求模型
 *
 * @author Suhuyuan
 */
@Data
public class HongmenPageBaseResp<TResponseData> {

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
     * 总页数
     */
    private Integer pageCount;

    /**
     * 总共多少条记录
     * <p>
     * 按查询条件搜索出来的记录总数
     */
    private Integer totalSize;

    /**
     * 返回的实际列表
     */
    private List<TResponseData> records;
}
