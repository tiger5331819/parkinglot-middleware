package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool;

import lombok.Data;

/**
 * 无牌车入场信息
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarURL {
    /**
     * 无牌车入场URL
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
