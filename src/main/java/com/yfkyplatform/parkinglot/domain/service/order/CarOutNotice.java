package com.yfkyplatform.parkinglot.domain.service.order;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆出场通知
 *
 * @author Suhuyuan
 */
@Data
public class CarOutNotice {
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
     * 出场时间
     */
    private LocalDateTime outTime;
    /**
     * 出场照片
     */
    private String outPic;
}
