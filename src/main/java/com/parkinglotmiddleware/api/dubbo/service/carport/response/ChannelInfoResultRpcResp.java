package com.parkinglotmiddleware.api.dubbo.service.carport.response;

import com.yfkyframework.common.core.domain.BaseRpcResp;
import lombok.Data;

/**
 * @author Suhuyuan
 */
@Data
public class ChannelInfoResultRpcResp extends BaseRpcResp {
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
    /**
     * 道闸状态，0 关到位 1-中间位 2-开到位 3-关闸中
     */
    private int door;
    /**
     * 地感状态 0正常 -1 离线
     */
    private int sense;
    /**
     * 相机状态 0正常 -1 离线
     */
    private int camera;
    /**
     * 主板状态 0正常 -1 离线
     */
    private int board;
}
