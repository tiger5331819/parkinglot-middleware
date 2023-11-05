package com.parkinglotmiddleware.api.dubbo.service.duecar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

/**
 * 通道车辆信息请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class DueCarConfigurationRpcReq extends ParkingLotRpcReq {

    /**
     * 不可进场启用状态(1:启用,0:禁用)
     */
    private Integer urgepayNotIn;

    /**
     * 不可离场启用状态(1:启用,0:禁用)
     */
    private Integer urgepayNotOut;

    /**
     * 生效开始时间 HH:mm:ss
     */
    private LocalTime startTime;

    /**·
     * 生效结束时间 HH:mm:ss
     */
    private LocalTime closeTime;
}
