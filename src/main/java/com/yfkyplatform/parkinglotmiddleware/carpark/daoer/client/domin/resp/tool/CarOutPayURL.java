package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool;

import lombok.Data;

/**
 * 通道出场缴费信息
 *
 * @author Suhuyuan
 */
@Data
public class CarOutPayURL {
    /**
     * 通道出场缴费URL
     */
    private String url;

    /**
     * 通道ID
     */
    private String channelId;

    /**
     * 通道名
     */
    private String channelName;

    /**
     * 0 入口 1出口
     */
    private Integer type;
}
