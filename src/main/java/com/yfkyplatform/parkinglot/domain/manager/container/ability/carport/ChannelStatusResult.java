package com.yfkyplatform.parkinglot.domain.manager.container.ability.carport;

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
