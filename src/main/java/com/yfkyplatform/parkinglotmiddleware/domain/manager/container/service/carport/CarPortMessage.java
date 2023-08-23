package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ChannelInfoResult;
import lombok.Data;

import java.util.List;

/**
 * 车场信息
 *
 * @author Suhuyuan
 */
@Data
public class CarPortMessage {

    /**
     * 配置文件
     */
    private ParkingLotConfiguration configuration;

    /**
     * 健康检查
     */
    private Boolean healthCheck;

    /**
     * 总车位数
     */
    private int total;

    /**
     * 空余车位数
     */
    private int rest;

    /**
     * 通道列表
     */
    List<ChannelInfoResult> channelList;
    /**
     * 在场内车位数
     */
    private int carNumber;
}
