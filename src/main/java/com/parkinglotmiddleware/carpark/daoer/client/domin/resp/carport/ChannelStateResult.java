package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport;

import lombok.Data;

/**
 * 通道状态结果
 *
 * @author Suhuyuan
 */
@Data
public class ChannelStateResult {
    /**
     * 道闸状态，0 关到位 1-中间位 2-开到位 3-关闸中
     */
    private int door;
    /**
     * 	通道名
     */
    private String channelName;
    /**
     * 地感状态 0正常 -1 离线
     */
    private int sense;
    /**
     * 相机状态 0正常 -1 离线
     */
    private int camera;
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 主板状态 0正常 -1 离线
     */
    private int board;
}
