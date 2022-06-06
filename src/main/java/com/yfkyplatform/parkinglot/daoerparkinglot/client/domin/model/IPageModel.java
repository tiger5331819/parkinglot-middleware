package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model;

/**
 * @author tiger
 */
public interface IPageModel {

    /**
     * 配置分页信息
     * @param pageNum 当前页
     * @param pageSize 页大小
     */
    default void configPage(int pageNum,int pageSize){
    }
}
