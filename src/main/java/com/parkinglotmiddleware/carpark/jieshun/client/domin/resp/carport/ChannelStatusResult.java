package com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.carport;

import lombok.Data;

/**
 * 通道状态结果
 *
 * @author Suhuyuan
 */
@Data
public class ChannelStatusResult {
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 1开启 2 关闭
     */
    private int channelStatus;
}
