package com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.carport;

import lombok.Data;

/**
 * @author Suhuyuan
 */
@Data
public class ChannelResult {
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 通道名称
     */
    private String channelName;
    /**
     * 0 入口 1出口
     */
    private int type;
}
