package com.parkinglotmiddleware.domain.manager.container.service.ability.carport;

import lombok.Data;
import lombok.ToString;

/**
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class ChannelInfoWithStateResult extends ChannelInfoResult {
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
