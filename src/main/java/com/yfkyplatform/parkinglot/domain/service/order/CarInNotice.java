package com.yfkyplatform.parkinglot.domain.service.order;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆入场通知
 *
 * @author Suhuyuan
 */
@Data
public class CarInNotice {
    /**
     * 停车场Id
     */
    private String parkingLotId;
    /**
     * 车牌号
     */
    private String carNo;
    /**
     * 车辆类型
     */
    private int carType;
    /**
     * 入场时间
     */
    private LocalDateTime inTime;
    /**
     * 入场照片
     */
    private String inPic;
}
