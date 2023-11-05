package com.parkinglotmiddleware.domain.manager.container.service.ability.carport;

import lombok.Data;

/**
 * 通道开关状态结果
 *
 * @author Suhuyuan
 */
@Data
public class ChannelDoorStateResult {
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 通道开关状态
     * 1开启 0 关闭
     */
    private Boolean channelStatus;
}
