package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport;

import lombok.Data;

/**
 * @author Suhuyuan
 */
@Data
public class ChannelInfoResult {
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
