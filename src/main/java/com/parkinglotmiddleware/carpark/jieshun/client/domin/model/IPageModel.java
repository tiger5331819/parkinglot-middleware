package com.parkinglotmiddleware.carpark.jieshun.client.domin.model;

/**
 * @author Suhuyuan
 */
public interface IPageModel {

    /**
     * 配置分页信息
     *
     * @param pageNum  当前页
     * @param pageSize 页大小
     */
    default void configPage(int pageNum, int pageSize) {
    }
}
